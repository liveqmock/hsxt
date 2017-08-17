/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.ws.api;

import java.util.List;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.bean.ClaimsAccountingDetail;
import com.gy.hsxt.ws.bean.ClaimsAccountingQueryCondition;
import com.gy.hsxt.ws.bean.ClaimsAccountingRecord;
import com.gy.hsxt.ws.bean.MedicalDetail;

/**
 * 理赔核算 服务接口
 * 
 * @Package: com.gy.hsxt.ws.api
 * @ClassName: IWsClaimsAccountingService
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-12-11 下午4:49:52
 * @version V1.0
 */
public interface IWsClaimsAccountingService {

	/**
	 * 医疗明细核算
	 * 
	 * @param list
	 *            医疗明细列表 必传
	 * @throws HsException
	 */
	public void countMedicalDetail(List<MedicalDetail> list) throws HsException;

	/**
	 * 确认核算
	 * 
	 * @param list
	 *            医疗明细列表 必传
	 * @throws HsException
	 */
	public void confrimClaimsAccounting(List<MedicalDetail> list) throws HsException;

	/**
	 * 查询已核算的 理赔核算单
	 * 
	 * @param queryCondition
	 *            查询条件 必传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 */
	public PageData<ClaimsAccountingRecord> listHisClaimsAccounting(
			ClaimsAccountingQueryCondition queryCondition, Page page) throws HsException;

	/**
	 * 查询待核算的 理赔核算单
	 * 
	 * @param queryCondition
	 *            查询条件 必传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 */
	public PageData<ClaimsAccountingRecord> listPendingClaimsAccounting(
			ClaimsAccountingQueryCondition queryCondition, Page page) throws HsException;

	/**
	 * 查询理赔核算单 详情
	 * 
	 * @param accountingId
	 *            理赔核算单编号（主键） 必传
	 * @return
	 * @throws HsException
	 */
	public ClaimsAccountingDetail queryClaimsAccountingDetail(String accountingId)
			throws HsException;

}
