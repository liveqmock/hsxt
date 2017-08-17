/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.invoice.mapper;

import com.gy.hsxt.bs.bean.invoice.Invoice;
import com.gy.hsxt.bs.bean.invoice.InvoicePlat;
import com.gy.hsxt.bs.bean.invoice.InvoiceQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 平台发票DAO接口
 *
 * @Package :com.gy.hsxt.bs.invoice.mapper
 * @ClassName : InvoiceApplyMapper
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/15 20:42
 * @Version V3.0.0.0
 */
@Repository
public interface InvoicePlatMapper {

    /**
     * 保存发票
     *
     * @param invoice 发票实体
     * @return int
     */
    int insertInvoicePlat(Invoice invoice);

    /**
     * 分页查询平台发票数据列表
     *
     * @param invoiceQuery 查询实体
     * @return list
     */
    List<InvoicePlat> selectBeanListPage(InvoiceQuery invoiceQuery);

    /**
     * 根据ID查询实体
     *
     * @param invoiceId 发票ID
     * @return 发票
     */
    InvoicePlat selectBeanById(@Param("invoiceId") String invoiceId);


    /**
     * 条件查询发票列表
     *
     * @param invoiceQuery 查询实体
     * @return list
     */
    List<InvoicePlat> selectBeanListByQuery(InvoiceQuery invoiceQuery);

    /**
     * 更新平台发票
     *
     * @param invoicePlat 平台发票
     * @return int
     */
    int updateBean(InvoicePlat invoicePlat);
}
