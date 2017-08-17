/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.platformProxy;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gy.hsxt.bs.bean.tool.EntResSegment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.api.tool.IBSToolOrderService;
import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.order.DeliverInfo;
import com.gy.hsxt.bs.bean.tool.ProxyOrder;
import com.gy.hsxt.bs.bean.tool.ProxyOrderDetail;
import com.gy.hsxt.bs.bean.tool.ToolProduct;
import com.gy.hsxt.bs.bean.tool.resultbean.EntResource;
import com.gy.hsxt.bs.bean.tool.resultbean.ResourceSegment;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolOrderResult;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.CurrencyCode;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.StartResType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.common.IUCAsReceiveAddrInfoService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.common.AsReceiveAddr;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;

/**
 * 工具管理(终端)Service
 * 
 * @Package: com.gy.hsxt.access.web.company.services.systemBusiness.imp
 * @ClassName: ToolManageServiceImpl
 * @author: likui
 * @date: 2016年1月11日 下午3:58:20
 * @company: gyist
 * @version V3.0.0
 */
@Service
public class ToolManageServiceImpl extends BaseServiceImpl<ToolManageServiceImpl> implements IToolManageService {
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
			HsAssert.notNull(entInfo, BSRespCode.BS_NOT_QUERY_ENT_INFO, "未查询到企业公司信息");
			HsAssert.notNull(entInfo.getMainInfo(), BSRespCode.BS_NOT_QUERY_ENT_INFO, "未查询到企业公司信息");
			HsAssert.notNull(entInfo.getBaseInfo(), BSRespCode.BS_NOT_QUERY_ENT_INFO, "未查询到企业公司信息");
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
	 * @return
	 */
	@Override
	public ToolOrderResult commitToolOrder(String entCustId, String custEntName, String custId, String confs,
			String addr, String operName)
	{
		// 配置单列表
		List<ProxyOrderDetail> orderDetails = null;
		ProxyOrderDetail orderDetail = null;
		// 收货地址
		DeliverInfo deliver = null;
		// 申购工具订单
		ProxyOrder proxyOrder = null;
		// 收货地址JSON对象
		JSONObject json = null;
		// 配置单JSON集合对象
		JSONArray jsons = null;
		// 互生号
		String entResNo = entCustId.substring(0, 11);
		try
		{
			// 创建对象
			jsons = JSONArray.parseArray(URLDecoder.decode(confs, "UTF-8"));
			json = JSONObject.parseObject(URLDecoder.decode(addr, "UTF-8"));
			orderDetails = new ArrayList<ProxyOrderDetail>();
			proxyOrder = new ProxyOrder();

			// 收货地址设置参数
			deliver = new DeliverInfo();
			deliver.setHsCustId(entCustId);
			deliver.setStreetAddr(json.getString("fullAddr"));
			deliver.setFullAddr(json.getString("fullAddr") + json.getString("entName"));
			deliver.setLinkman(json.getString("linkMan"));
			deliver.setMobile(json.getString("mobile"));
			deliver.setZipCode(json.getString("zipCode"));
			deliver.setCreatedby(custId);
			// 验证收货地址参数
			vaildOrderParam(deliver, null);
			double sumAmount = 0.0;
			double detailAmount = 0.0;
			// 配置单设置参数
			for (int i = 0; i < jsons.size(); i++)
			{
				json = jsons.getJSONObject(i);
				orderDetail = new ProxyOrderDetail();
				orderDetail.setCategoryCode(json.getString("categoryCode"));
				orderDetail.setPrice(json.getString("price"));
				orderDetail.setProductId(json.getString("productId"));
				orderDetail.setProductName(json.getString("productName"));
				orderDetail.setUnit(json.getString("unit"));
				orderDetail.setQuantity(CommonUtils.toInteger(json.getString("quanilty")));

				// 验证配置单参数
				vaildOrderParam(null, orderDetail);
				detailAmount = Double.parseDouble(json.getString("price"))
						* CommonUtils.toInteger(json.getString("quanilty"));
				orderDetail.setTotalAmount(detailAmount + "");
				sumAmount += detailAmount;
				orderDetails.add(orderDetail);
			}
			// 订单设置参数
			proxyOrder.setEntCustId(entCustId);
			proxyOrder.setEntCustName(custEntName);
			proxyOrder.setEntResNo(entResNo);
			proxyOrder.setCustType(HsResNoUtils.getCustTypeByHsResNo(entResNo));
			proxyOrder.setReqOperator(operName);
			proxyOrder.setOrderType(OrderType.BUY_TOOL.getCode());
			proxyOrder.setOrderAmount(sumAmount + "");
			proxyOrder.setCurrencyCode(CurrencyCode.HSB.getCode());
			proxyOrder.setReqRemark("新增申购工具下单");
			// 设置申购订单参数
			proxyOrder.setDetail(orderDetails);
			proxyOrder.setDeliverInfo(deliver);

			return toolOrderService.addPlatProxyOrder(proxyOrder);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "commitToolOrder", "调用BS工具订单提交失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
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
		List<ProxyOrderDetail> orderDetails = null;
		ProxyOrderDetail orderDetail = null;
		// 收货地址
		DeliverInfo deliver = null;
		// 申购工具订单
		ProxyOrder proxyOrder = null;
		// 工具参数
		ToolProduct productTool = null;
		// 收货地址JSON对象
		JSONObject json = null;
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

			orderDetails = new ArrayList<ProxyOrderDetail>();
			proxyOrder = new ProxyOrder();

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

			orderDetail = new ProxyOrderDetail();
			orderDetail.setSegmentIds(segmentIds);
			orderDetail.setCategoryCode(productTool.getCategoryCode());
			orderDetail.setPrice(productTool.getPrice());
			orderDetail.setQuantity(CommonUtils.toInteger(cardAndAmount[0]));
			orderDetail.setTotalAmount(cardAndAmount[1]);
			orderDetail.setProductId(productTool.getProductId());
			orderDetail.setProductName(productTool.getProductName());
			orderDetail.setUnit(productTool.getUnit());
			orderDetails.add(orderDetail);
			// 验证配置单参数
			vaildOrderParam(null, orderDetail);

			// 订单设置参数
			proxyOrder.setEntCustId(entCustId);
			proxyOrder.setEntCustName(custEntName);
			proxyOrder.setEntResNo(entResNo);
			proxyOrder.setCustType(HsResNoUtils.getCustTypeByHsResNo(entResNo));
			proxyOrder.setReqOperator(operName);
			proxyOrder.setOrderType(OrderType.APPLY_PERSON_RESOURCE.getCode());
			proxyOrder.setOrderAmount(cardAndAmount[1]);
			proxyOrder.setCurrencyCode(CurrencyCode.HSB.getCode());
			proxyOrder.setReqRemark("新增系统资源申购下单");
			// 设置申购订单参数
			proxyOrder.setDetail(orderDetails);
			proxyOrder.setDeliverInfo(deliver);

			ret = toolOrderService.addPlatProxyOrder(proxyOrder);
			HsAssert.notNull(ret, RespCode.FAIL);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "commitToolOrderCard", "调用BS申购系统资源失败", ex);
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
			if (!"20000".equals(res.getSegmentPrice()))
			{
				throw new HsException(ASRespCode.EW_SEGMENT_PRICE_ERROR);
			}
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
	 * 定制互生卡样下单
	 * 
	 * @param entCustId
	 * @param custEntName
	 * @param custId
	 * @param operName
	 * @return
	 */
	@Override
	public ToolOrderResult addCardStyleFeeOrder(String entCustId, String custEntName, String custId, String operName)
	{
		ProxyOrder proxyOrder = null;
		// 互生号
		String entResNo = entCustId.substring(0, 11);
		try
		{
			proxyOrder = new ProxyOrder();
			// 订单设置参数
			proxyOrder.setEntCustId(entCustId);
			proxyOrder.setEntCustName(custEntName);
			proxyOrder.setEntResNo(entResNo);
			proxyOrder.setCustType(HsResNoUtils.getCustTypeByHsResNo(entResNo));
			proxyOrder.setReqOperator(operName);
			proxyOrder.setOrderType(OrderType.CARD_STYLE_FEE.getCode());
			proxyOrder.setOrderAmount("1000");
			proxyOrder.setCurrencyCode(CurrencyCode.HSB.getCode());
			proxyOrder.setReqRemark("定制卡样服务下单");
			return toolOrderService.addPlatProxyOrder(proxyOrder);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findMayToolNum", "调用BS工具订单支付失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
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
	private void vaildOrderParam(DeliverInfo deliver, ProxyOrderDetail conf) throws HsException
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
	public PageData<ProxyOrder> queryPlatProxyOrderPage(Map filterMap, Map sortMap, Page page)
	{
		BaseParam param = new BaseParam();
		param.setHsResNo((String) filterMap.get("companyResNo"));
		param.setExeCustId((String) filterMap.get("custId"));
		param.setStatus(CommonUtils.toInteger(filterMap.get("status")));
		try
		{
			return toolOrderService.queryPlatProxyOrderPage(param, page);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryPlatProxyOrderPage", "调用BS分页查询企业工具订单失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 根据代购订单编号查询代购订单
	 * 
	 * @Description:
	 * @param orderNo
	 * @return
	 */
	@Override
	public ProxyOrder queryPlatProxyOrderById(String orderNo)
	{
		try
		{
			// 地区平台信息
			ProxyOrder bean = toolOrderService.queryPlatProxyOrderById(orderNo);
			return bean;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryToolOrderList", "调用BS查询工具订单详情失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 平台代购订单审批
	 * 
	 * @Description:
	 * @param order
	 * @throws HsException
	 */
	@Override
	public void platProxyOrderAppr(ProxyOrder order) throws HsException
	{
		try
		{
			toolOrderService.platProxyOrderAppr(order);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "platProxyOrderAppr", "调用BS代购订单审批失败", ex);
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
	 * 分页查询平台代购订单记录
	 * 
	 * @Description:
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 */
	@Override
	public PageData<ProxyOrder> queryPlatProxyOrderRecordPage(Map filterMap, Map sortMap, Page page)
	{
		BaseParam param = new BaseParam();
		param.setStartDate((String) filterMap.get("startDate"));
		param.setEndDate((String) filterMap.get("endDate"));
		param.setType((String) filterMap.get("orderType"));
		param.setHsResNo((String) filterMap.get("companyResNo"));
		param.setHsCustName((String) filterMap.get("companyCustName"));
		param.setStatus(CommonUtils.toInteger(filterMap.get("status")));
		try
		{
			return toolOrderService.queryPlatProxyOrderRecordPage(param, page);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryPlatProxyOrderPage", "调用BS分页查询企业工具订单失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}
}
