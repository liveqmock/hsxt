package com.gy.hsxt.access.web.scs.controllers.codeclaration;

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
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.scs.services.codeclaration.IEntFilingService;
import com.gy.hsxt.access.web.scs.services.codeclaration.IShareholderService;
import com.gy.hsxt.access.web.scs.services.common.SCSConfigService;
import com.gy.hsxt.bs.bean.apply.FilingApp;
import com.gy.hsxt.bs.bean.apply.FilingAptitude;
import com.gy.hsxt.bs.bean.apply.FilingShareHolder;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 
 * @projectName : hsxt-access-web-scs
 * @package : com.gy.hsxt.access.web.scs.controllers.codeclaration
 * @className : EntFilingController.java
 * @description : 企业报备
 * @author : maocy
 * @createDate : 2015-10-30
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("entFilingController")
public class EntFilingController extends BaseController {

	@Autowired
	private SCSConfigService scsConfigService;// 配置服务类

	@Resource
	private IEntFilingService entFilingService; // 企业报备服务类

	@Resource
	private IShareholderService shareholderService; // 股东信息服务类

	@Resource
	private RedisUtil<String> changeRedisUtil;

	/**
	 * 方法名称：企业报备查询 方法描述：企业报备查询
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param response
	 *            HttpServletResponse对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	@Override
	public HttpRespEnvelope doList(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			super.verifyPointNo(request);// 校验互生卡号
			RequestUtil.verifyQueryDate(request.getParameter("search_startDate"),
					request.getParameter("search_endDate"));// 校验时间
			request.setAttribute("serviceName", entFilingService);
			request.setAttribute("methodName", "findScrollResult");
			return super.doList(request, response);
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：异议报备查询 方法描述：异议报备查询
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param response
	 *            HttpServletResponse对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/dissentList")
	protected HttpRespEnvelope dissentList(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			super.verifyPointNo(request);// 校验互生卡号
			RequestUtil.verifyQueryDate(request.getParameter("search_startDate"),
					request.getParameter("search_endDate"));// 校验时间
			request.setAttribute("serviceName", entFilingService);
			request.setAttribute("methodName", "serviceQueryDisagreedFiling");
			return super.doList(request, response);
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查看异议报备详情 方法描述：依据申请编号查询异议报备详情
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findFilingById")
	protected HttpRespEnvelope findFilingById(HttpServletRequest request)
	{
		try
		{
			super.verifySecureToken(request);
			String applyId = request.getParameter("applyId");// 申请编号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { applyId, RespCode.SW_ENT_FILING_APPLYID_INVALID });
			Map<String, Object> resMap = this.entFilingService.findFilingById(applyId);
			if (resMap == null)
			{
				throw new HsException(RespCode.SW_ENT_FILING_DETAIL_INVALID);
			}
			return new HttpRespEnvelope(resMap);
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：提异议 方法描述：提交异议
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/raiseDissent")
	protected HttpRespEnvelope raiseDissent(HttpServletRequest request)
	{
		try
		{
			super.verifySecureToken(request);
			String custId = request.getParameter("custId");// 操作员客户号
			String applyId = request.getParameter("applyId");// 申请编号
			String remark = request.getParameter("remark");// 异议说明
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { applyId, RespCode.SW_ENT_FILING_APPLYID_INVALID },
					new Object[] { remark, RespCode.SW_ENT_FILING_RAISE_REMARK_INVALID });
			// 长度验证
			RequestUtil.verifyParamsLength(new Object[] { remark, 1, 300,
					RespCode.SW_ENT_FILING_RAISE_REMARK_LENGTH_INVALID });
			this.entFilingService.raiseDissent(applyId, custId, remark);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 企业报备-新增企业基本信息
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param filingApp
	 *            报备企业信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/createEntFiling")
	public HttpRespEnvelope createEntFiling(HttpServletRequest request, @ModelAttribute FilingApp filingApp)
	{
		try
		{
			super.verifySecureToken(request);
			String custId = request.getParameter("custId");// 获取登陆用户名称
			String pointNo = super.verifyPointNo(request);// 获取企业互生号
			String applyId = request.getParameter("applyId");// 企业申请编号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { filingApp, RespCode.SW_ENT_FILING_OBJECT_INVALID },
					new Object[] { filingApp.getEntCustName(), RespCode.SW_ENT_FILING_ENTCUSTNAME_INVALID },
					new Object[] { filingApp.getEntAddress(), RespCode.SW_ENT_FILING_ENT_ADDRESS_INVALID },
//					new Object[] {filingApp.getLicenseNo(),RespCode.SW_ENT_FILING_LICENSENO_INVALID},
					new Object[] { filingApp.getLegalName(), RespCode.SW_ENT_FILING_LEGALNAME_INVALID }, 
//					new Object[] {filingApp.getLegalType(), RespCode.SW_ENT_FILING_LEGALTYPE_INVALID }, 
					new Object[] {filingApp.getLinkman(), RespCode.SW_ENT_FILING_LINKEMAN_INVALID },
//					new Object[] {filingApp.getEntType(),RespCode.SW_ENT_FILING_ENTTYPE_INVALID},
//					new Object[] { filingApp.getLegalNo(), RespCode.SW_ENT_FILING_LEGALNO_INVALID }, 
					new Object[] {filingApp.getPhone(), RespCode.SW_ENT_FILING_PHONE_INVALID }
//			        new Object[] {filingApp.getDealArea(),RespCode.SW_ENT_FILING_DEALAREA_INVALID},
//			        new Object[] {filingApp.getReqReason(), RespCode.SW_ENT_FILING_APPRREMARK_INVALID}
					);
			// 长度验证
			RequestUtil.verifyParamsLength(
			        new Object[] { filingApp.getDealArea(), 0, 300,RespCode.SW_ENT_FILING_DEALAREA_LENGTH_INVALID }, 
			        new Object[] { filingApp.getReqReason(), 0, 300,RespCode.SW_ENT_FILING_APPRREMARK_LENGTH_INVALID }, 
			        new Object[] { filingApp.getEntCustName(), 2,128, RespCode.SW_ENT_FILING_ENTCUSTNAME_LENGTH_INVALID }, 
					new Object[] { filingApp.getEntAddress(),2, 128, RespCode.SW_ENT_FILING_ENT_ADDRESS_LENGTH_INVALID },
					new Object[] { filingApp.getLegalName(), 2, 20, RespCode.SW_ENT_FILING_LEGALNAME_LENGTH_INVALID },
					new Object[] { filingApp.getLinkman(), 2, 20, RespCode.SW_ENT_FILING_LINKEMAN_LENGTH_INVALID },
					new Object[] { filingApp.getEntType(), 0, 20, RespCode.SW_ENT_FILING_ENTTYPE_LENGTH_INVALID });
			filingApp.setOpResNo(pointNo);
			if (StringUtils.isBlank(applyId))
			{
				filingApp.setCreatedBy(custId);
				filingApp.setCreatedDate(DateUtil.getCurrentDateTime());
				applyId = this.entFilingService.createEntFiling(filingApp);
			} else
			{
				filingApp.setUpdatedBy(custId);
				filingApp.setUpdatedDate(DateUtil.getCurrentDateTime());
				this.entFilingService.updateEntFiling(filingApp);
			}
			return new HttpRespEnvelope(applyId);
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 方法名称：查询报备进行步骤 方法描述：企业报备-查询报备进行步骤
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findFilingStep")
	public HttpRespEnvelope findFilingStep(HttpServletRequest request)
	{
		try
		{
			super.verifySecureToken(request);
			String applyId = request.getParameter("applyId");// 申请编号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { applyId, RespCode.SW_ENT_FILING_APPLYID_INVALID });
			Integer step = this.entFilingService.queryFilingStep(applyId);
			return new HttpRespEnvelope(step);
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：企业报备-删除企业报备 方法描述：企业报备-删除企业报备
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delEntFilingById")
	public HttpRespEnvelope delEntFilingById(HttpServletRequest request)
	{
		try
		{
			super.verifySecureToken(request);
			String applyId = request.getParameter("applyId");// 申请编号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { applyId, RespCode.SW_ENT_FILING_APPLYID_INVALID });
			this.entFilingService.delEntFilingById(applyId);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 方法名称：查询股东信息 方法描述：企业报备-查询股东信息
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findShareholderList")
	public HttpRespEnvelope doListShareholder(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			super.verifySecureToken(request);
			String applyId = request.getParameter("search_applyId");// 申请编号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { applyId, RespCode.SW_ENT_FILING_APPLYID_INVALID });
			request.setAttribute("serviceName", shareholderService);
			request.setAttribute("methodName", "findScrollResult");
			return super.doList(request, response);
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 方法名称：新增股东信息 方法描述：企业报备-新增股东信息
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param shareHolder
	 *            股东信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/createShareholder")
	public HttpRespEnvelope createShareholder(HttpServletRequest request, @ModelAttribute FilingShareHolder shareHolder)
	{
		try
		{
			super.verifySecureToken(request);
			// 非空验证
			RequestUtil
					.verifyParamsIsNotEmpty(new Object[] { shareHolder, RespCode.SW_ENT_FILING_SHAREHOLDER_INVALID });
			shareHolder.setOptCustId(request.getParameter("custId"));// 设置客户号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { shareHolder, RespCode.SW_ENT_FILING_SHAREHOLDER_INVALID }, new Object[] {
							shareHolder.getApplyId(), RespCode.SW_ENT_FILING_APPLYID_INVALID }, new Object[] {
							shareHolder.getOptCustId(), RespCode.SW_ENT_FILING_OPTCUSTID_INVALID }, new Object[] {
							shareHolder.getShName(), RespCode.SW_ENT_FILING_SHAREHOLDER_SHNAME_INVALID }, new Object[] {
							shareHolder.getShType(), RespCode.SW_ENT_FILING_SHAREHOLDER_SHTYPE_INVALID }, new Object[] {
							shareHolder.getIdType(), RespCode.SW_ENT_FILING_SHAREHOLDER_IDTYPE_INVALID }, new Object[] {
							shareHolder.getIdNo(), RespCode.SW_ENT_FILING_SHAREHOLDER_IDNO_INVALID }, new Object[] {
							shareHolder.getPhone(), RespCode.SW_ENT_FILING_SHAREHOLDER_PHONE_INVALID });
			this.shareholderService.createShareholder(shareHolder);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * /** 方法名称：修改股东信息 方法描述：企业报备-修改股东信息
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param shareHolder
	 *            股东信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateShareholder")
	public HttpRespEnvelope updateShareholder(HttpServletRequest request, @ModelAttribute FilingShareHolder shareHolder)
	{
		try
		{
			super.verifySecureToken(request);
			// 非空验证
			RequestUtil
					.verifyParamsIsNotEmpty(new Object[] { shareHolder, RespCode.SW_ENT_FILING_SHAREHOLDER_INVALID });
			shareHolder.setOptCustId(request.getParameter("custId"));// 设置客户号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { shareHolder.getApplyId(),
					RespCode.SW_ENT_FILING_APPLYID_INVALID }, new Object[] { shareHolder,
					RespCode.SW_ENT_FILING_SHAREHOLDER_INVALID }, new Object[] { shareHolder.getFilingShId(),
					RespCode.SW_ENT_FILING_SHAREHOLDERID_INVALID }, new Object[] { shareHolder.getOptCustId(),
					RespCode.SW_ENT_FILING_OPTCUSTID_INVALID }, new Object[] { shareHolder.getShName(),
					RespCode.SW_ENT_FILING_SHAREHOLDER_SHNAME_INVALID }, new Object[] { shareHolder.getShType(),
					RespCode.SW_ENT_FILING_SHAREHOLDER_SHTYPE_INVALID }, new Object[] { shareHolder.getIdType(),
					RespCode.SW_ENT_FILING_SHAREHOLDER_IDTYPE_INVALID }, new Object[] { shareHolder.getIdNo(),
					RespCode.SW_ENT_FILING_SHAREHOLDER_IDNO_INVALID }, new Object[] { shareHolder.getPhone(),
					RespCode.SW_ENT_FILING_SHAREHOLDER_PHONE_INVALID });
			this.shareholderService.updateShareholder(shareHolder);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 方法名称：删除股东信息 方法描述：企业报备-删除股东信息
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteShareholder")
	public HttpRespEnvelope deleteShareholder(HttpServletRequest request)
	{
		try
		{
			super.verifySecureToken(request);
			String id = request.getParameter("id");// 股东编号
			String custId = request.getParameter("custId");// 操作员客户号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { id, RespCode.SW_ENT_FILING_SHAREHOLDERID_INVALID });
			this.shareholderService.deleteShareholder(id, custId);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 方法名称：初始化上传界面 方法描述：企业报备-初始化附件信息上传界面
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/initUpload")
	public HttpRespEnvelope initUpload(HttpServletRequest request)
	{
		try
		{
			super.verifySecureToken(request);
			String applyId = request.getParameter("applyId");// 报备企业申请编号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { applyId, RespCode.SW_ENT_FILING_APPLYID_INVALID });
			// 查询附件
			List<FilingAptitude> realList = this.entFilingService.queryAptByApplyId(applyId);
			// 样例数据
			List<FilingAptitude> caseList = Arrays.asList(new FilingAptitude[] {
					this.buildFilingAptitude(1, scsConfigService.getLrCredentialFrontFileId()),
					this.buildFilingAptitude(2, scsConfigService.getLrCredentialBackFileId()),
					this.buildFilingAptitude(3, scsConfigService.getBusLicenceFileId()),
					this.buildFilingAptitude(4, scsConfigService.getBankPicFileId()),
					this.buildFilingAptitude(5, scsConfigService.getSharePicFileId()), });
			// 存放数据
			Map<String, Object> resMap = new HashMap<String, Object>();
			resMap.put("caseList", caseList);
			resMap.put("realList", realList);
			return new HttpRespEnvelope(resMap);
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 方法名称：保存附件信息 方法描述：企业报备-保存附件信息
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveAptitude")
	public HttpRespEnvelope saveAptitude(HttpServletRequest request)
	{
		try
		{
			super.verifySecureToken(request);
			String custId = request.getParameter("custId");// 获取登陆用户名称
			String applyId = request.getParameter("applyId");// 报备企业申请编号
			String licensePicFileId = request.getParameter("licensePicFileId");// 营业执照正本扫描件文件Id
			String bankPicFileId = request.getParameter("bankPicFileId");// 银行资金证明文件Id
			String sharePicFileId = request.getParameter("sharePicFileId");// 合作股东证明文件Id
			String licensePicAptId = request.getParameter("licensePicAptId");// 营业执照正本扫描件附件Id
			String bankPicAptId = request.getParameter("bankPicAptId");// 银行资金证明附件Id
			String sharePicAptId = request.getParameter("sharePicAptId");// 合作股东证明附件Id
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(
			        new Object[] { applyId, RespCode.SW_ENT_FILING_APPLYID_INVALID },
					new Object[] {licensePicFileId, RespCode.SW_ENT_FILING_LICE_PIC_FILEID_INVALID }
					);
			List<FilingAptitude> aptList = new ArrayList<FilingAptitude>();
			aptList.add(this.buildFilingAptitude(licensePicAptId, applyId, 3, licensePicFileId, custId));
			if (!StringUtils.isBlank(bankPicFileId))
			{
				aptList.add(this.buildFilingAptitude(bankPicAptId, applyId, 4, bankPicFileId, custId));
			}
			if (!StringUtils.isBlank(sharePicFileId))
			{
				aptList.add(this.buildFilingAptitude(sharePicAptId, applyId, 5, sharePicFileId, custId));
			}
			List<FilingAptitude> resList = this.entFilingService.saveAptitude(aptList);
			return new HttpRespEnvelope(resList);
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 验证码验证
	 * 
	 * @param custId
	 * @param verificationCode
	 * @throws HsException
	 */
	public void verificationCode(String custId, String verificationCode, String codeType) throws HsException
	{
		// 获取验证码
		String vCode = changeRedisUtil.get(custId.trim() + "_" + codeType, String.class);
		// 判断为空
		if (StringUtils.isEmpty(vCode))
		{
			throw new HsException(ASRespCode.VERIFICATION_CODE_INVALID);
		}
		// 判断相等
		if (!verificationCode.toUpperCase().equals(vCode.toUpperCase()))
		{
			throw new HsException(ASRespCode.VERIFICATION_CODE_ERROR);
		}
	}

