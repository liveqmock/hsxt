package com.gy.hsxt.point.client.bean;

import com.alibaba.fastjson.JSON;

/**
 * 国家常用货币，POS机初始化用
 * 
 * @Package: com.gy.hsxt.lcs.bean
 * @ClassName: CountryCurrency
 * @Description: TODO
 * 
 * @author: liyh
 * @date: 2015-12-11 上午11:51:57
 * @version V3.0
 */
public class CountryCurrency implements java.io.Serializable {
    private static final long serialVersionUID = 6919084795184140029L;

    /** 货币编号 **/
    private String currencyNo;

    /** 货币代码 **/
    private String currencyCode;

    /** 货币名称 **/
    private String currencyName;

    /** 版本号 **/
    private int version;

    /** 序号 **/
    private int currencySeqNo;

    /**
     * @return the 货币编号
     */
    public String getCurrencyNo() {
        return currencyNo;
    }

    /**
     * @param 货币编号
     *            the currencyNo to set
     */
    public void setCurrencyNo(String currencyNo) {
        this.currencyNo = currencyNo;
    }

    /**
     * @return the 货币代码
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * @param 货币代码
     *            the currencyCode to set
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * @return the 货币名称
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * @param 货币名称
     *            the currencyName to set
     */
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    /**
     * @return the 版本号
     */
    public int getVersion() {
        return version;
    }

    /**
     * @param 版本号
     *            the version to set
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * @return the 序号
     */
    public int getCurrencySeqNo() {
        return currencySeqNo;
    }

    /**
     * @param 序号
     *            the currencySeqNo to set
     */
    public void setCurrencySeqNo(int currencySeqNo) {
        this.currencySeqNo = currencySeqNo;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
