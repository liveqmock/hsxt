/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.base.OptHisInfo;

/**
 * 
 * @Package: com.gy.hsxt.bs.common.mapper
 * @ClassName: OperationMapper
 * @Description: 操作Mapper
 * 
 * @author: xiaofl
 * @date: 2015-12-17 下午6:05:52
 * @version V1.0
 */
@Repository
public interface OperationMapper {

    /**
     * 创建操作历史
     * 
     * @param map
     */
    public void createOptHis(Map<String, Object> map);

    /**
     * 创建审批记录
     * 
     * @param map
     */
    public void createApprHis(Map<String, Object> map);

    // /**
    // * 分页查询审批记录历史
    // *
    // * @param applyId
    // * 申请编号
    // * @param tableName
    // * 表名
    // * @return 审批记录历史列表
    // */
    // public List<ApprOperationHis> queryApprHisListPage(@Param("applyId")
    // String applyId,
    // @Param("tableName") String tableName);

    /**
     * 分页查询操作历史
     * 
     * @param applyId
     *            申请编号
     * @param tableName
     *            表名
     * @return 操作历史
     */
    public List<OptHisInfo> queryOptHisListPage(@Param("applyId") String applyId, @Param("tableName") String tableName);

    /**
     * 分页查询操作历史，过滤某些办理状态
     * @param applyId
     * @param tableName
     * @param excludeResult
     * @return
     */
    List<OptHisInfo>  queryOptHisFilterListPage(@Param("applyId") String applyId, @Param("tableName") String tableName, @Param("excludeStatusArray")int[] excludeStatusArray);
    /**
     * 查询全部审批记录历史
     * 
     * @param applyId
     *            申请编号
     * @param tableName
     *            表名
     * @return 审批记录历史列表
     */
    public List<OptHisInfo> queryOptHisAll(@Param("applyId") String applyId, @Param("tableName") String tableName);

    // /**
    // * 查询申报操作历史
    // *
    // * @param applyId
    // * 申请编号
    // * @return 申报操作历史
    // */
    // public List<DeclareHisInfo> queryDeclareOptHisListPage(@Param("applyId")
    // String applyId);

}
