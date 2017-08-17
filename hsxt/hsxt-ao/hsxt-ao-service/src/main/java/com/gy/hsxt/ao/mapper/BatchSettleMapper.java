/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.ao.bean.BatchSettle;

/**
 * 终端批结算表 mapper dao映射类
 * 
 * @Package: com.gy.hsxt.ao.mapper
 * @ClassName: BatchSettleMapper
 * @Description: TODO
 * 
 * @author: liyh
 * @date: 2015-12-8 上午10:47:21
 * @version V1.0
 */
public interface BatchSettleMapper {

    /**
     * 根据结算批次信息查询是否已经存在批结算记录，如果有直接返回记录中的结果。 结算信息包括：企业客户号、企业互生号、批次编号、终端渠道、终端编号
     * 
     * @param batchSettle
     * @return
     */
    public Integer findBatchSettleByInfo(BatchSettle batchSettle);

    /**
     * 查询已存在结算信息
     * 
     * @param batchSettle
     * @return
     */
    public Integer findExistsBatchSettleInfo(BatchSettle batchSettle);

    /**
     * 根据结算批次信息查询是否已经存在批结算记录，如果有直接返回记录中的结果。 结算信息包括：企业客户号、企业互生号、批次编号、终端渠道、终端编号
     * 
     * @param batchSettle
     * @return
     */
    public String findBatchCheckIdByInfo(BatchSettle batchSettle);

    /**
     * 根据结算批次信息查询是否已经存在批结算记录，如果有直接返回记录中的结果。 结算信息包括：企业客户号、企业互生号、批次编号、终端渠道、终端编号
     * 
     * @param batchSettle
     * @return
     */
    public Integer findUploadFlagByInfo(BatchSettle batchSettle);

    /**
     * 保存批结算结果
     * 
     * @param batchSettle
     * @return
     */
    public int insertBatchSettle(BatchSettle batchSettle);

    /**
     * 更新批上传时间和上传标识
     * 
     * @param batchSettle
     * @return
     */
    public Integer updateBatchSettleByInfo(BatchSettle batchSettle);

    /**
     * 插入 终端批结算数据
     * 
     * @param batchUpload
     *            批结算集合
     * @return
     */
    public int insertBatchSettle(@Param("batchSettle") List<BatchSettle> batchSettle);

    /**
     * 删除前几个月的批结算数据
     * 
     * @param month
     *            前几个月
     */
    public void deleteBatchSettleLastMath(@Param("month") Integer month);

}
