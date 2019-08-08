package com.ryx.credit.service.order;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.order.AgentVoTerminalTransferDetail;
import com.ryx.credit.pojo.admin.order.TerminalTransfer;
import com.ryx.credit.pojo.admin.order.TerminalTransferDetail;
import com.ryx.credit.pojo.admin.vo.AgentVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by RYX on 2018/12/20.
 */
public interface TerminalTransferService {

    PageInfo terminalTransferList(TerminalTransfer terminalTransfer, Page page, String agName, String dataRole, Long userId);

    PageInfo terminalTransferDetailList(TerminalTransferDetail terminalTransferDetail, Page page, String agName, String dataRole, Long userId);

    AgentResult startTerminalTransferActivity(String id, String cuser, String agentId, Boolean isSave) throws Exception;

    AgentResult approvalTerminalTransferTask(AgentVo agentVo, String userId, String busId) throws Exception;

    AgentResult compressTerminalTransferActivity(String proIns, BigDecimal agStatus)throws Exception;

    AgentResult saveTerminalTransfer(TerminalTransfer terminalTransfer, List<TerminalTransferDetail> terminalTransferDetailList, String cuser, String agentId, String saveFlag)throws Exception;

    TerminalTransfer queryTerminalTransfer(String terminalTransferId);

    List<TerminalTransferDetail> queryDetailByTerminalId(String terminalTransferId);

    AgentResult importTerminal(List<List<Object>> excelList, String cUser)throws Exception;

    List<TerminalTransferDetail> queryImprotMsgList(String terminalTransferId);

    AgentResult delTerminalTransfer(String terminalTransferId, String cUser)throws Exception;

    AgentResult editTerminalTransfer(TerminalTransfer terminalTransfer, List<TerminalTransferDetail> terminalTransferDetailList, String cuser, String agentId)throws Exception;

    void appTerminalTransfer()throws Exception;

    Map<String, Object>  getAgentType(String orgId);

    void  queryTerminalTransferResult() throws Exception;
    PageInfo terminalTransferDetailListExport(AgentVoTerminalTransferDetail terminalTransferDetail);

    public Map<String, Object> disposeSN(String snBeginNum, String snEndNum);
}
