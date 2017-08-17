/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.utils;

import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.gpf.fss.bean.FileNotify;
import com.gy.hsxt.gpf.fss.constant.FssPurpose;
import com.gy.hsxt.gpf.fss.constant.IfssPurpose;

/**
 * 消息编码生成工具
 *
 * @Package :com.gy.hsxt.gpf.fss.utils
 * @ClassName : NotifyNoUtil
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/21 18:02
 * @Version V3.0.0.0
 */
public abstract class NotifyNoUtil {

    private static String NOTIFY_NO_PRE = "fss";
    private static String NOTIFY_NO_JOIN= "|";

    /**
     * 通知消息编码
     *
     * @param notify
     * @return
     */
    public static String outNotifyNo(FileNotify notify) {
        return outNotifyNo(notify.getFromPlat(), notify.getFromSys(), notify.getToPlat(), notify.getToSys(), FssPurpose.typeOf(notify.getPurpose()));
    }

    /**
     * 通知消息编码
     * 格式：fss+ 时间戳 (yyyyMMdd) + fromCode + 'to' + toCode + 'for' + purpose
     *
     * @param fromPlat 平台代码
     * @param fromSys  子系统代码
     * @param toPlat   平台代码
     * @param toSys    子系统代码
     * @param purpose  消息用途
     * @return
     */
    public static String outNotifyNo(String fromPlat, String fromSys, String toPlat, String toSys, IfssPurpose purpose) {
        String dateStamp = DateUtil.DateToString(DateUtil.getCurrentDate(), DateUtil.DATE_FORMAT);
        StringBuilder builder = new StringBuilder(NOTIFY_NO_PRE);
        builder.append(dateStamp).append(NOTIFY_NO_JOIN);
        builder.append(fromPlat).append(fromSys);
        builder.append(NOTIFY_NO_JOIN);
        builder.append(toPlat).append(toSys);
        builder.append(NOTIFY_NO_JOIN);
        builder.append(purpose.getCode());
        return builder.toString();
    }

}
