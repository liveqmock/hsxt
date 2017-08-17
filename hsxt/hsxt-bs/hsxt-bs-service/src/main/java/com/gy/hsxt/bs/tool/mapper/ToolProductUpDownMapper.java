/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.bs.tool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.tool.ToolProductUpDown;

/**
 * @Description: 工具产品上架(包含新增及价格调整)审批、下架审批
 * 
 * @Package: com.gy.hsxt.bs.tool.mapper
 * @ClassName: ToolProductUpDownMapper
 * @author: yangjianguo
 * @date: 2016-2-26 下午2:36:58
 * @version V1.0
 */
@Repository(value = "toolProductUpDownMapper")
public interface ToolProductUpDownMapper {
    /**
     * 查询工具产品上下架待审批记录
     * 
     * @param applyType
     *            申请类型：1：价格审批(就是上架审批) 2：下架审批
     * @param productName
     *            产品名称，支持模糊查询
     * @param categoryName
     *            工具类别，支持模糊查询
     * @param exeCustId
     *            当前经办人编号
     * @return
     */
    List<ToolProductUpDown> findToolProductUpDownListPage(@Param("applyType") int applyType,
            @Param("productName") String productName, @Param("categoryName") String categoryName,
            @Param("exeCustId") String exeCustId);

    /**
     * 根据ID查询工具产品上下架申请记录
     * 
     * @param applyId
     *            申报编号
     * @return
     */
    ToolProductUpDown getToolProductUpDownById(@Param("applyId") String applyId);
    
    /**
     * 根据产品ID查询工具产品上下架申请记录
     * @param productId
     * @return
     */
    ToolProductUpDown getToolProductUpDownByProductId(String productId);

    /**
     * 新增工具产品上架或下架申请
     * 
     * @param applyBean
     * @return
     */
    int insertToolProductUpDown(ToolProductUpDown applyBean);

    /**
     * 审批后修改工具产品上下架审批信息
     * 
     * @param applyBean
     * @return
     */
    int updateToolProductUpDownForAppr(ToolProductUpDown applyBean);

}
