/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bizfile.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.bizfile.ImageDoc;

/**
 * 示例图片mapper
 * 
 * @Package: com.gy.hsxt.bs.bizfile.mapper
 * @ClassName: ImageDocMapper
 * @Description:
 * 
 * @author: kongsl
 * @date: 2016-2-19 下午2:40:55
 * @version V3.0.0
 */
@Repository
public interface ImageDocMapper {
    /**
     * 插入示例图片
     * 
     * @param imageDoc
     *            示例图片对象
     * @return 成功记录数
     */
    public int insertImageDoc(ImageDoc imageDoc);

    /**
     * 修改示例图片
     * 
     * @param imageDoc
     *            示例图片对象
     * @return 成功记录数
     */
    public int updateImageDoc(ImageDoc imagedoc);

    /**
     * 修改文档状态
     * 
     * @param status
     *            状态
     * @param docId
     *            文档ID
     * @param isActive
     *            是否活动的
     * @return 成功记录数
     */
    public int updateImageDocStatus(@Param("status") Integer status, @Param("docId") String docId,
            @Param("isActive") boolean isActive);

    /**
     * 更新文档活动状态
     * 
     * @param docCode
     *            唯一标识
     * @param isActive
     *            是否活动的
     * @return 成功记录数
     */
    public int updateImageDocActive(@Param("docCode") String docCode, @Param("isActive") boolean isActive);

    /**
     * 停用文档
     * 
     * @param docCode
     *            唯一标识
     * @return 成功记录数
     */
    public int updateGroupDocStatus(@Param("docCode") String docCode);

    /**
     * 更新修改时间和修改人
     * 
     * @param updateBy
     *            修改人
     * @param docId
     *            文档编号
     * @return 成功记录数
     */
    public int updateTimeAndBy(@Param("updateBy") String updateBy, @Param("docId") String docId);

    /**
     * 根据文档唯一标识更新修改时间
     * 
     * @param docCode
     *            唯一标识
     * @param updateBy
     *            更新人
     * @return 成功记录数
     */
    public int updateStopTimeByCode(@Param("docCode") String docCode, @Param("updateBy") String updateBy);

    /**
     * 删除文档
     * 
     * @param docId
     *            文档ID
     * @return 成功记录数
     */
    public int deleteImageDoc(@Param("docId") String docId);

    /**
     * 查询历史版本
     * 
     * @param docCode
     *            唯一标识
     * @return 示例图片历史列表
     */
    public List<ImageDoc> findImageDocHis(@Param("docCode") String docCode);

    /**
     * 查询示例图片管理列表
     * 
     * @param status
     *            文档状态
     * @return 示例图片管理列表
     */
    public List<ImageDoc> findImageDocList(@Param("status") Integer status);

    /**
     * 查询文档详情
     * 
     * @param docId
     *            文档ID
     * @return 文档详情
     */
    public ImageDoc findImageDocInfo(@Param("docId") String docId);

    /**
     * 查询正常状态的文档
     * 
     * @param docCode
     *            唯一标识
     * @return 记录数
     */
    public int findNormalStatus(@Param("docCode") String docCode);

    /**
     * 查询正常状态的文档
     * 
     * @param docCode
     *            唯一标识
     * @return 记录数
     */
    public ImageDoc findNormalDoc(@Param("docCode") String docCode);

    /**
     * 查询是否存在唯一标识
     * 
     * @param docCode
     *            唯一标识
     * @return 记录数
     */
    public int findExistsDocCode(@Param("docCode") String docCode);

    /**
     * 查询是否存在非自己的标识
     * 
     * @param resDocCode
     *            源标识
     * @param tarDocCode
     *            目标标识
     * @return 记录数
     */
    public int findExistsTarDocCode(@Param("resDocCode") String resDocCode, @Param("tarDocCode") String tarDocCode);
}
