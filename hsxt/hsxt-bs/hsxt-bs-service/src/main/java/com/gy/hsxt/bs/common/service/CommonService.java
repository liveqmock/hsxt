/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.lcs.bean.City;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.bean.Province;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uc.bs.api.common.IUCBsBankAcctInfoService;
import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;
import com.gy.hsxt.uc.bs.bean.common.BsBankAcctInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntAllInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntBaseInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntMainInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntStatusInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 公共Service接口实现
 * 
 * @Package: com.gy.hsxt.bs.common.service
 * @ClassName: CommonService
 * @Description: TODO
 * @author: likui
 * @date: 2015年11月11日 上午10:02:03
 * @company: gyist
 * @version V3.0.0
 */
@Service("commonService")
public class CommonService implements ICommonService {

	/** 地区平台配送Service **/
	@Resource
	private LcsClient lcsClient;

	/** 用户中心企业Service **/
	@Resource
	private IUCBsEntService bsEntService;

	/** 用户中心企业银行账户Service **/
	@Resource
	private IUCBsBankAcctInfoService ucBankAcctInfoService;

	/**
	 * 当前平台企业客户号
	 */
	private String platCustId;

	/**
	 * 获取本地区平台信息
	 * 
	 * @Description:
	 * @return
	 */
	@Override
	public LocalInfo getAreaPlatInfo()
	{
		try
		{
		    LocalInfo localInfo = lcsClient.getLocalInfo();
			BsEntBaseInfo baseInfo = this.getEntBaseInfoToUc(localInfo.getPlatResNo(),null);
			if (baseInfo != null) {
				localInfo.setPlatNameCn(baseInfo.getEntName());
			}
			return localInfo;
		} catch (HsException ex)
		{
			SystemLog.error(this.getClass().getName(), "function:getAreaPlatInfo",
					BSRespCode.BS_INVOKE_LCS_FAIL.getCode() + ":dubbo调用LCS接口失败", ex);
			return null;
		}
	}

	/**
	 * 获取地区平台客户号
	 * 
	 * @Description:
	 * @return
	 */
	@Override
	public String getAreaPlatCustId()
	{
		if (platCustId == null)
		{
			LocalInfo info = getAreaPlatInfo();
			try
			{
				platCustId = bsEntService.findEntCustIdByEntResNo(info.getPlatResNo());
			} catch (Exception ex)
			{
				SystemLog.error(this.getClass().getName(), "function:getAreaPlatCustId",
						BSRespCode.BS_DUBBO_INVOKE_UC_FAIL.getCode() + ":dubbo调用用户中心接口失败", ex);
			}
		}
		return platCustId;
	}

	/**
	 * 获取本地区平台的省份
	 * 
	 * @Description:
	 * @return
	 */
	@Override
	public List<Province> getAreaPlatProvince()
	{
		LocalInfo info = getAreaPlatInfo();
		return lcsClient.queryProvinceByParent(info.getCountryNo());
	}

	/**
	 * 获取本地区平台的省份编号
	 * 
	 * @Description:
	 * @return
	 */
	@Override
	public List<String> getAreaPlatProvinceNo()
	{
		List<Province> provinces = getAreaPlatProvince();
		if (null == provinces || provinces.size() <= 0)
		{
			return null;
		}
		List<String> provinceNos = new ArrayList<String>();
		for (int i = 0; i < provinces.size(); i++)
		{
			provinceNos.add(provinces.get(i).getProvinceNo());
		}
		return provinceNos;
	}

	/**
	 * 根据省编号获取省信息
	 * 
	 * @Description:
	 * @param provinceNo
	 * @return
	 */
	@Override
	public Province getProvinceByNo(String provinceNo)
	{
		LocalInfo info = getAreaPlatInfo();
		return lcsClient.getProvinceById(info.getCountryNo(), provinceNo);
	}

	/**
	 * 获取省下所有城市
	 * 
	 * @Description:
	 * @param provinceNo
	 * @return
	 */
	@Override
	public List<City> getCityByProvinceNo(String provinceNo)
	{
		LocalInfo info = getAreaPlatInfo();
		return lcsClient.queryCityByParent(info.getCountryNo(), provinceNo);
	}

