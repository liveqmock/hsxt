package com.gy.hsxt.es.distribution.bean;

import com.gy.hsxt.es.common.EsRedisUtil;

/**
 * Simple to Introduction
 * 
 * @category Simple to Introduction
 * @projectName hsxt-ps-service
 * @package com.gy.hsxt.ps.distribution.bean.EnterpriseItems.java
 * @className PointItems
 * @description 一句话描述该类的功能
 * @author liuchao
 * @createDate 2015-8-7 下午2:59:17
 * @updateUser liuchao
 * @updateDate 2015-8-7 下午2:59:17
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
public class OrganizeItems {

	/** * 平台客户号 */
	protected String paasCustId = EsRedisUtil.getPlatInfo().getLeft();
	/** * 平台互生号 */
	protected String paasResNo = EsRedisUtil.getPlatInfo().getRight();

	/** * 管理公司客户号 */
	protected String manageCustId;
	/** * 管理公司互生号 */
	protected String manageResNo;

	/** * 服务公司客户号 */
	protected String serviceCustId;
	/** * 服务公司互生号 */
	protected String serviceResNo;

	/** * 托管公司客户号 */
	protected String trusteeCustId;
	/** * 托管公司互生号 */
	protected String trusteeResNo;

	/** 成员企业互生号 */
	protected String entResNo;
	/** 成员企业客户号 */
	protected String entCustId;

	/** * 管理公司积分税收分配比例 */
	// protected BigDecimal manageTaxRate;
	/** * 服务公司积分税收分配比例 */
	// protected BigDecimal serviceTaxRate;
	/** * 托管企业积分税收分配比例 */
	// protected BigDecimal trusteeTaxRate;

	/**
	 * 初始化管理公司、服务公司、托管企业互生号、客户号
	 * 
	 * @param perResNo
	 */
	protected void initHsRes(String perResNo) {
		// 管理公司
		this.manageResNo = perResNo.substring(0, 2) + "000000000";
		// 服务公司
		this.serviceResNo = perResNo.substring(0, 5) + "000000";
		// 托管企业
		this.trusteeResNo = perResNo.substring(0, 7) + "0000";
		// 成员企业
		this.entResNo = perResNo.substring(0, 5) + "00" + perResNo.substring(7, 11);

		this.manageCustId = this.manageResNo;
		this.serviceCustId = this.serviceResNo;
		this.trusteeCustId = this.trusteeResNo;
		this.entCustId = this.entResNo;

		// trusteeTaxRate=new BigDecimal(0.1);
		// serviceTaxRate=new BigDecimal(0.1);
		// manageTaxRate=new BigDecimal(0.1);
	}
	
}
