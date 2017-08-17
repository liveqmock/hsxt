/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.common.constant;

/**
 * 
 * @Package: com.gy.hsxt.common.constant
 * @ClassName: CustType
 * @Description: 统一客户类型定义 1 持卡人;2 成员企业;3 托管企业;4 服务公司;5 管理公司;6 地区平台;7 总平台;8 非持卡人;
 *               9 非互生格式化企业 11：操作员 21：POS机；22：积分刷卡器；23：消费刷卡器；24：平板；25：云台
 * 
 * @author: yangjianguo
 * @date: 2015-9-1 下午2:54:25
 * @version V1.0
 */
public enum CustType {

	/** 持卡人 **/
	PERSON(1),
	/** 成员企业 **/
	MEMBER_ENT(2),
	/** 托管企业 **/
	TRUSTEESHIP_ENT(3),
	/** 服务公司 **/
	SERVICE_CORP(4),
	/** 管理公司 **/
	MANAGE_CORP(5),
	/** 地区平台 **/
	AREA_PLAT(6),
	/** 总平台 **/
	CENTER_PLAT(7),
	/** 其他地区平台 **/
	OTHER_AREA_PLAT(8),
	/** 操作员 **/
	OPERATOR(11),

	/** POS机 **/
	POS_DEVICE(21),
	/** 积分刷卡器 **/
	POINT_MCR_DEVICE(22),
	/** 消费刷卡器 **/
	CONSUME_MCR_DEVICE(23),
	/** 平板 **/
	TABLET_DEVICE(24),
	/** 云台 **/
	CLOUD_PLAT_DEVICE(25),

	/** 非持卡人 **/
	NOT_HS_PERSON(51),
	/** 非互生格式化企业 **/
	NOT_HS_ENT(52);

	private int code;

	CustType(int code)
	{
		this.code = code;
	}

	public int getCode()
	{
		return code;
	}

	/**
	 * 校验客户类型
	 *
	 * @param code 类型代码
	 * @return boolean
	 */
	public static boolean checkType(int code) {
		for (CustType custType : CustType.values()) {
			if (custType.getCode() == code) {
				return true;
			}
		}
		return false;
	}
}
