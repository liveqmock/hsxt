package com.gy.hsxt.access.web.aps.services.invoice.imp;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gy.hsxt.access.web.aps.services.invoice.ITaxrateChangeService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.tax.IBSTaxrateChangeService;
import com.gy.hsxt.bs.bean.tax.TaxrateChange;
import com.gy.hsxt.bs.bean.tax.TaxrateChangeQuery;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.services.invoice.imp
 * @className : TaxrateChangeService.java
 * @description : 税率调整申请管理
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@Service("taxrateChangeService")
public class TaxrateChangeService extends BaseServiceImpl<TaxrateChangeService>
		implements ITaxrateChangeService {
	@Autowired
	private IBSTaxrateChangeService BSTaxrateChangeService;

	/**
	 * 分页查询税率调整申请列表
	 * 
	 * @param filterMap
	 *            :查询条件map
	 * @param filterMap
	 *            :排序条件map
	 * @param page
	 *            :分页信息
	 * @return 税率调整申请的分页数据
	 */
	@Override
	public PageData<TaxrateChange> findScrollResult(Map filterMap, Map sortMap,
			Page page) throws HsException {
		TaxrateChangeQuery taxrateChangeQuery = new TaxrateChangeQuery();
		if (filterMap != null) {
			// 企业名称
			String companyName = (String) filterMap.get("companyName");
			if (!StringUtils.isEmpty(companyName)
					&& !"null".equals(companyName)) {
				taxrateChangeQuery.setCustName((String) filterMap
						.get("companyName"));
			}
			// 企业互生号
			String resNo = (String) filterMap.get("resNo");
			if (!StringUtils.isEmpty(resNo)) {
				taxrateChangeQuery.setResNo(resNo);
			}
			// 状态
			String status = (String) filterMap.get("status");
			if (!StringUtils.isEmpty(status)) {
				taxrateChangeQuery.setStatus(Integer.parseInt(status));
			}
		}
		return BSTaxrateChangeService.queryTaxrateChangeListPage(page,
				taxrateChangeQuery);
	}
	/**
	 * 分页查询企业税率调整审批/复核列表
	 * 
	 * @param filterMap
	 *            :查询条件map
	 * @param filterMap
	 *            :排序条件map
	 * @param page
	 *            :分页信息
	 * @return 税率调整申请的分页数据
	 */
	@Override
	public PageData<TaxrateChange> approveTaxrateChangeList(Map filterMap, Map sortMap,
			Page page) throws HsException {
		TaxrateChangeQuery taxrateChangeQuery = new TaxrateChangeQuery();
		if (filterMap != null) {
			// 企业名称
			String companyName = (String) filterMap.get("companyName");
			if (!StringUtils.isEmpty(companyName)
					&& !"null".equals(companyName)) {
				taxrateChangeQuery.setCustName((String) filterMap
						.get("companyName"));
			}
			// 企业互生号
			String resNo = (String) filterMap.get("resNo");
			if (!StringUtils.isEmpty(resNo)) {
				taxrateChangeQuery.setResNo(resNo);
			}
			// 状态
			String status = (String) filterMap.get("status");
			if (!StringUtils.isEmpty(status)) {
				taxrateChangeQuery.setStatus(Integer.parseInt(status));
			}
			// 操作员客户号
			String custId = (String) filterMap.get("custId");
			if (!StringUtils.isEmpty(custId) && !custId.equals("undefined")) {
				taxrateChangeQuery.setOptCustId(custId);
			}
		}
		return BSTaxrateChangeService.queryTaskListPage(page,
				taxrateChangeQuery);
	}
	/**
	 * 获取 某一条企业税率调整申请记录
	 * 
	 * @param applyId
	 *            申请编号 必须 非null
	 * @return 返回一个实体对象
	 * @throws HsException
	 */
	@Override
	public TaxrateChange findById(String applyId) throws HsException {
		return BSTaxrateChangeService.queryTaxrateChangeById(applyId);
	}

	/**
	 * 审批/复核企业税率调整申请记录
	 * 
	 * @param taxrateChange
	 *            审批操作历史的实体类 非null
	 * @throws HsException
	 */
	@Override
	public void approve(TaxrateChange taxrateChange) throws HsException {
		BSTaxrateChangeService.apprTaxrateChange(taxrateChange);

	}
}
