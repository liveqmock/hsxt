/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tool.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.api.tool.IBSToolOrderService;
import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.order.AccountDetail;
import com.gy.hsxt.bs.bean.order.DeliverInfo;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.tempacct.TempAcctLink;
import com.gy.hsxt.bs.bean.tempacct.TempAcctLinkQuery;
import com.gy.hsxt.bs.bean.tool.*;
import com.gy.hsxt.bs.bean.tool.resultbean.*;
import com.gy.hsxt.bs.common.ObjectFactory;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.StringUtil;
import com.gy.hsxt.bs.common.TransCodeUtil;
import com.gy.hsxt.bs.common.bean.PayUrl;
import com.gy.hsxt.bs.common.enumtype.ApprStatus;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.EnableStatus;
import com.gy.hsxt.bs.common.enumtype.RemarkType;
import com.gy.hsxt.bs.common.enumtype.order.*;
import com.gy.hsxt.bs.common.enumtype.tool.*;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.common.util.ValidateParamUtil;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.order.interfaces.IAccountDetailService;
import com.gy.hsxt.bs.order.interfaces.IOrderService;
import com.gy.hsxt.bs.order.mapper.DeliverInfoMapper;
import com.gy.hsxt.bs.order.mapper.OrderMapper;
import com.gy.hsxt.bs.task.bean.Task;
import com.gy.hsxt.bs.task.interfaces.ITaskService;
import com.gy.hsxt.bs.tempacct.mapper.TempAcctLinkMapper;
import com.gy.hsxt.bs.tool.bean.ConfigSegment;
import com.gy.hsxt.bs.tool.bean.MakingCardInfo;
import com.gy.hsxt.bs.tool.bean.ToolVaild;
import com.gy.hsxt.bs.tool.mapper.*;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.*;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.*;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntAllInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntBaseInfo;

/**
 * 工具购买下单API接口实现类
 *
 * @version V3.0.0
 * @Package: com.hsxt.bs.btool.service
 * @ClassName: ToolOrderService
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午12:07:13
 * @company: gyist
 */
@Service("toolOrderService")
public class ToolOrderService implements IBSToolOrderService {

	/**
	 * 业务服务私有配置参数
	 **/
	@Autowired
	private BsConfig bsConfig;

	/**
	 * 工具产品Mapper
	 **/
	@Autowired
	private ToolProductMapper toolProductMapper;

	/**
	 * 互生卡样Mapper
	 **/
	@Autowired
	private CardStyleMapper cardStyleMapper;

	/**
	 * 代购订单Mapper
	 **/
	@Autowired
	private ProxyOrderMapper proxyOrderMapper;

	/**
	 * 仓库Mapper
	 **/
	@Autowired
	private WarehouseMapper warehouseMapper;

	/**
	 * 工具配置单Mapper
	 **/
	@Autowired
	private ToolConfigMapper toolConfigMapper;

	/**
	 * 互生卡信息Mapper
	 **/
	@Autowired
	private CardInfoMapper cardInfoMapper;

	/**
	 * 订单Mapper
	 **/
	@Autowired
	private OrderMapper orderMapper;

	/**
	 * 收货信息Mapper
	 **/
	@Autowired
	private DeliverInfoMapper deliverInfoMapper;

	/**
	 * 订单Service
	 **/
	@Autowired
	private IOrderService orderService;

	/**
	 * 公共Mapper
	 **/
	@Autowired
	private CommonMapper commonMapper;

	/**
	 * 内部实现类
	 **/
	@Autowired
	private InsideInvokeCall insideInvokeCall;

	/**
	 * 任务Service
	 **/
	@Autowired
	private ITaskService taskService;

	/**
	 * BS公共Service
	 **/
	@Autowired
	private ICommonService commonService;

	/**
	 * 记帐分解Service
	 **/
	@Autowired
	private IAccountDetailService accountDetailService;

	/**
	 * 查询售后服务查询Mapper
	 **/
	@Autowired
	private AfterServiceSelectMapper afterServiceSelectMapper;

	/**
	 * 帐关联申请Mapper接口
	 */
	@Autowired
	private TempAcctLinkMapper tempAcctLinkMapper;

	/**
	 * 资源段Mapper接口
	 */
	@Autowired
	private ResSegmentMapper resSegmentMapper;

	/**
	 * 查询可以申购的工具
	 *
	 * @param custType
	 *            客户类型
	 * @return
	 * @Description:
	 */
	@Override
	public List<ToolProduct> queryMayBuyToolProduct(Integer custType)
	{
		if (StringUtils.isBlank(custType))
		{
			return null;
		}
		return toolProductMapper.selectMayBuyToolProduct(custType, null);
	}

	/**
	 * 根据工具服务查询客户可以购买的工具
	 *
	 * @param custType
	 *            客户类型
	 * @param toolService
	 *            工具服务
	 * @return
	 * @Description:
	 */
	@Override
	public List<ToolProduct> queryMayBuyToolProductByToolSevice(Integer custType, String toolService)
	{
		if (StringUtils.isBlank(custType) || StringUtils.isBlank(toolService))
		{
			return null;
		}
		return toolProductMapper.selectMayBuyToolProduct(custType,
				StringUtil.assembleQueryInIf(ToolService.getToolCategoryCode(toolService)));
	}

	/**
	 * 查询企业的所有个性卡样
	 *
	 * @param entResNo
	 *            互生号
	 * @return
	 * @Description:
	 */
	@Override
	public List<CardStyle> queryEntSpecialCardStyle(String entResNo)
	{
		if (StringUtils.isBlank(entResNo))
		{
			return null;
		}
		return cardStyleMapper.selectCardStyleByEnt(entResNo);
	}

	/**
	 * 查询企业的所有卡样(标准 + 个性)
	 *
	 * @param entResNo
	 *            互生号
	 * @return
	 * @Description:
	 */
	@Override
	public List<CardStyle> queryEntCardStyleByAll(String entResNo)
	{
		if (StringUtils.isBlank(entResNo))
		{
			return null;
		}
		return cardStyleMapper.selectCardStyleByMark(entResNo, entResNo.substring(0, 5) + "000000");
	}

	/**
	 * 查询可以购买的数量
	 *
	 * @param entCustId
	 *            企业客户号
	 * @param categoryCode
	 *            工具类别代码
	 * @return
	 * @Description:
	 */
	@Override
	public Integer queryMayBuyToolNum(String entCustId, String categoryCode)
	{
		if (StringUtils.isBlank(entCustId) || StringUtils.isBlank(categoryCode))
		{
			return null;
		}
		// 消费刷卡器和积分刷卡器同用数量
		if (categoryCode.equals(CategoryCode.POINT_MCR.name()) || categoryCode.equals(CategoryCode.CONSUME_MCR.name()))
		{
			categoryCode = "'" + CategoryCode.CONSUME_MCR.name() + "','" + CategoryCode.POINT_MCR.name() + "'";
		} else
		{
			categoryCode = "'" + categoryCode + "'";
		}
		return toolProductMapper.selectMayBuyToolNum(entCustId, categoryCode);
	}

