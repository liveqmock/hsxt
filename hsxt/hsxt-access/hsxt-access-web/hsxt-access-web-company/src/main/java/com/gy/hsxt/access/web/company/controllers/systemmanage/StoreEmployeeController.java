/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.company.controllers.systemmanage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsec.external.bean.ReturnResult;
import com.gy.hsec.external.bean.StoreEmployeeBinding;
import com.gy.hsec.external.bean.StoreEmployeeRelation;
import com.gy.hsec.external.bean.StoreReturnResult;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.company.services.systemmanage.StoreEmployeeService;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;

@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("storeemployee")
public class StoreEmployeeController extends BaseController {

    @Resource
    private StoreEmployeeService storeEmployeeService;
    
    @ResponseBody
    @RequestMapping(value = "/getSalerStoresByEntResNoAndEmpActNo" ,method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getSalerStoresByEntResNoAndEmpActNo(HttpServletRequest request, @ModelAttribute AsOperator oper) {
        try {
            
            super.verifySecureToken(request);
            
            //企业员工账号
            String employeeAccountNo = request.getParameter("employeeAccountNo");
            //企业资源号（企业互生号）
            String entResNo = request.getParameter("pointNo");
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {employeeAccountNo, RespCode.MW_OPRATOR_USERNAME},
                new Object[] {entResNo, RespCode.MW_OPRATOR_ENTRESNO}
            );
            
            StoreReturnResult<List<StoreEmployeeBinding>> result = null;
            try
            {
                result  = storeEmployeeService.getSalerStoresByEntResNoAndEmpActNo(entResNo, employeeAccountNo);
            }
            catch (Exception e)
            {
                return new HttpRespEnvelope(e);
            }
            return new HttpRespEnvelope(result);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    @ResponseBody
    @RequestMapping(value = "/setSalerStoreEmployeeRelation" ,method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope setSalerStoreEmployeeRelation(HttpServletRequest request, @ModelAttribute AsOperator oper) {
        try {
            
            super.verifySecureToken(request);
            
            //企业营业点ID
            String storeId = request.getParameter("storeId");
            //企业资源号（企业互生号）
            String entResNo = request.getParameter("hsResNo");
            //企业客户号
            String entCustId = request.getParameter("entCustId");
            //企业员工账号
            String employeeAccountNo = request.getParameter("employeeAccountNo");
            //企业员工客户号
            String employeeCustId = request.getParameter("employeeCustId");
            //创建人账号
            String custName = request.getParameter("custName");
            //创建人姓名
            String operName = request.getParameter("operName");
            //员工角色列表
            String roleIds = request.getParameter("roleIds");
            List<String> roleList = null;
            if(StringUtils.isNotBlank(roleIds)){
                String[] strs = roleIds.split(",");
                roleList = Arrays.asList(strs);
            }
            
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {storeId, ASRespCode.APS_GLYYD_STOREID_INVALID},
                new Object[] {entResNo, ASRespCode.APS_GLYYD_ENTRESNO_INVALID},
                new Object[] {employeeAccountNo, ASRespCode.APS_GLYYD_OPCUSTNAME_INVALID},
                new Object[] {custName, ASRespCode.APS_GLYYD_CREATE_INVALID},
                new Object[] {entCustId, ASRespCode.APS_GLYYD_ENTCUSTID_INVALID}
            );
            
            List<StoreEmployeeRelation> paramList = new ArrayList<StoreEmployeeRelation>();
            if(StringUtils.isNotEmpty(storeId)){
                String[] storeIds = storeId.split(",");
                for(String id:storeIds){
                    StoreEmployeeRelation temp = new StoreEmployeeRelation();
                    temp.setStoreId(id);
                    temp.setCreateTime(new Date());
                    temp.setCreator(custName + "(" + operName + ")");
                    temp.setEmployeeAccountNo(employeeAccountNo);
                    temp.setEmployeeCustomerNo(employeeCustId);
                    temp.setEnterpriseCustomerNo(entCustId);
                    temp.setEnterpriseResourceNo(entResNo);
                    temp.setRoleIds(roleList);
                    paramList.add(temp);
                }
            }
            
            ReturnResult result = null;
            try
            {
                result  = storeEmployeeService.setSalerStoreEmployeeRelation(paramList);
            }
            catch (Exception e)
            {
                return new HttpRespEnvelope(e);
            }
            return new HttpRespEnvelope(result);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    @ResponseBody
    @RequestMapping(value = "/deleteStoreEmployeesByEntResNoAndEmpActNo" ,method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope deleteStoreEmployeesByEntResNoAndEmpActNo(HttpServletRequest request, @ModelAttribute AsOperator oper) {
        try {
            
            super.verifySecureToken(request);
            //企业资源号（企业互生号）
            String entResNo = request.getParameter("hsResNo");
            //企业员工账号
            String employeeAccountNo = request.getParameter("employeeAccountNo");
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {entResNo, ASRespCode.APS_GLYYD_ENTRESNO_INVALID},
                new Object[] {employeeAccountNo, ASRespCode.APS_GLYYD_OPCUSTNAME_INVALID}
            );
            
            ReturnResult result = null;
            try
            {
                result = storeEmployeeService.deleteStoreEmployeesByEntResNoAndEmpActNo(entResNo, employeeAccountNo);
            }
            catch (Exception e)
            {
                return new HttpRespEnvelope(e);
            }
            return new HttpRespEnvelope(result);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    @Override
    protected IBaseService getEntityService() {
        // TODO Auto-generated method stub
        return storeEmployeeService;
    }

}
