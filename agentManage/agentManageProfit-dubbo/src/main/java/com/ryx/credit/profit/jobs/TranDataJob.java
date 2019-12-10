package com.ryx.credit.profit.jobs;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.Des3Util;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.pojo.ProfitOrganTranMonth;
import com.ryx.credit.profit.pojo.TranCheckData;
import com.ryx.credit.profit.pojo.TranCheckPlatForm;
import com.ryx.credit.profit.pojo.TransProfitDetail;
import com.ryx.credit.profit.service.ProfitComputerService;
import com.ryx.credit.profit.service.ProfitOrganTranMonthService;
import com.ryx.credit.profit.service.TransProfitDetailService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.profit.IPosProfitDataService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author zhaodw
 * @Title: TranDataJob
 * @ProjectName agentManage
 * @Description: 获取交易数据，对数据进行差异核对
 * @date 2018/7/2911:34
 */
@PropertySource("classpath:/config.properties")
@Service("tranDataJob")
@Transactional(rollbackFor = Exception.class)
public class TranDataJob {
    private org.slf4j.Logger LOG = LoggerFactory.getLogger(TranDataJob.class);

    private static final String URL = AppConfig.getProperty("check_tran_url");
    private static final String rhbReqUrl = AppConfig.getProperty("rhb_req_url");
    private static final String rhb3desKey = AppConfig.getProperty("rhb_3des_Key");
    private static final String rhb3desIv = AppConfig.getProperty("rhb_3des_iv");
    private static final String environment = AppConfig.getProperty("jobEnvironment");

    @Autowired
    private IPosProfitDataService posProfitDataService;

    @Autowired
    private TransProfitDetailService transProfitDetailServiceImpl;

    @Autowired
    private ProfitOrganTranMonthService profitOrganTranMonthService;

    @Autowired
    private ProfitComputerService profitComputerService;

