/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.tempacct.interfaces;

import com.gy.hsxt.bs.bean.tempacct.AccountName;
import com.gy.hsxt.bs.bean.tempacct.AccountNameQuery;
import com.gy.hsxt.bs.common.interfaces.IBaseService;
import com.gy.hsxt.common.exception.HsException;

/**
 * 收款账户名称业务接口
 *
 * @Package :com.gy.hsxt.bs.tempacct.interfaces
 * @ClassName : IAccountService
 * @Description : 收款账户名称业务接口
 * @Author : chenm
 * @Date : 2015/12/19 10:15
 * @Version V3.0.0.0
 */
public interface IAccountNameService extends IBaseService<AccountName> {

    /**
     * 根据条件查询唯一收款账户名称
     *
     * @param accountNameQuery 查询实体
     * @return {@code AccountName}
     * @throws HsException
     */
    AccountName queryUniqueBeanByQuery(AccountNameQuery accountNameQuery) throws HsException;
}