	/**
	 * 申购工具下单
	 *
	 * @param bean
	 *            工具下单参数实体
	 * @return
	 * @throws HsException
	 * @Description:
	 */
	@Override
	@Transactional
	public ToolOrderResult addToolBuyOrder(OrderBean bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:addToolBuyOrder", "params==>" + bean, "申购工具下单");
		// 订单编号
		String orderNo = "";
		// 收货信息编号
		String deliverId = "";
		// 支付超时时间
		String payOverTime = "";
		// 订单实体
		Order order = null;
		// 收货信息实体
		DeliverInfo deliverInfo = null;
		// 配置单集合
		List<ToolConfig> confs = null;
		// 订单互生币金额
		String orderHsbAmount = "0";
		// 地区平台信息
		LocalInfo info = null;
		// 仓库
		Warehouse wh = null;
		// 企业基本信息
		BsEntBaseInfo baseInfo = null;
		String now = DateUtil.getCurrentDateTime();

		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "申购工具下单参数为NULL," + bean);
		// 订单号、收货信息编号
		orderNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
		deliverId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());

		// 订单、收货信息、配置单、支付超时时间
		order = bean.getOrder();
		deliverInfo = bean.getDeliverInfo();
		confs = bean.getConfs();
		payOverTime = DateUtil.addDaysStr(null, bsConfig.getToolOrderInvalidDays());

		// 验证数据
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "工具申购下单参数为NULL," + bean);
		HsAssert.notNull(order, RespCode.PARAM_ERROR, "工具申购下单订单参数为NULL," + bean);
		HsAssert.notNull(deliverInfo, RespCode.PARAM_ERROR, "工具申购下单收货信息参数为NULL," + bean);
		HsAssert.isTrue(!CollectionUtils.isEmpty(confs), RespCode.PARAM_ERROR, "工具申购下单配置单参数为NULL," + bean);

		// 验证购买的数量是否超标
		String isMayBuy = vaildCreateToolOrderBuyNum(order.getCustId(), bean.getConfs(), null);
		HsAssert.isTrue(StringUtils.isBlank(isMayBuy), BSRespCode.BS_TOOL_ORDER_BUY_NUM_ERROR,
				"工具申购下单购买数量超标,工具类别代码:" + isMayBuy);

		// 查询地区平台信息
		info = commonService.getAreaPlatInfo();
		HsAssert.notNull(info, BSRespCode.BS_INVOKE_LCS_FAIL, "调用LCS失败," + bean);

		// 查询用户中心企业信息
		baseInfo = commonService.getEntBaseInfoToUc(order.getHsResNo(), null);
		if (null == baseInfo)
		{
			SystemLog.info(this.getClass().getName(), "function:addToolBuyOrder",
					BSRespCode.BS_NOT_QUERY_ENT_INFO.getCode() + ":未查询到企业信息," + bean);
		}
		try
		{
			// 根据企业省编号获取仓库，不存在就获取地区平台默认仓库
			wh = warehouseMapper.selectWarehouseByNo(null != baseInfo ? baseInfo.getProvinceCode() : null);
			if (null == wh)
			{
				// 获取地区平台默认仓库
				wh = warehouseMapper.selectWarehouseByDefault();
			}
			// 设置配置单
			for (ToolConfig conf : confs)
			{
				// 配置单总金额
				String hsbAmount = BigDecimalUtils.ceilingMul(conf.getPrice(), String.valueOf(conf.getQuantity()))
						.toString();
				// 订单互生币金额
				orderHsbAmount = BigDecimalUtils.ceilingAdd(orderHsbAmount, hsbAmount).toString();
				// 设置工具配置单的数据
				conf.setConfNo(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));// 配置单号
				conf.setOrderNo(orderNo);// 订单号
				conf.setConfStatus(ConfStatus.WAIT_PAY.getCode());// 配置状态
				conf.setConfType(ConfType.ADD_CONFIG.getCode());// 配置类型
				conf.setTotalAmount(hsbAmount);// 配置单总金额
				conf.setWhId(wh.getWhId());// 仓库id
			}

			// 设置订单参数
			order.setOrderNo(orderNo);// 订单号
			order.setIsProxy(false);// 是否平台代购
			order.setOrderTime(now);// 订单时间
			//order.setOrderType(OrderType.BUY_TOOL.getCode()); // 订单类型
			order.setOrderOriginalAmount(orderHsbAmount);// 原始金额
			order.setOrderDerateAmount("0");// 减免金额
			order.setOrderCashAmount(BigDecimalUtils.ceilingMul(orderHsbAmount, info.getExchangeRate()).toString());// 货币金额
			order.setOrderHsbAmount(orderHsbAmount);// 互生币金额
			order.setCurrencyCode(info.getHsbCode());// 币种
			order.setExchangeRate(info.getExchangeRate());// 货币转换比率
			order.setDeliverId(deliverId);// 收货信息编号
			order.setPayOvertime(payOverTime);// 订单支付超时时间
			order.setOrderStatus(OrderStatus.WAIT_PAY.getCode());// 订单状态
			order.setPayStatus(PayStatus.WAIT_PAY.getCode());// 支付状态
			order.setIsNeedInvoice(OrderGeneral.YES.getCode());// 是否需要发票

			// 收货信息
			deliverInfo.setDeliverId(deliverId);
			deliverInfo.setCreatedDate(now);

			// 插入订单、配置单、收货信息数据
			orderMapper.insertOrder(order);
			toolConfigMapper.batchInsertToolConfig(confs);
			deliverInfoMapper.insertDeliverInfo(deliverInfo);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:addToolBuyOrder",
					BSRespCode.BS_TOOL_ORDERE_SUBMIT_FAIL.getCode() + "工具订单提交失败," + bean, ex);
			throw new HsException(BSRespCode.BS_TOOL_ORDERE_SUBMIT_FAIL, "工具订单提交失败," + bean);
		}
		return new ToolOrderResult(orderNo, payOverTime, order.getOrderHsbAmount(), order.getOrderHsbAmount(),
				info.getExchangeRate(), info.getCurrencyNameCn());
	}

	/**
	 * 申购企业资源段下单
	 * 
	 * @param bean
	 *            企业资源段下单参数实体
	 * @return
	 * @throws HsException
	 */
	@Override
	@Transactional
	public ToolOrderResult addResSegmentBuyOrder(OrderBean bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:addResSegmentBuyOrder", "params==>" + bean, "申购企业资源段下单");
		// 订单编号
		String orderNo = "";
		// 收货信息编号
		String deliverId = "";
		// 支付超时时间
		String payOverTime = "";
		// 订单实体
		Order order = null;
		// 收货信息实体
		DeliverInfo deliverInfo = null;
		// 配置单集合
		List<ToolConfig> confs = null;
		// 订单互生币金额
		String orderHsbAmount = "0";
		// 地区平台信息
		LocalInfo info = null;
		// 仓库
		Warehouse wh = null;
		// 配置单编号(用于资源段)
		String confNo = null;
		// 资源段Id集合
		List<String> segmentIds = null;
		// 配置资源段
		ConfigSegment confSegment = null;
		String now = DateUtil.getCurrentDateTime();

		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "申购企业资源段下单参数为NULL," + bean);
		// 订单号、收货信息编号
		orderNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
		deliverId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());

		// 订单、收货信息、配置单、支付超时时间
		order = bean.getOrder();
		deliverInfo = bean.getDeliverInfo();
		confs = bean.getConfs();
		payOverTime = DateUtil.addDaysStr(null, bsConfig.getToolOrderInvalidDays());

		// 验证数据
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "申购企业资源段下单参数为NULL," + bean);
		HsAssert.notNull(order, RespCode.PARAM_ERROR, "申购企业资源段下单订单参数为NULL," + bean);
		HsAssert.notNull(deliverInfo, RespCode.PARAM_ERROR, "申购企业资源段下单收货信息参数为NULL," + bean);
		HsAssert.isTrue(!CollectionUtils.isEmpty(confs), RespCode.PARAM_ERROR, "申购企业资源段下单配置单参数为NULL," + bean);

		// 验证购买的数量是否超标
		String isMayBuy = vaildCreateToolOrderBuyNum(order.getCustId(), bean.getConfs(), null);
		HsAssert.isTrue(StringUtils.isBlank(isMayBuy), BSRespCode.BS_TOOL_ORDER_BUY_NUM_ERROR,
				"申购企业资源段下单购买数量超标,工具类别代码:" + isMayBuy);

		// 查询地区平台信息
		info = commonService.getAreaPlatInfo();
		HsAssert.notNull(info, BSRespCode.BS_INVOKE_LCS_FAIL, "申购企业资源段下单,调用LCS失败," + bean);

		try
		{
			// 获取地区平台默认仓库
			wh = warehouseMapper.selectWarehouseByDefault();

			// 设置配置单
			for (ToolConfig conf : confs)
			{
				// 查询配置资源段
				confSegment = resSegmentMapper.selectConfigSegmentByNo(conf.getSegmentIds());

				// 配置单总金额
				String hsbAmount = confSegment.getAmount();
				// 订单互生币金额
				orderHsbAmount = BigDecimalUtils.ceilingAdd(orderHsbAmount, hsbAmount).toString();
				// 设置工具配置单的数据
				confNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
				conf.setConfNo(confNo);// 配置单号
				conf.setOrderNo(orderNo);// 订单号
				conf.setConfStatus(ConfStatus.WAIT_PAY.getCode());// 配置状态
				conf.setConfType(ConfType.ADD_CONFIG.getCode());// 配置类型
				conf.setTotalAmount(hsbAmount);// 配置单总金额
				conf.setQuantity(confSegment.getCount());// 配置数量
				conf.setPrice(bsConfig.getReformCardFee());// 价格
				conf.setWhId(wh.getWhId());// 仓库id
				segmentIds = conf.getSegmentIds();
			}

			// 验证是否选择资源段
			HsAssert.isTrue(null != segmentIds && segmentIds.size() > 0, RespCode.PARAM_ERROR, "选择的资源段不能为空," + bean);
			int count = resSegmentMapper.valiResSegmentIsOrder(segmentIds);
			HsAssert.isTrue(count == segmentIds.size(), BSRespCode.BS_SEGMENTID_HAS_ORDER_BOUGHT, "选择资源段有已下单或已申购的");

			// 设置订单参数
			order.setOrderNo(orderNo);// 订单号
			order.setIsProxy(false);// 是否平台代购
			order.setOrderType(OrderType.APPLY_PERSON_RESOURCE.getCode());// 订单类型
			order.setOrderTime(now);// 订单时间
			order.setOrderOriginalAmount(orderHsbAmount);// 原始金额
			order.setOrderDerateAmount("0");// 减免金额
			order.setOrderCashAmount(BigDecimalUtils.ceilingMul(orderHsbAmount, info.getExchangeRate()).toString());// 货币金额
			order.setOrderHsbAmount(orderHsbAmount);// 互生币金额
			order.setCurrencyCode(info.getHsbCode());// 币种
			order.setExchangeRate(info.getExchangeRate());// 货币转换比率
			order.setDeliverId(deliverId);// 收货信息编号
			order.setPayOvertime(payOverTime);// 订单支付超时时间
			order.setOrderStatus(OrderStatus.WAIT_PAY.getCode());// 订单状态
			order.setPayStatus(PayStatus.WAIT_PAY.getCode());// 支付状态
			order.setIsNeedInvoice(OrderGeneral.YES.getCode());// 是否需要发票

			// 收货信息
			deliverInfo.setDeliverId(deliverId);
			deliverInfo.setCreatedDate(now);

			// 插入订单、配置单、收货信息数据
			orderMapper.insertOrder(order);
			toolConfigMapper.batchInsertToolConfig(confs);
			deliverInfoMapper.insertDeliverInfo(deliverInfo);
			// 修改资源段配置单号
			resSegmentMapper.updateResSegmentByIds(segmentIds, null, confNo, BuyStatus.ORDER.getCode());

		} catch (HsException ex)
		{
			throw ex;
		}
		catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:addResSegmentBuyOrder",
					BSRespCode.BS_RES_SEGMENT_ORDER_SUBMIT_FAIL.getCode() + "申购资源段订单提交失败," + bean, ex);
			throw new HsException(BSRespCode.BS_RES_SEGMENT_ORDER_SUBMIT_FAIL, "申购资源段订单提交失败," + bean);
		}
		return new ToolOrderResult(orderNo, payOverTime, order.getOrderHsbAmount(), order.getOrderHsbAmount(),
				info.getExchangeRate(), info.getCurrencyNameCn());
	}

	/**
	 * 查询企业资源段
	 *
	 * @param entCustId
	 *            客户号
	 * @return
	 * @Description:
	 */
	@Override
	public EntResource queryEntResourceSegment(String entCustId)
	{
		// 总数
		int totalNum = 9999;
		// 已经购买数量
		int boughtNum = 0;
		// 还可以购买数量
		Integer mayBuyNum = null;
		// 可以购买段数
		int mayBuySegment = 0;
		// 已经购买段数
		int boughtSegment = 0;
		// 判断可以已购数量是否是1000的整数倍
		boolean falg = true;
		if (StringUtils.isBlank(entCustId))
		{
			return null;
		}
		if (!HsResNoUtils.isTrustResNo(entCustId.substring(0, 11)))
		{
			return null;
		}
		// 查询卡工具
		List<ToolProduct> tools = toolProductMapper.selectToolProductNotByStatus(CategoryCode.P_CARD.name());
		if (null == tools)
		{
			return null;
		}
		// 企业系统资源
		EntResource bean = new EntResource();
		if (null != tools && tools.size() > 0)
		{
			bean.setProduct(tools.get(0));
		}
		// 查询可以购买的数量
		mayBuyNum = toolProductMapper.selectMayBuyToolNum(entCustId, "'" + CategoryCode.P_CARD.name() + "'");
		if (null == mayBuyNum)
		{
			return null;
		}
		falg = mayBuyNum % 1000 == 0 ? false : true;
		boughtNum = totalNum - mayBuyNum;
		// 计算可以购买段数
		mayBuySegment = (mayBuyNum > 0 && falg) ? (mayBuyNum / 1000) + 1 : (mayBuyNum / 1000);
		// 计算已经购买段数
		if (boughtNum > 0)
		{
			boughtSegment = falg ? boughtNum / 1000 : (boughtNum / 1000) + 1;
		}
		// 设置开始购买段
		bean.setStartBuyRes(boughtSegment + 1);
		List<ResourceSegment> segment = new ArrayList<ResourceSegment>();
		// 计算已经购买资源段
		for (int i = 1; i <= boughtSegment; i++)
		{
			String segmentDesc = i == 1 ? "首" : "第" + i;
			String cardCount = (i == boughtSegment && !falg) ? "999" : "1000";
			segment.add(new ResourceSegment(i, segmentDesc + "段托管企业系统消费者资源", true, "20000", cardCount));
		}
		// 生成可以购买资源段
		for (int i = 1; i <= mayBuySegment; i++)
		{
			String cardCount = (i == mayBuySegment && falg) ? "999" : "1000";
			int count = boughtSegment + i;
			segment.add(new ResourceSegment(count, "第" + count + "段托管企业系统消费者资源", false, "20000", cardCount));
		}
		bean.setSegment(segment);
		return bean;
	}

	/**
	 * 查询企业资源段(新接口)
	 *
	 * @param entCustId
	 *            客户号
	 * @return
	 * @Description:
	 */
	@Override
	@Transactional
	public EntResSegment queryEntResSegmentNew(String entCustId) throws HsException
	{
		// 已经购买数量
		Integer boughtNum = null;
		// 资源段列表
		List<ResSegment> segment = null;
		// 下单数量
		Integer orderNum = null;
		if (StringUtils.isBlank(entCustId))
		{
			return null;
		}
		if (!HsResNoUtils.isTrustResNo(entCustId.substring(0, 11)))
		{
			return null;
		}
		// 查询卡工具
		List<ToolProduct> tools = toolProductMapper.selectToolProductNotByStatus(CategoryCode.P_CARD.name());
		if (null == tools)
		{
			return null;
		}
		// 企业系统资源
		EntResSegment bean = new EntResSegment();
		if (null != tools && tools.size() > 0)
		{
			bean.setProduct(tools.get(0));
		}
		// 根据查询企业的资源段详情
		segment = resSegmentMapper.selectResSegmentByIf(null, entCustId, null);
		// 如果企业已经保存了资源段之间返回
		if (null != segment && segment.size() > 0)
		{
			// 查询开始购买的段
			bean.setStartBuyRes(resSegmentMapper.selectStartBuySegmentByCust(entCustId));
			bean.setSegment(segment);
			return bean;
		}
		// 查询已购买的数量
		boughtNum = toolProductMapper.selectResourceSegmentBuy(entCustId, new int[]
		{ ConfStatus.WAIT_CONFIG.getCode(), ConfStatus.WAIT_MARK.getCode(), ConfStatus.WAIT_ENTER.getCode(),
				ConfStatus.WAIT_SEND.getCode(), ConfStatus.SENDED.getCode(), ConfStatus.SIGNED.getCode() });

		if (null == boughtNum)
		{
			return null;
		}
		// 查询下单的数量
		orderNum = toolProductMapper.selectResourceSegmentBuy(entCustId, new int[]
		{ ConfStatus.WAIT_PAY.getCode(), ConfStatus.WAIT_CONFIRM.getCode() });

		// 生成企业资源段数据
		bean = insideInvokeCall.createEntResourceSegment(entCustId, boughtNum.intValue(),
				orderNum == null ? 0 : orderNum.intValue());
		if (null != tools && tools.size() > 0)
		{
			bean.setProduct(tools.get(0));
		}
		try
		{
			// 插入企业的资源段
			resSegmentMapper.batchInsertResSegment(bean.getSegment());
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:queryEntResourceSegment",
					BSRespCode.BS_ADD_RESOURCE_SEGMENT_FAIL.getCode() + "新增企业的资源段失败," + segment, ex);
			throw new HsException(BSRespCode.BS_ADD_RESOURCE_SEGMENT_FAIL, "新增企业的资源段失败," + segment);
		}
		return bean;
	}

	/**
	 * 定制互生卡样下单
	 *
	 * @param bean
	 *            订制卡样下单参数实体
	 * @param style
	 *            卡样参数实体
	 * @return
	 * @throws HsException
	 * @Description:
	 */
	@Override
	@Transactional
	public ToolOrderResult addCardStyleFeeOrder(Order bean, CardStyle style) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:addCardStyleFeeOrder",
				"params==>order:" + bean + ",style:" + style, "定制互生卡样下单");
		// 订单号
		String orderNo = "";
		// 支付超时时间
		String payOverTime = "";
		// 订单互生币金额
		String orderHsbAmount = bsConfig.getCardStyleFee();
		// 地区平台信息
		LocalInfo info = null;

		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "定制互生卡样下单订单参数为NULL");
		// 订单号、支付超时是
		orderNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
		payOverTime = DateUtil.addDaysStr(null, bsConfig.getToolOrderInvalidDays());
		// 查询地区平台信息
		info = commonService.getAreaPlatInfo();
		HsAssert.notNull(info, BSRespCode.BS_INVOKE_LCS_FAIL, "调用LCS失败," + bean);

		try
		{
			// 设置订单参数
			bean.setOrderNo(orderNo);
			bean.setCustType(CustType.TRUSTEESHIP_ENT.getCode());// 客户类型
			bean.setOrderType(OrderType.CARD_STYLE_FEE.getCode());// 订单类型
			bean.setIsProxy(StringUtils.isNotBlank(bean.getIsProxy()) ? true : false);// 是否平台代购
			bean.setOrderHsbAmount(orderHsbAmount);// 互生币金额
			bean.setOrderDerateAmount("0");// 减免金额
			bean.setOrderOriginalAmount(orderHsbAmount);// 原始金额
			bean.setOrderCashAmount(BigDecimalUtils.ceilingMul(orderHsbAmount, info.getExchangeRate()).toString());// 货币金额
			bean.setOrderTime(DateUtil.getCurrentDateTime());
			bean.setPayOvertime(payOverTime);// 是否超时时间
			bean.setOrderStatus(OrderStatus.WAIT_PAY.getCode());// 订单状态
			bean.setPayStatus(PayStatus.WAIT_PAY.getCode());// 支付状态
			bean.setPayTime(null);// 支付时间
			bean.setExchangeRate(info.getExchangeRate().toString());// 货币转换比率
			bean.setCurrencyCode(info.getHsbCode());// 币种
			bean.setIsNeedInvoice(OrderGeneral.YES.getCode());// 是否需要发票

			// 插入订单数据
			orderMapper.insertOrder(bean);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:addCardStyleFeeOrder",
					BSRespCode.BS_CARD_STYLE_ORDERE_SUBMIT_FAIL.getCode() + "订制卡样订单提交失败," + bean, ex);
			throw new HsException(BSRespCode.BS_CARD_STYLE_ORDERE_SUBMIT_FAIL, "订制卡样订单提交失败," + bean);
		}
		if (null != style)
		{
			style.setOrderNo(orderNo);
			// 插入卡样数据
			addSpecialCardStyleEnt(style);
		}
		return new ToolOrderResult(orderNo, payOverTime, bean.getOrderHsbAmount(), bean.getOrderHsbAmount(),
				info.getExchangeRate(), info.getCurrencyNameCn(), false);
	}

	/**
	 * 新增平台代购订单
	 *
	 * @param bean
	 *            平台代购参数实体
	 * @throws HsException
	 * @Description:
	 */
	@Override
	@Transactional
	public ToolOrderResult addPlatProxyOrder(ProxyOrder bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:addPlatProxyOrder", "params==>" + bean, "新增平台代购订单");
		// 平台代购订单编号
		String proxyOrderNo = "";
		// 收货信息编号
		String deliverId = "";
		// 资源段id集合
		List<String> segmentIds = null;
		// 地区平台信息
		LocalInfo info = null;
		// 配置资源段
		ConfigSegment confSegment = null;
		//清单
		ProxyOrderDetail detail = null;
		Map<String, Object> params = new HashMap<String, Object>();

		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "新增平台代购参数为NULL," + bean);
		// 根据注解验证参数
		String valid = ValidateParamUtil.validateParam(bean);
		HsAssert.isTrue(StringUtils.isBlank(valid), RespCode.PARAM_ERROR, valid + "," + bean);
		// 非定制卡样服务
		if (!OrderType.CARD_STYLE_FEE.getCode().equals(bean.getOrderType()))
		{
			HsAssert.isTrue(StringUtils.isNotBlank(bean.getDetail()) && bean.getDetail().size() > 0,
					RespCode.PARAM_ERROR, "代购清单不能为空," + bean);
			HsAssert.notNull(bean.getDeliverInfo(), RespCode.PARAM_ERROR, "收货信息不能为空," + bean);
		}
		// 验证平台代购的订单类型
		HsAssert.isTrue(OrderType.mayPlatProxyOrderType(bean.getOrderType()), BSRespCode.BS_PLAT_PROXY_ORDER_TYPE_ERROR,
				"平台代购订单类型错误," + bean);
		// 查询地区平台信息
		info = commonService.getAreaPlatInfo();
		HsAssert.notNull(info, BSRespCode.BS_INVOKE_LCS_FAIL, "调用LCS失败," + bean);
		try
		{
			// 生成id
			proxyOrderNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
			deliverId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());

			// 平台代购订单
			bean.setProxyOrderNo(proxyOrderNo);
			bean.setStatus(ApprStatus.WAIT_APPR.getCode());
			bean.setCurrencyCode(info.getHsbCode());

			// 非定制卡样服务
			if (!OrderType.CARD_STYLE_FEE.getCode().equals(bean.getOrderType()))
			{

				// 收货信息
				DeliverInfo deliver = bean.getDeliverInfo();
				deliver.setDeliverId(deliverId);
				deliver.setCreatedDate(DateUtil.getCurrentDateTime());

				bean.setDeliverId(deliverId);

				// 申购系统资源费
				if (OrderType.APPLY_PERSON_RESOURCE.getCode().equals(bean.getOrderType())&& false )
				{
					detail = bean.getDetail().get(0);
					segmentIds = detail.getSegmentIds();

					// 验证资源段是否在代购中
					int count = resSegmentMapper.validResSegmentIsProxy(segmentIds);
					HsAssert.isTrue(count == 0, BSRespCode.BS_SEGMENTID_HAS_PROXY, "选择资源段含有代购中的,请先处理完代购中订单");

					// 验证是否选择资源段
					HsAssert.isTrue(null != segmentIds && segmentIds.size() > 0, RespCode.PARAM_ERROR,
							"选择的资源段不能为空," + bean);
					count = resSegmentMapper.valiResSegmentIsOrder(segmentIds);
					HsAssert.isTrue(count == segmentIds.size(), BSRespCode.BS_SEGMENTID_HAS_ORDER_BOUGHT,
							"选择资源段有已下单或已申购的");

					// 查询配置资源段
					confSegment = resSegmentMapper.selectConfigSegmentByNo(segmentIds);

					// 代购订单金额
					bean.setOrderAmount(confSegment.getAmount());

					detail.setPrice(bsConfig.getReformCardFee());// 清单资源段价格
					detail.setQuantity(confSegment.getCount());// 数量
					detail.setTotalAmount(confSegment.getAmount());// 金额

				}
				// 平台代购清单
				params.put("proxyOrderNo", proxyOrderNo);
				params.put("details", bean.getDetail());

				// 插入配置单、收货信息数据
				proxyOrderMapper.insertProxyOrderList(params);
				deliverInfoMapper.insertDeliverInfo(deliver);
				// 申购系统资源费修改资源段代购订单号
				if (OrderType.APPLY_PERSON_RESOURCE.getCode().equals(bean.getOrderType())&&false)
				{
					resSegmentMapper.updateResSegmentByIds(segmentIds, proxyOrderNo, null, null);
				}
			} else
			{
				bean.setOrderAmount(bsConfig.getCardStyleFee());
			}
			// 插入订单信息数据
			proxyOrderMapper.insertProxyOrder(bean);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:addPlatProxyOrder",
					BSRespCode.BS_ADD_PROXY_ORDERE_FAIL.getCode() + ":新增平台代购订单失败," + bean, ex);
			throw new HsException(BSRespCode.BS_ADD_PROXY_ORDERE_FAIL, "新增平台代购订单失败," + bean);
		}
		// 打开工单
		if (bsConfig.getWorkTaskIsOpen().booleanValue())
		{
			// 插入一条平台代购审批任务,平台代购时工单办理业务主体设置为代购企业
			taskService.saveTask(new Task(proxyOrderNo, TaskType.TASK_TYPE332.getCode(),
					commonService.getAreaPlatCustId(), bean.getEntResNo(), bean.getEntCustName()));
		}
		return new ToolOrderResult(proxyOrderNo, null, bean.getOrderAmount(), bean.getOrderAmount(),
				info.getExchangeRate(), info.getCurrencyNameCn());
	}

	/**
	 * 分页查询平台代购
	 *
	 * @param param
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 * @Description:
	 */
	@Override
	public PageData<ProxyOrder> queryPlatProxyOrderPage(BaseParam param, Page page)
	{
		if (null == page)
		{
			return new PageData<ProxyOrder>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 如果关闭工单，查询时不关联经办人进行查询，需要设置经办人为空
		if (!bsConfig.getWorkTaskIsOpen().booleanValue())
		{
			param.setExeCustId(null);
		}
		// 查询平台代购审批列表
		List<ProxyOrder> proxys = proxyOrderMapper.selectProxyOrderListPage(param);
		if (StringUtils.isNotBlank(proxys))
		{
			return new PageData<ProxyOrder>(page.getCount(), proxys);
		}
		return new PageData<ProxyOrder>(0, null);
	}

	/**
	 * 根据id查询平台代购订单
	 *
	 * @param proxyOrderNo
	 *            平台代购订单号
	 * @return
	 * @Description:
	 */
	@Override
	public ProxyOrder queryPlatProxyOrderById(String proxyOrderNo)
	{
		if (StringUtils.isBlank(proxyOrderNo))
		{
			return null;
		}
		// 查询平台代购
		ProxyOrder bean = proxyOrderMapper.selectProxyOrderById(proxyOrderNo);
		if (null != bean)
		{
			// 获取平台代购清单
			bean.setDetail(proxyOrderMapper.selectProxyOrderDetailById(proxyOrderNo));
			// 获取收货信息
			bean.setDeliverInfo(deliverInfoMapper.findDeliverByDeliverId(bean.getDeliverId()));
		}
		return bean;
	}

	/**
	 * 平台代购审批
	 *
	 * @param bean
	 *            平台代购参数实体
	 * @throws HsException
	 * @Description:
	 */
	@Override
	@Transactional
	public void platProxyOrderAppr(ProxyOrder bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:platProxyOrderAppr", "params==>" + bean, "平台代购审批");
		int count = 0;
		// 仓库
		Warehouse wh = null;
		// 配置单
		List<ToolConfig> confs = null;
		// 地区平台信息
		LocalInfo info = null;
		// 企业基本信息
		BsEntBaseInfo baseInfo = null;
		// 订单号
		String orderNo = "";
		// 任务id
		String taskId = "";
		// 默认卡样
		CardStyle style = null;
		// 资源段id集合
		List<String> segmentIds = null;
		// 卡样id
		String cardStyleId = "";
		// 配置单号
		String confNo = null;
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "平台代购审批参数为NULL," + bean);
		HsAssert.notNull(bean.getStatus(), RespCode.PARAM_ERROR, "平台代购审批状态为NULL," + bean);

		// 打开工单
		if (bsConfig.getWorkTaskIsOpen().booleanValue())
		{
			// 获取平台代购任务id
			taskId = taskService.getSrcTask(bean.getProxyOrderNo(), bean.getExeCustId());
			HsAssert.hasText(taskId, RespCode.PARAM_ERROR, "客户未查询待办的任务," + bean);
		}

		// 根据平台代购订单号查询平台代购单是否存在
		ProxyOrder param = queryPlatProxyOrderById(bean.getProxyOrderNo());
		HsAssert.notNull(param, RespCode.PARAM_ERROR, "查询平台代购订单为NULL," + bean);
		// 验证平台代购订单状态
		HsAssert.isTrue(bean.getStatus().intValue() != ApprStatus.WAIT_APPR.getCode(), RespCode.PARAM_ERROR,
				"平台代购订单审批状态错误," + bean);

		// 审批通过(不是定制卡样服务)
		if (bean.getStatus() == ApprStatus.PASS.getCode()
				&& !OrderType.CARD_STYLE_FEE.getCode().equals(param.getOrderType()))
		{
			// 验证购买的数量是否超标
			String isMayBuy = vaildCreateToolOrderBuyNum(param.getEntCustId(), null, param.getDetail());
			HsAssert.isTrue(StringUtils.isBlank(isMayBuy), BSRespCode.BS_TOOL_ORDER_BUY_NUM_ERROR,
					"平台代购下单购买数量超标,工具类别代码:" + isMayBuy);
		}
		// 查询用户中心企业信息
		baseInfo = commonService.getEntBaseInfoToUc(param.getEntResNo(), null);
		if (null == baseInfo)
		{
			SystemLog.info(this.getClass().getName(), "function:apprAfterService",
					BSRespCode.BS_NOT_QUERY_ENT_INFO.getCode() + ":未查询到企业信息," + bean);
		}
		// 获取地区平台信息
		info = commonService.getAreaPlatInfo();
		HsAssert.notNull(info, BSRespCode.BS_INVOKE_LCS_FAIL, "调用LCS失败," + bean);

		try
		{
			count = proxyOrderMapper.proxyOrderAppr(bean);
			// 审批通过
			if (count > 0 && bean.getStatus() == ApprStatus.PASS.getCode())
			{
				// 申购系统资源费修改资源段配置单号
				if (OrderType.APPLY_PERSON_RESOURCE.getCode().equals(param.getOrderType()) && false)
				{
					segmentIds = resSegmentMapper.selectSegmentIdByIf(bean.getProxyOrderNo(), null, null);
					// 验证是否选择资源段
					HsAssert.isTrue(null != segmentIds && segmentIds.size() > 0, RespCode.PARAM_ERROR, "选择的资源段不能为空," + bean);
					count = resSegmentMapper.valiResSegmentIsOrder(segmentIds);
					HsAssert.isTrue(count == segmentIds.size(), BSRespCode.BS_SEGMENTID_HAS_ORDER_BOUGHT, "选择资源段有已下单或已申购的");
				}

				// 订单号
				orderNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
				// 根据企业省编号获取仓库，不存在就获取地区平台默认仓库
				wh = warehouseMapper.selectWarehouseByNo(null != baseInfo ? baseInfo.getProvinceCode() : null);
				if (null == wh)
				{
					// 获取地区平台默认仓库
					wh = warehouseMapper.selectWarehouseByDefault();
				}

				// 生成工具配置单
				confs = new ArrayList<ToolConfig>();
				// 不是定制卡样服务
				if (!OrderType.CARD_STYLE_FEE.getCode().equals(param.getOrderType()))
				{
					for (ProxyOrderDetail detail : param.getDetail())
					{
						// 工具类别是互生卡时
						if (detail.getCategoryCode().equals(CategoryCode.P_CARD.name()))
						{
							cardStyleId = detail.getCardStyleId();
							// 没有选择企业的个性卡样,获取地区平台的默认卡样
							if (StringUtils.isBlank(detail.getCardStyleId()))
							{
								// 获取默认卡样
								style = cardStyleMapper.selectCardStyleByDefault();
								cardStyleId = style != null ? style.getCardStyleId() : "";
							}
						}
						confNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
						// 配置单数据
						ToolConfig conf = new ToolConfig(confNo, param.getEntCustId(), param.getEntResNo(), null,
								detail.getCategoryCode(), detail.getProductId(), detail.getProductName(),
								detail.getUnit(), detail.getPrice(), detail.getQuantity(), detail.getTotalAmount(), cardStyleId,
								orderNo, ConfStatus.WAIT_PAY.getCode(), null, "平台代购订单配置", param.getApprOperator(), null,
								ConfType.ADD_CONFIG.getCode(), wh.getWhId());
						confs.add(conf);
					}

					// 生成工具订单
					Order order = ObjectFactory.createOrder(orderNo, param.getEntCustId(), param.getEntCustName(),
							param.getProxyOrderNo(), param.getOrderType(), param.getOrderAmount(), info.getHsbCode(),
							info.getExchangeRate().toString(), param.getDeliverId(),
							DateUtil.addDaysStr(null, bsConfig.getToolOrderInvalidDays()), bean.getApprOperator(),
							param.getReqRemark());
					order.setPayChannel(PayChannel.TRANSFER_REMITTANCE.getCode());
					// 插入工具订单、配置单数据
					orderMapper.insertOrder(order);

					toolConfigMapper.batchInsertToolConfig(confs);
					// 申购系统资源费
					if (OrderType.APPLY_PERSON_RESOURCE.getCode().equals(param.getOrderType()) && false)
					{
						resSegmentMapper.updateResSegmentByIds(segmentIds, null, confNo ,BuyStatus.ORDER.getCode());
					}
					// 定制卡样服务
				} else
				{
					// 新增定制服务订单
					Order order = new Order();
					// 订单设置参数
					order.setCustId(param.getEntCustId());// 客户号
					order.setBizNo(param.getProxyOrderNo());// 业务编号
					order.setCustName(param.getEntCustName());// 客户名称
					order.setHsResNo(param.getEntResNo());// 互生号
					order.setCustType(param.getCustType());// 客户类型
					order.setOrderOperator(param.getReqOperator());// 操作员
					order.setOrderRemark(param.getReqRemark());// 备注
					order.setPayChannel(PayChannel.TRANSFER_REMITTANCE.getCode());// 支付方式
					order.setOrderChannel(OrderChannel.WEB.getCode());// 订单渠道
					order.setIsProxy(true);

					// 生成卡样数据
					style = new CardStyle();
					style.setCardStyleName("互生平台个性卡样");// 卡样名称
					style.setMaterialMicroPic("");// 缩略图
					style.setMaterialSourceFile("");// 素材
					style.setEntResNo(param.getEntResNo());// 互生号
					style.setEntCustId(param.getEntCustId());// 客户号
					style.setReqOperator(param.getReqOperator());// 操作员
					style.setReqRemark("平台代购个性卡样");// 备注

					this.addCardStyleFeeOrder(order, style);
				}
			}
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:platProxyOrderAppr",
					BSRespCode.BS_PROXY_ORDERE_APPR_FAIL.getCode() + "平台代购审批失败," + bean, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_PROXY_ORDERE_APPR_FAIL, "平台代购审批失败," + bean);
		// 打开工单
		if (bsConfig.getWorkTaskIsOpen().booleanValue())
		{
			// 修改平台代购审批任务完成
			taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());
		}
	}

	/**
	 * 分页查询平台代购订单记录
	 *
	 * @param param
	 *            查询参数
	 * @param page
	 *            分页查询
	 * @return
	 * @Description:
	 */
	@Override
	public PageData<ProxyOrder> queryPlatProxyOrderRecordPage(BaseParam param, Page page)
	{
		if (null == page)
		{
			return new PageData<ProxyOrder>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 设置日期查询条件
		param.setQueryDate();
		// 查询平台代购审批列表
		List<ProxyOrder> proxys = proxyOrderMapper.selectPlatProxyOrderRecordListPage(param);
		if (StringUtils.isNotBlank(proxys))
		{
			return new PageData<ProxyOrder>(page.getCount(), proxys);
		}
		return new PageData<ProxyOrder>(0, null);
	}

	/**
	 * 个人补卡下单
	 *
	 * @param bean
	 *            个人补卡下单参数实体
	 * @return
	 * @throws HsException
	 * @Description:
	 */
	@Override
	@Transactional
	public ToolOrderResult personMendCardOrder(OrderBean bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:personMendCardOrder", "params==>" + bean, "个人补卡下单");
		// 订单编号
		String orderNo = "";
		// 收货信息编号
		String deliverId = "";
		// 支付超时时间
		String payOverTime = "";
		// 订单实体
		Order order = null;
		// 收货信息实体
		DeliverInfo deliverInfo = null;
		// 互生号列表
		List<String> perResNos = null;
		// 默认仓库
		Warehouse wh = null;
		// 互生卡工具产品
		ToolProduct toolProduct = null;
		// 地区平台信息
		LocalInfo info = null;
		// 默认卡样
		CardStyle style = null;
		// 卡样id
		String cardStyleId = "";
		String now = DateUtil.getCurrentDateTime();

		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "个人补卡下单参数为NULL," + bean);
		// 订单号，收货信息id
		orderNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
		deliverId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
		// 订单、收货信息、互生号、支付超时时间
		order = bean.getOrder();
		deliverInfo = bean.getDeliverInfo();
		perResNos = bean.getPerResNos();
		payOverTime = DateUtil.addDaysStr(null, bsConfig.getToolOrderInvalidDays());

		// 验证订单、售后信息、互生号
		HsAssert.notNull(order, RespCode.PARAM_ERROR, "个人补卡下单订单参数为NULL," + bean);
		HsAssert.notNull(deliverInfo, RespCode.PARAM_ERROR, "个人补卡下单收货信息参数为NULL," + bean);
		HsAssert.isTrue(!CollectionUtils.isEmpty(perResNos), RespCode.PARAM_ERROR, "个人补卡下单互生号参数为NULL," + bean);

		// 地区平台信息
		info = commonService.getAreaPlatInfo();
		HsAssert.notNull(info, BSRespCode.BS_INVOKE_LCS_FAIL, "调用LCS失败," + bean);

		// 查询客户补卡未完成的配置单
		int noFinish = toolConfigMapper.selectNoFinishConfig(order.getCustId());
		if (noFinish > 0)
		{
			// 查询客户补卡有未付款的订单
			Order noPayOrder = orderMapper.selectCustNoPayFinishOrder(order.getCustId(),
					OrderType.REMAKE_MY_CARD.getCode());
			if (null != noPayOrder)
			{
				// 直接返回待付款(未过期)的订单进行支付
				return new ToolOrderResult(noPayOrder.getOrderNo(), noPayOrder.getPayOvertime(),
						noPayOrder.getOrderHsbAmount(), noPayOrder.getOrderHsbAmount(), info.getExchangeRate(),
						info.getCurrencyNameCn());
			} else
			{
				// 抛异常,提示客户有未完成的订单,暂时不能下单
				HsAssert.isTrue(false, BSRespCode.BS_CUST_HAS_NOFINISH_CONFIG, "你已经申请了补办互生卡业务，无需再次申请");
			}
		}

		// 获取工具产品互生卡
		toolProduct = toolProductMapper.selectToolProductNotByStatus(CategoryCode.P_CARD.name()).get(0);
		HsAssert.notNull(toolProduct, BSRespCode.BS_QUERY_TOOL_CARD_FAIL, "查询工具互生卡失败," + bean);
		try
		{
			// 获取地区平台默认仓库
			wh = warehouseMapper.selectWarehouseByDefault();

			// 获取默认卡样
			style = cardStyleMapper.selectCardStyleByDefault();
			cardStyleId = style != null ? style.getCardStyleId() : "";

			// 计算货币金额
			String castAmount = BigDecimalUtils
					.ceilingMul(bsConfig.getReformCardFee(), info.getExchangeRate().toString()).toString();
			// 设置订单参数
			order.setOrderNo(orderNo);// 订单号
			order.setCustType(CustType.PERSON.getCode());// 客户类型
			order.setOrderType(OrderType.REMAKE_MY_CARD.getCode());// 订单类型
			order.setIsProxy(false);// 是否平台代购
			order.setOrderTime(now);// 订单时间
			order.setDeliverId(deliverId);// 收货信息编号
			order.setPayOvertime(payOverTime);// 支付超时时间
			order.setOrderStatus(OrderStatus.WAIT_PAY.getCode());// 订单状态
			order.setPayStatus(PayStatus.WAIT_PAY.getCode());// 支付状态
			order.setIsNeedInvoice(OrderGeneral.YES.getCode());// 是否需要发票
			order.setOrderOriginalAmount(toolProduct.getPrice());// 原始金额
			order.setOrderDerateAmount("0");
			order.setOrderCashAmount(castAmount);// 货币金额
			order.setOrderHsbAmount(bsConfig.getReformCardFee());// 互生币金额
			order.setExchangeRate(info.getExchangeRate().toString());// 货币转换比率
			// order.setOrderRemark("个人补卡下单");
			order.setCurrencyCode(info.getCurrencyCode());// 币种

			// 收货信息
			deliverInfo.setDeliverId(deliverId);
			deliverInfo.setCreatedDate(now);

			// 配置单号
			String confNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
			// 配置单
			ToolConfig conf = new ToolConfig(confNo, order.getCustId(), order.getHsResNo(), null,
					toolProduct.getCategoryCode(), toolProduct.getProductId(), toolProduct.getProductName(),
					toolProduct.getUnit(), bsConfig.getReformCardFee(), 1, bsConfig.getReformCardFee(), cardStyleId,
					orderNo, ConfStatus.WAIT_PAY.getCode(), null, "个人补卡配置", order.getOrderOperator(), null,
					ConfType.AFTER_CONFIG.getCode(), wh != null ? wh.getWhId() : "");

			// 插入订单、配置单、收货信息、重做互生卡数据
			orderMapper.insertOrder(order);
			toolConfigMapper.insertToolConfig(conf);
			deliverInfoMapper.insertDeliverInfo(deliverInfo);
			cardInfoMapper.insertMakingCardInfo(
					new MakingCardInfo(confNo, perResNos.get(0), RemarkType.PERSON_REMARK.getCode()));
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:personMendCardOrder",
					BSRespCode.BS_PERSON_MEND_CARD_ORDER_SUBMIT_FAIL.getCode() + ":个人补卡订单提交失败," + bean, ex);
			throw new HsException(BSRespCode.BS_PERSON_MEND_CARD_ORDER_SUBMIT_FAIL, "个人补卡订单提交失败," + bean);
		}
		return new ToolOrderResult(orderNo, payOverTime, order.getOrderHsbAmount(), order.getOrderHsbAmount(),
				info.getExchangeRate(), info.getCurrencyNameCn());
	}

	/**
	 * 企业重做卡下单
	 *
	 * @param bean
	 *            企业重做卡参数实体
	 * @return
	 * @Description:
	 */
	@Override
	@Transactional
	public ToolOrderResult entMakingCardOrder(OrderBean bean)
	{
		BizLog.biz(this.getClass().getName(), "function:entMakingCardOrder", "params==>" + bean, "企业重做卡下单");
		// 订单编号
		String orderNo = "";
		// 收货信息编号
		String deliverId = "";
		// 支付超时时间
		String payOverTime = "";
		// 订单实体
		Order order = null;
		// 收货信息实体
		DeliverInfo deliverInfo = null;
		// 订单互生币金额
		String orderHsbAmount = "0";
		// 互生号列表
		List<String> perResNos = null;
		// 重做互生号列表
		List<MakingCardInfo> makings = null;
		// 默认仓库
		Warehouse wh = null;
		// 互生卡工具产品
		ToolProduct toolProduct = null;
		// 地区平台信息
		LocalInfo info = null;
		// 默认卡样
		CardStyle style = null;
		// 卡样id
		String cardStyleId = "";
		String now = DateUtil.getCurrentDateTime();

		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "企业重做卡下单参数为NULL," + bean);
		// 订单号、收货信息编号
		orderNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
		deliverId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
		// 订单、收货信息、消费者互生号列表、支付超时时间
		order = bean.getOrder();
		deliverInfo = bean.getDeliverInfo();
		perResNos = bean.getPerResNos();
		payOverTime = DateUtil.addDaysStr(null, bsConfig.getToolOrderInvalidDays());
		// 验证订单、收货信息、互生号列表数据
		HsAssert.notNull(order, RespCode.PARAM_ERROR, "企业重做卡下单订单参数为NULL," + bean);
		HsAssert.notNull(deliverInfo, RespCode.PARAM_ERROR, "企业重做卡下单收货信息参数为NULL," + bean);
		HsAssert.isTrue(!CollectionUtils.isEmpty(perResNos), RespCode.PARAM_ERROR, "企业重做卡下单互生号列表参数为NULL," + bean);

		makings = new ArrayList<MakingCardInfo>();
		// 地区平台信息
		info = commonService.getAreaPlatInfo();
		HsAssert.notNull(info, BSRespCode.BS_INVOKE_LCS_FAIL, "调用LCS失败," + bean);
		// 互生卡工具产品
		toolProduct = toolProductMapper.selectToolProductNotByStatus(CategoryCode.P_CARD.name()).get(0);
		HsAssert.notNull(toolProduct, BSRespCode.BS_QUERY_TOOL_CARD_FAIL, "查询工具互生卡失败," + bean);
		try
		{
			// 地区平台默认仓库
			wh = warehouseMapper.selectWarehouseByDefault();

			// 获取默认卡样
			style = cardStyleMapper.selectCardStyleByDefault();
			cardStyleId = style != null ? style.getCardStyleId() : "";

			// 互生币金额
			orderHsbAmount = BigDecimalUtils.ceilingMul(bsConfig.getReformCardFee(), String.valueOf(perResNos.size()))
					.toString();
			// 设置订单参数
			order.setOrderNo(orderNo);// 订单号
			order.setCustType(CustType.TRUSTEESHIP_ENT.getCode());// 客户类型
			order.setOrderType(OrderType.REMAKE_BATCH_CARD.getCode());// 订单类型
			order.setIsProxy(true);// 是否平台代购
			order.setOrderTime(now);// 订单时间
			order.setDeliverId(deliverId);// 收货信息编号
			order.setPayOvertime(payOverTime);// 支付超时时间
			order.setOrderStatus(OrderStatus.WAIT_PAY.getCode());// 订单状态
			order.setPayStatus(PayStatus.WAIT_PAY.getCode());// 支付状态
			order.setIsNeedInvoice(OrderGeneral.YES.getCode());// 是否需要发票
			order.setOrderOriginalAmount(orderHsbAmount);// 原始金额
			order.setOrderDerateAmount("0");// 减免金额
			order.setOrderCashAmount(BigDecimalUtils.ceilingMul(orderHsbAmount, info.getExchangeRate()).toString());// 货币金额
			order.setOrderHsbAmount(orderHsbAmount);// 互生币金额
			order.setExchangeRate(info.getExchangeRate().toString());// 货币转换比率
			order.setOrderRemark("企业重做卡下单");
			order.setCurrencyCode(info.getHsbCode());// 币种

			// 收货信息
			deliverInfo.setDeliverId(deliverId);
			deliverInfo.setCreatedDate(now);

			// 配置单号
			String confNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
			// 配置单
			ToolConfig conf = new ToolConfig(confNo, order.getCustId(), order.getHsResNo(), null,
					toolProduct.getCategoryCode(), toolProduct.getProductId(), toolProduct.getProductName(),
					toolProduct.getUnit(), bsConfig.getReformCardFee(), perResNos.size(), orderHsbAmount, cardStyleId,
					orderNo, ConfStatus.WAIT_PAY.getCode(), null, "企业重做卡配置", order.getOrderOperator(), null,
					ConfType.AFTER_CONFIG.getCode(), wh != null ? wh.getWhId() : "");

			// 生成重做互生卡数据
			for (String perResNo : perResNos)
			{
				makings.add(new MakingCardInfo(confNo, perResNo, RemarkType.ENT_REMARK.getCode()));
			}
			// 插入订单、配置单、收货信息、重做互生卡数据
			orderMapper.insertOrder(order);
			toolConfigMapper.insertToolConfig(conf);
			deliverInfoMapper.insertDeliverInfo(deliverInfo);
			cardInfoMapper.batchInsertMakingCardInfo(makings);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:entMakingCardOrder",
					BSRespCode.BS_ENT_MAKING_CARD_ORDER_SUBMIT_FAIL.getCode() + ":企业重做卡订单提交失败," + bean, ex);
			throw new HsException(BSRespCode.BS_ENT_MAKING_CARD_ORDER_SUBMIT_FAIL, "企业重做卡订单提交失败," + bean);
		}
		return new ToolOrderResult(orderNo, payOverTime, order.getOrderHsbAmount(), order.getOrderHsbAmount(),
				info.getExchangeRate(), info.getCurrencyNameCn());
	}

	/**
	 * 工具订单支付(申购、补卡、重做卡、订制卡样,售后工具)
	 *
	 * @param bean
	 *            工具订单支付参数实体
	 * @return
	 * @throws HsException
	 * @Description:
	 */
	@Override
	@Transactional
	public String buyToolOrderToPay(ToolOrderPay bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:buyToolOrderToPay", "params==>" + bean, "工具订单支付(补卡、重做卡");
		// 订单实体
		Order order = null;

		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "工具订单支付,支付参数为null," + bean);
		// 根据注解验证参数
		String valid = ValidateParamUtil.validateParam(bean);
		HsAssert.isTrue(StringUtils.isBlank(valid), RespCode.PARAM_ERROR, valid + "," + bean);

		// 获取订单数据(订单类型:申购工具,系统资源申购,个人补卡,企业重做卡,卡样定制服务,售后服务)
		order = orderMapper.findToolOrderByNo(bean.getOrderNo(), new String[]
		{ OrderType.BUY_TOOL.getCode(), OrderType.REMAKE_MY_CARD.getCode(), OrderType.REMAKE_BATCH_CARD.getCode(),
				OrderType.CARD_STYLE_FEE.getCode(), OrderType.AFTER_SERVICE.getCode(),
				OrderType.APPLY_PERSON_RESOURCE.getCode() });
		HsAssert.notNull(order, RespCode.PARAM_ERROR, "订单号错误，没有查询到订单数据," + bean);

		// 验证订单的状态(是否支付成功、支付处理中、已失效)
		HsAssert.isTrue(order.getPayStatus().intValue() != PayStatus.PROCESSING.getCode(),
				BSRespCode.BS_TOOL_ORDER_PAY_PROCESSING, "工具订单支付处理中,稍等支付结果," + bean);

		HsAssert.isTrue(OrderStatus.checkOrderIsPay(order.getOrderStatus().intValue()), BSRespCode.BS_TOOL_ORDER_PAID,
				"工具订单已支付," + bean);

		HsAssert.isTrue(OrderStatus.checkOrderIsInvalid(order.getOrderStatus().intValue()),
				BSRespCode.BS_TOOL_ORDER_CLOSED_OR_EXPIRY, "工具订单已关闭或已失效," + bean);
		try
		{
			// 更新支付方式
			orderMapper.updateOrderPayChannel(bean.getOrderNo(), bean.getPayChannel());
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:afterToolOrderToPay",
					BSRespCode.BS_MODIFY_TOOL_ORDER_PAY_CHANNEL_FAIL + ":更新工具订单支付方式失败," + bean, ex);
			throw new HsException(BSRespCode.BS_MODIFY_TOOL_ORDER_PAY_CHANNEL_FAIL, "更新工具订单支付方式失败," + bean);
		}
		// 获取支付地址
		if (PayChannel.isGainPayAddress(bean.getPayChannel().intValue()))
		{
			// 调用订单模块获取支付地址
			return orderService.getPayUrl(PayUrl.bulid(order, bean.getPayChannel(), bean.getJumpUrl(),
					bean.getBindingNo(), bean.getSmsCode(), OrderType.getGoodNameByOrderType(order.getOrderType())));
		}
		// 互生币支付
		if (bean.getPayChannel().intValue() == PayChannel.HS_COIN_PAY.getCode())
		{
			insideInvokeCall.checkOrderAmountIsOverLimit(order.getOrderHsbAmount(), order.getCustId());
			// 调用互生币记账
			insideInvokeCall.toolOrderHsbPayAccount(order);
		}
		return null;
	}

	/**
	 * 企业分页查询工具订单
	 *
	 * @param param
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 * @Description:
	 */
	@Override
	public PageData<OrderEnt> queryToolOrderEntByPage(BaseParam param, Page page)
	{
		if (null == page || StringUtils.isBlank(param.getHsResNo()) || StringUtils.isBlank(param.getHsCustId()))
		{
			return new PageData<OrderEnt>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 设置日期查询条件
		param.setQueryDate();
		// 设置订单类型
		param.setOrderType(StringUtil.assembleQueryInIf(new String[]
		{ OrderType.APPLY_BUY_TOOL.getCode(), OrderType.BUY_TOOL.getCode(), OrderType.REMAKE_MY_CARD.getCode(),
				OrderType.REMAKE_BATCH_CARD.getCode(), OrderType.CARD_STYLE_FEE.getCode(),
				OrderType.AFTER_SERVICE.getCode(), OrderType.APPLY_PERSON_RESOURCE.getCode() }));
		// 查询工具订单列表
		List<OrderEnt> orders = commonMapper.selectOrderEntByListPage(param);
		if (StringUtils.isNotBlank(orders))
		{
			return new PageData<OrderEnt>(page.getCount(), orders);
		}
		return new PageData<OrderEnt>(0, null);
	}

	/**
	 * 平台分页查询工具订单
	 *
	 * @param param
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 * @Description:
	 */
	@Override
	public PageData<ToolOrderPage> queryToolOrderPlatPage(BaseParam param, Page page)
	{
		if (null == page)
		{
			return new PageData<ToolOrderPage>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 设置日期查询条件
		param.setQueryDate();
		// 设置订单类别
		param.setOrderType(StringUtil.assembleQueryInIf(new String[]
		{ OrderType.APPLY_BUY_TOOL.getCode(), OrderType.BUY_TOOL.getCode(), OrderType.REMAKE_MY_CARD.getCode(),
				OrderType.REMAKE_BATCH_CARD.getCode(), OrderType.CARD_STYLE_FEE.getCode(),
				OrderType.APPLY_PERSON_RESOURCE.getCode() }));
		// 查询工具订单列表
		List<ToolOrderPage> orders = commonMapper.selectToolOrderPlatListPage(param);
		if (StringUtils.isNotBlank(orders))
		{
			return new PageData<ToolOrderPage>(page.getCount(), orders);
		}
		return new PageData<ToolOrderPage>(0, null);
	}

	/**
	 * 查询订单详情
	 *
	 * @param orderNo
	 *            订单号
	 * @return
	 * @Description:
	 */
	@Override
	public OrderBean queryOrderDetailByNo(String orderNo)
	{
		if (StringUtils.isBlank(orderNo))
		{
			return null;
		}
		OrderBean bean = new OrderBean();
		// 查询订单
		Order order = orderMapper.findOrderByNo(orderNo);
		if (null != order)
		{
			bean.setOrder(order);
			// 包含配置单的订单类型
			if (OrderType.isHasConfigToolOrder(order.getOrderType()))
			{
				// 配置单
				bean.setConfs(toolConfigMapper.selectToolConfigByOrderNo(orderNo));
				// 收货信息
				bean.setDeliverInfo(deliverInfoMapper.findDeliverByDeliverId(order.getDeliverId()));
				// 售后订单类型
				if (OrderType.AFTER_SERVICE.getCode().equals(order.getOrderType()))
				{
					bean.setAfterDetail(afterServiceSelectMapper.selectAfterServiceDetailByOrderNo(order.getOrderNo()));
				}
				// 申报订单
				if (OrderType.APPLY_BUY_TOOL.getCode().equals(order.getOrderType()))
				{
					String sEntResNo = order.getHsResNo().substring(0, 5) + "000000";
					// 企业名称
					String entName = null;
					// 企业信息
					BsEntAllInfo entInfo = null;
					// 收货人信息
					DeliverInfo deliver = null;
					// 用户中心查询收货信息
					entInfo = commonService.getEntAllInfoToUc(sEntResNo, null);

					// 验证调用企业信息
					HsAssert.notNull(entInfo, BSRespCode.BS_NOT_QUERY_ENT_INFO, "申报订单类型,未查询到服务公司信息,");
					HsAssert.notNull(entInfo.getMainInfo(), BSRespCode.BS_NOT_QUERY_ENT_INFO, "申报订单类型,未查询到服务公司信息,");
					HsAssert.notNull(entInfo.getBaseInfo(), BSRespCode.BS_NOT_QUERY_ENT_INFO, "申报订单类型,未查询到服务公司信息,");

					// 企业名称
					entName = StringUtils.isNotBlank(entInfo.getBaseInfo().getEntName())
							? entInfo.getBaseInfo().getEntName() : entInfo.getBaseInfo().getEntNameEn();
					// 联系人地址
					deliver = new DeliverInfo();
					deliver.setLinkman(entInfo.getMainInfo().getContactPerson());// 联系人
					deliver.setFullAddr(entInfo.getBaseInfo().getContactAddr() + "(" + entName + ")");// 联系人地址
					bean.setDeliverInfo(deliver);
				}
			}
		}
		return bean;
	}

	/**
	 * 根据订单号和工具类别查询配置单
	 *
	 * @param orderNo
	 *            订单号
	 * @param categoryCode
	 *            工具类别
	 * @return
	 * @Description:
	 */
	@Override
	public ToolConfig queryToolConfigByNoAndCode(String orderNo, String categoryCode)
	{
		if (StringUtils.isBlank(orderNo) || StringUtils.isBlank(categoryCode))
		{
			return null;
		}
		return toolConfigMapper.selecctToolConfigByNoAndCode(orderNo, categoryCode);
	}

	/**
	 * 分页查询互生个性卡样(平台)
	 *
	 * @param param
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 * @Description:
	 */
	@Override
	public PageData<SpecialCardStyle> querySpecialCardStylePage(BaseParam param, Page page)
	{
		if (null == page)
		{
			return new PageData<SpecialCardStyle>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 设置订单类型
		param.setOrderType(OrderType.CARD_STYLE_FEE.getCode());
		// 查询互生个性卡样列表
		List<SpecialCardStyle> styles = cardStyleMapper.selectSpecialCardStyleListPage(param);
		if (StringUtils.isNotBlank(styles))
		{
			return new PageData<SpecialCardStyle>(page.getCount(), styles);
		}
		return new PageData<SpecialCardStyle>(0, null);
	}

	/**
	 * 根据订单号查询互生卡样
	 *
	 * @param orderNo
	 *            订单号
	 * @return
	 * @Description:
	 */
	@Override
	public CardStyle queryCardStyleByOrderNo(String orderNo)
	{
		if (StringUtils.isBlank(orderNo))
		{
			return null;
		}
		return cardStyleMapper.selectCardStyleByOrderNo(orderNo);
	}

	/**
	 * 修改卡样锁定状态
	 *
	 * @param orderNo
	 *            订单号
	 * @param isLock
	 *            是否锁定
	 * @throws HsException
	 * @Description:
	 */
	@Override
	@Transactional
	public void modifyCardStyleLockStatus(String orderNo, Boolean isLock) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:modifyCaryStyleLockStatus",
				"params==>orderNo:" + orderNo + "isLock:" + isLock, "修改卡样锁定状态");
		int count = 0;
		// 验证订单、锁定参数
		HsAssert.hasText(orderNo, RespCode.PARAM_ERROR, "修改卡样锁定状态订单号为NULL,orderNo:" + orderNo + "isLock:" + isLock);
		HsAssert.notNull(isLock, RespCode.PARAM_ERROR, "修改卡样锁定状态为NULL,orderNo:" + orderNo + "isLock:" + isLock);
		try
		{
			// 修改卡样的锁定状态
			count = cardStyleMapper.updateCardStyleByNo(new CardStyle(orderNo, null, isLock, null, null));
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:modifyCaryStyleLockStatus",
					BSRespCode.BS_MODIFY_CARD_STYLE_LOCK_FAIL.getCode() + ":更新卡样锁定状态失败,orderNo:" + orderNo + "isLock:"
							+ isLock,
					ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_MODIFY_CARD_STYLE_LOCK_FAIL,
				"更新卡样锁定状态失败,orderNo:" + orderNo + "isLock:" + isLock);
	}

	/**
	 * 上传卡样制作文件(平台)
	 *
	 * @param bean
	 *            卡样参数实体
	 * @throws HsException
	 * @Description:
	 */
	@Override
	@Transactional
	public void uploadCardStyleMarkFile(CardStyle bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:uploadCardStyleMarkFile", "params==>" + bean, "上传卡样制作文件(平台)");
		int count = 0;
		// 验证参数
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "上传卡样制作文件(平台)参数为NULL," + bean);
		HsAssert.hasText(bean.getOrderNo(), RespCode.PARAM_ERROR, "上传卡样制作文件(平台)订单号为NULL," + bean);
		HsAssert.hasText(bean.getReqOperator(), RespCode.PARAM_ERROR, "上传卡样制作文件(平台)操作员为NULL," + bean);
		try
		{
			// 修改互生卡样(上传文件)
			count = cardStyleMapper.updateCardStyleByNo(bean);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:uploadCardStyleMarkFile",
					BSRespCode.BS_UPLOAD_CARD_MARK_FILE_FAIL.getCode() + ":上传卡样制作文件(平台)失败", ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_UPLOAD_CARD_MARK_FILE_FAIL, "上传卡样制作文件(平台)失败," + bean);
	}

	/**
	 * 企业分页查询个性卡样
	 *
	 * @param entResNo
	 *            企业互生号
	 * @param page
	 *            分页参数实体
	 * @return
	 * @Description:
	 */
	public PageData<SpecialCardStyle> queryEntSpecialCardStylePage(String entResNo, Page page)
	{
		if (null == page || StringUtils.isBlank(entResNo))
		{
			return new PageData<SpecialCardStyle>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 创建查询条件对象
		BaseParam bean = new BaseParam(entResNo, null, null, null);
		// 设置订单类型
		bean.setOrderType(OrderType.CARD_STYLE_FEE.getCode());
		// 查询互生个性卡样列表
		List<SpecialCardStyle> styles = cardStyleMapper.selectSpecialCardStyleListPage(bean);
		if (StringUtils.isNotBlank(styles))
		{
			return new PageData<SpecialCardStyle>(page.getCount(), styles);
		}
		return new PageData<SpecialCardStyle>(0, null);
	}

	/**
	 * 企业上传卡样素材(订制卡样)
	 *
	 * @param bean
	 *            卡样参数实体
	 * @throws HsException
	 * @Description:
	 */
	@Override
	@Transactional
	public void addSpecialCardStyleEnt(CardStyle bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:addSpecialCardStyleEnt", "params==>" + bean, "企业上传卡样(订制卡样)");
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "企业上传卡样参数为NULL," + bean);
		// 卡样
		CardStyle style = null;
		int count = 0;
		try
		{
			// 卡样服务费订单已经存在时,只修改卡样素材
			style = cardStyleMapper.selectCardStyleByOrderNo(bean.getOrderNo());
			if (null == style)
			{
				// 根据注解验证参数
				String valid = ValidateParamUtil.validateParam(bean);
				HsAssert.isTrue(StringUtils.isBlank(valid), RespCode.PARAM_ERROR, valid + "," + bean);

				// 设置卡样参数
				bean.setCardStyleId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
				bean.setIsSpecial(true);// 是否个性卡样
				bean.setIsLock(false);// 是否锁定
				bean.setIsDefault(false);// 是否默认
				bean.setMicroPic(null);// 缩略图
				bean.setSourceFile(null);// 源文件
				bean.setEnableStatus(EnableStatus.WAIT_ENABLE.getCode());// 启用状态
				bean.setStatus(CardStyleApprStatus.APP_ENABLE.getCode());// 审批状态
				cardStyleMapper.insertCardStyle(bean);
			} else
			{
				// 判断卡样是否锁定
				HsAssert.isTrue(!style.getIsLock().booleanValue(), RespCode.PARAM_ERROR, "个性卡样已经锁定," + bean);

				style = new CardStyle(null, null, bean.getMaterialMicroPic(), bean.getMaterialSourceFile(),
						bean.getOrderNo());
				style.setReqOperator(bean.getReqOperator());
				style.setReqRemark(bean.getReqRemark());
				// 修改卡样素材
				count = cardStyleMapper.updateCardStyleByNo(style);
			}
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:addSpecialCardStyleEnt",
					BSRespCode.BS_UPLOAD_CARD_STYLE_MATERIAL_FAIL.getCode() + ":企业上传卡样素材失败," + bean, ex);
			throw new HsException(BSRespCode.BS_UPLOAD_CARD_STYLE_MATERIAL_FAIL, "企业上传卡样素材失败," + bean);
		}
		if (null != style)
		{
			HsAssert.isTrue(count > 0, BSRespCode.BS_UPLOAD_CARD_STYLE_MATERIAL_FAIL, "企业上传卡样素材失败," + bean);
		}
	}

	/**
	 * 企业确认卡样
	 *
	 * @param bean
	 *            卡样参数实体
	 * @throws HsException
	 * @Description:
	 */
	@Override
	@Transactional
	public void entConfirmCardStyle(CardStyle bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:entConfirmCardStyle", "params==>" + bean, "企业确认卡样");
		int count = 0;
		// 验证参数实体、订单号
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "企业确认卡样参数实体为null," + bean);
		HsAssert.hasText(bean.getOrderNo(), RespCode.PARAM_ERROR, "企业确认卡样订单号为null," + bean);
		try
		{
			// 设置卡样数据
			bean.setEnableStatus(EnableStatus.ENABLED.getCode());
			bean.setStatus(CardStyleApprStatus.ENABLE.getCode());
			bean.setConfirmFile(null);// 设置确认文件不被修改掉
			bean.setIsConfirm(true);// 是否确认
			// 修改卡样启用
			count = cardStyleMapper.updateCardStyleByNo(bean);
		} catch (HsException ex)
		{
			SystemLog.error(this.getClass().getName(), "function:entConfirmCardStyle",
					BSRespCode.BS_ENT_CARD_STYLE_CONFIRM_FAIL + ":企业确认卡样失败," + bean, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_ENT_CARD_STYLE_CONFIRM_FAIL, "企业确认卡样失败," + bean);
	}

	/**
	 * 企业上传互生卡制作卡样确认书
	 *
	 * @param confNo
	 *            配置单号
	 * @param confirmFile
	 *            确认书文件
	 * @throws HsException
	 * @Description:
	 */
	@Override
	@Transactional
	public void uploadCardMarkConfirmFile(String confNo, String confirmFile) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:uploadCardMarkConfirmFile",
				"params==>confNo" + confNo + ",confirmFile" + confirmFile, "企业确认卡样");
		int count = 0;
		// 验证配置单号、确认文件
		HsAssert.hasText(confNo, RespCode.PARAM_ERROR,
				"卡制作卡样确认书配置单号为null,confNo" + confNo + ",confirmFile" + confirmFile);
		HsAssert.hasText(confirmFile, RespCode.PARAM_ERROR,
				"卡制作卡样确认书为null,confNo" + confNo + ",confirmFile" + confirmFile);
		try
		{
			count = toolConfigMapper.updateToolConfigByConfNo(new ToolConfig(confNo, confirmFile));
		} catch (HsException ex)
		{
			SystemLog.error(this.getClass().getName(), "function:uploadCardMarkConfirmFile",
					BSRespCode.BS_UPLOAD_CARD_MARK_CONFIRM_FILE_FAIL + ":上传互生卡制作确认文件失败 ,confNo" + confNo
							+ ",confirmFile" + confirmFile,
					ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_UPLOAD_CARD_MARK_CONFIRM_FILE_FAIL,
				"上传互生卡制作确认文件失败 ,confNo" + confNo + ",confirmFile" + confirmFile);
	}

	/**
	 * 工具订单确认制作
	 *
	 * @param orderNo
	 *            订单号
	 * @throws HsException
	 * @Description:
	 */
	@Override
	@Transactional
	public void toolOrderConfirmMark(String orderNo) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:toolOrderConfirmMark", "params==>" + orderNo, "工具订单确认制作");
		// 订单实体
		Order bean = null;
		// 配置单
		List<ToolConfig> confs = null;
		int count = 0;
		HsAssert.hasText(orderNo, RespCode.PARAM_ERROR, "工具订单确认制作参数为null," + orderNo);
		// 获取订单数据
		bean = orderMapper.findToolOrderByNo(orderNo, new String[]
		{ OrderType.BUY_TOOL.getCode(), OrderType.REMAKE_BATCH_CARD.getCode(), OrderType.REMAKE_MY_CARD.getCode(),
				OrderType.APPLY_PERSON_RESOURCE.getCode() });

		// 验证订单的状态(是否待确认)
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "订单号错误或不是确认制作的订单类型," + orderNo);
		HsAssert.isTrue(bean.getOrderStatus().intValue() == OrderStatus.WAIT_CONFIRM.getCode(),
				BSRespCode.BS_ORDER_IS_NOT_WAIT_CONFIRM, "工具订单不是待确认状态," + orderNo);

		// 查询工具配置单
		confs = toolConfigMapper.selectToolConfigByOrderNo(orderNo);
		HsAssert.isTrue(!CollectionUtils.isEmpty(confs), BSRespCode.BS_TOOL_ORDER_NOT_QUERY_CONFIG, "工具订单未查询到配置单");

		// 如果申购系统资源,进行验证已经确认的卡的数量
		if (OrderType.APPLY_PERSON_RESOURCE.getCode().equals(bean.getOrderType()))
		{
			int quantity = toolConfigMapper.selectConfirmMardCardCount(bean.getCustId());
			HsAssert.isTrue(quantity <= 9999, BSRespCode.BS_CARD_CONFIRM_MARK_EXCEED_MAX, "系统资源已确认制作数量操作最大数量");
		}
		try
		{
			// 修改订单状态(待配货)
			count = orderMapper.updateOrderStatus(bean.getOrderNo(), OrderStatus.WAIT_CONFIG_GOODS.getCode());
			// 包含工具配置单的工具订单
			if (count > 0)
			{
				// 修改配置单(待配置)
				count = toolConfigMapper.updateConfStatusByOrderNo(bean.getOrderNo(), ConfStatus.WAIT_CONFIG.getCode());
			}
			// 申购消费者资源段订单类型撤单修改资源为已购买
			if (OrderType.APPLY_PERSON_RESOURCE.getCode().equals(bean.getOrderType()) && false)
			{
				resSegmentMapper.updateResSegmentStatusByOrderNo(bean.getOrderNo(), BuyStatus.BOUGHT.getCode());
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:toolOrderConfirmMark",
					BSRespCode.BS_ORDER_IS_CONFIRM_MARK_FAIL.getCode() + ":工具订单确认制作失败," + orderNo, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_ORDER_IS_CONFIRM_MARK_FAIL, "工具订单确认制作失败," + orderNo);
	}

	/**
	 * 工具订单撤单
	 *
	 * @param orderNo
	 *            订单号
	 * @throws HsException
	 * @Description:
	 */
	@Override
	@Transactional
	@Deprecated
	public void toolOrderWithdrawals(String orderNo) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:toolOrderWithdrawals", "params==>" + orderNo, "工具订单撤单");
		int count = 0;
		// 订单实体
		Order bean = null;
		// 地区平台信息
		LocalInfo info = commonService.getAreaPlatInfo();
		// 地区平台客户号
		String platCustId = commonService.getAreaPlatCustId();
		// 交易类型
		String transType = "";
		// 内部记账分解参数
		List<AccountDetail> details = null;
		HsAssert.hasText(orderNo, RespCode.PARAM_ERROR, "工具订单撤单参数为null," + orderNo);
		// 获取订单数据
		bean = orderMapper.findToolOrderByNo(orderNo, new String[]
		{ OrderType.BUY_TOOL.getCode(), OrderType.REMAKE_BATCH_CARD.getCode(),
				OrderType.APPLY_PERSON_RESOURCE.getCode() });
		// 验证订单的状态(是否待确认)
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "订单号错误或不是可以撤单的订单类型," + orderNo);

		HsAssert.isTrue(bean.getOrderStatus().intValue() == OrderStatus.WAIT_CONFIRM.getCode(),
				BSRespCode.BS_ORDER_IS_NOT_WAIT_CONFIRM, "工具订单不是待确认状态," + orderNo);
		// 获取交易类型
		transType = TransCodeUtil.getToolOrderCancelTransCode(bean.getOrderType(), bean.getPayChannel());

		try
		{
			// 内部记账分解
			details = new ArrayList<AccountDetail>();
			AccountDetail detail = null;
			// 客户
			detail = new AccountDetail(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()), bean.getOrderNo(),
					transType, bean.getCustId(), bean.getHsResNo(), bean.getCustName(), bean.getCustType(),
					AccountType.ACC_TYPE_POINT20110.getCode(), bean.getOrderHsbAmount(), "0", info.getHsbCode(),
					DateUtil.getCurrentDateTime(), "工具订单撤单企业记账", true);
			details.add(detail);
			// 地区平台
			detail = new AccountDetail(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()), bean.getOrderNo(),
					transType, platCustId, info.getPlatResNo(), info.getPlatNameCn(), CustType.AREA_PLAT.getCode(),
					getAccountType(bean.getOrderType()), "0", bean.getOrderHsbAmount(), info.getHsbCode(),
					DateUtil.getCurrentDateTime(), "工具订单撤单平台记账", true);

			details.add(detail);
			// 修改订单撤单
			count = orderMapper.updateOrderStatus(bean.getOrderNo(), OrderStatus.CANCELED.getCode());
			if (count > 0)
			{
				// 修改配置单取消
				count = toolConfigMapper.updateConfStatusByOrderNo(bean.getOrderNo(), ConfStatus.CANCELED.getCode());
			}
			// 申购消费者资源段订单类型撤单修改资源为待购买
			if (OrderType.APPLY_PERSON_RESOURCE.getCode().equals(bean.getOrderType()) && false)
			{
				resSegmentMapper.updateResSegmentStatusByOrderNo(bean.getOrderNo(),
						BuyStatus.MAY_BUY.getCode());
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:toolOrderWithdrawals",
					BSRespCode.BS_TOOL_ORDER_WITHDRAWALS_FAIL.getCode() + ":工具订单撤单失败", ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_TOOL_ORDER_WITHDRAWALS_FAIL, "工具订单撤单失败," + orderNo);
		// 调用内部记账分解
		accountDetailService.saveGenActDetail(details, "工具订单撤单记账", true);
	}

	/**
	 * 获取账户类型
	 *
	 * @param orderType
	 * @return : String
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月16日 下午3:49:15
	 * @version V3.0.0
	 */
	private String getAccountType(String orderType)
	{
		if (OrderType.APPLY_PERSON_RESOURCE.getCode().equals(orderType))
		{
			return AccountType.ACC_TYPE_POINT20520.getCode();
		} else if (OrderType.CARD_STYLE_FEE.getCode().equals(orderType))
		{
			return AccountType.ACC_TYPE_POINT20460.getCode();
		}
		return AccountType.ACC_TYPE_POINT20430.getCode();
	}

	/**
	 * 关闭或撤销工具订单 关闭(未付款之前的订单) 撤单(付款成功的,退款)
	 *
	 * @param orderNo
	 *            订单号
	 * @throws HsException
	 * @Description:
	 */
	@Override
	public void closeOrWithdrawalsToolOrder(String orderNo) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:closeOrWithdrawalsToolOrder", "params==>" + orderNo,
				"关闭或撤销工具订单 关闭(未付款之前的订单) 撤单(付款成功的,退款)");

		HsAssert.hasText(orderNo, RespCode.PARAM_ERROR, "关闭或撤销工具订单参数为null," + orderNo);
		// 订单实体
		Order bean = null;
		// 获取订单数据
		bean = orderMapper.findToolOrderByNo(orderNo, new String[]
		{ OrderType.BUY_TOOL.getCode(), OrderType.REMAKE_MY_CARD.getCode(), OrderType.REMAKE_BATCH_CARD.getCode(),
				OrderType.CARD_STYLE_FEE.getCode(), OrderType.APPLY_PERSON_RESOURCE.getCode() });
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "订单号错误，没有查询到订单数据," + orderNo);
		try
		{
			TempAcctLinkQuery tempAcctLinkQuery = new TempAcctLinkQuery();
			tempAcctLinkQuery.setOrderNo(bean.getOrderNo());
			tempAcctLinkQuery.setStatus(0);
			// 查询临帐信息
			TempAcctLink tempAcctLink = tempAcctLinkMapper.selectBeanByQuery(tempAcctLinkQuery);
			// 正在审批中的订单不可关闭
			HsAssert.isNull(tempAcctLink, BSRespCode.BS_CLOSE_ORDER_IS_APPR, "关闭订单：当前订单正在审批");

			if (bean.getOrderStatus() == OrderStatus.WAIT_PAY.getCode())
			{
				// 调用关闭订单方法
				insideInvokeCall.toolOrderClose(bean);
			} else if (bean.getOrderStatus().intValue() == OrderStatus.WAIT_CONFIRM.getCode())
			{
				// 调用撤销订单方法
				insideInvokeCall.toolOrderWithdrawals(bean);
			} else
			{
				throw new HsException(BSRespCode.BS_ORDER_IS_NOT_WAIT_PAY_OR_CONFIRM, "订单状态错误，不是待付款或待确认状态");
			}
		} catch (HsException ex)
		{
			throw ex;
		}
	}

	/**
	 * 分页查询个人补卡订单
	 *
	 * @param param
	 *            查询参数
	 * @param page
	 *            分页参数
	 * @return
	 * @Description:
	 */
	@Override
	public PageData<Order> queryPersonMendCardOrderPage(BaseParam param, Page page)
	{
		if (null == page || null == param || StringUtils.isBlank(param.getHsCustId()))
		{
			return new PageData<Order>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 设置订单类型
		param.setType(OrderType.REMAKE_MY_CARD.getCode());
		// 设置查询时间
		param.setQueryDate();
		// 查询工具订单列表
		List<Order> orders = commonMapper.selectPersonMendCardOrderListPage(param);
		if (StringUtils.isNotBlank(orders))
		{
			return new PageData<Order>(page.getCount(), orders);
		}
		return new PageData<Order>(0, null);
	}

	/**
	 * 验证生成订单时购买的数量是否够
	 *
	 * @param hsCustId
	 *            客户号
	 * @param confs
	 *            配置单列表
	 * @param details
	 *            代购清单
	 * @return : String
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月16日 上午11:36:02
	 * @version V3.0.0
	 */
	private String vaildCreateToolOrderBuyNum(String hsCustId, List<ToolConfig> confs, List<ProxyOrderDetail> details)
	{
		// 工具类别
		String categoryCode = "";
		// 验证实体集合
		Map<String, ToolVaild> map = null;
		// 验证实体
		ToolVaild vaild = null;
		// 新增申购
		if (null != confs && confs.size() > 0)
		{
			map = new HashMap<String, ToolVaild>();
			for (ToolConfig conf : confs)
			{
				// 消费刷卡器和积分刷卡器同用数量进行累加
				if (conf.getCategoryCode().equals(CategoryCode.POINT_MCR.name())
						|| conf.getCategoryCode().equals(CategoryCode.CONSUME_MCR.name()))
				{
					categoryCode = "'" + CategoryCode.CONSUME_MCR.name() + "','" + CategoryCode.POINT_MCR.name() + "'";
				} else
				{
					categoryCode = "'" + conf.getCategoryCode() + "'";
				}
				vaild = map.get(categoryCode);
				map.put(categoryCode, new ToolVaild(categoryCode, null != vaild
						? vaild.getQuantity() + conf.getQuantity().intValue() : conf.getQuantity().intValue()));
			}
		}
		// 平台代购
		if (null != details && details.size() > 0)
		{
			map = new HashMap<String, ToolVaild>();
			for (ProxyOrderDetail detail : details)
			{
				// 消费刷卡器和积分刷卡器同用数量进行累加
				if (detail.getCategoryCode().equals(CategoryCode.POINT_MCR.name())
						|| detail.getCategoryCode().equals(CategoryCode.CONSUME_MCR.name()))
				{
					categoryCode = "'" + CategoryCode.CONSUME_MCR.name() + "','" + CategoryCode.POINT_MCR.name() + "'";
				} else
				{
					categoryCode = "'" + detail.getCategoryCode() + "'";
				}
				vaild = map.get(categoryCode);
				map.put(categoryCode, new ToolVaild(categoryCode,
						null != vaild ? vaild.getQuantity() + detail.getQuantity() : detail.getQuantity()));
			}
		}
		return vaildToolNum(map, hsCustId);
	}

	/**
	 * 验证
	 *
	 * @param map
	 *            数据集合
	 * @param hsCustId
	 *            客户号
	 * @return : String
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月7日 下午3:52:15
	 * @version V3.0.0
	 */
	private String vaildToolNum(Map<String, ToolVaild> map, String hsCustId)
	{
		if (!CollectionUtils.isEmpty(map))
		{
			for (Map.Entry<String, ToolVaild> entry : map.entrySet())
			{
				ToolVaild vaild = entry.getValue();
				int count = toolProductMapper.selectMayBuyToolNum(hsCustId, entry.getKey());
				if (vaild.getQuantity() > count)
				{
					return entry.getKey();
				}
			}
		}
		return "";
	}
}
