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
import com.gy.hsxt.tc.api.ITcPsAcService;
import com.gy.hsxt.tc.bean.PsAcImbalance;
import com.gy.hsxt.tc.bean.PsAcSummary;
import com.gy.hsxt.tc.mapper.PsAcImbalanceMapper;
import com.gy.hsxt.tc.mapper.PsAcSummaryMapper;

/**
 * 消费积分系统（PS）与账务系统（AC）对账接口实现类
 * 
 * @Package: com.gy.hsxt.tc.service
 * @ClassName: TcPsAcService
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-18 下午7:51:05
 * @version V1.0
 */
@Service
public class TcPsAcService extends BaseService implements ITcPsAcService {
	@Autowired
	private PsAcSummaryMapper summaryMapper;
	@Autowired
	private PsAcImbalanceMapper imbalanceMapper;

	/**
	 * 查询消费积分系统PS 与账务系统AC 对账汇总记录
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
	 * @throws HsException
	 * @see com.gy.hsxt.tc.api.ITcPsAcService#querySummary(java.lang.String,
	 *      java.lang.String, java.lang.Integer, com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<PsAcSummary> querySummary(String beginDate, String endDate,
			Integer tcResult, Page page) throws HsException {
		// 数据验证
		validEmptyOrNull(page);
		validDateParam(beginDate, endDate);

		int totalSize = summaryMapper.countTotalSize(beginDate, endDate,
				tcResult);
		List<PsAcSummary> list = summaryMapper.pagination(beginDate, endDate,
				tcResult, page);
		return new PageData<PsAcSummary>(totalSize, list);
	}

	/**
	 * 查询消费积分系统PS 与账务系统AC 对账不平衡记录
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
	 * @throws HsException
	 * @see com.gy.hsxt.tc.api.ITcPsAcService#queryImbalance(java.lang.String,
	 *      java.lang.String, java.lang.Integer, com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<PsAcImbalance> queryImbalance(String beginDate,
			String endDate, Integer imbalanceType, Page page)
			throws HsException {
		// 数据验证
		validEmptyOrNull(page);
		validDateParam(beginDate, endDate);

		int totalSize = imbalanceMapper.countTotalSize(beginDate, endDate,
				imbalanceType);
		List<PsAcImbalance> list = imbalanceMapper.pagination(beginDate,
				endDate, imbalanceType, page);
		return new PageData<PsAcImbalance>(totalSize, list);
	}

}
