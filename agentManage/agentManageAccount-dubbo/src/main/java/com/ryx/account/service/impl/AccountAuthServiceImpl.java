package com.ryx.account.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.account.dao.AuthLoginTokenMapper;
import com.ryx.account.dao.AuthSysCodeMapper;
import com.ryx.account.pojo.AuthLoginToken;
import com.ryx.account.pojo.AuthLoginTokenExample;
import com.ryx.account.pojo.AuthSysCode;
import com.ryx.account.pojo.AuthSysCodeExample;
import com.ryx.account.service.AccountAuthService;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import org.apache.http.auth.AUTH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2020/2/10 16:02
 * @Param
 * @return
 **/
@Service("accountAuthService")
public class AccountAuthServiceImpl implements AccountAuthService {

    @Autowired
    private IdService idService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private AuthSysCodeMapper authSysCodeMapper;
    @Autowired
    private AuthLoginTokenMapper authLoginTokenMapper;

    private int getAuthTime(){
        int authTime = 60*24*7;//默认一周  10080
        Dict authTimeDict = dictOptionsService.findDictByName(DictGroup.AUTH.name(), DictGroup.AUTH_TIME.name(), DictGroup.AUTH_CODE.name());
        if(authTimeDict!=null){
            authTime = Integer.parseInt(authTimeDict.getdItemvalue());
        }
        return authTime;
    }

    private int getTokenTime() {
        int tokenTime = 30;//默认半小时
        Dict tokenTimeDict = dictOptionsService.findDictByName(DictGroup.AUTH.name(), DictGroup.AUTH_TIME.name(), DictGroup.TOKEN_CODE.name());
        if (tokenTimeDict != null) {
            tokenTime = Integer.parseInt(tokenTimeDict.getdItemvalue());
        }
        return tokenTime;
    }

    private String getCode(){
       return UUID.randomUUID().toString().replace("-","");
    }


    private void commonVerify(String platformType)throws MessageException{
        if(StringUtils.isBlank(platformType)){
            throw new MessageException(AuthCode.LACK_SYSTEM_CODE.getContent());
        }
        String contentByValue = PlatformType.getContentByValue(platformType);
        if(StringUtils.isBlank(contentByValue)){
            throw new MessageException(AuthCode.UNKNOWN_SYSTEM_CODE.getContent());
        }
    }


