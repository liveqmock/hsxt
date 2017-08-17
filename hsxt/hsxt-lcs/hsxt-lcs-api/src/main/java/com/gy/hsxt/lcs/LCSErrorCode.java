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

package com.gy.hsxt.lcs;

import com.gy.hsxt.common.constant.IRespCode;

/**
 * LCS错误码定义,错误码范围：23000~23999
 * 
 * @Package: com.gy.hsxt.lcs
 * @ClassName: LCSErrorCode
 * @Description: TODO
 * 
 * @author: yangjianguo
 * @date: 2016-1-13 上午10:37:52
 * @version V1.0
 */
public enum LCSErrorCode implements IRespCode {
    /** 数据没找到 **/
    DATA_NOT_FOUND(23801, "数据没找到 "),
    /** 初始化地区平台信息未找到 **/
    INIT_LOCAL_INFO_NOT_FOUND(23804, "初始化地区平台信息未找到 "),

    ;

    /**
     * 错误代码
     */
    private int errorCode;

    /**
     * 错误描述
     */
    private String erroDesc;

    LCSErrorCode(int code, String desc) {
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
