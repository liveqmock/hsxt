/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tool.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.api.tool.IBSToolMarkService;
import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.tool.*;
import com.gy.hsxt.bs.bean.tool.resultbean.*;
import com.gy.hsxt.bs.common.ObjectFactory;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.StringUtil;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.DeviceType;
import com.gy.hsxt.bs.common.enumtype.McrType;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.bs.common.enumtype.tool.*;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.common.util.ValidateParamUtil;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.tool.bean.BatchOpenCard;
import com.gy.hsxt.bs.tool.bean.DeviceConfig;
import com.gy.hsxt.bs.tool.bean.MakingCardInfo;
import com.gy.hsxt.bs.tool.mapper.*;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.common.utils.RandomCodeUtils;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderService;
import com.gy.hsxt.uc.bs.api.device.IUCBsDeviceService;
import com.gy.hsxt.uc.bs.bean.consumer.BsHsCard;
import com.gy.hsxt.uc.bs.bean.device.BsCardReader;
import com.gy.hsxt.uc.bs.bean.ent.BsEntMainInfo;

/**
 * 工具制作API接口实现类
 * 
 * @Package: com.hsxt.bs.btool.service
 * @ClassName: ToolMarkservice
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月30日 下午6:19:43
 * @company: gyist
 * @version V3.0.0
 */
@Service("toolMarkService")
public class ToolMarkService implements IBSToolMarkService {

	/** 业务服务私有配置参数 **/
	@Resource
	private BsConfig bsConfig;

	/** 刷卡器数据Mapper **/
	@Resource
	private KsnInfoMapper ksnInfoMapper;

	/** 工具配置单Mapper **/
	@Resource
	private ToolConfigMapper toolConfigMapper;

	/** 互生卡信息Mapper **/
	@Resource
	private CardInfoMapper cardInfoMapper;

	/** 积分卡出入库Mapper **/
	@Resource
	private CardInOutMapper cardInOutMapper;

	/** 设备信息Mapper **/
	@Resource
	private DeviceInfoMapper deviceInfoMapper;

	/** 公共Mapper **/
	@Resource
	private CommonMapper commonMapper;

	/** 互生卡样Mapper **/
	@Resource
	private CardStyleMapper cardStyleMapper;

	/** 供应商Mapper **/
	@Resource
	private SupplierMapper supplierMapper;

	/** 用户中心设备Service **/
	@Resource
	private IUCBsDeviceService bsDeviceService;

	/** 用户中心互生卡开卡Service **/
	@Resource
	private IUCBsCardHolderService bsCardHolderService;

	/** BS公共Service **/
	@Resource
	private ICommonService commonService;

