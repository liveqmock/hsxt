/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.controllers.systemBusiness;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.gy.hsxt.access.web.bean.CompanyBase;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.access.web.common.utils.HttpClientUtils;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.company.services.common.PubParamService;
import com.gy.hsxt.access.web.company.services.systemBusiness.IToolManageService;
import com.gy.hsxt.bs.bean.tool.ToolOrderPay;
import com.gy.hsxt.bs.bean.tool.ToolProduct;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolOrderResult;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;

/**
 * 商品管理中商品分类管理的control
 * 
 * @Package: com.gy.hsxt.access.web.company.controllers.goodsManagement
 * @ClassName: ToolManageController
 * @Description: TODO
 * 
 * @author: zhangcy
 * @date: 2015-10-13 上午10:13:37
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("toolmanage")
public class ToolManageController extends BaseController {

	@Resource
	private IToolManageService toolManageService;

	@Resource
	private PubParamService pubParamService;

	@Autowired
	private IUCAsEntService iuCAsEntService; // 查询企业状态dubbo接口

	/**
	 * 查询可以购买的工具
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/query_may_buy_tool" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryMayBuyTool(@RequestParam("serviceType") String serviceType, HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String entCustId = request.getParameter("entCustId");
			try
			{
				RequestUtil.verifyParamsIsNotEmpty(
						new Object[] { entCustId, ASRespCode.AS_CUSTID_INVALID.getCode(),
								ASRespCode.AS_CUSTID_INVALID.getDesc() },
						new Object[] { serviceType, ASRespCode.EW_TOOL_SERVICE_TYPE.getCode(),
								ASRespCode.EW_TOOL_SERVICE_TYPE.getDesc() });
				List<ToolProduct> result = toolManageService
						.findMayBuyTools(HsResNoUtils.getCustTypeByHsResNo(entCustId.substring(0, 11)), serviceType);
				httpRespEnvelope = new HttpRespEnvelope(result);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 新增收货地址
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月25日 下午4:40:10
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/add_deliver_address" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope addDeliverAddress(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String entCustId = request.getParameter("entCustId");
			String addr = request.getParameter("addr");
			try
			{
				RequestUtil.verifyParamsIsNotEmpty(new Object[] { entCustId, ASRespCode.AS_CUSTID_INVALID.getCode(),
						ASRespCode.AS_CUSTID_INVALID.getDesc() });
				toolManageService.addDeliverAddress(entCustId, addr);
				httpRespEnvelope = new HttpRespEnvelope();
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 修改收货地址
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月25日 下午4:43:18
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/modify_deliver_address" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope modifyDeliverAddress(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String entCustId = request.getParameter("entCustId");
			String addr = request.getParameter("addr");
			try
			{
				RequestUtil.verifyParamsIsNotEmpty(new Object[] { entCustId, ASRespCode.AS_CUSTID_INVALID.getCode(),
						ASRespCode.AS_CUSTID_INVALID.getDesc() });
				toolManageService.modifyDeliverAddress(entCustId, addr);
				httpRespEnvelope = new HttpRespEnvelope();
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 删除收货地址
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月25日 下午4:44:32
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/remove_deliver_address" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope removeDeliverAddress(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String entCustId = request.getParameter("entCustId");
			String addrId = request.getParameter("addrId");
			try
			{
				RequestUtil.verifyParamsIsNotEmpty(new Object[] { entCustId, ASRespCode.AS_CUSTID_INVALID.getCode(),
						ASRespCode.AS_CUSTID_INVALID.getDesc() });
				toolManageService.removeDeliverAddress(entCustId, Long.parseLong(addrId));
				httpRespEnvelope = new HttpRespEnvelope();
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 查询收货地址
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月25日 下午4:45:16
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/query_deliver_address" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryDeliverAddress(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String entCustId = request.getParameter("entCustId");
			String addrId = request.getParameter("addrId");
			try
			{
				RequestUtil.verifyParamsIsNotEmpty(
						new Object[] { entCustId, ASRespCode.AS_CUSTID_INVALID.getCode(),
								ASRespCode.AS_CUSTID_INVALID.getDesc() },
						new Object[] { addrId, ASRespCode.EW_ADDR_ID_INVALID.getCode(),
								ASRespCode.EW_ADDR_ID_INVALID.getDesc() });
				httpRespEnvelope = new HttpRespEnvelope(
						toolManageService.queryDeliverAddress(entCustId, Long.parseLong(addrId)));
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 查询企业收货地址
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/query_ent_address" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryEntAddress(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String entCustId = request.getParameter("entCustId");
			try
			{
				RequestUtil.verifyParamsIsNotEmpty(new Object[] { entCustId, ASRespCode.AS_CUSTID_INVALID.getCode(),
						ASRespCode.AS_CUSTID_INVALID.getDesc() });
				JSONArray delivers = toolManageService.findEntDeliverAddress(entCustId);
				httpRespEnvelope = new HttpRespEnvelope(delivers);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 查询企业可以购买某种工具产品的数量
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/query_tool_num" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryMayToolNum(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String entCustId = request.getParameter("entCustId");
			String toolType = request.getParameter("toolType");
			try
			{
				RequestUtil.verifyParamsIsNotEmpty(
						new Object[] { entCustId, ASRespCode.AS_CUSTID_INVALID.getCode(),
								ASRespCode.AS_CUSTID_INVALID.getDesc() },
						new Object[] { toolType, ASRespCode.EW_TOOL_SERVICE_TYPE.getCode(),
								ASRespCode.EW_TOOL_SERVICE_TYPE.getDesc() });
				int result = toolManageService.findMayToolNum(entCustId, toolType);
				httpRespEnvelope = new HttpRespEnvelope(result);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 提交订单
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/commit_bytool_order" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope commitToolOrder(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String entCustId = request.getParameter("entCustId");
			String custId = request.getParameter("custId");
			String custEntName = request.getParameter("custEntName");
			String confs = request.getParameter("confs");
			String addr = request.getParameter("addr");
			String operName = request.getParameter("operName");
			String orderOperator = request.getParameter("custName") + "(" + operName + ")";
			try
			{

				RequestUtil.verifyParamsIsNotEmpty(
						new Object[] { entCustId, ASRespCode.AS_CUSTID_INVALID.getCode(),
								ASRespCode.AS_CUSTID_INVALID.getDesc() },
						new Object[] { custId, RespCode.AS_OPRATOR_OPTCUSTID.getCode(),
								ASRespCode.AS_OPRATOR_OPTCUSTID.getDesc() },
						new Object[] { custEntName, ASRespCode.AS_CUSTNAME_INVALID.getCode(),
								ASRespCode.AS_CUSTNAME_INVALID.getDesc() },
						new Object[] { confs, ASRespCode.EW_TOOL_CONFIGS_INVALID.getCode(),
								ASRespCode.EW_TOOL_CONFIGS_INVALID.getDesc() },
						new Object[] { addr, ASRespCode.EW_TOOL_ADDR_INVALID.getCode(),
								ASRespCode.EW_TOOL_ADDR_INVALID.getDesc() });

				// 验证企业状态（注销中 、已注销、停止积分活动申请中、已停止积分活动）不能互生币转货比
				AsEntStatusInfo info = iuCAsEntService.searchEntStatusInfo(entCustId);
				if (info != null)
				{
					switch (info.getBaseStatus())
					{
					case 5:
						return new HttpRespEnvelope(ASRespCode.EW_STATUS5_IS_NOT_REPLACE_BUY_HSB.getCode(),
								ASRespCode.EW_STATUS5_IS_NOT_REPLACE_BUY_HSB.getDesc());
					case 6:
						return new HttpRespEnvelope(ASRespCode.EW_STATUS6_IS_NOT_REPLACE_BUY_HSB.getCode(),
								ASRespCode.EW_STATUS6_IS_NOT_REPLACE_BUY_HSB.getDesc());
					case 7:
						return new HttpRespEnvelope(ASRespCode.EW_STATUS7_IS_NOT_REPLACE_BUY_HSB.getCode(),
								ASRespCode.EW_STATUS7_IS_NOT_REPLACE_BUY_HSB.getDesc());
					case 8:
						return new HttpRespEnvelope(ASRespCode.EW_STATUS8_IS_NOT_REPLACE_BUY_HSB.getCode(),
								ASRespCode.EW_STATUS8_IS_NOT_REPLACE_BUY_HSB.getDesc());
					}
				}
				ToolOrderResult bean = toolManageService.commitToolOrder(entCustId, custEntName, custId, confs, addr,
						operName, orderOperator);
				httpRespEnvelope = new HttpRespEnvelope(bean);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 购买卡
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月14日 下午5:35:49
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/commit_bytool_order_card" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope commitToolOrderCard(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String entCustId = request.getParameter("entCustId");
			String custId = request.getParameter("custId");
			String custEntName = request.getParameter("custEntName");
			String product = request.getParameter("product");
			String segmentId = request.getParameter("segmentId");
			String segment = request.getParameter("segment");
			String addr = request.getParameter("addr");
			String operName = request.getParameter("custName") + "(" + request.getParameter("operName") + ")";
			try
			{

				RequestUtil.verifyParamsIsNotEmpty(
						new Object[] { entCustId, ASRespCode.AS_CUSTID_INVALID.getCode(),
								ASRespCode.AS_CUSTID_INVALID.getDesc() },
						new Object[] { custId, RespCode.AS_OPRATOR_OPTCUSTID.getCode(),
								ASRespCode.AS_OPRATOR_OPTCUSTID.getDesc() },
						new Object[] { custEntName, ASRespCode.AS_CUSTNAME_INVALID.getCode(),
								ASRespCode.AS_CUSTNAME_INVALID.getDesc() },
						new Object[] { product, ASRespCode.EW_PROCUCT_INVALID.getCode(),
								ASRespCode.EW_PROCUCT_INVALID.getDesc() },
						new Object[] { segment, ASRespCode.EW_SEGMENT_INVALID.getCode(),
								ASRespCode.EW_SEGMENT_INVALID.getDesc() },
						new Object[] { addr, ASRespCode.EW_TOOL_ADDR_INVALID.getCode(),
								ASRespCode.EW_TOOL_ADDR_INVALID.getDesc() });

				// 验证企业状态（注销中 、已注销、停止积分活动申请中、已停止积分活动）不能互生币转货比
				AsEntStatusInfo info = iuCAsEntService.searchEntStatusInfo(entCustId);
				if (info != null)
				{
					switch (info.getBaseStatus())
					{
					case 5:
						return new HttpRespEnvelope(ASRespCode.EW_STATUS5_IS_NOT_REPLACE_BUY_HSB.getCode(),
								ASRespCode.EW_STATUS5_IS_NOT_REPLACE_BUY_HSB.getDesc());
					case 6:
						return new HttpRespEnvelope(ASRespCode.EW_STATUS6_IS_NOT_REPLACE_BUY_HSB.getCode(),
								ASRespCode.EW_STATUS6_IS_NOT_REPLACE_BUY_HSB.getDesc());
					case 7:
						return new HttpRespEnvelope(ASRespCode.EW_STATUS7_IS_NOT_REPLACE_BUY_HSB.getCode(),
								ASRespCode.EW_STATUS7_IS_NOT_REPLACE_BUY_HSB.getDesc());
					case 8:
						return new HttpRespEnvelope(ASRespCode.EW_STATUS8_IS_NOT_REPLACE_BUY_HSB.getCode(),
								ASRespCode.EW_STATUS8_IS_NOT_REPLACE_BUY_HSB.getDesc());
					}
				}

				ToolOrderResult bean = toolManageService.commitToolOrderCard(entCustId, custEntName, custId, product,
						addr, segment, operName);
				httpRespEnvelope = new HttpRespEnvelope(bean);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 工具订单支付
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/tool_order_payment" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope toolOrderPayment(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String entCustId = request.getParameter("entCustId");// 客户号
			String orderNo = request.getParameter("orderNo");// 订单号
			Integer payChannel = CommonUtils.toInteger(request.getParameter("payChannel"));// 支付方式
			String tradePwd = request.getParameter("tradePwd"); // 交易密码
			String smsCode = request.getParameter("smsCode"); // 短信验证码
			String bindingNo = request.getParameter("bindingNo"); // 快捷签约号
			String randomToken = request.getParameter("randomToken"); // 随机token
			String orderAmount = request.getParameter("orderAmount"); // 订单金额
			try
			{
				RequestUtil.verifyParamsIsNotEmpty(
						new Object[] { payChannel, ASRespCode.EW_TOOL_PAY_CHANNEL_INVALID.getCode(),
								ASRespCode.EW_TOOL_PAY_CHANNEL_INVALID.getDesc() },
						new Object[] { orderNo, ASRespCode.EW_TOOL_ORDERNO_INVALID.getCode(),
								ASRespCode.EW_TOOL_ORDERNO_INVALID.getDesc() });
				// 互生币支付
				if (payChannel.intValue() == PayChannel.HS_COIN_PAY.getCode().intValue())
				{
					RequestUtil.verifyParamsIsNotEmpty(
							new Object[] { entCustId, ASRespCode.AS_CUSTID_INVALID.getCode(),
									ASRespCode.AS_CUSTID_INVALID.getDesc() },
							new Object[] { tradePwd, ASRespCode.AS_TRADEPWD_INVALID.getCode(),
									ASRespCode.AS_TRADEPWD_INVALID.getDesc() },
							new Object[] { randomToken, ASRespCode.AS_SECURE_TOKEN_NULL.getCode(),
									ASRespCode.AS_SECURE_TOKEN_NULL.getDesc() });
				}
				// 快捷支付支付
				if (payChannel.intValue() == PayChannel.QUICK_PAY.getCode().intValue())
				{
					RequestUtil.verifyParamsIsNotEmpty(
							new Object[] { entCustId, ASRespCode.AS_CUSTID_INVALID.getCode(),
									ASRespCode.AS_CUSTID_INVALID.getDesc() },
							new Object[] { smsCode, ASRespCode.EW_SMSCODE_INVALID.getCode(),
									ASRespCode.EW_SMSCODE_INVALID.getDesc() },
							new Object[] { bindingNo, ASRespCode.EW_BINDINGNO_INVALID.getCode(),
									ASRespCode.EW_BINDINGNO_INVALID.getDesc() });
				}

				ToolOrderPay bean = new ToolOrderPay();
				bean.setOrderNo(orderNo);
				bean.setPayChannel(payChannel);
				bean.setSmsCode(smsCode);
				bean.setBindingNo(bindingNo);
				String ret = toolManageService.toolOrderPayment(entCustId, tradePwd, randomToken, bean, orderAmount);
				// 快捷支付发送http请求
				if (payChannel.intValue() == PayChannel.QUICK_PAY.getCode().intValue())
				{
					ret = HttpClientUtils.get(ret);
				}
				httpRespEnvelope = new HttpRespEnvelope(ret);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 分页查询企业工具订单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月11日 下午4:14:49
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/tool_order_list" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope toolOrderList(HttpServletRequest request, HttpServletResponse response)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				super.verifyPointNo(request);// 校验互生卡号
				request.setAttribute("serviceName", toolManageService);
				request.setAttribute("methodName", "queryToolOrderList");
				httpRespEnvelope = super.doList(request, response);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 查询工具订单详情
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月11日 下午5:02:54
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/tool_order_detail" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope toolOrderDetail(HttpServletRequest request, HttpServletResponse response)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String orderNo = request.getParameter("orderNo");// 订单号

			try
			{
				RequestUtil.verifyParamsIsNotEmpty(new Object[] { orderNo, ASRespCode.EW_TOOL_ORDERNO_INVALID.getCode(),
						ASRespCode.EW_TOOL_ORDERNO_INVALID.getDesc() });

				httpRespEnvelope = new HttpRespEnvelope(toolManageService.queryToolOrderDetail(orderNo));
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 根据订单号查询配置单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月16日 下午6:05:32
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/get_tool_config_by_no" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getToolConfigByNo(HttpServletRequest request, HttpServletResponse response)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String orderNo = request.getParameter("orderNo");// 订单号
			try
			{
				RequestUtil.verifyParamsIsNotEmpty(new Object[] { orderNo, ASRespCode.EW_TOOL_ORDERNO_INVALID.getCode(),
						ASRespCode.EW_TOOL_ORDERNO_INVALID.getDesc() });

				httpRespEnvelope = new HttpRespEnvelope(toolManageService.queryToolConfigByNo(orderNo));
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 工具订单撤单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月11日 下午5:09:33
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/tool_order_cancel" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope toolOrderCancel(HttpServletRequest request, HttpServletResponse response)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String orderNo = request.getParameter("orderNo");// 订单号

			try
			{
				RequestUtil.verifyParamsIsNotEmpty(new Object[] { orderNo, ASRespCode.EW_TOOL_ORDERNO_INVALID.getCode(),
						ASRespCode.EW_TOOL_ORDERNO_INVALID.getDesc() });

				toolManageService.toolOrderCancel(orderNo);
				httpRespEnvelope = new HttpRespEnvelope();
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 分页查询企业终端设备
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月11日 下午8:34:42
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/tool_terminal_list" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope entTerminalList(HttpServletRequest request, HttpServletResponse response)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				super.verifyPointNo(request);// 校验互生卡号
				request.setAttribute("serviceName", toolManageService);
				request.setAttribute("methodName", "queryEntTerminalList");
				httpRespEnvelope = super.doList(request, response);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 修改终端状态
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月12日 上午9:15:51
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/modify_device_status" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope modifyDeviceStatus(HttpServletRequest request, HttpServletResponse response)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String entResNo = request.getParameter("pointNo");// 互生号
			String custId = request.getParameter("custId");// 客户号
			Integer deviceType = CommonUtils.toInteger(request.getParameter("deviceType"));// 设备类型
			String deviceNo = request.getParameter("deviceNo");// 设备号
			Integer deviceStatus = CommonUtils.toInteger(request.getParameter("deviceStatus"));// 设备状态

			try
			{
				RequestUtil.verifyParamsIsNotEmpty(
						new Object[] { custId, ASRespCode.AS_CUSTID_INVALID.getCode(),
								ASRespCode.AS_CUSTID_INVALID.getDesc() },
						new Object[] { entResNo, RespCode.AS_POINTNO_INVALID.getCode(),
								ASRespCode.AS_POINTNO_INVALID.getDesc() },
						new Object[] { deviceType, ASRespCode.EW_DEVICETYPE_INVALID.getCode(),
								ASRespCode.EW_DEVICETYPE_INVALID.getDesc() },
						new Object[] { deviceNo, ASRespCode.EW_DEVICENO_INVALID.getCode(),
								ASRespCode.EW_DEVICENO_INVALID.getDesc() },
						new Object[] { deviceStatus, ASRespCode.EW_DEVICESTATUS_INVALID.getCode(),
								ASRespCode.EW_DEVICESTATUS_INVALID.getDesc() });

				toolManageService.updateDeviceStatus(entResNo, deviceType, deviceNo, deviceStatus, custId);
				httpRespEnvelope = new HttpRespEnvelope();
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 分页查询互生卡样服务
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月13日 下午7:33:13
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/special_card_style_list" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope SpecialCardStyleList(HttpServletRequest request, HttpServletResponse response)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				super.verifyPointNo(request);// 校验互生卡号
				request.setAttribute("serviceName", toolManageService);
				request.setAttribute("methodName", "querySpecialCardStyleList");
				httpRespEnvelope = super.doList(request, response);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 查询企业个性卡样
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月16日 下午6:18:14
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/ent_special_card_style" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope entSpecialCardStyle(HttpServletRequest request, HttpServletResponse response)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String entResNo = request.getParameter("pointNo");// 互生号

			try
			{
				RequestUtil.verifyParamsIsNotEmpty(new Object[] { entResNo, ASRespCode.AS_POINTNO_INVALID.getCode(),
						ASRespCode.AS_POINTNO_INVALID.getDesc() });
				httpRespEnvelope = new HttpRespEnvelope(toolManageService.entSpecialCardStyle(entResNo));
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 订制卡样下单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月13日 下午7:38:40
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/submit_special_card_style" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope submitSpecialCardStyleOrder(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String entCustId = request.getParameter("entCustId");
			String custId = request.getParameter("custId");
			String custEntName = request.getParameter("custEntName");
			String cardStyleName = request.getParameter("cardStyleName");
			String sourceFile = request.getParameter("sourceFile");
			String remark = request.getParameter("remark");
			String operName =  request.getParameter("custName") + "("
					+ request.getParameter("operName") + ")";
			try
			{
				RequestUtil.verifyParamsIsNotEmpty(
						new Object[] { entCustId, ASRespCode.AS_CUSTID_INVALID.getCode(),
								ASRespCode.AS_CUSTID_INVALID.getDesc() },
						new Object[] { custId, RespCode.AS_OPRATOR_OPTCUSTID.getCode(),
								ASRespCode.AS_OPRATOR_OPTCUSTID.getDesc() },
						new Object[] { custEntName, ASRespCode.AS_CUSTNAME_INVALID.getCode(),
								ASRespCode.AS_CUSTNAME_INVALID.getDesc() },
						new Object[] { cardStyleName, ASRespCode.EW_CARD_STYLE_NAME_INVALID.getCode(),
								ASRespCode.EW_CARD_STYLE_NAME_INVALID.getDesc() });

				CompanyBase baseInfo = new CompanyBase();
				baseInfo.setEntCustId(entCustId);
				baseInfo.setCustName(custEntName);
				baseInfo.setCustId(custId);
				ToolOrderResult bean = toolManageService.submitSpecialCardStyleOrder(baseInfo, cardStyleName, sourceFile,
						remark, operName);
				httpRespEnvelope = new HttpRespEnvelope(bean);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 上传卡样素材
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月18日 上午11:11:29
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/add_special_card_style" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope addSpecialCardStyle(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String orderNo = request.getParameter("orderNo");
			String entCustId = request.getParameter("entCustId");
			String custId = request.getParameter("custId");
			String custEntName = request.getParameter("custEntName");
			String microPic = request.getParameter("microPic");
			String sourceFile = request.getParameter("sourceFile");
			String remark = request.getParameter("remark");

			try
			{
				RequestUtil.verifyParamsIsNotEmpty(
						new Object[] { orderNo, ASRespCode.EW_TOOL_ORDERNO_INVALID.getCode(),
								ASRespCode.EW_TOOL_ORDERNO_INVALID.getDesc() },
						new Object[] { entCustId, ASRespCode.AS_CUSTID_INVALID.getCode(),
								ASRespCode.AS_CUSTID_INVALID.getDesc() },
						new Object[] { custId, ASRespCode.AS_OPRATOR_OPTCUSTID.getCode(),
								ASRespCode.AS_OPRATOR_OPTCUSTID.getDesc() },
						new Object[] { custEntName, ASRespCode.AS_CUSTNAME_INVALID.getCode(),
								ASRespCode.AS_CUSTNAME_INVALID.getDesc() });

				CompanyBase baseInfo = new CompanyBase();
				baseInfo.setEntCustId(entCustId);
				baseInfo.setCustName(custEntName);
				baseInfo.setCustId(custId);
				toolManageService.addSpecialCardStyle(orderNo, baseInfo, microPic, sourceFile, remark);
				httpRespEnvelope = new HttpRespEnvelope();
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 确认卡样
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月18日 上午11:16:36
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/confirm_card_style" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope confirmCardStyle(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String orderNo = request.getParameter("orderNo");
			String custId = request.getParameter("custId");
			String remark = request.getParameter("remark");
			try
			{
				RequestUtil.verifyParamsIsNotEmpty(
						new Object[] { orderNo, ASRespCode.EW_TOOL_ORDERNO_INVALID.getCode(),
								ASRespCode.EW_TOOL_ORDERNO_INVALID.getDesc() },
						new Object[] { custId, ASRespCode.AS_OPRATOR_OPTCUSTID.getCode(),
								ASRespCode.AS_OPRATOR_OPTCUSTID.getDesc() });

				toolManageService.confirmCardStyle(orderNo, custId, remark);
				httpRespEnvelope = new HttpRespEnvelope();
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 上传卡制作卡样确认文件
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月18日 上午11:17:43
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = { "/upload_card_mark_confirm_file" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/html;charset=UTF-8")
	public HttpRespEnvelope uploadCardMarkConfirmFile(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		response.setContentType("text/html;charset=UTF-8");
		if (httpRespEnvelope == null)
		{
			String confNo = request.getParameter("confNo");
			String confirmFile = request.getParameter("confirmFile");

			try
			{
				RequestUtil.verifyParamsIsNotEmpty(
						new Object[] { confNo, ASRespCode.EW_TOOL_CONFNO_INVALID.getCode(),
								ASRespCode.EW_TOOL_CONFNO_INVALID.getDesc() },
						new Object[] { confirmFile, ASRespCode.AS_OPRATOR_OPTCUSTID.getCode(),
								ASRespCode.AS_OPRATOR_OPTCUSTID.getDesc() });
				toolManageService.uploadCardMarkConfirmFile(confNo, confirmFile);
				httpRespEnvelope = new HttpRespEnvelope();
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		response.getWriter().write(JSON.toJSONString(httpRespEnvelope));
		response.getWriter().flush();
		return null;
	}

	/**
	 * 查询企业可以购买的资源段
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月14日 下午5:33:28
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/query_ent_resource_segment" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryEntResourceSegment(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String entCustId = request.getParameter("entCustId");
			try
			{
				RequestUtil.verifyParamsIsNotEmpty(new Object[] { entCustId, ASRespCode.AS_CUSTID_INVALID.getCode(),
						ASRespCode.AS_CUSTID_INVALID.getDesc() });
				httpRespEnvelope = new HttpRespEnvelope(toolManageService.queryEntResourceSegment(entCustId));
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 查询企业可以购买的资源段(新)
	 *
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月14日 下午5:33:28
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/query_ent_resource_segment_new" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryEntResourceSegmentNew(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String entCustId = request.getParameter("entCustId");
			try
			{
				RequestUtil.verifyParamsIsNotEmpty(new Object[] { entCustId, ASRespCode.AS_CUSTID_INVALID.getCode(),
						ASRespCode.AS_CUSTID_INVALID.getDesc() });
				httpRespEnvelope = new HttpRespEnvelope(toolManageService.queryEntResourceSegmentNew(entCustId));
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 获取企业可以选择的卡样
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月15日 下午3:15:14
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/get_ent_card_style_by_all" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getEntCardStyleByAll(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String entCustId = request.getParameter("entCustId");
			try
			{
				RequestUtil.verifyParamsIsNotEmpty(new Object[] { entCustId, ASRespCode.AS_CUSTID_INVALID.getCode(),
						ASRespCode.AS_CUSTID_INVALID.getDesc() });
				httpRespEnvelope = new HttpRespEnvelope(toolManageService.queryEntCardStyleByAll(entCustId));
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
		return toolManageService;
	}
}
