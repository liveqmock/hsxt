/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.bean;

/**
 * 局域网内通知/本地通知类
 *
 * @Package :com.gy.hsxt.gpf.fss.bean
 * @ClassName : LocalNotify
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/22 16:48
 * @Version V3.0.0.0
 */
public class LocalNotify extends FileNotify {

    private static final long serialVersionUID = -8195571652889581606L;

    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * 构建本地通知
     *
     * @param notifyNo 通知编号
     * @return bean
     */
    public static LocalNotify build(String notifyNo) {
        LocalNotify localNotify = new LocalNotify();
        localNotify.setNotifyNo(notifyNo);
        return localNotify;
    }
}
