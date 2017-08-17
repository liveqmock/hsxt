/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.controllers.hsc;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.common.utils.ValidateUtil;
import com.gy.hsxt.access.web.person.services.consumer.ICardholderService;
import com.gy.hsxt.access.web.person.services.hsc.ICardHolderAuthInfoService;
import com.gy.hsxt.access.web.person.services.hsc.IImportantInfoChangeService;
import com.gy.hsxt.bs.bean.entstatus.PerChangeInfo;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameAuth;
import com.gy.hsxt.uc.as.bean.enumerate.CerTypeEnum;

/**
 * 
 * @projectName : hsxt-access-web-person
 * @package : com.gy.hsxt.access.web.person.controllers.hsc
 * @className : IImportantInfoChangeController.java
 * @description : 重要信息变更
 * @author : maocy
 * @createDate : 2016-01-19
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("importantInfoChangeController")
public class IImportantInfoChangeController extends BaseController {

	@Resource
	private ICardholderService cardholderService;// 持卡人服务类

	@Resource
	private IImportantInfoChangeService changeService;// 重要信息变更服务类

	@Resource
	private ICardHolderAuthInfoService cardHolderAuthInfoService;// 查询实名注册信息服务类

	@Resource
	private RedisUtil<String> changeRedisUtil;

	/**
	 * 初始化重要信息变更
	 * 
	 * @param request
	 * @param custId
	 *            客户号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/initPerChange" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope initImportantInfoChange(String custId,
			HttpServletRequest request) {
		try {
			// Token验证
			super.checkSecureToken(request);
			// 执行查询 (1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证)
			String authStatus = this.cardHolderAuthInfoService.findAuthStatusByCustId(custId);
			// 存入结果
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("authStatus", authStatus);// 实名认证状态
			if ("3".equals(authStatus)) {
				// 获取用户重要信息变更状态
				boolean imptStatus = this.cardholderService.findImportantInfoChangeStatus(custId);
				map.put("imptStatus", imptStatus);
				PerChangeInfo perChangeInfo = this.changeService.queryPerChangeByCustId(custId);
				map.put("perChangeInfo", perChangeInfo);// 重要信息变更信息

				AsRealNameAuth realnameAuth = this.cardholderService.findRealNameAuthInfo(custId);
				if(realnameAuth!=null){
				    if(ValidateUtil.validateFX(realnameAuth.getUserName())){
		                map.put("maskName",CommonUtils.strMask(realnameAuth.getUserName(), 2, 0, "*"));
		            }else{
		                map.put("maskName",CommonUtils.strMask(realnameAuth.getUserName(), 1, 0, "*"));
		            }
				}
				map.put("realNameAuth", realnameAuth);// 实名认证信息

			}
			return new HttpRespEnvelope(map);
		} catch (Exception e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：数据校验 方法描述：数据校验
	 * 
	 * @param info
	 */
	public void verifySaveData(PerChangeInfo info) throws HsException {
		if (CerTypeEnum.BUSILICENCE.getType() == info.getCreTypeOld()) {// 营业执照
			// 长度验证
			RequestUtil.verifyParamsLength(new Object[] { info.getEntTypeNew(),
					0, 20, RespCode.PW_ENTTYPE_LENGTH_INVALID },// 公司类型
					new Object[] { info.getEntNameNew(), 0, 40,
							RespCode.PW_ENTERPRISE_INVALID.getCode(),
							RespCode.PW_ENTERPRISE_INVALID.getDesc() },// 企业名称
					new Object[] { info.getEntRegAddrNew(), 0, 50,
							RespCode.PW_ENTERPRISE_INVALID.getCode(),
							RespCode.PW_ENTERPRISE_INVALID.getDesc() }// 企业地址
					);
		} else if (CerTypeEnum.IDCARD.getType() == info.getCreTypeOld()) {// 身份证
			// 长度验证
			RequestUtil.verifyParamsLength(
					new Object[] { info.getNationalityNew(), 0, 128,
							RespCode.PW_BIRTHADDR_LENGTH_INVALID },// 户籍地址
					new Object[] { info.getProfessionNew(), 0, 50,
							RespCode.PW_PROFESSION_LENGTH_INVALID },// 职业
					new Object[] { info.getCreIssueOrgNew(), 0, 128,
							RespCode.PW_LICENCE_ISSUING_LENGTH_INVALID },// 发证机关
					new Object[] { info.getCreExpireDateNew(), 0, 20,
							RespCode.PW_VALID_DATE_LENGTH_INVALID }// 证件有效期
					);
			// 校验身份证号码
			if (!StringUtils.isBlank(info.getCreNoNew())) {
				RequestUtil.verifyCard(info.getCreNoNew(),
						RespCode.PW_IDCARD_NO_INVALID);
			}
		} else if (CerTypeEnum.PASSPORT.getType() == info.getCreTypeOld()) {// 护照
			// 长度验证
			RequestUtil
					.verifyParamsLength(
							new Object[] {
									info.getNationalityNew(),
									0,
									128,
									RespCode.PW_PASSPORT_BIRTHADDR_LENGTH_INVALID },// 户籍地址
							new Object[] {
									info.getIssuePlaceNew(),
									0,
									128,
									RespCode.PW_PASSPORT_PROFESSION_LENGTH_INVALID },// 签发地点
							new Object[] {
									info.getCreIssueOrgNew(),
									0,
									128,
									RespCode.PW_PASSPORT_LICENCE_ISSUING_LENGTH_INVALID }// 签发机关
					);
		} else {
			throw new HsException(RespCode.PW_CERTYPE_NULL);
		}
	}

	/**
	 * 
	 * 方法名称：创建重要信息变更 方法描述：创建重要信息变更
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param info
	 *            重要信息变更
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/createPerChange")
	public HttpRespEnvelope createPerChange(String custId, String checkcode,
			HttpServletRequest request, @ModelAttribute PerChangeInfo info) {
		try {
			String perResNo = super.verifyPointNo(request);// 获取消费者互生号
			String perCustId = request.getParameter("custId");// 获取消费者客户号
			String optName = request.getParameter("custName");// 获取操作员名字
			String hsResNo = request.getParameter("hsResNo");//获取互生号

			// 数据校验
			this.verifySaveData(info);
			info.setOptName(hsResNo + "(" + optName + ")");
			info.setOptCustId(perCustId);
			info.setPerCustId(perCustId);
			info.setPerResNo(perResNo);
			info.setPerCustName(optName);
			info.setOptEntName(optName);
			// 创建重要信息变更
			this.changeService.createPerChange(info);
			return new HttpRespEnvelope();
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：修改重要信息变更 方法描述：修改重要信息变更
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param info
	 *            重要信息变更
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/modifyPerChange")
	public HttpRespEnvelope modifyPerChange(HttpServletRequest request,
			@ModelAttribute PerChangeInfo info) {
		try {
			String applyId = request.getParameter("applyId");// 获取申请编号
			String perCustId = request.getParameter("custId");// 获取消费者客户号
			String optName = request.getParameter("custName");// 获取操作员名字
			String optEntName = request.getParameter("custEntName");// 获取操作员所属公司名称/个人消费者名称
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { applyId,
					RespCode.AS_APPLYID_INVALID });
			// 数据校验
			this.verifySaveData(info);
			info.setOptName(optName);
			info.setOptEntName(optEntName);
			info.setOptCustId(perCustId);
			// 修改重要信息变更
			this.changeService.modifyPerChange(info);
			return new HttpRespEnvelope();
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
	}

	@Override
	protected IBaseService getEntityService() {
		return this.changeService;
	}

}
