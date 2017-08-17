/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.services.systemBusiness.imp;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gy.hsxt.bs.bean.tool.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.bean.CompanyBase;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.company.services.systemBusiness.IToolManageService;
import com.gy.hsxt.bp.api.IBusinessParamSearchService;
import com.gy.hsxt.bp.bean.BusinessCustParamItemsRedis;
import com.gy.hsxt.bs.api.tool.IBSToolOrderService;
import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.order.DeliverInfo;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.tool.resultbean.EntResource;
import com.gy.hsxt.bs.bean.tool.resultbean.OrderEnt;
import com.gy.hsxt.bs.bean.tool.resultbean.ResourceSegment;
import com.gy.hsxt.bs.bean.tool.resultbean.SpecialCardStyle;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolOrderResult;
import com.gy.hsxt.bs.common.enumtype.order.OrderChannel;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.bs.common.enumtype.tool.CategoryCode;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.StartResType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.common.IUCAsReceiveAddrInfoService;
import com.gy.hsxt.uc.as.api.device.IUCAsDeviceService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsReceiveAddr;
import com.gy.hsxt.uc.as.bean.device.AsDevice;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;

/**
 * 工具管理(终端)Service
 * 
 * @Package: com.gy.hsxt.access.web.company.services.systemBusiness.imp
 * @ClassName: ToolManageServiceImpl
 * @Description: TODO
 * @author: likui
 * @date: 2016年1月11日 下午3:58:20
 * @company: gyist
 * @version V3.0.0
 */
@Service
public class ToolManageServiceImpl extends BaseServiceImpl<ToolManageServiceImpl> implements IToolManageService {

	/** 地区平台配送Service **/
	@Autowired
	private LcsClient lcsClient;

	/**
	 * BS工具订单Service
	 */
	@Autowired
	private IBSToolOrderService toolOrderService;

	/**
	 * UC查企业信息Service
	 */
	@Autowired
	private IUCAsEntService iuCAsEntService;

	/**
	 * UC查企业信息Service
	 */
	@Autowired
	private IUCAsReceiveAddrInfoService uCAsReceiveAddrInfoService;

	/**
	 * UC密码Service
	 */
	@Autowired
	private IUCAsPwdService ucAsPwdService;

	/**
	 * UC设备Service
	 */
	@Autowired
	private IUCAsDeviceService iuCAsDeviceService;

	/**
	 * 参数配置系统
	 */
	@Autowired
	private IBusinessParamSearchService businessParamSearchService;

