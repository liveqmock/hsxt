/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.gks.utils;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;

public class ExceptionUtil {
    /**
     * 
     * @param code
     *            异常码
     * @param msg
     *            异常信息
     * @return
     */
    public static HttpRespEnvelope putExceptionInfo(int code, String msg) {
        HttpRespEnvelope hre = new HttpRespEnvelope();
        hre.setRetCode(code);
        hre.setResultDesc(msg);
        hre.setSuccess(false);
        return hre;
    }
}
