package com.gy.hsxt.es.distribution.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import com.gy.hsxt.es.common.PropertyConfigurer;

/**
 * Simple to Introduction
 * 
 * @category Simple to Introduction
 * @projectName hsxt-ps-service
 * @package com.gy.hsxt.es.distribution.bean.EnterPriseRate.java
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
	public static BigDecimal perRate;

	/** * 托管企业积分分配比例 */
	public static BigDecimal trusteeRate;

	/** * 服务公司积分分配比例 */
	public static BigDecimal serviceRate;

	/** * 管理公司积分分配比例 */
	public static BigDecimal manageRate;
	
	/** * 托管企业税收比例 */
	public static BigDecimal trusteeTaxRate;

	/** * 服务公司税收比例 */
	public static BigDecimal serviceTaxRate;

	/** * 管理公司税收比例 */ 
	public static BigDecimal manageTaxRate;

	/** * 地区平台积分税收分配比例 */
	public static BigDecimal paasRate;
	
	/** * 地区平台积分税收分配比例 */
	public static BigDecimal paasTaxRate;
	
	/** 托管企业/成员企业日终商业服务费暂存比例 */
	public static BigDecimal trusteeServiceRate;
	
	/** 服务公司每月所得商业服务费比例**/
	public static BigDecimal serviceBusinessRate;

	/** * 运营成本 */
	public static BigDecimal paasOpexRate;

	/** * 互生慈善救助基金 */
	public static BigDecimal paasSalvageRate;

	/** 地区平台(积分企业增值基金) */
	public static BigDecimal paasInsuranceRate;

	/** * 积分企业增值基金 */
	public static BigDecimal paasIncrementRate;

	/** * 社会应急储备基金 */
	public static BigDecimal paasReserveRate;

	/** * 消费者意外保障 */
	public static BigDecimal paasAccidentRate;

	static {
		perRate = new BigDecimal(PropertyConfigurer.getProperty("es.customerRate"));
		trusteeRate = new BigDecimal(PropertyConfigurer.getProperty("es.trusteeRate"));
		serviceRate = new BigDecimal(PropertyConfigurer.getProperty("es.serviceRate"));
		manageRate = new BigDecimal(PropertyConfigurer.getProperty("es.manageRate"));
		
		trusteeTaxRate = new BigDecimal(PropertyConfigurer.getProperty("es.trusteeTaxRate"));
		serviceTaxRate = new BigDecimal(PropertyConfigurer.getProperty("es.serviceTaxRate"));
		manageTaxRate = new BigDecimal(PropertyConfigurer.getProperty("es.manageTaxRate"));
		
		paasRate = new BigDecimal(PropertyConfigurer.getProperty("es.paasRate"));
		paasTaxRate = new BigDecimal(PropertyConfigurer.getProperty("es.paasTaxRate"));
		paasOpexRate = new BigDecimal(PropertyConfigurer.getProperty("es.paasOpexRate"));
		paasSalvageRate = new BigDecimal(PropertyConfigurer.getProperty("es.paasSalvageRate"));
		paasInsuranceRate = new BigDecimal(PropertyConfigurer.getProperty("es.paasInsuranceRate"));
		paasIncrementRate = new BigDecimal(PropertyConfigurer.getProperty("es.paasIncrementRate"));
		paasReserveRate = new BigDecimal(PropertyConfigurer.getProperty("es.paasReserveRate"));
		paasAccidentRate = new BigDecimal(PropertyConfigurer.getProperty("es.paasAccidentRate"));
		
		trusteeServiceRate = new BigDecimal(PropertyConfigurer.getProperty("es.trusteeServiceRate"));
		serviceBusinessRate = new BigDecimal(PropertyConfigurer.getProperty("es.serviceBusinessRate"));

	}

}
