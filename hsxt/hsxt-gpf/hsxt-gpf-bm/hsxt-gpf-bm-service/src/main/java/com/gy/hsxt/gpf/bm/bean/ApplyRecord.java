package com.gy.hsxt.gpf.bm.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 申报记录
 *
 * @Package : com.gy.hsxt.gpf.bm.bean
 * @ClassName : ApplyRecord
 * @Description : 申报记录
 * @Author : chenm
 * @Date : 2016/1/8 9:55
 * @Version V3.0.0.0
 */
public class ApplyRecord implements Serializable {

    private static final long serialVersionUID = 4081155385403152877L;

    /**
     * 被申报企业的父节点客户号（管理公司可以为互生号） 必填
     */
    private String appCustId;

    /**
     * 拟启用互生号 必填
     */
    private String popNo;

    /**
     * 拟启用客户号 必填
     **/
    private String popCustId;

    /**
     * 增值区域（left||right） 必填
     *
     * @see com.gy.hsxt.gpf.bm.common.Constants#LEFT
     * @see com.gy.hsxt.gpf.bm.common.Constants#RIGHT
     */
    private String area;

    /**
     * 是否跨区（0 跨库 1 非跨库）必填
     *
     * @see com.gy.hsxt.gpf.bm.common.Constants#FLAG_0
     * @see com.gy.hsxt.gpf.bm.common.Constants#FLAG_1
     */
    private String flag;

    /**
     * 被申报企业类型(托管企业、成员企业、服务公司) 非必填
     *
     * @see com.gy.hsxt.common.constant.CustType
     */
    private Integer type;

    /**
     * 拟启用资源号对应的管理公司客户号 非必填
     */
    private String manageCustId;

    /**
     * 申报日期(一定要有时分秒 yyyy-MM-dd HH:mm:ss) 非必填
     */
    private String appDate;

    /**
     * 是否处理状态 0 未处理， 1 已处理  非必填
     *
     */
    private String status;

    /**
     * 被申报企业对应的结算公司（地区平台） 非必填
     */
    private String finCustId;

    public String getAppCustId() {
        return appCustId;
    }

    public void setAppCustId(String appCustId) {
        this.appCustId = appCustId;
    }

    public String getPopNo() {
        return popNo;
    }

    public void setPopNo(String popNo) {
        this.popNo = popNo;
    }

    public String getPopCustId() {
        return popCustId;
    }

    public void setPopCustId(String popCustId) {
        this.popCustId = popCustId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getManageCustId() {
        return manageCustId;
    }

    public void setManageCustId(String manageCustId) {
        this.manageCustId = manageCustId;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFinCustId() {
        return finCustId;
    }

    public void setFinCustId(String finCustId) {
        this.finCustId = finCustId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}

	