package com.ryx.account.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.account.dao.AuthLoginTokenMapper;
import com.ryx.account.dao.AuthSysCodeMapper;
import com.ryx.account.pojo.AuthLoginToken;
import com.ryx.account.pojo.AuthLoginTokenExample;
import com.ryx.account.pojo.AuthSysCode;
import com.ryx.account.pojo.AuthSysCodeExample;
import com.ryx.account.service.AccountAuthService;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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

    private int authTime = 300;     //默认五分钟
    private int tokenTime = 300*6;  //默认半小时

    @Autowired
    private IdService idService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private AuthSysCodeMapper authSysCodeMapper;
    @Autowired
    private AuthLoginTokenMapper authLoginTokenMapper;


    @PostConstruct
    public void init(){
        Dict authTimeDict = dictOptionsService.findDictByName(DictGroup.AUTH.name(), DictGroup.AUTH_TIME.name(), DictGroup.AUTH_CODE.name());
        if(authTimeDict!=null){
            authTime = Integer.getInteger(authTimeDict.getdItemvalue());
        }
        Dict tokenTimeDict = dictOptionsService.findDictByName(DictGroup.AUTH.name(),DictGroup.AUTH_TIME.name(),DictGroup.TOKEN_CODE.name());
        if(tokenTimeDict!=null){
            tokenTime = Integer.getInteger(tokenTimeDict.getdItemvalue());
        }
    }

    private String getCode(){
       return UUID.randomUUID().toString().replace("-","");
    }


    private void commonVerify(String platformType)throws MessageException{
        if(StringUtils.isBlank(platformType)){
            throw new MessageException("缺少系统编码");
        }
        String contentByValue = PlatformType.getContentByValue(platformType);
        if(StringUtils.isBlank(contentByValue)){
            throw new MessageException("未知的系统编码");
        }
    }


    /**
     * 获取授权码
     * @param platformType  平台
     * @param serverIp  请求ip
     * @throws MessageException
     */
    @Override
    public Map<String,Object> getAuthCode(String platformType, String serverIp)throws MessageException{

        commonVerify(platformType);

        AuthSysCodeExample authSysCodeExample = new AuthSysCodeExample();
        AuthSysCodeExample.Criteria criteria = authSysCodeExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status.toString());
        criteria.andPlatformTypeEqualTo(platformType);
        criteria.andAuthCodeEndTimeGreaterThan(new Date());
        List<AuthSysCode> authSysCodes = authSysCodeMapper.selectByExample(authSysCodeExample);
        if(authSysCodes.size()!=0){
            throw new MessageException("授权码尚未失效，请勿重复请求");
        }

        AuthSysCode authSysCode = new AuthSysCode();
        authSysCode.setId(idService.genId(TabId.AUTH_SYS_CODE));
        authSysCode.setPlatformType(platformType);
        Date date = new Date();
        authSysCode.setAuthCodeBeginTime(date);
        authSysCode.setAuthCodeEndTime(DateUtil.dateAddSecond(date,authTime));
        authSysCode.setAuthCode(getCode());
        authSysCode.setServerIp(serverIp);
        authSysCode.setStatus(Status.STATUS_1.status.toString());
        authSysCodeMapper.insertSelective(authSysCode);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("authCode",authSysCode.getAuthCode());
        resultMap.put("authCodeBeginTime",DateUtil.format(authSysCode.getAuthCodeBeginTime()));
        resultMap.put("authCodeEndTime",DateUtil.format(authSysCode.getAuthCodeEndTime()));
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
    public Map<String,Object> refreshAuthCode(String authCode,String platformType,String serverIp)throws MessageException{

        commonVerify(platformType);
        if(StringUtils.isBlank(serverIp)){
            throw new MessageException("缺少请求ip地址");
        }
        AuthSysCode authSysCode = verifyAuthCode(platformType, authCode);
        if(!serverIp.equals(authSysCode.getServerIp())){
            throw new MessageException("请求ip与上次请求ip不一致");
        }
        authSysCode.setAuthCodeEndTime(DateUtil.dateAddSecond(new Date(),authTime));
        int i = authSysCodeMapper.updateByPrimaryKey(authSysCode);
        if(i!=1){
            throw new MessageException("授权码刷新失败");
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("authCode",authSysCode.getAuthCode());
        resultMap.put("authCodeBeginTime",DateUtil.format(authSysCode.getAuthCodeBeginTime()));
        resultMap.put("authCodeEndTime",DateUtil.format(authSysCode.getAuthCodeEndTime()));
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
            throw new MessageException("缺少授权码");
        }
        AuthSysCodeExample authSysCodeExample = new AuthSysCodeExample();
        AuthSysCodeExample.Criteria criteria = authSysCodeExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status.toString());
        criteria.andPlatformTypeEqualTo(platformType);
        criteria.andAuthCodeEqualTo(authCode);
        List<AuthSysCode> authSysCodes = authSysCodeMapper.selectByExample(authSysCodeExample);
        if(authSysCodes.size()!=1){
            throw new MessageException("授权码不存在");
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
            throw new MessageException("授权码已过期");
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
        public Map<String,Object> getTokenCode(String platformType,String authCode,String serverIp,String loginName,String passWord,List<Map<String,Object>> busInfos)throws MessageException{

        approveAuthCode(platformType, authCode);
        if(StringUtils.isBlank(loginName)){
            throw new MessageException("缺少登陆名");
        }
        if(StringUtils.isBlank(passWord)){
            throw new MessageException("缺少密码");
        }
        AuthLoginTokenExample authLoginTokenExample = new AuthLoginTokenExample();
        AuthLoginTokenExample.Criteria criteria = authLoginTokenExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status.toString());
        criteria.andPlatformTypeEqualTo(platformType);
        criteria.andAuthCodeEqualTo(authCode);
        criteria.andTokenEndTimeGreaterThan(new Date());
        List<AuthLoginToken> authLoginTokens = authLoginTokenMapper.selectByExample(authLoginTokenExample);
        if(authLoginTokens.size()!=0){
            throw new MessageException("令牌尚未失效，请勿重复请求");
        }

        AuthLoginToken authLoginToken = new AuthLoginToken();
        authLoginToken.setId(idService.genId(TabId.AUTH_LOGIN_TOKEN));
        authLoginToken.setPlatformType(platformType);
        authLoginToken.setAuthCode(authCode);
        authLoginToken.setLogName(loginName);
        authLoginToken.setPassWord(passWord);
        Date date = new Date();
        authLoginToken.setTokenBeginTime(date);
        authLoginToken.setTokenEndTime(DateUtil.dateAddSecond(date,tokenTime));
        String token = getCode();
        authLoginToken.setToken(token);
        authLoginToken.setRequestId(serverIp);
        authLoginToken.setStatus(Status.STATUS_1.status.toString());
        String busInfoJson = JSONObject.toJSONString(busInfos);
        authLoginToken.setBusInfo(busInfoJson);
        authLoginTokenMapper.insertSelective(authLoginToken);

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("tokenCode",authLoginToken.getAuthCode());
        resultMap.put("tokenCodeBeginTime",DateUtil.format(authLoginToken.getTokenBeginTime()));
        resultMap.put("tokenCodeEndTime",DateUtil.format(authLoginToken.getTokenEndTime()));
        return resultMap;
    }





}
