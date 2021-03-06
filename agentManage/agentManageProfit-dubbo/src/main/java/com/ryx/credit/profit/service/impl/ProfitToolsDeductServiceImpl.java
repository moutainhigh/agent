package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.PamentSrcType;
import com.ryx.credit.common.enumc.PaySign;
import com.ryx.credit.profit.enums.DeductionStatus;
import com.ryx.credit.profit.enums.DeductionType;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.*;
import com.ryx.credit.service.order.IOPdSumService;
import com.ryx.credit.service.order.IPaymentDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author yangmx
 * @desc 机具分润扣款
 */
@Service("profitToolsDeductServiceImpl")
public class ProfitToolsDeductServiceImpl implements DeductService {

    private static Logger LOG = LoggerFactory.getLogger(ProfitToolsDeductServiceImpl.class);
    @Autowired
    private ProfitDeductionService profitDeductionService;
    @Autowired
    private ProfitDeducttionDetailService profitDeducttionDetailService;
    @Autowired
    private ProfitMonthService profitMonthService;
    @Autowired
    private ProfitDetailMonthService profitDetailMonthServiceImpl;
    @Autowired
    private IPaymentDetailService iPaymentDetailService;
    @Autowired
    private IOPdSumService OPdSumService;

    private static final String RHB = "5000";
    private static final String POS = "100003";
    private static final String ZPOS = "100002";

    @Override
    public Map<String, Object> execut(Map<String, Object> map) throws Exception {
        LOG.info("机具分润扣款请求参数：{}", map);
        String agentPid = map.get("agentPid").toString();
        String computType = map.get("computType").toString();
        String parentAgentId =null;
        if(map.get("parentAgentId")!=null){
            parentAgentId = map.get("parentAgentId").toString();
        }
        PToolSupply pToolSupply  =null;
        if(map.get("pToolSupply")!=null){
               pToolSupply = (PToolSupply) map.get("pToolSupply");
        }
        ProfitDeduction profitDeduction = new ProfitDeduction();
        profitDeduction.setAgentId(agentPid);
        profitDeduction.setDeductionDate(map.get("deductDate").toString());
        profitDeduction.setDeductionType(DeductionType.MACHINE.getType());
        profitDeduction.setParentAgentId(parentAgentId);
        List<ProfitDeduction> list = profitDeductionService.getProfitDeduction(profitDeduction);
        if (list != null && !list.isEmpty()) {
            if (Objects.equals("1", map.get("rotation"))) {
                LOG.info("第一轮机具扣款，扣代理商本身，代理商编号：{}", agentPid);
                return this.fristRound(map, agentPid, computType, list);
            } /*else if (Objects.equals("2", map.get("rotation"))) {
                LOG.info("第二轮机具扣款，扣合并代理商：代理商编号：{}", agentPid);
                return this.secondRound(agentPid, map, list, computType);
            }*/ else if (Objects.equals("3", map.get("rotation"))) {
                LOG.info("第三轮机具扣款，扣关联代理商：代理商编号：{}", agentPid);
                return this.secondRound(agentPid, map, list, computType);
            } else if (Objects.equals("4", map.get("rotation"))){
                LOG.info("第四轮机具扣款（上级代扣或者下级补款）：代理商编号：{}", agentPid);
                return  this.fourRound(map, pToolSupply, computType);
            }
        }
        return map;
    }

