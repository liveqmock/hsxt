/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.common.enumtype.order.OrderGeneral;
import com.gy.hsxt.bs.common.enumtype.order.OrderStatus;
import com.gy.hsxt.bs.common.enumtype.order.PayStatus;
import com.gy.hsxt.common.constant.Channel;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 对象工厂仓库相应对应
 * 
 * @Package: com.hsxt.bs.common
 * @ClassName: ObjectFactory
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月8日 上午10:41:04
 * @company: gyist
 * @version V3.0.0
 */
public class ObjectFactory implements Serializable {

	private static final long serialVersionUID = 3318096738764905656L;

	private ObjectFactory()
	{
		super();
	}

	/**
	 * 生成订单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月19日 上午11:29:43
	 * @param orderNo
	 *            订单号
	 * @param custId
	 *            客户号
	 * @param custName
	 *            客户名称
	 * @param bizNo
	 *            业务编号
	 * @param orderType
	 *            订单类型
	 * @param orderHbsamount
	 *            订单互生币金额
	 * @param currencyCode
	 *            币种
	 * @param exchangeRate
	 *            货币转换比率
	 * @param deliverId
	 *            收货信息编号
	 * @param payOverTime
	 *            支付超时时间
	 * @param operNo
	 *            操作员编号
	 * @param remark
	 *            备注
	 * @return
	 * @return : Order
	 * @version V3.0.0
	 */
	public static Order createOrder(String orderNo, String custId, String custName, String bizNo, String orderType,
			String orderHbsamount, String currencyCode, String exchangeRate, String deliverId, String payOverTime,
			String operNo, String remark)
	{
		// 互生号
		String hsResNo = custId.substring(0, 11);
		Order order = new Order();
		order.setOrderNo(orderNo);// 订单号
		order.setCustId(custId);// 客户号
		order.setHsResNo(hsResNo);// 互生号
		order.setCustName(custName);// 客户名称
		order.setCustType(HsResNoUtils.getCustTypeByHsResNo(hsResNo));// 客户类型
		order.setBizNo(bizNo);// 业务编号
		order.setOrderType(orderType);// 订单类型
		order.setIsProxy(true);// 是否平台代购
		order.setQuantity(null);
		order.setOrderUnit("");
		order.setOrderHsbAmount(orderHbsamount);// 互生币金额
		order.setOrderDerateAmount("0");// 减免金额
		order.setOrderOriginalAmount(orderHbsamount);// 原始金额
		order.setOrderCashAmount(BigDecimalUtils.ceilingMul(orderHbsamount, exchangeRate).toString());// 货币金额
		order.setExchangeRate(exchangeRate);// 货币转换比率
		order.setCurrencyCode(currencyCode);// 币种
		order.setOrderRemark(remark);// 备注
		order.setOrderTime(DateUtil.getCurrentDateTime());// 订单时间
		order.setDeliverId(deliverId);// 收货新编号
		order.setOrderChannel(Channel.WEB.getCode());// 订单渠道
		order.setOrderOperator(operNo);
		order.setPayOvertime(payOverTime);// 支付超时时间
		order.setOrderStatus(OrderStatus.WAIT_PAY.getCode());// 订单状态
		order.setPayStatus(PayStatus.WAIT_PAY.getCode());// 支付状态
		order.setIsNeedInvoice(OrderGeneral.YES.getCode());// 是否需要发票
		return order;
	}

	/**
	 * 去掉集合的重复数据
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月29日 下午8:33:34
	 * @param list
	 *            数据集合
	 * @return
	 * @return : List<T>
	 * @version V3.0.0
	 */
	public static <T> List<T> removeDuplicate(List<T> list)
	{
		return new ArrayList<T>(new HashSet<T>(list));
	}

	/**
	 * 验证集合中是否包含重复数据
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2016/6/15 10:51
	 * @param: list
	 * 			数据集合
	 * @eturn: boolean
	 * @copany: gyist
	 * @version V3.0.0
	 */
	public static boolean isDuplicateList(List<?> list)
	{
		List<?> obj = ObjectFactory.removeDuplicate(list);
		if (obj.size() < list.size())
		{
			return false;
		}
		return true;
	}

	/**
	 * 获取对象中某个属性的值集合
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月30日 上午9:35:28
	 * @param list
	 *            数据集合
	 * @param attrName
	 *            属性名称
	 * @return
	 * @return : List<String>
	 * @version V3.0.0
	 */
	@SuppressWarnings("rawtypes")
	public static <T> List<Object> getListEntityAttr(List<T> list, String attrName)
	{
		// 返回集合
		List<Object> result = null;
		// 对象的属性get方法
		Method method = null;
		// 获取 clazz 类型中的 propertyName 的属性描述器
		PropertyDescriptor pd = null;
		if (StringUtils.isNotBlank(list) && StringUtils.isNotBlank(attrName))
		{
			// 类
			Class clazz = list.get(0).getClass();
			result = new ArrayList<Object>();
			try
			{
				// 根据属性获取属性描述器
				pd = new PropertyDescriptor(attrName, clazz);
				// 获取get方法
				method = pd.getReadMethod();
				for (T t : list)
				{
					result.add(method.invoke(t));
				}
			} catch (Exception ex)
			{
				SystemLog.error(ObjectFactory.class.getName(), "function:getListEntityAttr",
						"属性名错误,类中不包含此属性,List:" + list + "attr:" + attrName, ex);
				return null;
			}
		}
		return result;
	}
}
