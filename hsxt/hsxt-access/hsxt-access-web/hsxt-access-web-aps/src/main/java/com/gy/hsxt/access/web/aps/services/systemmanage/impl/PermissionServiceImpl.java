/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.systemmanage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.aps.services.systemmanage.PermissionService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.auth.IUCAsPermissionService;
import com.gy.hsxt.uc.as.bean.auth.AsPermission;

@SuppressWarnings("rawtypes")
@Service
public class PermissionServiceImpl extends BaseServiceImpl implements PermissionService {

    @Autowired
    private IUCAsPermissionService iuCAsPermissionService;

    @Override
    public List<AsPermission> listPerm(String platformCode, String subSystemCode, Short permType, String parentId)
            throws HsException {
        
        return iuCAsPermissionService.listPerm(platformCode, subSystemCode, permType, parentId);
    }

    @Override
    public void grantPerm(String roleId, List<String> list, String operator) throws HsException {
        iuCAsPermissionService.grantPerm(roleId, list, operator);
    }

    @Override
    public void revokePerm(String roleId, List<String> list, String operator) throws HsException {
        iuCAsPermissionService.revokePerm(roleId, list, operator);
    }

    @Override
    public void resetPerm(String roleId, List<String> list, String operator) throws HsException {
        iuCAsPermissionService.resetPerm(roleId, list, operator);
    }

    @Override
    public List<AsPermission> listPermByRoleId(String platformCode, String subSystemCode, String roleId)
            throws HsException {
        return iuCAsPermissionService.listPermByRoleId(platformCode, subSystemCode, roleId);
    }
}
