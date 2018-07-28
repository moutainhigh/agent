package com.ryx.credit.service.order;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.order.OReceiptOrder;
import com.ryx.credit.pojo.admin.order.OReceiptPro;
import com.ryx.credit.pojo.admin.order.ReceiptPlan;

/**
 * Created by RYX on 2018/7/20.
 */
public interface PlannerService {

    PageInfo queryPlannerList(OReceiptOrder receiptOrder, OReceiptPro receiptPro, Page page);

    AgentResult savePlanner(ReceiptPlan receiptPlan,String receiptProId) throws Exception;

}