package com.gy.hsxt.gpf.bm.common;

/**
 * 常量类
 *
 * @Package : com.gy.hsxt.gpf.bm.service.impl
 * @ClassName : ApplyRecordServiceImpl
 * @Description : 常量类
 * @Author : chenm
 * @Date : 2016/1/8 9:55
 * @Version V3.0.0.0
 */
public class Constants {

    /**
     * 第一层节点资源号
     */
    public static final String FIRST_RESOURCE_NO = "00000000001";

    /**
     * 是否跨库申报（是）
     */
    public static final String FLAG_0 = "0";

    /**
     * 是否跨库申报（否）
     */
    public static final String FLAG_1 = "1";

    /**
     * 增值节点左节点
     */
    public static final String LEFT = "left";

    /**
     * 增值节点右节点
     */
    public static final String RIGHT = "right";

    /**
     * 0未处理
     */
    public static final String APPLY_TEMP_STATUS_0 = "0";

    /**
     * 1已处理
     */
    public static final String APPLY_TEMP_STATUS_1 = "1";

    /**
     * 增值结构表
     */
    public static final String T_APP_INCREMENT = "T_APP_INCREMENT";

    /**
     * 达到赠送积分表
     */
    public static final String T_APP_POINT_VALUE = "T_APP_POINT_VALUE";

    /**
     * 再增值表
     */
    public static final String T_APP_BMLM = "T_APP_BMLM";

    /**
     * 申报临时记录表
     */
    public static final String T_APP_TEMP_RECORD = "T_APP_TEMP_RECORD";

    /**
     * 操作日志表
     */
    public static final String T_APP_OPER_RECORD = "T_APP_OPER_RECORD";

    /**
     * 再增值积分数据下载操作
     */
    public static final String OPER_BMLM_DOWNLOAD = "bmlmDownload";

    /**
     * 再增值积分汇总数据上传操作
     */
    public static final String OPER_BMLM_UPLOAD = "bmlmUpload";

    /**
     * 增值积分汇总数据上传操作
     */
    public static final String OPER_MLM_UPLOAD = "mlmUpload";

    /**
     * 积分参考值 = 托管企业/成员企业积分  * 1%
     */
    public static final String BMLM_PCT = "0.01";

    /**
     * 积分参考值 = 服务公司申报个数 * 10
     */
    public static final String BMLM_NUMBER = "10";

    /**
     * 日期格式
     */
    public static final String DATE_TIME_FORMAT_SSS = "yyyyMMddHHmmssSSS";

    /**
     * 增值点有效
     */
    public static final String INCREMENT_ISACTIVE_Y = "Y";

    /**
     * 增值点无效
     */
    public static final String INCREMENT_ISACTIVE_N = "N";
}

	