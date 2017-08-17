/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.entstatus.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.entstatus.CloseOpenEnt;
import com.gy.hsxt.bs.bean.entstatus.CloseOpenEntDetail;
import com.gy.hsxt.bs.bean.entstatus.CloseOpenEntInfo;
import com.gy.hsxt.bs.bean.entstatus.CloseOpenEntQueryParam;

/**
 * 
 * @Package: com.gy.hsxt.bs.entstatus.mapper
 * @ClassName: CloseOpenEntMapper
 * @Description: 关闭开启系统管理Mapper
 * 
 * @author: xiaofl
 * @date: 2015-12-16 下午4:49:34
 * @version V1.0
 */
@Repository
public interface CloseOpenEntMapper {

    /**
     * 判断是否存在正在审批的开关系统申请
     * 
     * @param entCustId
     *            企业客户号
     * @return true 存在，false不存在
     */
    boolean existApplying(String entCustId);

    /**
     * 创建关闭/开启系统申请
     * 
     * @param closeOpenEnt
     *            关闭/开启系统信息
     */
    int createCloseOpenEnt(CloseOpenEnt closeOpenEnt);

    /**
     * 查询关开系统复核
     * 
     * @param closeOpenEntParam
     *            查询参数
     * @return 关闭/开启系统申请列表
     */
    List<CloseOpenEnt> queryCloseOpenEnt4ApprListPage(CloseOpenEntQueryParam closeOpenEntParam);

    /**
     * 查询开关系统审核查询
     * 
     * @param closeOpenEntParam
     *            查询参数
     * @return 关闭/开启系统申请列表
     */
    List<CloseOpenEnt> queryCloseOpenEntListPage(CloseOpenEntQueryParam closeOpenEntParam);

    /**
     * 查询关闭/开启系统申请详情
     * 
     * @param applyId
     *            申请编号
     * @return 关闭/开启系统申请详情
     */
    CloseOpenEntDetail queryCloseOpenEntDetail(String applyId);

    /**
     * 审批关闭/开启系统申请
     * 
     * @param applyId
     *            申请编号
     * @param status
     *            状态
     * @param apprRemark
     *            审批意见
     * @param apprOptCustId
     *            审批操作员客户号
     * @param apprOptCustName
     *            审批操作员名字
     */
    void apprCloseOpenEnt(@Param("applyId") String applyId, @Param("status") Integer status,
            @Param("apprRemark") String apprRemark, @Param("apprOptCustId") String apprOptCustId,
            @Param("apprOptCustName") String apprOptCustName);

    /**
     * 查询最近一次开启关闭系统信息（上一次）
     * 
     * @param entCustId
     *            企业客户号
     * @param applyType
     *            申请类型
     * @param thisApplyDate
     *            此次申请时间
     * @return 关闭系统信息
     */
    CloseOpenEntInfo queryLastCloseOpenEntInfo(@Param("entCustId") String entCustId,
            @Param("applyType") Integer applyType, @Param("thisApplyDate") String thisApplyDate);

    /**
     * 通过申请编号查询关闭/开启系统申请详情
     * 
     * @param applyId
     *            申请编号
     * @return 关闭/开启系统申请详情
     */
    CloseOpenEnt queryCloseEntById(String applyId);

}
