/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.invoice.mapper;

import com.gy.hsxt.bs.bean.invoice.InvoiceList;
import com.gy.hsxt.bs.bean.invoice.InvoiceListQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 发票清单Dao接口
 *
 * @Package :com.gy.hsxt.bs.invoice.mapper
 * @ClassName : InvoiceListMapper
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/15 21:26
 * @Version V3.0.0.0
 */
@Repository
public interface InvoiceListMapper {

    /**
     * 保存发票清单
     *
     * @param invoiceList 清单
     * @return int
     */
    int insertInvoiceList(InvoiceList invoiceList);

    /**
     * 查询发票清单列表
     *
     * @param invoiceListQuery 查询实体
     * @return list
     */
    List<InvoiceList> selectListByQuery(InvoiceListQuery invoiceListQuery);
}
