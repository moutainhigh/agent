package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.common.util.agentUtil.StageUtil;
import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.dao.agent.CapitalMapper;
import com.ryx.credit.dao.order.OPaymentDetailMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.OPaymentDetail;
import com.ryx.credit.pojo.admin.vo.CapitalVo;
import com.ryx.credit.service.agent.AccountPaidItemService;
import com.ryx.credit.service.agent.AgentDataHistoryService;
import com.ryx.credit.service.agent.CapitalFlowService;
import com.ryx.credit.service.dict.IdService;
import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.management.resources.agent;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 缴纳款项服务层
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/5/23 14:20
 */
@Service("accountPaidItemService")
public class AccountPaidItemServiceImpl implements AccountPaidItemService {

    private static Logger log = LoggerFactory.getLogger(AccountPaidItemServiceImpl.class);
    @Autowired
    private CapitalMapper capitalMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private AttachmentRelMapper attachmentRelMapper;
    @Autowired
    private AgentDataHistoryService agentDataHistoryService;
    @Autowired
    private OPaymentDetailMapper oPaymentDetailMapper;
    @Autowired
    private CapitalFlowService capitalFlowService;
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    public AgentResult insertAccountPaid(Capital capital,List<String> fileIdList,String cUser,Boolean isPass,String saveType)throws Exception{

        AgentResult result = new AgentResult(500,"参数错误","");
        if(StringUtils.isBlank(cUser)){
            return result;
        }

        if(StringUtils.isBlank(capital.getcType()) && !"1".equals(saveType)){
            return result;
        }
        capital.setcIsin(Status.STATUS_0.status);
        capital.setStatus(Status.STATUS_1.status);
        capital.setVersion(Status.STATUS_1.status);
        capital.setId(idService.genId(TabId.a_capital));
        Date nowDate = new Date();
        capital.setcTime(nowDate);
        capital.setcUtime(nowDate);
        capital.setCloReviewStatus(AgStatus.Create.status);
        capital.setcInAmount(capital.getcInAmount()==null?Status.STATUS_0.status:capital.getcInAmount());
        capital.setFreezeAmt(Status.STATUS_0.status);
        if(!isPass){
            if(PayType.YHHK.code.equals(capital.getcPayType()) && StringUtils.isNotBlank(capital.getcAmount().toString())) {
                capital.setcFqInAmount(capital.getcAmount());
            }else if(PayType.FRDK.code.equals(capital.getcPayType())  && StringUtils.isNotBlank(capital.getcAmount().toString())){
                capital.setcFqInAmount(Status.STATUS_0.status);
                if(null==capital.getcFqCount()){
                    throw new ProcessException("分期期数不能为空");
                }
            }
        }else{
            if(PayType.YHHK.code.equals(capital.getcPayType())) {
                capital.setcFqInAmount(capital.getcInAmount());
            }else if(PayType.FRDK.code.equals(capital.getcPayType())){
                capital.setcFqInAmount(Status.STATUS_0.status);
                if(null==capital.getcFqCount()){
                    throw new ProcessException("分期期数不能为空");
                }
            }
        }

        int insertResult = capitalMapper.insertSelective(capital);
        if(1==insertResult){
            if(fileIdList!=null) {
                for (String fileId : fileIdList) {
                    AttachmentRel record = new AttachmentRel();
                    record.setAttId(fileId);
                    record.setSrcId(capital.getId());
                    record.setcUser(cUser);
                    record.setcTime(nowDate);
                    record.setStatus(Status.STATUS_1.status);
                    record.setBusType(AttachmentRelType.Capital.name());
                    record.setId(idService.genId(TabId.a_attachment_rel));
                    int i = attachmentRelMapper.insertSelective(record);
                    if (1 != i) {
                        log.info("insertAccountPaid添加缴纳款项附件关系失败");
                        return new AgentResult(500, "系统异常", "");
                    }
                }
            }
            //记录历史缴款
            if(!agentDataHistoryService.saveDataHistory(capital, DataHistoryType.PAYMENT.getValue()).isOK()){
                throw new ProcessException("历史数据保存失败");
            }
            log.info("insertAccountPaid缴纳款项添加:成功");
            return AgentResult.ok();
        }
        throw new ProcessException("缴款信息处理失败");
    }

