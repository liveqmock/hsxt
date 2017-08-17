/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.bean;

/**
 * 结果通知类
 *
 * @Package :com.gy.hsxt.gpf.fss.bean
 * @ClassName : ResultNotify
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/23 15:12
 * @Version V3.0.0.0
 */
public class ResultNotify extends BaseNotify {

    private static final long serialVersionUID = 5453732039970122789L;
    /**
     * 通知编码
     */
    private String notifyNo;

    /**
     * 相关操作是否完成 上传或下载
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
     * 相关说明
     */
    private String remark;

    public String getNotifyNo() {
        return notifyNo;
    }

    public void setNotifyNo(String notifyNo) {
        this.notifyNo = notifyNo;
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

    @Override
    public String toString() {
        return super.toString();
    }
}
