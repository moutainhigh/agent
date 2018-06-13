package com.ryx.credit.service.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.AgentPlatFormSyn;


/**
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/6/11 11:36
 */
public interface AgentNotifyService {

    void asynNotifyPlatform(String busId);

    void notifyPlatform(String busId)throws Exception;

    PageInfo queryList(Page page,AgentPlatFormSyn agentPlatFormSyn);

    AgentPlatFormSyn findByBusId(String busId);
}