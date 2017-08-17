/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.service;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.common.exception.HsException;
		
/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.service  
 * @ClassName: AcApiService 
 * @Description: 账务接口封装类
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:12:08 
 * @version V1.0
 */
public interface AcApiService {
	
    /**
     * 全额查询
     * @author   wucl   
     * 2015-10-30 下午5:21:34
     * @param custID
     * @param accType   账户类型
     * @return
     * @throws HsException
     */
	public AccountBalance searchAccBalance(String custId, String itemType)throws HsException;
	
	/**
     * 正常全额查询
     * 建议使用 searchAccBalance 方法
     * @author   wucl   
     * 2015-10-30 下午5:21:34
     * @param custID
     * @param accType   账户类型
     * @return
     * @throws HsException
     */
	public AccountBalance searchAccNormal(String custID, String accType) throws HsException;
	
	
	/**
	 * 查询整个账套下所有的账项余额
	 * @param custId
	 * @param accCategory
	 * @return
	 * @throws HsException
	 */
	public Map<String,AccountBalance> searchAccBalanceByAccCategory(String custId, int accCategory) throws HsException;

}

	