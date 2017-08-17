/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.controllers.companyInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.company.services.companyInfo.ICompanyInfoService;
import com.gy.hsxt.access.web.company.services.companyInfo.IUCBankfoService;
import com.gy.hsxt.access.web.company.services.systemBusiness.IMemberEnterpriseService;
import com.gy.hsxt.bs.bean.entstatus.EntChangeInfo;
import com.gy.hsxt.bs.bean.entstatus.EntRealnameAuth;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;

/**
 * 
 * @projectName : hsxt-access-web-company
 * @package : com.gy.hsxt.access.web.company.controllers.companyinfo
 * @className : CompanyInfoController.java
 * @description : 企业系统信息
 * @author : maocy
 * @createDate : 2016-01-27
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("companyInfoController")
public class CompanyInfoController extends BaseController {

	@Resource
	private RedisUtil<String> changeRedisUtil;

	@Resource
	private IUCBankfoService ucBankService;// 银行信息服务类

	@Resource
	private ICompanyInfoService companyService;// 企业信息服务类
	
	@Resource
    private IMemberEnterpriseService imemberEnterpriseService;

	private final static String USER_TYPE = "4";// 用户类型

	/**
	 * 
	 * 方法名称：查询信息 方法描述：企业系统信息-查询信息
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param entCustId
	 *            企业客户号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findEntAllInfo")
	public HttpRespEnvelope findEntAllInfo(HttpServletRequest request, String entCustId)
	{
		try
		{
			super.verifySecureToken(request);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { entCustId, ASRespCode.AS_ENT_CUSTID_INVALID });
			AsEntExtendInfo info = this.companyService.findEntAllInfo(entCustId);
			return new HttpRespEnvelope(info);
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：保存企业联系信息 方法描述：企业系统信息-保存企业联系信息
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param entInfo
	 *            企业信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateLinkInfo")
	public HttpRespEnvelope updateLinkInfo(HttpServletRequest request, @ModelAttribute AsEntExtendInfo entInfo)
	{
		try
		{
			super.verifySecureToken(request);
			String custId = request.getParameter("custId");// 操作员客户号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { custId, ASRespCode.AS_OPRATOR_OPTCUSTID }, 
					new Object[] {entInfo.getEntCustId(), ASRespCode.AS_ENT_CUSTID_INVALID }
					//new Object[] { entInfo.getContactProxy(), ASRespCode.EW_ENT_LINKINFO_CERT_INVALID }
					);
			// 长度验证
			if (!StringUtils.isBlank(entInfo.getContactAddr()))
			{
				RequestUtil.verifyParamsLength(new Object[] { entInfo.getContactAddr(), 2, 128,
						ASRespCode.EW_ENT_LINKINFO_ADDRESS_LENGTH_INVALID });
			}
			
			//验证企业状态
            this.verificationEntStatus(entInfo.getEntCustId());
            
			this.companyService.updateEntExtInfo(entInfo, custId);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：保存企业信息 方法描述：企业系统信息-保存企业信息
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param entInfo
	 *            企业信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateEntExtInfo")
	public HttpRespEnvelope updateEntExtInfo(HttpServletRequest request, @ModelAttribute AsEntExtendInfo entInfo)
	{
		try
		{
			super.verifySecureToken(request);
			String custId = request.getParameter("custId");// 操作员客户号
			
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { custId, ASRespCode.AS_OPRATOR_OPTCUSTID }, 
					new Object[] {entInfo.getEntCustId(), ASRespCode.AS_ENT_CUSTID_INVALID }, 
					//new Object[] { entInfo.getOfficeTel(),ASRespCode.EW_BIZREG_MOBILE_INVALID }, 
					new Object[] { entInfo.getNature(),ASRespCode.EW_BIZREG_ENTTYPE_INVALID },
					new Object[] { entInfo.getBuildDate(), ASRespCode.EW_BIZREG_ESTADATE_INVALID });
			// 长度验证
			RequestUtil.verifyParamsLength(
					new Object[] { entInfo.getNature(), 2, 20,ASRespCode.EW_BIZREG_ENTTYPE_LENGTH_INVALID }, 
					new Object[] { entInfo.getEndDate(), 2, 20,ASRespCode.EW_BIZREG_LIMITDATE_LENGTH_INVALID }, 
					new Object[] { entInfo.getBusinessScope(), 1, 300,ASRespCode.EW_ENT_FILING_DEALAREA_LENGTH_INVALID });
			
			//验证企业状态
            this.verificationEntStatus(entInfo.getEntCustId());
            
			this.companyService.updateEntExtInfo(entInfo, custId);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：添加银行卡 方法描述：企业系统信息-添加银行卡
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param acctInfo
	 *            银行账户对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addBank")
	public HttpRespEnvelope addBank(HttpServletRequest request, @ModelAttribute AsBankAcctInfo acctInfo)
	{
		try
		{
			String resNo = super.verifyPointNo(request);
			String entCustId = request.getParameter("entCustId");// 企业客户号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { entCustId, ASRespCode.AS_ENT_CUSTID_INVALID },
					new Object[] { acctInfo.getBankAccNo(), ASRespCode.EW_BANK_INVALID },
					new Object[] { acctInfo.getBankCode(), ASRespCode.EW_BANKINFO_BANKCODE_INVALID });
			acctInfo.setCustId(entCustId);
			acctInfo.setResNo(resNo);
			
			//验证企业状态
            this.verificationEntStatus(entCustId);
            
			this.ucBankService.addBank(acctInfo, USER_TYPE);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：删除银行卡 方法描述：企业系统信息-删除银行卡
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param accId
	 *            银行卡编号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delBank")
	public HttpRespEnvelope delBank(HttpServletRequest request, String accId)
	{
		try
		{
			super.verifySecureToken(request);
			Long accId_ = CommonUtils.toLong(accId);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { accId_, ASRespCode.AS_BANKINFO_ACCID_INVALID });
			String entCustId = request.getParameter("entCustId");// 企业客户号
			//验证企业状态
            this.verificationEntStatus(entCustId);
            
			this.ucBankService.delBank(accId_, USER_TYPE);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查询银行卡 方法描述：企业系统信息-查询银行卡
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param accId
	 *            银行卡编号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findBanksByCustId")
	public HttpRespEnvelope findBanksByCustId(HttpServletRequest request, String custId)
	{
		try
		{
			super.verifySecureToken(request);
			String entCustId = request.getParameter("entCustId");// 企业客户号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { entCustId, ASRespCode.AS_ENT_CUSTID_INVALID });
			List<AsBankAcctInfo> list = this.ucBankService.findBanksByCustId(entCustId, USER_TYPE);
			return new HttpRespEnvelope(list);
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：初始化企业实名认证 方法描述：初始化企业实名认证
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param accId
	 *            银行卡编号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/initEntRealName")
	public HttpRespEnvelope initEntRealName(HttpServletRequest request, String custId)
	{
		try
		{
			super.verifySecureToken(request);
			String entCustId = request.getParameter("entCustId");// 企业客户号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { entCustId, ASRespCode.AS_ENT_CUSTID_INVALID });
			// 查询企业认证状态
			AsEntStatusInfo status = this.companyService.findEntStatusInfo(entCustId);
			if (status == null)
			{
				throw new HsException(ASRespCode.EW_ENT_STATUS_INVALID);
			}
			Map<String, Object> resMap = new HashMap<String, Object>();
			if (status.getIsRealnameAuth() == 1)
			{// 已实名认证
				resMap.put("isRealnameAuth", true);
			} else
			{
				// 查询企业基本信息
				AsEntExtendInfo info = this.companyService.findEntAllInfo(entCustId);
				if (info == null)
				{
					throw new HsException(ASRespCode.EW_ENT_INFO_INVALID);
				}
				// 查询企业认证信息
				EntRealnameAuth auth = this.companyService.findEntRealnameAuthByCustId(entCustId);
				resMap.put("isRealnameAuth", false);
				resMap.put("entExtendInfo", info);
				resMap.put("entRealnameAuth", auth);
			}
			return new HttpRespEnvelope(resMap);
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：创建企业实名认证信息 方法描述：创建企业实名认证信息
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param info
	 *            实名认证信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/createEntRealNameAuth")
	public HttpRespEnvelope createEntRealNameAuth(HttpServletRequest request, @ModelAttribute EntRealnameAuth info)
	{
		try
		{
			String pointNo = super.verifyPointNo(request);// 获取登陆企业互生号
			String custId = request.getParameter("custId");// 获取登陆客户号
			String entCustId = request.getParameter("entCustId");// 获取登陆企业客户号
			String optEntName = request.getParameter("custEntName");// 获取登陆客户企业名称
			String cookieOperNoName = request.getParameter("cookieOperNoName"); // 获取验证码
			// 长度验证
			RequestUtil.verifyParamsLength(new Object[] { info.getPostScript(), 0, 300,
					ASRespCode.EW_POST_SCRIPT_LENGTH_INVALID });
			
			//验证企业状态
            this.verificationEntStatus(entCustId);
            
			info.setEntResNo(pointNo);
			info.setEntCustId(entCustId);
			info.setOptCustId(custId);
			info.setOptName(cookieOperNoName);
			info.setOptEntName(optEntName);
			
			this.companyService.createEntRealNameAuth(info);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 验证企业状态
	 * 
	 * @param custId 企业客户编号
	 * @throws HsException
	 */
	public void verificationEntStatus(String entCustId) throws HsException
	{
	    AsEntStatusInfo status = imemberEnterpriseService.searchEntStatusInfo(entCustId);
        if (status != null)
        {
            switch (status.getBaseStatus())
            {
            case 5:
                throw new HsException(ASRespCode.EW_STATUS5_IS_NOT_REPLACE_BUY_HSB.getCode(),
                        ASRespCode.EW_STATUS5_IS_NOT_REPLACE_BUY_HSB.getDesc());
            case 8:
                throw new HsException(ASRespCode.EW_STATUS8_IS_NOT_REPLACE_BUY_HSB.getCode(),
                        ASRespCode.EW_STATUS8_IS_NOT_REPLACE_BUY_HSB.getDesc());
            }
        }
	}

	/**
	 * 
	 * 方法名称：初始化重要信息变更 方法描述：初始化重要信息变更
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param entCustId
	 *            企业客户号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/initEntChange")
	public HttpRespEnvelope initEntChange(HttpServletRequest request, String entCustId)
	{
		try
		{
			super.verifySecureToken(request);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { entCustId, ASRespCode.AS_ENT_CUSTID_INVALID });
			
			Map<String, Object> resMap = new HashMap<String, Object>();
			
			// 查询企业基本信息
			AsEntExtendInfo info = this.companyService.findEntAllInfo(entCustId);
			
			if (info == null)
			{
				throw new HsException(ASRespCode.EW_ENT_INFO_INVALID);
			}
			
			// 查询企业重要变更信息
			EntChangeInfo changeInfo = this.companyService.findEntChangeByCustId(entCustId);
			resMap.put("isRealnameAuth", true);
			resMap.put("changeInfo", changeInfo);
			resMap.put("entExtendInfo", info);
			return new HttpRespEnvelope(resMap);
		} catch (Exception e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查询重要信息变更 方法描述：依据客户号查询重要信息变更
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param applyId
	 *            申请编号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findEntChangeByApplyId")
	public HttpRespEnvelope findEntChangeByApplyId(HttpServletRequest request, String applyId)
	{
		try
		{
			super.verifySecureToken(request);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { applyId, ASRespCode.AS_APPLYID_INVALID });
			// 查询企业重要变更信息
			EntChangeInfo changeInfo = this.companyService.findEntChangeByApplyId(applyId);
			return new HttpRespEnvelope(changeInfo);
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：创建企业重要信息变更 方法描述：创建企业重要信息变更
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param info
	 *            重要信息变更信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/createEntChange")
	public HttpRespEnvelope createEntChange(HttpServletRequest request, @ModelAttribute EntChangeInfo info)
	{
		try
		{
			String pointNo = super.verifyPointNo(request);// 获取登陆企业互生号
			String custId = request.getParameter("custId");// 获取登陆客户号
			String entCustId = request.getParameter("entCustId");// 获取登陆企业客户号
			String optEntName = request.getParameter("custEntName");// 获取登陆客户企业名称
			String cookieOperNoName = request.getParameter("cookieOperNoName");// 0000(张三)

			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(
			        new Object[] { entCustId, ASRespCode.AS_ENT_CUSTID_INVALID },
					new Object[] { info.getEntCustName(), ASRespCode.EW_ENT_FILING_ENTCUSTNAME_INVALID },
					new Object[] {info.getLinkman(), ASRespCode.EW_ENT_FILING_LINKEMAN_INVALID },
					new Object[] { info.getMobile(), ASRespCode.EW_ENT_FILING_PHONE_INVALID },
					new Object[] {info.getImptappPic(), ASRespCode.EW_IMPTAP_PIC_INVALID });
			// 验证图片
			vaildUploadImg(info);
			// 长度验证
			RequestUtil.verifyParamsLength(
			        new Object[] { info.getCustNameNew(), 0, 128,ASRespCode.EW_REGINFO_ENTNAME_LENGTH_INVALID }, 
					new Object[] { info.getCustAddressNew(), 0, 128,ASRespCode.EW_ENT_FILING_ENT_ADDRESS_LENGTH_INVALID }, 
					new Object[] { info.getLegalRepNew(), 0, 20,ASRespCode.EW_ENT_FILING_LEGALNAME_LENGTH_INVALID }, 
					new Object[] { info.getLinkmanNew(), 0, 20,ASRespCode.EW_ENT_LINKINFO_LINKMAN_LENGTH_INVALID }, 
					new Object[] { info.getApplyReason(), 0, 300,ASRespCode.EW_APPR_REMARK_LENGTH_INVALID });
			
			//验证企业状态
            this.verificationEntStatus(entCustId);
            
			info.setEntResNo(pointNo);
			info.setEntCustId(entCustId);
			info.setOptCustId(custId);
			info.setOptName(cookieOperNoName);
			info.setOptEntName(optEntName);

			this.companyService.createEntChange(info);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 验证上传文件
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年2月3日 下午4:00:42
	 * @param info
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	private void vaildUploadImg(EntChangeInfo info) throws HsException
	{
		// 修改企业名称、企业地址、法人代表姓名中任一项内容，须重新提交营业执照扫描件
		if (StringUtils.isNotBlank(info.getCustNameNew()) || StringUtils.isNotBlank(info.getCustAddressNew()) || StringUtils.isNotBlank(info.getLegalRepNew()))
		{
			RequestUtil.verifyParamsIsNotEmpty(
			        new Object[] { info.getBizLicenseCrePicNew(),ASRespCode.EW_ENT_FILING_LICE_PIC_FILEID_INVALID }
			    );
		}

		// 营业执照
		if (StringUtils.isNotBlank(info.getBizLicenseNoNew()))
		{
			RequestUtil.verifyParamsIsNotEmpty(
			        new Object[] { info.getBizLicenseCrePicNew(),ASRespCode.EW_ENT_FILING_LICE_PIC_FILEID_INVALID });
		}

	}

	@Override
	protected IBaseService getEntityService()
	{
		return this.companyService;
	}

}
