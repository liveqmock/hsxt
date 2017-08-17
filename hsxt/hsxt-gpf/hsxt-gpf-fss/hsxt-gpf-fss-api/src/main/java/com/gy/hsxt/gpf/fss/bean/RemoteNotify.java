/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.bean;

/**
 * 平台间的远程通知
 *
 * @Package :com.gy.hsxt.gpf.fss.bean
 * @ClassName : RemoteNotify
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/22 16:30
 * @Version V3.0.0.0
 */
public class RemoteNotify extends FileNotify {

    private static final long serialVersionUID = -3914706569529830011L;

    /**
     * 构建远程通知
     * @param notifyNo 通知编号
     * @return {@link RemoteNotify}
     */
    public static RemoteNotify bulid(String notifyNo) {
        RemoteNotify remoteNotify = new RemoteNotify();
        remoteNotify.setNotifyNo(notifyNo);
        return remoteNotify;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
