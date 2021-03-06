package com.ryx.credit.machine.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.agentUtil.AESUtil;
import com.ryx.credit.common.util.agentUtil.RSAUtil;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.*;
import com.ryx.credit.pojo.admin.order.ORefundPriceDiffDetail;
import com.ryx.credit.pojo.admin.order.TerminalTransferDetail;
import com.ryx.credit.util.Constants;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 作者：zhaoyd
 * 时间：2019/10/18
 * 描述：瑞+平台接口
 */
@Service("rjTermMachineServiceImpl")
public class RJPosTermMachineServiceImpl implements TermMachineService {

    private static Logger logger = LoggerFactory.getLogger(RJPosTermMachineServiceImpl.class);

    /**
     * 物流下发
     * @param lowerHairMachineVo
     * @return
     * @throws Exception
     */
    @Override
    public AgentResult lowerHairMachine(LowerHairMachineVo lowerHairMachineVo) throws Exception {
        try {
            String cooperator = Constants.cooperator;
            String charset = "UTF-8"; // 字符集
            String tranCode = "Sns003"; // 交易码
            String reqMsgId = UUID.randomUUID().toString().replace("-", ""); // 请求流水
            String reqDate = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"); // 请求时间

            //组装参数
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("version", "1.0.0");
            jsonParams.put("msgType", "01");
            jsonParams.put("reqDate", reqDate);
            jsonParams.put("data", JSONObject.parseObject(lowerHairMachineVo.getJsonString()));
            String plainXML = jsonParams.toString();
            logger.info("瑞+物流下发接口封装参数:{}", plainXML);
            //请求报文加密开始
            String keyStr = AESUtil.getAESKey();
            byte[] plainBytes = plainXML.getBytes(charset);
            byte[] keyBytes = keyStr.getBytes(charset);
            String encryptData = new String(Base64.encodeBase64((AESUtil.encrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
            String signData = new String(Base64.encodeBase64(RSAUtil.digitalSign(plainBytes, Constants.privateKey, "SHA1WithRSA")), charset);
            String encrtptKey = new String(Base64.encodeBase64(RSAUtil.encrypt(keyBytes, Constants.publicKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
            //请求报文加密结束

            Map<String, String> map = new HashMap<>();
            map.put("encryptData", encryptData);
            map.put("encryptKey", encrtptKey);
            map.put("cooperator", cooperator);
            map.put("signData", signData);
            map.put("tranCode", tranCode);
            map.put("reqMsgId", reqMsgId);

            //下发接口
            logger.info("瑞+物流下发接口请求地址:{},参数:{},", AppConfig.getProperty("rjpos.queryTermActive"), JSONObject.toJSONString(map));
            String respResult = HttpClientUtil.doGet(AppConfig.getProperty("rjpos.queryTermActive"), map);
            logger.info("瑞+物流下发接口返回参数:{}", respResult);

            JSONObject jsonObject = JSONObject.parseObject(respResult);
            if (!(jsonObject.containsKey("encryptData") && jsonObject.containsKey("encryptKey"))) return AgentResult.build(2,"瑞+请求下发返回失败！");

            String resEncryptData = jsonObject.getString("encryptData");
            String resEncryptKey = jsonObject.getString("encryptKey");
            byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
            byte[] merchantAESKeyBytes = RSAUtil.decrypt(decodeBase64KeyBytes, Constants.privateKey, 2048, 11, "RSA/ECB/PKCS1Padding");
            byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
            byte[] merchantXmlDataBytes = AESUtil.decrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
            String respXML = new String(merchantXmlDataBytes, charset);
            logger.info("瑞+物流下发接口返回参数解密后:{}", respXML);

            //验签
            String resSignData = jsonObject.getString("signData");
            byte[] signBytes = Base64.decodeBase64(resSignData);
            if (!RSAUtil.verifyDigitalSign(respXML.getBytes(charset), signBytes, Constants.publicKey, "SHA1WithRSA"))
                return AgentResult.build(2,"验证签名失败");

            JSONObject respJson = JSONObject.parseObject(respXML);
            if (null != respJson.getString("respCode") && "000000".equals(respJson.getString("respCode"))) {
                //下发成功
                return AgentResult.build(0,"下发成功");
            } else {
                //下发异常
                logger.info("瑞+物流下发接口返回异常:{}", respResult);
                return AgentResult.build(2, null != respJson.getString("respMsg") ? respJson.getString("respMsg") : "瑞+平台返回异常!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 查询物流下发结果
     * @param pamMap
     * @param platformType
     * @return
     * @throws Exception
     */
    @Override
    public AgentResult queryLogisticsResult(Map<String, Object> pamMap, String platformType) throws Exception {
        try {
            String cooperator = Constants.cooperator;
            String charset = "UTF-8"; // 字符集
            String tranCode = "Sns002"; // 交易码
            String reqMsgId = UUID.randomUUID().toString().replace("-", ""); // 请求流水
            String reqDate = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"); // 请求时间

            //组装参数
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("version", "1.0.0");
            jsonParams.put("msgType", "01");
            jsonParams.put("reqDate", reqDate);
            jsonParams.put("data", JSONObject.toJSONString(FastMap.fastMap("taskId", pamMap.get("taskId"))));
            String plainXML = jsonParams.toString();
            logger.info("瑞+机具下发查询接口请求参数:{}", plainXML);
            //请求报文加密开始
            String keyStr = AESUtil.getAESKey();
            byte[] plainBytes = plainXML.getBytes(charset);
            byte[] keyBytes = keyStr.getBytes(charset);
            String encryptData = new String(Base64.encodeBase64((AESUtil.encrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
            String signData = new String(Base64.encodeBase64(RSAUtil.digitalSign(plainBytes, Constants.privateKey, "SHA1WithRSA")), charset);
            String encrtptKey = new String(Base64.encodeBase64(RSAUtil.encrypt(keyBytes, Constants.publicKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
            //请求报文加密结束

            Map<String, String> map = new HashMap<>();
            map.put("encryptData", encryptData);
            map.put("encryptKey", encrtptKey);
            map.put("cooperator", cooperator);
            map.put("signData", signData);
            map.put("tranCode", tranCode);
            map.put("reqMsgId", reqMsgId);

            //下发接口
            logger.info("瑞+机具下发查询接口请求加密参数:{}", JSONObject.toJSONString(map));
            String respResult = HttpClientUtil.doPost(AppConfig.getProperty("rjpos.queryTermActive"), map);
            logger.info("瑞+机具下发查询接口返回加密参数:{}", respResult);

            JSONObject jsonObject = JSONObject.parseObject(respResult);
            if (!(jsonObject.containsKey("encryptData") && jsonObject.containsKey("encryptKey"))) return AgentResult.build(2,"瑞+请求下发返回失败！");

            String resEncryptData = jsonObject.getString("encryptData");
            String resEncryptKey = jsonObject.getString("encryptKey");
            byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
            byte[] merchantAESKeyBytes = RSAUtil.decrypt(decodeBase64KeyBytes, Constants.privateKey, 2048, 11, "RSA/ECB/PKCS1Padding");
            byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
            byte[] merchantXmlDataBytes = AESUtil.decrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
            String respXML = new String(merchantXmlDataBytes, charset);
            logger.info("瑞+物流下发查询接口返回参数:{}", respXML);

            //验签
            String resSignData = jsonObject.getString("signData");
            byte[] signBytes = Base64.decodeBase64(resSignData);
            if (!RSAUtil.verifyDigitalSign(respXML.getBytes(charset), signBytes, Constants.publicKey, "SHA1WithRSA"))
                return AgentResult.build(2,"验证签名失败");

            JSONArray respArray = JSONObject.parseObject(respXML).getJSONObject("data").getJSONArray("SNMachineStorageLog");

            if (null == respArray){
                return AgentResult.build(2,"未查到信息，请重新下发");
            }

            JSONObject retObject = (JSONObject) respArray.get(0);

            logger.info("瑞+查询结果最终值:{}", retObject);
            if (null != retObject.getString("STATUS") && "0".equals(retObject.getString("STATUS"))) {
                //处理中
                return AgentResult.build(0,"处理中");
            } else if (null != retObject.getString("STATUS") && "6".equals(retObject.getString("STATUS"))) {
                //处理成功
                return AgentResult.build(1, null != retObject.getString("REASON") ? retObject.getString("REASON") : "瑞+平台划拨成功。");
            } else if (null != retObject.getString("STATUS") && "1".equals(retObject.getString("STATUS"))) {
                //处理失败
                return AgentResult.build(2, null != retObject.getString("REASON") ? retObject.getString("REASON") : "失败，瑞+未返回失败原因。");
            } else if (null != retObject.getString("STATUS") && "2".equals(retObject.getString("STATUS"))) {
                //程序失败
                return AgentResult.build(2, null != retObject.getString("REASON") ? retObject.getString("REASON") : "瑞+平台程序崩溃，请稍后重新下发。");
            } else {
                //瑞+返回值异常
                return AgentResult.build(2, "瑞+查询接口返回异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public AgentResult unFreezeCompensate(Map<String, Object> pamMap, String platformType) throws Exception {
        return AgentResult.ok();
    }

    @Override
    public AgentResult resendFailedCompensate(Map<String, Object> pamMap, String platformType) throws Exception {
        return AgentResult.fail("瑞+平台暂不支持重新发送！");
        /*try {
            String httpString = JSONObject.toJSONString(pamMap);
            logger.info("手刷换活动失败重新发送参数:{},{}", AppConfig.getProperty("mpos.termUnlock"), httpString);
            String retString = HttpClientUtil.doPostJson(AppConfig.getProperty("mpos.termUnlock"), httpString);
            logger.info("手刷换活动失败重新发送返回值:{}", retString);

            //验证返回值
            if (!StringUtils.isNotBlank(retString)) return AgentResult.fail("手刷换活动失败重新发送，返回值为空。");
            JSONObject resJson = JSONObject.parseObject(retString);

            //返回最终查询结果
            if (null != resJson.getString("code") && resJson.getString("code").equals("00")) {
                //可以更换活动，封装参参数返回
                return AgentResult.ok(resJson.get("result"));
            } else if (null != resJson.getString("code") && resJson.getString("code").equals("9999") && null != resJson.getString("msg")) {
                //不可以更换活动
                return AgentResult.fail(resJson.getString("msg") + "，不可以更换活动！");
            } else {
                //异常结果
                return AgentResult.fail("查询手刷换活动返回值异常！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }*/
    }

    @Override
    public AgentResult agentFNoorbidde(List<String> pamMap, String platformType) throws Exception {
        return null;
    }

    /**
     * 查询活动
     * @param platformType
     * @param pamMap
     * @return
     * @throws Exception
     */
    @Override
    public List<TermMachineVo> queryTermMachine(PlatformType platformType, Map<String, String> pamMap) throws Exception {
        if (null == pamMap.get("busPlatForm")) throw new MessageException("瑞+活动缺少必要参数");

        try {
            String cooperator = Constants.cooperator;
            String charset = "UTF-8"; // 字符集
            String tranCode = "Sns001"; // 交易码
            String reqMsgId = UUID.randomUUID().toString().replace("-", ""); // 请求流水
            String reqDate = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"); // 请求时间

            //组装参数
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("version", "1.0.0");
            jsonParams.put("msgType", "01");
            jsonParams.put("reqDate", reqDate);
            jsonParams.put("data", JSONObject.parseObject(JSONObject.toJSONString(FastMap.fastMap("branchId", pamMap.get("busPlatForm")))));
            String plainXML = jsonParams.toString();
            logger.info("RJ机具查询参数:{}", plainXML);
            //请求报文加密开始
            String keyStr = AESUtil.getAESKey();
            byte[] plainBytes = plainXML.getBytes(charset);
            byte[] keyBytes = keyStr.getBytes(charset);
            String encryptData = new String(Base64.encodeBase64((AESUtil.encrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
            String signData = new String(Base64.encodeBase64(RSAUtil.digitalSign(plainBytes, Constants.privateKey, "SHA1WithRSA")), charset);
            String encrtptKey = new String(Base64.encodeBase64(RSAUtil.encrypt(keyBytes, Constants.publicKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
            //请求报文加密结束

            Map<String, String> map = new HashMap<>();
            map.put("encryptData", encryptData);
            map.put("encryptKey", encrtptKey);
            map.put("cooperator", cooperator);
            map.put("signData", signData);
            map.put("tranCode", tranCode);
            map.put("reqMsgId", reqMsgId);

            //查询接口
            logger.info("RJ查询活动接口请求加密参数:{},{}", AppConfig.getProperty("rjpos.queryTermActive"), JSONObject.toJSONString(map));
            String respResult = HttpClientUtil.doPost(AppConfig.getProperty("rjpos.queryTermActive"), map);
            logger.info("RJ查询活动接口返回加密参数:{}", respResult);

            JSONObject jsonObject = JSONObject.parseObject(respResult);
            if (!(jsonObject.containsKey("encryptData") && jsonObject.containsKey("encryptKey"))) throw new Exception("瑞+查询活动返回失败！");

            String resEncryptData = jsonObject.getString("encryptData");
            String resEncryptKey = jsonObject.getString("encryptKey");
            byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
            byte[] merchantAESKeyBytes = RSAUtil.decrypt(decodeBase64KeyBytes, Constants.privateKey, 2048, 11, "RSA/ECB/PKCS1Padding");
            byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
            byte[] merchantXmlDataBytes = AESUtil.decrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
            String respXML = new String(merchantXmlDataBytes, charset);
            logger.info("瑞+查询活动接口返回参数:{}", respXML);

            //验签
            String resSignData = jsonObject.getString("signData");
            byte[] signBytes = Base64.decodeBase64(resSignData);
            if (!RSAUtil.verifyDigitalSign(respXML.getBytes(charset), signBytes, Constants.publicKey, "SHA1WithRSA")) throw new Exception("验证签名失败");

            JSONArray machineList = JSONObject.parseObject(respXML).getJSONObject("data").getJSONArray("machineTypeList");

            List<TermMachineVo> resData = new ArrayList<TermMachineVo>();
            for (int i = 0; i < machineList.size(); i++) {
                JSONObject item =  machineList.getJSONObject(i);
                TermMachineVo machineVo =  new TermMachineVo();
                machineVo.setId(item.getString("MACHINEID"));
                machineVo.setMechineName(
                        "机具编号:" + item.getString("MACHINEID")
                        + "|机具型号:" + item.getString("MODELNAME")
                        + "|机具类型:" + item.getString("MACHINETYPENAME")
                        + "|押金:" + item.getString("DEPOSIT")
                        + "|备注:" + (StringUtils.isNotEmpty(item.getString("REMARK"))?item.getString("REMARK"):"--"));
                machineVo.setPosType(item.getString("MODELTYPE"));
                machineVo.setPosSpePrice(item.getString("DEPOSIT"));
                resData.add(machineVo);
            }
            return resData;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<MposTermBatchVo> queryMposTermBatch(PlatformType platformType) throws Exception {
        return null;
    }

    @Override
    public List<MposTermTypeVo> queryMposTermType(PlatformType platformType) throws Exception {
        return null;
    }

    @Override
    public AgentResult adjustmentMachine(AdjustmentMachineVo adjustmentMachineVo) throws Exception {
        return null;
    }

    @Override
    public AgentResult changeActMachine(ChangeActMachineVo changeActMachineVo) throws Exception {
        return null;
    }

    @Override
    public JSONObject request(Map data, String url) throws Exception {
        return null;
    }

    @Override
    public AgentResult querySnMsg(PlatformType platformType, String snBegin, String snEnd) throws Exception {
        return null;
    }

    @Override
    public AgentResult synOrVerifyCompensate(List<ORefundPriceDiffDetail> refundPriceDiffDetailList, String operation, String isFreeze) throws ProcessException {
        return AgentResult.ok();
    }

    @Override
    public AgentResult queryCompensateResult(Map<String, Object> map, String platformType) throws Exception {
        return AgentResult.ok("04");
    }

    @Override
    public boolean checkModleIsEq(Map<String, String> data, String platformType) {
        return false;
    }

    @Override
    public AgentResult checkOrderReturnSN(List<Map<String, Object>> list, String platformType) throws Exception {
        return AgentResult.ok();
    }

    @Override
    public AgentResult unfreezeOrderReturnSN(List<Map<String, Object>> list, String platformType) throws Exception {
        return AgentResult.ok();
    }


    /**
     * 终端划拨检验请求
     * @param terminalTransferDetailLists 划拨明细
     * @param operation 校验类型
     * @return
     * @throws Exception
     */
    @Override
    public AgentResult queryTerminalTransfer(List<TerminalTransferDetail> terminalTransferDetailLists, String operation,String taskId) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("operation", operation);
        jsonObject.put("taskId",taskId);

        List<Map<String, Object>> listDetail = new ArrayList<>();
        for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailLists) {
            Map<String, Object> mapDetail = new HashMap<>();
            mapDetail.put("agentOrgId", terminalTransferDetail.getOriginalOrgId());
            mapDetail.put("newOrgId", terminalTransferDetail.getGoalOrgId());
            mapDetail.put("posNum", String.valueOf(terminalTransferDetail.getSnCount()));
            mapDetail.put("posSnBegin", terminalTransferDetail.getSnBeginNum());
            mapDetail.put("posSnEnd", terminalTransferDetail.getSnEndNum());
            mapDetail.put("serialNumber", terminalTransferDetail.getId());
            mapDetail.put("num", String.valueOf(terminalTransferDetail.getComSnNum()));
            listDetail.add(mapDetail);
        }
        jsonObject.put("snList", listDetail);

        if("check".equals(operation)){
            jsonObject.put("isfeeze","00");
            logger.info("RJPOS的请求参数==请求参数:{}",JSONObject.toJSON(jsonObject));
            return  request( jsonObject,"transfer003");
        }else{
            logger.info("RJPOS的请求参数==请求参数:{}",JSONObject.toJSON(jsonObject));
            return  request(jsonObject,"transfer004");
        }

    }

    /**
     * 终端划拨结果查询
     * @param serialNumber 批次号
     * @param type 平台
     * @return
     * @throws Exception
     */
    @Override
    public AgentResult queryTerminalTransferResult(String serialNumber,String type) throws Exception{
        JSONObject data = new JSONObject();
        data.put("serialNumber", serialNumber);
        return request( data,"transfer002");
    }

    /**
     * 终端划拨解锁
     * @param taskId  总批次号
     * @param serialNumber  单个批次号
     * @param type
     * @return
     * @throws Exception
     */
    @Override
    public AgentResult terminalTransferunlock(String taskId, String serialNumber, String type) throws Exception {
        JSONObject data = new JSONObject();
        data.put("taskId",taskId);
        data.put("serialNumber",serialNumber);
        logger.info("RJPOS终端划拨解锁参数"+JSONObject.toJSON(data));
        return  request( data,"transfer001");
    }

    @Override
    public AgentResult terminalTransAgain(Map<String,Object>  param) throws Exception {
        JSONObject data = new JSONObject();
        data.put("taskId",param.get("taskId"));
        data.put("serialNumber",param.get("serialNumber"));
        logger.info("RJPOS终端划拨重新发起参数"+JSONObject.toJSON(data));
        return  request( data,"transfer005");
    }


    /**
     * 封装请求参数
     * chenliang
     * @param data  封装好的map
     * @param transfer 交易码
     * @return
     * @throws Exception
     */
    public AgentResult request(JSONObject data,String transfer) throws Exception {
        try {
            String cooperator = Constants.cooperator;
            String charset = "UTF-8"; // 字符集
            String tranCode = transfer; // 交易码
            String reqMsgId = UUID.randomUUID().toString().replace("-", ""); // 请求流水
            String reqDate = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"); // 请求时间

            //组装参数
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("version", "1.0.0");
            jsonParams.put("msgType", "01");
            jsonParams.put("reqDate", reqDate);
            jsonParams.put("data", data);
            String plainXML = jsonParams.toString();
            logger.info("RJ请求参数:{}", plainXML);
            //请求报文加密开始
            String keyStr = AESUtil.getAESKey();
            byte[] plainBytes = plainXML.getBytes(charset);
            byte[] keyBytes = keyStr.getBytes(charset);
            String encryptData = new String(Base64.encodeBase64((AESUtil.encrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
            String signData = new String(Base64.encodeBase64(RSAUtil.digitalSign(plainBytes, Constants.privateKey, "SHA1WithRSA")), charset);
            String encrtptKey = new String(Base64.encodeBase64(RSAUtil.encrypt(keyBytes, Constants.publicKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
            //请求报文加密结束

            Map<String, String> map = new HashMap<>();
            map.put("encryptData", encryptData);
            map.put("encryptKey", encrtptKey);
            map.put("cooperator", cooperator);
            map.put("signData", signData);
            map.put("tranCode", tranCode);
            map.put("reqMsgId", reqMsgId);

            //查询接口
            logger.info("RJ接口请求加密参数:{},{}", AppConfig.getProperty("rjpos.queryTermActive"), JSONObject.toJSONString(map));
            String respResult = HttpClientUtil.doPost(AppConfig.getProperty("rjpos.queryTermActive"), map);
            logger.info("RJ返回加密参数:{}", respResult);

            JSONObject jsonObject = JSONObject.parseObject(respResult);
            if (!(jsonObject.containsKey("encryptData") && jsonObject.containsKey("encryptKey"))) {
                throw new Exception("瑞+查询返回失败！");
            }

            String resEncryptData = jsonObject.getString("encryptData");
            String resEncryptKey = jsonObject.getString("encryptKey");
            byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
            byte[] merchantAESKeyBytes = RSAUtil.decrypt(decodeBase64KeyBytes, Constants.privateKey, 2048, 11, "RSA/ECB/PKCS1Padding");
            byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
            byte[] merchantXmlDataBytes = AESUtil.decrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
            String respXML = new String(merchantXmlDataBytes, charset);
            logger.info("瑞+接口返回参数:{}", respXML);

            //验签
            String resSignData = jsonObject.getString("signData");
            byte[] signBytes = Base64.decodeBase64(resSignData);
            if (!RSAUtil.verifyDigitalSign(respXML.getBytes(charset), signBytes, Constants.publicKey, "SHA1WithRSA")){
                logger.info("瑞+签名验证失败");
            }else {
                logger.info("瑞+签名验证成功");
                JSONObject respXMLObj = JSONObject.parseObject(respXML);
                String respCode = String.valueOf(respXMLObj.get("respCode"));
                if (respCode.equals("000000")) {
                    return AgentResult.build(200, respXMLObj.toString(),respXMLObj.toString());
                } else {
                    logger.info("http请求超时返回错误:{}", respXML);
                    return AgentResult.fail(respXMLObj.toString());
                }
            }
            return new AgentResult(500, "http请求异常", respXML);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
