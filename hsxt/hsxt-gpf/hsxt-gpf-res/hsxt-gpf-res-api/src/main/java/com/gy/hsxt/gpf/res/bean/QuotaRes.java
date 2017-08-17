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

package com.gy.hsxt.gpf.res.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/** 
 * 
 * @Package: com.gy.hsxt.gpf.res.bean  
 * @ClassName: QuotaRes 
 * @Description: 配额资源信息，包含服务公司号及分配状态
 *
 * @author: yangjianguo 
 * @date: 2015-12-8 上午9:26:25 
 * @version V1.0 
 */
public class QuotaRes implements Serializable {

    private static final long serialVersionUID = -2221129723652649608L;

    /** 服务资源号 **/
    private String sResNo;
    
    /** 地区平台代码 **/
    private String platNo;
    
    /** 分配状态 ：未分配、申请中、已分配  **/
    private Integer status;
    
    /** 申请编号  **/
    private String applyId;

    public String getsResNo() {
        return sResNo;
    }

    public void setsResNo(String sResNo) {
        this.sResNo = sResNo;
    }

    public String getPlatNo() {
        return platNo;
    }

    public void setPlatNo(String platNo) {
        this.platNo = platNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }
    
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    } 
}
