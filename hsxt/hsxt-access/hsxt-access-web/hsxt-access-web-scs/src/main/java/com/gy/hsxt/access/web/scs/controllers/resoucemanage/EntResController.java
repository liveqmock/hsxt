/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.controllers.resoucemanage;

import static com.gy.hsxt.common.utils.StringUtils.isNotBlank;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.scs.services.companyInfo.ICompanyInfoService;
import com.gy.hsxt.access.web.scs.services.companyInfo.IUCBankfoService;
import com.gy.hsxt.access.web.scs.services.resoucemanage.IEntResService;
import com.gy.hsxt.bs.bean.entstatus.MemberQuit;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;

/***
 * 
 * @Package: com.gy.hsxt.access.web.scs.controllers.sysres
 * @ClassName: TrusteeshipEnt
 * @Description: 企业资源
 * @author: wangjg
 * @date: 2015-11-3 下午8:40:58
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
@RequestMapping("ent_sys_res")
@Controller
public class EntResController extends BaseController {

    @Autowired
    private IEntResService entResService;//企业资源管理接口类
    
    @Resource
    private IUCBankfoService ucBankService;//银行信息服务类
	
	@Resource
    private ICompanyInfoService companyService;//企业信息服务类
	
	/**用户类型*/
	private final static String USER_TYPE = "4";
    
	@Override
	protected HttpRespEnvelope beforeList(HttpServletRequest request, Map filterMap, Map sortMap) {
		try {
			super.verifyPointNo(request);//校验互生卡号
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
		return null;
	}
	
	/**
     * 
     * 方法名称：查询成员企业资格维护
     * 方法描述：查询成员企业资格维护
     * @param request HttpServletRequest对象
     * @param request HttpServletResponse对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findQualMainList")
    public HttpRespEnvelope findQualMainList(HttpServletRequest request, HttpServletResponse response) {
        try {
            super.verifySecureToken(request);
            request.setAttribute("serviceName", entResService);
            request.setAttribute("methodName", "findQualMainList");
            return super.doList(request, response);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
	
	/**
     * 
     * 方法名称：查询信息
     * 方法描述：企业系统信息-查询信息
     * @param request HttpServletRequest对象
     * @param companyCustId  企业客户号
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findCompanyAllInfo")
    public HttpRespEnvelope findEntAllInfo(HttpServletRequest request, String companyCustId) {
        try {
            super.verifySecureToken(request);
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {companyCustId, RespCode.AS_ENT_CUSTID_INVALID}
            );
            AsEntExtendInfo info = this.companyService.findEntAllInfo(companyCustId);
            return new HttpRespEnvelope(info);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：查询银行卡
     * 方法描述：企业系统信息-查询银行卡
     * @param request HttpServletRequest对象
     * @param accId 银行卡编号
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findCompanyBanksByCustId")
    public HttpRespEnvelope findBanksByCustId(HttpServletRequest request, String companyCustId) {
        try {
            super.verifySecureToken(request);
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
            	new Object[] {companyCustId, RespCode.AS_ENT_CUSTID_INVALID}
            );
            List<AsBankAcctInfo> list = this.ucBankService.findBanksByCustId(companyCustId, USER_TYPE);
            return new HttpRespEnvelope(list);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：成员企业资格维护-重新启用成员企业
     * 方法描述：成员企业资格维护-重新启用成员企业
     * @param request HttpServletRequest对象
     * @param companyCustId 企业客户号
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateEntStatusInfo")
    public HttpRespEnvelope updateEntStatusInfo(HttpServletRequest request, String companyCustId) {
        try {
            super.verifySecureToken(request);
            String custId = request.getParameter("custId");
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
            	new Object[] {companyCustId, RespCode.AS_ENT_CUSTID_INVALID}
            );
            this.companyService.updateEntStatusInfo(companyCustId, custId);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：成员企业资格维护-注销成员企业
     * 方法描述：成员企业资格维护-注销成员企业
     * @param request HttpServletRequest对象
     * @param memberQuit 注销信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/createMemberQuit")
    public HttpRespEnvelope createMemberQuit(HttpServletRequest request, @ModelAttribute MemberQuit memberQuit) {
        try {
        	String pointNo = super.verifyPointNo(request);
            String entCustId = request.getParameter("entCustId");//获取登陆企业客户号
            String optEntName = request.getParameter("custEntName");//获取登陆客户企业名称
            
            String quitEntCustId = request.getParameter("quit_entCustId");//注销企业客户号
            String quitEntResNo = request.getParameter("quit_entResNo");//注销企业互生号
            String quitEntCustName = request.getParameter("quit_entCustName");//企业名称
            if(isNotBlank(quitEntCustId)){
            	memberQuit.setEntCustId(quitEntCustId);
            }
            if(isNotBlank(quitEntResNo)){
            	memberQuit.setEntResNo(quitEntResNo);
            }
            if(isNotBlank(quitEntCustName)){
            	memberQuit.setEntCustName(quitEntCustName);
            }
            
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
            	new Object[] {memberQuit.getEntCustId(), ASRespCode.SW_ENT_RES_ENT_CUSTID_INVALID},
            	new Object[] {memberQuit.getEntResNo(), ASRespCode.SW_ENT_RES_ENT_ENT_RESNO_INVALID},
            	new Object[] {memberQuit.getEntCustName(), ASRespCode.SW_ENT_RES_ENT_ENTCUSTNAME_INVALID},
            	new Object[] {memberQuit.getOldStatus(), ASRespCode.SW_ENT_RES_ENT_OLD_STATUS_INVALID},
            	new Object[] {memberQuit.getBankAcctId(), ASRespCode.SW_ENT_RES_ENT_BANKACCTID_INVALID},
            	new Object[] {memberQuit.getReportFile(), ASRespCode.SW_ENT_REPORTFILE_INVALID},
            	new Object[] {memberQuit.getStatementFile(), ASRespCode.SW_ENT_STATEMENTFILE_INVALID}
            );
            //长度验证
            RequestUtil.verifyParamsLength(
                new Object[] {memberQuit.getApplyReason(), 0, 300, ASRespCode.SW_ENT_RES_ENT_APPLYREASON_INVALID}
            );
            memberQuit.setApplyType(2);//申请类型
            memberQuit.setSerEntCustId(entCustId);//服务公司客户号
            memberQuit.setSerEntResNo(pointNo);//服务公司客户号
            memberQuit.setSerEntCustName(optEntName);//服务公司互生号
            this.entResService.createMemberQuit(memberQuit);//服务公司名称
            return new HttpRespEnvelope();
        } catch (Exception e) {
            return new HttpRespEnvelope(e);
        }
    }

    @Override
    protected IBaseService getEntityService() {
        return this.entResService;
    }

}