    private Map<String, Object> fristRound(Map<String, Object> map, String agentPid, String computType, List<ProfitDeduction> list) throws Exception {
       /* String agentId= map.get("agentPid").toString();
        String deductDate = map.get("deductDate").toString();
        BigDecimal profitSumAmt = BigDecimal.ZERO;*/
        BigDecimal profitSumAmt = new BigDecimal(map.get("agentProfitAmt").toString());


     /* list.stream().filter(profitDeduction1 -> Objects.equals(POS, profitDeduction1.getDeductionDesc())).map(ProfitDeduction::getMustDeductionAmt).reduce(BigDecimal::add).ifPresent(bigDecimal -> map.put("PosDgMustDeductionAmt", bigDecimal));
        list.stream().filter(profitDeduction1 -> Objects.equals(RHB, profitDeduction1.getDeductionDesc())).map(ProfitDeduction::getMustDeductionAmt).reduce(BigDecimal::add).ifPresent(bigDecimal -> map.put("RhbDgMustDeductionAmt", bigDecimal));
        list.stream().filter(profitDeduction1 -> Objects.equals(ZPOS, profitDeduction1.getDeductionDesc())).map(ProfitDeduction::getMustDeductionAmt).reduce(BigDecimal::add).ifPresent(bigDecimal -> map.put("ZposDgMustDeductionAmt", bigDecimal));*/
        BigDecimal sumMustDeductionAmt =BigDecimal.ZERO;
     for (ProfitDeduction profitDeduction:list) {
         sumMustDeductionAmt = profitDeduction.getMustDeductionAmt().add(sumMustDeductionAmt);
        }
        map.put("PosDgMustDeductionAmt",sumMustDeductionAmt);
        /*ProfitDetailMonthExample profitDetailMonthExample = new ProfitDetailMonthExample();
        ProfitDetailMonthExample.Criteria criteria = profitDetailMonthExample.createCriteria();
        if(agentPid!=null){
            criteria.andAgentIdEqualTo(agentPid);
        }
        if (map.get("deductDate")!=null) {
            criteria.andProfitDateEqualTo(map.get("deductDate").toString());
        }
        if(map.get("parentAgentId")!=null){
            criteria.andParentAgentIdEqualTo(map.get("parentAgentId").toString());
        }
        List<ProfitDetailMonth> profitDetailMonthList =  profitDetailMonthServiceImpl.selectByExample(profitDetailMonthExample);
        if(profitDetailMonthList.size()!=1){
            LOG.info("获取月分润数据错误");
            return null;
        }
        ProfitDetailMonth profitDetailMonth = profitDetailMonthList.get(0);
        profitDetailMonth.setPosDgMustDeductionAmt(sumMustDeductionAmt);*/

     for (ProfitDeduction deduction : list) {
            if (deduction.getMustDeductionAmt().compareTo(deduction.getActualDeductionAmt()) == 0) {
                continue;
            }
            BigDecimal mustAmt = null;
            if (deduction.getActualDeductionAmt().compareTo(BigDecimal.ZERO) > 0) {
                mustAmt = deduction.getMustDeductionAmt().subtract(deduction.getActualDeductionAmt());
            } else {
                mustAmt = deduction.getMustDeductionAmt();
            }
            LOG.info("机具扣款流水号：{}，代理商唯一码：{}，应扣金额：{}，分润汇总剩余：{}", deduction.getSourceId(), agentPid, mustAmt, profitSumAmt);

            if (Objects.equals("1", map.get("rotation"))) {
               /* String busCode =deduction.getDeductionDesc();
                TransProfitDetailExample transProfitDetail = new TransProfitDetailExample();
                TransProfitDetailExample.Criteria criteria = transProfitDetail.createCriteria();
                criteria.andAgentIdEqualTo(agentId);
                criteria.andProfitDateEqualTo(deductDate);
                criteria.andBusCodeEqualTo(busCode);
                List<TransProfitDetail> transProfitDetails = profitDetailMonthServiceImpl.getTransProfitDetailByBusCode(transProfitDetail);
                if (transProfitDetails.size()!=1){
                    LOG.info("获取对应平台的月份润错误");
                    return null;
                }
                TransProfitDetail transProfitDetail1 = transProfitDetails.get(0);
                //平台对应的分润
                profitSumAmt = transProfitDetail1.getProfitAmt();*/
                if (profitSumAmt.compareTo(BigDecimal.ZERO) > 0) {
                    this.basicsProfitDeductAmt(deduction, mustAmt, profitSumAmt, map);
                    profitSumAmt = (BigDecimal) map.get("profitSumAmt");
                    LOG.info("机具扣款流水号：{}，此条机具扣款完毕。应扣：{}，实扣：{}，未扣足：{}，分润汇总剩余：{}", deduction.getSourceId(),
                            deduction.getMustDeductionAmt(), deduction.getActualDeductionAmt(), deduction.getNotDeductionAmt(), profitSumAmt);
                }
            }
            /*if(Objects.equals("3", map.get("rotation"))){
                this.deduceParentAgent(deduction, mustAmt, computType);
                LOG.info("机具扣款流水号：{}，此条机具扣款完毕。应扣：{}，实扣：{}，未扣足：{}", deduction.getSourceId(),
                        deduction.getMustDeductionAmt(), deduction.getActualDeductionAmt(), deduction.getNotDeductionAmt());
            }*/
        }
       /* list.stream().filter(profitDeduction1 -> Objects.equals(POS, profitDeduction1.getDeductionDesc())).map(ProfitDeduction::getActualDeductionAmt).reduce(BigDecimal::add).ifPresent(bigDecimal -> map.put("PosDgRealDeductionAmt", bigDecimal));
        list.stream().filter(profitDeduction1 -> Objects.equals(RHB, profitDeduction1.getDeductionDesc())).map(ProfitDeduction::getActualDeductionAmt).reduce(BigDecimal::add).ifPresent(bigDecimal -> map.put("RhbDgRealDeductionAmt", bigDecimal));
        list.stream().filter(profitDeduction1 -> Objects.equals(ZPOS, profitDeduction1.getDeductionDesc())).map(ProfitDeduction::getActualDeductionAmt).reduce(BigDecimal::add).ifPresent(bigDecimal -> map.put("ZposTdRealDeductionAmt", bigDecimal));*/
        BigDecimal sumRealDeductionAmt =BigDecimal.ZERO;
        for (ProfitDeduction profitDeduction:list) {
            sumRealDeductionAmt = profitDeduction.getActualDeductionAmt().add(sumRealDeductionAmt);
        }
        map.put("PosDgRealDeductionAmt",sumRealDeductionAmt);
        /*profitDetailMonth.setPosDgRealDeductionAmt(sumRealDeductionAmt);

        profitMonthService.updateByPrimaryKeySelective(profitDetailMonth);*/
        map.put("agentProfitAmt", profitSumAmt);
        LOG.info("机具分润扣款响应参数：{}", map);
        return map;
    }

