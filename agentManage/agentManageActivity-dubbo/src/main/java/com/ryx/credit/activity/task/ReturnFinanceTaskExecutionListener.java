package com.ryx.credit.activity.task;

import com.ryx.credit.activity.entity.ActIdUser;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.common.util.ThreadPool;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.service.ActIdUserService;
import com.ryx.credit.service.agent.DataChangeActivityService;
import com.ryx.credit.service.order.OSupplementService;
import com.ryx.credit.spring.MySpringContextHandler;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.task.IdentityLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * ReturnFinanceTaskExecutionListener
 * Created by IntelliJ IDEA.
 * 补款审批
 *
 * @author Wang Qi
 * @version 1.0 2018/7/19 11:37
 * @see ReturnFinanceTaskExecutionListener
 * To change this template use File | Settings | File Templates.
 */

public class ReturnFinanceTaskExecutionListener  extends BaseTaskListener implements TaskListener, ExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(ReturnFinanceTaskExecutionListener.class);


    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String eventName = delegateExecution.getEventName();
        if ("start".equals(eventName)) {
            logger.info("start=========" + "ActivityId:" + delegateExecution.getCurrentActivityId() + "  ProcessInstanceId:" + delegateExecution.getProcessInstanceId() + "  Execution:" + delegateExecution.getId());
        } else if ("end".equals(eventName)) {
            String activityName = delegateExecution.getCurrentActivityName();
            OSupplementService oSupplementService = (OSupplementService) MySpringContextHandler.applicationContext.getBean("oSupplementService");
            //数据变更服务类
            //审批拒绝
            if ("reject_end".equals(activityName)) {
                logger.info("=========ReturnFinanceTaskExecutionListener 流程{}eventName{}res{}", delegateExecution.getProcessInstanceId(), eventName, "");
                //更新业务流程关系表--状态
                 oSupplementService.updateByActivId(delegateExecution.getProcessInstanceId(),activityName);
            }
            //审批同意更新数据库
            if ("finish_end".equals(activityName)) {
                logger.info("=========ReturnFinanceTaskExecutionListener 流程{}eventName{}res{}", delegateExecution.getProcessInstanceId(), eventName, "");
                oSupplementService.updateByActivId(delegateExecution.getProcessInstanceId(),activityName);
            }
        } else if ("take".equals(eventName)) {
            logger.info("take=========" + "ActivityId:" + delegateExecution.getCurrentActivityId() + "  ProcessInstanceId:" + delegateExecution.getProcessInstanceId() + "  Execution:" + delegateExecution.getId());
        }
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        notity(delegateTask);
    }
}
