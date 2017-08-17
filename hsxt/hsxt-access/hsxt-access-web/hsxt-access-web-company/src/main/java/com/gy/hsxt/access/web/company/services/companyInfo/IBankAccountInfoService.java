/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.services.companyInfo;

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
    public List<AsBankAcctInfo> findBankAccountList(String custId,String type) throws HsException;
	
	/**
	 * 新增
	 * @param bcInfo
	 * @throws HsException
	 */
	 public void addBankCard(AsBankAcctInfo bcInfo,String custType)throws HsException;
	 
	 /**
	  * 删除 
	  * @param bankCardNo
	  * @param custNo
	  * @throws HsException
	  */
	 public void delBankCard(String bankCardNo,String custType)throws HsException;

}
