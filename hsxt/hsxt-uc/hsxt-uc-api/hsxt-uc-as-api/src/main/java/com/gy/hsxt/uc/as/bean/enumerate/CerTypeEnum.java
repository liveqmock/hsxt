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

/**
 * 
 * 
 * @Package: com.gy.hsxt.uc.bs.enumerate  
 * @ClassName: CerTypeEnum 
 * @Description: 证件类型枚举类
 *
 * @author: tianxh
 * @date: 2015-11-19 下午3:04:26 
 * @version V1.0
 */
public enum CerTypeEnum {
	IDCARD(1), PASSPORT(2),  BUSILICENCE(3);
	Integer type;

	CerTypeEnum(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}
}
