/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.tc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.tc.bean.TcJob;

public interface TcJobMapper {

	/**
	 * 分页 查询对账任务进度 记录数
	 * 
	 * @param beginDate
	 *            对账开始日期 格式YYYY-MM-DD
	 * @param endDate
	 *            对账结束日期 格式YYYY-MM-DD 备注：开始日期和结束日期必须要填写一个
	 * @param tcState
	 *            处理状态 0：成功 1：失败 2：处理中
	 * @param tcSys
	 *            对账间业务简称 GP-CH:支付系统与上海银联 BS-GP-PAY:业务服务与支付系统支付业务
	 *            BS-AC:业务服务与账务系统 PS-AC:消费积分与账务系统
	 * @return
	 */
	int countTotalSize(@Param("beginDate") String beginDate,
			@Param("endDate") String endDate,
			@Param("tcState") Integer tcState, @Param("tcSys") String tcSys);

	/**
	 * 分页 查询对账任务进度
	 * 
	 * @param beginDate
	 *            对账开始日期 格式YYYY-MM-DD
	 * @param endDate
	 *            对账结束日期 格式YYYY-MM-DD 备注：开始日期和结束日期必须要填写一个
	 * @param tcState
	 *            处理状态 0：成功 1：失败 2：处理中
	 * @param tcSys
	 *            对账间业务简称 GP-CH:支付系统与上海银联 BS-GP-PAY:业务服务与支付系统支付业务
	 *            BS-AC:业务服务与账务系统 PS-AC:消费积分与账务系统
	 * @param page
	 *            分页查询参数
	 * @return
	 */
	List<TcJob> pagination(@Param("beginDate") String beginDate,
			@Param("endDate") String endDate,
			@Param("tcState") Integer tcState, @Param("tcSys") String tcSys,
			@Param("page") Page page);
}