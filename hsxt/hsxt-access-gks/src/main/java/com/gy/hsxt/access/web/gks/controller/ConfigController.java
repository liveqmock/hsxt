/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.gks.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.gks.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.gks.bean.GenPmkResult;
import com.gy.hsxt.access.web.gks.bean.PmkSecretKeyParam;
import com.gy.hsxt.access.web.gks.bean.UpdateDeviceInfoParam;
import com.gy.hsxt.access.web.gks.service.IMaintConfigService;
import com.gy.hsxt.bs.bean.tool.resultbean.AfterDeviceDetail;
import com.gy.hsxt.bs.bean.tool.resultbean.SecretKeyOrderInfoPage;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/***
 * POS 维护配置
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.posInterface
 * @ClassName: MaintenanceConfig
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-23 下午3:02:20
 * @version V1.0
 */
@Controller
@RequestMapping("/posConfig")
public class ConfigController extends BaseController<Object> {

	@Resource
	private IMaintConfigService configService;

	/**
	 * 分页查询POS机维护配置单
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getPosConfigList", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getPosConfigList(String resNo, String custName,
			String custId, Integer pageSize, Integer curPage) {
		HttpRespEnvelope hre = null;// 返回值
		try {
			Page page = new Page(curPage, pageSize);
			PageData<SecretKeyOrderInfoPage>  result = configService.queryAfterSecretKeyConfigByPage(resNo, custName,
					custId, page);
			hre = new HttpRespEnvelope(result);
		} catch (HsException e) {
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}
 
		return hre;
	} 

	/**
	 * 分页查询POS机维护配置单详细清单
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getConfigDetail", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getConfigDetail(String confNo,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;// 返回值
		try {
			// 空数据验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { confNo,
					RespCode.APS_CONFNO_NOT_NULL.getCode(),
					RespCode.APS_CONFNO_NOT_NULL.getDesc() });

			// 配置单详情
			List<AfterDeviceDetail> addList = configService
					.queryAfterSecretKeyConfigDetail(confNo);
			hre = new HttpRespEnvelope(addList);
		} catch (HsException e) {
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 获取PMK秘钥
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getPmk", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getPmk(HttpServletRequest request,
			PmkSecretKeyParam pskb) {
		HttpRespEnvelope hre = null;// 返回值
		try {
			// 空数据验证
			pskb.checkData();

			// 获取密钥
			GenPmkResult pspo = configService.reCreatePosPMK(pskb);
			hre = new HttpRespEnvelope(pspo);
		} catch (HsException e) {
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 修改POS机启用状态
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updatePosStatus", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope updatePosStatus(UpdateDeviceInfoParam param) {
		HttpRespEnvelope hre = null;// 返回值
		try {
			// 空数据验证
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { param.getEntResNo(),
							RespCode.APS_ENTRESNO_NOT_NULL.getCode(),
							RespCode.APS_ENTRESNO_NOT_NULL.getDesc() },
					new Object[] { param.getPmk(),
							RespCode.AS_PARAM_INVALID.getCode(),
							"PMK不能为空" },
					new Object[] { param.getDeviceStatus(),
							RespCode.APS_STATUS_NOT_NULL.getCode(),
							RespCode.APS_STATUS_NOT_NULL.getDesc() },
					new Object[] { param.getEntCustId(),
							RespCode.APS_ENTRESNO_NOT_NULL.getCode(),
							RespCode.APS_ENTRESNO_NOT_NULL.getDesc() },
					new Object[] { param.getOrderNo(),
							RespCode.APS_CONFNO_NOT_NULL.getCode(),
							RespCode.APS_CONFNO_NOT_NULL.getDesc() });
 
			// 修改设备状态
		    configService.configSecretKeyDeviceAfter(param);
			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	@Override
	protected IBaseService getEntityService() {
		// TODO Auto-generated method stub
		return null;
	}

}
