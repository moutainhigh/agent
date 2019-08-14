package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.dao.agent.AgentContractMapper;
import com.ryx.credit.dao.agent.AssProtoColMapper;
import com.ryx.credit.dao.agent.AttachmentMapper;
import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentContractVo;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentAssProtocolService;
import com.ryx.credit.service.agent.AgentContractService;
import com.ryx.credit.service.agent.AgentDataHistoryService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.management.resources.agent;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by cx on 2018/5/22.
 */
@Service("agentContractService")
public class AgentContractServiceImpl implements AgentContractService {

    private static Logger logger = LoggerFactory.getLogger(AgentContractServiceImpl.class);

    @Autowired
    private IdService idService;

    @Autowired
    private AgentContractMapper agentContractMapper;

    @Autowired
    private AttachmentRelMapper attachmentRelMapper;

    @Autowired
    private DictOptionsService dictOptionsService;

    @Autowired
    private AgentDataHistoryService agentDataHistoryService;

    @Autowired
    private AssProtoColMapper assProtoColMapper;

    @Autowired
    private AgentAssProtocolService agentAssProtocolService;

    @Autowired
    private IUserService iUserService;
    @Autowired
    private AgentEnterService agentEnterService;

    @Autowired
    private AttachmentMapper attachmentMapper;

