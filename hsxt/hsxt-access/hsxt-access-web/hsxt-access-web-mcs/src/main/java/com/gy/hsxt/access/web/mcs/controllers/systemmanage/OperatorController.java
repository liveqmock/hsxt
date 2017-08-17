/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.mcs.controllers.systemmanage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.bean.MCSBase;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.mcs.services.systemmanage.OperatorService;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tm.bean.Group;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.web.mcs.controllers.systemmanage  
 * @ClassName: OperatorController 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2016-1-9 下午12:10:20 
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("operator")
public class OperatorController extends BaseController {

    @Resource
    private OperatorService operatorService;
    
    /**
     * 添加操作员
     * @param request
     * @param apprParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add",method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope add(HttpServletRequest request, @ModelAttribute AsOperator oper) {
        try {
            
            super.verifySecureToken(request);
            //操作人客户号
            String adminCustId = request.getParameter("adminCustId");
            //用户组
            String groupIds = request.getParameter("groupIds");
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {adminCustId, RespCode.MW_OPRATOR_ADMINCUSTID},
                new Object[] {oper.getEntCustId(), RespCode.MW_OPRATOR_ENTCUSNTID},
                new Object[] {oper.getEntResNo(), RespCode.MW_OPRATOR_ENTRESNO},
                new Object[] {oper.getUserName(), RespCode.MW_OPRATOR_USERNAME},
                new Object[] {oper.getLoginPwd(), RespCode.MW_OPRATOR_LOGINPWD}
            );
            
            List<Long> groups = null;
            if(StringUtils.isNotBlank(groupIds)){
                groups = new ArrayList<Long>();
                for(String str : groupIds.split(",")){
                    groups.add(Long.parseLong(str));
                }
            }
            
            String operId = operatorService.addOper(oper, adminCustId,groups);
            return new HttpRespEnvelope(operId);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    /**
     * 修改操作员
     * @param request
     * @param oper
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update" ,method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope update(HttpServletRequest request, @ModelAttribute AsOperator oper) {
        try {
            
            super.verifySecureToken(request);
            //操作人
            String adminCustId = request.getParameter("adminCustId");
            //用户组 ","号隔开
            String groupIds = request.getParameter("groupIds");
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {adminCustId, RespCode.MW_OPRATOR_ADMINCUSTID},
                new Object[] {oper.getUserName(), RespCode.MW_OPRATOR_USERNAME},
                new Object[] {oper.getOperCustId(), RespCode.MW_OPRATOR_OPTCUSTID}
            );
            
            List<Long> groups = null;
            if(StringUtils.isNotBlank(groupIds)){
                groups = new ArrayList<Long>();
                for(String str : groupIds.split(",")){
                    groups.add(Long.parseLong(str));
                }
            }
            
            operatorService.updateOper(oper, adminCustId,groups);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    /**
     * 删除操作员
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete",method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope delete(HttpServletRequest request) {
        try {
            
            super.verifySecureToken(request);
            //管理员客户号
            String adminCustId = request.getParameter("adminCustId");
            //操作员客户号
            String operCustId = request.getParameter("operCustId");
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    
                    new Object[] {adminCustId, RespCode.MW_OPRATOR_ADMINCUSTID},
                    new Object[] {operCustId, RespCode.MW_OPRATOR_OPTCUSTID}
                    
            );
            operatorService.deleteOper(operCustId, adminCustId);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    /**
     * 查询操作员
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/query",method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope query(HttpServletRequest request) {
        try {
            
            super.verifySecureToken(request);
            //操作员客户号
            String operCustId = request.getParameter("operCustId");
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    
                    new Object[] {operCustId, RespCode.MW_OPRATOR_OPTCUSTID}
                    
                    );
            AsOperator result = operatorService.searchOperByCustId(operCustId);
            return new HttpRespEnvelope(result);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    /**
     * 查询操作员列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/query_list",method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryList(HttpServletRequest request) {
        try {
            
            super.verifySecureToken(request);
            //操作员客户号
            String entCustId = request.getParameter("entCustId");
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    
                    new Object[] {entCustId, RespCode.MW_OPRATOR_ENTCUSNTID}
                    
                    );
            List<AsOperator> result = operatorService.listOperByEntCustId(entCustId);
            return new HttpRespEnvelope(result);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 操作员信息
     * @param request
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("get_operator_login_detail")
    public HttpRespEnvelope operatorDetail(HttpServletRequest request, MCSBase mcsBase) throws IOException {
        try
        {
            //操作员登录信息
            Map<String, Object> retMap = operatorService.getOperatorDetail(mcsBase);
            return new HttpRespEnvelope(retMap);
        }
        catch (HsException e)
        {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 查询所有工单分组及分组下的所有操作员
     * @param request
     */
    @ResponseBody
    @RequestMapping("findTaskGroupInfo")
    public HttpRespEnvelope findTaskGroupInfo(HttpServletRequest request) {
        try {
        	String entCustId = request.getParameter("entCustId");//企业客户号
        	List<Group> list = this.operatorService.findTaskGroupInfo(entCustId);
            return new HttpRespEnvelope(list);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    @Override
    protected IBaseService getEntityService() {
        return operatorService;
    }

}
