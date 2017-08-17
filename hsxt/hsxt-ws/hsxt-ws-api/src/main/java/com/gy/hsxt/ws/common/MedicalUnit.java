/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.ws.common;

/**
 * 医疗常见计量单位
 * 
 * @Package: com.gy.hsxt.ws.common
 * @ClassName: MedicalUnit
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-12-14 上午10:49:45
 * @version V1.0
 */
public enum MedicalUnit {
	BOX(1, "盒"),

	BOTTLE(2, "瓶"),

	BAG(3, "袋"),

	BED(4, "床"),

	CASE(5, "例"),

	PIECE(6, "片"),

	A(7, "个"),

	SET(8, "套"),

	STRIPE(9, "条"),

	G(10, "克"),

	KG(11, "千克") ;

	int unit;

	String desc;

	private MedicalUnit(int unit, String desc) {
		this.unit = unit;
		this.desc = desc;
	}

	public int getUnit() {
		return unit;
	}

	public String getDesc() {
		return desc;
	}
}
