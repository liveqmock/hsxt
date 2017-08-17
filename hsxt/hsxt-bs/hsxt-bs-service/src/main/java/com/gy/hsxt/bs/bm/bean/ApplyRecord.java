/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bm.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 申报系统 to 增值系统临时记录
 *
 * @Package :com.gy.hsxt.bs.bm.bean
 * @ClassName : ApplyRecord
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/27 12:28
 * @Version V3.0.0.0
 */
public class ApplyRecord implements Serializable {

    private static final long serialVersionUID = -8347672368145282024L;
    /**
     * 申请者客户号（服务公司 ||管理公司）
     * <p/>
     * 服务公司和托管企业申请资源为 XXL 或者 XXR
     */
    private String appCustId;

    /**
     * 拟启用互生号
     */
    private String popNo;

    /**
     * 拟启用客户号
     **/
    private String popCustId;

    /**
     * 增值区域（left||right）
     */
    private String area;

    /**
     * 是否跨区（0 跨库 1 非跨库）
     * <p/>
     * 即是否跨管理公司
     * <p/>
     * 只能是服务公司跨申报服务公司 （必须是同一个结算公司）
     */
    private String flag;

    /**
     * 企业资源类型(托管企业、成员企业、服务公司)
     */
    private Integer type;

    /**
     * 拟启用资源号对应的管理公司客户号
     */
    private String manageCustId;

    /**
     * 申报日期(一定要有时分秒 yyyy-MM-dd HH:mm:ss)
     */
    private String appDate;

    /**
     * 是否处理状态 0 未处理， 1 已处理
     * <p/>
     * 未添加到增值节点（未处理），已添加到增值节点（已处理）
     */
    private String status;

    /**
     * 被申报企业对应的结算公司
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
