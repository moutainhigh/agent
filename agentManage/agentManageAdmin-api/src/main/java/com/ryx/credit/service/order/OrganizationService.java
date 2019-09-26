package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.order.OrgPlatform;
import com.ryx.credit.pojo.admin.order.Organization;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OrganizationSerchVo;
import com.ryx.credit.pojo.admin.vo.OrganizationVo;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lrr
 * @Date: 2019/6/12 17:56
 * @Description:
 */
public interface OrganizationService {

    PageInfo organizationList(Page page, Organization organization);

    ResultVO organizationAdd(AgentVo agentVo) throws Exception;

    AgentResult organizationDelete(String id, String user);

    List<Organization> selectTop();

    List<Organization> selectOrganization(String orgId);

    ResultVO organizationEdit(AgentVo agentVo) throws Exception;

    List<OrganizationSerchVo> queryOrganization(String orgId);

    List<OrgPlatform> queryOrgPlatform(String orgId);

    List<Organization> queryAllOrgan();

    Organization selectById(String id);

    /**
     * 根据业务平台查询机构
     */
    List<Map> queryOrg(String platForm);

}