    /**
     * 获取合同类型
     *
     * @return
     */
    public List<Dict> queryContractType() {
        return dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.CONTRACT_TYPE.name());
    }


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentContract insertAgentContract(AgentContract contract, List<String> attr,String userId,String saveType) throws ProcessException {
        if (contract == null) {
            logger.info("代理商合同添加:{}", "合同信息为空");
            throw new ProcessException("合同信息为空");
        }
        if (StringUtils.isEmpty(contract.getAgentId())) {
            logger.info("代理商合同添加:{}", "代理商id不能为空");
            throw new ProcessException("合同代理商信息不能为空");
        }
//        if (StringUtils.isEmpty(contract.getContNum())) {
//            logger.info("代理商合同添加:{}", "合同编号不能为空");
//            throw new ProcessException("合同编号不能为空");
//        }
        if (null == contract.getContType() && !"1".equals(saveType)) {
            logger.info("代理商合同添加:{}", "合同类型不能为空");
            throw new ProcessException("合同类型不能为空");
        }
//        if (null == contract.getContDate()) {
//            logger.info("代理商合同添加:{}", "签约时间不能为空");
//            throw new ProcessException("签约时间不能为空");
//        }
//        if (null == contract.getContEndDate()) {
//            logger.info("代理商合同添加:{}", "签约到期时间不能为空");
//            throw new ProcessException("签约到期时间不能为空");
//        }
//        if (StringUtils.isEmpty(contract.getcUser())) {
//            logger.info("代理商合同添加:{}", "操作人不能为空");
//            throw new ProcessException("操作人不能为空");
//        }

        Date date = Calendar.getInstance().getTime();
        contract.setcUser(userId);
        contract.setStatus(Status.STATUS_1.status);
        contract.setVersion(Status.STATUS_1.status);
        contract.setCloReviewStatus(AgStatus.Create.status);
        contract.setcTime(date);
        contract.setcUtime(date);
        contract.setId(idService.genId(TabId.a_agent_contract));
        if (1 == agentContractMapper.insertSelective(contract)) {
            if (attr != null) {
                for (String s : attr) {
                    if (StringUtils.isEmpty(s)) continue;
                    AttachmentRel record = new AttachmentRel();
                    record.setAttId(s);
                    record.setSrcId(contract.getId());
                    record.setcUser(contract.getcUser());
                    record.setcTime(contract.getcTime());
                    record.setStatus(Status.STATUS_1.status);
                    record.setBusType(AttachmentRelType.Contract.name());
                    record.setId(idService.genId(TabId.a_attachment_rel));
                    if (1 != attachmentRelMapper.insertSelective(record)) {
                        logger.info("代理商合同添加:{}", "添加合同附件关系失败");
                        throw new ProcessException("添加合同附件关系失败");
                    }
                }
            }
            logger.info("代理商合同添加:成功");

            //记录合同历史
            if(!agentDataHistoryService.saveDataHistory(contract,DataHistoryType.CONTRACT.getValue()).isOK()){
                throw new ProcessException("添加代理商合同失败,请重试");
            }
            return contract;
        }
        logger.info("代理商合同添加:{}", "添加代理商合同失败");
        throw new ProcessException("添加代理商合同失败");
    }


    @Override
    public int removeAgentContract(String id) {
        AgentContract contract = agentContractMapper.selectByPrimaryKey(id);
        if (contract != null) {
            contract.setStatus(Status.STATUS_0.status);
            return agentContractMapper.updateByPrimaryKeySelective(contract);
        }
        return 0;
    }


    @Override
    public List<AgentContract> queryAgentContract(String agentId, String contractId, BigDecimal approveStatus) {
        AgentContractExample example = new AgentContractExample();
        AgentContractExample.Criteria c = example.or().andStatusEqualTo(Status.STATUS_1.status);
        if (StringUtils.isNotEmpty(agentId)) {
            c.andAgentIdEqualTo(agentId);
        }
        if (StringUtils.isNotEmpty(contractId)) {
            c.andIdEqualTo(contractId);
        }
        if (null != approveStatus) {
            c.andCloReviewStatusEqualTo(approveStatus);
        }
        return agentContractMapper.selectByExample(example);
    }


    @Override
    public int update(AgentContract a) {
        return agentContractMapper.updateByPrimaryKeySelective(a);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO updateAgentContractVo(List<AgentContractVo> volist, Agent agent,String userId) {
        try {
            if (agent == null) throw new ProcessException("代理商信息不能为空");
            for (AgentContractVo agentContractVo : volist) {
                agentContractVo.setcUser(agent.getcUser());
                agentContractVo.setAgentId(agent.getId());
                if (StringUtils.isEmpty(agentContractVo.getId())) {
                    //直接新增
                    AgentContract result = insertAgentContract(agentContractVo, agentContractVo.getContractTableFile(),userId,null);
                    if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(agentContractVo.getAgentAssProtocol())){
                        AssProtoColRel rel = new AssProtoColRel();
                        rel.setAgentBusinfoId(result.getId());
                        rel.setAssProtocolId(agentContractVo.getAgentAssProtocol());
                        AssProtoCol assProtoCol = assProtoColMapper.selectByPrimaryKey(agentContractVo.getAgentAssProtocol());
                        if(StringUtils.isNotBlank(agentContractVo.getProtocolRuleValue())){
                            String ruleReplace = assProtoCol.getProtocolRule().replace("{}", agentContractVo.getProtocolRuleValue());
                            rel.setProtocolRule(ruleReplace);
                        }else{
                            rel.setProtocolRule(assProtoCol.getProtocolRule());
                        }
                        rel.setProtocolRuleValue(agentContractVo.getProtocolRuleValue());
                        if(1!=agentAssProtocolService.addProtocolRel(rel,agent.getcUser())){
                            throw new ProcessException("合同分管协议添加失败");
                        }
                    }
                    logger.info("代理商合同添加:{}{}", "添加代理商合同成功", result.getId());
                } else {
                    AgentContract db_AgentContract = agentContractMapper.selectByPrimaryKey(agentContractVo.getId());
                    db_AgentContract.setAgentId(agent.getId());
                    db_AgentContract.setContNum(agentContractVo.getContNum());
                    db_AgentContract.setContType(agentContractVo.getContType());
                    db_AgentContract.setContDate(agentContractVo.getContDate());
                    db_AgentContract.setContEndDate(agentContractVo.getContEndDate());
                    db_AgentContract.setRemark(agentContractVo.getRemark());
                    db_AgentContract.setcUser(agentContractVo.getcUser());
                    db_AgentContract.setStatus(agentContractVo.getStatus());
                    db_AgentContract.setAppendAgree(agentContractVo.getAppendAgree());
                    if (1 != agentContractMapper.updateByPrimaryKeySelective(db_AgentContract)) {
                        throw new ProcessException("更新代理商合同失败");
                    }else{
                        //记录历史
                        if(!agentDataHistoryService.saveDataHistory(db_AgentContract,db_AgentContract.getId(), DataHistoryType.CONTRACT.getValue(),userId,db_AgentContract.getVersion()).isOK()){
                            throw new ProcessException("更新代理商合同失败");
                        }
                    }

                    //更新分管协议
                    if(StringUtils.isNotBlank(agentContractVo.getAgentAssProtocol())){
                        List<AssProtoColRel>  rels =agentAssProtocolService.queryProtoColByBusIds(Arrays.asList(db_AgentContract.getId()));
                        for (AssProtoColRel rel : rels) {
                            rel.setStatus(Status.STATUS_0.status);
                            if(1!=agentAssProtocolService.updateAssProtoColRel(rel)){
                                throw new ProcessException("业务分管协议更新失败");
                            }
                        }
                        AssProtoColRel rel = new AssProtoColRel();
                        rel.setAgentBusinfoId(db_AgentContract.getId());
                        rel.setAssProtocolId(agentContractVo.getAgentAssProtocol());
                        AssProtoCol assProtoCol = assProtoColMapper.selectByPrimaryKey(agentContractVo.getAgentAssProtocol());
                        if(StringUtils.isNotBlank(agentContractVo.getProtocolRuleValue())){
                            String ruleReplace = assProtoCol.getProtocolRule().replace("{}", agentContractVo.getProtocolRuleValue());
                            rel.setProtocolRule(ruleReplace);
                        }else{
                            rel.setProtocolRule(assProtoCol.getProtocolRule());
                        }
                        rel.setProtocolRuleValue(agentContractVo.getProtocolRuleValue());
                        if(1!=agentAssProtocolService.addProtocolRel(rel,agent.getcUser())){
                            throw new ProcessException("业务分管协议添加失败");
                        }
                    }else{
                        //删除分管协议
                        List<AssProtoColRel>  rels = agentAssProtocolService.queryProtoColByBusIds(Arrays.asList(db_AgentContract.getId()));
                        for (AssProtoColRel rel : rels) {
                            rel.setStatus(Status.STATUS_0.status);
                            if(1!=agentAssProtocolService.updateAssProtoColRel(rel)){
                                throw new ProcessException("业务分管协议更新失败");
                            }
                        }
                    }
                    //删除老的附件
                    AttachmentRelExample example = new AttachmentRelExample();
                    example.or().andBusTypeEqualTo(AttachmentRelType.Contract.name()).andSrcIdEqualTo(db_AgentContract.getId()).andStatusEqualTo(Status.STATUS_1.status);
                    List<AttachmentRel> list = attachmentRelMapper.selectByExample(example);
                    for (AttachmentRel attachmentRel : list) {
                        attachmentRel.setStatus(Status.STATUS_0.status);
                        int i = attachmentRelMapper.updateByPrimaryKeySelective(attachmentRel);
                        if (1 != i) {
                            logger.info("修改合同附件关系失败{}", attachmentRel.getId());
                            throw new ProcessException("更新合同信息失败");
                        }
                    }

                    //添加新的附件
                    List<String> fileIdList = agentContractVo.getContractTableFile();
                    if (fileIdList != null) {
                        for (String fileId : fileIdList) {
                            AttachmentRel record = new AttachmentRel();
                            record.setAttId(fileId);
                            record.setSrcId(db_AgentContract.getId());
                            record.setcUser(db_AgentContract.getcUser());
                            record.setcTime(Calendar.getInstance().getTime());
                            record.setStatus(Status.STATUS_1.status);
                            record.setBusType(AttachmentRelType.Contract.name());
                            record.setId(idService.genId(TabId.a_attachment_rel));
                            int i = attachmentRelMapper.insertSelective(record);
                            if (1 != i) {
                                logger.info("合同附件关系失败");
                                throw new ProcessException("更新合同失败");
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
    public PageInfo getAgentContractList(Page page, Map map, Long userId) {
        List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
        if (orgCodeRes==null && orgCodeRes.size()!=1) {
            return null;
        }
        Map<String, Object> stringObjectMap = orgCodeRes.get(0);
        String orgId = String.valueOf(stringObjectMap.get("ORGID"));
        String organizationCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
        map.put("orgId", orgId);
        map.put("userId", userId);
        map.put("organizationCode", organizationCode);
        Map startPar = agentEnterService.startPar(String.valueOf(userId));
        if (startPar != null) {
            map.put("all", "true");
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(agentContractMapper.getAgentContractList(map, page));
        pageInfo.setTotal(agentContractMapper.getAgentContractCount(map));
        return pageInfo;
    }

    @Override
    public List<AgentContract> queryContract(String proIns) {
        if (StringUtils.isBlank(proIns)){
            new ArrayList<AgentContract>();
        }
        AgentContractExample agentContractExample = new AgentContractExample();
        AgentContractExample.Criteria criteria = agentContractExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andActivIdEqualTo(proIns);
        List<AgentContract> agentContracts = agentContractMapper.selectByExample(agentContractExample);
        if (null!=agentContracts && agentContracts.size()>0){
            for (AgentContract agentContract : agentContracts) {
                agentContract.setAttachmentList(attachmentMapper.accessoryQuery(agentContract.getId(), AttachmentRelType.Contract.name()));
                List<Map<String, Object>> maps = assProtoColMapper.selectByBusInfoId(agentContract.getId());
                if(null==maps){
                    continue;
                }else if(maps.size()==0){
                    continue;
                }else{
                    agentContract.setAssProtocolMap(maps.get(0));
                }
            }
        }
        return agentContracts;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public void updateContractList(List<AgentContractVo> volist,Map map) throws Exception {
        try {

            String userId ="";
            String agentId ="";
            String  sid="";
            if (null!=map){
                userId = String.valueOf(map.get("userId"));
                agentId = String.valueOf(map.get("agentId"));
                sid = String.valueOf(map.get("sid"));
            }
            for (AgentContractVo agentContractVo : volist) {
                agentContractVo.setActivId(sid);
                agentContractVo.setAgentId(agentId);
                agentContractVo.setcUser(userId);
                if (StringUtils.isEmpty(agentContractVo.getId())) {
                    //直接新增
                    AgentContract result = insertAgentContract(agentContractVo, agentContractVo.getContractTableFile(),userId,null);
                    if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(agentContractVo.getAgentAssProtocol())){
                        AssProtoColRel rel = new AssProtoColRel();
                        rel.setAgentBusinfoId(result.getId());
                        rel.setAssProtocolId(agentContractVo.getAgentAssProtocol());
                        AssProtoCol assProtoCol = assProtoColMapper.selectByPrimaryKey(agentContractVo.getAgentAssProtocol());
                        if(StringUtils.isNotBlank(agentContractVo.getProtocolRuleValue())){
                            String ruleReplace = assProtoCol.getProtocolRule().replace("{}", agentContractVo.getProtocolRuleValue());
                            rel.setProtocolRule(ruleReplace);
                        }else{
                            rel.setProtocolRule(assProtoCol.getProtocolRule());
                        }
                        rel.setProtocolRuleValue(agentContractVo.getProtocolRuleValue());
                        if(1!=agentAssProtocolService.addProtocolRel(rel,userId)){
                            throw new MessageException("合同分管协议添加失败");
                        }
                    }
                    logger.info("代理商合同添加:{}{}", "添加代理商合同成功", result.getId());
                } else {
                    AgentContract db_AgentContract = agentContractMapper.selectByPrimaryKey(agentContractVo.getId());
                    db_AgentContract.setAgentId(db_AgentContract.getAgentId());
                    db_AgentContract.setContNum(agentContractVo.getContNum());
                    db_AgentContract.setContType(agentContractVo.getContType());
                    db_AgentContract.setContDate(agentContractVo.getContDate());
                    db_AgentContract.setContEndDate(agentContractVo.getContEndDate());
                    db_AgentContract.setRemark(agentContractVo.getRemark());
                    db_AgentContract.setcUser(agentContractVo.getcUser());
                    db_AgentContract.setStatus(agentContractVo.getStatus());
                    db_AgentContract.setAppendAgree(agentContractVo.getAppendAgree());
                    if (1 != agentContractMapper.updateByPrimaryKeySelective(db_AgentContract)) {
                        throw new MessageException("更新代理商合同失败");
                    }else{
                        //记录历史
                        if(!agentDataHistoryService.saveDataHistory(db_AgentContract,db_AgentContract.getId(), DataHistoryType.CONTRACT.getValue(),userId,db_AgentContract.getVersion()).isOK()){
                            throw new MessageException("更新代理商合同失败");
                        }
                    }

                    //更新分管协议
                    if(StringUtils.isNotBlank(agentContractVo.getAgentAssProtocol())){
                        List<AssProtoColRel>  rels =agentAssProtocolService.queryProtoColByBusIds(Arrays.asList(db_AgentContract.getId()));
                        for (AssProtoColRel rel : rels) {
                            rel.setStatus(Status.STATUS_0.status);
                            if(1!=agentAssProtocolService.updateAssProtoColRel(rel)){
                                throw new MessageException("业务分管协议更新失败");
                            }
                        }
                        AssProtoColRel rel = new AssProtoColRel();
                        rel.setAgentBusinfoId(db_AgentContract.getId());
                        rel.setAssProtocolId(agentContractVo.getAgentAssProtocol());
                        AssProtoCol assProtoCol = assProtoColMapper.selectByPrimaryKey(agentContractVo.getAgentAssProtocol());
                        if(StringUtils.isNotBlank(agentContractVo.getProtocolRuleValue())){
                            String ruleReplace = assProtoCol.getProtocolRule().replace("{}", agentContractVo.getProtocolRuleValue());
                            rel.setProtocolRule(ruleReplace);
                        }else{
                            rel.setProtocolRule(assProtoCol.getProtocolRule());
                        }
                        rel.setProtocolRuleValue(agentContractVo.getProtocolRuleValue());
                        if(1!=agentAssProtocolService.addProtocolRel(rel,userId)){
                            throw new MessageException("业务分管协议添加失败");
                        }
                    }else{
                        //删除分管协议
                        List<AssProtoColRel>  rels = agentAssProtocolService.queryProtoColByBusIds(Arrays.asList(db_AgentContract.getId()));
                        for (AssProtoColRel rel : rels) {
                            rel.setStatus(Status.STATUS_0.status);
                            if(1!=agentAssProtocolService.updateAssProtoColRel(rel)){
                                throw new ProcessException("业务分管协议更新失败");
                            }
                        }
                    }
                    //删除老的附件
                    AttachmentRelExample example = new AttachmentRelExample();
                    example.or().andBusTypeEqualTo(AttachmentRelType.Contract.name()).andSrcIdEqualTo(db_AgentContract.getId()).andStatusEqualTo(Status.STATUS_1.status);
                    List<AttachmentRel> list = attachmentRelMapper.selectByExample(example);
                    for (AttachmentRel attachmentRel : list) {
                        attachmentRel.setStatus(Status.STATUS_0.status);
                        int i = attachmentRelMapper.updateByPrimaryKeySelective(attachmentRel);
                        if (1 != i) {
                            logger.info("修改合同附件关系失败{}", attachmentRel.getId());
                            throw new ProcessException("更新合同信息失败");
                        }
                    }

                    //添加新的附件
                    List<String> fileIdList = agentContractVo.getContractTableFile();
                    if (fileIdList != null) {
                        for (String fileId : fileIdList) {
                            AttachmentRel record = new AttachmentRel();
                            record.setAttId(fileId);
                            record.setSrcId(db_AgentContract.getId());
                            record.setcUser(db_AgentContract.getcUser());
                            record.setcTime(Calendar.getInstance().getTime());
                            record.setStatus(Status.STATUS_1.status);
                            record.setBusType(AttachmentRelType.Contract.name());
                            record.setId(idService.genId(TabId.a_attachment_rel));
                            int i = attachmentRelMapper.insertSelective(record);
                            if (1 != i) {
                                logger.info("合同附件关系失败");
                                throw new ProcessException("更新合同失败");
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
