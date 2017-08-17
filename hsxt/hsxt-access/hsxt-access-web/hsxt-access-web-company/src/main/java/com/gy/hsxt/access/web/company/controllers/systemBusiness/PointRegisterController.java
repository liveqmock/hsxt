/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.controllers.systemBusiness;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.company.services.accountManagement.IBalanceService;
import com.gy.hsxt.access.web.company.services.systemBusiness.ICompanyInforService;
import com.gy.hsxt.access.web.company.services.systemBusiness.IPointRegisterService;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.Channel;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.bean.Point;
import com.gy.hsxt.ps.bean.PointResult;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;
import com.gy.hsxt.uid.uid.service.IUidService;

/**
 * 网上积分登记
 * 
 * @Package: com.gy.hsxt.access.web.company.controllers.systemBusiness
 * @ClassName: PointRegisterController
 * @Description:
 * 
 * @author: chenli
 * @date: 2015-01-16 下午6:43:46
 * @version V3.0.0
 */

@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("pointRegister")
public class PointRegisterController extends BaseController {

	@Resource
	private IPointRegisterService service;

	@Resource
	private ICompanyInforService companyInforService;

	@Autowired
	private IUCAsEntService iuCAsEntService; // 查询企业状态
	@Autowired
	private IUidService iUidService;// 获取流水号
	@Resource
	private IBalanceService balanceService; // 账户余额查询服务类

	/**
	 * 网上积分登记
	 * 
	 * @param request
	 * @param param
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = { "/register" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope register(Point point, HttpServletRequest request) {
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);

		if (httpRespEnvelope == null) {
			try {
				String entCustId = request.getParameter("entCustId");
				httpRespEnvelope = chekcEntStatus(entCustId);
				if (httpRespEnvelope == null) {
					// 如果未使用抵扣券则orderAmount不设置，为接口的特殊要求 start
					Integer voucher = point.getDeductionVoucher();
					if (voucher == null || voucher == 0) {
						point.setOrderAmount(null);
					}
					// 如果未使用抵扣券则orderAmount不设置，为接口的特殊要求 end

					service.pointRegister(point);
					httpRespEnvelope = new HttpRespEnvelope();
				}
			} catch (HsException e) {
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}
*/
	/**
	 * 验证企业状态（注销中 、已注销、停止积分活动申请中、已停止积分活动）不能进行网上积分登记
	 * 
	 * @param entCustId
	 * @return
	 */
	private HttpRespEnvelope chekcEntStatus(String entCustId) {
		HttpRespEnvelope httpRespEnvelope = null;
		AsEntStatusInfo info = iuCAsEntService.searchEntStatusInfo(entCustId);
		if (info == null) {
			return new HttpRespEnvelope(ASRespCode.EW_EXCHANGE_HSB_ENTCUSTNAME);
		} else if (info != null) {
			switch (info.getBaseStatus()) {
			case 5:
				return new HttpRespEnvelope(
						ASRespCode.EW_STATUS5_IS_NOT_REPLACE_BUY_HSB.getCode(),
						ASRespCode.EW_STATUS5_IS_NOT_REPLACE_BUY_HSB.getDesc());
			case 6:
				return new HttpRespEnvelope(
						ASRespCode.EW_STATUS6_IS_NOT_REPLACE_BUY_HSB.getCode(),
						ASRespCode.EW_STATUS6_IS_NOT_REPLACE_BUY_HSB.getDesc());
			case 7:
				return new HttpRespEnvelope(
						ASRespCode.EW_STATUS7_IS_NOT_REPLACE_BUY_HSB.getCode(),
						ASRespCode.EW_STATUS7_IS_NOT_REPLACE_BUY_HSB.getDesc());
			case 8:
				return new HttpRespEnvelope(
						ASRespCode.EW_STATUS8_IS_NOT_REPLACE_BUY_HSB.getCode(),
						ASRespCode.EW_STATUS8_IS_NOT_REPLACE_BUY_HSB.getDesc());
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 获取流水号
	 * 
	 * @param request
	 * @param custId
	 *            :客户号
	 * @param loginPwd
	 *            :登陆密码(AES加密后的密文)
	 * @param secretKey
	 *            :AES秘钥（随机报文校验token)
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/getSequence" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getSequence(double yfhsb, String custName,
			String perResNo, String custEntName, Point point,
			HttpServletRequest request) {
		point.setOperNo(custName);
		point.setEntName(custEntName);
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);

		if (httpRespEnvelope != null) {
			return httpRespEnvelope;
		}
		// 验证企业状态
		httpRespEnvelope = chekcEntStatus(point.getEntCustId());
		if (httpRespEnvelope != null) {
			return httpRespEnvelope;
		}

		try {
			// 判断应付积分是否大于互生币账户余额 start
			AccountBalance accBalance = balanceService.findAccNormal(
					point.getEntCustId(),
					AccountType.ACC_TYPE_POINT20110.getCode());

			String circulationHsb = "0.00";
			if (accBalance != null) {
				circulationHsb = accBalance.getAccBalance();
			}
			if (Double.parseDouble(circulationHsb) < yfhsb) {
				throw new HsException(
						ASRespCode.EW_POINT_REGIST_YFHSB_LAGER_BALANCE);
			}
			// 判断应付积分是否大于互生币账户余额 end

			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { perResNo,
					RespCode.APS_INVOICE_RES_NO_EMPTY.getCode(),
					RespCode.APS_INVOICE_RES_NO_EMPTY.getDesc() });

			// 获取消费者卡号对应的ID并设置
			String hsCardNoId = companyInforService.findCustIdByResNo(perResNo);
			point.setPerCustId(hsCardNoId);

			// 查询流水号并设置
			Long uid = iUidService.getUid(point.getEntResNo());
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { uid,
					RespCode.PS_TRANS_NO_ERROR.getCode(),
					RespCode.PS_TRANS_NO_ERROR.getDesc() });
			String sequence = Channel.MCR.getCode()
					+ String.format("%011d", uid);
			point.setSourceTransNo(sequence);

			// 如果未使用抵扣券则orderAmount不设置，为接口的特殊要求 start
			int voucher = point.getDeductionVoucher();
			if (voucher == 0) {
				point.setOrderAmount(null);
			}
			// 如果未使用抵扣券则orderAmount不设置，为接口的特殊要求 end

			PointResult pointResult = service.pointRegister(point);
			point.setPointSum(pointResult.getEntPoint());

			httpRespEnvelope = new HttpRespEnvelope(point);
		} catch (HsException e) {
			httpRespEnvelope = new HttpRespEnvelope(e);
		}
		return httpRespEnvelope;
	}

	/**
	 * 计算扣除积分数
	 * 
	 * @param consume_fee
	 *            :消费金额
	 * @param wsdjjf_jfbl
	 *            :积分比例
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/register" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope calculatePoint(String consume_fee,
			String wsdjjf_jfbl) {

		BigDecimal jfbl = new BigDecimal(wsdjjf_jfbl);
		BigDecimal fee = new BigDecimal(consume_fee);

		double point = jfbl.multiply(fee).setScale(2, BigDecimal.ROUND_UP)
				.doubleValue();
		HttpRespEnvelope httpRespEnvelope = new HttpRespEnvelope(point);
		return httpRespEnvelope;
	}

	@Override
	protected IBaseService getEntityService() {
		return service;
	}

}