	/**
	 * 方法名称：提交报备申请 方法描述：企业报备-提交报备申请
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/submitFiling")
	public HttpRespEnvelope submitFiling(HttpServletRequest request)
	{
		try
		{
			super.verifySecureToken(request);
			String custId = request.getParameter("custId");// 获取登陆用户名称
			String applyId = request.getParameter("applyId");// 报备企业申请编号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { applyId, RespCode.SW_ENT_FILING_APPLYID_INVALID });
			this.entFilingService.submitFiling(applyId, custId);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/isExistSameOrSimilar")
	public HttpRespEnvelope isExistSameOrSimilar(HttpServletRequest request)
	{
		try
		{
			super.verifySecureToken(request);
			String applyId = request.getParameter("applyId");// 报备企业申请编号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { applyId, RespCode.SW_ENT_FILING_APPLYID_INVALID });
			String sameStr=null;
			Integer sameStauts=null;
			//需要对企业相关字段验证：想似性
			sameStauts=this.entFilingService.isExistSameOrSimilar(applyId);
			if(sameStauts!=null&&sameStauts==1){
				sameStr="报备系统中已存在与该企业相似的资料，是否提交报备？";
			}else if(sameStauts!=null&&sameStauts==2){
				sameStr="该企业已经被报备，提交报备失败.";
			}else{
				sameStr="0";
			}
			return new HttpRespEnvelope(sameStr);
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}
	

	/**
	 * 方法名称：查询企业基本信息 方法描述：根据申请编号查询企业基本信息
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getEntFilingById")
	public HttpRespEnvelope getEntFilingById(HttpServletRequest request)
	{
		try
		{
			super.verifySecureToken(request);
			String applyId = request.getParameter("applyId");// 报备企业申请编号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { applyId, RespCode.SW_ENT_FILING_APPLYID_INVALID });
			FilingApp obj = this.entFilingService.queryFilingBaseInfoById(applyId);
			if (obj == null)
			{
				throw new HsException(RespCode.SW_ENT_FILING_BASEINFO_INVALID);
			}
			return new HttpRespEnvelope(obj);
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：构建附件对象 方法描述：构建附件对象
	 * 
	 * @param aptType
	 *            附件类型
	 * @param fileId
	 *            文件地址
	 */
	public FilingAptitude buildFilingAptitude(Integer aptType, String fileId)
	{
		FilingAptitude obj = new FilingAptitude();
		obj.setFileId(fileId);
		obj.setAptType(aptType);
		return obj;
	}

	/**
	 * 
	 * 方法名称：构建附件对象 方法描述：构建附件对象
	 * 
	 * @param filingAptId
	 *            附件ID
	 * @param applyId
	 *            报备企业申请编号
	 * @param aptType
	 *            附件类型
	 * @param fileId
	 *            文件地址
	 * @param custName
	 *            编辑者
	 */
	public FilingAptitude buildFilingAptitude(String filingAptId, String applyId, Integer aptType, String fileId,
			String custName)
	{
		FilingAptitude obj = new FilingAptitude();
		obj.setFilingAptId(filingAptId);
		obj.setApplyId(applyId);
		obj.setAptType(aptType);
		obj.setFileId(fileId);
		if (StringUtils.isBlank(filingAptId))
		{
			obj.setCreatedBy(custName);
			obj.setCreatedDate(DateUtil.getCurrentDateTime());
		} else
		{
			obj.setUpdatedBy(custName);
			obj.setUpdateDate(DateUtil.getCurrentDateTime());
		}
		return obj;
	}

	@Override
	protected IBaseService getEntityService()
	{
		return entFilingService;
	}

}
