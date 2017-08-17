/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.tempacct.interfaces;

import com.gy.hsxt.bs.bean.tempacct.TempAcctRefund;
import com.gy.hsxt.bs.common.interfaces.IBaseService;
import com.gy.hsxt.common.exception.HsException;

/**
 * 临账退款业务接口
 *
 * @Package :com.gy.hsxt.bs.tempacct.interfaces
 * @ClassName : ITempAcctRefundService
 * @Description : 临账退款业务接口
 * @Author : chenm
 * @Date : 2015/12/21 16:06
 * @Version V3.0.0.0
 */
public interface ITempAcctRefundService extends IBaseService<TempAcctRefund> {

    /**
     * 查询临账退款申请信息
     *
     * @param debitId 临账ID
     * @return bean
     * @throws HsException
     */
    TempAcctRefund queryBeanByDebitId(String debitId) throws HsException;
}
