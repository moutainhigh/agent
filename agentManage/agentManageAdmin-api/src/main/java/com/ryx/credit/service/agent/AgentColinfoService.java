package com.ryx.credit.service.agent;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentColinfoVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by cx on 2018/5/28.
 * com.ryx.credit.service.agent.AgentColinfoService
 */
public interface AgentColinfoService {
    /**
     * 代理商入网添加代理商交款项
     * @param ac
     * @param att
     * @return
     * @throws ProcessException
     */
    AgentColinfo agentColinfoInsert(AgentColinfo ac, List<String> att,String saveType)throws ProcessException;

    AgentResult saveAgentColinfoRel(AgentColinfoRel agentColinfoRel,String cUser)throws Exception;

    public List<AgentColinfo> queryAgentColinfoService(String agentId,String colId,BigDecimal appStatus);

    public int update(AgentColinfo a);

    public ResultVO updateAgentColinfoVo(List<AgentColinfoVo> colinfoVoList, Agent agent,String userId,String saveStatus)throws Exception;

    public AgentColinfo queryPoint(AgentColinfo agentColinfo);

    int updateByPrimaryKeySelective(AgentColinfo record);

    public AgentResult checkColInfo(AgentColinfo agentColinfo) throws ProcessException;

    void insertByPayment(AColinfoPayment colinfoPayment) throws Exception;

    void updateByPaymentResult(AColinfoPayment aColinfoPayment, Map<String, Object> resultMap) throws Exception;

    AgentColinfo selectByPrimaryKey(String id);

    AgentColinfo selectByAgentId(String agentId);

    AgentColinfo selectByAgentIdAndBusId(String agentId,String agentbusId);

    AgentResult updateAgentColinfo(AgentColinfo agentColinfo);

    AgentResult saveAgentColinfo(AgentColinfo agentColinfo);

    AgentResult checkAgentColinfo();


    Attachment selectAttachment(String id);

    public ResultVO updateAgentColinfoVoNow(List<AgentColinfoVo> colinfoVoList, Agent agent,String userId,String saveStatus)throws Exception;


    /**
     * 全量或者单个结算卡变Kafka通知处理类更处理类
     * @return
     * @throws Exception
     */
    public FastMap notifyCardChange(String type,String ag,String userId)throws Exception;

    AgentResult amendAgentColinfo(Map map);


}
