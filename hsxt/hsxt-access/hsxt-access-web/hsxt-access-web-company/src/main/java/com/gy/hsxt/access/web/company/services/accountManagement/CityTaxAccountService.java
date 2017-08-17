/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.services.accountManagement;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsxt.access.web.bean.CompanyBase;
import com.gy.hsxt.access.web.bean.accountManage.CompanyTaxrateChange;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.tax.IBSTaxrateChangeService;
import com.gy.hsxt.bs.bean.tax.TaxrateChange;
import com.gy.hsxt.bs.bean.tax.TaxrateChangeQuery;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.api.ILCSBaseDataService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;

/***
 * 城市税收对接账户
 * 
 * @Package: com.gy.hsxt.access.web.scs.services.accountManagement
 * @ClassName: CityTaxAccountService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2016-1-16 下午7:26:24
 * @version V1.0
 */
@Service
public class CityTaxAccountService extends BaseServiceImpl implements ICityTaxAccountService {

	/** 注入 企业税率调整接口 */
	@Resource
	private IBSTaxrateChangeService ibsTaxrateChangeService;

	/** 注入 企业服务接口 */
	@Resource
	private IUCAsEntService iuCAsEntService;

	@Resource
	private ILCSBaseDataService lcsBaseDataService;

	/**
	 * 税率调整申请
	 * 
	 * @param atc
	 * @param scsBase
	 * @see com.gy.hsxt.access.web.scs.services.accountManagement.ICityTaxAccountService#taxAdjustmentApply(com.gy.hsxt.access.web.bean.CompanyTaxrateChange.ApsTaxrateChange,
	 *      com.gy.hsxt.access.web.bean.SCSBase)
	 */
	@Override
	public void taxAdjustmentApply(CompanyTaxrateChange atc, CompanyBase companyBase)
	{
		// 获取企业明细
		AsEntExtendInfo aeei = iuCAsEntService.searchEntExtInfo(companyBase.getEntCustId());
		// 企业信息
		atc.setResNo(aeei.getEntResNo());
		atc.setCustId(aeei.getEntCustId());
		atc.setCustName(aeei.getEntCustName());
		atc.setLinkman(aeei.getContactPerson());
		atc.setTelephone(aeei.getContactPhone());
		atc.setCreatedBy(companyBase.getCustId());
		// 申请
		ibsTaxrateChangeService.createTaxrateChange(atc);
	}

	/**
	 * 税率调整申请查询
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.scs.services.accountManagement.ICityTaxAccountService#taxAdjustmentApplyPage(java.util.Map,
	 *      java.util.Map, com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<TaxrateChange> taxAdjustmentApplyPage(Map filterMap, Map sortMap, Page page) throws HsException
	{
		// 创建查询类
		TaxrateChangeQuery tcq = this.createTCQ(filterMap);
		// 分页查询
		return ibsTaxrateChangeService.queryTaxrateChangeListPage(page, tcq);
	}

	/**
	 * 创建税率调整查询实体
	 * 
	 * @param filter
	 * @return
	 */
	TaxrateChangeQuery createTCQ(Map filter)
	{
		TaxrateChangeQuery tcq = new TaxrateChangeQuery();
		// 企业互生号(查看自己申请记录)
		// tcq.setResNo(filter.get("entResNo").toString());
		tcq.setCustId(filter.get("entCustId").toString());
		// 开始日期
		String startDate = (String) filter.get("beginDate");
		if (!StringUtils.isEmpty(startDate))
		{
			tcq.setStartDate(startDate);
		}
		// 结束日期
		String endDate = (String) filter.get("endDate");
		if (!StringUtils.isEmpty(endDate))
		{
			tcq.setEndDate(endDate);
		}
		return tcq;
	}

	/**
	 * 获取税率
	 * 
	 * @param scsBase
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.scs.services.accountManagement.ICityTaxAccountService#queryTax(com.gy.hsxt.access.web.bean.SCSBase)
	 */
	@Override
	public String queryTax(CompanyBase companyBase) throws HsException
	{
		// 获取企业明细
		AsEntExtendInfo aeei = iuCAsEntService.searchEntExtInfo(companyBase.getEntCustId());
		// 非空验证
		if (null == aeei)
		{
			throw new HsException(RespCode.GL_DATA_NOT_FOUND);
		}

		return aeei.getTaxRate() == null ? "0" : aeei.getTaxRate().toString();
	}

	/**
	 * 查询已申请税率调整明细
	 * 
	 * @param applyId
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.scs.services.accountManagement.ICityTaxAccountService#queryTaxApplyDetail(java.lang.String)
	 */
	@Override
	public TaxrateChange queryTaxApplyDetail(String applyId) throws HsException
	{
		// 查询申请明细接口
		return ibsTaxrateChangeService.queryTaxrateChangeById(applyId);
	}

	/**
	 * 查询企业税率调整的最新审批结果
	 * 
	 * @param companyBase
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.company.services.accountManagement.ICityTaxAccountService#getLastTaxApply(com.gy.hsxt.access.web.bean.CompanyBase)
	 */
	@Override
	public TaxrateChange getLastTaxApply(CompanyBase companyBase) throws HsException
	{
		TaxrateChange tc = ibsTaxrateChangeService.queryLastHisByCustId(companyBase.getEntCustId());
		if (null == tc)
		{
			tc = new TaxrateChange();
		}
		return tc;
	}

}