	/**
	 * 分页查询刷卡器KSN生成记录
	 * 
	 * @Description:
	 * @param storeStatus
	 *            库存状态
	 * @param mcrType
	 *            刷卡器类型
	 * @param page
	 *            分页参数实体
	 * @return
	 */
	@Override
	public PageData<McrKsnRecord> queryMcrKsnRecordPage(Integer storeStatus, Integer mcrType, Page page)
	{
		if (null == page)
		{
			return new PageData<McrKsnRecord>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 查询刷卡器生成记录列表
		List<McrKsnRecord> records = ksnInfoMapper.selectMcrKsnRecordListPage(storeStatus, mcrType);
		if (StringUtils.isNotBlank(records))
		{
			return new PageData<McrKsnRecord>(page.getCount(), records);
		}
		return new PageData<McrKsnRecord>(0, null);
	}

	/**
	 * 生成积分刷卡器KSN
	 * 
	 * @Description:
	 * @param number
	 *            数量
	 * @param operNo
	 *            操作员编号
	 * @return
	 * @throws HsException
	 */
	@Override
	@Transactional
	public String createPointKSNInfo(Integer number, String operNo) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:createPointKSNInfo",
				"params==>number:" + number + "operNo:" + operNo, "生成积分刷卡器KSN");
		// 序列号
		int seqNo = 0;
		String deviceSeqNo = "";
		// 下一个序列号
		String nextSeqNo = "";
		// 批次号
		String batchNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
		HsAssert.isTrue(null != number && number.intValue() > 0, RespCode.PARAM_ERROR, "生成积分刷卡器数据参数错误");
		// 获取最大序列号
		deviceSeqNo = ksnInfoMapper.selectMaxPointDeviceSeqNo();
		// 序列号为null从1开始，不为null最大值加1
		if (StringUtils.isNotBlank(deviceSeqNo))
		{
			seqNo = Integer.parseInt(deviceSeqNo.substring(2, deviceSeqNo.length()));
		}
		// 根据最大序列号，生成刷卡器数据
		List<PointKSN> points = new ArrayList<PointKSN>();
		for (int i = 1; i <= number.intValue(); i++)
		{
			// 获取序列号
			nextSeqNo = "ec" + StringUtil.frontCompWithZore((seqNo + i),
					StringUtils.isNotBlank(deviceSeqNo) ? (deviceSeqNo.length() - 2) : 12);
			// 积分刷卡器KSN
			PointKSN point = new PointKSN(
					StringUtil.createDeviceCustId(DeviceType.MCR.getCode(), bsConfig.getAppNode()), nextSeqNo, batchNo,
					StringUtil.createKsnCode(12));
			points.add(point);
		}
		// 刷卡器KSN记录
		McrKsnRecord record = new McrKsnRecord(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()), batchNo,
				McrType.POINT.getCode(), number, StoreStatus.WAIT_ENTER.getCode());
		record.setCreatedBy(operNo);
		try
		{
			// 插入积分刷卡器数据、刷卡器记录数据
			ksnInfoMapper.insertPointKsnInfo(points);
			ksnInfoMapper.insertMcrKsnRecord(record);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:createPointKSNInfo",
					BSRespCode.BS_CREATE_POINT_KSN_FAIL.getCode() + ":生成积分刷卡器KSN失败", ex);
			throw new HsException(BSRespCode.BS_CREATE_POINT_KSN_FAIL, "生成积分刷卡器KSN失败");
		}
		return batchNo;
	}

	/**
	 * 导出积分刷卡器数据
	 * 
	 * @Description:
	 * @param bean
	 *            导出记录实体
	 * @return
	 */
	@Override
	public List<PointKSN> exportPointKSNInfo(KsnExportRecord bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:exportPointKSNInfo", "params==>" + bean, "导出积分刷卡器数据");
		int count = 0;
		// 验证数据
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "导出积分刷卡器参数为null," + bean);
		HsAssert.hasText(bean.getBahctNo(), RespCode.PARAM_ERROR, "导出积分刷卡器批次号为null," + bean);
		HsAssert.hasText(bean.getCreatedBy(), RespCode.PARAM_ERROR, "导出积分刷卡器操作员编号为null," + bean);
		HsAssert.hasText(bean.getCreatedName(), RespCode.PARAM_ERROR, "导出积分刷卡器操作员名称为null," + bean);
		// 插入导出记录
		bean.setExportId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
		try
		{
			count = ksnInfoMapper.insertPointKsnExportRecord(bean);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:exportPointKSNInfo",
					BSRespCode.BS_EXPORT_CONSUME_KSN_FAIL.getCode() + ":导出积分刷卡器KSN失败," + bean, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_EXPORT_POINT_KSN_FAIL, "导出积分刷卡器失败");

		return ksnInfoMapper.selectPointKsnInfoByNo(bean.getBahctNo());
	}

	/**
	 * 查询积分ksn信息
	 * 
	 * @Description:
	 * @param batchNo
	 *            批次号
	 * @return
	 */
	@Override
	public List<PointKSN> queryPointKSNInfo(String batchNo)
	{
		if (StringUtils.isBlank(batchNo))
		{
			return null;
		}
		return ksnInfoMapper.selectPointKsnInfoByNo(batchNo);
	}

	@Override
	public void createConsumeKSNInfo(Integer number, String operNo) throws HsException
	{
		// TODO Auto-generated method stub

	}

	/**
	 * 导入消费刷卡器KSN
	 * 
	 * @Description:
	 * @param beans
	 *            消费刷卡器参数实体
	 * @param operNo
	 *            操作员编号
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void importConsumeKSNInfo(List<ConsumeKSN> beans, String operNo) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:importConsumeKSNInfo",
				"params==>beans:" + beans + "operNo:" + operNo, "导入消费刷卡器KSN");
		// 验证数据
		HsAssert.isTrue(!CollectionUtils.isEmpty(beans), RespCode.PARAM_ERROR, "导入消费刷卡器KSN参数为NULL,");
		// 批次号
		String batchNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
		// 获取去掉重复序列号列表
		List<Object> seq = ObjectFactory.removeDuplicate(ObjectFactory.getListEntityAttr(beans, "deviceSeqNo"));
		HsAssert.isTrue(seq.size() == beans.size(), BSRespCode.BS_DEVICE_NO_LIST_HAS_REPEAT, "设备序列号列表中有重复的数据");

		// 验证该批次的序列号是否已经导入
		List<String> seqNo = ksnInfoMapper.selectConsumeKsnBySeqNo(StringUtil.assembleQueryInIf(seq));
		HsAssert.isTrue((null == seqNo || seqNo.size() <= 0), BSRespCode.BS_DEVICE_SEQ_NO_IS_IMPORT,
				"验证该批次的序列号有已经导入," + JSONObject.toJSONString(seqNo));
		// 生成消费刷卡器数据
		for (ConsumeKSN ksn : beans)
		{
			if (StringUtils.isNotBlank(ksn.getKsnCodeY()))
			{
				// 币种
				// String currency = ksn.getKsnCodeY().substring(0, 3);
				// 刷卡器代码
				String ksnCode = ksn.getKsnCodeY().substring(3);

				ksn.setDeviceCustId(StringUtil.createDeviceCustId(DeviceType.MCR.getCode(), bsConfig.getAppNode()));
				ksn.setBatchNo(batchNo);
				// 刷卡器序列号
				// ksn.setDeviceSeqNo(currency + ksn.getDeviceSeqNo() +
				// ksnCode);
				ksn.setConsumeCode(ksnCode);
			}
		}
		// 刷卡器KSN记录
		McrKsnRecord record = new McrKsnRecord(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()), batchNo,
				McrType.CONSUME.getCode(), beans.size(), StoreStatus.WAIT_ENTER.getCode());
		record.setCreatedBy(operNo);
		try
		{
			// 插入积分刷卡器数据、刷卡器记录数据
			ksnInfoMapper.insertConsumeKsnInfo(beans);
			ksnInfoMapper.insertMcrKsnRecord(record);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:importConsumeKSNInfo",
					BSRespCode.BS_IMPORT_CONSUME_KSN_FAIL.getCode() + ":导入消费刷卡器KSN失败," + beans, ex);
			throw new HsException(BSRespCode.BS_IMPORT_CONSUME_KSN_FAIL, "导入消费刷卡器KSN失败");
		}
	}

	/**
	 * 导出消费刷卡器数据
	 * 
	 * @Description:
	 * @param bean
	 *            导出记录实体
	 * @return
	 */
	@Override
	public List<ConsumeKSN> exportConsumeKSNInfo(KsnExportRecord bean)
	{
		BizLog.biz(this.getClass().getName(), "function:exportConsumeKSNInfo", "params==>" + bean, "导出消费刷卡器数据");
		int count = 0;
		// 验证数据
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "导出消费刷卡器参数为null," + bean);
		HsAssert.hasText(bean.getBahctNo(), RespCode.PARAM_ERROR, "导出消费刷卡器批次号为null," + bean);
		HsAssert.hasText(bean.getCreatedBy(), RespCode.PARAM_ERROR, "导出消费刷卡器操作员编号为null," + bean);
		HsAssert.hasText(bean.getCreatedName(), RespCode.PARAM_ERROR, "导出消费刷卡器操作员名称为null," + bean);
		// 插入导出记录
		bean.setExportId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
		try
		{
			count = ksnInfoMapper.insertPointKsnExportRecord(bean);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:exportConsumeKSNInfo",
					BSRespCode.BS_EXPORT_CONSUME_KSN_FAIL.getCode() + ":导出消费刷卡器KSN失败," + bean, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_EXPORT_CONSUME_KSN_FAIL, "导出消费刷卡器失败");
		return ksnInfoMapper.selectConsumeKsnByNo(bean.getBahctNo());
	}

	/**
	 * 查询消费ksn信息
	 * 
	 * @Description:
	 * @param batchNo
	 *            批次号
	 * @return
	 */
	@Override
	public List<ConsumeKSN> queryConsumeKSNInfo(String batchNo)
	{
		if (StringUtils.isBlank(batchNo))
		{
			return null;
		}
		return ksnInfoMapper.selectConsumeKsnByNo(batchNo);
	}

	/**
	 * 查询刷卡器导出记录
	 * 
	 * @Description:
	 * @param batchNo
	 *            批次号
	 * @return
	 */
	@Override
	public List<KsnExportRecord> queryKsnExportRecord(String batchNo)
	{
		if (StringUtils.isBlank(batchNo))
		{
			return null;
		}
		return ksnInfoMapper.selectKsnExportRecord(batchNo);
	}

	/**
	 * 分页查询刷卡工具制作配置单(申报新增)
	 * 
	 * @Description:
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 */
	@Override
	public PageData<ToolConfigPage> queryToolConfigMarkPage(BaseParam bean, Page page)
	{
		if (null == page)
		{
			return new PageData<ToolConfigPage>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 设置日期查询条件
		bean.setQueryDate();
		// 设置角色列表
		bean.setRoleId();
		// 查询刷卡工具配置单列表
		List<ToolConfigPage> confs = toolConfigMapper.selectServiceToolMarkListPage(bean);
		if (StringUtils.isNotBlank(confs))
		{
			return new PageData<ToolConfigPage>(page.getCount(), confs);
		}
		return new PageData<ToolConfigPage>(0, null);
	}

	/**
	 * 查询设备关联详情
	 * 
	 * @Description:
	 * @param confNo
	 *            配置单号
	 * @return
	 */
	@Override
	public List<ToolRelationDetail> queryDeviceRelevanceDetail(String confNo)
	{
		if (StringUtils.isBlank(confNo))
		{
			return null;
		}
		return deviceInfoMapper.selectDeviceRelevanceDetail(confNo);
	}

	/**
	 * 分页查询刷卡工具配置单(申报新增)
	 *
	 * @Description:
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 */
	@Override
	public PageData<ToolConfigPage> queryServiceToolConfigPage(BaseParam bean, Page page)
	{
		if (null == page)
		{
			return new PageData<ToolConfigPage>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 设置日期查询条件
		bean.setQueryDate();
		// 查询刷卡工具配置单列表
		List<ToolConfigPage> confs = toolConfigMapper.selectServiceToolConfigListPage(bean);
		if (StringUtils.isNotBlank(confs))
		{
			return new PageData<ToolConfigPage>(page.getCount(), confs);
		}
		return new PageData<ToolConfigPage>(0, null);
	}

	/**
	 * 分页查询秘钥配置(申报新增)--灌秘钥
	 * 
	 * @Description:
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 */
	@Override
	public PageData<SecretKeyOrderInfoPage> querySecretKeyConfigByPage(BaseParam bean, Page page)
	{
		if (null == page || StringUtils.isBlank(bean))
		{
			return new PageData<SecretKeyOrderInfoPage>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 设置角色列表
		bean.setRoleId();
		// 查询灌秘钥配置单列表
		List<SecretKeyOrderInfoPage> posInfos = toolConfigMapper.selectSecretKeyOrderListPage(bean);
		if (StringUtils.isNotBlank(posInfos))
		{
			return new PageData<SecretKeyOrderInfoPage>(page.getCount(), posInfos);
		}
		return new PageData<SecretKeyOrderInfoPage>(0, null);
	}

	/**
	 * 验证设备信息合法(POS机、平板)
	 * 
	 * @Description:
	 * @param deviceSeqNo
	 *            设备序列号
	 */
	@Override
	public void vaildDeviceInfoLawful(String deviceSeqNo) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:vaildDeviceInfoLawful", "params==>deviceSeqNo:" + deviceSeqNo,
				"验证设备信息合法(POS机、平板)");
		DeviceInfo device = deviceInfoMapper.selectDeviceInfoBySeqNo(deviceSeqNo);
		HsAssert.notNull(device, BSRespCode.BS_TOOL_DEVICE_NOT_EXIST, "工具设备不存在," + deviceSeqNo);

		HsAssert.isTrue(device.getUseStatus() == UseStatus.NOT_USED.getCode(), BSRespCode.BS_DEVICE_IS_NOT_USED,
				"工具设备不是未使用的," + deviceSeqNo);
	}

	/**
	 * 查询设备的终端编号列表(POS机、平板)
	 * 
	 * @Description:
	 * @param entCustId
	 *            企业客户号
	 * @param confNo
	 *            配置单号
	 * @param categoryCode
	 *            工具类别代码
	 * @return
	 */
	@Override
	public DeviceTerminalNo queryConifgDeviceTerminalNo(String entCustId, String confNo, String categoryCode)
	{

		if (StringUtils.isBlank(entCustId) || StringUtils.isBlank(confNo) || StringUtils.isBlank(categoryCode))
		{
			return null;
		}
		// 返回终端编号详情实体
		DeviceTerminalNo bean = null;
		// 待使用的终端编号
		List<String> terminalNos = null;
		// 关联上的终端
		List<String> confTerminalNos = null;
		// 终端编号
		String terminalNo = "";
		// 最大终端编号
		String maxTerminalNo = "";
		// 互生号
		String hsResNo = entCustId.substring(0, 11);
		int count = 0;
		// 这个客户号的配置单是否存在
		ToolConfig config = toolConfigMapper.selectToolConfigById(confNo);
		if (null == config || !config.getHsCustId().equals(entCustId))
		{
			return bean;
		}
		// 不是POS机、平板
		if (!CategoryCode.isSecretKeyCode(categoryCode))
		{
			return bean;
		}
		bean = new DeviceTerminalNo();
		terminalNos = new ArrayList<String>();
		bean.setConfNo(confNo);
		bean.setEntCustId(entCustId);

		// 已经配置的终端编号
		confTerminalNos = deviceInfoMapper.selectConfigTerminalNo(confNo);
		if (StringUtils.isNotBlank(confTerminalNos))
		{
			bean.setConfTerminalNos(confTerminalNos);
		}

		// 生成未配置的终端编号
		maxTerminalNo = deviceInfoMapper.selectMaxTerminalNo(entCustId, new String[]
		{ categoryCode });
		// 如果配置单没有全部配置单完成
		count = null != confTerminalNos ? config.getQuantity().intValue() - confTerminalNos.size()
				: config.getQuantity().intValue() - 0;
		// 根据最大的终端编号和配置单未配置完成的数据，生成将可以配置的终端编号
		maxTerminalNo = StringUtils.isNotBlank(maxTerminalNo) ? maxTerminalNo : hsResNo + "0000";
		if (count > 0)
		{
			int lenght = maxTerminalNo.length();
			for (int i = 1; i <= count; i++)
			{
				// 生成终端编号
				terminalNo = hsResNo + StringUtil
						.frontCompWithZore((Integer.parseInt(maxTerminalNo.substring(lenght - 4, lenght))) + i, 4);
				terminalNos.add(terminalNo);
			}
			bean.setTerminalNos(terminalNos);
		}
		return bean;
	}

	/**
	 * 配置POS机关联
	 * 
	 * @Description:
	 * @param entCustId
	 *            企业互生号
	 * @param confNo
	 *            配置单号
	 * @param deviceSeqNo
	 *            设备序列号
	 * @return
	 * @throws HsException
	 */
	@Override
	@Transactional
	public String configToolPos(String entCustId, String confNo, String deviceSeqNo) throws HsException
	{
		String inParam = "params==>entCustId:" + entCustId + "confNo:" + confNo + "deviceSeqNo:" + deviceSeqNo;
		BizLog.biz(this.getClass().getName(), "function:configToolPos", inParam, "配置POS机关联");
		int num = 1;
		// 最大终端编号
		String maxTerminalNo = "";
		// 终端编号
		String terminalNo = "";
		// 设备信息
		DeviceInfo device = null;
		// 设备配置详情
		DeviceConfig dConf = null;
		// 验证参数
		HsAssert.hasText(entCustId, RespCode.PARAM_ERROR, "配置POS机客户号为null," + inParam);
		HsAssert.hasText(confNo, RespCode.PARAM_ERROR, "配置POS机配置单号为null," + inParam);
		HsAssert.hasText(deviceSeqNo, RespCode.PARAM_ERROR, "配置POS机设备序列号为null," + inParam);

		// 验证配置单是否属于企业
		List<SecretKeyOrderInfoPage> posInfos = toolConfigMapper.selectSecretKeyOrderByIf(entCustId, confNo);
		HsAssert.isTrue(!CollectionUtils.isEmpty(posInfos), BSRespCode.BS_NOT_QUERY_POS_INFO,
				"根据配置单和客户号未查询到POS机配置数据," + inParam);
		// 验证配置单状态
		HsAssert.isTrue(posInfos.get(0).getStatus() == ConfStatus.WAIT_CONFIG.getCode(),
				BSRespCode.BS_TOOL_CONFIG_IS_NOT_WAIT_CONFIG, "配置单不是待配置状态," + inParam);

		// 验证设备和配置单是否同仓库
		int count = deviceInfoMapper.selectDeviceInfoByNoAndConfNo(deviceSeqNo, confNo);
		HsAssert.isTrue(count > 0, BSRespCode.BS_DEVICE_CONIFG_NO_SAME_WH, "设备和配置单不在同一仓库," + inParam);

		// 验证设备是否存在、是否未使用
		device = deviceInfoMapper.selectDeviceInfoBySeqNo(deviceSeqNo);
		HsAssert.notNull(device, BSRespCode.BS_TOOL_DEVICE_NOT_EXIST, "工具设备不存在," + inParam);
		HsAssert.isTrue(device.getUseStatus() == UseStatus.NOT_USED.getCode(), BSRespCode.BS_DEVICE_IS_NOT_USED,
				"工具设备不是未使用的," + inParam);

		// 设备的关联关系未使用前，可以重新灌秘钥
		dConf = deviceInfoMapper.selectDeviceConfigByCustIdAndNo(device.getDeviceCustId(), null);
		if (null != dConf)
		{
			// 设备已经关联了其他的配置单
			if (!dConf.getConfNo().equals(confNo))
			{
				throw new HsException(BSRespCode.BS_DEVICE_RELEVANCE_OTHER_CONFIG, "设备已关联其他配置单," + inParam);
			}
			// 如果配置的关联还是为使用时，直接返回终端编号再次灌秘钥
			if (!dConf.getIsUse())
			{
				return dConf.getTerminalNo();
			}
			throw new HsException(BSRespCode.BS_TOOL_DEVICE_IS_CONFIG, "工具设备已经配置," + inParam);
		}

		terminalNo = entCustId.substring(0, 11);
		// 或者企业的最大终端编号
		maxTerminalNo = deviceInfoMapper.selectMaxTerminalNo(entCustId, new String[]
		{ CategoryCode.P_POS.name() });
		// 根据 最大终端编号生成关联的终端编号(最大终端编号为null从1开始)
		if (StringUtils.isBlank(maxTerminalNo))
		{
			terminalNo = terminalNo + StringUtil.frontCompWithZore(num, 4);
		} else
		{
			int lenght = maxTerminalNo.length();
			terminalNo = terminalNo + StringUtil
					.frontCompWithZore((Integer.parseInt(maxTerminalNo.substring(lenght - 4, lenght))) + num, 4);
		}
		try
		{
			// 增加设备关联
			deviceInfoMapper.insertDeviceConfig(new DeviceConfig(device.getDeviceCustId(), confNo, terminalNo, false));
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:configToolPos",
					BSRespCode.BS_POS_CONFIG_FAIL.getCode() + ":POS机配置关联失败," + inParam, ex);
			throw new HsException(BSRespCode.BS_POS_CONFIG_FAIL, "POS机配置关联失败," + inParam);
		}
		return terminalNo;
	}

	/**
	 * 配置设备关联已使用
	 * 
	 * @Description:
	 * @param entCustId
	 *            企业客户号
	 * @param confNo
	 *            配置单号
	 * @param terminalNo
	 *            终端编号
	 * @param operNo
	 *            操作员编号
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void configToolDeviceIsUsed(String entCustId, String confNo, String terminalNo, String operNo)
			throws HsException
	{
		String inParam = "params==>entCustId:" + entCustId + "confNo:" + confNo + "terminalNo:" + terminalNo;
		BizLog.biz(this.getClass().getName(), "function:configToolDeviceIsUsed", inParam, "配置设备关联已使用");
		int count = 0;
		// 设备配置详情
		DeviceConfig dConf = null;
		// 配置单的配置单设备详情
		SecretKeyOrderInfoPage device = null;
		// 验证参数 客户号、配置单号、终端编号
		HsAssert.hasText(entCustId, RespCode.PARAM_ERROR, "配置设备关联使用客户号为null," + inParam);
		HsAssert.hasText(confNo, RespCode.PARAM_ERROR, "配置设备关联使用配置单号为null," + inParam);
		HsAssert.hasText(terminalNo, RespCode.PARAM_ERROR, "配置设备关联使用终端编号为null," + inParam);

		// 验证配置单是否属于企业
		List<SecretKeyOrderInfoPage> secretKeys = toolConfigMapper.selectSecretKeyOrderByIf(entCustId, confNo);
		HsAssert.isTrue(!CollectionUtils.isEmpty(secretKeys), BSRespCode.BS_NOT_QUERY_POS_OR_TABLET_INFO,
				"根据配置单和客户号未查询到POS机数据," + inParam);

		// 验证设备关联关系
		dConf = deviceInfoMapper.selectDeviceConfigByCongNoAndNo(confNo, terminalNo);
		HsAssert.notNull(dConf, BSRespCode.BS_NO_QUERY_DEVICE_CONFIG, "未查询到设备关联," + inParam);
		HsAssert.isTrue(!dConf.getIsUse().booleanValue(), BSRespCode.BS_TOOL_DEVICE_CONFIG_IS_USE,
				"工具设备配置关联已使用," + inParam);

		device = secretKeys.get(0);
		try
		{
			// 修改设备关联已使用
			count = deviceInfoMapper.updateDeviceConfigIsUse(confNo, terminalNo);
			if (count > 0)
			{
				// 修改设备使用状态
				count = deviceInfoMapper.updateDeviceUseStatusById(dConf.getDeviceCustId(), UseStatus.USED.getCode(),
						null, operNo);
			}
			// 如果是最后一个就修改配置单的状态
			if (device.getConfingNum().intValue() == 1 && count > 0)
			{
				// 修改配置单状态
				count = toolConfigMapper.updateToolConfigByConfNo(
						new ToolConfig(confNo, null, ConfStatus.WAIT_SEND.getCode(), null, null));
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:configToolDeviceIsUsed",
					BSRespCode.BS_DEVICE_CONFIG_IS_USE_FAIL.getCode() + ":配置设备关系使用失败," + inParam, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_DEVICE_CONFIG_IS_USE_FAIL, "配置设备关系使用失败," + inParam);
	}

	/**
	 * 配置刷卡器关联
	 * 
	 * @Description:
	 * @param entCustId
	 *            企业客户号
	 * @param confNo
	 *            配置单号
	 * @param deviceSeqNo
	 *            设备序列号
	 * @param operNo
	 *            操作员编号
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void configKsnRelation(String entCustId, String confNo, String deviceSeqNo, String operNo) throws HsException
	{
		String inParam = "params==>entCustId:" + entCustId + "confNo:" + confNo + "deviceSeqNo:" + deviceSeqNo
				+ "operNo:" + operNo;
		BizLog.biz(this.getClass().getName(), "function:configKsnRelation", inParam, "配置刷卡器关联");
		int count = 0;
		// 默认起始终端编号
		int num = 1;
		// 最大终端编号
		String maxTerminalNo = "";
		// 终端编号
		String terminalNo = "";
		// 互生号
		String entResNo = "";
		// 设备信息
		DeviceInfo device = null;
		// 设备配置详情
		DeviceConfig dConf = null;
		// 配置单的配置单设备详情
		SecretKeyOrderInfoPage mcr = null;
		// 用户中心设备对象集合
		List<BsCardReader> cardReaders = null;
		BsCardReader cardReader = null;
		// 积分刷卡器KSN
		PointKSN point = null;
		// 消费刷卡器KSN
		ConsumeKSN consume = null;
		// 验证参数 客户号、配置单号、设备序列号
		HsAssert.hasText(entCustId, RespCode.PARAM_ERROR, "配置刷卡器关联客户号为null," + inParam);
		HsAssert.hasText(confNo, RespCode.PARAM_ERROR, "配置刷卡器关联配置单号为null," + inParam);
		HsAssert.hasText(deviceSeqNo, RespCode.PARAM_ERROR, "配置刷卡器关联设备序列号为null," + inParam);

		// 验证配置单是否属于企业
		List<SecretKeyOrderInfoPage> mrcInfos = toolConfigMapper.selectMcrOrderByIf(entCustId, confNo);
		HsAssert.isTrue(!CollectionUtils.isEmpty(mrcInfos), BSRespCode.BS_NOT_QUERY_MCR_INFO,
				"根据配置单和客户号未查询到刷卡器数据," + inParam);
		mcr = mrcInfos.get(0);
		// 验证配置单状态
		HsAssert.isTrue(mcr.getStatus() == ConfStatus.WAIT_CONFIG.getCode(),
				BSRespCode.BS_TOOL_CONFIG_IS_NOT_WAIT_CONFIG, "配置单是待配置状态," + inParam);

		// 查询设备，验证设备是否存在、验证设备状态
		device = deviceInfoMapper.selectDeviceInfoBySeqNo(deviceSeqNo);
		HsAssert.notNull(device, BSRespCode.BS_TOOL_DEVICE_NOT_EXIST, "工具设备不存在," + inParam);
		HsAssert.isTrue(device.getUseStatus() == UseStatus.NOT_USED.getCode(), BSRespCode.BS_DEVICE_IS_NOT_USED,
				"工具设备不是未使用的," + inParam);

		// 验证设备是否已经关联
		dConf = deviceInfoMapper.selectDeviceConfigByCustIdAndNo(device.getDeviceCustId(), confNo);
		HsAssert.isTrue(null == dConf, BSRespCode.BS_TOOL_DEVICE_IS_CONFIG, "工具设备已经配置," + inParam);

		entResNo = entCustId.substring(0, 11);
		// 获取最大终端编号
		maxTerminalNo = deviceInfoMapper.selectMaxTerminalNo(entCustId, new String[]
		{ CategoryCode.CONSUME_MCR.name(), CategoryCode.POINT_MCR.name() });
		// 根据 最大终端编号生成关联的终端编号(最大终端编号为null从1开始)
		if (StringUtils.isBlank(maxTerminalNo))
		{
			terminalNo = StringUtil.frontCompWithZore(num, 4);
		} else
		{
			int lenght = maxTerminalNo.length();
			terminalNo = StringUtil
					.frontCompWithZore((Integer.parseInt(maxTerminalNo.substring(lenght - 4, lenght))) + num, 4);
		}
		try
		{
			// 增加关联
			deviceInfoMapper.insertDeviceConfig(
					new DeviceConfig(device.getDeviceCustId(), confNo, entResNo + terminalNo, true));

			// 修改设备使用状态
			count = deviceInfoMapper.updateDeviceUseStatusById(device.getDeviceCustId(), UseStatus.USED.getCode(), null,
					operNo);

			// 如果是最后一个就修改配置单的状态
			if (mcr.getConfingNum().intValue() == 1 && count > 0)
			{
				// 修改配置单状态
				count = toolConfigMapper.updateToolConfigByConfNo(
						new ToolConfig(confNo, null, ConfStatus.WAIT_SEND.getCode(), null, null));
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:configKsnRelation",
					BSRespCode.BS_MCR_CONFIG_FAIL.getCode() + ":刷卡器配置关联失败," + inParam, ex);
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_MCR_CONFIG_FAIL, "刷卡器配置关联失败," + inParam);

		// 用户中心设备数据
		cardReaders = new ArrayList<BsCardReader>();
		cardReader = new BsCardReader();
		cardReader.setEntResNo(entResNo);
		cardReader.setEntCustId(entCustId);
		cardReader.setMachineNo(deviceSeqNo);
		cardReader.setDeviceNO(terminalNo);
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
			cardReaders.add(cardReader);
			// 调用用户中心同步刷卡器数据
			bsDeviceService.batchAddCardReader(cardReaders, operNo);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:configKsnRelation",
					BSRespCode.BS_DUBBO_INVOKE_UC_FAIL.getCode() + ":dubbo调用UC同步刷卡器数据失败," + inParam, ex);
			throw new HsException(BSRespCode.BS_DUBBO_INVOKE_UC_FAIL, "dubbo调用UC同步刷卡器数据失败 ");
		}
	}

	/**
	 * 验证刷卡器设备是否合法
	 * 
	 * @param confNo
	 *            配置单号
	 * @param deviceSeqNo
	 *            设备序列号
	 * @return
	 * @throws HsException
	 */
	@Override
	public ToolRelationDetail vaildMcrDeviceLawful(String confNo, String deviceSeqNo) throws HsException
	{
		String inParam = "params==>confNo:" + confNo + ",deviceSeqNo:" + deviceSeqNo;
		BizLog.biz(this.getClass().getName(), "function:configKsnRelation", inParam, "验证刷卡器设备是否合法");
		// 设备信息
		DeviceInfo device = null;
		// 设备配置详情
		DeviceConfig dConf = null;
		// 设备序列号集合
		List<DeviceInfo> devices = null;

		// 验证参数 配置单号、设备序列号
		HsAssert.hasText(confNo, RespCode.PARAM_ERROR, "验证刷卡器设备是否合法配置单号为null," + inParam);
		HsAssert.hasText(deviceSeqNo, RespCode.PARAM_ERROR, "验证刷卡器设备是否合法设备序列号为null," + inParam);

		// 查询设备，验证设备是否存在、验证设备状态
		device = devices.get(0);
		HsAssert.notNull(device, BSRespCode.BS_TOOL_DEVICE_NOT_EXIST, "工具设备不存在," + inParam);
		HsAssert.isTrue(device.getUseStatus() == UseStatus.NOT_USED.getCode(), BSRespCode.BS_DEVICE_IS_NOT_USED,
				"工具设备不是未使用的," + inParam);

		// 验证设备是否已经关联
		dConf = deviceInfoMapper.selectDeviceConfigByCustIdAndNo(device.getDeviceCustId(), confNo);
		HsAssert.isTrue(null == dConf, BSRespCode.BS_TOOL_DEVICE_IS_CONFIG, "工具设备已经配置," + inParam);

		// 验证设备类型和配置单工具类型是否一致
		devices = deviceInfoMapper.vaildDeviceTypeIsSame(Arrays.asList(new String[]
		{ deviceSeqNo }), confNo);
		HsAssert.isTrue(devices.size() == 1, BSRespCode.BS_DEVICE_CONIFG_NO_SAME_TYPE, "设备类型和配置单工具类型不一致," + inParam);

		// 验证设备和配置单是否同仓库
		int count = deviceInfoMapper.selectDeviceInfoByNoAndConfNo(deviceSeqNo, confNo);
		HsAssert.isTrue(count > 0, BSRespCode.BS_DEVICE_CONIFG_NO_SAME_WH, "设备和配置单不在同一仓库," + inParam);

		return deviceInfoMapper.selectDeviceDetail(deviceSeqNo);
	}

	/**
	 * 验证设备是否合法
	 *
	 * @param confNo
	 *            配置单号
	 * @param deviceSeqNo
	 *            设备序列号
	 * @return
	 * @throws HsException
	 */
	@Override
	public ToolRelationDetail vaildDeviceLawfulNew(String confNo, String deviceSeqNo) throws HsException
	{
		String inParam = "params==>confNo:" + confNo + ",deviceSeqNo:" + deviceSeqNo;
		BizLog.biz(this.getClass().getName(), "function:vaildDeviceLawfulNew", inParam, "验证设备是否合法");
		// 设备信息
		DeviceInfo device = null;
		// 设备配置详情
		DeviceConfig dConf = null;
		// 设备序列号集合
		List<DeviceInfo> devices = null;

		// 验证参数 配置单号、设备序列号
		HsAssert.hasText(confNo, RespCode.PARAM_ERROR, "验证设备是否合法配置单号为null," + inParam);
		HsAssert.hasText(deviceSeqNo, RespCode.PARAM_ERROR, "验证设备是否合法设备序列号为null," + inParam);

		// 查询设备，验证设备是否存在、验证设备状态
		device = devices.get(0);
		HsAssert.notNull(device, BSRespCode.BS_TOOL_DEVICE_NOT_EXIST, "工具设备不存在," + inParam);
		HsAssert.isTrue(device.getUseStatus() == UseStatus.NOT_USED.getCode(), BSRespCode.BS_DEVICE_IS_NOT_USED,
				"工具设备不是未使用的," + inParam);

		// 验证设备是否已经关联
		dConf = deviceInfoMapper.selectDeviceConfigByCustIdAndNo(device.getDeviceCustId(), confNo);
		HsAssert.isTrue(null == dConf, BSRespCode.BS_TOOL_DEVICE_IS_CONFIG, "工具设备已经配置," + inParam);

		// 验证设备类型和配置单工具类型是否一致
		devices = deviceInfoMapper.vaildDeviceTypeIsSame(Arrays.asList(new String[]
		{ deviceSeqNo }), confNo);
		HsAssert.isTrue(devices.size() == 1, BSRespCode.BS_DEVICE_CONIFG_NO_SAME_TYPE, "设备类型和配置单工具类型不一致," + inParam);

		// 验证设备和配置单是否同仓库
		int count = deviceInfoMapper.selectDeviceInfoByNoAndConfNo(deviceSeqNo, confNo);
		HsAssert.isTrue(count > 0, BSRespCode.BS_DEVICE_CONIFG_NO_SAME_WH, "设备和配置单不在同一仓库," + inParam);

		return deviceInfoMapper.selectDeviceDetail(deviceSeqNo);
	}

	/**
	 * 批量配置刷卡器关联
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @param confNo
	 *            配置单号
	 * @param deviceSeqNos
	 *            设备序列号
	 * @param operNo
	 *            操作员编号
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void batchConfigKsnRelation(String entCustId, String confNo, List<String> deviceSeqNos, String operNo)
			throws HsException
	{
		String inParam = "params==>entCustId:" + entCustId + ",confNo:" + confNo + ",deviceSeqNos:" + deviceSeqNos
				+ ",operNo:" + operNo;
		BizLog.biz(this.getClass().getName(), "function:batchConfigKsnRelation", inParam, "批量配置刷卡器关联");
		int count = 0;
		// 最大终端编号
		String maxTerminalNo = "";
		// 终端编号
		String terminalNo = "";
		// 互生号
		String entResNo = "";
		// 配置单的配置单设备详情
		SecretKeyOrderInfoPage mcr = null;
		// 用户中心设备对象集合
		List<BsCardReader> cardReaders = null;
		BsCardReader cardReader = null;
		// 积分刷卡器KSN
		PointKSN point = null;
		// 消费刷卡器KSN
		ConsumeKSN consume = null;
		// 设备状态
		String[] deviceStatus = null;
		// 配置关联集合
		List<DeviceConfig> dConfs = null;
		// 设备集合
		List<DeviceInfo> devices = null;

		// 验证参数 客户号、配置单号、设备序列号
		HsAssert.hasText(entCustId, RespCode.PARAM_ERROR, "配置刷卡器关联客户号为null," + inParam);
		HsAssert.hasText(confNo, RespCode.PARAM_ERROR, "配置刷卡器关联配置单号为null," + inParam);
		HsAssert.isTrue(null != deviceSeqNos && deviceSeqNos.size() > 0, RespCode.PARAM_ERROR,
				"配置刷卡器关联设备序列号为null," + inParam);

		// 验证序列号号是否有重复数据
		HsAssert.isTrue(ObjectFactory.isDuplicateList(deviceSeqNos), BSRespCode.BS_DEVICES_HAS_SAME,
				"设备序列号列表包含相同的序列号," + inParam);

		// 验证配置单是否属于企业
		List<SecretKeyOrderInfoPage> mrcInfos = toolConfigMapper.selectMcrOrderByIf(entCustId, confNo);
		HsAssert.isTrue(!CollectionUtils.isEmpty(mrcInfos), BSRespCode.BS_NOT_QUERY_MCR_INFO,
				"根据配置单和客户号未查询到刷卡器数据," + inParam);
		mcr = mrcInfos.get(0);

		// 验证配置单状态
		HsAssert.isTrue(mcr.getStatus() == ConfStatus.WAIT_CONFIG.getCode(),
				BSRespCode.BS_TOOL_CONFIG_IS_NOT_WAIT_CONFIG, "配置单是待配置状态," + inParam);

		// 验证关联的数量是否大于配置单的数量
		HsAssert.isTrue(mcr.getConfingNum().intValue() >= deviceSeqNos.size(),
				BSRespCode.BS_DEVICES_GREATER_THAN_CONFIG_NUM, "进行关联设备的数量大于配置单的数量," + inParam);

		// 批量验证设备状态
		deviceStatus = deviceInfoMapper.batchVaildDeviceStatus(deviceSeqNos).split(",");

		HsAssert.notNull((Integer.parseInt(deviceStatus[0]) == deviceSeqNos.size()),
				BSRespCode.BS_TOOL_DEVICE_NOT_EXIST, "设备列表中有工具设备不存在," + inParam);
		HsAssert.isTrue((Integer.parseInt(deviceStatus[1]) == deviceSeqNos.size()), BSRespCode.BS_DEVICE_IS_NOT_USED,
				"设备列表中工具设备不是未使用的," + inParam);

		// 验证设备类型和配置单工具类型不一致
		devices = deviceInfoMapper.vaildDeviceTypeIsSame(deviceSeqNos, confNo);
		HsAssert.isTrue(devices.size() == deviceSeqNos.size(), BSRespCode.BS_DEVICE_CONIFG_NO_SAME_TYPE,
				"设备类型和配置单工具类型不一致," + inParam);

		entResNo = entCustId.substring(0, 11);
		// 获取最大终端编号
		maxTerminalNo = deviceInfoMapper.selectMaxTerminalNo(entCustId, new String[]
		{ CategoryCode.CONSUME_MCR.name(), CategoryCode.POINT_MCR.name() });
		// 根据 最大终端编号生成关联的终端编号(最大终端编号为null从1开始)
		if (StringUtils.isBlank(maxTerminalNo))
		{
			terminalNo = StringUtil.frontCompWithZore(0, 4);
		} else
		{
			int lenght = maxTerminalNo.length();
			terminalNo = maxTerminalNo.substring(lenght - 4, lenght);
		}
		// 设备关联数据
		dConfs = new ArrayList<DeviceConfig>();
		// 用户中心设备数据
		cardReaders = new ArrayList<BsCardReader>();
		// 循环生成数据
		for (DeviceInfo device : devices)
		{
			terminalNo = StringUtil.frontCompWithZore((Integer.parseInt(terminalNo)) + 1, 4);
			// 设备关联
			dConfs.add(new DeviceConfig(device.getDeviceCustId(), confNo, entResNo + terminalNo, true));

			// 设置设备属性
			device.setUseStatus(UseStatus.USED.getCode());
			device.setUpdatedBy(operNo);

			// 设置用户中心设备数据
			cardReader = new BsCardReader();
			cardReader.setEntResNo(entResNo);
			cardReader.setEntCustId(entCustId);
			cardReader.setMachineNo(device.getDeviceSeqNo());
			cardReader.setDeviceNO(terminalNo);

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
			cardReaders.add(cardReader);
		}

		try
		{
			// 增加关联
			count = deviceInfoMapper.batchInsertDeviceConfig(dConfs);

			if (count > 0)
			{
				// 修改设备使用状态
				deviceInfoMapper.batchUpdateDeviceStatus(devices);
			}

			// 如果是最后一个就修改配置单的状态
			if (mcr.getConfingNum().intValue() == deviceSeqNos.size() && count > 0)
			{
				// 修改配置单状态
				count = toolConfigMapper.updateToolConfigByConfNo(
						new ToolConfig(confNo, null, ConfStatus.WAIT_SEND.getCode(), null, null));
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:batchConfigKsnRelation",
					BSRespCode.BS_MCR_CONFIG_FAIL.getCode() + ":批量配置刷卡器关联失败," + inParam, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_MCR_CONFIG_FAIL, "批量配置刷卡器关联失败," + inParam);

		try
		{
			// 调用用户中心同步刷卡器数据
			bsDeviceService.batchAddCardReader(cardReaders, operNo);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:batchConfigKsnRelation",
					BSRespCode.BS_DUBBO_INVOKE_UC_FAIL.getCode() + ":dubbo调用UC同步刷卡器数据失败," + inParam, ex);
			throw new HsException(BSRespCode.BS_DUBBO_INVOKE_UC_FAIL, "dubbo调用UC同步刷卡器数据失败 ");
		}
	}

	/**
	 * 配置互生平板关联
	 * 
	 * @Description:
	 * @param entCustId
	 *            企业客户号
	 * @param confNo
	 *            配置单号
	 * @param deviceSeqNo
	 *            设备序列号
	 * @return
	 * @throws HsException
	 */
	@Override
	@Transactional
	public String configToolTablet(String entCustId, String confNo, String deviceSeqNo) throws HsException
	{
		String inParam = "params==>entCustId:" + entCustId + "confNo:" + confNo + "deviceSeqNo:" + deviceSeqNo;
		BizLog.biz(this.getClass().getName(), "function:configToolTablet", inParam, "配置互生平板关联");
		// 默认起始终端编号
		int num = 1;
		// 最大终端编号
		String maxTerminalNo = "";
		// 终端编号
		String terminalNo = "";
		// 设备信息
		DeviceInfo device = null;
		// 设备配置详情
		DeviceConfig dConf = null;
		// 验证参数 客户号、配置单号、设备序列号
		HsAssert.hasText(entCustId, RespCode.PARAM_ERROR, "配置互生平板客户号为null," + inParam);
		HsAssert.hasText(confNo, RespCode.PARAM_ERROR, "配置互生平板配置单号为null," + inParam);
		HsAssert.hasText(deviceSeqNo, RespCode.PARAM_ERROR, "配置互生平板设备序列号为null," + inParam);

		// 验证配置单是否属于企业
		List<SecretKeyOrderInfoPage> tabletInfos = toolConfigMapper.selectSecretKeyOrderByIf(entCustId, confNo);
		HsAssert.isTrue(!CollectionUtils.isEmpty(tabletInfos), BSRespCode.BS_NOT_QUERY_TABLET_INFO,
				"根据配置单和客户号未查询到互生平板数据," + inParam);
		// 验证配置单状态
		HsAssert.isTrue(tabletInfos.get(0).getStatus() == ConfStatus.WAIT_CONFIG.getCode(),
				BSRespCode.BS_TOOL_CONFIG_IS_NOT_WAIT_CONFIG, "配置单不是待配置状态," + inParam);

		// 验证设备是否存在、设备状态是否未使用
		device = deviceInfoMapper.selectDeviceInfoBySeqNo(deviceSeqNo);
		HsAssert.notNull(device, BSRespCode.BS_TOOL_DEVICE_NOT_EXIST, "工具设备不存在," + inParam);
		HsAssert.isTrue(device.getUseStatus() == UseStatus.NOT_USED.getCode(), BSRespCode.BS_DEVICE_IS_NOT_USED,
				"工具设备不是未使用的," + inParam);

		// 设备的关联关系未使用前，可以重新灌秘钥
		dConf = deviceInfoMapper.selectDeviceConfigByCustIdAndNo(device.getDeviceCustId(), confNo);
		if (null != dConf)
		{
			// 设备已经关联了其他的配置单
			if (!dConf.getConfNo().equals(confNo))
			{
				throw new HsException(BSRespCode.BS_DEVICE_RELEVANCE_OTHER_CONFIG, "设备已关联其他配置单," + inParam);
			}
			// 如果配置的关联还是为使用时，直接返回终端编号再次灌秘钥
			if (!dConf.getIsUse())
			{
				return dConf.getTerminalNo();
			}
			throw new HsException(BSRespCode.BS_TOOL_DEVICE_IS_CONFIG, "工具设备已经配置," + inParam);
		}

		terminalNo = entCustId.substring(0, 11);
		// 获取最大终端编号
		maxTerminalNo = deviceInfoMapper.selectMaxTerminalNo(entCustId, new String[]
		{ CategoryCode.TABLET.name() });
		// 根据 最大终端编号生成关联的终端编号(最大终端编号为null从1开始)
		if (StringUtils.isBlank(maxTerminalNo))
		{
			terminalNo = terminalNo + StringUtil.frontCompWithZore(num, 4);
		} else
		{
			int lenght = maxTerminalNo.length();
			terminalNo = terminalNo + StringUtil
					.frontCompWithZore((Integer.parseInt(maxTerminalNo.substring(lenght - 4, lenght))) + num, 4);
		}
		try
		{
			// 增加设备关联
			deviceInfoMapper.insertDeviceConfig(new DeviceConfig(device.getDeviceCustId(), confNo, terminalNo, false));
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:configToolPos",
					BSRespCode.BS_TABLET_CONFIG_FAIL.getCode() + ":互生平板关联失败," + inParam, ex);
			throw new HsException(BSRespCode.BS_TABLET_CONFIG_FAIL, "互生平板关联失败," + inParam);
		}
		return terminalNo;
	}

	/**
	 * 分页查询互生卡制作配置单
	 * 
	 * @Description:
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 */
	@Override
	public PageData<ToolConfigPage> queryToolConfigMarkCardPage(BaseParam bean, Page page)
	{
		if (null == page)
		{
			return new PageData<ToolConfigPage>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 设置日期查询条件
		bean.setQueryDate();
		// 设置角色列表
		bean.setRoleId();
		// 查询互生卡制作配置单列表
		List<ToolConfigPage> cards = toolConfigMapper.selectToolConfigMarkCardListPage(bean);
		if (StringUtils.isNotBlank(cards))
		{
			return new PageData<ToolConfigPage>(page.getCount(), cards);
		}
		return new PageData<ToolConfigPage>(0, null);
	}

	/**
	 * 互生卡开卡
	 * 
	 * @Description:
	 * @param confNo
	 *            配置单号
	 * @param operNo
	 *            操作员编号
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void openCard(String confNo, String operNo) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:openCard", "params==>confNo" + confNo + "operCustId:" + operNo,
				"互生卡开卡");
		// 调用存储过程开卡参数实体
		BatchOpenCard param = null;
		// 地区平台信息
		LocalInfo info = null;
		int count = 0;
		HsAssert.hasText(confNo, RespCode.PARAM_ERROR, "配置单号为null," + confNo + "operCustId:" + operNo);
		HsAssert.hasText(operNo, RespCode.PARAM_ERROR, "操作员编号为null," + confNo + "operCustId:" + operNo);
		// 获取配置单数据
		ToolConfig config = toolConfigMapper.selectToolConfigByIdAndType(confNo, new String[]
		{ OrderType.APPLY_BUY_TOOL.getCode(), OrderType.BUY_TOOL.getCode(),
				OrderType.APPLY_PERSON_RESOURCE.getCode() });
		// 验证配置单数据、状态
		HsAssert.notNull(config, RespCode.PARAM_ERROR, "配置单单号错误," + confNo + "operCustId:" + operNo);

		HsAssert.isTrue(config.getConfStatus().intValue() == ConfStatus.WAIT_CONFIG.getCode(),
				BSRespCode.BS_TOOL_CONFIG_IS_NOT_OPEN_CARD, "配置单不是待开卡的状态," + confNo + "operCustId:" + operNo);
		// 地区平台信息
		info = commonService.getAreaPlatInfo();
		HsAssert.notNull(info, BSRespCode.BS_INVOKE_LCS_FAIL, "调用LCS失败," + confNo + "operCustId:" + operNo);
		try
		{
			// 互生卡出入库数据
			String batchNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
			CardInOut bean = new CardInOut(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()),
					CardInOutFalg.WAIT_IN.getCode(), batchNo, config.getProductId(), config.getProductName(),
					config.getQuantity(), config.getWhId(), config.getOrderNo(), "互生卡待入库", operNo, null);
			// 插入互生卡出入库数据
			cardInOutMapper.insertCardInOut(bean);

			// 修改配置单状态
			count = toolConfigMapper.updateToolConfigByConfNo(
					new ToolConfig(confNo, null, ConfStatus.WAIT_MARK.getCode(), batchNo, null));

			if (count > 0)
			{
				param = new BatchOpenCard(info.getCountryNo(), config.getHsResNo(), config.getQuantity(),
						config.getConfNo());
				// 调用存储过程开卡
				cardInfoMapper.callBatchOpenCard(param);
				// 判断存储过程是否调用成功
				count = (null != param && param.getResult().intValue() == RespCode.SUCCESS.getCode()) ? 1 : 0;
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:openCard",
					BSRespCode.BS_BATCH_OPEN_CARD_FAIL.getCode() + ":批量开卡失败,confNo:" + confNo + ",operCustId:" + operNo,
					ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_BATCH_OPEN_CARD_FAIL,
				"批量开卡失败,confNo:" + confNo + ",operCustId:" + operNo);
	}

	/**
	 * 重做互生卡开卡(补卡、重做卡)
	 * 
	 * @Description:
	 * @param confNo
	 *            配置单号
	 * @param orderType
	 *            订单类型
	 * @param operNo
	 *            操作员编号
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void remarkOpenCard(String confNo, String orderType, String operNo) throws HsException
	{
		String inParam = "params==>confNo:" + confNo + "orderType:" + orderType + "operNo:" + operNo;
		BizLog.biz(this.getClass().getName(), "function:remarkOpenCard", inParam, "重做互生卡开卡(补卡、重做卡)");
		// 地区平台信息
		LocalInfo local = null;
		// 暗码
		String dark = "";
		// 卡号
		String cardId = "";
		// 卡信息
		CardInfo info = null;
		// 卡信息集合
		List<CardInfo> infos = null;
		// 重做互生卡信息
		List<MakingCardInfo> makings = null;
		// 同步用户中心卡信息
		List<BsHsCard> cards = null;
		BsHsCard card = null;
		int count = 0;
		// 验证参数 订单类型、配置单号、操作员编号
		HsAssert.hasText(orderType, RespCode.PARAM_ERROR, "重做互生卡开卡订单类型为null," + inParam);
		HsAssert.hasText(confNo, RespCode.PARAM_ERROR, "重做互生卡开卡配置单号为null," + inParam);
		HsAssert.hasText(operNo, RespCode.PARAM_ERROR, "重做互生卡开卡操作员编号为null," + inParam);

		// 获取配置单、验证配置单数据、状态
		ToolConfig config = toolConfigMapper.selectToolConfigByIdAndType(confNo, new String[]
		{ orderType });
		HsAssert.notNull(config, RespCode.PARAM_ERROR, "配置单号错误,未查询订单类型的配置单," + inParam);
		HsAssert.isTrue(config.getConfStatus().intValue() == ConfStatus.WAIT_CONFIG.getCode(),
				BSRespCode.BS_TOOL_CONFIG_IS_NOT_OPEN_CARD, "配置单状态错误，不可以开卡," + inParam);

		// 根据配置单号查询重做卡信息
		makings = cardInfoMapper.selectMakingCardInfoByNo(confNo);
		HsAssert.isTrue(!CollectionUtils.isEmpty(makings), BSRespCode.BS_NOT_QUERY_MARKING_CARDINFO,
				"未查询到重做互生卡信息," + inParam);

		// 生成互生卡信息
		infos = new ArrayList<CardInfo>();
		cards = new ArrayList<BsHsCard>();
		// 地区平台信息
		local = commonService.getAreaPlatInfo();
		HsAssert.notNull(local, BSRespCode.BS_INVOKE_LCS_FAIL, "调用LCS失败," + inParam);
		// 生成卡数据
		for (MakingCardInfo making : makings)
		{
			do
			{
				dark = "1" + local.getCurrencyNo() + RandomCodeUtils.getNumberCode(4);
				cardId = making.getPerResNo() + dark;
				// 验证互生卡id是否重复
				info = cardInfoMapper.selectCardInfoById(cardId);
			} while (info != null);
			// 个人补卡 --没有密码
			if (orderType.equals(OrderType.REMAKE_MY_CARD.getCode()))
			{// 个人补卡数据
				infos.add(new CardInfo(cardId, confNo, making.getPerResNo(), dark, null));
				card = new BsHsCard();
				card.setPerResNo(making.getPerResNo());
				card.setCryptogram(dark);
				// 企业
			} else if (orderType.equals(OrderType.REMAKE_BATCH_CARD.getCode()))
			{// 企业重做卡数据
				String pwd = RandomCodeUtils.getNumberCode(6);
				infos.add(new CardInfo(cardId, confNo, making.getPerResNo(), dark, pwd));
				card = new BsHsCard();
				card.setPerResNo(making.getPerResNo());// 互生号
				card.setCryptogram(dark);// 暗码
				card.setLoginPwd(pwd);// 登录密码
				cards.add(card);
			}
		}

		// 互生卡出入库数据
		String batchNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
		CardInOut bean = new CardInOut(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()),
				CardInOutFalg.WAIT_IN.getCode(), batchNo, config.getProductId(), config.getProductName(),
				config.getQuantity(), config.getWhId(), config.getOrderNo(), "互生卡待入库", operNo, null);

		try
		{
			// 修改配置单状态
			count = toolConfigMapper.updateToolConfigByConfNo(
					new ToolConfig(confNo, null, ConfStatus.WAIT_MARK.getCode(), batchNo, "重做互生卡开卡"));
			if (count > 0)
			{
				// 插入互生卡信息
				cardInfoMapper.batchInsertCardInfo(infos);
				// 插入互生卡待入库
				cardInOutMapper.insertCardInOut(bean);
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:remarkOpenCard",
					BSRespCode.BS_RENEWAL_OPEN_CARD_FAIL.getCode() + ":重做互生卡开卡失败," + inParam, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_RENEWAL_OPEN_CARD_FAIL, "重做互生卡开卡失败," + inParam);

		// 同步数据到用户中心
		try
		{
			// 个人补卡 --没有密码
			if (orderType.equals(OrderType.REMAKE_MY_CARD.getCode()))
			{
				// 调用用户中心个人补卡接口
				bsCardHolderService.remakeCard(operNo, card);
				// 企业
			} else if (orderType.equals(OrderType.REMAKE_BATCH_CARD.getCode()))
			{
				// 调用用户中心企业重做卡接口
				bsCardHolderService.batchResendCards(operNo, cards, config.getHsResNo());
			}
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:remarkOpenCard",
					BSRespCode.BS_DUBBO_INVOKE_UC_FAIL.getCode() + ":dubbo调用UC同步重做互生卡数据失败," + inParam, ex);
			throw new HsException(BSRespCode.BS_DUBBO_INVOKE_UC_FAIL, "dubbo调用UC同步重做互生卡数据失败 ," + inParam);
		}
	}

	/**
	 * 导出密码
	 * 
	 * @Description:
	 * @param confNo
	 *            配置单号
	 * @return
	 */
	@Override
	public List<CardInfo> exportCardPwd(String confNo)
	{
		if (StringUtils.isBlank(confNo))
		{
			return null;
		}
		return cardInfoMapper.selectCardInfoInitPwd(confNo);
	}

	/**
	 * 查询互生卡配置单制作数据
	 * 
	 * @Description:
	 * @param orderNo
	 *            订单号
	 * @param confNo
	 *            配置单号
	 * @param hsResNo
	 *            互生号
	 * @return
	 */
	@Override
	public CardMarkData queryCardConfigMarkData(String orderNo, String confNo, String hsResNo)
	{
		if (StringUtils.isBlank(orderNo) || StringUtils.isBlank(confNo) || StringUtils.isBlank(hsResNo))
		{
			return null;
		}
		CardMarkData bean = null;
		// 服务公司互生号
		String sEntResNo = null;
		// 卡制作数据
		bean = commonMapper.selectCardMarkData(orderNo);
		if (null != bean)
		{
			// 获取配置单的最大和最小互生号
			String perResNo = cardInfoMapper.selectConfMaxAndMinPreResNo(confNo);
			if (StringUtils.isNotBlank(perResNo) && perResNo.indexOf(",") != -1)
			{
				String[] perResNos = perResNo.split(",");
				bean.setMinPerResNo(perResNos[0]);// 最小互生号
				bean.setMaxPerResNo(perResNos[1]);// 最大互生号
			}
			// 如果是托管企业则可以查询到上级服务的卡样
			if (HsResNoUtils.isTrustResNo(hsResNo))
			{
				sEntResNo = hsResNo.substring(0, 5) + "000000";
			}
			// 查询默认标准卡样，企业订制卡样
			bean.setStyles(cardStyleMapper.selectCardStyleByMark(hsResNo, sEntResNo));
			// 获取所有供应商
			bean.setSuppliers(supplierMapper.seleteSupplierByAll());
			// 申报订单
			if (bean.getOrderType().equals(OrderType.APPLY_BUY_TOOL.getCode()))
			{
				// 查询用户中心获取企业信息(联系人、联系人电话)
				BsEntMainInfo info = commonService.getEntMainInfoToUc(hsResNo, null);
				if (info != null)
				{
					bean.setLinkMan(info.getContactPerson());// 联系人
					bean.setPhone(info.getContactPhone());// 联系人电话
				}
			}
			return bean;
		}
		return null;
	}

	/**
	 * 导出暗码
	 * 
	 * @Description:
	 * @param confNo
	 *            配置单号
	 * @return
	 */
	@Override
	public List<CardInfo> exportCardDarkCode(String confNo)
	{
		if (StringUtils.isBlank(confNo))
		{
			return null;
		}
		return cardInfoMapper.selectCardInfoRarkCode(confNo);
	}

	/**
	 * 互生卡配置单制成
	 * 
	 * @Description:
	 * @param bean
	 *            互生卡配置单制成参数实体
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void cardConfigMark(CardMarkConfig bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "function:cardConfigMark", "params==>" + bean, "互生卡配置单制成");
		int count = 0;
		// 验证参数
		HsAssert.notNull(bean, RespCode.PARAM_ERROR, "卡制作单制成参数为null," + bean);

		// 根据注解验证参数
		String valid = ValidateParamUtil.validateParam(bean);
		HsAssert.isTrue(StringUtils.isBlank(valid), RespCode.PARAM_ERROR, valid + "," + bean);

		// 获取配置单数据，验证配置单状态
		ToolConfig config = toolConfigMapper.selectToolConfigById(bean.getConfNo());
		HsAssert.notNull(config, RespCode.PARAM_ERROR, "配置单号错误,未查询到配置单数据," + bean);
		HsAssert.isTrue(config.getConfStatus().intValue() == ConfStatus.WAIT_MARK.getCode(),
				BSRespCode.BS_TOOL_CONFIG_IS_NOT_WAIT_MARK, "配置单不是待制作状态," + bean);
		try
		{
			// 修改卡的配置单卡样和状态为待入库
			count = toolConfigMapper.updateToolConfigByConfNo(new ToolConfig(bean.getConfNo(), bean.getCardStyleId(),
					ConfStatus.WAIT_ENTER.getCode(), null, bean.getDescription()));
			if (count > 0)
			{
				// 修改互生卡出入库数据
				count = cardInOutMapper.updateCardInOut(
						new CardInOut(null, bean.getOrderNo(), "添加互生的供应商", bean.getOperNo(), bean.getSupplierId()));
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:cardConfigMark",
					BSRespCode.BS_CARD_MARK_FAIL.getCode() + ":卡制作单制成失败," + bean, ex);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_CARD_MARK_FAIL, "卡制作单制成失败," + bean);
	}

	/**
	 * 查询卡制作数据(制作单)
	 * 
	 * @Description:
	 * @param orderNo
	 *            订单号
	 * @param confNo
	 *            配置单号
	 * @param hsResNo
	 *            互生号
	 * @return
	 */
	@Override
	public CardMarkData queryCardMarkData(String orderNo, String confNo, String hsResNo)
	{
		if (StringUtils.isBlank(orderNo) || StringUtils.isBlank(confNo) || StringUtils.isBlank(hsResNo))
		{
			return null;
		}
		CardMarkData bean = null;
		// 卡制作数据
		bean = commonMapper.selectCardMarkData(orderNo);
		if (null != bean)
		{
			// 获取配置单的最小和最大互生号
			String perResNo = cardInfoMapper.selectConfMaxAndMinPreResNo(confNo);
			if (StringUtils.isNotBlank(perResNo) && perResNo.indexOf(",") != -1)
			{
				String[] perResNos = perResNo.split(",");
				bean.setMinPerResNo(perResNos[0]);// 最小互生号
				bean.setMaxPerResNo(perResNos[1]);// 最大互生号
			}
			// 获取卡样
			bean.setStyle(cardStyleMapper.selectCardStyleById(bean.getCardStyleId()));
			// 获取供应商
			bean.setSupplier(supplierMapper.seleteSupplierById(bean.getSupplierId()));
			// 申报订单
			if (bean.getOrderType().equals(OrderType.APPLY_BUY_TOOL.getCode()))
			{
				// 查询用户中心获取企业信息(联系人、联系人电话)
				BsEntMainInfo info = commonService.getEntMainInfoToUc(hsResNo, null);
				if (info != null)
				{
					bean.setLinkMan(info.getContactPerson());
					bean.setPhone(info.getContactPhone());
				}
			}
			return bean;
		}
		return null;
	}

	/**
	 * 查看互生卡出入库详情
	 * 
	 * @Description:
	 * @param orderNo
	 *            订单号
	 * @return
	 */
	@Override
	public CardInOut queryCardInOutDetail(String orderNo)
	{
		if (StringUtils.isBlank(orderNo))
		{
			return null;
		}
		return cardInOutMapper.selectCardInOutByNo(orderNo);
	}

	/**
	 * 互生卡入库 + 同步数据到用户中心
	 * 
	 * @Description:
	 * @param confNo
	 *            配置单号
	 * @param operNo
	 *            操作员编号
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void cardConfigEnter(String confNo, String operNo) throws HsException
	{
		String inParam = "confNo:" + confNo + "operNo:" + operNo;
		BizLog.biz(this.getClass().getName(), "function:cardConfigEnter", "params==>" + inParam, "互生卡入库");
		int count = 0;

		// 验证非null
		HsAssert.hasText(confNo, RespCode.PARAM_ERROR, "配置单号为null," + inParam);
		HsAssert.hasText(operNo, RespCode.PARAM_ERROR, "操作员编号为null," + inParam);

		// 获取配置单数据，验证配置单状态
		ToolConfig config = toolConfigMapper.selectToolConfigById(confNo);
		HsAssert.notNull(config, RespCode.PARAM_ERROR, "配置单号错误,未查询到配置单数据," + inParam);
		HsAssert.isTrue(config.getConfStatus().intValue() == ConfStatus.WAIT_ENTER.getCode(),
				BSRespCode.BS_TOOL_CONFIG_IS_NOT_WAIT_ENTER, "配置单不是待入库状态," + inParam);
		try
		{
			// 修改配置单状态
			count = toolConfigMapper
					.updateToolConfigByConfNo(new ToolConfig(confNo, null, ConfStatus.WAIT_SEND.getCode(), null, null));
			if (count > 0)
			{
				// 互生卡入库
				count = cardInOutMapper.updateCardInOut(
						new CardInOut(CardInOutFalg.IN.getCode(), config.getOrderNo(), "互生卡入库", operNo, null));
			}
		} catch (Exception ex)
		{
			SystemLog.debug(this.getClass().getName(), "function:cardConfigEnter",
					BSRespCode.BS_CARD_ENTER_FAIL.getCode() + ":互生卡入库失败," + inParam);
			count = 0;
		}
		HsAssert.isTrue(count > 0, BSRespCode.BS_CARD_ENTER_FAIL, "互生卡入库失败," + inParam);

		// 不是售后服务配置单类型
		if (config.getConfType() != ConfType.AFTER_CONFIG.getCode())
		{
			try
			{
				// 同步卡信息用户中心
				bsCardHolderService.batchAddCards(operNo, cardInfoMapper.selectCardInfoByUcSync(confNo),
						config.getHsResNo());
			} catch (HsException ex)
			{
				// 如果用户中心返回的异常码是160103就不回滚
				if (ex.getErrorCode() != 160103)
				{
					throw ex;
				}
			} catch (Exception ex)
			{
				SystemLog.error(this.getClass().getName(), "function:cardConfigEnter",
						BSRespCode.BS_DUBBO_INVOKE_UC_FAIL.getCode() + ":dubbo调用UC同步互生卡数据失败," + inParam, ex);
				throw new HsException(BSRespCode.BS_DUBBO_INVOKE_UC_FAIL, "dubbo调用UC同步互生卡数据失败 ," + inParam);
			}
		}
	}
}
