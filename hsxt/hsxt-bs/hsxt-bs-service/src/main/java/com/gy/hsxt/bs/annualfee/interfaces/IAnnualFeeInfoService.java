/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.annualfee.interfaces;

import com.gy.hsxt.bs.bean.annualfee.AnnualFeeInfo;
import com.gy.hsxt.bs.common.interfaces.IBaseService;
import com.gy.hsxt.common.exception.HsException;

/**
 * @Package :com.gy.hsxt.bs.annualfee.interfaces
 * @ClassName : IAnnualFeeInfoService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/9 16:53
 * @Version V3.0.0.0
 */
public interface IAnnualFeeInfoService extends IBaseService<AnnualFeeInfo> {

    /**
     * 修改支付完成的年费信息
     *
     * @param custId  客户号
     * @param orderNo 订单号
     * @return 修改前的截至日期
     * @throws HsException
     */
    String modifyBeanForPaid(String custId, String orderNo) throws HsException;

    /**
     * 判断是否欠年费
     *
     * @param custId 客户号
     * @return boolean
     * @throws HsException
     */
    boolean isArrear(String custId) throws HsException;
}
