/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.service.impl;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.fss.bean.FileDetail;
import com.gy.hsxt.gpf.fss.constant.FSSRespCode;
import com.gy.hsxt.gpf.fss.mapper.FileDetailMapper;
import com.gy.hsxt.gpf.fss.service.FileDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * @Package :com.gy.hsxt.gpf.fss.service.impl
 * @ClassName : FileDetailServiceImpl
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/23 12:44
 * @Version V3.0.0.0
 */
@Service("fileDetailService")
public class FileDetailServiceImpl implements FileDetailService {

    private Logger logger = LoggerFactory.getLogger(FileDetailServiceImpl.class);

    @Resource
    private FileDetailMapper fileDetailMapper;

    /**
     * 保存单个实体
     *
     * @param fileDetail
     * @return
     * @throws HsException
     */
    @Override
    public int save(FileDetail fileDetail) throws HsException {
        int count;
        try {
            count = fileDetailMapper.insert(fileDetail);
        } catch (Exception e) {
            logger.error("======保存文件详情异常======", e);
            throw new HsException(FSSRespCode.SAVE_DATA_ERROR.getCode(), "保存文件详情异常");
        }
        return count;
    }

    /**
     * 批量保存文件详情
     *
     * @param details
     * @return
     * @throws HsException
     */
    @Override
    public int batchSave(List<FileDetail> details) throws HsException {
        int count;
        try {
            count = fileDetailMapper.batchInsert(details);
        } catch (Exception e) {
            logger.error("======批量保存文件详情异常======", e);
            throw new HsException(FSSRespCode.SAVE_DATA_ERROR.getCode(), "批量保存文件详情异常");
        }
        return count;
    }

    /**
     * 移除单个实体
     *
     * @param fileDetail
     * @throws HsException
     */
    @Override
    public void remove(FileDetail fileDetail) throws HsException {

    }

    /**
     * 通过主键移除单个实体
     *
     * @param id
     * @throws HsException
     */
    @Override
    public void removeById(Serializable id) throws HsException {

    }

    /**
     * 更新单个实体
     *
     * @param fileDetail
     * @return count
     * @throws HsException
     */
    @Override
    public int modify(FileDetail fileDetail) throws HsException {
        int count;
        try {
            count = fileDetailMapper.update(fileDetail);
        } catch (Exception e) {
            logger.error("======更新文件详情异常======", e);
            throw new HsException(FSSRespCode.MOD_DATA_ERROR.getCode(), "更新文件详情异常");
        }
        return count;
    }

    /**
     * 查询单个实体
     *
     * @param fileDetail
     * @return
     * @throws HsException
     */
    @Override
    public FileDetail query(FileDetail fileDetail) throws HsException {
        return null;
    }

    /**
     * 根据主键查询单个实体
     *
     * @param id
     * @return
     * @throws HsException
     */
    @Override
    public FileDetail queryById(Serializable id) throws HsException {
        return fileDetailMapper.selectOneById(id);
    }

    /**
     * 根据条件查询
     *
     * @param fileDetail
     * @return
     * @throws HsException
     */
    @Override
    public List<FileDetail> queryByCondition(FileDetail fileDetail) throws HsException {
        return fileDetailMapper.selectByCondition(fileDetail);
    }

    /**
     * 根据通知编号查询对应的所有文件详情
     *
     * @param notifyNo
     * @return
     * @throws HsException
     */
    @Override
    public List<FileDetail> queryByNotifyNo(String notifyNo) throws HsException {
        return fileDetailMapper.selectByNotifyNo(notifyNo);
    }
}
