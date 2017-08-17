/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.notify;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.fss.bean.FileDetail;
import com.gy.hsxt.gpf.fss.bean.FileNotify;
import com.gy.hsxt.gpf.fss.bean.LocalNotify;
import com.gy.hsxt.gpf.fss.bean.RemoteNotify;
import com.gy.hsxt.gpf.fss.constant.FssNotifyType;
import com.gy.hsxt.gpf.fss.notify.handler.DownloadHandler;
import com.gy.hsxt.gpf.fss.notify.handler.UploadHandler;
import com.gy.hsxt.gpf.fss.notify.handler.VerifyHandler;
import com.gy.hsxt.gpf.fss.service.CallbackService;
import com.gy.hsxt.gpf.fss.service.FileDetailService;
import com.gy.hsxt.gpf.fss.service.LocalNotifyService;
import com.gy.hsxt.gpf.fss.service.RemoteNotifyService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 通知管理中心
 *
 * @Package :com.gy.hsxt.gpf.fss.file
 * @ClassName : NotifyManagerCenter
 * @Description : 通知管理中心
 * @Author : chenm
 * @Date : 2015/10/23 16:14
 * @Version V3.0.0.0
 */
@Service
public class NotifyManagerCenter {

    private Logger logger = LoggerFactory.getLogger(NotifyManagerCenter.class);

    @Resource
    private ThreadPoolTaskExecutor jobExecutor;

    @Resource
    private FileDetailService fileDetailService;

    @Resource
    private LocalNotifyService localNotifyService;

    @Resource
    private RemoteNotifyService remoteNotifyService;

    @Resource
    private CallbackService callbackService;

    /**
     * 处理文件通知
     *
     * @param notify
     * @return
     * @throws HsException
     */
    public boolean handleNotify(FileNotify notify) throws HsException {
        FssNotifyType notifyType = FssNotifyType.typeOf(notify.getNotifyType());
        List<FileDetail> details = fileDetailService.queryByNotifyNo(notify.getNotifyNo());
        boolean success = false;
        if (notifyType != null && CollectionUtils.isNotEmpty(details)) {
            switch (notifyType) {
                case DOWNLOAD:
                    success = new DownloadHandler(jobExecutor).handler(details, fileDetailService);
                    break;
                case UPLOAD:
                    success = new UploadHandler(jobExecutor).handler(details, fileDetailService);
                    break;
                case MD5:
                    success = new VerifyHandler(jobExecutor).handler(details, fileDetailService);
                    break;
                case DOWNLOAD_MD5:
                    success = new DownloadHandler(jobExecutor).setNext(new VerifyHandler(jobExecutor)).handler(details, fileDetailService);
                    break;
                case UPLOAD_MD5:
                    success = new UploadHandler(jobExecutor).setNext(new VerifyHandler(jobExecutor)).handler(details, fileDetailService);
                    break;
                default:
                    logger.info("======该类型[{}]暂不支持处理=====",notifyType);
                    break;
            }
        }
        return success;
    }

    /**
     * 远程通知处理完成之后 通知本地系统使用
     *
     * @param notifyNo
     */
    public void sendLocalNotify(String notifyNo) {
        RemoteNotify remoteNotify = remoteNotifyService.queryById(notifyNo);
        //所有文件下载后通过了校验，则可通知子系统使用
        if (remoteNotifyService.checkAllPass(notifyNo)) {
            //下载时已经校验通过了
            remoteNotify.setAllPass(1);
            boolean success = callbackService.callbackLocalForRemote(remoteNotify);
            logger.info("=====远程通知本地回调的结果：{} =====", success);
            //异步返回远程通知结果
            success = callbackService.callbackRemoteForRemote(remoteNotify);
            logger.info("=====远程通知远程回调的结果：{} =====", success);
        } else {//若没有，则重新下载
            handleNotify(remoteNotify);
        }
    }

    /**
     * 通知综合前置 再到其他平台子系统
     *
     * @param notifyNo
     */
    public void sendRemoteNotify(String notifyNo) {
        LocalNotify localNotify = localNotifyService.queryById(notifyNo);
        //所有文件校验通过之后，可发送远程通知
        if (localNotifyService.checkAllPass(notifyNo)) {
            localNotify.setAllPass(1);
            boolean success = callbackService.callbackRemoteForLocal(localNotify);
            logger.info("=====本地通知远程回调的结果：{} =====", success);
        } else {//校验如果没有通过，则通知子系统重新上传
            boolean success = callbackService.callbackLocalForLocal(localNotify);
            logger.info("=====本地通知本地回调的结果：{} =====", success);
        }
    }


    /**
     * 执行一个线程
     *
     * @param runnable
     */
    public void execute(Runnable runnable) {
        jobExecutor.execute(runnable);
    }
}
