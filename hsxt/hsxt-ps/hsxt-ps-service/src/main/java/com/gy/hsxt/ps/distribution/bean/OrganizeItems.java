package com.gy.hsxt.ps.distribution.bean;

import com.gy.hsxt.ps.common.PsRedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liuchao
 * @version v3.0
 * @description 互生号解析
 * @createDate 2015-8-7 下午2:59:17
 * @updateUser liuchao
 * @updateDate 2015-8-7 下午2:59:17
 * @updateRemark 说明本次修改内容
 */
public class OrganizeItems {
    /**
     * 平台客户号
     */
    protected String paasCustId = PsRedisUtil.getPlatInfo().getLeft();
    /**
     * 平台互生号
     */
    protected String paasResNo = PsRedisUtil.getPlatInfo().getRight();

    /**
     * 管理公司客户号
     */
    protected String manageCustId ;
    /**
     * 管理公司互生号
     */
    protected String manageResNo;

    /**
     * 服务公司客户号
     */
    protected String serviceCustId;
    /**
     * 服务公司互生号
     */
    protected String serviceResNo;

    /**
     * 托管公司客户号
     */
    protected String trusteeCustId;
    /**
     * 托管公司互生号
     */
    protected String trusteeResNo;

    /**
     * 成员企业互生号
     */
    protected String entResNo;
    /**
     * 成员企业客户号
     */
    protected String entCustId;

    /** * 管理公司积分税收分配比例 */
    // protected BigDecimal manageTaxRate;
    /** * 服务公司积分税收分配比例 */
    // protected BigDecimal serviceTaxRate;
    /** * 托管企业积分税收分配比例 */
    // protected BigDecimal trusteeTaxRate;

    /**
     * 初始化管理公司、服务公司、托管企业互生号、成员企业
     *
     * @param perResNo
     */
    protected void initHsRes(String perResNo) {
        if (StringUtils.isNotBlank(perResNo)){
            // 管理公司
            this.manageResNo = perResNo.substring(0, 2) + "000000000";
            // 服务公司
            this.serviceResNo = perResNo.substring(0, 5) + "000000";
            // 托管企业
            this.trusteeResNo = perResNo.substring(0, 7) + "0000";
        }
        else {
            // 管理公司
            this.manageResNo =null;
            // 服务公司
            this.serviceResNo =null;
            // 托管企业
            this.trusteeResNo =null;
        }
    }

    protected void initHsResNo(String perResNo) {
        // 服务公司
        this.serviceResNo = perResNo.substring(0, 5) + "000000";

        this.serviceCustId = this.serviceResNo;

    }

}
