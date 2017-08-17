/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.fss.api.IfssNotifyService;
import com.gy.hsxt.gpf.fss.bean.RemoteNotify;
import com.gy.hsxt.gpf.fss.bean.ResultNotify;

/**
 * 通知业务处理接口
 *
 * @Package :com.gy.hsxt.gpf.fss.service
 * @ClassName : NotifyService
 * @Description : 通知业务处理接口
 * @Author : chenm
 * @Date : 2015/10/22 19:38
 * @Version V3.0.0.0
 */
public interface NotifyService extends IfssNotifyService {

    /**
     * 远程通知：接收综合前置同步文件的通知
     * 其他平台文件同步系统------>综合前置------>本系统------>子系统
     *
     * @param notify
     * @return
     * @throws HsException
     */
    boolean remoteSyncNotify(RemoteNotify notify) throws HsException;

    /**
     * 异步接收通知处理结果
     *
     * @param notify
     * @return
     * @throws HsException
     */
    boolean receiveResultNotify(ResultNotify notify) throws HsException;
}
