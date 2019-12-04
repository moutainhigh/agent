package com.ryx.credit.dao.agent;


import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentExample;
import com.ryx.credit.pojo.admin.vo.AgentoutVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AgentMapper {
    int countByExample(AgentExample example);

    int deleteByExample(AgentExample example);

    int insert(Agent record);

    int insertSelective(Agent record);

    List<Agent> selectByExample(AgentExample example);

    Agent selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Agent record);

    int updateByPrimaryKey(Agent record);

    List<Map<String,Object>> queryDeptName(String agDocPro);

    List<Map<String,Object>> queryDeptNameDis(String agDocDistrict);

    List<AgentoutVo> excelAgent(@Param("map")Map<String,Object> map);

    int queryAgentListViewCount(@Param("map") Map<String,Object> map);

    List<Map<String,Object>> queryAgentListView(@Param("map") Map<String,Object> map,@Param("page") Page page);

    List<Agent> queryAgentTierList(@Param("map") Map<String,Object> map,@Param("page") Page page);

    int queryAgentTierCount(@Param("map") Map<String,Object> map);

    //通过登录用户查询代理商
    Map<String, Object> selectAgentByOrgId(@Param("map")Map<String,Object> map);

    List<Agent> selectByPrimaryKeys(List ids);

    List<Map<String,Object>> queryCaManagerList(@Param("map") Map<String,Object> map,@Param("page") Page page);

    int queryCaManagerListCount(@Param("map") Map<String,Object> map,@Param("page") Page page);

    List<Agent> expoerCaList(@Param("map") Map<String,Object> map);

    Agent selectByAgent(Agent agent);

    List<Map<String,Object>> queryEchartDataList(@Param("map") Map<String,Object> map,@Param("page") Page page);

    int queryEchartDataCount(@Param("map") Map<String,Object> map);
}