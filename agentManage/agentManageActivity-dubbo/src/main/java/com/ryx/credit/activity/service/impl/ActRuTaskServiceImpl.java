package com.ryx.credit.activity.service.impl;

import com.ryx.credit.activity.dao.ActRuTaskMapper;
import com.ryx.credit.activity.entity.ActRuTask;
import com.ryx.credit.activity.entity.ActRuTaskExample;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.service.ActRuTaskService;
import com.ryx.credit.service.CRoleService;
import com.ryx.credit.service.IOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * ActRuTaskServiceImpl
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2018/5/22
 * @Time: 15:17
 * @description: ActRuTaskServiceImpl
 * To change this template use File | Settings | File Templates.
 */
@Service("actRuTaskService")
public class ActRuTaskServiceImpl implements ActRuTaskService {

    @Autowired
    private ActRuTaskMapper  actRuTaskMapper;
    @Autowired
    private CRoleService roleService;
    @Autowired
    private IOrganizationService organizationService;

    @Override
    public int insert(ActRuTask record) {
        return actRuTaskMapper.insert(record);
    }

    @Override
    public int insertSelective(ActRuTask record) {
        return actRuTaskMapper.insertSelective(record);
    }

    @Override
    public ActRuTask selectByPrimaryKey(Object id) {
        return actRuTaskMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ActRuTask record) {
        return actRuTaskMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ActRuTask record) {
        return actRuTaskMapper.updateByPrimaryKey(record);
    }

    @Override
    public HashMap<String, Object> configExample(Page page, ActRuTask actRuTask) {
        if (actRuTask != null && page != null) {
            ActRuTaskExample actRuTaskExample = new ActRuTaskExample();
            ActRuTaskExample.Criteria criteria = actRuTaskExample.or();
            if (actRuTask.getId() != null) {
                criteria.andIdEqualTo(actRuTask.getId());
            }

            if (actRuTask.getExecutionId() != null) {
                criteria.andExecutionIdEqualTo(actRuTask.getExecutionId());
            }
            if (actRuTask.getProcInstId() != null) {
                criteria.andProcInstIdEqualTo(actRuTask.getProcInstId());
            }

            if (actRuTask.getProcDefId() != null) {
                criteria.andProcDefIdEqualTo(actRuTask.getProcDefId());
            }

            if (actRuTask.getName() != null) {
                criteria.andNameEqualTo(actRuTask.getName());
            }

            if (actRuTask.getAssignee() != null) {
                criteria.andAssigneeEqualTo(actRuTask.getAssignee());
            }

            int count = actRuTaskMapper.countByExample(actRuTaskExample);
            page.setCount(count);
            actRuTaskExample.setPage(page);
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("list", actRuTaskMapper.selectByExample(actRuTaskExample));
            hashMap.put("page", page);
            return hashMap;
        } else {
            return null;
        }
    }


    @Override
    public List<Map<String, Object>>  queryMyTask(Map<String,Object> param){
        List<Map<String, Object>> taskList = actRuTaskMapper.queryMyTask(param);
        return taskList;
    }


    @Override
    public PageInfo queryMyTaskPage(Page page, Map<String,Object> param){

        String dbUrlsPid = AppConfig.getProperty("dbUrls_pid");
        String netInUrlsPid = AppConfig.getProperty("netInUrls_pid");
        Set<String> dbUrls = roleService.selectShiroUrl((Long) param.get("userId"),dbUrlsPid,"/BusActRelBusType");//审批类型权限
        Set<String> netInUrls = roleService.selectShiroUrl((Long) param.get("userId"),netInUrlsPid,"");//品牌权限
        Set<String> roleNames = roleService.findFinanceRole((Long) param.get("userId"));//角色编号

        param.put("roleIds",roleNames);
        param.put("dbUrls",dbUrls);
        param.put("netInUrls",netInUrls);
        List<Map<String, Object>> taskList = actRuTaskMapper.queryMyTaskPage(param,page);
        for (Map<String, Object> resultMap : taskList) {
            String agDocPro = String.valueOf(resultMap.get("AG_DOC_PRO"));
            String agDocDistrict = String.valueOf(resultMap.get("AG_DOC_DISTRICT"));
            String explain = String.valueOf(resultMap.get("EXPLAIN"));
            if(!agDocPro.equals("null")){
                COrganization proOrganization = organizationService.selectByPrimaryKey(Integer.parseInt(agDocPro));
                if(proOrganization!=null)
                resultMap.put("AG_DOC_PRO_NAME",proOrganization.getName());
            }else{
                resultMap.put("AG_DOC_PRO_NAME","");
            }
            if(!agDocDistrict.equals("null")){
                COrganization districtOrganization = organizationService.selectByPrimaryKey(Integer.parseInt(agDocDistrict));
                if(districtOrganization!=null)
                resultMap.put("AG_DOC_DISTRICT_NAME",districtOrganization.getName());
            }else{
                resultMap.put("AG_DOC_DISTRICT_NAME","");
            }
            if (!explain.equals("null")){
                resultMap.put("EXPLAIN",explain);
            }else{
                resultMap.put("EXPLAIN","");
            }
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(taskList);
        pageInfo.setTotal(actRuTaskMapper.queryMyTaskCount(param));

        return pageInfo;
    }

    @Override
    public List<Map<String, Object>> queryHuddleMyTask(Map<String, Object> params) {
        return actRuTaskMapper.queryHuddleMyTask(params);
    }
}
