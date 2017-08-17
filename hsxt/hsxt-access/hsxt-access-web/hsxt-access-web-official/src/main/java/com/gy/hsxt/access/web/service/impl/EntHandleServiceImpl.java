/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.service.impl;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import com.gy.hsxt.bs.bean.tool.CardProvideApply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.bean.HttpResp;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.service.IEntHandleService;
import com.gy.hsxt.bs.api.apply.IBSOfficialWebService;
import com.gy.hsxt.bs.api.order.IBSOrderService;
import com.gy.hsxt.bs.bean.apply.EntBaseInfo;
import com.gy.hsxt.bs.bean.apply.EntInfo;
import com.gy.hsxt.bs.bean.apply.IntentCust;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.Base64Utils;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.lcs.bean.City;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.bean.Province;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.ent.AsEntInfo;

/**
 * 企业办理接口实现类
 * 
 * @Package: com.gy.hsxt.access.web.service.impl
 * @ClassName: EntHandleServiceImpl
 * @Description: TODO
 * @author: likui
 * @date: 2015年12月9日 下午5:37:39
 * @company: gyist
 * @version V3.0.0
 */
@Service("entHandleService")
public class EntHandleServiceImpl implements IEntHandleService {

	/** 地区平台配送Service **/
	@Autowired
	private LcsClient lcsClient;

	/** 官网Service **/
	@Autowired
	private IBSOfficialWebService officialWebService;

	/** 用户中心企业信息Service **/
	@Autowired
	private IUCAsEntService asEntService;

	/** 业务服务订单Service **/
	@Autowired
	private IBSOrderService orderService;

