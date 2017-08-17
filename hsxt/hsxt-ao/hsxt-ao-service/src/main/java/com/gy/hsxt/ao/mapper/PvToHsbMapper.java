/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.mapper;

import com.gy.hsxt.ao.bean.PvToHsb;

/**
 * 积分转互生币mapper dao映射类
 * 
 * @Package: com.gy.hsxt.ao.mapper
 * @ClassName: PvToHsbMapper
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-26 下午6:32:33
 * @version V3.0.0
 */
public interface PvToHsbMapper {

	/**
	 * 插入积分转互生币记录
	 * 
	 * @param pvToHsb
	 *            积分转互生币信息
	 */
	public int insertPvToHsb(PvToHsb pvToHsb);

	/**
	 * 查询积分转互生币
	 * 
	 * @param transNo
	 *            交易流水号
	 * @return 积分转互生币
	 */
	public PvToHsb findPvToHsb(String transNo);
}
