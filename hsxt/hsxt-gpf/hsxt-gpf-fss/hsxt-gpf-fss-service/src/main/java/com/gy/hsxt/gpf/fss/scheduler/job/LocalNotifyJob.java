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
 * 本地通知定时扫描
 *
 * @Package :com.gy.hsxt.gpf.fss.scheduler.job
 * @ClassName : LocalNotifyJob
 * @Description : 本地通知有两种情况：
 * 1.本平台内，其他子系统通知给文件同步系统的通知（即本平台通知给其他平台的本地通知），收到通知才会有记录，不必考虑。
 * 2.其他平台通知本平台其他子系统的本地通知
 * @Author : chenm
 * @Date : 2015/11/2 18:46
 * @Version V3.0.0.0
 */
public class LocalNotifyJob extends AbstractJob {


    /**
     * 定时扫描的方法
     */
    public void scan() {

        QueryNotify queryNotify = new QueryNotify();
        //没有接收到的
        queryNotify.setReceived(0);
        //远程通知本地回调的
        queryNotify.setToPlat(platCode);
        //查询符合条件的本地通知
        List<LocalNotify> localNotifies = localNotifyService.queryByOther(queryNotify);

        if (CollectionUtils.isNotEmpty(localNotifies)) {
            for (LocalNotify localNotify : localNotifies) {
                touch(localNotify);
            }
        }
    }

    /**
     * 触发回调
     * @param localNotify 本地通知
     * @return boolean
     */
    public boolean touch(LocalNotify localNotify) {
        RemoteNotify remoteNotify = remoteNotifyService.queryById(localNotify.getNotifyNo());
        boolean success = callbackService.callbackLocalForRemote(remoteNotify);
        logger.info("=====远程通知[{}]本地回调结果:[{}]=====", remoteNotify.getNotifyNo(), success);
        return success;
    }

}
