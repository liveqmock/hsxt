/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.tempacct.mapper;

import com.gy.hsxt.bs.bean.tempacct.Debit;
import com.gy.hsxt.bs.bean.tempacct.DebitQuery;
import com.gy.hsxt.bs.bean.tempacct.DebitSum;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 临账Dao层接口
 *
 * @Package :com.gy.hsxt.bs.tempacct.mapper
 * @ClassName : DebitMapper
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/24 11:46
 * @Version V3.0.0.0
 */
@Repository
public interface DebitMapper {
    /**
     * 创建 临帐记录
     *
     * @param debit 实体类 非null
     * @return int
     */
    int insertBean(Debit debit);

    /**
     * 更新临账记录
     *
     * @param debit 实体类
     * @return int
     */
    int updateBean(Debit debit);

    /**
     * 分页查询 临帐记录列表
     *
     * @param debitQuery 查询实体类
     * @return 返回分页的数据列表
     */
    List<Debit> selectBeanListPage(DebitQuery debitQuery);

    /**
     * 分页查询临账录入复核/退款复核列表
     *
     * @param debitQuery 查询实体类
     * @return 返回分页的数据列表
     */
    List<Debit> selectTaskListPage(DebitQuery debitQuery);

    /**
     * 获取 某一条临帐记录
     *
     * @param debitId 临账记录编号 非空
     * @return 返回实体类
     */
    Debit selectBeanById(@Param(value = "debitId") String debitId);

    /**
     * 更新临账记录状态
     *
     * @param debit 临账记录实体
     * @return int
     */
    int updateDebitStatus(Debit debit);

    /**
     * 分页查询 临帐记录按帐户分组统计总金额
     *
     * @param debitQuery 查询对象
     * @return 返回分页的数据列表
     */
    List<DebitSum> selectDebitSumListPage(DebitQuery debitQuery);

    /**
     * 查询临账统计详情
     *
     * @param receiveAccountName 收款账户名称
     * @param debitQuery 查询实体
     * @return list
     */
    List<Debit> selectDebitSumDetail(@Param("receiveAccountName") String receiveAccountName,@Param("debitQuery") DebitQuery debitQuery);

    /**
     * 统计不动款的总金额
     *
     * @param debitStatus 临账状态
     * @return 返回不动款的总金额
     */
    String frozenDebitSum(@Param("debitStatus") int debitStatus);

    /**
     * 根据条件查询临账记录列表
     *
     * @param debitQuery 查询实体
     * @return {@link List}
     */
    List<Debit> selectBeanListByQuery(DebitQuery debitQuery);
}
