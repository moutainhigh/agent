package com.ryx.internet.service;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OCashReceivablesVo;
import com.ryx.internet.pojo.InternetRenewOffset;
import com.ryx.internet.pojo.InternetRenewOffsetDetail;
import com.ryx.internet.pojo.OInternetRenew;
import com.ryx.internet.pojo.OInternetRenewDetail;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/7/1 19:15
 * @Param
 * @return
 **/
public interface OInternetRenewService {

    PageInfo internetRenewList(OInternetRenew internetRenew, Page page,String agentId,Long userId);

    PageInfo internetRenewDetailList(OInternetRenewDetail internetRenewDetail, Page page,String agentId,Long userId);

    PageInfo internetRenewOffsetList(InternetRenewOffset internetRenewOffset, Page page, String agentId,Long userId);

    PageInfo internetRenewOffsetDetailList(InternetRenewOffsetDetail internetRenewOffsetDetail, Page page, String agentId,Long userId);

    AgentResult saveAndApprove(OInternetRenew internetRenew, List<String> iccids, String cUser,
                               List<OCashReceivablesVo> oCashReceivablesVoList)throws MessageException;

    OInternetRenew selectByPrimaryKey(String id);

    AgentResult approvalTask(AgentVo agentVo, String userId) throws Exception;

    void approveTashBusiness(AgentVo agentVo, String userId) throws Exception;

    AgentResult compressCompensateActivity(String proIns, BigDecimal agStatus,String reqType,OInternetRenew oInternetRenew)throws Exception;

    List<OInternetRenewDetail> queryInternetRenewDetailList(OInternetRenewDetail internetRenewDetail, Page page,String agentId,Long userId);

    Integer queryInternetRenewDetailCount(OInternetRenewDetail internetRenewDetail,String agentId,Long userId);

    void renewVerify(String iccidNumIds,Long userId,String reqType)throws MessageException;

    Map<Object, Object> getInternetRenewWay(Long cUser);

    void processDataInternetCardOffset();

    List<InternetRenewOffsetDetail> queryInternetRenewOffsetDetailList(InternetRenewOffsetDetail internetRenewOffsetDetail, Page page,String agentId,Long userId);

    Integer queryInternetRenewOffsetDetailCount(InternetRenewOffsetDetail internetRenewOffsetDetail,String agentId,Long userId);

    AgentResult queryMonthSumOffsetAmt(Map<String,Object> reqMap);

    AgentResult queryCardProfit(Map<String,Object> reqMap);

    AgentResult batchRenewInsert(OInternetRenew internetRenew, String iccids,String batchNo) throws MessageException;
}