	/**
	 * 查询所有服务公司
	 * 
	 * @Description:
	 * @param cityName
	 * @return
	 */
	@Override
	public HttpResp selectAllEntInfoS(String cityName)
	{
		BizLog.biz(this.getClass().getName(), "function:selectAllEntInfoS", "", "params==>" + cityName + ">>>>查询所有服务公司");
		if (StringUtils.isBlank(cityName))
		{
			return new HttpResp(ASRespCode.AS_PARAM_INVALID.getCode());
		}
		List<AsEntInfo> list = null;
		try
		{
			List<String> cityNo = getCistyNoByName(URLDecoder.decode(new String(Base64Utils.decode(cityName)), "UTF-8"));
			if (StringUtils.isBlank(cityNo))
			{
				return new HttpResp(RespCode.FAIL.getCode());
			}
			// 调用用户中心查询地区平台所有服务公司
			list = asEntService.listCityListEntInfo(cityNo);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "selectAllEntInfoS", "调用UC查询地区平台所有服务公司", ex);
		}
		return new HttpResp(RespCode.SUCCESS.getCode(), list);
	}

	/**
	 * 根据城市名称查询城市列表
	 * 
	 * @param cityName
	 * @return
	 */
	private List<String> getCistyNoByName(String cityName)
	{
		List<String> cityNo = new ArrayList<String>();
		try
		{
			LocalInfo info = lcsClient.getLocalInfo();
			List<Province> provinces = lcsClient.queryProvinceByParent(info.getCountryNo());
			for (Province province : provinces)
			{
				List<City> citys = lcsClient.queryCityByParent(info.getCountryNo(), province.getProvinceNo());
				for (City city : citys)
				{
					if (cityName.indexOf(city.getCityName()) != -1 || city.getCityName().indexOf(cityName) != -1)
					{
						cityNo.add(city.getCityNo());
					}
				}
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:getCistyNoByName", "调用LCS获取地区平台地区信息失败", ex);
			return null;
		}
		return cityNo;
	}

	/**
	 * 提交意向客户申请
	 * 
	 * @Description:
	 * @param intent
	 * @return
	 */
	@Override
	public HttpResp submitIntentionCustApply(String intent)
	{
		BizLog.biz(this.getClass().getName(), "function:submitIntentionCustApply", "params==>" + intent, "提交意向客户申请");
		IntentCust bean = null;
		try
		{
			intent = new String(Base64Utils.decode(intent));
			bean = JSONObject.parseObject(URLDecoder.decode(intent, "UTF-8"), IntentCust.class);
			// 调用业务服务添加意向客户
			officialWebService.createIntentCust(bean);
			return new HttpResp(RespCode.SUCCESS.getCode());
		} catch (HsException ex)
		{
			return new HttpResp(ex.getErrorCode());
		} catch (Exception ex)
		{
			SystemLog
					.error(this.getClass().getName(), "function:submitIntentionCustApply", "调用BS提交意向客户申请失败" + bean, ex);
		}
		return new HttpResp(RespCode.FAIL.getCode());
	}

	/**
	 * 根据名称查询企业信息
	 * 
	 * @Description:
	 * @param entCustName
	 * @return
	 */
	@Override
	public HttpResp selectEntInfoByName(String entCustName)
	{
		BizLog.biz(this.getClass().getName(), "function:selectEntInfoByName", "params==>" + entCustName, "根据名称查询企业信息");
		try
		{
			// 调用业务服务查询企业信息
			List<EntBaseInfo> baseInfo = officialWebService.queryEntInfo(entCustName);
			if (StringUtils.isNotBlank(baseInfo))
			{
				for (EntBaseInfo info : baseInfo)
				{
					info.setAddress(getCityFullName(info.getCountryNo(), info.getProvinceNo(), info.getCityNo()));
				}
				return new HttpResp(RespCode.SUCCESS.getCode(), baseInfo);
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:submitIntentionCustApply", "调用BS查询企业信息失败"
					+ entCustName, ex);
		}
		return new HttpResp(RespCode.FAIL.getCode());
	}

	/**
	 * 根据授权码查询企业信息
	 * 
	 * @Description:
	 * @param authCode
	 * @param retData
	 *            true:返回数据,false:不返回数据
	 * @return
	 */
	@Override
	public HttpResp selectEntInfoByCode(String authCode, Boolean retData)
	{
		BizLog.biz(this.getClass().getName(), "function:selectEntInfoByCode", "params==>" + authCode, "根据授权码查询企业信息");
		// 返回json对象
		JSONObject json = null;
		// 企业信息对象
		EntInfo info = null;
		// 地区平台公共信息
		LocalInfo local = null;
		try
		{
			// 调用配置获取地区平台信息
			local = lcsClient.getLocalInfo();
			// 调用业务服务获取企业信息
			info = officialWebService.queryEntInfoByAuthCode(authCode);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:selectEntInfoByCode",
					ASRespCode.AS_NOT_QUERY_DATA.getCode() + "查询企业信息或平台银行信息失败,平台信息" + local + ",企业信息:" + info, ex);
			return new HttpResp(RespCode.FAIL.getCode());
		}

		if (StringUtils.isNotBlank(info))
		{
			json = new JSONObject();

			JSONObject platBankInfo = new JSONObject();
			platBankInfo.put("payTypeName", getPayTypeName(info.getCustType()));
			platBankInfo.put("currencyNameCn", local.getCurrencyNameCn());
			platBankInfo.put("amount", info.getAmount());
			platBankInfo.put("bankAccName", "");
			platBankInfo.put("bankName", "");
			platBankInfo.put("bankAccNo", "");

			info.setAddress(getCityFullName(info.getCountryNo(), info.getProvinceNo(), info.getCityNo()));

			json.put("entInfo", info);
			json.put("platBankInfo", platBankInfo);
			return new HttpResp(RespCode.SUCCESS.getCode(), retData ? json : null);
		}
		return new HttpResp(RespCode.FAIL.getCode());
	}

	/**
	 * 查询公告期的企业
	 * 
	 * @Description:
	 * @return
	 */
	@Override
	public HttpResp selectNoticeEnt()
	{
		BizLog.biz(this.getClass().getName(), "function:selectNoticeEnt", "", "查询公告期的企业");
		try
		{
			// 调用业务服务获取公告期的企业信息
			List<EntBaseInfo> baseInfo = officialWebService.queryPlacardEnt();
			if (StringUtils.isNotBlank(baseInfo))
			{
				for (EntBaseInfo info : baseInfo)
				{
					info.setAddress(getCityFullName(info.getCountryNo(), info.getProvinceNo(), info.getCityNo()));
				}
				return new HttpResp(RespCode.SUCCESS.getCode(), baseInfo);
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:selectNoticeEnt", "调用BS查询公告期的企业失败", ex);
		}
		return new HttpResp(RespCode.FAIL.getCode());
	}

	/**
	 * 获取网银支付地址
	 * 
	 * @Description:
	 * @param orderNo
	 * @return
	 */
	@Override
	public HttpResp getEBankPayUrl(String orderNo)
	{
		BizLog.biz(this.getClass().getName(), "function:getEBankPayUrl", "params==>" + orderNo, "获取网银支付地址");
		try
		{
			// 调用业务服务获取网银支付URL
			return new HttpResp(RespCode.SUCCESS.getCode(), orderService.getPayUrl(PayChannel.CARD_PAY.getCode(),
					orderNo, null, null));
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:getEBankPayUrl", "调用BS获取网银支付地址失败" + orderNo, ex);
		}
		return new HttpResp(RespCode.FAIL.getCode());
	}

	/**
	 * 提交互生卡发放申请
	 *
	 * @Description:
	 * @param cardProvice
	 * @return
	 */
	@Override
	public HttpResp submitCardProvideApply(String cardProvice)
	{
		BizLog.biz(this.getClass().getName(), "function:submitCardProvideApply", "params==>" + cardProvice, "提交互生卡发放申请");
		CardProvideApply bean = null;
		try
		{
			cardProvice = new String(Base64Utils.decode(cardProvice));
			bean = JSONObject.parseObject(URLDecoder.decode(cardProvice, "UTF-8"), CardProvideApply.class);
			// 调用业务服务添加意向客户
			officialWebService.addCardProvideApply(bean);
			return new HttpResp(RespCode.SUCCESS.getCode());
		} catch (HsException ex)
		{
			return new HttpResp(ex.getErrorCode());
		} catch (Exception ex)
		{
			SystemLog
					.error(this.getClass().getName(), "function:submitCardProvideApply", "调用BS提交互生卡发放申请失败" + bean, ex);
		}
		return new HttpResp(RespCode.FAIL.getCode());
	}

	/**
	 * 获取城市全称
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月11日 下午4:35:48
	 * @param countryNo
	 *            国家编号
	 * @param provinceNo
	 *            省编号
	 * @param cityNo
	 *            城市编号
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	private String getCityFullName(String countryNo, String provinceNo, String cityNo)
	{
		try
		{
			City city = lcsClient.getCityById(countryNo, provinceNo, cityNo);
			return StringUtils.isNotBlank(city) ? city.getCityFullName() : countryNo + provinceNo + cityNo;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:getCityFullName", "调用LCS获取地区平台地区信息失败", ex);
		}
		return null;
	}

	/**
	 * 获取费用类型
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月15日 下午4:34:56
	 * @param custType
	 *            客户类型
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	private String getPayTypeName(int custType)
	{
		switch (custType)
		{
		case 2:
			return "成员企业资源费";

		case 3:
			return "托管企业资源费";

		case 4:
			return "服务公司资源费";

		default:
			return "";
		}
	}
}
