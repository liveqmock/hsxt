/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.bean;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.apply.CertificateQueryParam;

/** 
 * 
 * @Package: com.gy.hsxt.bs.apply.bean  
 * @ClassName: CertificateParam 
 * @Description: 证书查询参数
 *
 * @author: xiaofl 
 * @date: 2015-12-14 下午4:43:58 
 * @version V1.0 
 

 */ 
public class CertificateParam extends CertificateQueryParam {

    private static final long serialVersionUID = -3456211151962804610L;

    /** 证书类型 **/
    private Integer certificateType;

    /** 包含的状态，如"(1,2,3)" **/
    private String inStatus;

    public Integer getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(Integer certificateType) {
        this.certificateType = certificateType;
    }

    public String getInStatus() {
        return inStatus;
    }

    public void setInStatus(String inStatus) {
        this.inStatus = inStatus;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
