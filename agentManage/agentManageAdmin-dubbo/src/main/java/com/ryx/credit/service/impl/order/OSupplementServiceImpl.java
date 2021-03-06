package com.ryx.credit.service.impl.order;

import com.alibaba.druid.sql.visitor.functions.If;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.dao.agent.BusActRelMapper;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OCashReceivablesVo;
import com.ryx.credit.pojo.admin.vo.OsupplementVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.OCashReceivablesService;
import com.ryx.credit.service.order.OSupplementService;
import com.ryx.credit.service.order.OrderOffsetService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ryx.credit.common.enumc.OffsetPaytype.DDBK;

@Service("oSupplementService")
public class OSupplementServiceImpl implements OSupplementService {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(OSupplementServiceImpl.class);
    @Autowired
    private OSupplementMapper oSupplementMapper;
    @Autowired
    private OPaymentDetailMapper oPaymentDetailMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private AttachmentRelMapper attachmentRelMapper;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private OPaymentMapper oPaymentMapper;
    @Autowired
    private AgentService agentService;
    @Autowired
    private OCashReceivablesService oCashReceivablesService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private OOrderMapper oOrderMapper;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private OrderOffsetService orderOffsetService;


    @Override
    public PageInfo selectAll(Page page, OSupplement oSupplement, String time, String userId, String supplementShrio) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(oSupplement.getPkType())) {
            map.put("pkType", oSupplement.getPkType());
        }
        if (StringUtils.isNotBlank(oSupplement.getPayMethod())) {
            map.put("payMethod", oSupplement.getPayMethod());
        }
        if (!StringUtils.isEmpty(oSupplement.getReviewStatus())) {
            map.put("reviewStatus", oSupplement.getReviewStatus());
        }
        if (StringUtils.isNotBlank(time)) {
            String reltime = time.substring(0, 10);
            map.put("time", reltime);
        }
        AgentResult result = agentService.isAgent(userId);
        if (result.isOK()) {
            //说明有代理商
            Agent data = (Agent) result.getData();
            map.put("agentId", data.getId());
        } else {
            if (StringUtils.isBlank(supplementShrio)) {
                List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.valueOf(userId));
                if (orgCodeRes == null && orgCodeRes.size() != 1) {
                    return null;
                }
                Map<String, Object> objectMap = orgCodeRes.get(0);
                String orgId = String.valueOf(objectMap.get("ORGID"));
                map.put("orgId", orgId);
            }
        }

        List<Map<String, Object>> supplementList = oSupplementMapper.selectAll(map, page);
        for (Map<String, Object> maps : supplementList) {
            maps.put("PK_TYPE", PkType.gePkTypeValue(String.valueOf(maps.get("PK_TYPE"))));//补款类型
            maps.put("PAY_METHOD", PayMethod.getPayMethod(String.valueOf(maps.get("PAY_METHOD"))));//付款方式
            maps.put("REVIEW_STATUS", AgStatus.getMsg((BigDecimal) (maps.get("REVIEW_STATUS"))));//审核状态
            maps.put("SCHSTATUS", SchStatus.getMsg((BigDecimal) maps.get("SCHSTATUS")));//补款状态
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(supplementList);
        pageInfo.setTotal(oSupplementMapper.getCount(map));
        return pageInfo;
    }

    @Override
    public OSupplement selectById(String id) {
        return selectOSupplement(id);
    }

    public OSupplement selectOSupplement(String id) {
        OSupplementExample oSupplementExample = new OSupplementExample();
        OSupplementExample.Criteria criteria = oSupplementExample.createCriteria();
        criteria.andIdEqualTo(id);
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<OSupplement> oSupplements = oSupplementMapper.selectByExample(oSupplementExample);
        if (oSupplements.size() != 1) {
            return null;
        }
        return oSupplements.get(0);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO supplementSave(OsupplementVo osupplementVo) throws Exception {

        if (null == osupplementVo.getSupplement()) {
            logger.info("补款添加:{}", "补款添加信息为空");
            return ResultVO.fail("补款添加信息为空");
        }
        OSupplement oSupplement = osupplementVo.getSupplement();
        if (StringUtils.isEmpty(oSupplement.getcUser())) {
            logger.info("补款添加:{}", "操作用户不能为空");
            return ResultVO.fail("操作用户不能为空");
        }
        if (StringUtils.isEmpty(oSupplement.getPkType())) {
            logger.info("补款添加:{}", "类型不能为空");
            return ResultVO.fail("类型不能为空");
        }
        if (StringUtils.isEmpty(oSupplement.getSrcId())) {
            logger.info("补款添加:{}", "源数据不能为空");
            return ResultVO.fail("源数据不能为空");
        }
        List<String> file = osupplementVo.getAgentTableFile();
        if (file == null || file.size() == 0) {
            logger.info("补款添加:{}", "请上传打款截图");
            throw new MessageException("请上传打款截图");
        }
        if (osupplementVo.getoCashReceivablesVos() == null || osupplementVo.getoCashReceivablesVos().size() == 0) {
            throw new MessageException("必须上传打款记录");
        }
        List<OCashReceivablesVo> oCashReceivablesVos = osupplementVo.getoCashReceivablesVos();
        BigDecimal sumAmount = BigDecimal.ZERO;
        for (OCashReceivablesVo oCashReceivablesVo : oCashReceivablesVos) {
            sumAmount = sumAmount.add(oCashReceivablesVo.getAmount());
        }
        if (sumAmount.compareTo(oSupplement.getPayAmount()) != 0) {
            throw new MessageException("打款金额必须与付款金额相同");
        }
        if (null != oSupplement.getAmount()) {
            if (oSupplement.getPayAmount().compareTo(oSupplement.getAmount()) == 1) {
                //如果实际付款金额大于总金额
                logger.info("实际付款金额大于欠款金额金额,请重新添加补款金额");
                throw new MessageException("实际付款金额大于欠款金额,请重新添加补款金额");
            }

        }
        //去查询是否已经在审批
        String srcId = oSupplement.getSrcId();
        String pkType = oSupplement.getPkType();
        OSupplementExample oSupplementExample = new OSupplementExample();
        OSupplementExample.Criteria criteria = oSupplementExample.createCriteria();
        criteria.andSrcIdEqualTo(srcId);
        criteria.andPkTypeEqualTo(pkType);
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andReviewStatusIn(Arrays.asList(AgStatus.Create.status, AgStatus.Approving.status));
        List<OSupplement> oSupplements = oSupplementMapper.selectByExample(oSupplementExample);
        if (null != oSupplements && oSupplements.size() > 0) {
            logger.info("补款添加:{}", "已在补款中");
            return ResultVO.fail("已在补款中！！");
        }


        //查询一个订单中是否还存在付款中的
        OPaymentDetail oPaymentDetail = oPaymentDetailMapper.selectById(srcId);
        if (null != oPaymentDetail) {
            OPaymentDetailExample oPaymentDetailExample = new OPaymentDetailExample();
            OPaymentDetailExample.Criteria criteria1 = oPaymentDetailExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andPaymentIdEqualTo(oPaymentDetail.getPaymentId()).andPaymentStatusEqualTo(PaymentStatus.FKING.code);
            List<OPaymentDetail> oPaymentDetails = oPaymentDetailMapper.selectByExample(oPaymentDetailExample);
            if (null != oPaymentDetails && oPaymentDetails.size() > 0) {
                logger.info("补款添加:{}", "当前有正在审批中的补款申请 请在该审批结束后再次提交补款 ");
                throw new MessageException("当前有正在审批中的补款申请 请在该审批结束后再次提交补款 ！！");
            }
        }


        //再查询是否是最后一期的补款 不可多补或者少补
        List<OPaymentDetail> oPaymentDetailList = oPaymentDetailMapper.selectCount(oPaymentDetail.getOrderId(), PamentIdType.ORDER_FKD.code, PaymentStatus.DF.code);
        //去查询还剩几期待付款
        BigDecimal count = new BigDecimal(oPaymentDetailList.size());
        if (count.compareTo(new BigDecimal(1)) == 0) {
            //如果就剩本条待付款  则需全部结清
            OPaymentDetail oPayment_detail = oPaymentDetailList.get(0);
            if(oPayment_detail.getPaymentStatus().compareTo(new BigDecimal(1))==0 || oPayment_detail.getPaymentStatus().compareTo(new BigDecimal(3))==0 ){
                //如果是待付款 或者 逾期
                BigDecimal amount = oPaymentDetail.getPayAmount();//这个是订单需补款金额
                if (oSupplement.getPayAmount().compareTo(amount) == -1 || oSupplement.getPayAmount().compareTo(amount) == 1) {
                    logger.info("应补款金额为{}，请重新补款", amount);
                    throw new MessageException("应补款金额为" + amount + "，请重新补款");
                }
            }else if(oPayment_detail.getPaymentStatus().compareTo(new BigDecimal(2))==0){
                //否则是部分付款
                BigDecimal amount = oPaymentDetail.getPayAmount().subtract(oPaymentDetail.getRealPayAmount());
                if (oSupplement.getPayAmount().compareTo(amount) == -1 || oSupplement.getPayAmount().compareTo(amount) == 1) {
                    logger.info("应补款金额为{}，请重新补款", amount);
                    throw new MessageException("应补款金额为" + amount + "，请重新补款");
                }
            }

        }

        Date date = Calendar.getInstance().getTime();
        oSupplement.setId(idService.genId(TabId.o_Supplement));
        oSupplement.setcTime(date);
        oSupplement.setReviewStatus(AgStatus.Create.status);//审批状态
        oSupplement.setSchstatus(SchStatus.ONE.getValue());//补款状态
        oSupplement.setStatus(Status.STATUS_1.status);
        oSupplement.setVersion(Status.STATUS_1.status);
        oSupplement.setLogicalVersion(LogicalVersion.ONE.code);
        AgentResult result = oCashReceivablesService.addOCashReceivables(osupplementVo.getoCashReceivablesVos(), String.valueOf(oSupplement.getcUser()), osupplementVo.getSupplement().getAgentId(), CashPayType.getContentEnum(CashPayType.SUPPLEMENT.code), osupplementVo.getSupplement().getId());
        if (result.getMapData() != null) {
            Map<String, Object> resMapCash = result.getMapData();
            if (resMapCash.get("isYHHK") != null && (Boolean) resMapCash.get("isYHHK")) {
                if (file == null || file.size() == 0) {
                    logger.info("补款添加:{}", "请上传打款截图");
                    throw new MessageException("请上传打款截图");
                }
            }
        }
        oSupplement.setRealPayAmount((BigDecimal) result.getData());
        if (1 == oSupplementMapper.insertSelective(oSupplement)) {
            osupplementVo.setSupplement(oSupplement);
            if (null != file) {
                for (String s : file) {
                    if (org.apache.commons.lang.StringUtils.isEmpty(s)) continue;
                    AttachmentRel record = new AttachmentRel();
                    record.setAttId(s);
                    record.setSrcId(oSupplement.getId());
                    record.setcUser(oSupplement.getcUser());
                    record.setcTime(oSupplement.getcTime());
                    record.setStatus(Status.STATUS_1.status);
                    record.setBusType(AttachmentRelType.Order.name());
                    record.setId(idService.genId(TabId.a_attachment_rel));
                    if (1 != attachmentRelMapper.insertSelective(record)) {
                        logger.info("补款添加:{}", "上传打款截图失败");
                        throw new MessageException("上传打款截图失败");
                    }
                }
            }
            AgentResult agentResult= orderOffsetService.OffsetArrears(oPaymentDetailList, oSupplement.getPayAmount(), DDBK.code, oSupplement.getId());
            if (agentResult.getMapData() != null) {
                Map<String, Object> resMapCash = agentResult.getMapData();
                BigDecimal residueAmt = new BigDecimal(resMapCash.get("residueAmt").toString());
                if (residueAmt.compareTo(new BigDecimal(BigInteger.ZERO))==-1 || residueAmt.compareTo(new BigDecimal(BigInteger.ZERO))==1) {
                    logger.info("补款失败:{}", "抵扣金额大于或小于欠款金额"+residueAmt);
                    throw new MessageException("抵扣金额大于或小于欠款金额"+residueAmt);
                }
                List<OPaymentDetail> offsetPaymentDetails=(ArrayList)resMapCash.get("offsetPaymentDetails");
            }
            startSuppActivity(osupplementVo.getSupplement().getId(), oSupplement.getcUser() + "");
            logger.info("补款添加:成功");
        }
        return ResultVO.success(osupplementVo);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO startSuppActivity(String id, String userId) throws Exception {
        logger.info("========用户{}启动补款审核{}", userId, id);
        if (StringUtils.isBlank(id)) {
            logger.info("补款审批,补款ID为空{}:{}", id, userId);
            throw new MessageException("补款审批中，补款ID为空");
        }
        if (StringUtils.isBlank(userId)) {
            logger.info("补款审批,操作用户为空{}:{}", id, userId);
            throw new MessageException("补款审批中，操作用户为空");
        }
        oCashReceivablesService.startProcing(CashPayType.getContentEnum(CashPayType.SUPPLEMENT.code), id, userId);
        OSupplement oSupplement = oSupplementMapper.selectByPrimaryKey(id);

        String orderId = "";
        if (oSupplement.getPkType().equals(PkType.FQBK.code)) {
            //获取资源id
            OPaymentDetailExample oPaymentDetailExample = new OPaymentDetailExample();
            OPaymentDetailExample.Criteria criteria = oPaymentDetailExample.createCriteria();
            criteria.andIdEqualTo(oSupplement.getSrcId());
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            List<OPaymentDetail> oPaymentDetails = oPaymentDetailMapper.selectByExample(oPaymentDetailExample);
            if (oPaymentDetails.size() != 1) {
                return ResultVO.fail("明细为空");
            }
            OPaymentDetail oPaymentDetail = oPaymentDetails.get(0);
            orderId = oPaymentDetail.getOrderId();

            BigDecimal paymentStatus = oPaymentDetail.getPaymentStatus();
            List<String> status =  Stream.of(String.valueOf(PaymentStatus.DF.code),String.valueOf(PaymentStatus.YQ.code),String.valueOf(PaymentStatus.BF.code)).collect(Collectors.toList());
            if (!status.contains(String.valueOf(paymentStatus))){
                logger.info("补款信息状态异常{}:{}", id, userId);
                throw new MessageException("补款信息状态异常");
            }

            //只有是待付款状态才可以启动流程   并修改状态为付款中
            oPaymentDetail.setPaymentStatus(PaymentStatus.FKING.code);
            if (1 != oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail)) {
                logger.info("订单付款状态修改失败{}:", oPaymentDetail.getId());
                throw new MessageException("订单付款状态修改失败");
            }

        }
        //检查是否有审批中补款
        BusActRelExample example = new BusActRelExample();
        example.or().andBusIdEqualTo(id).andActivStatusEqualTo(AgStatus.Approving.name()).andStatusEqualTo(Status.STATUS_1.status);
        if (busActRelMapper.selectByExample(example).size() > 0) {
            logger.info("补款审批,禁止重复提交审批{}:{}", id, userId);
            throw new MessageException("补款审批中，禁止重复提交审批");
        }
//        List<Dict> actlist = dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.ACT_RETURN_FINANCE.name());
//        String workId = null;
//        for (Dict dict : actlist) {
//            workId = dict.getdItemvalue();
//        }
        oSupplement.setReviewStatus(AgStatus.Approving.status);

        if (1 != oSupplementMapper.updateByPrimaryKeySelective(oSupplement)) {
            logger.info("补款审批，启动审批异常，更新记录状态{}:{}", oSupplement.getId(), userId);
            throw new MessageException("更新记录状态异常");
        }
//        if (StringUtils.isEmpty(workId)) {
//            logger.info("========用户{}启动补款审批{}{}", id, userId, "审批流启动失败字典中未配置部署流程");
//            throw new MessageException("审批流启动失败字典中未配置部署流程!");
//        }
        Map startPar = agentEnterService.startPar(userId);
        if (null == startPar) {
            logger.info("========用户{}启动补款审批{}{}启动部门参数为空", id, userId, "审批流启动失败字典中未配置部署流程");
            throw new MessageException("启动部门参数为空!");
        }
        String proce = activityService.createDeloyFlow(null, dictOptionsService.getApproveVersion("returnFinance"), null, null, startPar);
        if (proce == null) {
            logger.info("========用户{}启动补款审批申请{}{}", id, userId, "补款审批，审批流启动失败");
            logger.info("补款审批，审批流启动失败{}:{}", id, userId);
            throw new MessageException("审批流启动失败!");
        }
        //补款业务流程关系
        BusActRel record = new BusActRel();
        record.setBusId(oSupplement.getId());
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(userId);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.PkType.name());//流程关系类型是数据申请类型
        record.setActivStatus(AgStatus.Approving.name());
        record.setDataShiro(BusActRelBusType.PkType.key);
        if (null != oSupplement.getAgentId()) {
            record.setAgentId(oSupplement.getAgentId());
            Agent agent = agentMapper.selectByPrimaryKey(oSupplement.getAgentId());
            if (null != agent) {
                record.setAgentName(agent.getAgName());
            }

        }

        OOrderExample oOrderExample = new OOrderExample();
        OOrderExample.Criteria criteria = oOrderExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andIdEqualTo(orderId);
        List<OOrder> oOrderList = oOrderMapper.selectByExample(oOrderExample);
        if (null!=oOrderList && oOrderList.size()>0){
            AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
            AgentBusInfoExample.Criteria criteria1 = agentBusInfoExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andIdEqualTo(oOrderList.get(0).getBusId());
            List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
            if (null!=agentBusInfos && agentBusInfos.size()>0 ){
                record.setAgDocDistrict(agentBusInfos.get(0).getAgDocDistrict());
                record.setAgDocPro(agentBusInfos.get(0).getAgDocPro());
            }

        }


        if (1 != busActRelMapper.insertSelective(record)) {
            logger.info("补款审批审批，启动审批异常，添加审批关系失败{}:{}", oSupplement.getId(), proce);
            throw new MessageException("添加审批关系失败");

        }
        return ResultVO.success(null);
    }

    @Override
    public OSupplement informationQuery(String id) {
        return oSupplementMapper.selectByPrimaryKey(id);
    }

    @Override
    public OPaymentDetail selectByOPaymentId(String id) {
        OPaymentDetailExample oPaymentDetailExample = new OPaymentDetailExample();
        OPaymentDetailExample.Criteria criteria = oPaymentDetailExample.createCriteria();
        criteria.andIdEqualTo(id);
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<OPaymentDetail> details = oPaymentDetailMapper.selectByExample(oPaymentDetailExample);
        if (details.size() != 1) {
            return null;
        }
        OPaymentDetail oPaymentDetail = details.get(0);
        oPaymentDetail.setPayType(PaymentType.getPaymentTypeValue(oPaymentDetail.getPayType()));
        if (StringUtils.isNotBlank(oPaymentDetail.getSrcType())) {
            oPaymentDetail.setSrcType(PamentSrcType.getSrcTypeValue(oPaymentDetail.getSrcType()));
        }
        return oPaymentDetail;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws Exception {
        try {
            AgentResult result = agentEnterService.completeTaskEnterActivity(agentVo, userId);
            if (!result.isOK()) {
                logger.error(result.getMsg());
                throw new MessageException("工作流处理任务异常");
            }
        } catch (MessageException | ProcessException e) {
            e.printStackTrace();
            throw new MessageException(e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MessageException("catch工作流处理任务异常!");
        }
        return AgentResult.ok();
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO updateByActivId(String id, String activityName) throws MessageException {
        BusActRel busActRel = selectByActivId(id);
        if (null == busActRel) {
            return null;
        }
        OSupplement oSupplement = oSupplementMapper.selectByPrimaryKey(busActRel.getBusId());
        String srcId = "";
        if (oSupplement == null) {
            logger.info("补款记录未找到");
            throw new MessageException("补款记录未找到");
        }
        if ("reject_end".equals(activityName)) {//审批拒绝
            logger.info("补款审批拒绝{}{}:", busActRel.getActivId(), oSupplement.getId());
            busActRel.setActivStatus(AgStatus.Refuse.name());
            if (1 != busActRelMapper.updateByPrimaryKeySelective(busActRel)) {
                logger.info("业务流程状态修改失败{}:", busActRel.getActivId());
                throw new MessageException("业务流程状态修改失败");
            }
            oSupplement.setSchstatus(SchStatus.FOUR.getValue());
            oSupplement.setReviewStatus(AgStatus.Refuse.status);
            if (1 != oSupplementMapper.updateByPrimaryKeySelective(oSupplement)) {
                logger.info("补款状态修改失败{}:", busActRel.getActivId());
                throw new MessageException("补款状态修改失败");
            }

            try {
                oCashReceivablesService.refuseProcing(CashPayType.getContentEnum(CashPayType.SUPPLEMENT.code), oSupplement.getId(), null);
            } catch (Exception e) {
                throw new MessageException("审批通过失败");
            }
           /* OSupplement supplement = selectOSupplement(oSupplement.getId());
            if (supplement.getPkType().equals(PkType.FQBK.code)) {
                OPaymentDetail oPaymentDetail = oPaymentDetailMapper.selectByPrimaryKey(supplement.getSrcId());
                oPaymentDetail.setPaymentStatus(PaymentStatus.DF.code);
                if (1 != oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail)) {
                    logger.info("订单付款状态修改失败{}:", busActRel.getActivId());
                    throw new MessageException("订单付款状态修改失败");
                }
                logger.info("补款审批拒绝更新记录为待付款状态成功{}{}:", busActRel.getActivId(), oSupplement.getId());
            } else {
                logger.info("补款审批拒绝pktype不匹配{}{}:", busActRel.getActivId(), oSupplement.getId());
            }
*/
            AgentResult agentResult = orderOffsetService.OffsetArrearsCancle(oSupplement.getPayAmount(), OffsetPaytype.DDBK.code, oSupplement.getId());
            if (!agentResult.isOK()){
                logger.error("补款拒绝失败");
                throw new MessageException("补款拒绝失败！");
            }
        } else if ("finish_end".equals(activityName)) {//审批同意
            logger.info("补款审批同意{}{}:", busActRel.getActivId(), oSupplement.getId());
            busActRel.setActivStatus(AgStatus.Approved.name());
            if (1 != busActRelMapper.updateByPrimaryKeySelective(busActRel)) {
                logger.info("业务流程状态修改失败{}", busActRel.getActivId());
                throw new MessageException("业务流程状态修改失败");
            }
            oSupplement.setSchstatus(SchStatus.THREE.getValue());
            oSupplement.setReviewStatus(AgStatus.Approved.status);
            if (1 != oSupplementMapper.updateByPrimaryKeySelective(oSupplement)) {
                logger.info("补款状态修改失败{}:", busActRel.getActivId());
                throw new MessageException("补款状态修改失败");
            }

            try {
                oCashReceivablesService.finishProcing(CashPayType.getContentEnum(CashPayType.SUPPLEMENT.code), oSupplement.getId(), null);
            } catch (Exception e) {
                throw new MessageException("拒绝审批失败");
            }
            OSupplement osupplement = oSupplementMapper.selectByPrimaryKey(oSupplement.getId());
            AgentResult agentResult = orderOffsetService.OffsetArrearsCommit(osupplement.getPayAmount(), OffsetPaytype.DDBK.code, osupplement.getId());
            if (!agentResult.isOK()){
                logger.info(agentResult.getMsg());
                throw new MessageException("补款更新异常！");
            }
         /*   //修改订单明细付款状态
            OSupplement supplement = selectOSupplement(oSupplement.getId());
            if (supplement.getPkType().equals(PkType.FQBK.code)) {
                OPaymentDetail oPaymentDetail = oPaymentDetailMapper.selectByPrimaryKey(supplement.getSrcId());
                if (oPaymentDetail.getPayAmount().compareTo(supplement.getRealPayAmount()) == 0 || oPaymentDetail.getPayAmount().compareTo(supplement.getRealPayAmount()) == -1) {
                    //应付金额等于实付金额  或者  应付金额小于实付金额  则是已结清
                    oPaymentDetail.setPaymentStatus(PaymentStatus.JQ.code);
                } else {
                    oPaymentDetail.setPaymentStatus(PaymentStatus.YF.code);
                }
                oPaymentDetail.setRealPayAmount(supplement.getRealPayAmount());
                //审批通过还需要更新srcId,srcType,实际付款时间
                oPaymentDetail.setSrcId(supplement.getId());
                oPaymentDetail.setSrcType(PamentSrcType.XXBK.code);
                if (null != supplement && null != supplement.getRemitTime())
                    oPaymentDetail.setPayTime(supplement.getRemitTime());
                else
                    oPaymentDetail.setPayTime(Calendar.getInstance().getTime());
                if (1 != oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail)) {
                    logger.info("订单付款状态修改失败{}:", busActRel.getActivId());
                    throw new MessageException("订单付款状态修改失败");
                }
                oPaymentDetail= oPaymentDetailMapper.selectByPrimaryKey(oPaymentDetail.getId());
                //更新付款单的已付款和未付款
                OPaymentExample oPaymentExample = new OPaymentExample();
                OPaymentExample.Criteria criteria = oPaymentExample.createCriteria();
                criteria.andIdEqualTo(oPaymentDetail.getPaymentId());
                criteria.andStatusEqualTo(Status.STATUS_1.status);
                OPayment oPayments = oPaymentMapper.selectByPrimaryKey(oPaymentDetail.getPaymentId());
                if (null == oPayments) {
                    logger.info("无此数据");
                    throw new MessageException("无此数据!!!");
                }
                OPayment oPayment = oPayments;
                if (null == oPaymentDetail.getRealPayAmount()) {
                    oPaymentDetail.setRealPayAmount(new BigDecimal(0));
                }
                oPayment.setRealAmount(oPayment.getRealAmount().add(oPaymentDetail.getRealPayAmount()));
                if (null == oPayment.getOutstandingAmount() || oPayment.getOutstandingAmount().compareTo(new BigDecimal(0)) == 0) {
                    logger.info("欠款已结清,请审批拒绝");
                    throw new MessageException("欠款已结清,请审批拒绝!!!");
                }

                //需要查询到总金额
                OPayment oPayMent = oPayments;
                List<OPaymentDetail> countMap = oPaymentDetailMapper.selectCount(oPaymentDetail.getOrderId(), PamentIdType.ORDER_FKD.code, PaymentStatus.DF.code);

                //多加个判断  判断实际付款金额是否大于欠款金额
                if (supplement.getRealPayAmount().compareTo(oPayMent.getOutstandingAmount()) == 1) {
                    logger.info("实际付款金额大于欠款金额,请拒绝审批重新添加补款金额");
                    throw new MessageException("实际付款金额大于欠款金额,请拒绝审批重新添加补款金额");
                } else if (supplement.getRealPayAmount().compareTo(oPayMent.getOutstandingAmount()) == 0) {
                    //实际付款金额是否等于欠款金额
                    if (null != countMap || countMap.size() > 0) {
                        for (OPaymentDetail paymentDetail : countMap) {
                            paymentDetail.setPayAmount(new BigDecimal(0));
                            paymentDetail.setPaymentStatus(PaymentStatus.JQ.code);
                            if (1 != oPaymentDetailMapper.updateByPrimaryKeySelective(paymentDetail)) {
                                logger.info("实际付款金额等于欠款金额,更新失败");
                                throw new MessageException("更新失败");
                            }
                        }
                    }

                    oPayment.setOutstandingAmount(oPayment.getOutstandingAmount().subtract(oPaymentDetail.getRealPayAmount()));
                    if (1 != oPaymentMapper.updateByPrimaryKeySelective(oPayment)) {
                        logger.info("付款单修改失败");
                        throw new MessageException("付款单修改失败");
                    }
                } else {
                    oPayment.setOutstandingAmount(oPayment.getOutstandingAmount().subtract(oPaymentDetail.getRealPayAmount()));
                    if (1 != oPaymentMapper.updateByPrimaryKeySelective(oPayment)) {
                        logger.info("付款单修改失败");
                        throw new MessageException("付款单修改失败");
                    }
                    BigDecimal payAmount = oPayMent.getPayAmount();
                    //  查询已结清的实际付款金额
                    BigDecimal realPayAmount = oPaymentDetailMapper.selectRealAmount(oPaymentDetail.getOrderId(), PamentIdType.ORDER_FKD.code);
                    //如果总金额大于实际已经付款金额  则还有未结清金额
                    BigDecimal residue = new BigDecimal(0);
                    //剩余未结清的金额
                    residue = payAmount.subtract(realPayAmount);
                *//*if (residue.compareTo(oPayMent.getOutstandingAmount()) == 0) {
                    return ResultVO.success(null);
                }*//*
                    List<OPaymentDetail> notCountMap = oPaymentDetailMapper.selectCount(oPaymentDetail.getOrderId(), PamentIdType.ORDER_FKD.code, PaymentStatus.DF.code);
                    //去查询还剩几期待付款
                    BigDecimal count = new BigDecimal(notCountMap.size());
                    if (count.compareTo(new BigDecimal(0)) == 0) {
                        //如果就剩本条待付款  则需全部结清
                        BigDecimal amount = oPaymentDetail.getPayAmount();//这个是订单需补款金额
                        if (supplement.getRealPayAmount().compareTo(amount) == -1 || supplement.getRealPayAmount().compareTo(amount) == 1) {
                            logger.info("应补款金额为{}，请审批拒绝，重新补款", amount);
                            throw new MessageException("应补款金额为" + amount + "，请审批拒绝，重新补款");
                        } else if (supplement.getRealPayAmount().compareTo(amount) == 0) {
                            //否则是相等的  则进行更新
                            oPaymentDetail.setPayAmount(supplement.getRealPayAmount());
                            if (1 != oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail)) {
                                logger.info("付款金额更新失败");
                                throw new MessageException("付款金额更新失败");
                            }
                        }
                    } else {
                        try {
                            List<BigDecimal> divideList = new ArrayList<>();
                            BigDecimal money = oPaymentDetail.getPayAmount().subtract(supplement.getRealPayAmount());
                            BigDecimal stage = money.divide(count,2,BigDecimal.ROUND_HALF_UP);
                            BigDecimal tt = BigDecimal.ZERO;
                            for (int i = 0; i < count.intValue(); i++) {
                                //补充除不尽余额
                                if(i==count.intValue()-1){
                                    if(0!=money.subtract(tt).compareTo(stage)){
                                        stage =   money.subtract(tt);
                                    }
                                }
                                tt=tt.add(stage);
                                divideList.add(stage);
                            }

                            for (int j = 0; j < notCountMap.size(); j++) {
                                OPaymentDetail paymentDetail = notCountMap.get(j);
                                paymentDetail.setPayAmount(paymentDetail.getPayAmount().add(divideList.get(j)));
                                if (1 != oPaymentDetailMapper.updateByPrimaryKeySelective(paymentDetail)) {
                                    logger.info("平均金额失败");
                                    throw new MessageException("平均金额失败");
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }*/
        }
        return ResultVO.success(null);
    }

    @Override
    public ResultVO selectBySrcId(OsupplementVo osupplementVo) {
        ResultVO res = new ResultVO();
        if (null == osupplementVo.getSupplement()) {
            logger.info("补款信息为空{}:", osupplementVo.getSupplement());
            return res.fail("失败");
        }
        OSupplement supplement = osupplementVo.getSupplement();
        String oPaymentDetailId = supplement.getSrcId();
        OPaymentDetailExample oPaymentDetailExample = new OPaymentDetailExample();
        OPaymentDetailExample.Criteria criteria = oPaymentDetailExample.createCriteria();
        criteria.andIdEqualTo(oPaymentDetailId);
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<OPaymentDetail> oPaymentDetails = oPaymentDetailMapper.selectByExample(oPaymentDetailExample);
        if (1 != oPaymentDetails.size()) {
            return res.fail("失败");
        }
        OPaymentDetail oPaymentDetail = oPaymentDetails.get(0);
        if (null != oPaymentDetail.getPaymentStatus()) {
            if (oPaymentDetail.getPaymentStatus().equals(PaymentStatus.DF.code) || oPaymentDetail.getPaymentStatus().equals(PaymentStatus.BF.code )|| oPaymentDetail.getPaymentStatus().equals(PaymentStatus.YQ.code)) {
                return res.success("");
            }
        }
        return res;
    }


    public BusActRel selectByActivId(String id) {
        BusActRelExample busActRelExample = new BusActRelExample();
        BusActRelExample.Criteria criteria = busActRelExample.createCriteria();
        criteria.andActivIdEqualTo(id);
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andActivStatusEqualTo(AgStatus.Approving.name());
        List<BusActRel> busActRels = busActRelMapper.selectByExample(busActRelExample);
        if (1 != busActRels.size()) {
            return null;
        }
        return busActRels.get(0);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO updateAmount(AgentVo agentVo, Long userId) throws Exception {

        if (null == userId) {
            logger.info("无法获取当前登录用户");
            throw new MessageException("无法获取当前登录用户");
        }
        if (StringUtils.isBlank(agentVo.getSupplementId())) {
            logger.info("补款id为空");
            throw new MessageException("补款id为空");
        }
        if (null == agentVo.getCheckTime()) {
            logger.info("请填写核款时间");
            throw new MessageException("请填写核款时间");
        }
        OSupplement oSupplement = selectOSupplement(agentVo.getSupplementId());
        oSupplement.setRealPayAmount(agentVo.getRealPayAmount());
        oSupplement.setCheckTime(agentVo.getCheckTime());
        oSupplement.setCheckPeople(String.valueOf(userId));
        if (1 != oSupplementMapper.updateByPrimaryKeySelective(oSupplement)) {
            logger.info("实际金额保存失败");
            throw new MessageException("实际金额保存失败");
        }
        oCashReceivablesService.approveTashBusiness(CashPayType.getContentEnum(CashPayType.SUPPLEMENT.code), agentVo.getSupplementId(), String.valueOf(userId), agentVo.getCheckTime(), agentVo.getoCashReceivablesVoList());
        logger.info("更新实际付款金额成功");
        return ResultVO.success("");
    }

    @Override
    public BigDecimal selectPayAmout(String srcid, String pkType) {
//        BigDecimal payAmout = oSupplementMapper.selectPayAmout(srcid, pkType);
        BigDecimal amount=new BigDecimal(BigInteger.ZERO);
        OPaymentDetailExample oPaymentDetailExample = new OPaymentDetailExample();
        OPaymentDetailExample.Criteria criteria = oPaymentDetailExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andIdEqualTo(srcid);
        List<OPaymentDetail> oPaymentDetailList = oPaymentDetailMapper.selectByExample(oPaymentDetailExample);
        if(null!=oPaymentDetailList && oPaymentDetailList.size()>0){
            OPaymentDetail oPaymentDetail = oPaymentDetailList.get(0);
            if(StringUtils.isNotBlank(oPaymentDetail.getOrderId())){
                SimpleDateFormat mm = new SimpleDateFormat("YYYY-MM");
                String time = mm.format(new Date());
                amount=oPaymentDetailMapper.selectQk(oPaymentDetail.getOrderId(),time);
                if (null==amount){
                    amount=new BigDecimal(BigInteger.ZERO);
                }
            }
        }
        return amount;
    }


    @Override
    public OPaymentDetail selectPaymentDetailById(String id) {
        OPaymentDetail oPaymentDetail = oPaymentDetailMapper.selectByPrimaryKey(id);
        return oPaymentDetail;
    }

    @Override
    public OPayment selectOpayment(String id) {
        OPaymentExample oPaymentExample = new OPaymentExample();
        OPaymentExample.Criteria criteria = oPaymentExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andIdEqualTo(id);
        List<OPayment> oPayments = oPaymentMapper.selectByExample(oPaymentExample);
        if (null == oPayments || oPayments.size() == 0) {
            return new OPayment();
        }
        return oPayments.get(0);
    }

    @Override
    public List<OPaymentDetail> selectCount(String orderId, String code) {
      return   oPaymentDetailMapper.selectCount(orderId,code, null);
    }

    @Override
    public List<OPaymentDetail> selectPaymentDetail(String orderId, String code) {
        return   oPaymentDetailMapper.selectPaymentDetail(orderId,code, null);
    }

    @Override
    public List<OPayDetail> selectOpayDetail(OPaymentDetail oPaymentDetail) {
        if(null!=oPaymentDetail){
            if (oPaymentDetail.getPayType().equals(PaymentType.TK.code) || oPaymentDetail.getPayType().equals(PaymentType.GZ.code)){
                return  orderOffsetService.OffsetArrearsQuery(QueryType.SRCID.code,oPaymentDetail.getSrcId());
            }else if (oPaymentDetail.getPayAmount().compareTo(BigDecimal.ZERO)>0){
                return  orderOffsetService.OffsetArrearsQuery(QueryType.ARRID.code,oPaymentDetail.getId());
            }
        }
        return new ArrayList<>();
    }

    @Override
    public OPaymentDetail selectoPaymentDetail(String orderId) {
        if (StringUtils.isBlank(orderId)){
            return null;
        }
        SimpleDateFormat mm = new SimpleDateFormat("YYYY-MM");
        String time = mm.format(new Date());
        OPaymentDetail oPaymentDetail = oPaymentDetailMapper.selectoPaymentDetail(orderId, time);
        return oPaymentDetail;
    }

}
