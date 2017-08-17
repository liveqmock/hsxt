/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.mcs.controllers.systemmanage;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.mcs.services.systemmanage.RoleService;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.enumerate.PlatFormEnum;
import com.gy.hsxt.uc.as.api.enumerate.SubSysEnum;
import com.gy.hsxt.uc.as.bean.auth.AsRole;

/**
 * 角色管理controller
 * 
 * @Package: com.gy.hsxt.access.web.mcs.controllers.systemmanage  
 * @ClassName: RoleController 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2016-1-11 下午3:37:53 
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("role")
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;
    
    /**
     * 添加角色
     * @param request
     * @param group
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add",method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope add(HttpServletRequest request, @ModelAttribute AsRole role) {
        try {
           
            super.verifySecureToken(request);
            //设置平台代码
            role.setPlatformCode(PlatFormEnum.HSXT.name());
            //设置子系统编码
            role.setSubSystemCode(SubSysEnum.MCS.name());
            //操作者客户号
            String adminCustId = request.getParameter("adminCustId");
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {adminCustId, RespCode.MW_OPRATOR_ADMINCUSTID},
                new Object[] {role.getEntCustId(), RespCode.MW_OPRATOR_ENTCUSNTID},
                new Object[] {role.getRoleName(), RespCode.MW_ROLE_ROLENAME}
            );
            String roleId = roleService.addRole(role, adminCustId);
            return new HttpRespEnvelope(roleId);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 删除角色
     * @param request
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete",method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope delete(HttpServletRequest request) {
        try {
           
            super.verifySecureToken(request);
            //操作者客户号
            String adminCustId = request.getParameter("adminCustId");
            //角色id
            String roleId = request.getParameter("roleId");
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    
                new Object[] {adminCustId, RespCode.MW_OPRATOR_ADMINCUSTID},
                new Object[] {roleId, RespCode.MW_ROLE_ROLEID}
                
            );
            roleService.deleteRole(roleId, adminCustId);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 修改角色
     * @param request
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update",method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope update(HttpServletRequest request, @ModelAttribute AsRole role) {
        try {
            
            super.verifySecureToken(request);
            //操作者客户号
            String adminCustId = request.getParameter("adminCustId");
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    
                    new Object[] {adminCustId, RespCode.MW_OPRATOR_ADMINCUSTID},
                    new Object[] {role.getRoleName(), RespCode.MW_ROLE_ROLENAME},
                    new Object[] {role.getRoleId(), RespCode.MW_ROLE_ROLEID}
                    );
            roleService.updateRole(role, adminCustId);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 给角色添加操作员（给操作员赋角色）
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/grantrole",method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope grantRole(HttpServletRequest request) {
        try {
            
            super.verifySecureToken(request);
            //操作者客户号
            String adminCustId = request.getParameter("adminCustId");
            //角色串 ","号隔开
            String roleIds = request.getParameter("roleIds");
            //操作员客户号
            String operCustId = request.getParameter("operCustId");
            
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    
                    new Object[] {adminCustId, RespCode.MW_OPRATOR_ADMINCUSTID},
                    new Object[] {roleIds, RespCode.MW_ROLE_ROLEID},
                    new Object[] {operCustId, RespCode.MW_OPRATOR_OPTCUSTID}
                    
            );
            
            List<String> list = Arrays.asList(roleIds.split(","));
            
            roleService.grantRole(operCustId, list, adminCustId);
            
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 给角色重置操作员（给操作员重设角色）
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/resetrole",method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope resetRole(HttpServletRequest request) {
        try {
            
            super.verifySecureToken(request);
            //操作者客户号
            String adminCustId = request.getParameter("adminCustId");
            //角色串 ","号隔开
            String roleIds = request.getParameter("roleIds");
            //操作员客户号
            String operCustId = request.getParameter("operCustId");
            
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    
                    new Object[] {adminCustId, RespCode.MW_OPRATOR_ADMINCUSTID},
                    new Object[] {roleIds, RespCode.MW_ROLE_ROLEID},
                    new Object[] {operCustId, RespCode.MW_OPRATOR_OPTCUSTID}
                    
                    );
            
            List<String> list = Arrays.asList(roleIds.split(","));
            
            roleService.resetRole(operCustId, list, adminCustId);
            
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 不分页查询角色列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/rolelist",method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope roleList(HttpServletRequest request) {
        try {
            
            super.verifySecureToken(request);
            //平台编码
            String platformCode = request.getParameter("platformCode");
            platformCode = StringUtils.isBlank(platformCode)?null:platformCode;
            //子系统编码
            String subSystemCode = request.getParameter("subSystemCode");
            subSystemCode = StringUtils.isBlank(subSystemCode)?null:subSystemCode;
            //角色类型
            String roleType = request.getParameter("roleType");
            Short roleType2 = StringUtils.isBlank(roleType)?null:Short.parseShort(roleType);
            //企业客户号
            String entCustId = request.getParameter("entCustId");
            entCustId = StringUtils.isBlank(entCustId)?null:entCustId;
            //客户类型
            String custType = request.getParameter("custType");
            custType = StringUtils.isBlank(custType)?null:custType;
            
            List<AsRole> listRole = roleService.listRole(platformCode, subSystemCode, roleType2, entCustId,custType);
            
            return new HttpRespEnvelope(listRole);
            
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    
    @Override
    protected IBaseService getEntityService() {
        return roleService;
    }

}
