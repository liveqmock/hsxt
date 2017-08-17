package com.gy.hsxt.lcs.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 转现银行
 * 
 * @Package: com.gy.hsxt.lcs.bean  
 * @ClassName: Bank 
 * @Description: TODO
 *
 * @author: yangjianguo 
 * @date: 2015-12-15 下午12:28:55 
 * @version V1.0
 */
public class Bank implements Serializable {

    private static final long serialVersionUID = -3460546955261742761L;

    /** 银行代码 */
    private String bankNo;

    /** 银行名称 */
    private String bankName;

    /** 删处标识 */
    private long delFlag;

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public long getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(long delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
