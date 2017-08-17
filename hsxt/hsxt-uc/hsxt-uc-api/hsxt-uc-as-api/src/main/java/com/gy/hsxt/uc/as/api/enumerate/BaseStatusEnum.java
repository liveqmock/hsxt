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

package com.gy.hsxt.uc.as.api.enumerate;

/**
 * 
 * @Package: com.gy.hsxt.uc.consumer.enumerate
 * @ClassName: FixRedisKeyPrefix
 * @Description: Redis缓存中的key前缀枚举类
 * 
 * @author: tianxh
 * @date: 2015-10-23 下午12:54:56
 * @version V1.0
 */
public enum BaseStatusEnum {
    INACTIVE(1), ACTIVE(2), SLEEP(3), SUBSIDE(4);
	Integer type;

	BaseStatusEnum(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}
}
