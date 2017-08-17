/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.os.tcm.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tc.api.ITcBsAcService;
import com.gy.hsxt.tc.api.ITcBsGpService;
import com.gy.hsxt.tc.api.ITcGpChService;
import com.gy.hsxt.tc.api.ITcPsAcService;
import com.gy.hsxt.tc.bean.BsAcSummary;
import com.gy.hsxt.tc.bean.BsGpSummary;
import com.gy.hsxt.tc.bean.GpChSummary;
import com.gy.hsxt.tc.bean.PsAcSummary;

/**
 * 调账管理
 * 
 * @Package: com.gy.hsxt.access.web.os.tcm.AccountResultController
 * @ClassName: CheckBalanceController
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2016-03-20 下午4:51:05
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/accountResult")
public class AccountResultController {

	@Autowired
	ITcGpChService gpchService;
	@Autowired
	ITcBsGpService bsGpService;
	@Autowired
	ITcBsAcService bsAcService;
	@Autowired
	ITcPsAcService psAcService;

	@ResponseBody
	@RequestMapping(value = "/listPayAndBank", method = RequestMethod.POST)
	// 支付与银联对帐列表
	public HttpRespEnvelope listPayAndBank(
			@RequestParam(required = true) String beginDate,
			@RequestParam(required = true) String endDate,
			@RequestParam(required = false) Integer tcResult,
			@RequestParam(required = true) Integer pageSize,
			@RequestParam(required = true) Integer curPage) {
		Page page = new Page(pageSize, curPage);
		HttpRespEnvelope hre = new HttpRespEnvelope();
		try {
			PageData<GpChSummary> list = gpchService.querySummary(beginDate,
					endDate, tcResult, page);
			// 如果没有数据，使用测试用假数据
			if (list.getResult() == null || list.getResult().size() == 0) {
				GpChSummary s = new GpChSummary();
				s.setChHaveAmount(new BigDecimal(1));
				s.setChHaveNum(2L);
				s.setChTranAmount(new BigDecimal(3));
				s.setChTranNum(4L);
				s.setFlatAmount(new BigDecimal(5));
				s.setFlatNum(6L);
				s.setGpHaveAmount(new BigDecimal(7));
				s.setGpHaveNum(8L);
				s.setGpTranAmount(new BigDecimal(9));
				s.setGpTranNum(10L);
				s.setSumId(11L);
				s.setTcDate("2016-02-22");
				s.setTcResult(1);
				List<GpChSummary> result = new ArrayList<>();
				result.add(s);
				list.setResult(result);
				list.setCount(1);
			}
			hre.setCurPage(curPage);
			if (list != null) {
				hre.setData(list.getResult());
				hre.setTotalRows(list.getCount());
			}
			return hre;
		} catch (HsException hse) {
			hse.printStackTrace();
			hre.setRetCode(hse.getErrorCode());
			hre.setResultDesc(hse.getMessage());
			hre.setSuccess(false);
			return hre;
		} catch (Exception e) {
			e.printStackTrace();
			hre.setRetCode(RespCode.UNKNOWN.getCode());
			hre.setResultDesc("未知错误，请查看日志");
			hre.setSuccess(false);
			return hre;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/listPayAndBusiness", method = RequestMethod.POST)
	// 支付与业务对帐列表
	public HttpRespEnvelope listPayAndBusiness(
			@RequestParam(required = true) String beginDate,
			@RequestParam(required = true) String endDate,
			@RequestParam(required = true) Integer tcResult,
			@RequestParam(required = true) Integer pageSize,
			@RequestParam(required = true) Integer curPage) {
		Page page = new Page(pageSize, curPage);
		HttpRespEnvelope hre = new HttpRespEnvelope();
		try {
			PageData<BsGpSummary> list = bsGpService.querySummary(beginDate,
					endDate, tcResult, page);
			// 如果没有数据，使用测试用假数据
			if (list.getResult() == null || list.getResult().size() == 0) {
				BsGpSummary s = new BsGpSummary();
				s.setBsHaveAmount(new BigDecimal(11));
				s.setBsHaveNum(12L);
				s.setBsTranAmount(new BigDecimal(13));
				s.setBsTranNum(14L);
				s.setFlatAmount(new BigDecimal(15));
				s.setFlatNum(16L);
				s.setGpHaveAmount(new BigDecimal(17));
				s.setGpHaveNum(18L);
				s.setGpTranAmount(new BigDecimal(19));
				s.setGpTranNum(20L);
				s.setSumId(21L);
				s.setTcDate("2016-02-22");
				s.setTcResult(0);
				List<BsGpSummary> result = new ArrayList<>();
				result.add(s);
				list.setResult(result);
				list.setCount(1);
			}

			hre.setCurPage(curPage);
			if (list != null) {
				hre.setData(list.getResult());
				hre.setTotalRows(list.getCount());
			}
			return hre;
		} catch (HsException hse) {
			hse.printStackTrace();
			hre.setRetCode(hse.getErrorCode());
			hre.setResultDesc(hse.getMessage());
			hre.setSuccess(false);
			return hre;
		} catch (Exception e) {
			e.printStackTrace();
			hre.setRetCode(RespCode.UNKNOWN.getCode());
			hre.setResultDesc("未知错误，请查看日志");
			hre.setSuccess(false);
			return hre;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/listBusinessAndAc", method = RequestMethod.POST)
	// 业务与账务对帐列表
	public HttpRespEnvelope listBusinessAndAc(
			@RequestParam(required = true) String beginDate,
			@RequestParam(required = true) String endDate,
			@RequestParam(required = true) Integer tcResult,
			@RequestParam(required = true) Integer pageSize,
			@RequestParam(required = true) Integer curPage) {
		Page page = new Page(pageSize, curPage);
		HttpRespEnvelope hre = new HttpRespEnvelope();
		try {
			PageData<BsAcSummary> list = bsAcService.querySummary(beginDate,
					endDate, tcResult, page);

			// 如果没有数据，使用测试用假数据
			if (list.getResult() == null || list.getResult().size() == 0) {
				BsAcSummary s = new BsAcSummary();
				s.setBsHaveAmount(new BigDecimal(21));
				s.setBsHaveNum(22L);
				s.setBsTranAmount(new BigDecimal(23));
				s.setBsTranNum(24L);
				s.setFlatAmount(new BigDecimal(25));
				s.setFlatNum(26L);
				s.setAcHaveNum(28L);
				s.setAcTranAmount(new BigDecimal(29));
				s.setAcTranNum(20L);
				s.setAcHaveAmount(new BigDecimal(22));
				s.setSumId(21L);
				s.setTcDate("2016-02-23");
				s.setTcResult(1);
				List<BsAcSummary> result = new ArrayList<>();
				result.add(s);
				list.setResult(result);
				list.setCount(1);
			}

			hre.setCurPage(curPage);
			if (list != null) {
				hre.setData(list.getResult());
				hre.setTotalRows(list.getCount());
			}
			return hre;
		} catch (HsException hse) {
			hse.printStackTrace();
			hre.setRetCode(hse.getErrorCode());
			hre.setResultDesc(hse.getMessage());
			hre.setSuccess(false);
			return hre;
		} catch (Exception e) {
			e.printStackTrace();
			hre.setRetCode(RespCode.UNKNOWN.getCode());
			hre.setResultDesc("未知错误，请查看日志");
			hre.setSuccess(false);
			return hre;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/listPvAndAc", method = RequestMethod.POST)
	// 积分与账务对帐列表
	public HttpRespEnvelope listPvAndAc(
			@RequestParam(required = true) String beginDate,
			@RequestParam(required = true) String endDate,
			@RequestParam(required = true) Integer tcResult,
			@RequestParam(required = true) Integer pageSize,
			@RequestParam(required = true) Integer curPage) {
		Page page = new Page(pageSize, curPage);
		HttpRespEnvelope hre = new HttpRespEnvelope();
		try {
			PageData<PsAcSummary> list = psAcService.querySummary(beginDate,
					endDate, tcResult, page);
			// 如果没有数据，使用测试用假数据
			if (list.getResult() == null || list.getResult().size() == 0) {
				PsAcSummary s = new PsAcSummary();
				s.setAcHaveAmount(new BigDecimal(31));
				s.setAcHaveNum(32L);
				s.setAcTranAmount(new BigDecimal(33));
				s.setAcTranNum(34L);
				s.setPsHaveAmount(new BigDecimal(31));
				s.setPsHaveNum(32L);
				s.setPsTranAmount(new BigDecimal(33));
				s.setPsTranNum(34L);
				s.setFlatAmount(new BigDecimal(35));
				s.setFlatNum(36L);
				s.setAcHaveNum(8L);
				s.setAcTranAmount(new BigDecimal(39));
				s.setAcTranNum(30L);
				s.setSumId(31L);
				s.setTcDate("2016-02-22");
				s.setTcResult(1);
				List<PsAcSummary> result = new ArrayList<>();
				result.add(s);
				list.setResult(result);
				list.setCount(1);
			}
			hre.setCurPage(curPage);
			if (list != null) {
				hre.setData(list.getResult());
				hre.setTotalRows(list.getCount());
			}
			return hre;
		} catch (HsException hse) {
			hse.printStackTrace();
			hre.setRetCode(hse.getErrorCode());
			hre.setResultDesc(hse.getMessage());
			hre.setSuccess(false);
			return hre;
		} catch (Exception e) {
			e.printStackTrace();
			hre.setRetCode(RespCode.UNKNOWN.getCode());
			hre.setResultDesc("未知错误，请查看日志");
			hre.setSuccess(false);
			return hre;
		}
	}
}
