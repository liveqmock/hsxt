/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bizfile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.api.bizfile.IBSBizFileService;
import com.gy.hsxt.bs.bean.bizfile.BizDoc;
import com.gy.hsxt.bs.bean.bizfile.ImageDoc;
import com.gy.hsxt.bs.bean.bizfile.NormalDoc;
import com.gy.hsxt.bs.bizfile.mapper.BizDocMapper;
import com.gy.hsxt.bs.bizfile.mapper.ImageDocMapper;
import com.gy.hsxt.bs.bizfile.mapper.NormalDocMapper;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.bizfile.DocStatus;
import com.gy.hsxt.bs.common.enumtype.bizfile.FileType;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.HsAssert;

/**
 * 业务文件管理接口实现类
 * 
 * @Package: com.gy.hsxt.bs.bizfile.service
 * @ClassName: BizFileService
 * @Description:
 * 
 * @author: kongsl
 * @date: 2016-2-19 下午2:30:24
 * @version V3.0.0
 */
@Service("bizFileService")
public class BizFileService implements IBSBizFileService {
    /**
     * 业务服务私有配置参数
     */
    @Autowired
    private BsConfig bsConfig;

    /**
     * 示例图片mapper
     */
    @Autowired
    ImageDocMapper imageDocMapper;

    /**
     * 办理业务mapper
     */
    @Autowired
    BizDocMapper bizDocMapper;

    /**
     * 常用业务mapper
     */
    @Autowired
    NormalDocMapper normalDocMapper;

