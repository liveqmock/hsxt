/**
 * 
 */
package com.gy.hsxt.access.web.aps.services.toolorder.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.toolorder.IAfterPaidOrderApprService;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.api.tool.IBSToolAfterService;
import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.tool.AfterService;
import com.gy.hsxt.bs.bean.tool.AfterServiceDetail;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolOrderPage;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * @author weiyq
 * 
 */
@Service
public class AfterPaidOrderApprServiceImpl extends BaseServiceImpl implements
		IAfterPaidOrderApprService {

	@Autowired
	private IBSToolAfterService bsToolAfterService;

	/**
	 * 分页查询售后订单
	 * 
	 * @Description:
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 */
	@Override
	public PageData<ToolOrderPage> queryAfterOrderPlatPage(Map filterMap,
			Map sortMap, Page page) {
		BaseParam param = new BaseParam();
		param.setStartDate(filterMap.get("startDate").toString());
		param.setEndDate(filterMap.get("endDate").toString());
		param.setOrderNo(filterMap.get("orderNo").toString());
		param.setHsResNo(filterMap.get("hsResNo").toString());
		param.setStatus(CommonUtils.toInteger((String) filterMap.get("status")));
		try {
			return bsToolAfterService.queryAfterOrderPlatPage(param, page);
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(),
					"queryAfterOrderPlatPage", "调用BS分页查询售后订单失败", ex);
			return null;
		}
	}

	@Override
	public PageData<AfterService> findScrollResult(Map filterMap, Map sortMap,
			Page page) throws HsException {
		String startDate = (String) filterMap.get("startDate");
		String endDate = (String) filterMap.get("endDate");
		String exeCustId = (String) filterMap.get("custId");
		String hsResNo = (String) filterMap.get("hsResNo");
		String hsCustName = (String) filterMap.get("hsCustName");

		Object statusObj = filterMap.get("apprStatus");
		Integer status = null;
		if (statusObj != null) {
			status = Integer.parseInt((String) statusObj);
		}
		BaseParam bp = new BaseParam();
		bp.setStartDate(startDate);
		bp.setEndDate(endDate);
		bp.setExeCustId(exeCustId);

		bp.setHsResNo(hsResNo);
		bp.setHsCustName(hsCustName);
		bp.setStatus(status);
		try {
			return bsToolAfterService.queryAfterOrderApprPage(bp, page);
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "findScrollResult",
					"调用BS查询工具售后单失败", ex);
			return null;
		}
	}

	/**
	 * 查询工具售后单
	 * 
	 * @param orderNo
	 * @return
	 * @throws HsException
	 */
	@Override
	public AfterService searchOrderDetail(String orderNo) throws HsException {
		RequestUtil.verifyParamsIsNotEmpty(new Object[] { orderNo,
				ASRespCode.APS_TOOL_AFTERORDERNO_INVALID.getCode(),
				ASRespCode.APS_TOOL_AFTERORDERNO_INVALID.getDesc() });
		try {
			return bsToolAfterService.queryAfterServiceByNo(orderNo);
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "searchOrderDetail",
					"调用BS查询工具售后单失败", ex);
			return null;
		}
	}

	/**
	 * 
	 */
	@Override
	public void apprAfterService(AfterService asObj, String listDetail)
			throws HsException {
		RequestUtil.verifyParamsIsNotEmpty(
				new Object[] { asObj.getAfterOrderNo(),
						ASRespCode.APS_TOOL_AFTERORDERNO_INVALID.getCode(),
						ASRespCode.APS_TOOL_AFTERORDERNO_INVALID.getDesc() },
				new Object[] { asObj.getExeCustId(),
						ASRespCode.APS_TOOL_EXECUSTID_INVALID.getCode(),
						ASRespCode.APS_TOOL_EXECUSTID_INVALID.getDesc() },
				new Object[] { asObj.getStatus(),
						ASRespCode.APS_TOOL_APPRSTATUS_INVALID.getCode(),
						ASRespCode.APS_TOOL_APPRSTATUS_INVALID.getDesc() },
				new Object[] { asObj.getApprOperator(),
						ASRespCode.APS_TOOL_APPROPERATOR_INVALID.getCode(),
						ASRespCode.APS_TOOL_APPROPERATOR_INVALID.getDesc() });
		List<String> deviceList;
		try {
			deviceList = JSON.parseArray(
					URLDecoder.decode(listDetail, "UTF-8"), String.class);
		} catch (UnsupportedEncodingException e) {
			throw new HsException();
		}
		List<AfterServiceDetail> detail = new LinkedList<AfterServiceDetail>();
		for (String str : deviceList) {
			String[] ary = str.split(";");
			RequestUtil
					.verifyParamsIsNotEmpty(
							new Object[] {
									ary[0],
									ASRespCode.APS_TOOL_DEVICESEQNO_INVALID
											.getCode(),
									ASRespCode.APS_TOOL_DEVICESEQNO_INVALID
											.getDesc() },
							new Object[] {
									ary[1],
									ASRespCode.APS_TOOL_CATEGORYCODE_INVALID
											.getCode(),
									ASRespCode.APS_TOOL_CATEGORYCODE_INVALID
											.getDesc() },
							new Object[] {
									ary[2],
									ASRespCode.APS_TOOL_DISPOSETYPE_INVALID
											.getCode(),
									ASRespCode.APS_TOOL_DISPOSETYPE_INVALID
											.getDesc() },
							new Object[] {
									ary[3],
									ASRespCode.APS_TOOL_DISPOSEAMOUNT_INVALID
											.getCode(),
									ASRespCode.APS_TOOL_DISPOSEAMOUNT_INVALID
											.getDesc() },
							new Object[] {
									ary[5],
									ASRespCode.APS_TOOL_PRODUCTID_INVALID
											.getCode(),
									ASRespCode.APS_TOOL_PRODUCTID_INVALID
											.getDesc() });
			AfterServiceDetail asd = new AfterServiceDetail();
			asd.setAfterOrderNo(asObj.getAfterOrderNo());
			asd.setDeviceSeqNo(ary[0]);
			asd.setCategoryCode(ary[1]);
			try {
				asd.setDisposeType(Integer.parseInt(ary[2]));
			} catch (Exception e) {
				throw new HsException(ASRespCode.APS_TOOL_DISPOSETYPE_INVALID);
			}
			asd.setDisposeAmount(ary[3]);
			asd.setProductId(ary[5]);

			detail.add(asd);
		}

		asObj.setDetail(detail);
		try {
			bsToolAfterService.apprAfterService(asObj);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "apprAfterService",
					"调用BS审批售后单失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}
}
