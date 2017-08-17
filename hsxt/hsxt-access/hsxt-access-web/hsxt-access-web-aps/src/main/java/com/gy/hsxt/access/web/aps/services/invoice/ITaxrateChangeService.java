package com.gy.hsxt.access.web.aps.services.invoice;

import java.util.Map;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tax.TaxrateChange;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.services.invoice
 * @className : ITaxrateChangeService.java
 * @description : 企业税率调整接口
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
public interface ITaxrateChangeService extends IBaseService {

	/**
	 * 获取 某一条企业税率调整申请记录
	 * 
	 * @param applyId
	 *            申请编号 必须 非null
	 * @return 返回一个实体对象
	 * @throws HsException
	 */
	TaxrateChange findById(String applyId) throws HsException;

	/**
	 * 审批/复核企业税率调整申请记录
	 * 
	 * @param taxrateChange
	 *            审批操作历史的实体类 非null
	 * @throws HsException
	 */
	void approve(TaxrateChange taxrateChange) throws HsException;
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
	
	public PageData<TaxrateChange> approveTaxrateChangeList(Map filterMap, Map sortMap,
			Page page) throws HsException;
}
