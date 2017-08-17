/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.tc.api;

import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tc.bean.TcCheckBalance;
import com.gy.hsxt.tc.bean.TcCheckBalanceParam;

/**
 * 调账申请接口
 * 
 * @Package: com.gy.hsxt.tc.api
 * @ClassName: ITcCheckBalanceService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2016-03-16 上午11:17:35
 * @version V1.0
 */
public interface ITcCheckBalanceService {
	/**
	 * 根据条件搜索调账申请信息
	 * @param cb 调账申请的搜索条件
	 * @return
	 */
	PageData<TcCheckBalance> searchCheckBalances(TcCheckBalanceParam param) throws HsException;
	
	/**
	 * 添加调账申请信息
	 * @param balance 调账申请
	 */
	void addCheckBalance(TcCheckBalance balance) throws HsException;
	
	/**
	 * 更新调账状态
	 * @param balanceId ID
	 * @param status 调账状态
	 */
	public void updateCheckBalanceStatus(String balanceId, int status) throws HsException;
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	public TcCheckBalance searchCheckBalanceById(String id) throws HsException;
}
