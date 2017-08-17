/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.tc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.tc.bean.BsAcImbalance;

public interface BsAcImbalanceMapper {

	/**
	 * 查询总数
	 * 
	 * @param beginDate
	 *            对账开始日期 格式YYYY-MM-DD
	 * @param endDate
	 *            对账结束日期 格式YYYY-MM-DD
	 * @param imbalanceType
	 *            对账结果： 0：长款 1：短款 2：要素不一致
	 * @return
	 */
	int countTotalSize(@Param("beginDate") String beginDate,
			@Param("endDate") String endDate,
			@Param("imbalanceType") Integer imbalanceType);

	/**
	 * 分页查询
	 * 
	 * @param beginDate
	 *            对账开始日期 格式YYYY-MM-DD
	 * @param endDate
	 *            对账结束日期 格式YYYY-MM-DD
	 * @param imbalanceType
	 *            对账结果： 0：长款 1：短款 2：要素不一致
	 * @param page
	 *            分页查询参数
	 * @return
	 */
	List<BsAcImbalance> pagination(@Param("beginDate") String beginDate,
			@Param("endDate") String endDate,
			@Param("imbalanceType") Integer imbalanceType,
			@Param("page") Page page);

}