/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.company.controllers.systemmanage;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.company.services.systemmanage.PermissionService;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.auth.AsPermission;

@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("perm")
public class PermissionController extends BaseController {

    @Resource
    private PermissionService permissionService;
    
    /**
     * 查询权限列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listperm",method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope listPerm(HttpServletRequest request) {
        try {
            
            super.verifySecureToken(request);
            //平台编码
            String platformCode = request.getParameter("platformCode");
            platformCode = StringUtils.isBlank(platformCode)?null:platformCode;
            //子系统编码
            String subSystemCode = request.getParameter("subSystemCode");
            subSystemCode = StringUtils.isBlank(subSystemCode)?null:subSystemCode;
            //权限类型
            String permType = request.getParameter("permType");
            //父权限id
            String parentId = request.getParameter("parentId");
            parentId = StringUtils.isBlank(parentId)?null:parentId;
            //企业类型
            String entResType = request.getParameter("entResType");
            
            Short type = StringUtils.isBlank(permType)?null:Short.parseShort(permType);
            
            List<AsPermission> list = permissionService.listPerm(platformCode, subSystemCode, type, parentId,entResType);
            
            return new HttpRespEnvelope(list);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    
    /**
     * 给角色赋权限
     * @param request
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/grantperm",method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope grantPerm(HttpServletRequest request) {
        try {
            
            super.verifySecureToken(request);
            //操作者客户号
            String adminCustId = request.getParameter("adminCustId");
            //角色id
            String roleId = request.getParameter("roleId");
            //权限id串 ","号隔开
            String powerIds = request.getParameter("powerIds");
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    
                    new Object[] {adminCustId, RespCode.MW_OPRATOR_ADMINCUSTID},
                    new Object[] {roleId, RespCode.MW_ROLE_ROLEID},
                    new Object[] {powerIds, RespCode.MW_PERMISSION_POWERID}
                    
            );
            
            List<String> list = Arrays.asList(powerIds.split(","));
            
            permissionService.resetPerm(roleId, list, adminCustId);
            
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 查询角色所拥有的权限
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/roleperm",method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope rolePerm(HttpServletRequest request) {
        try {
            
            super.verifySecureToken(request);
          //平台编码
            String platformCode = request.getParameter("platformCode");
            platformCode = StringUtils.isBlank(platformCode)?null:platformCode;
            //子系统编码
            String subSystemCode = request.getParameter("subSystemCode");
            subSystemCode = StringUtils.isBlank(subSystemCode)?null:subSystemCode;
            //角色id
            String roleId = request.getParameter("roleId");
            
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    
                    new Object[] {roleId, RespCode.MW_ROLE_ROLEID}
                    
                    );
            
            
            List<AsPermission> list = permissionService.listPermByRoleId(platformCode, subSystemCode, roleId);
            
            return new HttpRespEnvelope(list);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    @Override
    protected IBaseService getEntityService() {
        return permissionService;
    }

}
