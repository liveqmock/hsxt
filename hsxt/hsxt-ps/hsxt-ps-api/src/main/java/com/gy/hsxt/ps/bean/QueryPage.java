package com.gy.hsxt.ps.bean;

import com.gy.hsxt.common.bean.Page;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author chenhz
 * @version v0.0.1
 * @description POS单笔查询
 * @createDate 2015-8-18 上午10:19:53
 * @Company 深圳市归一科技研发有限公司
 */
public class QueryPage implements Serializable {

    private static final long serialVersionUID = 6039715178100191384L;

    /**
     * 企业互生号
     */
    @NotBlank(message = "企业互生号不能为空")
    @Size(min = 11, max = 11, message = "企业互生号必须11位！")
    private String entResNo;

    /**
     * 消费者互生号
     */
    private String perResNo;

    /**
     * 开始时间
     */
    private String startDate;

    /**
     * 结束时间
     */
    private String endDate;

    /**
     * 查询类型（1为消费积分，2为退货，3为撤单）
     */
    @NotNull(message = "查询类型不能为空，查询类型（1为消费积分，2为退货，3为撤单）")
    private int queryType;

    private Page page;

    /**
     * 获取互生号
     *
     * @return entResNo 互生号
     */
    public String getEntResNo() {
        return entResNo;
    }

    /**
     * 设置互生号
     *
     * @param entResNo 互生号
     */
    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    /**
     * @return perResNo 消费者互生号
     */
    public String getPerResNo() {
        return perResNo;
    }

    /**
     * @param perResNo 消费者互生号
     *
     */
    public void setPerResNo(String perResNo) {
        this.perResNo = perResNo;
    }

    /**
     * @return queryType 交易类型
     */
    public int getQueryType() {
        return queryType;
    }

    /**
     * @param queryType 交易类型
     */
    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }

    /**
     * @return startDate 开始时间
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate 开始时间
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return endDate 结束时间
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate 结束时间
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
