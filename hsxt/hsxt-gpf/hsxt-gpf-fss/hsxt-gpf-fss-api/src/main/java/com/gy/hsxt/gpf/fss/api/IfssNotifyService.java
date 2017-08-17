/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.api;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.fss.bean.LocalNotify;

/**
 * FSS接收本地通知业务接口
 *
 * @Package :com.gy.hsxt.gpf.fss
 * @ClassName : IfssNotifyService
 * @Description : FSS接收本地通知业务接口
 * @Author : chenm
 * @Date : 2015/10/21 14:16
 * @Version V3.0.0.0
 */
public interface IfssNotifyService {

    /**
     * 本地通知：接收本地局域网中子系统同步文件的通知
     * 子系统------>本系统------>综合前置-------->其他平台文件同步系统
     *
     * @param notify
     * @return
     * @throws HsException
     */
    boolean localSyncNotify(LocalNotify notify) throws HsException;
}
