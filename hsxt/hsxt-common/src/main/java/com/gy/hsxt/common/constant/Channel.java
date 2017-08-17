/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.common.constant;

/**
 * 接入渠道类型定义 
 * @Package: com.gy.hsxt.common.constant  
 * @ClassName: Channel 
 * @Description: 
 *
 * @author: yangjianguo 
 * @date: 2015-11-5 下午2:21:51 
 * @version V1.0
 */
public enum Channel {
    /** 网页接入**/
    WEB(1),         
    
    /** POS接入**/
    POS(2),         
    
    /** 刷卡器接入**/
    MCR(3),         
    
    /** 移动App接入**/
    MOBILE(4),     
    
    /** 互生平板接入**/
    HSPAD(5),       
    
    /** 互生系统接入**/
    SYS(6),         
    
    /** 语音接入**/
    IVR(7),         
    
    /** 第三方接入**/
    THIRDPART(8);   
    
    /**
     * 接入渠道代码
     */
    private int code;
    
    Channel(int code){
        this.code = code;
    }
    
    public int getCode(){
        return code;
    }
}