	/**
	 * 根据企业互生号或客户号查询企业全部信息,优先使用客户号
	 * 
	 * @Description:
	 * @param entResNo
	 * @param entCustId
	 * @return
	 */
	@Override
	public BsEntAllInfo getEntAllInfoToUc(String entResNo, String entCustId)
	{
		BsEntAllInfo info = null;
		try
		{
			if (StringUtils.isNotBlank(entCustId))
			{
				info = bsEntService.searchEntAllInfo(entCustId);
			} else if (StringUtils.isNotBlank(entResNo))
			{
				info = bsEntService.searchEntAllInfoByResNo(entResNo);
			}

		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:getAreaPlatCustId",
					BSRespCode.BS_DUBBO_INVOKE_UC_FAIL.getCode() + ":dubbo调用用户中心接口失败", ex);
			return null;
		}
		return info;
	}

	/**
	 * 根据企业互生号或客户号查询企业主要信息,优先使用客户号
	 * 
	 * @Description:
	 * @param entResNo
	 * @param entCustId
	 * @return
	 */
	@Override
	public BsEntMainInfo getEntMainInfoToUc(String entResNo, String entCustId)
	{
		BsEntMainInfo info = null;
		try
		{
			if (StringUtils.isNotBlank(entCustId))
			{
				info = bsEntService.searchEntMainInfo(entCustId);
			} else if (StringUtils.isNotBlank(entResNo))
			{
				info = bsEntService.getMainInfoByResNo(entResNo);
			}

		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:getAreaPlatCustId",
					BSRespCode.BS_DUBBO_INVOKE_UC_FAIL.getCode() + ":dubbo调用用户中心接口失败", ex);
			return null;
		}
		return info;
	}

	/**
	 * 根据企业互生号或客户号查询企业基本信息,优先使用客户号
	 * 
	 * @Description:
	 * @param entResNo
	 * @param entCustId
	 * @return
	 */
	@Override
	public BsEntBaseInfo getEntBaseInfoToUc(String entResNo, String entCustId)
	{
		BsEntAllInfo info = null;
		BsEntBaseInfo baseInfo = null;
		try
		{
			if (StringUtils.isNotBlank(entCustId))
			{
				baseInfo = bsEntService.searchEntBaseInfo(entCustId);
			} else if (StringUtils.isNotBlank(entResNo))
			{
				info = bsEntService.searchEntAllInfoByResNo(entResNo);
				baseInfo = info == null ? null : info.getBaseInfo();
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:getAreaPlatCustId",
					BSRespCode.BS_DUBBO_INVOKE_UC_FAIL.getCode() + ":dubbo调用用户中心接口失败", ex);
			return null;
		}
		return baseInfo;
	}

	/**
	 * 根据企业互生号或客户号查询企业状态信息,优先使用客户号
	 * 
	 * @Description:
	 * @param entResNo
	 * @param entCustId
	 * @return
	 */
	@Override
	public BsEntStatusInfo getEntStatusInfoToUc(String entResNo, String entCustId)
	{
		BsEntAllInfo info = null;
		BsEntStatusInfo statusInfo = null;
		try
		{
			if (StringUtils.isNotBlank(entCustId))
			{
				statusInfo = bsEntService.searchEntStatusInfo(entCustId);
			} else if (StringUtils.isNotBlank(entResNo))
			{
				info = bsEntService.searchEntAllInfoByResNo(entResNo);
				statusInfo = info == null ? null : info.getStatusInfo();
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:getAreaPlatCustId",
					BSRespCode.BS_DUBBO_INVOKE_UC_FAIL.getCode() + ":dubbo调用用户中心接口失败", ex);
			return null;
		}
		return statusInfo;
	}

	/**
	 * 根据客户号获取默认银行账户信息
	 * 
	 * @Description:
	 * @param hsCustId
	 * @return
	 */
	@Override
	public BsBankAcctInfo getDefaultEntBankAcctByCustId(String hsCustId)
	{
		BsBankAcctInfo acct = null;
		try
		{
			if (StringUtils.isBlank(hsCustId) && HsResNoUtils.isHsResNo(hsCustId.substring(0, 11)))
			{
				return null;
			}
			acct = ucBankAcctInfoService.searchDefaultBankAcc(hsCustId,
					HsResNoUtils.getCustTypeByHsResNo(hsCustId.substring(0, 11)).toString());
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:getAreaPlatCustId",
					BSRespCode.BS_DUBBO_INVOKE_UC_FAIL.getCode() + ":dubbo调用用户中心接口失败", ex);
			return null;
		}
		return acct;
	}

	/**
	 * 根据互生号获取默认银行账户信息
	 * 
	 * @Description:
	 * @param hsResNo
	 * @return
	 */
	@Override
	public BsBankAcctInfo getDefaultEntBankAcctByNo(String hsResNo)
	{
		BsBankAcctInfo acct = null;
		try
		{
			if (HsResNoUtils.isHsResNo(hsResNo))
			{
				return null;
			}
			acct = ucBankAcctInfoService.searchDefaultBankAcc(hsResNo);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:getAreaPlatCustId",
					BSRespCode.BS_DUBBO_INVOKE_UC_FAIL.getCode() + ":dubbo调用用户中心接口失败", ex);
			return null;
		}
		return acct;
	}

}
