/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.invoice.interfaces;

import com.gy.hsxt.bs.bean.invoice.InvoicePlat;
import com.gy.hsxt.bs.common.interfaces.IBaseService;
import com.gy.hsxt.common.exception.HsException;

/**
 * 平台发票业务接口
 *
 * @Package :com.gy.hsxt.bs.invoice.interfaces
 * @ClassName : IInvoiceApplyService
 * @Description : 平台发票业务接口
 * @Author : chenm
 * @Date : 2015/12/15 20:17
 * @Version V3.0.0.0
 */
public interface IInvoicePlatService extends IBaseService<InvoicePlat> {

    /**
     * 修改配送方式
     *
     * @param invoicePlat 平台发票
     * @return boolean
     * @throws HsException
     */
    int modifyPostWay(InvoicePlat invoicePlat) throws HsException;
}
