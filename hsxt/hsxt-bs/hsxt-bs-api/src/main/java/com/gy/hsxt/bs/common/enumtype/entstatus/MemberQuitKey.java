/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.entstatus;

/** 
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.entstatus  
 * @ClassName: MemberQuitKey 
 * @Description: 成员企业注销key
 *
 * @author: xiaofl 
 * @date: 2015-12-15 下午3:06:37 
 * @version V1.0 
 

 */ 
public enum MemberQuitKey {
    
    /** 成员企业资格注销申请信息 **/
    MEMBER_QUIT("MEMBER_QUIT"),
    
    /** 服务公司及审批信息 **/
    SER_APPR_HIS("SER_APPR_HIS"),
    
    /** 地区平台审批信息 **/
    PLAT_APPR_HIS("PLAT_APPR_HIS"),
    
    /** 地区平台复核信息 **/
    PLAT_REVIEW_HIS("PLAT_REVIEW_HIS");

    private String code;

    MemberQuitKey(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
