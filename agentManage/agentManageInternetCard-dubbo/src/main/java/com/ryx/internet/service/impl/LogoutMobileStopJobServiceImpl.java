package com.ryx.internet.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.MailUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.internet.dao.InternetLogoutDetailMapper;
import com.ryx.internet.dao.OInternetCardMapper;
import com.ryx.internet.pojo.InternetLogoutDetail;
import com.ryx.internet.pojo.InternetLogoutDetailExample;
import com.ryx.internet.pojo.OInternetCard;
import com.ryx.internet.service.LogoutMobileStopJobService;
import com.ryx.internet.service.impl.api.ChinaMobileForJYHttpReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/***
 * 揭阳移动通知移动关停Job
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/12/9 16:12
 * @Param
 * @return
 **/
@Service("logoutMobileStopJobService")
public class LogoutMobileStopJobServiceImpl implements LogoutMobileStopJobService {

    private static Logger log = LoggerFactory.getLogger(LogoutMobileStopJobServiceImpl.class);
    @Autowired
    private OInternetCardMapper internetCardMapper;
    @Autowired
    private InternetLogoutDetailMapper internetLogoutDetailMapper;
    @Autowired
    private RedisService redisService;


    @Override
    public void logoutMobileStopJob(){

        log.info("logoutMobileStopJob申请注销明细通知移动关停开始");
        String retIdentifier = "";
        try {
            retIdentifier = redisService.lockWithTimeout(RedisCachKey.LOGOUT_MOBILE_STOP_JOB.code, RedisService.ACQUIRE_TIME_OUT, RedisService.TIME_OUT);
            if (StringUtils.isBlank(retIdentifier)) {
                log.info("申请注销明细通知移动关停重复执行");
                AppConfig.sendEmails("申请注销明细通知移动关停，定时任务重复执行", "申请注销明细通知移动出现异常,logoutMobileStopJob方法");
                return;
            }
            InternetLogoutDetailExample internetLogoutDetailExample = new InternetLogoutDetailExample();
            InternetLogoutDetailExample.Criteria criteria = internetLogoutDetailExample.createCriteria();
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            criteria.andLogoutStatusEqualTo(InternetLogoutStatus.TJCLZ.getValue());// 注销中 查询需要通知移动的记录
            criteria.andIssuerEqualTo(Issuerstatus.JY_MOBILE.getValue());// 揭阳移动
            internetLogoutDetailExample.setOrderByClause(" c_time asc ");
            internetLogoutDetailExample.setPage(new Page(0,3));// 揭阳移动访问次数限制
            List<InternetLogoutDetail> internetLogoutDetails = internetLogoutDetailMapper.selectByExample(internetLogoutDetailExample);
            for (InternetLogoutDetail internetLogoutDetail : internetLogoutDetails) {
                try {
                    if(StringUtils.isBlank(internetLogoutDetail.getInternetCardNum())){
                        log.info("logoutMobileStopJob申请注销明细通知移动，缺少物联网卡号，iccid：{}",internetLogoutDetail.getIccidNum());
                        AppConfig.sendEmails("logoutMobileStopJob申请注销明细通知移动，缺少物联网卡号，iccid："+internetLogoutDetail.getIccidNum(), "申请注销明细通知移动出现异常,logoutMobileStopJob方法");
                        continue;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }
                    String mobileResult = ChinaMobileForJYHttpReq.msisdnSwitch(internetLogoutDetail.getInternetCardNum(), JyMobileOptType.STOP.getValue());
                    OInternetCard oInternetCard = internetCardMapper.selectByPrimaryKey(internetLogoutDetail.getIccidNum());
                    if(StringUtils.isBlank(mobileResult)){
                        log.info("logoutMobileStopJob申请注销明细通知移动，mobileResult：{}",mobileResult);
                        continue;
//                        internetLogoutDetail.setLogoutStatus(InternetLogoutStatus.TJSB.getValue());
//                        internetLogoutDetail.setFailCause("请求异常,结果未知");
//                        internetLogoutDetail.setuTime(new Date());
//                        int i = internetLogoutDetailMapper.updateByPrimaryKey(internetLogoutDetail);
//                        if(i!=1){
//                            AppConfig.sendEmails("logoutMobileStopJob申请注销明细通知移动，更新异常1，iccid："+internetLogoutDetail.getIccidNum(), "申请注销明细通知移动出现异常,logoutMobileStopJob方法");
//                            continue;
//                        }
//                        oInternetCard.setRenewStatus(InternetRenewStatus.WXF.getValue());
//                        oInternetCard.setuTime(new Date());
//                        int k = internetCardMapper.updateByPrimaryKeySelective(oInternetCard);
//                        if(k!=1){
//                            AppConfig.sendEmails("logoutMobileStopJob申请注销明细通知移动，更新异常2，iccid："+internetLogoutDetail.getIccidNum(), "申请注销明细通知移动出现异常,logoutMobileStopJob方法");
//                            continue;
//                        }
                    }
                    JSONObject jsonObj = JSONObject.parseObject(mobileResult);
                    String code = jsonObj.getString("code");
                    // 所有非0返回结果码均表示请求处理失败
                    if(!code.equals("0")){
                        String error = jsonObj.getString("error");
                        if(StringUtils.isNotBlank(error) && error.contains("请降低服务访问的频率")){
                            continue;
                        }
                        log.info("logoutMobileStopJob申请注销明细通知移动,code!=0,error:{}",jsonObj.getString("error"));
                        internetLogoutDetail.setLogoutStatus(InternetLogoutStatus.TJSB.getValue());// 移动关停失败 --> 注销失败
                        internetLogoutDetail.setFailCause(error);
                        internetLogoutDetail.setuTime(new Date());
                        if(error.equals("该号码已经是停机")){
                            internetLogoutDetail.setLogoutStatus(InternetLogoutStatus.DZX.getValue());// 已经是停机状态 --> 已注销
                        }
                        int i = internetLogoutDetailMapper.updateByPrimaryKey(internetLogoutDetail);
                        if(i!=1){
                            AppConfig.sendEmails("logoutMobileStopJob申请注销明细通知移动，更新异常3，iccid："+internetLogoutDetail.getIccidNum(), "申请注销明细通知移动出现异常,logoutMobileStopJob方法");
                            continue;
                        }
                        oInternetCard.setRenewStatus(InternetRenewStatus.YZX.getValue());// 通过审批，无论是否成功，卡续费状态是 --> 已注销
                        if(error.equals("该号码已经是停机")) {
                            oInternetCard.setRenewStatus(InternetRenewStatus.YZX.getValue());
                            oInternetCard.setRenew(Status.STATUS_0.status);
                            oInternetCard.setInternetCardStatus(InternetCardStatus.LOGOUT.getValue());// 注销成功 更改卡状态 注销
                        }
                        oInternetCard.setuTime(new Date());
                        int k = internetCardMapper.updateByPrimaryKeySelective(oInternetCard);
                        if(k!=1){
                            AppConfig.sendEmails("logoutMobileStopJob申请注销明细通知移动，更新异常4，iccid："+internetLogoutDetail.getIccidNum(), "申请注销明细通知移动出现异常,logoutMobileStopJob方法");
                            continue;
                        }
                    }else { // 处理成功
                        JSONObject jsonData = JSONObject.parseObject(jsonObj.getString("data"));
                        String dataCode = jsonData.getString("code");
                        if(dataCode.equals("0")){ // 处理成功
                            internetLogoutDetail.setLogoutStatus(InternetLogoutStatus.DZX.getValue());// 明细注销状态是 --> 已注销
                            oInternetCard.setRenewStatus(InternetRenewStatus.YZX.getValue());// 通过审批，无论是否成功，卡续费状态是 --> 已注销
                            oInternetCard.setRenew(Status.STATUS_0.status);// 是否续费 0 否 1 是
                            oInternetCard.setInternetCardStatus(InternetCardStatus.LOGOUT.getValue());// 注销成功 更改卡状态 注销
                        }else{ // 处理失败
                            internetLogoutDetail.setLogoutStatus(InternetLogoutStatus.TJSB.getValue());
                            oInternetCard.setRenewStatus(InternetRenewStatus.YZX.getValue());// 通过审批，无论是否成功，卡续费状态是 --> 已注销
                            oInternetCard.setRenew(Status.STATUS_0.status);// 是否续费 0 否 1 是
                        }
                        if(StringUtils.isNotBlank(jsonData.getString("orderNo")))
                        internetLogoutDetail.setMobileOrderNo(jsonData.getString("orderNo"));
                        if(StringUtils.isNotBlank(jsonData.getString("error")))
                        internetLogoutDetail.setFailCause(jsonData.getString("error"));
                        internetLogoutDetail.setuTime(new Date());
                        int i = internetLogoutDetailMapper.updateByPrimaryKey(internetLogoutDetail);
                        if(i!=1){
                            AppConfig.sendEmails("logoutMobileStopJob申请注销明细通知移动，更新异常5，iccid："+internetLogoutDetail.getIccidNum(), "申请注销明细通知移动出现异常,logoutMobileStopJob方法");
                            continue;
                        }
                        oInternetCard.setuTime(new Date());
                        int k = internetCardMapper.updateByPrimaryKeySelective(oInternetCard);
                        if(k!=1){
                            AppConfig.sendEmails("logoutMobileStopJob申请注销明细通知移动，更新异常6，iccid："+internetLogoutDetail.getIccidNum(), "申请注销明细通知移动出现异常,logoutMobileStopJob方法");
                            continue;
                        }
                    }
                } catch (Exception e) {
                    AppConfig.sendEmails(MailUtil.printStackTrace(e), "申请注销明细通知移动出现异常,logoutMobileStopJob方法");
                    e.printStackTrace();
                }
            }
        }finally {
            if(StringUtils.isNotBlank(retIdentifier)){
                redisService.releaseLock(RedisCachKey.LOGOUT_MOBILE_STOP_JOB.code, retIdentifier);
            }
        }
        log.info("logoutMobileStopJob申请注销明细通知移动关停结束");
    }

}
