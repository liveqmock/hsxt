/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.invoice.mapper;

import com.gy.hsxt.bs.bean.invoice.InvoicePool;
import com.gy.hsxt.bs.bean.invoice.InvoicePoolQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 发票池统计Dao接口
 *
 * @Package :com.gy.hsxt.bs.invoice.mapper
 * @ClassName : InvoicePoolMapper
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/15 12:36
 * @Version V3.0.0.0
 */
@Repository
public interface InvoicePoolMapper {

    /**
     * 保存发票统计
     *
     * @param invoicePool 发票池
     * @return int
     */
    int insertInvoicePool(InvoicePool invoicePool);


    /**
     * 根据条件查询发票池记录
     *
     * @param invoicePoolQuery 查询实体
     * @return list
     */
    List<InvoicePool> selectListByQuery(InvoicePoolQuery invoicePoolQuery);

    /**
     * 分页查询发票池数据列表
     *
     * @param invoicePoolQuery 查询实体
     * @return list
     */
    List<InvoicePool> selectBeanListPage(InvoicePoolQuery invoicePoolQuery);

    /**
     * 根据客户ID和业务类型查询唯一的发票池记录
     *
     * @param custId  客户ID
     * @param bizType 业务类型
     * @return pool
     */
    InvoicePool selectBeanByIdAndType(@Param("custId") String custId, @Param("bizType") String bizType);

    /**
     * 更新发票池数据
     *
     * @param invoicePool 发票池数据
     * @return int
     */
    int updateBean(InvoicePool invoicePool);
}
