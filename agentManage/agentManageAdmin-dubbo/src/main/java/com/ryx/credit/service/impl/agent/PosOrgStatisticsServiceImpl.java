package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.common.enumc.TerminalPlatformType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.Des3Util;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.common.util.agentUtil.AESUtil;
import com.ryx.credit.common.util.agentUtil.RSAUtil;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.PlatFormMapper;
import com.ryx.credit.dao.order.OOrderMapper;
import com.ryx.credit.dao.order.OSubOrderMapper;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.AgentColinfo;
import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.service.agent.BusinessPlatformService;
import com.ryx.credit.service.agent.PosOrgStatisticsService;
import com.ryx.credit.util.Constants;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by RYX on 2018/10/9.
 */
@Service("posOrgStatisticsService")
public class PosOrgStatisticsServiceImpl implements PosOrgStatisticsService {

    private static Logger log = LoggerFactory.getLogger(PosOrgStatisticsServiceImpl.class);

    private static final String rhbReqUrl = AppConfig.getProperty("rhb_req_url");
    private static final String rhb3desKey = AppConfig.getProperty("rhb_3des_Key");
    private static final String rhb3desIv = AppConfig.getProperty("rhb_3des_iv");
    @Autowired
    private PlatFormMapper platFormMapper;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private BusinessPlatformService businessPlatformService;
    @Autowired
    private OSubOrderMapper oSubOrderMapper;

//    @Override
//    public AgentResult posOrgStatistics(String busPlatform,String orgId,String busId,String termType)throws Exception{
//        PlatForm platForm = platFormMapper.selectByPlatFormNum(busPlatform);
//        String platformType = platForm.getPlatformType();
//        AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(busId);
//        if(StringUtils.isBlank(busId)){
//            throw new MessageException("业务ID不存在");
//        }
//        if(StringUtils.isBlank(agentBusInfo.getBusParent())){
//            throw new MessageException("上级不能为空");
//        }
//        AgentBusInfo parentBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusParent());
//        if(parentBusInfo==null){
//            throw new MessageException("上级不能为空");
//        }
//        if(PlatformType.MPOS.getValue().equals(platformType)){
//            AgentResult agentResult = httpForMpos(orgId,parentBusInfo.getBusNum(),termType);
//            agentResult.setMsg(platformType);
//            return agentResult;
//        }else if(PlatformType.whetherPOS(platformType)){
//            AgentResult agentResult = httpForPos(orgId,parentBusInfo.getBusNum());
//            agentResult.setMsg(platformType);
//            return agentResult;
//        }
//        return AgentResult.fail();
//    }

