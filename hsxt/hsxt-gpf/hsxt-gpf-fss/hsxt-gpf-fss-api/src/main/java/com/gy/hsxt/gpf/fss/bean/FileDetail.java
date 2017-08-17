/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 文件详情实体
 *
 * @Package :com.gy.hsxt.gpf.fss.bean
 * @ClassName : FileDetail
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/28 11:38
 * @Version V3.0.0.0
 */
public class FileDetail implements Serializable {

    private static final long serialVersionUID = -8034089007133426119L;

    /**
     * 文件详情ID
     */
    private int detailId;

    /**
     * 通知编号
     */
    private String notifyNo;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件大小
     */
    private long fileSize;

    /**
     * 大小单位 B \ KB  \ MB  \ GB
     */
    private String unit;

    /**
     * 文件验证码
     */
    private String code;

    /**
     * 验证是否通过 0为不通过 1为通过
     * 下载时用
     */
    private int isPass;

    /**
     * 文件传输开始时间 格式：yyyy-MM-dd HH:mm:ss
     */
    private String startTime;

    /**
     * 文件传输结束时间 格式：yyyy-MM-dd HH:mm:ss
     */
    private String endTime;

    /**
     * 文件源地址：
     * 1.远程下载时，源文件地址是外网网址
     * 2.本地下载时，源文件地址与文件目标地址相同，同是本地共享文件夹下的文件地址
     */
    private String source;

    /**
     * 文件目标地址：本地共享文件夹下的文件地址
     */
    private String target;

    /**
     * 是否局域网共享 0-跨平台非共享 1-本平台共享
     */
    private int isLocal;

    /**
     * 文件传输比率
     * 传输失败时，已完成的比率
     */
    private double percent;

    /**
     * 重试次数
     */
    private int recount;

    /**
     * 相关原因说明
     */
    private String reason;

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public String getNotifyNo() {
        return notifyNo;
    }

    public void setNotifyNo(String notifyNo) {
        this.notifyNo = notifyNo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getIsPass() {
        return isPass;
    }

    public void setIsPass(int isPass) {
        this.isPass = isPass;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(int isLocal) {
        this.isLocal = isLocal;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public int getRecount() {
        return recount;
    }

    public void setRecount(int recount) {
        this.recount = recount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
