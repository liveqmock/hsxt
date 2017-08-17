/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.mapper;

import com.gy.hsxt.ao.bean.HsbToCash;

/**
 * 互生币转货币mapper dao映射类
 * 
 * @Package: com.gy.hsxt.ao.mapper
 * @ClassName: HsbToCashMapper
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-26 下午8:28:15
 * @version V3.0.0
 */
public interface HsbToCashMapper {

	/**
	 * 新增互生币转货币记录
	 * 
	 * @param hsbToCash
	 *            互生币转货币信息
	 * @return 成功记录数
	 */
	public int insertHsbToCash(HsbToCash hsbToCash);

	/**
	 * 查询互生币转货币信息
	 * 
	 * @param transNo
	 *            交易流水号
	 * @return 互生币转货币信息
	 */
	public HsbToCash findHsbToCash(String transNo);
}
