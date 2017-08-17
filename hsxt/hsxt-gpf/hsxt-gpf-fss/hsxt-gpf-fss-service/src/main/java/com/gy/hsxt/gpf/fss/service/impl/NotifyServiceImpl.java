/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.service.impl;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gpf.fss.bean.FileDetail;
import com.gy.hsxt.gpf.fss.bean.LocalNotify;
import com.gy.hsxt.gpf.fss.bean.RemoteNotify;
import com.gy.hsxt.gpf.fss.bean.ResultNotify;
import com.gy.hsxt.gpf.fss.constant.FSSRespCode;
import com.gy.hsxt.gpf.fss.notify.NotifyManagerCenter;
import com.gy.hsxt.gpf.fss.service.FileDetailService;
import com.gy.hsxt.gpf.fss.service.LocalNotifyService;
import com.gy.hsxt.gpf.fss.service.NotifyService;
import com.gy.hsxt.gpf.fss.service.RemoteNotifyService;
import com.gy.hsxt.gpf.fss.utils.DirPathBuilder;
import com.gy.hsxt.gpf.fss.utils.FssDateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;

/**
 * @Package :com.gy.hsxt.gpf.fss.service.impl
 * @ClassName : NotifyServiceImpl
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/22 19:39
 * @Version V3.0.0.0
 */
@Service("fssNotifyService")
public class NotifyServiceImpl implements NotifyService {

    private Logger logger = LoggerFactory.getLogger(NotifyServiceImpl.class);

    @Resource
    private RemoteNotifyService remoteNotifyService;

    @Resource
    private LocalNotifyService localNotifyService;

    @Resource
    private FileDetailService fileDetailService;

    @Resource
    private NotifyManagerCenter notifyManagerCenter;

    @Value("${dir.root}")
    private String dirRoot;

