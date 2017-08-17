/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.tempacct.service;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.bean.base.Query;
import com.gy.hsxt.bs.bean.tempacct.AccountName;
import com.gy.hsxt.bs.bean.tempacct.AccountNameQuery;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.tempacct.ReceiveAccountType;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.tempacct.interfaces.IAccountNameService;
import com.gy.hsxt.bs.tempacct.mapper.AccountNameMapper;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.HsAssert;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 账户名称业务实现
 *
 * @Package :com.gy.hsxt.bs.tempacct.service
 * @ClassName : AccountService
 * @Description : 账户名称业务实现
 * @Author : chenm
 * @Date : 2015/12/19 10:16
 * @Version V3.0.0.0
 */
@Service
public class AccountNameService implements IAccountNameService {

    /**
     * 业务服务私有配置参数
     **/
    @Resource
    private BsConfig bsConfig;

    /**
     * 注入 收款账户名称 Mapper接口类
     */
    @Resource
    private AccountNameMapper accountNameMapper;

    /**
     * 保存实体
     *
     * @param accountName 实体
     * @return string
     * @throws HsException
     */
    @Override
    public String saveBean(AccountName accountName) throws HsException {
        //系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:saveBean", "创建收款账户名称实体[accountName]：" + accountName);
        // 实体参数为null时抛出异常
        HsAssert.notNull(accountName, RespCode.PARAM_ERROR, "创建收款账户名称实体[accountName]为null");
        // 如果收款账户类型参数错误抛出异常
        // 收款账户类型 1：收款账户 2：节余不动款账户
        HsAssert.isTrue(ReceiveAccountType.checkType(accountName.getReceiveAccountType()), RespCode.PARAM_ERROR, "收款账户类型[receiveAccountType]参数错误");
        HsAssert.hasText(accountName.getReceiveAccountName(), RespCode.PARAM_ERROR, "收款账户名称[receiveAccountName]为空");
        HsAssert.hasText(accountName.getCreatedBy(), RespCode.PARAM_ERROR, "创建者[createdBy]为空");
        accountName.setReceiveAccountName(StringUtils.trimToEmpty(accountName.getReceiveAccountName()));//去空格

        //校验账户名称是否重复
        AccountName exist = queryUniqueBeanByQuery(AccountNameQuery.bulid(accountName));
        HsAssert.isNull(exist, BSRespCode.BS_ACCOUNT_NAME_EXIST, "同类型的账户名称已存在");
        try {
            // 生成guid
            accountName.setReceiveAccountId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));

