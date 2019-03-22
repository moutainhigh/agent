package com.ryx.credit.activity.task;

import com.ryx.credit.activity.entity.ActIdUser;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.OrderType;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.common.util.ThreadPool;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.order.ORefundPriceDiff;
import com.ryx.credit.service.ActIdUserService;
import com.ryx.credit.service.agent.BusActRelService;
import com.ryx.credit.service.agent.DataChangeActivityService;
import com.ryx.credit.service.order.CompensateService;
import com.ryx.credit.service.order.OldCompensateService;
import com.ryx.credit.spring.MySpringContextHandler;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * CompensationTaskExecutionListener
 * Created by IntelliJ IDEA.
 *补退差价
 * @author Wang Qi
 * @version 1.0 2018/7/19 11:15
 * @see CompensationTaskExecutionListener
 * To change this template use File | Settings | File Templates.
 */

public class CompensationTaskExecutionListener implements TaskListener, ExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(CompensationTaskExecutionListener.class);


    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String eventName = delegateExecution.getEventName();
        if ("start".equals(eventName)) {
            logger.info("start=========" + "ActivityId:" + delegateExecution.getCurrentActivityId() + "  ProcessInstanceId:" + delegateExecution.getProcessInstanceId() + "  Execution:" + delegateExecution.getId());
        } else if ("end".equals(eventName)) {
            String activityName = delegateExecution.getCurrentActivityName();
            //数据变更服务类
            CompensateService dataChange = (CompensateService) MySpringContextHandler.applicationContext.getBean("compensateService");
            OldCompensateService oldCompensateService = (OldCompensateService) MySpringContextHandler.applicationContext.getBean("oldCompensateService");
            BusActRelService busActRelService = (BusActRelService) MySpringContextHandler.applicationContext.getBean("busActRelService");
            BusActRel busActRel = busActRelService.findByProIns(delegateExecution.getProcessInstanceId());
            ORefundPriceDiff oRefundPriceDiff = dataChange.selectByPrimaryKey(busActRel.getBusId());
            //审批拒绝
            if ("reject_end".equals(activityName)) {
                if(oRefundPriceDiff.getOrderType().compareTo(OrderType.NEW.getValue())==0){
                    AgentResult res = dataChange.compressCompensateActivity(delegateExecution.getProcessInstanceId(), AgStatus.Refuse.status);
                    logger.info("=========CompensationTaskExecutionListener 流程{}eventName{}res{}", delegateExecution.getProcessInstanceId(), eventName, res.getMsg());
                }else{
                    AgentResult res = oldCompensateService.compressCompensateActivity(delegateExecution.getProcessInstanceId(), AgStatus.Refuse.status);
                }
            }
            //审批同意更新数据库
            if ("finish_end".equals(activityName)) {
                if(oRefundPriceDiff.getOrderType().compareTo(OrderType.NEW.getValue())==0){
                    AgentResult res = dataChange.compressCompensateActivity(delegateExecution.getProcessInstanceId(), AgStatus.Approved.status);
                    logger.info("=========CompensationTaskExecutionListener 流程{}eventName{}res{}", delegateExecution.getProcessInstanceId(), eventName, res.getMsg());
                }else{
                    AgentResult res = oldCompensateService.compressCompensateActivity(delegateExecution.getProcessInstanceId(), AgStatus.Refuse.status);
                }
            }
        } else if ("take".equals(eventName)) {
            logger.info("take=========" + "ActivityId:" + delegateExecution.getCurrentActivityId() + "  ProcessInstanceId:" + delegateExecution.getProcessInstanceId() + "  Execution:" + delegateExecution.getId());
        }
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        String eventName = delegateTask.getEventName();
        if ("create".endsWith(eventName)) {
            ThreadPool.putThreadPool(() -> {
                ThreadLocal<String> threadLocal = new ThreadLocal<>();
                threadLocal.set(delegateTask.getId());
                try {
                    Thread.sleep(10000L);
                } catch (InterruptedException e) {
                    logger.error("Thread error");
                }
                ActIdUserService actIdUserService = (ActIdUserService) MySpringContextHandler.applicationContext.getBean("actIdUserService");
                List<ActIdUser> actIdUserList = actIdUserService.selectByTaskId(threadLocal.get());
                String[] emails = new String[actIdUserList.size()];
                int i = 0;
                for (ActIdUser actIdUser : actIdUserList) {
                    emails[i++] = (String) actIdUser.getEmail();
                }
                AppConfig.sendEmail(emails, "name:" + delegateTask.getName() + "  ProcessInstanceId:" + delegateTask.getProcessInstanceId() + "  task:" + delegateTask.getId(), "审批任务通知" + eventName);

            });
            logger.info("create=========" + "ProcessDefinition:" + delegateTask.getProcessDefinitionId() + "  ProcessInstanceId:" + delegateTask.getProcessInstanceId() + "  task:" + delegateTask.getId());
        } else if ("assignment".endsWith(eventName)) {
            AppConfig.sendEmails("ProcessDefinition:" + delegateTask.getProcessDefinitionId() + "  ProcessInstanceId:" + delegateTask.getProcessInstanceId() + "  task:" + delegateTask.getId(), "task工作流通知" + eventName);
            logger.info("assignment========" + "ProcessDefinition:" + delegateTask.getProcessDefinitionId() + "  ProcessInstanceId:" + delegateTask.getProcessInstanceId() + "  task:" + delegateTask.getId());
        } else if ("complete".endsWith(eventName)) {
            AppConfig.sendEmails("name:" + delegateTask.getName() + "  ProcessDefinition:" + delegateTask.getProcessDefinitionId() + "  ProcessInstanceId:" + delegateTask.getProcessInstanceId() + "  task:" + delegateTask.getId(), "task工作流通知" + eventName);
            logger.info("complete===========" + "ProcessDefinition:" + delegateTask.getProcessDefinitionId() + "  ProcessInstanceId:" + delegateTask.getProcessInstanceId() + "  task:" + delegateTask.getId());
        } else if ("delete".endsWith(eventName)) {
            logger.info("delete=============");
        }
    }
}
