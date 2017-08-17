/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.utils;

import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.gpf.fss.constant.IfssPurpose;

import java.util.Date;

/**
 * 文件名称构建工具
 *
 * @Package :com.gy.hsxt.gpf.fss.utils
 * @ClassName : FileNameBuilder
 * @Description :
 * 文件名称规则：用途 + 日期 (yyyyMMdd) + . + 来源系统代码 + . + out
 * @Author : chenm
 * @Date : 2015/10/27 14:54
 * @Version V3.0.0.0
 */
public abstract class FileNameBuilder {

    public static final String DOT_STR = ".";

    public static final String FILE_TAIL = "out";

    public static final String UN_LINE = "_";

    /**
     * 构建文件名称
     * 示例：再增值系统文件
     * 业务系统的文件：bmlmIn20151014.bs.out
     * 账务系统的文件：bmlmIn20151014.ac.out
     *
     * @param purpose     用途
     * @param date        日期
     * @param fromSysCode 来源子系统
     * @return 文件名称
     */
    public static String build(IfssPurpose purpose, Date date, String fromSysCode) {
        String dateStr = DateUtil.DateToString(date, DateUtil.DATE_FORMAT);
        return build(purpose, dateStr, fromSysCode);
    }

    /**
     * 构建文件名称
     * 示例：再增值系统文件
     * 业务系统的文件：bmlmIn20151014.bs.out
     * 账务系统的文件：bmlmIn20151014.ac.out
     *
     * @param purpose     用途
     * @param date        日期
     * @param fileNo      文件编号
     * @param fromSysCode 来源子系统
     * @return 文件名称
     */
    public static String build(IfssPurpose purpose, Date date, Integer fileNo, String fromSysCode) {
        String dateStr = DateUtil.DateToString(date, DateUtil.DATE_FORMAT);
        return build(purpose, dateStr,fileNo, fromSysCode);
    }

    /**
     * 构建文件名称
     * 示例：再增值系统文件
     * 业务系统的文件：bmlm20151014.bs.out
     * 账务系统的文件：bmlm20151014.ac.out
     * 增值系统的文件：bmlm20151014.bm.out
     *
     * @param purpose     用途
     * @param date        日期
     * @param fromSysCode 来源子系统
     * @return 文件名称
     */
    public static String build(IfssPurpose purpose, String date, String fromSysCode) {
        return build(purpose,date,null,fromSysCode);
    }

    /**
     * 构建文件名称
     * 示例：再增值系统文件
     * 业务系统的文件：bmlm20151014_0.bs.out
     * 账务系统的文件：bmlm20151014_1.ac.out
     * 增值系统的文件：bmlm20151014_2.bm.out
     *
     * @param purpose     用途
     * @param date        日期
     * @param fileNo      文件编号
     * @param fromSysCode 来源子系统
     * @return 文件名称
     */
    public static String build(IfssPurpose purpose, String date, Integer fileNo, String fromSysCode) {
        StringBuilder fileName = new StringBuilder();
        fileName.append(purpose.getCode());
        fileName.append(date);
        if (fileNo != null) {
            fileName.append(UN_LINE).append(fileNo);
        }
        fileName.append(DOT_STR);
        fileName.append(fromSysCode).append(DOT_STR).append(FILE_TAIL);
        return fileName.toString();
    }
}
