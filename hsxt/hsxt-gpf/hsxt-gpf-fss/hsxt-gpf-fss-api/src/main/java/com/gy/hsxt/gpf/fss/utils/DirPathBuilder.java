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

import java.io.File;
import java.util.Date;

/**
 * @Package :com.gy.hsxt.gpf.fss.utils
 * @ClassName : DirPathBuilder
 * @Description :
 * 文件路径规则：download 或 upload / 子系统代码 /  年 / 月日 / 用途 / 目的平台代码
 * 用途：{@link IfssPurpose}
 * 示例：dirRoot/download/bm/2015/1027/bmlm/001/
 *               dirRoot/download/bm/2015/1027/bmlm/002/
 *  dirRoot为文件夹根路径，需与文件同步系统一致；linux系统下为/opt/share
 * 请大家遵守规则
 * @Author : chenm
 * @Date : 2015/10/27 14:34
 * @Version V3.0.0.0
 */
public abstract class DirPathBuilder {

    /**
     * 下载文件夹
     */
    public static final String DOWNLOAD_DIR = "download";

    /**
     * 上传文件夹
     */
    public static final String UPLOAD_DIR = "upload";

    /**
     * 下载时，文件存放路径
     * @param notify 通知实体
     * @return 文件夹路径
     */
    public static String downloadDir(FileNotify notify) {
        IfssPurpose purpose = FssPurpose.typeOf(notify.getPurpose());
        Date date = DateUtil.StringToDateTime(notify.getNotifyTime());
        //无论yyyy-MM-dd HH:mm:ss 还是yyyy-MM-dd
        date = date == null ? DateUtil.StringToDate(notify.getNotifyTime()) : date;
        return downloadDir(notify.getToSys(), date, purpose, notify.getFromPlat());
    }

    /**
     * 下载时，文件存放路径
     *
     * @param toSysCode 给本平台哪个系统
     * @param date 日期
     * @param purpose 用途
     * @param fromPlatCode 来自哪个平台
     * @return 文件夹路径
     */
    public static String downloadDir(String toSysCode, Date date, IfssPurpose purpose, String fromPlatCode) {
        String dateStr = DateUtil.DateToString(date, DateUtil.DATE_FORMAT);
        return downloadDir(toSysCode, dateStr, purpose, fromPlatCode);
    }

    /**
     * 下载时，文件存放路径
     *
     * @param toSysCode 给本平台哪个系统
     * @param date 日期
     * @param purpose 用途
     * @param fromPlatCode 来自哪个平台
     * @return 文件夹路径
     */
    public static String downloadDir(String toSysCode, String date, IfssPurpose purpose, String fromPlatCode) {
        return makeDir(DOWNLOAD_DIR, toSysCode, date, purpose, fromPlatCode);
    }

    /**
     * 上传时，文件存放路径
     *
     * @param fromSysCode 来自哪个子系统
     * @param date 日期
     * @param purpose 用途
     * @param toPlatCode 哪个平台用
     * @return 文件夹路径
     */
    public static String uploadDir(String fromSysCode, Date date, IfssPurpose purpose, String toPlatCode) {
        String dateStr = DateUtil.DateToString(date, DateUtil.DATE_FORMAT);
        return uploadDir(fromSysCode, dateStr, purpose, toPlatCode);
    }

    /**
     * 上传时，文件存放路径
     *
     * @param fromSysCode 来自哪个子系统
     * @param date 日期
     * @param purpose 用途
     * @param toPlatCode 哪个平台用
     * @return 文件夹路径
     */
    public static String uploadDir(String fromSysCode, String date, IfssPurpose purpose, String toPlatCode) {
        return makeDir(UPLOAD_DIR,fromSysCode,date,purpose,toPlatCode);
    }

    /**
     * 文件夹路径生成
     *
     * @param type 类型
     * @param sysCode 子系统代码
     * @param date 日期
     * @param purpose 用途
     * @param platCode 平台代码
     * @return string
     */
    private static String makeDir(String type, String sysCode, String date, IfssPurpose purpose, String platCode) {
        StringBuilder dir = new StringBuilder(File.separator);
        dir.append(type).append(File.separator);
        dir.append(sysCode).append(File.separator);
        dir.append(date.substring(0, 4)).append(File.separator);
        dir.append(date.substring(4)).append(File.separator);
        dir.append(purpose.getCode()).append(File.separator);
        dir.append(platCode).append(File.separator);
        return dir.toString();
    }
}
