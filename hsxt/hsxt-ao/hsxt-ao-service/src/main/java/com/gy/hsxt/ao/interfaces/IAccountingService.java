/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.interfaces;

import com.gy.hsxt.common.exception.HsException;

/**
 * 记帐分解接口定义
 * 
 * @Package: com.gy.hsxt.ao.interfaces
 * @ClassName: IAccountingService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-26 下午9:13:08
 * @version V3.0.0
 */
public interface IAccountingService {

    /**
     * 保存记帐分解
     * 
     * @param obj
     *            分解对象
     * @throws HsException
     */
    public void saveAccounting(Object obj) throws HsException;

    /**
     * 销户记帐
     * 
     * @param obj
     *            记帐对象
     * @throws HsException
     */
    public void closeAccountAccounting(Object obj) throws HsException;
}
