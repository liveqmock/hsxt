package com.gy.hsxt.access.web.person.services.accountManagement;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.ao.bean.BuyHsb;
import com.gy.hsxt.ao.bean.HsbToCash;
import com.gy.hsxt.ao.bean.PvToHsb;
import com.gy.hsxt.bs.bean.order.OrderCustom;
import com.gy.hsxt.bs.bean.order.PointDividend;
import com.gy.hsxt.ws.bean.GrantDetail;

public interface IHsbAccountDetailService extends IBaseService {
	/**
	 * 查询积分投资分红详情
	 * 
	 * @param transNo
	 *            : 分红流水号
	 * @return PointDividend:积分投资分红实体类
	 */
	public PointDividend getPointDividendInfo(String transNo);

	/**
	 * 线下兑换互生币 查询订单详情
	 * 
	 * @param orderNo
	 * @return
	 */
	public OrderCustom getOrder(String transNo);

	/**
	 * 查询互生币转货币详情
	 * 
	 * @param transNo
	 * @return
	 */
	public HsbToCash getHsbToCashInfo(String transNo);

	/**
	 * 查询兑换互生币详情
	 * 
	 * @param transNo
	 * @return
	 */
	public BuyHsb getBuyHsbInfo(String transNo);
	/**
	 * 查询积分转互生币详情
	 * 
	 * @param transNo
	 * @return
	 */
	public PvToHsb getPvToHsbInfo(String transNo);
	/**
	 * 查询积分福利详情
	 * 
	 * @param transNo
	 * @return
	 */
	public GrantDetail queryWelfareApplyDetail(String transNo);
}
