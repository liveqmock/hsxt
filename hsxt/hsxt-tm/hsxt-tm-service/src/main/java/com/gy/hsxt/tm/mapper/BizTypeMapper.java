/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.tm.bean.BizType;

/**
 * 业务类型mapper dao映射
 * 
 * @Package: com.gy.hsxt.tm.mapper
 * @ClassName: BizTypeMapper
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-12 下午3:51:03
 * @version V3.0.0
 */
public interface BizTypeMapper {

    /**
     * 新增业务类型
     * 
     * @param bizType
     *            业务类型信息
     * @return 成功记录数
     */
    public int insertBizType(BizType bizType);

    /**
     * 修改业务类型名称
     * 
     * @param bizType
     *            业务类型信息
     * @return 成功记录数
     */
    public int updateBizTypeName(BizType bizType);

    /**
     * 删除业务类型
     * 
     * @param bizTypeCode
     *            类型代码
     * @return 成功记录数
     */
    public int deleteBizType(@Param("bizTypeCode") String bizTypeCode);

    /**
     * 查询业务类型列表
     * 
     * @param permIdList
     *            权限ID列表
     * @return 业务类型列表
     */
    public List<BizType> findBizTypeList(@Param("permIdList") List<String> permIdList);

    /**
     * 查询权限ID列表
     */
    public List<String> findPermIdList();

    /**
     * 查询业务类型列表
     * 
     * @return 业务类型列表
     */
    public List<BizType> findListByCustType(@Param("bizEntCustType") Integer bizEntCustType);

    /**
     * 查询业务类型是否已存在
     * 
     * @param bizType
     *            业务类型代码
     * @return 记录数
     */
    public int findBizTypeExists(@Param("bizType") String bizType);

}
