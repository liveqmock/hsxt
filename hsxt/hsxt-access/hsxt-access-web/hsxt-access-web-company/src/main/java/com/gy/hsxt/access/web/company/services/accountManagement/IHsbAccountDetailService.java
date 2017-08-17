package com.gy.hsxt.access.web.company.services.accountManagement;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.ao.bean.BuyHsb;
import com.gy.hsxt.ao.bean.HsbToCash;
import com.gy.hsxt.ao.bean.PvToHsb;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrder;
import com.gy.hsxt.bs.bean.order.OrderCustom;
import com.gy.hsxt.bs.bean.order.PointDividend;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.bean.ReportsPointDeduction;
import com.gy.hsxt.uc.as.bean.device.AsDevice;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;

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
	 * 消费积分扣除统计查询
	 * 
	 * @param filterMap
	 *            查询条件
	 * @param page
	 *            分页信息（curPage 当前页,pageSize 每页记录数）
	 * @return 消费积分扣除统计查询信息
	 * @throws HsException
	 */
	public PageData<ReportsPointDeduction> searchReportsPointDeduction(
			Map filterMap, Map sortMap, Page page) throws HsException;

	/**
	 * 消费积分扣除终端统计查询
	 * 
	 * @param filterMap
	 *            查询条件
	 * @param page
	 *            分页信息（curPage 当前页,pageSize 每页记录数）
	 * @return 消费积分扣除终端统计查询信息
	 * @throws HsException
	 */
	public PageData<ReportsPointDeduction> searchReportsPointDeductionByChannel(
			Map filterMap, Map sortMap, Page page) throws HsException;

	/**
	 * 消费积分扣除操作员统计查询
	 * 
	 * @param filterMap
	 *            查询条件
	 * @param page
	 *            分页信息（curPage 当前页,pageSize 每页记录数）
	 * @return 消费积分扣除操作员统计查询信息
	 * @throws HsException
	 */
	public PageData<ReportsPointDeduction> searchReportsPointDeductionByOper(
			Map filterMap, Map sortMap, Page page) throws HsException;

	/**
	 * 消费积分扣除统计汇总查询
	 * 
	 * @param pointDedution
	 *            查询条件
	 * @return 消费积分扣除统计汇总查询信息
	 * @throws HsException
	 */
	public ReportsPointDeduction searchReportsPointDeductionSum(
			ReportsPointDeduction pointDedution) throws HsException;

	/**
	 * 消费积分扣除终端统计汇总查询
	 * 
	 * @param reportsPointDeduction
	 *            查询条件
	 * @return 消费积分扣除统计汇总信息
	 * @throws HsException
	 */
	public ReportsPointDeduction searchReportsPointDeductionByChannelSum(
			ReportsPointDeduction pointDedution) throws HsException;

	/**
	 * 消费积分扣除操作员统计汇总查询
	 * 
	 * @param reportsPointDeduction
	 *            查询条件
	 * @return 消费积分扣除统计汇总信息
	 * @throws HsException
	 */
	public ReportsPointDeduction searchReportsPointDeductionByOperSum(
			ReportsPointDeduction pointDedution) throws HsException;
	
	
	/**
     * 获取终端编号列表查询
     * @param filterMap 查询条件
     * @return 获取终端编号列表
     * @throws HsException
     */
    public PageData<AsDevice> getPosDeviceNoList(String entResNo) throws HsException;
	
	/**
     * 获取操作员编号列表查询
     * @param entCustId  查询条件
     * @return 获取操作员编号列表
     * @throws HsException
     */
    public List<AsOperator> getOperaterNoList(String entCustId) throws HsException;
    
}
