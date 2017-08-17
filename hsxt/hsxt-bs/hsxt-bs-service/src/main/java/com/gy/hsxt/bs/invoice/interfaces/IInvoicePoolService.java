/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.invoice.interfaces;

import com.gy.hsxt.bs.bean.invoice.InvoicePool;
import com.gy.hsxt.bs.common.interfaces.IBaseService;

/**
 * 发票统计池业务层接口
 *
 * @Package :com.gy.hsxt.bs.invoice.interfaces
 * @ClassName : IInvoicePoolService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/15 12:30
 * @Version V3.0.0.0
 */
public interface IInvoicePoolService extends IBaseService<InvoicePool> {

    /**
     * 根据客户ID和业务类型查询唯一的发票池记录
     *
     * @param custId  客户ID
     * @param bizType 业务类型
     * @return pool
     */
    InvoicePool queryBeanByIdAndType(String custId, String bizType);
}
