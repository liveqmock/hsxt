/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.bean.safeSet;

import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.uc.as.bean.common.AsPwdQuestion;

/***
 * 密保问题实体维护类
 * 
 * @Package: com.gy.hsxt.access.web.bean.safeSet
 * @ClassName: PwdQuestionBean
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-2 下午7:57:40
 * @version V1.0
 */
public class PwdQuestionBean extends AsPwdQuestion {

    private static final long serialVersionUID = 346987390595388436L;

    /**
     * 操作员客户号
     */
    private String custId;

    /**
     * 企业客户号
     */
    private String entCustId;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    /**
     * 数据验证
     */
    public void validateEmptyData() {
        RequestUtil.verifyParamsIsNotEmpty(
                new Object[] { super.getAnswer(), RespCode.AS_PARAM_INVALID},
                new Object[] { super.getQuestion(), RespCode.AS_PARAM_INVALID}
        );
    }

    public String getEntCustId() {
        return entCustId;
    }

    public void setEntCustId(String entCustId) {
        this.entCustId = entCustId;
    }

}
