/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.common.enumtype.apply;

/** 
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.apply  
 * @ClassName: ContractKey 
 * @Description: 合同key
 *
 * @author: xiaofl 
 * @date: 2015-12-15 下午2:57:48 
 * @version V1.0 
 

 */ 
public enum ContractKey {
    
    /** 合同基本信息 **/
    CONTRACT_BASE_INFO("CONTRACT_BASE_INFO"),

    /** 合同发放历史 **/
    CONTRACT_SEND_HIS("CONTRACT_SEND_HIS");

    private String code;

    ContractKey(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

}
