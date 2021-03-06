package com.ryx.credit.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.commons.utils.BeanUtils;
import com.ryx.credit.commons.utils.DigestUtils;
import com.ryx.credit.commons.utils.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.CUserMapper;
import com.ryx.credit.dao.CUserRoleMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.CUserRole;
import com.ryx.credit.pojo.admin.vo.UserVo;
import com.ryx.credit.service.IBranchInnerConnectionService;
import com.ryx.credit.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import java.util.*;
import java.util.regex.Pattern;

/**
 *
 * CUser 表数据服务层接口实现类
 *
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<CUserMapper, CUser> implements IUserService {

    @Autowired
    private CUserMapper userMapper;
    @Autowired
    private CUserRoleMapper userRoleMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private IBranchInnerConnectionService branchInnerConnectionService;

    @Override
    public List<CUser> selectByLoginName(UserVo userVo) {
        CUser user = new CUser();
        user.setLoginName(userVo.getLoginName());
        EntityWrapper<CUser> wrapper = new EntityWrapper<CUser>(user);
        if (null != userVo.getId()) {
            wrapper.where("id != {0}", userVo.getId());
        }
        //List<CUser> userInfo = userMapper.selectListByLogin(userVo.getLoginName());
        //return userInfo;
        return this.selectList(wrapper);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertByVo(UserVo userVo, Map<String, String> innerParam) throws Exception{
        CUser user = BeanUtils.copy(userVo, CUser.class);
        user.setCreateTime(new Date());
        this.insert(user);
        List<UserVo>  listcuser = userMapper.selectListByLogin(userVo.getLoginName());
        UserVo userVoNew = listcuser.get(0);
        String[] roles = userVo.getRoleIds().split(",");
        CUserRole userRole = new CUserRole();
        for (String string : roles) {
            userRole.setUserId(userVoNew.getId());
            userRole.setRoleId(Long.valueOf(string));
            userRoleMapper.insert(userRole);
        }
        //建立内管账号
        if (null != innerParam.get("innerType") && "true".equals(innerParam.get("innerType"))) {
            try {
                //联动内管
                Map<String, Object> retMap = branchInnerConnectionService.buildInnerAccout(userVo, FastMap.fastMap("innerPwd",innerParam.get("innerPwd")));
                if (!("1".equals(retMap.get("code")))){
                    throw new Exception(null != retMap.get("msg") ? (String) retMap.get("msg") : "内管添加账号失败！");
                }
                //建立本地账号
                //branchInnerConnectionService.branchConnectionInner();
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    @Override
    public UserVo selectVoById(Long id) {
        return userMapper.selectUserVoById(id);
    }

    @Override
    public void updateByVo(UserVo userVo) {
        CUser user = BeanUtils.copy(userVo, CUser.class);
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        }
        this.updateById(user);
        
        Long id = userVo.getId();
        List<CUserRole> userRoles = userRoleMapper.selectByUserId(id);
        if (userRoles != null && !userRoles.isEmpty()) {
            for (CUserRole userRole : userRoles) {
                userRoleMapper.deleteById(userRole.getId());
            }
        }

        String[] roles = userVo.getRoleIds().split(",");
        CUserRole userRole = new CUserRole();
        for (String string : roles) {
            userRole.setUserId(id);
            userRole.setRoleId(Long.valueOf(string));
            userRoleMapper.insert(userRole);
        }
    }

    @Override
    public void updatePwdByUserId(Long userId, String md5Hex) {
        CUser user = new CUser();
        user.setId(userId);
        user.setPassword(md5Hex);
        this.updateById(user);
    }

    @Override
    public Object selectDataGrid(PageInfo pageInfo) {
        Page<Map<String, Object>> page = new Page<>(pageInfo.getNowpage(), pageInfo.getSize());
        page.setOrderByField(pageInfo.getSort());
        page.setAsc(pageInfo.getOrder().equalsIgnoreCase("asc"));
        List<Map<String, Object>> list =  userMapper.selectUserPage(page, pageInfo.getCondition());
        pageInfo.setRows((ArrayList) list);
        pageInfo.setTotal(userMapper.selectUserCount(pageInfo.getCondition()));
        return JSON.toJSON(pageInfo);
    }

    @Override
    public void deleteUserById(Long id) {
        this.deleteById(id);
        userRoleMapper.deleteByUserId(id);
    }

	@Override
	public UserVo selectByName(String name) {
		// TODO Auto-generated method stub
		return userMapper.selectbyName(name);
	}

    @Override
    public List<UserVo> selectListByName(String name) {
        // TODO Auto-generated method stub
        return userMapper.selectListByName(name);
    }

    @Override
    public List<Map<String, Object>> orgCode(Long userID) {
        if(null==userID){
           return Arrays.asList();
        }
        List<Map<String, Object>> cUserOrgCodelist = userMapper.selectOrganizationCodeById(userID);
        if(cUserOrgCodelist.size()==0){
            return Arrays.asList();
        }
        for (Map<String, Object> stringObjectMap : cUserOrgCodelist) {
            //上级编号
            String pidorgcode = String.valueOf(stringObjectMap.get("PIDORGCODE"));
            if(StringUtils.isNotBlank(pidorgcode) && !pidorgcode.equals("null")){
                //针对多级省区优化
                boolean isRegion = Pattern.matches("region_[a-zA-Z]{1,5}", pidorgcode);
                if(isRegion){
                    stringObjectMap.put("isRegion",true);
                }else {
                    if("beijing".equals(pidorgcode)){
                        stringObjectMap.put("isRegion",true);
                    }else{
                        stringObjectMap.put("isRegion",false);
                    }
                }
            }
            //上上级编号
            String ppidorgcode = String.valueOf(stringObjectMap.get("PPIDORGCODE"));
            if(StringUtils.isNotBlank(ppidorgcode) && !ppidorgcode.equals("null")){
                //针对多级省区优化
                boolean ppidorgcodeisRegion = Pattern.matches("region_[a-zA-Z]{1,5}", ppidorgcode);
                if(ppidorgcodeisRegion){
                    stringObjectMap.put("ppidorgcodeisRegion",true);
                }else {
                    if("beijing".equals(ppidorgcode)){
                        stringObjectMap.put("ppidorgcodeisRegion",true);
                    }else{
                        stringObjectMap.put("ppidorgcodeisRegion",false);
                    }
                }
            }
        }
        return cUserOrgCodelist;
    }

    @Override
    public Map<String, Object> selectAgentByOrgId(Map<String, Object> map) {
        return agentMapper.selectAgentByOrgId(map);
    }

    @Override
    @Transactional
    public void copyUser(Long id) throws Exception {
        CUser cUser = userMapper.selectById(id);
        UserVo userVo = BeanUtils.copy(cUser, UserVo.class);
        List<CUserRole> cUserRoles = userRoleMapper.selectByUserId(id);
        String roleIds = "";
        for (CUserRole cUserRole : cUserRoles) {
            roleIds+=cUserRole.getRoleId()+",";
        }
        userVo.setRoleIds(roleIds);
        userVo.setLoginName(userVo.getLoginName()+"1");
        insertByVo(userVo,new HashMap<>());
    }

    @Override
    public UserVo selectByLogin(UserVo userVo) {

        UserVo query = new UserVo();
        query.setLoginName(userVo.getLoginName());
        List<CUser> list = selectByLoginName(query);
        // 账号不存在
        if (list == null || list.isEmpty()) {
            return null;
        }
        CUser db_User = list.get(0);
        // 账号未启用
        if (db_User.getStatus() == 1) {
            return null;
        }
        if (db_User != null && db_User.getPassword()!=null) {
            //传递密码进行加密
            String slt = db_User.getSalt();
            String pas = DigestUtils.hashByShiro("md5", userVo.getPassword(), db_User.getSalt(), 1);
            //密码校验
            if (db_User.getPassword().equals(pas)) {
                UserVo uv = new UserVo();
                uv.setLoginName(db_User.getLoginName());
                uv.setId(db_User.getId());
                uv.setName(db_User.getName());
                uv.setPassword(pas);
                return uv;
            }else{
                return null;
            }
        }
        return null;
    }


}