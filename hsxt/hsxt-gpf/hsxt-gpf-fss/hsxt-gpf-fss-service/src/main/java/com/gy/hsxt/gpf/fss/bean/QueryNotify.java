/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.bean;

import com.gy.hsxt.gpf.fss.constant.FssNotifyType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Package :com.gy.hsxt.gpf.fss.bean
 * @ClassName : QueryNotify
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/26 16:55
 * @Version V3.0.0.0
 */
public class QueryNotify implements Serializable {

    private static final long serialVersionUID = 5188161994814301345L;

    /**
     * 记录开始编号
     */
    private int start;

    /**
     * 分页长度
     */
    private int length;

    /**
     * 通知来源平台
     */
    private String fromPlat;

    /**
     * 通知目的平台
     */
    private String toPlat;

    /**
     * 通知来源子系统
     */
    private String fromSys;

    /**
     * 通知目的子系统
     */
    private String toSys;

    /**
     * 文件用途 bmlm 再增值 mlm 增值
     * @see com.gy.hsxt.gpf.fss.constant.IfssPurpose
     */
    private String purpose;

    /**
     * 通知开始时间 格式：yyyy-MM-dd
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String notifyStartDate;

    /**
     * 通知结束时间 格式：yyyy-MM-dd
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String notifyEndDate;

    /**
     * 通知类别
     * @see FssNotifyType
     */
    private int notifyType;

    /**
     * 是否被接收 0 - 未接收 1-已接收
     */
    private Integer received;

    /**
     * 操作是否全部完成 0为未完成  1为完成
     */
    private Integer allCompleted;

    /**
     * 文件校验是否全部通过 0 为 未通过 1为通过
     */
    private Integer allPass;

    /**
     * 记录开始创建时间
     * 格式：yyyy-MM-dd
     */
    private String createStartDate;
    /**
     * 记录结束创建时间
     * 格式：yyyy-MM-dd
     */
    private String createEndDate;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getFromPlat() {
        return fromPlat;
    }

    public void setFromPlat(String fromPlat) {
        this.fromPlat = fromPlat;
    }

    public String getToPlat() {
        return toPlat;
    }

    public void setToPlat(String toPlat) {
        this.toPlat = toPlat;
    }

    public String getFromSys() {
        return fromSys;
    }

    public void setFromSys(String fromSys) {
        this.fromSys = fromSys;
    }

    public String getToSys() {
        return toSys;
    }

    public void setToSys(String toSys) {
        this.toSys = toSys;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getNotifyStartDate() {
        return notifyStartDate;
    }

    public void setNotifyStartDate(String notifyStartDate) {
        this.notifyStartDate = notifyStartDate;
    }

    public String getNotifyEndDate() {
        return notifyEndDate;
    }

    public void setNotifyEndDate(String notifyEndDate) {
        this.notifyEndDate = notifyEndDate;
    }

    public int getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(int notifyType) {
        this.notifyType = notifyType;
    }

    public Integer getReceived() {
        return received;
    }

    public void setReceived(Integer received) {
        this.received = received;
    }

    public Integer getAllCompleted() {
        return allCompleted;
    }

    public void setAllCompleted(Integer allCompleted) {
        this.allCompleted = allCompleted;
    }

    public Integer getAllPass() {
        return allPass;
    }

    public void setAllPass(Integer allPass) {
        this.allPass = allPass;
    }

    public String getCreateStartDate() {
        return createStartDate;
    }

    public void setCreateStartDate(String createStartDate) {
        this.createStartDate = createStartDate;
    }

    public String getCreateEndDate() {
        return createEndDate;
    }

    public void setCreateEndDate(String createEndDate) {
        this.createEndDate = createEndDate;
    }
}
