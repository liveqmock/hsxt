package com.gy.hsxt.access.web.scs.controllers.codeclaration;

import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.scs.services.codeclaration.*;
import com.gy.hsxt.access.web.scs.services.common.IPubParamService;
import com.gy.hsxt.access.web.scs.services.common.SCSConfigService;
import com.gy.hsxt.bs.bean.apply.*;
import com.gy.hsxt.bs.bean.base.OptInfo;
import com.gy.hsxt.bs.bean.bm.Increment;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.lcs.bean.LocalInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @projectName : hsxt-access-web-scs
 * @package : com.gy.hsxt.access.web.scs.controllers.codeclaration
 * @className : EntDeclareController.java
 * @description : 企业申报/新增/修改
 * @author : maocy
 * @createDate : 2015-10-30
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@Controller
@RequestMapping("entDeclareController")
public class EntDeclareController extends BaseController {

	@Autowired
	private SCSConfigService scsConfigService;// 配置服务类

	@Resource
	private IBankService bankService;// 企业申报-银行服务类

	@Resource
	private IPubParamService pubService;// 系统服务类

	@Resource
	private ILinkService linkService;// 企业申报-联系人服务类

	@Resource
	private IBizRegInfoService bizRegInfoService;// 企业申报-工商登记服务类

	@Resource
	private IRegInfoService regInfoService;// 企业申报-系统注册信息服务类

	@Resource
	private IDeclareAptitudeService declareAptitudeService;// 企业申报-附件信息服务类

	@Resource
	private IEntDeclareService entDeclareService;// 企业申报服务类

	@Resource
	private RedisUtil<String> changeRedisUtil;

