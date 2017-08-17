/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.controllers.toolorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.gy.hsxt.access.web.aps.services.toolorder.EntInfoService;
import com.gy.hsxt.access.web.aps.services.toolorder.ToolDispatchingMaService;
import com.gy.hsxt.access.web.aps.services.toolorder.UserCenterService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.tool.Shipping;
import com.gy.hsxt.bs.bean.tool.ShippingData;
import com.gy.hsxt.bs.bean.tool.ToolConfig;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolderAllInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;

/**
 * 工具配送管理
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.toolorder
 * @ClassName: ToolDispatchingController
 * @Description: TODO
 *
 * @author: zhangcy
 * @date: 2015-12-16 下午6:05:55
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("tooldispatching")
public class ToolDispatchingMaController extends BaseController {

	@Resource
	private ToolDispatchingMaService toolDispatchingMaService;
	@Resource
	private UserCenterService userCenterService;
	@Resource
	private EntInfoService entInfoService;
	@Autowired
	private IUCAsCardHolderService ucAsCardHolderService;

	@ResponseBody
	@RequestMapping(value =
	{ "/querytooldispatch" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryToolDispatch(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				// 订单类型103：新增申购 101:申报申购
				// 见枚举类:com.gy.hsxt.bs.common.enumtype.order.OrderType
				String orderType = request.getParameter("orderType");
				// 配置单编号
				String confNo = request.getParameter("confNo");
				// 企业客户ID
				String entCustId = request.getParameter("searchEntCustId");
				// 企业互生号
				String entResNo = request.getParameter("searchEntResNo");
				Map<String, Object> result = new HashMap<String, Object>();
				// 查询配送单信息
				ShippingData ship = toolDispatchingMaService.queryShipingData(orderType, confNo.split(","));
				result.put("ship", ship);
				if (OrderType.REMAKE_MY_CARD.getCode().equals(orderType))
				{
					// 消费者补卡
					AsCardHolderAllInfo info = ucAsCardHolderService.searchAllInfo(entCustId);
					result.put("entinfo", info);
				} else
				{
					// 查询企业所有信息
					AsEntAllInfo entinfo = entInfoService.searchEntAllInfo(entCustId);
					// 查询服务公司所有信息 根据企业互生号拼接服务公司互生号
					String servResNo = entResNo.substring(0, 5).concat("000000");
					AsEntAllInfo serinfo = entInfoService.searchEntAllInfoByResNo(servResNo);
					result.put("entinfo", entinfo);
					result.put("serinfo", serinfo);
				}
				httpRespEnvelope = new HttpRespEnvelope(result);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	@ResponseBody
	@RequestMapping(value =
	{ "/addtooldispatch" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope addToolDispatch(HttpServletRequest request, @ModelAttribute Shipping bean)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		bean.setCustId(request.getParameter("targetCustId"));
		bean.setHsResNo(request.getParameter("targetResNo"));
		bean.setCustName(request.getParameter("targetName"));

		Integer targetCustType = null;
		String targetCustTypeObj = request.getParameter("targetCustType");
		if (targetCustTypeObj != null)
		{
			targetCustType = Integer.parseInt(targetCustTypeObj);
		}
		bean.setCustType(targetCustType);

		if (httpRespEnvelope == null)
		{
			try
			{
				// 用户名
				// String userName = request.getParameter("userName");
				// //密码
				// String password = request.getParameter("password");
				// //企业互生号
				// String entResNo = request.getParameter("entResNo");
				// //随机秘钥
				// String secretKey = request.getParameter("secretKey");
				//
				// //非空验证
				// RequestUtil.verifyParamsIsNotEmpty(
				//
				// new Object[] {userName,
				// RespCode.APS_HSKZZ_DOUBLESIGN_USERNAME.getCode(),
				// RespCode.APS_HSKZZ_DOUBLESIGN_USERNAME.getDesc()},
				// new Object[] {password,
				// RespCode.APS_HSKZZ_DOUBLESIGN_PASSWORD.getCode(),
				// RespCode.APS_HSKZZ_DOUBLESIGN_PASSWORD.getDesc()},
				// new Object[] {entResNo,
				// RespCode.APS_HSKZZ_DOUBLESIGN_ENTRESNO.getCode(),
				// RespCode.APS_HSKZZ_DOUBLESIGN_ENTRESNO.getDesc()}
				//
				// );
				//
				// //双签鉴权 抛出异常则验证失败
				// userCenterService.doubleSign(userName, password,
				// entResNo,secretKey);

				// 配送信息非空验证
				RequestUtil.verifyParamsIsNotEmpty(

						new Object[]
						{ bean.getShippingType(), RespCode.APS_GJPSGL_SHIPPINGTYPE.getCode(),
								RespCode.APS_GJPSGL_SHIPPINGTYPE.getDesc() },
						new Object[]
						{ bean.getHsResNo(), RespCode.APS_GJPSGL_HSRESNO.getCode(),
								RespCode.APS_GJPSGL_HSRESNO.getDesc() },
						new Object[]
						{ bean.getCustType(), RespCode.APS_GJPSGL_CUSTTYPE.getCode(),
								RespCode.APS_GJPSGL_CUSTTYPE.getDesc() },
						new Object[]
						{ bean.getReceiver(), RespCode.APS_GJPSGL_RECEIVER.getCode(),
								RespCode.APS_GJPSGL_RECEIVER.getDesc() },
						new Object[]
						{ bean.getReceiverAddr(), RespCode.APS_GJPSGL_RECEIVERADDR.getCode(),
								RespCode.APS_GJPSGL_RECEIVERADDR.getDesc() },
						new Object[]
						{ bean.getMobile(), RespCode.APS_GJPSGL_RECEIVERMOBILE.getCode(),
								RespCode.APS_GJPSGL_RECEIVERMOBILE.getDesc() },
						new Object[]
						{ bean.getConsignor(), RespCode.APS_GJPSGL_CONSIGNOR.getCode(),
								RespCode.APS_GJPSGL_CONSIGNOR.getDesc() }, new Object[]
						{ bean.getTrackingNo(), RespCode.APS_GJPSGL_TRACKINGNO.getCode(),
								RespCode.APS_GJPSGL_TRACKINGNO.getDesc() }, new Object[]
						{ bean.getShippingFee(), RespCode.APS_GJPSGL_SHIPPINGFEE.getCode(),
								RespCode.APS_GJPSGL_SHIPPINGFEE.getDesc() }

				);

				/**
				 * 配送物品清单 格式为
				 * hsResNo,categoryCode,productId,productName,quantity
				 * ,confUser,whId:
				 * hsResNo,categoryCode,productId,productName,quantity
				 * ,confUser,whId:....
				 */
				String conFigs = request.getParameter("conFigs");
				List<ToolConfig> configs = new ArrayList<ToolConfig>();
				if (conFigs != null && !"".equals(conFigs))
				{
					String[] cons = conFigs.split(":");
					for (String figs : cons)
					{
						// 判断配送物品信息是否输入完整
						String[] ary = figs.split(",");
						if (ary.length != 7)
						{
							throw new HsException(ASRespCode.APS_GJPSGL_PSQDLBLACK.getCode(),
									ASRespCode.APS_GJPSGL_PSQDLBLACK.getDesc());
						} else
						{
							ToolConfig config = new ToolConfig();
							config.setHsResNo(ary[0]);
							config.setCategoryCode(ary[1]);
							config.setProductId(ary[2]);
							config.setProductName(ary[3]);
							config.setQuantity(Integer.parseInt(ary[4]));
							config.setConfUser(ary[5]);
							config.setWhId(ary[6]);
							configs.add(config);
						}
					}
				}

				bean.setConfigs(configs);

				// 主产品的配置清单编号
				String confNo = request.getParameter("confNo");
				// 非空验证
				RequestUtil.verifyParamsIsNotEmpty(

				new Object[]
				{ confNo, RespCode.APS_GJPSGL_CONFNOS.getCode(), RespCode.APS_GJPSGL_CONFNOS.getDesc() }

				);
				if (!StringUtils.isEmpty(confNo))
				{
					bean.setConfNos(confNo.split(","));
				}
				// 添加发货单
				toolDispatchingMaService.addToolShipping(bean);

				httpRespEnvelope = new HttpRespEnvelope();
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	@ResponseBody
	@RequestMapping(value =
	{ "/querytoolnum" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryToolNum(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				// 工具编号
				String productId = request.getParameter("productId");
				// 仓库ID
				String whId = request.getParameter("whId");

				String result = toolDispatchingMaService.queryToolEnterByForFree(productId, whId);
				httpRespEnvelope = new HttpRespEnvelope(result);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 查看配置单数据
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/queryshipping" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryShippingById(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				// 企业客户ID
				String entCustId = request.getParameter("entCustId");
				// 查询企业所有信息
				AsEntAllInfo entinfo = entInfoService.searchEntAllInfo(entCustId);

				// 配置单编号编号
				String shippingId = request.getParameter("shippingId");
				// 查询配置单信息
				Shipping ship = toolDispatchingMaService.queryShippingById(shippingId);

				Map<String, Object> result = new HashMap<String, Object>();
				result.put("entinfo", entinfo);
				result.put("ship", ship);

				httpRespEnvelope = new HttpRespEnvelope(result);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	@Override
	protected IBaseService getEntityService()
	{
		return toolDispatchingMaService;
	}

}
