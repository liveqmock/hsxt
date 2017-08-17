package com.gy.hsxt.access.web.bean.callCenter;

/**
 * Created by Leiyt on 2016/1/28.
 */
public class SysService {

    /**业务流水号*/
    private String no;

    /**日期*/
    private String bsDate;

    /**受理结果*/
    private Object bsResult;

    /**备注*/
    private String remark;

    public SysService() {
        super();
    }

    public SysService(String no, String bsDate, Object bsResult) {
        super();
        this.no = no;
        this.bsDate = bsDate;
        this.bsResult = bsResult;
    }

    public SysService(String no, String bsDate, Object bsResult, String remark) {
        super();
        this.no = no;
        this.bsDate = bsDate;
        this.bsResult = bsResult;
        this.remark = remark;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getBsDate() {
        return bsDate;
    }

    public void setBsDate(String bsDate) {
        this.bsDate = bsDate;
    }

    public Object getBsResult() {
        return bsResult;
    }

    public void setBsResult(Object bsResult) {
        this.bsResult = bsResult;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
