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

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.ac.api.IAccountSearchService;
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.tc.api.ITcCheckBalanceResultService;
import com.gy.hsxt.tc.api.ITcCheckBalanceService;
import com.gy.hsxt.tc.bean.TcCheckBalance;
import com.gy.hsxt.tc.bean.TcCheckBalanceParam;
import com.gy.hsxt.tc.bean.TcCheckBalanceResult;
import com.gy.hsxt.tc.enums.TcErrorCode;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolderAllInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;

/**
 * 调账管理
 * 
 * @Package: com.gy.hsxt.access.web.os.tcm.controller
 * @ClassName: CheckBalanceController
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2016-03-20 下午4:51:05
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/checkBalance")
public class CheckBalanceController {
	@Autowired
	ITcCheckBalanceService checkBalanceService;
	@Autowired
	ITcCheckBalanceResultService checkBalanceResultService;
	@Autowired
	IUCAsEntService entService;
	@Autowired
	IUCAsCardHolderService carderService;
	@Autowired
	IAccountSearchService accountSearchService;

	/**
	 * 根据调账ID查询调账审核结果列表 `
	 * 
	 * @param balanceId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getCheckBalanceResults", method = RequestMethod.POST)
	public HttpRespEnvelope getCheckBalanceResults(
			@RequestParam(required = true) String balanceId) {
		HttpRespEnvelope hre = new HttpRespEnvelope();
		try {
			List<TcCheckBalanceResult> list = checkBalanceResultService
					.getCheckBalanceResult(balanceId);
			hre.setCurPage(1);
			hre.setData(list);
			hre.setTotalRows(list.size());
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

	/**
	 * 添加调账申请的审批
	 * 
	 * @param result
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addCheckBalanceResult", method = RequestMethod.POST)
	public HttpRespEnvelope addCheckBalanceResult(
			@RequestParam(required = true) String checker,
			@RequestParam(required = true) String remark,
			@RequestParam(required = true) String checkBalanceId,
			@RequestParam(required = true) Integer checkResult,
			@RequestParam(required = true) Integer resultType) {
		HttpRespEnvelope hre = new HttpRespEnvelope();
		try {
			TcCheckBalanceResult result = new TcCheckBalanceResult();
			result.setCheckBalanceId(checkBalanceId);
			result.setChecker(checker);
			result.setCheckResult(checkResult);
			result.setRemark(remark);
			result.setCheckType(resultType);
			result.setCheckDate(DateUtil.getCurrentDate());
			checkBalanceResultService.addCheckBalanceResult(result);
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

	/**
	 * 添加申请
	 * 
	 * @param acctType
	 *            帐户类型
	 * @param amount
	 *            金额
	 * @param name
	 *            单位名称
	 * @param remark
	 *            说明
	 * @param checkType
	 *            调账类型，1 收入，0 支出
	 * @param resNo
	 *            互生号
	 * @param checker
	 *            操作员
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addCheckBalance", method = RequestMethod.POST)
	public HttpRespEnvelope addCheckBalance(
			@RequestParam(required = true) String acctType,
			@RequestParam(required = true) String amount,
			@RequestParam(required = true) String name,
			@RequestParam(required = true) String remark,
			@RequestParam(required = true) Integer checkType,
			@RequestParam(required = true) String resNo, String checker) {
		HttpRespEnvelope hre = new HttpRespEnvelope();
		try {
			TcCheckBalance balance = new TcCheckBalance();
			balance.setAcctType(acctType);
			balance.setAmount(new BigDecimal(amount));
			balance.setName(name);
			balance.setRemark(remark);
			balance.setCheckType(checkType);
			balance.setResNo(resNo);
			balance.setCreator(checker);

			checkBalanceService.addCheckBalance(balance);
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

	/**
	 * 查看调账详情
	 * 
	 * @param balanceId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getCheckBalanceDetail", method = RequestMethod.POST)
	public HttpRespEnvelope getCheckBalanceDetail(
			@RequestParam(required = true) String balanceId) {
		HttpRespEnvelope hre = new HttpRespEnvelope();
		try {
			TcCheckBalance b = checkBalanceService
					.searchCheckBalanceById(balanceId);
			hre.setData(b);
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

	/**
	 * 查看调账申请记录
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param dateType
	 * @param resNo
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getCheckBalances", method = RequestMethod.POST)
	public HttpRespEnvelope getCheckBalances(
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,

			@RequestParam(required = false) String resNo,
			@RequestParam(required = false) String status,
			@RequestParam(required = true, defaultValue = "10") Integer pageSize,
			@RequestParam(required = true, defaultValue = "1") Integer curPage) {
		Page page = new Page(curPage, pageSize);
		HttpRespEnvelope hre = new HttpRespEnvelope();

		TcCheckBalanceParam param = new TcCheckBalanceParam();
		param.setStartDate(startDate);
		param.setEndDate(endDate);
		param.setStatusStr(status);		
		param.setResNo(resNo);
		param.setPage(page);
		try {
			PageData<TcCheckBalance> list = checkBalanceService
					.searchCheckBalances(param);
			System.out.println(JSONObject.toJSONString(list));
			if(list.getResult() == null){
				List<TcCheckBalance> list1 = new ArrayList<>();
				hre.setData(list1);
			}
			else{
				hre.setData(list.getResult());
			}
			hre.setSuccess(true);
			
			hre.setTotalRows(list.getCount());
			hre.setCurPage(curPage);
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

	/**
	 * 根据互生号获取用户名
	 * 
	 * @param resNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUsernameByResNo", method = RequestMethod.POST)
	public HttpRespEnvelope getUsernameByResNo(
			@RequestParam(required = true) String resNo) {
		HttpRespEnvelope hre = new HttpRespEnvelope();
		try {
			Integer custType = HsResNoUtils.getCustTypeByHsResNo(resNo);
			if (custType == null) {
				throw new HsException(TcErrorCode.RES_NO_IS_WRONG.getCode(),
						TcErrorCode.RES_NO_IS_WRONG.getDesc());
			}
			// 根据资源号查找客户号
			String name = null;
			if (CustType.PERSON.getCode() == custType.intValue()) {
				String custId = carderService.findCustIdByResNo(resNo);
				if (custId == null) {
					hre.setRetCode(TcErrorCode.USER_NOT_FOUND.getCode());
					hre.setResultDesc("用户未找到");
					hre.setSuccess(false);
					return hre;
				}
				AsCardHolderAllInfo carder = carderService
						.searchAllInfo(custId);
				// 获取用户的实名名称，如果没有实名姓名，返回互生号
				if (carder.getBaseInfo().getIsRealnameAuth().intValue() > 1) {
					name = carder.getAuthInfo().getUserName();
				} else {
					name = resNo;
				}
			} else {
				String custId = entService.findEntCustIdByEntResNo(resNo);
				if (custId == null) {
					hre.setRetCode(TcErrorCode.USER_NOT_FOUND.getCode());
					hre.setResultDesc("用户未找到");
					hre.setSuccess(false);
					return hre;
				}
				AsEntExtendInfo ent = entService.searchEntExtInfo(custId);
				name = ent.getEntCustName();
			}
			hre.setData(name);
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
	@RequestMapping(value = "/getBalance", method = RequestMethod.POST)
	public HttpRespEnvelope getBalance(
			@RequestParam(required = true) String resNo,
			@RequestParam(required = true) String acctType) {
		HttpRespEnvelope hre = new HttpRespEnvelope();
		try {
			Integer custType = HsResNoUtils.getCustTypeByHsResNo(resNo);
			if (custType == null) {
				throw new HsException(TcErrorCode.RES_NO_IS_WRONG.getCode(),
						TcErrorCode.RES_NO_IS_WRONG.getDesc());
			}
			// 根据资源号查找客户号
			String custId = null;
			if (CustType.PERSON.getCode() == custType.intValue()) {
				custId = carderService.findCustIdByResNo(resNo);
			} else {
				custId = entService.findEntCustIdByEntResNo(resNo);
			}
			AccountBalance balance = accountSearchService.searchAccNormal(
					custId, acctType);
			if(balance == null){				
				hre.setData(0);
			}
			else{
				hre.setData(balance.getAccBalance());
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
