/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.point.client.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.bs.bean.tool.resultbean.AfterDeviceDetail;
import com.gy.hsxt.bs.bean.tool.resultbean.SecretKeyOrderInfoPage;
import com.gy.hsxt.point.client.bean.GenPmkResult;
import com.gy.hsxt.point.client.bean.PosInfoResult;
import com.gy.hsxt.point.client.util.HttpRequestor;
import com.gy.hsxt.point.client.util.Persistence;
import com.gy.hsxt.point.client.util.PosUtil;

/**
 * POS机烧机维护接入层
 * 
 * @Package: com.gy.point.client.util
 * @ClassName: ComToPosRepairAccess
 * 
 * @author: liyh
 * @date: 2015-11-17 下午3:13:11
 * @version V3.0
 */
public class ComToRepairAccess {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ComToRepairAccess.class);
	public static String pos_maint_config = "posConfig";

	/**
	 * 查询配置单，根据类型，查询售后维护配置单
	 * 
	 * @param map
	 * @param persistence
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> accessRepareOrder(Map<String, Object> map,
			Persistence persistence) {
		Map<String, Object> resultMap = null;
		JSONObject obj = null;
		String url = null;
		JSONObject objdata = null;
		JSONArray jsonArray = null;
		List<SecretKeyOrderInfoPage> list = null;
		try {
			list = new ArrayList<SecretKeyOrderInfoPage>();
			resultMap = new HashMap<String, Object>();
			// 获取请求URL
			url = persistence.getWebURL(pos_maint_config, "getPosConfigList");
			// post请求并且返回结果
			obj = new HttpRequestor().doPost(url, map);
			// 获取data数据对象
			objdata = obj.getJSONObject("data");
			int count = (int) objdata.get("count");
			resultMap.put("count", count);
			// 获取结果集合数组
			jsonArray = objdata.getJSONArray("result");
			if (jsonArray != null && jsonArray.size() > 0) {
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject item = jsonArray.getJSONObject(i); // 每条记录又由几个Object对象组成
					String orderNo = item.getString("orderNo"); // 订单号
					String confNo = item.getString("confNo"); // 配置单号
					String entResNo = item.getString("entResNo"); // 互生号
					String entCustId = item.getString("entCustId"); // 客户号
					String custName = item.getString("custName");// 客户名称
					String categoryCode = item.getString("categoryCode"); // 工具类别
					int confingNum = item.getIntValue("confingNum");// 数量

					SecretKeyOrderInfoPage toolConfigPage = new SecretKeyOrderInfoPage();
					toolConfigPage.setConfNo(confNo);
					toolConfigPage.setOrderNo(orderNo);
					toolConfigPage.setEntResNo(entResNo);
					toolConfigPage.setEntCustId(entCustId);
					toolConfigPage.setCustName(custName);
					toolConfigPage.setCategoryCode(categoryCode);
					toolConfigPage.setConfingNum(confingNum);
					list.add(toolConfigPage);
				}
			}
			resultMap.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询售后配置单失败，接入服务异常：" + e.getMessage());
		}
		return resultMap;
	}

	public List<AfterDeviceDetail> accessRepareDetailOrder(
			Map<String, Object> map, Persistence persistence) {
		JSONObject obj1 = null;
		String url = persistence.getWebURL(pos_maint_config, "getConfigDetail");
		List<AfterDeviceDetail> list = null;
		try {
			obj1 = new HttpRequestor().doPost(url, map);
			JSONArray jsonArray = (JSONArray) obj1.get("data");
			list = new ArrayList<AfterDeviceDetail>();
			if (jsonArray != null && jsonArray.size() > 0) {
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject item = jsonArray.getJSONObject(i); // 每条记录又由几个Object对象组成
					String afterOrderNo = item.getString("afterOrderNo"); // 获取对象对应的值
					String confNo = item.getString("confNo");
					String terminalNo = item.getString("terminalNo"); // 获取对象对应的值
					boolean isConfig = item.getBoolean("isConfig");
					String deviceSeqNo=item.getString("deviceSeqNo");
					AfterDeviceDetail afterDeviceDetail = new AfterDeviceDetail();
					afterDeviceDetail.setAfterOrderNo(afterOrderNo);
					afterDeviceDetail.setConfNo(confNo);
					afterDeviceDetail.setTerminalNo(terminalNo);
					afterDeviceDetail.setIsConfig(isConfig);
					afterDeviceDetail.setDeviceSeqNo(deviceSeqNo);
					list.add(afterDeviceDetail);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询售后配置单失败，接入服务异常：" + e.getMessage());
		}
		return list;
	}

	/**
	 * 获取秘钥，维护配置单用到
	 * 
	 * @param map
	 *            maps.put("entResNo","1"); 企业客户号,互生号 maps.put("deviceNo","2"
	 *            );终端编号 平台系统给企业设备定义的4位数编号 maps.put("sNewVersion", "true");
	 *            是否为新版本创建秘钥 true:新版本 false:旧版本 maps.put("operCustId", "pos01");
	 *            操作者客户号
	 * @param persistence
	 *            配置类
	 * @return 返回秘钥
	 */
	public Map<String, Object> accessGetReparPmk(Map<String, Object> map,
			Persistence persistence) {
		String url = null;
		JSONObject obj = null;
		JSONObject objdata = null;
		byte[] dataByte = null;
		Map<String, Object> mapResult = null;
		try {
			// 获取URL
			url = persistence.getWebURL(pos_maint_config, "getPmk");
			// post请求并且返回结果
			obj = new HttpRequestor().doPost(url, map);
			// 获取具体数据对象
			objdata = obj.getJSONObject("data");
			if (objdata != null) {
				GenPmkResult genPmkResult = jsonToGenPmkResult(objdata);
				if (genPmkResult != null) {
					PosUtil p = new PosUtil();
					dataByte = p.pmkDone(genPmkResult);
					if (dataByte != null) {
						mapResult = new HashMap<String, Object>();
						mapResult.put("pmk", dataByte);
						mapResult.put("entResNo", genPmkResult.getPosInfo()
								.getEntResNo());// 11企业互生号
						mapResult.put("posNo", genPmkResult.getPosInfo()
								.getPosNo());// 4终端编号
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("获取售后秘钥失败，接入服务异常：" + e.getMessage());
		}
		return mapResult;
	}

	/**
	 * 
	 * @param jsonObj
	 * @return
	 */
	public static GenPmkResult jsonToGenPmkResult(
			com.alibaba.fastjson.JSONObject jsonObj) {
		GenPmkResult genPmkResult = null;
		PosInfoResult posInfoResult = null;
		try {
			// 1pmk
			String pmk = jsonObj.getString("pmk");
			// 获取PosInfoResult
			JSONObject posInfoJson = jsonObj.getJSONObject("posInfo");
			// 转化
			if (posInfoJson != null) {
				genPmkResult = new GenPmkResult();
				posInfoResult = JSONObject.toJavaObject(
						JSONObject.parseObject(posInfoJson.toString()),
						PosInfoResult.class);
				genPmkResult.setPmk(pmk);
				genPmkResult.setPosInfo(posInfoResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("秘钥数据转换失败：" + e.getMessage());
		}
		return genPmkResult;
	}

	public boolean accessUpdateRepairPosStatus(Map<String, Object> map,
			Persistence persistence) {
		String url = null;
		JSONObject obj = null;
		boolean updateFlag = false;
		try {
			// 获取请求URL
			url = persistence.getWebURL(pos_maint_config, "updatePosStatus");
			// 请求并且返回结果
			obj = new HttpRequestor().doPost(url, map);
			// 获取异常信息
			if (obj != null) {
				// 获取异常信息
				String retCode = obj.getString("retCode");// 22000
				if (retCode.equals("22000")) {
					updateFlag = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("修改配置关联关系和修改pos设备状态失败" + e.getMessage());
		}
		return updateFlag;
	}

}
