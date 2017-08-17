/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.tempacct;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 临账统计实体
 *
 * @Package :com.gy.hsxt.bs.bean.tempacct
 * @ClassName : DebitSum
 * @Description : 临账统计实体
 * @Author : chenm
 * @Date : 2015/11/25 16:51
 * @Version V3.0.0.0
 */
public class DebitSum implements Serializable {

    private static final long serialVersionUID = 6887198490744191495L;

    /**
     * 收款账户名称
     */
    private String receiveAccountName;

    /**
     * 收款总金额
     */
    private String payAllAmount;

    /**
     * 已用总金额
     */
    private String linkAllAmount;

    /**
     * 未用总余额
     */
    private String unlinkAllAmount;

    /**
     * 退款总金额
     */
    private String refundAllAmount;

    public String getReceiveAccountName() {
        return receiveAccountName;
    }

    public void setReceiveAccountName(String receiveAccountName) {
        this.receiveAccountName = receiveAccountName;
    }

    public String getPayAllAmount() {
        return payAllAmount;
    }

    public void setPayAllAmount(String payAllAmount) {
        this.payAllAmount = payAllAmount;
    }

    public String getLinkAllAmount() {
        return linkAllAmount;
    }

    public void setLinkAllAmount(String linkAllAmount) {
        this.linkAllAmount = linkAllAmount;
    }

    public String getUnlinkAllAmount() {
        return unlinkAllAmount;
    }

    public void setUnlinkAllAmount(String unlinkAllAmount) {
        this.unlinkAllAmount = unlinkAllAmount;
    }

    public String getRefundAllAmount() {
        return refundAllAmount;
    }

    public void setRefundAllAmount(String refundAllAmount) {
        this.refundAllAmount = refundAllAmount;
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p/>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
