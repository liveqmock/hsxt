/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.common.enumtype.apply;


/** 
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.apply  
 * @ClassName: FilingAppKey 
 * @Description:报备key 
 *
 * @author: xiaofl 
 * @date: 2015-12-15 下午3:02:36 
 * @version V1.0 
 

 */ 
public enum FilingAppKey {
    
    /** 企业基本信息 **/
    FILING_APP("FILING_APP"),

    /** 股东信息 **/
    FILING_SH("FILING_SH"),

    /** 附件信息 **/
    FILING_APT("FILING_APT");

    private String code;

    FilingAppKey(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

}
