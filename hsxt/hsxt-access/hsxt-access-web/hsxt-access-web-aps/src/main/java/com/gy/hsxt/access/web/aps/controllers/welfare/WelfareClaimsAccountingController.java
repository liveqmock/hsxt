package com.gy.hsxt.access.web.aps.controllers.welfare;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.access.web.aps.services.welfare.IWelfareClaimsAccountingService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.bean.ClaimsAccountingDetail;
import com.gy.hsxt.ws.bean.ClaimsAccountingQueryCondition;
import com.gy.hsxt.ws.bean.ClaimsAccountingRecord;
import com.gy.hsxt.ws.bean.MedicalDetail;

/**
 * 积分福利理赔核算控制类
 * 
 * @category 积分福利理赔核算控制类
 * @projectName hsxt-access-web-aps
 * @package com.gy.hsxt.access.web.aps.controllers.welfare.
 *          WelfareClaimsAccountingController.java
 * @className WelfareClaimsAccountingController
 * @description 积分福利理赔核算控制类
 * @author leiyt
 * @createDate 2015-12-31 下午5:05:21
 * @updateUser leiyt
 * @updateDate 2015-12-31 下午5:05:21
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
@Controller
@RequestMapping("welfareClaimsAccounting")
public class WelfareClaimsAccountingController extends BaseController<Object> {

	@Autowired
	IWelfareClaimsAccountingService welfareClaimsAccountingService;

	/**
	 * 查询待核算理赔单列表
	 * 
	 * @param queryCondition
	 * @param page
	 * @return
	 */
	@RequestMapping(value = { "/listPendingClaimsAccounting" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope listPendingClaimsAccounting(
			ClaimsAccountingQueryCondition queryCondition, Page page, HttpServletRequest request,
			HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {
			hre = new HttpRespEnvelope();
			hre.setCurPage(page.getCurPage());
			PageData<ClaimsAccountingRecord> list = welfareClaimsAccountingService
					.listPendingClaimsAccounting(queryCondition, page);
			if (list != null) {
				hre.setData(list.getResult());
				hre.setTotalRows(list.getCount());
			} else {
				hre.setTotalRows(0);
			}
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 查询已核算理赔单列表
	 * 
	 * @param queryCondition
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/listHisClaimsAccounting" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope listHisClaimsAccounting(ClaimsAccountingQueryCondition queryCondition,
			Page page, HttpServletRequest request, HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {
			hre = new HttpRespEnvelope(welfareClaimsAccountingService.listHisClaimsAccounting(
					queryCondition, page));
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 理赔核算
	 * 
	 * @param list
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/countMedicalDetail" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope countMedicalDetail(String medicalDetailJsonStr,
			HttpServletRequest request, HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {

			List<MedicalDetail> sList = JSON.parseArray(
					URLDecoder.decode(medicalDetailJsonStr, "UTF-8"), MedicalDetail.class);

			welfareClaimsAccountingService.countMedicalDetail(sList);
			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		} catch (UnsupportedEncodingException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 理赔核算确认
	 * 
	 * @param list
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/confirmClaimsAccounting" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope confirmClaimsAccounting(String medicalDetailJsonStr,
			HttpServletRequest request, HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {

			List<MedicalDetail> sList = JSON.parseArray(
					URLDecoder.decode(medicalDetailJsonStr, "UTF-8"), MedicalDetail.class);
			welfareClaimsAccountingService.confirmClaimsAccounting(sList);
			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		} catch (UnsupportedEncodingException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 查询理赔核算明细
	 * 
	 * @param list
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/queryClaimsAccountingDetail" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope queryClaimsAccountingDetail(String accountingId,
			HttpServletRequest request, HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {
			hre = new HttpRespEnvelope();
			ClaimsAccountingDetail accountingDetail = welfareClaimsAccountingService
					.queryClaimsAccountingDetail(accountingId);
			if (accountingDetail != null) {
				hre.setData(accountingDetail.getDetailList());
			} else {
				List<MedicalDetail> list = new ArrayList<MedicalDetail>();
				hre.setData(list);
			}
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	@Override
	protected IBaseService<?> getEntityService() {
		// TODO Auto-generated method stub
		return null;
	}

}
