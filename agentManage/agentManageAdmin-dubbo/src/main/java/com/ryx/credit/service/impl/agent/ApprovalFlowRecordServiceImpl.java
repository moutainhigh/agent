package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.common.FieldTranslate;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.COrganizationMapper;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.*;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.service.bank.PosRegionService;
import com.ryx.credit.service.dict.DepartmentService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 已审批待办任务
 * Created by RYX on 2018/9/4.
 */
@Service("approvalFlowRecordService")
public class ApprovalFlowRecordServiceImpl implements ApprovalFlowRecordService {

    private static Logger logger = LoggerFactory.getLogger(ApprovalFlowRecordServiceImpl.class);
    @Autowired
    private ApprovalFlowRecordMapper approvalFlowRecordMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private COrganizationMapper cOrganizationMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private BusActRelService busActRelService;
    @Autowired
    private AgentMergeMapper agentMergeMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private PosRegionService posRegionService;
    @Autowired
    private CapitalMapper capitalMapper;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private DateChangeRequestMapper dateChangeRequestMapper;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private ApaycompService apaycompService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private PlatFormService platFormService;


    @Override
    public String insert(ApprovalFlowRecord record)throws Exception{
        record.setId(idService.genId(TabId.a_approval_flow_record));
        record.setStatus(Status.STATUS_1.status);
        record.setVersion(Status.STATUS_1.status);
        approvalFlowRecordMapper.insert(record);
        return record.getId();
    }

    @Override
    public int update(ApprovalFlowRecord record){
        int i = approvalFlowRecordMapper.updateByPrimaryKeySelective(record);
        return i;
    }

    @Override
    public PageInfo approvalFlowList(ApprovalFlowRecord approvalFlowRecord, Page page) {

        ApprovalFlowRecordExample example = new ApprovalFlowRecordExample();
        ApprovalFlowRecordExample.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotBlank(approvalFlowRecord.getApprovalPerson())){
            String[] split = approvalFlowRecord.getApprovalPerson().split(",");
            List<String> personList = new ArrayList<>();
            for(int i=0;i<split.length;i++){
                personList.add(split[i]);
            }
            criteria.andApprovalPersonIn(personList);
        }

        if(StringUtils.isNotBlank(approvalFlowRecord.getApprovalDep())){
            criteria.andApprovalDepEqualTo(approvalFlowRecord.getApprovalDep());
        }
        if(StringUtils.isNotBlank(approvalFlowRecord.getAgentId())){
            criteria.andAgentIdEqualTo(approvalFlowRecord.getAgentId());
        }
        if(StringUtils.isNotBlank(approvalFlowRecord.getAgentName())){
            criteria.andAgentNameLike("%"+approvalFlowRecord.getAgentName()+"%");
        }
        if(StringUtils.isNotBlank(approvalFlowRecord.getAppResult())){
            criteria.andApprovalResultEqualTo(approvalFlowRecord.getAppResult());
        }
        if(StringUtils.isNotBlank(approvalFlowRecord.getBusId())){
            criteria.andBusIdEqualTo(approvalFlowRecord.getBusId());
        }
        if(StringUtils.isNotBlank(approvalFlowRecord.getBusType())){
            criteria.andBusTypeEqualTo(approvalFlowRecord.getBusType());
        }
        if(StringUtils.isNotBlank(approvalFlowRecord.getBeginTime()) && StringUtils.isNotBlank(approvalFlowRecord.getEndTime())){
            Date beginTime = DateUtil.format(approvalFlowRecord.getBeginTime(),"yyyy-MM-dd");
            Date endTime = DateUtil.format(approvalFlowRecord.getEndTime(),"yyyy-MM-dd");
            criteria.andApprovalTimeBetween(beginTime,endTime);
        }else{
            if(StringUtils.isNotBlank(approvalFlowRecord.getBeginTime())){
                Date beginTime = DateUtil.format(approvalFlowRecord.getBeginTime(),"yyyy-MM-dd");
                criteria.andApprovalTimeGreaterThanOrEqualTo(beginTime);
            }
            if(StringUtils.isNotBlank(approvalFlowRecord.getEndTime())){
                Date endTime = DateUtil.format(approvalFlowRecord.getEndTime(),"yyyy-MM-dd");
                criteria.andApprovalTimeLessThanOrEqualTo(endTime);
            }
        }
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        example.setPage(page);
        example.setOrderByClause("A_APPROVAL_FLOW_RECORD.APPROVAL_TIME desc");

