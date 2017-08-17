/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.controllers.systemBusiness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gy.hsxt.access.web.bean.Menu;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.company.services.systemBusiness.IPointRateService;
import com.gy.hsxt.bp.bean.BusinessSysParamItemsRedis;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.exception.HsException;

/**
 * 积分比例
 * 
 * @Package: com.gy.hsxt.access.web.company.controllers.systemBusiness
 * @ClassName: PointRateController
 * @Description: TODO
 * 
 * @author: zhangcy
 * @date: 2015-12-2 下午6:43:46
 * @version V3.0.0
 */

@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("pointrate")
public class PointRateController extends BaseController {

	@Resource
	private IPointRateService ipointRateService;

	@Autowired
	private BusinessParamSearch businessParamSearch;// 业务参数服务接口

	/**
	 * 积分比例设置
	 * 
	 * @param request
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/add" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope setPointRate(HttpServletRequest request) {
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null) {
			try {
				String entResNo = request.getParameter("hsResNo");
				String[] pointRate = request.getParameter("rate").split(",");
				String opreator = request.getParameter("custName");
				ipointRateService.savePointRate(entResNo, pointRate, opreator);
				httpRespEnvelope = new HttpRespEnvelope();
			} catch (HsException e) {
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 查询企业积分比例
	 * 
	 * @param request
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/query" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryPointRate(HttpServletRequest request) {
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null) {
			try {
				String entResNo = request.getParameter("hsResNo");
				String[] result = ipointRateService.findPointRate(entResNo);
				httpRespEnvelope = new HttpRespEnvelope(result);
			} catch (HsException e) {
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 查询企业积分比例
	 * 
	 * @param request
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/queryMenu" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryPointRateMenu(HttpServletRequest request) {
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null) {
			try {
				String entResNo = request.getParameter("hsResNo");
				String[] result = ipointRateService.findPointRate(entResNo);
				List<Menu> rateList = new ArrayList<Menu>();
				for (int i = 0; i < result.length; i++) {
					rateList.add(new Menu(result[i], "" + i));
				}
				String sysGroupCode = BusinessParam.DEDUCTION_VOUCHER.getCode(); // 抵扣券组（组）
				String voucherMaxCode = BusinessParam.DEDUCTION_VOUCHER_MAX_NUM
						.getCode(); // 抵扣券最大张数
				String voucherAmountCode = BusinessParam.DEDUCTION_VOUCHER_PER_AMOUNT
						.getCode(); // 抵扣券每张面额
				String voucherRateCode = BusinessParam.DEDUCTION_VOUCHER_RATE
						.getCode(); // 抵扣券金额占消费金额比率

				Map<String, BusinessSysParamItemsRedis> paramGroup = businessParamSearch
						.searchSysParamItemsByGroup(sysGroupCode);
				int voucherMax = Integer.parseInt(paramGroup
						.get(voucherMaxCode).getSysItemsValue());
				String voucherAmount = paramGroup.get(voucherAmountCode)
						.getSysItemsValue();
				String voucherRate = paramGroup.get(voucherRateCode)
						.getSysItemsValue();
				
				List<Menu> voucherList = new ArrayList<Menu>();
				voucherList.add(new Menu("不使用", "0"));
				for (int i = 1; i <= voucherMax; i++) {
					voucherList.add(new Menu(i + "张（面额" + voucherAmount
							+ "元）", "" + i));
				}
				
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("rateList", rateList);
				resultMap.put("voucherList", voucherList);
				resultMap.put("voucherAmount", voucherAmount);
				resultMap.put("voucherRate", voucherRate);
				httpRespEnvelope = new HttpRespEnvelope(resultMap);
			} catch (HsException e) {
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 修改积分比例
	 * 
	 * @param request
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/update" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope updatePointRate(HttpServletRequest request) {
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null) {
			try {
				String entResNo = request.getParameter("hsResNo");
				String[] pointRate = request.getParameter("rate").split(",");
				String opreator = request.getParameter("custName");
				ipointRateService
						.updatePointRate(entResNo, pointRate, opreator);
				httpRespEnvelope = new HttpRespEnvelope();
			} catch (HsException e) {
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	@Override
	protected IBaseService getEntityService() {
		return ipointRateService;
	}

	public static void main(String[] s){

		int voucherMax = Integer.parseInt(".5");
		System.out.println("========"+voucherMax);
	}
	
}
