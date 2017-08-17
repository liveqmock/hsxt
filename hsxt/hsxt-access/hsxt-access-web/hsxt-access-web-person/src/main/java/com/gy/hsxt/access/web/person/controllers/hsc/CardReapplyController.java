package com.gy.hsxt.access.web.person.controllers.hsc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.gy.hsxt.access.web.common.constant.ASRespCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.access.web.common.utils.HttpClientUtils;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.common.utils.ValidateUtil;
import com.gy.hsxt.access.web.person.bean.CardReapplyOrder;
import com.gy.hsxt.access.web.person.bean.ReceiveAddress;
import com.gy.hsxt.access.web.person.services.common.IPubParamService;
import com.gy.hsxt.access.web.person.services.hsc.ICardReapplyService;
import com.gy.hsxt.access.web.person.services.safetyset.IPwdSetService;
import com.gy.hsxt.bs.api.order.IBSOrderService;
import com.gy.hsxt.bs.api.tool.IBSToolOrderService;
import com.gy.hsxt.bs.bean.order.DeliverInfo;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.tool.OrderBean;
import com.gy.hsxt.bs.bean.tool.ToolOrderPay;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolOrderResult;
import com.gy.hsxt.bs.common.enumtype.order.OrderChannel;
import com.gy.hsxt.bs.common.enumtype.order.OrderGeneral;
import com.gy.hsxt.common.bean.OpenQuickPayBean;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.common.IUCAsReceiveAddrInfoService;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsReceiveAddr;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-access-web-person
 * 
 *  Package Name    : com.gy.hsxt.access.web.person.hsc.controller
 * 
 *  File Name       : CardReapplyController.java
 * 
 *  Creation Date   : 2015-9-18 19:54:45  
 *  
 *  updateUse
 * 
 *  Author          : zhangcy
 * 
 *  Purpose         : 互生卡补办<br/>
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("cardReapply")
public class CardReapplyController extends BaseController {
	@Resource
	private ICardReapplyService icardReapplyService;

	@Autowired
	private IUCAsReceiveAddrInfoService iUCAsReceiveAddrInfoService;

	@Autowired
	private IPubParamService pubParamService;// 平台服务公共信息服务类

	@Autowired
	private IBSToolOrderService iBSToolOrderService;

	@Autowired
	private LcsClient lcsClient; // 全局配置服务接口

	/**
	 * UC密码Service
	 */
	@Autowired
	private IUCAsPwdService ucAsPwdService;

	/**
	 * bs订单Service
	 */
	@Autowired
	private IBSOrderService ibSOrderService;

