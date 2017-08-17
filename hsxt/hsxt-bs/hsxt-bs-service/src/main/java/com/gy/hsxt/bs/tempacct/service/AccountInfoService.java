/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.tempacct.service;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.bean.base.Query;
import com.gy.hsxt.bs.bean.tempacct.AccountInfo;
import com.gy.hsxt.bs.bean.tempacct.AccountInfoQuery;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.tempacct.interfaces.IAccountInfoService;
import com.gy.hsxt.bs.tempacct.mapper.AccountInfoMapper;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.HsAssert;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 收款账户信息实现
 *
 * @Package :com.gy.hsxt.bs.tempacct.service
 * @ClassName : AccountInfoService
 * @Description : 收款账户信息实现
 * @Author : chenm
 * @Date : 2015/12/19 10:32
 * @Version V3.0.0.0
 */
@Service
public class AccountInfoService implements IAccountInfoService {

    /**
     * 业务服务私有配置参数
     **/
    @Resource
    private BsConfig bsConfig;

    /**
     * 注入 收款账户信息 Mapper接口类
     */
    @Resource
    private AccountInfoMapper accountInfoMapper;

    /**
     * 保存实体
     *
     * @param accountInfo 实体
     * @return string
     * @throws HsException
     */
    @Override
    public String saveBean(AccountInfo accountInfo) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:saveBean", "创建收款账户信息实体[accountInfo]:" + accountInfo);
        // 实体参数为null时抛出异常
        HsAssert.notNull(accountInfo, RespCode.PARAM_ERROR, "创建收款账户信息实体[accountInfo]为null");
        HsAssert.hasText(accountInfo.getReceiveAccountId(), RespCode.PARAM_ERROR, "收款账户名称ID[receiveAccountId]为空");
        HsAssert.hasText(accountInfo.getBankId(), RespCode.PARAM_ERROR, "银行ID[bankId]为空");
        HsAssert.hasText(accountInfo.getBankBranchName(), RespCode.PARAM_ERROR, "银行支行名称[bankBranchName]为空");
        HsAssert.hasText(accountInfo.getReceiveAccountNo(), RespCode.PARAM_ERROR, "收款账号[receiveAccountNo]为空");
        HsAssert.hasText(accountInfo.getBankName(), RespCode.PARAM_ERROR, "银行名称[bankName]为空");
        HsAssert.hasText(accountInfo.getCreatedBy(), RespCode.PARAM_ERROR, "创建者[createdBy]为空");
        //校验唯一性
        AccountInfo exist = queryUniqueBeanByQuery(AccountInfoQuery.bulid(accountInfo));
        HsAssert.isNull(exist,BSRespCode.BS_ACCOUNT_INFO_EXIST,"同收账户名称同收款账号同银行名称的账户信息已存在");

