package com.ryx.credit.machine.service;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.machine.entity.ImsTermAdjustDetail;
import com.ryx.credit.pojo.admin.order.OLogistics;
import com.ryx.credit.pojo.admin.order.OLogisticsDetail;

import java.util.List;

/**
 * Created by RYX on 2018/10/11.
 */
public interface ImsTermAdjustDetailService {

    AgentResult insertImsTermAdjustDetail(OLogistics oLogistics,List<OLogisticsDetail> snList, ImsTermAdjustDetail imsTermAdjustDetail)throws MessageException;

}