	/**
     * 交易密码服务类
     */
    @Resource
    private IPwdSetService pwdSetService;
	/**
	 * 查询收件信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/query_user_address" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryUserAddress(HttpServletRequest request)
	{
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String custId = request.getParameter("custId");
			try
			{
				List<ReceiveAddress> result = icardReapplyService.getUserAddressList(custId);
				httpRespEnvelope = new HttpRespEnvelope(result);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 添加收件信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/add_user_address" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope addUserAddress(@ModelAttribute ReceiveAddress receiveAddress, HttpServletRequest request)
	{
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				icardReapplyService.addUserAddress(receiveAddress);
				httpRespEnvelope = new HttpRespEnvelope();
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 查询省、市、区联动信息
	 * 
	 * @param areaCode
	 *            省编码/市编码/区编码
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/query_area_lists" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getAreaLists(String areaCode, HttpServletRequest request)
	{
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				List<Map<String, Object>> result = icardReapplyService.getAreaLists(areaCode);
				httpRespEnvelope = new HttpRespEnvelope(result);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 查询互生币余额
	 * 
	 * @param custId
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/query_user_balace" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getUserBalace(String custId, HttpServletRequest request)
	{
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				Map<String, Object> result = icardReapplyService.getUserBalace(custId);
				httpRespEnvelope = new HttpRespEnvelope(result);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 查询用户是否有未支付或支付未成功的补卡申请
	 * 
	 * @param custId
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/query_user_apply" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getUserApply(String custId, HttpServletRequest request)
	{
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				CardReapplyOrder result = icardReapplyService.getUserApplyOrder(custId);
				httpRespEnvelope = new HttpRespEnvelope(result);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 添加补卡申请
	 * 
	 * @param
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/add_card_reapply" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope addUserApply(@ModelAttribute CardReapplyOrder order, HttpServletRequest request)
	{
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				CardReapplyOrder result = icardReapplyService.addUserApplyOrder(order);
				httpRespEnvelope = new HttpRespEnvelope(result);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 互生卡补办 在线支付
	 * 
	 * @param order
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/add_car_reapply_bypaybtn" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope addUserApplyByPayBtn(@ModelAttribute CardReapplyOrder order, HttpServletRequest request)
	{
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				String result = icardReapplyService.addUserApplyByPayBtn(order);
				httpRespEnvelope = new HttpRespEnvelope(result);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 添加收货地址
	 * 
	 * @param
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/add_Address" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope addAddress(@ModelAttribute ReceiveAddress receiveAddress, String defaultAddr,
			HttpServletRequest request)
	{
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);

		LocalInfo lcalInfo = null; // 本地平台信息
		String custId = request.getParameter("custId");
		if (httpRespEnvelope == null)
		{
			try
			{

				AsReceiveAddr asReceiveAddr = new AsReceiveAddr();
				asReceiveAddr.setCustId(custId);
				asReceiveAddr.setReceiver(receiveAddress.getReceiverName());
				lcalInfo = pubParamService.findSystemInfo();
				String countryNo = lcalInfo.getCountryNo();
				asReceiveAddr.setCountryNo(countryNo);
				asReceiveAddr.setProvinceNo(receiveAddress.getProvinceCode());
				asReceiveAddr.setCityNo(receiveAddress.getCityCode());
				asReceiveAddr.setArea(receiveAddress.getAreaCode());
				asReceiveAddr.setAddress(receiveAddress.getAddress());
				asReceiveAddr.setPostCode(receiveAddress.getPostcode());
				asReceiveAddr.setTelphone(receiveAddress.getFixedTelephone());
				asReceiveAddr.setMobile(receiveAddress.getPhone());
				if (CommonUtils.toInteger(defaultAddr) != null)
				{
					asReceiveAddr.setIsDefault(CommonUtils.toInteger(defaultAddr));
				} else
				{
					asReceiveAddr.setIsDefault(0);
				}

				iUCAsReceiveAddrInfoService.addReceiveAddr(custId, asReceiveAddr);
				httpRespEnvelope = new HttpRespEnvelope();
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
				httpRespEnvelope.setRetCode(e.getErrorCode());
			}catch (Exception e)
			{
				httpRespEnvelope = new HttpRespEnvelope(new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET));
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 更新收货地址
	 * 
	 * @param
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/update_Address" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope updateAddress(@ModelAttribute ReceiveAddress receiveAddress, String defaultAddr,
			HttpServletRequest request)
	{
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);

		String custId = request.getParameter("custId");
		long addressId = Long.parseLong(receiveAddress.getId());
		if (httpRespEnvelope == null)
		{
			try
			{

				AsReceiveAddr asReceiveAddr = new AsReceiveAddr();
				asReceiveAddr.setAddrId(addressId);
				asReceiveAddr.setCustId(custId);
				asReceiveAddr.setReceiver(receiveAddress.getReceiverName());
				LocalInfo lcalInfo = pubParamService.findSystemInfo();
				String countryNo = lcalInfo.getCountryNo();
				asReceiveAddr.setCountryNo(countryNo);
				asReceiveAddr.setProvinceNo(receiveAddress.getProvinceCode());
				asReceiveAddr.setCityNo(receiveAddress.getCityCode());
				asReceiveAddr.setArea(receiveAddress.getAreaCode());
				asReceiveAddr.setAddress(receiveAddress.getAddress());
				asReceiveAddr.setPostCode(receiveAddress.getPostcode());
				asReceiveAddr.setTelphone(receiveAddress.getFixedTelephone());
				asReceiveAddr.setMobile(receiveAddress.getPhone());
				asReceiveAddr.setIsDefault(CommonUtils.toInteger(defaultAddr));
				iUCAsReceiveAddrInfoService.updateReceiveAddr(custId, asReceiveAddr);
				httpRespEnvelope = new HttpRespEnvelope();
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
				httpRespEnvelope.setRetCode(e.getErrorCode());
			}catch (Exception e)
			{
				httpRespEnvelope = new HttpRespEnvelope(new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET));
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 删除收货地址
	 * 
	 * @param
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/delete_Address" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope deleteAddress(@ModelAttribute ReceiveAddress receiveAddress, HttpServletRequest request)
	{
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);

		String custId = request.getParameter("custId");
		long addressId = Long.parseLong(receiveAddress.getId());
		if (httpRespEnvelope == null)
		{
			try
			{

				iUCAsReceiveAddrInfoService.deleteReceiveAddr(custId, addressId);
				httpRespEnvelope = new HttpRespEnvelope();

			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
				httpRespEnvelope.setRetCode(e.getErrorCode());
			}catch (Exception e)
			{
				httpRespEnvelope = new HttpRespEnvelope(new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET));
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 设置默认的收货地址
	 * 
	 * @param
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/set_Default_Addr" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope setDefaultReceiveAddr(@ModelAttribute ReceiveAddress receiveAddress,
			HttpServletRequest request)
	{
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);

		String custId = request.getParameter("custId");
		long addressId = Long.parseLong(receiveAddress.getId());
		if (httpRespEnvelope == null)
		{
			try
			{

				iUCAsReceiveAddrInfoService.setDefaultReceiveAddr(custId, addressId);
				httpRespEnvelope = new HttpRespEnvelope();

			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
				httpRespEnvelope.setRetCode(e.getErrorCode());
			}catch (Exception e)
			{
				httpRespEnvelope = new HttpRespEnvelope(new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET));
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 补卡申请下单
	 * 
	 * @param
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/person_Mend_CardOrder" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope personMendCardOrder(@ModelAttribute ReceiveAddress receiveAddress,
			HttpServletRequest request)
	{
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);

		String custId = request.getParameter("custId");
		String entCustId = request.getParameter("entCustId") == null ? custId : request.getParameter("entCustId");
		String hsResNo = request.getParameter("hsResNo");
		String custName = request.getParameter("custName");
		String remark = request.getParameter("remark");
		if (httpRespEnvelope == null)
		{
			try
			{

				OrderBean orderBean = new OrderBean();
				Order order = new Order();
				order.setCustId(custId);
				order.setHsResNo(hsResNo);
				order.setCustName(custName);
				order.setCustType(CustType.PERSON.getCode());
				order.setOrderType("105");
				order.setOrderChannel(OrderChannel.WEB.getCode());
				
				order.setOrderOperator(hsResNo);
				order.setIsInvoiced(OrderGeneral.NO.getCode());
				order.setIsNeedInvoice(OrderGeneral.NO.getCode());
				order.setOrderRemark(remark);
				orderBean.setOrder(order);

				DeliverInfo deliverInfo = new DeliverInfo();
				deliverInfo.setHsCustId(custId);
				deliverInfo.setStreetAddr(receiveAddress.getAddress());
				deliverInfo.setFullAddr(
						receiveAddress.getProvince() + receiveAddress.getCity() + receiveAddress.getAddress());
				deliverInfo.setLinkman(receiveAddress.getReceiverName());
				deliverInfo.setMobile(receiveAddress.getPhone());
				deliverInfo.setCreatedby(entCustId);
				orderBean.setDeliverInfo(deliverInfo);

				List<String> list = new ArrayList<String>();
				list.add(hsResNo);
				orderBean.setPerResNos(list);

				ToolOrderResult toolOrderResult = iBSToolOrderService.personMendCardOrder(orderBean);
				httpRespEnvelope = new HttpRespEnvelope(toolOrderResult);

			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
				httpRespEnvelope.setRetCode(e.getErrorCode());
			}catch (Exception e)
			{
				httpRespEnvelope = new HttpRespEnvelope(new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET));
			}
		}
		return httpRespEnvelope;
	}

	/*
	 * 
	 * @param order
	 * 
	 * @param request
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/search_Address_List" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope searchAddressList(HttpServletRequest request)
	{
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);

		String custId = request.getParameter("custId");
		if (httpRespEnvelope == null)
		{
			try
			{
				List<AsReceiveAddr> lsit = iUCAsReceiveAddrInfoService.listReceiveAddrByCustId(custId);
				httpRespEnvelope = new HttpRespEnvelope(lsit);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}catch (Exception e)
			{
				httpRespEnvelope = new HttpRespEnvelope(new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET));
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 条件查询收货地址
	 * 
	 * @param
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/query_Address_List" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryAddressList(HttpServletRequest request)
	{
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);

		String custId = request.getParameter("search_custId");
		if (httpRespEnvelope == null)
		{
			try
			{
				List<AsReceiveAddr> lsit = iUCAsReceiveAddrInfoService.listReceiveAddrByCustId(custId);
				httpRespEnvelope = new HttpRespEnvelope(lsit);
				httpRespEnvelope.setTotalRows(lsit.size());
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}catch (Exception e)
			{
				httpRespEnvelope = new HttpRespEnvelope(new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET));
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
	@RequestMapping(value =
	{ "/tool_order_payment" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope toolOrderPayment(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String custId = request.getParameter("custId");// 客户号
			String orderNo = request.getParameter("orderNo");// 订单号
			Integer payChannel = CommonUtils.toInteger(request.getParameter("payChannel"));// 支付方式
			String tradePwd = request.getParameter("tradePwd"); // 交易密码
			String smsCode = request.getParameter("smsCode"); // 短信验证码
			String bindingNo = request.getParameter("bindingNo"); // 快捷签约号
			String randomToken = request.getParameter("randomToken"); // 随机token

			RequestUtil.verifyParamsIsNotEmpty(new Object[]
			{ payChannel, RespCode.EW_TOOL_PAY_CHANNEL_INVALID.getCode(),
					RespCode.EW_TOOL_PAY_CHANNEL_INVALID.getDesc() }, new Object[]
			{ orderNo, RespCode.EW_TOOL_ORDERNO_INVALID.getCode(), RespCode.EW_TOOL_ORDERNO_INVALID.getDesc() });
			// 互生币支付
			if (payChannel.intValue() == PayChannel.HS_COIN_PAY.getCode().intValue())
			{
				RequestUtil.verifyParamsIsNotEmpty(new Object[]
				{ custId, RespCode.AS_CUSTID_INVALID.getCode(), RespCode.AS_CUSTID_INVALID.getDesc() }, new Object[]
				{ tradePwd, RespCode.AS_TRADEPWD_INVALID.getCode(), RespCode.AS_TRADEPWD_INVALID.getDesc() },
						new Object[]
				{ randomToken, RespCode.AS_SECURE_TOKEN_NULL.getCode(), RespCode.AS_SECURE_TOKEN_NULL.getDesc() });
				
			}
			// 快捷支付支付
			if (payChannel.intValue() == PayChannel.QUICK_PAY.getCode().intValue())
			{
				RequestUtil.verifyParamsIsNotEmpty(new Object[]
				{ custId, RespCode.AS_CUSTID_INVALID.getCode(), RespCode.AS_CUSTID_INVALID.getDesc() }, new Object[]
				{ smsCode, RespCode.EW_SMSCODE_INVALID.getCode(), RespCode.EW_SMSCODE_INVALID.getDesc() }, new Object[]
				{ bindingNo, RespCode.EW_BINDINGNO_INVALID.getCode(), RespCode.EW_BINDINGNO_INVALID.getDesc() });
			}
			
			try
			{
			    // 互生币支付 验证交易密码
	            if (payChannel.intValue() == PayChannel.HS_COIN_PAY.getCode().intValue())
	            {
	                // 交易密码验证
	                pwdSetService.checkTradePwd(custId, tradePwd,UserTypeEnum.CARDER.getType(), randomToken);
	            }
				// 地区平台信息
				LocalInfo info = lcsClient.getLocalInfo();

				ToolOrderPay bean = new ToolOrderPay();
				bean.setOrderNo(orderNo);
				bean.setPayChannel(payChannel);
				bean.setSmsCode(smsCode);
				bean.setBindingNo(bindingNo);
				bean.setJumpUrl(info.getWebPayJumpUrl());
				String ret = iBSToolOrderService.buyToolOrderToPay(bean);
				// 快捷支付发送http请求
				if (payChannel.intValue() == PayChannel.QUICK_PAY.getCode().intValue())
				{
					ret = HttpClientUtils.get(ret);
				}
				httpRespEnvelope = new HttpRespEnvelope(ret);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}catch (Exception e)
			{
				httpRespEnvelope = new HttpRespEnvelope(new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET));
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 开通快捷支付
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月12日 下午6:15:24
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/open_quick_pay" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope openQuickPay(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String orderNo = request.getParameter("orderNo");
			String custId = request.getParameter("custId");
			String bankCardNo = request.getParameter("bankCardNo");
			String bankCardType = request.getParameter("bankCardType");
			String bankId = request.getParameter("bankId");
			
			try
			{
			    RequestUtil.verifyParamsIsNotEmpty(new Object[]
			            { orderNo, RespCode.EW_TOOL_ORDERNO_INVALID.getCode(), RespCode.EW_TOOL_ORDERNO_INVALID.getDesc() },
			                    new Object[]
			            { custId, RespCode.AS_CUSTID_INVALID.getCode(), RespCode.AS_CUSTID_INVALID.getDesc() }, new Object[]
			            { bankCardNo, RespCode.AS_BANKCARDNO_INVALID.getCode(), RespCode.AS_BANKCARDNO_INVALID.getDesc() },
			                    new Object[]
			            { bankCardType, RespCode.AS_BANKCARDTYPE_INVALID.getCode(), RespCode.AS_BANKCARDTYPE_INVALID.getDesc() },
			                    new Object[]
			            { bankId, RespCode.AS_BANKID_INVALID.getCode(), RespCode.AS_BANKID_INVALID.getDesc() });
				HsAssert.isTrue(ValidateUtil.validateBankCardId(bankCardNo), RespCode.AS_BANKCARDNO_ERROR);
				// 地区平台信息
				LocalInfo info = lcsClient.getLocalInfo();
				String ret = ibSOrderService.getOpenQuickPayUrl(new OpenQuickPayBean(null, orderNo, null, null,
						info.getWebPayJumpUrl(), custId, bankCardNo, CommonUtils.toInteger(bankCardType), bankId));

				httpRespEnvelope = new HttpRespEnvelope(ret);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}catch (Exception e)
			{
				httpRespEnvelope = new HttpRespEnvelope(new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET));
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 发送快捷支付短信
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月12日 下午6:26:42
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/send_quick_pay_sms" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope sendQuickPaySms(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String orderNo = request.getParameter("orderNo");
			String bindingNo = request.getParameter("bindingNo");
			String privObligate = request.getParameter("privObligate");
			try
			{
				ibSOrderService.getQuickPaySmsCode(orderNo, bindingNo, privObligate);
				httpRespEnvelope = new HttpRespEnvelope();
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}catch (Exception e)
			{
				httpRespEnvelope = new HttpRespEnvelope(new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET));
			}
		}
		return httpRespEnvelope;
	}

	@Override
	protected IBaseService getEntityService()
	{
		return icardReapplyService;
	}

}
