/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.tc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.tc.bean.BsGpSummary;

public interface BsGpSummaryMapper {

	/**
	 * 查询 业务系统BS 与支付系统GP 对账汇总记录数
	 * 
	 * @param beginDate
	 *            对账开始日期 格式YYYY-MM-DD
	 * @param endDate
	 *            对账结束日期 格式YYYY-MM-DD
	 * @param tcResult
	 * @return
	 */
	int countTotalSize(@Param("beginDate") String beginDate,
			@Param("endDate") String endDate,
			@Param("tcResult") Integer tcResult);

	/**
	 * 分页查询 业务系统BS 与支付系统GP 对账汇总记录
	 * 
	 * @param beginDate
	 *            对账开始日期 格式YYYY-MM-DD
	 * @param endDate
	 *            对账结束日期 格式YYYY-MM-DD
	 * @param tcResult
	 *            对账结果：1平衡、2不平衡
	 * @param page
	 *            分页查询参数
	 * @return
	 */
	List<BsGpSummary> pagination(@Param("beginDate") String beginDate,
			@Param("endDate") String endDate,
			@Param("tcResult") Integer tcResult, @Param("page") Page page);
}