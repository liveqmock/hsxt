/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.ws.api;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.bean.GrantDetail;
import com.gy.hsxt.ws.bean.GrantQueryCondition;
import com.gy.hsxt.ws.bean.GrantRecord;

/**
 * 积分福利发放服务
 * 
 * @Package: com.gy.hsxt.ws.api
 * @ClassName: IWsGrantService
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-12-11 下午4:47:48
 * @version V1.0
 */
public interface IWsGrantService {

	/**
	 * 查询待发放的积分福利记录
	 * 
	 * @param queryCondition
	 *            积分福利发放记录查询条件 必传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 */
	public PageData<GrantRecord> listPendingGrant(GrantQueryCondition queryCondition, Page page)
			throws HsException;

	/**
	 * 查询已经发放的积分福利
	 * 
	 * @param queryCondition
	 *            积分福利发放记录查询条件 必传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 */
	public PageData<GrantRecord> listHistoryGrant(GrantQueryCondition queryCondition, Page page)
			throws HsException;

	/**
	 * 查询积分福利发放记录详情
	 * 
	 * @param grantId
	 *            发放记录编号（主键） 必传
	 * 
	 * @return
	 * 
	 * @throws HsException
	 */
	public GrantDetail queryGrantDetail(String grantId) throws HsException;

	/**
	 * 发放积分福利
	 * 
	 * @param grantId
	 *            发放记录编号（主键） 必传
	 * @param remark
	 *            发放备注信息
	 * @throws HsException
	 */
	public void grantWelfare(String grantId, String remark) throws HsException;
}
