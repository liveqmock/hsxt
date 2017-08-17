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
 * 服务记账返回对象
 * @Package: com.gy.hsxt.ac.bean  
 * @ClassName: AccountServiceInfo 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2015-11-3 上午11:30:32 
 * @version V1.0 
 

 */
public class AccountServiceInfo implements Serializable{

    private static final long serialVersionUID = -3068202525756724136L;

    /** 客户全局编号 */
    private   String       custID;
    
    /** 互生号 */
    private   String       hsResNo;
    
    /** 扣除标识
     *  Y : 扣除成功
     *  N : 扣除失败
     */
    private   String       dedutFlag;
    
    /** 失败信息  */
    private   String       failInfo;

    /**
     * @return the 客户全局编号
     */
    public String getCustID() {
        return custID;
    }

    /**
     * @param 客户全局编号 the custID to set
     */
    public void setCustID(String custID) {
        this.custID = custID;
    }

    /**
     * @return the 互生号
     */
    public String getHsResNo() {
        return hsResNo;
    }

    /**
     * @param 互生号 the hsResNo to set
     */
    public void setHsResNo(String hsResNo) {
        this.hsResNo = hsResNo;
    }

    /**
     * @return the 扣除标识Y:扣除成功N:扣除失败
     */
    public String getDedutFlag() {
        return dedutFlag;
    }

    /**
     * @param 扣除标识Y:扣除成功N:扣除失败 the dedutFlag to set
     */
    public void setDedutFlag(String dedutFlag) {
        this.dedutFlag = dedutFlag;
    }

    /**
     * @return the 失败信息
     */
    public String getFailInfo() {
        return failInfo;
    }

    /**
     * @param 失败信息 the failInfo to set
     */
    public void setFailInfo(String failInfo) {
        this.failInfo = failInfo;
    } 
    
    
    
    
}
