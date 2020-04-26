package com.ryx.credit.machine.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.*;
import com.ryx.credit.pojo.admin.order.ORefundPriceDiffDetail;
import com.ryx.credit.pojo.admin.order.TerminalTransferDetail;
import org.apache.commons.collections.FastHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：cx
 * 时间：2018/10/10
 * 描述：手刷极具相关接口
 */
@Service("mposTermMachineServiceImpl")
public class MposTermMachineServiceImpl implements TermMachineService {

    public static final String MPOS_SUCESS_respCode = "000000";//000000-成功，000002-系统报错，000003-缺失参数，000004-其他, 100000-失败
    public static final String MPOS_SUCESS_respType = "S";//S-成功，E-报错，R-重复请求
    private Logger logger = LoggerFactory.getLogger(MposTermMachineServiceImpl.class);

    @Override
    public List<TermMachineVo> queryTermMachine(PlatformType platformType,Map<String,String> par) throws Exception{
        JSONObject res = request(FastMap.fastSuccessMap(), AppConfig.getProperty("mpos.queryTermActive"));
        if(null!=res && MPOS_SUCESS_respCode.equals(res.getString("respCode")) && MPOS_SUCESS_respType.equals(res.getString("respType"))){
            JSONArray data =  res.getJSONArray("data");
            List<TermMachineVo> resData = new ArrayList<TermMachineVo>();
            for (int i = 0; i < data.size(); i++) {

                JSONObject item =  data.getJSONObject(i);
                String name = item.getString("termActiveName");
                String id = item.getString("termActiveId");

                TermMachineVo machineVo =  new TermMachineVo();
                machineVo.setId(id);
                machineVo.setMechineName(name);
                resData.add(machineVo);

            }
            return resData;
        }else{
            AppConfig.sendEmails(res.getString("respMsg"), "机具划拨通知手刷失败报警");
            throw new MessageException(res.getString("respMsg"));
        }

    }

    @Override
    public List<MposTermBatchVo> queryMposTermBatch(PlatformType platformType) throws Exception{
        JSONObject res = request(FastMap.fastSuccessMap(), AppConfig.getProperty("mpos.queryTermBatch"));
        if(null!=res && MPOS_SUCESS_respCode.equals(res.getString("respCode")) && MPOS_SUCESS_respType.equals(res.getString("respType"))){
            JSONArray data =  res.getJSONArray("data");
            List<MposTermBatchVo> resData = new ArrayList<MposTermBatchVo>();
            for (int i = 0; i < data.size(); i++) {

                JSONObject item =  data.getJSONObject(i);
                String batchName = item.getString("batchName");
                String batchId = item.getString("batchId");
                String source = item.getString("source");

                MposTermBatchVo batchVo =  new MposTermBatchVo();
                batchVo.setBatchId(batchId);
                batchVo.setBatchName(batchName+"-"+source);
                resData.add(batchVo);

            }
            return resData;
        }else{
            AppConfig.sendEmails(res.getString("respMsg"), "机具划拨通知手刷失败报警");
            throw new MessageException(res.getString("respMsg"));
        }
    }

    @Override
    public List<MposTermTypeVo> queryMposTermType(PlatformType platformType) throws Exception{
        JSONObject res = request(FastMap.fastSuccessMap(), AppConfig.getProperty("mpos.queryTermType"));
        if(null!=res && MPOS_SUCESS_respCode.equals(res.getString("respCode")) && MPOS_SUCESS_respType.equals(res.getString("respType"))){
            JSONArray data =  res.getJSONArray("data");
            List<MposTermTypeVo> resData = new ArrayList<MposTermTypeVo>();
            for (int i = 0; i < data.size(); i++) {

                JSONObject item =  data.getJSONObject(i);
                String name = item.getString("termTypeName");
                String id = item.getString("termTypeId");

                MposTermTypeVo termTypeVo =  new MposTermTypeVo();
                termTypeVo.setTermTypeId(id);
                termTypeVo.setTermTypeName(name);
                resData.add(termTypeVo);

            }
            return resData;
        }else{
            AppConfig.sendEmails(res.getString("respMsg"), "机具划拨通知手刷失败报警");
            throw new MessageException(res.getString("respMsg"));
        }
    }


