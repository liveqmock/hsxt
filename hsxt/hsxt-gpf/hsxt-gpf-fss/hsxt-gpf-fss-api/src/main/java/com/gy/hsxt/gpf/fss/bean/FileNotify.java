/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.bean;

import java.util.List;

/**
 * 文件通知实体
 *
 * @Package :com.gy.hsxt.gpf.fss.bean
 * @ClassName : FileNotify
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/28 11:39
 * @Version V3.0.0.0
 */
public abstract class FileNotify extends BaseNotify {

    private static final long serialVersionUID = 4717138095611500989L;
    /**
     * 通知信息编号
     */
    private String  notifyNo;

    /**
     * 通知来源子系统
     */
    private String fromSys;

    /**
     * 通知目的子系统
     */
    private String toSys;

    /**
     * 通知时间 格式：yyyy-MM-dd HH:mm:ss
     */
    private String notifyTime;

    /**
     * 文件用途 bmlm 再增值 mlm 增值
     * @see com.gy.hsxt.gpf.fss.constant.IfssPurpose
     */
    private String purpose;

    /**
     * 文件个数
     */
    private int fileCount;

    /**
     * 是否被接收 0 - 未接收 1-已接收
     */
    private int received;

    /**
     * 操作是否全部完成 0为未完成  1为完成
     */
    private int allCompleted;

    /**
     * 完成时间
     * 格式：yyyy-MM-dd HH:mm:ss
     */
    private String completedTime;

    /**
     * 文件校验是否全部通过 0 为 未通过 1为通过
     */
    private int allPass;

    /**
     * 通知说明
     */
    private String remark;

    /**
     * 记录创建时间
     * 格式：yyyy-MM-dd HH:mm:ss
     */
    private String createTime;

    /**
     * 文件详情
     */
    private List<FileDetail> details;

    public String getNotifyNo() {
        return notifyNo;
    }

    public void setNotifyNo(String notifyNo) {
        this.notifyNo = notifyNo;
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

    public String getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(String notifyTime) {
        this.notifyTime = notifyTime;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public int getReceived() {
        return received;
    }

    public void setReceived(int received) {
        this.received = received;
    }

    public int getAllCompleted() {
        return allCompleted;
    }

    public void setAllCompleted(int allCompleted) {
        this.allCompleted = allCompleted;
    }

    public String getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(String completedTime) {
        this.completedTime = completedTime;
    }

    public int getAllPass() {
        return allPass;
    }

    public void setAllPass(int allPass) {
        this.allPass = allPass;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<FileDetail> getDetails() {
        return details;
    }

    public void setDetails(List<FileDetail> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}