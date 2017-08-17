/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.annualfee.interfaces;

import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrder;
import com.gy.hsxt.bs.common.interfaces.IBaseService;
import com.gy.hsxt.common.exception.HsException;

/**
 * @Package :com.gy.hsxt.bs.annualfee.interfaces
 * @ClassName : IAnnualFeeOrderService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/11 14:10
 * @Version V3.0.0.0
 */
public interface IAnnualFeeOrderService extends IBaseService<AnnualFeeOrder> {

    /**
     * 查询某企业未付款的年费业务单的编号
     *
     * @param custId 企业客户号
     * @return order
     */
    AnnualFeeOrder queryBeanForWaitPayByCustId(String custId) throws HsException;

    /**
     * 修改年费业务单的金额
     *
     * @param annualFeeOrder 年费业务单
     * @return int
     * @throws HsException
     */
    int modifyBeanForAmount(AnnualFeeOrder annualFeeOrder) throws HsException;

    /**
     * 支付完成后修改年费业务单
     *
     * @param annualFeeOrder 年费业务单
     * @param note 是否实时记账
     * @param orgEndDate 修改前的截止日期
     * @return int
     * @throws HsException
     */
    int modifyBeanForPaid(AnnualFeeOrder annualFeeOrder,String orgEndDate,boolean note)throws HsException;
}
