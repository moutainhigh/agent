package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.OrgType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.PlatFormMapper;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.pojo.admin.agent.PlatFormExample;
import com.ryx.credit.pojo.admin.vo.AgentNotifyVo;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.PlatformSynService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by RYX on 2018/8/11.
 */
@Service("platformSynServiceMpos")
public class PlatformSynServiceMpos implements PlatformSynService {

    private Logger log = LoggerFactory.getLogger(PlatformSynServiceMpos.class);
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private PlatFormMapper platFormMapper;

    @Override
    public Boolean isMyPlatform(String id) {
        AgentBusInfo agentBusInfo = agentBusinfoService.getById(id);
        PlatFormExample example = new PlatFormExample();
        example.or().andPlatformNumEqualTo(agentBusInfo.getBusPlatform()).andStatusEqualTo(Status.STATUS_1.status);
        List<PlatForm> list = platFormMapper.selectByExample(example);
        if(list.size()>0){
            PlatForm p = list.get(0);
            if("MPOS".equals(p.getPlatformType())){
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean isMyPlatformByPlatformCode(String platformCode) {
        PlatFormExample example = new PlatFormExample();
        example.or().andPlatformNumEqualTo(platformCode).andStatusEqualTo(Status.STATUS_1.status);
        List<PlatForm> list = platFormMapper.selectByExample(example);
        if(list.size()>0){
            PlatForm p = list.get(0);
            if("MPOS".equals(p.getPlatformType())){
                return true;
            }
        }
        return false;
    }

    @Override
    public JSONObject request(Map data,String url) throws Exception {
        String json = JsonUtil.objectToJson(data);
        log.info("升级通知手刷请求参数：{}",json);
        String httpResult = HttpClientUtil.doPostJson(url, json);
        log.info("升级通知手刷返回参数：{}",httpResult);
        JSONObject respXMLObj = JSONObject.parseObject(httpResult);
        return respXMLObj;
    }



    @Override
    public Map agencyLevelUpdateChangeData(Map data) {
        //待请求参数
        FastMap fastMap = FastMap.fastMap("agencyLevelUpdateChangeData","agencyLevelUpdateChangeData");
        try {
            Object agentBusinfo = data.get("agentBusinfoId");
            AgentBusInfo agentBusInfo = agentBusinfoService.getById(agentBusinfo+"");
            if(StringUtils.isNotBlank(agentBusInfo.getBusParent())) {
                AgentBusInfo parent = agentBusinfoService.getById(agentBusInfo.getBusParent());
                fastMap.putKeyV("newparentAgencyId",parent.getBusNum());
            }
            //查询代理商的上级代理
            fastMap.putKeyV("agencyId",agentBusInfo.getBusNum());
            fastMap.putKeyV("type","0");
            fastMap.putKeyV("createName",data.get("processingId"));
        } catch (Exception e) {
            e.printStackTrace();
            fastMap.putKeyV("msg",e.getLocalizedMessage());
        }
        return fastMap;
    }

    /**
     * agencyId 代理商编号
     * oldparentAgencyId 原上级代理商
     * newparentAgencyId newparentAgencyId
     * type 0:直签 1:变更关系
     * createName 操作人
     * @param data
     * @return
     * @throws Exception
     */
    @Override
    public AgentResult agencyLevelUpdateChange(Map data) throws Exception{
        log.info("通知手刷请求参数 代理商升级：{}",data);
        try {

            Map<String,Object> jsonParams = new HashMap<>();
            jsonParams.put("agencyId",data.get("agencyId"));

            if(null!=data.get("oldparentAgencyId"))
            jsonParams.put("oldparentAgencyId",data.get("oldparentAgencyId")); //使用范围
            jsonParams.put("newparentAgencyId",data.get("newparentAgencyId"));
            jsonParams.put("type",data.get("type"));
            jsonParams.put("createName",data.get("createName"));

            JSONObject respXMLObj = request(jsonParams,AppConfig.getProperty("agencyChange_mpos_notify_url"));

            String respCode = respXMLObj.getString("respCode");
            String respMsg = respXMLObj.getString("respMsg");

            if(StringUtils.isNotBlank(respCode) && respCode.equals("000000")){
                JSONObject repdata =  respXMLObj.getJSONObject("data");
                return AgentResult.ok(respXMLObj);
            }else{
                AppConfig.sendEmails(respXMLObj.toJSONString(), "升级通知手刷失败报警");
                AgentResult ag = AgentResult.fail(respMsg);
                ag.setData(respXMLObj);
                return ag;
            }
        } catch (Exception e) {
            log.info("通知失败:{}",e.getMessage());
            AppConfig.sendEmails("通知失败:"+ MailUtil.printStackTrace(e), "升级通知手刷失败报警");
            throw e;
        }
    }


    @Override
    public AgentResult httpRequestNetIn(AgentNotifyVo agentNotifyVo)throws Exception{
        try {
            Map<String,Object> jsonParams = new HashMap<>();
            jsonParams.put("uniqueId",agentNotifyVo.getUniqueId());
            if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(agentNotifyVo.getOrgId()))
                jsonParams.put("orgId",agentNotifyVo.getOrgId());
            jsonParams.put("useOrgan",agentNotifyVo.getUseOrgan()); //使用范围
            jsonParams.put("orgName",agentNotifyVo.getOrgName());
            jsonParams.put("busPlatform",agentNotifyVo.getBusPlatform());
            jsonParams.put("agHeadMobile",agentNotifyVo.getAgHeadMobile());
            jsonParams.put("baseMessage",agentNotifyVo.getBaseMessage());
            jsonParams.put("busMessage",agentNotifyVo.getBusMessage());
            jsonParams.put("colinfoMessage",agentNotifyVo.getColinfoMessage());
            if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(agentNotifyVo.getProvince()))
                jsonParams.put("province",agentNotifyVo.getProvince());
            if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(agentNotifyVo.getCity()))
                jsonParams.put("city",agentNotifyVo.getCity());
            if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(agentNotifyVo.getCity()))
                jsonParams.put("cityArea",agentNotifyVo.getCity());
            jsonParams.put("orgType",agentNotifyVo.getOrgType());
            if(agentNotifyVo.getOrgType().equals(OrgType.STR.getValue()))
                jsonParams.put("supDorgId",agentNotifyVo.getSupDorgId());

            String json = JsonUtil.objectToJson(jsonParams);
            log.info("通知手刷请求参数：{}",json);

            //发送请求
            String httpResult = HttpClientUtil.doPostJson(AppConfig.getProperty("agent_mpos_notify_url"), json);

            log.info("通知手刷返回参数：{}",httpResult);
            if (httpResult.contains("data") && httpResult.contains("orgId")){
                JSONObject respXMLObj = JSONObject.parseObject(httpResult);
                JSONObject dataObj = JSONObject.parseObject(respXMLObj.get("data").toString());
                log.info(dataObj.toJSONString());
                if(com.ryx.credit.commons.utils.StringUtils.isBlank(respXMLObj.get("data").toString())){
                    AppConfig.sendEmails(httpResult, "入网通知手刷失败报警");
                }
                return AgentResult.ok(dataObj);
            }else{
                AppConfig.sendEmails(httpResult, "入网通知手刷失败报警");
                log.info("http请求超时返回错误:{}",httpResult);
                throw new Exception("http返回有误");
            }
        } catch (Exception e) {
            AppConfig.sendEmails("通知手刷请求超时："+ MailUtil.printStackTrace(e), "入网通知手刷失败报警");
            log.info("通知失败:{}",e.getMessage());
            throw new Exception("通知失败");
        }
    }
}
