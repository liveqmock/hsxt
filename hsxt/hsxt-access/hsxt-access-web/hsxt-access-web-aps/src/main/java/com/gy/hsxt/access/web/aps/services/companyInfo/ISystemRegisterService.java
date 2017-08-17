/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.companyInfo;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;


public interface ISystemRegisterService extends IBaseService{
	public AsEntBaseInfo  findSysRegisterInfo(String custId);
}
