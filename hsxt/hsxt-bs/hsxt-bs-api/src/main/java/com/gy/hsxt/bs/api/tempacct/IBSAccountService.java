/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.api.tempacct;

import com.gy.hsxt.bs.bean.tempacct.*;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

import java.util.List;

/**
 * 临帐帐户信息接口类
 *
 * @Package : com.gy.hsxt.bs.api.tempacct
 * @ClassName : ITempAcctService
 * @Description :
 * 临帐帐户信息接口类
 * @Author : liuhq
 * @Date : 2015-9-7 下午3:51:42
 * @Version V3.0
 */
public interface IBSAccountService {

    // ===================== 收款账户名称 开始====================

    /**
     * 创建 收款账户名称
     *
     * @param accountName 实体类 非null
     *                    <p/>
     *                    无异常便成功，Exception失败
     * @throws HsException
     */
    void createAccountName(AccountName accountName) throws HsException;

    /**
     * 分页查询收款账户名称列表
     *
     * @param page             分页对象 非null
     * @param accountNameQuery 查询实体
     * @return 返回分好页的数据列表
     * @throws HsException
     */
    PageData<AccountName> queryAccountNameListForPage(Page page, AccountNameQuery accountNameQuery) throws HsException;

    /**
     * 查某一条收款账户名称详情
     *
     * @param receiveAccountId 账户户名编号 必须 非null
     * @return 返回一个实体类
     * @throws HsException
     */
    AccountName queryAccountNameDetail(String receiveAccountId) throws HsException;

    /**
     * 更新 收款账户名称
     *
     * @param accountName 实体类 非null
     *                    <p/>
     *                    无异常便成功，Exception失败
     * @throws HsException
     */
    void modifyAccountName(AccountName accountName) throws HsException;

    /**
     * 条件查询收款账户名称列表
     *
     * @param accountNameQuery 查询实体
     * @return 返回分好页的数据列表
     * @throws HsException
     */
    List<AccountName> queryAccountNameList(AccountNameQuery accountNameQuery) throws HsException;

    // ===================== 收款账户信息 开始====================

    /**
     * 创建 收款账户信息
     *
     * @param accountInfo 实体类 非null
     *                    <p/>
     *                    无异常便成功，Exception失败
     * @throws HsException
     */
    void createAccountInfo(AccountInfo accountInfo) throws HsException;

    /**
     * 分页查询 收款账户信息列表
     *
     * @param page             分页对象 非null
     * @param accountInfoQuery 查询实体
     * @return 返回分好页的数据列表
     * @throws HsException
     */
    PageData<AccountInfo> queryAccountInfoListForPage(Page page, AccountInfoQuery accountInfoQuery) throws HsException;

    /**
     * 查询某一条收款账户信息
     *
     * @param receiveAccountInfoId 账户户名编号 必须 非null
     * @return 返回一个实体类
     * @throws HsException
     */
    AccountInfo queryAccountInfoBean(String receiveAccountInfoId) throws HsException;

    /**
     * 更新 收款账户信息
     *
     * @param accountInfo 实体类 非null
     *                    <p/>
     *                    无异常便成功，Exception失败
     * @throws HsException
     */
    void modifyAccountInfo(AccountInfo accountInfo) throws HsException;

    /**
     * 禁用账户信息
     *
     * @param receiveAccountInfoId 收款账户信息ID
     * @throws HsException
     */
    void forbidAccountInfo(String receiveAccountInfoId) throws HsException;

    /**
     * 获取 收款账户信息下拉菜单列表
     *
     * @return 返回分好页的数据列表
     * @throws HsException
     */
    List<AccountOption> queryAccountInfoDropdownMenu() throws HsException;

}
