/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.tempacct.service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.bean.tempacct.*;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.tempacct.interfaces.IAccountInfoService;
import com.gy.hsxt.bs.tempacct.interfaces.IAccountNameService;
import com.gy.hsxt.bs.tempacct.interfaces.IAccountService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 临帐帐户信息 实现类
 *
 * @Package : com.gy.hsxt.bs.tempacct.service
 * @ClassName : TempAccountService
 * @Description :
 * @Author : liuhq
 * @Date : 2015-10-16 下午2:57:38
 * @Version V3.0
 */
@Service
public class AccountService implements IAccountService {
    /**
     * 业务服务私有配置参数
     **/
    @Autowired
    private BsConfig bsConfig;

    /**
     * 收款账户名称业务层接口
     */
    @Resource
    private IAccountNameService accountNameService;

    /**
     * 账户信息业务接口
     */
    @Resource
    private IAccountInfoService accountInfoService;

    // ===================== 收款账户名称 开始====================

    /**
     * 创建 收款账户名称
     *
     * @param accountName 实体类 非null
     *                    <p/>
     *                    无异常便成功，Exception失败
     * @throws HsException
     */
    @Override
    public void createAccountName(AccountName accountName) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:createAccountName", "创建收款账户名称实体[accountName]：" + accountName);
        //创建收款账户名
        accountNameService.saveBean(accountName);
    }

    /**
     * 分页查询 收款账户名称列表
     *
     * @param page             分页对象 非null
     * @param accountNameQuery 查询实体
     * @return 返回分好页的数据列表
     * @throws HsException
     */
    @Override
    public PageData<AccountName> queryAccountNameListForPage(Page page, AccountNameQuery accountNameQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryAccountNameListForPage", "分页查询收款账户名称列表--分页对象[page]为：" + page);

        // 获取当前页数据列表
        List<AccountName> accountNames = accountNameService.queryListForPage(page, accountNameQuery);

        return PageData.bulid(page.getCount(), accountNames);
    }

    /**
     * 查询某一条收款账户名称详情
     *
     * @param receiveAccountId 账户户名编号 必须 非null
     * @return 返回一个实体类
     * @throws HsException
     */
    @Override
    public AccountName queryAccountNameDetail(String receiveAccountId) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryAccountNameDetail", "查询某一条收款账户名称详情参数[receiveAccountId]:" + receiveAccountId);
        //查询某一条收款账户名称详情
        return accountNameService.queryBeanById(receiveAccountId);
    }

    /**
     * 更新 收款账户名称
     *
     * @param accountName 实体类 非null
     *                    <p/>
     *                    无异常便成功，Exception失败
     * @throws HsException
     */
    @Override
    public void modifyAccountName(AccountName accountName) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:modifyAccountName", "更新收款账户名称参数[accountName]:" + accountName);
        //更新收款账户名称
        accountNameService.modifyBean(accountName);
    }

    /**
     * 条件查询收款账户名称列表
     *
     * @return 返回分好页的数据列表
     * @throws HsException
     */
    @Override
    public List<AccountName> queryAccountNameList(AccountNameQuery accountNameQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryAccountNameList", "条件查询收款账户名称列表参数[accountNameQuery]:" + accountNameQuery);
        //条件查询收款账户名称列表
        return accountNameService.queryListByQuery(accountNameQuery);
    }

    // ===================== 收款账户信息 开始====================

    /**
     * 创建收款账户信息
     *
     * @param accountInfo 实体类 非null
     *                    <p/>
     *                    无异常便成功，Exception失败
     * @throws HsException
     */
    @Override
    public void createAccountInfo(AccountInfo accountInfo) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:createAccountInfo", "创建收款账户信息实体[accountInfo]:" + accountInfo);
        //创建收款账户信息
        accountInfoService.saveBean(accountInfo);
    }

    /**
     * 分页查询收款账户信息列表
     *
     * @param page             分页对象 非null
     * @param accountInfoQuery 查询实体
     * @return 返回分好页的数据列表
     * @throws HsException
     */
    @Override
    public PageData<AccountInfo> queryAccountInfoListForPage(Page page, AccountInfoQuery accountInfoQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryAccountInfoListForPage", "分页查询收款账户信息列表参数[accountInfoQuery]:" + accountInfoQuery);

        List<AccountInfo> accountInfos = accountInfoService.queryListForPage(page, accountInfoQuery);

        return PageData.bulid(page.getCount(), accountInfos);
    }

    /**
     * 查询某一条收款账户信息
     *
     * @param receiveAccountInfoId 账户户名编号 必须 非null
     * @return 返回一个实体类
     * @throws HsException
     */
    @Override
    public AccountInfo queryAccountInfoBean(String receiveAccountInfoId) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryAccountInfoBean", "查询某一条收款账户信息参数[receiveAccountInfoId]:" + receiveAccountInfoId);
        //查询某一条收款账户信息
        return accountInfoService.queryBeanById(receiveAccountInfoId);
    }

    /**
     * 更新收款账户信息
     *
     * @param accountInfo 实体类 非null
     *                    <p/>
     *                    无异常便成功，Exception失败
     * @throws HsException
     */
    @Override
    public void modifyAccountInfo(AccountInfo accountInfo) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:modifyAccountInfo", "更新收款账户信息参数[accountInfo]:" + accountInfo);
        //更新收款账户信息
        accountInfoService.modifyBean(accountInfo);
    }

    /**
     * 禁用账户信息
     *
     * @param receiveAccountInfoId 收款账户信息ID
     * @throws HsException
     */
    @Override
    public void forbidAccountInfo(String receiveAccountInfoId) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:forbidAccountInfo", "禁用账户信息参数[receiveAccountInfoId]:" + receiveAccountInfoId);
        //禁用账户信息
        accountInfoService.forbidAccountInfo(receiveAccountInfoId);
    }

    /**
     * 获取收款账户信息下拉菜单列表
     *
     * @return 账户信息选项列表
     * @throws HsException
     */
    @Override
    public List<AccountOption> queryAccountInfoDropdownMenu() throws HsException {

        List<AccountOption> options = new ArrayList<>();
        AccountInfoQuery query = new AccountInfoQuery();
        query.setIsActive("Y");//未被禁用的
        //查询所有收款账户信息
        List<AccountInfo> list = accountInfoService.queryListByQuery(query);
        if (CollectionUtils.isNotEmpty(list)) {
            for (AccountInfo accountInfo : list) {
                //封装处理数据
                options.add(AccountOption.valueOf(accountInfo));
            }
        }
        return options;
    }
}
