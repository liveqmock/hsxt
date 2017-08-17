/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.api.bizfile;

import java.util.List;

import com.gy.hsxt.bs.bean.bizfile.BizDoc;
import com.gy.hsxt.bs.bean.bizfile.ImageDoc;
import com.gy.hsxt.bs.bean.bizfile.NormalDoc;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 业务文件接口
 * 
 * @Package: com.gy.hsxt.bs.api.bizfile
 * @ClassName: IBSBizFileService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2016-2-18 下午3:31:47
 * @version V3.0.0
 */
public interface IBSBizFileService {

    /**
     * 新增文档
     * 
     * @param obj
     *            文档对象
     * @throws HsException
     */
    public void saveDoc(Object obj) throws HsException;

    /**
     * 发布文档
     * 
     * @param docId
     *            文档ID
     * @param fileType
     *            文件类型
     * @throws HsException
     */
    public void releaseDoc(String docId, Integer fileType) throws HsException;

    /**
     * 修改文档
     * 
     * @param obj
     *            文档对象
     * @throws HsException
     */
    public void modifyDoc(Object obj) throws HsException;

    /**
     * 删除文档
     * 
     * @param docId
     *            文档ID
     * @param fileType
     *            文件类型
     * @throws HsException
     */
    public void deleteDoc(String docId, Integer fileType) throws HsException;

    /**
     * 获取历史版本
     * 
     * @param docCode
     *            唯一标识
     * @return 示例图片历史版本列表
     * @throws HsException
     */
    public List<ImageDoc> getImageDocHis(String docCode) throws HsException;

    /**
     * 停用文档
     * 
     * @param docCode
     *            唯一标识
     * @param fileType
     *            文件类型
     * @throws HsException
     */
    public void stopUsedDoc(String docCode, Integer fileType) throws HsException;

    /**
     * 获取示例图片管理列表
     * 
     * @param docStatus
     *            文档状态
     * @return 示例图片管理列表
     * @throws HsException
     */
    public List<ImageDoc> getImageDocList(Integer docStatus) throws HsException;

    /**
     * 恢复示例图片版本
     * 
     * @param docId
     *            文档ID
     * @throws HsException
     */
    public void recoveryImageDoc(String docId) throws HsException;

    /**
     * 查询文档详情
     * 
     * @param docId
     *            文档ID
     * @param fileType
     *            文件类型
     * @return 文档信息对象
     * @throws HsException
     */
    public Object getDocInfo(String docId, Integer fileType) throws HsException;

    /**
     * 分页查询办理业务文档列表
     * 
     * @param docName
     *            文档名称
     * @param docStatus
     *            文档状态
     * @param page
     *            分页信息
     * @return 文档列表
     * @throws HsException
     */
    public PageData<BizDoc> getBizDocList(String docName, Integer docStatus, Page page) throws HsException;

    /**
     * 分页查询常用业务文档列表
     * 
     * @param docName
     *            文档名称
     * @param docStatus
     *            文档状态
     * @param page
     *            分页信息
     * @return 文档列表
     * @throws HsException
     */
    public PageData<NormalDoc> getNormalDocList(String docName, Integer docStatus, Page page) throws HsException;
}
