/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.accountCompany;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;

/***
 * 城市税收对接账户
 * @Package: com.gy.hsxt.access.web.scs.services.accountManagement
 * @ClassName: ICityTaxAccountService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2016-1-16 下午7:21:32
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
public interface ICityTaxAccountService extends IBaseService {
    
    /**
     * 获取税率
     * @param scsBase
     * @return
     * @throws HsException
     */
    public String queryTax(String engCustId) throws HsException;

}
