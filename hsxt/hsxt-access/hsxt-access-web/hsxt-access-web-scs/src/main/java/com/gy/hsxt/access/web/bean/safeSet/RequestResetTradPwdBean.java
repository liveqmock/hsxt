/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.bean.safeSet;

import java.io.Serializable;

import com.gy.hsxt.access.web.bean.AbstractSCSBase;
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
 * @date: 2015-11-2 下午7:59:09
 * @version V1.0
 */
public class RequestResetTradPwdBean extends AbstractSCSBase implements Serializable {
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
    
    /**
     * 非空数据验证
     * 
     * @return
     */
    public void validateEmptyData() {
        RequestUtil.verifyParamsIsNotEmpty(
            new Object[] { this.applyID, RespCode.AS_PARAM_INVALID },       //申请业务书id
            new Object[] { this.explain, RespCode.AS_PARAM_INVALID },       //申请说明
            new Object[] { this.validateCode, RespCode.AS_PARAM_INVALID }); //验证码
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

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

}
