package com.ryx.credit.service.agent;

import java.util.List;
import java.util.Map;

import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;

/**
 * Created by cx on 2018/5/23.
 */
public interface AgentBusinfoService {
    /**
     * 代理商查询插件数据获取
     * @param par
     * @return
     */
    PageInfo agentBusInfoSelectViewList(Map par,  PageInfo page);
    
    public void agentBusInfoInsert(AgentBusInfo agentBusInfo);
}