    @Override
    public int removeAccountPaid(String id) {
        AttachmentRel attachmentRel = attachmentRelMapper.selectByPrimaryKey(id);
        if(attachmentRel!=null){
            attachmentRel.setStatus(Status.STATUS_0.status);
            return attachmentRelMapper.updateByPrimaryKeySelective(attachmentRel);
        }
        return 0;
    }

    @Override
    public List<Capital> queryCap(String agentId, String type, BigDecimal isIn, BigDecimal cloReviewStatus) {
        CapitalExample example = new CapitalExample();
        CapitalExample.Criteria c =  example.or().andStatusEqualTo(Status.STATUS_1.status);
        if(StringUtils.isNotEmpty(agentId)){
            c.andCAgentIdEqualTo(agentId);
        }
        if(StringUtils.isNotEmpty(type)){
            c.andCTypeEqualTo(type);
        }
        if(isIn!=null){
            c.andCIsinEqualTo(isIn);
        }
        if(cloReviewStatus!=null){
            c.andCloReviewStatusEqualTo(cloReviewStatus);
        }
        return  capitalMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public ResultVO updateListCapitalVo(List<CapitalVo> capitalVoList, Agent agent,String userId,Boolean isPass) throws Exception {
        try {
            if(agent==null)throw new ProcessException("代理商信息不能为空");
            for (CapitalVo capitalVo : capitalVoList) {
                capitalVo.setcUser(agent.getcUser());
                capitalVo.setcAgentId(agent.getId());
                if(StringUtils.isEmpty(capitalVo.getId())) {
                    //直接新曾
                    AgentResult result =   insertAccountPaid(capitalVo, capitalVo.getCapitalTableFile(), userId,isPass,null);
                    //加入流水
                    capitalFlowService.insertCapitalFlow(capitalVo, BigDecimal.ZERO,capitalVo.getSrcId(),capitalVo.getSrcRemark());
                    if(!result.isOK())throw new ProcessException("新增收款信息失败");
                }else{

                    Capital db_capital = capitalMapper.selectByPrimaryKey(capitalVo.getId());
                    db_capital.setcAgentId(agent.getId());
                    db_capital.setcType(capitalVo.getcType());
                    db_capital.setcAmount(capitalVo.getcAmount());
                    db_capital.setcSrc(capitalVo.getcSrc());
                    db_capital.setcPaytime(capitalVo.getcPaytime());
                    db_capital.setcUser(capitalVo.getcUser());
                    db_capital.setRemark(capitalVo.getRemark());
                    db_capital.setStatus(capitalVo.getStatus());

                    db_capital.setcFqCount(capitalVo.getcFqCount());
                    db_capital.setcPayuser(capitalVo.getcPayuser());
                    db_capital.setcPaytime(capitalVo.getcPaytime());
                    db_capital.setcInCom(capitalVo.getcInCom());
                    db_capital.setcPayType(capitalVo.getcPayType());

                    if(!isPass){
                        if(PayType.YHHK.code.equals(db_capital.getcPayType())) {
                            db_capital.setcFqInAmount(db_capital.getcAmount());
                        }else{
                            db_capital.setcFqInAmount(Status.STATUS_0.status);
                        }
                    }else{
                        if(PayType.YHHK.code.equals(db_capital.getcPayType())) {
                            db_capital.setcFqInAmount(db_capital.getcInAmount());
                        }
                    }

                    if(1!=capitalMapper.updateByPrimaryKeySelective(db_capital)){
                        throw new ProcessException("更新收款信息失败");
                    }else{
                        if(!agentDataHistoryService.saveDataHistory(db_capital,db_capital.getId(), DataHistoryType.PAYMENT.getValue(),userId,db_capital.getVersion()).isOK()){
                            throw new ProcessException("更新收款信息失败");
                        }
                    }

                    //删除老的附件
                    AttachmentRelExample example = new AttachmentRelExample();
                    example.or().andBusTypeEqualTo(AttachmentRelType.Capital.name()).andSrcIdEqualTo(db_capital.getId()).andStatusEqualTo(Status.STATUS_1.status);
                    List<AttachmentRel> list = attachmentRelMapper.selectByExample(example);
                    for (AttachmentRel attachmentRel : list) {
                        attachmentRel.setStatus(Status.STATUS_0.status);
                        int i = attachmentRelMapper.updateByPrimaryKeySelective(attachmentRel);
                        if (1 != i) {
                            log.info("修改缴纳款项附件关系失败");
                            throw new ProcessException("更新收款信息失败");
                        }
                    }

                    //添加新的附件
                    List<String> fileIdList = capitalVo.getCapitalTableFile();
                    if(fileIdList!=null) {
                        for (String fileId : fileIdList) {
                            AttachmentRel record = new AttachmentRel();
                            record.setAttId(fileId);
                            record.setSrcId(db_capital.getId());
                            record.setcUser(db_capital.getcUser());
                            record.setcTime(Calendar.getInstance().getTime());
                            record.setStatus(Status.STATUS_1.status);
                            record.setBusType(AttachmentRelType.Capital.name());
                            record.setId(idService.genId(TabId.a_attachment_rel));
                            int i = attachmentRelMapper.insertSelective(record);
                            if (1 != i) {
                                log.info("修改缴纳款项附件关系失败");
                                throw new ProcessException("更新收款信息失败");
                            }
                        }
                    }

                }

            }
            return ResultVO.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public int update(Capital capital){
        return capitalMapper.updateByPrimaryKeySelective(capital);
    }

    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    @Override
    public AgentResult capitalFq(Capital capital)throws Exception{
        if(capital==null)return AgentResult.fail();
        if(capital.getcFqCount()==null || capital.getcFqCount().intValue()<=0)return AgentResult.fail();
        Capital db_capital = capitalMapper.selectByPrimaryKey(capital.getId());
        if(db_capital==null)return AgentResult.fail();

        if(PayType.FRDK.code.equals(db_capital.getcPayType())) {
            Calendar temp = Calendar.getInstance();
            String batchCode =temp.getTime().getTime()+"";
            //分期数据
            List<Map> FKFQ_data = StageUtil.stageOrder(
                    db_capital.getcAmount(),
                    db_capital.getcFqCount().intValue(),
                    new Date(), temp.get(Calendar.DAY_OF_MONTH));

            //明细处理
            for (Map datum : FKFQ_data) {
                OPaymentDetail record = new OPaymentDetail();
                record.setId(idService.genId(TabId.o_payment_detail));
                record.setBatchCode(batchCode);
                record.setPaymentId(db_capital.getId());
                record.setPaymentType(PamentIdType.ORDER_BZJ.code);
                record.setOrderId(db_capital.getId());
                record.setPayType(PaymentType.FRFQ.code);
                record.setPayAmount((BigDecimal) datum.get("item"));
                record.setRealPayAmount(BigDecimal.ZERO);
                record.setPlanPayTime((Date) datum.get("date"));
                record.setPlanNum((BigDecimal) datum.get("count"));
                record.setAgentId(db_capital.getcAgentId());
                record.setPaymentStatus(PaymentStatus.DF.code);
                record.setcUser(db_capital.getcUser());
                record.setcDate(temp.getTime());
                record.setStatus(Status.STATUS_1.status);
                record.setVersion(Status.STATUS_1.status);
                if (1 != oPaymentDetailMapper.insert(record)) {
                    log.info("保证金分期抵扣分润:明细生成失败:capitalID:{}",
                            db_capital.getId());
                    throw new MessageException("保证金分期抵扣分润异常");
                }
            }
        }

        return AgentResult.ok();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public void updateListCapitalVo(List<CapitalVo> capitalVoList) throws Exception {
        try {
            for (CapitalVo capitalVo : capitalVoList) {
                String agentId="";
                String  actid="";
                String cUser="";
                boolean isPass=false;
                CapitalExample capitalExample = new CapitalExample();
                CapitalExample.Criteria criteria = capitalExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andIdEqualTo(capitalVo.getId());
                List<Capital> capitals = capitalMapper.selectByExample(capitalExample);
                if(null!=capitals && capitals.size()>0){
                    for (Capital capital : capitals) {
                        if (null==capital.getcAgentId()){
                            agentId=capital.getcAgentId();
                        }
                        if(StringUtils.isNotBlank(capital.getActivId())){
                            actid=capital.getActivId();
                        }
                        if (StringUtils.isNotBlank(capital.getcUser())){
                            cUser=capital.getcUser();
                        }
                    }
                }


                capitalVo.setcUser(cUser);
                capitalVo.setcAgentId(agentId);
                capitalVo.setActivId(actid);
                if(StringUtils.isEmpty(capitalVo.getId())) {
                    //直接新曾
                    AgentResult result =   insertAccountPaid(capitalVo, capitalVo.getCapitalTableFile(), cUser,isPass,null);
                    //加入流水
                    capitalFlowService.insertCapitalFlow(capitalVo, BigDecimal.ZERO,capitalVo.getSrcId(),capitalVo.getSrcRemark());
                    if(!result.isOK())throw new MessageException("新增缴纳款项信息失败");
                }else{

                    Capital db_capital = capitalMapper.selectByPrimaryKey(capitalVo.getId());
                    db_capital.setcAgentId(cUser);
                    db_capital.setcType(capitalVo.getcType());
                    db_capital.setcAmount(capitalVo.getcAmount());
                    db_capital.setcSrc(capitalVo.getcSrc());
                    db_capital.setcPaytime(capitalVo.getcPaytime());
                    db_capital.setcUser(capitalVo.getcUser());
                    db_capital.setRemark(capitalVo.getRemark());
                    db_capital.setStatus(capitalVo.getStatus());

                    db_capital.setcFqCount(capitalVo.getcFqCount());
                    db_capital.setcPayuser(capitalVo.getcPayuser());
                    db_capital.setcPaytime(capitalVo.getcPaytime());
                    db_capital.setcInCom(capitalVo.getcInCom());
                    db_capital.setcPayType(capitalVo.getcPayType());

                    if(!isPass){
                        if(PayType.YHHK.code.equals(db_capital.getcPayType())) {
                            db_capital.setcFqInAmount(db_capital.getcAmount());
                        }else{
                            db_capital.setcFqInAmount(Status.STATUS_0.status);
                        }
                    }else{
                        if(PayType.YHHK.code.equals(db_capital.getcPayType())) {
                            db_capital.setcFqInAmount(db_capital.getcInAmount());
                        }
                    }

                    if(1!=capitalMapper.updateByPrimaryKeySelective(db_capital)){
                        throw new MessageException("更新缴纳款项信息失败");
                    }else{
                        if(!agentDataHistoryService.saveDataHistory(db_capital,db_capital.getId(), DataHistoryType.PAYMENT.getValue(),cUser,db_capital.getVersion()).isOK()){
                            throw new MessageException("更新缴纳款项信息失败");
                        }
                    }

                    //删除老的附件
                    AttachmentRelExample example = new AttachmentRelExample();
                    example.or().andBusTypeEqualTo(AttachmentRelType.Capital.name()).andSrcIdEqualTo(db_capital.getId()).andStatusEqualTo(Status.STATUS_1.status);
                    List<AttachmentRel> list = attachmentRelMapper.selectByExample(example);
                    for (AttachmentRel attachmentRel : list) {
                        attachmentRel.setStatus(Status.STATUS_0.status);
                        int i = attachmentRelMapper.updateByPrimaryKeySelective(attachmentRel);
                        if (1 != i) {
                            log.info("修改缴纳款项附件关系失败");
                            throw new MessageException("更新缴纳款项信息失败");
                        }
                    }

                    //添加新的附件
                    List<String> fileIdList = capitalVo.getCapitalTableFile();
                    if(fileIdList!=null) {
                        for (String fileId : fileIdList) {
                            AttachmentRel record = new AttachmentRel();
                            record.setAttId(fileId);
                            record.setSrcId(db_capital.getId());
                            record.setcUser(db_capital.getcUser());
                            record.setcTime(Calendar.getInstance().getTime());
                            record.setStatus(Status.STATUS_1.status);
                            record.setBusType(AttachmentRelType.Capital.name());
                            record.setId(idService.genId(TabId.a_attachment_rel));
                            int i = attachmentRelMapper.insertSelective(record);
                            if (1 != i) {
                                log.info("修改缴纳款项附件关系失败");
                                throw new MessageException("更新缴纳款项信息失败");
                            }
                        }
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