    /**
     * 机具的下发
     * @param lowerHairMachineVo
     * @return
     */
    @Override
    public AgentResult lowerHairMachine(LowerHairMachineVo lowerHairMachineVo) throws Exception{
        logger.info("Mpos机具的下发:");
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(lowerHairMachineVo));
        JSONObject res = request(data, AppConfig.getProperty("mpos.lowerHairMachine"));
        if(null!=res && MPOS_SUCESS_respCode.equals(res.getString("respCode")) && MPOS_SUCESS_respType.equals(res.getString("respType"))){
            logger.info("Mpos机具的调整，下发:{}{}{}",AppConfig.getProperty("mpos.lowerHairMachine"),res.toJSONString(),res.getString("respMsg"));
            return AgentResult.ok();
        }else{
            AppConfig.sendEmails(res.getString("respMsg"), "机具划拨通知手刷失败报警");
            throw new MessageException(res.getString("respMsg"));
        }
    }

    /**
     * 机具的调整，，退货是使用
     * @param adjustmentMachineVo
     * @return
     */
    @Override
    public AgentResult adjustmentMachine(AdjustmentMachineVo adjustmentMachineVo) throws Exception{
        logger.info("Mpos机具的调整，退货是使用");
        adjustmentMachineVo.setPlatformNum(adjustmentMachineVo.getPlatformNum());
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(adjustmentMachineVo));
        JSONObject res = request(data, AppConfig.getProperty("mpos.adjustmentMachine"));
        if(null!=res && MPOS_SUCESS_respCode.equals(res.getString("respCode")) && MPOS_SUCESS_respType.equals(res.getString("respType"))){
            logger.info("Mpos机具的调整，退货是使用:{}{}{}",AppConfig.getProperty("mpos.adjustmentMachine"),res.toJSONString(),res.getString("respMsg"));
            return AgentResult.ok();
        }else{
            AppConfig.sendEmails(res.getString("respMsg"), "机具划拨通知手刷失败报警");
            throw new MessageException(res.getString("respMsg"));
        }
    }

    /**
     * 机具活动的变更
     * @param changeActMachineVo
     * @return
     */
    @Override
    public AgentResult changeActMachine(ChangeActMachineVo changeActMachineVo) throws Exception{
        changeActMachineVo.setLogisticsDetailList(null);
        logger.info("Mpos机具的调整，，机具活动的变更");
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(changeActMachineVo));
        JSONObject res = request(data, AppConfig.getProperty("mpos.changeActMachine"));
        if(null!=res && MPOS_SUCESS_respCode.equals(res.getString("respCode")) && MPOS_SUCESS_respType.equals(res.getString("respType"))){
            logger.info("Mpos机具的调整，机具活动的变更:{}{}{}",AppConfig.getProperty("mpos.changeActMachine"),res.getString("respMsg"),res.toJSONString());
            return AgentResult.ok();
        }else{
            AppConfig.sendEmails(res.getString("respMsg"), "机具划拨通知手刷失败报警");
            throw new MessageException(res.getString("respMsg"));
        }
    }

    @Override
    public JSONObject request(Map data, String url) throws Exception {
        try {
            String json = JsonUtil.objectToJson(data);
            logger.info("通知手刷请求参数：{},{}",url,json);
            String httpResult = HttpClientUtil.doPostJsonWithException(url, json);
            logger.info("通知手刷返回参数：{},{}",url,httpResult);
            if(StringUtils.isNotBlank(httpResult)) {
                JSONObject respXMLObj = JSONObject.parseObject(httpResult);
                return respXMLObj;
            }else{
                AppConfig.sendEmails("错误信息："+httpResult, "机具划拨通知手刷失败报警");
                throw new Exception("请求出错");
            }
        } catch (Exception e) {
            logger.error("首刷发送请求失败：",e);
            e.printStackTrace();
            AppConfig.sendEmails("首刷发送请求失败："+ MailUtil.printStackTrace(e), "机具划拨通知手刷失败报警");
            throw e;
        }

    }


    @Override
    public AgentResult querySnMsg(PlatformType platformType,String snBegin,String snEnd)throws Exception{

        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("beginTermNum",snBegin);
        resultMap.put("endTermNum",snEnd);

        logger.info("老订单Mpos请求参数：{}",resultMap.toString());
        JSONObject res = request(resultMap, AppConfig.getProperty("mpos.oldChangeActMachine"));
        if(null!=res && MPOS_SUCESS_respCode.equals(res.getString("respCode")) && MPOS_SUCESS_respType.equals(res.getString("respType"))){
            logger.info("老订单Mpos机具查询:{}{}{}",AppConfig.getProperty("mpos.oldChangeActMachine"),res.getString("respMsg"),res.toJSONString());
            List<Map<String,Object>> termMachineListMap = (List<Map<String,Object>>) JSONArray.parse(String.valueOf(res.get("data")));
            return AgentResult.ok(termMachineListMap);
        }else{
            throw new MessageException(res.getString("respMsg"));
        }
    }

    @Override
    public AgentResult queryTerminalTransfer(List<TerminalTransferDetail> terminalTransferDetailList, String operation,String taskId) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type",operation);
        List<Map<String,String>> mapList = new ArrayList<>();
        for (TerminalTransferDetail terminalTransferDetail:terminalTransferDetailList) {
            Map map = new HashMap();
            map.put("busPlatform",terminalTransferDetail.getPlatFrom());
            map.put("startTerm",terminalTransferDetail.getSnBeginNum());
            map.put("endTerm",terminalTransferDetail.getSnEndNum());
            map.put("oldAgencyId",terminalTransferDetail.getOriginalOrgId());
            map.put("newAgencyId",terminalTransferDetail.getGoalOrgId());
            map.put("num", String.valueOf(terminalTransferDetail.getComSnNum().intValue()));
            map.put("limitNum", String.valueOf(terminalTransferDetail.getSnCount().intValue()));
            map.put("batchId",terminalTransferDetail.getId());
            mapList.add(map);
        }
        jsonObject.put("info",mapList);
        logger.info("机具终端划拨通知查询类型:{}",operation);
        logger.info("机具终端划拨通知查询参数:{}",mapList);
        JSONObject res=null;
        try{
           res = request(jsonObject, AppConfig.getProperty("mpos.termMachine"));
        }catch (Exception e){
            logger.info("机具终端划拨通知查询类型调用远程接口:{}失败",AppConfig.getProperty("mpos.termMachine"));
            return AgentResult.fail("机具终端划拨通知查询类型调用远程接口失败");
        }

        if(null!=res && MPOS_SUCESS_respCode.equals(res.getString("respCode")) && MPOS_SUCESS_respType.equals(res.getString("respType"))){
            logger.info("机具终端划拨通知查询成功:{}{}{}",AppConfig.getProperty("mpos.termMachine"),res.getString("respMsg"),res.toJSONString());
            List<Map<String,Object>> codeType =(List<Map<String,Object>>) JSONArray.parse(String.valueOf(res.get("data")));
            return AgentResult.ok(codeType);
        }else{
            logger.info("机具终端划拨通知查询失败:{}{}{}",AppConfig.getProperty("mpos.termMachine"),res.getString("respMsg"),res.toJSONString());
            return AgentResult.fail(res.getString("respMsg"));
        }
    }

    @Override
    public AgentResult queryTerminalTransferResult(String serialNumber,String type) throws Exception {
        JSONObject data = new JSONObject();
        data.put("batchId", serialNumber);
        JSONObject res=null;
        try{
            res = request(data, AppConfig.getProperty("mpos.termMachineResult"));
        }catch (Exception e){
            logger.info("机具终端划拨查询结果调用远程接口:{}失败",AppConfig.getProperty("mpos.termMachineResult"));
            return AgentResult.fail("机具终端划拨查询结果调用远程接口失败");
        }
        if(null!=res && MPOS_SUCESS_respCode.equals(res.getString("respCode")) && MPOS_SUCESS_respType.equals(res.getString("respType"))){
            logger.info("机具终端划拨结果查询成功:{}{}{}",AppConfig.getProperty("mpos.termMachineResult"),res.getString("respMsg"),res.toJSONString());
            JSONObject codeType = JSONObject.parseObject(String.valueOf(res.get("data")));
            return AgentResult.ok(codeType);
        }else{
            logger.info("机具终端划拨结果查询失败:{}{}{}",AppConfig.getProperty("mpos.termMachineResult"),res.getString("respMsg"),res.toJSONString());
            return AgentResult.fail(res.getString("respMsg"));
        }
    }

    @Override
    public AgentResult synOrVerifyCompensate(List<ORefundPriceDiffDetail> refundPriceDiffDetailList, String operation) throws Exception {
        return  AgentResult.ok("未联动");
    }

    @Override
    public AgentResult queryCompensateResult(String serialNumber,String platformType) throws Exception {
        return AgentResult.ok("04");
    }

    @Override
    public boolean checkModleIsEq(Map<String, String> data, String platformType) {
        return true;
    }

    @Override
    public AgentResult checkOrderReturnSN(List<Map<String, Object>> list, String platformType) throws Exception {
        return AgentResult.ok();
    }

    @Override
    public AgentResult unfreezeOrderReturnSN(List<Map<String, Object>> list, String platformType) throws Exception {
        return AgentResult.ok();
    }

    @Override
    public AgentResult queryLogisticsResult(Map<String, Object> pamMap, String platformType) throws Exception {
        return null;
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
        return null;
    }

    @Override
    public AgentResult terminalTransAgain(String taskId, String serialNumber, String type) throws Exception {
        return null;
    }
}
