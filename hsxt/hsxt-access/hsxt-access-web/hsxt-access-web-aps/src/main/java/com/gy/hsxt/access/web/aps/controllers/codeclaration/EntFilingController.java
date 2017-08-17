package com.gy.hsxt.access.web.aps.controllers.codeclaration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.access.web.aps.services.codeclaration.IEntFilingService;
import com.gy.hsxt.access.web.aps.services.codeclaration.IShareholderService;
import com.gy.hsxt.access.web.aps.services.common.ApsConfigService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.apply.FilingApp;
import com.gy.hsxt.bs.bean.apply.FilingApprParam;
import com.gy.hsxt.bs.bean.apply.FilingAptitude;
import com.gy.hsxt.bs.bean.apply.FilingShareHolder;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.controllers.codeclaration
 * @className     : EntFilingController.java
 * @description   : 企业报备
 * @author        : maocy
 * @createDate    : 2015-12-15
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Controller
@RequestMapping("entFilingController")
public class EntFilingController extends BaseController{
    
    @Autowired
    private ApsConfigService configService;//配置服务类
	
	@Resource
    private IEntFilingService entFilingService; //企业报备服务类
	
	@Resource
    private IShareholderService shareholderService; //股东信息服务类
    @Resource
    private RedisUtil<String> changeRedisUtil;
    /**
     * 
     * 方法名称：报备信息查询
     * 方法描述：报备信息查询
     * @param request HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/list")
	public HttpRespEnvelope doList(HttpServletRequest request, HttpServletResponse response) {
		try {
			super.verifyPointNo(request);//校验互生卡号
			request.setAttribute("serviceName", entFilingService);
			request.setAttribute("methodName", "findScrollResult");
			return super.doList(request, response);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}
	
	/**
	 * 
     * 方法名称：异议报备审核查询
     * 方法描述：异议报备审核查询
	 * @param request HttpServletRequest对象
	 * @param response HttpServletResponse对象
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/findDisagreedFilingList")
	protected HttpRespEnvelope findDisagreedFilingList(HttpServletRequest request, HttpServletResponse response) {
		try {
			super.verifyPointNo(request);//校验互生卡号
			RequestUtil.verifyQueryDate(request.getParameter("search_startDate"), request.getParameter("search_endDate"));//校验时间
			request.setAttribute("serviceName", entFilingService);
			request.setAttribute("methodName", "platQueryDisagreedFiling");
			return super.doList(request, response);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}
	
	/**
     * 
     * 方法名称：企业报备审核查询
     * 方法描述：企业报备审核查询
     * @param request HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findApprFilingList")
    protected HttpRespEnvelope findApprFilingList(HttpServletRequest request, HttpServletResponse response) {
        try {
            super.verifyPointNo(request);//校验互生卡号
            RequestUtil.verifyQueryDate(request.getParameter("search_startDate"), request.getParameter("search_endDate"));//校验时间
            request.setAttribute("serviceName", entFilingService);
            request.setAttribute("methodName", "platQueryApprFiling");
            return super.doList(request, response);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
	
	/**
	 * 
	 * 方法名称：查看异议报备详情
	 * 方法描述：依据申请编号查询异议报备详情
	 * @param request HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/findFilingById")
	protected HttpRespEnvelope findFilingById(HttpServletRequest request) {
		try {
			super.verifySecureToken(request);
			String applyId = request.getParameter("applyId");//申请编号
			//非空验证
	        RequestUtil.verifyParamsIsNotEmpty(
	        	new Object[] {applyId, RespCode.APS_APPLYID_INVALID}
	        );
			Map<String, Object> resMap = this.entFilingService.findFilingById(applyId);
			if(resMap == null){
				throw new HsException(RespCode.APS_ENT_FILING_DETAIL_INVALID);
			}
			return new HttpRespEnvelope(resMap);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}
	
	/**
	 * 方法名称：查询股东信息
	 * 方法描述：企业报备-查询股东信息
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/findShareholderList")
	public HttpRespEnvelope doListShareholder(HttpServletRequest request, HttpServletResponse response) {
		try {
			super.verifySecureToken(request);
			String applyId = request.getParameter("search_applyId");//申请编号
			//非空验证
	        RequestUtil.verifyParamsIsNotEmpty(
	        	new Object[] {applyId, RespCode.APS_APPLYID_INVALID}
	        );
			request.setAttribute("serviceName", shareholderService);
			request.setAttribute("methodName", "findScrollResult");
			return super.doList(request, response);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}
	
	/**
	 * 方法名称：新增股东信息
	 * 方法描述：企业报备-新增股东信息
	 * @param request HttpServletRequest
	 * @param shareHolder 股东信息
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/createShareholder")
	public HttpRespEnvelope createShareholder(HttpServletRequest request, @ModelAttribute FilingShareHolder shareHolder) {
		try {
			super.verifySecureToken(request);
			String optEntName = request.getParameter("custEntName");//获取登陆客户企业名称
			//非空验证
	        RequestUtil.verifyParamsIsNotEmpty(
	            new Object[] {shareHolder, RespCode.APS_ENT_FILING_SHAREHOLDER_INVALID},
	        	new Object[] {shareHolder.getApplyId(), RespCode.APS_ENT_FILING_APPLYID_INVALID},
	        	new Object[] {shareHolder.getShName(), RespCode.APS_ENT_FILING_SHAREHOLDER_SHNAME_INVALID},
	        	new Object[] {shareHolder.getShType(), RespCode.APS_ENT_FILING_SHAREHOLDER_SHTYPE_INVALID},
	        	new Object[] {shareHolder.getIdType(), RespCode.APS_ENT_FILING_SHAREHOLDER_IDTYPE_INVALID},
	        	new Object[] {shareHolder.getIdNo(), RespCode.APS_ENT_FILING_SHAREHOLDER_IDNO_INVALID},
	        	new Object[] {shareHolder.getPhone(), RespCode.APS_ENT_FILING_SHAREHOLDER_PHONE_INVALID}
	        );
	        shareHolder.setOptCustId(request.getParameter("custId"));//设置客户号
	        shareHolder.setOptEntName(optEntName);
			this.shareholderService.createShareholder(shareHolder);
			return new HttpRespEnvelope();
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}
	
	/**
	/**
	 * 方法名称：修改股东信息
	 * 方法描述：企业报备-修改股东信息
	 * @param request HttpServletRequest
	 * @param shareHolder 股东信息
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/updateShareholder")
	public HttpRespEnvelope updateShareholder(HttpServletRequest request, @ModelAttribute FilingShareHolder shareHolder) {
		try {
			super.verifySecureToken(request);
			String optEntName = request.getParameter("custEntName");//获取登陆客户企业名称
			//非空验证
	        RequestUtil.verifyParamsIsNotEmpty(
	        	new Object[] {shareHolder, RespCode.APS_ENT_FILING_SHAREHOLDER_INVALID},
	        	new Object[] {shareHolder.getApplyId(), RespCode.APS_ENT_FILING_APPLYID_INVALID},
	        	new Object[] {shareHolder, RespCode.APS_ENT_FILING_SHAREHOLDER_INVALID},
	        	new Object[] {shareHolder.getFilingShId(), RespCode.APS_ENT_FILING_SHAREHOLDERID_INVALID},
	        	new Object[] {shareHolder.getShName(), RespCode.APS_ENT_FILING_SHAREHOLDER_SHNAME_INVALID},
	        	new Object[] {shareHolder.getShType(), RespCode.APS_ENT_FILING_SHAREHOLDER_SHTYPE_INVALID},
	        	new Object[] {shareHolder.getIdType(), RespCode.APS_ENT_FILING_SHAREHOLDER_IDTYPE_INVALID},
	        	new Object[] {shareHolder.getIdNo(), RespCode.APS_ENT_FILING_SHAREHOLDER_IDNO_INVALID},
	        	new Object[] {shareHolder.getPhone(), RespCode.APS_ENT_FILING_SHAREHOLDER_PHONE_INVALID}
	        );
	        shareHolder.setOptCustId(request.getParameter("custId"));//设置客户号
	        shareHolder.setOptEntName(optEntName);
			this.shareholderService.updateShareholder(shareHolder);
			return new HttpRespEnvelope();
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}
	
	/**
	 * 方法名称：删除股东信息
	 * 方法描述：企业报备-删除股东信息
	 * @param request HttpServletRequest
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/deleteShareholder")
	public HttpRespEnvelope deleteShareholder(HttpServletRequest request) {
		try {
			super.verifySecureToken(request);
			String id = request.getParameter("id");//股东编号
			String custId = request.getParameter("custId");//操作员客户号
			//非空验证
	        RequestUtil.verifyParamsIsNotEmpty(
	        	new Object[] {id, RespCode.APS_ENT_FILING_SHAREHOLDERID_INVALID}
	        );
			this.shareholderService.deleteShareholder(id, custId);
			return new HttpRespEnvelope();
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}
	
	/**
     * 方法名称：初始化上传界面
     * 方法描述：企业报备-初始化附件信息上传界面
     * @param request HttpServletRequest
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/initUpload")
    public HttpRespEnvelope initUpload(HttpServletRequest request) {
        try {
            super.verifySecureToken(request);
            String applyId = request.getParameter("applyId");//报备企业申请编号
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
            	new Object[] {applyId, RespCode.APS_APPLYID_INVALID}
            );
            //查询附件
            List<FilingAptitude> realList = this.entFilingService.queryAptByApplyId(applyId);
           
            //存放数据
            Map<String, Object> resMap = new HashMap<String, Object>();
           
            resMap.put("realList", realList);
            return new HttpRespEnvelope(resMap);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 方法名称：保存附件信息
     * 方法描述：企业报备-保存附件信息
     * @param request HttpServletRequest
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveAptitude")
    public HttpRespEnvelope saveAptitude(HttpServletRequest request) {
        try {
            super.verifySecureToken(request);
            super.verifyCodes(request);//校验验证码
            String custId = request.getParameter("custId");//获取登陆用户名称
            String applyId = request.getParameter("applyId");//报备企业申请编号
            String dblOptCustId = request.getParameter("dblOptCustId");//报备企业申请编号
            String licensePicFileId = request.getParameter("licensePicFileId");//营业执照正本扫描件文件Id
            String bankPicFileId = request.getParameter("bankPicFileId");//银行资金证明文件Id
            String sharePicFileId = request.getParameter("sharePicFileId");//合作股东证明文件Id
            String licensePicAptId = request.getParameter("licensePicAptId");//营业执照正本扫描件附件Id
            String bankPicAptId = request.getParameter("bankPicAptId");//银行资金证明附件Id
            String sharePicAptId = request.getParameter("sharePicAptId");//合作股东证明附件Id
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {applyId, RespCode.APS_APPLYID_INVALID},
                new Object[] {dblOptCustId, RespCode.AS_DOULBE_USERID_INVALID},
                new Object[] {licensePicFileId, RespCode.APS_ENT_FILING_LICE_PIC_FILEID_INVALID}
//                new Object[] {bankPicFileId, RespCode.APS_ENT_FILING_BANK_PIC_FILEID_INVALID},
//                new Object[] {sharePicFileId, RespCode.APS_ENT_FILING_SHARE_PIC_FILEID_INVALID}
            );
            List<FilingAptitude> aptList = new ArrayList<FilingAptitude>();
            aptList.add(this.buildFilingAptitude(licensePicAptId, applyId, 3, licensePicFileId, custId));
            if(!StringUtils.isBlank(bankPicFileId)){
            	 aptList.add(this.buildFilingAptitude(bankPicAptId, applyId, 4, bankPicFileId, custId));
            }
            if(!StringUtils.isBlank(sharePicFileId)){
            	aptList.add(this.buildFilingAptitude(sharePicAptId, applyId, 5, sharePicFileId, custId));
            }
            List<FilingAptitude> resList = this.entFilingService.saveAptitude(aptList);
            return new HttpRespEnvelope(resList);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 方法名称：查询企业基本信息
     * 方法描述：根据申请编号查询企业基本信息
     * @param request HttpServletRequest
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getEntFilingById")
    public HttpRespEnvelope getEntFilingById(HttpServletRequest request) {
        try {
            super.verifySecureToken(request);
            String applyId = request.getParameter("applyId");//报备企业申请编号
            //非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                new Object[] {applyId, RespCode.APS_APPLYID_INVALID}
            );
            FilingApp obj = this.entFilingService.queryFilingBaseInfoById(applyId);
            if(obj == null){
                throw new HsException(RespCode.APS_ENT_FILING_BASEINFO_INVALID);
            }
            return new HttpRespEnvelope(obj);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
	 * 企业报备-保存企业基本信息
	 * @param request HttpServletRequest
	 * @param filingApp 报备企业信息
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/createEntFiling")
	public HttpRespEnvelope createEntFiling(HttpServletRequest request, @ModelAttribute FilingApp filingApp) {
		try {
			super.verifySecureToken(request);
			String custId = request.getParameter("custId");//获取登陆用户名称
			String pointNo = super.verifyPointNo(request);//获取企业互生号
			String applyId  = request.getParameter("applyId");//企业申请编号
			String optEntName = request.getParameter("custEntName");//获取登陆客户企业名称
			//非空验证
	        RequestUtil.verifyParamsIsNotEmpty(
        		new Object[] {applyId, RespCode.APS_APPLYID_INVALID},
        		new Object[] {filingApp, RespCode.APS_ENT_FILING_OBJECT_INVALID},
        		new Object[] {filingApp.getDblOptCustId(), RespCode.AS_DOULBE_USERID_INVALID},
        		new Object[] {filingApp.getEntCustName(), RespCode.APS_ENT_FILING_ENTCUSTNAME_INVALID},
        		new Object[] {filingApp.getEntAddress(), RespCode.APS_ENT_FILING_ENT_ADDRESS_INVALID},
        		new Object[] {filingApp.getLegalName(), RespCode.APS_ENT_FILING_LEGALNAME_INVALID},
        		new Object[] {filingApp.getLinkman(), RespCode.APS_ENT_FILING_LINKEMAN_INVALID},
        		new Object[] {filingApp.getPhone(), RespCode.APS_ENT_FILING_PHONE_INVALID}
	        );
	        //长度验证
	        RequestUtil.verifyParamsLength(
	        	new Object[] {filingApp.getDealArea(), 0, 300, RespCode.APS_ENT_FILING_DEALAREA_LENGTH_INVALID},
	        	new Object[] {filingApp.getReqReason(), 0, 300, RespCode.APS_ENT_FILING_APPRREMARK_LENGTH_INVALID},
	        	new Object[] {filingApp.getEntCustName(), 2, 128, RespCode.APS_ENT_FILING_ENTCUSTNAME_LENGTH_INVALID},
	        	new Object[] {filingApp.getEntAddress(), 2, 128, RespCode.APS_ENT_FILING_ENT_ADDRESS_LENGTH_INVALID},
	        	new Object[] {filingApp.getLegalName(), 2, 20, RespCode.APS_ENT_FILING_LEGALNAME_LENGTH_INVALID},
	        	new Object[] {filingApp.getLinkman(), 2, 20, RespCode.APS_ENT_FILING_LINKEMAN_LENGTH_INVALID},
	        	new Object[] {filingApp.getEntType(), 0, 20, RespCode.APS_ENT_FILING_ENTTYPE_LENGTH_INVALID}
	        );
	        filingApp.setOptCustId(custId);
	        filingApp.setOpResNo(pointNo);
	        filingApp.setOptEntName(optEntName);
            filingApp.setUpdatedBy(custId);
            filingApp.setUpdatedDate(DateUtil.getCurrentDateTime());
            this.entFilingService.updateEntFiling(filingApp);
			return new HttpRespEnvelope(applyId);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}
	
	/**
	 * 方法名称：审批企业报备
	 * 方法描述：地区平台审批企业报备
	 * @param request HttpServletRequest
	 * @param apprParam 审批信息
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/apprCommFiling")
	public HttpRespEnvelope apprCommFiling(HttpServletRequest request, @ModelAttribute FilingApprParam apprParam) {
		try {
			super.verifySecureToken(request);
			String applyId = request.getParameter("applyId");//申请编号
			String custId = request.getParameter("custId");//获取登陆客户号
			//非空验证
	        RequestUtil.verifyParamsIsNotEmpty(
	        	new Object[] {applyId, RespCode.APS_APPLYID_INVALID},
	        	new Object[] {apprParam.getStatus(), RespCode.APS_APPR_STATUS_INVALID}
	        );
	        //长度验证
            RequestUtil.verifyParamsLength(
                new Object[] {apprParam.getApprRemark(), 0, 300, RespCode.APS_APPR_REMARK_LENGTH_INVALID}
            );
	        apprParam.setApprOperator(custId);
	        this.entFilingService.apprCommFiling(apprParam);
	        return new HttpRespEnvelope();
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}
	
	/**
	 * 方法名称：审批企业异议报备
	 * 方法描述：地区平台审批企业异议报备
	 * @param request HttpServletRequest
	 * @param apprParam 审批信息
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/apprDisaFiling")
	public HttpRespEnvelope apprDisaFiling(HttpServletRequest request, @ModelAttribute FilingApprParam apprParam) {
		try {
			super.verifySecureToken(request);
			String applyId = request.getParameter("applyId");//申请编号
			String custId = request.getParameter("custId");//获取登陆客户号
			//非空验证
	        RequestUtil.verifyParamsIsNotEmpty(
	        	new Object[] {applyId, RespCode.APS_APPLYID_INVALID},
	        	new Object[] {apprParam.getStatus(), RespCode.APS_APPR_STATUS_INVALID}
	        );
	        //长度验证
            RequestUtil.verifyParamsLength(
                new Object[] {apprParam.getApprRemark(), 0, 300, RespCode.APS_APPR_REMARK_LENGTH_INVALID}
            );
	        apprParam.setApprOperator(custId);
	        this.entFilingService.apprDisaFiling(apprParam);
	        return new HttpRespEnvelope();
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 查询报备相同项
	 *
	 * @param request 请求
	 * @return 对象
	 */
	@ResponseBody
	@RequestMapping(value = "/findSameItem")
	public HttpRespEnvelope findSameItem(HttpServletRequest request) {
		try {
			super.verifySecureToken(request);
			String applyId = request.getParameter("applyId");//申请编号
			//非空验证
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[]{applyId, RespCode.APS_APPLYID_INVALID}
			);
			return new HttpRespEnvelope(this.entFilingService.findSameItem(applyId));
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
     * 
     * 方法名称：构建附件对象
     * 方法描述：构建附件对象
     * @param aptType 附件类型
     * @param fileId 文件地址
     */
    public FilingAptitude buildFilingAptitude(Integer aptType, String fileId){
        FilingAptitude obj = new FilingAptitude();
        obj.setFileId(fileId);
        obj.setAptType(aptType);
        return obj;
    }
    
    /**
     * 
     * 方法名称：构建附件对象
     * 方法描述：构建附件对象
     * @param filingAptId 附件ID
     * @param applyId 报备企业申请编号
     * @param aptType 附件类型
     * @param fileId 文件地址
     * @param custName 编辑者
     */
    public FilingAptitude buildFilingAptitude(String filingAptId, String applyId, Integer aptType, String fileId, String custName){
        FilingAptitude obj = new FilingAptitude();
        obj.setFilingAptId(filingAptId);
        obj.setApplyId(applyId);
        obj.setAptType(aptType);
        obj.setFileId(fileId);
        if(StringUtils.isBlank(filingAptId)){
            obj.setCreatedBy(custName);
            obj.setCreatedDate(DateUtil.getCurrentDateTime());
        }else{
            obj.setUpdatedBy(custName);
            obj.setUpdateDate(DateUtil.getCurrentDateTime());
        }
        return obj;
    }
    
	@Override
	protected IBaseService getEntityService() {
		return entFilingService;
	}

}
