package com.ryx.credit.service.impl.order;

import com.alibaba.druid.sql.visitor.functions.If;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.activity.entity.ActRuTask;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.common.util.agentUtil.StageUtil;
import com.ryx.credit.commons.result.Result;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.*;
import com.ryx.credit.service.ActRuTaskService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IResourceService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.*;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ryx.credit.common.enumc.OffsetPaytype.DDBK;
import static com.ryx.credit.common.enumc.OffsetPaytype.DDTZ;
import static java.util.stream.Collectors.toList;

/**
 * Created by RYX on 2018/7/13.
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OOrderMapper orderMapper;
    @Autowired
    private CapitalMapper capitalMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private OSubOrderMapper oSubOrderMapper;
    @Autowired
    private OSubOrderActivityMapper oSubOrderActivityMapper;
    @Autowired
    private OPaymentMapper oPaymentMapper;
    @Autowired
    private OPaymentDetailMapper oPaymentDetailMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private OProductMapper oProductMapper;
    @Autowired
    private OAddressMapper oAddressMapper;
    @Autowired
    private OReceiptOrderMapper oReceiptOrderMapper;
    @Autowired
    private OReceiptProMapper oReceiptProMapper;
    @Autowired
    private AttachmentRelMapper attachmentRelMapper;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private BusActRelService busActRelService;
    @Autowired
    private OActivityMapper oActivityMapper;
    @Autowired
    private ApaycompService apaycompService;
    @Autowired
    private AgentQueryService agentQueryService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private AgentDataHistoryService agentDataHistoryService;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private OCashReceivablesService oCashReceivablesService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private CapitalService capitalService;
    @Autowired
    private IResourceService iResourceService;
    @Autowired
    private PlatFormMapper platFormMapper;
    @Autowired
    private ReceiptPlanMapper receiptPlanMapper;
    @Autowired
    private AgentService agentService;
    @Autowired
    private CashSummaryMouthMapper cashSummaryMouthMapper;
    @Autowired
    private ActRuTaskService actRuTaskService;
    @Autowired
    private IPaymentDetailService paymentDetailService;
    @Autowired
    private ORemoveAccountMapper oRemoveAccountMapper;
    @Autowired
    private OrderAdjMapper orderAdjMapper;
    @Autowired
    private OrderAdjDetailMapper orderAdjDetailMapper;
    @Autowired
    private ORefundPriceDiffMapper refundPriceDiffMapper;
    @Autowired
    private OPaymentDetailMapper paymentDetailMapper;
    @Autowired
    DataHistoryMapper dataHistoryMapper;
    @Autowired
    private OPaymentMapper paymentMapper;
    @Autowired
    private OAccountAdjustMapper accountAdjustMapper;
    @Autowired
    private OAccountAdjustDetailMapper accountAdjustDetailMapper;
    @Autowired
    private ORefundAgentMapper refundAgentMapper;
    @Autowired
    private OSupplementMapper oSupplementMapper;
    @Autowired
    private ODeductCapitalMapper deductCapitalMapper;
    @Autowired
    private SettleAccountsMapper settleAccountsMapper;
    @Resource(name = "redisService")
    private RedisService  redisService;
    @Autowired
    private IAccountAdjustService accountAdjustService;
    @Autowired
    private AgentColinfoMapper agentColinfoMapper;
    @Autowired
    private OrderOffsetService orderOffsetService;
    /**
     * 分页查询订单列表
     * @param product
     * @param page
     * @return
     */
