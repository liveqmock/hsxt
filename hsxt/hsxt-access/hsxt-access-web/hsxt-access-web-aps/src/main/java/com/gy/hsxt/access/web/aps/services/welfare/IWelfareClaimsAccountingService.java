package com.gy.hsxt.access.web.aps.services.welfare;

import java.util.List;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.bean.ClaimsAccountingDetail;
import com.gy.hsxt.ws.bean.ClaimsAccountingQueryCondition;
import com.gy.hsxt.ws.bean.ClaimsAccountingRecord;
import com.gy.hsxt.ws.bean.MedicalDetail;

/**
 * 积分福利--理赔核算
 * 
 * @category 积分福利理赔核算
 * @projectName hsxt-access-web-aps
 * @package 
 *          com.gy.hsxt.access.web.aps.services.welfare.IWelfareClaimsAccountingService
 *          . java
 * @className IClaimsAccountingService
 * @description 积分福利--理赔核算
 * @author leiyt
 * @createDate 2015-12-29 下午3:36:44
 * @updateUser leiyt
 * @updateDate 2015-12-29 下午3:36:44
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
public interface IWelfareClaimsAccountingService extends IBaseService {
	/**
	 * 查询待核算理赔单
	 * 
	 * @category 查询待核算理赔单
	 * @param queryCondition
	 *            查询条件对象
	 * @param page
	 *            分页参数
	 * @return
	 * @throws HsException
	 */
	public PageData<ClaimsAccountingRecord> listPendingClaimsAccounting(
			ClaimsAccountingQueryCondition queryCondition, Page page) throws HsException;

	/**
	 * 查询已核算理赔单
	 * 
	 * @category 查询已核算理赔单
	 * @param queryCondition
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public PageData<ClaimsAccountingRecord> listHisClaimsAccounting(
			ClaimsAccountingQueryCondition queryCondition, Page page) throws HsException;

	/**
	 * 理赔核算
	 * 
	 * @category 理赔核算
	 * @param list
	 *            明细
	 * @throws HsException
	 */
	public void countMedicalDetail(List<MedicalDetail> list) throws HsException;

	/**
	 * 理赔核算确认
	 * 
	 * @category 理赔核算
	 * @param list
	 *            明细
	 * @throws HsException
	 */
	public void confirmClaimsAccounting(List<MedicalDetail> list) throws HsException;

	/**
	 * 查询理赔核算详情
	 * 
	 * @param accountingId
	 * @return
	 * @throws HsException
	 */
	public ClaimsAccountingDetail queryClaimsAccountingDetail(String accountingId)
			throws HsException;
}