    /**
     * @Author: Zhang Lei
     * @Description: 执行机具扣款
     * @Param deduction 机具扣款记录
     * @Param mustAmt 应扣
     * @Param basicsProfitAmt 剩余分润
     * @Param map
     * @Date: 15:34 2019/2/15
     */
    private void basicsProfitDeductAmt(ProfitDeduction deduction, BigDecimal mustAmt, BigDecimal basicsProfitAmt, Map<String, Object> map) {
        if (basicsProfitAmt.compareTo(mustAmt) >= 0) {
            deduction.setActualDeductionAmt(deduction.getActualDeductionAmt().add(mustAmt));
            deduction.setNotDeductionAmt(BigDecimal.ZERO);
            basicsProfitAmt = basicsProfitAmt.subtract(mustAmt);
            map.put("profitSumAmt", basicsProfitAmt);
            try {
                this.updateDeductionInfo(deduction, map.get("computType").toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            BigDecimal notDeductionAmt = mustAmt.subtract(basicsProfitAmt);
            LOG.info("机具扣款流水号：{}，代理商唯一码：{}，汇总分润不足，先扣减剩余的分润：{}，未扣足：{}",
                    deduction.getSourceId(), deduction.getAgentId(), basicsProfitAmt, notDeductionAmt);
            map.put("profitSumAmt", BigDecimal.ZERO);
            deduction.setActualDeductionAmt(deduction.getActualDeductionAmt().add(basicsProfitAmt));

            //if(deduction.getParentAgentPid() == null){
            //   LOG.info("机具扣款流水号：{}，代理商唯一码：{}，汇总分润已扣完，且没有担保代理商。", deduction.getSourceId(), deduction.getAgentId());
            try {
                deduction.setNotDeductionAmt(notDeductionAmt);
                this.updateDeductionInfo(deduction, map.get("computType").toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            //} else {
            //    this.deduceParentAgent(deduction, notDeductionAmt, map.get("computType").toString());
            //}
        }
    }

    private void updateDeductionInfo(ProfitDeduction profitDeductionList, String computType) throws Exception {
        try {

                if (profitDeductionList != null) {
                    ProfitDeduction update = new ProfitDeduction();
                    update.setId(profitDeductionList.getId());
                    if (Objects.equals(computType, "1")) {
                        update.setStagingStatus(DeductionStatus.YES_WITHHOLD.getStatus());
                    }
                    update.setActualDeductionAmt(profitDeductionList.getActualDeductionAmt());
                    update.setNotDeductionAmt(profitDeductionList.getNotDeductionAmt());
                    profitDeductionService.updateProfitDeduction(update);
                    profitDeducttionDetailService.insertDeducttionDetail(profitDeductionList);

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("机具扣款更新失败。");
        }
    }


    /**
     * 扣除合并代理商的分润，合并代理商的分润被扣完，则将该合并代理从list列表里删除
     * 代理商代扣的机具款，记录在合并代理商对应的机具实扣列中
     *
     * @param agentPid
     * @param map
     * @return
     */
    private Map<String, Object> secondRound(String agentPid, Map<String, Object> map, List<ProfitDeduction> list, String computType) {
        String typename = (String) map.get("rotation");
        List<Map<String, Object>> mergeAgentList = (List<Map<String, Object>>) map.get("hbList");
        for (ProfitDeduction profitDeductionList : list) {
            if (profitDeductionList.getNotDeductionAmt().compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }
            Iterator<Map<String, Object>> iter = mergeAgentList.iterator();
            while (iter.hasNext()) {
                Map<String, Object> mergeMap = iter.next();
                ProfitDetailMonth profitMonth = profitMonthService.getProfitDetailMonth((String) mergeMap.get("id"));
                if (profitMonth == null) {
                    continue;
                }
                BigDecimal basicAmt = new BigDecimal(mergeMap.get("basicAmt").toString());
                BigDecimal notDeductionAmt = profitDeductionList.getNotDeductionAmt();
                LOG.info("机具扣款代理商唯一码：{}，应扣：{}，实扣：{}，未扣足金额：{}",
                        agentPid, profitDeductionList.getMustDeductionAmt(), profitDeductionList.getActualDeductionAmt(), notDeductionAmt);
                LOG.info(typename + "代理商唯一码：{}，代理商基础分润：{}", profitMonth.getAgentId(), basicAmt);
                BigDecimal deductAmt = null;
                ProfitDeduction insert = new ProfitDeduction();
                if (basicAmt.compareTo(notDeductionAmt) >= 0) {
                    basicAmt = basicAmt.subtract(notDeductionAmt);
                    deductAmt = notDeductionAmt;
                    mergeMap.put("basicAmt", basicAmt);
                    insert.setMustDeductionAmt(notDeductionAmt);
                    insert.setActualDeductionAmt(notDeductionAmt);
                    profitDeductionList.setNotDeductionAmt(BigDecimal.ZERO);
                    profitDeductionList.setActualDeductionAmt(profitDeductionList.getActualDeductionAmt().add(notDeductionAmt));
                    LOG.info(typename + "代理商唯一码：{}，已扣金额：{}，代理商剩余基础分润：{}",
                            profitMonth.getAgentId(), notDeductionAmt, basicAmt);
                } else {
                    LOG.info("基础分润不足，" + typename + "代理商唯一码：{}，已扣金额：{}，代理商剩余基础分润：{}",
                            profitMonth.getAgentId(), basicAmt, BigDecimal.ZERO);
                    BigDecimal surNotDeductionAmt = notDeductionAmt.subtract(basicAmt);
                    insert.setMustDeductionAmt(notDeductionAmt);
                    insert.setActualDeductionAmt(basicAmt);
                    insert.setNotDeductionAmt(surNotDeductionAmt);
                    profitDeductionList.setNotDeductionAmt(surNotDeductionAmt);
                    profitDeductionList.setActualDeductionAmt(profitDeductionList.getActualDeductionAmt().add(basicAmt));
                    deductAmt = basicAmt;
                    mergeMap.put("basicAmt", BigDecimal.ZERO);
                }

                try {
                    //if (Objects.equals(computType, "1")) {
                        insert.setAgentPid(profitMonth.getAgentId());
                        insert.setAgentId(profitMonth.getAgentId());
                        insert.setAgentName(profitMonth.getAgentName());
                        insert.setDeductionType(DeductionType.MACHINE.getType());
                        insert.setDeductionDesc(profitDeductionList.getDeductionDesc());
                        insert.setDeductionDate(profitDeductionList.getDeductionDate());
                        insert.setId(profitDeductionList.getId());
                        insert.setRemark(typename + "代理商代扣机具款，扣款明细：" + profitDeductionList.getSourceId());
                        insert.setUserId(profitDeductionList.getAgentId());
                        profitDeducttionDetailService.insertDeducttionDetail(insert);
                    //}

                    ProfitDetailMonth update = new ProfitDetailMonth();
                    update.setId((String) mergeMap.get("id"));
                    /*if (Objects.equals(POS, profitDeductionList.getDeductionDesc())) {*/
                        BigDecimal posDgRealDeductionAmt = profitMonth.getPosDgRealDeductionAmt() == null ? BigDecimal.ZERO : profitMonth.getPosDgRealDeductionAmt();
                        update.setPosDgRealDeductionAmt(posDgRealDeductionAmt.add(deductAmt));
                    /*} else if (Objects.equals(ZPOS, profitDeductionList.getDeductionDesc())) {
                        BigDecimal zposTdRealDeductionAmt = profitMonth.getZposTdRealDeductionAmt() == null ? BigDecimal.ZERO : profitMonth.getZposTdRealDeductionAmt();
                        update.setZposTdRealDeductionAmt(zposTdRealDeductionAmt.add(deductAmt));
                    } else if (Objects.equals(RHB, profitDeductionList.getDeductionDesc())) {
                        BigDecimal rhbDgRealDeductionAmt = profitMonth.getRhbDgRealDeductionAmt() == null ? BigDecimal.ZERO : profitMonth.getRhbDgRealDeductionAmt();
                        update.setRhbDgRealDeductionAmt(rhbDgRealDeductionAmt.add(deductAmt));
                    }*/
                    //更新剩余分润
                    update.setBasicsProfitAmt((BigDecimal) mergeMap.get("basicAmt"));
                    profitMonthService.updateByPrimaryKeySelective(update);


                        ProfitDeduction updateDeduct = new ProfitDeduction();
                        updateDeduct.setId(profitDeductionList.getId());
                        if (Objects.equals(computType, "1")) {
                            updateDeduct.setStagingStatus(DeductionStatus.YES_WITHHOLD.getStatus());
                        }
                        updateDeduct.setRev1(insert.getActualDeductionAmt().toString());
                        updateDeduct.setActualDeductionAmt(profitDeductionList.getActualDeductionAmt());
                        updateDeduct.setNotDeductionAmt(profitDeductionList.getNotDeductionAmt());
                        profitDeductionService.updateProfitDeduction(updateDeduct);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (profitDeductionList.getMustDeductionAmt().compareTo(profitDeductionList.getActualDeductionAmt()) == 0) {
                    LOG.info("代理商唯一码：{}，订单号：{}，该笔机具款实扣金额已经扣完，循环下一条机具扣款。应扣：{}，实扣：{}，未扣足：{}",
                            profitDeductionList.getAgentId(), profitDeductionList.getSourceId(),
                            profitDeductionList.getMustDeductionAmt(), profitDeductionList.getActualDeductionAmt(), profitDeductionList.getNotDeductionAmt());
                    break;
                } else {
                    LOG.info(typename + "代理商分润已扣完。代理商分润主键：{}，继续扣下个代理商扣款。应扣：{}，实扣：{}，未扣足：{}",
                            mergeMap.get("id"), profitDeductionList.getMustDeductionAmt(), profitDeductionList.getActualDeductionAmt(), profitDeductionList.getNotDeductionAmt());
                }
            }
        }
        map.put("hbList", mergeAgentList);
        LOG.info("机具分润扣款响应参数：{}", map);
        return map;
    }







    /**
     * 代理商机具扣款，本级以及关联代理商不够扣，申请线下补款和上级代扣
     * @param map
     * @param computType
     * @return
     * @throws Exception
     * chenliang
     */

    private Map<String, Object> fourRound(Map<String, Object> map, PToolSupply pToolSupply, String computType) throws Exception {
        try {
        String typename = (String) map.get("rotation");
        LOG.info("查询此条补款对应的扣款");
        //此平台下对应的机具扣款
        ProfitDeduction profitDeduction = profitDeductionService.getProfitDeductionById(pToolSupply.getDeductionId());
        //查询此条数据对应的月份润明细里的记录，中的平台码所对应的的上级
        LOG.info("查询此条补款对应的月份润平台对应的上级");
        TransProfitDetailExample transProfitDetailExample = new TransProfitDetailExample();
        TransProfitDetailExample.Criteria criteria1 = transProfitDetailExample.createCriteria();
        criteria1.andBusCodeEqualTo(pToolSupply.getBusCode());
        criteria1.andAgentIdEqualTo(pToolSupply.getAgentId());
        criteria1.andProfitDateEqualTo(pToolSupply.getProfitDate());
        List<TransProfitDetail> transProfitDetails = profitDetailMonthServiceImpl.getTransProfitDetailByBusCode(transProfitDetailExample);
        if(1!=transProfitDetails.size()){
            LOG.info("查询{}补款对应的月份润平台对应的上级失败",pToolSupply.getId());
            return null;
        }

        LOG.info("查询此条补款对应的月份润汇总");
        ProfitDetailMonthExample profitDetailMonthExample = new ProfitDetailMonthExample();
        ProfitDetailMonthExample.Criteria criteria = profitDetailMonthExample.createCriteria();
        criteria.andAgentIdEqualTo(pToolSupply.getAgentId());
        criteria.andProfitDateEqualTo(pToolSupply.getProfitDate());
        criteria.andParentAgentIdEqualTo(transProfitDetails.get(0).getParentAgentId());
        List<ProfitDetailMonth> profitDetailMonthList = profitMonthService.byProfitDetailMonth(profitDetailMonthExample);
        if(1!=profitDetailMonthList.size()){
            LOG.info("查询{}补款对应的月份润汇总失败",pToolSupply.getId());
            return null;
        }

        ProfitDetailMonth profitDetailMonth =  profitDetailMonthList.get(0);
        //上级代理商的月份润
        BigDecimal basicAmt = profitDetailMonth.getBasicsProfitAmt();
        //线下补款
        profitDeduction.setRev2(pToolSupply.getRemitAmt().toString());

        //需要上级代扣的款项
        BigDecimal upSupplyAmt   = pToolSupply.getParenterSupplyAmt();
        //更新机具扣款表
        profitDeduction.setRev3(upSupplyAmt.toString());
        profitDeduction.setActualDeductionAmt(profitDeduction.getActualDeductionAmt().add(pToolSupply.getRemitAmt()).add(upSupplyAmt));
        profitDeduction.setNotDeductionAmt(profitDeduction.getMustDeductionAmt().subtract(profitDeduction.getActualDeductionAmt()));
        //上级代扣明细
        ProfitDeduction insertup = new ProfitDeduction();
        if(pToolSupply.getParenterSupplyAmt().compareTo(BigDecimal.ZERO)==1){
            insertup.setMustDeductionAmt(pToolSupply.getToolsInvoiceAmt().subtract(pToolSupply.getRemitAmt()));
            insertup.setActualDeductionAmt(upSupplyAmt);
            insertup.setNotDeductionAmt(insertup.getMustDeductionAmt().subtract(insertup.getActualDeductionAmt()));
            insertup.setAgentPid(profitDeduction.getAgentId());
            insertup.setAgentId(profitDeduction.getAgentId());
            insertup.setAgentName(profitDeduction.getAgentName());
            insertup.setDeductionType(DeductionType.MACHINE.getType());
            insertup.setDeductionDesc(profitDeduction.getDeductionDesc());
            insertup.setDeductionDate(profitDeduction.getDeductionDate());
            insertup.setId(profitDeduction.getId());
            insertup.setRemark(typename + "上级代理商代理商代扣机具款，扣款明细：" + profitDeduction.getSourceId());
            insertup.setUserId(profitDeduction.getAgentId());
            profitDeducttionDetailService.insertDeducttionDetail(insertup);
        }
        basicAmt = basicAmt.subtract(pToolSupply.getParenterSupplyAmt());
        profitDetailMonth.setBasicsProfitAmt(basicAmt);


            //更新剩余分润
            LOG.info("更新扣款表{}",profitDetailMonth.getId());
            BigDecimal posDgRealDeductionAmt = profitDetailMonth.getPosDgRealDeductionAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getPosDgRealDeductionAmt();
            profitDetailMonth.setPosDgRealDeductionAmt(posDgRealDeductionAmt.add(insertup.getActualDeductionAmt()));
            profitMonthService.updateByPrimaryKeySelective(profitDetailMonth);



            LOG.info("更新扣款表{}",profitDeduction.getId());
            //更新扣款表
            profitDeductionService.updateProfitDeduction(profitDeduction);

        }catch (Exception e ){

            e.printStackTrace();
        }

           return null;
    }





    /**
     * 终审后，查询机具未扣款订单，通知订单系统，未扣款订单与金额
     */
    @Override
    public void otherOperate() {
        String deductDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.ISO_LOCAL_DATE).substring(0, 7).replace("-", "");
        ProfitDeduction profitDeduction = new ProfitDeduction();
        profitDeduction.setDeductionDate(deductDate);
        profitDeduction.setDeductionType(DeductionType.MACHINE.getType());
        List<ProfitDeduction> list = profitDeductionService.getProfitDeduction(profitDeduction);
        if (list != null && !list.isEmpty()) {
            List<Map<String, Object>> noticeList = new ArrayList<Map<String, Object>>(list.size());
            for (ProfitDeduction deduction : list) {
                Map<String, Object> map = new HashMap<String, Object>(8);
                ProfitDeducttionDetail detail = profitDeducttionDetailService.getProfitDeducttionDetail(deduction);
                map.put("deductTime", detail != null ? detail.getCreateDateTime() : "");
                map.put("mustDeductionAmtSum", deduction.getSumDeductionAmt().toString());
                map.put("actualDeductionAmtSum", deduction.getActualDeductionAmt().toString());
                BigDecimal notDeductionAmt = deduction.getSumDeductionAmt().subtract(deduction.getActualDeductionAmt());
                map.put("notDeductionAmt", notDeductionAmt.toString());
                map.put("detailId", deduction.getSourceId());
                map.put("srcId", deduction.getId());
                map.put("srcType", PamentSrcType.FENRUN_DIKOU.code);
                noticeList.add(map);
            }
            LOG.info("系统已经终审，通知订单系统，机具汇总款变更清算状态，通知数据：{}",JSONObject.toJSON(noticeList));
            OPdSumService.uploadStatus(noticeList, PaySign.JQ.code);
        }
    }

    @Override
    public void clearDetail() {

    }



    /*private void deduceParentAgent(ProfitDeduction profitDeduction, BigDecimal mustAmt, String computType){
        LOG.info("机具扣款流水号：{}，代理商唯一码：{}，汇总分润不足：{}",
                profitDeduction.getSourceId(), profitDeduction.getAgentId(), mustAmt);
        List<ProfitDetailMonth> profitMonth = profitMonthService.getAgentProfit(profitDeduction.getParentAgentId(),
                profitDeduction.getDeductionDate().replaceAll("-", ""), null);
        if(profitMonth != null && !profitMonth.isEmpty()){
            for (ProfitDetailMonth detail : profitMonth){
                BigDecimal parentBasicsProfitAmt = detail.getBasicsProfitAmt() == null ? BigDecimal.ZERO : detail.getBasicsProfitAmt();
                if(parentBasicsProfitAmt.compareTo(BigDecimal.ZERO) <= 0 ){
                    continue;
                } else {
                    BigDecimal deductionAmt = this.deductParentAgentProfit(profitDeduction, detail, computType, mustAmt);
                    mustAmt = mustAmt.subtract(deductionAmt);
                    LOG.info("机具扣款流水号：{}，代理商唯一码：{}，该笔机具款目前，应扣金额还剩：{}元未扣足",
                            profitDeduction.getSourceId(), profitDeduction.getAgentId(), mustAmt);
                }
            }
            try {
                if(Objects.equals(computType, "1")){
                    ProfitDeduction update = new ProfitDeduction();
                    update.setId(profitDeduction.getId());
                    update.setStagingStatus(DeductionStatus.YES_WITHHOLD.getStatus());
                    update.setActualDeductionAmt(profitDeduction.getActualDeductionAmt());
                    update.setNotDeductionAmt(mustAmt);
                    profitDeductionService.updateProfitDeduction(update);
                }
            } catch (Exception e){e.printStackTrace();}
        } else {
           try {
                profitDeduction.setNotDeductionAmt(mustAmt);
                this.updateDeductionInfo(profitDeduction, computType);
            } catch (Exception e){e.printStackTrace();}
        }
    }


    private BigDecimal deductParentAgentProfit(ProfitDeduction profitDeductionList, ProfitDetailMonth profitMonth, String computType, BigDecimal mustNotDeductionAmt) {
        BigDecimal parentBasicsProfitAmt = profitMonth.getBasicsProfitAmt();
        BigDecimal deductionAmt = BigDecimal.ZERO;
        if (parentBasicsProfitAmt.compareTo(mustNotDeductionAmt) >= 0) {
            LOG.info("机具扣款流水号：{}，代理商唯一码：{}，担保代理商：{}，担保代理商基础分润：{}，代扣金额：{}，担保代理剩余分润：{}", profitDeductionList.getSourceId(),
                    profitDeductionList.getAgentId(), profitDeductionList.getParentAgentId(), parentBasicsProfitAmt, mustNotDeductionAmt, parentBasicsProfitAmt.subtract(mustNotDeductionAmt));
            profitDeductionList.setActualDeductionAmt(profitDeductionList.getActualDeductionAmt().add(mustNotDeductionAmt));
            this.updateProfitMonth(profitMonth, mustNotDeductionAmt, profitDeductionList.getDeductionDesc());
            deductionAmt = mustNotDeductionAmt;
            profitMonth.setBasicsProfitAmt(parentBasicsProfitAmt.subtract(mustNotDeductionAmt));
        } else if(mustNotDeductionAmt.compareTo(parentBasicsProfitAmt) >= 0){
            LOG.info("机具扣款流水号：{}，代理商唯一码：{}，担保代理商：{}，担保代理商基础分润：{}，代扣金额：{}，担保代理剩余分润：{}", profitDeductionList.getSourceId(),
                    profitDeductionList.getAgentId(), profitDeductionList.getParentAgentId(), parentBasicsProfitAmt, parentBasicsProfitAmt, BigDecimal.ZERO);
            profitDeductionList.setActualDeductionAmt(profitDeductionList.getActualDeductionAmt().add(parentBasicsProfitAmt));
            this.updateProfitMonth(profitMonth, parentBasicsProfitAmt, profitDeductionList.getDeductionDesc());
            profitMonth.setBasicsProfitAmt(BigDecimal.ZERO);
            deductionAmt = parentBasicsProfitAmt;
        }
        try {
            this.insertDeductionInfo(profitDeductionList, profitMonth, computType, deductionAmt);
        } catch (Exception e){e.printStackTrace();}
        return deductionAmt;
    }

    private ProfitDetailMonth updateProfitMonth(ProfitDetailMonth profitDetailMonth, BigDecimal dudecutAmt, String paltformNo){
        if(Objects.equals(POS, paltformNo)){
            BigDecimal posMuust = profitDetailMonth.getPosDgMustDeductionAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getPosDgMustDeductionAmt();
            BigDecimal posReal = profitDetailMonth.getPosDgRealDeductionAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getPosDgRealDeductionAmt();
            profitDetailMonth.setPosDgMustDeductionAmt(posMuust.add(dudecutAmt));
            profitDetailMonth.setPosDgRealDeductionAmt(posReal.add(dudecutAmt));
            LOG.info("基础分润ID：{}，担保代理商唯一码：{}，POS机具，累加前应扣：{}，累加前实扣：{}，累加扣款金额：{}",
                    profitDetailMonth.getId(), profitDetailMonth.getAgentId(), posMuust, posReal, dudecutAmt);
            LOG.info("基础分润ID：{}，担保代理商唯一码：{}，POS机具，累加后应扣：{}，累加后实扣：{}",
                    profitDetailMonth.getId(), profitDetailMonth.getAgentId(), profitDetailMonth.getPosDgMustDeductionAmt(), profitDetailMonth.getPosDgRealDeductionAmt());
        } else if(Objects.equals(ZPOS, paltformNo)){
            BigDecimal zposMuust = profitDetailMonth.getZposDgMustDeductionAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getZposDgMustDeductionAmt();
            BigDecimal zposReal = profitDetailMonth.getZposTdRealDeductionAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getZposTdRealDeductionAmt();
            profitDetailMonth.setZposDgMustDeductionAmt(zposMuust.add(dudecutAmt));
            profitDetailMonth.setZposTdRealDeductionAmt(zposReal.add(dudecutAmt));
            LOG.info("基础分润ID：{}，担保代理商唯一码：{}，ZPOS机具，累加前应扣：{}，累加前实扣：{}，累加扣款金额：{}",
                    profitDetailMonth.getId(), profitDetailMonth.getAgentId(), zposMuust, zposReal, dudecutAmt);
            LOG.info("基础分润ID：{}，担保代理商唯一码：{}，ZPOS机具，累加后应扣：{}，累加后实扣：{}",
                    profitDetailMonth.getId(), profitDetailMonth.getAgentId(), profitDetailMonth.getZposDgMustDeductionAmt(), profitDetailMonth.getZposTdRealDeductionAmt());
        }else if(Objects.equals(RHB, paltformNo)){
            BigDecimal rhbMuust = profitDetailMonth.getRhbDgMustDeductionAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getRhbDgMustDeductionAmt();
            BigDecimal rhbReal = profitDetailMonth.getRhbDgRealDeductionAmt() == null ? BigDecimal.ZERO : profitDetailMonth.getRhbDgRealDeductionAmt();
            profitDetailMonth.setRhbDgMustDeductionAmt(rhbMuust.add(dudecutAmt));
            profitDetailMonth.setRhbDgRealDeductionAmt(rhbReal.add(dudecutAmt));
            LOG.info("基础分润ID：{}，担保代理商唯一码：{}，瑞和宝机具，累加前应扣：{}，累加前实扣：{}，累加扣款金额：{}",
                    profitDetailMonth.getId(), profitDetailMonth.getAgentId(), rhbMuust, rhbReal, dudecutAmt);
            LOG.info("基础分润ID：{}，担保代理商唯一码：{}，瑞和宝机具，累加后应扣：{}，累加后实扣：{}",
                    profitDetailMonth.getId(), profitDetailMonth.getAgentId(), profitDetailMonth.getRhbDgMustDeductionAmt(), profitDetailMonth.getRhbDgRealDeductionAmt());
        }
        return profitDetailMonth;
    }

    private void insertDeductionInfo(ProfitDeduction profitDeductionList, ProfitDetailMonth profitDetailMonth,
                                     String computType, BigDecimal deductionAmt) throws Exception {
        try {
            if(Objects.equals(computType, "1")){
                if(profitDetailMonth != null){
                    ProfitDeduction danbaoDeduct = new ProfitDeduction();
                    danbaoDeduct.setAgentId(profitDetailMonth.getAgentPid());
                    danbaoDeduct.setAgentPid(profitDetailMonth.getAgentPid());
                    danbaoDeduct.setDeductionDate(profitDeductionList.getDeductionDate());
                    danbaoDeduct.setDeductionDesc(profitDeductionList.getDeductionDesc());
                    danbaoDeduct.setDeductionType(DeductionType.MACHINE.getType());
                    danbaoDeduct.setActualDeductionAmt(deductionAmt);
                    danbaoDeduct.setMustDeductionAmt(deductionAmt);
                    danbaoDeduct.setRemark("担保代理商代扣,扣款明细:"+profitDeductionList.getSourceId());
                    danbaoDeduct.setUserId(profitDeductionList.getAgentId());
                    danbaoDeduct.setId(profitDeductionList.getId());
                    profitDeducttionDetailService.insertDeducttionDetail(danbaoDeduct);
                }
            }

            if(profitDetailMonth != null){
                ProfitDetailMonth detatil = new ProfitDetailMonth();
                detatil.setId(profitDetailMonth.getId());
                detatil.setPosDgMustDeductionAmt(profitDetailMonth.getPosDgMustDeductionAmt());
                detatil.setPosDgRealDeductionAmt(profitDetailMonth.getPosDgRealDeductionAmt());
                detatil.setZposDgMustDeductionAmt(profitDetailMonth.getZposDgMustDeductionAmt());
                detatil.setZposTdRealDeductionAmt(profitDetailMonth.getZposTdRealDeductionAmt());
                detatil.setRhbDgMustDeductionAmt(profitDetailMonth.getRhbDgMustDeductionAmt());
                detatil.setRhbDgRealDeductionAmt(profitDetailMonth.getRhbDgRealDeductionAmt());
                detatil.setBasicsProfitAmt(profitDetailMonth.getBasicsProfitAmt());
                profitDetailMonthServiceImpl.updateByPrimaryKeySelective(detatil);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("机具扣款更新失败。");
        }
    }*/
}