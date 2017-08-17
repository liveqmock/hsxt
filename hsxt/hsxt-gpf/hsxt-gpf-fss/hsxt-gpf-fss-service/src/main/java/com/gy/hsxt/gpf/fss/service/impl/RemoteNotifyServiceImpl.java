/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.service.impl;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.fss.bean.*;
import com.gy.hsxt.gpf.fss.constant.FSSRespCode;
import com.gy.hsxt.gpf.fss.mapper.FileDetailMapper;
import com.gy.hsxt.gpf.fss.mapper.RemoteNotifyMapper;
import com.gy.hsxt.gpf.fss.service.RemoteNotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 通知服务层实现类
 *
 * @Package :com.gy.hsxt.gpf.fss.service
 * @ClassName : NotifyServiceImpl
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/21 17:08
 * @Version V3.0.0.0
 */
@Service("remoteNotifyService")
public class RemoteNotifyServiceImpl implements RemoteNotifyService {

    private Logger logger = LoggerFactory.getLogger(RemoteNotifyServiceImpl.class);

    @Resource
    private RemoteNotifyMapper remoteNotifyMapper;

    @Resource
    private FileDetailMapper fileDetailMapper;

    /**
     * 保存单个实体
     *
     * @param remoteNotify
     * @return
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int save(RemoteNotify remoteNotify) throws HsException {
        int count;
        try {
            count = remoteNotifyMapper.insert(remoteNotify);
        } catch (Exception e) {
            logger.error("======保存远程通知异常======", e);
            throw new HsException(FSSRespCode.SAVE_DATA_ERROR.getCode(), "保存远程通知异常");
        }
        return count;
    }

    /**
     * 移除单个实体
     *
     * @param remoteNotify
     * @throws HsException
     */
    @Override
    public void remove(RemoteNotify remoteNotify) throws HsException {

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
     * @param remoteNotify
     * @return count
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int modify(RemoteNotify remoteNotify) throws HsException {
        int count;
        try {
            count = remoteNotifyMapper.update(remoteNotify);
        } catch (Exception e) {
            logger.error("======更新远程通知异常======", e);
            throw new HsException(FSSRespCode.MOD_DATA_ERROR.getCode(), "更新远程通知异常");
        }
        return count;
    }

    /**
     * 查询单个实体
     *
     * @param remoteNotify
     * @return
     * @throws HsException
     */
    @Override
    public RemoteNotify query(RemoteNotify remoteNotify) throws HsException {
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
    public RemoteNotify queryById(Serializable id) throws HsException {
        return remoteNotifyMapper.selectOneById(id);
    }

    /**
     * 根据条件查询
     *
     * @param remoteNotify
     * @return
     * @throws HsException
     */
    @Override
    public List<RemoteNotify> queryByCondition(RemoteNotify remoteNotify) throws HsException {
        return remoteNotifyMapper.selectByCondition(remoteNotify);
    }

    /**
     * 检查文件详情是否全部校验通过
     *
     * @param notifyNo
     * @return
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public boolean checkAllPass(String notifyNo) throws HsException {
        List<FileDetail> details = fileDetailMapper.selectByNotifyNo(notifyNo);
        for (FileDetail detail : details) {
            if (detail.getIsPass() == 0) {
                logger.info("=====文件[{}]没有校验通过=====", detail.getFileName());
                return false;
            }
        }
        RemoteNotify notify = new RemoteNotify();
        notify.setNotifyNo(notifyNo);
        notify.setAllPass(1);
        int count = remoteNotifyMapper.update(notify);
        return count == 1;
    }

    /**
     * 根据查询实体查询列表
     *
     * @param queryNotify
     * @return
     * @throws HsException
     */
    @Override
    public List<RemoteNotify> queryByOther(QueryNotify queryNotify) throws HsException {
        return remoteNotifyMapper.selectByOther(queryNotify);
    }

    /**
     * 分页查询
     *
     * @param queryNotify 查询实体
     * @return pageData
     * @throws HsException
     */
    @Override
    public PageData<RemoteNotify> queryRemoteNotifyForPage(QueryNotify queryNotify) throws HsException {
        //符合条件的记录总数
        int total = remoteNotifyMapper.selectCountByOther(queryNotify);
        //分页查询
//        queryNotify.setStart(PageContext.getOffset());
//        queryNotify.setLength(PageContext.getPageSize());
        List<RemoteNotify> data = remoteNotifyMapper.selectByOther(queryNotify);

        return PageData.build(total,data);
    }
}
