package com.gy.hsxt.access.web.aps.services.accountCompany;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.ao.bean.BuyHsb;
import com.gy.hsxt.ao.bean.HsbToCash;
import com.gy.hsxt.ao.bean.ProxyBuyHsb;
import com.gy.hsxt.ao.bean.PvToHsb;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrder;
import com.gy.hsxt.bs.bean.order.CustomPointDividend;
import com.gy.hsxt.bs.bean.order.DividendDetail;
import com.gy.hsxt.bs.bean.order.OrderCustom;
import com.gy.hsxt.bs.bean.order.PointDividend;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.ps.bean.QueryResult;
import com.gy.hsxt.ps.bean.QuerySingle;
@SuppressWarnings("rawtypes")
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
	 * 获取年费订单详情
	 * 
	 * @param transNo
	 *            :订单编号
	 * @return AnnualFeeOrder: 年费订单实体类
	 */
	public AnnualFeeOrder queryAnnualFeeOrder(String transNo);

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
     * 查询企业待兑互生币详情
     * 
     * @param transNo
     * @return
     */
    public ProxyBuyHsb getEntProxyBuyHsbInfo(String transNo);
    
    /**
     * 查询互生币支付，撤单，退货
     * 
     * @param transNo
     * @return
     */
    public QueryResult singlePosQuery(QuerySingle querySingle);
    
    /**
     * 查询互生币支付，撤单，退货
     * 
     * @param transNo
     * @return
     */
    public QueryResult queryDetailsByTransNo(String transNo);
    
    /**
     * 投资分红详情查询
     * 
     * @param transNo
     * @return
     */
    public CustomPointDividend getInvestDividendInfo(String transNo,String year);
    
    /**
     * 投资分红汇总下的流水详情
     * 
     * @param transNo
     * @return
     */
    public PageData<DividendDetail> getDividendDetailList(String hsResNo,String year,Page page);
}
