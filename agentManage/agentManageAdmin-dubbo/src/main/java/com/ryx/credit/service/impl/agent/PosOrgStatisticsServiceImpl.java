package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.common.util.agentUtil.AESUtil;
import com.ryx.credit.common.util.agentUtil.RSAUtil;
import com.ryx.credit.dao.agent.PlatFormMapper;
import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.service.agent.PosOrgStatisticsService;
import com.ryx.credit.util.Constants;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by RYX on 2018/10/9.
 */
@Service("posOrgStatisticsService")
public class PosOrgStatisticsServiceImpl implements PosOrgStatisticsService {

    private static Logger log = LoggerFactory.getLogger(PosOrgStatisticsServiceImpl.class);
    @Autowired
    private PlatFormMapper platFormMapper;

    public AgentResult posOrgStatistics(String busPlatform,String orgId)throws Exception{
        PlatForm platForm = platFormMapper.selectByPlatFormNum(busPlatform);
        String platformType = platForm.getPlatformType();
        if(PlatformType.MPOS.getValue().equals(platformType)){
            AgentResult agentResult = httpForMpos(orgId);
            agentResult.setMsg(platformType);
            return agentResult;
        }else if(PlatformType.POS.getValue().equals(platformType) || PlatformType.ZPOS.getValue().equals(platformType)){
            AgentResult agentResult = httpForPos(orgId);
            agentResult.setMsg(platformType);
            return agentResult;
        }
        return AgentResult.fail();
    }

    private AgentResult httpForPos(String orgId)throws Exception{
        try {
            String cooperator = com.ryx.credit.util.Constants.cooperator;
            String charset = "UTF-8"; // 字符集
            String tranCode = "ORG005"; // 交易码
            String reqMsgId = UUID.randomUUID().toString().replace("-", ""); // 请求流水
            String reqDate = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"); // 请求时间

            JSONObject jsonParams = new JSONObject();
            JSONObject data = new JSONObject();
            jsonParams.put("version", "1.0.0");
            jsonParams.put("msgType", "01");
            jsonParams.put("reqDate", reqDate);
            data.put("orgId",orgId);

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

            log.info("机构统计信息查询请求参数:{}",map);
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
                log.info("机构统计信息查询返回参数：{}",respXML);

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
                        resultMap = JsonUtil.jsonToMap(String.valueOf(respXMLMap.get("data")));
                    }
                    return AgentResult.ok(resultMap);
                }
                return new AgentResult(500,"http请求异常","");
            }
        } catch (Exception e) {
            log.info("http请求超时:{}",e.getMessage());
            throw e;
        }
    }

    private AgentResult httpForMpos(String orgId)throws Exception{
        try {
            Map<String, String> map = new HashMap<>();
            map.put("agencyId",orgId);
            String toJson = JsonUtil.objectToJson(map);
            String httpResult = HttpClientUtil.doPostJson(AppConfig.getProperty("pos_org_statistics_url"), toJson);
            Map<String, Object> stringObjectMap = JsonUtil.jsonToMap(httpResult);
            String data = String.valueOf(stringObjectMap.get("data"));
            List<Map> dataMap = JsonUtil.jsonToList(data, Map.class);
            return AgentResult.ok(dataMap);
        } catch (Exception e) {
            log.info("http请求超时:{}",e.getMessage());
            throw e;
        }
    }

}