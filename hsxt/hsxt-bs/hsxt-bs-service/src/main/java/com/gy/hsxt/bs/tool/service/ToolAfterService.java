/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tool.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.api.tool.IBSToolAfterService;
import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.order.DeliverInfo;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.tool.*;
import com.gy.hsxt.bs.bean.tool.resultbean.*;
import com.gy.hsxt.bs.common.ObjectFactory;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.StringUtil;
import com.gy.hsxt.bs.common.enumtype.ApprStatus;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.DeviceFalg;
import com.gy.hsxt.bs.common.enumtype.McrType;
import com.gy.hsxt.bs.common.enumtype.order.*;
import com.gy.hsxt.bs.common.enumtype.tool.*;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.common.util.ValidateParamUtil;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.order.mapper.DeliverInfoMapper;
import com.gy.hsxt.bs.order.mapper.OrderMapper;
import com.gy.hsxt.bs.task.bean.Task;
import com.gy.hsxt.bs.task.interfaces.ITaskService;
import com.gy.hsxt.bs.tool.bean.DeviceConfig;
import com.gy.hsxt.bs.tool.bean.Out;
import com.gy.hsxt.bs.tool.bean.OutDetail;
import com.gy.hsxt.bs.tool.mapper.*;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.*;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.*;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.tm.enumtype.TaskSrc;
import com.gy.hsxt.uc.bs.api.device.IUCBsDeviceService;
import com.gy.hsxt.uc.bs.bean.device.BsCardReader;
import com.gy.hsxt.uc.bs.bean.ent.BsEntAllInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntBaseInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntMainInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 工具售后API接口实现类
 * 
 * @Package: com.gy.hsxt.bs.tool.service
 * @ClassName: ToolAfterService
 * @Description:TODO
 * @author: likui
 * @date: 2015年10月29日 下午6:52:28
 * @company: gyist
 * @version V3.0.0
 */
@Service("toolAfterService")
public class ToolAfterService implements IBSToolAfterService {

	/** 业务服务私有配置参数 **/
	@Autowired
	private BsConfig bsConfig;

	/** 仓库Mapper **/
	@Autowired
	private WarehouseMapper warehouseMapper;

	/** 工具配置单Mapper **/
	@Autowired
	private ToolConfigMapper toolConfigMapper;

	/** 订单Mapper **/
	@Autowired
	private OrderMapper orderMapper;

	/** 收货信息Mapper **/
	@Autowired
	private DeliverInfoMapper deliverInfoMapper;

	/** 发货单Mapper **/
	@Autowired
	private ShippingMapper shippingMapper;

	/** 售后服务Mapper **/
	@Autowired
	private AfterServiceMapper afterServiceMapper;

	/** 配送方式Mapper **/
	@Autowired
	private ShippingMethodMapper shippingMethodMapper;

	/** 公共Mapper **/
	@Autowired
	private CommonMapper commonMapper;

	/** 售后服务查询Mapper **/
	@Autowired
	private AfterServiceSelectMapper afterServiceSelectMapper;

	/** 设备信息Mapper **/
	@Autowired
	private DeviceInfoMapper deviceInfoMapper;

	/** BS公共Service **/
	@Autowired
	private ICommonService commonService;

	/** 任务Service **/
	@Autowired
	private ITaskService taskService;

	/** 用户中心设备Service **/
	@Autowired
	private IUCBsDeviceService bsDeviceService;

	/** 刷卡器数据Mapper **/
	@Autowired
	private KsnInfoMapper ksnInfoMapper;

	/** 出库Mapper **/
	@Autowired
	private OutMapper outMapper;

