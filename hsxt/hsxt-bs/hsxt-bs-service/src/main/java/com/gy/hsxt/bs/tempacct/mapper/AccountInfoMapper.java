/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.tempacct.mapper;

import com.gy.hsxt.bs.bean.tempacct.AccountInfo;
import com.gy.hsxt.bs.bean.tempacct.AccountInfoQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 收款账户信息 Mapper接口类
 *
 * @Package : com.gy.hsxt.bs.tempacct.mapper
 * @ClassName : AccountingMapper
 * @Description :
 * @Author : liuhq
 * @Date : 2015-10-16 下午2:49:05
 * @Version V3.0
 */
@Repository
public interface AccountInfoMapper {
    /**
     * 创建 收款账户信息
     *
     * @param accountInfo 实体类 非null
     *                    <p/>
     *                    无异常便成功，Exception失败
     */
    void insertAccountInfo(AccountInfo accountInfo);

    /**
     * 分页查询 收款账户信息列表
     *
     * @param accountInfoQuery 查询实体
     * @return 返回分好页的数据列表
     */
    List<AccountInfo> selectBeanListPage(AccountInfoQuery accountInfoQuery);

    /**
     * 查询某一条收款账户信息
     *
     * @param receiveAccountInfoId 账户户名编号 必须 非null
     * @return 返回一个实体类
     */
    AccountInfo selectBeanById(@Param("receiveAccountInfoId") String receiveAccountInfoId);

    /**
     * 更新 收款账户信息
     *
     * @param account 实体类 非null
     *                <p/>
     *                无异常便成功，Exception失败
     * @return int
     */
    int updateAccountInfo(AccountInfo account);

    /**
     * 条件查询收款账户信息列表
     *
     * @return 返回分好页的数据列表
     */
    List<AccountInfo> selectListByQuery(AccountInfoQuery accountInfoQuery);

    /**
     * 收款账户信息唯一性查询
     *
     * @param accountInfoQuery 查询实体
     * @return AccountInfo
     */
    AccountInfo selectUniqueBeanByQuery(AccountInfoQuery accountInfoQuery);

    /**
     * 禁用账户信息
     *
     * @param accountInfoId 账户信息ID
     * @return {@code int}
     */
    int forbidAccountInfo(@Param("accountInfoId") String accountInfoId);
}
