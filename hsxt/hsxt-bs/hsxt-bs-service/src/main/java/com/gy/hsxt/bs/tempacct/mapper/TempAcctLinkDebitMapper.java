/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.tempacct.mapper;

import com.gy.hsxt.bs.bean.tempacct.TempAcctLinkDebit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 临帐关联临帐记录 Mapper接口
 *
 * @Package : com.gy.hsxt.bs.tempacct.mapper
 * @ClassName : TempAcctLinkDebitMapper
 * @Description :  临帐关联临帐记录 Mapper接口
 * @Author : liuhq
 * @Date : 2015-10-20 上午11:11:05
 * @Version V3.0
 */
@Repository
public interface TempAcctLinkDebitMapper {
    /**
     * 创建 临帐关联 临帐记录
     *
     * @param tempAcctLinkDebit 临帐关联临帐记录 实体类 非null
     * @return int
     */
    int insertBean(TempAcctLinkDebit tempAcctLinkDebit);

    /**
     * 根据业务订单编号查询关联的临账关系实体列表
     *
     * @param applyId 申请编号
     * @return list
     */
    List<TempAcctLinkDebit> selectBeanListById(@Param("applyId") String applyId);

    /**
     * 更新关联关系实体
     *
     * @param tempAcctLinkDebit 对象
     * @return int
     */
    int updateBean(TempAcctLinkDebit tempAcctLinkDebit);
}
