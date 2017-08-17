/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.gks.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.access.web.gks.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.gks.bean.GenPmkResult;
import com.gy.hsxt.access.web.gks.bean.PmkSecretKeyParam;
import com.gy.hsxt.access.web.gks.bean.UpdateDeviceStatusParam;
import com.gy.hsxt.access.web.gks.service.IPurchaseConfigService;
import com.gy.hsxt.bs.bean.tool.resultbean.DeviceTerminalNo; 
import com.gy.hsxt.bs.bean.tool.resultbean.SecretKeyOrderInfoPage;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/***
 * POS 申购配置
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.posInterface
 * @ClassName: PurchaseConfig
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-23 下午3:02:35 
 * @version V1.0
 */
@Controller
@RequestMapping("/posPurchaseConfig")
public class PurchaseConfigController extends BaseController<Object> {

	@Resource
	private IPurchaseConfigService purchaseConfigService;

	/**
	 * 分页查询POS机申购配置单
	 * 
	 * @param request
	 * @param apqa
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getPurchaseConfig", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getPurchaseConfig(String resNo, String custName, String custId, Integer pageSize, Integer curPage) {
		HttpRespEnvelope hre = null;// 返回值
		try {
			System.out.println("进入分页查询申购配置单的接口. curPage:" + curPage + ", pageSize:" + pageSize + ", custId:" + custId + ", custName:" + custName);
			Page page = new Page(curPage,pageSize);
			PageData<SecretKeyOrderInfoPage> result = purchaseConfigService.querySecretKeyConfigByPage(resNo, custName,custId, page);
			hre = new HttpRespEnvelope(result);
			System.out.println("查询申购配置单完成, 返回值：" + JSONObject.toJSONString(hre));
		} catch (HsException e) {
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 查询设备的终端编号列表
	 * 
	 * @param request
	 * @param entCustId
	 * @param confNo
	 * @param categoryCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getTerminalNo", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getTerminalNo(HttpServletRequest request,
			String entCustId, String confNo, String categoryCode) {
		HttpRespEnvelope hre = null;// 返回值
		try {
			System.out.println("进入分页查询申购配置单的接口. entCustId:" + entCustId + ", confNo:" + confNo + ", categoryCode:" + categoryCode);
			
			// 空数据验证
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { entCustId,
							RespCode.APS_SKGJZZ_GLXLH_ENTCUSTID.getCode(),
							RespCode.APS_SKGJZZ_GLXLH_ENTCUSTID.getDesc() },
					new Object[] { confNo,
							RespCode.APS_CONFNO_NOT_NULL.getCode(),
							RespCode.APS_CONFNO_NOT_NULL.getDesc() },
					new Object[] { categoryCode,
							RespCode.APS_CATEGORYCODE_NOT_NLL.getCode(),
							RespCode.APS_CATEGORYCODE_NOT_NLL.getDesc() });
			System.out.println("分页查询申购配置单验证通过");
			// 查询设备的终端编号列表
			DeviceTerminalNo dtn = purchaseConfigService
					.queryConifgDeviceTerminalNo(entCustId, confNo,
							categoryCode);
			System.out.println("查询设备的终端编号列表完成, 返回值：" + JSONObject.toJSONString(hre));
			hre = new HttpRespEnvelope(dtn);
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
	public HttpRespEnvelope getPMK(HttpServletRequest request,
			PmkSecretKeyParam pskb) {
		try {
			System.out.println("获取PMK秘钥开始");
			// 空数据验证
			pskb.checkData();
			System.out.println("获取PMK秘钥接口验证通过，开始调用获取PMK接口");
			// 获取密钥
			GenPmkResult pmkaspo = purchaseConfigService.createPosPMK(pskb);
			System.out.println("------- PMK: " + pmkaspo);
			return new HttpRespEnvelope(pmkaspo);
		} catch (HsException e) {
			e.printStackTrace();
			HttpRespEnvelope result = new HttpRespEnvelope(e);
			result.setRetCode(e.getErrorCode());
			result.setResultDesc(e.getMessage());
			return result;
		}
	}

	/**
	 * 修改POS机启用状态
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updatePosStatus", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope updatePosStatus(UpdateDeviceStatusParam param) {
		HttpRespEnvelope hre = null;// 返回值
		try {
			System.out.println("开始进入修改POS机状态");
			// 空数据验证
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { param.getEntResNo(),
					RespCode.APS_ENTRESNO_NOT_NULL.getCode(),
					RespCode.APS_ENTRESNO_NOT_NULL.getDesc() }, 
					new Object[] {
					param.getDeviceNo(), RespCode.APS_DEVICENO_NOT_NULL.getCode(),
					RespCode.APS_DEVICENO_NOT_NULL.getDesc() }, 
					new Object[] {
					param.getStatus(), RespCode.APS_STATUS_NOT_NULL.getCode(),
					RespCode.APS_STATUS_NOT_NULL.getDesc() }, 
					new Object[] {
					param.getEntCustId(), RespCode.APS_ENTRESNO_NOT_NULL.getCode(),
					RespCode.APS_ENTRESNO_NOT_NULL.getDesc() }, 
					new Object[] {
							param.getConfNo(), RespCode.APS_CONFNO_NOT_NULL.getCode(),
							RespCode.APS_CONFNO_NOT_NULL.getDesc() });
			System.out.println("修改POS机状态验证数据成功");
			// 修改设备状态
			purchaseConfigService.updateDeviceStatus(param);
			hre = new HttpRespEnvelope();
			System.out.println("修改POS机状态成功，返回值：" + JSONObject.toJSONString(hre));
		} catch (HsException hse) {
			hse.printStackTrace();
			hre = new HttpRespEnvelope(hse);
		}
		catch(Exception e){
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
