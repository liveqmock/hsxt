/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.companyInfo;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;


public interface ISaicRegisterService extends IBaseService{
	
	/***
	 * 修改在工商局注册的信息
	 * @param map
	 * @return
	 * @throws HsException
	 */
	public void updateEntBaseInfo(AsEntBaseInfo srInfo) throws HsException;
	
	
	public Object findSaicRegisterInfo(String custId)  throws HsException;
	
}