	/**
	 * 根据客户类型查询可购买的工具
	 * 
	 * @Description:
	 * @param custType
	 * @param toolServie
	 * @return
	 * @throws HsException
	 */
	@Override
	public List<ToolProduct> findMayBuyTools(Integer custType, String toolServie) throws HsException
	{
		try
		{
			return toolOrderService.queryMayBuyToolProductByToolSevice(custType, toolServie);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findMayBuyTools", "调用BS查询可购买的工具失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 新增收货地址
	 * 
	 * @Description:
	 * @param entCustId
	 * @param addr
	 * @throws HsException
	 */
	@Override
	public void addDeliverAddress(String entCustId, String addr) throws HsException
	{
		AsReceiveAddr bean = null;
		try
		{
			bean = JSONObject.parseObject(URLDecoder.decode(addr, "UTF-8"), AsReceiveAddr.class);
			vaildDeliverAddrParam(bean, "add");
			uCAsReceiveAddrInfoService.addReceiveAddr(entCustId, bean);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "addDeliverAddress", "新增收货地址失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 验证收货地址参数
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月26日 上午10:49:13
	 * @param bean
	 * @return : void
	 * @version V3.0.0
	 */
	private void vaildDeliverAddrParam(AsReceiveAddr bean, String type) throws HsException
	{
		RequestUtil.verifyParamsIsNotEmpty(new Object[]
		{ bean.getReceiver(), ASRespCode.EW_RECEIVER_INVALID.getCode(), ASRespCode.EW_RECEIVER_INVALID.getDesc() });
		RequestUtil
				.verifyParamsIsNotEmpty(new Object[]
				{ bean.getProvinceNo(), ASRespCode.EW_PROVINCENO_INVALID.getCode(),
						ASRespCode.EW_PROVINCENO_INVALID.getDesc() });
		RequestUtil.verifyParamsIsNotEmpty(new Object[]
		{ bean.getCityNo(), ASRespCode.EW_CITYNO_INVALID.getCode(), ASRespCode.EW_CITYNO_INVALID.getDesc() });
		RequestUtil.verifyParamsIsNotEmpty(new Object[]
		{ bean.getAddress(), ASRespCode.EW_ADDRESS_INVALID.getCode(), ASRespCode.EW_ADDRESS_INVALID.getDesc() });
		RequestUtil.verifyParamsIsNotEmpty(new Object[]
		{ bean.getMobile(), ASRespCode.EW_MOBILE_INVALID.getCode(), ASRespCode.EW_MOBILE_INVALID.getDesc() });
		if (type.equals("modify"))
		{
			RequestUtil.verifyParamsIsNotEmpty(new Object[]
			{ bean.getAddrId(), ASRespCode.EW_ADDRID_INVALID.getCode(), ASRespCode.EW_ADDRID_INVALID.getDesc() });
		}
	}

	/**
	 * 修改收货地址
	 * 
	 * @Description:
	 * @param entCustId
	 * @param addr
	 * @throws HsException
	 */
	@Override
	public void modifyDeliverAddress(String entCustId, String addr) throws HsException
	{
		AsReceiveAddr bean = null;
		try
		{
			bean = JSONObject.parseObject(URLDecoder.decode(addr, "UTF-8"), AsReceiveAddr.class);
			vaildDeliverAddrParam(bean, "modify");
			uCAsReceiveAddrInfoService.updateReceiveAddr(entCustId, bean);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "modifyDeliverAddress", "修改收货地址失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 查询收货地址
	 * 
	 * @Description:
	 * @param entCustId
	 * @param addrId
	 * @return
	 * @throws HsException
	 */
	@Override
	public AsReceiveAddr queryDeliverAddress(String entCustId, Long addrId) throws HsException
	{
		try
		{
			return uCAsReceiveAddrInfoService.searchReceiveAddrInfo(entCustId, addrId);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryDeliverAddress", "调用UC查询收货地址失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 删除收货地址
	 * 
	 * @Description:
	 * @param addrId
	 * @throws HsException
	 */
	@Override
	public void removeDeliverAddress(String entCustId, Long addrId) throws HsException
	{
		try
		{
			uCAsReceiveAddrInfoService.deleteReceiveAddr(entCustId, addrId);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findMayBuyTools", "调用UC删除收货地址失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 查询企业收货地址
	 * 
	 * @Description:
	 * @param entCustId
	 * @return
	 * @throws HsException
	 */
	@Override
	public JSONArray findEntDeliverAddress(String entCustId) throws HsException
	{
		// 企业名称
		String entName = "";
		// 地址集合
		JSONArray jsons = null;
		// 地址
		JSONObject json = null;
		try
		{
			AsEntAllInfo entInfo = iuCAsEntService.searchEntAllInfo(entCustId);
			// 验证调用企业信息
			HsAssert.notNull(entInfo, ASRespCode.AS_NOT_QUERY_ENT_INFO, "未查询到企业公司信息");
			HsAssert.notNull(entInfo.getMainInfo(), ASRespCode.AS_NOT_QUERY_ENT_INFO, "未查询到企业公司信息");
			HsAssert.notNull(entInfo.getBaseInfo(), ASRespCode.AS_NOT_QUERY_ENT_INFO, "未查询到企业公司信息");
			// 企业名称
			entName = StringUtils.isNotBlank(entInfo.getBaseInfo().getEntName()) ? entInfo.getBaseInfo().getEntName()
					: entInfo.getBaseInfo().getEntNameEn();

			jsons = new JSONArray();

			// 联系人地址
			json = new JSONObject();
			json.put("linkMan", entInfo.getMainInfo().getContactPerson());// 联系人
			json.put("mobile", entInfo.getMainInfo().getContactPhone());// 联系人电话
			json.put("fullAddr", entInfo.getBaseInfo().getContactAddr());// 联系人地址
			json.put("entName", entName);// 企业名称
			json.put("zipCode", entInfo.getBaseInfo().getPostCode());// 邮编
			json.put("addrType", "contact");// 地址类型
			json.put("isDefault", "0");// 是否默认地址
			jsons.add(json);

			// 企业地址
			json = new JSONObject();
			json.put("linkMan", entInfo.getMainInfo().getContactPerson());// 联系人
			json.put("mobile", entInfo.getMainInfo().getContactPhone());// 联系人电话
			json.put("fullAddr", entInfo.getMainInfo().getEntRegAddr());// 企业地址
			json.put("entName", entName);// 企业名称
			json.put("zipCode", entInfo.getBaseInfo().getPostCode());// 邮编
			json.put("addrType", "ent");// 地址类型
			json.put("isDefault", "0");// 是否默认地址
			json.put("addrId", null);// 地址ID
			jsons.add(json);

			// 查询企业自定义地址
			List<AsReceiveAddr> addrs = uCAsReceiveAddrInfoService.listReceiveAddrByCustId(entCustId);
			if (StringUtils.isNotBlank(addrs))
			{
				for (AsReceiveAddr addr : addrs)
				{
					// 自定义地址
					json = new JSONObject();
					json.put("linkMan", addr.getReceiver());// 联系人
					json.put("mobile", addr.getMobile());// 联系人电话
					json.put("fullAddr", addr.getAddress());// 企业地址
					json.put("entName", entName);// 企业名称
					json.put("zipCode", addr.getPostCode());// 邮编
					json.put("addrType", "auto");// 地址类型
					json.put("isDefault", addr.getIsDefault());// 是否默认地址
					json.put("addrId", addr.getAddrId());// 地址ID
					json.put("countryNo", addr.getCountryNo());// 国家编号
					json.put("provinceNo", addr.getProvinceNo());// 省编号
					json.put("cityNo", addr.getCityNo());// 城市编号
					jsons.add(json);
				}
			}
			return jsons;
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findEntDeliverAddress", "调用UC查询企业收货地址失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 查询某企业客户可以购买某种工具产品的数量
	 * 
	 * @Description:
	 * @param entCustId
	 * @param toolType
	 * @return
	 * @throws HsException
	 */
	@Override
	public int findMayToolNum(String entCustId, String toolType) throws HsException
	{
		try
		{
			return toolOrderService.queryMayBuyToolNum(entCustId, toolType);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findMayToolNum", "调用BS查询客户可以购买某种工具产品的数量失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 提交申购订单
	 * 
	 * @Description:
	 * @param entCustId
	 *            客户号
	 * @param custEntName
	 *            客户名称
	 * @param custId
	 *            操作员客户号
	 * @param confs
	 *            配置单列表
	 * @param addr
	 *            收货地址
	 * @param operName
	 *            创建人（当前系统登录人）姓名
	 * @return
	 */
	@Override
	public ToolOrderResult commitToolOrder(String entCustId, String custEntName, String custId, String confs,
			String addr, String operName, String orderOperator)
	{
		// 配置单列表
		List<ToolConfig> configs = null;
		ToolConfig config = null;
		// 收货地址
		DeliverInfo deliver = null;
		// 申购工具订单
		OrderBean bean = null;
		// 收货地址JSON对象
		JSONObject json = null;
		// 配置单JSON集合对象
		JSONArray jsons = null;
		// 订单实体
		Order order = null;
		// 互生号
		String entResNo = entCustId.substring(0, 11);
		// 下单成功返回实体
		ToolOrderResult ret = null;
		try
		{
			// 创建对象
			jsons = JSONArray.parseArray(URLDecoder.decode(confs, "UTF-8"));
			json = JSONObject.parseObject(URLDecoder.decode(addr, "UTF-8"));
			configs = new ArrayList<ToolConfig>();
			bean = new OrderBean();

			// 收货地址设置参数
			deliver = new DeliverInfo();
			deliver.setHsCustId(entCustId);
			deliver.setStreetAddr(json.getString("fullAddr"));
			deliver.setFullAddr(json.getString("fullAddr") + "(" + json.getString("entName") + ")");
			deliver.setLinkman(json.getString("linkMan"));
			deliver.setMobile(json.getString("mobile"));
			deliver.setZipCode(json.getString("zipCode"));
			deliver.setCreatedby(custId);
			// 验证收货地址参数
			vaildOrderParam(deliver, null);
			// 配置单设置参数
			for (int i = 0; i < jsons.size(); i++)
			{
				json = jsons.getJSONObject(i);
				config = new ToolConfig();
				config.setCategoryCode(json.getString("categoryCode"));
				config.setPrice(json.getString("price"));
				config.setProductId(json.getString("productId"));
				config.setProductName(json.getString("productName"));
				config.setUnit(json.getString("unit"));
				config.setQuantity(CommonUtils.toInteger(json.getString("quanilty")));
				config.setHsCustId(entCustId);
				config.setHsResNo(entResNo);
				config.setConfUser(custId);
				config.setDescription(custEntName + "新增申购工具下单");
				// 验证配置单参数
				vaildOrderParam(null, config);
				configs.add(config);
			}
			// 订单设置参数
			order = new Order();
			order.setCustId(entCustId);
			order.setCustName(custEntName);
			order.setHsResNo(entResNo);
			order.setCustType(HsResNoUtils.getCustTypeByHsResNo(entResNo));
			order.setOrderChannel(OrderChannel.WEB.getCode());
			order.setOrderRemark("新增申购工具下单");
			order.setOrderType(OrderType.BUY_TOOL.getCode());
			order.setOrderOperator(orderOperator);

			// 设置申购订单参数
			bean.setOrder(order);
			bean.setConfs(configs);
			bean.setDeliverInfo(deliver);

			ret = toolOrderService.addToolBuyOrder(bean);
			HsAssert.notNull(ret, ASRespCode.AS_BIZ_OPT_FAII_RESET);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findMayToolNum", "调用BS工具订单支付失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
		return ret;
	}

	/**
	 * 系统资源购买下单
	 * 
	 * @Description:
	 * @param entCustId
	 *            客户号
	 * @param custEntName
	 *            考核名称
	 * @param custId
	 *            操作员客户号
	 * @param product
	 *            工具
	 * @param addr
	 *            地址
	 * @param segmentId
	 *            资源段
	 * @param operName
	 *            创建人（当前系统登录人）姓名
	 * @return
	 * @throws HsException
	 */
	@Override
	public ToolOrderResult commitToolOrderCard(String entCustId, String custEntName, String custId, String product,
			String addr, String segmentId, String operName) throws HsException
	{
		// 配置单列表
		List<ToolConfig> configs = null;
		ToolConfig config = null;
		// 工具参数
		ToolProduct productTool = null;
		// 收货地址
		DeliverInfo deliver = null;
		// 申购工具订单
		OrderBean bean = null;
		// 收货地址JSON对象
		JSONObject json = null;
		// 订单实体
		Order order = null;
		// 互生号
		String entResNo = entCustId.substring(0, 11);
		// 资源段Id
		List<String> segmentIds = null;
		// 资源段
		List<ResourceSegment> segments = null;

		// 下单成功返回实体
		ToolOrderResult ret = null;
		try
		{
			json = JSONObject.parseObject(URLDecoder.decode(addr, "UTF-8"));
			productTool = JSONObject.parseObject(URLDecoder.decode(product, "UTF-8"), ToolProduct.class);
			//segmentIds = JSONObject.parseArray(URLDecoder.decode(segmentId, "UTF-8"), String.class);
			segments = JSONObject.parseArray(URLDecoder.decode(segmentId, "UTF-8"), ResourceSegment.class);

			configs = new ArrayList<ToolConfig>();
			bean = new OrderBean();

			// 收货地址设置参数
			deliver = new DeliverInfo();
			deliver.setHsCustId(entCustId);
			deliver.setStreetAddr(json.getString("fullAddr"));
			deliver.setFullAddr(json.getString("fullAddr") + "(" + json.getString("entName") + ")");
			deliver.setLinkman(json.getString("linkMan"));
			deliver.setMobile(json.getString("mobile"));
			deliver.setZipCode(json.getString("zipCode"));
			deliver.setCreatedby(custId);
			// 验证收货地址参数
			vaildOrderParam(deliver, null);
			// 获取获取卡数量和金额
			String[] cardAndAmount = validResourceSegment(segments);

			// 配置单
			config = new ToolConfig();
			config.setSegmentIds(segmentIds);
			config.setCategoryCode(productTool.getCategoryCode());
			config.setPrice(productTool.getPrice());
			config.setProductId(productTool.getProductId());
			config.setProductName(productTool.getProductName());
			config.setUnit(productTool.getUnit());
			config.setQuantity(CommonUtils.toInteger(cardAndAmount[0]));
			config.setTotalAmount(cardAndAmount[1]);
			config.setHsCustId(entCustId);
			config.setHsResNo(entResNo);
			config.setConfUser(custId);
			config.setDescription(custEntName + "新增申购工具下单");
			// 验证配置单参数
			vaildOrderParam(null, config);
			configs.add(config);

			// 订单设置参数
			order = new Order();
			order.setCustId(entCustId);
			order.setCustName(custEntName);
			order.setHsResNo(entResNo);
			order.setOrderHsbAmount(cardAndAmount[1]);
			order.setCustType(HsResNoUtils.getCustTypeByHsResNo(entResNo));
			order.setOrderChannel(OrderChannel.WEB.getCode());
			order.setOrderRemark("新增系统资源下单");
			order.setOrderType(OrderType.APPLY_PERSON_RESOURCE.getCode());
			order.setOrderOperator(operName);

			// 设置申购订单参数
			bean.setOrder(order);
			bean.setConfs(configs);
			bean.setDeliverInfo(deliver);

			ret = toolOrderService.addToolBuyOrder(bean);
			HsAssert.notNull(ret, RespCode.FAIL);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findMayToolNum", "调用BS工具订单支付失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
		return ret;
	}

	/**
	 * 获取卡数量和金额
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月15日 下午5:45:02
	 * @param segments
	 * @return
	 * @throws HsException
	 * @return : String[]
	 * @version V3.0.0
	 */
	private String[] validResourceSegment(List<ResourceSegment> segments) throws HsException
	{
		if (null == segments || segments.size() <= 0)
		{
			return null;
		}
		int cardCount = 0;
		String amount = "0";
		for (ResourceSegment res : segments)
		{
			// 每段价格不是20000
			HsAssert.isTrue("20000".equals(res.getSegmentPrice()), ASRespCode.EW_SEGMENT_PRICE_ERROR);
			// 每段卡数量不是1000或最后一段不是999||1000
			if (res.getSegmentCount() == 10)
			{
				HsAssert.isTrue(("999".equals(res.getCardCount()) || "1000".equals(res.getCardCount())),
						ASRespCode.EW_SEGMENT_CARD_COUNT_ERROR);
			} else
			{
				HsAssert.isTrue("1000".equals(res.getCardCount()), ASRespCode.EW_SEGMENT_CARD_COUNT_ERROR);
			}
			cardCount = cardCount + CommonUtils.toInteger(res.getCardCount());
			amount = BigDecimalUtils.ceilingAdd(amount, res.getSegmentPrice()).toString();
		}
		return new String[]
		{ String.valueOf(cardCount), amount };
	}

	/**
	 * 互生币支付限额
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年2月26日 下午2:18:23
	 * @param amount
	 * @param entCustId
	 * @return : void
	 * @version V3.0.0
	 */
	public void checkOrderAmountIsOverLimit(String amount, String entCustId)
	{
		// 互生币支付code
		// String code = BusinessParam.HSB_PAYMENT.getCode();
		// 企业互生币支付单笔限额code
		String codeMin = BusinessParam.ENT_PAYMENT_MAX.getCode();
		// 企业互生币支付当日限额code
		String codeDayMax = BusinessParam.ENT_PAYMENT_DAY_MAX.getCode();

		BusinessCustParamItemsRedis business = businessParamSearchService.searchCustParamItemsByIdKey(entCustId,
				codeMin);
		// 企业互生币支付单笔限额
		String amountmin = (null == business || StringUtils.isBlank(business.getSysItemsValue())) ? "0" : business
				.getSysItemsValue();
		business = businessParamSearchService.searchCustParamItemsByIdKey(entCustId, codeDayMax);
		// 企业互生币支付当日限额code
		String maxAmountMax = (null == business || StringUtils.isBlank(business.getSysItemsValue())) ? "0" : business
				.getSysItemsValue();
		if (!"0".equals(amountmin))
		{
			if (BigDecimalUtils.compareTo(amount, amountmin) > 0)
			{
				throw new HsException(ASRespCode.EW_HSB_PAY_OVER_LIMIT);
			}
		}
		if (!"0".equals(maxAmountMax))
		{
			if (BigDecimalUtils.compareTo(amount, maxAmountMax) > 0)
			{
				throw new HsException(ASRespCode.EW_HSB_PAY_OVER_LIMIT);
			}
		}
	}

	/**
	 * 验证订单参数
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月30日 下午2:20:38
	 * @param deliver
	 * @param conf
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	private void vaildOrderParam(DeliverInfo deliver, ToolConfig conf) throws HsException
	{
		if (StringUtils.isNotBlank(deliver))
		{
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[]
					{ deliver.getStreetAddr(), ASRespCode.EW_FULL_ADDR_INVALID.getCode(),
							ASRespCode.EW_FULL_ADDR_INVALID.getDesc() },
					new Object[]
					{ deliver.getStreetAddr(), ASRespCode.EW_LINKMAN_INVALID.getCode(),
							ASRespCode.EW_LINKMAN_INVALID.getDesc() },
					new Object[]
					{ deliver.getMobile(), ASRespCode.EW_MOBILE_INVALID.getCode(),
							ASRespCode.EW_MOBILE_INVALID.getDesc() });
		}
		if (StringUtils.isNotBlank(conf))
		{
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[]
					{ conf.getCategoryCode(), ASRespCode.EW_CATEGORYCODE_INVALID.getCode(),
							ASRespCode.EW_CATEGORYCODE_INVALID.getDesc() },
					new Object[]
					{ conf.getProductId(), ASRespCode.EW_PROCUCT_ID_INVALID.getCode(),
							ASRespCode.EW_PROCUCT_ID_INVALID.getDesc() }, new Object[]
					{ conf.getProductName(), ASRespCode.EW_PRODUCT_NAME_INVALID.getCode(),
							ASRespCode.EW_PRODUCT_NAME_INVALID.getDesc() }, new Object[]
					{ conf.getPrice(), ASRespCode.EW_PRODUCT_PRICE_INVALID.getCode(),
							ASRespCode.EW_PRODUCT_PRICE_INVALID.getDesc() }, new Object[]
					{ conf.getQuantity(), ASRespCode.EW_PRODUCT_QUANTITY_INVALID.getCode(),
							ASRespCode.EW_PRODUCT_QUANTITY_INVALID.getDesc() });
		}
	}

	/**
	 * 工具订单支付
	 * 
	 * @Description:
	 * @param entCustId
	 * @param tradePwd
	 * @param randomToken
	 * @param bean
	 * @return
	 * @throws HsException
	 */
	@Override
	public String toolOrderPayment(String entCustId, String tradePwd, String randomToken, ToolOrderPay bean,
			String orderAmount) throws HsException
	{
		try
		{
			// 地区平台信息
			LocalInfo info = lcsClient.getLocalInfo();
			// 互生币支付验证交易密码
			if (bean.getPayChannel() == PayChannel.HS_COIN_PAY.getCode().intValue())
			{
				// 互生币支付限额
				// checkOrderAmountIsOverLimit(orderAmount, entCustId);

				ucAsPwdService.checkTradePwd(entCustId, tradePwd, UserTypeEnum.ENT.getType(), randomToken);
			}
			bean.setJumpUrl(info.getWebPayJumpUrl());
			return toolOrderService.buyToolOrderToPay(bean);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "toolOrderPayment", "调用BS工具订单支付失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 分页查询企业工具订单
	 * 
	 * @Description:
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public PageData<OrderEnt> queryToolOrderList(Map filterMap, Map sortMap, Page page)
	{
		BaseParam bean = new BaseParam();
		bean.setStartDate((String) filterMap.get("beginDate"));
		bean.setEndDate((String) filterMap.get("endDate"));
		bean.setHsResNo((String) filterMap.get("pointNo"));
		bean.setHsCustId((String) filterMap.get("entCustId"));
		bean.setOrderNo((String) filterMap.get("orderNo"));
		bean.setChannel(CommonUtils.toInteger(filterMap.get("channel")));
		bean.setStatus(CommonUtils.toInteger(filterMap.get("status")));
		try
		{
			return toolOrderService.queryToolOrderEntByPage(bean, page);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryToolOrderList", "调用BS分页查询企业工具订单失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 查询工具订单详情
	 * 
	 * @Description:
	 * @param orderNo
	 * @return
	 */
	@Override
	public OrderBean queryToolOrderDetail(String orderNo)
	{
		try
		{
			// 地区平台信息
			LocalInfo info = lcsClient.getLocalInfo();
			OrderBean bean = toolOrderService.queryOrderDetailByNo(orderNo);
			bean.getOrder().setCurrencyCode(info.getCurrencyNameCn());
			return bean;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryToolOrderList", "调用BS查询工具订单详情失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 根据订单号查询配置单
	 * 
	 * @Description:
	 * @param orderNo
	 * @return
	 */
	@Override
	public ToolConfig queryToolConfigByNo(String orderNo)
	{
		try
		{
			return toolOrderService.queryToolConfigByNoAndCode(orderNo, CategoryCode.P_CARD.name());
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryToolConfigByNo", "调用BS查询根据订单号查询配置单失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 工具订单撤单
	 * 
	 * @Description:
	 * @param orderNo
	 * @throws HsException
	 */
	@Override
	public void toolOrderCancel(String orderNo) throws HsException
	{
		try
		{
			toolOrderService.closeOrWithdrawalsToolOrder(orderNo);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "toolOrderCancel", "调用BS工具订单撤单失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 分页查询企业工具设备
	 * 
	 * @Description:
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public PageData<AsDevice> queryEntTerminalList(Map filterMap, Map sortMap, Page page) throws HsException
	{
		try
		{
			String entResNo = (String) filterMap.get("pointNo");
			if (StringUtils.isNotBlank(entResNo))
			{
				String device = filterMap.get("deviceType").toString();
				Integer deviceType = CommonUtils.toInteger("0".equals(device) ? null : device);
				String terminalNo = (String) filterMap.get("terminalNo");
				int currentPage = page.getCurPage();
				int pageCount = page.getPageSize();
				return iuCAsDeviceService.listAsDevice(entResNo, deviceType, StringUtils.isBlank(terminalNo) ? null
						: terminalNo, currentPage, pageCount);
			}
			return null;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryEntTerminalList", "调用UC分页查询企业工具设备失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 修改设备状态
	 * 
	 * @Description:
	 * @param entResNo
	 * @param deviceType
	 * @param deviceNo
	 * @param deviceStatus
	 * @param custId
	 * @throws HsException
	 */
	@Override
	public void updateDeviceStatus(String entResNo, Integer deviceType, String deviceNo, Integer deviceStatus,
			String custId) throws HsException
	{
		try
		{
			iuCAsDeviceService.updateDeviceStatus(entResNo, deviceType, deviceNo, deviceStatus, custId);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "updateDeviceStatus", "调用UC修改设备状态失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 分页查询企业个性定制
	 * 
	 * @Description:
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public PageData<SpecialCardStyle> querySpecialCardStyleList(Map filterMap, Map sortMap, Page page)
	{
		try
		{
			String entResNo = (String) filterMap.get("pointNo");
			if (StringUtils.isNotBlank(entResNo))
			{
				return toolOrderService.queryEntSpecialCardStylePage(entResNo, page);
			}
			return null;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "querySpecialCardStyleList", "调用BS分页查询企业个性定制失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 查询企业个性卡列表
	 * 
	 * @Description:
	 * @param entResNo
	 * @return
	 */
	@Override
	public List<CardStyle> entSpecialCardStyle(String entResNo)
	{
		try
		{
			return toolOrderService.queryEntSpecialCardStyle(entResNo);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "querySpecialCardStyleList", "调用BS查询企业个性卡列表失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 定制卡样下单
	 * 
	 * @Description:
	 * @param bean
	 * @throws HsException
	 */
	@Override
	public ToolOrderResult submitSpecialCardStyleOrder(CompanyBase bean, String cardStyleName, String sourceFile,
			String remark, String operName) throws HsException
	{
		// 订单实体
		Order order = null;
		// 卡样
		CardStyle style = null;
		// 客户号
		String entCustId = bean.getEntCustId();
		// 互生号
		String entResNo = entCustId.substring(0, 11);
		// 订单设置参数
		order = new Order();
		order.setCustId(entCustId);
		order.setCustName(bean.getCustName());
		order.setHsResNo(entResNo);
		order.setOrderChannel(OrderChannel.WEB.getCode());
		order.setOrderOperator(operName);
		order.setOrderRemark(remark);
		// 上传卡样
		style = new CardStyle();
		style.setCardStyleName(cardStyleName);
		style.setMaterialMicroPic("");
		style.setMaterialSourceFile(sourceFile);
		style.setEntResNo(entResNo);
		style.setEntCustId(entCustId);
		style.setReqOperator(bean.getCustId());
		style.setReqRemark(remark);
		try
		{
			return toolOrderService.addCardStyleFeeOrder(order, style);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "submitSpecialCardStyleOrder", "调用BS定制卡样下单失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 上传卡样素材
	 * 
	 * @Description:
	 * @param orderNo
	 * @param bean
	 * @param microPic
	 * @param sourceFile
	 * @throws HsException
	 */
	@Override
	public void addSpecialCardStyle(String orderNo, CompanyBase bean, String microPic, String sourceFile, String remark)
			throws HsException
	{
		// 卡样
		CardStyle style = null;
		// 客户号
		String entCustId = bean.getEntCustId();
		// 互生号
		String entResNo = entCustId.substring(0, 11);
		// 设置卡样参数
		style = new CardStyle();
		style.setOrderNo(orderNo);
		style.setMaterialMicroPic(microPic);
		style.setMaterialSourceFile(sourceFile);
		style.setEntResNo(entResNo);
		style.setEntCustId(entCustId);
		style.setReqOperator(bean.getCustId());
		style.setReqRemark(remark);
		try
		{
			toolOrderService.addSpecialCardStyleEnt(style);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "addSpecialCardStyle", "调用BS上传卡样素材失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 确认卡样
	 * 
	 * @Description:
	 * @param orderNo
	 * @param custId
	 * @param remark
	 * @throws HsException
	 */
	@Override
	public void confirmCardStyle(String orderNo, String custId, String remark) throws HsException
	{
		// 卡样
		CardStyle style = null;

		// 设置卡样参数
		style = new CardStyle();
		style.setOrderNo(orderNo);
		style.setReqOperator(custId);
		style.setReqRemark(remark);
		try
		{
			toolOrderService.entConfirmCardStyle(style);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "confirmCardStyle", "调用BS确认卡样失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 上传卡制作卡样确认书
	 * 
	 * @Description:
	 * @param confNo
	 * @param confirmFile
	 * @throws HsException
	 */
	@Override
	public void uploadCardMarkConfirmFile(String confNo, String confirmFile) throws HsException
	{
		try
		{
			toolOrderService.uploadCardMarkConfirmFile(confNo, confirmFile);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "uploadCardMarkConfirmFile", "调用BS上传卡制作卡样确认书失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 查询企业可以购买的资源段
	 * 
	 * @Description:
	 * @param entCustId
	 * @return
	 */
	@Override
	public EntResource queryEntResourceSegment(String entCustId)
	{
		try
		{
			// 验证是否是全部资源
			AsEntExtendInfo info = iuCAsEntService.searchEntExtInfo(entCustId);
			HsAssert.notNull(info, ASRespCode.AS_NOT_QUERY_ENT_INFO);
			HsAssert.isTrue(
					StringUtils.isNotBlank(info.getStartResType())
							&& StartResType.ALL.getCode() != info.getStartResType(), ASRespCode.EW_ENT_IS_ALL_RES);

			return toolOrderService.queryEntResourceSegment(entCustId);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryEntResourceSegment", "调用BS查询企业可以购买的资源段失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 查询企业可以购买的资源段(新)
	 *
	 * @Description:
	 * @param entCustId
	 * @return
	 */
	@Override
	public EntResSegment queryEntResourceSegmentNew(String entCustId) {
		try
		{
			// 验证是否是全部资源
			AsEntExtendInfo info = iuCAsEntService.searchEntExtInfo(entCustId);
			HsAssert.notNull(info, ASRespCode.AS_NOT_QUERY_ENT_INFO);
			HsAssert.isTrue(
					StringUtils.isNotBlank(info.getStartResType())
							&& StartResType.ALL.getCode() != info.getStartResType(), ASRespCode.EW_ENT_IS_ALL_RES);

			return toolOrderService.queryEntResSegmentNew(entCustId);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryEntResourceSegmentNew", "调用BS查询企业可以购买的资源段失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 获取企业可以选择的卡样
	 * 
	 * @Description:
	 * @param entCustId
	 * @return
	 */
	@Override
	public List<CardStyle> queryEntCardStyleByAll(String entCustId)
	{
		try
		{
			return toolOrderService.queryEntCardStyleByAll(entCustId.substring(0, 11));
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryEntResourceSegment", "调用BS获取企业可以选择的卡样失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}
}