    public static AgentResult httpForPos(String orgId,String supDorgId)throws Exception{
        try {
            String cooperator = com.ryx.credit.util.Constants.cooperator;
            String charset = "UTF-8"; // 字符集
            String tranCode = "ORG011"; // 交易码
            String reqMsgId = UUID.randomUUID().toString().replace("-", ""); // 请求流水
            String reqDate = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"); // 请求时间

            JSONObject jsonParams = new JSONObject();
            JSONObject data = new JSONObject();
            jsonParams.put("version", "1.0.0");
            jsonParams.put("msgType", "01");
            jsonParams.put("reqDate", reqDate);
            data.put("orgId",orgId);
            data.put("supDorgId",supDorgId);

            jsonParams.put("data", data);
            String plainXML = jsonParams.toString();
            // 请求报文加密开始
            String keyStr = AESUtil.getAESKey();
            byte[] plainBytes = plainXML.getBytes(charset);
            byte[] keyBytes = keyStr.getBytes(charset);
            String encryptData = new String(Base64.encodeBase64((AESUtil.encrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
            String signData = new String(Base64.encodeBase64(RSAUtil.digitalSign(plainBytes, Constants.privateKey, "SHA1WithRSA")), charset);
            String encrtptKey = new String(org.apache.commons.codec.binary.Base64.encodeBase64(RSAUtil.encrypt(keyBytes, Constants.publicKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
            // 请求报文加密结束

            Map<String, String> map = new HashMap<>();
            map.put("encryptData", encryptData);
            map.put("encryptKey", encrtptKey);
            map.put("cooperator", cooperator);
            map.put("signData", signData);
            map.put("tranCode", tranCode);
            map.put("reqMsgId", reqMsgId);

            log.info("POS机构统计信息查询请求参数:{}",map);
            String httpResult = HttpClientUtil.doPost(AppConfig.getProperty("agent_pos_notify_url"), map);
            JSONObject jsonObject = JSONObject.parseObject(httpResult);
            if (!jsonObject.containsKey("encryptData") || !jsonObject.containsKey("encryptKey")) {
                System.out.println("请求异常======" + httpResult);
                throw new Exception("http请求异常");
            } else {
                String resEncryptData = jsonObject.getString("encryptData");
                String resEncryptKey = jsonObject.getString("encryptKey");
                byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
                byte[] merchantAESKeyBytes = RSAUtil.decrypt(decodeBase64KeyBytes, Constants.privateKey, 2048, 11, "RSA/ECB/PKCS1Padding");
                byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
                byte[] merchantXmlDataBytes = AESUtil.decrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
                String respXML = new String(merchantXmlDataBytes, charset);
                log.info("POS机构统计信息查询返回参数：{}",respXML);

                // 报文验签
                String resSignData = jsonObject.getString("signData");
                byte[] signBytes = Base64.decodeBase64(resSignData);
                if (!RSAUtil.verifyDigitalSign(respXML.getBytes(charset), signBytes, Constants.publicKey, "SHA1WithRSA")) {
                    System.out.println("签名验证失败");
                } else {
                    System.out.println("签名验证成功");
                    Map<String, Object> respXMLMap = JsonUtil.jsonToMap(respXML);
                    String respType = String.valueOf(respXMLMap.get("respType"));
                    Map<String, Object> resultMap = new HashMap<>();
                    if(respType.equals("S")){
                        resultMap = JSONArray.parseObject(String.valueOf(respXMLMap.get("data")));
                    }else{
                        return AgentResult.failObj(String.valueOf(respXMLMap.get("respMsg")));
                    }
                    return AgentResult.ok(resultMap);
                }
                return new AgentResult(500,"http请求异常","");
            }
        } catch (Exception e) {
            log.info("通知失败:{}",e.getMessage());
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    private static AgentResult httpForMpos(String orgId,String parentAgencyId,String termType)throws Exception{
        try {
            Map<String, String> map = new HashMap<>();
            map.put("agencyId",orgId);
            map.put("parentAgencyId",parentAgencyId);
            map.put("type",termType);
            String toJson = JsonUtil.objectToJson(map);
            log.info("手刷机构统计信息查询请求参数:{}",toJson);
            String httpResult = HttpClientUtil.doPostJson(AppConfig.getProperty("pos_org_statistics_url"), toJson);
            log.info("手刷机构统计信息查询返回参数:{}",httpResult);
            Map<String, Object> stringObjectMap = JsonUtil.jsonToMap(httpResult);
            String data = String.valueOf(stringObjectMap.get("data"));
            List<Map> dataMap = JsonUtil.jsonToList(data, Map.class);
            return AgentResult.ok(dataMap);
        } catch (Exception e) {
            log.info("通知失败:{}",e.getMessage());
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    private AgentResult httpForRDBpos(String agencyId)throws Exception{
        try {
            Map<String, String> map = new HashMap<>();
            List<Map<String, Object>> list = businessPlatformService.queryByBusNum(agencyId);
            map.put("branchid",String.valueOf(list.get(0).get("BUS_PLATFORM")).split("_")[0]);
            map.put("agencyId",agencyId);
            String toJson = JsonUtil.objectToJson(map);
            log.info("瑞大宝查询终端下限数量请求参数:{}",toJson);
            String httpResult = HttpClientUtil.doPostJson(AppConfig.getProperty("rdb_req_url")+"agency/getTermCount", toJson);
            log.info("瑞大宝查询终端下限数量返回参数:{}",httpResult);
            JSONObject jsonObject = JSONObject.parseObject(httpResult);
            JSONArray result = jsonObject.getJSONArray("result");
            return AgentResult.ok(result);
        } catch (Exception e) {
            log.info("通知失败:{}",e.getMessage());
            e.printStackTrace();
            throw new Exception(e);
        }
    }


    private AgentResult httpForRHpos(String agencyId)throws Exception{
        try {
            Map<String, String> map = new HashMap<>();
            map.put("application","AgencyTerminalCount");
            map.put("agentId",agencyId);
            String json = JsonUtil.objectToJson(map);
            String reqParamEncrypt = Des3Util.Encrypt(json, rhb3desKey, rhb3desIv.getBytes());
            log.info("瑞花宝查询终端数量参数：{}",json);
            log.info("瑞花宝查询终端数量参数加密：{}",reqParamEncrypt);
            String httpResult = HttpClientUtil.sendHttpPost(rhbReqUrl, reqParamEncrypt);
            String reqParamDecrypt = Des3Util.Decrypt(httpResult, rhb3desKey, rhb3desIv.getBytes());
            log.info("瑞花宝查询终端数量返回参数：{}",reqParamDecrypt);
            JSONObject respXMLObj = JSONObject.parseObject(reqParamDecrypt);
            if (respXMLObj.getString("MSG_CODE").equals("0000")){
                JSONArray result = respXMLObj.getJSONArray("RESULT");
                return AgentResult.ok(result);
            }
            return AgentResult.fail();
        } catch (Exception e) {
            log.info("通知失败:{}",e.getMessage());
            e.printStackTrace();
            throw new Exception(e);
        }
    }



    public static AgentResult httpForRJPos(String orgId,String supDorgId)throws Exception{
        try {
            String cooperator = com.ryx.credit.util.Constants.cooperator;
            String charset = "UTF-8"; // 字符集
            String tranCode = "ORG011"; // 交易码
            String reqMsgId = UUID.randomUUID().toString().replace("-", ""); // 请求流水
            String reqDate = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"); // 请求时间

            JSONObject jsonParams = new JSONObject();
            JSONObject data = new JSONObject();
            jsonParams.put("version", "1.0.0");
            jsonParams.put("msgType", "01");
            jsonParams.put("reqDate", reqDate);
            data.put("orgId",orgId);
            data.put("supDorgId",supDorgId);

            jsonParams.put("data", data);
            String plainXML = jsonParams.toString();
            // 请求报文加密开始
            String keyStr = AESUtil.getAESKey();
            byte[] plainBytes = plainXML.getBytes(charset);
            byte[] keyBytes = keyStr.getBytes(charset);
            String encryptData = new String(Base64.encodeBase64((AESUtil.encrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
            String signData = new String(Base64.encodeBase64(RSAUtil.digitalSign(plainBytes, Constants.privateKey, "SHA1WithRSA")), charset);
            String encrtptKey = new String(org.apache.commons.codec.binary.Base64.encodeBase64(RSAUtil.encrypt(keyBytes, Constants.publicKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
            // 请求报文加密结束

            Map<String, String> map = new HashMap<>();
            map.put("encryptData", encryptData);
            map.put("encryptKey", encrtptKey);
            map.put("cooperator", cooperator);
            map.put("signData", signData);
            map.put("tranCode", tranCode);
            map.put("reqMsgId", reqMsgId);

            log.info("RJPOS机构统计信息查询请求参数:{}",map);
            String httpResult = HttpClientUtil.doPost(AppConfig.getProperty("agent_rjpos_notify_url"), map);
            JSONObject jsonObject = JSONObject.parseObject(httpResult);
            if (!jsonObject.containsKey("encryptData") || !jsonObject.containsKey("encryptKey")) {
                System.out.println("请求异常======" + httpResult);
                throw new Exception("http请求异常");
            } else {
                String resEncryptData = jsonObject.getString("encryptData");
                String resEncryptKey = jsonObject.getString("encryptKey");
                byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
                byte[] merchantAESKeyBytes = RSAUtil.decrypt(decodeBase64KeyBytes, Constants.privateKey, 2048, 11, "RSA/ECB/PKCS1Padding");
                byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
                byte[] merchantXmlDataBytes = AESUtil.decrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
                String respXML = new String(merchantXmlDataBytes, charset);
                log.info("RJPOS机构统计信息查询返回参数：{}",respXML);

                // 报文验签
                String resSignData = jsonObject.getString("signData");
                byte[] signBytes = Base64.decodeBase64(resSignData);
                if (!RSAUtil.verifyDigitalSign(respXML.getBytes(charset), signBytes, Constants.publicKey, "SHA1WithRSA")) {
                    System.out.println("签名验证失败");
                } else {
                    System.out.println("签名验证成功");
                    Map<String, Object> respXMLMap = JsonUtil.jsonToMap(respXML);
                    String respType = String.valueOf(respXMLMap.get("respType"));
                    Map<String, Object> resultMap = new HashMap<>();
                    if(respType.equals("S")){
                        resultMap = JSONArray.parseObject(String.valueOf(respXMLMap.get("data")));
                    }else{
                        return AgentResult.failObj(String.valueOf(respXMLMap.get("respMsg")));
                    }
                    return AgentResult.ok(resultMap);
                }
                return new AgentResult(500,"http请求异常","");
            }
        } catch (Exception e) {
            log.info("通知失败:{}",e.getMessage());
            e.printStackTrace();
            throw new Exception(e);
        }
    }


    @Override
    public AgentResult posOrgStatistics(String orgId,String termType)throws Exception{

        if(TerminalPlatformType.MPOS.getValue().compareTo(new BigDecimal(termType))==0){
            AgentResult agentResult = httpForMpos(orgId,"",termType);
            agentResult.setMsg(PlatformType.MPOS.getValue());
            return agentResult;
        }else if(TerminalPlatformType.POS.getValue().compareTo(new BigDecimal(termType))==0){
            AgentResult agentResult = httpForPos(orgId,orgId);
            agentResult.setMsg(PlatformType.POS.getValue());
            return agentResult;
        }else if(TerminalPlatformType.RDBPOS.getValue().compareTo(new BigDecimal(termType))==0){
            AgentResult agentResult = httpForRDBpos(orgId);
            agentResult.setMsg(PlatformType.RDBPOS.getValue());
            return agentResult;
        }
        return AgentResult.fail();
    }

    @Override
    public AgentResult posOrgStatistics(Map map) throws Exception {
        if (null==map|| map.size()==0){
            return AgentResult.fail();
        }
        String busPlatform =(String)map.get("busPlatform");
        String busId =(String)map.get("busParent");
        String orgId =(String)map.get("busNum");
        String termType =(String)map.get("termType");
        if (StringUtils.isBlank(busId) && StringUtils.isBlank(orgId)){
            return  AgentResult.fail();
        }
        PlatForm platForm = platFormMapper.selectByPlatFormNum(busPlatform);
        String platformType = platForm.getPlatformType();
        AgentBusInfo parentBusInfo = agentBusInfoMapper.selectByPrimaryKey(busId);
        String parentBusNum="";
        if(parentBusInfo!=null){
            parentBusNum=parentBusInfo.getBusNum();
        }
        if(PlatformType.MPOS.getValue().equals(platformType)){
            AgentResult agentResult = httpForMpos(orgId,parentBusNum,termType);
            agentResult.setMsg(platformType);
            return agentResult;
        }else if(PlatformType.whetherPOS(platformType) || PlatformType.SSPOS.getValue().equals(platformType)){
            if(StringUtils.isEmpty(orgId)){
                if(StringUtils.isNotEmpty(parentBusNum)){
                    orgId = parentBusNum;
                }
            }
            AgentResult agentResult = httpForPos(orgId,parentBusNum);
            agentResult.setMsg(platformType);
            return agentResult;
        }else if(PlatformType.RDBPOS.getValue().equals(platformType)){
            if(StringUtils.isEmpty(orgId)){
                if(StringUtils.isNotEmpty(parentBusNum)){
                    orgId = parentBusNum;
                }
            }
            AgentResult agentResult = httpForRDBpos(orgId);
            agentResult.setMsg(platformType);
            return agentResult;
        }else if(PlatformType.RJPOS.getValue().equals(platformType)){
            if(StringUtils.isEmpty(orgId)){
                if(StringUtils.isNotEmpty(parentBusNum)){
                    orgId = parentBusNum;
                }
            }
            AgentResult agentResult = httpForRJPos(orgId,parentBusNum);
            agentResult.setMsg(platformType);
            return agentResult;
        }else if(PlatformType.RHPOS.getValue().equals(platformType)){
            if(StringUtils.isEmpty(orgId)){
                if(StringUtils.isNotEmpty(parentBusNum)){
                    orgId = parentBusNum;
                }
            }
            AgentResult agentResult = httpForRHpos(orgId);
            agentResult.setMsg(platformType);
            return agentResult;
        }
        return AgentResult.fail();
    }

    @Override
    public Long posOrgStatisticsCount(Map map) {
        //业务bus_num,查询订单id,查询采购订单,pro_num
        return oSubOrderMapper.selectProNumByBusNum((String)map.get("busNum"));
    }

}