    @Autowired
    private IdService idService;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev(){
        return new PropertySourcesPlaceholderConfigurer();
    }
    /**
     * @Author: Zhang Lei
     * @Description: 获取交易量数据同步
     * 每月3号12点执行
     * @Date: 11:33 2019/1/24
     */
    @Scheduled(cron = "${profit_checkdata_job_cron}")
    public void doCron() {
       if (!"preproduction".equals(environment)){
           String settleMonth = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0, 6);
           deal(settleMonth);
       }
    }

    @Transactional
    public void deal(String settleMonth) {

        LOG.info("===获取交易量数据开始,分润月份{}====", settleMonth);
        try {
            AgentResult agentResult = posProfitDataService.getPosProfitDate(settleMonth);
            if (agentResult != null && agentResult.getData() != null) {
                JSONObject json = JSONObject.parseObject(agentResult.getData().toString());//pos平台数据
                if (json != null) {
                    //LOG.info(JSONObject.toJSONString(json));
                    BigDecimal tranAmt = BigDecimal.ZERO;
                    BigDecimal zyssAmt = BigDecimal.ZERO;
                    BigDecimal posAmt = BigDecimal.ZERO;
                    JSONObject jsonObject = getTranData(settleMonth);
                    if (jsonObject != null && jsonObject.containsKey("info")) {
                        JSONObject tranData = jsonObject.getJSONObject("info");
                        zyssAmt = tranData.getBigDecimal("zyssAmt") == null ? BigDecimal.ZERO : tranData.getBigDecimal("zyssAmt");
                        ;// 自营代理手刷总金额
                        BigDecimal zydlPosDjAmt = tranData.getBigDecimal("zydlPosDjAmt") == null ? BigDecimal.ZERO : tranData.getBigDecimal("zydlPosDjAmt");
                        ;// 贷记金额
                        BigDecimal zydlPosJjAmt = tranData.getBigDecimal("zydlPosJjAmt") == null ? BigDecimal.ZERO : tranData.getBigDecimal("zydlPosJjAmt");//借记金额
                        BigDecimal jgPosAmt = tranData.getBigDecimal("jgPosAmt") == null ? BigDecimal.ZERO : tranData.getBigDecimal("jgPosAmt");//借记金额
                        BigDecimal zyPosAmt = tranData.getBigDecimal("zyPosAmt") == null ? BigDecimal.ZERO : tranData.getBigDecimal("zyPosAmt");//自营pos金额
                        BigDecimal hyxPosJwAmt = tranData.getBigDecimal("hyxPosJwAmt") == null ? BigDecimal.ZERO : tranData.getBigDecimal("hyxPosJwAmt");//汇银讯境外金额
                        BigDecimal dlPosJwAmt = tranData.getBigDecimal("dlPosJwAmt") == null ? BigDecimal.ZERO : tranData.getBigDecimal("dlPosJwAmt");//代理商境外
                        tranAmt = zydlPosDjAmt.add(zydlPosJjAmt).add(jgPosAmt);//清算pos交易总量
                        //t.hyxpos_jw_amt+t.dlpos_jw_amt
                        posAmt = zyPosAmt.add(hyxPosJwAmt).add(dlPosJwAmt);
                    }
                    insertOrUpdate(json, settleMonth, tranAmt, zyssAmt, posAmt);//新增二维码
                } else {
                    LOG.error("月份：" + settleMonth + "，二维码提供的没有获取到数据");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("分润数据处理失败");
            throw new RuntimeException("分润数据处理失败");
        }
        LOG.info("===获取交易量数据结束====");
    }


    /***
     * @Description:新增或修改数据
     * @Param:json pos分润数据
     * @Param:settleMonth 分润月份
     * @Param:tranAmt pos交易金额
     * @Param:zyssAmt 手刷交易金额
     * @Author: zhaodw
     * @Date: 2018/8/3
     */
    private void insertOrUpdate(JSONObject json, String settleMonth, BigDecimal tranAmt, BigDecimal zyssAmt, BigDecimal posAmt) {
        ProfitOrganTranMonth profitOrganTranMonth = new ProfitOrganTranMonth();
        profitOrganTranMonth.setProfitDate(settleMonth);
        profitOrganTranMonthService.delete(profitOrganTranMonth);
        addPos(json, settleMonth, tranAmt, posAmt);
        addMpos(json, settleMonth, zyssAmt);
        addQr(json, settleMonth);
    }

    /***
     * @Description: 增加MPOS数据
     * @Param: json
     * @Author: zhaodw
     * @Date: 2018/8/1
     */
    private void addMpos(JSONObject json, String settleMonth, BigDecimal tranAmt) {
        ProfitOrganTranMonth profitOrganTranMonth = new ProfitOrganTranMonth();
        profitOrganTranMonth.setProfitDate(settleMonth);
        profitOrganTranMonth.setCheckDate(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        profitOrganTranMonth.setId(idService.genId(TabId.P_ORGAN_TRAN_MONTH));
        profitOrganTranMonth.setProductType("02");
        profitOrganTranMonth.setProductName("MPOS");
        profitOrganTranMonth.setTranAmt(tranAmt.add(new BigDecimal(json.getString("QR_SS_TOTAL_AMT"))));
        try {
            BigDecimal settleAmt = profitComputerService.synchroSSTotalTransAmt(null);
            profitOrganTranMonth.setSettleAmt(settleAmt);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取手刷分润交易数据失败");
        }
        profitOrganTranMonth.setDifferenceAmt(profitOrganTranMonth.getSettleAmt().subtract(profitOrganTranMonth.getTranAmt()));
        profitOrganTranMonthService.insert(profitOrganTranMonth);
    }

    /***
     * @Description: 增加 二维码核对数据
     * @Param: json
     * @Author: zhaodw
     * @Date: 2018/8/1
     */
    private void addQr(JSONObject json, String settleMonth) {
        BigDecimal tranAmt = json.getBigDecimal("QR_POS_TOTAL_AMT");
        BigDecimal qrPos886 = json.getBigDecimal("QR_POS_886_WXZFB_AMT") == null ? BigDecimal.ZERO : json.getBigDecimal("QR_POS_886_WXZFB_AMT");
        BigDecimal qrNpos886 = json.getBigDecimal("QR_NPOS_886_WXZFB_AMT") == null ? BigDecimal.ZERO : json.getBigDecimal("QR_NPOS_886_WXZFB_AMT");
        BigDecimal qrPosUp886 = json.getBigDecimal("QR_POS_886_UPZF_AMT") == null ? BigDecimal.ZERO : json.getBigDecimal("QR_POS_886_UPZF_AMT");
        BigDecimal qrNposUp886 = json.getBigDecimal("QR_NPOS_886_UPZF_AMT") == null ? BigDecimal.ZERO : json.getBigDecimal("QR_NPOS_886_UPZF_AMT");
        BigDecimal qrPos885 = json.getBigDecimal("QR_POS_885_WXZFB_AMT") == null ? BigDecimal.ZERO : json.getBigDecimal("QR_POS_885_WXZFB_AMT");
        BigDecimal qrPosUp885 = json.getBigDecimal("QR_POS_885_UPZF_AMT") == null ? BigDecimal.ZERO : json.getBigDecimal("QR_POS_885_UPZF_AMT");
        ProfitOrganTranMonth profitOrganTranMonth = new ProfitOrganTranMonth();
        profitOrganTranMonth.setProfitDate(settleMonth);
        profitOrganTranMonth.setCheckDate(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        profitOrganTranMonth.setId(idService.genId(TabId.P_ORGAN_TRAN_MONTH));
        profitOrganTranMonth.setProductType("03");
        profitOrganTranMonth.setProductName("二维码");
        profitOrganTranMonth.setTranAmt(tranAmt);
        profitOrganTranMonth.setSettleAmt(qrPos886.add(qrNpos886).add(qrPosUp886).add(qrNposUp886).add(qrPos885).add(qrPosUp885));
        profitOrganTranMonth.setDifferenceAmt(profitOrganTranMonth.getTranAmt().subtract(profitOrganTranMonth.getSettleAmt()));
        profitOrganTranMonthService.insert(profitOrganTranMonth);
    }

    /***
     * @Description: 增加POS数据
     * @Param: json
     * @Author: zhaodw
     * @Date: 2018/8/1
     */
    private void addPos(JSONObject json, String settleMonth, BigDecimal tranAmt, BigDecimal posAmt) {
        ProfitOrganTranMonth profitOrganTranMonth = new ProfitOrganTranMonth();
        profitOrganTranMonth.setProfitDate(settleMonth);
        profitOrganTranMonth.setCheckDate(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        profitOrganTranMonth.setId(idService.genId(TabId.P_ORGAN_TRAN_MONTH));
        profitOrganTranMonth.setProductType("01");
        profitOrganTranMonth.setProductName("POS");
        profitOrganTranMonth.setSettleAmt(json.getBigDecimal("POS_TOTAL_AMT"));
        profitOrganTranMonth.setTranAmt(tranAmt);
        profitOrganTranMonth.setDifferenceAmt(profitOrganTranMonth.getSettleAmt().subtract(tranAmt));
        profitOrganTranMonthService.insert(profitOrganTranMonth);
    }


    /***
     * @Description: 获取POS交易数据
     * @Param: settleMonth 分润月份
     * @return: 数据json对象
     * @Author: zhaodw
     * @Date: 2018/8/2
     */
    private JSONObject getTranData(String settleMonth) {
        LOG.info("从清算获取POS交易数据:");
        JSONObject json = new JSONObject();
        json.put("tranType", "22");
        json.put("tranMon", settleMonth);
        String result = HttpClientUtil.doPostJson(URL, json.toJSONString());
        LOG.info(result);
        if (StringUtils.isNotBlank(result)) {
            return JSONObject.parseObject(result);
        }
        return null;
    }




    /*
    * 新核对
    * renshenghao
    * */

    /**
     * @Author: Renshenghao
     * @Description: 新-获取交易量数据同步
     * 每月3号12点执行
     * @Date: 15:05 2019/4/2
     */
    @Scheduled(cron = "${profit_checkdata_job_cron}")
    public void doSynchronizeTranCheckData() {
        if (!"preproduction".equals(environment)) {
            synchronizeTranCheckData();
        }
    }

    /**
     * 拉取数据并存入数据库
     * @return
     */
    public Map<String,String> synchronizeTranCheckData(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        String searchTime=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss").format(calendar.getTime());
        String tranMonth= new SimpleDateFormat("yyyyMM").format(calendar.getTime());
        LOG.info("================"+tranMonth+"交易量核对数据同步开始================");
        Map<String,String> resultMap=new HashMap<>();

        //手刷平台的交易量和手续费(技术)
        Map<String, Object> tranAmt = getTranAmtByMonth(calendar.getTime());
        if(tranAmt==null||tranAmt.size()==0){
            LOG.error("================手刷平台的交易量和手续费(技术)拉取失败================");
            resultMap.put("resultCode","error");
            resultMap.put("msg","手刷平台的交易量和手续费(技术)拉取异常");
            return resultMap;
        }
        LOG.info("================手刷平台的交易量和手续费(技术)拉取完成================");

        //清结算接口数据：
        Map<String,Object> settleData = doSettleTranAmount(calendar.getTime());
        if(settleData==null||settleData.size()==0){
            LOG.error("================清结算接口数据拉取失败================");
            resultMap.put("resultCode","error");
            resultMap.put("msg","清结算接口数据拉取异常");
            return resultMap;
        }
        LOG.info("================清结算接口数据拉取完成================");

        //瑞大宝交易总量和手续费(技术):
        Map<String,Object>ruiDabaoData=getRuiDaBaoProfitAmtAndProfitFee(calendar.getTime());
        if(ruiDabaoData==null||ruiDabaoData.size()==0){
            LOG.error("================瑞大宝交易总量和手续费查询（技术）拉取失败================");
            resultMap.put("resultCode","error");
            resultMap.put("msg","瑞大宝交易总量和手续费查询（技术）拉取异常");
            return resultMap;
        }
        LOG.info("================瑞大宝交易总量和手续费查询（技术）拉取完成================");

        //瑞花宝交易总量和手续费（技术）:
        Map<String, Object> ruiHuaBaoData = getRuiHuaBaoProfitAmtAndProfitFee(calendar.getTime(),"RHB");
        if(ruiHuaBaoData==null||ruiHuaBaoData.size()==0){
            LOG.error("================瑞花宝交易量手续费查询（技术）拉取失败================");
            resultMap.put("resultCode","error");
            resultMap.put("msg","瑞花宝交易量手续费查询（技术）拉取异常");
            return resultMap;
        }
        LOG.info("================瑞花宝交易量手续费查询（技术）拉取完成================");

        //瑞通宝plus交易总量和手续费（技术）:
        Map<String, Object> ruiTongBaoPlusData = getRuiHuaBaoProfitAmtAndProfitFee(calendar.getTime(),"RTBP");
        if(ruiTongBaoPlusData==null||ruiTongBaoPlusData.size()==0){
            LOG.error("================瑞通宝plus交易量手续费查询（技术）拉取失败================");
            resultMap.put("resultCode","error");
            resultMap.put("msg","瑞通宝plus交易量手续费查询（技术）拉取异常");
            return resultMap;
        }
        LOG.info("================瑞通宝plus交易量手续费查询（技术）拉取完成================");

        //月分润交易接口数据（技术列）:
        Map<String, Object> tradingVolumeData1 = doProfitTradingVolume(calendar.getTime(),"PFT003");
        if(tradingVolumeData1==null||tradingVolumeData1.size()==0){
            LOG.error("================月分润交易接口数据（技术列）拉取失败================");
            resultMap.put("resultCode","error");
            resultMap.put("msg","月分润交易接口数据（技术列）拉取异常");
            return resultMap;
        }
        LOG.info("================月分润交易接口数据（技术列）拉取完成================");

        //手刷百卡通数据：
        TransProfitDetail tranBkt = getTranBktByProfitMonth(tranMonth);
        if (tranBkt==null){
            LOG.error("================手刷百卡通数据拉取失败================");
            resultMap.put("resultCode","error");
            resultMap.put("msg","手刷百卡通数据拉取异常");
            return resultMap;
        }
        LOG.info("================手刷百卡通数据拉取完成================");
        //新月结分润接口数据：
        Map<String, Object> newMonthData = doProfitNewMonth(calendar.getTime());
        if(newMonthData==null){
            LOG.error("================新月结分润接口数据拉取失败================");
            resultMap.put("resultCode","error");
            resultMap.put("msg","新月结分润接口数据拉取异常");
            return resultMap;
        }
        LOG.info("================新月结分润接口数据拉取完成================");
        //月分润交易接口数据（清算列）:
        Map<String, Object> tradingVolumeData2 = doProfitTradingVolume(calendar.getTime(),"PFT004");
        if(tradingVolumeData2==null||tradingVolumeData2.size()==0){
            LOG.error("================月分润交易接口数据（清算列）拉取失败================");
            resultMap.put("resultCode","error");
            resultMap.put("msg","月分润交易接口数据（清算列）拉取异常");
            return resultMap;
        }
        LOG.info("================月分润交易接口数据（清算列）拉取完成================");



        List<TranCheckPlatForm> platForms = profitOrganTranMonthService.getAllPlatForm();
        List<TranCheckData> list=new ArrayList<>();

        for (TranCheckPlatForm platForm:platForms) {
            TranCheckData checkData=new TranCheckData();
            checkData.setId(idService.genId(TabId.TRANCHECK_DATA));
            checkData.setProfitMonth(tranMonth);
            checkData.setPlatformType(platForm.getPlatformType());
            checkData.setPlatformNum(platForm.getPlatformNum());
            checkData.setSearchTime(searchTime);
            checkData.setPlatformId(platForm.getId());
            switch (platForm.getTechnologyAddress()==null?"":platForm.getTechnologyAddress()){
                case "tranMonthProfit1":{
                    Object technologyAmt = tradingVolumeData1.get(platForm.getTechnologyAmt());
                    Object technologyFee = tradingVolumeData1.get(platForm.getTechnologyFee());
                    if(technologyAmt==null){
                        if (tradingVolumeData1.containsKey(platForm.getTechnologyAmt())){
                            checkData.setTechnologyAmt(BigDecimal.ZERO);
                        }else{
                            checkData.setTechnologyAmt(null);
                        }
                    }else{
                        checkData.setTechnologyAmt(new BigDecimal(technologyAmt.toString()));
                    }
                    if(technologyFee==null){
                        if (tradingVolumeData1.containsKey(platForm.getTechnologyFee())){
                            checkData.setTechnologyFee(BigDecimal.ZERO);
                        }else {
                            checkData.setTechnologyFee(null);
                        }
                    }else{
                        checkData.setTechnologyFee(new BigDecimal(technologyFee.toString()));
                    }
                    break;
                }
                case "tranAmt":{
                    Object technologyAmt = tranAmt.get(platForm.getTechnologyAmt());
                    Object technologyFee = tranAmt.get(platForm.getTechnologyFee());
                    if(technologyAmt==null){
                        if (tranAmt.containsKey(platForm.getTechnologyAmt())){
                            checkData.setTechnologyAmt(BigDecimal.ZERO);
                        }else{
                            checkData.setTechnologyAmt(null);
                        }
                    }else{
                        checkData.setTechnologyAmt(new BigDecimal(technologyAmt.toString()));
                    }
                    if(technologyFee==null){
                        if (tranAmt.containsKey(platForm.getTechnologyFee())){
                            checkData.setTechnologyFee(BigDecimal.ONE);
                        }else {
                            checkData.setTechnologyFee(null);
                        }
                    }else{
                        checkData.setTechnologyFee(new BigDecimal(technologyFee.toString()));
                    }
                    break;
                }
                case "tranBkt":{
                    if(tranBkt!=null){
                        checkData.setTechnologyAmt(tranBkt.getInTransAmt()==null?BigDecimal.ZERO:tranBkt.getInTransAmt());
                        checkData.setTechnologyFee(tranBkt.getTransFee()==null?BigDecimal.ZERO:tranBkt.getTransFee());
                    }
                    break;
                }
                case "tranNewMonth":{
                    Object technologyAmt = newMonthData.get(platForm.getTechnologyAmt());
                    Object technologyFee = newMonthData.get(platForm.getTechnologyFee());
                    if(technologyAmt==null){
                        if(newMonthData.containsKey(platForm.getTechnologyAmt())){
                            checkData.setTechnologyAmt(BigDecimal.ZERO);
                        }else{
                            checkData.setTechnologyAmt(null);
                        }
                    }else{
                        checkData.setTechnologyAmt(new BigDecimal(technologyAmt.toString()));
                    }
                    if(technologyFee==null){
                        if (newMonthData.containsKey(platForm.getTechnologyFee())){
                            checkData.setTechnologyFee(BigDecimal.ZERO);
                        }else {
                            checkData.setTechnologyFee(null);
                        }
                    }else{
                        checkData.setTechnologyFee(new BigDecimal(technologyFee.toString()));
                    }
                    break;
                }
                case "tranSettle":{
                    Object technologyAmt = settleData.get(platForm.getTechnologyAmt());
                    Object technologyFee = settleData.get(platForm.getTechnologyFee());
                    if(technologyAmt==null){
                        if (settleData.containsKey(platForm.getTechnologyAmt())){
                            checkData.setTechnologyAmt(BigDecimal.ZERO);
                        }else {
                            checkData.setTechnologyAmt(null);
                        }
                    }else{
                        checkData.setTechnologyAmt(new BigDecimal(technologyAmt.toString()));
                    }
                    if(technologyFee==null){
                        if (settleData.containsKey(platForm.getTechnologyFee())){
                            checkData.setTechnologyFee(BigDecimal.ZERO);
                        }else{
                            checkData.setTechnologyFee(null);
                        }
                    }else{
                        checkData.setTechnologyFee(new BigDecimal(technologyFee.toString()));
                    }
                    break;
                }
                case "ruihuabao":{
                    Object technologyAmt = ruiHuaBaoData.get(platForm.getTechnologyAmt());
                    Object technologyFee = ruiHuaBaoData.get(platForm.getTechnologyFee());
                    if(technologyAmt==null){
                        if (ruiHuaBaoData.containsKey(platForm.getTechnologyAmt())){
                            checkData.setTechnologyAmt(BigDecimal.ZERO);
                        }else {
                            checkData.setTechnologyAmt(null);
                        }
                    }else{
                        checkData.setTechnologyAmt(new BigDecimal(technologyAmt.toString()));
                    }
                    if(technologyFee==null){
                        if (ruiHuaBaoData.containsKey(platForm.getTechnologyFee())){
                            checkData.setTechnologyFee(BigDecimal.ZERO);
                        }else {
                            checkData.setTechnologyFee(null);
                        }
                    }else{
                        checkData.setTechnologyFee(new BigDecimal(technologyFee.toString()));
                    }
                    break;
                }
                case "ruitongbaoplus":{
                    Object technologyAmt = ruiTongBaoPlusData.get(platForm.getTechnologyAmt());
                    Object technologyFee = ruiTongBaoPlusData.get(platForm.getTechnologyFee());
                    if(technologyAmt==null){
                        if (ruiTongBaoPlusData.containsKey(platForm.getTechnologyAmt())){
                            checkData.setTechnologyAmt(BigDecimal.ZERO);
                        }else {
                            checkData.setTechnologyAmt(null);
                        }
                    }else{
                        checkData.setTechnologyAmt(new BigDecimal(technologyAmt.toString()));
                    }
                    if(technologyFee==null){
                        if (ruiTongBaoPlusData.containsKey(platForm.getTechnologyFee())){
                            checkData.setTechnologyFee(BigDecimal.ZERO);
                        }else {
                            checkData.setTechnologyFee(null);
                        }
                    }else{
                        checkData.setTechnologyFee(new BigDecimal(technologyFee.toString()));
                    }
                    break;
                }
                case "ruiDabao":{
                    Object technologyAmt = ruiDabaoData.get(platForm.getTechnologyAmt());
                    Object technologyFee = ruiDabaoData.get(platForm.getTechnologyFee());
                    if(technologyAmt==null){
                        if (ruiDabaoData.containsKey(platForm.getTechnologyAmt())){
                            checkData.setTechnologyAmt(BigDecimal.ZERO);
                        }else {
                            checkData.setTechnologyAmt(null);
                        }
                    }else{
                        checkData.setTechnologyAmt(new BigDecimal(technologyAmt.toString()));
                    }
                    if(technologyFee==null){
                        if (ruiDabaoData.containsKey(platForm.getTechnologyFee())){
                            checkData.setTechnologyFee(BigDecimal.ZERO);
                        }else {
                            checkData.setTechnologyFee(null);
                        }
                    }else{
                        checkData.setTechnologyFee(new BigDecimal(technologyFee.toString()));
                    }
                    break;
                }
                default:{
                    checkData.setTechnologyAmt(null);
                    checkData.setTechnologyFee(null);
                }
            }
            switch (platForm.getClearAddress()==null?"":platForm.getClearAddress()){
                case "tranSettle":{
                    Object clearAmt = settleData.get(platForm.getClearAmt());
                    Object clearFee = settleData.get(platForm.getClearFee());
                    if(clearAmt==null){
                        if (settleData.containsKey(platForm.getClearAmt())){
                            checkData.setClearAmt(BigDecimal.ZERO);
                        }else {
                            checkData.setClearAmt(null);
                        }
                    }else{
                        checkData.setClearAmt(new BigDecimal(clearAmt.toString()));
                    }
                    if(clearFee==null){
                        if (settleData.containsKey(platForm.getClearFee())){
                            checkData.setClearFee(BigDecimal.ZERO);
                        }else {
                            checkData.setClearFee(null);
                        }
                    }else{
                        checkData.setClearFee(new BigDecimal(clearFee.toString()));
                    }
                    break;
                }
                case "tranMonthProfit2":{
                    Object clearAmt = tradingVolumeData2.get(platForm.getClearAmt());
                    Object clearFee = tradingVolumeData2.get(platForm.getClearFee());
                    if(clearAmt==null){
                        if (tradingVolumeData2.containsKey(platForm.getClearAmt())){
                            checkData.setClearAmt(BigDecimal.ZERO);
                        }else {
                            checkData.setClearAmt(null);
                        }
                    }else{
                        checkData.setClearAmt(new BigDecimal(clearAmt.toString()));
                    }
                    if(clearFee==null){
                        if (tradingVolumeData2.containsKey(platForm.getClearFee())){
                            checkData.setClearFee(BigDecimal.ZERO);
                        }else {
                            checkData.setClearFee(null);
                        }
                    }else{
                        checkData.setClearFee(new BigDecimal(clearFee.toString()));
                    }
                    break;
                }
                default:{
                    checkData.setClearAmt(null);
                    checkData.setClearFee(null);
                }
            }
            list.add(checkData);
        }
        if (list.size()==platForms.size()){
            try{
                profitOrganTranMonthService.synchronizeTranCheckData(list);
                LOG.info("数据同步成功!");
                resultMap.put("resultCode","success");
                resultMap.put("msg","数据同步成功！");
            }catch (Exception e){
                LOG.info("数据同步失败,msg:"+e.getMessage());
                e.printStackTrace();
                resultMap.put("resultCode","error");
                resultMap.put("msg",e.getMessage());
            }
        }else{
            LOG.info("数据同步失败!");
            resultMap.put("resultCode","error");
            resultMap.put("msg","数据生成异常");
        }
        return resultMap;
    }
    public Map<String,Object> doSettleTranAmount(Date tranMon){
        String tranMonth= new SimpleDateFormat("yyyyMM").format(tranMon);
        Map<String, String> param=new HashMap<>();
        param.put("tranType","22");
        param.put("tranMon",tranMonth);
        String resultStr = profitOrganTranMonthService.doSettleTranAmount(param);
        LOG.info("=========清结算返回数据："+resultStr);
        JSONObject jsonObject = JSONObject.parseObject(resultStr);
        if(jsonObject==null){
            LOG.info("清算接口访问异常,resultStr=====>"+resultStr+"\n\n");
            return null;
        }else if (!"00".equals(jsonObject.get("code"))){
            LOG.info("清算接口访问异常,resultStr=====>"+resultStr+"\n\n");
            return null;
        }
        Map<String, Object> result = (Map<String, Object>) jsonObject.get("info");
        return result;
    }
    public Map<String,Object> doProfitNewMonth(Date tranMon){
        String tranMonth= new SimpleDateFormat("yyyyMM").format(tranMon);
        Map<String, String> param=new HashMap<>();
        param.put("pageNumber","1");
        param.put("pageSize","2");
        param.put("frMonth",tranMonth);
        String resultStr = profitOrganTranMonthService.doProfitNewMonth(param);
        JSONObject jsonObject = JSONObject.parseObject(resultStr);
        return jsonObject;
    }
    public Map<String,Object> doProfitTradingVolume(Date tranMon,String tranCode){
        String tranMonth= new SimpleDateFormat("yyyyMM").format(tranMon);
        AgentResult agentResult = posProfitDataService.getTradingVolume(tranMonth,tranCode);
        Map<String, Object> agentResultData;
        try{
            if(!"".equals(agentResult.getData())){
                agentResultData = (Map<String, Object>) agentResult.getData();
            }else{
                LOG.info("月分润交易接口访问异常:"+agentResult.getMsg());
                agentResultData=null;
            }
        }catch (Exception e){
            LOG.info("月分润交易接口访问异常:"+e.getMessage());
            throw new RuntimeException("月分润交易接口访问异常！");
        }
        return agentResultData;
    }

    public Map<String,Object> getRuiHuaBaoProfitAmtAndProfitFee(Date tranMon,String brandCode) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("application","queryTradeInfo");
            map.put("brandCode",brandCode);
            map.put("tradeMonth",new SimpleDateFormat("yyyyMM").format(tranMon));
            String json = JsonUtil.objectToJson(map);
            String reqParamEncrypt = Des3Util.Encrypt(json, rhb3desKey, rhb3desIv.getBytes());
            LOG.info("瑞花宝交易量手续费查询参数：{}",json);
            LOG.info("瑞花宝交易量手续费查询参数加密：{}",reqParamEncrypt);
            String httpResult = HttpClientUtil.sendHttpPost(rhbReqUrl, reqParamEncrypt);
            String reqParamDecrypt = Des3Util.Decrypt(httpResult, rhb3desKey, rhb3desIv.getBytes());
            LOG.info("瑞花宝交易量手续费查询返回参数：{}",reqParamDecrypt);
            JSONObject respXMLObj = JSONObject.parseObject(reqParamDecrypt);
            if (respXMLObj.getString("MSG_CODE").equals("0000")){
                return respXMLObj.getJSONObject("result");
            }else {
                String msg=(String)respXMLObj.get("MSG_TEXT");
                LOG.info("瑞花宝数据查询失败---"+msg);
                throw new RuntimeException("瑞花宝交易量手续费查询失败--"+msg);
            }
        } catch (Exception e) {
            LOG.info("通知失败:{}",e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("瑞花宝交易量手续费查询失败！");
        }
    }
    public Map<String,Object> getRuiDaBaoProfitAmtAndProfitFee(Date tranMon) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("month",new SimpleDateFormat("yyyyMM").format(tranMon));
            String json = JsonUtil.objectToJson(map);
            LOG.info("瑞大宝交易量手续费查询参数：{}",json);

            StringBuffer urlBuffer=new StringBuffer();
            urlBuffer.append(AppConfig.getProperty("ruidabao.tranAmount"));
            urlBuffer.append("?");
            Set<String> keys = map.keySet();
            for (String key:keys){
                String value = map.get(key);
                urlBuffer.append(key+"="+value+"&");
            }
            urlBuffer.deleteCharAt(urlBuffer.length()-1);
            String res = HttpClientUtil.doGet(urlBuffer.toString());

            LOG.info("瑞大宝交易量手续费查询返回参数：{}",res);
            JSONObject respXMLObj = JSONObject.parseObject(res);
            if (respXMLObj.getString("code").equals("0000")){
                return respXMLObj.getJSONObject("info");
            }else {
                String msg=(String)respXMLObj.get("msg");
                LOG.info("瑞大宝数据查询失败---"+msg);
                throw new RuntimeException("瑞大宝交易量手续费查询失败--"+msg);
            }
        } catch (Exception e) {
            LOG.info("通知失败:{}",e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("瑞大宝交易量手续费查询失败！");
        }
    }
    public Map<String,Object> getTranAmtByMonth(Date tranMon){

        String url = AppConfig.getProperty("old_brand_ss_url");
        String tranMonth= new SimpleDateFormat("yyyyMM").format(tranMon);
        JSONObject object=new JSONObject();
        object.put("tradeTime",tranMonth);
        String res = HttpClientUtil.doPostJson(url,object.toJSONString());
        LOG.info("手刷老品牌分润数据查询接口查询返回参数：{}",res);
        JSONObject result = JSONObject.parseObject(res);
        String data = (String) result.get("data");
        LOG.info("手刷老品牌分润数据查询接口查询返回数据data:"+data);
        List<Map> jsonObj = JSONArray.parseArray(data, Map.class);
        BigDecimal profitAmt=BigDecimal.ZERO;
        BigDecimal profitFee=BigDecimal.ZERO;
        for (Map<String,Object> map:jsonObj){
            BigDecimal amount = new BigDecimal(map.get("amount")==null?"0":map.get("amount").toString());
            BigDecimal fee = new BigDecimal(map.get("fee")==null?"0":map.get("fee").toString());
            profitAmt=profitAmt.add(amount);
            profitFee=profitFee.add(fee);
        }

        Map<String, Object> tranAmt = new HashMap<>();
        tranAmt.put("MPOS_TRAN_AMT",profitAmt);
        tranAmt.put("TRANS_FEE",profitFee);
        return tranAmt;
    }
    public TransProfitDetail getTranBktByProfitMonth(String profitMonth){
        TransProfitDetail transProfitDetail=new TransProfitDetail();
        transProfitDetail.setBusNum("3000111423");
        transProfitDetail.setProfitDate(profitMonth);
        List<TransProfitDetail> transProfitDetailList = transProfitDetailServiceImpl.getTransProfitDetailByBusNum(transProfitDetail);
        if (transProfitDetailList.size()==1){
            transProfitDetail=transProfitDetailList.get(0);
        }else{
            transProfitDetail=null;
        }
        return transProfitDetail;
    }
}
