package com.gy.hsxt.tc.api;

import java.util.List;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tc.bean.TcCheckBalanceResult;

/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/**
 * 调账审批接口
 * 
 * @Package: com.gy.hsxt.tc.api
 * @ClassName: ITcCheckBalanceResultService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2016-03-16 上午11:17:35
 * @version V1.0
 */
public interface ITcCheckBalanceResultService {
	/**
	 * 获取调账审批结果
	 * @param balanceId
	 * @return
	 */
	List<TcCheckBalanceResult> getCheckBalanceResult(String balanceId) throws HsException;
	
	/**
	 * 添加审批结果
	 * @param cb
	 */
	void addCheckBalanceResult(TcCheckBalanceResult cb) throws HsException;
}
