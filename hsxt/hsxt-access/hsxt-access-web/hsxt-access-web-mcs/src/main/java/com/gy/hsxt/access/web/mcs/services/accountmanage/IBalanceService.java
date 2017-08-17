/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.mcs.services.accountmanage;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.ac.bean.AccountEntrySum;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.ao.bean.TransOut;
import com.gy.hsxt.bs.bean.order.OrderCustom;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.bean.AllocDetail;
import com.gy.hsxt.ps.bean.AllocDetailSum;

/**
 * *************************************************************************
 * 
 * <PRE>
 * Description      : 账户余额查询
 * 
 * Project Name     : hsxt-access-web-company 
 * 
 * Package Name     : com.gy.hsxt.access.web.company.services.accountManagement  
 * 
 * File Name        : IBalanceService 
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-10-8 下午4:08:18
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-10-8 下午4:08:18
 * 
 * UpdateRemark     : 说明本次修改内容
 * 
 * Version          : v1.0
 * 
 * </PRE>
 ************************************************************************** 
 */
@Service
public interface IBalanceService extends IBaseService {

	/**
	 * 账户余额单查询
	 * 
	 * @param custId
	 *            客户全局编号
	 * @param accType
	 *            账户类型编码
	 * @return 账户余额实体集合对象
	 * @throws HsException
	 */
	public AccountBalance findAccNormal(String custId, String accType)
			throws HsException;

	/**
	 * 企业昨日积分查询
	 * 
	 * @param custId
	 * @param accType
	 * @return
	 * @throws HsException
	 */
	public AccountEntrySum findEntIntegralByYesterday(String custId,
			String accType) throws HsException;

	/**
	 * 账户明细分页查询(积分、互生币、货币)
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public PageData<AccountEntry> searchAccEntryPage(Map filterMap,
			Map sortMap, Page page) throws HsException;

	/**
	 * 获取保底积分
	 * 
	 * @return
	 * @throws HsException
	 */
	public String baseIntegral() throws HsException;

	/**
	 * 查询积分分配明细汇总
	 * 
	 * @return
	 * @throws HsException
	 */

	public AllocDetailSum pointDetailSum(String batchNo, String entCustId)
			throws HsException;

	/**
	 * 查询积分分配明细列表
	 * 
	 * @return
	 * @throws HsException
	 */

	public PageData<AllocDetail> pointDetailList(Map filterMap,
			Map sortMap, Page page) throws HsException;

	public Map<String, Object> queryAccOptDetailed(String transNo, String transType)
			throws HsException;
	
	// 分页查询消费积分分配汇总
    public PageData<AllocDetailSum> queryPointDetailSumPage(Map filterMap, Map sortMap, Page page) throws HsException;
}
