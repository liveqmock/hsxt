/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.controllers.businessdoc;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.businessdoc.IBusinessDocService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.bizfile.BizDoc;
import com.gy.hsxt.bs.bean.bizfile.ImageDoc;
import com.gy.hsxt.bs.bean.bizfile.NormalDoc;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.controllers.businessdoc
 * @className     : BusinessDocController.java
 * @description   : 业务文件管理
 * @author        : maocy
 * @createDate    : 2016-02-27
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("businessDocController")
public class BusinessDocController extends BaseController {
	
	@Resource
    private IBusinessDocService businessDocService;//业务文件管理服务类
	
	/**
     * 
     * 方法名称：保存业务文件
     * 方法描述：保存业务文件-示例图片
     * @param request HttpServletRequest对象
     * @param bean 业务文件对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/savePicDoc")
    public HttpRespEnvelope savePicDoc(HttpServletRequest request, String entCustId, @ModelAttribute ImageDoc bean) {
        try {
            super.verifySecureToken(request);
            String custName = request.getParameter("custName");//获取登陆用户名称
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {bean.getDocName(), ASRespCode.APS_BUS_DOC_NAME_INVALID},
                new Object[] {bean.getDocType(), ASRespCode.APS_BUS_DOC_TYPE_INVALID},
                new Object[] {bean.getDocCode(), ASRespCode.APS_BUS_DOC_CODE_INVALID},
                new Object[] {bean.getFileId(), ASRespCode.APS_BUS_DOC_FILEID_INVALID}
            );
            //长度验证
            RequestUtil.verifyParamsLength(
                new Object[] {bean.getDocName(), 1, 20, ASRespCode.APS_BUS_DOC_NAME_LENGTH_INVALID},
                new Object[] {bean.getRemark(), 0, 300, ASRespCode.APS_BUS_DOC_REMARK_LENGTH_INVALID}
            );
            if(StringUtils.isBlank(bean.getDocId())){
            	bean.setCreatedBy(custName);
            	this.businessDocService.saveDoc(bean);
            }else{
            	bean.setUpdatedBy(custName);
            	this.businessDocService.modifyDoc(bean);
            }
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：保存业务文件
     * 方法描述：保存业务文件-常用业务文档
     * @param request HttpServletRequest对象
     * @param bean 业务文件对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveComDoc")
    public HttpRespEnvelope saveComDoc(HttpServletRequest request, String entCustId, @ModelAttribute NormalDoc bean) {
        try {
            super.verifySecureToken(request);
            String custName = request.getParameter("custName");//获取登陆用户名称
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {bean.getDocName(), ASRespCode.APS_BUS_DOC_NAME_INVALID},
                new Object[] {bean.getDocType(), ASRespCode.APS_BUS_DOC_TYPE_INVALID},
                new Object[] {bean.getDocCode(), ASRespCode.APS_BUS_DOC_CODE_INVALID},
                new Object[] {bean.getFileId(), ASRespCode.APS_BUS_DOC_FILEID_INVALID}
            );
            //长度验证
            RequestUtil.verifyParamsLength(
                new Object[] {bean.getDocName(), 1, 20, ASRespCode.APS_BUS_DOC_NAME_LENGTH_INVALID},
                new Object[] {bean.getRemark(), 0, 300, ASRespCode.APS_BUS_DOC_REMARK_LENGTH_INVALID}
            );
            if(StringUtils.isBlank(bean.getDocId())){
            	bean.setCreatedBy(custName);
            	this.businessDocService.saveDoc(bean);
            }else{
            	bean.setUpdatedBy(custName);
            	this.businessDocService.modifyDoc(bean);
            }
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：保存业务文件
     * 方法描述：保存业务文件-办理业务申请书
     * @param request HttpServletRequest对象
     * @param bean 业务文件对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveBusDoc")
    public HttpRespEnvelope saveBusDoc(HttpServletRequest request, String entCustId, @ModelAttribute BizDoc bean) {
        try {
            super.verifySecureToken(request);
            String custName = request.getParameter("custName");//获取登陆用户名称
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {bean.getDocName(), ASRespCode.APS_BUS_DOC_NAME_INVALID},
                new Object[] {bean.getDocType(), ASRespCode.APS_BUS_DOC_TYPE_INVALID},
                new Object[] {bean.getDocCode(), ASRespCode.APS_BUS_DOC_CODE_INVALID},
                new Object[] {bean.getFileId(), ASRespCode.APS_BUS_DOC_FILEID_INVALID}
            );
            //长度验证
            RequestUtil.verifyParamsLength(
                new Object[] {bean.getDocName(), 1, 20, ASRespCode.APS_BUS_DOC_NAME_LENGTH_INVALID},
                new Object[] {bean.getRemark(), 0, 300, ASRespCode.APS_BUS_DOC_REMARK_LENGTH_INVALID}
            );
            if(StringUtils.isBlank(bean.getDocId())){
            	bean.setCreatedBy(custName);
            	this.businessDocService.saveDoc(bean);
            }else{
            	bean.setUpdatedBy(custName);
            	this.businessDocService.modifyDoc(bean);
            }
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：发布文档
     * 方法描述：发布文档
     * @param request HttpServletRequest对象
     * @param docId 文档ID
     * @param fileType 文档类型，1：图片文件 ，2：业务文件，3：常用文件
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/releaseBusinessDoc")
    public HttpRespEnvelope releaseDoc(HttpServletRequest request, String docId, Integer fileType) {
        try {
            super.verifySecureToken(request);
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {docId, ASRespCode.APS_BUS_DOC_ID_INVALID},
                new Object[] {fileType, ASRespCode.APS_BUS_DOC_TYPE_INVALID}
            );
            this.businessDocService.releaseDoc(docId, fileType);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：删除文档
     * 方法描述：删除文档
     * @param request HttpServletRequest对象
     * @param docId 文档ID
     * @param fileType 文档类型，1：图片文件 ，2：业务文件，3：常用文件
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteBusinessDoc")
    public HttpRespEnvelope deleteDoc(HttpServletRequest request, String docCode, Integer fileType) {
        try {
            super.verifySecureToken(request);
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
        		 new Object[] {docCode, ASRespCode.APS_BUS_DOC_CODE_INVALID},
                 new Object[] {fileType, ASRespCode.APS_BUS_DOC_TYPE_INVALID}
            );
            this.businessDocService.deleteDoc(docCode, fileType);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：停用文档
     * 方法描述：停用文档
     * @param request HttpServletRequest对象
     * @param docCode 文档唯一标识
     * @param fileType 文档类型，1：图片文件 ，2：业务文件，3：常用文件
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/stopUsedBusinessDoc")
    public HttpRespEnvelope stopUsedDoc(HttpServletRequest request, String docCode, Integer fileType) {
        try {
            super.verifySecureToken(request);
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
        		 new Object[] {docCode, ASRespCode.APS_BUS_DOC_CODE_INVALID},
                 new Object[] {fileType, ASRespCode.APS_BUS_DOC_TYPE_INVALID}
            );
            this.businessDocService.stopUsedDoc(docCode, fileType);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：查询文档详情
     * 方法描述：查询文档详情
     * @param request HttpServletRequest对象
     * @param docId 文档ID
     * @param fileType 文档类型，1：图片文件 ，2：业务文件，3：常用文件
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findBusinessDocInfo")
    public HttpRespEnvelope findDocInfo(HttpServletRequest request, String docId, Integer fileType) {
        try {
            super.verifySecureToken(request);
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
        		 new Object[] {docId, ASRespCode.APS_BUS_DOC_ID_INVALID},
                 new Object[] {fileType, ASRespCode.APS_BUS_DOC_TYPE_INVALID}
            );
            Object obj = this.businessDocService.findDocInfo(docId, fileType);
            return new HttpRespEnvelope(obj);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：获取示例图片版本
     * 方法描述：获取示例图片版本
     * @param request HttpServletRequest对象
     * @param docCode 文档唯一标识
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findBusinessImageDocHis")
    public HttpRespEnvelope getImageDocHis(HttpServletRequest request, String docCode) {
        try {
            super.verifySecureToken(request);
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {docCode, ASRespCode.APS_BUS_DOC_CODE_INVALID}
            );
            List<ImageDoc> list = this.businessDocService.findImageDocHis(docCode);
            return new HttpRespEnvelope(list);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：获取示例图片管理列表
     * 方法描述：获取示例图片管理列表
     * @param request HttpServletRequest对象
     * @param docCode 文档小类别
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findBusinessImageDocList")
    public HttpRespEnvelope findImageDocList(HttpServletRequest request) {
        try {
            super.verifySecureToken(request);
            List<ImageDoc> list = this.businessDocService.findImageDocList(null);
            return new HttpRespEnvelope(list);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：恢复示例图片版本
     * 方法描述：恢复示例图片版本
     * @param request HttpServletRequest对象
     * @param docId 文档ID
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/recoveryBusinessImageDoc")
    public HttpRespEnvelope recoveryImageDoc(HttpServletRequest request, String docId) {
        try {
            super.verifySecureToken(request);
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {docId, ASRespCode.APS_BUS_DOC_ID_INVALID}
            );
            this.businessDocService.recoveryImageDoc(docId);
            return new HttpRespEnvelope();
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：查询办理业务文档列表
     * 方法描述：查询办理业务文档列表
     * @param request HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/findBusinessBizDocList")
	public HttpRespEnvelope findBizDocList(HttpServletRequest request, HttpServletResponse response) {
		try {
			super.verifyPointNo(request);//校验互生卡号
			request.setAttribute("serviceName", this.businessDocService);
			request.setAttribute("methodName", "findBizDocList");
			return super.doList(request, response);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}
	
	/**
     * 
     * 方法名称：查询常用业务文档列表
     * 方法描述：查询常用业务文档列表
     * @param request HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/findBusinessNormalDocList")
	public HttpRespEnvelope findNormalDocList(HttpServletRequest request, HttpServletResponse response) {
		try {
			super.verifyPointNo(request);//校验互生卡号
			request.setAttribute("serviceName", this.businessDocService);
			request.setAttribute("methodName", "findNormalDocList");
			return super.doList(request, response);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}
    
	@Override
	protected IBaseService getEntityService() {
		return this.businessDocService;
	}

}