//    @Override
//    public PageInfo orderList(OOrder product, Page page) {
//
//        OOrderExample example = new OOrderExample();
//        OOrderExample.Criteria criteria = example.createCriteria();
//
//        example.setPage(page);
//        example.setOrderByClause(" c_time desc ");
//        List<OOrder> oOrders = orderMapper.selectByExample(example);
//        PageInfo pageInfo = new PageInfo();
//        pageInfo.setRows(oOrders);
//        pageInfo.setTotal(orderMapper.countByExample(example));
//        return pageInfo;
//    }

    /**
     * 根据ID查询订单
     * @param orderId
     * @return
     */
    @Override
    public OOrder getById(String orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }

    /**
     * 分页查询订单列表
     * 公司内部查看订单
     * @param par
     * @param page
     * @return
     */
    @Override
    public PageInfo orderList(Map par, Page page) {
        PageInfo pageInfo = new PageInfo();
        par.put("page", page);
        if(null!=par.get("userId")) {
            Long userId = (Long) par.get("userId");
            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
            if (orgCodeRes == null && orgCodeRes.size() != 1) {
                return null;
            }
            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
            String organizationCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
            par.put("organizationCode", organizationCode);

            List<Map> platfromPerm = iResourceService.userHasPlatfromPerm(userId);
            par.put("platfromPerm", platfromPerm);
        }
        pageInfo.setTotal(orderMapper.queryOrderListViewCount(par));
        pageInfo.setRows(orderMapper.queryOrderListView(par));
        return pageInfo;
    }

    /**
     * 查询所有订单
     * 查看所有订单
     *
     * @param par
     * @param page
     * @return
     */
    @Override
    public PageInfo allOderList(Map par, Page page) {
        PageInfo pageInfo = new PageInfo();
        par.put("page", page);
        if(null!=par.get("userId")) {
            Long userId = (Long) par.get("userId");
            List<Map> platfromPerm = iResourceService.userHasPlatfromPerm(userId);
            par.put("platfromPerm", platfromPerm);
        }
        pageInfo.setTotal(orderMapper.queryAllOrderListViewCount(par));
        pageInfo.setRows(orderMapper.queryAllOrderListView(par));
        return pageInfo;
    }

    /**
     * 查询代理商订单
     *
     * @param par
     * @param page
     * @return
     */
    @Override
    public PageInfo agentOderList(Map par, Page page) {
        PageInfo pageInfo = new PageInfo();
        if (par == null) return pageInfo;
        if (par.get("agentId") == null) return pageInfo;
        if (StringUtils.isBlank(par.get("agentId").toString())) return pageInfo;
        if(null!=par.get("userId")) {
            Long userId = (Long) par.get("userId");
            List<Map> platfromPerm = iResourceService.userHasPlatfromPerm(userId);
            par.put("platfromPerm", platfromPerm);
        }
        par.put("page", page);
        pageInfo.setTotal(orderMapper.queryAllOrderListViewCount(par));
        pageInfo.setRows(orderMapper.queryAllOrderListView(par));
        return pageInfo;
    }

    /**
     * 查询给定条件的订单的payment
     *
     * @param agentId
     * @param approveStatus
     * @param orderStatus
     * @return
     */
    @Override
    public List<OPayment> queryApprovePayment(String agentId, BigDecimal approveStatus, List<BigDecimal> orderStatus) {
        OOrderExample example = new OOrderExample();
        example.or()
                .andStatusEqualTo(Status.STATUS_1.status)
                .andAgentIdEqualTo(agentId)
                .andReviewStatusEqualTo(approveStatus)
                .andOrderStatusIn(orderStatus);
        List<OOrder> orders = orderMapper.selectByExample(example);
        List<String> ids = orders.stream().map(OOrder::getId).collect(toList());
        OPaymentExample oPaymentExample = new OPaymentExample();
        OPaymentExample.Criteria c = oPaymentExample.or().andStatusEqualTo(Status.STATUS_1.status);
        if(ids.size()>0){
            c.andOrderIdIn(ids);
            return oPaymentMapper.selectByExample(oPaymentExample);
        }else{
            return new ArrayList<>();
        }

    }

    /**
     * 订单构建
     *
     * @param orderFormVo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult buildOrder(OrderFormVo orderFormVo, String userId) throws Exception {
        logger.info("用户[{}]订单构建[{}]",userId, JSONObject.toJSONString(orderFormVo));
        if (StringUtils.isBlank(orderFormVo.getAgentId())) {
            return AgentResult.fail("请选择代理商");
        }
        if (StringUtils.isBlank(orderFormVo.getOrderPlatform())) {
            return AgentResult.fail("请选择平台");
        }
        if (orderFormVo.getoSubOrder() == null || orderFormVo.getoSubOrder().size() == 0) {
            return AgentResult.fail("请选择商品");
        }
        orderFormVo.setUserId(userId);
        //保存订单数据
        orderFormVo = setOrderFormValue(orderFormVo, userId);
        //是否启动流程
        if(org.apache.commons.lang.StringUtils.isNotEmpty(orderFormVo.getIsApproveWhenSubmit()) && "1".equals(orderFormVo.getIsApproveWhenSubmit())){
            //启动流程审批
            AgentResult agentResult = startOrderActiviy(orderFormVo.getId(),userId);
            if(!agentResult.isOK()){
                throw new Exception(agentResult.getMsg());
            }
        }


        //添加到数据历史表
        agentDataHistoryService.saveDataHistory(orderFormVo, DataHistoryType.ORDER.getValue());
        return AgentResult.ok(orderFormVo.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult updateOrder(OrderFormVo orderFormVo, String userId) throws Exception {
        if (StringUtils.isBlank(orderFormVo.getAgentId())) {
            return AgentResult.fail("请选择代理商");
        }
        if (StringUtils.isBlank(orderFormVo.getOrderPlatform())) {
            return AgentResult.fail("请选择平台");
        }
        if (orderFormVo.getoSubOrder() == null || orderFormVo.getoSubOrder().size() == 0) {
            return AgentResult.fail("请选择商品");
        }
        if (StringUtils.isBlank(orderFormVo.getId())) {
            return AgentResult.fail("订单ID不能为空");
        }
        orderFormVo.setUserId(userId);
        //保存订单数据
        orderFormVo = updateOrderFormValue(orderFormVo, userId);
        agentDataHistoryService.saveDataHistory(orderFormVo, DataHistoryType.ORDER.getValue());
        return AgentResult.ok(orderFormVo.getId());
    }

    @Override
    public AgentResult checkDownPaymentDate(Date date) {

        Calendar c = Calendar.getInstance();
        //当前日
        int day = c.get(Calendar.DAY_OF_MONTH);

        //三个月后
        c.add(Calendar.MONTH, 3);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR, c.getActualMinimum(Calendar.HOUR));
        c.set(Calendar.MINUTE, c.getActualMinimum(Calendar.MINUTE));
        c.set(Calendar.SECOND, c.getActualMinimum(Calendar.SECOND));
        c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));
        Date threeMDay = c.getTime();

        //用户选择
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR, c.getActualMinimum(Calendar.HOUR));
        c.set(Calendar.MINUTE, c.getActualMinimum(Calendar.MINUTE));
        c.set(Calendar.SECOND, c.getActualMinimum(Calendar.SECOND));
        c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));
        Date selDay = c.getTime();

        c.setTime(new Date());
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR, c.getActualMinimum(Calendar.HOUR));
        c.set(Calendar.MINUTE, c.getActualMinimum(Calendar.MINUTE));
        c.set(Calendar.SECOND, c.getActualMinimum(Calendar.SECOND));
        c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));
        Date current = c.getTime();

        if (threeMDay.compareTo(selDay) < 0) {
            return AgentResult.fail("分期日超出三个月");
        }

//        if (day > 5) {
//            if (current.compareTo(selDay) == 0) {
//                return AgentResult.fail("5号以后只能选择下月开始分期");
//            }
//        }
        return AgentResult.ok();
    }

    /**
     * 根据支付类型初始化付款单参数
     *
     * @param agentVo
     * @return
     */
    @Override
    public OPayment initPayment(OrderFormVo agentVo) throws MessageException {
        OPayment payment = agentVo.getoPayment();
        Calendar c= Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH,c.getActualMinimum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY,c.getActualMinimum(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MILLISECOND,c.getActualMinimum(Calendar.MILLISECOND));
        c.set(Calendar.SECOND,c.getActualMinimum(Calendar.SECOND));
        c.set(Calendar.MINUTE,c.getActualMinimum(Calendar.MINUTE));
        Date dateFromStr = DateUtil.getDateFromStr(DateUtil.format(c.getTime(), "yyyy-MM-dd"), "yyyy-MM-dd");
        if(StringUtils.isNotBlank(payment.getCustomStaging()) && !payment.getCustomStaging().equals(Status.STATUS_1.status.toPlainString()) && !payment.getCustomStaging().equals(Status.STATUS_0.status.toPlainString()) ){
            throw new MessageException("是否自定义分期状态异常");
        }
        switch (payment.getPayMethod()) {
            case "SF1"://首付+分润分期
                if (payment.getDownPayment() == null || payment.getDownPayment().compareTo(BigDecimal.ZERO) == 0) {
                    throw new MessageException("请填写首付金额");
                }
                // cxinfo 日期校验
                if (payment.getDownPaymentDate() == null || payment.getDownPaymentDate().compareTo(dateFromStr) < 0) {
                    throw new MessageException("分期日期有误");
                }
                if (payment.getDownPaymentCount() == null || payment.getDownPaymentCount().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new MessageException("分期期数有误");
                }else{
                    //分期情况下自定义分期校验
                    if(Status.STATUS_1.status.toString().equals(payment.getCustomStaging())){
                        //检查分期数据
                        if(agentVo.getCustomStagingUser()==null || (agentVo.getCustomStagingUser().size()!=payment.getDownPaymentCount().intValue()) ){
                            throw new MessageException("分期期数和自定义分期期数不对等");
                        }
                    }
                }
                // cxinfo 修改为多条打款信息
                List<OCashReceivablesVo> cash = agentVo.getoCashReceivables();
                if (cash==null || cash.size()== 0) {
                    throw new MessageException("首付+分润分期支付方式，必须添加打款项");
                }
                if (payment.getDownPayment() != null && payment.getPayAmount()!=null && payment.getDownPayment().compareTo(payment.getPayAmount()) >= 0) {
                    throw new MessageException("首付+分润分期支付方式，首付不能大于等于订单金额");
                }
                if (agentVo.getAttachments().size()==0) {
                    throw new MessageException("打款截图不能为空");
                }
                AgentResult SF1_checkDownPaymentDateres = checkDownPaymentDate(payment.getDownPaymentDate());
                if (!SF1_checkDownPaymentDateres.isOK()) {
                    throw new MessageException(SF1_checkDownPaymentDateres.getMsg());
                }
                return payment;
            case "SF2"://首付+打款分期
                if (payment.getDownPayment() == null || payment.getDownPayment().compareTo(BigDecimal.ZERO) == 0) {
                    throw new MessageException("请填写首付金额");
                }
                // cxinfo 日期校验
                if (payment.getDownPaymentDate() == null || payment.getDownPaymentDate().compareTo(dateFromStr) < 0) {
                    throw new MessageException("分期日期有误");
                }
                if (payment.getDownPaymentCount() == null || payment.getDownPaymentCount().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new MessageException("分期期数有误");
                }else{
                    //分期情况下自定义分期校验
                    if(Status.STATUS_1.status.toString().equals(payment.getCustomStaging())){
                        //检查分期数据
                        if(agentVo.getCustomStagingUser()==null || (agentVo.getCustomStagingUser().size()!=payment.getDownPaymentCount().intValue()) ){
                            throw new MessageException("分期期数和自定义分期期数不对等");
                        }
                    }
                }
                // cxinfo 修改为多条打款信息
                List<OCashReceivablesVo> cash_sf2 = agentVo.getoCashReceivables();
                if (cash_sf2==null || cash_sf2.size()== 0) {
                    throw new MessageException("首付+打款分期支付方式，必须添加打款项");
                }
                if (payment.getDownPayment() != null && payment.getPayAmount()!=null && payment.getDownPayment().compareTo(payment.getPayAmount()) >= 0) {
                    throw new MessageException("首付+打款分期支付方式，首付不能大于等于订单金额");
                }

                if (agentVo.getAttachments().size()==0) {
                    throw new MessageException("打款截图不能为空");
                }
                AgentResult SF2_checkDownPaymentDateres = checkDownPaymentDate(payment.getDownPaymentDate());
                if (!SF2_checkDownPaymentDateres.isOK()) {
                    throw new MessageException(SF2_checkDownPaymentDateres.getMsg());
                }
                return payment;
            case "FKFQ"://打款分期
                if (payment.getDownPaymentDate() == null || payment.getDownPaymentDate().compareTo(dateFromStr) < 0) {
                    throw new MessageException("分期日期有误");
                }
                if (payment.getDownPaymentCount() == null || payment.getDownPaymentCount().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new MessageException("分期期数有误");
                }else{
                    //分期情况下自定义分期校验
                    if(Status.STATUS_1.status.toString().equals(payment.getCustomStaging())){
                        //检查分期数据
                        if(agentVo.getCustomStagingUser()==null || (agentVo.getCustomStagingUser().size()!=payment.getDownPaymentCount().intValue()) ){
                            throw new MessageException("分期期数和自定义分期期数不对等");
                        }
                    }
                }
                List<OCashReceivablesVo> cash_FKFQ = agentVo.getoCashReceivables();
                if (cash_FKFQ==null || cash_FKFQ.size()> 0) {
                    agentVo.setoCashReceivables(new ArrayList<>());
                }

                if (payment.getDownPaymentCount() == null || payment.getDownPaymentCount().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new MessageException("分期期数有误");
                }
                payment.setDownPayment(BigDecimal.ZERO);
                payment.setActualReceipt(BigDecimal.ZERO);
                return payment;
            case "FRFQ"://分润分期
                if (payment.getDownPaymentDate() == null || payment.getDownPaymentDate().compareTo(dateFromStr) < 0) {
                    throw new MessageException("分期日期有误");
                }
                if (payment.getDownPaymentCount() == null || payment.getDownPaymentCount().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new MessageException("分期期数有误");
                }else{
                    //分期情况下自定义分期校验
                    if(Status.STATUS_1.status.toString().equals(payment.getCustomStaging())){
                        //检查分期数据
                        if(agentVo.getCustomStagingUser()==null || (agentVo.getCustomStagingUser().size()!=payment.getDownPaymentCount().intValue()) ){
                            throw new MessageException("分期期数和自定义分期期数不对等");
                        }
                    }
                }
                List<OCashReceivablesVo> cash_FRFQ = agentVo.getoCashReceivables();
                if (cash_FRFQ==null || cash_FRFQ.size()> 0) {
                    agentVo.setoCashReceivables(new ArrayList<>());
                }

                if (payment.getDownPaymentCount() == null || payment.getDownPaymentCount().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new MessageException("分期期数有误");
                }
                payment.setDownPayment(BigDecimal.ZERO);
                payment.setActualReceipt(BigDecimal.ZERO);
                return payment;
            case "XXDK"://线下打款
                //分期情况下自定义分期校验
                if(Status.STATUS_1.status.toString().equals(payment.getCustomStaging())){
                    throw new MessageException("线下打款不支持自定义分期");
                }
                // cxinfo 修改为多条打款信息
                List<OCashReceivablesVo> cash_XXDK = agentVo.getoCashReceivables();
                if (cash_XXDK==null || cash_XXDK.size()== 0) {
                    throw new MessageException("线下打款支付方式，必须添加打款项");
                }
                if (agentVo.getAttachments().size()==0) {
                    throw new MessageException("打款截图不能为空");
                }
                payment.setDownPayment(BigDecimal.ZERO);
                payment.setDownPaymentDate(null);
                payment.setDownPaymentCount(BigDecimal.ZERO);
                payment.setCustomStaging(Status.STATUS_0.status.toPlainString());
                return payment;
            case "QT"://其他
                if(Status.STATUS_1.status.toString().equals(payment.getCustomStaging())){
                    throw new MessageException("该支付方式不支持自定义分期");
                }
                // cxinfo 修改为多条打款信息
                List<OCashReceivablesVo> cash_QT = agentVo.getoCashReceivables();
                if (cash_QT==null || cash_QT.size()== 0) {
                    agentVo.setoCashReceivables(new ArrayList<>());
                }
                payment.setDownPayment(BigDecimal.ZERO);
                payment.setDownPaymentDate(null);
                payment.setDownPaymentCount(BigDecimal.ZERO);
                payment.setActualReceipt(BigDecimal.ZERO);
                payment.setCustomStaging(Status.STATUS_0.status.toPlainString());
                return payment;
        }
        return payment;
    }

    /**
     * 生成自定义分期
     * @param paymentId, paymentMethod, data
     * @return
     * @throws MessageException
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public boolean initPaymentDetail(String paymentId,String paymentMethod,List<String> data) throws MessageException {
        Date da = Calendar.getInstance().getTime();
        OPayment payment = oPaymentMapper.selectByPrimaryKey(paymentId);
        //记录分期金额
        BigDecimal fqje = BigDecimal.ZERO;
        //将已生成的分期删除重新生成分期
        OPaymentDetailExample example = new OPaymentDetailExample();
        example.or().andOrderIdEqualTo(payment.getOrderId());
        oPaymentDetailMapper.deleteByExample(example);
        logger.info("生成自定义分期 删除付款明细 {}"+payment.getOrderId());
        if(StringUtils.isNotBlank(payment.getCustomStaging()) && Status.STATUS_1.status.toString().equals(payment.getCustomStaging())) {
            //初始化生成明细
            switch (paymentMethod) {
                case "FKFQ":
                    //生成自定义预分期数据
                    if (StringUtils.isNotBlank(payment.getCustomStaging()) && Status.STATUS_1.status.toString().equals(payment.getCustomStaging())) {
                        Date DownPaymentDate = payment.getDownPaymentDate();
                        Calendar c = Calendar.getInstance();
                        c.setTime(DownPaymentDate);

                        for (int i = 0; i < data.size(); i++) {

                            c.add(Calendar.MONTH,1);

                            String amount = data.get(i);
                            OPaymentDetail record = new OPaymentDetail();
                            record.setId(idService.genId(TabId.o_payment_detail));
                            record.setBatchCode(da.getTime()+"");
                            record.setPaymentId(payment.getId());
                            record.setPaymentType(PamentIdType.ORDER_FKD.code);
                            record.setOrderId(payment.getOrderId());
                            record.setPayType(PaymentType.DKFQ.code);
                            record.setPayAmount(new BigDecimal(amount));
                            record.setRealPayAmount(BigDecimal.ZERO);
                            record.setPlanPayTime(c.getTime());
                            record.setPlanNum(new BigDecimal(i).add(BigDecimal.ONE));
                            record.setAgentId(payment.getAgentId());
                            record.setPaymentStatus(PaymentStatus.DS.code);
                            record.setcUser(payment.getUserId());
                            record.setcDate(da);
                            record.setStatus(Status.STATUS_1.status);
                            record.setVersion(Status.STATUS_1.status);
                            if (1 != oPaymentDetailMapper.insert(record)) {
                                throw new MessageException("分期处理");
                            }
                            fqje = fqje.add(record.getPayAmount());

                        }
                    }
                    break;
                case "FRFQ":
                    //生成自定义预分期数据
                    if (StringUtils.isNotBlank(payment.getCustomStaging()) && Status.STATUS_1.status.toString().equals(payment.getCustomStaging())) {
                        Date DownPaymentDate = payment.getDownPaymentDate();
                        Calendar c = Calendar.getInstance();
                        c.setTime(DownPaymentDate);

                        for (int i = 0; i < data.size(); i++) {

                            c.add(Calendar.MONTH,1);

                            String amount = data.get(i);
                            OPaymentDetail record = new OPaymentDetail();
                            record.setId(idService.genId(TabId.o_payment_detail));
                            record.setBatchCode(da.getTime()+"");
                            record.setPaymentId(payment.getId());
                            record.setPaymentType(PamentIdType.ORDER_FKD.code);
                            record.setOrderId(payment.getOrderId());
                            record.setPayType(PaymentType.FRFQ.code);
                            record.setPayAmount(new BigDecimal(amount));
                            record.setRealPayAmount(BigDecimal.ZERO);
                            record.setPlanPayTime(c.getTime());
                            record.setPlanNum(new BigDecimal(i).add(BigDecimal.ONE));
                            record.setAgentId(payment.getAgentId());
                            record.setPaymentStatus(PaymentStatus.DS.code);
                            record.setcUser(payment.getUserId());
                            record.setcDate(da);
                            record.setStatus(Status.STATUS_1.status);
                            record.setVersion(Status.STATUS_1.status);
                            if (1 != oPaymentDetailMapper.insert(record)) {
                                throw new MessageException("分期处理");
                            }
                            fqje = fqje.add(record.getPayAmount());
                        }
                    }
                    break;
                case "XXDK":
                    if (StringUtils.isNotBlank(payment.getCustomStaging()) && Status.STATUS_1.status.toString().equals(payment.getCustomStaging())) {
                        throw new MessageException("线下打款不支持自定义分期");
                    }
                    break;
                case "SF1"://首付+分润分期
                    if (StringUtils.isNotBlank(payment.getCustomStaging()) && Status.STATUS_1.status.toString().equals(payment.getCustomStaging())) {
                        Date DownPaymentDate = payment.getDownPaymentDate();
                        Calendar c = Calendar.getInstance();
                        c.setTime(DownPaymentDate);

                        for (int i = 0; i < data.size(); i++) {

                            c.add(Calendar.MONTH,1);

                            String amount = data.get(i);
                            OPaymentDetail record = new OPaymentDetail();
                            record.setId(idService.genId(TabId.o_payment_detail));
                            record.setBatchCode(da.getTime()+"");
                            record.setPaymentId(payment.getId());
                            record.setPaymentType(PamentIdType.ORDER_FKD.code);
                            record.setOrderId(payment.getOrderId());
                            record.setPayType(PaymentType.FRFQ.code);
                            record.setPayAmount(new BigDecimal(amount));
                            record.setRealPayAmount(BigDecimal.ZERO);
                            record.setPlanPayTime(c.getTime());
                            record.setPlanNum(new BigDecimal(i).add(BigDecimal.ONE));
                            record.setAgentId(payment.getAgentId());
                            record.setPaymentStatus(PaymentStatus.DS.code);
                            record.setcUser(payment.getUserId());
                            record.setcDate(da);
                            record.setStatus(Status.STATUS_1.status);
                            record.setVersion(Status.STATUS_1.status);
                            if (1 != oPaymentDetailMapper.insert(record)) {
                                throw new MessageException("分期处理");
                            }
                            fqje = fqje.add(record.getPayAmount());
                        }
                    }
                    break;
                case "SF2"://打款分期
                    if (StringUtils.isNotBlank(payment.getCustomStaging()) && Status.STATUS_1.status.toString().equals(payment.getCustomStaging())) {
                        Date DownPaymentDate = payment.getDownPaymentDate();
                        Calendar c = Calendar.getInstance();
                        c.setTime(DownPaymentDate);
                        for (int i = 0; i < data.size(); i++) {
                            c.add(Calendar.MONTH,1);
                            String amount = data.get(i);
                            OPaymentDetail record = new OPaymentDetail();
                            record.setId(idService.genId(TabId.o_payment_detail));
                            record.setBatchCode(da.getTime()+"");
                            record.setPaymentId(payment.getId());
                            record.setPaymentType(PamentIdType.ORDER_FKD.code);
                            record.setOrderId(payment.getOrderId());
                            record.setPayType(PaymentType.DKFQ.code);
                            record.setPayAmount(new BigDecimal(amount));
                            record.setRealPayAmount(BigDecimal.ZERO);
                            record.setPlanPayTime(c.getTime());
                            record.setPlanNum(new BigDecimal(i).add(BigDecimal.ONE));
                            record.setAgentId(payment.getAgentId());
                            record.setPaymentStatus(PaymentStatus.DS.code);
                            record.setcUser(payment.getUserId());
                            record.setcDate(da);
                            record.setStatus(Status.STATUS_1.status);
                            record.setVersion(Status.STATUS_1.status);
                            if (1 != oPaymentDetailMapper.insert(record)) {
                                throw new MessageException("分期处理");
                            }
                            fqje = fqje.add(record.getPayAmount());
                        }
                    }
                    break;
                case "QT"://抵扣金额必须等于待付金额
                    if (StringUtils.isNotBlank(payment.getCustomStaging()) && Status.STATUS_1.status.toString().equals(payment.getCustomStaging())) {
                        throw new MessageException("其他付款方式不支持自定义分期");
                    }
                    break;
            }

            if(payment.getDeductionAmount()==null){
                payment.setDeductionAmount(BigDecimal.ZERO);
            }
            if(payment.getDownPayment()==null){
                payment.setDownPayment(BigDecimal.ZERO);
            }
            //TODO 检查付款明细及打款信息是否和订单金额一致 订单应付金额 = 分期金额+首付金额+抵扣金额
            if(payment.getPayAmount().compareTo(payment.getDownPayment().add(fqje))!=0){
                throw new MessageException("分期金额配置失败:应为"+ payment.getPayAmount().subtract(payment.getDownPayment()));
            }
        }
        return true;
    }

    /**
     * 订单form表单填充并入库
     *
     * @param orderFormVo
     * @param userId
     * @return
     */
    private OrderFormVo setOrderFormValue(OrderFormVo orderFormVo, String userId) throws Exception {
        logger.info("下订单:{}{}", userId, orderFormVo.getAgentId());
        //订单基础数据
        Date d = Calendar.getInstance().getTime();
        orderFormVo.setId(idService.genOrderId(TabId.o_order, Integer.valueOf(userId)));
        orderFormVo.setoNum(orderFormVo.getId());
        orderFormVo.setoApytime(orderFormVo.getcTime());
        orderFormVo.setUserId(userId);
        orderFormVo.setPayAmo(orderFormVo.getoAmo());
        orderFormVo.setReviewStatus(AgStatus.Create.status);
        orderFormVo.setOrderStatus(OrderStatus.CREATE.status);
        orderFormVo.setClearStatus(Status.STATUS_0.status);
        orderFormVo.setStatus(Status.STATUS_1.status);
        orderFormVo.setcTime(d);
        orderFormVo.setuUser(userId);
        orderFormVo.setuTime(d);
        orderFormVo.setVersion(Status.STATUS_0.status);

        //平台信息错误
        String orderPatform = orderFormVo.getOrderPlatform();
        if(StringUtils.isNotBlank(orderPatform)){
            AgentBusInfo ainfo =  agentBusinfoService.getById(orderPatform);
            if(ainfo!=null && ainfo.getAgentId().equals(orderFormVo.getAgentId())) {
                orderFormVo.setOrderPlatform(ainfo.getBusPlatform());
                orderFormVo.setBusId(ainfo.getId());
            }else{
                logger.info("下订单:{}{}",orderPatform, "平台数据错误");
                throw new MessageException("平台数据错误");
            }
        }

        //支付方式
        OPayment oPayment = orderFormVo.getoPayment();
        oPayment.setId(idService.genId(TabId.o_payment));
        oPayment.setUserId(userId);
        oPayment.setOrderId(orderFormVo.getId());
        oPayment.setAgentId(orderFormVo.getAgentId());
        oPayment.setcTime(d);
        oPayment.setRealAmount(Status.STATUS_0.status);//已付金额
        oPayment.setPayStatus(PayStatus.NON_PAYMENT.code);
        oPayment.setStatus(Status.STATUS_1.status);
        if (StringUtils.isBlank(orderFormVo.getPaymentMethod())) {
            logger.info("下订单:{}", "商品价格数据错误");
            throw new MessageException("付款方式不能为空");
        }
        //初始化数据
        oPayment = initPayment(orderFormVo);
        //订单总金额
        BigDecimal forPayAmount = new BigDecimal(0);
        //订单应付金额
        BigDecimal forRealPayAmount = new BigDecimal(0);

        //子订单接口 计算整个订单数据
        List<OSubOrder> OSubOrders = orderFormVo.getoSubOrder();
        if (OSubOrders == null || OSubOrders.size() == 0) {
            logger.info("下订单:{}", "请选择商品");
            throw new MessageException("请选择商品");
        }



        for (OSubOrder oSubOrder : OSubOrders) {
            oSubOrder.setId(idService.genId(TabId.o_sub_order));
            OProduct product = oProductMapper.selectByPrimaryKey(oSubOrder.getProId());

            if (oSubOrder.getProNum() == null || oSubOrder.getProNum().compareTo(BigDecimal.ZERO) <= 0) {
                logger.info("下订单:{}", "商品数量错误");
                throw new MessageException("商品数量错误");
            }
            int proNum = oSubOrder.getProNum().intValue();
            oSubOrder.setOrderId(orderFormVo.getId());
            oSubOrder.setProCode(product.getProCode());
            oSubOrder.setProName(product.getProName());
            oSubOrder.setProType(product.getProType());
            oSubOrder.setSendNum(Status.STATUS_0.status);
            oSubOrder.setIsDeposit(product.getIsDeposit());
            oSubOrder.setDeposit(product.getDeposit());
            oSubOrder.setModel(product.getProModel());
            oSubOrder.setcTime(d);
            oSubOrder.setuUser(userId);
            oSubOrder.setcUser(userId);
            oSubOrder.setuTime(d);
            oSubOrder.setStatus(Status.STATUS_1.status);
            oSubOrder.setVersion(Status.STATUS_0.status);
            oSubOrder.setAgentId(orderFormVo.getAgentId());
            //商品参加的活动
            String oActivity = oSubOrder.getActivity();
            if (StringUtils.isNotBlank(oActivity)) {
                OActivity activity = oActivityMapper.selectByPrimaryKey(oActivity);
                if (activity != null && activity.getPrice() != null && activity.getPrice().compareTo(BigDecimal.ZERO) >= 0) {

                    if (activity.getOriginalPrice()==null || activity.getOriginalPrice().compareTo(BigDecimal.ZERO) < 0) {
                        logger.info("下订单:{}", "活动原价不能小于0");
                        throw new MessageException("活动原价不能小于0");
                    }
                    if (null == activity.getQuantityLimit() || activity.getQuantityLimit() < 1) {
                        throw new MessageException("请补全活动名称为：" + activity.getActivityName() + "的订货数量下限！");
                    }
                    if (proNum < activity.getQuantityLimit()) {
                        throw new MessageException("活动：" + activity.getActivityName() + "，最低订货数量为" + activity.getQuantityLimit() + "台");
                    }

                    oSubOrder.setProPrice(activity.getOriginalPrice());
                    oSubOrder.setProRelPrice(activity.getPrice());
                    //cxinfo 订单添加  添加原价字段变更 活动商品临时表同步商品 原价 xx
                    OSubOrderActivity oSubOrderActivity = new OSubOrderActivity();
                    oSubOrderActivity.setId(idService.genId(TabId.o_sub_order_activity));
                    oSubOrderActivity.setActivityId(activity.getId());
                    oSubOrderActivity.setSubOrderId(oSubOrder.getId());
                    oSubOrderActivity.setActivityName(activity.getActivityName());
                    oSubOrderActivity.setRuleId(activity.getRuleId());
                    oSubOrderActivity.setProId(oSubOrder.getProId());
                    oSubOrderActivity.setProName(oSubOrder.getProName());
                    oSubOrderActivity.setActivityRule(activity.getActivityRule());
                    oSubOrderActivity.setActivityWay(activity.getActivityWay());
                    oSubOrderActivity.setPrice(activity.getPrice());
                    oSubOrderActivity.setOriginalPrice(activity.getOriginalPrice());
                    oSubOrderActivity.setProModel(activity.getProModel());
                    oSubOrderActivity.setVender(activity.getVender());
                    oSubOrderActivity.setPlatform(activity.getPlatform());
                    oSubOrderActivity.setgTime(activity.getgTime());
                    oSubOrderActivity.setcTime(d);
                    oSubOrderActivity.setuTime(d);
                    oSubOrderActivity.setcUser(userId);
                    oSubOrderActivity.setuUser(userId);
                    oSubOrderActivity.setStatus(Status.STATUS_1.status);
                    oSubOrderActivity.setVersion(Status.STATUS_0.status);
                    oSubOrderActivity.setBusProCode(activity.getBusProCode());
                    oSubOrderActivity.setBusProName(activity.getBusProName());
                    oSubOrderActivity.setTermBatchcode(activity.getTermBatchcode());
                    oSubOrderActivity.setTermBatchname(activity.getTermBatchname());
                    oSubOrderActivity.setTermtype(activity.getTermtype());
                    oSubOrderActivity.setTermtypename(activity.getTermtypename());
                    oSubOrderActivity.setPosType(activity.getPosType());
                    oSubOrderActivity.setPosSpePrice(activity.getPosSpePrice());
                    oSubOrderActivity.setStandTime(activity.getStandTime());
                    oSubOrderActivity.setStandAmt(activity.getStandAmt());
                    oSubOrderActivity.setBackType(activity.getBackType());
                    if (1 != oSubOrderActivityMapper.insertSelective(oSubOrderActivity)) {
                        logger.info("下订单:{}{}", activity.getActivityName(), "商品添加活动失败");
                        throw new MessageException("商品添加活动失败");
                    } else {
                        logger.info("下订单:{}{}", activity.getActivityName(), "商品添加活动成功");
                    }
                } else {
                    oSubOrder.setProRelPrice(oSubOrder.getProPrice());
                    logger.info("下订单 商品必须选择 商品活动:{}", oSubOrder.getProId());
                    throw new MessageException("下订单商品必须选择商品活动");
                }
            } else {
                oSubOrder.setProRelPrice(oSubOrder.getProPrice());
                logger.info("下订单 商品必须选择 商品活动:{}", oSubOrder.getProId());
                throw new MessageException("下订单商品必须选择商品活动");
            }
            //插入订单商品信息
            if (1 != oSubOrderMapper.insertSelective(oSubOrder)) {
                logger.info("下订单:{}", "oSubOrder添加失败");
                throw new MessageException("oPayment添加失败");
            }
            //计算订单金额
            forPayAmount = forPayAmount.add(oSubOrder.getProPrice().multiply(oSubOrder.getProNum()));
            //优惠后金额
            forRealPayAmount = forRealPayAmount.add(oSubOrder.getProRelPrice().multiply(oSubOrder.getProNum()));
        }
        //收货地址
        if (orderFormVo.getoReceiptOrderList() != null) {
            List<OReceiptOrderVo> OReceiptOrderVos = orderFormVo.getoReceiptOrderList();
            for (OReceiptOrderVo oReceiptOrderVo : OReceiptOrderVos) {
                oReceiptOrderVo.setId(idService.genId(TabId.o_receipt_order));
                oReceiptOrderVo.setOrderId(orderFormVo.getId());
                oReceiptOrderVo.setReceiptNum(oReceiptOrderVo.getId());
                OAddress address = oAddressMapper.selectByPrimaryKey(oReceiptOrderVo.getAddressId());
                oReceiptOrderVo.setAddrRealname(address.getAddrRealname());
                oReceiptOrderVo.setAddrMobile(address.getAddrMobile());
                oReceiptOrderVo.setAddrProvince(address.getAddrProvince());
                oReceiptOrderVo.setAddrCity(address.getAddrCity());
                oReceiptOrderVo.setAddrDistrict(address.getAddrDistrict());
                oReceiptOrderVo.setAddrDetail(address.getAddrDetail());
                oReceiptOrderVo.setRemark(address.getRemark());
                oReceiptOrderVo.setZipCode(address.getZipCode());
                oReceiptOrderVo.setcTime(d);
                oReceiptOrderVo.setuTime(d);
                oReceiptOrderVo.setReceiptStatus(OReceiptStatus.TEMPORARY_STORAGE.code);
                oReceiptOrderVo.setuUser(userId);
                oReceiptOrderVo.setcUser(userId);
                oReceiptOrderVo.setStatus(Status.STATUS_1.status);
                oReceiptOrderVo.setVersion(Status.STATUS_0.status);
                oReceiptOrderVo.setAgentId(orderFormVo.getAgentId());
                BigDecimal b = new BigDecimal(0);
                List<OReceiptPro> pros = oReceiptOrderVo.getoReceiptPros();
                if (pros.size() == 0) {
                    logger.info("下订单:{}", "请为收货地址[" + address.getRemark() + "]配置上商品明细");
                    throw new MessageException("请为收货地址[" + address.getRemark() + "]配置上商品明细");
                }
                //收货地址商品
                for (OReceiptPro pro : pros) {
                    pro.setId(idService.genId(TabId.o_receipt_pro));
                    pro.setcTime(d);
                    pro.setOrderid(orderFormVo.getId());
                    pro.setReceiptId(oReceiptOrderVo.getId());
                    String proid = pro.getProId();
                    OProduct product = oProductMapper.selectByPrimaryKey(proid);
                    pro.setProCode(product.getProCode());
                    pro.setProName(product.getProName());
                    pro.setSendNum(new BigDecimal(0));
                    pro.setcUser(userId);
                    pro.setuTime(d);
                    pro.setuUser(userId);
                    pro.setStatus(Status.STATUS_1.status);
                    pro.setVersion(Status.STATUS_0.status);
                    pro.setReceiptProStatus(OReceiptStatus.TEMPORARY_STORAGE.code);
                    //插入收货地址明细
                    if (1 != oReceiptProMapper.insertSelective(pro)) {
                        logger.info("下订单:{}", "oReceiptPro添加失败");
                        throw new MessageException("oPayment添加失败");
                    }
                    b = b.add(pro.getProNum());
                }
                oReceiptOrderVo.setProNum(b);
                //插入收货地址
                if (1 != oReceiptOrderMapper.insertSelective(oReceiptOrderVo)) {
                    logger.info("下订单:{}", "oReceiptOrderVo添加失败");
                    throw new MessageException("oReceiptOrderVo添加失败");
                }
            }
        }
        //附件信息
        List<Attachment> attr = orderFormVo.getAttachments();
        if (attr != null && attr.size() > 0) {
            for (Attachment attachment : attr) {
                if (org.apache.commons.lang.StringUtils.isEmpty(attachment.getId())) continue;
                AttachmentRel record = new AttachmentRel();
                record.setAttId(attachment.getId());
                record.setSrcId(orderFormVo.getId());
                record.setcUser(userId);
                record.setcTime(d);
                record.setStatus(Status.STATUS_1.status);
                record.setBusType(AttachmentRelType.Order.name());
                record.setId(idService.genId(TabId.a_attachment_rel));
                if (1 != attachmentRelMapper.insertSelective(record)) {
                    logger.info("下订单:{}", "附件添加失败");
                    throw new MessageException("下订单附件添加失败");
                }
            }
        }

        //需要手动计算付款金额
        oPayment.setPayAmount(forRealPayAmount);//应付金额
        oPayment.setOutstandingAmount(forRealPayAmount);//待付金额
        oPayment.setRealAmount(new BigDecimal(0));//已付金额
        //需要手动计算付款金额
        orderFormVo.setIncentiveAmo(forPayAmount.subtract(forRealPayAmount));//订单优惠金额
        orderFormVo.setoAmo(forPayAmount);//订单总金额
        orderFormVo.setPayAmo(forRealPayAmount);//订单应付金额

        //订单首付金额不能大于订单金额
        if (oPayment.getDownPayment() != null && orderFormVo.getPayAmo()!=null && oPayment.getDownPayment().compareTo(orderFormVo.getPayAmo()) > 0) {
            throw new MessageException("首付不能大于等于订单金额");
        }
        //检查抵扣金额
        if(StringUtils.isNotBlank(oPayment.getDeductionType())){
            //抵扣金额查询
            AgentResult agentResult = queryAgentCapital(orderFormVo.getAgentId(),oPayment.getDeductionType());
            if(agentResult.isOK()){
                FastMap f =   (FastMap)agentResult.getData();
                BigDecimal can = new BigDecimal(f.get("can")+"");
                if(oPayment.getDeductionAmount()==null || oPayment.getDeductionAmount().compareTo(BigDecimal.ZERO)<0) {
                    throw new MessageException("请填写抵扣金额");
                }
                if(can.compareTo(oPayment.getDeductionAmount())<0){
                    throw new MessageException("抵扣金额不足");
                }
                //抵扣金额大于待付金额
                if(oPayment.getDeductionAmount().compareTo(oPayment.getPayAmount())>0){
                    throw new MessageException("抵扣金额大于应付金额");
                }
                Agent agent =  agentMapper.selectByPrimaryKey(orderFormVo.getAgentId());
                //保证金冻结
                capitalService.disposeCapital(oPayment.getDeductionType(),
                        oPayment.getDeductionAmount(),oPayment.getId(),userId,
                        oPayment.getAgentId(),agent.getAgName(),"下订单",SrcType.DD,null);

            }else{
                throw new MessageException("不可抵扣");
            }
        }

        //插入订单
        if (1 != orderMapper.insertSelective(orderFormVo)) {
            throw new MessageException("订单添加失败");
        }
        //插入付款单
        oPayment = initPayment(orderFormVo);

        //线下付款明细添加
        AgentResult cashReceivables = oCashReceivablesService.addOCashReceivables(orderFormVo.getoCashReceivables(),userId,oPayment.getAgentId(),CashPayType.PAYMENT,oPayment.getId());
        if(cashReceivables.isOK()){
            logger.info("下订单线下付款明细添加成功:{}", JSONArray.toJSONString(orderFormVo.getoCashReceivables()));
            //根据明细天剑实收金额
            oPayment.setActualReceipt((BigDecimal)cashReceivables.getData());
            if(oPayment.getActualReceipt().compareTo(oPayment.getPayAmount())>0){
                throw new MessageException("实付金额不能大于订单金额");
            }
        }

        if (1 != oPaymentMapper.insertSelective(oPayment)) {
            throw new MessageException("oPayment添加失败");
        }

        //初始化订单自定义分期
        if(!initPaymentDetail(oPayment.getId(),oPayment.getPayMethod(),orderFormVo.getCustomStagingUser())){
            throw new MessageException("自定义分期处理失败");
        }


        if("SF1".equals(oPayment.getPayMethod()) || "SF2".equals(oPayment.getPayMethod())) {
            //抵扣金额
            BigDecimal dc = oPayment.getDeductionAmount() == null ? BigDecimal.ZERO : oPayment.getDeductionAmount();
            //首付金额
            BigDecimal downPayment = oPayment.getDownPayment()== null ? BigDecimal.ZERO : oPayment.getDownPayment();
            //打款金额+抵扣金额不能大于首付金额
            if (oPayment.getActualReceipt().add(dc).compareTo(downPayment) != 0) {
                throw new MessageException("打款金额加抵扣金额不等于首付金额");
            }
        }

        return orderFormVo;
    }

    private OrderFormVo updateOrderFormValue(OrderFormVo orderFormVo, String userId) throws Exception {

        logger.info("下订单:{}{}", userId, orderFormVo.getAgentId());

        //订单基础数据
        OOrder order_db = orderMapper.selectByPrimaryKey(orderFormVo.getId());
        Date d = Calendar.getInstance().getTime();
        order_db.setPayAmo(orderFormVo.getoAmo());
        order_db.setuUser(userId);
        order_db.setuTime(d);
        order_db.setAgentId(orderFormVo.getAgentId());
        order_db.setOrderPlatform(orderFormVo.getOrderPlatform());
        order_db.setoAmo(orderFormVo.getoAmo());
        order_db.setPaymentMethod(orderFormVo.getPaymentMethod());
        order_db.setRemark(orderFormVo.getRemark());


        //平台信息错误
        String orderPatform = order_db.getOrderPlatform();
        if(StringUtils.isNotBlank(orderPatform)){
            AgentBusInfo ainfo =  agentBusinfoService.getById(orderPatform);
            if(ainfo!=null && ainfo.getAgentId().equals(order_db.getAgentId())) {
                order_db.setOrderPlatform(ainfo.getBusPlatform());
                order_db.setBusId(ainfo.getId());
            }else{
                logger.info("下订单:{}{}",orderPatform, "平台数据错误");
                throw new MessageException("平台数据错误");
            }
        }

        //支付方式
        OPayment oPayment = orderFormVo.getoPayment();

        OPayment oPayment_db = oPaymentMapper.selectByPrimaryKey(oPayment.getId());
        oPayment_db.setOrderId(orderFormVo.getId());
        oPayment_db.setAgentId(orderFormVo.getAgentId());
        oPayment_db.setPayMethod(order_db.getPaymentMethod());
        oPayment_db.setGuaranteeAgent(oPayment.getGuaranteeAgent());
        oPayment_db.setSettlementPrice(oPayment.getSettlementPrice());
        oPayment_db.setDownPayment(oPayment.getDownPayment());
        oPayment_db.setDownPaymentCount(oPayment.getDownPaymentCount());
        oPayment_db.setDownPaymentDate(oPayment.getDownPaymentDate());
        oPayment_db.setDownPaymentUser(oPayment.getDownPaymentUser());
        oPayment_db.setActualReceipt(oPayment.getActualReceipt());
        oPayment_db.setCollectCompany(oPayment.getCollectCompany());
        oPayment_db.setRemark(oPayment.getRemark());
        oPayment_db.setActualReceiptDate(oPayment.getActualReceiptDate());
        oPayment_db.setIsCloInvoice(oPayment.getIsCloInvoice());
        if(StringUtils.isNotBlank(oPayment.getDeductionType())){
            oPayment_db.setDeductionType(oPayment.getDeductionType());
        }
        if(null!=oPayment.getDeductionAmount()){
            oPayment_db.setDeductionAmount(oPayment.getDeductionAmount());
        }
        if (StringUtils.isBlank(orderFormVo.getPaymentMethod())) {
            logger.info("下订单:{}", "商品价格数据错误");
            throw new MessageException("付款方式不能为空");
        }
        //插入付款单
        oPayment_db = initPayment(orderFormVo);
        //订单总金额
        BigDecimal forPayAmount = new BigDecimal(0);
        //订单应付金额
        BigDecimal forRealPayAmount = new BigDecimal(0);


        //子订单接口 计算整个订单数据
        List<OSubOrder> OSubOrders = orderFormVo.getoSubOrder();

        //删除订购单信息
        OSubOrderExample example = new OSubOrderExample();
        example.or().andOrderIdEqualTo(order_db.getId()).andStatusEqualTo(Status.STATUS_1.status);
        List<OSubOrder> fordele_subOrders = oSubOrderMapper.selectByExample(example);
        for (OSubOrder fordele_subOrder : fordele_subOrders) {
            fordele_subOrder.setStatus(Status.STATUS_0.status);
            fordele_subOrder.setuUser(userId);
            if (1 != oSubOrderMapper.updateByPrimaryKeySelective(fordele_subOrder)) {
                logger.info("下订单:{}{}", "删除订购单失败", fordele_subOrder.getId());
                throw new MessageException("删除订购单失败");
            }
        }

        //更新商品
        for (OSubOrder oSubOrder : OSubOrders) {

            OProduct product = oProductMapper.selectByPrimaryKey(oSubOrder.getProId());

            if (oSubOrder.getProNum() == null || oSubOrder.getProNum().compareTo(BigDecimal.ZERO) <= 0) {
                logger.info("下订单:{}", "商品数量错误");
                throw new MessageException("商品数量错误");
            }
            //商品参加的活动
            String oActivity = oSubOrder.getActivity();
            //商品活动对象
            OSubOrderActivity oSubOrderActivity = null;
            //订购单不为空，删除之前参与的活动
            if (StringUtils.isNotBlank(oSubOrder.getId())) {
                OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
                oSubOrderActivityExample.or().andSubOrderIdEqualTo(oSubOrder.getId()).andStatusEqualTo(Status.STATUS_1.status);
                List<OSubOrderActivity> OSubOrderActivitys = oSubOrderActivityMapper.selectByExample(oSubOrderActivityExample);
                for (OSubOrderActivity subOrderActivity : OSubOrderActivitys) {
                    subOrderActivity.setStatus(Status.STATUS_0.status);
                    subOrderActivity.setuUser(userId);
                    if (1 != oSubOrderActivityMapper.updateByPrimaryKeySelective(subOrderActivity)) {
                        logger.info("下订单:{}{}", "删除活动有误", subOrderActivity.getId());
                        throw new MessageException("删除活动有误");
                    }

                }
            }

            //cxinfo 订单修改 添加原价字段变更 价格计算采用活动中的价格 xx
            //参与活动
            if (StringUtils.isNotBlank(oActivity)) {
                //查询活动
                OActivity activity = oActivityMapper.selectByPrimaryKey(oActivity);
                //活动存在
                if (activity != null && activity.getPrice() != null && activity.getPrice().compareTo(BigDecimal.ZERO) >= 0) {

                    if (null == activity.getQuantityLimit() || activity.getQuantityLimit() < 1) {
                        logger.info("请补全活动名称为：" + activity.getActivityName() + "的订货数量下限！");
                        throw new MessageException("请补全活动名称为：" + activity.getActivityName() + "的订货数量下限！");
                    }
                    if (oSubOrder.getProNum().intValue() < activity.getQuantityLimit()) {
                        logger.info("活动：" + activity.getActivityName() + "，最低订货数量为" + activity.getQuantityLimit() + "台");
                        throw new MessageException("活动：" + activity.getActivityName() + "，最低订货数量为" + activity.getQuantityLimit() + "台");
                    }
                    //设置商品实际单价
                    oSubOrder.setProPrice(activity.getOriginalPrice());
                    oSubOrder.setProRelPrice(activity.getPrice());
                    //缓存活动信息
                    oSubOrderActivity = new OSubOrderActivity();
                    oSubOrderActivity.setId(idService.genId(TabId.o_sub_order_activity));
                    oSubOrderActivity.setActivityId(activity.getId());
                    oSubOrderActivity.setSubOrderId(oSubOrder.getId());
                    oSubOrderActivity.setActivityName(activity.getActivityName());
                    oSubOrderActivity.setRuleId(activity.getRuleId());
                    oSubOrderActivity.setProId(oSubOrder.getProId());
                    oSubOrderActivity.setProName(oSubOrder.getProName());
                    oSubOrderActivity.setActivityRule(activity.getActivityRule());
                    oSubOrderActivity.setActivityWay(activity.getActivityWay());
                    oSubOrderActivity.setPrice(activity.getPrice());
                    oSubOrderActivity.setOriginalPrice(activity.getOriginalPrice());
                    oSubOrderActivity.setProModel(activity.getProModel());
                    oSubOrderActivity.setVender(activity.getVender());
                    oSubOrderActivity.setPlatform(activity.getPlatform());
                    oSubOrderActivity.setgTime(activity.getgTime());
                    oSubOrderActivity.setcTime(d);
                    oSubOrderActivity.setuTime(d);
                    oSubOrderActivity.setcUser(userId);
                    oSubOrderActivity.setuUser(userId);
                    oSubOrderActivity.setVersion(Status.STATUS_0.status);
                    oSubOrderActivity.setStatus(Status.STATUS_1.status);
                    oSubOrderActivity.setBusProCode(activity.getBusProCode());
                    oSubOrderActivity.setBusProName(activity.getBusProName());
                    oSubOrderActivity.setTermBatchcode(activity.getTermBatchcode());
                    oSubOrderActivity.setTermBatchname(activity.getTermBatchname());
                    oSubOrderActivity.setTermtype(activity.getTermtype());
                    oSubOrderActivity.setTermtypename(activity.getTermtypename());
                    oSubOrderActivity.setPosType(activity.getPosType());
                    oSubOrderActivity.setPosSpePrice(activity.getPosSpePrice());
                    oSubOrderActivity.setStandTime(activity.getStandTime());
                    oSubOrderActivity.setStandAmt(activity.getStandAmt());
                    oSubOrderActivity.setBackType(activity.getBackType());
                } else {
                    //设置商品实际单价
                    throw new MessageException("商品必须选择指定的活动");
                }
            } else {
                //设置商品实际单价
                throw new MessageException("商品必须选择指定的活动");
            }
            //添加订购单
            oSubOrder.setOrderId(order_db.getId());
            oSubOrder.setProCode(product.getProCode());
            oSubOrder.setProName(product.getProName());
            oSubOrder.setProType(product.getProType());
            oSubOrder.setSendNum(Status.STATUS_0.status);
            oSubOrder.setIsDeposit(product.getIsDeposit());
            oSubOrder.setDeposit(product.getDeposit());
            oSubOrder.setModel(product.getProModel());
            oSubOrder.setcTime(d);
            oSubOrder.setcUser(userId);
            oSubOrder.setuUser(userId);
            oSubOrder.setuTime(d);
            oSubOrder.setStatus(Status.STATUS_1.status);
            oSubOrder.setVersion(Status.STATUS_0.status);
            oSubOrder.setAgentId(order_db.getAgentId());
            oSubOrder.setId(idService.genId(TabId.o_sub_order));

            OSubOrderExample oSubOrderExamplesureExist = new OSubOrderExample();
            oSubOrderExamplesureExist.or()
                    .andOrderIdEqualTo(oSubOrder.getOrderId())
                    .andProIdEqualTo(oSubOrder.getProId())
                    .andStatusEqualTo(Status.STATUS_1.status);

            List<OSubOrder> oSubOrderList = oSubOrderMapper.selectByExample(oSubOrderExamplesureExist);
            if (oSubOrderList.size() > 0) {
                logger.info("下订单:{}{}", oSubOrder.getOrderId(), "订单商品重复");
                throw new MessageException("订单商品重复");
            }
            //插入订单商品信息
            if (1 != oSubOrderMapper.insertSelective(oSubOrder)) {
                logger.info("下订单:{}", "oSubOrder添加失败");
                throw new MessageException("oPayment添加失败");
            }
            //更新商品参与活动
            if (oSubOrderActivity != null) {
                oSubOrderActivity.setSubOrderId(oSubOrder.getId());
                if (1 != oSubOrderActivityMapper.insertSelective(oSubOrderActivity)) {
                    logger.info("下订单:{}{}", oSubOrderActivity.getActivityName(), "商品添加活动失败");
                    throw new MessageException("商品添加活动失败");
                } else {
                    logger.info("下订单:{}{}{}", order_db.getId(), oSubOrderActivity.getActivityName(), "商品添加活动成功");
                }
            }
            //计算订单金额
            forPayAmount = forPayAmount.add(oSubOrder.getProPrice().multiply(oSubOrder.getProNum()));
            forRealPayAmount = forRealPayAmount.add(oSubOrder.getProRelPrice().multiply(oSubOrder.getProNum()));

        }
        //附件
        List<Attachment> attr = orderFormVo.getAttachments();
        //删除附件
        AttachmentRelExample deleAttr = new AttachmentRelExample();
        deleAttr.or()
                .andBusTypeEqualTo(AttachmentRelType.Order.name())
                .andSrcIdEqualTo(orderFormVo.getId())
                .andStatusEqualTo(Status.STATUS_1.status);
        List<AttachmentRel> attachmentRels = attachmentRelMapper.selectByExample(deleAttr);
        if (attachmentRels.size() > 0) {
            for (AttachmentRel attachmentRelItem : attachmentRels) {
                attachmentRelItem.setStatus(Status.STATUS_0.status);
                if (attachmentRelMapper.updateByPrimaryKeySelective(attachmentRelItem) != 1) {
                    logger.info("下订单:{},{},{}", order_db.getId(), "删除附件失败", userId);
                    throw new MessageException("删除附件失败");
                }
                logger.info("下订单:{},{},{}", order_db.getId(), "删除附件成功", userId);
            }
        }

        //添加新附件
        for (Attachment attachment : attr) {
            if (org.apache.commons.lang.StringUtils.isEmpty(attachment.getId())) continue;
            AttachmentRel record = new AttachmentRel();
            record.setAttId(attachment.getId());
            record.setSrcId(orderFormVo.getId());
            record.setcUser(userId);
            record.setcTime(d);
            record.setStatus(Status.STATUS_1.status);
            record.setBusType(AttachmentRelType.Order.name());
            record.setId(idService.genId(TabId.a_attachment_rel));
            if (1 != attachmentRelMapper.insertSelective(record)) {
                logger.info("下订单:{},{}", order_db.getId(), "附件添加失败");
                throw new MessageException("附件添加失败");
            }
        }

        //需要手动计算付款金额
        oPayment_db.setPayAmount(forRealPayAmount);//应付金额
        oPayment_db.setOutstandingAmount(forRealPayAmount);//待付金额
        oPayment_db.setRealAmount(new BigDecimal(0));//已付金额

        //需要手动计算付款金额
        order_db.setIncentiveAmo(forPayAmount.subtract(forRealPayAmount));//订单优惠金额
        order_db.setoAmo(forPayAmount);//订单总金额
        order_db.setPayAmo(forRealPayAmount);//订单应付金额
        //订单首付金额不能大于订单金额
        if (oPayment_db.getDownPayment() != null && order_db.getPayAmo()!=null && oPayment_db.getDownPayment().compareTo(order_db.getPayAmo()) > 0) {
            throw new MessageException("首付不能大于订单金额");
        }

        //检查抵扣金额
        if(StringUtils.isNotBlank(oPayment.getDeductionType())){
            //抵扣金额查询
            AgentResult agentResult = queryAgentCapital(orderFormVo.getAgentId(),oPayment.getDeductionType());
            if(agentResult.isOK()){
                FastMap f =   (FastMap)agentResult.getData();
                BigDecimal can = new BigDecimal(f.get("can")+"");
                if(oPayment.getDeductionAmount()==null || oPayment.getDeductionAmount().compareTo(BigDecimal.ZERO)<0) {
                    throw new MessageException("请填写抵扣金额");
                }

                if(can.add(oPayment_db.getDeductionAmount()==null?BigDecimal.ZERO:oPayment_db.getDeductionAmount()).compareTo(oPayment.getDeductionAmount())<0){
                    throw new MessageException("抵扣金额不足");
                }
                //抵扣金额大于待付金额
                if(oPayment.getDeductionAmount().compareTo(oPayment.getPayAmount())>0){
                    throw new MessageException("抵扣金额大于应付金额");
                }


                Agent agent =  agentMapper.selectByPrimaryKey(orderFormVo.getAgentId());

                //数据库中临时数据
                OPayment oPayment_tran  = oPaymentMapper.selectByPrimaryKey(oPayment.getId());
                //保证金冻结 数据库中临时数据
                capitalService.unDisposeCapital(oPayment_tran.getDeductionType(),
                        oPayment_tran.getDeductionAmount(),oPayment_tran.getId(),userId,
                        oPayment_tran.getAgentId(),agent.getAgName(),"下订单",SrcType.DD,null);

                //保证金冻结 新数据
                capitalService.disposeCapital(oPayment.getDeductionType(),
                        oPayment.getDeductionAmount(),oPayment.getId(),userId,
                        oPayment.getAgentId(),agent.getAgName(),"下订单",SrcType.DD,null);
            }else{
                throw new MessageException("不可抵扣");
            }
        }

        //插入订单
        if (1 != orderMapper.updateByPrimaryKeySelective(order_db)) {
            throw new MessageException("订单添加失败");
        }else{
            //添加到数据历史表
            agentDataHistoryService.saveDataHistory(order_db,order_db.getId(),DataHistoryType.ORDER.code,userId,order_db.getVersion());
        }
        oPayment_db = initPayment(orderFormVo);

        //线下付款明细添加
        AgentResult cashReceivables = oCashReceivablesService.addOCashReceivables(orderFormVo.getoCashReceivables(),userId,oPayment.getAgentId(),CashPayType.PAYMENT,oPayment.getId());
        if(cashReceivables.isOK()){
            logger.info("下订单线下付款明细添加成功:{}", JSONArray.toJSONString(orderFormVo.getoCashReceivables()));
            //根据明细天剑实收金额
            oPayment_db.setActualReceipt((BigDecimal)cashReceivables.getData());
            if(oPayment_db.getActualReceipt().compareTo(oPayment_db.getPayAmount())>0){
                throw new MessageException("实付金额不能大于订单金额");
            }
        }

        //插入付款单
        if (1 != oPaymentMapper.updateByPrimaryKeySelective(oPayment_db)) {
            throw new MessageException("oPayment添加失败");
        }else{
            //添加到数据历史表
            agentDataHistoryService.saveDataHistory(oPayment_db,oPayment_db.getId(),DataHistoryType.ORDER_PAYMENT.code,userId,oPayment_db.getVersion());
        }

        //初始化订单自定义分期
        if(!initPaymentDetail(oPayment.getId(),oPayment.getPayMethod(),orderFormVo.getCustomStagingUser())){
            throw new MessageException("自定义分期处理失败");
        }


        if("SF1".equals(oPayment_db.getPayMethod()) || "SF2".equals(oPayment_db.getPayMethod())) {
            //抵扣金额
            BigDecimal dc = oPayment_db.getDeductionAmount() == null ? BigDecimal.ZERO : oPayment_db.getDeductionAmount();
            //首付金额
            BigDecimal downPayment = oPayment_db.getDownPayment()== null ? BigDecimal.ZERO : oPayment_db.getDownPayment();
            //打款金额+抵扣金额不能大于首付金额
            if (oPayment_db.getActualReceipt().add(dc).compareTo(downPayment) != 0) {
                throw new MessageException("打款金额加抵扣金额不等于首付金额");
            }
        }
        return orderFormVo;
    }

    /**
     * 加载订单数据
     *
     * @param id
     * @return
     */
    @Override
    public AgentResult loadAgentInfo(String id) throws Exception {
        List<Map<String,Object>> orderRecords = new ArrayList<>();
        //订单
        OOrder order = orderMapper.selectByPrimaryKey(id);
        FastMap f = FastMap.fastMap("order", order);

        Agent agent = agentMapper.selectByPrimaryKey(order.getAgentId());
        f.putKeyV("agent", agent);

        Map<String,Object> parentInfo = agentBusInfoMapper.queryBusInfoParent(FastMap.fastMap("id",order.getBusId()));
        f.putKeyV("parentInfo", parentInfo);

        String orderPlatform = order.getOrderPlatform();
        PlatForm platForm = platFormMapper.selectByPlatFormNum(orderPlatform);
        f.putKeyV("platForm", platForm);
        //商品信息
        OSubOrderExample osubOrderExample = new OSubOrderExample();
        osubOrderExample.or().andOrderIdEqualTo(order.getId()).andStatusEqualTo(Status.STATUS_1.status);
        List<OSubOrder> oSubOrders = oSubOrderMapper.selectByExample(osubOrderExample);
        f.putKeyV("oSubOrders", oSubOrders);
        f.putKeyV("oSubOrdersJson", JSONArray.toJSONString(oSubOrders));

        //商品活动信息
        if (oSubOrders.size() > 0) {
            List<String> ids = new ArrayList<>();
            oSubOrders.forEach(oSubOrder->{
                FastMap par = FastMap.fastMap("subOrderId", oSubOrder.getId());
                BigDecimal oReceiptPros = oReceiptProMapper.receiptCountTotal(order.getId(), oSubOrder.getProId());//配货
                BigDecimal countPlans = receiptPlanMapper.planCountTotal(order.getId(), oSubOrder.getProId());//排单
                Map<String,Object> orderRecord = new HashMap<>();
                orderRecord.put("oReceiptPros", oReceiptPros);//配货数量
                orderRecord.put("countPlan", countPlans);//排单总计
                orderRecord.put("orderSubId", oSubOrder.getId());
                orderRecord.put("adjNum",
                        oSubOrder.getProNum()
                                .subtract(oReceiptPros));
                orderRecord.put("calPrice",
                        oSubOrder.getProRelPrice().multiply(oSubOrder.getProNum()
                                .subtract(oReceiptPros))
                                .setScale(2, BigDecimal.ROUND_UP));
                orderRecords.add(orderRecord);
                f.putKeyV("orderRecords", orderRecords);
            });

            for (OSubOrder oSubOrder : oSubOrders) {
                ids.add(oSubOrder.getId());
            }
            if (ids.size() > 0) {
                OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
                oSubOrderActivityExample.or().andSubOrderIdIn(ids).andStatusEqualTo(Status.STATUS_1.status);
                List<OSubOrderActivity> sSubOrderActivitys = oSubOrderActivityMapper.selectByExample(oSubOrderActivityExample);
                f.putKeyV("sSubOrderActivitys", sSubOrderActivitys);
                f.putKeyV("sSubOrderActivitysJson", JSONArray.toJSONString(sSubOrderActivitys));

                //活动代码
                for (OSubOrderActivity sSubOrderActivity : sSubOrderActivitys) {
                    OActivityExample oActivityExample = new OActivityExample();
                    oActivityExample.createCriteria()
                            .andStatusEqualTo(Status.STATUS_1.status)
                            .andIdEqualTo(sSubOrderActivity.getActivityId());
                    List<OActivity> oActivityList = oActivityMapper.selectByExample(oActivityExample);
                    if (oActivityList.size() > 0) {
                        for (OActivity oActivity : oActivityList) {
                            sSubOrderActivity.setActivityName(sSubOrderActivity.getActivityName()+"-"+oActivity.getActCode());
                        }
                    }
                }
            }
            List<Object> oActivityLists = new ArrayList<>();
            for (OSubOrder oSubOrder : oSubOrders) {
                //根据商品id、实际单价查询可变更的活动数据
                OActivityExample oActivityExample = new OActivityExample();
                oActivityExample.or()
                        .andStatusEqualTo(Status.STATUS_1.status)
                        .andProductIdEqualTo(oSubOrder.getProId())
                        .andPriceEqualTo(oSubOrder.getProRelPrice());
                List<OActivity> oActivityList = oActivityMapper.selectByExample(oActivityExample);
                //厂商、机具型号、pos类型
                for (OActivity oActivity : oActivityList) {
                    if (null != oActivity.getVender()) {
                        Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(), oActivity.getVender());
                        if (null != dictByValue)
                            oActivity.setVender(dictByValue.getdItemname());
                    }
                    if (null != oActivity.getProModel()) {
                        Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.PROMODE.name(), oActivity.getProModel());
                        if (null != dictByValue)
                            oActivity.setProModel(dictByValue.getdItemname());
                    }
                    if (null != oActivity.getPosType()) {
                        oActivity.setPosType(PosType.getContentByValue(oActivity.getPosType()));
                    }
                }
                oActivityLists.add(oActivityList);
            }
            f.putKeyV("oActivityLists", oActivityLists);
        }
        //配货信息
        OReceiptOrderExample oReceiptOrderExample = new OReceiptOrderExample();
        oReceiptOrderExample.or().andStatusEqualTo(Status.STATUS_1.status).andOrderIdEqualTo(order.getId());
        List<OReceiptOrder> oReceiptOrderList = oReceiptOrderMapper.selectByExample(oReceiptOrderExample);
        f.putKeyV("oReceiptOrders", oReceiptOrderList);

        //配货商品
        OReceiptProExample oReceiptProExample = new OReceiptProExample();
        oReceiptProExample.or().andOrderidEqualTo(order.getId()).andStatusEqualTo(Status.STATUS_1.status);
        List<OReceiptPro> oReceiptPros = oReceiptProMapper.selectByExample(oReceiptProExample);
        f.putKeyV("oReceiptPros", oReceiptPros);

        //支付信息
        OPaymentExample oPaymentExample = new OPaymentExample();
        oPaymentExample.or().andStatusEqualTo(Status.STATUS_1.status).andOrderIdEqualTo(order.getId());
        List<OPayment> oPaymentList = oPaymentMapper.selectByExample(oPaymentExample);
        if (oPaymentList.size() != 1) {
            return AgentResult.fail("支付信息错误");
        }
        OPayment oPayment = oPaymentList.get(0);
        f.putKeyV("oPayment", oPayment);

        //瑞大宝分润类型
        if(StringUtils.isNotBlank(oPayment.getProfitForm())){
            f.putKeyV("oPayment_ProfitForm", oPayment.getProfitForm().split(","));
        }
        //实付打款分条明细
        List<OCashReceivables> listoCashReceivables = oCashReceivablesService.query(null,oPayment.getAgentId(),CashPayType.PAYMENT,oPayment.getId(),Arrays.asList(AgStatus.Create.status,AgStatus.Approving.status,AgStatus.Approved.status));
        f.putKeyV("oCashReceivables", listoCashReceivables);

        OPaymentDetailExample oPaymentDetailExample = new OPaymentDetailExample();
        oPaymentDetailExample.or()
                .andStatusEqualTo(Status.STATUS_1.status)
                .andPaymentIdEqualTo(oPayment.getId()).andOrderIdEqualTo(order.getId());
        oPaymentDetailExample.setOrderByClause(" pay_time asc, plan_num asc, plan_pay_time asc ");
        List<OPaymentDetail> oPaymentDetails = oPaymentDetailMapper.selectByExample(oPaymentDetailExample);
        f.putKeyV("oPaymentDetails", oPaymentDetails);
        //计算待付款分期款
        BigDecimal singlePrice = new BigDecimal(0.00);
        if (null!=oPaymentDetails && oPaymentDetails.size()>0){
            BigDecimal price = BigDecimal.ZERO;
            BigDecimal size = new BigDecimal(0);
            for (OPaymentDetail oPaymentDetail:oPaymentDetails){
                if (oPaymentDetail.getPaymentStatus().compareTo(PaymentStatus.DF.code)==0){
                    price = price.add(oPaymentDetail.getPayAmount());
                    size = size.add(BigDecimal.valueOf(1));
                }
            }
            if (size.compareTo(BigDecimal.ZERO) > 0){
                singlePrice = price.divide(size,2);
            }
        }
        f.putKeyV("singlePrice",singlePrice);

        //订单附件
        List<Attachment> attr = attachmentMapper.accessoryQuery(order.getId(), AttachmentRelType.Order.name());
        f.putKeyV("attrs", attr);

        //收款公司
        List<PayComp> comp = apaycompService.recCompList();
        f.putKeyV("comp", comp);

        return AgentResult.ok(f);
    }

    /**
     * 启动订单审批流程
     *
     * @param id
     * @param cuser
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult startOrderActiviy(String id, String cuser) throws Exception {
        if (StringUtils.isBlank(id)) {
            logger.info("订单提交审批,订单ID为空{}:{}", id, cuser);
            return AgentResult.fail("订单提交审批，订单ID为空");
        }
        if (StringUtils.isBlank(cuser)) {
            logger.info("订单提交审批,操作用户为空{}:{}", id, cuser);
            return AgentResult.fail("代理商审批中，操作用户为空");
        }

        //检查是否有审批中的代理商新
        BusActRelExample example = new BusActRelExample();
        example.or().andBusIdEqualTo(id)
                .andBusTypeEqualTo(BusActRelBusType.ORDER.name())
                .andActivStatusEqualTo(AgStatus.Approving.name())
                .andStatusEqualTo(Status.STATUS_1.status);
        if (busActRelMapper.selectByExample(example).size() > 0) {
            logger.info("订单提交审批,禁止重复提交审批{}:{}", id, cuser);
            return AgentResult.fail("订单提交审批，禁止重复提交审批");
        }
        OOrder order = orderMapper.selectByPrimaryKey(id);

        //抵扣金额检查
        OPaymentExample oPaymentExample = new OPaymentExample();
        oPaymentExample.or().andOrderIdEqualTo(order.getId());
        List<OPayment> pament= oPaymentMapper.selectByExample(oPaymentExample);
        if(pament.size()==1){
            OPayment  oPayment = pament.get(0);
            //检查抵扣金额
            if(StringUtils.isNotBlank(oPayment.getDeductionType())){
                //抵扣金额查询
                AgentResult agentResult = queryAgentCapital(order.getAgentId(),oPayment.getDeductionType());
                if(agentResult.isOK()){
                    FastMap f =   (FastMap)agentResult.getData();
                    BigDecimal can = new BigDecimal(f.get("can")+"");
                    if(oPayment.getDeductionAmount()==null || oPayment.getDeductionAmount().compareTo(BigDecimal.ZERO)<0) {
                        throw new MessageException("请填写抵扣金额");
                    }
                    if(can.compareTo(oPayment.getDeductionAmount())<0){
                        throw new MessageException("抵扣金额不足");
                    }
                    //抵扣金额大于待付金额
                    if(oPayment.getDeductionAmount().compareTo(oPayment.getPayAmount())>0){
                        throw new MessageException("抵扣金额大于应付金额");
                    }
                }else{
                    throw new MessageException("不可抵扣");
                }
            }
            //现付金额记录更新
            AgentResult agentResult = oCashReceivablesService.startProcing(CashPayType.PAYMENT,oPayment.getId(),cuser);
            logger.info("订单提交审批,提交审批现付金额记录更新结果orderid{},PaymentId{}:{}", id,oPayment.getId(), agentResult.getMsg());
        }


        //提交审批的用户必须是创建人
        if (!order.getUserId().equals(cuser)) {
            logger.info("提交审批的用户必须是创建订单的用户{}:{}", id, cuser);
            return AgentResult.fail("提交审批的用户必须是创建订单的用户");
        }

        if (order.getReviewStatus().equals(AgStatus.Approving.name())) {
            logger.info("订单提交审批,禁止重复提交审批{}:{}", id, cuser);
            return AgentResult.fail("订单提交审批，禁止重复提交审批");
        }
        if (order.getReviewStatus().equals(AgStatus.Approved.name())) {
            logger.info("订单提交审批,禁止重复提交审批{}:{}", id, cuser);
            return AgentResult.fail("订单提交审批，禁止重复提交审批");
        }


        if (!order.getStatus().equals(Status.STATUS_1.status)) {
            logger.info("订单提交审批,代理商信息已失效{}:{}", id, cuser);
            return AgentResult.fail("订单信息已失效");
        }

        //更新代理商审批中
        order.setReviewStatus(AgStatus.Approving.status);
        order.setoApytime(new Date());
        if (1 != orderMapper.updateByPrimaryKeySelective(order)) {
            logger.info("订单提交审批，更新订单基本信息失败{}:{}", id, cuser);
            throw new MessageException("订单提交审批，更新订单基本信息失败");
        }



        //流程中的部门参数
        Map startPar = agentEnterService.startPar(cuser);
        if (null == startPar) {
            logger.info("========用户{}{}启动部门参数为空", id, cuser);
            throw new MessageException("启动部门参数为空!");
        }
        Object party = startPar.get("party");
        //不同的业务类型找到不同的启动流程
        String workId = null;
        if(agentService.isAgent(cuser).isOK()){
            workId = dictOptionsService.getApproveVersion("orderAgent");
        }else {
            workId = dictOptionsService.getApproveVersion("orderCity");
        }
        if(startPar.get("party").toString().equals("beijing")) {
            startPar.put("rs", ApprovalType.PASS.getValue());
        }
        //订单启动流程
        if(StringUtils.isBlank(workId)){
            logger.info("========用户{}{}订单启动流程未找到", cuser, workId);
            throw new MessageException("订单启动流程未找到!");
        }
        //启动审批
        String proce = activityService.createDeloyFlow(null, workId, null, null, startPar);
        if (proce == null) {
            logger.info("订单提交审批，审批流启动失败{}:{}", id, cuser);
            throw new MessageException("审批流启动失败!");
        }

        Agent agent = agentMapper.selectByPrimaryKey(order.getAgentId());
        //代理商业务视频关系
        BusActRel record = new BusActRel();
        record.setBusId(order.getId());
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(cuser);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.ORDER.name());
        record.setActivStatus(AgStatus.Approving.name());
        record.setAgentId(order.getAgentId());
        record.setAgentName(agent.getAgName());
        record.setDataShiro(BusActRelBusType.ORDER.key);
        AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(order.getBusId());
        record.setAgDocPro(agentBusInfo.getAgDocPro());
        record.setAgDocDistrict(agentBusInfo.getAgDocDistrict());
        record.setNetInBusType("ACTIVITY_"+order.getOrderPlatform());
        if (1 != busActRelMapper.insertSelective(record)) {
            logger.info("订单提交审批，启动审批异常，添加审批关系失败{}:{}", id, proce);
            throw new MessageException("审批流启动失败:添加审批关系失败");
        }
        return AgentResult.ok();
    }

    /**
     * 审批订单任务
     *
     * @param agentVo
     * @param userId
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws Exception {
        try {
            //处理审批数据
            logger.info("订单提交审批，完成任务{}:{}：{}", agentVo.getTaskId(), userId, JSONObject.toJSONString(agentVo));
            //只有通过才处理业务
            if(agentVo.getApprovalResult().equals(ApprovalType.PASS.getValue())){
                AgentResult busres = orderService.approvalTaskBussiData(agentVo,userId);
                if(!busres.isOK()){
                    return busres;
                }
            }
            //完成任务
            AgentResult result = new AgentResult(500, "系统异常", "");
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("rs", agentVo.getApprovalResult());
            reqMap.put("approvalOpinion", agentVo.getApprovalOpinion());
            reqMap.put("approvalPerson", userId);
            reqMap.put("createTime", DateUtils.dateToStringss(new Date()));
            reqMap.put("taskId", agentVo.getTaskId());
            //下一个节点参数
            if (org.apache.commons.lang.StringUtils.isNotEmpty(agentVo.getOrderAprDept()))
                reqMap.put("dept", agentVo.getOrderAprDept());

            //传递部门信息
            Map startPar = agentEnterService.startPar(userId);
            if (null != startPar) {
                if(!agentVo.getApprovalResult().equals("back")){
                    ActRuTask actRuTask = actRuTaskService.selectByPrimaryKey(agentVo.getTaskId());
                    if(actRuTask==null){
                        return result;
                    }
                    String[] procDefId = String.valueOf(actRuTask.getProcDefId()).split(":");
                    if(procDefId==null){
                        return result;
                    }
                    String taskView = procDefId[0];
                    String[] taskViewVersion = taskView.split("_");
                    if(taskViewVersion.length>=2){
                        BigDecimal version = new BigDecimal(taskViewVersion[1]);
                        if(version.compareTo(new BigDecimal("3.0"))>=0){
                            reqMap.put("party", startPar.get("party"));
                        }else{
                            reqMap.put("party", "north");
                        }
                    }else{
                        reqMap.put("party", "north");
                    }
                }
            }
            //完成任务
            Map resultMap = activityService.completeTask(agentVo.getTaskId(), reqMap);
            if (resultMap == null) {
                throw new MessageException("catch工作流处理任务异常!");
            }
            Boolean rs = (Boolean) resultMap.get("rs");
            String msg = String.valueOf(resultMap.get("msg"));
            if (!rs) {
                throw new MessageException("catch工作流处理任务异常!");
            }
            return AgentResult.ok(null);
        } catch (MessageException e) {
            e.printStackTrace();
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional( isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    @Override
    public AgentResult approvalTaskBussiData(AgentVo agentVo, String userId) throws Exception {
        if(!ApprovalType.PASS.getValue().equals(agentVo.getApprovalResult())){
            return AgentResult.ok();
        }
        //如果有业务数据就保存
        if (null != agentVo.getoPayment()) {
            if(StringUtils.isNotBlank(agentVo.getoPayment().get("id"))){
                //付款单
                OPayment db =  oPaymentMapper.selectByPrimaryKey(agentVo.getoPayment().get("id"));

                OPayment oPayment = new OPayment();
                oPayment.setId(db.getId());
                oPayment.setVersion(db.getVersion());

                //收款时间 迁移到打款明细当中
//                if(StringUtils.isNotBlank(agentVo.getoPayment().get("actualReceiptDate"))
//                        || StringUtils.isNotBlank(agentVo.getoPayment().get("actualReceipt"))){
//                    //收款金额
//                    if(StringUtils.isNotBlank(agentVo.getoPayment().get("actualReceipt"))){
//                        if(new BigDecimal(agentVo.getoPayment().get("actualReceipt")).compareTo(BigDecimal.ZERO)<0){
//                            throw new MessageException("实收金额不能小于0");
//                        }
//                        if(new BigDecimal(agentVo.getoPayment().get("actualReceipt")).compareTo(db.getOutstandingAmount())>0){
//                            throw new MessageException("实收不能大于待付");
//                        }
//                        if(0!=oPayment.getActualReceipt().compareTo(new BigDecimal(agentVo.getoPayment().get("actualReceipt")))){
//                            throw new MessageException("核款金额必须等于打款金额");
//                        }
//                    }else{
//                        throw new MessageException("实收金额不能为空");
//                    }
                //收款公司 迁移到打款明细当中
//                    if(StringUtils.isNotBlank(agentVo.getoPayment().get("collectCompany"))){
//                        oPayment.setCollectCompany(agentVo.getoPayment().get("collectCompany"));
//                    }else{
//                        throw new MessageException("收款公司不能为空");
//                    }
                //收款时间
//                    if(StringUtils.isNotBlank(agentVo.getoPayment().get("actualReceiptDate"))){
//                        oPayment.setActualReceiptDate(DateUtil.format(agentVo.getoPayment().get("actualReceiptDate"),"yyyy-MM-dd"));
//                    } else{
//                        if (StringUtils.isNotBlank(agentVo.getPayMethod())){
//                              if (agentVo.getPayMethod().equals(SettlementType.XXDK.code) || agentVo.getPayMethod().equals(SettlementType.SF1.code)
//                                      || agentVo.getPayMethod().equals(SettlementType.SF2.code)  || agentVo.getPayMethod().equals(SettlementType.QT.code)){
//                                  throw new MessageException("收款时间不能为空");
//                              }
//                        }
//
//                    }


//                }
                //结算价
                if(StringUtils.isNotBlank(agentVo.getoPayment().get("settlementPrice"))){
                    oPayment.setSettlementPrice(new BigDecimal(agentVo.getoPayment().get("settlementPrice")));
                }
                //分润模板
                if(StringUtils.isNotBlank(agentVo.getoPayment().get("shareTemplet"))){
                    oPayment.setShareTemplet(agentVo.getoPayment().get("shareTemplet"));
                }
                //是否开具发票
                if(StringUtils.isNotBlank(agentVo.getoPayment().get("isCloInvoice"))){
                    oPayment.setIsCloInvoice(new BigDecimal(agentVo.getoPayment().get("isCloInvoice")));
                }
                //担保代理商
                if(StringUtils.isNotBlank(agentVo.getoPayment().get("guaranteeAgent"))){
                    oPayment.setGuaranteeAgent(agentVo.getoPayment().get("guaranteeAgent"));
                }
                //实际到账时间
                List<OCashReceivablesVo> oCashReceivablesVoList = agentVo.getoCashReceivablesVoList();
                //核款时间
                if(StringUtils.isNotBlank(agentVo.getoPayment().get("nuclearTime")) || ( oCashReceivablesVoList!=null && oCashReceivablesVoList.size()>0 )){
                    try {
                        Date nuclearTime = DateUtil.format(agentVo.getoPayment().get("nuclearTime") + "", "yyyy-MM-dd");
                        oPayment.setNuclearTime(nuclearTime);
                        oPayment.setNuclearUser(userId);
                        for (OCashReceivablesVo oCashReceivablesVo : oCashReceivablesVoList) {
                            if(null==oCashReceivablesVo.getRealRecTime()){
                                throw new MessageException("请填写实际到账时间");
                            }
                        }
                        //审核人审核时间
                        AgentResult ocash  = oCashReceivablesService.approveTashBusiness(CashPayType.PAYMENT,oPayment.getId(),userId,nuclearTime,oCashReceivablesVoList);
                        logger.info("核款时间核款人更新成功order{}user{}date{}",oPayment.getOrderId(),userId,nuclearTime.getTime());
                    }catch (MessageException e){
                        e.printStackTrace();
                        throw new MessageException(e.getMsg());
                    }catch (Exception e){
                        e.printStackTrace();
                        throw new MessageException("核款日期错误");
                    }
                }


                //抵扣类型
                if(StringUtils.isNotBlank(agentVo.getoPayment().get("deductionType"))){
                    //抵扣金额不能为空
                    if(agentVo.getoPayment().get("deductionAmount")==null || new BigDecimal(agentVo.getoPayment().get("deductionAmount")+"").compareTo(BigDecimal.ZERO)<=0){
                        throw new MessageException("选择抵扣方式,必须填写抵扣金额");
                    }
                    //抵扣金额查询
                    oPayment.setDeductionType(agentVo.getoPayment().get("deductionType"));
                    AgentResult agentResult = queryAgentCapital(db.getAgentId(), oPayment.getDeductionType());
                    if (agentResult.isOK()) {
                        FastMap f = (FastMap) agentResult.getData();
                        BigDecimal can = new BigDecimal(f.get("can") + "");
                        if (agentVo.getoPayment().get("deductionAmount") != null) {
                            oPayment.setDeductionAmount(new BigDecimal(agentVo.getoPayment().get("deductionAmount")));
                        } else {
                            throw new MessageException("抵扣类型不为空，请填写抵扣金额");
                        }
                        //把老的加你上去比较新的
                        if (can.add(db.getDeductionAmount()==null?BigDecimal.ZERO:db.getDeductionAmount()).compareTo(new BigDecimal(agentVo.getoPayment().get("deductionAmount"))) < 0) {
                            throw new MessageException("抵扣金额不足");
                        }
                    } else {
                        throw new MessageException("不可抵扣");
                    }
                    //抵扣审批判断
                    if(oPayment.getDeductionAmount().compareTo(db.getOutstandingAmount())>0){
                        throw new MessageException("抵扣超出待付");
                    }
                }

                //首付审批
                if(oPayment.getDownPayment()!=null) {
                    if (oPayment.getDownPayment().compareTo(db.getOutstandingAmount()) > 0) {
                        throw new MessageException("首付超出待付");
                    }
                    if((oPayment.getDownPayment().add(oPayment.getDeductionAmount())).compareTo(db.getOutstandingAmount())>0){
                        throw new MessageException("首付加抵扣超出待付");
                    }
                }

                if (1 != oPaymentMapper.updateByPrimaryKeySelective(oPayment)) {
                    logger.info("付款单数据储存失败");
                    throw new MessageException("付款单数据储存失败");
                }else{
                    //添加到数据历史表
                    agentDataHistoryService.saveDataHistory(oPayment,oPayment.getId(),DataHistoryType.ORDER_PAYMENT.code,userId,oPayment.getVersion());
                }

            }
        }
        return AgentResult.ok();
    }

    //订单审批通过
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult approveFinish(String insid, String actname) throws Exception {

        logger.info("代理商订单审批完成:{},{}", insid, actname);
        //审批流关系
        BusActRel rel = busActRelService.findById(insid);
        Calendar d = Calendar.getInstance();
        Calendar temp = Calendar.getInstance();
        String batchCode = d.getTime().getTime() + "";
        if (actname.equals("finish_end")) {
            logger.info("代理商订单审批完,审批通过{}",rel.getBusId());
            rel.setActivStatus(AgStatus.Approved.name());
            if(busActRelService.updateByPrimaryKey(rel)!=1){
                throw new MessageException("请重新提交");
            }
            //订单信息
            OOrder order = orderMapper.selectByPrimaryKey(rel.getBusId());
            if(order.getReviewStatus().compareTo(AgStatus.Approved.status)==0){
                logger.info("代理商订单审批完成:已审批过:{}", order.getId());
                return AgentResult.ok();
            }
            OPaymentExample example = new OPaymentExample();
            example.or().andOrderIdEqualTo(order.getId()).andStatusEqualTo(Status.STATUS_1.status);
            List<OPayment> payments = oPaymentMapper.selectByExample(example);
            if (payments.size() != 1) {
                logger.info("代理商订单审批完成:付款单未找到:{}", order.getId());
                throw new MessageException("付款单未找到");
            }

            OPayment oPayment = payments.get(0);
            //更新订单状态 审批状态，结算状态 订单生效时间
            order.setOrderStatus(OrderStatus.ENABLE.status);
            order.setReviewStatus(AgStatus.Approved.status);
            order.setoInuretime(d.getTime());
            Calendar orderdate = Calendar.getInstance();
            orderdate.setTime(order.getcTime());
            orderdate.set(Calendar.DAY_OF_MONTH,orderdate.getActualMinimum(Calendar.DAY_OF_MONTH));
            orderdate.set(Calendar.HOUR_OF_DAY,orderdate.getActualMinimum(Calendar.HOUR_OF_DAY));
            orderdate.set(Calendar.MILLISECOND,orderdate.getActualMinimum(Calendar.MILLISECOND));
            orderdate.set(Calendar.SECOND,orderdate.getActualMinimum(Calendar.SECOND));
            orderdate.set(Calendar.MINUTE,orderdate.getActualMinimum(Calendar.MINUTE));
            Date orderTime = DateUtil.getDateFromStr(DateUtil.format(orderdate.getTime(), "yyyy-MM-dd"), "yyyy-MM-dd");
            //付款单设置
            switch (order.getPaymentMethod()) {
                case "FKFQ":
                    if(oPayment.getOutstandingAmount()==null || oPayment.getOutstandingAmount().compareTo(BigDecimal.ZERO)<=0){
                        logger.info("代理商订单审批完成:待付金额不能为空:{},{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                        throw new MessageException("待付金额不能为空");
                    }
                    if (oPayment.getDownPaymentCount() == null || oPayment.getDownPaymentCount().compareTo(BigDecimal.ZERO) <= 0) {
                        logger.info("代理商订单审批完成:分期数据为错误:{},{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                        throw new MessageException("分期数有误");
                    }
                    if (oPayment.getDownPaymentDate() == null || oPayment.getDownPaymentDate().compareTo(orderTime) < 0) {
                        logger.info("代理商订单审批完成:分期数据为错误:{},{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                        throw new MessageException("分期日期错误");
                    }

                    //待付 订单待付减去抵扣
                    if (oPayment.getDeductionAmount() != null && oPayment.getDeductionAmount().compareTo(BigDecimal.ZERO) > 0) {
                        //已付款 加上抵扣金额
                        oPayment.setRealAmount(oPayment.getRealAmount().add(oPayment.getDeductionAmount()));
                        //待付款处理 减去抵扣金额
                        oPayment.setOutstandingAmount(oPayment.getOutstandingAmount().subtract(oPayment.getDeductionAmount()));
                        //抵扣操作
                        AgentResult dealOrderDeductionRes = dealOrderDeduction(oPayment);
                        if (dealOrderDeductionRes.isOK()) {
                            List<OPaymentDetail> details = (List<OPaymentDetail>) dealOrderDeductionRes.getData();
                            for (OPaymentDetail detail : details) {
                                if (1 != oPaymentDetailMapper.insertSelective(detail)) {
                                    logger.info("代理商订单审批完成:抵扣款项插入失败:{},{},{},{}",
                                            order.getId(),
                                            oPayment.getPayMethod(),
                                            oPayment.getDeductionType(),
                                            oPayment.getDeductionAmount());
                                    throw new MessageException("抵扣操作失败");
                                } else {
                                    logger.info("代理商订单审批完成:抵扣款项插入成功:{},{},{},{},{}",
                                            order.getId(),
                                            oPayment.getPayMethod(),
                                            oPayment.getDeductionType(),
                                            oPayment.getDeductionAmount(),
                                            detail.getPayAmount());
                                }
                            }
                        } else {
                            throw new MessageException("抵扣操作失败");
                        }
                    }
                    logger.info("代理商订单审批完成:处理明细:{},{},{}",
                            order.getId(),
                            oPayment.getId(),
                            oPayment.getPayMethod());

                    //如果是自定义分期
                    if(StringUtils.isNotBlank(oPayment.getCustomStaging()) && Status.STATUS_1.status.toPlainString().equals(oPayment.getCustomStaging())){
                        //这里不处理首付值处理分期
                        //更新付款分期为审批通过，检查分期金额是否等于待付金额
                        OPaymentDetailExample customStaginDetailQeury = new OPaymentDetailExample();
                        customStaginDetailQeury.or()
                                .andStatusEqualTo(Status.STATUS_1.status)
                                .andPayTypeEqualTo(PaymentType.DKFQ.code)
                                .andOrderIdEqualTo(oPayment.getOrderId())
                                .andPaymentStatusEqualTo(PaymentStatus.DS.code)
                                .andPaymentIdEqualTo(oPayment.getId());
                        List<OPaymentDetail> oPaymentDetailsList = oPaymentDetailMapper.selectByExample(customStaginDetailQeury);
                        BigDecimal dkfqAmount = BigDecimal.ZERO;
                        for (OPaymentDetail oPaymentDetail : oPaymentDetailsList) {
                            dkfqAmount =dkfqAmount.add(oPaymentDetail.getPayAmount());
                        }
                        //欠款和代扣分期是否一致，不一致抛出异常
                        if(dkfqAmount.compareTo(oPayment.getOutstandingAmount())!=0){
                            throw new MessageException("待付款和分期欠款不匹配");
                        }
                        //审批通过更新自定义分期为待付款
                        for (OPaymentDetail oPaymentDetail : oPaymentDetailsList) {
                            oPaymentDetail.setRealPayAmount(BigDecimal.ZERO);
                            oPaymentDetail.setPaymentStatus(PaymentStatus.DF.code);
                            if(1!=oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail)){
                                throw new MessageException("付款明细处理失败，请重试！");
                            }
                        }
                        //非自定义分期
                    }else{

                        temp.setTime(oPayment.getDownPaymentDate());
                        //分期数据
                        List<Map> FKFQ_data = StageUtil.stageOrder(
                                oPayment.getOutstandingAmount(),
                                oPayment.getDownPaymentCount().intValue(),
                                oPayment.getDownPaymentDate(), temp.get(Calendar.DAY_OF_MONTH));
                        //明细处理
                        for (Map datum : FKFQ_data) {
                            OPaymentDetail record = new OPaymentDetail();
                            record.setId(idService.genId(TabId.o_payment_detail));
                            record.setBatchCode(batchCode);
                            record.setPaymentId(oPayment.getId());
                            record.setPaymentType(PamentIdType.ORDER_FKD.code);
                            record.setOrderId(oPayment.getOrderId());
                            record.setPayType(PaymentType.DKFQ.code);
                            record.setPayAmount((BigDecimal) datum.get("item"));
                            record.setRealPayAmount(BigDecimal.ZERO);
                            record.setPlanPayTime((Date) datum.get("date"));
                            record.setPlanNum((BigDecimal) datum.get("count"));
                            record.setAgentId(oPayment.getAgentId());
                            record.setPaymentStatus(PaymentStatus.DF.code);
                            record.setcUser(oPayment.getUserId());
                            record.setcDate(d.getTime());
                            record.setStatus(Status.STATUS_1.status);
                            record.setVersion(Status.STATUS_1.status);

                            if (1 != oPaymentDetailMapper.insert(record)) {
                                logger.info("代理商订单审批完成:明细生成失败:订单ID:{},付款单ID:{},付款方式:{}，明细ID:{}",
                                        order.getId(),
                                        oPayment.getId(),
                                        oPayment.getPayMethod(),
                                        record.getId());
                                throw new MessageException("分期处理");
                            }

                            logger.info("代理商订单审批完成:明细生成:订单ID:{},付款单ID:{},付款方式:{}，明细ID:{}",
                                    order.getId(),
                                    oPayment.getId(),
                                    oPayment.getPayMethod(),
                                    record.getId());
                        }
                    }
                    break;

                case "FRFQ":

                    if (oPayment.getOutstandingAmount() == null || oPayment.getOutstandingAmount().compareTo(BigDecimal.ZERO) <= 0) {
                        logger.info("代理商订单审批完成:待付金额不能为空:{},{}", order.getId(), oPayment.getPayMethod());
                        throw new MessageException("待付金额不能为空");
                    }
                    if (oPayment.getDownPaymentCount() == null || oPayment.getDownPaymentCount().compareTo(BigDecimal.ZERO) <= 0) {
                        logger.info("代理商订单审批完成:分期数据为错误:{},{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                        throw new MessageException("分期数有误");
                    }
                    if (oPayment.getDownPaymentDate() == null || oPayment.getDownPaymentDate().compareTo(orderTime) < 0) {
                        logger.info("代理商订单审批完成:分期数据为错误:{},{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                        throw new MessageException("分期日期错误");
                    }

                    //待付 订单待付减去抵扣
                    if (oPayment.getDeductionAmount() != null && oPayment.getDeductionAmount().compareTo(BigDecimal.ZERO) > 0) {
                        //已付款 加上抵扣金额
                        oPayment.setRealAmount(oPayment.getRealAmount().add(oPayment.getDeductionAmount()));
                        //待付款处理 减去抵扣金额
                        oPayment.setOutstandingAmount(oPayment.getOutstandingAmount().subtract(oPayment.getDeductionAmount()));
                        //抵扣操作
                        AgentResult dealOrderDeductionRes = dealOrderDeduction(oPayment);
                        if (dealOrderDeductionRes.isOK()) {
                            List<OPaymentDetail> details = (List<OPaymentDetail>) dealOrderDeductionRes.getData();
                            for (OPaymentDetail detail : details) {
                                if (1 != oPaymentDetailMapper.insertSelective(detail)) {
                                    logger.info("代理商订单审批完成:抵扣款项插入失败:{},{},{},{}",
                                            order.getId(),
                                            oPayment.getPayMethod(),
                                            oPayment.getDeductionType(),
                                            oPayment.getDeductionAmount());
                                    throw new MessageException("抵扣操作失败");
                                } else {
                                    logger.info("代理商订单审批完成:抵扣款项插入成功:{},{},{},{},{}",
                                            order.getId(),
                                            oPayment.getPayMethod(),
                                            oPayment.getDeductionType(),
                                            oPayment.getDeductionAmount(),
                                            detail.getPayAmount());
                                }
                            }
                        } else {
                            throw new MessageException("抵扣操作失败");
                        }
                    }


                    logger.info("代理商订单审批完成处理明细完成:{},{},{}",
                            order.getId(),
                            oPayment.getId(),
                            oPayment.getPayMethod());
                    //如果是自定义分期
                    if(StringUtils.isNotBlank(oPayment.getCustomStaging()) && Status.STATUS_1.status.toPlainString().equals(oPayment.getCustomStaging())) {
                        //这里不处理首付值处理分期
                        //更新付款分期为审批通过，检查分期金额是否等于待付金额
                        OPaymentDetailExample customStaginDetailQeury = new OPaymentDetailExample();
                        customStaginDetailQeury.or()
                                .andStatusEqualTo(Status.STATUS_1.status)
                                .andPayTypeEqualTo(PaymentType.FRFQ.code)
                                .andOrderIdEqualTo(oPayment.getOrderId())
                                .andPaymentStatusEqualTo(PaymentStatus.DS.code)
                                .andPaymentIdEqualTo(oPayment.getId());
                        List<OPaymentDetail> oPaymentDetailsList = oPaymentDetailMapper.selectByExample(customStaginDetailQeury);
                        BigDecimal dkfqAmount = BigDecimal.ZERO;
                        for (OPaymentDetail oPaymentDetail : oPaymentDetailsList) {
                            dkfqAmount =dkfqAmount.add(oPaymentDetail.getPayAmount());
                        }
                        //欠款和代扣分期是否一致，不一致抛出异常
                        if(dkfqAmount.compareTo(oPayment.getOutstandingAmount())!=0){
                            throw new MessageException("待付款和分期欠款不匹配");
                        }
                        //审批通过更新自定义分期为待付款
                        for (OPaymentDetail oPaymentDetail : oPaymentDetailsList) {
                            oPaymentDetail.setRealPayAmount(BigDecimal.ZERO);
                            oPaymentDetail.setPaymentStatus(PaymentStatus.DF.code);
                            if(1!=oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail)){
                                throw new MessageException("付款明细处理失败，请重试！");
                            }
                        }

                    }else {
                        temp.setTime(oPayment.getDownPaymentDate());
                        //分期数据
                        List<Map> FRFQ_data = StageUtil.stageOrder(
                                oPayment.getOutstandingAmount(),
                                oPayment.getDownPaymentCount().intValue(),
                                oPayment.getDownPaymentDate(), temp.get(Calendar.DAY_OF_MONTH));

                        //明细处理
                        for (Map datum : FRFQ_data) {
                            OPaymentDetail record = new OPaymentDetail();
                            record.setId(idService.genId(TabId.o_payment_detail));
                            record.setBatchCode(batchCode);
                            record.setPaymentId(oPayment.getId());
                            record.setPaymentType(PamentIdType.ORDER_FKD.code);
                            record.setOrderId(oPayment.getOrderId());
                            record.setPayType(PaymentType.FRFQ.code);
                            record.setPayAmount((BigDecimal) datum.get("item"));
                            record.setRealPayAmount(BigDecimal.ZERO);
                            record.setPlanPayTime((Date) datum.get("date"));
                            record.setPlanNum((BigDecimal) datum.get("count"));
                            record.setAgentId(oPayment.getAgentId());
                            record.setPaymentStatus(PaymentStatus.DF.code);
                            record.setcUser(oPayment.getUserId());
                            record.setcDate(d.getTime());
                            record.setStatus(Status.STATUS_1.status);
                            record.setVersion(Status.STATUS_1.status);
                            if (1 != oPaymentDetailMapper.insert(record)) {
                                logger.info("代理商订单审批完成:明细生成失败:订单ID:{},付款单ID:{},付款方式:{}，明细ID:{}",
                                        order.getId(),
                                        oPayment.getId(),
                                        oPayment.getPayMethod(),
                                        record.getId());
                                throw new MessageException("分期处理");
                            }

                            logger.info("代理商订单审批完成:明细生成:订单ID:{},付款方式:{}，明细ID:{}",
                                    order.getId(),
                                    oPayment.getPayMethod(),
                                    record.getId());
                        }
                    }
                    break;
                case "XXDK":

                    if (oPayment.getActualReceipt() == null || oPayment.getActualReceipt().compareTo(BigDecimal.ZERO) <= 0) {
                        logger.info("代理商订单审批完成:实际收款金额不能为空:{},{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                        throw new MessageException("实际收款金额不能为空");
                    }

                    //已付款 首付实付金额
                    oPayment.setRealAmount(oPayment.getRealAmount().add(oPayment.getActualReceipt()));
                    //待付  待付金额减去实付金额
                    oPayment.setOutstandingAmount(oPayment.getOutstandingAmount().subtract(oPayment.getActualReceipt()));

                    //实收明细
                    if (oPayment.getActualReceipt() != null && oPayment.getActualReceipt().compareTo(BigDecimal.ZERO) > 0) {
                        //添加打款明细
                        OPaymentDetail record_XXDK = new OPaymentDetail();
                        record_XXDK.setId(idService.genId(TabId.o_payment_detail));
                        record_XXDK.setBatchCode(batchCode);
                        record_XXDK.setPaymentId(oPayment.getId());
                        record_XXDK.setPaymentType(PamentIdType.ORDER_FKD.code);
                        record_XXDK.setOrderId(oPayment.getOrderId());
                        record_XXDK.setPayType(PaymentType.DK.code);
                        record_XXDK.setPayAmount(oPayment.getActualReceipt());
                        record_XXDK.setRealPayAmount(oPayment.getActualReceipt());
                        record_XXDK.setPlanPayTime(d.getTime());
                        record_XXDK.setPlanNum(Status.STATUS_0.status);
                        record_XXDK.setPayTime(d.getTime());
                        record_XXDK.setAgentId(oPayment.getAgentId());
                        record_XXDK.setPaymentStatus(PaymentStatus.JQ.code);
                        record_XXDK.setcUser(oPayment.getUserId());
                        record_XXDK.setcDate(d.getTime());
                        record_XXDK.setStatus(Status.STATUS_1.status);
                        record_XXDK.setVersion(Status.STATUS_1.status);
                        if (1 != oPaymentDetailMapper.insert(record_XXDK)) {
                            throw new MessageException("打款明细错误");
                        }
                        logger.info("代理商订单审批完成处理明细完成首付数据成功{}:{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                    }

                    //待付 订单待付减去抵扣
                    if (oPayment.getDeductionAmount() != null && oPayment.getDeductionAmount().compareTo(BigDecimal.ZERO) > 0) {
                        //已付款 加上抵扣金额
                        oPayment.setRealAmount(oPayment.getRealAmount().add(oPayment.getDeductionAmount()));
                        //待付款处理 减去抵扣金额
                        oPayment.setOutstandingAmount(oPayment.getOutstandingAmount().subtract(oPayment.getDeductionAmount()));
                        //抵扣操作
                        AgentResult dealOrderDeductionRes = dealOrderDeduction(oPayment);
                        if (dealOrderDeductionRes.isOK()) {
                            List<OPaymentDetail> details = (List<OPaymentDetail>) dealOrderDeductionRes.getData();
                            for (OPaymentDetail detail : details) {
                                if (1 != oPaymentDetailMapper.insertSelective(detail)) {
                                    logger.info("代理商订单审批完成:抵扣款项插入失败:{},{},{},{}", order.getId(), oPayment.getPayMethod(), oPayment.getDeductionType(), oPayment.getDeductionAmount());
                                    throw new MessageException("抵扣操作失败");
                                } else {
                                    logger.info("代理商订单审批完成:抵扣款项插入成功:{},{},{},{},{}", order.getId(), oPayment.getPayMethod(), oPayment.getDeductionType(), oPayment.getDeductionAmount(), detail.getPayAmount());
                                }
                            }
                        } else {
                            throw new MessageException("抵扣操作失败");
                        }
                    }
                    //未付清生成待付明细
                    if (oPayment.getOutstandingAmount().compareTo(BigDecimal.ZERO) > 0) {
                        //添加打款明细
                        OPaymentDetail record_XXDK = new OPaymentDetail();
                        record_XXDK.setId(idService.genId(TabId.o_payment_detail));
                        record_XXDK.setBatchCode(batchCode);
                        record_XXDK.setPaymentId(oPayment.getId());
                        record_XXDK.setPaymentType(PamentIdType.ORDER_FKD.code);
                        record_XXDK.setOrderId(oPayment.getOrderId());
                        record_XXDK.setPayType(PaymentType.DK.code);
                        record_XXDK.setPayAmount(oPayment.getOutstandingAmount());
                        record_XXDK.setRealPayAmount(BigDecimal.ZERO);
                        record_XXDK.setPlanPayTime(d.getTime());
                        record_XXDK.setPlanNum(Status.STATUS_0.status);
                        record_XXDK.setAgentId(oPayment.getAgentId());
                        record_XXDK.setPaymentStatus(PaymentStatus.DF.code);
                        record_XXDK.setcUser(oPayment.getUserId());
                        record_XXDK.setcDate(d.getTime());
                        record_XXDK.setStatus(Status.STATUS_1.status);
                        record_XXDK.setVersion(Status.STATUS_1.status);
                        if (1 != oPaymentDetailMapper.insert(record_XXDK)) {
                            throw new MessageException("打款明细错误");
                        }
                        logger.info("代理商订单审批完成处理明细完成首付数据成功{}:{},{}",
                                order.getId(),
                                oPayment.getOutstandingAmount(),
                                oPayment.getPayMethod());
                    }
                    break;
                case "SF1"://首付+分润分期

                    if(oPayment.getDownPayment()==null || oPayment.getDownPayment().compareTo(BigDecimal.ZERO)<=0){
                        logger.info("代理商订单审批完成:首付金额为空:{},{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                        throw new MessageException("首付金额为空");
                    }
                    if(oPayment.getDownPaymentCount()==null || oPayment.getDownPaymentCount().compareTo(BigDecimal.ZERO)<=0){
                        logger.info("代理商订单审批完成:分期数据为错误:{},{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                        throw new MessageException("分期数有误");
                    }
                    if(oPayment.getDownPaymentDate()==null || oPayment.getDownPaymentDate().compareTo(orderTime)<0){
                        logger.info("代理商订单审批完成:分期数据为错误:{},{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                        throw new MessageException("分期日期错误");
                    }
                    if(oPayment.getActualReceipt()==null || oPayment.getActualReceipt().compareTo(BigDecimal.ZERO)<=0){
                        logger.info("代理商订单审批完成:实际收款金额不能为空:{},{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                        throw new MessageException("实际收款金额不能为空");
                    }

                    //已付款 首付实付金额
                    oPayment.setRealAmount(oPayment.getRealAmount().add(oPayment.getActualReceipt()));
                    //待付  待付金额减去实付金额
                    oPayment.setOutstandingAmount(oPayment.getOutstandingAmount().subtract(oPayment.getActualReceipt()));
                    //首付款添加明细
                    if (oPayment.getActualReceipt() != null && oPayment.getActualReceipt().compareTo(BigDecimal.ZERO) > 0) {
                        //添加首付明细
                        OPaymentDetail record_SF1 = new OPaymentDetail();
                        record_SF1.setId(idService.genId(TabId.o_payment_detail));
                        record_SF1.setBatchCode(batchCode);
                        record_SF1.setPaymentId(oPayment.getId());
                        record_SF1.setPaymentType(PamentIdType.ORDER_FKD.code);
                        record_SF1.setOrderId(oPayment.getOrderId());
                        record_SF1.setPayType(PaymentType.SF.code);
                        record_SF1.setPayAmount(oPayment.getActualReceipt());
                        record_SF1.setRealPayAmount(oPayment.getActualReceipt());
                        record_SF1.setPlanPayTime(d.getTime());
                        record_SF1.setPayTime(record_SF1.getPlanPayTime());
                        record_SF1.setPlanNum(Status.STATUS_0.status);
                        record_SF1.setAgentId(oPayment.getAgentId());
                        record_SF1.setPaymentStatus(PaymentStatus.JQ.code);
                        record_SF1.setcUser(oPayment.getUserId());
                        record_SF1.setcDate(d.getTime());
                        record_SF1.setStatus(Status.STATUS_1.status);
                        record_SF1.setVersion(Status.STATUS_1.status);
                        if (1 != oPaymentDetailMapper.insert(record_SF1)) {
                            throw new MessageException("首付错误");
                        }
                        logger.info("代理商订单审批完成处理明细完成首付数据成功{}:{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                    }


                    //待付 订单待付减去抵扣
                    if(oPayment.getDeductionAmount()!=null && oPayment.getDeductionAmount().compareTo(BigDecimal.ZERO)>0){
                        //已付款 加上抵扣金额
                        oPayment.setRealAmount(oPayment.getRealAmount().add(oPayment.getDeductionAmount()));
                        //待付款处理 减去抵扣金额
                        oPayment.setOutstandingAmount(oPayment.getOutstandingAmount().subtract(oPayment.getDeductionAmount()));
                        //抵扣操作
                        AgentResult dealOrderDeductionRes =  dealOrderDeduction(oPayment);
                        if(dealOrderDeductionRes.isOK()){
                            List<OPaymentDetail> details =  (List<OPaymentDetail>)dealOrderDeductionRes.getData();
                            for (OPaymentDetail detail : details) {
                                if(1!=oPaymentDetailMapper.insertSelective(detail)){
                                    logger.info("代理商订单审批完成:抵扣款项插入失败:{},{},{},{}", order.getId(), oPayment.getPayMethod(), oPayment.getDeductionType(), oPayment.getDeductionAmount());
                                    throw new MessageException("抵扣操作失败");
                                }else{
                                    logger.info("代理商订单审批完成:抵扣款项插入成功:{},{},{},{},{}", order.getId(), oPayment.getPayMethod(), oPayment.getDeductionType(), oPayment.getDeductionAmount(),detail.getPayAmount());
                                }
                            }
                        }else{
                            throw new MessageException("抵扣操作失败");
                        }
                    }
                    //如果是自定义分期
                    if(StringUtils.isNotBlank(oPayment.getCustomStaging()) && Status.STATUS_1.status.toPlainString().equals(oPayment.getCustomStaging())) {
                        //这里不处理首付值处理分期
                        //更新付款分期为审批通过，检查分期金额是否等于待付金额
                        OPaymentDetailExample customStaginDetailQeury = new OPaymentDetailExample();
                        customStaginDetailQeury.or()
                                .andStatusEqualTo(Status.STATUS_1.status)
                                .andPayTypeEqualTo(PaymentType.FRFQ.code)
                                .andOrderIdEqualTo(oPayment.getOrderId())
                                .andPaymentStatusEqualTo(PaymentStatus.DS.code)
                                .andPaymentIdEqualTo(oPayment.getId());
                        List<OPaymentDetail> oPaymentDetailsList = oPaymentDetailMapper.selectByExample(customStaginDetailQeury);
                        BigDecimal dkfqAmount = BigDecimal.ZERO;
                        for (OPaymentDetail oPaymentDetail : oPaymentDetailsList) {
                            dkfqAmount =dkfqAmount.add(oPaymentDetail.getPayAmount());
                        }
                        //欠款和代扣分期是否一致，不一致抛出异常
                        if(dkfqAmount.compareTo(oPayment.getOutstandingAmount())!=0){
                            throw new MessageException("待付款和分期欠款不匹配");
                        }
                        //审批通过更新自定义分期为待付款
                        for (OPaymentDetail oPaymentDetail : oPaymentDetailsList) {
                            oPaymentDetail.setRealPayAmount(BigDecimal.ZERO);
                            oPaymentDetail.setPaymentStatus(PaymentStatus.DF.code);
                            if(1!=oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail)){
                                throw new MessageException("付款明细处理失败，请重试！");
                            }
                        }

                    }else {
                        //如果有待付款 对待付款进行分期
                        if (oPayment.getOutstandingAmount().compareTo(BigDecimal.ZERO) > 0) {
                            temp.setTime(oPayment.getDownPaymentDate());
                            //分期数据
                            List<Map> SF1_data = StageUtil.stageOrder(
                                    oPayment.getOutstandingAmount(),
                                    oPayment.getDownPaymentCount().intValue(),
                                    oPayment.getDownPaymentDate(), temp.get(Calendar.DAY_OF_MONTH));

                            //明细处理
                            for (Map datum : SF1_data) {
                                OPaymentDetail record = new OPaymentDetail();
                                record.setId(idService.genId(TabId.o_payment_detail));
                                record.setBatchCode(batchCode);
                                record.setPaymentId(oPayment.getId());
                                record.setPaymentType(PamentIdType.ORDER_FKD.code);
                                record.setOrderId(oPayment.getOrderId());
                                record.setPayType(PaymentType.FRFQ.code);
                                record.setPayAmount((BigDecimal) datum.get("item"));
                                record.setRealPayAmount(BigDecimal.ZERO);
                                record.setPlanPayTime((Date) datum.get("date"));
                                record.setPlanNum((BigDecimal) datum.get("count"));
                                record.setAgentId(oPayment.getAgentId());
                                record.setPaymentStatus(PaymentStatus.DF.code);
                                record.setcUser(oPayment.getUserId());
                                record.setcDate(d.getTime());
                                record.setStatus(Status.STATUS_1.status);
                                record.setVersion(Status.STATUS_1.status);
                                if (1 != oPaymentDetailMapper.insert(record)) {
                                    logger.info("代理商订单审批完成:明细生成失败:订单ID:{},付款方式:{}，明细ID:{}",
                                            order.getId(),
                                            oPayment.getPayMethod(),
                                            record.getId());
                                    throw new MessageException("分期处理");
                                }
                                logger.info("代理商订单审批完成:明细生成:订单ID:{},付款方式:{}，明细ID:{}",
                                        order.getId(),
                                        oPayment.getPayMethod(),
                                        record.getId());
                            }
                        }
                    }
                    break;

                case "SF2"://打款分期
                    if(oPayment.getDownPayment()==null || oPayment.getDownPayment().compareTo(BigDecimal.ZERO)<=0){
                        logger.info("代理商订单审批完成:首付金额为空:{},{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                        throw new MessageException("首付金额为空");
                    }
                    if(oPayment.getDownPaymentCount()==null || oPayment.getDownPaymentCount().compareTo(BigDecimal.ZERO)<=0){
                        logger.info("代理商订单审批完成:分期数据为错误:{},{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                        throw new MessageException("分期数有误");
                    }
                    if(oPayment.getDownPaymentDate()==null || oPayment.getDownPaymentDate().compareTo(orderTime)<0){
                        logger.info("代理商订单审批完成:分期数据为错误:{},{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                        throw new MessageException("分期日期错误");
                    }
                    if(oPayment.getActualReceipt()==null || oPayment.getActualReceipt().compareTo(BigDecimal.ZERO)<=0){
                        logger.info("代理商订单审批完成:实际收款金额不能为空:{},{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                        throw new MessageException("实际收款金额不能为空");
                    }
                    //结算单 已付金额，代付金额，付款状态
                    //已付款 首付实付金额
                    oPayment.setRealAmount(oPayment.getRealAmount().add(oPayment.getActualReceipt()));
                    //待付  待付金额减去实付金额
                    oPayment.setOutstandingAmount(oPayment.getOutstandingAmount().subtract(oPayment.getActualReceipt()));

                    //首付明细插入
                    if (oPayment.getActualReceipt() != null && oPayment.getActualReceipt().compareTo(BigDecimal.ZERO) > 0) {
                        //添加首付明细
                        OPaymentDetail record_SF2 = new OPaymentDetail();
                        record_SF2.setId(idService.genId(TabId.o_payment_detail));
                        record_SF2.setBatchCode(batchCode);
                        record_SF2.setPaymentId(oPayment.getId());
                        record_SF2.setPaymentType(PamentIdType.ORDER_FKD.code);
                        record_SF2.setOrderId(oPayment.getOrderId());
                        record_SF2.setPayType(PaymentType.SF.code);
                        record_SF2.setPayAmount(oPayment.getActualReceipt());
                        record_SF2.setRealPayAmount(oPayment.getActualReceipt());
                        record_SF2.setPlanPayTime(d.getTime());
                        record_SF2.setPayTime(record_SF2.getPlanPayTime());
                        record_SF2.setPlanNum(Status.STATUS_0.status);
                        record_SF2.setAgentId(oPayment.getAgentId());
                        record_SF2.setPaymentStatus(PaymentStatus.JQ.code);
                        record_SF2.setcUser(oPayment.getUserId());
                        record_SF2.setcDate(d.getTime());
                        record_SF2.setStatus(Status.STATUS_1.status);
                        record_SF2.setVersion(Status.STATUS_1.status);
                        if (1 != oPaymentDetailMapper.insert(record_SF2)) {
                            throw new MessageException("首付错误");
                        }
                        logger.info("代理商订单审批完成处理明细完成首付数据成功{}:{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                    }

                    //抵扣处理
                    if(oPayment.getDeductionAmount()!=null && oPayment.getDeductionAmount().compareTo(BigDecimal.ZERO)>0){
                        //已付款 已付金额加上抵扣金额
                        oPayment.setRealAmount(oPayment.getRealAmount().add(oPayment.getDeductionAmount()));
                        //待付  待付减去抵扣金额
                        oPayment.setOutstandingAmount(oPayment.getOutstandingAmount().subtract(oPayment.getDeductionAmount()));
                        //抵扣操作
                        AgentResult dealOrderDeductionRes =  dealOrderDeduction(oPayment);
                        if(dealOrderDeductionRes.isOK()){
                            List<OPaymentDetail> details =  (List<OPaymentDetail>)dealOrderDeductionRes.getData();
                            for (OPaymentDetail detail : details) {
                                if(1!=oPaymentDetailMapper.insertSelective(detail)){
                                    logger.info("代理商订单审批完成:抵扣款项插入失败:{},{},{},{}", order.getId(), oPayment.getPayMethod(), oPayment.getDeductionType(), oPayment.getDeductionAmount());
                                    throw new MessageException("抵扣操作失败");
                                }else{
                                    logger.info("代理商订单审批完成:抵扣款项插入成功:{},{},{},{},{}", order.getId(), oPayment.getPayMethod(), oPayment.getDeductionType(), oPayment.getDeductionAmount(),detail.getPayAmount());
                                }
                            }
                        }else{
                            throw new MessageException("抵扣操作失败");
                        }
                    }
                    //如果是自定义分期
                    if(StringUtils.isNotBlank(oPayment.getCustomStaging()) && Status.STATUS_1.status.toPlainString().equals(oPayment.getCustomStaging())) {
                        //这里不处理首付值处理分期
                        //更新付款分期为审批通过，检查分期金额是否等于待付金额
                        OPaymentDetailExample customStaginDetailQeury = new OPaymentDetailExample();
                        customStaginDetailQeury.or()
                                .andStatusEqualTo(Status.STATUS_1.status)
                                .andPayTypeEqualTo(PaymentType.DKFQ.code)
                                .andOrderIdEqualTo(oPayment.getOrderId())
                                .andPaymentStatusEqualTo(PaymentStatus.DS.code)
                                .andPaymentIdEqualTo(oPayment.getId());
                        List<OPaymentDetail> oPaymentDetailsList = oPaymentDetailMapper.selectByExample(customStaginDetailQeury);
                        BigDecimal dkfqAmount = BigDecimal.ZERO;
                        for (OPaymentDetail oPaymentDetail : oPaymentDetailsList) {
                            dkfqAmount =dkfqAmount.add(oPaymentDetail.getPayAmount());
                        }
                        //欠款和代扣分期是否一致，不一致抛出异常
                        if(dkfqAmount.compareTo(oPayment.getOutstandingAmount())!=0){
                            throw new MessageException("待付款和分期欠款不匹配");
                        }
                        //审批通过更新自定义分期为待付款
                        for (OPaymentDetail oPaymentDetail : oPaymentDetailsList) {
                            oPaymentDetail.setRealPayAmount(BigDecimal.ZERO);
                            oPaymentDetail.setPaymentStatus(PaymentStatus.DF.code);
                            if(1!=oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail)){
                                throw new MessageException("付款明细处理失败，请重试！");
                            }
                        }

                    }else {
                        //待付款生成分期
                        if (oPayment.getOutstandingAmount().compareTo(BigDecimal.ZERO) > 0) {

                            temp.setTime(oPayment.getDownPaymentDate());
                            //分期数据
                            List<Map> SF2_data = StageUtil.stageOrder(
                                    oPayment.getOutstandingAmount(),
                                    oPayment.getDownPaymentCount().intValue(),
                                    oPayment.getDownPaymentDate(), temp.get(Calendar.DAY_OF_MONTH));

                            //明细处理
                            for (Map datum : SF2_data) {
                                OPaymentDetail record = new OPaymentDetail();
                                record.setId(idService.genId(TabId.o_payment_detail));
                                record.setBatchCode(batchCode);
                                record.setPaymentId(oPayment.getId());
                                record.setPaymentType(PamentIdType.ORDER_FKD.code);
                                record.setOrderId(oPayment.getOrderId());
                                record.setPayType(PaymentType.DKFQ.code);
                                record.setPayAmount((BigDecimal) datum.get("item"));
                                record.setRealPayAmount(BigDecimal.ZERO);
                                record.setPlanPayTime((Date) datum.get("date"));
                                record.setPlanNum((BigDecimal) datum.get("count"));
                                record.setAgentId(oPayment.getAgentId());
                                record.setPaymentStatus(PaymentStatus.DF.code);
                                record.setcUser(oPayment.getUserId());
                                record.setcDate(d.getTime());
                                record.setStatus(Status.STATUS_1.status);
                                record.setVersion(Status.STATUS_1.status);

                                if (1 != oPaymentDetailMapper.insert(record)) {
                                    logger.info("代理商订单审批完成:明细生成失败:订单ID:{},付款单ID:{},付款方式:{}，明细ID:{}", order.getId(), oPayment.getId(), oPayment.getPayMethod(), record.getId());
                                    throw new MessageException("分期处理");
                                }
                                logger.info("代理商订单审批完成:明细生成:订单ID:{},付款单ID:{},付款方式:{}，明细ID:{}", order.getId(), oPayment.getId(), oPayment.getPayMethod(), record.getId());
                            }

                        }
                    }
                    logger.info("代理商订单审批完成处理明细完成{}:{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                    break;
                case "QT"://抵扣金额必须等于待付金额
                    //如果存在抵扣 进行抵扣处理
                    if(StringUtils.isNotBlank(oPayment.getDeductionType()) && oPayment.getDeductionAmount()!=null && oPayment.getDeductionAmount().compareTo(BigDecimal.ZERO) >0) {
                        //抵扣金额判断
                        if (oPayment.getDeductionAmount() == null || oPayment.getDeductionAmount().compareTo(BigDecimal.ZERO) <= 0) {
                            logger.info("代理商订单审批完成QT抵扣不可为空{}:{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                            throw new MessageException("代理商订单审批完成QT抵扣不可为空");
                        }
                        if (oPayment.getDeductionType() == null || StringUtils.isBlank(oPayment.getDeductionType())) {
                            logger.info("代理商订单审批完成QT抵扣类型不可为空{}:{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                            throw new MessageException("抵扣类型不可为空");
                        }
                        //抵扣操作
                        AgentResult dealOrderDeductionRes = dealOrderDeduction(oPayment);
                        if (dealOrderDeductionRes.isOK()) {
                            List<OPaymentDetail> details = (List<OPaymentDetail>) dealOrderDeductionRes.getData();
                            for (OPaymentDetail detail : details) {
                                if (1 != oPaymentDetailMapper.insertSelective(detail)) {
                                    logger.info("代理商订单审批完成:抵扣款项插入失败:{},{},{},{}", order.getId(), oPayment.getPayMethod(), oPayment.getDeductionType(), oPayment.getDeductionAmount());
                                    throw new MessageException("抵扣操作失败");
                                } else {
                                    logger.info("代理商订单审批完成:抵扣款项插入成功:{},{},{},{},{}", order.getId(), oPayment.getPayMethod(), oPayment.getDeductionType(), oPayment.getDeductionAmount(), detail.getPayAmount());
                                }
                            }
                        } else {
                            logger.info("代理商订单审批完成:抵扣操作失败:{},{},{},{}",
                                    order.getId(),
                                    oPayment.getPayMethod(),
                                    oPayment.getDeductionType(),
                                    oPayment.getDeductionAmount());
                            throw new MessageException("抵扣操作失败");

                        }
                    }else{
                        oPayment.setDeductionAmount(BigDecimal.ZERO);
                    }
                    //设置待付和已付
                    logger.info("代理商订单审批完成处理明细完成数据成功{}:{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                    oPayment.setRealAmount(oPayment.getRealAmount().add(oPayment.getDeductionAmount()));//已付款
                    oPayment.setOutstandingAmount(oPayment.getOutstandingAmount().subtract(oPayment.getDeductionAmount()));//待付
                    if(oPayment.getOutstandingAmount().compareTo(BigDecimal.ZERO)==0){
                        oPayment.setPayStatus(PayStatus.CLOSED.code);
                        order.setClearStatus(ClearStatus.CLEARED.status);
                    }else{
                        logger.info("代理商订单审批完成QT抵扣金额不等于订单待付金额{}:{},{},{}",
                                order.getId(),
                                oPayment.getPayMethod(),
                                oPayment.getOutstandingAmount(),
                                oPayment.getDeductionAmount());
                        //添加抵扣后的余款为欠款
                        OPaymentDetail record_QT = new OPaymentDetail();
                        record_QT.setId(idService.genId(TabId.o_payment_detail));
                        record_QT.setBatchCode(batchCode);
                        record_QT.setPaymentId(oPayment.getId());
                        record_QT.setPaymentType(PamentIdType.ORDER_FKD.code);
                        record_QT.setOrderId(oPayment.getOrderId());
                        record_QT.setPayType(PaymentType.SF.code);
                        record_QT.setPayAmount(oPayment.getOutstandingAmount());
                        record_QT.setRealPayAmount(BigDecimal.ZERO);
                        record_QT.setPlanPayTime(d.getTime());
                        record_QT.setPlanNum(Status.STATUS_0.status);
                        record_QT.setAgentId(oPayment.getAgentId());
                        record_QT.setPaymentStatus(PaymentStatus.DF.code);
                        record_QT.setcUser(oPayment.getUserId());
                        record_QT.setcDate(d.getTime());
                        record_QT.setStatus(Status.STATUS_1.status);
                        record_QT.setVersion(Status.STATUS_1.status);
                        if (1 != oPaymentDetailMapper.insert(record_QT)) {
                            throw new MessageException("首付错误");
                        }
                        logger.info("代理商订单审批完成处理明细完成首付数据成功{}:{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                    }
                    logger.info("代理商订单审批完成处理明细完成{}:{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                    break;
                default:
                    throw new MessageException("未知的支付方式");
            }

            //检查订单状态
            if(oPayment.getOutstandingAmount().compareTo(BigDecimal.ZERO)==0){
                oPayment.setPayStatus(PayStatus.CLOSED.code);
                order.setClearStatus(ClearStatus.CLEARED.status);
            }else{
                oPayment.setPayStatus(PayStatus.PART_PAYMENT.code);
                order.setClearStatus(ClearStatus.UNCLEARED.status);
            }
            //订单更新
            if (1 != orderMapper.updateByPrimaryKeySelective(order)) {
                throw new MessageException("订单更新异常");
            }
            //订单线下付款更新
            AgentResult ocash = oCashReceivablesService.finishProcing(CashPayType.PAYMENT,oPayment.getId(),null);
            if(!ocash.isOK()){
                throw new MessageException("订单现金付款明细更新异常");
            }
            //付款单处理
            if (1 != oPaymentMapper.updateByPrimaryKeySelective(oPayment)) {
                throw new MessageException("订单更新异常");
            }
            //  发货单状态修改
            OReceiptOrderExample oReceiptOrderExample = getoReceiptOrderExample();
            oReceiptOrderExample.or().andStatusEqualTo(Status.STATUS_1.status)
                    .andOrderIdEqualTo(order.getId())
                    .andReceiptStatusEqualTo(OReceiptStatus.TEMPORARY_STORAGE.code);
            List<OReceiptOrder> oReceiptOrderList = oReceiptOrderMapper.selectByExample(oReceiptOrderExample);
            for (OReceiptOrder oReceiptOrder : oReceiptOrderList) {
                oReceiptOrder.setReceiptStatus(OReceiptStatus.WAITING_LIST.code);
                oReceiptOrder.setuTime(d.getTime());
                if(1!=oReceiptOrderMapper.updateByPrimaryKeySelective(oReceiptOrder)){
                    logger.error("更新收货单异常{}",order.getId());
                    throw new MessageException("更新收货单异常");
                }
            }
            //  发货单商品状态修改
            OReceiptProExample oReceiptProExample = new OReceiptProExample();
            oReceiptProExample.or().andStatusEqualTo(Status.STATUS_1.status)
                    .andOrderidEqualTo(order.getId())
                    .andReceiptProStatusEqualTo(OReceiptStatus.TEMPORARY_STORAGE.code);
            List<OReceiptPro>  pros =  oReceiptProMapper.selectByExample(oReceiptProExample);
            for (OReceiptPro pro : pros) {
                //  发货单商品状态修改 更新成待排单
                pro.setReceiptProStatus(OReceiptStatus.WAITING_LIST.code);
                pro.setuTime(d.getTime());
                if(1!=oReceiptProMapper.updateByPrimaryKeySelective(pro)){
                    logger.error("更新收货单商品异常{}",order.getId());
                    throw new MessageException("更新收货单商品异常");
                }
            }

            //根据订单审批成功检查业务状态 是否需要更新成从未激活更新成启用
            Agent agent_check = agentMapper.selectByPrimaryKey(order.getAgentId());
            AgentBusInfo ab_check = agentBusInfoMapper.selectByPrimaryKey(order.getBusId());
            //未激活业务
            if(ab_check.getBusStatus()!=null && ab_check.getBusStatus().compareTo(Status.STATUS_2.status)==0){
                logger.info("代理商订单审批完审批完成激活业务{}",rel.getBusId());
                ab_check.setBusStatus(BusinessStatus.Enabled.status);
                if(1!=agentBusInfoMapper.updateByPrimaryKeySelective(ab_check)){
                    logger.error("更新业务 未激活 到 启用 失败{}{}",order.getId(),ab_check.getId());
                }
            }
            //未入网未激活状态下更新代理商状态
            if(agent_check.getcIncomStatus()!=null && agent_check.getcIncomStatus().compareTo(AgentInStatus.NO_ACT.status)==0){
                logger.info("代理商订单审批完审批完成激活代理商{}",rel.getBusId());
                agent_check.setcIncomStatus(AgentInStatus.IN.status);
                agentMapper.updateByPrimaryKeySelective(agent_check);
            }


            //TODO 处理线下打款通知kafka
            try {
                logger.info("订单审批通过 信息开始发送到kafka:{}",order.getId());
                paymentDetailService.sendSFPayMentToPlatform(order.getId());
            } catch (Exception e) {
                logger.info("首付款项发送kafka失败:{}",e.getMessage());
                e.printStackTrace();
            }

        } else if (actname.equals("reject_end")) {
            logger.info("代理商订单审批完审批拒绝{}",rel.getBusId());
            rel.setActivStatus(AgStatus.Refuse.name());
            if(busActRelService.updateByPrimaryKey(rel)!=1){
                throw new MessageException("请重新提交");
            }
            //订单信息
            OOrder order = orderMapper.selectByPrimaryKey(rel.getBusId());
            OPaymentExample example = new OPaymentExample();
            example.or().andOrderIdEqualTo(order.getId()).andStatusEqualTo(Status.STATUS_1.status);
            List<OPayment> payments = oPaymentMapper.selectByExample(example);
            if (payments.size() != 1) throw new MessageException("支付单信息错误");
            OPayment oPayment = payments.get(0);
            //更新订单状态 审批状态，结算状态 订单生效时间
            order.setOrderStatus(OrderStatus.UNENABLE.status);
            order.setReviewStatus(AgStatus.Refuse.status);

            //付款单设置
            //结算单 已付金额，代付金额，付款状态
            oPayment.setPayStatus(PayStatus.NON_PAYMENT.code);//付款状态
            oPayment.setRealAmount(Status.STATUS_0.status);//已付款
            oPayment.setOutstandingAmount(oPayment.getPayAmount());//待付

            logger.info("代理商订单审批完成处理明细完成:{},{}",
                    order.getId(),
                    oPayment.getPayMethod());

            //处理付款明细
            OPaymentDetailExample oPaymentDetailExample = new OPaymentDetailExample();
            oPaymentDetailExample.or()
                    .andOrderIdEqualTo(oPayment.getOrderId())
                    .andPaymentIdEqualTo(oPayment.getId())
                    .andStatusEqualTo(Status.STATUS_1.status);
            List<OPaymentDetail> details = oPaymentDetailMapper.selectByExample(oPaymentDetailExample);
            for (OPaymentDetail detail : details) {
                detail.setStatus(Status.STATUS_0.status);
                detail.setPaymentStatus(PaymentStatus.DS.code);
                if (1 != oPaymentDetailMapper.updateByPrimaryKeySelective(detail)) {
                    logger.error("代理商订单审批完成处理明细{}:{},{},{},{}",
                            order.getId(),
                            detail.getId(),
                            detail.getBatchCode(),
                            detail.getPayAmount(),
                            detail.getPaymentStatus());
                    throw new MessageException("明细处理异常");
                } else {
                    logger.info("代理商订单审批完成处理明细{}:{},{},{},{}",
                            order.getId(),
                            detail.getId(),
                            detail.getBatchCode(),
                            detail.getPayAmount(),
                            detail.getPaymentStatus());
                }
            }
            logger.info("代理商订单审批完成处理明细完成{}:{},{}",
                    order.getId(),
                    oPayment.getPayMethod());

            capitalService.refuseUnfreeze(oPayment.getId(),SrcType.DD,oPayment.getUserId());
            //订单更新
            if (1 != orderMapper.updateByPrimaryKeySelective(order)) {
                throw new MessageException("订单更新异常");
            }
            //订单线下付款更新
            AgentResult ocash = oCashReceivablesService.refuseProcing(CashPayType.PAYMENT,oPayment.getId(),null);
            if(!ocash.isOK()){
                throw new MessageException("订单现金付款明细更新异常");
            }
            //付款单处理
            if (1 != oPaymentMapper.updateByPrimaryKeySelective(oPayment)) {
                throw new MessageException("订单更新异常");
            }
        }
        return AgentResult.ok();
    }

    /**
     * 处理订单抵扣 此方法已经废弃
     * @param payment 付款单
     * @return
     */
    //    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    //    @Override
    @Deprecated
    private AgentResult dealOrderDeduction_befor(OPayment payment)throws Exception {

        if(StringUtils.isBlank(payment.getDeductionType()))return AgentResult.ok();
        //可扣款的缴款项
        List<Capital>  listc =  agentQueryService.capitalQuery(payment.getAgentId(),payment.getDeductionType());
        //需要的扣款金额
        BigDecimal deductionAmount =  payment.getDeductionAmount().setScale(2,BigDecimal.ROUND_HALF_UP);

        BigDecimal for_deal = deductionAmount.setScale(2,BigDecimal.ROUND_HALF_UP);

        List<OPaymentDetail> OPaymentDetail = new ArrayList<>();

        String batchCode = Calendar.getInstance().getTime().getTime()+"";
        if(listc.size()>0){
            //检查保证金等是否有分期
            for (Capital capitalItem : listc) {
                //银行汇款抵扣
                if (PayType.YHHK.code.equals(capitalItem.getcPayType() + "")){
                    if (capitalItem.getcFqInAmount().compareTo(for_deal) > 0) {
                        //扣除缴款项
                        capitalItem.setcAmount(capitalItem.getcAmount().subtract(for_deal));
                        capitalItem.setcInAmount(for_deal);
                        capitalItem.setcIsin(Status.STATUS_0.status);
                        capitalItem.setcBusStatus(Status.STATUS_4.status);//已扣款，已拆分，已冻结 部分扣款等
                        if (capitalMapper.updateByPrimaryKeySelective(capitalItem) != 1) {
                            throw new MessageException("更新缴款项失败");
                        }
                        //添加抵扣明细
                        OPaymentDetail record_QT = new OPaymentDetail();
                        record_QT.setId(idService.genId(TabId.o_payment_detail));
                        record_QT.setBatchCode(batchCode);
                        record_QT.setPaymentId(payment.getId());
                        record_QT.setPaymentType(PamentIdType.ORDER_FKD.code);
                        record_QT.setOrderId(payment.getOrderId());
                        record_QT.setPayType(payment.getDeductionType());
                        record_QT.setPayAmount(for_deal);
                        record_QT.setRealPayAmount(for_deal);
                        record_QT.setPlanPayTime(Calendar.getInstance().getTime());
                        record_QT.setPayTime(record_QT.getPlanPayTime());
                        record_QT.setPlanNum(Status.STATUS_0.status);
                        record_QT.setAgentId(payment.getAgentId());
                        record_QT.setPaymentStatus(PaymentStatus.JQ.code);
                        record_QT.setSrcId(capitalItem.getId());
                        record_QT.setSrcType(PamentSrcType.CAPITAL_DIKOU.code);
                        record_QT.setcUser(payment.getUserId());
                        record_QT.setcDate(Calendar.getInstance().getTime());
                        record_QT.setStatus(Status.STATUS_1.status);
                        record_QT.setVersion(Status.STATUS_1.status);
                        OPaymentDetail.add(record_QT);
                        for_deal = new BigDecimal(0);
                        break;
                    } else if (capitalItem.getcAmount().compareTo(for_deal) == 0) {
                        //扣除缴款项
                        capitalItem.setcAmount(capitalItem.getcAmount().subtract(for_deal));
                        capitalItem.setcInAmount(for_deal);
                        capitalItem.setcIsin(Status.STATUS_0.status);
                        capitalItem.setcBusStatus(Status.STATUS_4.status);//已扣款，已拆分，已冻结 部分扣款等
                        if (capitalMapper.updateByPrimaryKeySelective(capitalItem) != 1) {
                            throw new MessageException("更新缴款项失败");
                        }
                        //添加抵扣明细
                        OPaymentDetail record_QT = new OPaymentDetail();
                        record_QT.setId(idService.genId(TabId.o_payment_detail));
                        record_QT.setBatchCode(batchCode);
                        record_QT.setPaymentId(payment.getId());
                        record_QT.setPaymentType(PamentIdType.ORDER_FKD.code);
                        record_QT.setOrderId(payment.getOrderId());
                        record_QT.setPayType(payment.getDeductionType());
                        record_QT.setPayAmount(for_deal);
                        record_QT.setRealPayAmount(for_deal);
                        record_QT.setPlanPayTime(Calendar.getInstance().getTime());
                        record_QT.setPayTime(record_QT.getPlanPayTime());
                        record_QT.setPlanNum(Status.STATUS_0.status);
                        record_QT.setAgentId(payment.getAgentId());
                        record_QT.setPaymentStatus(PaymentStatus.JQ.code);
                        record_QT.setSrcId(capitalItem.getId());
                        record_QT.setSrcType(PamentSrcType.CAPITAL_DIKOU.code);
                        record_QT.setcUser(payment.getUserId());
                        record_QT.setcDate(Calendar.getInstance().getTime());
                        record_QT.setStatus(Status.STATUS_1.status);
                        record_QT.setVersion(Status.STATUS_1.status);
                        OPaymentDetail.add(record_QT);
                        for_deal = new BigDecimal(0);
                        break;
                    } else if (capitalItem.getcAmount().compareTo(for_deal) < 0) {
                        BigDecimal camount = capitalItem.getcAmount();
                        //扣除缴款项
                        capitalItem.setcInAmount(camount);
                        capitalItem.setcAmount(new BigDecimal(0));
                        capitalItem.setcIsin(Status.STATUS_1.status);
                        capitalItem.setcBusStatus(Status.STATUS_1.status);//已扣款，已拆分，已冻结 部分扣款等
                        if (capitalMapper.updateByPrimaryKeySelective(capitalItem) != 1) {
                            throw new MessageException("更新缴款项失败");
                        }
                        //添加抵扣明细
                        OPaymentDetail record_QT = new OPaymentDetail();
                        record_QT.setId(idService.genId(TabId.o_payment_detail));
                        record_QT.setBatchCode(batchCode);
                        record_QT.setPaymentId(payment.getId());
                        record_QT.setPaymentType(PamentIdType.ORDER_FKD.code);
                        record_QT.setOrderId(payment.getOrderId());
                        record_QT.setPayType(payment.getDeductionType());
                        record_QT.setPayAmount(camount);
                        record_QT.setRealPayAmount(camount);
                        record_QT.setPlanPayTime(Calendar.getInstance().getTime());
                        record_QT.setPayTime(record_QT.getPlanPayTime());
                        record_QT.setPlanNum(Status.STATUS_0.status);
                        record_QT.setAgentId(payment.getAgentId());
                        record_QT.setPaymentStatus(PaymentStatus.JQ.code);
                        record_QT.setSrcId(capitalItem.getId());
                        record_QT.setSrcType(PamentSrcType.CAPITAL_DIKOU.code);
                        record_QT.setcUser(payment.getUserId());
                        record_QT.setcDate(Calendar.getInstance().getTime());
                        record_QT.setStatus(Status.STATUS_1.status);
                        record_QT.setVersion(Status.STATUS_1.status);
                        OPaymentDetail.add(record_QT);
                        for_deal = for_deal.subtract(camount);
                    }
                    //分润分期抵扣
                }else if(PayType.FRDK.code.equals(capitalItem.getcPayType() + "")){

                    //已入账金额
                    BigDecimal cInAmount = capitalItem.getcInAmount()==null?new BigDecimal(0):capitalItem.getcInAmount();
                    //分润已抵扣金额
                    BigDecimal cFqInAmount = capitalItem.getcFqInAmount()==null?new BigDecimal(0):capitalItem.getcFqInAmount();
                    //分润已抵扣金额减去消费金额 大于抵扣金额
                    if (cFqInAmount.subtract(cInAmount).compareTo(for_deal) > 0) {
                        //扣除缴款项 用保证金抵扣 不用分期金额抵扣
                        capitalItem.setcAmount(capitalItem.getcAmount().subtract(for_deal));
                        capitalItem.setcInAmount(cInAmount.add(for_deal));
                        capitalItem.setcIsin(Status.STATUS_0.status);
                        capitalItem.setcBusStatus(Status.STATUS_4.status);//已扣款，已拆分，已冻结 部分扣款等
                        if (capitalMapper.updateByPrimaryKeySelective(capitalItem) != 1) {
                            throw new MessageException("更新缴款项失败");
                        }
                        //添加抵扣明细
                        OPaymentDetail record_QT = new OPaymentDetail();
                        record_QT.setId(idService.genId(TabId.o_payment_detail));
                        record_QT.setBatchCode(batchCode);
                        record_QT.setPaymentId(payment.getId());
                        record_QT.setPaymentType(PamentIdType.ORDER_FKD.code);
                        record_QT.setOrderId(payment.getOrderId());
                        record_QT.setPayType(payment.getDeductionType());
                        record_QT.setPayAmount(for_deal);
                        record_QT.setRealPayAmount(for_deal);
                        record_QT.setPlanPayTime(Calendar.getInstance().getTime());
                        record_QT.setPayTime(record_QT.getPlanPayTime());
                        record_QT.setPlanNum(Status.STATUS_0.status);
                        record_QT.setAgentId(payment.getAgentId());
                        record_QT.setPaymentStatus(PaymentStatus.JQ.code);
                        record_QT.setSrcId(capitalItem.getId());
                        record_QT.setSrcType(PamentSrcType.CAPITAL_DIKOU.code);
                        record_QT.setcUser(payment.getUserId());
                        record_QT.setcDate(Calendar.getInstance().getTime());
                        record_QT.setStatus(Status.STATUS_1.status);
                        record_QT.setVersion(Status.STATUS_1.status);
                        OPaymentDetail.add(record_QT);
                        for_deal = new BigDecimal(0);
                        break;
                    } else if (cFqInAmount.subtract(cInAmount).compareTo(for_deal) == 0) {
                        //扣除缴款项
                        capitalItem.setcAmount(capitalItem.getcAmount().subtract(for_deal));
                        capitalItem.setcInAmount(cInAmount.add(for_deal));
                        capitalItem.setcIsin(Status.STATUS_0.status);
                        capitalItem.setcBusStatus(Status.STATUS_4.status);//已扣款，已拆分，已冻结 部分扣款等
                        if (capitalMapper.updateByPrimaryKeySelective(capitalItem) != 1) {
                            throw new MessageException("更新缴款项失败");
                        }
                        //添加抵扣明细
                        OPaymentDetail record_QT = new OPaymentDetail();
                        record_QT.setId(idService.genId(TabId.o_payment_detail));
                        record_QT.setBatchCode(batchCode);
                        record_QT.setPaymentId(payment.getId());
                        record_QT.setPaymentType(PamentIdType.ORDER_FKD.code);
                        record_QT.setOrderId(payment.getOrderId());
                        record_QT.setPayType(payment.getDeductionType());
                        record_QT.setPayAmount(for_deal);
                        record_QT.setRealPayAmount(for_deal);
                        record_QT.setPlanPayTime(Calendar.getInstance().getTime());
                        record_QT.setPayTime(record_QT.getPlanPayTime());
                        record_QT.setPlanNum(Status.STATUS_0.status);
                        record_QT.setAgentId(payment.getAgentId());
                        record_QT.setPaymentStatus(PaymentStatus.JQ.code);
                        record_QT.setSrcId(capitalItem.getId());
                        record_QT.setSrcType(PamentSrcType.CAPITAL_DIKOU.code);
                        record_QT.setcUser(payment.getUserId());
                        record_QT.setcDate(Calendar.getInstance().getTime());
                        record_QT.setStatus(Status.STATUS_1.status);
                        record_QT.setVersion(Status.STATUS_1.status);
                        OPaymentDetail.add(record_QT);
                        for_deal = new BigDecimal(0);
                        break;
                    } else if (cFqInAmount.subtract(cInAmount).compareTo(for_deal) < 0) {

                        //本次抵扣
                        BigDecimal realIn = cFqInAmount.subtract(cInAmount);
                        //之前抵扣
                        BigDecimal cInAmount_local = capitalItem.getcInAmount()==null?new BigDecimal(0):capitalItem.getcInAmount();
                        //扣除缴款项
                        capitalItem.setcInAmount(cInAmount_local.add(realIn));
                        capitalItem.setcAmount(capitalItem.getcAmount().subtract(realIn));
                        if(capitalItem.getcAmount().compareTo(BigDecimal.ZERO)>0){
                            capitalItem.setcIsin(Status.STATUS_0.status);
                            capitalItem.setcBusStatus(Status.STATUS_4.status);//已扣款，已拆分，已冻结 部分扣款等
                        }else{
                            capitalItem.setcIsin(Status.STATUS_1.status);
                            capitalItem.setcBusStatus(Status.STATUS_1.status);//已扣款，已拆分，已冻结 部分扣款等
                        }
                        if (capitalMapper.updateByPrimaryKeySelective(capitalItem) != 1) {
                            throw new MessageException("更新缴款项失败");
                        }

                        //添加抵扣明细
                        OPaymentDetail record_QT = new OPaymentDetail();
                        record_QT.setId(idService.genId(TabId.o_payment_detail));
                        record_QT.setBatchCode(batchCode);
                        record_QT.setPaymentId(payment.getId());
                        record_QT.setPaymentType(PamentIdType.ORDER_FKD.code);
                        record_QT.setOrderId(payment.getOrderId());
                        record_QT.setPayType(payment.getDeductionType());
                        record_QT.setPayAmount(realIn);
                        record_QT.setRealPayAmount(realIn);
                        record_QT.setPlanPayTime(Calendar.getInstance().getTime());
                        record_QT.setPayTime(record_QT.getPlanPayTime());
                        record_QT.setPlanNum(Status.STATUS_0.status);
                        record_QT.setAgentId(payment.getAgentId());
                        record_QT.setPaymentStatus(PaymentStatus.JQ.code);
                        record_QT.setSrcId(capitalItem.getId());
                        record_QT.setSrcType(PamentSrcType.CAPITAL_DIKOU.code);
                        record_QT.setcUser(payment.getUserId());
                        record_QT.setcDate(Calendar.getInstance().getTime());
                        record_QT.setStatus(Status.STATUS_1.status);
                        record_QT.setVersion(Status.STATUS_1.status);
                        OPaymentDetail.add(record_QT);
                        for_deal = for_deal.subtract(realIn);
                    }
                }
            }
        }
        if (for_deal.compareTo(BigDecimal.ZERO) != 0) return AgentResult.fail();
        return AgentResult.ok(OPaymentDetail);
    }

    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public AgentResult dealOrderDeduction(OPayment payment)throws Exception {
        if(StringUtils.isBlank(payment.getDeductionType()))return AgentResult.ok();
        //审批通过扣除冻结金额
        List<CapitalFlow> flows = capitalService.approvedDeduct(payment.getId(),SrcType.DD,payment.getUserId());
        List<OPaymentDetail> OPaymentDetail = new ArrayList<>();
        String batchCode = Calendar.getInstance().getTime().getTime()+"";
        if(flows.size()>0){
            //检查保证金等是否有分期
            for (CapitalFlow capitalFlow : flows) {
                //添加抵扣明细
                OPaymentDetail record_QT = new OPaymentDetail();
                record_QT.setId(idService.genIdInTran(TabId.o_payment_detail));
                record_QT.setBatchCode(batchCode);
                record_QT.setPaymentId(payment.getId());
                record_QT.setPaymentType(PamentIdType.ORDER_FKD.code);
                record_QT.setOrderId(payment.getOrderId());
                record_QT.setPayType(PaymentType.SF.code);
                record_QT.setPayAmount(capitalFlow.getcAmount());
                record_QT.setRealPayAmount(capitalFlow.getcAmount());
                record_QT.setPlanPayTime(Calendar.getInstance().getTime());
                record_QT.setPayTime(record_QT.getPlanPayTime());
                record_QT.setPlanNum(Status.STATUS_1.status);
                record_QT.setAgentId(payment.getAgentId());
                record_QT.setPaymentStatus(PaymentStatus.JQ.code);
                record_QT.setSrcId(capitalFlow.getId());
                record_QT.setSrcType(PamentSrcType.CAPITAL_DIKOU.code);
                record_QT.setcUser(payment.getUserId());
                record_QT.setcDate(Calendar.getInstance().getTime());
                record_QT.setStatus(Status.STATUS_1.status);
                record_QT.setVersion(Status.STATUS_1.status);
                OPaymentDetail.add(record_QT);
            }
        }
        return AgentResult.ok(OPaymentDetail);
    }

    private OReceiptOrderExample getoReceiptOrderExample() {
        return new OReceiptOrderExample();
    }

    @Override
    public OPayment selectByOrderId(String orderId) {
        OPaymentExample oPaymentExample = new OPaymentExample();
        OPaymentExample.Criteria criteria = oPaymentExample.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<OPayment> oPayments = oPaymentMapper.selectByExample(oPaymentExample);
        if (1 != oPayments.size()) {
            return null;
        }
        return oPayments.get(0);
    }

    /**
     * 订单管理:
     * 1、列表查询
     * 2、导出订单信息
     */
    @Override
    public PageInfo getOrderList(Map<String, Object> param, PageInfo pageInfo) {
        Long count = orderMapper.getOrderCount(param);
        List<Map<String, Object>> list = orderMapper.getOrderList(param);
        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(list);
        System.out.println("查询/导出============================================" + JSONObject.toJSON(list));
        return pageInfo;
    }

    /**
     * 根据给定的类型查询用户的缴款项金额和可用余额
     *
     * @param agentId
     * @param type
     * @return
     */
    @Override
    public AgentResult queryAgentCapital(String agentId, String type) {
        FastMap f = FastMap.fastSuccessMap();
        List<Capital> listc = agentQueryService.capitalQuery(agentId, type);
        if (listc.size() == 0) {
            f.putKeyV("all", 0);
            f.putKeyV("can", 0);
        } else {
            //总资金
            BigDecimal all = new BigDecimal(0);
            BigDecimal cannot = new BigDecimal(0);
            for (Capital capital : listc) {
                //银行汇款可抵扣
                if(PayType.YHHK.code.equals(capital.getcPayType())) {
                    all = all.add(capital.getcFqInAmount().add(capital.getFreezeAmt()));
                    cannot = cannot.add(capital.getFreezeAmt());
                }else if(PayType.FRDK.code.equals(capital.getcPayType())){
                    all = all.add(capital.getcFqInAmount().add(capital.getFreezeAmt()));
                    cannot = cannot.add(capital.getFreezeAmt());
                }
            }
            f.putKeyV("all", all);
            //可用资金 审批中的订单
//            List<OPayment> pamentS = queryApprovePayment(agentId, AgStatus.Approving.status, Arrays.asList(OrderStatus.CREATE.status));
//
//            for (OPayment pament : pamentS) {
//                if (StringUtils.isNotBlank(pament.getDeductionType())
//                        && pament.getDeductionType().equals(type)
//                        && pament.getDeductionAmount() != null
//                        && pament.getDeductionAmount().compareTo(BigDecimal.ZERO) > 0) {
//                    cannot = cannot.add(pament.getDeductionAmount());
//                }
//            }
            if (all.compareTo(cannot) >= 0) {
                f.putKeyV("can", all.subtract(cannot));
            } else {
                f.putKeyV("can", 0);
            }
        }
        return AgentResult.ok(f);
    }

    /**
     * 查询订单付款
     *
     * @param orderId
     * @return
     */
    @Override
    public AgentResult queryOrderForOSupplementPaymentdetail(String orderId, String agentId) {
        OOrder order = orderMapper.selectByPrimaryKey(orderId);
        OPaymentDetailExample example = new OPaymentDetailExample();
        example.or().andOrderIdEqualTo(orderId)
                .andStatusEqualTo(Status.STATUS_1.status)
                .andPaymentStatusIn(Arrays.asList(PaymentStatus.DF.code,PaymentStatus.BF.code, PaymentStatus.YQ.code))
                .andAgentIdEqualTo(agentId);
        example.setOrderByClause(" plan_pay_time asc ");
        List<OPaymentDetail> paymentDetails = oPaymentDetailMapper.selectByExample(example);
        if (paymentDetails.size() > 0) {
            return AgentResult.ok(FastMap.fastSuccessMap().putKeyV("order", order).putKeyV("paymentDetails", paymentDetails.get(0)));
        } else {
            return AgentResult.fail("没有需要补款的欠款");
        }
    }

    /**
     * 查询订单的订购单信息
     *
     * @param agentId
     * @param orderId
     * @return
     */
    @Override
    public List<Map<String, Object>> querySubOrderInfoList(String agentId, String orderId) {
        return orderMapper.queryOrderSubOrderProduct(orderId, agentId);
    }

    /**
     * 查询已派单信息
     *
     * @param agentId
     * @param orderId
     * @return
     */

    @Override
    public List<Map<String, Object>> queryHavePeiHuoProduct(String agentId, String orderId) {
        return orderMapper.queryHavePeiHuoProduct(orderId, agentId);
    }

    /**
     * 配货操作
     *
     * @param oReceiptOrder
     * @param oReceiptPro
     * @param sendNum
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult subOrderPeiHuo(OReceiptOrder oReceiptOrder, OReceiptPro oReceiptPro, int sendNum) throws Exception {

        if (StringUtils.isBlank(oReceiptOrder.getOrderId())
                || StringUtils.isBlank(oReceiptOrder.getAddressId())
                || StringUtils.isBlank(oReceiptOrder.getAgentId())
                || StringUtils.isBlank(oReceiptPro.getProId())
                || StringUtils.isBlank(oReceiptOrder.getuUser())
                || sendNum <= 0) {

            return AgentResult.fail("参数错误");
        }

        OOrder order = orderMapper.selectByPrimaryKey(oReceiptOrder.getOrderId());
        if (AgStatus.Approved.status.compareTo(order.getReviewStatus()) != 0) {
            return AgentResult.fail("审批未通过");
        }
        Calendar d = Calendar.getInstance();
        String userid = oReceiptOrder.getuUser();

        logger.info("订单配货:{},{},{},{},{}",
                oReceiptOrder.getOrderId(),
                oReceiptOrder.getAddressId(),
                oReceiptPro.getProId(),
                sendNum,
                oReceiptOrder.getcUser());

        //查询收货地址是否已经存在收货单中
        OReceiptOrderExample oReceiptOrderQuery = new OReceiptOrderExample();
        oReceiptOrderQuery.or()
                .andOrderIdEqualTo(oReceiptOrder.getOrderId())
                .andAddressIdEqualTo(oReceiptOrder.getAddressId())
                .andAgentIdEqualTo(oReceiptOrder.getAgentId())
                .andStatusEqualTo(Status.STATUS_1.status);

        //地址信息
        OAddress address = oAddressMapper.selectByPrimaryKey(oReceiptOrder.getAddressId());

        //订货单记录是否存在
        List<OReceiptOrder> OReceiptOrderList = oReceiptOrderMapper.selectByExample(oReceiptOrderQuery);
        OReceiptOrder receiptOrder_option = null;
        if (OReceiptOrderList.size() > 0) {
            receiptOrder_option = OReceiptOrderList.get(0);
        } else {
            //不存在则新增
            OReceiptOrder receiptOrder_db = new OReceiptOrder();
            receiptOrder_db.setId(idService.genId(TabId.o_receipt_order));
            receiptOrder_db.setOrderId(oReceiptOrder.getOrderId());
            receiptOrder_db.setReceiptNum(receiptOrder_db.getId());
            receiptOrder_db.setAddressId(address.getId());
            receiptOrder_db.setAddrRealname(address.getAddrRealname());
            receiptOrder_db.setAddrMobile(address.getAddrMobile());
            receiptOrder_db.setAddrProvince(address.getAddrProvince());
            receiptOrder_db.setAddrCity(address.getAddrCity());
            receiptOrder_db.setAddrDistrict(address.getAddrDistrict());
            receiptOrder_db.setAddrDetail(address.getAddrDetail());
            receiptOrder_db.setRemark(address.getRemark());
            receiptOrder_db.setZipCode(address.getZipCode());
            receiptOrder_db.setcTime(d.getTime());
            receiptOrder_db.setuTime(d.getTime());
            receiptOrder_db.setReceiptStatus(OReceiptStatus.TEMPORARY_STORAGE.code);
            receiptOrder_db.setuUser(userid);
            receiptOrder_db.setcUser(userid);
            receiptOrder_db.setStatus(Status.STATUS_1.status);
            receiptOrder_db.setVersion(Status.STATUS_0.status);
            receiptOrder_db.setAgentId(oReceiptOrder.getAgentId());

            if (1 != oReceiptOrderMapper.insertSelective(receiptOrder_db)) {
                throw new MessageException("收货单添加失败");
            }
            receiptOrder_option = receiptOrder_db;
        }

        //商品信息
        OSubOrderExample oSubOrderQuery = new OSubOrderExample();
        oSubOrderQuery.or()
                .andStatusEqualTo(Status.STATUS_1.status)
                .andOrderIdEqualTo(receiptOrder_option.getOrderId())
                .andProIdEqualTo(oReceiptPro.getProId());
        List<OSubOrder> oSubOrderList = oSubOrderMapper.selectByExample(oSubOrderQuery);
        if (oSubOrderList.size() == 0) throw new MessageException("订购单信息未找到");
        OSubOrder oSubOrder = oSubOrderList.get(0);

        //检查已配货的商品数量
        OReceiptProExample oReceiptProQuery = new OReceiptProExample();
        oReceiptProQuery.or()
                .andOrderidEqualTo(receiptOrder_option.getOrderId())
                .andProIdEqualTo(oSubOrder.getProId())
                .andStatusEqualTo(Status.STATUS_1.status);
        //计算已配货的商品数量
        List<OReceiptPro> OReceiptProListHaveIn = oReceiptProMapper.selectByExample(oReceiptProQuery);
        BigDecimal have_peihuo_count = new BigDecimal(0);
        for (OReceiptPro receiptPro : OReceiptProListHaveIn) {
            have_peihuo_count = have_peihuo_count.add(receiptPro.getProNum());
        }
        if (have_peihuo_count.add(new BigDecimal(sendNum)).compareTo(oSubOrder.getProNum()) > 0) {
            throw new MessageException("配货商品数量已超出订单商品数量");
        }

        //添加配货单商品
        oReceiptPro.setId(idService.genId(TabId.o_receipt_pro));
        oReceiptPro.setOrderid(receiptOrder_option.getOrderId());
        oReceiptPro.setReceiptId(receiptOrder_option.getId());
        oReceiptPro.setProId(oSubOrder.getProId());
        oReceiptPro.setProCode(oSubOrder.getProCode());
        oReceiptPro.setProName(oSubOrder.getProName());
        //传递配单的商品数量
        oReceiptPro.setProNum(new BigDecimal(sendNum));
        oReceiptPro.setSendNum(Status.STATUS_0.status);
        oReceiptPro.setcUser(userid);
        oReceiptPro.setcTime(d.getTime());
        oReceiptPro.setuUser(userid);
        oReceiptPro.setuTime(d.getTime());
        oReceiptPro.setStatus(Status.STATUS_1.status);
        oReceiptPro.setVersion(Status.STATUS_0.status);
        oReceiptPro.setReceiptProStatus(OReceiptStatus.TEMPORARY_STORAGE.code);
        if (1 != oReceiptProMapper.insertSelective(oReceiptPro)) {
            throw new MessageException("收货单商品添加失败");
        }
        //数据库配货地址同步
        AgentResult sysn = sysnReceiptOrderPorNum(oReceiptPro.getReceiptId());
        return sysn;
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult subOrderPeiHuoUpdate(OReceiptPro oReceiptPro) throws Exception {

        logger.info("用户{}修改{}", oReceiptPro.getuUser(), JSONObject.toJSONString(oReceiptPro));
        BigDecimal flag = new BigDecimal(0);
        if(null!=oReceiptPro){
            if(null!=oReceiptPro.getProNum()){
                flag=oReceiptPro.getProNum();
            }
        }
        //数据库配货地址商品
        OReceiptPro oReceiptPro_db = oReceiptProMapper.selectByPrimaryKey(oReceiptPro.getId());
        oReceiptPro_db.setuUser(oReceiptPro.getuUser());
        oReceiptPro_db.setuTime(Calendar.getInstance().getTime());

        //检查订单状态
        OOrder order = orderMapper.selectByPrimaryKey(oReceiptPro_db.getOrderid());
        if (AgStatus.Approved.status.compareTo(order.getReviewStatus()) != 0) {
            return AgentResult.fail("审批未通过");
        }


        if (oReceiptPro_db.getReceiptProStatus().compareTo(OReceiptStatus.DISPATCHED_ORDER.code) == 0) {
            logger.info("用户{}修改{},{},更新发货商品失败请重试", oReceiptPro.getuUser(), oReceiptPro.getId(), oReceiptPro.getProNum());
            return AgentResult.fail("该条信息已经排单");
        }

        //检查数量
        OReceiptProExample oReceiptProExample = new OReceiptProExample();
        OReceiptProExample.Criteria criteria = oReceiptProExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andProIdEqualTo(oReceiptPro_db.getProId()).andOrderidEqualTo(oReceiptPro_db.getOrderid()).andIdNotEqualTo(oReceiptPro_db.getId());
        List<OReceiptPro> oReceiptPros = oReceiptProMapper.selectByExample(oReceiptProExample);
        BigDecimal count = new BigDecimal(0);
        if (null!=oReceiptPros){
            for (OReceiptPro receiptPro : oReceiptPros) {
                count=receiptPro.getProNum().add(count);
            }
        }
        count=count.add(oReceiptPro.getProNum());
        BigDecimal sum = new BigDecimal(0);
        OSubOrderExample oSubOrderExample = new OSubOrderExample();
        OSubOrderExample.Criteria criteria1 = oSubOrderExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andOrderIdEqualTo(oReceiptPro_db.getOrderid()).andProIdEqualTo(oReceiptPro_db.getProId());
        List<OSubOrder> oSubOrders = oSubOrderMapper.selectByExample(oSubOrderExample);
        if (null!=oSubOrders){
            for (OSubOrder oSubOrder : oSubOrders) {
                sum=oSubOrder.getProNum().add(sum);
            }
        }
        //进行判断
        if (count.compareTo(sum)==1){
            logger.info("商品{}修改失败,订单商品总数{},超出{}件", oReceiptPro_db.getProName(),sum, count.subtract(sum));
            throw new MessageException("商品"+oReceiptPro_db.getProName()+"修改失败,订单商品总数"+sum+"超出"+count.subtract(sum)+"件");
        }

        if(flag.compareTo(oReceiptPro_db.getSendNum())==-1){
            logger.info("已部分排单,最低可调整为"+oReceiptPro_db.getSendNum()+"台(如需要调整到"+flag+"台请联系业务部撤销排单)");
            throw new MessageException("已部分排单,最低可调整为"+oReceiptPro_db.getSendNum()+"台(如需要调整到"+flag+"台请联系业务部撤销排单)");
        } else if(flag.compareTo(oReceiptPro_db.getSendNum())==0){
            oReceiptPro_db.setReceiptProStatus(OReceiptStatus.DISPATCHED_ORDER.code);
        }
        //还得判断已排单和订货数量
        ReceiptPlanExample receiptPlanExample = new ReceiptPlanExample();
        ReceiptPlanExample.Criteria criteria2 = receiptPlanExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andReceiptIdEqualTo(oReceiptPro_db.getReceiptId()).andProIdEqualTo(oReceiptPro_db.getId());
        List<ReceiptPlan> receiptPlans = receiptPlanMapper.selectByExample(receiptPlanExample);
    /*    BigDecimal planCount = new BigDecimal(0);
        if (null!=receiptPlans && receiptPlans.size()>0){
            logger.info("已排单,不可修改,如需要请联系业务部撤销排单");
            throw new MessageException("已排单,不可修改,如需要请联系业务部撤销排单");
        }*/

        if (null != oReceiptPro.getProNum()) {
            oReceiptPro_db.setProNum(oReceiptPro.getProNum());
            if (oReceiptPro_db.getProNum().compareTo(BigDecimal.ZERO) <= 0) {
                oReceiptPro_db.setStatus(Status.STATUS_0.status);
            }
        }

        if (null != oReceiptPro.getReceiptProStatus()) {
            oReceiptPro_db.setReceiptProStatus(oReceiptPro.getReceiptProStatus());
        }

        if (1 != oReceiptProMapper.updateByPrimaryKeySelective(oReceiptPro_db)) {
            logger.info("用户{}修改{},{},更新发货商品失败请重试", oReceiptPro.getuUser(), oReceiptPro.getId(), oReceiptPro.getProNum());
            throw new MessageException("更新发货商品失败请重试");
        }

        //数据库配货地址同步
        AgentResult sysn = sysnReceiptOrderPorNum(oReceiptPro_db.getReceiptId());
        return sysn;
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult subOrderPeiHuoUpdateStatus(String orderId, String agentId) throws Exception {
        OOrder order = orderMapper.selectByPrimaryKey(orderId);
        if (!order.getAgentId().equals(agentId)) {
            logger.info("配货信息修改失败，订单代理商和参数代理商不匹配");
            AgentResult.fail("信息错误");
        }
        OReceiptProExample example = new OReceiptProExample();
        example.or().andOrderidEqualTo(orderId).andStatusEqualTo(Status.STATUS_1.status).andReceiptProStatusEqualTo(OReceiptStatus.TEMPORARY_STORAGE.code);
        List<OReceiptPro> oReceiptProList = oReceiptProMapper.selectByExample(example);
        for (OReceiptPro oReceiptPro : oReceiptProList) {
            oReceiptPro.setReceiptProStatus(OReceiptStatus.WAITING_LIST.code);
            if(1!=oReceiptProMapper.updateByPrimaryKeySelective(oReceiptPro)){
                throw new MessageException("配货失败");
            }
        }
        return AgentResult.ok();
    }

    /**
     * 同步发货单数量
     *
     * @param receiptOrder
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult sysnReceiptOrderPorNum(String receiptOrder) throws Exception {
        //数据库配货地址
        OReceiptOrder oReceiptOrder_db = oReceiptOrderMapper.selectByPrimaryKey(receiptOrder);

        //检查订单状态
        OOrder order = orderMapper.selectByPrimaryKey(oReceiptOrder_db.getOrderId());
        if (AgStatus.Approved.status.compareTo(order.getReviewStatus()) != 0) {
            return AgentResult.fail("审批未通过");
        }

        OReceiptProExample oReceiptProQuery = new OReceiptProExample();
        oReceiptProQuery.or().andReceiptIdEqualTo(receiptOrder).andStatusEqualTo(Status.STATUS_1.status);
        List<OReceiptPro> oReceiptProList = oReceiptProMapper.selectByExample(oReceiptProQuery);
        BigDecimal allCount = BigDecimal.ZERO;

        if (oReceiptProList.size() > 0) {
            for (OReceiptPro oReceiptPro : oReceiptProList) {
                if (oReceiptPro.getProNum() == null || oReceiptPro.getProNum().compareTo(BigDecimal.ZERO) <= 0) {
                    oReceiptPro.setStatus(Status.STATUS_0.status);
                    if (1 != oReceiptProMapper.updateByPrimaryKeySelective(oReceiptPro)) {
                        throw new MessageException("更新发货商品失败请重试");
                    }
                }
                allCount = allCount.add(oReceiptPro.getProNum());
            }

            if (allCount.compareTo(BigDecimal.ZERO) <= 0) {
                oReceiptOrder_db.setProNum(BigDecimal.ZERO);
                oReceiptOrder_db.setStatus(Status.STATUS_0.status);
            } else {
                oReceiptOrder_db.setProNum(allCount);
            }
        } else {
            oReceiptOrder_db.setProNum(BigDecimal.ZERO);
            oReceiptOrder_db.setStatus(Status.STATUS_0.status);
        }
        if (1 != oReceiptOrderMapper.updateByPrimaryKeySelective(oReceiptOrder_db)) {
            throw new MessageException("更新发货订单失败请重试");

        }
        return AgentResult.ok("此条配货信息已变更  请点击查询按钮以获取数据 ");
    }

    /**
     * 根据业务平台编号查询线下打款数据
     * @param busNum
     * @return
     */
    @Override
    public AgentResult queryPaymentXXDK(String busNum){
        AgentResult result = new AgentResult(500,"参数错误","");
        if(StringUtils.isBlank(busNum)){
            return result;
        }
        Map<String,Object> params = new HashMap<>();
        params.put("busNum",busNum);
        params.put("reviewStatus",AgStatus.Approved.getValue());
        List<Map<String,Object>> resultListMap = oPaymentMapper.queryPaymentXXDK(params);
        if(resultListMap==null){
            result.setMsg("查询异常");
            return result;
        }
        if(resultListMap.size()==0){
            result.setMsg("暂无数据");
            return result;
        }
        for (Map<String, Object> stringObjectMap : resultListMap) {
            Map<String, Object> reqMap = new HashMap<>();
            BigDecimal actualReceipt = new BigDecimal(stringObjectMap.get("ACTUAL_RECEIPT").toString());
            reqMap.put("reviewStatus",AgStatus.Approved.getValue());
            reqMap.put("payMethod",PayMethod.OfflineMoney.getValue());
            reqMap.put("paymentId",stringObjectMap.get("ID"));
            BigDecimal suppleAmt = oPaymentDetailMapper.querySupplementXXDK(reqMap);
            stringObjectMap.put("ACTUAL_RECEIPT",actualReceipt.add(suppleAmt));

            String busId = String.valueOf(stringObjectMap.get("BUS_ID"));
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(busId);
            stringObjectMap.put("BUS_NUM",agentBusInfo.getBusNum());
            if(StringUtils.isNotBlank(agentBusInfo.getBusParent())){
                AgentBusInfo agentBusInfoParet = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusParent());
                stringObjectMap.put("PARENT_BUS_NUM",agentBusInfoParet.getBusNum());
            }else{
                stringObjectMap.put("PARENT_BUS_NUM","");
            }
            stringObjectMap.remove("BUS_ID");
        }
        result.setStatus(200);
        result.setMsg("查询成功");
        result.setData(resultListMap);
        return result;
    }

    /**
     * 批量更新分润税点抵扣金额
     * @param taxAmtList
     * @return
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult updateProfitTaxAmt(List<OPayment> taxAmtList)throws Exception{
        AgentResult result = new AgentResult(500,"参数错误","");
        if(null==taxAmtList){
            return result;
        }
        if(taxAmtList.size()==0){
            return result;
        }
        for (OPayment payment : taxAmtList) {
            String id = payment.getId();
            BigDecimal profitTaxAmt = payment.getProfitTaxAmt();
            if (StringUtils.isBlank(id) || id.equals("null")) {
                logger.info("批量更新分润税点抵扣金额:{}", "操作id不能为空");
                throw new ProcessException("操作id不能为空");
            }
            if (null==profitTaxAmt || profitTaxAmt.equals("null")) {
                logger.info("批量更新分润税点抵扣金额:{}", "操作amt不能为空");
                throw new ProcessException("操作amt不能为空");
            }
            OPayment selectPayment= oPaymentMapper.selectByPrimaryKey(id);
            BigDecimal selectProfitTaxAmt = selectPayment.getProfitTaxAmt();
            if(null==selectProfitTaxAmt){
                selectProfitTaxAmt = new BigDecimal(0);
            }
            OPayment oPayment = new OPayment();
            oPayment.setId(id);
            oPayment.setProfitTaxAmt(selectProfitTaxAmt.add(profitTaxAmt));
            int i = oPaymentMapper.updateByPrimaryKeySelective(oPayment);
            if(i!=1){
                logger.info("批量更新分润税点抵扣金额:{}", "更新异常");
                throw new ProcessException("批量更新异常");
            }
        }
        return AgentResult.ok("批量更新成功");
    }

    /**
     * 订单管理导出
     * @param map
     * @return
     */
    @Override
    public List<OrderoutVo> exportOrder(Map map) {
        if(null != map.get("userId")) {
            Long userId = (Long) map.get("userId");
            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
            if (orgCodeRes == null && orgCodeRes.size() != 1) {
                return null;
            }
            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
            String organizationCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
            map.put("organizationCode", organizationCode);
            List<Map> platfromPerm = iResourceService.userHasPlatfromPerm(userId);
            map.put("platfromPerm", platfromPerm);
        }

        List<OrderoutVo> orderoutList = orderMapper.orderExcel(map);
        List<Dict> dictList = dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.SETTLEMENT_TYPE.name());
        List<Dict> capitalType = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.CAPITAL_TYPE.name());

        if (null!=orderoutList && orderoutList.size()>0){
            for (OrderoutVo orderoutVo : orderoutList) {
                if (StringUtils.isNotBlank(orderoutVo.getPayMethod()) && !orderoutVo.getPayMethod().equals("null")) {
                    for (Dict dict : dictList) {
                        if (null!=dict && orderoutVo.getPayMethod().equals(dict.getdItemvalue())) {
                            orderoutVo.setPayMethod(dict.getdItemname());
                            break;
                        }
                    }
                }
                if (StringUtils.isNotBlank(orderoutVo.getDeductionType()) && !orderoutVo.getDeductionType().equals("null")) {
                    for (Dict dict : capitalType) {
                        if (null!=dict && orderoutVo.getDeductionType().equals(dict.getdItemvalue())) {
                            orderoutVo.setDeductionType(dict.getdItemname());
                            BigDecimal deductionAmount = new BigDecimal(0);
                            if (null!=orderoutVo.getDeductionAmount()) {
                                deductionAmount = orderoutVo.getDeductionAmount();
                            }
                            orderoutVo.setAmount(orderoutVo.getDeductionType()+":"+orderoutVo.getDeductionAmount());
                            break;
                        }
                    }
                }
                if (StringUtils.isNotBlank(orderoutVo.getMqydkAmt()) && !orderoutVo.getMqydkAmt().equals("null")) {
                    if (StringUtils.isNotBlank(orderoutVo.getPayMethod())) {
                        if (orderoutVo.getPayMethod().equals("首付+分润分期") || orderoutVo.getPayMethod().equals("分润分期")) {
                            orderoutVo.setMqydkAmt(String.valueOf(BigDecimal.ZERO));
                        } else if (orderoutVo.getPayMethod().equals("首付+打款分期") || orderoutVo.getPayMethod().equals("付款分期")) {
                            orderoutVo.setMqykAmt(String.valueOf(BigDecimal.ZERO));
                        }
                    }
                }
                if (StringUtils.isNotBlank(orderoutVo.getProfitMouth()) && !orderoutVo.getProfitMouth().equals("null")) {
                    if (orderoutVo.getProfitMouth().equals("1")) {
                        orderoutVo.setProfitMouth("是");
                    } else if (orderoutVo.getProfitMouth().equals("0")) {
                        orderoutVo.setProfitMouth("否");
                    }
                }
                if (StringUtils.isNotBlank(orderoutVo.getProfitForm()) && !orderoutVo.getProfitForm().equals("null")) {
                    String valueProfit = "";
                    String[] profitForm = orderoutVo.getProfitForm().split(",");
                    for (String item : profitForm) {
                        String profitFormValue = ProfitForm.getContentByValue(new BigDecimal(item));
                        StringBuffer buffer = new StringBuffer();
                        StringBuffer appendStr = buffer.append(profitFormValue);
                        if (null != appendStr) {
                            if ("".equals(valueProfit)) {
                                valueProfit = String.valueOf(appendStr);
                            } else {
                                valueProfit += "," + appendStr;
                            }
                            orderoutVo.setProfitForm(String.valueOf(valueProfit));
                        }
                    }
                }
                if (StringUtils.isNotBlank(orderoutVo.getNuclearUser())) {
                    CUser cUser = iUserService.selectById(orderoutVo.getNuclearUser());
                    if(null != cUser)
                        orderoutVo.setNuclearUser(cUser.getName());
                }
                if (StringUtils.isNotBlank(orderoutVo.getOrderUser())) {
                    CUser cUser = iUserService.selectById(orderoutVo.getOrderUser());
                    if(null != cUser)
                        orderoutVo.setOrderUser(cUser.getName());
                }
                if (StringUtils.isNotBlank(orderoutVo.getReviewStatus()) && !orderoutVo.getReviewStatus().equals("null")) {
                    String agStatusByValue = AgStatus.getMsg(new BigDecimal(orderoutVo.getReviewStatus()));
                    if (null != agStatusByValue) {
                        orderoutVo.setReviewStatus(agStatusByValue);
                    }
                }
            }
        }
        return orderoutList;
    }

    /**
     * 所有订单导出
     * @param map
     * @return
     */
    @Override
    public List<OrderoutVo> exportOrderAll(Map map) {
        if(null != map.get("userId")) {
            Long userId = (Long) map.get("userId");
            List<Map> platfromPerm = iResourceService.userHasPlatfromPerm(userId);
            map.put("platfromPerm", platfromPerm);
        }

        List<OrderoutVo> orderoutList = orderMapper.excelOrder(map);
        List<Dict> dictList = dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.SETTLEMENT_TYPE.name());
        List<Dict> capitalType = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.CAPITAL_TYPE.name());

        if (null!=orderoutList && orderoutList.size()>0) {
            for (OrderoutVo orderoutVo : orderoutList) {
                if (StringUtils.isNotBlank(orderoutVo.getPayMethod()) && !orderoutVo.getPayMethod().equals("null")) {
                    for (Dict dict : dictList) {
                        if (null!=dict && orderoutVo.getPayMethod().equals(dict.getdItemvalue())) {
                            orderoutVo.setPayMethod(dict.getdItemname());
                            break;
                        }
                    }
                }
                if (StringUtils.isNotBlank(orderoutVo.getDeductionType()) && !orderoutVo.getDeductionType().equals("null")) {
                    for (Dict dict : capitalType) {
                        if (null!=dict && orderoutVo.getDeductionType().equals(dict.getdItemvalue())) {
                            orderoutVo.setDeductionType(dict.getdItemname());
                            BigDecimal deductionAmount = new BigDecimal(0);
                            if (null != orderoutVo.getDeductionAmount()) {
                                deductionAmount = orderoutVo.getDeductionAmount();
                            }
                            orderoutVo.setAmount(orderoutVo.getDeductionType() + ":" + orderoutVo.getDeductionAmount());
                            break;
                        }
                    }
                }
                if (StringUtils.isNotBlank(orderoutVo.getMqydkAmt()) && !orderoutVo.getMqydkAmt().equals("null")) {
                    if (StringUtils.isNotBlank(orderoutVo.getPayMethod())) {
                        if (orderoutVo.getPayMethod().equals("首付+分润分期") || orderoutVo.getPayMethod().equals("分润分期")) {
                            orderoutVo.setMqydkAmt(String.valueOf(BigDecimal.ZERO));
                        } else if (orderoutVo.getPayMethod().equals("首付+打款分期") || orderoutVo.getPayMethod().equals("付款分期")) {
                            orderoutVo.setMqykAmt(String.valueOf(BigDecimal.ZERO));
                        }
                    }
                }
                if (StringUtils.isNotBlank(orderoutVo.getProfitMouth()) && !orderoutVo.getProfitMouth().equals("null")) {
                    if (orderoutVo.getProfitMouth().equals("1")) {
                        orderoutVo.setProfitMouth("是");
                    } else if (orderoutVo.getProfitMouth().equals("0")) {
                        orderoutVo.setProfitMouth("否");
                    }
                }
                if (StringUtils.isNotBlank(orderoutVo.getProfitForm()) && !orderoutVo.getProfitForm().equals("null")) {
                    String valueProfit = "";
                    String[] profitForm = orderoutVo.getProfitForm().split(",");
                    for (String item : profitForm) {
                        String profitFormValue = ProfitForm.getContentByValue(new BigDecimal(item));
                        StringBuffer buffer = new StringBuffer();
                        StringBuffer appendStr = buffer.append(profitFormValue);
                        if (null != appendStr) {
                            if ("".equals(valueProfit)) {
                                valueProfit = String.valueOf(appendStr);
                            } else {
                                valueProfit += "," + appendStr;
                            }
                            orderoutVo.setProfitForm(String.valueOf(valueProfit));
                        }
                    }
                }
                if (StringUtils.isNotBlank(orderoutVo.getNuclearUser())) {
                    CUser cUser = iUserService.selectById(orderoutVo.getNuclearUser());
                    if (null != cUser)
                        orderoutVo.setNuclearUser(cUser.getName());
                }
                if (StringUtils.isNotBlank(orderoutVo.getOrderUser())) {
                    CUser cUser = iUserService.selectById(orderoutVo.getOrderUser());
                    if(null != cUser) {
                        orderoutVo.setOrderUser(cUser.getName());
                    }
                }
                if (StringUtils.isNotBlank(orderoutVo.getReviewStatus()) && !orderoutVo.getReviewStatus().equals("null")) {
                    String agStatusByValue = AgStatus.getMsg(new BigDecimal(orderoutVo.getReviewStatus()));
                    if (null != agStatusByValue) {
                        orderoutVo.setReviewStatus(agStatusByValue);
                    }
                }
            }
        }
        return orderoutList;
    }

    /**
     * 订单商品明细导出
     * @param map
     * @return
     */
    @Override
    public List<OrderoutVo> exportOrderDetail(Map map) {
        if (null != map.get("userId")) {
            Long userId = (Long) map.get("userId");
            List<Map> platfromPerm = iResourceService.userHasPlatfromPerm(userId);
            map.put("platfromPerm", platfromPerm);
        }

        List<OrderoutVo> orderoutVoList = orderMapper.excelOrderDetail(map);
        List<Dict> dictList = dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.SETTLEMENT_TYPE.name());
        List<Dict> capitalType = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.CAPITAL_TYPE.name());

        if (null!=orderoutVoList && orderoutVoList.size()>0) {
            Map<String, Object> orderDetailMap = new HashMap<>();
            for (OrderoutVo orderoutVo : orderoutVoList) {
                if (orderDetailMap.get(orderoutVo.getNum()) == null) {
                    orderDetailMap.put(orderoutVo.getNum(), orderoutVo.getPayMethod());
                    orderDetailMap.put(orderoutVo.getNum(), orderoutVo.getComName());
                    orderDetailMap.put(orderoutVo.getNum(), orderoutVo.getPayUser());
                    orderDetailMap.put(orderoutVo.getNum(), orderoutVo.getRealRecTime());
                    orderDetailMap.put(orderoutVo.getNum(), orderoutVo.getNuclearUser());
                    orderDetailMap.put(orderoutVo.getNum(), orderoutVo.getCheckDate());
                    orderDetailMap.put(orderoutVo.getNum(), orderoutVo.getXxAmount());
                    orderDetailMap.put(orderoutVo.getNum(), orderoutVo.getProfitMouth());
                    orderDetailMap.put(orderoutVo.getNum(), orderoutVo.getProfitForm());
                    orderDetailMap.put(orderoutVo.getNum(), orderoutVo.getYkfrAmt());
                    orderDetailMap.put(orderoutVo.getNum(), orderoutVo.getDownPaymentDate());
                    orderDetailMap.put(orderoutVo.getNum(), orderoutVo.getDownPaymentCount());
                    orderDetailMap.put(orderoutVo.getNum(), orderoutVo.getMqykAmt());
                    orderDetailMap.put(orderoutVo.getNum(), orderoutVo.getFqdkAmt());
                    orderDetailMap.put(orderoutVo.getNum(), orderoutVo.getFrdkCount());
                    orderDetailMap.put(orderoutVo.getNum(), orderoutVo.getMqydkAmt());
                    orderDetailMap.put(orderoutVo.getNum(), orderoutVo.getSjdkAmt());
                    orderDetailMap.put(orderoutVo.getNum(), orderoutVo.getSyqkAmt());
                } else {
                    orderoutVo.setPayMethod("");
                    orderoutVo.setComName("");
                    orderoutVo.setPayUser("");
                    orderoutVo.setRealRecTime("");
                    orderoutVo.setNuclearUser("");
                    orderoutVo.setCheckDate("");
                    orderoutVo.setXxAmount("");
                    orderoutVo.setProfitMouth("");
                    orderoutVo.setProfitForm("");
                    orderoutVo.setYkfrAmt(null);
                    orderoutVo.setDownPaymentDate("");
                    orderoutVo.setDownPaymentCount(null);
                    orderoutVo.setMqykAmt("");
                    orderoutVo.setFqdkAmt(null);
                    orderoutVo.setFrdkCount(null);
                    orderoutVo.setMqydkAmt("");
                    orderoutVo.setSjdkAmt(null);
                    orderoutVo.setSyqkAmt(null);
                }
                if (StringUtils.isNotBlank(orderoutVo.getPayMethod()) && !orderoutVo.getPayMethod().equals("null")) {
                    for (Dict dict : dictList) {
                        if (null!=dict && orderoutVo.getPayMethod().equals(dict.getdItemvalue())) {
                            orderoutVo.setPayMethod(dict.getdItemname());
                            break;
                        }
                    }
                }
                if (StringUtils.isNotBlank(orderoutVo.getDeductionType()) && !orderoutVo.getDeductionType().equals("null")) {
                    for (Dict dict : capitalType) {
                        if (null!=dict && orderoutVo.getDeductionType().equals(dict.getdItemvalue())) {
                            orderoutVo.setDeductionType(dict.getdItemname());
                            BigDecimal deductionAmount = new BigDecimal(0);
                            if (null != orderoutVo.getDeductionAmount()) {
                                deductionAmount = orderoutVo.getDeductionAmount();
                            }
                            orderoutVo.setAmount(orderoutVo.getDeductionType() + ":" + orderoutVo.getDeductionAmount());
                            break;
                        }
                    }
                }
                if (StringUtils.isNotBlank(orderoutVo.getMqydkAmt()) && !orderoutVo.getMqydkAmt().equals("null")) {
                    if (StringUtils.isNotBlank(orderoutVo.getPayMethod())) {
                        if (orderoutVo.getPayMethod().equals("首付+分润分期") || orderoutVo.getPayMethod().equals("分润分期")) {
                            orderoutVo.setMqydkAmt(String.valueOf(BigDecimal.ZERO));
                        } else if (orderoutVo.getPayMethod().equals("首付+打款分期") || orderoutVo.getPayMethod().equals("付款分期")) {
                            orderoutVo.setMqykAmt(String.valueOf(BigDecimal.ZERO));
                        }
                    }
                }
                if (StringUtils.isNotBlank(orderoutVo.getProfitMouth()) && !orderoutVo.getProfitMouth().equals("null")) {
                    if (orderoutVo.getProfitMouth().equals("1")) {
                        orderoutVo.setProfitMouth("是");
                    } else if (orderoutVo.getProfitMouth().equals("0")) {
                        orderoutVo.setProfitMouth("否");
                    }
                }
                if (StringUtils.isNotBlank(orderoutVo.getProfitForm()) && !orderoutVo.getProfitForm().equals("null")) {
                    String valueProfit = "";
                    String[] profitForm = orderoutVo.getProfitForm().split(",");
                    for (String item : profitForm) {
                        String profitFormValue = ProfitForm.getContentByValue(new BigDecimal(item));
                        StringBuffer buffer = new StringBuffer();
                        StringBuffer appendStr = buffer.append(profitFormValue);
                        if (null != appendStr) {
                            if ("".equals(valueProfit)) {
                                valueProfit = String.valueOf(appendStr);
                            } else {
                                valueProfit += "," + appendStr;
                            }
                            orderoutVo.setProfitForm(String.valueOf(valueProfit));
                        }
                    }
                }
                if (StringUtils.isNotBlank(orderoutVo.getNuclearUser())) {
                    CUser cUser = iUserService.selectById(orderoutVo.getNuclearUser());
                    if (null != cUser)
                        orderoutVo.setNuclearUser(cUser.getName());
                }
                if (StringUtils.isNotBlank(orderoutVo.getOrderUser())) {
                    CUser cUser = iUserService.selectById(orderoutVo.getOrderUser());
                    if(null != cUser) {
                        orderoutVo.setOrderUser(cUser.getName());
                    }
                }
                if (StringUtils.isNotBlank(orderoutVo.getReviewStatus()) && !orderoutVo.getReviewStatus().equals("null")) {
                    String agStatusByValue = AgStatus.getMsg(new BigDecimal(orderoutVo.getReviewStatus()));
                    if (null != agStatusByValue) {
                        orderoutVo.setReviewStatus(agStatusByValue);
                    }
                }
            }
        }
        return orderoutVoList;
    }

    @Override
    public BigDecimal queryAgentDebt(String agentId) {
        Double res = oPaymentMapper.queryAgentDebt(agentId);
        if(res==null)return BigDecimal.ZERO;
        return BigDecimal.valueOf(res);
    }

    @Override
    public AgentResult updateStatus(String id, String user) throws Exception{
        //删除订单
        if (null == user) return AgentResult.fail("操作用户不能为空");
        if (StringUtils.isBlank(id)) return AgentResult.fail("ID不能为空");
        OOrder oOrder = new OOrder();
        oOrder.setId(id);
        oOrder.setuUser(user);
        oOrder.setStatus(Status.STATUS_0.status);
        if (1==orderMapper.updateByPrimaryKeySelective(oOrder)){
            return AgentResult.ok("成功");
        }
        //删除订单付款信息
        OPaymentExample paymentExample = new OPaymentExample();
        paymentExample.or().andOrderIdEqualTo(id);
        List<OPayment> listPayment = oPaymentMapper.selectByExample(paymentExample);
        for (OPayment oPayment : listPayment) {
            oPayment.setStatus(Status.STATUS_0.status);
            if(1!=oPaymentMapper.updateByPrimaryKeySelective(oPayment)){
                throw new MessageException("删除订单付款单失败");
            }
            //删除付款信息打款记录
            List<OCashReceivables> oCashReceivables = oCashReceivablesService.query(null,null,null,oPayment.getId(),null);
            for (OCashReceivables oCashReceivable : oCashReceivables) {
                oCashReceivable.setStatus(Status.STATUS_0.status);
                if(!oCashReceivablesService.dele(oCashReceivable,user).isOK()){
                    throw new MessageException("删除订单付款单失败");
                }
            }
        }
        //订单付款明细
        OPaymentDetailExample paymentDetailExample = new OPaymentDetailExample();
        paymentDetailExample.or().andOrderIdEqualTo(id);
        List<OPaymentDetail> paymentDetails = oPaymentDetailMapper.selectByExample(paymentDetailExample);
        for (OPaymentDetail paymentDetail : paymentDetails) {
            paymentDetail.setStatus(Status.STATUS_0.status);
            if(1!=oPaymentDetailMapper.updateByPrimaryKeySelective(paymentDetail)){
                throw new MessageException("删除订单付款单失败");
            }
        }
        return AgentResult.fail();
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult insertSelectiveCashSummaryMouth(CashSummaryMouth cashSummaryMouth) {
        if(null==cashSummaryMouthMapper.selectByPrimaryKey(cashSummaryMouth)){
            cashSummaryMouth.setStatus(Status.STATUS_1.status);
            cashSummaryMouth.setcDate(Calendar.getInstance().getTime());
            if(cashSummaryMouthMapper.insertSelective(cashSummaryMouth)==1){
                return AgentResult.ok();
            }else{
                return AgentResult.fail("插入失败");
            }
        }
        return AgentResult.ok();
    }


    /**
     * 代理商月度打款金额开票不开票信息统计任务执行
     */
    @Override
    public void CashSummaryMouth() {
        logger.info("======代理商月度打款金额开票不开票信息统计任务执行======");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH,-1);
        String date = DateUtil.format(c.getTime(),DateUtil.DATE_FORMAT_yyyyMM);
        logger.info("======代理商月度打款金额开票不开票信息统计任务执行======统计月份{}",date);
        //不开票信息
        List<CashSummaryMouth> res =  cashSummaryMouthMapper.selectCashSummaryMouthData(date,"0");
        logger.info("======代理商月度打款金额开票不开票信息统计任务执行=统计月份:{}=不开票信息:{}",date,res.size());
        for (CashSummaryMouth re : res) {
            try {
                AgentResult summaryMouth = orderService.insertSelectiveCashSummaryMouth(re);
                if(summaryMouth.isOK()){
                    logger.info("======代理商月度打款金额不开票信息统计任务执行结果({})=统计月份:{}=代理商:{}=金额:{}",summaryMouth.getMsg(),date,re.getAgentId(),re.getPayAmount());
                }else{
                    logger.info("======代理商月度打款金额不开票信息统计任务执行结果({})=统计月份:{}=代理商:{}=金额:{}",summaryMouth.getMsg(),date,re.getAgentId(),re.getPayAmount());
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("======代理商月度打款金额开票不开票信息统计任务执行异常({})=统计月份:{}=代理商:{}=金额:{}",e.getLocalizedMessage(),date,re.getAgentId(),re.getPayAmount());
            }
        }

        //开票信息
        res =  cashSummaryMouthMapper.selectCashSummaryMouthData(date,"1");
        logger.info("======代理商月度打款金额开票不开票信息统计任务执行=统计月份:{}=开票信息:{}",date,res.size());
        for (CashSummaryMouth re : res) {
            try {
                AgentResult summaryMouth = orderService.insertSelectiveCashSummaryMouth(re);
                if(summaryMouth.isOK()){
                    logger.info("======代理商月度打款金额开票信息统计任务执行结果({})=统计月份:{}=代理商:{}=金额:{}",summaryMouth.getMsg(),date,re.getAgentId(),re.getPayAmount());
                }else{
                    logger.info("======代理商月度打款金额开票信息统计任务执行结果({})=统计月份:{}=代理商:{}=金额:{}",summaryMouth.getMsg(),date,re.getAgentId(),re.getPayAmount());
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("======代理商月度打款金额开票信息统计任务执行异常({})=统计月份:{}=代理商:{}=金额:{}",e.getLocalizedMessage(),date,re.getAgentId(),re.getPayAmount());
            }
        }
    }

    @Transactional
    @Override
    public void testRepeatableRead() {

        OOrder order = orderMapper.selectByPrimaryKey("OO20181121000000000001830");
        logger.info("==1===="+order.getoNum());
        //两个事物 都更新 或者 一个事物更新
//        order.setoNum("21173445661830078");
//        order.setoNum("21173445661830079");
//        orderMapper.updateByPrimaryKeySelective(order);
        logger.info("==2===="+order.getoNum());
        OOrder order1 = orderMapper.selectByPrimaryKey("OO20181121000000000001830");
        logger.info("==2===="+order1.getoNum());
        logger.info("==3====");
    }

    @Override
    public AgentResult revocationOrder(String id, String user) {
        if (null == user) {
            return AgentResult.fail("操作用户不能为空");
        }
        if (StringUtils.isBlank(id)) {
            return AgentResult.fail("订单ID不能为空");
        }
        OOrder oOrder = orderMapper.selectByPrimaryKey(id);
        oOrder.setuUser(user);
        oOrder.setOrderStatus(OrderStatus.REVOKE.status);
        int update = orderMapper.updateByPrimaryKeySelective(oOrder);
        if (1 != update){
            return AgentResult.fail("撤销失败");
        }
        return AgentResult.ok("撤销成功");
    }

    @Override
    public AgentResult deletePeihuoAction(OReceiptPro oReceiptPro) throws Exception {
        logger.info("配货{}删除{}", oReceiptPro.getuUser(), JSONObject.toJSONString(oReceiptPro));
        BigDecimal flag = new BigDecimal(0);
        if(null!=oReceiptPro){
            if(null!=oReceiptPro.getProNum()){
                flag=oReceiptPro.getProNum();
            }
        }
        //数据库配货地址商品
        OReceiptPro oReceiptPro_db = oReceiptProMapper.selectByPrimaryKey(oReceiptPro.getId());
        oReceiptPro.setuTime(Calendar.getInstance().getTime());
        oReceiptPro.setStatus(Status.STATUS_0.status);
        if (oReceiptPro_db.getReceiptProStatus().compareTo(OReceiptStatus.DISPATCHED_ORDER.code) == 0) {
            logger.info("用户{}删除{},{},删除发货商品失败请重试", oReceiptPro.getuUser(), oReceiptPro.getId(), oReceiptPro.getProNum());
            return AgentResult.fail("该条信息已经排单");
        }
        //有排单的情况下是不允许删除的
        if(oReceiptPro_db.getSendNum().compareTo(new BigDecimal(BigInteger.ZERO))==1 ){
            if(flag.compareTo(oReceiptPro_db.getSendNum())==-1||flag.compareTo(oReceiptPro_db.getSendNum())==1){
                logger.info("已有部分排单为"+oReceiptPro_db.getSendNum()+"台,不可删除");
                throw new MessageException("已有部分排单为"+oReceiptPro_db.getSendNum()+"台,不可删除");
            }else if(flag.compareTo(oReceiptPro_db.getSendNum())==0){
                logger.info(oReceiptPro_db.getSendNum()+"台已全部排单,不可删除，可选择修改");
                throw new MessageException(oReceiptPro_db.getSendNum()+"台已全部排单,不可删除，可选择修改");
            }
        }
        //还得判断已排单和订货数量
        ReceiptPlanExample receiptPlanExample = new ReceiptPlanExample();
        ReceiptPlanExample.Criteria criteria2 = receiptPlanExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andReceiptIdEqualTo(oReceiptPro_db.getReceiptId()).andProIdEqualTo(oReceiptPro_db.getId());
        List<ReceiptPlan> receiptPlans = receiptPlanMapper.selectByExample(receiptPlanExample);
        BigDecimal planCount = new BigDecimal(0);
       /* if (null!=receiptPlans && receiptPlans.size()>0){
            logger.info("已排单,不可删除");
            throw new MessageException("已排单,不可删除");

        }*/

        //检查数量
        OReceiptProExample oReceiptProExample = new OReceiptProExample();
        OReceiptProExample.Criteria criteria = oReceiptProExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andProIdEqualTo(oReceiptPro_db.getProId()).andOrderidEqualTo(oReceiptPro_db.getOrderid()).andIdNotEqualTo(oReceiptPro_db.getId());
        List<OReceiptPro> oReceiptPros = oReceiptProMapper.selectByExample(oReceiptProExample);
        BigDecimal count = new BigDecimal(0);
        if (null!=oReceiptPros){
            for (OReceiptPro receiptPro : oReceiptPros) {
                count=receiptPro.getProNum().add(count);
            }
        }
        count=count.add(oReceiptPro.getProNum());
        BigDecimal sum = new BigDecimal(0);
        OSubOrderExample oSubOrderExample = new OSubOrderExample();
        OSubOrderExample.Criteria criteria1 = oSubOrderExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andOrderIdEqualTo(oReceiptPro_db.getOrderid()).andProIdEqualTo(oReceiptPro_db.getProId());
        List<OSubOrder> oSubOrders = oSubOrderMapper.selectByExample(oSubOrderExample);
        if (null!=oSubOrders){
            for (OSubOrder oSubOrder : oSubOrders) {
                sum=oSubOrder.getProNum().add(sum);
            }
        }
        //进行判断
        if (count.compareTo(sum)==1){
            logger.info("商品{}修改失败,订单商品总数{},超出{}件", oReceiptPro_db.getProName(),sum, count.subtract(sum));
            throw new MessageException("商品"+oReceiptPro_db.getProName()+"修改失败,订单商品总数"+sum+"超出"+count.subtract(sum)+"件");
        }


        if (1 != oReceiptProMapper.updateByPrimaryKeySelective(oReceiptPro)) {
            logger.info("用户{}删除{},{},删除发货商品失败请重试", oReceiptPro.getuUser(), oReceiptPro.getId(), oReceiptPro.getProNum());
            throw new MessageException("删除发货商品失败请重试");
        }
        //数据库配货地址同步
        AgentResult sysn = sysnReceiptOrderPorNum(oReceiptPro_db.getReceiptId());
        return sysn;
    }

    @Override
    public AgentResult orderChangeActivity(String oNum, String subOrderId, String activityId, String userId) throws Exception {
        logger.info("用户[{}]变更活动[{}]", userId, JSONObject.toJSONString(oNum));
        if (StringUtils.isBlank(activityId)) {
            return AgentResult.fail("请选择变更活动");
        }

        //查询此订单是否有已发货数据
        ReceiptPlanExample receiptPlanExample = new ReceiptPlanExample();
        receiptPlanExample.or()
                .andStatusEqualTo(Status.STATUS_1.status)
                .andPlanOrderStatusEqualTo(new BigDecimal(PlannerStatus.YesDeliver.getValue()))
                .andOrderIdEqualTo(oNum);
        List<ReceiptPlan> receiptPlanList = receiptPlanMapper.selectByExample(receiptPlanExample);
        if (receiptPlanList.size()!=0 && receiptPlanList!=null) {
            logger.info("用户{}变更活动{}，已有正在发货的商品，不允许变更活动", userId, oNum);
            return AgentResult.fail("已有正在发货的商品，不支持变更活动");
        }

        //变更活动，更新采购单、采购活动数据
        if (StringUtils.isNotBlank(subOrderId)) {
            //商品活动数据
            OActivityExample oActivityExample = new OActivityExample();
            oActivityExample.createCriteria()
                    .andStatusEqualTo(Status.STATUS_1.status)
                    .andIdEqualTo(activityId);
            List<OActivity> oActivityList = oActivityMapper.selectByExample(oActivityExample);
            OActivity oActivity = oActivityList.get(0);
            //判断条件限制
            OSubOrderExample oSubOrderExample = new OSubOrderExample();
            oSubOrderExample.createCriteria()
                    .andStatusEqualTo(Status.STATUS_1.status)
                    .andIdEqualTo(subOrderId);
            List<OSubOrder> oSubOrderList = oSubOrderMapper.selectByExample(oSubOrderExample);
            OSubOrder subOrder = oSubOrderList.get(0);
            if (subOrder != null) {
                if (!subOrder.getProId().equals(oActivity.getProductId())) {
                    logger.info("用户{}变更活动{}，变更活动商品ID与原活动商品ID不一致", userId, oNum);
                    return AgentResult.fail("活动变更失败，变更活动商品ID与原活动商品ID不一致");
                }
                if (!subOrder.getProRelPrice().equals(oActivity.getPrice())) {
                    logger.info("用户{}变更活动{}，变更活动商品价格与原活动商品价格不一致", userId, oNum);
                    return AgentResult.fail("活动变更失败，变更活动商品价格与原活动商品价格不一致");
                }
                OProduct product = oProductMapper.selectByPrimaryKey(subOrder.getProId());
                //采购单数据
                subOrder.setProCode(product.getProCode());
                subOrder.setProName(product.getProName());
                subOrder.setProType(product.getProType());
                subOrder.setModel(product.getProModel());
                subOrder.setuUser(userId);
                subOrder.setuTime(new Date());
                int updateOSubOrder = oSubOrderMapper.updateByPrimaryKeySelective(subOrder);
                if (updateOSubOrder != 1) {
                    logger.info("活动变更:{}", "OSubOrder-更新失败");
                    throw new MessageException("活动变更失败");
                }

                //采购活动数据
                OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
                oSubOrderActivityExample.createCriteria()
                        .andStatusEqualTo(Status.STATUS_1.status)
                        .andSubOrderIdEqualTo(subOrderId);
                List<OSubOrderActivity> oSubOrderActivityList = oSubOrderActivityMapper.selectByExample(oSubOrderActivityExample);
                OSubOrderActivity subOrderActivity = oSubOrderActivityList.get(0);
                subOrderActivity.setActivityId(oActivity.getId());
                subOrderActivity.setActivityName(oActivity.getActivityName());
                subOrderActivity.setRuleId(oActivity.getRuleId());
                subOrderActivity.setProName(subOrder.getProName());
                subOrderActivity.setActivityRule(oActivity.getActivityRule());
                subOrderActivity.setActivityWay(oActivity.getActivityWay());
                subOrderActivity.setProModel(oActivity.getProModel());
                subOrderActivity.setVender(oActivity.getVender());
                subOrderActivity.setPlatform(oActivity.getPlatform());
                subOrderActivity.setBusProCode(oActivity.getBusProCode());
                subOrderActivity.setBusProName(oActivity.getBusProName());
                subOrderActivity.setTermBatchcode(oActivity.getTermBatchcode());
                subOrderActivity.setTermBatchname(oActivity.getTermBatchname());
                subOrderActivity.setTermtype(oActivity.getTermtype());
                subOrderActivity.setPosSpePrice(oActivity.getPosSpePrice());
                subOrderActivity.setPosType(oActivity.getPosType());
                subOrderActivity.setStandTime(oActivity.getStandTime());
                subOrderActivity.setStandAmt(oActivity.getStandAmt());
                subOrderActivity.setBackType(oActivity.getBackType());
                subOrderActivity.setuUser(userId);
                subOrderActivity.setuTime(new Date());
                int updateOSubOrderActivity = oSubOrderActivityMapper.updateByPrimaryKeySelective(subOrderActivity);
                if (updateOSubOrderActivity != 1) {
                    logger.info("活动变更:{}", "OSubOrderActivity-更新失败");
                    throw new MessageException("活动变更失败");
                }

                //收货单商品
                OReceiptProExample oReceiptProExample = new OReceiptProExample();
                oReceiptProExample.createCriteria()
                        .andStatusEqualTo(Status.STATUS_1.status)
                        .andOrderidEqualTo(oNum)
                        .andProIdEqualTo(product.getId())
                        .andProNameEqualTo(product.getProName());
                List<OReceiptPro> oReceiptProList = oReceiptProMapper.selectByExample(oReceiptProExample);
                if (oReceiptProList.size()!=0 && oReceiptProList!=null) {
                    OReceiptPro oReceiptPro = oReceiptProList.get(0);
                    //排单数据
                    ReceiptPlanExample oReceiptPlanExample = new ReceiptPlanExample();
                    oReceiptPlanExample.or()
                            .andStatusEqualTo(Status.STATUS_1.status)
                            .andOrderIdEqualTo(oNum)
                            .andProIdEqualTo(oReceiptPro.getId());
                    List<ReceiptPlan> oReceiptPlanList = receiptPlanMapper.selectByExample(oReceiptPlanExample);
                    if (oReceiptPlanList.size()!=0 && oReceiptPlanList!=null) {
                        ReceiptPlan receiptPlan = oReceiptPlanList.get(0);
                        receiptPlan.setProCom(oActivity.getVender());
                        receiptPlan.setProType(oActivity.getProType());
                        receiptPlan.setModel(oActivity.getProModel());
                        receiptPlan.setActivityId(oActivity.getId());
                        int updateReceiptPlan = receiptPlanMapper.updateByPrimaryKeySelective(receiptPlan);
                        if (updateReceiptPlan != 1) {
                            logger.info("活动变更:{}", "ReceiptPlan-更新失败");
                            throw new MessageException("活动变更失败");
                        }
                    }
                }
            }
        }

        return AgentResult.ok(oNum);
    }

    @Override
    public PageInfo arrearageList(Map<String, Object> param, PageInfo pageInfo) {
        Long count = orderMapper.arrearageCount(param);
        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(orderMapper.arrearageList(param));
        return pageInfo;
    }

    @Override
    public PageInfo arrearageQuery(Map map, PageInfo pageInfo) {
        pageInfo.setRows(orderMapper.arrearageQuery(map));
        return pageInfo;
    }

    @Override
    public PageInfo serchArrearage(Map<String, Object> param, PageInfo pageInfo) {
        Long count = orderMapper.serchArrearageCount(param);
        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(orderMapper.serchArrearageList(param));
        return pageInfo;
    }

    @Override
    public AgentResult isRemoveAccount(Map map) {
        List<Map> removeAccountList=orderMapper.isRemoveAccount(map);
        if (null != removeAccountList && removeAccountList.size()> 0) {
            return AgentResult.ok(removeAccountList);
        } else {
            return AgentResult.fail("没有需要进行销账的订单");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO removeAccountSave(ORemoveAccountVo oRemoveAccountVo,List<Map> rAmountlist) throws Exception {
        if (null==rAmountlist && rAmountlist.size()==0){
            logger.info("销账添加:{}", "请选择需要销账的数据");
            return ResultVO.fail("请选择需要销账的数据");
        }
        if (null == oRemoveAccountVo.getRemoveAccount()) {
            logger.info("销账添加:{}", "销账添加信息为空");
            return ResultVO.fail("销账添加信息为空");
        }
        ORemoveAccount removeAccount = oRemoveAccountVo.getRemoveAccount();
        if (StringUtils.isEmpty(removeAccount.getSubmitPerson())) {
            logger.info("销账添加:{}", "提交用户不能为空");
            return ResultVO.fail("提交用户不能为空");
        }
        if (StringUtils.isEmpty(removeAccount.getPayMethod())) {
            logger.info("销账添加:{}", "销账方式不能为空");
            return ResultVO.fail("销账方式不能为空");
        }
        if (StringUtils.isEmpty(removeAccount.getRamount())) {
            logger.info("销账添加:{}", "销账金额不能为空");
            return ResultVO.fail("销账金额不能为空");
        }
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String batchNum = Calendar.getInstance().getTime().getTime() + "";
        if (null!=rAmountlist && rAmountlist.size()>0){
            for(int i=0;i<rAmountlist.size();i++){
                Map map = rAmountlist.get(i);
                String machinesAmount = String.valueOf(map.get("machinesAmount"));//机具欠款
                String ramount = String.valueOf(map.get("ramount"));//销账金额
                String agName ="";
                if(new BigDecimal(ramount).compareTo(new BigDecimal(machinesAmount))==1){
                    logger.info("填写的金额不能大于机具欠款金额,代理商为:"+agName+",机具金额:"+machinesAmount);
                    throw new MessageException("填写的金额不能大于机具欠款金额,代理商为:"+agName+",机具金额:"+machinesAmount);
                }
                if(!String.valueOf(map.get("agId")).equals("null")){
                    AgentExample agentExample = new AgentExample();
                    AgentExample.Criteria agId = agentExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andIdEqualTo(String.valueOf(map.get("agId")));
                    List<Agent> agentList= agentMapper.selectByExample(agentExample);
                    if (null!=agentList && agentList.size()>0){
                        Agent agent = agentList.get(0);
                        agName=agent.getAgName();
                    }

                }
                removeAccount.setId(idService.genId(TabId.O_REMOVE_ACCOUNT));
                removeAccount.setAgId(String.valueOf(map.get("agId")).equals("null")?"":String.valueOf(map.get("agId")));
                String rmonth = String.valueOf(map.get("rmonth"));
                if (StringUtils.isNotBlank(rmonth)){
                    removeAccount.setRmonth(format.parse(rmonth));
                }
                removeAccount.setAgName(agName);
                removeAccount.setBusNum(String.valueOf(map.get("busNum")).equals("null")?"":String.valueOf(map.get("busNum")));
                removeAccount.setBusPlatform(String.valueOf(map.get("busPlatform")).equals("null")?"":String.valueOf(map.get("busPlatform")));
                removeAccount.setRamount(new BigDecimal(ramount));
                removeAccount.setMachinesAmount(new BigDecimal(machinesAmount));
                removeAccount.setRstatus(RemoveAccountStatus.WCL.code);
                removeAccount.setBatchNum(batchNum);
                removeAccount.setSubmitTime(date);
                removeAccount.setStatus(Status.STATUS_1.status);
                removeAccount.setVersion(Status.STATUS_1.status);
                removeAccount.setRealRamount(new BigDecimal(0));
                removeAccount.setLogicalVersion(LogicalVersion.ONE.code);
                if (1 == oRemoveAccountMapper.insertSelective(removeAccount)) {
                    oRemoveAccountVo.setRemoveAccount(removeAccount);
                }
            }
        }
        if (null != oRemoveAccountVo.getRemoveAccountFile() && oRemoveAccountVo.getRemoveAccountFile().size()>0) {
            List<String> file = oRemoveAccountVo.getRemoveAccountFile();
            for (String s : file) {
                if (org.apache.commons.lang.StringUtils.isEmpty(s)) continue;
                AttachmentRel record = new AttachmentRel();
                record.setAttId(s);
                record.setSrcId(removeAccount.getBatchNum());
                record.setcUser(removeAccount.getSubmitPerson());
                record.setcTime(removeAccount.getSubmitTime());
                record.setStatus(Status.STATUS_1.status);
                record.setBusType(AttachmentRelType.removeAccount.name());
                record.setId(idService.genId(TabId.a_attachment_rel));
                if (1 != attachmentRelMapper.insertSelective(record)) {
                    logger.info("补款添加:{}", "上传打款截图失败");
                    throw new MessageException("上传打款截图失败");
                }
            }
        }
        logger.info("销账添加:成功");
        return ResultVO.success(oRemoveAccountVo);
    }

    @Override
    public AgentResult enableOrderAdjFinish(String orderAdjId) throws Exception {
        logger.info("查询是否允许结束该调整流程,id:"+orderAdjId);
        OrderAdj orderAdj = orderAdjMapper.selectByPrimaryKey(orderAdjId);
        if (null == orderAdj){
            return AgentResult.fail("该记录不存在!");
        }
        Date adjTm = orderAdj.getAdjTm();
        Date nowTm = new Date();
        Calendar adjtm = Calendar.getInstance();
        Calendar nowtm = Calendar.getInstance();
        adjtm.setTime(adjTm);
        int adjyear = adjtm.get(Calendar.YEAR);
        int adjmonth = adjtm.get(Calendar.MONTH);

        nowtm.setTime(nowTm);
        int nowtmyear = nowtm.get(Calendar.YEAR);
        int nowtmmonth = nowtm.get(Calendar.MONTH);
        if (adjyear == nowtmyear){
            if ((adjmonth - nowtmmonth) != 0){
                logger.error("该记录时间跨月,id:"+orderAdjId);
                return AgentResult.fail("该记录时间跨月!");
            }
        }else {
            logger.error("该记录时间跨年,id:"+orderAdjId);
            return AgentResult.fail("该记录时间跨年!");
        }
        if (orderAdj.getReviewsStat().compareTo(AgStatus.Approving.status) != 0){
            logger.error("该记录非审批中,id:"+orderAdjId);
            return AgentResult.fail("该记录非审批中!");
        }
        if (EnvironmentUtil.isProduction() ){
            List<Dict> dicts = dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.ADJ_ID.name());
            if (dicts != null && dicts.size()>0){
                String itemvalue = dicts.get(0).getdItemvalue();
                if (!itemvalue.equals(orderAdjId)){
                    logger.error("该记录与字典内ID不一致,id:"+orderAdjId);
                    return AgentResult.fail("该记录与字典内ID不一致!");
                }
            }
        }
//        BigDecimal refundStat = orderAdj.getRefundStat();
//        if (null != refundStat){
//            logger.error("退款状态为:"+RefundStat.getContentByValue(refundStat));
//            return AgentResult.fail("该记录已执行分期变更!");
//        }
        logger.info("该记录可以结束,id:"+orderAdjId);
        return AgentResult.ok();
    }

    @Override
    public PageInfo queryAgentUpModelDetailList(Map par, Page page) {
        PageInfo pageInfo = new PageInfo();
        if (par == null) return pageInfo;
        if (par.get("proName") != null && StringUtils.isNotBlank((String)par.get("proName"))){
            par.put("proName",Arrays.asList(((String)par.get("proName")).split(",")));
        }else {
            par.put("proName",new  ArrayList<String>());
        }
        if(null!=par.get("userId")) {
            Long userId = (Long) par.get("userId");
            List<Map> platfromPerm = iResourceService.userHasPlatfromPerm(userId);
            par.put("platfromPerm", platfromPerm);
        }
        par.put("page", page);
        Map<String,Object> orderMap = new HashMap<>();
        List<OrderAdjustVo> list = orderAdjMapper.selectOrderAdjustDetailAll(par, page);
        for (OrderAdjustVo orderAdjustVo:list){
            if (orderMap.get(orderAdjustVo.getAdjId()) == null){
                orderMap.put(orderAdjustVo.getAdjId(),orderAdjustVo.getProRefundAmount());
            }else {
                orderAdjustVo.setProRefundAmount("0");
            }
        }
        pageInfo.setTotal(orderAdjMapper.selectOrderAdjustDetailAllCount(par));
        pageInfo.setRows(list);
        return pageInfo;
    }

    @Override
    public List<OrderAdjustVo> excelOrderAdjustDetailAll(Map map) {

        if(null!=map.get("userId")) {
            Long userId = (Long) map.get("userId");
            List<Map> platfromPerm = iResourceService.userHasPlatfromPerm(userId);
            map.put("platfromPerm", platfromPerm);
        }
        List<OrderAdjustVo> orderAdjVoList = orderAdjMapper.excelOrderAdjustDetailAll(map);
        if (null!=orderAdjVoList && orderAdjVoList.size()>0) {
            for (OrderAdjustVo orderAdjustVo : orderAdjVoList) {
                if (StringUtils.isNotBlank(orderAdjustVo.getReviewsStat()) && !orderAdjustVo.getReviewsStat().equals("null")) {
                    String reviewsStatusByValue = AgStatus.getMsg(new BigDecimal(orderAdjustVo.getReviewsStat()));
                    if (null != reviewsStatusByValue) {
                        orderAdjustVo.setReviewsStat(reviewsStatusByValue);
                    }
                }
                if (StringUtils.isNotBlank(orderAdjustVo.getRefundStat()) && !orderAdjustVo.getRefundStat().equals("null")) {
                    Dict refundStatusByValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.REFUND_STAT.name(), orderAdjustVo.getRefundStat());
                    if (null != refundStatusByValue) {
                        orderAdjustVo.setRefundStat(refundStatusByValue.getdItemname());
                    }
                }
            }
        }
        return orderAdjVoList;
    }


}
