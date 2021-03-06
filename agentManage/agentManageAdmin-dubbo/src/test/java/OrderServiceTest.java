import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.service.agent.AgentFreezeService;
import com.ryx.credit.service.order.IPaymentDetailService;
import com.ryx.credit.service.order.OLogisticsService;
import com.ryx.credit.service.order.OrderRepairService;
import com.ryx.credit.service.order.OrderService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 作者：cx
 * 时间：2019/5/6
 * 描述：
 */
public class OrderServiceTest  extends BaseSpringTest  {

    private Logger logger = LoggerFactory.getLogger(OrderServiceTest.class);



    @Autowired
    private OLogisticsService oLogisticsService;
    @Autowired
    private OrderRepairService orderRepairService;
    @Autowired
    private IPaymentDetailService iPaymentDetailService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AgentFreezeService agentFreezeService;

    @Test
    public void testIdSn(){
        try {

               List<String> data =  oLogisticsService.idList("192JCE197001","192JCE1A1000");

        } catch (MessageException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void repairDumpOrderLogic(){
        try {
            orderRepairService.repairDumpOrderLogic(1,"OO20190215000000000002487");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPayKafka(){
        try {
            iPaymentDetailService.sendSFPayMentToPlatform("OD190827330409107");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void executeOrderAdj(){
        try {
            AgentResult agentResult = orderService.enableOrderAdjFinish("AD20200310000000000000709");
            logger.info("测试订单调整执行结果：{}", JSONObject.toJSON(agentResult));
        }catch (Exception e){
            e.toString();
        }

    }

    @Test
    public void test2(){
        AgentResult agentResult = agentFreezeService.queryAgentFreeze("AG19051970202");
        System.out.println(agentResult);
    }
}
