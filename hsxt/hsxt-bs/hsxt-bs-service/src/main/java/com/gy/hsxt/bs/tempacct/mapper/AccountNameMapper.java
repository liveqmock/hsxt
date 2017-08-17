/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.tempacct.mapper;

import com.gy.hsxt.bs.bean.tempacct.AccountName;
import com.gy.hsxt.bs.bean.tempacct.AccountNameQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 收款账户名称 Mapper接口类
 *
 * @Package : com.gy.hsxt.bs.tempacct.mapper
 * @ClassName : AccountMapper
 * @Description :
 * @Author : liuhq
 * @Date : 2015-10-16 下午2:43:22
 * @Version V3.0
 */
@Repository
public interface AccountNameMapper {
    /**
     * 创建 收款账户名称
     *
     * @param accountName 实体类 非null
     *                    <p/>
     *                    无异常便成功，Exception失败
     */
    void insertAccountName(AccountName accountName);

    /**
     * 分页查询 收款账户名称列表
     *
     * @return 返回分好页的数据列表
     */
    List<AccountName> queryAccountNameListPage(AccountNameQuery accountNameQuery);

    /**
     * 查询某一条收款账户名称详情
     *
     * @param receiveAccountId 账户户名编号 必须 非null
     * @return 返回一个实体类
     */
    AccountName selectBeanById(@Param("receiveAccountId") String receiveAccountId);

    /**
     * 更新 收款账户名称
     *
     * @param accountName 实体类 非null
     *                    <p/>
     *                    无异常便成功，Exception失败
     * @return int
     */
    int updateBean(AccountName accountName);

    /**
     * 条件查询收款账户名称列表
     *
     * @param accountNameQuery 查询实体
     * @return list
     */
    List<AccountName> selectListByQuery(AccountNameQuery accountNameQuery);

    /**
     * 根据条件查询唯一收款账户名称
     *
     * @param accountNameQuery 查询实体
     * @return AccountName
     */
    AccountName selectUniqueBeanByQuery(AccountNameQuery accountNameQuery);

}