    /**
     * 新增文档
     * 
     * @param obj
     *            文档对象
     * @throws HsException
     * @see com.gy.hsxt.bs.api.bizfile.IBSBizFileService#saveDoc(java.lang.Object)
     */
    @Override
    public void saveDoc(Object obj) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "保存业务文件,params[" + obj + "]");
        HsAssert.notNull(obj, BSRespCode.BS_PARAMS_NULL, "保存业务文件：实体参数对象为空");
        // 对象为示例图片类型
        if (obj instanceof ImageDoc)
        {
            ImageDoc imageDoc = (ImageDoc) obj;
            try
            {
                // 查询文档标识是否存在
                int resCount = imageDocMapper.findExistsDocCode(imageDoc.getDocCode());
                HsAssert.isTrue(resCount <= 0, BSRespCode.BS_EXISTS_DOC_CODE, "保存示例图片文档：文档标识已存在，docCode="
                        + imageDoc.getDocCode());
                // 设置文档ID
                imageDoc.setDocId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
                // 设置初始文档版本为1
                imageDoc.setDocVersion(1);
                // 插入示例图片
                imageDocMapper.insertImageDoc(imageDoc);
            }
            catch (HsException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                        BSRespCode.BS_SAVE_BIZ_FILE_ERROR.getCode() + "保存示例图片文档异常,params[" + imageDoc + "]", e);
                throw new HsException(BSRespCode.BS_SAVE_BIZ_FILE_ERROR.getCode(), "保存示例图片文档异常,params[" + imageDoc
                        + "]" + e);
            }
        }
        else
        // 办理业务类型
        if (obj instanceof BizDoc)
        {
            BizDoc bizDoc = (BizDoc) obj;
            try
            {
                int resNum = bizDocMapper.findExistsBizDoc(bizDoc.getDocName(), bizDoc.getDocCode());
                HsAssert.isTrue(resNum <= 0, BSRespCode.BS_EXISTS_DOC_CODE, "保存办理业务：已存在相同的业务申请书名称或唯一标识");
                // 设置文档ID
                bizDoc.setDocId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
                // 插入办理业务文档
                bizDocMapper.insertBizDoc(bizDoc);
            }
            catch (HsException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                        BSRespCode.BS_SAVE_BIZ_FILE_ERROR.getCode() + "保存办理业务文档异常,params[" + bizDoc + "]", e);
                throw new HsException(BSRespCode.BS_SAVE_BIZ_FILE_ERROR.getCode(), "保存办理业务文档异常,params[" + bizDoc + "]"
                        + e);
            }
        }
        else
        // 常用业务文档
        if (obj instanceof NormalDoc)
        {
            NormalDoc normalDoc = (NormalDoc) obj;
            try
            {
                int resNum = normalDocMapper.findExistsBizDoc(normalDoc.getDocName(), normalDoc.getDocCode());
                HsAssert.isTrue(resNum <= 0, BSRespCode.BS_EXISTS_DOC_CODE, "保存常用业务：已存在相同的常用文档名称或唯一标识");
                // 设置文档ID
                normalDoc.setDocId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
                // 插入常用 业务文档
                normalDocMapper.insertNormalDoc(normalDoc);
            }
            catch (HsException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                        BSRespCode.BS_SAVE_BIZ_FILE_ERROR.getCode() + "保存常用业务文档异常,params[" + normalDoc + "]", e);
                throw new HsException(BSRespCode.BS_SAVE_BIZ_FILE_ERROR.getCode(), "保存常用业务文档异常,params[" + normalDoc
                        + "]" + e);
            }
        }
    }

    /**
     * 发布文档
     * 
     * @param docId
     *            文档ID
     * @param fileType
     *            文件类型
     * @throws HsException
     * @see com.gy.hsxt.bs.api.bizfile.IBSBizFileService#releaseDoc(java.lang.String,
     *      java.lang.Integer)
     */
    @Override
    public void releaseDoc(String docId, Integer fileType) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "发布文档,params[docId：" + docId + ",fileType:" + fileType + "]");
        HsAssert.hasText(docId, BSRespCode.BS_PARAMS_NULL, "发布文档：文档ID参数为空");
        HsAssert.notNull(fileType, BSRespCode.BS_PARAMS_NULL, "发布文档：文档类型参数为空");
        // 文档类型为示例图片
        if (FileType.IMAGE.getCode() == fileType)
        {
            try
            {
                // 查询示例图片
                ImageDoc imageDoc = imageDocMapper.findImageDocInfo(docId);
                HsAssert.notNull(imageDoc, BSRespCode.BS_NOT_QUERY_DATA, "发布文档：未查询到当前文档ID记录，docId=" + docId);
                // 查询使用中的文档
                int resCount = imageDocMapper.findNormalStatus(imageDoc.getDocCode());
                HsAssert.isTrue(resCount <= 0, BSRespCode.BS_EXISTS_USED_DOC, "发布文档：已存在使用中的文档状态，docCode="
                        + imageDoc.getDocCode());
                // 更新文档状态为正常
                imageDocMapper.updateImageDocStatus(DocStatus.NORMAL.getCode(), docId, true);

                // 更新停用时间
                imageDocMapper.updateStopTimeByCode(imageDoc.getDocCode(), "");
            }
            catch (HsException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                        BSRespCode.BS_UPDATE_BIZ_FILE_STATUS_ERROR.getCode() + "更新示例图片文档状态异常,params[docId:" + docId
                                + ",fileType:" + fileType + "]", e);
                throw new HsException(BSRespCode.BS_UPDATE_BIZ_FILE_STATUS_ERROR.getCode(),
                        "更新示例图片文档状态异常,params[docId:" + docId + ",fileType:" + fileType + "]" + e);
            }
        }
        else
        // 办理业务
        if (FileType.BIZ.getCode() == fileType)
        {
            try
            {
                // 查询办理业务
                BizDoc bizDoc = bizDocMapper.findBizDocInfo(docId);
                HsAssert.notNull(bizDoc, BSRespCode.BS_NOT_QUERY_DATA, "发布文档：未查询到当前文档ID记录，docId=" + docId);
                // 更新文档状态为正常
                bizDocMapper.updateBizDocStatus(DocStatus.NORMAL.getCode(), docId);
            }
            catch (HsException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                        BSRespCode.BS_UPDATE_BIZ_FILE_STATUS_ERROR.getCode() + "更新办理业务文档状态异常,params[docId:" + docId
                                + ",fileType:" + fileType + "]", e);
                throw new HsException(BSRespCode.BS_UPDATE_BIZ_FILE_STATUS_ERROR.getCode(),
                        "更新办理业务文档状态异常,params[docId:" + docId + ",fileType:" + fileType + "]" + e);
            }
        }
        // 常用业务
        if (FileType.NORMAL.getCode() == fileType)
        {
            try
            {
                // 查询常用业务
                NormalDoc normalDoc = normalDocMapper.findNormalDocInfo(docId);
                HsAssert.notNull(normalDoc, BSRespCode.BS_NOT_QUERY_DATA, "发布文档：未查询到当前文档ID记录，docId=" + docId);
                // 更新文档状态为正常
                normalDocMapper.updateNormalDocStatus(DocStatus.NORMAL.getCode(), docId);
            }
            catch (HsException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                        BSRespCode.BS_UPDATE_BIZ_FILE_STATUS_ERROR.getCode() + "更新常用业务文档状态异常,params[docId:" + docId
                                + ",fileType:" + fileType + "]", e);
                throw new HsException(BSRespCode.BS_UPDATE_BIZ_FILE_STATUS_ERROR.getCode(),
                        "更新常用业务文档状态异常,params[docId:" + docId + ",fileType:" + fileType + "]" + e);
            }
        }
    }

    /**
     * 修改文档
     * 
     * @param obj
     *            文档对象
     * @throws HsException
     * @see com.gy.hsxt.bs.api.bizfile.IBSBizFileService#modifyDoc(java.lang.Object)
     */
    @Override
    @Transactional
    public void modifyDoc(Object obj) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "修改文档,params[" + obj + "]");
        HsAssert.notNull(obj, BSRespCode.BS_PARAMS_NULL, "修改文档：实体参数对象为空");
        int resNum = 0;
        // 对象为示例图片类型
        if (obj instanceof ImageDoc)
        {
            ImageDoc imageDoc = (ImageDoc) obj;
            try
            {
                // 文档ID为空
                HsAssert.hasText(imageDoc.getDocId(), BSRespCode.BS_PARAMS_NULL, "修改示例图片文档：文档ID为空");
                // 查询详情
                ImageDoc docInfo = imageDocMapper.findImageDocInfo(imageDoc.getDocId());
                HsAssert.notNull(docInfo, BSRespCode.BS_NOT_QUERY_DATA, "修改示例图片文档：未查询到该文档ID，docId="
                        + docInfo.getDocId());
                // 查询正常状态的文档
                resNum = imageDocMapper.findNormalStatus(docInfo.getDocCode());
                HsAssert.isTrue(resNum <= 0, BSRespCode.BS_EXISTS_USED_DOC, "修改示例图片文档：修改的唯一标识正在使用中，请先停用");

                // 更新停用时间和修改人
                imageDocMapper.updateTimeAndBy(docInfo.getUpdatedBy(), docInfo.getDocId());
                // 更新活动状态为非活动
                imageDocMapper.updateImageDocActive(docInfo.getDocCode(), false);
                /**
                 * *******************
                 */
                // 设置文档ID
                imageDoc.setDocId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
                // 设置文档版本
                if (imageDoc.getDocCode().equals(docInfo.getDocCode()))
                {
                    // 如果唯一标识相同财累加
                    imageDoc.setDocVersion(docInfo.getDocVersion() + 1);
                }
                else
                {
                    // 不相同从1开始
                    imageDoc.setDocVersion(1);
                }

                // 插入示例图片
                imageDocMapper.insertImageDoc(imageDoc);
            }
            catch (HsException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                        BSRespCode.BS_MODIFY_BIZ_FILE_ERROR.getCode() + "修改示例图片文档异常,params[" + imageDoc + "]", e);
                throw new HsException(BSRespCode.BS_MODIFY_BIZ_FILE_ERROR.getCode(), "修改示例图片文档异常,params[" + imageDoc
                        + "]" + e);
            }
        }
        else
        // 办理业务类型
        if (obj instanceof BizDoc)
        {
            BizDoc bizDoc = (BizDoc) obj;
            try
            {
                // 文档ID为空
                HsAssert.hasText(bizDoc.getDocId(), BSRespCode.BS_PARAMS_NULL, "修改业务文档：文档ID为空");
                resNum = bizDocMapper.findNotExistsBizDoc(bizDoc.getDocId(), bizDoc.getDocName(), bizDoc.getDocCode());
                HsAssert.isTrue(resNum <= 0, BSRespCode.BS_EXISTS_DOC_CODE, "修改业务文档：已存在相同的业务文档名称或标识");
                bizDocMapper.updateBizDoc(bizDoc);
            }
            catch (HsException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                        BSRespCode.BS_MODIFY_BIZ_FILE_ERROR.getCode() + "修改业务文档异常,params[" + bizDoc + "]", e);
                throw new HsException(BSRespCode.BS_MODIFY_BIZ_FILE_ERROR.getCode(), "修改业务文档异常,params[" + bizDoc + "]"
                        + e);
            }
        }
        else
        // 常用业务文档
        if (obj instanceof NormalDoc)
        {
            NormalDoc normalDoc = (NormalDoc) obj;
            try
            {
                // 文档ID为空
                HsAssert.hasText(normalDoc.getDocId(), BSRespCode.BS_PARAMS_NULL, "修改常用文档：文档ID为空");
                resNum = normalDocMapper.findNotExistsNormalDoc(normalDoc.getDocId(), normalDoc.getDocName(), normalDoc
                        .getDocCode());
                HsAssert.isTrue(resNum <= 0, BSRespCode.BS_EXISTS_DOC_CODE, "修改常用文档：已存在相同的业务文档名称或标识");
                normalDocMapper.updateNormalDoc(normalDoc);
            }
            catch (HsException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                        BSRespCode.BS_MODIFY_BIZ_FILE_ERROR.getCode() + "修改常用文档异常,params[" + normalDoc + "]", e);
                throw new HsException(BSRespCode.BS_MODIFY_BIZ_FILE_ERROR.getCode(), "修改常用文档异常,params[" + normalDoc
                        + "]" + e);
            }
        }
    }

    /**
     * 删除文档
     * 
     * @param docId
     *            文档ID
     * @param fileType
     *            文件类型
     * @throws HsException
     * @see com.gy.hsxt.bs.api.bizfile.IBSBizFileService#deleteDoc(java.lang.String,
     *      java.lang.Integer)
     */
    @Override
    public void deleteDoc(String docId, Integer fileType) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "删除文档,params[docId:" + docId + ",fileType:" + fileType + "]");
        HsAssert.hasText(docId, BSRespCode.BS_PARAMS_NULL, "删除文档：文档ID参数为空");
        HsAssert.notNull(fileType, BSRespCode.BS_PARAMS_NULL, "删除文档：文件类型参数为空");
        // 文档类型为示例图片
        if (FileType.IMAGE.getCode() == fileType)
        {
            // 删除示例图片
            imageDocMapper.deleteImageDoc(docId);
        }
        else
        // 办理业务
        if (FileType.BIZ.getCode() == fileType)
        {
            // 删除办理业务
            bizDocMapper.deleteBizDoc(docId);
        }
        // 常用业务
        if (FileType.NORMAL.getCode() == fileType)
        {
            // 删除常用业务
            normalDocMapper.deleteNormalDoc(docId);
        }
    }

    /**
     * 获取历史版本
     * 
     * @param unique
     *            唯一标识
     * @return 示例图片历史版本列表
     * @throws HsException
     * @see com.gy.hsxt.bs.api.bizfile.IBSBizFileService#getImageDocHis(java.lang.String)
     */
    @Override
    public List<ImageDoc> getImageDocHis(String unique) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "获取历史版本,params[unique:" + unique + "]");
        HsAssert.hasText(unique, BSRespCode.BS_PARAMS_NULL, "获取历史版本：唯一标识参数为空");
        // 查询示例图片历史版本
        return imageDocMapper.findImageDocHis(unique);
    }

    /**
     * 停用文档
     * 
     * @param docCode
     *            文档ID
     * @param fileType
     *            文件类型
     * @throws HsException
     * @see com.gy.hsxt.bs.api.bizfile.IBSBizFileService#stopUsedDoc(java.lang.String,
     *      java.lang.Integer)
     */
    @Override
    public void stopUsedDoc(String docCode, Integer fileType) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "停用文档,params[docCode:" + docCode + ",fileType:" + fileType + "]");
        HsAssert.hasText(docCode, BSRespCode.BS_PARAMS_NULL, "停用文档：文档唯一标识参数为空");
        HsAssert.notNull(fileType, BSRespCode.BS_PARAMS_NULL, "停用文档：文件类型参数为空");
        // 文档类型为示例图片
        if (FileType.IMAGE.getCode() == fileType)
        {
            // 停用示例图片
            imageDocMapper.updateGroupDocStatus(docCode);
            // 更新停用时间
            imageDocMapper.updateStopTimeByCode(docCode, "");
        }
        else
        // 办理业务
        if (FileType.BIZ.getCode() == fileType)
        {
            // 停用办理业务
            bizDocMapper.updateBizDocStatus(DocStatus.DISABLE.getCode(), docCode);
        }
        // 常用业务
        if (FileType.NORMAL.getCode() == fileType)
        {
            // 停用常用业务
            normalDocMapper.updateNormalDocStatus(DocStatus.DISABLE.getCode(), docCode);
        }
    }

    /**
     * 获取示例图片管理列表
     * 
     * @param docStatus
     *            文档状态
     * @return 示例图片管理列表
     * @throws HsException
     * @see com.gy.hsxt.bs.api.bizfile.IBSBizFileService#getImageDocList(java.lang.Integer)
     */
    @Override
    public List<ImageDoc> getImageDocList(Integer docStatus) throws HsException {
        return imageDocMapper.findImageDocList(docStatus);
    }

    /**
     * 恢复示例图片版本
     * 
     * @param docId
     *            文档ID
     * @throws HsException
     * @see com.gy.hsxt.bs.api.bizfile.IBSBizFileService#recoveryImageDoc(java.lang.String)
     */
    @Override
    public void recoveryImageDoc(String docId) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "恢复示例图片版本,params[docId:" + docId + "]");
        HsAssert.hasText(docId, BSRespCode.BS_PARAMS_NULL, "恢复示例图片版本：文档ID参数为空");
        // 查询示例图片
        ImageDoc imageDoc = imageDocMapper.findImageDocInfo(docId);
        HsAssert.notNull(imageDoc, BSRespCode.BS_NOT_QUERY_DATA, "恢复示例图片版本：未查询到当前文档ID记录，docId=" + docId);
        // 更新正在使用中的状态为停用
        imageDocMapper.updateImageDocActive(imageDoc.getDocCode(), false);
        // 更新要恢复的版本状态为正常
        imageDocMapper.updateImageDocStatus(DocStatus.UNPUBLISHED.getCode(), docId, true);
    }

    /**
     * 查询文档详情
     * 
     * @param docId
     *            文档ID
     * @param fileType
     *            文件类型
     * @throws HsException
     * @see com.gy.hsxt.bs.api.bizfile.IBSBizFileService#getDocInfo(java.lang.String,
     *      java.lang.Integer)
     */
    @Override
    public Object getDocInfo(String docId, Integer fileType) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "查询文档详情,params[docId:" + docId + ",fileType:" + fileType + "]");
        HsAssert.hasText(docId, BSRespCode.BS_PARAMS_NULL, "查询文档详情：文档ID参数为空");
        HsAssert.notNull(fileType, BSRespCode.BS_PARAMS_NULL, "查询文档详情：文件类型参数为空");
        // 文档类型为示例图片
        if (FileType.IMAGE.getCode() == fileType)
        {
            // 查询例图片
            return imageDocMapper.findImageDocInfo(docId);
        }
        else
        // 办理业务
        if (FileType.BIZ.getCode() == fileType)
        {
            // 查询办理业务
            return bizDocMapper.findBizDocInfo(docId);
        }
        // 常用业务
        if (FileType.NORMAL.getCode() == fileType)
        {
            // 查询常用业务
            return normalDocMapper.findNormalDocInfo(docId);
        }
        return null;
    }

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
     * @see com.gy.hsxt.bs.api.bizfile.IBSBizFileService#getBizDocList(java.lang.String,
     *      java.lang.String, java.lang.Integer, com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<BizDoc> getBizDocList(String docName, Integer docStatus, Page page) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "分页查询办理业务列表,params[docName:" + docName + ",docStatus" + docStatus + "]");
        // 分页数据
        PageData<BizDoc> bizDocListPage = null;
        // 分页参数为空
        HsAssert.notNull(page, BSRespCode.BS_PAGE_PARAM_NULL, "查询办理业务列表：分页条件参数为空");
        // 设置分页信息
        PageContext.setPage(page);
        try
        {
            // 执行查询
            List<BizDoc> docList = bizDocMapper.findBizDocListPage(docName, docStatus);
            if (docList != null && docList.size() > 0)
                // 为公用分页查询设置查询结果集
                bizDocListPage = new PageData<BizDoc>(page.getCount(), docList);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    BSRespCode.BS_QUERY_BIZ_DOC_LIST_ERROR.getCode() + ":查询办理业务列表异常,params[docName:" + docName
                            + ",docStatus" + docStatus + "]", e);
            throw new HsException(BSRespCode.BS_QUERY_BIZ_DOC_LIST_ERROR.getCode(), "查询办理业务列表异常,params[docName:"
                    + docName + ",docStatus" + docStatus + "]" + e);
        }
        return bizDocListPage;
    }

    /**
     * 分页查询常用业务文档列表
     * 
     * @param docName
     *            文档名称
     * @param docStatus
     *            文档状态
     * @param fileType
     *            文件类型
     * @param page
     *            分页信息
     * @return 文档列表
     * @throws HsException
     * @see com.gy.hsxt.bs.api.bizfile.IBSBizFileService#getNormalDocList(java.lang.String,
     *      java.lang.String, java.lang.Integer, com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<NormalDoc> getNormalDocList(String docName, Integer docStatus, Page page) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "分页查询常用业务列表,params[docName:" + docName + ",docStatus" + docStatus + "]");
        // 分页数据
        PageData<NormalDoc> normalDocListPage = null;
        // 分页参数为空
        HsAssert.notNull(page, BSRespCode.BS_PAGE_PARAM_NULL, "查询常用业务列表：分页条件参数为空");
        // 设置分页信息
        PageContext.setPage(page);
        try
        {
            // 执行查询
            List<NormalDoc> docList = normalDocMapper.findNormalDocListPage(docName, docStatus);
            if (docList != null && docList.size() > 0)
                // 为公用分页查询设置查询结果集
                normalDocListPage = new PageData<NormalDoc>(page.getCount(), docList);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    BSRespCode.BS_QUERY_NORMAL_DOC_LIST_ERROR.getCode() + ":查询常用业务列表异常,params[docName:" + docName
                            + ",docStatus" + docStatus + "]", e);
            throw new HsException(BSRespCode.BS_QUERY_NORMAL_DOC_LIST_ERROR.getCode(), "查询常用业务列表异常,params[docName:"
                    + docName + ",docStatus" + docStatus + "]" + e);
        }
        return normalDocListPage;
    }
}
