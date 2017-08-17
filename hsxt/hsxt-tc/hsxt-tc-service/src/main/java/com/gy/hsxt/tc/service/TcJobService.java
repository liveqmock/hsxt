/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.tc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tc.api.ITcJobService;
import com.gy.hsxt.tc.bean.TcJob;
import com.gy.hsxt.tc.mapper.TcJobMapper;

/**
 * 对账任务执行进度接口实现类
 * 
 * @Package: com.gy.hsxt.tc.service
 * @ClassName: TcJobService
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-18 下午7:59:39
 * @version V1.0
 */
@Service
public class TcJobService extends BaseService implements ITcJobService {
	@Autowired
	private TcJobMapper jobMapper;

	/**
	 * 查询对账任务进度
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
	 * @throws HsException
	 * @see com.gy.hsxt.tc.api.ITcJobService#queryProgress(java.lang.String,
	 *      java.lang.String, java.lang.Integer, java.lang.String,
	 *      com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<TcJob> queryProgress(String beginDate, String endDate,
			Integer tcState, String tcSys, Page page) throws HsException {
		// 数据验证
		validEmptyOrNull(page);
		validDateParam(beginDate, endDate);

		int totalSize = jobMapper.countTotalSize(beginDate, endDate, tcState,
				tcSys);
		List<TcJob> list = jobMapper.pagination(beginDate, endDate, tcState,
				tcSys, page);
		return new PageData<TcJob>(totalSize, list);
	}

}
