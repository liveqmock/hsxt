/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.systemmanage.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.aps.services.systemmanage.RoleService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.auth.IUCAsRoleService;
import com.gy.hsxt.uc.as.bean.auth.AsRole;

@SuppressWarnings("rawtypes")
@Service
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {

    @Autowired
    private IUCAsRoleService iuCAsRoleService;

    
    @Override
    public PageData findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
        //企业客户号
        String entCustId = (String) filterMap.get("entCustId");
        //平台
        String platformCode = (String) filterMap.get("platformCode");
        //子系统
        String subSystemCode = (String) filterMap.get("subSystemCode");
        //角色类型
        Short roleType = (Short) filterMap.get("roleType");
        //企业类型
        String custType = (String) filterMap.get("custType");
        //角色名称
        String roleName = (String)filterMap.get("roleName");
        
        return iuCAsRoleService.listRoleByPage(platformCode, subSystemCode, roleType, entCustId,custType,roleName,page);
    }

    @Override
    public String addRole(AsRole role, String oprator) throws HsException {
        return iuCAsRoleService.addRole(role, oprator);
    }

    @Override
    public void deleteRole(String roleId, String oprator) throws HsException {
        iuCAsRoleService.deleteRole(roleId, oprator);
    }

    @Override
    public void updateRole(AsRole role, String oprator) throws HsException {
        iuCAsRoleService.updateRole(role, oprator);
    }

    @Override
    public void grantRole(String custId, List<String> list, String operator) throws HsException {
        iuCAsRoleService.grantRole(custId, list, operator);
    }

    @Override
    public void revokeRole(String custId, List<String> list, String operator) throws HsException {
        iuCAsRoleService.revokeRole(custId, list, operator);
    }

    @Override
    public void resetRole(String custId, List<String> list, String operator) throws HsException {
        iuCAsRoleService.resetRole(custId, list, operator);
    }

    @Override
    public List<AsRole> listRole(String platformCode, String subSystemCode, Short roleType, String entCustId, String custType)
            throws HsException {
        
        return iuCAsRoleService.listRole(platformCode, subSystemCode, roleType, entCustId,custType);
    }
    
}
