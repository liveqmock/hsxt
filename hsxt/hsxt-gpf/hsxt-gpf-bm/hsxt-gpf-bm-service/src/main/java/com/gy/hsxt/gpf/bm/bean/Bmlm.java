package com.gy.hsxt.gpf.bm.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;

/**
 * 再增值积分
 * 增值点 == 子节点数
 * 积点数 == 1000:500 的剩余值
 *
 * @Package : com.gy.hsxt.gpf.bm.bean
 * @ClassName : IncVo
 * @Description : 再增值积分
 * @Author : chenm
 * @Date : 2016/1/8 9:55
 * @Version V3.0.0.0
 */
public class Bmlm implements Serializable {

    private static final long serialVersionUID = -5318970762227903402L;
    /**
     * 企业互生号
     */
    private String resNo;

    /**
     * 客户号
     */
    private String custId;

    /**
     * 积分数||申报个数
     */
    private String point = "0";
    /**
     * 积分参考值
     * 规则：
     * 服务公司 → 企业申报个数 * 10
     * 成员企业/托管企业 → 积分扣除总数 * 1%
     */
    private String pointREF = "0";
    /**
     * 基数
     */
    private String baseVal = "0";
    /**
     * 企业类型
     */
    private Integer type;
    /**
     * 计算后分配积分数
     */
    private String mlmPoint = "0";
    /**
     * 百位分配比
     **/
    private String percent = "0";
    /**
     * 累加次数
     **/
    private Integer count = 0 ;
    /**
     * 最后再增值计算时间
     */
    private String lastCalcTime;
    /**
     * 父节点
     **/
    private String parent;

    /**
     * 无参构造函数
     */
    public Bmlm() {
        super();
    }

    /**
     * 含参构造函数
     *
     * @param custId 客户号
     * @param point 积分值/申报个数
     */
    public Bmlm(String custId, String point) {
        this.custId = custId;
        this.point = point;
    }

    public String getResNo() {
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPointREF() {
        return pointREF;
    }

    public void setPointREF(String pointREF) {
        this.pointREF = pointREF;
    }

    public String getBaseVal() {
        return baseVal;
    }

    public void setBaseVal(String baseVal) {
        this.baseVal = baseVal;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMlmPoint() {
        return mlmPoint;
    }

    public void setMlmPoint(String mlmPoint) {
        this.mlmPoint = mlmPoint;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getLastCalcTime() {
        return lastCalcTime;
    }

    public void setLastCalcTime(String lastCalcTime) {
        this.lastCalcTime = lastCalcTime;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}

	