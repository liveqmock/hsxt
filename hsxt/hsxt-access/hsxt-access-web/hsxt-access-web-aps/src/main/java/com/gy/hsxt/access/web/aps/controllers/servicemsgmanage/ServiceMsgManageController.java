package com.gy.hsxt.access.web.aps.controllers.servicemsgmanage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.servicemsgmanage.IServiceMsgManageService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.msgtpl.MsgTemplate;
import com.gy.hsxt.bs.bean.msgtpl.MsgTemplateAppr;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.controllers.servicemsgmanage
 * @className     : ServiceMsgManageController.java
 * @description   : 服务消息管理
 * @author        : maocy
 * @createDate    : 2016-03-11
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("serviceMsgManageController")
public class ServiceMsgManageController extends BaseController {
	
	@Resource
    private IServiceMsgManageService serviceMsgManageService;//服务消息管理服务类
	
	/**
     * 
     * 方法名称：消息模版审批列表
     * 方法描述：消息模版审批列表
     * @param request HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/findMessageTplApprList")
	public HttpRespEnvelope findMessageTplApprList(HttpServletRequest request, HttpServletResponse response) {
		try {
			super.verifyPointNo(request);//校验互生卡号
			request.setAttribute("serviceName", this.serviceMsgManageService);
			request.setAttribute("methodName", "findMessageTplApprList");
			return super.doList(request, response);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}
	
	/**
     * 
     * 方法名称：模版审批历史列表
     * 方法描述：模版审批历史列表
     * @param request HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/findMsgTplApprHisList")
	public HttpRespEnvelope findMsgTplApprHisList(HttpServletRequest request, HttpServletResponse response) {
		try {
			super.verifyPointNo(request);//校验互生卡号
			request.setAttribute("serviceName", this.serviceMsgManageService);
			request.setAttribute("methodName", "findMsgTplApprHisList");
			return super.doList(request, response);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}
	
	/**
     * 
     * 方法名称：保存消息模版
     * 方法描述：保存消息模版
     * @param request HttpServletRequest对象
     * @param bean 消息模版对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveMessageTpl")
    public HttpRespEnvelope saveMessageTpl(HttpServletRequest request, @ModelAttribute MsgTemplate bean) {
        try {
            super.verifySecureToken(request);
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {bean.getTempName(), ASRespCode.APS_MSG_TEMPNAME_INVALID},
                new Object[] {bean.getTempType(), ASRespCode.APS_MSG_TEMP_TYPE_INVALID},
                new Object[] {bean.getBizType(), ASRespCode.APS_MSG_BIZ_TYPE_INVALID},
                new Object[] {bean.getCustType(), ASRespCode.APS_MSG_CUST_TYPE_INVALID},
                new Object[] {bean.getTempContent(), ASRespCode.APS_MSG_TEMP_CONTENT_INVALID},
                new Object[] {bean.getTempFmt(), ASRespCode.APS_MSG_TEMP_FMT_INVALID}
            );
            //长度验证
            RequestUtil.verifyParamsLength(
                new Object[] {bean.getTempName(), 0, 60, ASRespCode.APS_MSG_TEMP_NAME_LENGTH_INVALID}
            );
            if(StringUtils.isBlank(bean.getTempId())){
            	String custId = request.getParameter("custId");//申请操作员编号
                String custName = request.getParameter("operName");//申请操作员名称
            	this.serviceMsgManageService.saveMessageTpl(bean, custId, custName);
            }else{
            	this.serviceMsgManageService.modifyMessageTpl(bean);
            }
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：查询模版详情
     * 方法描述：查询模版详情
     * @param request HttpServletRequest对象
     * @param tempId 模版ID
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findMessageTplInfo")
    public HttpRespEnvelope findMessageTplInfo(HttpServletRequest request, String tempId) {
        try {
            super.verifySecureToken(request);
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {tempId, ASRespCode.APS_MSG_TEMPID_INVALID}
            );
            MsgTemplate obj = this.serviceMsgManageService.findMessageTplInfo(tempId);
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {obj, ASRespCode.APS_MSG_TEMP_NOT_FOUND}
            );
            return new HttpRespEnvelope(obj);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：复核模版
     * 方法描述：复核模版
     * @param request HttpServletRequest对象
     * @param bean 复核信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/reviewTemplate")
    public HttpRespEnvelope reviewTemplate(HttpServletRequest request, @ModelAttribute MsgTemplateAppr bean) {
        try {
            super.verifySecureToken(request);
            String custId = request.getParameter("custId");//申请操作员编号
            String custName = request.getParameter("operName");//申请操作员名称
            Integer reviewRes = CommonUtils.toInteger(request.getParameter("reviewRes"));//复核状态
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {bean.getApplyId(), ASRespCode.AS_APPLYID_INVALID},
                new Object[] {bean.getTempId(), ASRespCode.APS_MSG_TEMPID_INVALID},
                new Object[] {reviewRes, ASRespCode.APS_MSG_REVIEWRES_INVALID}
            );
            //长度验证
            RequestUtil.verifyParamsLength(
                new Object[] {bean.getApprRemark(), 0, 300, ASRespCode.APS_MSG_APPR_REMARK_LENGTH_INVALID}
            );
            bean.setApprOptId(custId);
            bean.setApprOptName(custName);
            this.serviceMsgManageService.reviewTemplate(bean, reviewRes);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：启用/停用模版
     * 方法描述：启用/停用模版
     * @param request HttpServletRequest对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/startOrStopTpl")
    public HttpRespEnvelope startOrStopTpl(HttpServletRequest request) {
        try {
            super.verifySecureToken(request);
            String tempId = request.getParameter("tempId");//模版ID
            String custId = request.getParameter("custId");//申请操作员编号
            String custName = request.getParameter("operName");//申请操作员名称
            Integer optType = CommonUtils.toInteger(request.getParameter("optType"));//操作类型（1：启用， 0：停用）
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {tempId, ASRespCode.APS_MSG_TEMPID_INVALID},
                new Object[] {optType, ASRespCode.APS_MSG_OPTTYPE_INVALID}
            );
            this.serviceMsgManageService.startOrStopTpl(tempId, optType, custId, custName);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：分页查询短信消息发送
     * 方法描述：分页查询短信消息发送
     * @param request HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/findNoteByPage")
	public HttpRespEnvelope findNoteByPage(HttpServletRequest request, HttpServletResponse response) {
		try {
			super.verifyPointNo(request);//校验互生卡号
			request.setAttribute("serviceName", this.serviceMsgManageService);
			request.setAttribute("methodName", "findNoteByPage");
			return super.doList(request, response);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}
	
	/**
     * 
     * 方法名称：分页查询邮件消息发送
     * 方法描述：分页查询邮件消息发送
     * @param request HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/findEmailByPage")
	public HttpRespEnvelope findEmailByPage(HttpServletRequest request, HttpServletResponse response) {
		try {
			super.verifyPointNo(request);//校验互生卡号
			request.setAttribute("serviceName", this.serviceMsgManageService);
			request.setAttribute("methodName", "findEmailByPage");
			return super.doList(request, response);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}
	
	/**
     * 
     * 方法名称：分页查询业务互动消息发送
     * 方法描述：分页查询业务互动消息发送
     * @param request HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/findDynamicBizByPage")
	public HttpRespEnvelope findDynamicBizByPage(HttpServletRequest request, HttpServletResponse response) {
		try {
			super.verifyPointNo(request);//校验互生卡号
			request.setAttribute("serviceName", this.serviceMsgManageService);
			request.setAttribute("methodName", "findDynamicBizByPage");
			return super.doList(request, response);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}
	
	@Override
	protected IBaseService getEntityService() {
		return this.serviceMsgManageService;
	}

}
