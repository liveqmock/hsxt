/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.companyInfo;

import java.util.List;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;

/***
 * 实名认证
 * 
 * @Package: com.gy.hsxt.access.web.business.systemInfo.service
 * @ClassName: IRealNameAuthService
 * @Description: TODO
 * 
 * @author: liuxy
 * @date: 2015-9-29 下午7:16:47
 * @version V3.0.0
 */
public interface IBankAccountInfoService extends IBaseService
{
    
    /**
     * 银行列表 
     * @return
     * @throws HsException
     */
    public List<AsBankAcctInfo> findBankAccountList(String custId) throws HsException;

	/**
	 * 银行列表 
	 * @return
	 * @throws HsException
	 */
	public Object findBankNameList() throws HsException;

	/***
	 * 省份列表 
	 * @return
	 * @throws HsException
	 */
	public Object findProvinceList() throws HsException;

	/**
	 * 城市列表 
	 * @return
	 * @throws HsException
	 */
	public Object findCityList(Object obj) throws HsException;
	
	/**
	 * 查询银行帐户信息
	 * @return
	 * @throws HsException
	 */
	public Object findBankAccountInfo(String  cardNo) throws HsException;
	
	
	/**
	 * 修改银行帐户信息
	 * @param obj
	 * @return
	 * @throws HsException
	 */
	public void updateBankAccountInfo(AsBankAcctInfo abaInfo) throws HsException;

	
	
	/**
	 * 新增
	 * @param bcInfo
	 * @throws HsException
	 */
	 public void addBankCard(AsBankAcctInfo bcInfo)throws HsException;
	 
	 /**
	  * 删除 
	  * @param bankCardNo
	  * @param custNo
	  * @throws HsException
	  */
	 public void delBankCard(String bankCardNo,String custNo)throws HsException;

}
