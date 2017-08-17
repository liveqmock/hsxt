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
 * 申请交易密码重置实体类
 * 
 * @Package: com.gy.hsxt.access.web.bean.safeSet
 * @ClassName: RequestResetTradPwdBean
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-10-28 下午6:43:43
 * @version V1.0
 */
public class RequestResetTradPwdBean extends AbstractCompanyBase implements Serializable {
    private static final long serialVersionUID = 8969815185117378310L;

    /**
     * 申请书文件id
     */
    private String applyID;

    /**
     * 申请说明
     */
    private String explain;

    /**
     * 验证码
     */
    private String validateCode;

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    /**
     * 非空数据验证
     * 
     * @return
     */
    public void validateEmptyData() {
        RequestUtil.verifyParamsIsNotEmpty(
            new Object[] { this.applyID, RespCode.GL_PARAM_ERROR }, 
            new Object[] { this.explain, RespCode.GL_PARAM_ERROR });
    }

    public String getApplyID() {
        return applyID;
    }

    public void setApplyID(String applyID) {
        this.applyID = applyID;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

}