    /**
     * 获取授权码
     * @param platformType  平台
     * @param serverIp  请求ip
     * @throws MessageException
     */
    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public Map<String,Object> getAuthCode(String platformType, String serverIp)throws MessageException{

        commonVerify(platformType);

        AuthSysCodeExample authSysCodeExample = new AuthSysCodeExample();
        AuthSysCodeExample.Criteria criteria = authSysCodeExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status.toString());
        criteria.andPlatformTypeEqualTo(platformType);
        criteria.andAuthCodeEndTimeGreaterThan(new Date());
        List<AuthSysCode> authSysCodes = authSysCodeMapper.selectByExample(authSysCodeExample);
        if(authSysCodes.size()==1){
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("authCode",authSysCodes.get(0).getAuthCode());
            resultMap.put("authCodeBeginTime",authSysCodes.get(0).getAuthCodeBeginTime().getTime());
            resultMap.put("authCodeEndTime",authSysCodes.get(0).getAuthCodeEndTime().getTime());
            return resultMap;
        }
        AuthSysCodeExample authSysCodeExample1 = new AuthSysCodeExample();
        AuthSysCodeExample.Criteria criteria1 = authSysCodeExample1.createCriteria();
        criteria1.andStatusEqualTo(Status.STATUS_1.status.toString());
        criteria1.andPlatformTypeEqualTo(platformType);
        List<AuthSysCode> authSysCodeList = authSysCodeMapper.selectByExample(authSysCodeExample1);
        for (AuthSysCode authSysCode : authSysCodeList) {
            authSysCode.setStatus(Status.STATUS_0.status.toString());
            int i = authSysCodeMapper.updateByPrimaryKeySelective(authSysCode);
            if(i!=1){
                throw new MessageException(AuthCode.AUTH_CODE_FAIL.getContent());
            }
        }
        AuthSysCode authSysCode = new AuthSysCode();
        authSysCode.setId(idService.genId(TabId.AUTH_SYS_CODE));
        authSysCode.setPlatformType(platformType);
        Date date = new Date();
        authSysCode.setAuthCodeBeginTime(date);
        authSysCode.setAuthCodeEndTime(DateUtil.dateAddMinute(date,getAuthTime()));
        authSysCode.setAuthCode(getCode());
        authSysCode.setServerIp(serverIp);
        authSysCode.setStatus(Status.STATUS_1.status.toString());
        authSysCodeMapper.insertSelective(authSysCode);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("authCode",authSysCode.getAuthCode());
        resultMap.put("authCodeBeginTime",authSysCode.getAuthCodeBeginTime().getTime());
        resultMap.put("authCodeEndTime",authSysCode.getAuthCodeEndTime().getTime());
        return resultMap;
     }


    /**
     * 授权码刷新
     * @param authCode  授权码
     * @param platformType  平台
     * @param serverIp  请求ip
     * @throws MessageException
     */
    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public Map<String,Object> refreshAuthCode(String authCode,String platformType,String serverIp)throws MessageException{

        commonVerify(platformType);
        if(StringUtils.isBlank(serverIp)){
            throw new MessageException(AuthCode.LACK_IP.getContent());
        }
        if(StringUtils.isBlank(authCode)){
            throw new MessageException(AuthCode.LACK_AUTH.getContent());
        }
        AuthSysCodeExample authSysCodeExample = new AuthSysCodeExample();
        AuthSysCodeExample.Criteria criteria = authSysCodeExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status.toString());
        criteria.andPlatformTypeEqualTo(platformType);
        List<AuthSysCode> authSysCodes = authSysCodeMapper.selectByExample(authSysCodeExample);
        for (AuthSysCode authSysCode : authSysCodes) {
            authSysCode.setStatus(Status.STATUS_0.status.toString());
            int i = authSysCodeMapper.updateByPrimaryKeySelective(authSysCode);
            if(i!=1){
                throw new MessageException("刷新失败");
            }
        }
        AuthSysCodeExample authSysCodeExample1 = new AuthSysCodeExample();
        AuthSysCodeExample.Criteria criteria1 = authSysCodeExample1.createCriteria();
        criteria1.andPlatformTypeEqualTo(platformType);
        criteria1.andAuthCodeEqualTo(authCode);
        List<AuthSysCode> authSysCodeList = authSysCodeMapper.selectByExample(authSysCodeExample1);
        if(authSysCodeList.size()!=1){
            throw new MessageException("刷新失败,存在多个授权码");
        }
        AuthSysCode authSysCode = authSysCodes.get(0);
