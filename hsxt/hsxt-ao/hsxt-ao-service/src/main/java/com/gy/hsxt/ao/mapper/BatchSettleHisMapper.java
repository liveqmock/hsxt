/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ao.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * 终端批结算历史表 mapper dao映射类
 * 
 * @Package: com.gy.hsxt.ao.mapper
 * @ClassName: BatchSettleHisMapper
 * @Description: TODO
 * 
 * @author: liyh
 * @date: 2015-12-10 下午2:17:04
 * @version V1.0
 */
public interface BatchSettleHisMapper {

	/**
	 * 根据 批总账编号 统计是否有数据存在
	 * 
	 * @param batchCheckId
	 *            批总账编号
	 * @return
	 */
	public Integer findBatchSettleHisByBatchCheckId(
			@Param("batchCheckId") String batchCheckId);

	/**
	 * 插入前几个月的终端批结算历史数据
	 * 
	 * @param month
	 *            前几个月
	 * @return
	 */
	public int insertBatchSettleHisLastMath(@Param("month") Integer month);

}
