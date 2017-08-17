/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.invoice.mapper;

import com.gy.hsxt.bs.bean.invoice.InvoiceCust;
import com.gy.hsxt.bs.bean.invoice.InvoiceQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 客户发票Dao接口
 *
 * @Package :com.gy.hsxt.bs.invoice.mapper
 * @ClassName : InvoiceCustMapper
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/16 14:44
 * @Version V3.0.0.0
 */
@Repository
public interface InvoiceCustMapper {

    /**
     * 保存发票申请
     *
     * @param invoiceCust 发票实体
     * @return int
     */
    int insertInvoiceCust(InvoiceCust invoiceCust);

    /**
     * 分页查询客户发票数据列表
     *
     * @param invoiceQuery 查询实体
     * @return list
     */
    List<InvoiceCust> selectBeanListPage(InvoiceQuery invoiceQuery);

    /**
     * 根据ID查询实体
     *
     * @param invoiceId 发票ID
     * @return 发票
     */
    InvoiceCust selectBeanById(@Param("invoiceId") String invoiceId);

    /**
     * 更新客户发票
     *
     * @param invoiceCust 发票
     * @return int
     */
    int updateBean(InvoiceCust invoiceCust);
}