        try {
            // 生成guid
            accountInfo.setReceiveAccountInfoId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
            //创建收款账户信息
            accountInfoMapper.insertAccountInfo(accountInfo);
            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:createAccountInfo", "params==>" + accountInfo, "创建收款账户信息成功");
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:createAccountInfo", "创建收款账户信息失败，参数[accountInfo]:" + accountInfo, e);
            throw new HsException(BSRespCode.BS_ACCOUNT_INFO_DB_ERROR, "创建收款账户信息失败,原因:" + e.getMessage());
        }
        return accountInfo.getReceiveAccountInfoId();
    }

    /**
     * 更新实体
     *
     * @param accountInfo 实体
     * @return int
     * @throws HsException
     */
    @Override
    public int modifyBean(AccountInfo accountInfo) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:modifyBean", "更新收款账户信息参数[accountInfo]:" + accountInfo);
        // 实体参数为null时抛出异常
        HsAssert.notNull(accountInfo, RespCode.PARAM_ERROR, "更新收款账户信息实体[accountInfo]为null");
        //收款账户信息参数校验
        HsAssert.hasText(accountInfo.getReceiveAccountInfoId(), RespCode.PARAM_ERROR, "收款账户信息编号[receiveAccountInfoId]为空");
        HsAssert.hasText(accountInfo.getReceiveAccountId(), RespCode.PARAM_ERROR, "收款账户名称编号[receiveAccountId]为空");
        HsAssert.hasText(accountInfo.getBankId(), RespCode.PARAM_ERROR, "银行ID[bankId]为空");
        HsAssert.hasText(accountInfo.getBankBranchName(), RespCode.PARAM_ERROR, "银行支行名称[bankBranchName]为空");
        HsAssert.hasText(accountInfo.getReceiveAccountNo(), RespCode.PARAM_ERROR, "收款账号[receiveAccountNo]为空");
        HsAssert.hasText(accountInfo.getUpdatedBy(), RespCode.PARAM_ERROR, "更新操作者[updatedBy]为空");
        //校验唯一性
        AccountInfo exist = queryUniqueBeanByQuery(AccountInfoQuery.bulid(accountInfo));
        HsAssert.isTrue(exist==null||exist.getReceiveAccountInfoId().equals(accountInfo.getReceiveAccountInfoId()),BSRespCode.BS_ACCOUNT_INFO_EXIST,"同收账户名称同收款账号同银行名称的账户信息已存在");

        try {
            int count = accountInfoMapper.updateAccountInfo(accountInfo);
            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:modifyBean", "params==>" + accountInfo, "更新收款账户信息成功");
            return count;
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:modifyBean", "更新收款账户信息失败,参数[accountInfo]:" + accountInfo, e);
            throw new HsException(BSRespCode.BS_ACCOUNT_INFO_DB_ERROR, "更新收款账户信息失败,原因:" + e.getMessage());
        }
    }

    /**
     * 根据ID查询实体
     *
     * @param id key
     * @return t
     * @throws HsException
     */
    @Override
    public AccountInfo queryBeanById(String id) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryBeanById", "查询某一条收款账户信息参数[receiveAccountInfoId]:" + id);
        // 账户户名编号为空时抛出异常
        HsAssert.hasText(id, RespCode.PARAM_ERROR, "获取收款账户信息--编号[receiveAccountInfoId]为空");

        try {
            return accountInfoMapper.selectBeanById(id);
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryAccountInfoBean", "查询某一条收款账户信息失败，参数[receiveAccountInfoId]:" + id, e);
            throw new HsException(BSRespCode.BS_ACCOUNT_INFO_DB_ERROR, "查询某一条收款账户信息失败,原因:" + e.getMessage());
        }
    }

    /**
     * 收款账户信息唯一性查询
     *
     * @param accountInfoQuery 查询实体
     * @return AccountInfo
     * @throws HsException
     */
    @Override
    public AccountInfo queryUniqueBeanByQuery(AccountInfoQuery accountInfoQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryBeanById", "收款账户信息唯一性查询参数[accountInfoQuery]:" + accountInfoQuery);
        // 实体参数为null时抛出异常
        HsAssert.notNull(accountInfoQuery, RespCode.PARAM_ERROR, "查询实体[accountInfoQuery]为null");
        //收款账户信息参数校验
        HsAssert.hasText(accountInfoQuery.getReceiveAccountId(), RespCode.PARAM_ERROR, "收款账户名称编号[receiveAccountId]为空");
        HsAssert.hasText(accountInfoQuery.getBankId(), RespCode.PARAM_ERROR, "银行ID[bankId]为空");
        HsAssert.hasText(accountInfoQuery.getReceiveAccountNo(), RespCode.PARAM_ERROR, "收款账号[receiveAccountNo]为空");

        try {
            return accountInfoMapper.selectUniqueBeanByQuery(accountInfoQuery);
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryAccountInfoBean", "收款账户信息唯一性查询失败，参数[accountInfoQuery]:" + accountInfoQuery, e);
            throw new HsException(BSRespCode.BS_ACCOUNT_INFO_DB_ERROR, "收款账户信息唯一性查询失败,原因:" + e.getMessage());
        }
    }

    /**
     * 查询实体列表
     *
     * @param query 查询实体
     * @return list
     * @throws HsException
     */
    @Override
    public List<AccountInfo> queryListByQuery(Query query) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryListByQuery", "条件查询收款账户信息列表参数[query]:" + query);

        AccountInfoQuery accountInfoQuery = null;
        if (query != null) {
            HsAssert.isInstanceOf(AccountInfoQuery.class, query, RespCode.PARAM_ERROR, "查询实体[query]不是AccountInfoQuery类型");
            accountInfoQuery = (AccountInfoQuery) query;
        }
        try {
            return accountInfoMapper.selectListByQuery(accountInfoQuery);
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryListByQuery", "条件查询收款账户信息列表失败，参数[query]:" + query, e);
            throw new HsException(BSRespCode.BS_ACCOUNT_INFO_DB_ERROR, "条件查询收款账户信息列表失败,原因:" + e.getMessage());
        }
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
        try {
            accountInfoMapper.forbidAccountInfo(receiveAccountInfoId);
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:forbidAccountInfo", "禁用账户信息参数[receiveAccountInfoId]:" + receiveAccountInfoId, e);
            throw new HsException(BSRespCode.BS_ACCOUNT_INFO_DB_ERROR, "禁用收款账户信息失败,原因:" + e.getMessage());
        }
    }

    /**
     * 分页查询列表
     *
     * @param page  分页对象
     * @param query 查询实体
     * @return list
     * @throws HsException
     */
    @Override
    public List<AccountInfo> queryListForPage(Page page, Query query) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryListForPage", "分页查询收款账户信息列表参数[accountInfoQuery]:" + query);
        // 分页对象为null抛出异常
        HsAssert.notNull(page, RespCode.PARAM_ERROR, "分页查询收款账户信息列表--分页对象[page]为null");

        AccountInfoQuery accountInfoQuery = null;
        if (query != null) {
            HsAssert.isInstanceOf(AccountInfoQuery.class, query, RespCode.PARAM_ERROR, "查询实体[query]不是AccountInfoQuery类型");
            accountInfoQuery = (AccountInfoQuery) query;
        }
        try {
            // 设置分页信息
            PageContext.setPage(page);

            // 获取当前页数据列表
            return accountInfoMapper.selectBeanListPage(accountInfoQuery);
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryListForPage", "分页查询收款账户信息列表失败,参数[query]:" + query, e);
            throw new HsException(BSRespCode.BS_ACCOUNT_INFO_DB_ERROR, "分页查询收款账户信息列表失败,原因:" + e.getMessage());
        }
    }
}
