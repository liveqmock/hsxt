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
import com.gy.hsxt.tc.api.ITcGpChService;
import com.gy.hsxt.tc.bean.GpChDoubt;
import com.gy.hsxt.tc.bean.GpChImbalance;
import com.gy.hsxt.tc.bean.GpChSummary;
import com.gy.hsxt.tc.mapper.GpChDoubtMapper;
import com.gy.hsxt.tc.mapper.GpChImbalanceMapper;
import com.gy.hsxt.tc.mapper.GpChSummaryMapper;

/**
 * 支付系统（GP）与上海银联（CH）对账接口
 * 
 * @Package: com.gy.hsxt.tc.service
 * @ClassName: TcGpChService
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-18 下午6:00:11
 * @version V1.0
 */
@Service
public class TcGpChService extends BaseService implements ITcGpChService {
	@Autowired
	private GpChSummaryMapper summaryMapper;
	@Autowired
	private GpChImbalanceMapper imbalanceMapper;
	@Autowired
	private GpChDoubtMapper doubtMapper;

	/**
	 * 查询支付系统GP 与银联CH 对账汇总记录
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
	 * @see com.gy.hsxt.tc.api.ITcGpChService#querySummary(java.lang.String,
	 *      java.lang.String, java.lang.Integer, com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<GpChSummary> querySummary(String beginDate, String endDate,
			Integer tcResult, Page page) throws HsException {
		validEmptyOrNull(page);
		validDateParam(beginDate, endDate);
		int totalSize = summaryMapper.countTotalSize(beginDate, endDate,
				tcResult);
		List<GpChSummary> list = summaryMapper.pagination(beginDate, endDate,
				tcResult, page);
		return new PageData<GpChSummary>(totalSize, list);
	}

	/**
	 * 查询支付系统GP 与银联CH 对账不平衡记录
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
	 * @see com.gy.hsxt.tc.api.ITcGpChService#queryImbalance(java.lang.String,
	 *      java.lang.String, java.lang.Integer, com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<GpChImbalance> queryImbalance(String beginDate,
			String endDate, Integer imbalanceType, Page page)
			throws HsException {
		validEmptyOrNull(page);
		validDateParam(beginDate, endDate);
		int totalSize = imbalanceMapper.countTotalSize(beginDate, endDate,
				imbalanceType);
		List<GpChImbalance> list = imbalanceMapper.pagination(beginDate,
				endDate, imbalanceType, page);
		return new PageData<GpChImbalance>(totalSize, list);
	}

	/**
	 * 查询支付系统GP 与银联CH 对账存疑记录
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
	 * @see com.gy.hsxt.tc.api.ITcGpChService#queryDoubt(java.lang.String,
	 *      java.lang.String, java.lang.Integer, com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<GpChDoubt> queryDoubt(String beginDate, String endDate,
			Integer imbalanceType, Page page) throws HsException {
		validEmptyOrNull(page);
		validDateParam(beginDate, endDate);
		int totalSize = doubtMapper.countTotalSize(beginDate, endDate,
				imbalanceType);
		List<GpChDoubt> list = doubtMapper.pagination(beginDate, endDate,
				imbalanceType, page);
		return new PageData<GpChDoubt>(totalSize, list);
	}

}
