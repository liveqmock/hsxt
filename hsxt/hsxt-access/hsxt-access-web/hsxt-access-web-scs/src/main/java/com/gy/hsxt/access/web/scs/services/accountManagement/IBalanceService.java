/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.services.accountManagement;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.ac.bean.AccountEntrySum;
import com.gy.hsxt.access.web.common.service.IBaseService;
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
	public AccountBalance findAccNormal(String custId, String accType) throws HsException;

	/**
	 * 企业昨日积分查询
	 * 
	 * @param custId
	 * @param accType
	 * @return
	 * @throws HsException
	 */
	public AccountEntrySum findEntIntegralByYesterday(String custId, String accType) throws HsException;

	/**
	 * 正常账户发生汇总额查询接口
	 * 
	 * @param accountEntry
	 *            账户分录实体对象
	 * @return
	 * @throws HsException
	 */
	public AccountEntry findAccSumNormal(AccountEntry accountEntry) throws HsException;

	/**
	 * 客户对应的账户分类查询接口定义
	 * 
	 * @param custId
	 *            客户号
	 * @param accCategory
	 *            分类标识和对应分类名称如下（1 ：积分类型，2 ：互生币类型，3 ：货币类型，5
	 *            ：地区平台银行货币存款/地区平台银行货币现金类型）
	 * @return
	 * @throws HsException
	 */
	public Map<String, AccountBalance> searchAccBalanceByAccCategory(String custId, int accCategory) throws HsException;

	/**
	 * 同一客户的多个账户余额查询接口定义
	 * 
	 * @param custId
	 *            企业客户号
	 * @param accTypes
	 *            账户类型
	 * @return
	 * @throws HsException
	 */
	public Map<String, AccountBalance> searchAccBalanceByCustIdAndAccType(String custId, String[] accTypes)
			throws HsException;

	/**
	 * 账户明细分页查询(积分、互生币、货币)
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public PageData<AccountEntry> searchAccEntryPage(Map filterMap, Map sortMap, Page page) throws HsException;

	/**
	 * 账户操作明细详情(积分、货币)
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月26日 下午2:22:23
	 * @param transNo
	 * @param transType
	 * @return
	 * @throws HsException
	 * @return : Map<String,Object>
	 * @version V3.0.0
	 */
	public Map<String, Object> queryAccOptDetailed(String transNo, String transType) throws HsException;

	/**
	 * 查询消费积分分配详情
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 上午9:25:33
	 * @param batchNo
	 * @param entCustId
	 * @return
	 * @throws HsException
	 * @return : QueryDetailResult
	 * @version V3.0.0
	 */
	public AllocDetailSum queryPointAllotDetailed(String batchNo, String entCustId) throws HsException;

	/**
	 * 查询消费积分分配详情流水
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午8:36:00
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 * @return : PageData<AllocDetail>
	 * @version V3.0.0
	 */
	public PageData<AllocDetail> queryPointAllotDetailedList(Map filterMap, Map sortMap, Page page) throws HsException;

	/**
	 * 明细查询
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public PageData<AccountEntry> taxDetailPage(Map filterMap, Map sortMap, Page page) throws HsException;

	/**
	 * 获取保底积分
	 * @return
	 * @throws HsException
	 */
	public String baseIntegral() throws HsException;
}
