/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.scheduler.job;

import com.gy.hsxt.gpf.fss.bean.LocalNotify;
import com.gy.hsxt.gpf.fss.bean.QueryNotify;
import com.gy.hsxt.gpf.fss.bean.RemoteNotify;
import com.gy.hsxt.gpf.fss.scheduler.AbstractJob;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * 远程通知定时扫描
 *
 * @Package :com.gy.hsxt.gpf.fss.scheduler.job
 * @ClassName : RemoteNotifyJob
 * @Description : 远程通知中包含两种情况
 * 1.其他平台通知本平台，收到通知的才会有记录；不必考虑。
 * 2.本平台发给其他平台的通知，如果没有收到其他平台的异步返回结果，可以定时重复通知。
 * @Author : chenm
 * @Date : 2015/11/2 18:47
 * @Version V3.0.0.0
 */
public class RemoteNotifyJob extends AbstractJob {


    /**
     * 定时扫描的方法
     */
    public void scan() {

        QueryNotify queryNotify = new QueryNotify();
        //查询所有没有接收到的通知
        queryNotify.setReceived(0);
        //发到本平台的远程通知：失败的情况下，是不保存记录的；待通知方重新发送通知
        //因此只需查询本平台发出的远程通知
        queryNotify.setFromPlat(platCode);
        //本平台发出去的远程通知(本地通知远程回调的情况)
        List<RemoteNotify> remoteNotifies = remoteNotifyService.queryByOther(queryNotify);

        if (CollectionUtils.isNotEmpty(remoteNotifies)) {
            for (RemoteNotify remoteNotify : remoteNotifies) {
                touch(remoteNotify);
            }
        }
    }

    /**
     * 触发回调
     * @param remoteNotify 远程通知
     * @return boolean
     */
    public boolean touch(RemoteNotify remoteNotify) {
        LocalNotify localNotify = localNotifyService.queryById(remoteNotify.getNotifyNo());
        boolean success = callbackService.callbackRemoteForLocal(localNotify);
        logger.info("=====本地通知[{}]远程回调结果:[{}]=====", remoteNotify.getNotifyNo(), success);
        return success;
    }
}
