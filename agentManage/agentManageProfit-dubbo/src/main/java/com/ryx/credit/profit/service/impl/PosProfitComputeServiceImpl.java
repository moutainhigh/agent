package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.profit.enums.DeductionType;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.*;
import com.ryx.credit.service.agent.AgentBusinfoService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author yangmx
 * @desc POS奖励分润计算
 */
@Service("posProfitComputeServiceImpl")
public class PosProfitComputeServiceImpl implements DeductService {
    private static Logger LOG = LoggerFactory.getLogger(PosProfitComputeServiceImpl.class);
    @Autowired
    private PosRewardTemplateService posRewardTemplateService;
    @Autowired
    private IPosRewardService iPosRewardService;
    @Autowired
    private ProfitDetailMonthService profitDetailMonthServiceImpl;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    protected RedisTemplate<String, String> redisTemplate;
    @Autowired
    private ProfitMonthService profitMonthService;
    @Autowired
    private ProfitDeducttionDetailService profitDeducttionDetailService;

    /**
     *  2:机构 3:机构一代 6:标准一代
     */
    private static final String AGENT_TYPE_2 = "2";
    private static final String AGENT_TYPE_3 = "3";
    private static final String AGENT_TYPE_6 = "6";
    private static final String PLATFORM_CODE = "100003";

