package com.gy.hsxt.gpf.bm.bean;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.gpf.fss.utils.FssDateUtil;

import java.io.Serializable;

/**
 * 再增值积分传值对象
 *
 * @Package : com.gy.hsxt.gpf.bm.bean
 * @ClassName : BmlmVo
 * @Description : 再增值积分传值对象
 * @Author : chenm
 * @Date : 2016/1/8 9:55
 * @Version V3.0.0.0
 */
public class BmlmVo implements Serializable {

    private static final long serialVersionUID = 685639606869474496L;
    /**
     * 企业互生号
     */
    private String resNo;
    /**
     * 企业客户号
     */
    private String custId;
    /**
     * 汇总开始时间
     */
    private String calcStartTime;
    /**
     * 汇总结束时间
     */
    private String calcEndTime;
    /**
     * 积分参考值
     */
    private String pointREF = "0";
    /**
     * 分配基数
     */
    private String baseVal = "0";
    /**
     * 百位分配比
     */
    private String percent = "0";
    /**
     * 再增值积分
     */
    private String bmlmPoint = "0";
    /**
     * 汇总笔数 (再增值可能是多笔汇总的和)
     */
    private int totalRow;

    /**
     * 统计分配时间
     */
    private String allotDate;

    public BmlmVo() {
    }

    public BmlmVo(String resNo, String custId, String pointREF, String baseVal, String percent, String bmlmPoint, int totalRow, String allotDate) {
        super();
        this.resNo = resNo;
        this.custId = custId;
        this.pointREF = pointREF;
        this.baseVal = baseVal;
        this.percent = percent;
        this.bmlmPoint = bmlmPoint;
        this.totalRow = totalRow;
        this.allotDate = allotDate;
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

    public String getCalcStartTime() {
        return calcStartTime;
    }

    public void setCalcStartTime(String calcStartTime) {
        this.calcStartTime = calcStartTime;
    }

    public String getCalcEndTime() {
        return calcEndTime;
    }

    public void setCalcEndTime(String calcEndTime) {
        this.calcEndTime = calcEndTime;
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

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getBmlmPoint() {
        return bmlmPoint;
    }

    public void setBmlmPoint(String bmlmPoint) {
        this.bmlmPoint = bmlmPoint;
    }

    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    public String getAllotDate() {
        return allotDate;
    }

    public void setAllotDate(String allotDate) {
        this.allotDate = allotDate;
    }

    /**
     * 构建传值对象
     *
     * @param bmlm 再增值积分
     * @return vo
     */
    public static BmlmVo bulid(Bmlm bmlm) {
        BmlmVo vo = new BmlmVo(
                bmlm.getResNo(),
                bmlm.getCustId(),
                bmlm.getPointREF(),
                bmlm.getBaseVal(),
                bmlm.getPercent(),
                bmlm.getMlmPoint(),
                bmlm.getCount(),
                bmlm.getLastCalcTime()
        );
        vo.setCalcStartTime(FssDateUtil.obtainMonthFirstDay(FssDateUtil.PREVIOUS_MONTH, FssDateUtil.SHORT_DATE_FORMAT));
        vo.setCalcEndTime(FssDateUtil.obtainMonthFirstDay(FssDateUtil.THIS_MONTH, FssDateUtil.SHORT_DATE_FORMAT));
        return vo;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
