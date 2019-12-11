package com.ryx.internet.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.InternetLogoutStatus;
import com.ryx.credit.common.enumc.InternetRenewStatus;
import com.ryx.credit.common.enumc.JyMobileOptType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.exception.MessageException;
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
 * 揭阳移动Job
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

    @Override
    public void logoutMobileStopJob(){

        log.info("logoutMobileStopJob申请注销明细通知移动关停开始");
        InternetLogoutDetailExample internetLogoutDetailExample = new InternetLogoutDetailExample();
        InternetLogoutDetailExample.Criteria criteria = internetLogoutDetailExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andLogoutStatusEqualTo(InternetLogoutStatus.TJCLZ.getValue());
        internetLogoutDetailExample.setOrderByClause(" c_time asc ");
        internetLogoutDetailExample.setPage(new Page(0,20));
        List<InternetLogoutDetail> internetLogoutDetails = internetLogoutDetailMapper.selectByExample(internetLogoutDetailExample);
        for (InternetLogoutDetail internetLogoutDetail : internetLogoutDetails) {
            try {
                if(StringUtils.isBlank(internetLogoutDetail.getInternetCardNum())){
                    log.info("logoutMobileStopJob申请注销明细通知移动，缺少物联网卡号，iccid：{}",internetLogoutDetail.getIccidNum());
                    AppConfig.sendEmails("logoutMobileStopJob申请注销明细通知移动，缺少物联网卡号，iccid："+internetLogoutDetail.getIccidNum(), "申请注销明细通知移动出现异常,logoutMobileStopJob方法");
                    continue;
                }
                String mobileResult = ChinaMobileForJYHttpReq.msisdnSwitch(internetLogoutDetail.getInternetCardNum(), JyMobileOptType.STOP.getValue());
                JSONObject jsonObj = JSONObject.parseObject(mobileResult);
                String code = jsonObj.getString("code");
                OInternetCard oInternetCard = internetCardMapper.selectByPrimaryKey(internetLogoutDetail.getIccidNum());
                //所有非0返回结果码均表示请求处理失败
                if(!code.equals("0")){
                    log.info("logoutMobileStopJob申请注销明细通知移动,code!=0,error:{}",jsonObj.getString("error"));
                    internetLogoutDetail.setLogoutStatus(InternetLogoutStatus.TJSB.getValue());
                    internetLogoutDetail.setFailCause(jsonObj.getString("error"));
                    internetLogoutDetail.setuTime(new Date());
                    int i = internetLogoutDetailMapper.updateByPrimaryKey(internetLogoutDetail);
                    if(i!=1){
                        AppConfig.sendEmails("logoutMobileStopJob申请注销明细通知移动，更新异常1，iccid："+internetLogoutDetail.getIccidNum(), "申请注销明细通知移动出现异常,logoutMobileStopJob方法");
                        continue;
                    }
                    oInternetCard.setRenewStatus(InternetRenewStatus.WXF.getValue());
                    oInternetCard.setuTime(new Date());
                    int k = internetCardMapper.updateByPrimaryKeySelective(oInternetCard);
                    if(k!=1){
                        AppConfig.sendEmails("logoutMobileStopJob申请注销明细通知移动，更新异常2，iccid："+internetLogoutDetail.getIccidNum(), "申请注销明细通知移动出现异常,logoutMobileStopJob方法");
                        continue;
                    }
                }
                JSONObject jsonData = JSONObject.parseObject(jsonObj.getString("data"));
                String dataCode = jsonData.getString("code");
                if(dataCode.equals("0")){
                    internetLogoutDetail.setLogoutStatus(InternetLogoutStatus.DZX.getValue());
                    oInternetCard.setRenewStatus(InternetRenewStatus.YZX.getValue());
                }else{
                    internetLogoutDetail.setLogoutStatus(InternetLogoutStatus.TJSB.getValue());
                    oInternetCard.setRenewStatus(InternetRenewStatus.WXF.getValue());
                }
                if(StringUtils.isNotBlank(jsonObj.getString("orderNo")))
                internetLogoutDetail.setMobileOrderNo(jsonObj.getString("orderNo"));
                if(StringUtils.isNotBlank(jsonObj.getString("error")))
                internetLogoutDetail.setFailCause(jsonObj.getString("error"));
                internetLogoutDetail.setuTime(new Date());
                int i = internetLogoutDetailMapper.updateByPrimaryKey(internetLogoutDetail);
                if(i!=1){
                    AppConfig.sendEmails("logoutMobileStopJob申请注销明细通知移动，更新异常3，iccid："+internetLogoutDetail.getIccidNum(), "申请注销明细通知移动出现异常,logoutMobileStopJob方法");
                    continue;
                }
                oInternetCard.setuTime(new Date());
                int k = internetCardMapper.updateByPrimaryKeySelective(oInternetCard);
                if(k!=1){
                    AppConfig.sendEmails("logoutMobileStopJob申请注销明细通知移动，更新异常4，iccid："+internetLogoutDetail.getIccidNum(), "申请注销明细通知移动出现异常,logoutMobileStopJob方法");
                    continue;
                }
            } catch (Exception e) {
                AppConfig.sendEmails(MailUtil.printStackTrace(e), "申请注销明细通知移动出现异常,logoutMobileStopJob方法");
                e.printStackTrace();
            }
        }
        log.info("logoutMobileStopJob申请注销明细通知移动关停结束");
    }

}
