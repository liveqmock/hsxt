package com.gy.hsxt.common.constant;

/**
 * Simple to Introduction
 * 
 * @category Simple to Introduction
 * @projectName hsxt-ps-service
 * @package com.gy.hsxt.ps.common.AccType.java
 * @className AccountType
 * @description 账务类型
 * @author liuchao
 * @createDate 2015-8-5 下午2:58:55
 * @updateUser liuchao
 * @updateDate 2015-8-5 下午2:58:55
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
public enum AccountType {

	/** 积分账户类型 */
	ACC_TYPE_POINT10110("10110"),

	/** 待分配积分 */
	ACC_TYPE_POINT10111("10111"),

	/** 非持卡人积分收入 */
	ACC_TYPE_POINT10210("10210"),

	/** 积分分配结余 */
	ACC_TYPE_POINT10220("10220"),

	/** 积分沉淀（暂定） */
	ACC_TYPE_POINT10230("10230"),
	
	/** 平台分配收入 **/
	ACC_TYPE_POINT10300("10300"),

	/** 平台税收 */
	ACC_TYPE_POINT10310("10310"),

	/** 消费者意外保障 */
	ACC_TYPE_POINT10320("10320"),

	/** 社会安全保障基金 */
	ACC_TYPE_POINT10330("10330"),

	/** 积分企业增值基金 */
	ACC_TYPE_POINT10340("10340"),

	/** 运营成本 */
	ACC_TYPE_POINT10350("10350"),

	/** 互生慈善救助基金 */
	ACC_TYPE_POINT10360("10360"),

	/** 社会应急储备基金 */
	ACC_TYPE_POINT10370("10370"),
	
	/** 异地卡本地消费收取积分 */
	ACC_TYPE_POINT10380("10380"),
	
	/** 本地卡异地消费支付积分 */
	ACC_TYPE_POINT10390("10390"),

	/** 积分投资 */
	ACC_TYPE_POINT10410("10410"),

	/** 积分税收 */
	ACC_TYPE_POINT10510("10510"),

	/** 流通币 */
	ACC_TYPE_POINT20110("20110"),

	/** 待清算流通币类型 */
	ACC_TYPE_POINT20111("20111"),

	/** 预留流通币 */
	ACC_TYPE_POINT20112("20112"),

	/** 定向消费币 */
	ACC_TYPE_POINT20120("20120"),

	/** 预留定向币 */
	ACC_TYPE_POINT20122("20122"),

	/** 慈善救助基金 */
	ACC_TYPE_POINT20130("20130"),

	/** 互生币分配结余 */
	ACC_TYPE_POINT20210("20210"),

	/** 互生币沉淀 */
	ACC_TYPE_POINT20220("20220"),

	/** 消费者医疗基金 */
	ACC_TYPE_POINT20310("20310"),

	/** 货币转换费 */
	ACC_TYPE_POINT20410("20410"),
	
	/** 电商外卖服务费 */
	ACC_TYPE_POINT20450("20450"),
	
	/** 制卡服务费 */
	ACC_TYPE_POINT20460("20460"),
	
	/** 异地卡本地消费支付互生币 */
	ACC_TYPE_POINT20470("20470"),
	
	/** 本地卡异地消费收取互生币 */
	ACC_TYPE_POINT20480("20480"),
	
	/** 平台扣款 */
	ACC_TYPE_POINT20490("20490"),

	/** 代兑互生币服务费1% */
	ACC_TYPE_POINT20510("20510"),
	
	/** 资源费收入 */
	ACC_TYPE_POINT20520("20520"),

	/** 互生币税收账户 */
	ACC_TYPE_POINT20610("20610"),

	/** 商业服务费 */
	ACC_TYPE_POINT20420("20420"),

	/** 待清算商业服务费 */
	ACC_TYPE_POINT20421("20421"),

	/** 销售工具收入 */
	ACC_TYPE_POINT20430("20430"),

	/** 年费收入 */
	ACC_TYPE_POINT20440("20440"),

	/** 货币类型 */
	ACC_TYPE_POINT30110("30110"),

	/** 积分预付款类型 */
	ACC_TYPE_POINT30210("30210"),

	/** 预留积分预付款类型 */
	ACC_TYPE_POINT30212("30212"),

	/** 货币税收 */
	ACC_TYPE_POINT30310("30310"),

	/** 银行转账手续费 */
	ACC_TYPE_POINT30410("30410"),

	/** 资源费收入 */
	ACC_TYPE_POINT30420("30420"),

	/** 平台网银收款(本币) **/
	ACC_TYPE_POINT50110("50110"),
	/** 平台临账收款(本币) **/
	ACC_TYPE_POINT50210("50210"),
	/** 平台网银转出(本币) **/
	ACC_TYPE_POINT50120("50120"),
	/** 互营网银收款(本币) **/
	ACC_TYPE_POINT50130("50130"),
	
	;
	private String code;

	AccountType(String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}

}
