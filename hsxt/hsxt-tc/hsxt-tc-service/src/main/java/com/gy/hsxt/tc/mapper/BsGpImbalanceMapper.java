/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.tc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.tc.bean.BsGpImbalance;

public interface BsGpImbalanceMapper {

	int countTotalSize(@Param("beginDate") String beginDate,
			@Param("endDate") String endDate,
			@Param("imbalanceType") Integer imbalanceType);

	List<BsGpImbalance> pagination(@Param("beginDate") String beginDate,
			@Param("endDate") String endDate,
			@Param("imbalanceType") Integer imbalanceType,
			@Param("page") Page page);
}