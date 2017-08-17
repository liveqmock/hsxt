/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bizfile.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.bizfile.BizDoc;

/**
 * 办理业务申请书mapper
 * 
 * @Package: com.gy.hsxt.bs.bizfile.mapper
 * @ClassName: BizDocMapper
 * @Description:
 * 
 * @author: kongsl
 * @date: 2016-2-19 下午3:24:00
 * @version V3.0.0
 */
@Repository
public interface BizDocMapper {
    /**
     * 插入办理业务申请书
     * 
     * @param bizDoc
     *            办理业务申请书对象
     * @return 成功记录数
     */
    public int insertBizDoc(BizDoc bizDoc);

    /**
     * 修改办理业务申请书
     * 
     * @param bizDoc
     *            办理业务申请书对象
     * @return 成功记录数
     */
    public int updateBizDoc(BizDoc bizDoc);

    /**
     * 修改文档状态
     * 
     * @param status
     *            状态
     * @param docId
     *            文档ID
     * @return 成功记录数
     */
    public int updateBizDocStatus(@Param("status") Integer status, @Param("docId") String docId);

    /**
     * 删除文档
     * 
     * @param docId
     *            文档ID
     * @return 成功记录数
     */
    public int deleteBizDoc(@Param("docId") String docId);

    /**
     * 查询办理业务申请书列表
     * 
     * @param docName
     *            文档名称
     * @param status
     *            文档状态
     * @return 办理业务申请书列表
     */
    public List<BizDoc> findBizDocListPage(@Param("docName") String docName, @Param("status") Integer status);

    /**
     * 查询文档详情
     * 
     * @param docId
     *            文档ID
     * @return 文档详情
     */
    public BizDoc findBizDocInfo(@Param("docId") String docId);

    /**
     * 查询已存在的办理业务
     * 
     * @param docName
     *            文档名称
     * @param docCode
     *            唯一标识
     * @return 记录数
     */
    public int findExistsBizDoc(@Param("docName") String docName, @Param("docCode") String docCode);

    /**
     * 查询不存在的办理业务
     * 
     * @param docId
     *            文档ID
     * @param docName
     *            文档名称
     * @param docCode
     *            唯一标识
     * @return 记录数
     */
    public int findNotExistsBizDoc(@Param("docId") String docId, @Param("docName") String docName,
            @Param("docCode") String docCode);
}
