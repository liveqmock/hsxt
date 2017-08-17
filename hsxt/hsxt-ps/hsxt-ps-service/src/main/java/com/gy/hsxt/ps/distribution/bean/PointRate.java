package com.gy.hsxt.ps.distribution.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import com.gy.hsxt.ps.common.PropertyConfigurer;

/**
 * Simple to Introduction
 * 
 * @category Simple to Introduction
 * @projectName hsxt-ps-service
 * @package com.gy.hsxt.ps.distribution.bean.EnterPriseRate.java
 * @className EnterpriseRate
 * @description 分配比例表
 * @author liuchao
 * @createDate 2015-8-7 下午3:09:27
 * @updateUser liuchao
 * @updateDate 2015-8-7 下午3:09:27
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
public class PointRate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8007292816161395361L;

	/** * 消费者积分分配比例 */
	public static final BigDecimal perRate;

	/** * 托管企业积分分配比例 */
	public static final BigDecimal trusteeRate;

	/** * 服务公司积分分配比例 */
	public static final BigDecimal serviceRate;

	/** * 管理公司积分分配比例 */
	public static final BigDecimal manageRate;
	
	/** * 托管企业税收比例 */
	public static final BigDecimal trusteeTaxRate;

	/** * 服务公司税收比例 */
	public static final BigDecimal serviceTaxRate;

	/** * 管理公司税收比例 */ 
	public static final BigDecimal manageTaxRate;

	/** * 地区平台积分分配比例 */
	public static final BigDecimal paasRate;
	
	/** * 地区平台积分税收分配比例 */
	public static final BigDecimal paasTaxRate;
	
	/** 托管企业/成员企业日终商业服务费暂存比例 */
	public static final BigDecimal trusteeServiceRate;
	
	/** 服务公司每月所得商业服务费比例**/
	public static final BigDecimal serviceBusinessRate;

	/** * 运营成本 */
	public static final BigDecimal paasOpexRate;

	/** * 互生慈善救助基金 */
	public static final BigDecimal paasSalvageRate;

	/** 地区平台(积分企业增值基金) */
	public static final BigDecimal paasInsuranceRate;

	/** * 积分企业增值基金 */
	public static final BigDecimal paasIncrementRate;

	/** * 社会应急储备基金 */
	public static final BigDecimal paasReserveRate;

	/** * 消费者意外保障 */
	public static final BigDecimal paasAccidentRate;
	
	static {
		perRate = new BigDecimal(PropertyConfigurer.getProperty("ps.customerRate"));
		trusteeRate = new BigDecimal(PropertyConfigurer.getProperty("ps.trusteeRate"));
		serviceRate = new BigDecimal(PropertyConfigurer.getProperty("ps.serviceRate"));
		manageRate = new BigDecimal(PropertyConfigurer.getProperty("ps.manageRate"));
		
		trusteeTaxRate = new BigDecimal(PropertyConfigurer.getProperty("ps.trusteeTaxRate"));
		serviceTaxRate = new BigDecimal(PropertyConfigurer.getProperty("ps.serviceTaxRate"));
		manageTaxRate = new BigDecimal(PropertyConfigurer.getProperty("ps.manageTaxRate"));
		
		paasRate = new BigDecimal(PropertyConfigurer.getProperty("ps.paasRate"));
		paasTaxRate = new BigDecimal(PropertyConfigurer.getProperty("ps.paasTaxRate"));
		paasOpexRate = new BigDecimal(PropertyConfigurer.getProperty("ps.paasOpexRate"));
		paasSalvageRate = new BigDecimal(PropertyConfigurer.getProperty("ps.paasSalvageRate"));
		paasInsuranceRate = new BigDecimal(PropertyConfigurer.getProperty("ps.paasInsuranceRate"));
		paasIncrementRate = new BigDecimal(PropertyConfigurer.getProperty("ps.paasIncrementRate"));
		paasReserveRate = new BigDecimal(PropertyConfigurer.getProperty("ps.paasReserveRate"));
		paasAccidentRate = new BigDecimal(PropertyConfigurer.getProperty("ps.paasAccidentRate"));
		
		trusteeServiceRate = new BigDecimal(PropertyConfigurer.getProperty("ps.trusteeServiceRate"));
		serviceBusinessRate = new BigDecimal(PropertyConfigurer.getProperty("ps.serviceBusinessRate"));

	}

}
