/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

package com.gy.hsxt.ac.bean;

import java.io.Serializable;

/** 
 * 冲正红冲对象
 * @Package: com.gy.hsxt.ac.bean  
 * @ClassName: AccountWriteBack 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2015-12-11 上午5:19:03 
 * @version V1.0 
 

 */
public class AccountWriteBack implements Serializable{
    
    private static final long serialVersionUID = -7045563429449335739L;
    
    /** 冲正红冲标识 （1 自动冲正 2 手工红冲）*/
    private     int     writeBack;
    
    /** 交易类型*/
    private     String  transType;
    
    /** 关联交易流水号*/   
    private     String  relTransNo;
    
    /** 备注*/   
    private     String  remark;

    /**
     * @return the 冲正红冲标识（）
     */
    public int getWriteBack() {
        return writeBack;
    }

    /**
     * @param 冲正红冲标识（） the writeBack to set
     */
    public void setWriteBack(int writeBack) {
        this.writeBack = writeBack;
    }

    /**
     * @return the 交易类型
     */
    public String getTransType() {
        return transType;
    }

    /**
     * @param 交易类型 the transType to set
     */
    public void setTransType(String transType) {
        this.transType = transType;
    }

    /**
     * @return the 关联交易流水号
     */
    public String getRelTransNo() {
        return relTransNo;
    }

    /**
     * @param 关联交易流水号 the relTransNo to set
     */
    public void setRelTransNo(String relTransNo) {
        this.relTransNo = relTransNo;
    }

    /**
     * @return the 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param 备注 the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
   
    
    
    
    
    
    
    
    
}
