package com.gy.hsxt.access.web.company.services.accountManagement;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsxt.ac.api.IAccountSearchService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.ao.api.IAOCurrencyConvertService;
import com.gy.hsxt.ao.api.IAOExchangeHsbService;
import com.gy.hsxt.ao.bean.BuyHsb;
import com.gy.hsxt.ao.bean.HsbToCash;
import com.gy.hsxt.ao.bean.PvToHsb;
import com.gy.hsxt.bs.api.annualfee.IBSAnnualFeeService;
import com.gy.hsxt.bs.api.order.IBSInvestProfitService;
import com.gy.hsxt.bs.api.order.IBSOrderService;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrder;
import com.gy.hsxt.bs.bean.order.OrderCustom;
import com.gy.hsxt.bs.bean.order.PointDividend;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.api.IReportsPointDeductionSearchService;
import com.gy.hsxt.rp.bean.ReportsPointDeduction;
import com.gy.hsxt.uc.as.api.device.IUCAsDeviceService;
import com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService;
import com.gy.hsxt.uc.as.bean.device.AsDevice;
import com.gy.hsxt.uc.as.bean.enumerate.AsDeviceTypeEumn;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;

@Service("hsbAccountDetailService")
public class HsbAccountDetailService extends BaseServiceImpl implements
		IHsbAccountDetailService {
	@Autowired
	private IAccountSearchService accountSearch;

	@Autowired
	private IBSInvestProfitService ibsInvestProfitService;// 积投资分红

	@Autowired
	private IBSAnnualFeeService ibsAnnualFeeService;// 获取年费

	@Autowired
	private IBSOrderService ibsOrderService;// 订单详情

	@Autowired
	private IAOCurrencyConvertService iaoCurrencyConvertService;// 货币转换 积分转互生币

	@Autowired
	private IAOExchangeHsbService iaoExchangeHsbService;// 兑换互生币
	@Autowired
	private IReportsPointDeductionSearchService irpPointDeductionSearchService;// 消费积分扣除统计接口

	@Autowired
	private IUCAsDeviceService iUCAsDeviceService;
	
	@Autowired
	private IUCAsOperatorService iUCAsOperatorService;
	
	/**
	 * 查询积分投资分红详情
	 * 
	 * @param transNo
	 *            : 分红流水号
	 * @return PointDividend:积分投资分红实体类
	 */
	@Override
	public PointDividend getPointDividendInfo(String transNo) {
		return ibsInvestProfitService.getPointDividendInfo(transNo);
	}

	/**
	 * 获取年费订单详情
	 * 
	 * @param transNo
	 *            :订单编号
	 * @return AnnualFeeOrder: 年费订单实体类
	 */
	@Override
	public AnnualFeeOrder queryAnnualFeeOrder(String transNo) {
		return ibsAnnualFeeService.queryAnnualFeeOrder(transNo);
	}

	/**
	 * 线下兑换互生币 查询订单详情
	 * 
	 * @param orderNo
	 * @return
	 */
	@Override
	public OrderCustom getOrder(String transNo) {
		return ibsOrderService.getOrder(transNo);
	}

	/**
	 * 查询互生币转货币详情
	 * 
	 * @param transNo
	 * @return
	 */
	@Override
	public HsbToCash getHsbToCashInfo(String transNo) {
		return iaoCurrencyConvertService.getHsbToCashInfo(transNo);
	}

	/**
	 * 查询积分转互生币详情
	 * 
	 * @param transNo
	 * @return
	 */
	@Override
	public PvToHsb getPvToHsbInfo(String transNo) {
		return iaoCurrencyConvertService.getPvToHsbInfo(transNo);
	}

	/**
	 * 查询兑换互生币详情
	 * 
	 * @param transNo
	 * @return
	 */
	@Override
	public BuyHsb getBuyHsbInfo(String transNo) {
		return iaoExchangeHsbService.getBuyHsbInfo(transNo);
	}

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
	@Override
	public PageData<ReportsPointDeduction> searchReportsPointDeduction(
			Map filterMap, Map sortMap, Page page) throws HsException {
		ReportsPointDeduction pointDedution = createReportsPointDeduction(filterMap);

		PageData<ReportsPointDeduction> rpds= irpPointDeductionSearchService.searchReportsPointDeduction(
				pointDedution, page);
		return rpds;
	}
	/**
     * 消费积分扣除统计汇总查询
     * @param reportsPointDeduction 查询条件
     * @return 消费积分扣除统计汇总信息
     * @throws HsException
     */
	@Override
    public ReportsPointDeduction searchReportsPointDeductionSum(ReportsPointDeduction pointDedution) throws HsException{
    	return irpPointDeductionSearchService.searchReportsPointDeductionSum(pointDedution);
    }

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
	@Override
	public PageData<ReportsPointDeduction> searchReportsPointDeductionByChannel(
			Map filterMap, Map sortMap, Page page) throws HsException {
		ReportsPointDeduction pointDedution = createReportsPointDeduction(filterMap);
		return irpPointDeductionSearchService
				.searchReportsPointDeductionByChannel(pointDedution, page);
	}
	/**
     * 消费积分扣除终端统计汇总查询
     * @param reportsPointDeduction 查询条件
     * @return 消费积分扣除统计汇总信息
     * @throws HsException
     */
	@Override
    public ReportsPointDeduction searchReportsPointDeductionByChannelSum(ReportsPointDeduction pointDedution) throws HsException{
    	return irpPointDeductionSearchService.searchReportsPointDeductionByChannelSum(pointDedution);
    }
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
	@Override
	public PageData<ReportsPointDeduction> searchReportsPointDeductionByOper(
			Map filterMap, Map sortMap, Page page) throws HsException {
		ReportsPointDeduction pointDedution = createReportsPointDeduction(filterMap);
		return irpPointDeductionSearchService
				.searchReportsPointDeductionByOper(pointDedution, page);
	}
	/**
     * 消费积分扣除操作员统计汇总查询
     * @param reportsPointDeduction 查询条件
     * @return 消费积分扣除统计汇总信息
     * @throws HsException
     */
	@Override
    public ReportsPointDeduction searchReportsPointDeductionByOperSum(ReportsPointDeduction pointDedution) throws HsException{
    	return irpPointDeductionSearchService.searchReportsPointDeductionByOperSum(pointDedution);
    }
	
	/**
     * 获取终端编号列表查询
     * 
     * @param filterMap 查询条件
     * @param entResNo 企业互生号 currentPage 当前页,pageCount 每页记录数）
     * @return 终端编号列表
     * @throws HsException
     */
    @Override
    public PageData<AsDevice> getPosDeviceNoList(String entResNo) throws HsException {
        int deviceType = AsDeviceTypeEumn.POS.getType();
        String deviceNo = null;//接口得优化，不需要传值
        int currentPage = 1;//接口得优化，目前传固定值
        int pageCount = 10000;//接口得优化，目前传固定值
        return iUCAsDeviceService.listAsDevice(entResNo, deviceType, deviceNo, currentPage, pageCount);
    }
    
    /**
     * 获取操作员编号列表查询
     * 
     * @param filterMap 查询条件
     * @param entResNo 企业互生号 currentPage 当前页,pageCount 每页记录数）
     * @return 操作员编号列表
     * @throws HsException
     */
    @Override
    public List<AsOperator> getOperaterNoList(String entCustId) throws HsException {
        return iUCAsOperatorService.listOperByEntCustId(entCustId);
    }
	
	/**
	 * 创建账户明细查询类
	 * 
	 * @param filter
	 * @return
	 */
	ReportsPointDeduction createReportsPointDeduction(Map filter) {
		ReportsPointDeduction pointDedution = new ReportsPointDeduction();
		// 客户号
		String entCustId = (String) filter.get("entCustId");
		if (!StringUtils.isEmpty(entCustId) && !"undefined".equals(entCustId)) {
			pointDedution.setCustId(entCustId);
		}
		// 操作员账号
		String operCustName = (String) filter.get("operCustName");
		if (!StringUtils.isEmpty(operCustName)
				&& !"undefined".equals(operCustName)) {
			pointDedution.setOperNo(operCustName);
		}
		// 设备编号
		String equipmentNo = (String) filter.get("equipmentNo");
		if (!StringUtils.isEmpty(equipmentNo)
				&& !"undefined".equals(equipmentNo)) {
			pointDedution.setEquipmentNo(equipmentNo);
		}
		// 终端类型
		String channelType = (String) filter.get("channelType");
		if (!StringUtils.isEmpty(channelType)
				&& !"undefined".equals(channelType)) {
			pointDedution.setChannelType(Integer.parseInt(channelType));
		}
		// 开始时间
		String beginDate = (String) filter.get("beginDate");
		if (!StringUtils.isEmpty(beginDate) && !"undefined".equals(beginDate)) {
			pointDedution.setBeginDate(beginDate);
		}
		// 结束时间
		String endDate = (String) filter.get("endDate");
		if (!StringUtils.isEmpty(endDate) && !"undefined".equals(endDate)) {
			pointDedution.setEndDate(endDate);
		}
		return pointDedution;
	}
}
