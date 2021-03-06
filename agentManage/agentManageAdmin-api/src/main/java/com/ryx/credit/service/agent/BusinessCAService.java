package com.ryx.credit.service.agent;

import com.ryx.credit.common.result.AgentResult;


public interface BusinessCAService {
    
	/**
     * 代理商工商认证
     * @param agentBusinfoName
     * @return ResultVO
     */
    AgentResult agentBusinessCA(String agentBusinfoName,String isCache);

    /**
     * 工商认证拉取代理商数据
     * @param agentId
     * @return
     */
    AgentResult agentBusinessCaToAgentDb(String agentId);


    /**
     * 批量进行工商认证
     * @return
     */
    public AgentResult caAgentList();
}