	/**
	 * 分页查询售后订单
	 * 
	 * @Description: 查询条件参数实体
	 * @param page
	 *            分页参数实体
	 */
	@Override
	public PageData<ToolOrderPage> queryAfterOrderPlatPage(BaseParam param, Page page)
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
		{ OrderType.AFTER_SERVICE.getCode() }));
		// 查询售后工具订单列表
		List<ToolOrderPage> orders = commonMapper.selectToolOrderPlatListPage(param);
		if (StringUtils.isNotBlank(orders))
		{
			return new PageData<ToolOrderPage>(page.getCount(), orders);
		}
		return new PageData<ToolOrderPage>(0, null);
	}

	/**
	 * 验证批量导入设备
	 * 
	 * @Description:
	 * @param seqNos
	 *            设备序列号列表
	 * @return
	 * @throws HsException
	 */
	@Override
	public List<ImportAfterService> validBatchImportSeqNo(List<String> seqNos) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:validBatchImportSeqNo", "params==>" + JSON.toJSONString(seqNos),
				"验证批量导入设备");
		// 验证参数
		HsAssert.isTrue((null != seqNos && seqNos.size() > 0), RespCode.PARAM_ERROR, "验证批量导入设备参数为空");

		// 获取去掉重复序列号列表
		List<String> seqNoList = ObjectFactory.removeDuplicate(seqNos);
		HsAssert.isTrue(seqNos.size() == seqNoList.size(), BSRespCode.BS_DEVICE_NO_LIST_HAS_REPEAT,
				"设备序列号列表中有重复的数据," + JSONObject.toJSONString(seqNos));

		// 验证同一设备是否还有处理中的数据
		seqNoList = afterServiceMapper.selectAfterDetailBySeqNo(StringUtil.assembleQueryInIf(seqNos));
		HsAssert.isTrue(StringUtils.isBlank(seqNoList), BSRespCode.BS_AFTER_IN_HAND_DEVICE_NO,
				"验证批量导入设备序列号有还在处理中的," + JSONObject.toJSONString(seqNoList));

		List<ImportAfterService> beans = new ArrayList<ImportAfterService>();
		ImportAfterService bean = null;
		for (String seqNo : seqNos)
		{
			bean = new ImportAfterService();
			bean.setDeviceSeqNo(seqNo);
			EntDeviceInfo info = deviceInfoMapper.selectEntDeviceInfo(seqNo);
			if (null == info)
			{
				bean.setDeviceFalg(DeviceFalg.NOT_EXIST.getCode());
			} else
			{
				bean.setDeviceFalg(info.getUseStatus().intValue() == UseStatus.USED.getCode()
						? DeviceFalg.EXIST.getCode() : DeviceFalg.NOT_USED.getCode());
				bean.setEntResNo(info.getEntResNo());
				bean.setTerminalNo(info.getTerminalNo());
			}
			beans.add(bean);
		}
		return beans;
	}

	/**
	 * 分页查询企业设备信息
	 * 
	 * @Description:
	 * @param seqNo
	 *            设备序列号
	 * @param entResNo
	 *            企业互生号
	 * @param page
	 *            分页参数
	 * @return
	 */
	@Override
	public PageData<EntDeviceInfo> queryEntDeviceInfoPage(String seqNo, String entResNo, Page page)
	{
		if (null == page || (StringUtils.isBlank(seqNo) && StringUtils.isBlank(entResNo)))
		{
			return new PageData<EntDeviceInfo>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 查询企业设备信息列表
		List<EntDeviceInfo> devices = deviceInfoMapper.selectEntDeviceInfoListPage(seqNo, entResNo);
		if (StringUtils.isNotBlank(devices))
		{
			return new PageData<EntDeviceInfo>(page.getCount(), devices);
		}
		return new PageData<EntDeviceInfo>(0, null);
	}

	/**
	 * 增加售后服务
	 * 
	 * @Description:
	 * @param bean
	 *            售后参数实体
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void addAfterService(AfterService bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:addAfterService", "params==>" + bean, "增加售后服务");
		// 售后服务清单
		List<AfterServiceDetail> details = null;
		// 售后订单编号
		String afterOrderNo = null;
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "增加售后服务参数为NULL," + bean);
		// 根据注解验证参数
		String valid = ValidateParamUtil.validateParam(bean);
		HsAssert.isTrue(StringUtils.isBlank(valid), RespCode.PARAM_ERROR, valid + "," + bean);

		// 获取序列号列表
		List<Object> seqNo = ObjectFactory.getListEntityAttr(bean.getDetail(), "deviceSeqNo");
		// 验证同一设备是否还有处理中的数据
		List<String> seqNos = afterServiceMapper.selectAfterDetailBySeqNo(StringUtil.assembleQueryInIf(seqNo));
		HsAssert.isTrue(StringUtils.isBlank(seqNos), BSRespCode.BS_AFTER_IN_HAND_DEVICE_NO,
				"验证批量导入设备序列号有还在处理中的," + JSONObject.toJSONString(seqNos));

		// 售后服务清单、收货信息
		details = bean.getDetail();
		// 售后服务订单号、收货信息编号
		afterOrderNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
		for (AfterServiceDetail detail : details)
		{
			// 判断设备是否存在
			DeviceInfo info = deviceInfoMapper.selectDeviceInfoBySeqNo(detail.getDeviceSeqNo());
			HsAssert.notNull(info, BSRespCode.BS_TOOL_DEVICE_NOT_EXIST,
					"未查询到设备信息,设备序列号" + detail.getDeviceSeqNo() + "," + bean);
			// 设备只有是出库已使用的才可以售后
			HsAssert.isTrue(
					info.getStoreStatus().intValue() == StoreStatus.OUTED.getCode()
							&& info.getUseStatus().intValue() == UseStatus.USED.getCode(),
					BSRespCode.BS_TOOL_DEVICE_NOT_OUTED, "工具设备不是出库和使用状态," + bean);

			// 设置售后服务清单
			detail.setAfterOrderNo(afterOrderNo);
			detail.setCategoryCode(info.getCategoryCode());
			detail.setProductId(info.getProductId());
			detail.setDisposeStatus(false);
			detail.setIsConfig(false);
		}

		// 售后服务
		bean.setAfterOrderNo(afterOrderNo);
		bean.setCustType(HsResNoUtils.getCustTypeByHsResNo(bean.getEntResNo()));
		bean.setStatus(ApprStatus.WAIT_APPR.getCode());

		try
		{
			// 插入售后服务、售后服务清单数据
			afterServiceMapper.insertAfterService(bean);
			afterServiceMapper.batchInsertAfterServiceDetail(details);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:addAfterService",
					BSRespCode.BS_ADD_AFERT_SERVICE_FAIL.getCode() + ":新增售后服务失败," + bean, ex);
			throw new HsException(BSRespCode.BS_ADD_AFERT_SERVICE_FAIL, "新增售后服务失败," + bean);
		}
		if (bsConfig.getWorkTaskIsOpen().booleanValue())
		{
			// 插入售后服务审批任务，售后服务审批工单办理业务主体设置为售后服务对象
			taskService.saveTask(new Task(afterOrderNo, TaskType.TASK_TYPE213.getCode(),
					commonService.getAreaPlatCustId(), bean.getEntResNo(), bean.getEntCustName()));
		}
	}

	/**
	 * 批量导入售后服务
	 * 
	 * @Description:
	 * @param beans
	 *            导入售后服务参数列表
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void batchImportAfterService(List<ImportAfterService> beans) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:batchImportAfterService", "params==>" + beans, "批量导入售后服务");

		// 售后服务
		List<AfterService> services = null;
		// 售后服务清单
		List<AfterServiceDetail> details = null;
		// 互生号列表
		List<Object> entResNos = null;
		// 售后订单编号
		String afterOrderNo = null;
		// 任务集合
		List<Task> tasks = null;
		// 验证参数非null
		HsAssert.notNull(StringUtils.isNotBlank(beans), RespCode.PARAM_ERROR, "批量导入售后服务参数为NULL," + beans);

		// 根据注解验证参数
		String valid = ValidateParamUtil.validateListParam(beans);
		HsAssert.isTrue(StringUtils.isBlank(valid), RespCode.PARAM_ERROR, valid + "," + beans);
		// 获取去掉重复互生号列表
		entResNos = ObjectFactory.removeDuplicate(ObjectFactory.getListEntityAttr(beans, "entResNo"));

		services = new ArrayList<AfterService>();
		details = new ArrayList<AfterServiceDetail>();
		tasks = new ArrayList<Task>();
		// 循环互生号列表
		for (Object obj : entResNos)
		{
			// 查询企业信息
			BsEntMainInfo info = commonService.getEntMainInfoToUc(obj.toString(), null);
			// 验证企业信息非null
			HsAssert.notNull(StringUtils.isNotBlank(info), BSRespCode.BS_NOT_QUERY_ENT_INFO,
					"未查询到企业信息," + obj.toString() + "," + beans);
			// 设置售后服务信息
			afterOrderNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
			services.add(new AfterService(afterOrderNo, info.getEntCustId(), info.getEntResNo(), info.getEntName(),
					info.getEntCustType(), ApprStatus.WAIT_APPR.getCode()));
			// 批量生成任务
			tasks.add(new Task(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()), afterOrderNo,
					TaskType.TASK_TYPE213.getCode(), commonService.getAreaPlatCustId(), info.getEntResNo(),
					info.getEntName(), TaskSrc.BS.getCode()));
			// 循环导入数据
			for (ImportAfterService after : beans)
			{
				if (after.getEntResNo().equals(obj.toString()))
				{
					// 判断设备是否存在
					DeviceInfo device = deviceInfoMapper.selectDeviceInfoBySeqNo(after.getDeviceSeqNo());
					HsAssert.notNull(info, BSRespCode.BS_TOOL_DEVICE_NOT_EXIST,
							"未查询到设备信息,设备序列号" + after.getDeviceSeqNo() + "," + beans);
					// 设备只有是出库已使用的才可以售后
					HsAssert.isTrue(
							device.getStoreStatus().intValue() == StoreStatus.OUTED.getCode()
									&& device.getUseStatus().intValue() == UseStatus.USED.getCode(),
							BSRespCode.BS_TOOL_DEVICE_NOT_OUTED, "工具设备不是出库和使用状态," + beans);
					// 设置售后清单
					details.add(new AfterServiceDetail(afterOrderNo, after.getDeviceSeqNo(), after.getTerminalNo(),
							device.getCategoryCode(), device.getProductId(), DisposeType.WAIT_DISPOSE.getCode(), false,
							false, device.getCategoryCode() + "售后清单"));
				}
			}
		}
		try
		{
			// 插入售后服务、售后服务清单
			afterServiceMapper.batchInsertAfterService(services);
			afterServiceMapper.batchInsertAfterServiceDetail(details);
			// 批量插入任务
			taskService.batchSaveTask(tasks);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:addAfterService",
					BSRespCode.BS_ADD_AFERT_SERVICE_FAIL.getCode() + ":批量导入售后服务失败," + beans, ex);
			throw new HsException(BSRespCode.BS_ADD_AFERT_SERVICE_FAIL, "批量导入售后服务失败," + beans);
		}
	}

	/**
	 * 根据编号查询售后单
	 * 
	 * @Description:
	 * @param afterOrderNo
	 *            售后服务订单号
	 * @return
	 */
	@Override
	public AfterService queryAfterServiceByNo(String afterOrderNo)
	{
		if (StringUtils.isBlank(afterOrderNo))
		{
			return null;
		}
		// 获取售后服务
		AfterService bean = afterServiceMapper.selectAfterServiceByNo(afterOrderNo);
		if (null != bean)
		{
			// 获取售后服务清单
			bean.setDetail(afterServiceMapper.selectAfterServiceDetailByNo(afterOrderNo));
			// 获取售后服务收货信息
			bean.setDeliver(deliverInfoMapper.findDeliverByDeliverId(bean.getDeliverId()));
		}
		return bean;
	}

	/**
	 * 分页查询售后单审批
	 * 
	 * @Description:
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * 
	 * @return
	 */
	@Override
	public PageData<AfterService> queryAfterOrderApprPage(BaseParam bean, Page page)
	{
		if (null == page)
		{
			return new PageData<AfterService>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 如果关闭工单，查询时不关联经办人进行查询，需要设置经办人为空
		if (!bsConfig.getWorkTaskIsOpen().booleanValue())
		{
			bean.setExeCustId(null);
		}
		// 设置查询时间
		bean.setQueryDate();
		// 查询售后服务审批列表
		List<AfterService> afters = afterServiceMapper.selectAfterServiceByListPage(bean);
		if (StringUtils.isNotBlank(afters))
		{
			return new PageData<AfterService>(page.getCount(), afters);
		}
		return new PageData<AfterService>(0, null);
	}

	/**
	 * 审批售后服务
	 * 
	 * @Description:
	 * @param bean
	 *            售后服务参数实体
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void apprAfterService(AfterService bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:apprAfterService", "params==>" + bean, "审批售后服务");
		// 售后服务清单
		List<AfterServiceDetail> details = null;
		// POS机类别数量、费用、配置单号、工具编号
		int posNum = 0;
		String posConfNo = null;
		String posFee = "0";
		String posProductId = null;
		// 平板类别数量、费用、配置单号、工具编号
		int tabletNum = 0;
		String tabletFee = "0";
		String tabletConfNo = null;
		String tabletProductId = null;
		// 积分刷卡器类别数量、费用、配置单号、工具编号
		int pointNum = 0;
		String pointFee = "0";
		String pointConfNo = null;
		String pointProductId = null;
		// 消费刷卡器类别数量、费用、配置单号、工具编号
		int comsumeNum = 0;
		String comsumeFee = "0";
		String comsumeConfNo = null;
		String comsumeProductId = null;
		// 订单编号
		String orderNo = "";
		// 订单互生币金额
		String orderHsbAmount = "0";
		// 地区平台信息
		LocalInfo info = null;
		// 订单状态
		Integer orderStatus = OrderStatus.WAIT_CONFIG_GOODS.getCode();
		// 支付状态
		Integer payStatus = PayStatus.PAY_FINISH.getCode();
		// 支付超时时间
		String payOverTime = "";
		// 支付时间
		String payTime = DateUtil.getCurrentDateTime();
		// 支付方式
		Integer payChannel = PayChannel.HS_COIN_PAY.getCode();
		// 配置状态
		Integer confStatus = ConfStatus.WAIT_CONFIG.getCode();
		// 配置单
		List<ToolConfig> confs = null;
		// 总金额
		BigDecimal amount = null;
		// 仓库
		Warehouse wh = null;
		// 设备集合
		List<DeviceInfo> devices = null;
		// 企业基本信息
		BsEntBaseInfo baseInfo = null;
		// 任务id
		String taskId = "";
		int count = 0;

		// 验证参数非null
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "审批售后服务参数为NULL," + bean);
		HsAssert.notNull(bean.getStatus(), RespCode.PARAM_ERROR, "售后服务审批状态为NULL," + bean);

		// 打开工单
		if (bsConfig.getWorkTaskIsOpen().booleanValue())
		{
			// 获取售后服务审批任务id
			taskId = taskService.getSrcTask(bean.getAfterOrderNo(), bean.getExeCustId());
			HsAssert.hasText(taskId, RespCode.PARAM_ERROR, "客户未查询到待办的任务," + bean);
		}

		// 验证售后服务清单数据
		details = bean.getDetail();
		HsAssert.isTrue(!CollectionUtils.isEmpty(details), RespCode.PARAM_ERROR, "审批售后服务清单为NULL," + bean);
		HsAssert.isTrue(bean.getStatus().intValue() != ApprStatus.WAIT_APPR.getCode(), RespCode.PARAM_ERROR,
				"售后服务审批状态错误," + bean);

		// 根据售后服务订单号查询是否存在
		AfterService param = afterServiceMapper.selectAfterServiceByNo(bean.getAfterOrderNo());
		HsAssert.notNull(param, BSRespCode.BS_NOT_QUERY_AFERT_SERVICE, "未查询到售后服务数据," + bean);

		// 查询用户中心企业信息
		baseInfo = commonService.getEntBaseInfoToUc(param.getEntResNo(), null);
		if (null == baseInfo)
		{
			SystemLog.debug(this.getClass().getName(), "function:apprAfterService",
					BSRespCode.BS_NOT_QUERY_ENT_INFO.getCode() + ":未查询到企业信息," + bean);
		}
		try
		{
			// 审批通过
			if (bean.getStatus().intValue() == ApprStatus.PASS.getCode())
			{
				// 收费服务订单号、POS机配置单号、平板配置单号、积分刷卡器配置单号、消费刷卡器配置单号
				orderNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
				posConfNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
				tabletConfNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
				pointConfNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
				comsumeConfNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
				// 设备信息列表、配置单列表
				devices = new ArrayList<DeviceInfo>();
				confs = new ArrayList<ToolConfig>();
				// 地区平台信息
				info = commonService.getAreaPlatInfo();
				// 根据企业省编号获取仓库，不存在就获取地区平台默认仓库
				wh = warehouseMapper.selectWarehouseByNo(null != baseInfo ? baseInfo.getProvinceCode() : null);
				if (null == wh)
				{
					// 获取地区平台默认仓库
					wh = warehouseMapper.selectWarehouseByDefault();
				}
				// 售后服务清单
				for (AfterServiceDetail detail : details)
				{
					// 设备信息
					DeviceInfo device = new DeviceInfo();
					// 根据处理类型设置是否配置、设备状态、设备序列号
					switch (DisposeType.getDisposeType(detail.getDisposeType().intValue()))
					{
					case NO_DISPOSE:// 无故障
						device.setUseStatus(UseStatus.USED.getCode());
						detail.setNewDeviceSeqNo(detail.getDeviceSeqNo());
						break;
					case BARTER:// 换货，设备已报废
						device.setUseStatus(UseStatus.BEEN_REJECT.getCode());
						break;
					case ANEW_CONFIG:// 重新配置
						device.setUseStatus(UseStatus.BEEN_REPAIRED.getCode());
						detail.setNewDeviceSeqNo(detail.getDeviceSeqNo());
						break;
					case SERVICING:// 维修，设备已维修
						device.setUseStatus(UseStatus.BEEN_REPAIRED.getCode());
						detail.setNewDeviceSeqNo(detail.getDeviceSeqNo());
						break;
					default:
						break;
					}
					// 根据售后清单的处理类型 获取工具类别数量、处理的费用、设置配置单号
					switch (CategoryCode.getCode(detail.getCategoryCode()))
					{
					case P_POS:// POS机
						posNum = posNum + 1;// 数量
						posFee = BigDecimalUtils.ceilingAdd(posFee, detail.getDisposeAmount()).toString();// 费用
						detail.setConfNo(posConfNo);// 配置单号
						posProductId = detail.getProductId();// 工具编号
						break;
					case TABLET:// 平板
						tabletNum = tabletNum + 1;// 数量
						tabletFee = BigDecimalUtils.ceilingAdd(tabletFee, detail.getDisposeAmount()).toString();// 费用
						detail.setConfNo(tabletConfNo);// 配置单号
						tabletProductId = detail.getProductId();// 工具编号
						break;
					case POINT_MCR:// 接刷卡器
						pointNum = pointNum + 1;// 数量
						pointFee = BigDecimalUtils.ceilingAdd(pointFee, detail.getDisposeAmount()).toString();// 费用
						detail.setConfNo(pointConfNo);// 配置单号
						pointProductId = detail.getProductId();// 工具编号
						break;
					case CONSUME_MCR:// 消费刷卡器
						comsumeNum = comsumeNum + 1;// 数量
						comsumeFee = BigDecimalUtils.ceilingAdd(comsumeFee, detail.getDisposeAmount()).toString();// 费用
						detail.setConfNo(comsumeConfNo);// 配置单号
						comsumeProductId = detail.getProductId();// 工具编号
						break;
					default:
						break;
					}
					device.setDeviceSeqNo(detail.getDeviceSeqNo());
					device.setUpdatedBy(bean.getApprOperator());
					devices.add(device);
				}
				// 判断累加的金额是否大于0
				amount = BigDecimalUtils.ceilingAdd(BigDecimalUtils.ceilingAdd(posFee, tabletFee).toString(),
						BigDecimalUtils.ceilingAdd(pointFee, comsumeFee).toString());

				// 金额大于0，需要付款，生成未付款的订单，反之生成付款成功金额为0的订单
				if (amount.compareTo(BigDecimal.ZERO) == 1)
				{
					// 订单互生币金额、订单状态、支付状态、配置单状态、支付超时时间、支付方式
					orderHsbAmount = amount.toString();
					orderStatus = OrderStatus.WAIT_PAY.getCode();
					payStatus = PayStatus.WAIT_PAY.getCode();
					confStatus = ConfStatus.WAIT_PAY.getCode();
					payOverTime = DateUtil.addDaysStr(null, bsConfig.getToolOrderInvalidDays());
					payChannel = null;
					payTime = "";
				}

				// 生成工具订单
				Order order = new Order();
				order.setOrderNo(orderNo);// 订单号
				order.setCustId(param.getEntCustId());// 客户号
				order.setHsResNo(param.getEntResNo());// 互生号
				order.setCustName(param.getEntCustName());// 客户名称
				order.setCustType(param.getCustType());// 客户类型
				order.setBizNo(param.getAfterOrderNo());// 业务编号
				order.setOrderType(OrderType.AFTER_SERVICE.getCode());// 订单类型
				order.setIsProxy(true);// 是否平台代购
				order.setQuantity(null);
				order.setOrderUnit("");
				order.setOrderHsbAmount(orderHsbAmount);// 互生币金额
				order.setOrderOriginalAmount(orderHsbAmount);// 原始金额
				order.setOrderCashAmount(BigDecimalUtils.ceilingMul(orderHsbAmount, info.getExchangeRate()).toString());// 货币金额
				order.setOrderDerateAmount("0");
				order.setExchangeRate(info.getExchangeRate());// 货币转换比率
				order.setCurrencyCode(info.getHsbCode());// 币种
				order.setOrderRemark("售后服务订单");
				order.setOrderTime(DateUtil.getCurrentDateTime());// 订单时间
				order.setOrderChannel(OrderChannel.WEB.getCode());// 订单渠道
				order.setPayChannel(payChannel);// 支付方式
				order.setPayTime(payTime);// 支付时间
				order.setOrderOperator(bean.getApprOperator());
				order.setPayOvertime(payOverTime);// 支付超时时间
				order.setOrderStatus(orderStatus);// 订单状态
				order.setPayStatus(payStatus);// 支付状态
				order.setIsNeedInvoice(OrderGeneral.YES.getCode());// 是否需要发票

				// POS机售后服务配置单
				if (posNum > 0)
				{
					ToolConfig conf = new ToolConfig(posConfNo, param.getEntCustId(), param.getEntResNo(), null,
							CategoryCode.P_POS.name(), posProductId, "POS机", "台",
							BigDecimalUtils.ceilingDiv(posFee, String.valueOf(posNum)).toString(), posNum,
							posFee.toString(), null, orderNo, confStatus, null, "售后服务订单配置", bean.getApprOperator(),
							null, ConfType.AFTER_CONFIG.getCode(), wh.getWhId());
					confs.add(conf);
				}
				// 平板售后服务配置单
				if (tabletNum > 0)
				{
					ToolConfig conf = new ToolConfig(tabletConfNo, param.getEntCustId(), param.getEntResNo(), null,
							CategoryCode.TABLET.name(), tabletProductId, "互生平板", "台",
							BigDecimalUtils.ceilingDiv(tabletFee, String.valueOf(tabletNum)).toString(), tabletNum,
							tabletFee.toString(), null, orderNo, confStatus, null, "售后服务订单配置", bean.getApprOperator(),
							null, ConfType.AFTER_CONFIG.getCode(), wh.getWhId());
					confs.add(conf);
				}
				// 积分刷卡器售后服务配置单
				if (pointNum > 0)
				{
					ToolConfig conf = new ToolConfig(pointConfNo, param.getEntCustId(), param.getEntResNo(), null,
							CategoryCode.POINT_MCR.name(), pointProductId, "积分刷卡器", "个",
							BigDecimalUtils.ceilingDiv(pointFee, String.valueOf(pointNum)).toString(), pointNum,
							pointFee.toString(), null, orderNo, confStatus, null, "售后服务订单配置", bean.getApprOperator(),
							null, ConfType.AFTER_CONFIG.getCode(), wh.getWhId());
					confs.add(conf);
				}
				// 消费刷卡器售后服务配置单
				if (comsumeNum > 0)
				{
					ToolConfig conf = new ToolConfig(comsumeConfNo, param.getEntCustId(), param.getEntResNo(), null,
							CategoryCode.CONSUME_MCR.name(), comsumeProductId, "消费刷卡器", "个",
							BigDecimalUtils.ceilingDiv(comsumeFee, String.valueOf(comsumeNum)).toString(), comsumeNum,
							comsumeFee.toString(), null, orderNo, confStatus, null, "售后服务订单配置", bean.getApprOperator(),
							null, ConfType.AFTER_CONFIG.getCode(), wh.getWhId());
					confs.add(conf);
				}
				// 插入订单、配置单数据、修改设备状态
				orderMapper.insertOrder(order);
				toolConfigMapper.batchInsertToolConfig(confs);
				deviceInfoMapper.batchUpdateDeviceStatus(devices);

				bean.setDisposeAmount(amount.toString());
				// 修改售后服务清单
				afterServiceMapper.batchUpdateAfterServiceDetail(details);
			}
			// 修改售后服务
			count = afterServiceMapper.updateAfterService(bean);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:apprAfterService",
					BSRespCode.BS_AFERT_SERVICE_ARRP_FAIL.getCode() + "售后服务审批失败," + bean, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_AFERT_SERVICE_ARRP_FAIL, "售后服务审批失败," + bean);
		// 打开工单
		if (bsConfig.getWorkTaskIsOpen().booleanValue())
		{
			// 修改任务完成、售后服务
			taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());
		}
	}

	/**
	 * 分页查询售后刷卡工具制作配置单
	 * 
	 * @Description:
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 */
	@Override
	public PageData<ToolConfigPage> queryToolAfterConfigPage(BaseParam bean, Page page)
	{
		if (null == page || null == bean)
		{
			return new PageData<ToolConfigPage>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 设置日期查询条件
		bean.setQueryDate();
		// 设置角色列表
		bean.setRoleId();
		// 查询售后刷卡工具制作列表
		List<ToolConfigPage> tools = afterServiceSelectMapper.selectToolAfterConfigListPage(bean);
		if (StringUtils.isNotBlank(tools))
		{
			return new PageData<ToolConfigPage>(page.getCount(), tools);
		}
		return new PageData<ToolConfigPage>(0, null);
	}

	/**
	 * 查询售后配置单详情
	 * 
	 * @Description:
	 * @param confNo
	 *            配置单号
	 * @return
	 */
	@Override
	public List<AfterServiceDetail> queryAfterConfigDetail(String confNo)
	{
		if (StringUtils.isBlank(confNo))
		{
			return null;
		}
		return afterServiceMapper.selectAfterServiceDetailByConfNo(new String[]
		{ confNo });
	}

	/**
	 * 刷卡工具保持关联(POS机、平板、刷卡器)
	 * 
	 * @Description:
	 * @param bean
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void keepDeviceRelationAfter(AfterKeepConfig bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:keepDeviceRelationAfter", "params==>" + bean, "刷卡工具保持关联");
		// 设备信息
		DeviceInfo device = null;
		// 售后清单
		List<AfterServiceDetail> details = null;
		int count = 0;

		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "配置刷卡工具保持关联参数为null," + bean);

		// 根据注解验证参数
		String valid = ValidateParamUtil.validateParam(bean);
		HsAssert.isTrue(StringUtils.isBlank(valid), RespCode.PARAM_ERROR, valid + "," + bean);

		// 查询配置单未配置的售后清单
		details = afterServiceMapper.selectAfterServiceDetailByNoIdNo(bean.getEntCustId(), bean.getAfterOrderNo(),
				bean.getConfNo(), false);
		HsAssert.isTrue(!CollectionUtils.isEmpty(details), BSRespCode.BS_NOT_QUERY_AFTER_DETAIL,
				"未查询客户配置单的售后清单数据," + bean);

		// 验证配置单状态
		HsAssert.isTrue(details.size() >= 1, BSRespCode.BS_TOOL_CONFIG_IS_NOT_WAIT_CONFIG, "配置单不是待配置状态," + bean);

		// 验证设备
		device = deviceInfoMapper.selectDeviceInfoBySeqNo(bean.getDeviceSeqNo());
		HsAssert.notNull(device, BSRespCode.BS_TOOL_DEVICE_NOT_EXIST, "工具设备不存在," + bean);

		// 验证设备状态
		HsAssert.isTrue(device.getUseStatus().intValue() == UseStatus.USED.getCode(), BSRespCode.BS_DEVICE_NOT_USED,
				"设备状态错误,不是已使用状态," + bean);
		try
		{
			// 修改售后清单完成
			count = afterServiceMapper.updateAfterServiceDetailConfig(bean.getAfterOrderNo(), bean.getDeviceSeqNo(),
					bean.getDeviceSeqNo());
			// 修改设备状态
			if (count > 0)
			{
				count = deviceInfoMapper.updateDeviceUseStatusById(device.getDeviceCustId(), UseStatus.USED.getCode(),
						null, bean.getOperNo());
			}
			// 配置单只有最后一个清单未完成时修改配置单状态
			if (details.size() == 1 && count > 0)
			{
				count = toolConfigMapper.updateToolConfigByConfNo(
						new ToolConfig(bean.getConfNo(), null, ConfStatus.WAIT_SEND.getCode(), null, null));
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:keepDeviceRelationAfter",
					BSRespCode.BS_KEEP_AFTER_CONFIG_FAIL + ":配置刷卡工具售后保持关联失败," + bean, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_KEEP_AFTER_CONFIG_FAIL, "配置刷卡工具售后保持关联失败," + bean);
	}

	/**
	 * 配置刷卡器售后关联
	 * 
	 * @Description:
	 * @param bean
	 *            售后服务配置参数实体
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void configMcrRelationAfter(AfterServiceConfig bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:configMcrRelationAfter", "params==>" + bean, "配置刷卡器售后关联");
		// 设备信息
		DeviceInfo device = null;
		// 设备配置详情
		DeviceConfig dConf = null;
		// 售后清单
		List<AfterServiceDetail> details = null;
		BsCardReader cardReader = null;
		// 积分刷卡器KSN
		PointKSN point = null;
		// 消费刷卡器KSN
		ConsumeKSN consume = null;
		int count = 0;
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "配置刷卡器售后关联参数为null," + bean);

		// 根据注解验证参数
		String valid = ValidateParamUtil.validateParam(bean);
		HsAssert.isTrue(StringUtils.isBlank(valid), RespCode.PARAM_ERROR, valid + "," + bean);

		// 查询配置单未配置的售后清单
		details = afterServiceMapper.selectAfterServiceDetailByNoIdNo(bean.getEntCustId(), bean.getAfterOrderNo(),
				bean.getConfNo(), false);
		HsAssert.isTrue(!CollectionUtils.isEmpty(details), BSRespCode.BS_NOT_QUERY_AFTER_DETAIL,
				"未查询客户配置单的售后清单数据," + bean);
		// 验证配置单状态
		HsAssert.isTrue(details.size() >= 1, BSRespCode.BS_TOOL_CONFIG_IS_NOT_WAIT_CONFIG, "配置单不是待配置状态," + bean);

		// 验证设备和配置单是否同仓库
		count = deviceInfoMapper.selectDeviceInfoByNoAndConfNo(bean.getNewDeviceSeqNo(), bean.getConfNo());
		HsAssert.isTrue(count > 0, BSRespCode.BS_DEVICE_CONIFG_NO_SAME_WH, "设备和配置单不在同一仓库," + bean);

		// 验证设备
		device = deviceInfoMapper.selectDeviceInfoBySeqNo(bean.getNewDeviceSeqNo());
		HsAssert.notNull(device, BSRespCode.BS_TOOL_DEVICE_NOT_EXIST, "工具设备不存在," + bean);
		// 验证设备状态
		HsAssert.isTrue(device.getUseStatus().intValue() == UseStatus.NOT_USED.getCode(),
				BSRespCode.BS_DEVICE_IS_NOT_USED, "设备不是未使用状态," + bean);

		// 验证设备是否配置
		dConf = deviceInfoMapper.selectDeviceConfigByCustIdAndNo(device.getDeviceCustId(), bean.getConfNo());
		HsAssert.isTrue(null == dConf, BSRespCode.BS_TOOL_DEVICE_IS_CONFIG, "设备已经配置," + bean);

		try
		{
			// 增加关联
			deviceInfoMapper.insertDeviceConfig(
					new DeviceConfig(device.getDeviceCustId(), bean.getConfNo(), bean.getTerminalNo(), true));
			// 修改售后清单完成
			count = afterServiceMapper.updateAfterServiceDetailConfig(bean.getAfterOrderNo(), bean.getDeviceSeqNo(),
					bean.getNewDeviceSeqNo());

			// 修改设备状态
			if (count > 0)
			{
				count = deviceInfoMapper.updateDeviceUseStatusById(device.getDeviceCustId(), UseStatus.USED.getCode(),
						null, bean.getOperNo());
			}
			// 配置单只有最后一个清单未完成时修改配置单状态
			if (details.size() == 1 && count > 0)
			{
				count = toolConfigMapper.updateToolConfigByConfNo(
						new ToolConfig(bean.getConfNo(), null, ConfStatus.WAIT_SEND.getCode(), null, null));
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:configMcrRelationAfter",
					BSRespCode.BS_AFTER_CONFIG_MCR_FAIL + ":配置刷卡器售后关联失败," + bean, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_AFTER_CONFIG_MCR_FAIL, "配置刷卡器售后关联失败," + bean);

		// 用户中心设备数据
		cardReader = new BsCardReader();
		cardReader.setEntResNo(bean.getEntCustId().substring(0, 11));
		cardReader.setEntCustId(bean.getEntCustId());
		cardReader.setMachineNo(bean.getNewDeviceSeqNo());
		cardReader.setDeviceNO(bean.getTerminalNo().substring(11));
		// 积分刷卡器
		if (device.getCategoryCode().equals(CategoryCode.POINT_MCR.name()))
		{
			// 获取积分刷卡器数据
			point = ksnInfoMapper.selectPointKsnInfoById(device.getDeviceCustId());
			cardReader.setCardReaderType(McrType.POINT.getCode());
			cardReader.setKsnCode1(point.getKsnCode());
		} else if (device.getCategoryCode().equals(CategoryCode.CONSUME_MCR.name()))
		{// 消费刷卡器
			// 获取消费刷卡器数据
			consume = ksnInfoMapper.selectConsumeKsnInfoById(device.getDeviceCustId());
			cardReader.setCardReaderType(McrType.CONSUME.getCode());
			cardReader.setKsnCode1(consume.getKsnCodeY());
			cardReader.setKsnCode2(consume.getKsnCodeE());
			cardReader.setKsnCode3(consume.getKsnCodeS());
			cardReader.setCipherText1(consume.getCiphertextY());
			cardReader.setCipherText2(consume.getCiphertextE());
			cardReader.setCipherText3(consume.getCiphertextS());
			cardReader.setKsnValid1(consume.getVaildY());
			cardReader.setKsnValid2(consume.getVaildE());
			cardReader.setKsnValid3(consume.getVaildS());
		}
		try
		{
			// 调用用户中心同步刷卡器数据
			bsDeviceService.exchangeCardReader(cardReader, bean.getOperNo());
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:configMcrRelationAfter",
					BSRespCode.BS_DUBBO_INVOKE_UC_FAIL.getCode() + ":dubbo调用UC同步刷卡器数据失败," + bean, ex);
			throw new HsException(BSRespCode.BS_DUBBO_INVOKE_UC_FAIL, "dubbo调用UC同步刷卡器数据失败");
		}
	}

	/**
	 * 验证灌秘钥设备售后(POS机)
	 * 
	 * @Description:
	 * @param deviceSeqNo
	 *            设备序列号
	 * @throws HsException
	 */
	@Override
	public void vaildSecretKeyDeviceAfter(String deviceSeqNo) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:vaildSecretKeyDeviceAfter", "params==>" + deviceSeqNo,
				"验证灌秘钥设备售后(POS机、平板)");

		HsAssert.hasText(deviceSeqNo, RespCode.PARAM_ERROR, "验证灌秘钥设备售后参数为NULL," + deviceSeqNo);
		DeviceInfo device = deviceInfoMapper.selectDeviceInfoBySeqNo(deviceSeqNo);

		HsAssert.notNull(device, BSRespCode.BS_TOOL_DEVICE_NOT_EXIST, "工具设备不存在," + deviceSeqNo);

		// 验证设备状态
		HsAssert.isTrue(
				device.getUseStatus().intValue() == UseStatus.NOT_USED.getCode()
						|| device.getUseStatus().intValue() == UseStatus.BEEN_REPAIRED.getCode(),
				BSRespCode.BS_AFTER_DEVICE_IS_NOT_USED_OR_BEEN_REPAIRED, "售后设备不是未使用或已返修,," + deviceSeqNo);
	}

	/**
	 * 配置秘钥设备售后(POS机)
	 * 
	 * @Description:
	 * @param bean
	 *            售后服务配置参数实体
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void configSecretKeyDeviceAfter(AfterServiceConfig bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:configDeviceAfterRelation", "params==>" + bean, "配置设备售后关联");
		// 设备信息
		DeviceInfo device = null;
		// 设备配置详情
		DeviceConfig dConf = null;
		// 售后清单
		List<AfterServiceDetail> details = null;
		int count = 0;
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "配置设备售后关联参数为null," + bean);

		// 根据注解验证参数
		String valid = ValidateParamUtil.validateParam(bean);
		HsAssert.isTrue(StringUtils.isBlank(valid), RespCode.PARAM_ERROR, valid + "," + bean);

		// 查询配置单未配置的售后清单
		details = afterServiceMapper.selectAfterServiceDetailByNoIdNo(bean.getEntCustId(), bean.getAfterOrderNo(),
				bean.getConfNo(), false);
		HsAssert.isTrue(!CollectionUtils.isEmpty(details), BSRespCode.BS_NOT_QUERY_AFTER_DETAIL,
				"未查询客户配置单的售后清单数据," + bean);
		// 验证配置单状态
		HsAssert.isTrue(details.size() >= 1, BSRespCode.BS_TOOL_CONFIG_IS_NOT_WAIT_CONFIG, "配置单状态错误，不是待配置状态," + bean);

		// 验证设备和配置单是否同仓库
		count = deviceInfoMapper.selectDeviceInfoByNoAndConfNo(bean.getNewDeviceSeqNo(), bean.getConfNo());
		HsAssert.isTrue(count > 0, BSRespCode.BS_DEVICE_CONIFG_NO_SAME_WH, "设备和配置单不在同一仓库," + bean);

		// 验证设备
		device = deviceInfoMapper.selectDeviceInfoBySeqNo(bean.getNewDeviceSeqNo());
		HsAssert.notNull(device, BSRespCode.BS_TOOL_DEVICE_NOT_EXIST, "工具设备不存在," + bean);
		// 验证设备状态
		HsAssert.isTrue(
				device.getUseStatus().intValue() == UseStatus.NOT_USED.getCode()
						|| device.getUseStatus().intValue() == UseStatus.BEEN_REPAIRED.getCode(),
				BSRespCode.BS_AFTER_DEVICE_IS_NOT_USED_OR_BEEN_REPAIRED, "售后设备不是未使用或已返修," + bean);
		// 验证设备是否配置
		dConf = deviceInfoMapper.selectDeviceConfigByCustIdAndNo(device.getDeviceCustId(), bean.getConfNo());

		HsAssert.isTrue(null == dConf, BSRespCode.BS_TOOL_DEVICE_IS_CONFIG, "工具设备已经配置," + bean);

		try
		{
			// 增加关联
			deviceInfoMapper.insertDeviceConfig(
					new DeviceConfig(device.getDeviceCustId(), bean.getConfNo(), bean.getTerminalNo(), true));
			// 修改售后清单完成
			count = afterServiceMapper.updateAfterServiceDetailConfig(bean.getAfterOrderNo(), bean.getDeviceSeqNo(),
					bean.getNewDeviceSeqNo());
			if (count > 0)
			{
				// 修改设备状态
				count = deviceInfoMapper.updateDeviceUseStatusById(device.getDeviceCustId(), UseStatus.USED.getCode(),
						null, bean.getOperNo());
			}

			// 配置单只有最后一个清单未完成时修改配置单状态
			if (details.size() == 1 && count > 0)
			{
				count = toolConfigMapper.updateToolConfigByConfNo(
						new ToolConfig(bean.getConfNo(), null, ConfStatus.WAIT_SEND.getCode(), null, null));
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:configDeviceAfterRelation",
					BSRespCode.BS_AFTER_CONFIG_SECRETKEY_DEVICE_FAIL.getCode() + ":售后配置秘钥设备失败," + bean, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_AFTER_CONFIG_SECRETKEY_DEVICE_FAIL, "售后配置秘钥设备失败," + bean);
	}

	/**
	 * 分页查询秘钥配置--售后灌秘钥
	 * 
	 * @Description:
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 */
	@Override
	public PageData<SecretKeyOrderInfoPage> queryAfterSecretKeyConfigByPage(BaseParam bean, Page page)
	{
		if (null == page || StringUtils.isBlank(bean))
		{
			return new PageData<SecretKeyOrderInfoPage>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 设置角色列表
		bean.setRoleId();
		// 查询售后灌秘钥工具列表
		List<SecretKeyOrderInfoPage> keys = afterServiceSelectMapper.selectAfterSecretKeyOrderByListPage(bean);
		if (StringUtils.isNotBlank(keys))
		{
			return new PageData<SecretKeyOrderInfoPage>(page.getCount(), keys);
		}
		return new PageData<SecretKeyOrderInfoPage>(0, null);
	}

	/**
	 * 查询售后秘钥设备配置清单
	 * 
	 * @Description:
	 * @param confNo
	 *            配置单号
	 * @return
	 */
	@Override
	public List<AfterDeviceDetail> queryAfterSecretKeyConfigDetail(String confNo)
	{
		if (StringUtils.isBlank(confNo))
		{
			return null;
		}
		return afterServiceSelectMapper.selectAfterSecretKeyConfigDetail(confNo);
	}

	/**
	 * 分页查询售后工具配送配送单(生成发货单)
	 * 
	 * @Description:
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 */
	@Override
	public PageData<ToolConfigPage> queryToolConfigShippingAfterPage(BaseParam bean, Page page)
	{
		if (null == page)
		{
			return new PageData<ToolConfigPage>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 设置角色列表
		bean.setRoleId();
		// 设置查询日期
		bean.setQueryDate();
		// 查询售后工具配送单列表
		List<ToolConfigPage> confs = afterServiceSelectMapper.selectToolConfigShippingAfterListPage(bean);
		if (StringUtils.isNotBlank(confs))
		{
			return new PageData<ToolConfigPage>(page.getCount(), confs);
		}
		return new PageData<ToolConfigPage>(0, null);
	}

	/**
	 * 查询发货单的数据(页面显示)
	 * 
	 * @Description:
	 * @param confNo
	 *            配置单号
	 * @return
	 * @throws HsException
	 */
	@Override
	public ShippingData queryAfterShipingData(String[] confNo) throws HsException
	{
		// 互生号
		List<String> resNo = null;
		int confCount = 0;
		// 收货信息
		List<DeliverInfo> delivers = null;
		// 收货信息
		DeliverInfo deliver = null;
		// 企业名称
		String entName = "";
		// 企业信息
		BsEntAllInfo entInfo = null;

		HsAssert.isTrue(null != confNo && confNo.length > 0, RespCode.PARAM_ERROR,
				"查询发货单的数据参数为NULL," + Arrays.toString(confNo));

		// 验证配置单的类型是否一致
		confCount = commonMapper.vaildToolConfigType(confNo, ConfType.AFTER_CONFIG.getCode());
		HsAssert.isTrue(confNo.length == confCount, BSRespCode.BS_TOOL_CONFIG_TYPE_NOT_SAME,
				"工具配置单类型不相同," + Arrays.toString(confNo));

		// 配置单
		List<ToolConfig> confs = toolConfigMapper.selectToolConfigByIds(confNo, ConfStatus.WAIT_SEND.getCode());
		// 验证配置单状态
		HsAssert.isTrue(confs != null ? confs.size() == confNo.length : false,
				BSRespCode.BS_TOOL_CONFIG_IS_NOT_WAIT_SEND, "配置单号列表有不是待发货的配置单," + Arrays.toString(confNo));

		// 验证是否是同一个企业的配置单
		resNo = commonMapper.vaildShippingIsSameEnt(confNo);
		HsAssert.isTrue(null != resNo && resNo.size() == 1, BSRespCode.BS_IS_NOT_SAME_ENT,
				"不是同一企业," + Arrays.toString(confNo));

		// 用户中心查询收货信息
		entInfo = commonService.getEntAllInfoToUc(resNo.get(0), null);

		// 验证调用企业信息
		HsAssert.notNull(entInfo, BSRespCode.BS_NOT_QUERY_ENT_INFO, "未查询到企业信息," + Arrays.toString(confNo));
		HsAssert.notNull(entInfo.getMainInfo(), BSRespCode.BS_NOT_QUERY_ENT_INFO,
				"未查询到企业信息," + Arrays.toString(confNo));
		HsAssert.notNull(entInfo.getBaseInfo(), BSRespCode.BS_NOT_QUERY_ENT_INFO,
				"未查询到企业信息," + Arrays.toString(confNo));

		// 企业名称
		entName = StringUtils.isNotBlank(entInfo.getBaseInfo().getEntName()) ? entInfo.getBaseInfo().getEntName()
				: entInfo.getBaseInfo().getEntNameEn();

		delivers = new ArrayList<DeliverInfo>();
		// 联系人地址
		deliver = new DeliverInfo();
		deliver.setLinkman(entInfo.getMainInfo().getContactPerson());// 联系人
		deliver.setMobile(entInfo.getMainInfo().getContactPhone());// 联系人电话
		String contactAddr = entInfo.getBaseInfo().getContactAddr();
		deliver.setFullAddr(StringUtils.isNotBlank(contactAddr) ? contactAddr : "" + "(" + entName + ")");// 联系人地址
		deliver.setZipCode(entInfo.getBaseInfo().getPostCode());// 邮编
		deliver.setAddrType("contact");// 地址类型
		delivers.add(deliver);
		// 企业地址
		deliver = new DeliverInfo();
		deliver.setLinkman(entInfo.getMainInfo().getContactPerson());// 联系人
		deliver.setMobile(entInfo.getMainInfo().getContactPhone());// 联系人电话
		deliver.setFullAddr(entInfo.getMainInfo().getEntRegAddr() + "(" + entName + ")");// 企业地址
		deliver.setZipCode(entInfo.getBaseInfo().getPostCode());// 邮编
		deliver.setAddrType("ent");// 地址类型
		delivers.add(deliver);

		ShippingData bean = new ShippingData();
		// 售后清单
		bean.setDetails(afterServiceMapper.selectAfterServiceDetailByConfNo(confNo));
		// 收货信息
		bean.setDelivers(delivers);
		// 配送方式
		bean.setMothods(shippingMethodMapper.selectShippingMethodAll());
		return bean;
	}

	/**
	 * 添加售后服务发货单
	 * 
	 * @Description:
	 * @param bean
	 *            发货单参数实体
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void addToolShippingAfter(Shipping bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:addToolShippingAfter", "params==>" + bean, "添加售后服务发货单");
		int count = 0;
		// 出库单
		List<Out> outs = null;
		// 出库清单
		List<OutDetail> outDetails = null;
		// 设备客户号
		List<String> deviceCustIds = null;
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "添加发货单参数为NULL," + bean);

		// 根据注解验证参数
		String valid = ValidateParamUtil.validateParam(bean);
		HsAssert.isTrue(StringUtils.isBlank(valid), RespCode.PARAM_ERROR, valid + "," + bean);

		// 配置单
		List<ToolConfig> confs = toolConfigMapper.selectToolConfigByIds(bean.getConfNos(),
				ConfStatus.WAIT_SEND.getCode());
		// 验证配置单状态
		HsAssert.isTrue(confs != null ? confs.size() == bean.getConfNos().length : false,
				BSRespCode.BS_TOOL_CONFIG_IS_NOT_WAIT_SEND, "配置单号列表有不是待发货的配置单," + bean);

		// 发货单编号、类型、发货状态
		String shippingId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
		bean.setShippingId(shippingId);
		bean.setShippingType(ShippingType.AFTER_TOOL.getCode());
		bean.setDeliveryStatus(DeliveryStatus.SENDED.getCode());

		// 出库流水、清单、设备客户号列表
		outs = new ArrayList<Out>();
		outDetails = new ArrayList<OutDetail>();
		deviceCustIds = new ArrayList<String>();
		for (String confNo : bean.getConfNos())
		{
			// 设备信息
			List<DeviceInfo> infos = deviceInfoMapper.selectDeviceConfigByAfterBarter(confNo);
			if (StringUtils.isNotBlank(infos))
			{
				// 出库流水
				String batchNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
				outs.add(new Out(batchNo, infos.get(0).getCategoryCode(), infos.get(0).getProductId(),
						infos.get(0).getWhId(), ToolOut.AFTER.getCode(), null, infos.size(), null, bean.getConsignor(),
						infos.get(0).getCategoryCode() + "出库"));
				for (DeviceInfo info : infos)
				{
					// 出库清单
					outDetails.add(new OutDetail(batchNo, info.getDeviceCustId()));
					// 设备客户号
					deviceCustIds.add(info.getDeviceCustId());
				}
			}
		}
		try
		{
			// 插入发货单
			shippingMapper.insertShipping(bean);
			// 插入出库单
			if (outs.size() > 0)
			{
				outMapper.batchInsertOut(outs);

			}
			// 插入出库清单
			if (outDetails.size() > 0)
			{
				outMapper.batchInsertOutDetail(outDetails);
			}
			if (deviceCustIds.size() > 0)
			{
				// 批量修改设备已使用出库
				deviceInfoMapper.batchUpdateDeviceOutStock(deviceCustIds, bean.getConsignor());
			}
			// 批量修改配置单的状态
			count = toolConfigMapper.updateToolConfigByAfterShipping(bean.getConfNos(), shippingId);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:addToolShipping",
					BSRespCode.BS_ADD_AFTER_SHIPPING_FAIL.getCode() + ":添加售后服务发货单失败," + bean, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_ADD_AFTER_SHIPPING_FAIL, "添加售后服务发货单失败," + bean);
	}

	/**
	 * 分页查询售后发货单(打印发货单)
	 * 
	 * @Description:
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 */
	@Override
	public PageData<ToolShippingPage> queryShippingAfterPage(BaseParam bean, Page page)
	{
		if (null == page)
		{
			return new PageData<ToolShippingPage>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 分页查询售后发货单列表
		List<ToolShippingPage> shipps = shippingMapper.selectAfterShippingListPage(bean);
		if (StringUtils.isNotBlank(shipps))
		{
			return new PageData<ToolShippingPage>(page.getCount(), shipps);
		}
		return new PageData<ToolShippingPage>(0, null);
	}

	/**
	 * 根据id查询售后发货单
	 * 
	 * @Description:
	 * @param shippingId
	 *            发货单id
	 * @return
	 */
	@Override
	public Shipping queryShippingAfterById(String shippingId)
	{
		if (StringUtils.isBlank(shippingId))
		{
			return null;
		}
		// 查发货单
		Shipping bean = shippingMapper.selectShippingById(shippingId);
		if (null != bean && bean.getShippingType().intValue() == ShippingType.AFTER_TOOL.getCode())
		{
			// 查询售后服务清单
			bean.setDetails(afterServiceMapper.selectAfterServiceDetailByShippingId(shippingId));
		}
		return bean;
	}
}
