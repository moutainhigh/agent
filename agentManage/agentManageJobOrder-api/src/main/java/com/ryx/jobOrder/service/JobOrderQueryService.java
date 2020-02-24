package com.ryx.jobOrder.service;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.jobOrder.pojo.JoOrder;

import java.util.Map;

public interface JobOrderQueryService {

    PageInfo jobOrderQueryList(Map map, Page page);


    JoOrder getByJobId(String id);

}
