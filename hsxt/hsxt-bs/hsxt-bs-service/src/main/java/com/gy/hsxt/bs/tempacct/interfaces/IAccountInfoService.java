/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.tempacct.interfaces;

import com.gy.hsxt.bs.bean.tempacct.AccountInfo;
import com.gy.hsxt.bs.bean.tempacct.AccountInfoQuery;
import com.gy.hsxt.bs.common.interfaces.IBaseService;
import com.gy.hsxt.common.exception.HsException;

/**
 * @Package :com.gy.hsxt.bs.tempacct.interfaces
 * @ClassName : IAccountInfoService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/19 10:18
 * @Version V3.0.0.0
 */
public interface IAccountInfoService extends IBaseService<AccountInfo> {

    /**
     * 收款账户信息唯一性查询
     *
     * @param accountInfoQuery 查询实体
     * @return AccountInfo
     * @throws HsException
     */
    AccountInfo queryUniqueBeanByQuery(AccountInfoQuery accountInfoQuery) throws HsException;

    /**
     * 禁用账户信息
     *
     * @param receiveAccountInfoId 收款账户信息ID
     * @throws HsException
     */
    void forbidAccountInfo(String receiveAccountInfoId) throws HsException;
}