        FastMap par = FastMap.fastMap("ApprovalFlowRecordExample",example);

        if(StringUtils.isNotBlank(approvalFlowRecord.getSubMitDateSta()) && StringUtils.isNotBlank(approvalFlowRecord.getSubMitDateEnd()) ){
            par.putKeyV("subMitDateSta",approvalFlowRecord.getSubMitDateSta());
            par.putKeyV("subMitDateEnd",approvalFlowRecord.getSubMitDateEnd());
        }

        List<ApprovalFlowRecord> approvalFlowRecords = approvalFlowRecordMapper.selectByExampleWithBusActRel(par);
        if(null!=approvalFlowRecords && approvalFlowRecords.size()!=0)
        approvalFlowRecords.forEach(row->{
            row.setBusTypeName(BusActRelBusType.getItemString(row.getBusType()));
            COrganization cOrganization = cOrganizationMapper.selectById(Integer.valueOf(row.getApprovalDep()));
            if(null!=cOrganization){
                row.setApprovalDep(cOrganization.getName());
            }
            CUser cUser = userService.selectById(Integer.valueOf(row.getApprovalPerson()));
            if(null!=cUser){
                row.setApprovalPerson(cUser.getName());
            }
            BusActRel busActRel = busActRelService.findById(row.getExecutionId());
            if(null!=busActRel && null!=busActRel.getcTime())
            row.setSubMitDate(DateUtil.format(busActRel.getcTime(),"yyyy-MM-dd"));
        });
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(approvalFlowRecords);
        pageInfo.setTotal((int)approvalFlowRecordMapper.selectByExampleWithBusActRelCount(par));
        return pageInfo;
    }

    /**
     * 导出查询
     * @param approvalFlowRecord
     * @return
     */
    private List<ApprovalFlowRecord> exprotCommon(ApprovalFlowRecord approvalFlowRecord){
        ApprovalFlowRecordExample approvalFlowRecordExample = new ApprovalFlowRecordExample();
        ApprovalFlowRecordExample.Criteria criteria = approvalFlowRecordExample.createCriteria();
        Date beginTime = DateUtil.format(approvalFlowRecord.getBeginTime(),"yyyy-MM-dd");
        Date endTime = DateUtil.format(approvalFlowRecord.getEndTime(),"yyyy-MM-dd");
        if (StringUtils.isNotBlank(approvalFlowRecord.getApprovalPerson())) {
            String[] split = approvalFlowRecord.getApprovalPerson().split(",");
            List<String> personList = new ArrayList<>();
            for (int i=0; i<split.length; i++) {
                personList.add(split[i]);
            }
            criteria.andApprovalPersonIn(personList);
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getApprovalDep())) {
            criteria.andApprovalDepEqualTo(approvalFlowRecord.getApprovalDep());
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getAgentId())) {
            criteria.andAgentIdEqualTo(approvalFlowRecord.getAgentId());
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getAgentName())) {
            criteria.andAgentNameEqualTo(approvalFlowRecord.getAgentName());
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getAppResult())) {
            criteria.andApprovalResultEqualTo(approvalFlowRecord.getAppResult());
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getBusId())) {
            criteria.andBusIdEqualTo(approvalFlowRecord.getBusId());
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getBusType())) {
            criteria.andBusTypeEqualTo(approvalFlowRecord.getBusType());
        }
        if (StringUtils.isNotBlank(approvalFlowRecord.getBeginTime()) && StringUtils.isNotBlank(approvalFlowRecord.getEndTime())) {
            criteria.andApprovalTimeBetween(beginTime, endTime);
        }else{
            if (StringUtils.isNotBlank(approvalFlowRecord.getBeginTime())) {
                criteria.andApprovalTimeGreaterThanOrEqualTo(beginTime);
            }
            if (StringUtils.isNotBlank(approvalFlowRecord.getEndTime())) {
                criteria.andApprovalTimeLessThanOrEqualTo(endTime);
            }
        }
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<ApprovalFlowRecord> approvalFlowRecords = approvalFlowRecordMapper.selectByExample(approvalFlowRecordExample);
        return approvalFlowRecords;
    }

    /**
     * 导出代理商合并数据
     * @param approvalFlowRecord
     * @return
     */
    @Override
    public List<Map<String, Object>> exportAgentMerge(ApprovalFlowRecord approvalFlowRecord) throws Exception {

        List<Map<String, Object>> resultList = new ArrayList<>();
        List<ApprovalFlowRecord> approvalFlowRecords = exprotCommon(approvalFlowRecord);
        if (null != approvalFlowRecords && approvalFlowRecords.size() != 0) {
            for (ApprovalFlowRecord flowRecord : approvalFlowRecords) {
                Map<String, Object> resultMap = new HashMap<>();
                AgentMerge agentMerge = agentMergeMapper.selectByPrimaryKey(flowRecord.getBusId());
                resultMap.put("uTime", DateUtil.format(agentMerge.getuTime()));
                resultMap.put("mainAgentId", agentMerge.getMainAgentId());
                resultMap.put("mainAgentName", agentMerge.getMainAgentName());
                resultMap.put("subAgentId", agentMerge.getSubAgentId());
                resultMap.put("subAgentName", agentMerge.getSubAgentName());

                Agent agentMain = agentMapper.selectByPrimaryKey(agentMerge.getMainAgentId());
                COrganization cOrganizationMain = cOrganizationMapper.selectByPrimaryKey(Integer.valueOf(agentMain.getAgDocPro()));
                resultMap.put("agDocProMain", cOrganizationMain.getName());
                Agent agentSub = agentMapper.selectByPrimaryKey(agentMerge.getSubAgentId());
                COrganization cOrganizationSub = cOrganizationMapper.selectByPrimaryKey(Integer.valueOf(agentSub.getAgDocPro()));
                resultMap.put("agDocProSub", cOrganizationSub.getName());

                List<AgentBusInfo> agentBusInfosMain = agentBusInfoMapper.selectByAgenId(agentMain.getId());
                if (null != agentBusInfosMain && agentBusInfosMain.size() != 0) {
                    String busTypeMain = "";
                    String busPlatformMain = "";
                    String busRegionMain = "";
                    for (AgentBusInfo agentBusInfo : agentBusInfosMain) {
                        busRegionMain += posRegionService.queryNameByCodes(agentBusInfo.getBusRegion()) + ",";
                        busTypeMain += BusType.getContentByValue(agentBusInfo.getBusType()) + ",";
                        busPlatformMain += Platform.getContentByValue(agentBusInfo.getBusPlatform()) + ",";

                    }
                    resultMap.put("busTypeMain", StringUtils.isNotBlank(busTypeMain) ? busTypeMain.substring(0, busTypeMain.length() -1) : busTypeMain);
                    resultMap.put("busPlatformMain", StringUtils.isNotBlank(busPlatformMain) ? busPlatformMain.substring(0, busPlatformMain.length() -1) : busPlatformMain);
                    resultMap.put("busRegionMain", StringUtils.isNotBlank(busRegionMain) ? busRegionMain.substring(0, busRegionMain.length() -1) : busRegionMain);
                }

                List<AgentBusInfo> agentBusInfosSub = agentBusInfoMapper.selectByAgenId(agentSub.getId());
                String busTypeSub = "";
                String busPlatformSub = "";
                String busRegionSub = "";
                if (null != agentBusInfosSub && agentBusInfosSub.size() != 0) {
                    for (AgentBusInfo agentBusInfo : agentBusInfosSub) {
                        busTypeSub += posRegionService.queryNameByCodes(agentBusInfo.getBusRegion()) + ",";
                        busPlatformSub += BusType.getContentByValue(agentBusInfo.getBusType()) + ",";
                        busRegionSub += Platform.getContentByValue(agentBusInfo.getBusPlatform()) + ",";
                    }
                    resultMap.put("busTypeSub", StringUtils.isNotBlank(busTypeSub) ? busTypeSub.substring(0, busTypeSub.length() -1 ) : busTypeSub);
                    resultMap.put("busPlatformSub", StringUtils.isNotBlank(busPlatformSub) ? busPlatformSub.substring(0, busPlatformSub.length() -1) : busPlatformSub);
                    resultMap.put("busRegionSub", StringUtils.isNotBlank(busRegionSub) ? busRegionSub.substring(0, busRegionSub.length() -1) : busRegionSub);
                }
                resultList.add(resultMap);
            }
        }
        return resultList;
    }

    /**
     * 代理商入网&代理商业务
     * @param approvalFlowRecord
     * @throws Exception
     */
    @Override
    public List<ApprovalFlowRecordVo> exportAgentNetln(ApprovalFlowRecord approvalFlowRecord) throws Exception {
        FastMap fastMap = FastMap.fastMap("approvalPerson", approvalFlowRecord.getApprovalPerson());
        fastMap.putKeyV("busId", approvalFlowRecord.getBusId());
        fastMap.putKeyV("busType", approvalFlowRecord.getBusType());
        fastMap.putKeyV("approvalResult", approvalFlowRecord.getAppResult());
        fastMap.putKeyV("agentId", approvalFlowRecord.getAgentId());
        fastMap.putKeyV("agentName", approvalFlowRecord.getAgentName());
        if(StringUtils.isNotBlank(approvalFlowRecord.getBeginTime()) && StringUtils.isNotBlank(approvalFlowRecord.getBeginTime())){
            fastMap.putKeyV("beginTime", approvalFlowRecord.getBeginTime());
            fastMap.putKeyV("endTime", approvalFlowRecord.getEndTime());
        }
        if(StringUtils.isNotBlank(approvalFlowRecord.getSubMitDateSta()) && StringUtils.isNotBlank(approvalFlowRecord.getSubMitDateEnd())){
            fastMap.putKeyV("subMitDateSta", approvalFlowRecord.getSubMitDateSta());
            fastMap.putKeyV("subMitDateEnd", approvalFlowRecord.getSubMitDateEnd());
        }
        List<ApprovalFlowRecordVo> approvalFlowRecordList = approvalFlowRecordMapper.exportAgentAndBusinfo(fastMap);
        return approvalFlowRecordList;
    }

    /**
     * 代理商业务修改
     * @param approvalFlowRecord
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> exportAgentEdit(ApprovalFlowRecord approvalFlowRecord) throws Exception {
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<ApprovalFlowRecord> approvalFlowRecords = exprotCommon(approvalFlowRecord);
        for (ApprovalFlowRecord flowRecord : approvalFlowRecords) {
            Map<String, Object> resultMap = new HashMap<>();
            List<String> diffList = new ArrayList<>();
            String busId = flowRecord.getBusId();
            DateChangeRequest dateChangeRequest = dateChangeRequestMapper.selectByPrimaryKey(busId);
            AgentVo dataPreAgentVo = JsonUtil.jsonToPojo(dateChangeRequest.getDataPreContent(), AgentVo.class);
            AgentVo dataAgentVo = JsonUtil.jsonToPojo(dateChangeRequest.getDataContent(), AgentVo.class);
            contrastObj(dataPreAgentVo.getAgent(), dataAgentVo.getAgent(), diffList);
            List<AgentBusInfoVo> preBusInfoVoList = dataPreAgentVo.getBusInfoVoList();
            List<AgentBusInfoVo> busInfoVoList = dataAgentVo.getBusInfoVoList();
            if (preBusInfoVoList!=null && busInfoVoList!=null && preBusInfoVoList.size()!=0 && busInfoVoList.size()!=0) {
                //变更前的业务信息
                AgentBusInfoVo agentBusInfoVo_before = preBusInfoVoList.get(0);
                if(agentBusInfoVo_before.getBusPlatform()!=null){//业务平台（前）
                    PlatForm platForm_before = platFormService.selectByPlatformNum(agentBusInfoVo_before.getBusPlatform());
                    resultMap.put("busPlatformBefore", platForm_before.getPlatformName());
                }else{
                    resultMap.put("busPlatformBefore", agentBusInfoVo_before.getBusPlatform()==null?"":agentBusInfoVo_before.getBusPlatform());
                }
                if(agentBusInfoVo_before.getBusParent()!=null){//上级代理商（前）、上级业务编码（前）
                    ApprovalFlowRecordVo getBusParent_before = approvalFlowRecordMapper.selectByAgentName(agentBusInfoVo_before.getBusParent());
                    if(getBusParent_before!=null)
                    resultMap.put("busParentBefore", getBusParent_before.getOneParentName());
                    if(getBusParent_before!=null)
                    resultMap.put("busParentNumBefore", getBusParent_before.getOneParentNum());
                }else{
                    resultMap.put("busParentBefore", agentBusInfoVo_before.getBusParent()==null?"":agentBusInfoVo_before.getBusParent());
                    resultMap.put("busParentNumBefore", "");
                }
                if(agentBusInfoVo_before.getBusActivationParent()!=null){//激活返现代理商（前）、激活返现业务编码（前）
                    ApprovalFlowRecordVo getBusActivationParent_before = approvalFlowRecordMapper.selectByAgentName(agentBusInfoVo_before.getBusActivationParent());
                    if(getBusActivationParent_before!=null)
                    resultMap.put("busActivationParentBefore", getBusActivationParent_before.getOneParentName());
                    if(getBusActivationParent_before!=null)
                    resultMap.put("busActivationParentNumBefore", getBusActivationParent_before.getOneParentNum());
                }else{
                    resultMap.put("busActivationParentBefore", agentBusInfoVo_before.getBusActivationParent()==null?"":agentBusInfoVo_before.getBusActivationParent());
                    resultMap.put("busActivationParentNumBefore", "");
                }
                if(agentBusInfoVo_before.getBusType()!=null){//业务类型（前）
                    Dict getBusType_before = dictOptionsService.findDictByValue(DictGroup.AGENT.name(), DictGroup.BUS_TYPE.name(), agentBusInfoVo_before.getBusType());
                    if(getBusType_before!=null)
                    resultMap.put("busTypeBefore", getBusType_before.getdItemname());
                }else{
                    resultMap.put("busTypeBefore", agentBusInfoVo_before.getBusType()==null?"":agentBusInfoVo_before.getBusType());
                }
                if(agentBusInfoVo_before.getDredgeS0()!=null){//是否开通S0（前）
                    Dict getDredgeS0_before = dictOptionsService.findDictByValue(DictGroup.ALL.name(), DictGroup.YESORNOISYES.name(), String.valueOf(agentBusInfoVo_before.getDredgeS0()));
                    resultMap.put("dredgeS0Before", getDredgeS0_before.getdItemname());
                }else{
                    resultMap.put("dredgeS0Before", agentBusInfoVo_before.getDredgeS0()==null?"":agentBusInfoVo_before.getDredgeS0());
                }
                if(agentBusInfoVo_before.getBusScope()!=null){//业务范围（前）
                    Dict getBusScope_before = dictOptionsService.findDictByValue(DictGroup.AGENT.name(), DictGroup.BUS_SCOPE.name(), agentBusInfoVo_before.getBusScope());
                    resultMap.put("busScopeBefore", getBusScope_before.getdItemname());
                }else{
                    resultMap.put("busScopeBefore", agentBusInfoVo_before.getBusScope()==null?"":agentBusInfoVo_before.getBusScope());
                }
                if(agentBusInfoVo_before.getBusIndeAss()!=null){//是否独立考核（前）
                    Dict getBusIndeAss_before = dictOptionsService.findDictByValue(DictGroup.ALL.name(), DictGroup.YESORNO.name(), String.valueOf(agentBusInfoVo_before.getBusIndeAss()));
                    resultMap.put("busIndeAssBefore", getBusIndeAss_before.getdItemname());
                }else{
                    resultMap.put("busIndeAssBefore", agentBusInfoVo_before.getBusIndeAss()==null?"":agentBusInfoVo_before.getBusIndeAss());
                }
//                resultMap.put("busTypeBefore", getBusType_before.getdItemname());
//                resultMap.put("busPlatformBefore", platForm_before.getPlatformName());
//                resultMap.put("busParentBefore", getBusParent_before.getOneParentName());
//                resultMap.put("busParentNumBefore", getBusParent_before.getOneParentNum());
//                resultMap.put("busActivationParentBefore", getBusActivationParent_before.getOneParentName());
//                resultMap.put("busActivationParentNumBefore", getBusActivationParent_before.getOneParentNum());
//                resultMap.put("dredgeS0Before", getDredgeS0_before.getdItemname());
//                resultMap.put("busScopeBefore", getBusScope_before.getdItemname());
//                resultMap.put("busIndeAssBefore", getBusIndeAss_before.getdItemname());
                //变更后的业务信息
                AgentBusInfoVo agentBusInfoVo_after = busInfoVoList.get(0);
                if(agentBusInfoVo_after.getBusPlatform()!=null){//业务平台（后）
                    PlatForm platForm_after = platFormService.selectByPlatformNum(agentBusInfoVo_after.getBusPlatform());
                    resultMap.put("busPlatformAfter", platForm_after.getPlatformName());
                }else{
                    resultMap.put("busPlatformAfter", agentBusInfoVo_after.getBusPlatform()==null?"":agentBusInfoVo_after.getBusPlatform());
                }
                if(agentBusInfoVo_after.getBusParent()!=null){//上级代理商（后）、上级业务编码（后）
                    ApprovalFlowRecordVo getBusParent_after = approvalFlowRecordMapper.selectByAgentName(agentBusInfoVo_after.getBusParent());
                    if(getBusParent_after!=null)
                    resultMap.put("busParentAfter", getBusParent_after.getOneParentName());
                    if(getBusParent_after!=null)
                    resultMap.put("busParentNumAfter", getBusParent_after.getOneParentNum());
                }else{
                    resultMap.put("busParentAfter", agentBusInfoVo_after.getBusParent()==null?"":agentBusInfoVo_after.getBusParent());
                    resultMap.put("busParentNumAfter", "");
                }
                if(agentBusInfoVo_after.getBusParent()!=null){//激活返现代理商（后）、激活返现业务编码（后）
                    ApprovalFlowRecordVo getBusActivationParent_after = approvalFlowRecordMapper.selectByAgentName(agentBusInfoVo_after.getBusActivationParent());
                    if(getBusActivationParent_after!=null)
                    resultMap.put("busActivationParentAfter", getBusActivationParent_after.getOneParentName());
                    if(getBusActivationParent_after!=null)
                    resultMap.put("busActivationParentNumAfter", getBusActivationParent_after.getOneParentNum());
                }else{
                    resultMap.put("busActivationParentAfter", agentBusInfoVo_after.getBusActivationParent()==null?"":agentBusInfoVo_after.getBusActivationParent());
                    resultMap.put("busActivationParentNumAfter", "");
                }
                if(agentBusInfoVo_after.getBusType()!=null){//业务类型（后）
                    Dict getBusType_after = dictOptionsService.findDictByValue(DictGroup.AGENT.name(), DictGroup.BUS_TYPE.name(), agentBusInfoVo_after.getBusType());
                    if(getBusType_after!=null)
                    resultMap.put("busTypeAfter", getBusType_after.getdItemname());
                }else{
                    resultMap.put("busTypeAfter", agentBusInfoVo_after.getBusType()==null?"":agentBusInfoVo_after.getBusType());
                }
                if(agentBusInfoVo_after.getDredgeS0()!=null){//是否开通S0（后）
                    Dict getDredgeS0_after = dictOptionsService.findDictByValue(DictGroup.ALL.name(), DictGroup.YESORNOISYES.name(), String.valueOf(agentBusInfoVo_after.getDredgeS0()));
                    resultMap.put("dredgeS0After", getDredgeS0_after.getdItemname());
                }else{
                    resultMap.put("dredgeS0After", agentBusInfoVo_after.getDredgeS0()==null?"":agentBusInfoVo_after.getDredgeS0());
                }
                if(agentBusInfoVo_after.getBusScope()!=null){//业务范围（后）
                    Dict getBusScope_after = dictOptionsService.findDictByValue(DictGroup.AGENT.name(), DictGroup.BUS_SCOPE.name(), agentBusInfoVo_after.getBusScope());
                    resultMap.put("busScopeAfter", getBusScope_after.getdItemname());
                }else{
                    resultMap.put("busScopeAfter", agentBusInfoVo_after.getBusScope()==null?"":agentBusInfoVo_after.getBusScope());
                }
                if(agentBusInfoVo_after.getBusIndeAss()!=null){//是否独立考核（后）
                    Dict getBusIndeAss_after = dictOptionsService.findDictByValue(DictGroup.ALL.name(), DictGroup.YESORNO.name(), String.valueOf(agentBusInfoVo_after.getBusIndeAss()));
                    resultMap.put("busIndeAssAfter", getBusIndeAss_after.getdItemname());
                }else{
                    resultMap.put("busIndeAssAfter", agentBusInfoVo_after.getBusIndeAss()==null?"":agentBusInfoVo_after.getBusIndeAss());
                }
                if(StringUtils.isNotBlank(agentBusInfoVo_after.getAgDocDistrict()) && StringUtils.isNotBlank(agentBusInfoVo_after.getAgDocPro())){//对接大区、对接省区
                    COrganization getAgDocDistrict = departmentService.getById(agentBusInfoVo_after.getAgDocDistrict());
                    COrganization getAgDocPro = departmentService.getById(agentBusInfoVo_after.getAgDocPro());
                    resultMap.put("agDocDistrict", getAgDocDistrict.getName());
                    resultMap.put("agDocPro", getAgDocPro.getName());
                }else{
                    resultMap.put("agDocDistrict", agentBusInfoVo_after.getAgDocDistrict()==null?"":agentBusInfoVo_after.getAgDocDistrict());
                    resultMap.put("agDocPro", agentBusInfoVo_after.getAgDocPro()==null?"":agentBusInfoVo_after.getAgDocPro());
                }
                resultMap.put("busId", agentBusInfoVo_after.getId());
                resultMap.put("busNum", agentBusInfoVo_after.getBusNum());
//                resultMap.put("agDocDistrict", departmentService.getById(agentBusInfoVo_after.getAgDocDistrict()).getName());
//                resultMap.put("agDocPro", departmentService.getById(agentBusInfoVo_after.getAgDocPro()).getName());
//                resultMap.put("busTypeAfter", getBusType_after.getdItemname());
//                resultMap.put("busPlatformAfter", platForm_after.getPlatformName());
//                resultMap.put("busParentAfter", getBusParent_after.getOneParentName());
//                resultMap.put("busParentNumAfter", getBusParent_after.getOneParentNum());
//                resultMap.put("busActivationParentAfter", getBusParent_after.getOneParentName());
//                resultMap.put("busActivationParentNumAfter", getBusParent_after.getOneParentNum());
//                resultMap.put("dredgeS0After", getDredgeS0_after.getdItemname());
//                resultMap.put("busScopeAfter", getBusScope_after.getdItemname());
//                resultMap.put("busIndeAssAfter", getBusIndeAss_after.getdItemname());
            }
            ApprovalFlowRecord approvalFlow = approvalFlowRecordMapper.selectByPrimaryKey(flowRecord.getId());
            BusActRel busActRel = busActRelMapper.findByActivId(approvalFlow.getExecutionId());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            CUser getApprovalPerson = userService.selectById(approvalFlow.getApprovalPerson());
            Dict getApprovalResult = dictOptionsService.findDictByValue(DictGroup.AGENT_AUDIT.name(), DictGroup.APPROVAL_TYPE.name(), approvalFlow.getApprovalResult());
            Dict getActivStatus = dictOptionsService.findDictByValue(DictGroup.AGENT.name(), DictGroup.AG_STATUS_S.name(), busActRel.getActivStatus());
            resultMap.put("approvalTime", dateFormat.format(approvalFlow.getApprovalTime()));
            resultMap.put("approvalPerson", getApprovalPerson.getName());
            resultMap.put("approvalResult", getApprovalResult.getdItemname());
            resultMap.put("approvalOpinion", approvalFlow.getApprovalOpinion());
            resultMap.put("activStatus", getActivStatus.getdItemname());
            resultMap.put("agentId", approvalFlow.getAgentId());
//            resultMap.put("busId", approvalFlow.getBusId());
            resultMap.put("agentName", approvalFlow.getAgentName());
            resultMap.put("dateChange", diffList.toString());
            resultList.add(resultMap);
        }
        return resultList;
    }

    private static List<String> contrastObj(Object obj1, Object obj2, List<String> textList) {
        try {
            Class clazz = obj1.getClass();
            Field[] fields = obj1.getClass().getDeclaredFields();
            for (Field field : fields) {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method getMethod = pd.getReadMethod();
                Object o1 = getMethod.invoke(obj1);
                Object o2 = getMethod.invoke(obj2);
                String s1 = o1 == null ? "" : o1.toString();
                String s2 = o2 == null ? "" : o2.toString();
                if (!s1.equals(s2)) {
                    if (!field.getName().equals("agUniqNum") && !field.getName().equals("agStatus")) {
                        textList.add(FieldTranslate.getNameByField(field.getName()) + "修改前:" + s1 + ";修改后:" + s2);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return textList;
    }



    @Override
    public List<Map<String,Object>> queryFlowByExecutionId(String executionId){

        List<Map<String,Object>> resultList = new ArrayList<>();
        ApprovalFlowRecordExample approvalFlowRecordExample = new ApprovalFlowRecordExample();
        ApprovalFlowRecordExample.Criteria criteria = approvalFlowRecordExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andActivityStatusEqualTo(Status.STATUS_1.status);
        criteria.andExecutionIdEqualTo(executionId);
        approvalFlowRecordExample.setOrderByClause(" APPROVAL_TIME ");
        List<ApprovalFlowRecord> approvalFlowRecords = approvalFlowRecordMapper.selectByExample(approvalFlowRecordExample);
        for (ApprovalFlowRecord approvalFlowRecord : approvalFlowRecords) {
            Map<String,Object> resultMap = new HashMap<>();
            //审核人
            CUser cUser = iUserService.selectById(approvalFlowRecord.getApprovalPerson());
            if(null!=cUser){
                resultMap.put("approvalPerson",cUser.getName());
            }else{
                resultMap.put("approvalPerson",approvalFlowRecord.getApprovalPerson());
            }
            //审核部门
            COrganization cOrganization = cOrganizationMapper.selectByPrimaryKey(Integer.valueOf(approvalFlowRecord.getApprovalDep()));
            if (null!=cOrganization){
                resultMap.put("approvalDep",cOrganization.getName());
            }else{
                resultMap.put("approvalDep",approvalFlowRecord.getApprovalPerson());
            }
            resultMap.put("createTime",DateUtil.format(approvalFlowRecord.getApprovalTime(),DateUtil.DATE_FORMAT_1));
            resultMap.put("rs",approvalFlowRecord.getApprovalResult());
            resultMap.put("approvalOpinion",approvalFlowRecord.getApprovalOpinion());
            resultList.add(resultMap);
        }
        return resultList;
    }
}
