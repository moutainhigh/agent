package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.BusActRelMapper;
import com.ryx.credit.dao.order.TerminalTransferDetailMapper;
import com.ryx.credit.dao.order.TerminalTransferMapper;
import com.ryx.credit.machine.vo.ChangeActMachineVo;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.TerminalTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2018/12/20 10:57
 * @Param
 * @return
 **/
@Service("terminalTransferService")
public class TerminalTransferServiceImpl implements TerminalTransferService {

    private static Logger log = LoggerFactory.getLogger(TerminalTransferServiceImpl.class);
    @Autowired
    private TerminalTransferMapper terminalTransferMapper;
    @Autowired
    private TerminalTransferDetailMapper terminalTransferDetailMapper;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;



    @Override
    public PageInfo terminalTransferList(TerminalTransfer terminalTransfer, Page page) {

        TerminalTransferExample example = new TerminalTransferExample();
        TerminalTransferExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        example.setPage(page);
        List<TerminalTransfer> terminalTransferList = terminalTransferMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(terminalTransferList);
        pageInfo.setTotal((int)terminalTransferMapper.countByExample(example));
        return pageInfo;
    }

    @Override
    public PageInfo terminalTransferDetailList(TerminalTransferDetail terminalTransferDetail, Page page) {

        Map<String,Object> reqMap = new HashMap<>();
        List<Map<String,Object>> terminalTransferList = terminalTransferDetailMapper.selectTerminalTransferDetailList(reqMap,page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(terminalTransferList);
        pageInfo.setTotal(terminalTransferDetailMapper.selectTerminalTransferDetailCount(reqMap));
        return pageInfo;
    }

    /**
     *
     * saveFlag 1暂存2提交审批
     * @param terminalTransfer
     * @param terminalTransferDetailList
     * @param cuser
     * @param saveFlag
     * @return
     * @throws Exception
     */
    public AgentResult saveTerminalTransfer(TerminalTransfer terminalTransfer,List<TerminalTransferDetail> terminalTransferDetailList, String cuser,String agentId,String saveFlag)throws Exception{

        if (StringUtils.isBlank(cuser)) {
            log.info("终端划拨提交审批,操作用户为空:{}", cuser);
            return AgentResult.fail("终端划拨审批中，操作用户为空");
        }
        try {
            if(saveFlag.equals(SaveFlag.TJSP.getValue())){
                terminalTransfer.setReviewStatus(AgStatus.Approving.status);
            }else{
                terminalTransfer.setReviewStatus(AgStatus.Create.status);
            }
            String terminalTransferId = idService.genId(TabId.O_TERMINAL_TRANSFER);
            terminalTransfer.setId(terminalTransferId);
            Date date = new Date();
            terminalTransfer.setcTime(date);
            terminalTransfer.setuTime(date);
            terminalTransfer.setcUser(cuser);
            terminalTransfer.setcUser(cuser);
            terminalTransfer.setStatus(Status.STATUS_1.status);
            terminalTransfer.setVersion(Status.STATUS_1.status);
            int i = terminalTransferMapper.insert(terminalTransfer);
            if (1 != i) {
                log.info("终端划拨提交审批，更新订单基本信息失败:{}", cuser);
                throw new MessageException("终端划拨提交审批，更新终端划拨基本信息失败");
            }
            for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailList) {
                if(StringUtils.isBlank(terminalTransferDetail.getGoalOrgId()) || StringUtils.isBlank(terminalTransferDetail.getOriginalOrgId())){
                    throw new MessageException("缺少参数");
                }
                AgentBusInfoExample originalExample = new AgentBusInfoExample();
                AgentBusInfoExample.Criteria originalCriteria = originalExample.createCriteria();
                originalCriteria.andStatusEqualTo(Status.STATUS_1.status);
                originalCriteria.andBusNumEqualTo(terminalTransferDetail.getOriginalOrgId());
                List<AgentBusInfo> originalAgentBusInfos = agentBusInfoMapper.selectByExample(originalExample);
                if(originalAgentBusInfos.size()!=1){
                    throw new MessageException("原机构数据有误");
                }
                AgentBusInfoExample goalExample = new AgentBusInfoExample();
                AgentBusInfoExample.Criteria goalCriteria = goalExample.createCriteria();
                goalCriteria.andStatusEqualTo(Status.STATUS_1.status);
                goalCriteria.andBusNumEqualTo(terminalTransferDetail.getOriginalOrgId());
                List<AgentBusInfo> goalBusInfos = agentBusInfoMapper.selectByExample(goalExample);
                if(goalBusInfos.size()!=1){
                    throw new MessageException("原机构数据有误");
                }
                AgentBusInfo originalAgentBusInfo = originalAgentBusInfos.get(0);
                AgentBusInfo goalAgentBusInfo = goalBusInfos.get(0);

                terminalTransferDetail.setId(idService.genId(TabId.O_TERMINAL_TRANSFER_DETAIL));
                terminalTransferDetail.setTerminalTransferId(terminalTransferId);
                terminalTransferDetail.setcUser(cuser);
                terminalTransferDetail.setuUser(cuser);
                terminalTransferDetail.setcTime(date);
                terminalTransferDetail.setuTime(date);
                terminalTransferDetail.setStatus(Status.STATUS_1.status);
                terminalTransferDetail.setVersion(Status.STATUS_1.status);
                terminalTransferDetail.setAgentId(agentId);
                terminalTransferDetail.setAdjustStatus(AdjustStatus.WTZ.getValue());
                terminalTransferDetail.setOriginalBusId(originalAgentBusInfo.getId());
                terminalTransferDetail.setGoalBusId(goalAgentBusInfo.getId());
                terminalTransferDetailMapper.insert(terminalTransferDetail);
            }
            return AgentResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("新增失败");
        }
    }


    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult startTerminalTransferActivity(String id, String cuser, String agentId) throws Exception {

        //启动审批
        String proce = activityService.createDeloyFlow(null, "agentTerminal", null, null, null);
        if (proce == null) {
            log.info("终端划拨提交审批，审批流启动失败{}:{}", id, cuser);
            throw new MessageException("审批流启动失败!");
        }
        //代理商业务视频关系
        BusActRel record = new BusActRel();
        record.setBusId(id);
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(cuser);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.COMPENSATE.name());
        record.setActivStatus(AgStatus.Approving.name());
        record.setAgentId(agentId);
        Agent agent = agentMapper.selectByPrimaryKey(agentId);
        if(null!=agent)
            record.setAgentName(agent.getAgName());
        if (1 != busActRelMapper.insertSelective(record)) {
            log.info("订单提交审批，启动审批异常，添加审批关系失败{}:{}", id, proce);
            throw new MessageException("审批流启动失败:添加审批关系失败");
        }
        return AgentResult.ok();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult approvalTerminalTransferTask(AgentVo agentVo, String userId) throws Exception{
        try {
            if(agentVo.getApprovalResult().equals("pass")){

            }
            AgentResult result = agentEnterService.completeTaskEnterActivity(agentVo,userId);
            if(!result.isOK()){
                log.error(result.getMsg());
                throw new ProcessException("工作流处理任务异常");
            }
        } catch (ProcessException e) {
            e.printStackTrace();
            throw new ProcessException("catch工作流处理任务异常!");
        }
        return AgentResult.ok();
    }


    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult compressTerminalTransferActivity(String proIns, BigDecimal agStatus)throws Exception{

        BusActRelExample example = new BusActRelExample();
        example.or().andActivIdEqualTo(proIns).andStatusEqualTo(Status.STATUS_1.status).andActivStatusEqualTo(AgStatus.Approving.name());
        List<BusActRel> list = busActRelMapper.selectByExample(example);
        if (list.size() != 1) {
            log.info("审批任务结束{}{}，未找到审批中的审批和数据关系", proIns, agStatus);
            throw new MessageException("审批和数据关系有误");
        }
        BusActRel busActRel = list.get(0);
        TerminalTransfer terminalTransfer = terminalTransferMapper.selectByPrimaryKey(busActRel.getBusId());
        terminalTransfer.setReviewStatus(agStatus);
        terminalTransfer.setuTime(new Date());
        int i = terminalTransferMapper.updateByPrimaryKeySelective(terminalTransfer);
        if(i!=1) {
            log.info("审批任务结束{}{}，终端划拨更新失败", proIns, agStatus);
            throw new MessageException("终端划拨更新失败");
        }
        return AgentResult.ok();
    }
}