/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.gpf.res;

import com.gy.hsxt.common.constant.IRespCode;

/**
 * @Description:RES错误码定义，46000~46999
 * 
 * @Package: com.gy.hsxt.gpf.res
 * @ClassName: RESErrorCode
 * @author: yangjianguo
 * @date: 2016-1-13 下午12:26:47
 * @version V1.0
 */
public enum RESErrorCode implements IRespCode {

    RES_SAVE_PLAT_INFO_ERROR(46000, "保存平台公司信息失败"),

    RES_SAVE_MENT_INFO_ERROR(46001, "保存管理公司信息失败"),

    RES_EXIST_PLAT_MENT_RELATIONSHIP(46002, "该平台与管理公司关系已在存"),

    RES_VIOLATE_SINGLE_ONE_TO_MANY(46003, "平台与管理公司关系违反单向一对多"),

    RES_MENT_EXIST(46004, "该管理公司信息已存在"),

    RES_MENT_QUOTA_EXCCED(46005, "该管理公司配额超过999，请减少申请配额"),

    RES_SYSC_QUOTA_ALLOT_DATA_ERROR(46010, "同步资源配额分配数据到地区平台失败"),

    RES_SYSC_ROUTE_DATA_TO_CENTER_PLAT_ERROR(46011, "同步路由数据到总平台失败"),

    RES_SYSC_PLAT_INFO_TO_AREA_PLAT_ERROR(46012, "同步平台公司信息到地区平台失败"),

    RES_SYSC_MENT_INFO_TO_UC_ERROR(46013, "同步管理公司信息到用户中心失败"),

    RES_SYSC_MENT_INFO_TO_BS_ERROR(46014, "同步管理公司信息到业务系统失败"),

    RES_SYSC_DATA_ERROR(46015, "同步数据失败"),

    RES_SAVE_QUOTA_APPLY_ERROR(46030, "保存配额申请失败"),

    RES_APPR_QUOTA_APPLY_ERROR(46031, "审批配额申请失败"),

    RES_NO_OCCUPY_OR_USED(46032, "互生号被占用或已使用"),

    RES_QUOTA_EXCCED(46033, "配额数不够")

    ;

    /**
     * 错误代码
     */
    private int errorCode;

    /**
     * 错误描述
     */
    private String erroDesc;

    RESErrorCode(int code) {
        this.errorCode = code;
    }

    RESErrorCode(int code, String desc) {
        this.errorCode = code;
        this.erroDesc = desc;
    }

    /**
     * @return
     * @see com.gy.hsxt.common.constant.IRespCode#getCode()
     */
    @Override
    public int getCode() {
        return errorCode;
    }

    /**
     * @return
     * @see com.gy.hsxt.common.constant.IRespCode#getDesc()
     */
    @Override
    public String getDesc() {
        return erroDesc;
    }
}
