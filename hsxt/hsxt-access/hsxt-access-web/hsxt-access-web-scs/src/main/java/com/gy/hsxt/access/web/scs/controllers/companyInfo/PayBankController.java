/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.controllers.companyInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.scs.services.companyInfo.IPayBankService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsQkBank;

/**
 * 
 * @projectName : hsxt-access-web-scs
 * @package : com.gy.hsxt.access.web.scs.controllers.hsc
 * @className : PayBankController.java
 * @description : 快捷支付
 * @author : maocy
 * @createDate : 2016-01-28
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("payBankController")
public class PayBankController extends BaseController {

	@Resource
	private IPayBankService payBankService;// 快捷支付服务类

	/**
	 * 
	 * 方法名称：查询绑定的快捷支付银行 方法描述：依据客户号查询绑定的快捷支付银行
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/findPayBanksByCustId" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findPayBanksByCustId(HttpServletRequest request) {
		try {
			// Token验证
			super.checkSecureToken(request);
			String entCustId = request.getParameter("entCustId");// 企业客户号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { entCustId,
					ASRespCode.AS_CUSTID_INVALID });
			Map<String, Object> map = new HashMap<String, Object>();
			// 查询绑定的银行列表
			map.put("banks", this.payBankService.findPayBanksByCustId(entCustId));
			// 获取最大绑定的银行卡数
			map.put("maxNum", 5);
			return new HttpRespEnvelope(map);
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：添加快捷支付银行 方法描述：添加快捷支付银行
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/addPayBank" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope addPayBank(HttpServletRequest request, @ModelAttribute AsQkBank bank) {
		try {
			// Token验证
			super.checkSecureToken(request);
			bank.setCustId(request.getParameter("entCustId"));// 企业客户号
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { bank.getCustId(),
					ASRespCode.AS_ENT_CUSTID_INVALID }, new Object[] { bank.getBankCode(),
					ASRespCode.AS_BANK_CODE_ERROR }, new Object[] { bank.getBankName(),
					ASRespCode.AS_BANK_NAME_ERROR }, new Object[] { bank.getBankCardNo(),
					ASRespCode.AS_BANKCARDNO_INVALID });
			// 验证银行卡格式
			RequestUtil.verifyBankNo(bank.getBankCardNo(), ASRespCode.AS_BANKCARDNO_ERROR);
			// 查询是否已存在此快捷支付账号
			List<AsQkBank> list = this.payBankService.findPayBanksByCustId(bank.getCustId());
			if (list != null && list.size() > 0) {
				for (AsQkBank qkBank : list) {
					if (qkBank.getBankCardNo().equals(bank.getBankCardNo())) {
						throw new HsException(ASRespCode.AS_QK_BANKCARDNO_EXIST);
					}
				}
			}
			// 保存快捷支付银行
			this.payBankService.addPayBank(bank);
			return new HttpRespEnvelope();
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：删除快捷支付银行 方法描述：删除快捷支付银行
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/delPayBank" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope delPayBank(HttpServletRequest request) {
		try {
			// Token验证
			super.checkSecureToken(request);
			Long accId = CommonUtils.toLong(request.getParameter("accId"));// 银行编号
			// 非空验证
			RequestUtil
					.verifyParamsIsNotEmpty(new Object[] { accId, ASRespCode.AS_BANKID_INVALID });
			// 删除快捷支付银行
			this.payBankService.delPayBank(accId);
			return new HttpRespEnvelope();
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	@Override
	protected IBaseService getEntityService() {
		return this.payBankService;
	}

}