	/**
	 * 
	 * 方法名称：查询联系人信息 方法描述：企业申报-查询联系人信息，同时返回联系人授权委托书文件ID
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findLinkmanByApplyId")
	public HttpRespEnvelope findLinkmanByApplyId(HttpServletRequest request) {
		try {
			super.verifySecureToken(request);
			String applyId = request.getParameter("applyId");// 申请编号
			DeclareLinkman link = null;
			if (!StringUtils.isBlank(applyId)) {
				link = this.linkService.findLinkmanByApplyId(applyId);
			}
			// 获取被申报企业客户类型
			DeclareRegInfo regInfo = regInfoService.findDeclareByApplyId(applyId);
			String venBefFlag = "false";// 是否需要填写创业帮扶协议
			// 如果为托管企业并且选择的是创业资源才需要填写创业帮扶协议
			if (regInfo != null && regInfo.getToCustType() == 3 && regInfo.getToBuyResRange() == 2) {
				venBefFlag = "true";
			}
			Map<String, Object> resMap = new HashMap<String, Object>();
			resMap.put("link", link);
			resMap.put("venBefFlag", venBefFlag);
			resMap.put("fileId", scsConfigService.getPowerOfAttorneyFileId());
			return new HttpRespEnvelope(resMap);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：保存联系人信息 方法描述：企业申报新增-保存联系人信息
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param link
	 *            企业联系信息对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveLinkInfo")
	public HttpRespEnvelope saveLinkInfo(HttpServletRequest request,
			@ModelAttribute DeclareLinkman link) {
		try {
			super.verifySecureToken(request);
			String custId = request.getParameter("custId");// 获取登陆客户号
			String custName = request.getParameter("cookieOperNoName");// 获取登陆用户名称
			String isNew = request.getParameter("isNew");// 是否为新增
			String optEntName = request.getParameter("custEntName");//获取登陆客户企业名称
			String venBefFlag = request.getParameter("venBefFlag");//是否需要填写创业帮扶协议
			String venBefProtocolFileId = request.getParameter("venBefProtocolFileId");//创业帮扶协议文件ID
            String venBefProtocolAptId = request.getParameter("venBefProtocolAptId");//创业帮扶协议附件ID
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { link,
					RespCode.SW_ENT_LINKINFO_OBJ_INVALID },
					new Object[] { link.getApplyId(),
							RespCode.SW_ENT_FILING_APPLYID_INVALID },
					new Object[] { link.getLinkman(),
							RespCode.SW_ENT_LINKINFO_LINKMAN_INVALID },
					new Object[] { link.getMobile(),
							RespCode.SW_ENT_LINKINFO_MOBILE_INVALID }
			// new Object[] {link.getAddress(),
			// RespCode.SW_ENT_LINKINFO_ADDRESS_INVALID},
			// new Object[] {link.getZipCode(),
			// RespCode.SW_ZIPCODE_INVALID},
			// new Object[] {link.getWebSite(),
			// RespCode.SW_ENT_LINKINFO_WEBSITE_INVALID},
			// new Object[] {link.getEmail(),
			// RespCode.SW_ENT_LINKINFO_EMAIL_INVALID},
			// new Object[] {link.getPhone(),
			// RespCode.SW_OFFICE_PHONE_INVALID},
			// new Object[] {link.getFax(), RespCode.SW_FAX_INVALID},
			// new Object[] {link.getJob(),
			// RespCode.SW_ENT_LINKINFO_LINKMAN_JOB_INVALID},
			// new Object[] {link.getQq(),
			// RespCode.SW_ENT_LINKINFO_QQ_INVALID},
			// new Object[] { link.getCertificateFile(),
			// RespCode.SW_ENT_LINKINFO_CERT_INVALID }
					);
			// 长度验证
			RequestUtil.verifyParamsLength(new Object[] { link.getLinkman(), 2,
					20, RespCode.SW_ENT_LINKINFO_LINKMAN_LENGTH_INVALID });
			// 长度验证
			if (!StringUtils.isBlank(link.getAddress())) {
				RequestUtil.verifyParamsLength(new Object[] {
						link.getAddress(), 2, 128,
						RespCode.SW_ENT_LINKINFO_ADDRESS_LENGTH_INVALID });
			}
			// 校验复核备注长度
			if (!StringUtils.isBlank(link.getOptRemark())) {
				RequestUtil.verifyParamsLength(new Object[] {
						link.getOptRemark(), 0, 300,
						RespCode.SW_VIEW_MARK_LENGTH_INVALID });
			}
			//托管企业创业帮扶协议必填
            if("true".equals(venBefFlag)){
                RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] {venBefProtocolFileId, RespCode.APS_DECLARE_PRO_PIC_FILEID_INVALID}
                );
                link.setProtocolAptitude(this.buildDeclareAptitude(venBefProtocolAptId, link.getApplyId(), 8, venBefProtocolFileId, custId, custName));
            }
			link.setOptCustId(custId);
			link.setOptName(custName);
			link.setOptEntName(optEntName);
			if ("true".equals(isNew)) {
				link = this.linkService.createLinkInfo(link);
			} else {
				link = this.linkService.updateLinkInfo(link);
			}
			return new HttpRespEnvelope(link);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查询银行信息 方法描述：企业申报-查询银行信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findBankByApplyId")
	public HttpRespEnvelope findBankByApplyId(HttpServletRequest request) {
		try {
			super.verifySecureToken(request);
			String applyId = request.getParameter("applyId");// 申请编号
			DeclareBank bank = this.bankService.findBankByApplyId(applyId);
			if (bank == null) {
				bank = new DeclareBank();
			}
			Map<String, Object> resMap = new HashMap<String, Object>();// 存放初始化数据
			LocalInfo info = pubService.findSystemInfo();
			resMap.put("countryNo", info.getCountryNo());
			resMap.put("currencyCode", info.getCurrencyCode());
			resMap.put("currencyName", info.getCurrencyNameCn());
			DeclareBizRegInfo regInfo = this.bizRegInfoService
					.queryDeclareEntByApplyId(applyId);
			resMap.put("accountName",
					regInfo == null ? "" : regInfo.getEntName());
			resMap.put("bank", bank);
			return new HttpRespEnvelope(resMap);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：保存银行信息 方法描述：企业申报新增-保存银行信息信息
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param bank
	 *            银行信息对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveBankInfo")
	public HttpRespEnvelope saveBankInfo(HttpServletRequest request,
			@ModelAttribute DeclareBank bank) {
		try {
			super.verifySecureToken(request);
			String custId = request.getParameter("custId");// 获取登陆客户号
			String custName = request.getParameter("cookieOperNoName");// 获取登陆用户名称
			String accountId = request.getParameter("accountId");// 银行账号ID
			String optEntName = request.getParameter("custEntName");//获取登陆客户企业名称
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { bank,
					RespCode.SW_BANKINFO_INVALID },
					new Object[] { bank.getAccountName(),
							RespCode.SW_BANKNAME_INVALID },
					new Object[] { bank.getCurrencyCode(),
							RespCode.SW_CUR_CODE_INVALID },
					new Object[] { bank.getBankCode(),
							RespCode.SW_BANKINFO_BANKCODE_INVALID },
					new Object[] { bank.getCountryNo(),
							RespCode.SW_BANKINFO_COUNTRYNO_INVALID },
					new Object[] { bank.getProvinceNo(),
							RespCode.SW_BANKINFO_PROVINCENO_INVALID },
					new Object[] { bank.getCityNo(),
							RespCode.SW_BANKINFO_CITYNO_INVALID },
					new Object[] { bank.getAccountNo(),
							RespCode.SW_BANK_INVALID });
			// 校验复核备注长度
			if (!StringUtils.isBlank(bank.getOptRemark())) {
				RequestUtil.verifyParamsLength(new Object[] {
						bank.getOptRemark(), 0, 300,
						RespCode.SW_VIEW_MARK_LENGTH_INVALID });
			}
			bank.setOptCustId(custId);
			bank.setOptName(custName);
			bank.setOptEntName(optEntName);
			if (StringUtils.isBlank(accountId)) {
				accountId = this.bankService.createBankInfo(bank);
			} else {
				this.bankService.updateBankInfo(bank);
			}
			return new HttpRespEnvelope(accountId);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查询工商登记信息 方法描述：企业申报-查询工商登记信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findDeclareEntByApplyId")
	public HttpRespEnvelope findDeclareEntByApplyId(HttpServletRequest request) {
		try {
			super.verifySecureToken(request);
			String applyId = request.getParameter("applyId");// 申请编号
			DeclareBizRegInfo bizInfo = this.bizRegInfoService
					.queryDeclareEntByApplyId(applyId);
			if (bizInfo == null) {
				bizInfo = new DeclareBizRegInfo();
			}
			return new HttpRespEnvelope(bizInfo);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：保存工商登记信息 方法描述：企业申报新增-保存工商登记信息
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param bizInfo
	 *            工商登记信息对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveDeclareEnt")
	public HttpRespEnvelope saveDeclareEnt(HttpServletRequest request,
			@ModelAttribute DeclareBizRegInfo bizInfo) {
		try {
			super.verifySecureToken(request);
			String custId = request.getParameter("custId");// 获取登陆客户号
			String custName = request.getParameter("cookieOperNoName");// 获取登陆用户名称
			String isNew = request.getParameter("isNew");// 新增标志
			String optEntName = request.getParameter("custEntName");//获取登陆客户企业名称
			String busLicenceFileiId = request.getParameter("busLicenceFileiId");//营业执照文件ID
            String busLicenceApitId = request.getParameter("busLicenceApitId");//营业执照附件ID
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { bizInfo, RespCode.SW_BIZREG_INVALID },
					new Object[] { bizInfo.getEntName(),
							RespCode.SW_ENT_FILING_ENTCUSTNAME_INVALID },
					new Object[] { bizInfo.getEntAddress(),
							RespCode.SW_ENT_FILING_ENT_ADDRESS_INVALID },
					new Object[] { bizInfo.getLegalName(),
							RespCode.SW_ENT_FILING_LEGALNAME_INVALID },
					// new Object[] {bizInfo.getLinkmanMobile(),
					// RespCode.SW_BIZREG_MOBILE_INVALID},
					// new Object[] {bizInfo.getEntType(),
					// RespCode.SW_BIZREG_ENTTYPE_INVALID},
					new Object[] { bizInfo.getLicenseNo(),
							RespCode.SW_ENT_FILING_LICENSENO_INVALID },
			        new Object[] {busLicenceFileiId, ASRespCode.APS_ENT_FILING_LICE_PIC_FILEID_INVALID}
//					new Object[] { bizInfo.getOrgNo(),
//							RespCode.SW_ORGNO_INVALID },
//					new Object[] { bizInfo.getTaxNo(),
//							RespCode.SW_TAXNO_INVALID }
					
			// new Object[] {bizInfo.getEstablishingDate(),
			// RespCode.SW_BIZREG_ESTADATE_INVALID},
			// new Object[] {bizInfo.getLimitDate(),
			// RespCode.SW_BIZREG_LIMITDATE_INVALID},
			// new Object[] {bizInfo.getDealArea(),
			// RespCode.SW_ENT_FILING_DEALAREA_INVALID}
					);
			// 长度验证
			RequestUtil
					.verifyParamsLength(
							new Object[] {
									bizInfo.getEntName(),
									2,
									128,
									RespCode.SW_ENT_FILING_ENTCUSTNAME_LENGTH_INVALID },
							new Object[] {
									bizInfo.getEntAddress(),
									2,
									128,
									RespCode.SW_ENT_FILING_ENT_ADDRESS_LENGTH_INVALID },
							new Object[] {
									bizInfo.getLegalName(),
									2,
									20,
									RespCode.SW_ENT_FILING_LEGALNAME_LENGTH_INVALID },
							new Object[] { bizInfo.getEntType(), 0, 20,
									RespCode.SW_BIZREG_ENTTYPE_LENGTH_INVALID },
							new Object[] { bizInfo.getLimitDate(), 0, 50,
									RespCode.SW_BIZREG_LIMITDATE_LENGTH_INVALID },
							new Object[] {
									bizInfo.getDealArea(),
									0,
									300,
									RespCode.SW_ENT_FILING_DEALAREA_LENGTH_INVALID });
			// 校验复核备注长度
			if (!StringUtils.isBlank(bizInfo.getOptRemark())) {
				RequestUtil.verifyParamsLength(new Object[] {
						bizInfo.getOptRemark(), 0, 300,
						RespCode.SW_VIEW_MARK_LENGTH_INVALID });
			}
			bizInfo.setLicenseAptitude(this.buildDeclareAptitude(busLicenceApitId, bizInfo.getApplyId(), 3, busLicenceFileiId, custId, custName));
			bizInfo.setOptCustId(custId);
			bizInfo.setOptName(custName);
			bizInfo.setOptEntName(optEntName);
			if ("true".equals(isNew)) {
				bizInfo = this.bizRegInfoService.createDeclareEnt(bizInfo);
			} else {
				bizInfo = this.bizRegInfoService.updateDeclareEnt(bizInfo);
			}
			return new HttpRespEnvelope(bizInfo);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：根据申请编号查询客户类型 方法描述：企业申报新增-根据申请编号查询客户类型及默认增值信息
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findCustTypeByApplyId")
	public HttpRespEnvelope findCustTypeByApplyId(HttpServletRequest request) {
		try {
			super.verifySecureToken(request);
			String applyId = request.getParameter("applyId");// 申请编号
			Integer custType = this.regInfoService
					.getCustTypeByApplyId(applyId);
			return new HttpRespEnvelope(custType);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查询系统注册信息 方法描述：企业申报-查询系统注册信息
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findDeclareByApplyId")
	public HttpRespEnvelope findDeclareByApplyId(HttpServletRequest request) {
		try {
			super.verifySecureToken(request);
			String applyId = request.getParameter("applyId");// 申请编号
			DeclareRegInfo regInfo = null;
			if (!StringUtils.isBlank(applyId)) {
				regInfo = this.regInfoService.findDeclareByApplyId(applyId);// 查询注册基本信息
			}
			return new HttpRespEnvelope(regInfo);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查询成员企业、托管企业配额数和可用互生号列表 方法描述：企业申报-成员企业、托管企业配额数和可用互生号列表
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/findResNoListAndQuota")
	public HttpRespEnvelope findResNoListAndQuota(HttpServletRequest request) {
		try {
			super.verifySecureToken(request);
			String serResNo = super.verifyPointNo(request);
			String buyResRange = request.getParameter("buyResRange");// 启用消费者资源类型
			String toCustType = request.getParameter("toCustType");// 申请类别
			Map<String, Object> resMap = new HashMap<String, Object>();// 存放数据
			Map<String, Object> map = this.regInfoService.getResNoListAndQuota(
					serResNo, CommonUtils.toInteger(toCustType),
					CommonUtils.toInteger(buyResRange));
			List<String> resNoList = (List<String>) map.get("resNoList");
			resMap.put("availQuota", map.get("quota"));
			if (resNoList != null && resNoList.size() > 0) {
				resMap.put("defaultEntResNo", resNoList.get(0));
			} else {
				resMap.put("defaultEntResNo", "");
			}
			resMap.put("entResNoList", resNoList);
			return new HttpRespEnvelope(resMap);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查询管理公司信息和服务公司配额数 方法描述：企业申报-查询系统注册信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findManageEntAndQuota")
	public HttpRespEnvelope findManageEntAndQuota(HttpServletRequest request) {
		try {
			super.verifySecureToken(request);
			String countryNo = request.getParameter("countryNo");// 所属国家
			String provinceNo = request.getParameter("provinceNo");// 所属省份
			String cityNo = request.getParameter("cityNo");// 所属城市
			// 查询管理公司信息和服务公司配额数
			Map<String, Object> map = this.regInfoService
					.queryManageEntAndQuota(countryNo, provinceNo, cityNo);
			return new HttpRespEnvelope(map);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查询增值信息 方法描述：企业申报-查询增值信息
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findIncrement")
	public HttpRespEnvelope findIncrement(HttpServletRequest request) {
		try {
			super.verifySecureToken(request);
			String resNo = request.getParameter("resNo");// 企业互生号
			Increment increment= this.regInfoService.queryIncrement(resNo);
			return new HttpRespEnvelope(increment);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：保存系统注册信息 方法描述：企业申报新增-保存系统注册信息
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param regInfo
	 *            系统注册信息对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveDeclare")
	public HttpRespEnvelope saveDeclare(HttpServletRequest request,
			@ModelAttribute DeclareRegInfo regInfo) {
		try {
			super.verifySecureToken(request);
			String pointNo = super.verifyPointNo(request);
			String custId = request.getParameter("custId");// 获取登陆客户号
			String custName = request.getParameter("cookieOperNoName");// 获取登陆用户名称
			String applyId = request.getParameter("applyId");// 申请编号
			String entCustId = request.getParameter("entCustId");// 获取登陆企业客户号
			String optEntName = request.getParameter("custEntName");// 获取登陆客户企业名称
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { regInfo,
					RespCode.SW_BIZREG_INVALID },
					new Object[] { regInfo.getToEntCustName(),
							RespCode.SW_REGINFO_ENTNAME_INVALID },
					new Object[] { regInfo.getCountryNo(),
							RespCode.SW_REGINFO_COUNTRYNO_INVALID },
					new Object[] { regInfo.getProvinceNo(),
							RespCode.SW_REGINFO_PROVINCENO_INVALID },
					new Object[] { regInfo.getCityNo(),
							RespCode.SW_REGINFO_CITY_INVALID }, new Object[] {
							regInfo.getToCustType(),
							RespCode.SW_REGINFO_TOCUSTTYPE_INVALID },
					new Object[] { regInfo.getToMResNo(),
							RespCode.SW_REGINFO_TOMRESNO_INVALID });
			// 长度验证
			RequestUtil.verifyParamsLength(new Object[] {
					regInfo.getToEntCustName(), 2, 128,
					RespCode.SW_REGINFO_ENTNAME_LENGTH_INVALID });
			// 验证英文名称
//			if (!StringUtils.isBlank(regInfo.getToEntEnName())) {
//				RequestUtil.verifyParamsLength(new Object[] {
//						regInfo.getToEntEnName(), 2, 128,
//						RespCode.SW_REGINFO_ENTENNAME_LENGTH_INVALID });
//			}
			// 成员企业校验
			if (regInfo.getToCustType() == 2) {
				RequestUtil.verifyParamsIsNotEmpty(
						new Object[] { regInfo.getToEntResNo(),
								RespCode.SW_REGINFO_TOENTRESNO_INVALID },
						new Object[] { regInfo.getToBuyResRange(),
								RespCode.SW_REGINFO_TOBUY_RESRANGE_INVALID });
			} else if (regInfo.getToCustType() == 3)// 托管企业校验
			{
				RequestUtil.verifyParamsIsNotEmpty(
						new Object[] { regInfo.getToEntResNo(),
								ASRespCode.SW_REGINFO_TOENTRESNO_INVALID },
						new Object[] { regInfo.getToBuyResRange(),
								ASRespCode.SW_REGINFO_TOBUY_RESRANGE_INVALID });
			} else if (regInfo.getToCustType() == 4)// 服务公司校验
			{
				RequestUtil.verifyParamsIsNotEmpty(new Object[] {
						regInfo.getToBusinessType(),
						ASRespCode.AS_BUSINESSTYPE_INVALID });
			}
			// 校验复核备注长度
			if (!StringUtils.isBlank(regInfo.getOptRemark())) {
				RequestUtil.verifyParamsLength(new Object[] {
						regInfo.getOptRemark(), 0, 300,
						RespCode.SW_VIEW_MARK_LENGTH_INVALID });
			}
			regInfo.setOptCustId(custId);
			regInfo.setOptName(custName);
			regInfo.setFrEntCustId(entCustId);
			regInfo.setFrEntCustName(optEntName);
			regInfo.setFrEntResNo(pointNo);
			regInfo.setSpreadEntCustId(entCustId);
			regInfo.setSpreadEntCustName(optEntName);
			regInfo.setSpreadEntResNo(pointNo);
			regInfo.setOptEntName(optEntName);
			if (StringUtils.isBlank(applyId)) {
				applyId = this.regInfoService.createDeclare(regInfo);
			} else {
				this.regInfoService.updateDeclare(regInfo);
			}
			return new HttpRespEnvelope(applyId);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 校验互生号是否被占用
	 *
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkValidEntResNo")
	public HttpRespEnvelope checkValidEntResNo(HttpServletRequest request) {
		try {
			String entResNo = request.getParameter("toEntResNo");//获取登陆用户名称
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[]{entResNo, BSRespCode.BS_DECLARE_INVALID_RES_NO}
			);
			Boolean validate = this.entDeclareService.checkValidEntResNo(entResNo);
			return new HttpRespEnvelope(validate);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}
	/**
	 * 
	 * 方法名称：查询企业资料上传 方法描述：企业申报-查询企业资料上传，同时初始化样例图片地址
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findAptitudeByApplyId")
	public HttpRespEnvelope findAptitudeByApplyId(HttpServletRequest request) {
		try {
			super.verifySecureToken(request);
			String applyId = request.getParameter("applyId");// 申请编号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { applyId,
					RespCode.SW_ENT_FILING_APPLYID_INVALID });
			// 查询附件
			Map<String, Object> aptMap = this.declareAptitudeService.queryAptitudeWithRemarkByApplyId(applyId);
			// 获取被申报企业客户类型
			DeclareRegInfo regInfo = regInfoService.findDeclareByApplyId(applyId);
			String venBefFlag = "false";// 是否需要填写创业帮扶协议
			// 如果为托管企业并且选择的是创业资源才需要填写创业帮扶协议
			if (regInfo != null && regInfo.getToCustType() == 3
					&& regInfo.getToBuyResRange() == 2) {
				venBefFlag = "true";
			}
			DeclareBizRegInfo bizRegInfo = bizRegInfoService.queryDeclareEntByApplyId(applyId);
			// 存放数据
			Map<String, Object> resMap = new HashMap<String, Object>();
			resMap.put("aptRemark", aptMap.get("REMARK"));
			resMap.put("realList", aptMap.get("APTITUDE_LIST"));
			resMap.put("creType", bizRegInfo.getLegalCreType());
			resMap.put("venBefFlag", venBefFlag);
			return new HttpRespEnvelope(resMap);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：保存企业资料上传 方法描述：企业申报新增-保存企业资料上传
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param info
	 *            操作员对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveDeclareAptitude")
	public HttpRespEnvelope saveDeclareAptitude(HttpServletRequest request,
			@ModelAttribute OptInfo info) {
		try {
			super.verifySecureToken(request);
			String optEntName = request.getParameter("custEntName");// 获取登陆客户企业名称
			String custId = request.getParameter("custId");// 获取登陆客户号
			String applyId = request.getParameter("applyId");// 报备企业申请编号
			String venBefFlag = request.getParameter("venBefFlag");// 是否需要填写创业帮扶协议
			String custName = request.getParameter("custName");// 操作员名字
			String cookieOperNoName = request.getParameter("cookieOperNoName");// 操作员名字
			String aptRemark = request.getParameter("aptRemark");// 备注说明
			String lrCredentialFrontFileId = request
					.getParameter("lrCredentialFrontFileId");// 法人代表证件正面文件ID
			String lrCredentialBackFileId = request
					.getParameter("lrCredentialBackFileId");// 法人代表证件反面文件ID
			String busLicenceFileId = request.getParameter("busLicenceFileId");// 营业执照正本扫描件文件ID
			String organizationFileId = request
					.getParameter("organizationFileId");// 组织机构代码证正本扫描件文件ID
			String taxplayerFileId = request.getParameter("taxplayerFileId");// 税务登记证正本扫描件文件ID
			String venBefProtocolFileId = request
					.getParameter("venBefProtocolFileId");// 创业帮扶协议件文件ID
			String lrCredentialFrontAptitudeId = request
					.getParameter("lrCredentialFrontAptitudeId");// 法人代表证件正面资质附件ID
			String lrCredentialBackAptitudeId = request
					.getParameter("lrCredentialBackAptitudeId");// 法人代表证件反面资质附件ID
			String busLicenceAptitudeId = request
					.getParameter("busLicenceAptitudeId");// 营业执照正本扫描件资质附件ID
			String organizPicAptitudeId = request
					.getParameter("organizPicAptitudeId");// 组织机构代码证正本扫描件资质附件ID
			String taxRegPicAptitudeId = request
					.getParameter("taxRegPicAptitudeId");// 税务登记证正本扫描件资质附件ID
			String venBefProtocolAptitudeId = request
					.getParameter("venBefProtocolAptitudeId");// 创业帮扶协议件附件ID
			String creType = request.getParameter("creType");//证件类型
			if (StringUtils.isEmpty(creType) || creType.equals("1")) {
				HsAssert.hasText(lrCredentialBackFileId,RespCode.SW_ENT_FILING_BACK_PIC_FILEID_INVALID);
			}
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { applyId,
					RespCode.SW_ENT_FILING_APPLYID_INVALID }, new Object[] {
					lrCredentialFrontFileId,
					RespCode.SW_ENT_FILING_POSI_PIC_FILEID_INVALID },
//					new Object[] { lrCredentialBackFileId,RespCode.SW_ENT_FILING_BACK_PIC_FILEID_INVALID },
					new Object[] { busLicenceFileId,
							RespCode.SW_ENT_FILING_LICE_PIC_FILEID_INVALID },
					new Object[] { organizationFileId,
							RespCode.SW_DECLARE_ORG_PIC_FILEID_INVALID },
					new Object[] { taxplayerFileId,
							RespCode.SW_DECLARE_TAX_PIC_FILEID_INVALID });
			// 托管企业创业帮扶协议必填
			if ("true".equals(venBefFlag)) {
				RequestUtil.verifyParamsIsNotEmpty(new Object[] {
						venBefProtocolFileId,
						RespCode.SW_DECLARE_PRO_PIC_FILEID_INVALID });
			}
			// 校验复核备注长度
			if (!StringUtils.isBlank(info.getOptRemark())) {
				RequestUtil.verifyParamsLength(new Object[] {
						info.getOptRemark(), 0, 300,
						RespCode.SW_VIEW_MARK_LENGTH_INVALID });
			}
			//校验备注说长度
			RequestUtil.verifyParamsLength(new Object[] {
				aptRemark, 0, 300, ASRespCode.AS_VIEW_MARK_LENGTH_INVALID
			});
			// 组织数据
			List<DeclareAptitude> aptList = new ArrayList<DeclareAptitude>();
			aptList.add(this.buildDeclareAptitude(lrCredentialFrontAptitudeId,
					applyId, 1, lrCredentialFrontFileId, custId, custName));
			if (StringUtils.isEmpty(creType) || creType.equals("1")) {
				aptList.add(this.buildDeclareAptitude(lrCredentialBackAptitudeId,applyId, 2, lrCredentialBackFileId, custId, custName));
			}
			aptList.add(this.buildDeclareAptitude(busLicenceAptitudeId,
					applyId, 3, busLicenceFileId, custId, custName));
			aptList.add(this.buildDeclareAptitude(organizPicAptitudeId,
					applyId, 6, organizationFileId, custId, custName));
			aptList.add(this.buildDeclareAptitude(taxRegPicAptitudeId, applyId,
					7, taxplayerFileId, custId, custName));
			// 托管企业创业帮扶协议必填
			if ("true".equals(venBefFlag)) {
				aptList.add(this.buildDeclareAptitude(venBefProtocolAptitudeId,
						applyId, 8, venBefProtocolFileId, custId, custName));
			}
			info.setOptCustId(custId);
			info.setOptName(cookieOperNoName);
			info.setOptEntName(optEntName);
			// 保存数据
			List<DeclareAptitude> temp = this.declareAptitudeService.saveAptitude(aptList, info, aptRemark);
			return new HttpRespEnvelope(temp);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：提交申报 方法描述：企业申报-提交申报
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/submitDeclare")
	public HttpRespEnvelope submitDeclare(HttpServletRequest request) {
		try {
			super.verifySecureToken(request);
			String applyId = request.getParameter("applyId");// 申请编号
			String optCustId = request.getParameter("custId");// 获取登陆客户号
			String optName = request.getParameter("cookieOperNoName");// 获取登陆客户名称
			String optEntName = request.getParameter("custEntName");// 获取登陆客户企业名称
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { applyId,
					RespCode.SW_ENT_FILING_APPLYID_INVALID });
			OptInfo operator = new OptInfo();
			operator.setOptCustId(optCustId);
			operator.setOptName(optName);
			operator.setOptEntName(optEntName);
			// 查询工商登记信息
			DeclareBizRegInfo bizInfo = this.bizRegInfoService
					.queryDeclareEntByApplyId(applyId);
			// 查询联系人信息
			DeclareLinkman link = this.linkService
					.findLinkmanByApplyId(applyId);
			if (!(bizInfo.getLegalName().equals(link.getLinkman()))
					&& (link.getCertificateFile() == null)) {
				throw new HsException(ASRespCode.AS_LEGALNAME_LINKMAN_NOT_EQUAL);
			}
			this.entDeclareService.submitDeclare(applyId, operator);
			return new HttpRespEnvelope();
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查询服务公司可用互生号 方法描述：企业申报-查询服务公司可用互生号
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param request
	 *            HttpServletResponse对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findSerResNos")
	public HttpRespEnvelope findSerResNos(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			super.verifySecureToken(request);
			request.setAttribute("serviceName", entDeclareService);
			request.setAttribute("methodName", "findSerResNos");
			return super.doList(request, response);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查询成员企业、托管企业可用互生号 方法描述：企业申报-查询成员企业、托管企业可用互生号
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param request
	 *            HttpServletResponse对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findEntResNos")
	public HttpRespEnvelope findEntResNos(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			super.verifySecureToken(request);
			request.setAttribute("serviceName", entDeclareService);
			request.setAttribute("methodName", "findEntResNos");
			return super.doList(request, response);
		} catch (HsException e) {
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
	public DeclareAptitude buildDeclareAptitude(Integer aptType, String fileId) {
		DeclareAptitude obj = new DeclareAptitude();
		obj.setFileId(fileId);
		obj.setAptitudeType(aptType);
		return obj;
	}

	/**
	 * 
	 * 方法名称：构建附件对象 方法描述：构建附件对象
	 * 
	 * @param aptitudeId
	 *            资质附件ID
	 * @param applyId
	 *            报备企业申请编号
	 * @param aptType
	 *            附件类型
	 * @param fileId
	 *            文件地址
	 * @param custId
	 *            操作员客户号
	 * @param custName
	 *            操作员名字
	 */
	public DeclareAptitude buildDeclareAptitude(String aptitudeId,
			String applyId, Integer aptType, String fileId, String custId,
			String custName) {
		DeclareAptitude obj = new DeclareAptitude();
		obj.setAptitudeId(aptitudeId);
		obj.setApplyId(applyId);
		obj.setFileId(fileId);
		obj.setAptitudeType(aptType);
		obj.setOptCustId(custId);
		obj.setOptName(custName);
		return obj;
	}

	/**
	 * 验证码验证
	 * 
	 * @param custId
	 * @param verificationCode
	 * @throws HsException
	 */
	public void verificationCode(String custId, String verificationCode,
			String codeType) throws HsException {
		HsAssert.hasText(verificationCode,ASRespCode.VERIFICATION_CODE_INVALID);
		// 获取验证码
		String vCode = changeRedisUtil.get(custId.trim() + "_" + codeType,String.class);
		// 判断为空
		HsAssert.hasText(vCode,ASRespCode.AS_VERIFICATION_PASSED_INVALID);
		// 判断相等
		if (!verificationCode.toUpperCase().equals(vCode.toUpperCase())) {
			throw new HsException(ASRespCode.VERIFICATION_CODE_ERROR);
		}
	}

	@Override
	protected IBaseService getEntityService() {
		return entDeclareService;
	}

}
