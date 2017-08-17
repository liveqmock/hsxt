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
import com.gy.hsxt.gpf.fss.mapper.LocalNotifyMapper;
import com.gy.hsxt.gpf.fss.service.LocalNotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * @Package :com.gy.hsxt.gpf.fss.service.impl
 * @ClassName : LocalNotifyServiceImpl
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/22 19:43
 * @Version V3.0.0.0
 */
@Service("localNotifyService")
public class LocalNotifyServiceImpl implements LocalNotifyService {

    private Logger logger = LoggerFactory.getLogger(LocalNotifyServiceImpl.class);

    @Resource
    private LocalNotifyMapper localNotifyMapper;

    @Resource
    private FileDetailMapper fileDetailMapper;

    /**
     * 保存单个实体
     *
     * @param localNotify
     * @return
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int save(LocalNotify localNotify) throws HsException {
        int count;
        try {
            count = localNotifyMapper.insert(localNotify);
        } catch (Exception e) {
            logger.error("======保存本地通知异常======", e);
            throw new HsException(FSSRespCode.SAVE_DATA_ERROR.getCode(), "保存本地通知异常");
        }
        return count;
    }

    /**
     * 移除单个实体
     *
     * @param localNotify
     * @throws HsException
     */
    @Override
    public void remove(LocalNotify localNotify) throws HsException {

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
     * @param localNotify
     * @return count
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int modify(LocalNotify localNotify) throws HsException {
        int count;
        try {
            count = localNotifyMapper.update(localNotify);
        } catch (Exception e) {
            logger.error("======更新本地通知异常======", e);
            throw new HsException(FSSRespCode.MOD_DATA_ERROR.getCode(), "更新本地通知异常");
        }
        return count;
    }

    /**
     * 查询单个实体
     *
     * @param localNotify
     * @return
     * @throws HsException
     */
    @Override
    public LocalNotify query(LocalNotify localNotify) throws HsException {
        return localNotifyMapper.selectOne(localNotify);
    }

    /**
     * 根据主键查询单个实体
     *
     * @param id
     * @return
     * @throws HsException
     */
    @Override
    public LocalNotify queryById(Serializable id) throws HsException {
        return localNotifyMapper.selectOneById(id);
    }

    /**
     * 根据条件查询
     *
     * @param localNotify
     * @return
     * @throws HsException
     */
    @Override
    public List<LocalNotify> queryByCondition(LocalNotify localNotify) throws HsException {
        return localNotifyMapper.selectByCondition(localNotify);
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
                logger.info("=====文件[{}]没有校验通过=====",detail.getFileName());
                return false;
            }
        }
        LocalNotify notify = new LocalNotify();
        notify.setNotifyNo(notifyNo);
        notify.setAllPass(1);
        int count = localNotifyMapper.update(notify);
        return count == 1;
    }

    /**
     * 根据查询实体进行列表查询
     *
     * @param queryNotify
     * @return
     * @throws HsException
     */
    @Override
    public List<LocalNotify> queryByOther(QueryNotify queryNotify) throws HsException {
        return localNotifyMapper.selectByOther(queryNotify);
    }

    /**
     * 分页查询
     *
     * @param queryNotify 查询实体
     * @return pageData
     * @throws HsException
     */
    @Override
    public PageData<LocalNotify> queryLocalNotifyForPage(QueryNotify queryNotify) throws HsException {
        //符合条件的记录总数
        int total = localNotifyMapper.selectCountByOther(queryNotify);
        //分页查询
//        queryNotify.setStart(PageContext.getOffset());
//        queryNotify.setLength(PageContext.getPageSize());
        List<LocalNotify> data = localNotifyMapper.selectByOther(queryNotify);

        return PageData.build(total,data);
    }
}