            accountNameMapper.insertAccountName(accountName);
            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:saveBean", "params==>" + accountName, "创建收款账户名称成功");
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:saveBean", "创建收款账户名称失败，参数[accountName]:" + accountName, e);
            throw new HsException(BSRespCode.BS_ACCOUNT_NAME_DB_ERROR, "创建收款账户名称失败,原因:" + e.getMessage());
        }
        return accountName.getReceiveAccountId();
    }

    /**
     * 更新实体
     *
     * @param accountName 实体
     * @return int
     * @throws HsException
     */
    @Override
    public int modifyBean(AccountName accountName) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:modifyBean", "更新收款账户名称参数[accountName]:" + accountName);
        // 实体参数为null时抛出异常
        HsAssert.notNull(accountName, RespCode.PARAM_ERROR, "收款账户名称实体[accountName]为null");
        //校验主键ID
        HsAssert.hasText(accountName.getReceiveAccountId(), RespCode.PARAM_ERROR, "收款账户名称主键[receiveAccountId]为空");
        HsAssert.hasText(accountName.getReceiveAccountName(), RespCode.PARAM_ERROR, "收款账户名称[receiveAccountName]为空");
        HsAssert.hasText(accountName.getUpdatedBy(), RespCode.PARAM_ERROR, "更新操作者[updatedBy]为空");
        // 收款账户类型 1：收款账户 2：节余不动款账户
        HsAssert.isTrue(ReceiveAccountType.checkType(accountName.getReceiveAccountType()), RespCode.PARAM_ERROR, "参数收款账户名称类型[receiveAccountType]类型错误");
        //校验账户名称是否重复
        AccountName exist = queryUniqueBeanByQuery(AccountNameQuery.bulid(accountName));
        HsAssert.isTrue(exist == null || exist.getReceiveAccountId().equals(accountName.getReceiveAccountId()), BSRespCode.BS_ACCOUNT_NAME_EXIST, "同类型的账户名称已存在");
        try {
            int count = accountNameMapper.updateBean(accountName);
            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:modifyBean", "params==>" + accountName, "更新收款账户名称成功");
            return count;
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:modifyBean", "更新收款账户名称失败,参数[accountName]:" + accountName, e);
            throw new HsException(BSRespCode.BS_ACCOUNT_NAME_DB_ERROR, "更新收款账户名称失败,原因:" + e.getMessage());
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
    public AccountName queryBeanById(String id) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryBeanById", "查询某一条收款账户名称详情参数[receiveAccountId]:" + id);
        // 账户户名编号为空时抛出异常
        HsAssert.hasText(id, RespCode.PARAM_ERROR, "账户名称编号[receiveAccountId]为空");

        try {
            //查询操作
            return accountNameMapper.selectBeanById(id);
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryBeanById", "查询某一条收款账户名称详情失败,参数[receiveAccountId]:" + id, e);
            throw new HsException(BSRespCode.BS_ACCOUNT_NAME_DB_ERROR, "查询某一条收款账户名称详情失败,原因:" + e.getMessage());
        }
    }

    /**
     * 根据条件查询唯一收款账户名称
     *
     * @param accountNameQuery 查询实体
     * @return {@code AccountName}
     * @throws HsException
     */
    @Override
    public AccountName queryUniqueBeanByQuery(AccountNameQuery accountNameQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryUniqueBeanByQuery", "根据条件查询唯一收款账户名称参数[accountNameQuery]:" + accountNameQuery);
        // 账户户名编号为空时抛出异常
        HsAssert.notNull(accountNameQuery, RespCode.PARAM_ERROR, "查询实体[accountNameQuery]为null");
        HsAssert.hasText(accountNameQuery.getReceiveAccountName(), RespCode.PARAM_ERROR, "账户名称[receiveAccountName]为空");
        HsAssert.isTrue(ReceiveAccountType.checkType(accountNameQuery.getReceiveAccountType()), RespCode.PARAM_ERROR, "收款账户类型[receiveAccountType]参数错误");

        try {
            //查询操作
            return accountNameMapper.selectUniqueBeanByQuery(accountNameQuery);
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryBeanById", "根据条件查询唯一收款账户名称失败,参数[accountNameQuery]:" + accountNameQuery, e);
            throw new HsException(BSRespCode.BS_ACCOUNT_NAME_DB_ERROR, "根据条件查询唯一收款账户名称失败,原因:" + e.getMessage());
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
    public List<AccountName> queryListByQuery(Query query) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryListByQuery", "条件查询收款账户名称列表参数[query]:" + query);

        AccountNameQuery accountNameQuery = null;
        if (query != null) {
            HsAssert.isInstanceOf(AccountNameQuery.class, query, RespCode.PARAM_ERROR, "查询实体[query]不是AccountNameQuery类型");
            accountNameQuery = (AccountNameQuery) query;
        }
        try {
            return accountNameMapper.selectListByQuery(accountNameQuery);
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryListByQuery", "条件查询收款账户名称列表失败，参数[query]:" + query, e);
            throw new HsException(BSRespCode.BS_ACCOUNT_NAME_DB_ERROR, "条件查询收款账户名称列表失败,原因：" + e.getMessage());
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
    public List<AccountName> queryListForPage(Page page, Query query) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryAccountNameListForPage", "分页查询收款账户名称列表参数[query]为：" + query);

        // 分页对象为null抛出异常
        HsAssert.notNull(page, RespCode.PARAM_ERROR, "分页查询收款账户名称列表--分页对象[page]为null");

        AccountNameQuery accountNameQuery = null;
        if (query != null) {
            HsAssert.isInstanceOf(AccountNameQuery.class, query, RespCode.PARAM_ERROR, "查询实体[query]不是AccountNameQuery类型");
            accountNameQuery = (AccountNameQuery) query;
        }
        // 设置分页信息
        PageContext.setPage(page);
        try {
            // 获取当前页数据列表
            return accountNameMapper.queryAccountNameListPage(accountNameQuery);
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryAccountNameListForPage", "分页查询收款账户名称列表,参数[query]:" + query, e);

            throw new HsException(BSRespCode.BS_ACCOUNT_NAME_DB_ERROR, "分页查询收款账户名称列表,原因:" + e.getMessage());
        }
    }
}