    /**
     * 上级代理商编号、代理商编号、类型（机构、机构一代、标准一代）、交易总额、贷记交易总额
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> execut(Map<String, Object> map) throws Exception {
        LOG.info("POS奖励计算，请求参数：{}", map);
        Map<String, Object> posMap = new HashMap<String, Object>();
        posMap.putAll(map);
        posMap.put("posRewardAmt", BigDecimal.ZERO);
        posMap.put("posAssDeductAmt", BigDecimal.ZERO);

        String deductDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.ISO_LOCAL_DATE).substring(0,7);
        if(Objects.equals(map.get("agentType"), AGENT_TYPE_2) || Objects.equals(map.get("agentType"), AGENT_TYPE_6)){
            LOG.info("POS奖励，代理商ID：{}，POS交易金额：{}，POS贷记交易金额：{}",
                    map.get("agentId"), map.get("posTranAmt"), map.get("posJlTranAmt"));
            PosReward posReward = new PosReward();
            posReward.setAgentId(map.get("agentId").toString());
            posReward.setApplyStatus("1");
            List<PosReward> posRewardList = iPosRewardService.selectByMonth(posReward);
            if(posRewardList == null || posRewardList.isEmpty()){
                this.obtainRewardTemp(deductDate, posMap);
            } else {
                this.specialRewardTemp(deductDate, posRewardList, posMap);
            }

            posRewardList.forEach(posReward1 -> {
                /** 到达考核月份，执行季度考核*/
                if(Objects.equals(posReward1.getTotalEndMonth(), deductDate)){
                    quarterAssessment(posReward1, posMap);
                }
            });
        } else if(Objects.equals(map.get("agentType"), AGENT_TYPE_3)){
            this.obtainParentAgentId(posMap);
            this.obtainRewardTemp(deductDate, posMap);
            if(new BigDecimal(posMap.get("posRewardAmt").toString()).compareTo(BigDecimal.ZERO) > 0
                    && posMap.get("parentAgentId") != null){
                LOG.info("POS奖励，代理商ID：{}，准备进行扣减上级代理商分润。",map.get("agentId"));
                if(Objects.equals(posMap.get("computType"), "1")){
                    this.deductParentAgentPosRewardAmt(deductDate, posMap);
                }
            }
        }
        map.put("posRewardAmt", posMap.get("posRewardAmt"));
        map.put("posAssDeductAmt", posMap.get("posAssDeductAmt"));
        LOG.info("POS奖励计算，响应参数：{}", map);
        return map;
    }

    /**
     * 获取代理商特殊POS奖励模板
     * @param deductDate
     * @param posRewardList
     * @param map
     */
    private void specialRewardTemp(String deductDate, List<PosReward> posRewardList, Map<String, Object> map) {
        posRewardList.forEach(posReward1 -> {
            if(posReward1.getTotalConsMonth().contains("~")){
                String[] spl = posReward1.getTotalConsMonth().trim().split("~");
                for (int i = 0; i < spl.length; i++) {
                    if(Objects.equals(spl[i], deductDate)){
                        LOG.info("代理商ID：{}，代理商特殊奖励活动生效月份：{}", map.get("agentId"), deductDate);
                        this.specialTranOrCreditAmt(posReward1, map, deductDate);
                    }
                }
            } else {
                if(Objects.equals(posReward1.getTotalConsMonth(), deductDate)){
                    this.specialTranOrCreditAmt(posReward1, map, deductDate);
                }
            }
        });
    }

    /**
     * 本月上送贷记交易金额+本月下级贷记交易金额 - 上月贷记对比月贷记交易金额+上月下级贷记对比月贷记交易金额 =POS奖励贷记交易金额 * 特殊奖励金额
     * @param posReward1 特殊奖励模板
     * @param map
     * @param deductDate
     */
    private void specialTranOrCreditAmt(PosReward posReward1, Map<String, Object> map, String deductDate) {
        LOG.info("代理商ID：{}，执行特殊POS奖励模板：{}", map.get("agentId"), JSONObject.toJSON(posReward1));
        String agentType = null;
        if(Objects.equals(AGENT_TYPE_2, map.get("agentType"))){
            agentType = AGENT_TYPE_3;
        } else {
            agentType = map.get("agentType").toString();
        }

        LOG.info("查询贷记对比月贷记交易金额，代理商ID：{}，贷记对比月：{}", map.get("agentId"), posReward1.getCreditConsMonth());
        TransProfitDetail transProfitDetail = profitDetailMonthServiceImpl.getTransProfitDetail(map.get("agentId").toString(),
                posReward1.getCreditConsMonth().replace("-", ""), map.get("agentType").toString());
        if(transProfitDetail == null){
            LOG.info("代理商ID：{}，未查询贷记对比月交易明细，暂不计算POS奖励", map.get("agentId"));
            return;
        }
        BigDecimal oldPosCreditAmt = transProfitDetail.getPosRewardAmt() == null ? BigDecimal.ZERO : transProfitDetail.getPosRewardAmt();
        BigDecimal newPosCreditAmt = new BigDecimal(map.get("posJlTranAmt").toString());
        LOG.info("代理商ID：{}，贷记对比月贷记交易金额：{}", map.get("agentId"), oldPosCreditAmt);

        List<String> agentIdList = this.queryChildLevelByBusNum(map);
        if(agentIdList.size() > 0){
            BigDecimal oldChildPosCreditAmt= BigDecimal.ZERO;
            List<TransProfitDetail> list = profitDetailMonthServiceImpl.getChildTransProfitDetailList(agentIdList, posReward1.getCreditConsMonth().replace("-", ""), agentType);
            if(list != null && !list.isEmpty()){
                for(TransProfitDetail detail1 : list){
                    oldChildPosCreditAmt = oldChildPosCreditAmt.add(detail1.getPosRewardAmt());
                }
            }
            LOG.info("代理商ID：{}，下级贷记对比月贷记交易总金额：{}", map.get("agentId"), oldChildPosCreditAmt);
            oldPosCreditAmt = oldPosCreditAmt.add(oldChildPosCreditAmt);
            LOG.info("代理商ID：{}，贷记对比月贷记交易汇总金额：{}", map.get("agentId"),  oldPosCreditAmt);
            LOG.info("查询当前执行月下级贷记交易金额，代理商ID：{}，当前执行月：{}", map.get("agentId"), deductDate);
            List<TransProfitDetail> newlist = profitDetailMonthServiceImpl.getChildTransProfitDetailList(agentIdList, deductDate.replace("-",""), agentType);
            BigDecimal newChildPosCreditAmt= BigDecimal.ZERO;
            if(newlist != null && !newlist.isEmpty()){
                for(TransProfitDetail detail1 : newlist){
                    newChildPosCreditAmt = newChildPosCreditAmt.add(detail1.getPosRewardAmt());
                }
            }
            LOG.info("代理商ID：{}，当前执行月下级贷记交易总金额：{}", map.get("agentId"), newChildPosCreditAmt);
            newPosCreditAmt = newPosCreditAmt.add(newChildPosCreditAmt);
        }
        LOG.info("代理商ID：{}，当前执行月贷记交易汇总金额：{}", map.get("agentId"),  newPosCreditAmt);
        this.computeSpecialPosReward(oldPosCreditAmt, newPosCreditAmt, posReward1.getRewardScale(), map);
    }

    /**
     * 特殊POS奖励计算(四舍五入)
     * @param oldPosCreditAmt
     * @param newPosCreditAmt
     * @param rewardScale
     * @param map
     */
    private void computeSpecialPosReward(BigDecimal oldPosCreditAmt, BigDecimal newPosCreditAmt, BigDecimal rewardScale, Map<String, Object> map) {
        if(newPosCreditAmt.compareTo(oldPosCreditAmt) > 0){
            BigDecimal posCreditAmt = newPosCreditAmt.subtract(oldPosCreditAmt);
            LOG.info("代理商ID：{}，计算后贷记交易额：{}，特殊奖励比例：{}", map.get("agentId"), posCreditAmt, rewardScale);
            BigDecimal posRewardAmt = posCreditAmt.multiply(rewardScale).setScale(2,BigDecimal.ROUND_HALF_UP);
            LOG.info("代理商ID：{}，POS奖励金额：{}", map.get("agentId"), posRewardAmt);
            map.put("posRewardAmt", posRewardAmt);
        } else {
            LOG.info("代理商ID：{}，本月贷记交易+本月下级商户贷记交易金额 < 贷记对比月贷记交易金额+下级商户贷记交易金额，没有POS奖励", map.get("agentId"));
        }
    }

    /**
     * 获取POS通用奖励模板
     * @param deductDate
     * @param map
     */
    private void obtainRewardTemp(String deductDate, Map<String, Object> map) {
        List<PosRewardTemplate> posRewardTemplates =  this.getPosRewardTemplateList();
        if(posRewardTemplates == null || posRewardTemplates.isEmpty()){
            LOG.info("业务部门未配置通用奖励模板");
            return;
        }
        String agentType = null;
        if(Objects.equals(AGENT_TYPE_2, map.get("agentType"))){
            agentType = AGENT_TYPE_3;
        } else {
            agentType = map.get("agentType").toString();
        }
        for (PosRewardTemplate posRewardTemplate: posRewardTemplates){
            if(posRewardTemplate.getActivityValid().contains("~")) {
                String[] spl = posRewardTemplate.getActivityValid().trim().split("~");
                for (int i = 0; i < spl.length; i++) {
                    if(Objects.equals(spl[i], deductDate)) {
                        LOG.info("代理商ID：{}，通用POS奖励活动生效月份：{}", map.get("agentId"), deductDate);
                        this.executcompute(deductDate, posRewardTemplate, map, agentType);
                        if(new BigDecimal(map.get("posRewardAmt").toString()).compareTo(BigDecimal.ZERO) > 0){
                            return;
                        }
                    }
                }
            } else {
                if(Objects.equals(posRewardTemplate.getActivityValid(), deductDate)){
                    this.executcompute(deductDate, posRewardTemplate, map, agentType);
                }
            }
        }
    }

    /**
     * 缓存中获取通用奖励模板
     * @return
     */
    private List<PosRewardTemplate> getPosRewardTemplateList() {
        String tmep = redisTemplate.opsForValue().get("POS_REWARD_TEMP");
        List list = JSONObject.parseObject(tmep, List.class);
        if(list != null && !list.isEmpty()){
            List<PosRewardTemplate> posRewardTemplates = new ArrayList<PosRewardTemplate>(list.size());
            list.forEach(lists -> {
                JSON json = (JSON) JSONObject.parse(lists.toString());
                PosRewardTemplate posRewardTemplate = JSONObject.toJavaObject(json, PosRewardTemplate.class);
                posRewardTemplates.add(posRewardTemplate);
            });
            return posRewardTemplates;
        } else {
           return  posRewardTemplateService.getPosRewardTemplateList();
        }
    }

    private void executcompute(String deductDate, PosRewardTemplate posRewardTemplate, Map<String, Object> map, String agentType) {
        BigDecimal traninTransAmt = this.queryTranContrastMonthAmt(deductDate.replace("-",""), posRewardTemplate.getTranContrastMonth().replace("-", ""), map, agentType);
        if(tranCountThreshold(traninTransAmt, posRewardTemplate, map)){
            BigDecimal creditposRewardAmt = this.queryCreditContrastMonthAmt(deductDate.replace("-",""), posRewardTemplate.getCreditTranContrastMonth().replace("-", ""), map, agentType);
            if(creditposRewardAmt.compareTo(BigDecimal.ZERO) > 0){
                BigDecimal posRewardAmt = creditposRewardAmt.multiply(posRewardTemplate.getProportion()).setScale(2,BigDecimal.ROUND_HALF_UP);
                LOG.info("代理商ID：{}，POS奖励金额：{}，奖励比例：{}", map.get("agentId"), posRewardAmt, posRewardTemplate.getProportion());
                map.put("posRewardAmt", posRewardAmt);
            } else {
                LOG.info("代理商ID：{}，当前执行月贷记交易总金额小于贷记交易对比月的贷记交易金额，没有POS奖励。", map.get("agentId"));
            }
        }
    }


    /**
     * 查询交易对比月交易金额
     * 如果是机构或者标准一代，则查询交易对比月下级商户交易金额。
     * @param tranContrastMonth
     * @param map
     */
    private BigDecimal queryTranContrastMonthAmt(String deductDate, String tranContrastMonth, Map<String, Object> map, String agentType) {
        LOG.info("查询对比月交易金额，代理商ID：{}，交易对比月：{}", map.get("agentId"), tranContrastMonth);
        TransProfitDetail transProfitDetail = profitDetailMonthServiceImpl.getTransProfitDetail(map.get("agentId").toString(),
                tranContrastMonth, map.get("agentType").toString());
        if(transProfitDetail == null){
            LOG.info("代理商ID：{}，没有找到交易对比月信息。暂不计算POS奖励", map.get("agentId"));
            return BigDecimal.ZERO;
        }
        BigDecimal oldInTransAmt = transProfitDetail.getInTransAmt() == null ? BigDecimal.ZERO :transProfitDetail.getInTransAmt();
        BigDecimal newInTransAmt = new BigDecimal(map.get("posTranAmt").toString());
        if(Objects.equals(map.get("agentType"), AGENT_TYPE_2) || Objects.equals(map.get("agentType"), AGENT_TYPE_6)){
            LOG.info("代理商ID：{}，该代理商为机构或者标准一代，查询下级机构业务编码", map.get("agentId"));
            List<String> agentIdList = this.queryChildLevelByBusNum(map);
            if(agentIdList.size() > 0){
                BigDecimal oldChildInTransAmt = BigDecimal.ZERO;
                List<TransProfitDetail> list = profitDetailMonthServiceImpl.getChildTransProfitDetailList(agentIdList, tranContrastMonth, agentType);
                if(list != null && !list.isEmpty()){
                    for(TransProfitDetail detail1 : list){
                        oldChildInTransAmt = oldChildInTransAmt.add(detail1.getInTransAmt() == null ? BigDecimal.ZERO : detail1.getInTransAmt());
                    }
                    LOG.info("代理商ID：{}，交易对比月下级商户交易汇总金额：{}", map.get("agentId"), oldChildInTransAmt);
                    oldInTransAmt = oldInTransAmt.add(oldChildInTransAmt);
                }

                LOG.info("查询当前执行月下级交易金额，代理商ID：{}，当前执行月：{}", map.get("agentId"), deductDate);
                BigDecimal newChildInTransAmt = BigDecimal.ZERO;
                List<TransProfitDetail> newlist = profitDetailMonthServiceImpl.getChildTransProfitDetailList(agentIdList, deductDate, agentType);
                if(newlist != null && !newlist.isEmpty()){
                    for(TransProfitDetail detail1 : newlist){
                        newChildInTransAmt = newChildInTransAmt.add(detail1.getInTransAmt() == null ? BigDecimal.ZERO : detail1.getInTransAmt());
                    }
                    LOG.info("代理商ID：{}，当前执行月下级商户交易汇总金额：{}", map.get("agentId"), newChildInTransAmt);
                    newInTransAmt = newInTransAmt.add(newChildInTransAmt);
                }
            } else {
                LOG.info("代理商ID：{}，该代理商为机构或者标准一代，没有找到他的下级", map.get("agentId"));
            }
        }
        LOG.info("代理商ID：{}，交易对比月交易汇总金额：{}", map.get("agentId"),  oldInTransAmt);
        LOG.info("代理商ID：{}，当前执行月交易汇总金额：{}", map.get("agentId"),  newInTransAmt);
        BigDecimal inTransAmt = newInTransAmt.subtract(oldInTransAmt);
        LOG.info("代理商ID：{}，交易汇总金额差值：{}", map.get("agentId"),  inTransAmt);
        return  inTransAmt;
    }

    /**
     * 校验交易总金额是否达到通用模板设定的活动奖励交易阀值
     * @param posTranAmt
     * @param posRewardTemplate
     * @param map
     */
    private boolean tranCountThreshold(BigDecimal posTranAmt,PosRewardTemplate posRewardTemplate, Map<String, Object> map) {
        if(posTranAmt.compareTo(posRewardTemplate.getTranTotalStart().multiply(new BigDecimal("10000"))) > 0
                && posTranAmt.compareTo(posRewardTemplate.getTranTotalEnd().multiply(new BigDecimal("10000"))) <= 0 ){
            LOG.info("代理商ID：{}，交易总额达标范围：{}万~{}万，奖励比例：{}", map.get("agentId"), posRewardTemplate.getTranTotalStart(),posRewardTemplate.getTranTotalEnd(), posRewardTemplate.getProportion());
            return true;
        } else {
            LOG.info("代理商ID：{}，交易总金额差值：{}，交易总额未达标范围：{}万~{}万", map.get("agentId"), posTranAmt, posRewardTemplate.getTranTotalStart(),posRewardTemplate.getTranTotalEnd());
            return false;
        }
    }

    /**
     * 查询贷记对比月贷记交易金额
     * 如果是机构或者标准一代，则查询贷记对比月下级商户贷记交易金额。
     * @param creditTranContrastMonth
     * @param map
     */
    private BigDecimal queryCreditContrastMonthAmt(String deductDate, String creditTranContrastMonth, Map<String, Object> map, String agentType) {
        LOG.info("查询对比月贷记交易金额，代理商ID：{}，贷记交易对比月：{}", map.get("agentId"), creditTranContrastMonth);
        TransProfitDetail transProfitDetail = profitDetailMonthServiceImpl.getTransProfitDetail(map.get("agentId").toString(),
                creditTranContrastMonth, map.get("agentType").toString());
        if(transProfitDetail == null){
            LOG.info("代理商ID：{}，没有找到贷记交易对比月信息。暂不计算POS奖励", map.get("agentId"));
            return BigDecimal.ZERO;
        }
        BigDecimal oldPosRewardAmt = transProfitDetail.getPosRewardAmt() == null ? BigDecimal.ZERO :transProfitDetail.getPosRewardAmt();
        BigDecimal newPosRewardAmt = new BigDecimal(map.get("posJlTranAmt").toString());
        if(Objects.equals(map.get("agentType"), AGENT_TYPE_2) || Objects.equals(map.get("agentType"), AGENT_TYPE_6)){
            LOG.info("代理商ID：{}，该代理商为机构或者标准一代，查询下级机构业务编码", map.get("agentId"));
            List<String> agentIdList = this.queryChildLevelByBusNum(map);
            if(agentIdList.size() > 0){
                BigDecimal oldChildPosRewardAmt = BigDecimal.ZERO;
                List<TransProfitDetail> list = profitDetailMonthServiceImpl.getChildTransProfitDetailList(agentIdList, creditTranContrastMonth, agentType);
                if(list != null && !list.isEmpty()){
                    for(TransProfitDetail detail1 : list){
                        oldChildPosRewardAmt = oldChildPosRewardAmt.add(detail1.getPosRewardAmt() == null ? BigDecimal.ZERO : detail1.getPosRewardAmt());
                    }
                    LOG.info("代理商ID：{}，贷记交易对比月下级商户贷记交易汇总金额：{}", map.get("agentId"), oldChildPosRewardAmt);
                    oldPosRewardAmt = oldPosRewardAmt.add(oldChildPosRewardAmt);
                }

                LOG.info("查询当前执行月下级贷记交易金额，代理商ID：{}，当前执行月：{}", map.get("agentId"), deductDate);
                BigDecimal newChildPosRewardAmt = BigDecimal.ZERO;
                List<TransProfitDetail> newlist = profitDetailMonthServiceImpl.getChildTransProfitDetailList(agentIdList, deductDate, agentType);
                if(newlist != null && !newlist.isEmpty()){
                    for(TransProfitDetail detail1 : newlist){
                        newChildPosRewardAmt = newChildPosRewardAmt.add(detail1.getPosRewardAmt() == null ? BigDecimal.ZERO : detail1.getPosRewardAmt());
                    }
                    LOG.info("代理商ID：{}，当前执行月下级商户贷记交易汇总金额：{}", map.get("agentId"), newChildPosRewardAmt);
                    newPosRewardAmt = newPosRewardAmt.add(newChildPosRewardAmt);
                }
            }else {
                LOG.info("代理商ID：{}，该代理商为机构或者标准一代，没有找到他的下级", map.get("agentId"));
            }
        }
        LOG.info("代理商ID：{}，贷记交易对比月贷记交易汇总金额：{}", map.get("agentId"),  oldPosRewardAmt);
        LOG.info("代理商ID：{}，当前执行月贷记交易汇总金额：{}", map.get("agentId"),  newPosRewardAmt);
        BigDecimal PosRewardAmt = newPosRewardAmt.subtract(oldPosRewardAmt);
        LOG.info("代理商ID：{}，贷记交易汇总金额差值：{}", map.get("agentId"),  PosRewardAmt);
        return  PosRewardAmt;
    }

    /**
     * 查询下级商户业务编码
     * @param map
     * @return
     */
    private List<String> queryChildLevelByBusNum(Map<String, Object> map){
        List<AgentBusInfo> busList = agentBusinfoService.queryChildLevelByBusNum(null, PLATFORM_CODE, map.get("agentId").toString());
        List<String> agentIdList = new ArrayList<String>();
        if(busList != null && !busList.isEmpty()) {
            busList.forEach(agentBusInfo -> {
                if (agentBusInfo.getBusNum() != null) {
                    agentIdList.add(agentBusInfo.getBusNum());
                }
            });
        }
        return agentIdList;
    }

    /**
     * 根据代理商查找上级代理商ID
     * @param posMap
     */
    private void obtainParentAgentId(Map<String, Object> posMap){
        List<AgentBusInfo> agentBusInfo = agentBusinfoService.queryParenFourLevelBusNum(new ArrayList<AgentBusInfo>(), PLATFORM_CODE, posMap.get("agentId").toString());
        if(agentBusInfo != null && !agentBusInfo.isEmpty()){
            posMap.put("parentAgentId", agentBusInfo.get(0).getAgentId());
            LOG.info("代理商ID：{}，上级代理商ID：{}", posMap.get("agentId"), agentBusInfo.get(0).getAgentId());
        }
    }

    /**
     * 扣减上级代理商月分润
     * @param deductDate
     * @param posMap
     */
    private void deductParentAgentPosRewardAmt(String deductDate, Map<String, Object> posMap) {

        Map<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("agentId", posMap.get("parentAgentId").toString());
        this.obtainParentAgentId(newMap);
        String parenAgentId = newMap.get("parentAgentId") == null ? null : newMap.get("parentAgentId").toString();
        ProfitDetailMonth profitDetailMonth = profitMonthService.getAgentProfit(posMap.get("parentAgentId").toString(),
                deductDate.replace("-",""), parenAgentId);
        if(profitDetailMonth == null ){
            LOG.error("代理商ID：{}，上级代理商ID：{}，未查询到上级代理商月交易明细", posMap.get("agentId"), posMap.get("parentAgentId"));
            return;
        }
        LOG.info("代理商ID：{}，上级代理商ID：{}，上级代理商分润：{}，上级代理POS奖励:{}，扣减奖励：{}", posMap.get("agentId"),
                posMap.get("parentAgentId"), profitDetailMonth.getRealProfitAmt(), profitDetailMonth.getPosRewardAmt(), "-"+posMap.get("posRewardAmt"));
        BigDecimal praentBasicsProfitAmt = profitDetailMonth.getBasicsProfitAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getBasicsProfitAmt();
        BigDecimal praentPosRewardAmt = profitDetailMonth.getPosRewardAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getPosRewardAmt();
        BigDecimal posRewardAmt = praentPosRewardAmt.subtract(new BigDecimal(posMap.get("posRewardAmt").toString()));
        ProfitDetailMonth update = new ProfitDetailMonth();
        update.setId(profitDetailMonth.getId());
        if(praentBasicsProfitAmt.compareTo(BigDecimal.ZERO) > 0){
            update.setPosRewardAmt(posRewardAmt);
            update.setBasicsProfitAmt(praentBasicsProfitAmt.subtract(new BigDecimal(posMap.get("posRewardAmt").toString())));
        } else {
            update.setPosRewardDeductionAmt(new BigDecimal(posMap.get("posRewardAmt").toString()).negate());
        }
        //新增扣款明细，
        ProfitDeduction deductDetail = new ProfitDeduction();
        deductDetail.setAgentId(profitDetailMonth.getAgentId());
        deductDetail.setAgentPid(profitDetailMonth.getAgentPid());
        deductDetail.setDeductionDate(deductDate);
        deductDetail.setDeductionDesc(PLATFORM_CODE);
        deductDetail.setDeductionType(DeductionType.POS_REWARD_DEDUCT.getType());
        deductDetail.setActualDeductionAmt(new BigDecimal(posMap.get("posRewardAmt").toString()));
        deductDetail.setMustDeductionAmt(new BigDecimal(posMap.get("posRewardAmt").toString()));
        deductDetail.setRemark("下级代理商扣减上级代理商pos奖励，下级代理的ID"+posMap.get("agentId"));
        deductDetail.setId(profitDetailMonth.getId());
        profitDeducttionDetailService.insertDeducttionDetail(deductDetail);
        profitDetailMonthServiceImpl.updateByPrimaryKeySelective(update);
    }

    /**
     * 季度考核扣款
     * @param posReward1
     * @param map
     */
    private void quarterAssessment(PosReward posReward1, Map<String, Object> map) {
        LOG.info("代理商ID：{}，到达活动考核月份", map.get("agentId"));
        Map<String, Object> assMap = new HashMap<String, Object>();
        assMap.putAll(map);
        BigDecimal posTranAmt = BigDecimal.ZERO;
        List<String> month = new ArrayList<String>();
        if(posReward1.getTotalConsMonth().contains("~")){
            String[] spl = posReward1.getTotalConsMonth().trim().split("~");
            for (int i = 0; i < spl.length; i++) {
                month.add(spl[i].replace("-",""));
            }
        } else {
            month.add(posReward1.getTotalConsMonth().replace("-",""));
        }
        //1、根据活动奖励月份，查询这个代理商活动月份交易总金额
        posTranAmt = posTranAmt.add(quarterAssessmentTranOrCreditAmt(assMap, month));
        LOG.info("代理商ID：{}，代理商季度考核活动总交易量：{}", assMap.get("agentId"), posTranAmt);

        //2、交易总金额与特殊奖励活动模板的总金额比较,累计交易量不满足奖励条件，按照通用POS奖励模板重新计算奖励
        if(posTranAmt.compareTo(posReward1.getGrowAmt().multiply(new BigDecimal("10000"))) >= 0){
            LOG.info("代理商ID：{}，代理商季度交易量达到活动指标，不进行扣减奖励", assMap.get("agentId"));
            return;
        }
        LOG.info("代理商ID：{}，总交易额：{}，活动设定总交易额：{}，代理商季度交易量指标不合格，进行扣减奖励", assMap.get("agentId"),posTranAmt ,posReward1.getGrowAmt().multiply(new BigDecimal("10000")));

        //3.按照通用奖励模板，重新计算代理活动月份奖励金额
        BigDecimal posRewardAmt = this.afreshPosRewardAmt(posReward1, assMap);

        //4、查找当前代理商（机构、标准一代）下所有代理商业务编码
        List<String> agentIdList = this.queryChildLevelByBusNum(assMap);
        BigDecimal beforeChildPosRewardAmt = BigDecimal.ZERO;
        if(agentIdList.size() > 0){
            //5.查询这个代理商所有下属代理商的POS奖励金额
            beforeChildPosRewardAmt = beforeChildPosRewardAmt.add(this.getBeforePosRewardAmt(month, assMap, agentIdList));
        }
        LOG.info("代理商ID：{}，获取下属所有代理商的POS奖励金额：{}", assMap.get("agentId"), beforeChildPosRewardAmt);
        BigDecimal beforePosRewardAmt = this.getBeforePosRewardAmt(month, assMap, null);
        LOG.info("代理商ID：{}，之前活动期间奖励的POS金额：：{}", assMap.get("agentId"), beforePosRewardAmt);
        //6.核算POS奖励：之前按特殊奖励发放的POS奖励-本次按通用模板计算的POS奖励-下属代理商发放的奖励=考核季度实际扣款奖励
        BigDecimal posAssDeductAmt = beforePosRewardAmt.subtract(posRewardAmt).subtract(beforeChildPosRewardAmt);
        LOG.info("代理商ID：{}，最后扣减奖励：{}", assMap.get("agentId"), posAssDeductAmt);
        map.put("posAssDeductAmt", posAssDeductAmt);
    }

    /**
     * 按照通用奖励模板，重新计算代理活动月份奖励金额
     * @param posReward1
     * @param assMap
     */
    private BigDecimal afreshPosRewardAmt(PosReward posReward1, Map<String, Object> assMap) {
        BigDecimal posRewardAmt = BigDecimal.ZERO;
        if(posReward1.getTotalConsMonth().contains("~")){
            String[] spl = posReward1.getTotalConsMonth().trim().split("~");
            for (int i = 0; i < spl.length; i++) {
                TransProfitDetail transProfitDetail = profitDetailMonthServiceImpl.getTransProfitDetail(assMap.get("agentId").toString(),
                        spl[i].replace("-", ""), assMap.get("agentType").toString());
                if(transProfitDetail != null){
                    Map<String, Object> map = this.refurtComputePosReward(assMap, spl[i], transProfitDetail);
                    BigDecimal refposRewardAmt = map.get("posRewardAmt") == null ? BigDecimal.ZERO : new BigDecimal(map.get("posRewardAmt").toString());
                    posRewardAmt = posRewardAmt.add(refposRewardAmt);
                    LOG.info("代理商ID：{}，月份：{}，采用通用模板计算的POS奖励：{}", assMap.get("agentId"), spl[i], posRewardAmt);
                }
            }
        } else {
            TransProfitDetail transProfitDetail = profitDetailMonthServiceImpl.getTransProfitDetail(assMap.get("agentId").toString(),
                    posReward1.getTotalConsMonth().replace("-",""), assMap.get("agentType").toString());
            if(transProfitDetail != null){
                Map<String, Object> map = this.refurtComputePosReward(assMap, posReward1.getTotalConsMonth(), transProfitDetail);
                BigDecimal refposRewardAmt = map.get("posRewardAmt") == null ? BigDecimal.ZERO : new BigDecimal(map.get("posRewardAmt").toString());
                posRewardAmt = posRewardAmt.add(refposRewardAmt);
                LOG.info("代理商ID：{}，月份：{}，采用通用模板计算的POS奖励：{}", assMap.get("agentId"), posReward1.getTotalConsMonth(), posRewardAmt);
            }
        }
        LOG.info("代理商ID：{}，按照通用POS奖励模板重新计算POS奖励，重新计算的POS奖励：{}", assMap.get("agentId"), posRewardAmt);
        return posRewardAmt;
    }

    private Map<String, Object> refurtComputePosReward(Map<String, Object> assMap, String profitDate, TransProfitDetail transProfitDetail) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("agentType", assMap.get("agentType"));
        map.put("agentId", assMap.get("agentId"));
        map.put("agentPid", assMap.get("agentPid"));
        map.put("posTranAmt", transProfitDetail.getPosRewardAmt());
        map.put("posJlTranAmt", transProfitDetail.getPosCreditAmt());
        map.put("posRewardAmt", BigDecimal.ZERO);
        map.put("posAssDeductAmt", BigDecimal.ZERO);
        this.obtainRewardTemp(profitDate, map);
        return map;
    }

    /**
     * 查询这个代理商所有下属代理商的POS奖励金额
     * @param month
     * @param assMap
     * @param agentIdList
     * @return
     */
    private BigDecimal getBeforePosRewardAmt(List<String> month, Map<String, Object> assMap, List<String> agentIdList) {
        BigDecimal beforePosRewardAmt = BigDecimal.ZERO;
        ProfitDetailMonthExample example = new ProfitDetailMonthExample();
        ProfitDetailMonthExample.Criteria criteria = example.createCriteria();
        if(agentIdList == null ){
            criteria.andAgentIdEqualTo(assMap.get("agentId").toString());
        } else {
            criteria.andAgentIdIn(agentIdList);
        }
        criteria.andProfitDateIn(month);
        List<ProfitDetailMonth> list = profitDetailMonthServiceImpl.selectByExample(example);
        if(list != null && !list.isEmpty()){
            for ( ProfitDetailMonth profitDetailMonth :list){
                beforePosRewardAmt = beforePosRewardAmt.add(profitDetailMonth.getPosRewardAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getPosRewardAmt());
            }
        }
        return beforePosRewardAmt;
    }

    /**
     * 根据活动奖励月份，查询这个代理商活动月份交易总金额
     * 包含所有下级的总交易
     * @param map
     * @param monthList
     * @return
     */
    private BigDecimal quarterAssessmentTranOrCreditAmt(Map<String, Object> map, List<String> monthList) {
        List<TransProfitDetail> transProfitDetail = profitDetailMonthServiceImpl.getChildTransProfitDetailList(map.get("agentId").toString(), monthList, map.get("agentType").toString());
        BigDecimal posTranAmt = BigDecimal.ZERO;
        for(TransProfitDetail list: transProfitDetail){
            posTranAmt = posTranAmt.add(list.getPosRewardAmt() == null ? BigDecimal.ZERO : list.getPosRewardAmt());
        }
        String agentType = null;
        if(Objects.equals(AGENT_TYPE_2, map.get("agentType"))){
            agentType = AGENT_TYPE_3;
        } else {
            agentType = map.get("agentType").toString();
        }

        //查询下级的交易量
        List<String> agentIdList = this.queryChildLevelByBusNum(map);
        BigDecimal childTranSumAmt = BigDecimal.ZERO;
        if(agentIdList.size() > 0){
            List<TransProfitDetail> childList = profitDetailMonthServiceImpl.getChildTransProfitDetailList(agentIdList, monthList, agentType);
            childList.forEach(transProfitDetail1 -> {
                childTranSumAmt.add(transProfitDetail1.getPosRewardAmt());
            });
        }
        BigDecimal sumAmt = posTranAmt.add(childTranSumAmt);
        return sumAmt;
    }
}
