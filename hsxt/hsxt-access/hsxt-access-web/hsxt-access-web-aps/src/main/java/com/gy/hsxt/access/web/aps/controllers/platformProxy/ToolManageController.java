/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.controllers.platformProxy;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.gy.hsxt.access.web.aps.services.platformProxy.IToolManageService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.tool.ProxyOrder;
import com.gy.hsxt.bs.bean.tool.ToolProduct;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolOrderResult;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsResNoUtils;

/**
 * 商品管理中商品分类管理的control
 * 
 * @Package: com.gy.hsxt.access.web.company.controllers.goodsManagement
 * @ClassName: ToolManageController
 * 
 * @author: chenli
 * @date: 2016-03-03 上午10:13:37
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("toolmanage")
public class ToolManageController extends BaseController {

	@Resource
	private IToolManageService toolManageService;

	/**
	 * 查询可以购买的工具
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/query_may_buy_tool" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryMayBuyTool(@RequestParam("serviceType") String serviceType, String companyCustId,
			HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				RequestUtil
						.verifyParamsIsNotEmpty(
								new Object[]
								{ companyCustId, ASRespCode.AS_CUSTID_INVALID.getCode(),
										ASRespCode.AS_CUSTID_INVALID.getDesc() }, new Object[]
								{ serviceType, ASRespCode.EW_TOOL_SERVICE_TYPE.getCode(),
										ASRespCode.EW_TOOL_SERVICE_TYPE.getDesc() });

				List<ToolProduct> result = toolManageService.findMayBuyTools(
						HsResNoUtils.getCustTypeByHsResNo(companyCustId.substring(0, 11)), serviceType);
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
	@RequestMapping(value =
	{ "/add_deliver_address" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope addDeliverAddress(String companyCustId, HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String addr = request.getParameter("addr");
			try
			{
				RequestUtil.verifyParamsIsNotEmpty(new Object[]
				{ companyCustId, ASRespCode.AS_CUSTID_INVALID.getCode(), ASRespCode.AS_CUSTID_INVALID.getDesc() });
				toolManageService.addDeliverAddress(companyCustId, addr);
				httpRespEnvelope = new HttpRespEnvelope();
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
	@RequestMapping(value =
	{ "/query_ent_address" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryEntAddress(String companyCustId, HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				RequestUtil.verifyParamsIsNotEmpty(new Object[]
				{ companyCustId, ASRespCode.AS_CUSTID_INVALID.getCode(), ASRespCode.AS_CUSTID_INVALID.getDesc() });
				JSONArray delivers = toolManageService.findEntDeliverAddress(companyCustId);
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
	@RequestMapping(value =
	{ "/query_tool_num" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryMayToolNum(String companyCustId, HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String toolType = request.getParameter("toolType");
			try
			{
				RequestUtil
						.verifyParamsIsNotEmpty(
								new Object[]
								{ companyCustId, ASRespCode.AS_CUSTID_INVALID.getCode(),
										ASRespCode.AS_CUSTID_INVALID.getDesc() },
								new Object[]
								{ toolType, ASRespCode.EW_TOOL_SERVICE_TYPE.getCode(),
										ASRespCode.EW_TOOL_SERVICE_TYPE.getDesc() });
				int result = toolManageService.findMayToolNum(companyCustId, toolType);
				httpRespEnvelope = new HttpRespEnvelope(result);
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
	@RequestMapping(value =
	{ "/tool_order_list" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
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
				request.setAttribute("methodName", "queryPlatProxyOrderPage");
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
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/tool_order_detail" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope toolOrderDetail(String orderNo, HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				RequestUtil
						.verifyParamsIsNotEmpty(new Object[]
						{ orderNo, ASRespCode.EW_TOOL_ORDERNO_INVALID.getCode(),
								ASRespCode.EW_TOOL_ORDERNO_INVALID.getDesc() });

				httpRespEnvelope = new HttpRespEnvelope(toolManageService.queryPlatProxyOrderById(orderNo));
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 提交订单(刷卡工具)
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/commit_bytool_order" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope commitToolOrder(String companyCustId, String companyName, String custId,
			HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String confs = request.getParameter("confs");
			String addr = request.getParameter("addr");
			String operName = request.getParameter("custName") + "(" + request.getParameter("operName") + ")";
			try
			{
				RequestUtil
						.verifyParamsIsNotEmpty(
								new Object[]
								{ companyCustId, ASRespCode.AS_CUSTID_INVALID.getCode(),
										ASRespCode.AS_CUSTID_INVALID.getDesc() },
								new Object[]
								{ custId, RespCode.AS_OPRATOR_OPTCUSTID.getCode(),
										ASRespCode.AS_OPRATOR_OPTCUSTID.getDesc() },
								new Object[]
								{ companyName, ASRespCode.AS_CUSTNAME_INVALID.getCode(),
										ASRespCode.AS_CUSTNAME_INVALID.getDesc() },
								new Object[]
								{ confs, ASRespCode.EW_TOOL_CONFIGS_INVALID.getCode(),
										ASRespCode.EW_TOOL_CONFIGS_INVALID.getDesc() },
								new Object[]
								{ addr, ASRespCode.EW_TOOL_ADDR_INVALID.getCode(),
										ASRespCode.EW_TOOL_ADDR_INVALID.getDesc() });
				ToolOrderResult ret = toolManageService.commitToolOrder(companyCustId, companyName, custId, confs,
						addr, operName);
				httpRespEnvelope = new HttpRespEnvelope(ret);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 提交订单(互生卡)
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/commit_bytool_order_card" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope commitToolOrderCard(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String companyCustId = request.getParameter("companyCustId");
			String custId = request.getParameter("custId");
			String companyName = request.getParameter("companyName");
			String product = request.getParameter("product");
			String segmentId = request.getParameter("segmentId");
			String segment = request.getParameter("segment");
			String addr = request.getParameter("addr");
			String operName = request.getParameter("custName") + "(" + request.getParameter("operName") + ")";
			try
			{
				RequestUtil
						.verifyParamsIsNotEmpty(
								new Object[]
								{ companyCustId, ASRespCode.AS_CUSTID_INVALID.getCode(),
										ASRespCode.AS_CUSTID_INVALID.getDesc() },
								new Object[]
								{ custId, RespCode.AS_OPRATOR_OPTCUSTID.getCode(),
										ASRespCode.AS_OPRATOR_OPTCUSTID.getDesc() },
								new Object[]
								{ companyName, ASRespCode.AS_CUSTNAME_INVALID.getCode(),
										ASRespCode.AS_CUSTNAME_INVALID.getDesc() },
								new Object[]
								{ product, ASRespCode.EW_PROCUCT_INVALID.getCode(),
										ASRespCode.EW_PROCUCT_INVALID.getDesc() },
								new Object[]
								{ segment, ASRespCode.EW_SEGMENT_INVALID.getCode(),
										ASRespCode.EW_SEGMENT_INVALID.getDesc() },
								new Object[]
								{ addr, ASRespCode.EW_TOOL_ADDR_INVALID.getCode(),
										ASRespCode.EW_TOOL_ADDR_INVALID.getDesc() });
				ToolOrderResult ret = toolManageService.commitToolOrderCard(companyCustId, companyName, custId,
						product, addr, segment, operName);
				httpRespEnvelope = new HttpRespEnvelope(ret);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 定制互生卡样下单
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/addCardStyleFeeOrder" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope addCardStyleFeeOrder(String companyCustId, String companyName, String custId,
			HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String operName = request.getParameter("custName") + "(" + request.getParameter("operName") + ")";
			try
			{
				RequestUtil
						.verifyParamsIsNotEmpty(
								new Object[]
								{ companyCustId, ASRespCode.AS_CUSTID_INVALID.getCode(),
										ASRespCode.AS_CUSTID_INVALID.getDesc() },
								new Object[]
								{ custId, RespCode.AS_OPRATOR_OPTCUSTID.getCode(),
										ASRespCode.AS_OPRATOR_OPTCUSTID.getDesc() },
								new Object[]
								{ companyName, ASRespCode.AS_CUSTNAME_INVALID.getCode(),
										ASRespCode.AS_CUSTNAME_INVALID.getDesc() });
				ToolOrderResult result = toolManageService.addCardStyleFeeOrder(companyCustId, companyName, custId,
						operName);
				httpRespEnvelope = new HttpRespEnvelope(result);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 平台代购订单审批
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
	@RequestMapping(value =
	{ "/proxy_order_appr" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope platProxyOrderAppr(String proxyOrderNo, String custId, Integer status, String apprRemark,
			HttpServletRequest request, HttpServletResponse response)
	{
		String operName = request.getParameter("custName") + "(" + request.getParameter("operName") + ")";
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		ProxyOrder order = new ProxyOrder();
		order.setApprOperator(operName);
		order.setExeCustId(custId);
		order.setStatus(status);
		order.setProxyOrderNo(proxyOrderNo);
		order.setApprRemark(apprRemark);
		if (httpRespEnvelope == null)
		{
			try
			{
				toolManageService.platProxyOrderAppr(order);
				httpRespEnvelope = new HttpRespEnvelope();
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 查询企业可以购买的资源段
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月18日 上午9:13:21
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/query_ent_resource_segment" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryEntResourceSegment(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String companyCustId = request.getParameter("companyCustId");
			try
			{
				RequestUtil.verifyParamsIsNotEmpty(new Object[]
				{ companyCustId, ASRespCode.AS_CUSTID_INVALID.getCode(), ASRespCode.AS_CUSTID_INVALID.getDesc() });
				httpRespEnvelope = new HttpRespEnvelope(toolManageService.queryEntResourceSegment(companyCustId));
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
	@RequestMapping(value =
			{ "/query_ent_resource_segment_new" }, method =
			{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryEntResourceSegmentNew(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String companyCustId = request.getParameter("companyCustId");
			try
			{
				RequestUtil.verifyParamsIsNotEmpty(new Object[]
						{ companyCustId, ASRespCode.AS_CUSTID_INVALID.getCode(), ASRespCode.AS_CUSTID_INVALID.getDesc() });
				httpRespEnvelope = new HttpRespEnvelope(toolManageService.queryEntResourceSegmentNew(companyCustId));
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 分页查询平台代购订单记录
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月20日 上午10:13:16
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/find_plat_proxy_order_record_page" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findPlatProxyOrderRecordPage(HttpServletRequest request, HttpServletResponse response)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				super.verifyPointNo(request);// 校验互生卡号
				request.setAttribute("serviceName", toolManageService);
				request.setAttribute("methodName", "queryPlatProxyOrderRecordPage");
				httpRespEnvelope = super.doList(request, response);
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
