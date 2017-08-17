/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.apply.ResFeeAlloc;

/**
 * 
 * @Package: com.gy.hsxt.bs.apply.mapper
 * @ClassName: ResFeeAllocMapper
 * @Description: 资源费分配记录Mapper
 * 
 * @author: xiaofl
 * @date: 2016-1-14 下午3:26:22
 * @version V1.0
 */
@Repository
public interface ResFeeAllocMapper {

    /**
     * 保存资源费分配记录
     * 
     * @param list
     *            资源费分配记录列表
     */
    void batchSaveResFeeAlloc(List<ResFeeAlloc> list);

    /**
     * 根据Id查询资源费分配记录
     * 
     * @param detailId
     *            记录编号
     * @return 资源费分配记录
     */
    ResFeeAlloc queryResFeeAllocById(String detailId);

    /**
     * 根据申报单编号查询分配记录
     * 
     * @param applyId
     *            申报单编号
     * @return 资源费分配记录
     */
    List<ResFeeAlloc> queryResFeeAllocByApplyId(String applyId);

    /**
     * 根据申报单编号查询待分配的记录(今天以前未分配)
     * 
     * @param applyId
     *            申报单编号
     * @return 资源费分配记录
     */
    List<ResFeeAlloc> queryUnAllocByApplyId(String applyId);

    /**
     * 查询所有待分配的记录(今天以前未分配)
     * 
     * @return 资源费分配记录
     */
    List<ResFeeAlloc> queryUnAllocAll();
    
    /**
     * 查询所有未完成分配的申报编号(今天以前未分配)
     * @return
     */
    List<String> queryUnAllocApplyIds();

    /**
     * 按申报单号更新资源费分配记录的分配标识
     */
    int updateAllocFlag(String applyId);
    
    /**
     * 批量更新资源费分配记录的分配标识
     * @param resFeeAllocs
     * @return
     */
    int batchUpdateAllocFlag(List<ResFeeAlloc> resFeeAllocs);

}
