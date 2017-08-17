/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.bs.enumerate;


/**
 * 设备类型
 * 
 * @Package: com.gy.hsxt.uc.bs.enumerate
 * @ClassName: DeviceTypeEumn
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-10-19 下午4:46:50
 * @version V1.0
 */
public enum BsDeviceTypeEumn {
	POS(1), CARD_READER(2), PAD(3);
	int type;

	BsDeviceTypeEumn(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static BsDeviceTypeEumn generateDeviceTypeEumn(int value) {
		if (value == 1)
			return BsDeviceTypeEumn.POS;
		if (value == 2)
			return BsDeviceTypeEumn.CARD_READER;
		if (value == 3)
			return BsDeviceTypeEumn.PAD;

		return BsDeviceTypeEumn.PAD;
	}
}
