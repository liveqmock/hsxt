/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.as.bean.enumerate;

import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;

/**
 * 
 * 
 * @Package: com.gy.hsxt.uc.bs.enumerate
 * @ClassName: CustomerType
 * @Description: 企业客户类型枚举类
 * 
 * @author: tianxh
 * @date: 2015-11-19 下午3:04:26
 * @version V1.0
 */
public enum CustomerType {

	// 企业客户类型1 持卡人;2 成员企业;3 托管企业;4 服务公司;5 管理公司;6 地区平台;7 总平台; 8 其它地区平台
	// 11：操作员
	// 21：POS机；22：积分刷卡器；23：消费刷卡器；24：平板；25：云台
	// 51 非持卡人; 52 非互生格式化企业
	CARD_HOLDER("1"), MEMBERS_ENT("2"), HOSTING_ENT("3"), SERVICE_COMPANY("4"), MANAGE_COMPANY(
			"5"), REGION_PLATFORM("6"), CHIEF_PLATFORM("7"), OTHER_REGION_PLATFORM(
			"8"), OPERATOR("11"), POS("21"), INTEGRAL_CARD_READER("22"), CONSUME_CARD_READER(
			"23"), PAD("24"), CLOUD_DECK("25"), NOCARD_HOLDER("51"), NOT_INTERGROWTH_ENT(
			"52");
	String type;

	CustomerType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	/**
	 * 客户号类型枚举到用户类型枚举转换
	 * @param type	客户号类型值
	 * @return	用户类型枚举值
	 */
	public static String getUserType(String type) {
		if (CustomerType.NOCARD_HOLDER.getType().equals(type)) {
			return UserTypeEnum.NO_CARDER.getType();
		} else if (CustomerType.CARD_HOLDER.getType().equals(type)) {
			return UserTypeEnum.CARDER.getType();
		} else if (CustomerType.OPERATOR.getType().equals(type)) {
			return UserTypeEnum.OPERATOR.getType();
		} else if (CustomerType.MEMBERS_ENT.getType().equals(type)
				|| CustomerType.HOSTING_ENT.getType().equals(type)
				|| CustomerType.SERVICE_COMPANY.getType().equals(type)
				|| CustomerType.MANAGE_COMPANY.getType().equals(type)
				|| CustomerType.CHIEF_PLATFORM.getType().equals(type)
				|| CustomerType.OTHER_REGION_PLATFORM.getType().equals(type)
				) {
			return UserTypeEnum.ENT.getType();
		}else{
			return ""; 
		}

	}
}
