/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.tempacct.mapper;

import com.gy.hsxt.bs.bean.tempacct.TempAcctRefund;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 临帐管理 退款申请 Mapper接口
 *
 * @Package : com.gy.hsxt.bs.tempacct.mapper
 * @ClassName : TempAcctRefundMapper
 * @Description :
 * @Author : liuhq
 * @Date : 2015-10-21 上午10:51:44
 * @Version V3.0
 */
@Repository
public interface TempAcctRefundMapper {
    /**
     * 创建 临账退款申请单
     *
     * @param tempAcctRefund 实体类 非null
     * @return int
     */
    int createTempAcctRefund(TempAcctRefund tempAcctRefund);

    /**
     * 审批 临账退款申请单
     *
     * @param tempAcctRefund 退款申请编号 非null
     * @return int
     */
    int updateBean(TempAcctRefund tempAcctRefund);

    /**
     * 更加临账ID查询退款信息
     *
     * @param debitId 临账ID
     * @return bean
     */
    TempAcctRefund selectBeanByDebitId(@Param("debitId") String debitId);
}
