/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.fss.bean.LocalNotify;
import com.gy.hsxt.gpf.fss.bean.RemoteNotify;

/**
 * @Package :com.gy.hsxt.gpf.fss.service
 * @ClassName : CallbackService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/30 14:34
 * @Version V3.0.0.0
 */
public interface CallbackService{

    /**
     * 远程通知本地回调
     *
     * @param remoteNotify
     * @return
     * @throws HsException
     */
    boolean callbackLocalForRemote(RemoteNotify remoteNotify) throws HsException;

    /**
     * 本地通知本地回调
     *
     * @param localNotify
     * @return
     * @throws HsException
     */
    boolean callbackLocalForLocal(LocalNotify localNotify) throws HsException;

    /**
     * 本地通知远程回调
     *
     * @param localNotify
     * @return
     * @throws HsException
     */
    boolean callbackRemoteForLocal(LocalNotify localNotify) throws HsException;

    /**
     * 远程通知远程回调
     *
     * @param remoteNotify
     * @return
     * @throws HsException
     */
    boolean callbackRemoteForRemote(RemoteNotify remoteNotify) throws HsException;

}
