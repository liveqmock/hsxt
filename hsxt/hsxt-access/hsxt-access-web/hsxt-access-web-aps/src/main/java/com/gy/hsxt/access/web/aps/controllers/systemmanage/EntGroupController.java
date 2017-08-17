/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.controllers.systemmanage;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.systemmanage.EntGroupService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.AsConstants;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.bean.ent.AsEntGroup;

/**
 * 用户组管理
 * 
 * @Package: com.gy.hsxt.access.web.mcs.controllers.systemmanage  
 * @ClassName: EntGroupController 
 * @Description: 用户组管理
 *
 * @author: zhangcy 
 * @date: 2016-1-9 下午6:00:09 
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("entgroup")
public class EntGroupController extends BaseController {

    @Resource
    private EntGroupService entGroupService;
    
    /**
     * 添加用户组
     * @param request
     * @param oper
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add",method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope add(HttpServletRequest request, @ModelAttribute AsEntGroup group) {
        try {
           
            super.verifySecureToken(request);
            //操作者客户号
            String adminCustId = request.getParameter("adminCustId");
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {adminCustId, RespCode.MW_OPRATOR_ADMINCUSTID},
                new Object[] {group.getEntCustId(), RespCode.MW_OPRATOR_ENTCUSNTID},
                new Object[] {group.getGroupDesc(), RespCode.MW_GROUP_GROUPDESC},
                new Object[] {group.getGroupName(), RespCode.MW_GROUP_GROUPNAME}
            );
            Long groupId =  entGroupService.addGroup(group, adminCustId);
            return new HttpRespEnvelope(groupId);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    /**
     * 修改用户组
     * @param request
     * @param group
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update",method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope update(HttpServletRequest request, @ModelAttribute AsEntGroup group) {
        try {
            
            super.verifySecureToken(request);
            //操作者客户号
            String adminCustId = request.getParameter("adminCustId");
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] {adminCustId, RespCode.MW_OPRATOR_ADMINCUSTID},
                    new Object[] {group.getGroupId(), RespCode.MW_GROUP_GROUPID},
                    new Object[] {group.getEntCustId(), RespCode.MW_OPRATOR_ENTCUSNTID},
                    new Object[] {group.getGroupDesc(), RespCode.MW_GROUP_GROUPDESC},
                    new Object[] {group.getGroupName(), RespCode.MW_GROUP_GROUPNAME}
                    );
            entGroupService.updateGroup(group, adminCustId);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    /**
     * 删除用户组
     * @param request
     * @param group
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete",method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope delete(HttpServletRequest request, @ModelAttribute AsEntGroup group) {
        try {
            
            super.verifySecureToken(request);
            //操作者客户号
            String adminCustId = request.getParameter("adminCustId");
            //用户组编号
            String groupId = request.getParameter("groupId");
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    
                new Object[] {adminCustId, RespCode.MW_OPRATOR_ADMINCUSTID},
                new Object[] {groupId, RespCode.MW_GROUP_GROUPID}
                
            );
            entGroupService.deleteGroup(Long.parseLong(groupId), adminCustId);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 查询用户组成员
     * @param request
     * @param group
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/userlist",method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope userList(HttpServletRequest request) {
        try {
            
            super.verifySecureToken(request);
            //企业客户号
            String entCustId = request.getParameter("entCustId");
            //用户组名称
            String groupName = request.getParameter("groupName");
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    
                    new Object[] {entCustId, RespCode.MW_OPRATOR_ENTCUSNTID},
                    new Object[] {groupName, RespCode.MW_GROUP_GROUPNAME}
                    
            );
            
            // 获取客户端传递每页显示条数
            String pageSize = (String) request.getParameter("pageSize");

            // 获取客户端传递当前页码
            String curPage = (String) request.getParameter("curPage");

            Integer no = null; // 当前页码
            Integer size = null; // 每页显示条数
            Page page = null;
            // 设置当前页默认第一页
            if (StringUtils.isBlank(curPage))
            {
                no = AsConstants.PAGE_NO;
            }
            else
            {
                no = Integer.parseInt(curPage);
            }

            // 设置页显示行数
            if (StringUtils.isBlank(pageSize))
            {
                size = AsConstants.PAGE_SIZE;
            }
            else
            {
                size = Integer.parseInt(pageSize);
            }

            // 构造分页对象
            page = new Page(no, size);
            
            AsEntGroup group = entGroupService.findGroup(entCustId, groupName);
            HttpRespEnvelope hre = new HttpRespEnvelope();
            hre.setData(group.getOpers());
            hre.setTotalRows(group.getOpers().size());
            hre.setCurPage(page.getCurPage());
            return hre;
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    /**
     * 成员组添加用户
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addgroupuser",method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope addGroupUser(HttpServletRequest request) {
        try {
            
            super.verifySecureToken(request);
            //操作者客户号
            String adminCustId = request.getParameter("adminCustId");
            //操作员（用户）客户号
            String operCustId = request.getParameter("operCustId");
            //用户组编号
            String groupId = request.getParameter("groupId");
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    
                    new Object[] {adminCustId, RespCode.MW_OPRATOR_ADMINCUSTID},
                    new Object[] {operCustId, RespCode.MW_OPRATOR_OPTCUSTID},
                    new Object[] {groupId, RespCode.MW_GROUP_GROUPID}
                    
            );
            
            List<String> operCustIds = Arrays.asList(operCustId.split(","));
            
            entGroupService.resetGroupOperator(operCustIds, Long.parseLong(groupId), adminCustId);
            
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    @ResponseBody
    @RequestMapping(value = "/delgroupuser",method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope delGroupUser(HttpServletRequest request) {
        try {
            
            super.verifySecureToken(request);
            //操作者客户号
            String adminCustId = request.getParameter("adminCustId");
            //操作员（用户）客户号
            String operCustId = request.getParameter("operCustId");
            //用户组编号
            String groupId = request.getParameter("groupId");
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    
                    new Object[] {adminCustId, RespCode.MW_OPRATOR_ADMINCUSTID},
                    new Object[] {operCustId, RespCode.MW_OPRATOR_OPTCUSTID},
                    new Object[] {groupId, RespCode.MW_GROUP_GROUPID}
                    
                    );
            
            entGroupService.deleteGroupUser(operCustId, Long.parseLong(groupId), adminCustId);
            
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    @Override
    protected IBaseService getEntityService() {
       
        return entGroupService;
    }

}
