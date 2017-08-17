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
import com.gy.hsxt.bs.bean.tool.resultbean.SecretKeyOrderInfoPage;
import com.gy.hsxt.point.client.bean.GenPmkResult;
import com.gy.hsxt.point.client.bean.PosInfoResult;
import com.gy.hsxt.point.client.util.HttpRequestor;
import com.gy.hsxt.point.client.util.Persistence;
import com.gy.hsxt.point.client.util.PosUtil;

/**
 * POS烧机申购配置单接入层
 * 
 * @Package: com.gy.point.client.util
 * @ClassName: ComToAccess 重构
 * @Description: TODO
 * 
 * @author: liyh
 * @date: 2015-11-16 下午3:32:37
 * @version V3.0
 */
public class ComToAccess {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ComToAccess.class);

	// 申购请求控制器映射
	public static String pos_purchase_config = "posPurchaseConfig";

	/**
	 * 登出
	 * 
	 * @param map
	 *            map.put("channelType", "2");// 登录类型为1，表示渠道类型，WEB（web端）
	 *            map.put("custId", "06002110000164063559726080");// 操作员客户号
	 * @param persistence
	 *            配置工具类
	 */
	public void accessLoginOut(Map<String, Object> map, Persistence persistence) {
		String url = null;
		@SuppressWarnings("unused")
		JSONObject obj = null;
		try {
			// 获取请求URL
			url = persistence.getWebURL("login", "operatorLogout");
			// 得到请求返回数据:JSONObject
			obj = new HttpRequestor().doPost(url, map);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("登出失败" + e);
		}
	}

	/**
	 * 登录
	 * 
	 * @param map
	 *            请求参数 map.put("channelType", "2");// 登录类型为1，表示渠道类型，WEB（web端）
	 *            map.put("loginIp", "192.168.12.24");// 登录IP
	 *            map.put("userName", "0000");// 用户
	 *            map1.put("resNo","06002110000"); // 互生号
	 *            map1.put("randomToken", "17cf679048b96b8c");// 随机 randomToken
	 *            String pwd = StringEncrypt.string2MD5("666666"); pwd =
	 *            StringEncrypt.encrypt(pwd, "17cf679048b96b8c");
	 *            map1.put("loginPwd", pwd);// 密码
	 * @param persistence
	 *            配置数据工具类，用于获取请求URL
	 * @param poslogger
	 *            POS机客户端日志类
	 * @return
	 */
	public Map<String, Object> accessLogin(Map<String, Object> map,
			Persistence persistence) {
		// 返回结果Map
		Map<String, Object> resultMap = null;
		// 请求url
		String url = null;
		JSONObject obj = null;
		JSONObject objdata = null;
		boolean flag = false;
		String custId = null;
		resultMap = new HashMap<String, Object>();
		try {
			// 1获取请求URL
			url = persistence.getWebURL("login", "login");
			// 2得到请求返回数据:JSONObject
			obj = new HttpRequestor().doPost(url, map);
			objdata = obj.getJSONObject("data");
			// 3获取具体data数据
			if (objdata != null) {
				// 4需要操作员客户号缓存在客户端
				// 获取操作员客户号
				custId = objdata.getString("custId");
				// 5若操作员客户号不为空，表示成功，否则为：失败
				if (custId != null && !"".equals(custId)) {
					flag = true;
					// 设置操作员客户号
					resultMap.put("custId", custId);
					resultMap.put("loginToken", objdata.getString("token"));
				}
			} else {
				resultMap.put("resultDesc", obj.getString("resultDesc"));
			}
			// 登陆成功标识
			resultMap.put("flag", flag);
		} catch (Exception e) {
			resultMap.put("resultDesc", e.getMessage());
			e.printStackTrace();
			LOGGER.error("登陆失败，接入服务异常：" + e.getMessage());
			return resultMap;
		}
		return resultMap;
	}

	/**
	 * 2查询配置单
	 * 
	 * @param map
	 *            请求参数 dataMap1.put("entResNo", entResNo);互生号
	 *            dataMap1.put("custName", custName);客户名称，也就是企业名称
	 *            dataMap1.put("pageSize", "1");分页每页行数 dataMap1.put("pageNo",
	 *            "1");第几页 dataMap1.put("roleId", roleId);角色id(仓库管理员角色)
	 * @param persistence
	 *            配置获取
	 * @return
	 */
	@SuppressWarnings("null")
	public Map<String, Object> accessOrder(Map<String, Object> map,
			Persistence persistence) {
		Map<String, Object> resultMap = null;
		JSONObject obj1 = null;
		String url = null;
		JSONArray jsonArray = null;
		List<SecretKeyOrderInfoPage> list = null;
		JSONObject obj2 = null;
		try {
			list = new ArrayList<SecretKeyOrderInfoPage>();
			// 获取请求URL
			url = persistence.getWebURL("posPurchaseConfig",
					"getPurchaseConfig");
			// POST请求，并且返回JSONobject对象
			obj1 = new HttpRequestor().doPost(url, map);
			obj2 = obj1.getJSONObject("data");
			if (obj2 != null) {
				// 获取结果集合数组
				resultMap = new HashMap<String, Object>();
				jsonArray = obj2.getJSONArray("result");
				Integer count = (int) obj2.get("count");
				if (count != null) {
					resultMap.put("count", count);
				}
				if (jsonArray != null) {
					// 遍历集合数组
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject item = jsonArray.getJSONObject(i); // 每条记录又由几个Object对象组成
						String orderNo = item.getString("orderNo"); // 订单号
						String custName = item.getString("custName"); // 客户名称
						String entCustId = item.getString("entCustId"); // 企业编号
						int confingNum = item.getIntValue("confingNum"); // 未配置数量
						String confNo = item.getString("confNo");// 配置单号
						SecretKeyOrderInfoPage secretKeyOrderInfoPage = new SecretKeyOrderInfoPage();
						secretKeyOrderInfoPage.setOrderNo(orderNo);
						secretKeyOrderInfoPage.setCustName(custName);
						secretKeyOrderInfoPage.setEntCustId(entCustId);
						secretKeyOrderInfoPage.setConfingNum(confingNum);
						secretKeyOrderInfoPage.setConfNo(confNo);
						list.add(secretKeyOrderInfoPage);
					}
					resultMap.put("data", list);
				}
			} else {
				// 异常情况
				resultMap.put("resultDesc", obj1.getString("resultDesc"));
				System.out.println(obj1.getString("resultDesc"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询配置单失败：接入服务异常：" + e);
		}
		return resultMap;
	}

	/**
	 * 3获取终端编号列表，用于申购订单详情已使用终端编号和未使用终端编号
	 * 
	 * @param map
	 *            仓库管理员角色 map.put("entCustId", roleIdText.getText()); 企业互生号
	 *            map.put("confNo",
	 *            OrderTable.getValueAt(OrderTable.getSelectedRow(), 3));
	 *            企业名称，客户名称 map.put("categoryCode", enName);
	 * @param persistence
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> accessOrderEntNo(Map<String, Object> map,
			Persistence persistence) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JSONObject obj1 = null;
		String url = null;
		JSONObject jsonObj1 = null;
		String tempUserNo = null;
		String tempNoUserNo = null;
		JSONArray jsonArray1 = null;
		JSONArray jsonArray2 = null;
		// 已经使用的终端编号
		List<String> listUserNo = null;
		// 未使用的终端编号
		List<String> listNoUserNo = null;
		try {
			listUserNo = new ArrayList<String>();
			listNoUserNo = new ArrayList<String>();
			// 获取请求URL
			url = persistence.getWebURL(pos_purchase_config, "getTerminalNo");
			// post请求并返回结果
			obj1 = new HttpRequestor().doPost(url, map);
			// 获取具体数据对象：data
			jsonObj1 = obj1.getJSONObject("data");
			if (jsonObj1 != null) {
				// 获取已经使用的终端编号
				if (jsonObj1.get("confTerminalNos") != null) {
					Object confTerminalNosStr = jsonObj1.get("confTerminalNos");
					System.out.println(confTerminalNosStr);
					if (!"null".equals(confTerminalNosStr.toString())) {
						jsonArray1 = jsonObj1.getJSONArray("confTerminalNos");
					}
				}
				// 获取为使用的终端编号
				if (jsonObj1.get("terminalNos") != null) {
					jsonArray2 = jsonObj1.getJSONArray("terminalNos");
				}
				// 遍历已经使用的终端编号
				if (jsonArray1 != null && jsonArray1.size() > 0) {
					for (int i = 0; i < jsonArray1.size(); i++) {
						tempUserNo = jsonArray1.get(i).toString();
						tempUserNo = tempUserNo.substring(
								tempUserNo.length() - 4, tempUserNo.length());
						listUserNo.add(tempUserNo);
					}
				}
				// 遍历未使用的终端编号
				if (jsonArray2 != null && jsonArray2.size() > 0) {
					for (int i = 0; i < jsonArray2.size(); i++) {
						tempNoUserNo = jsonArray2.get(i).toString();
						tempNoUserNo = tempNoUserNo.substring(
								tempNoUserNo.length() - 4,
								tempNoUserNo.length());
						listNoUserNo.add(tempNoUserNo);
					}
				}
				// 放入到Map
				if (listUserNo != null && listUserNo.size() > 0) {
					resultMap.put("userNo", listUserNo.toArray());
				}
				if (listNoUserNo != null && listNoUserNo.size() > 0) {
					resultMap.put("noUserNo", listNoUserNo.toArray());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询终端编号失败，接入服务异常：" + e);
		}
		return resultMap;
	}

	/**
	 * 4获取秘钥，申购配置单用到
	 * 
	 * @param map
	 *            maps.put("entResNo","1"); maps.put("deviceNo","2" );
	 *            maps.put("operCustId", "3"); maps.put("machineNo", "4");
	 *            maps.put("sNewVersion", "true"); maps.put("operCustId",
	 *            "pos01");
	 * @param persistence
	 * @return
	 */
	public Map<String, Object> accessGetPmk(Map<String, Object> map,
			Persistence persistence) {
		Map<String, Object> mapResult = new HashMap<String, Object>();
		String url = null;
		JSONObject obj = null;
		JSONObject objdata = null;
		byte[] dataByte = null;
		try {
			url = persistence.getWebURL(pos_purchase_config, "getPmk");
			obj = new HttpRequestor().doPost(url, map);
			objdata = obj.getJSONObject("data");
			if (objdata != null) {
				GenPmkResult genPmkResult = jsonToGenPmkResult(objdata);
				if (genPmkResult != null) {
					PosUtil p = new PosUtil();
					dataByte = p.pmkDone(genPmkResult);
					if (dataByte != null) {
						mapResult.put("pmk", dataByte);
						mapResult.put("deviceNo", genPmkResult.getPosInfo()
								.getPosNo());// 设备序列号，也就是终端编号
					}
				}
			} else {
				mapResult.put("resultDesc", obj.getString("resultDesc"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("获取秘钥失败，接入服务异常：" + e.getMessage());
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
			LOGGER.error("秘钥数据转换失败" + e.getMessage());
		}
		return genPmkResult;
	}

	/**
	 * 5烧机成功之后，修改配置关联关系状态，用申购订单
	 * 
	 * @param map
	 *            请求参数
	 * @param persistence
	 *            配置类
	 * @param poslogger
	 *            烧机日志类
	 * @param operator
	 *            操作员账户
	 * @return 返回异常信息，表示失败，否则成功
	 */
	public boolean accessUpdatePosStatus(Map<String, Object> map,
			Persistence persistence) {
		String url = null;
		JSONObject obj = null;
		boolean updateFlag = false;
		try {
			// 获取请求URL
			url = persistence.getWebURL(pos_purchase_config, "updatePosStatus");
			// 请求并且返回结果
			obj = new HttpRequestor().doPost(url, map);
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
