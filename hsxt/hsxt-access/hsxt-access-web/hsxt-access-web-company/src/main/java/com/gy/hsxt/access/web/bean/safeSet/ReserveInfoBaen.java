/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.bean.safeSet;

import java.io.Serializable;

import com.gy.hsxt.access.web.bean.AbstractCompanyBase;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.constant.RespCode;

/***
 * 预留信息实体类
 * 
 * @Package: com.gy.hsxt.access.web.bean.safeSet
 * @ClassName: ReserveInfoBaen
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-10-27 下午6:47:58
 * @version V1.0
 */
public class ReserveInfoBaen extends AbstractCompanyBase implements Serializable {

    private static final long serialVersionUID = 1914746408641031622L;

    /**
     * 预留信息
     */
    private String reserveInfo;

    /**
     * 数据验证
     * 
     * @return
     */
    public void validateEmptyData() {
        RequestUtil.verifyParamsIsNotEmpty(
        new Object[] { this.reserveInfo, RespCode.GL_PARAM_ERROR });
    }

    public String getReserveInfo() {
        return reserveInfo;
    }

    public void setReserveInfo(String reserveInfo) {
        this.reserveInfo = reserveInfo;
    }
}