    /**
     * 远程通知：接收综合前置同步文件的通知
     * 其他平台文件同步系统------>综合前置------>本系统------>子系统
     *
     * @param notify
     * @return
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean remoteSyncNotify(final RemoteNotify notify) throws HsException {
        logger.info("==========远程通知：{} =========", notify);
        //参数验证
        HsAssert.notNull(notify, FSSRespCode.PARAM_NULL_ERROR, "请求参数为null");
        HsAssert.hasText(notify.getNotifyNo(), FSSRespCode.PARAM_EMPTY_ERROR, "通知编号不能为空");
        HsAssert.isTrue(notify.getFileCount() == notify.getDetails().size(), FSSRespCode.FILE_COUNT_ERROR, "文件数量错误");
        //是否已经通知过
        RemoteNotify exist = remoteNotifyService.queryById(notify.getNotifyNo());
        if (exist == null) {
            //保存通知和文件详情
            notify.setReceived(1);
            int count = remoteNotifyService.save(notify);
            if (count == 1 && CollectionUtils.isNotEmpty(notify.getDetails())) {
                //拼接文件夹路径
                String downloadDir = dirRoot + DirPathBuilder.downloadDir(notify);
                File dir = new File(downloadDir);
                if (!dir.exists()) {
                    boolean success = dir.mkdirs();
                    logger.info("=====文件夹创建结果：{} =====", success);
                }
                //设置生成文件的路径
                for (FileDetail fileDetail : notify.getDetails()) {
                    fileDetail.setTarget(downloadDir + fileDetail.getFileName());
                }
                count = fileDetailService.batchSave(notify.getDetails());
                logger.info("========远程通知保存文件数：{} =======", count);
                if (count == notify.getDetails().size()) {
                    notifyManagerCenter.execute(new Runnable() {
                        @Override
                        public void run() {
                            boolean success = notifyManagerCenter.handleNotify(notify);
                            logger.info("=========线程处理文件结果：{} ==========", success);
                            //文件批处理完成之后，需要通知子系统使用
                            if (success) {
                                //更新远程通知
                                RemoteNotify remoteNotify = new RemoteNotify();
                                remoteNotify.setAllCompleted(1);
                                remoteNotify.setCompletedTime(FssDateUtil.obtainToday(FssDateUtil.DATE_TIME_FORMAT));
                                remoteNotify.setNotifyNo(notify.getNotifyNo());
                                int num = remoteNotifyService.modify(remoteNotify);
                                //通知本平台子系统
                                if (num == 1) {
                                    notifyManagerCenter.sendLocalNotify(notify.getNotifyNo());
                                }
                            }
                        }
                    });
                }
            }
        } else {
            logger.info("=========远程重复通知[{}]=========", notify.getNotifyNo());
        }
        return true;
    }

    /**
     * 本地通知：接收本地局域网中子系统同步文件的通知
     * 子系统------>本系统------>综合前置-------->其他平台文件同步系统
     *
     * @param notify
     * @return
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean localSyncNotify(final LocalNotify notify) throws HsException {
        logger.info("==========本地通知：{} =========", notify);
        HsAssert.notNull(notify, FSSRespCode.PARAM_NULL_ERROR, "请求参数为null");
        HsAssert.hasText(notify.getNotifyNo(), FSSRespCode.PARAM_EMPTY_ERROR, "通知编号不能为空");
        HsAssert.isTrue(notify.getFileCount() == notify.getDetails().size(), FSSRespCode.FILE_COUNT_ERROR, "文件数量错误");
        //是否重复通知
        LocalNotify exist = localNotifyService.queryById(notify.getNotifyNo());
        if (exist == null) {
            notify.setReceived(1);//接收到本地通知
            int count = localNotifyService.save(notify);
            if (count == 1 && CollectionUtils.isNotEmpty(notify.getDetails())) {
                for (FileDetail fileDetail : notify.getDetails()) {
                    fileDetail.setSource(dirRoot + File.separator+ DirPathBuilder.UPLOAD_DIR+ StringUtils.substringAfter(fileDetail.getSource(),DirPathBuilder.UPLOAD_DIR));
                    fileDetail.setTarget(fileDetail.getSource());
                }
                count = fileDetailService.batchSave(notify.getDetails());
                logger.info("========本地通知保存文件数：{} =======", count);
                notifyManagerCenter.execute(new Runnable() {
                    @Override
                    public void run() {
                        boolean success = notifyManagerCenter.handleNotify(notify);
                        logger.info("=========线程处理文件结果：{} ==========", success);
                        //本地上传的文件经过处理之后，通知其他平台进行同步和处理
                        if (success) {
                            //更新本地通知
                            LocalNotify localNotify = new LocalNotify();
                            localNotify.setNotifyNo(notify.getNotifyNo());
                            localNotify.setAllCompleted(1);
                            localNotify.setCompletedTime(FssDateUtil.obtainToday(FssDateUtil.DATE_TIME_FORMAT));
                            int num = localNotifyService.modify(localNotify);
                            //通知综合前置
                            if (num == 1) {
                                notifyManagerCenter.sendRemoteNotify(notify.getNotifyNo());
                            }
                        }
                    }
                });
            }
        } else {
            logger.info("=========本地重复通知[{}]=========", notify.getNotifyNo());
        }
        return true;
    }

    /**
     * 异步接收通知处理结果
     *
     * @param notify
     * @return
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean receiveResultNotify(ResultNotify notify) throws HsException {
        logger.info("==========结果通知：{} =========", notify);
        HsAssert.notNull(notify, FSSRespCode.PARAM_NULL_ERROR, "请求参数为null");
        HsAssert.hasText(notify.getNotifyNo(), FSSRespCode.PARAM_EMPTY_ERROR, "通知编号不能为空");
        int count;
        RemoteNotify remote = remoteNotifyService.queryById(notify.getNotifyNo());
        HsAssert.notNull(remote, FSSRespCode.DB_NOT_EXIST_ERROR, "对应通知不存在");
        RemoteNotify remoteNotify = new RemoteNotify();
        remoteNotify.setNotifyNo(notify.getNotifyNo());
        remoteNotify.setAllCompleted(notify.getAllCompleted());
        remoteNotify.setAllPass(notify.getAllPass());
        remoteNotify.setCompletedTime(notify.getCompletedTime());
        remoteNotify.setRemark(notify.getRemark());
        count = remoteNotifyService.modify(remoteNotify);
        return count == 1;
    }
}