//        if(!serverIp.equals(authSysCode.getServerIp())){
//            throw new MessageException("请求ip与上次请求ip不一致");
//        }
        authSysCode.setStatus(Status.STATUS_1.status.toString());
        authSysCode.setServerIp(serverIp);
        authSysCode.setAuthCodeEndTime(DateUtil.dateAddMinute(new Date(),getAuthTime()));
        int i = authSysCodeMapper.updateByPrimaryKey(authSysCode);
        if(i!=1){
            throw new MessageException("授权码刷新失败");
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("authCode",authSysCode.getAuthCode());
        resultMap.put("authCodeBeginTime",authSysCode.getAuthCodeBeginTime().getTime());
        resultMap.put("authCodeEndTime",authSysCode.getAuthCodeEndTime().getTime());
        return resultMap;

    }

    /**
     * 验证授权码
     * @param platformType 平台
     * @param authCode  授权码
     * @throws MessageException
     */
    private AuthSysCode verifyAuthCode(String platformType,String authCode)throws MessageException{
        if(StringUtils.isBlank(authCode)){
            throw new MessageException(AuthCode.LACK_AUTH.getContent());
        }
        AuthSysCodeExample authSysCodeExample = new AuthSysCodeExample();
        AuthSysCodeExample.Criteria criteria = authSysCodeExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status.toString());
        criteria.andPlatformTypeEqualTo(platformType);
        criteria.andAuthCodeEqualTo(authCode);
        List<AuthSysCode> authSysCodes = authSysCodeMapper.selectByExample(authSysCodeExample);
        if(authSysCodes.size()!=1){
            throw new MessageException(AuthCode.NO_AUTH.getContent());
        }
        return authSysCodes.get(0);
    }

    /**
     * 授权码认证
     * @param platformType  平台
     * @param authCode  授权码
     */
    @Override
    public void approveAuthCode(String platformType,String authCode)throws MessageException{
        AuthSysCode authSysCode = verifyAuthCode(platformType, authCode);
        if(authSysCode.getAuthCodeEndTime().getTime()<new Date().getTime()){
            throw new MessageException(AuthCode.EXPIRED.getContent());
        }
    }

    /**
     * 获取令牌
     * @param platformType 平台
     * @param authCode  授权码
     * @param serverIp  请求ip
     * @param loginName  登陆名
     * @param passWord   密码
     * @param busInfos   业务信息
     *                   业务平台 busPlatform
     *                   平台号   busNum
     *                   品牌号   brandNum
     *                   类型     busType
     * @throws MessageException
     */
    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public Map<String,Object> getTokenCode(String platformType,String authCode,String serverIp,String loginName,String passWord,List<Map<String,Object>> busInfos)throws MessageException{

        approveAuthCode(platformType, authCode);
        if(StringUtils.isBlank(loginName)){
            throw new MessageException(AuthCode.LACK_LOGIN_NAME.getContent());
        }
        if(StringUtils.isBlank(passWord)){
            throw new MessageException(AuthCode.LACK_PASS_WORD.getContent());
        }
        if(busInfos.size()==0){
            throw new MessageException(AuthCode.LACK_BUS_INFO.getContent());
        }
        AuthLoginTokenExample authLoginTokenExample = new AuthLoginTokenExample();
        AuthLoginTokenExample.Criteria criteria = authLoginTokenExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status.toString());
        criteria.andPlatformTypeEqualTo(platformType);
        criteria.andLogNameEqualTo(loginName);
        criteria.andTokenEndTimeGreaterThan(new Date());
        List<AuthLoginToken> authLoginTokens = authLoginTokenMapper.selectByExample(authLoginTokenExample);
        if(authLoginTokens.size()>1){
            throw new MessageException("令牌获取异常");
        }
        if(authLoginTokens.size()==1){
            AuthLoginToken authLoginToken = authLoginTokens.get(0);
            authLoginToken.setTokenEndTime(DateUtil.dateAddMinute(new Date(),getTokenTime()));
            int i = authLoginTokenMapper.updateByPrimaryKeySelective(authLoginToken);
            if(i!=1){
                throw new MessageException("令牌获取异常!");
            }
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("tokenCode",authLoginToken.getToken());
            resultMap.put("tokenCodeBeginTime",authLoginToken.getTokenBeginTime().getTime());
            resultMap.put("tokenCodeEndTime",authLoginToken.getTokenEndTime().getTime());
            return resultMap;
        }
        AuthLoginTokenExample authLoginTokenExample1 = new AuthLoginTokenExample();
        AuthLoginTokenExample.Criteria criteria1 = authLoginTokenExample1.createCriteria();
        criteria1.andStatusEqualTo(Status.STATUS_1.status.toString());
        criteria1.andPlatformTypeEqualTo(platformType);
        criteria1.andLogNameEqualTo(loginName);
        List<AuthLoginToken> authLoginTokenList = authLoginTokenMapper.selectByExample(authLoginTokenExample1);
        for (AuthLoginToken authLoginToken : authLoginTokenList) {
            authLoginToken.setStatus(Status.STATUS_0.status.toString());
            int i = authLoginTokenMapper.updateByPrimaryKeySelective(authLoginToken);
            if(i!=1){
                throw new MessageException("令牌获取异常!");
            }
        }

        AuthLoginToken authLoginToken = new AuthLoginToken();
        authLoginToken.setId(idService.genId(TabId.AUTH_LOGIN_TOKEN));
        authLoginToken.setPlatformType(platformType);
        authLoginToken.setAuthCode(authCode);
        authLoginToken.setLogName(loginName);
        authLoginToken.setPassWord(passWord);
        Date date = new Date();
        authLoginToken.setTokenBeginTime(date);
        authLoginToken.setTokenEndTime(DateUtil.dateAddMinute(date,getTokenTime()));
        String token = getCode();
        authLoginToken.setToken(token);
        authLoginToken.setRequestId(serverIp);
        authLoginToken.setStatus(Status.STATUS_1.status.toString());
        String busInfoJson = JSONObject.toJSONString(busInfos);
        authLoginToken.setBusInfo(busInfoJson);
        authLoginTokenMapper.insertSelective(authLoginToken);

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("tokenCode",authLoginToken.getToken());
        resultMap.put("tokenCodeBeginTime",authLoginToken.getTokenBeginTime().getTime());
        resultMap.put("tokenCodeEndTime",authLoginToken.getTokenEndTime().getTime());
        return resultMap;
    }



    /**
     * 令牌刷新
     * @param tokenCode
     * @param platformType
     * @param serverIp
     * @throws MessageException
     */
    @Override
    public Map<String,Object> refreshTokenCode(String tokenCode,String platformType,String serverIp)throws MessageException{

        commonVerify(platformType);
        if(StringUtils.isBlank(serverIp)){
            throw new MessageException(AuthCode.LACK_IP.getContent());
        }
        AuthLoginToken authLoginToken = tokenVerity(tokenCode, platformType);
//        if(!serverIp.equals(authLoginToken.getRequestId())){
//            throw new MessageException("请求ip与上次请求ip不一致");
//        }
        authLoginToken.setRequestId(serverIp);
        authLoginToken.setTokenEndTime(DateUtil.dateAddMinute(new Date(),getTokenTime()));
        int i = authLoginTokenMapper.updateByPrimaryKey(authLoginToken);
        if(i!=1){
            throw new MessageException("令牌刷新失败");
        }

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("tokenCode",authLoginToken.getAuthCode());
        resultMap.put("tokenCodeBeginTime",DateUtil.format(authLoginToken.getTokenBeginTime()));
        resultMap.put("tokenCodeEndTime",DateUtil.format(authLoginToken.getTokenEndTime()));
        return resultMap;
    }

    private AuthLoginToken tokenVerity(String tokenCode,String platformType)throws MessageException{
        if(StringUtils.isBlank(tokenCode)){
            throw new MessageException(AuthCode.LACK_TOKEN.getContent());
        }
        AuthLoginTokenExample authLoginTokenExample = new AuthLoginTokenExample();
        AuthLoginTokenExample.Criteria criteria = authLoginTokenExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status.toString());
        criteria.andPlatformTypeEqualTo(platformType);
        criteria.andTokenEqualTo(tokenCode);
        List<AuthLoginToken> authLoginTokens = authLoginTokenMapper.selectByExample(authLoginTokenExample);
        if(authLoginTokens.size()!=1){
            throw new MessageException(AuthCode.NO_TOKEN.getContent());
        }
        AuthLoginToken authLoginToken = authLoginTokens.get(0);
        return authLoginToken;
    }

    /**
     * 令牌验证
     * @param platformType
     * @param tokenCode
     * @throws MessageException
     */
    @Override
    public Map<String,Object> approveTokenCode(String platformType,String tokenCode)throws MessageException{

        AuthLoginToken authLoginToken = tokenVerity(tokenCode, platformType);
        if(authLoginToken.getTokenEndTime().getTime()<new Date().getTime()){
            throw new MessageException(AuthCode.TOKEN_PAST.getContent());
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("loginName",authLoginToken.getLogName());
        List<Map<String,Object>> resultList = JSONObject.parseObject(authLoginToken.getBusInfo(),List.class);
        resultMap.put("busInfo",resultList);
        return resultMap;
    }


}
