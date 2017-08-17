/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.bs.quota.service;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.api.quota.IBSQuotaService;
import com.gy.hsxt.bs.bean.quota.*;
import com.gy.hsxt.bs.bean.quota.result.*;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.StringUtil;
import com.gy.hsxt.bs.common.enumtype.ApprStatus;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.quota.AppType;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.quota.mapper.QuotaMapper;
import com.gy.hsxt.bs.task.bean.Task;
import com.gy.hsxt.bs.task.interfaces.ITaskService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.*;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.lcs.bean.City;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uf.api.IUFRegionPacketService;
import com.gy.hsxt.uf.bean.packet.RegionPacketBody;
import com.gy.hsxt.uf.bean.packet.RegionPacketHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源配额分配，审批，查询，统计服务实现类
 * 
 * @Package: com.gy.hsxt.bs.quota.service
 * @ClassName: QuotaService
 * @Description: TODO
 * 
 * @author: yangjianguo
 * @date: 2015-10-19 下午12:23:28
 * @version V1.0
 */
@Service(value = "quotaService")
public class QuotaService implements IBSQuotaService {

	/** 配额Mapper **/
	@Autowired
	private QuotaMapper quotaMapper;

	/** 业务服务私有配置参数 **/
	@Autowired
	private BsConfig bsConfig;

	/** 任务Service **/
	@Autowired
	private ITaskService taskService;

	/** 公共Service **/
	@Autowired
	private ICommonService commonService;

	/** 综合前置Service **/
	@Autowired
	private IUFRegionPacketService ufRegionPacketService;

	@Autowired
	LcsClient lcsClient;

	/**
	 * 一级区域配额分配申请(管理公司列表)
	 * 
	 * @Description:
	 * @return
	 */
	@Override
	public List<PlatAppManage> queryPlatAppManageList()
	{
		return quotaMapper.selectPlatAppManageList(null);
	}

	/**
	 * 地区平台(一级区域)资源配额申请
	 * 
	 * @Description:
	 * @param bean
	 *            地区平台资源配额申请实体
	 * @throws HsException
	 */
	@Override
	@Transactional
	public void applyPlatQuota(PlatQuotaApp bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "applyPlatQuota", "input param:" + bean, "地区平台(一级区域)资源配额申请");
		try
		{
			// 验证参数非null，平台不支持减少配额
			HsAssert.notNull(bean, RespCode.PARAM_ERROR, "一级区域配额申请参数为null," + bean);
			HsAssert.notNull(bean.getApplyType(), RespCode.PARAM_ERROR, "一级区域配额申请类型为null," + bean);
			HsAssert.isTrue(AppType.LESSEN.getCode() != bean.getApplyType(), RespCode.PARAM_ERROR, "平台不支持配额减少," + bean);

			// HsAssert.isTrue(bean.getApplyNum() <=
			// quotaMapper.countFreePlatQuota(),
			// RespCode.BS_QUOTA_NOT_ENAUGH_FOR_RELEASE, "可减少资源配额不足");

			bean.setApplyId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
			bean.setStatus(ApprStatus.WAIT_APPR.getCode());
			// 插入地区平台(一级区域)资源配额申请
			quotaMapper.applyPlatQuota(bean);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "applyPlatQuota",
					BSRespCode.BS_AREA_PLAT_QUOTA_APPLY_FAIL.getCode() + ":一级区域配额申请失败," + bean, ex);
			throw new HsException(BSRespCode.BS_AREA_PLAT_QUOTA_APPLY_FAIL, "一级区域配额申请失败," + bean);
		}
		try
		{
			// 组装综合前置的参数报文
			RegionPacketHeader packetHeader = RegionPacketHeader.build()
					.setDestPlatformId(GlobalConstant.CENTER_PLAT_NO)
					.setDestBizCode(AcrossPlatBizCode.TO_CENTER_APPLY_QUOTA.name());
			// 参数体
			RegionPacketBody packetBody = RegionPacketBody.build(bean.toString());
			// 调用综合前置同步地区平台配额申请数据
			Integer result = (Integer) ufRegionPacketService.sendSyncRegionPacket(packetHeader, packetBody);
			if (result != RespCode.SUCCESS.getCode())
			{
				throw new HsException();
			}
		} catch (HsException ex)
		{
			SystemLog.error(this.getClass().getName(), "applyPlatQuota", ex.getErrorCode() + ":一级区域配额申请调用总平台失败," + bean,
					ex);
			throw new HsException(BSRespCode.BS_CALL_CENTERPLAT_FAIL, "一级区域配额申请调用总平台失败");
		}
	}

	/**
	 * 根据id查询一级区域配额申请
	 * 
	 * @Description:
	 * @param applyId
	 *            申请编号
	 * @return
	 */
	@Override
	public PlatQuotaApp getPlatQuotaById(String applyId)
	{
		return quotaMapper.getPlatQuotaById(applyId);
	}

	/**
	 * 分页查询一级区域配额申请
	 * 
	 * @Description:
	 * @param entResNo
	 *            管理公司互生号
	 * @param entCustName
	 *            管理公司名称
	 * @param exeCustId
	 *            执行审批操作的客户号
	 * @param page
	 *            分页条件
	 * @return
	 */
	@Override
	public PageData<PlatQuotaApp> queryPlatQuotaPage(String entResNo, String entCustName, String exeCustId, Page page)
	{
		if (null == page)
		{
			return new PageData<PlatQuotaApp>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 查询一级配额申请列表
		List<PlatQuotaApp> result = quotaMapper.queryPlatQuotaListPage(entResNo, entCustName, exeCustId);
		if (StringUtils.isNotBlank(result))
		{
			return new PageData<PlatQuotaApp>(page.getCount(), result);
		}
		return new PageData<PlatQuotaApp>(0, null);
	}

	/**
	 * 申请省级区域(二级区域)配额分配
	 * 
	 * @Description:
	 * @param bean
	 *            省级区域配额申请参数实体
	 * @throws HsException
	 */
	@Transactional
	@Override
	public void applyProvinceQuota(ProvinceQuotaApp bean) throws HsException
	{
		// 申请编号
		String applyId = "";
		BizLog.biz(this.getClass().getName(), "applyProvinceQuota" + bean, "input param:", "申请省级区域(二级区域)配额分配");
		try
		{
			// 验证参数非null
			HsAssert.notNull(bean, RespCode.PARAM_ERROR, "二级区域配额申请参数为null," + bean);
			HsAssert.notNull(bean.getApplyType(), RespCode.PARAM_ERROR, "二级区域配额申请类型为空," + bean);
			HsAssert.hasText(bean.getEntResNo(), RespCode.PARAM_ERROR, "二级区域配额申请参数管理公司号为空," + bean);
			HsAssert.notNull(bean.getProvinceNo(), RespCode.PARAM_ERROR, "二级区域配额申请参数省代码为空," + bean);

			// 验证省份是否有申请中的
			List<String> applyingProvinceNo = quotaMapper.selectApplyingProvince(bean.getEntResNo(),
					bean.getProvinceNo());
			HsAssert.isTrue(StringUtils.isBlank(applyingProvinceNo), BSRespCode.BS_PROVINCE_HAS_APPLYING_DATA,
					"省份有申请的配额申请," + bean);

			// 首次配置、增加
			if (AppType.FIRST.getCode() == bean.getApplyType() || AppType.ADD.getCode() == bean.getApplyType())
			{
				// 验证地区平台配额数据是否够
				HsAssert.isTrue(bean.getApplyNum() <= quotaMapper.countFreePlatQuotaOfManager(bean.getEntResNo()),
						BSRespCode.BS_QUOTA_NOT_ENOUGH_FOR_ALLOT, "可分配的资源配额不足," + bean);
			} else
			{// 减少配额
				// 验证未分到城市的省配额是否够
				HsAssert.isTrue(bean.getApplyNum() <= quotaMapper.countFreeProvinceQuota(bean.getProvinceNo()),
						BSRespCode.BS_QUOTA_NOT_ENAUGH_FOR_RELEASE, "可减少资源配额不足," + bean);
			}
			applyId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
			bean.setApplyId(applyId);
			bean.setStatus(ApprStatus.WAIT_APPR.getCode());
			// 插入申请省级区域配额
			quotaMapper.applyProvinceQuota(bean);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "applyProvinceQuota",
					BSRespCode.BS_PROVINCE_QUOTA_APPLY_FAIL.getCode() + ":二级区域配配额申请失败," + bean, ex);
			throw new HsException(BSRespCode.BS_PROVINCE_QUOTA_APPLY_FAIL, "二级区域配配额申请失败," + bean);
		}
        // 打开工单
        if (bsConfig.getWorkTaskIsOpen().booleanValue()) {
            LocalInfo info = commonService.getAreaPlatInfo();
            if (null != info) {
                // 增加一条申请省级区域配额审批任务
                taskService.saveTask(new Task(applyId, TaskType.TASK_TYPE104.getCode(), commonService.getAreaPlatCustId(),
                        info.getPlatResNo(), info.getPlatNameCn()));
            } else {
                // 增加一条申请省级区域配额审批任务
                taskService.saveTask(new Task(applyId, TaskType.TASK_TYPE104.getCode(), commonService.getAreaPlatCustId()));
            }
        }
	}

	/**
	 * 分页查询二级区域配额申请
	 * 
	 * @Description:
	 * @param proviceNo
	 *            省编号
	 * @param applyType
	 *            申请类型
	 * @param status
	 *            审批状态
	 * @param exeCustId
	 *            受理人客户号
	 * @param page
	 *            分页参数
	 * @return
	 */
	@Override
	public PageData<ProvinceQuotaApp> queryProvinceQuotaPage(String proviceNo, Integer applyType, Integer status,
			String exeCustId, Page page)
	{
		if (null == page)
		{
			return new PageData<ProvinceQuotaApp>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 查询省级配额申请列表
		List<ProvinceQuotaApp> result = quotaMapper.queryProvinceQuotaListPage(proviceNo, applyType, status, exeCustId);
		if (StringUtils.isNotBlank(result))
		{
			return new PageData<ProvinceQuotaApp>(page.getCount(), result);
		}
		return new PageData<ProvinceQuotaApp>(0, null);
	}

	/**
	 * 根据id查询二级区域申请
	 * 
	 * @Description:
	 * @param applyId
	 *            申请编号
	 * @return
	 */
	@Override
	public ProvinceQuotaApp getProvinceQuotaById(String applyId)
	{
		return quotaMapper.getProvinceQuotaById(applyId);
	}

	/**
	 * 二级区域配额申请审批
	 * 
	 * @Description:
	 * @param bean
	 *            省级区域配额申请参数实体
	 * @throws HsException
	 */
	@Transactional
	@Override
	public void apprProvinceQuota(ProvinceQuotaApp bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "apprProvinceQuota" + bean, "input param:", "二级区域配额申请审批");
		// id
		String taskId = "";
		try
		{
			// 验证参数非null
			HsAssert.notNull(bean, RespCode.PARAM_ERROR, "二级区域配额申请审批参数为null," + bean);
			HsAssert.notNull(bean.getStatus(), RespCode.PARAM_ERROR, "二级区域配额审批状态为null," + bean);

			// 根据申请编号查询申请数据是否存在
			ProvinceQuotaApp param = quotaMapper.getProvinceQuotaById(bean.getApplyId());
			HsAssert.notNull(param, BSRespCode.BS_NOT_QUERY_DATA, "未查询配额申请待审批数据," + bean);

			// 验证审批状态
			HsAssert.isTrue(bean.getStatus() != ApprStatus.WAIT_APPR.getCode(),
					BSRespCode.BS_PROVINCE_QUOTA_APPR_STATUS_ERROR, "二级区域配配额审批状态错误," + bean);
			// 验证批复数量
			HsAssert.isTrue(bean.getApprNum() <= param.getApplyNum().intValue(), BSRespCode.BS_APPR_GREATER_APP_NUM,
					"审批数量大于申请数量," + bean);

			// 打开工单
			if (bsConfig.getWorkTaskIsOpen() != null && bsConfig.getWorkTaskIsOpen())
			{
				// 获取省级区域配额申请审批任务id
				taskId = taskService.getSrcTask(bean.getApplyId(), bean.getExeCustId());
				HsAssert.hasText(taskId, RespCode.PARAM_ERROR, "客户未查询待办的任务");
			}

			// 审批通过
			if (bean.getStatus() == ApprStatus.PASS.getCode())
			{
				// 首次、新增配置
				if (AppType.FIRST.getCode() == param.getApplyType() || AppType.ADD.getCode() == param.getApplyType())
				{
					// 验证地区平台配额数据是否够
					HsAssert.isTrue(bean.getApprNum() <= quotaMapper.countFreePlatQuotaOfManager(param.getEntResNo()),
							BSRespCode.BS_QUOTA_NOT_ENOUGH_FOR_ALLOT, "可分配的资源配额不足," + bean);
					// 查询未分配到省的互生号(升序查询)
					List<String> resNoList = quotaMapper.queryFreeQuotaOfManager(param.getEntResNo(),
							bean.getApprNum());
					if (StringUtils.isNotBlank(resNoList))
					{
						int size = resNoList.size();
						// 设置实际分配的资源号列表，保存起来以便查询
						bean.setResNoList(
								StringUtil.arrayToString((String[]) resNoList.toArray(new String[size]), ","));
						// 修改互生号的省编号
						quotaMapper.allotToProvince(resNoList, param.getProvinceNo());
					}
				} else
				{
					// 验证查询未分配到城市的省配额
					HsAssert.isTrue(bean.getApprNum() <= quotaMapper.countFreeProvinceQuota(param.getProvinceNo()),
							BSRespCode.BS_QUOTA_NOT_ENAUGH_FOR_RELEASE, "可减少资源配额不足," + bean);
					// 查询未分配到城市的省配额互生号(降序查询)
					List<String> resNoList = quotaMapper.queryFreeQuotaOfProvince(param.getProvinceNo(),
							bean.getApprNum(), true);
					if (StringUtils.isNotBlank(resNoList))
					{
						int size = resNoList.size();
						// 设置实际分配的资源号列表，保存起来以便查询
						bean.setResNoList(
								StringUtil.arrayToString((String[]) resNoList.toArray(new String[size]), ","));
					}
					// 释放二级区域配额
					quotaMapper.releaseFromProvince(resNoList, param.getProvinceNo());
				}
			}
			// 修改省配额审批
			quotaMapper.apprProvinceQuota(bean);

		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "apprProvinceQuota",
					BSRespCode.BS_PROVINCE_QUOTA_APPR_FAIL.getCode() + ":二级区域配配额审批失败," + bean, ex);
			throw new HsException(BSRespCode.BS_PROVINCE_QUOTA_APPR_FAIL, "二级区域配配额审批失败," + bean);
		}
		// 打开工单
		if (bsConfig.getWorkTaskIsOpen() != null && bsConfig.getWorkTaskIsOpen())
		{
			// 修改省配额审批任务完成
			taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());
		}
	}

	/**
	 * 三级区域配额申请
	 * 
	 * @Description:
	 * @param bean
	 *            申请市级配额参数实体
	 * @throws HsException
	 */
	@Transactional
	@Override
	public void applyCityQuota(CityQuotaApp bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "applyCityQuota", "input param:" + bean, "三级区域配额申请");
		// 申请编号
		String applyId = "";
		try
		{
			// 验证参数非null
			HsAssert.notNull(bean, RespCode.PARAM_ERROR, "三级区域配额申请参数为null," + bean);
			HsAssert.notNull(bean.getApplyType(), RespCode.PARAM_ERROR, "三级区域配额申请类型为null," + bean);
			// 验证城市是否有申请中的
			List<String> applyingCityNo = quotaMapper.selectApplyingCity(bean.getProvinceNo(), bean.getCityNo());
			HsAssert.isTrue(StringUtils.isBlank(applyingCityNo), BSRespCode.BS_CITY_HAS_APPLYING_DATA,
					"三级区域有申请的配额申请," + bean);
			String cityName = null;
			try
			{
				// 补充申请配额的城市名称
				City city = lcsClient.getCityById(lcsClient.getLocalInfo().getCountryNo(), bean.getProvinceNo(),
						bean.getCityNo());
				cityName = city.getCityName();
				bean.setCityName(cityName);
			} catch (Exception e)
			{
				SystemLog.error(this.getClass().getName(), "applyCityQuota", "获取城市名称出错", e);
			}

			// 首次、新增配置
			if (AppType.FIRST.getCode() == bean.getApplyType() || AppType.ADD.getCode() == bean.getApplyType())
			{
				// 验证未分配的城市的省配额是否够(包括三级申请待审批数量)
				HsAssert.isTrue(bean.getApplyNum() <= quotaMapper.countFreeProvinceQuota(bean.getProvinceNo()),
						BSRespCode.BS_QUOTA_NOT_ENOUGH_FOR_ALLOT, "可分配资源配额不足," + bean);
			} else
			{// 减少
				// 验证未申报成功和占用的城市配额是否够
				HsAssert.isTrue(bean.getApplyNum() <= quotaMapper.countFreeCityQuota(bean.getCityNo()),
						BSRespCode.BS_QUOTA_NOT_ENAUGH_FOR_RELEASE, "可减少资源配额不足," + bean);
			}
			applyId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
			bean.setApplyId(applyId);
			bean.setStatus(ApprStatus.WAIT_APPR.getCode());
			// 插入申请市级配额
			quotaMapper.applyCityQuota(bean);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "applyCityQuota",
					BSRespCode.BS_CITY_QUOTA_APPLY_FAIL.getCode() + ":三级区域配额申请失败 ," + bean, ex);
			throw new HsException(BSRespCode.BS_CITY_QUOTA_APPLY_FAIL, "三级区域配额申请失败," + bean);
		}
		// 打开工单
		if (bsConfig.getWorkTaskIsOpen().booleanValue())
		{
			LocalInfo info = commonService.getAreaPlatInfo();
			if (null != info)
			{
				// 增加一条申请市级配额审批任务
				taskService.saveTask(new Task(applyId, TaskType.TASK_TYPE106.getCode(),
						commonService.getAreaPlatCustId(), info.getPlatResNo(), info.getPlatNameCn()));
			} else
			{
				// 增加一条申请市级配额审批任务
				taskService.saveTask(
						new Task(applyId, TaskType.TASK_TYPE106.getCode(), commonService.getAreaPlatCustId()));
			}
		}
	}

	/**
	 * 分页查询三级区域配额申请
	 * 
	 * @Description:
	 * @param param
	 *            查询条件参数
	 * @param page
	 *            查询分页参数
	 * @return
	 */
	@Override
	public PageData<CityQuotaApp> queryCityQuotaPage(CityQuotaQueryParam param, Page page)
	{
		HsAssert.notNull(page, BSRespCode.BS_PARAMS_NULL, "分页对象[page]为null");
		// 设置分页参数
		PageContext.setPage(page);
		// 设置日期查询条件
		param.setQueryDate();
		// 查询城市配额申请列表
		List<CityQuotaApp> result = quotaMapper.queryCityQuotaListPage(param);
		return PageData.bulid(page.getCount(), result);
	}

	/**
	 * 根据id查询三级区域配额申请
	 * 
	 * @Description:
	 * @param applyId
	 *            申请编号
	 * @return
	 */
	@Override
	public CityQuotaApp getCityQuotaById(String applyId)
	{
		return quotaMapper.getCityQuotaById(applyId);
	}

	/**
	 * 三级区域配额申请审批
	 * 
	 * @Description:
	 * @param bean
	 * @throws HsException
	 */
	@Transactional
	@Override
	public void apprCityQuota(CityQuotaApp bean) throws HsException
	{
		BizLog.biz(this.getClass().getName(), "apprCityQuota", "input param:" + bean, "三级区域配额申请审批");
		// 任务id
		String taskId = "";
		try
		{
			// 验证非null
			HsAssert.notNull(bean, RespCode.PARAM_ERROR, "三级区域配额申请审批参数为null," + bean);
			HsAssert.notNull(bean.getStatus(), RespCode.PARAM_ERROR, "三级区域配额审批状态为null");

			// 查询申请编号的申请数据是否存在
			CityQuotaApp param = quotaMapper.getCityQuotaById(bean.getApplyId());
			HsAssert.notNull(param, BSRespCode.BS_NOT_QUERY_DATA, "未查询配额申请待审批数据," + bean);
			// 验证审批状态
			HsAssert.isTrue(bean.getStatus() != ApprStatus.WAIT_APPR.getCode(),
					BSRespCode.BS_CITY_QUOTA_APPR_STATUS_ERROR, "三级区域配额审批状态错误 ," + bean);
			// 验证批复数量
			HsAssert.isTrue(bean.getApprNum() <= param.getApplyNum().intValue(), BSRespCode.BS_APPR_GREATER_APP_NUM,
					"审批数量大于申请数量," + bean);

			// 打开工单
			if (bsConfig.getWorkTaskIsOpen() != null && bsConfig.getWorkTaskIsOpen())
			{
				// 获取三级区域配额申请审批任务id
				taskId = taskService.getSrcTask(bean.getApplyId(), bean.getExeCustId());
				HsAssert.hasText(taskId, RespCode.PARAM_ERROR, "客户未查询待办的任务," + bean);
			}

			// 审批通过
			if (bean.getStatus() == ApprStatus.PASS.getCode())
			{
				// 首次、新增配置
				if (AppType.FIRST.getCode() == param.getApplyType() || AppType.ADD.getCode() == param.getApplyType())
				{
					// 未分配到城市的省配额是否够
					HsAssert.isTrue(bean.getApprNum() <= quotaMapper.countFreeProvinceQuota(param.getProvinceNo()),
							BSRespCode.BS_QUOTA_NOT_ENOUGH_FOR_ALLOT, "可分配的资源配额数量不足," + bean);
					// 查询未分配到城市的省配额互生号(升序查询)
					List<String> resNoList = quotaMapper.queryFreeQuotaOfProvince(param.getProvinceNo(),
							bean.getApprNum(), false);
					if (StringUtils.isNotBlank(resNoList))
					{
						int size = resNoList.size();
						// 设置实际分配的资源号列表，保存起来以便查询
						bean.setResNoList(
								StringUtil.arrayToString((String[]) resNoList.toArray(new String[size]), ","));
						// 增加三级区域配额
						quotaMapper.allotToCity(resNoList, param.getCityNo());
						// 首次分配插入城市人口数
						if (AppType.FIRST.getCode() == param.getApplyType())
						{
							quotaMapper.insertCityPopulation(new CityPopulation(param.getCountryNo(),
									param.getProvinceNo(), param.getCityNo(), param.getPopulation()));
						}
					}
				} else
				{// 减少
					// 验证未申报的城市配额数量是否够
					HsAssert.isTrue(bean.getApprNum() <= quotaMapper.countFreeCityQuota(param.getCityNo()),
							BSRespCode.BS_QUOTA_NOT_ENAUGH_FOR_RELEASE, "可减少资源配额不足," + bean);
					// 查询未申报的城市配额的互生号(降序查询)
					List<String> resNoList = quotaMapper.queryFreeQuotaOfCity(param.getCityNo(), bean.getApprNum());
					if (StringUtils.isNotBlank(resNoList))
					{
						int size = resNoList.size();
						// 设置实际分配的资源号列表，保存起来以便查询
						bean.setResNoList(
								StringUtil.arrayToString((String[]) resNoList.toArray(new String[size]), ","));
						// 释放三级区域互生号
						quotaMapper.releaseFromCity(resNoList, param.getCityNo());
					}
				}
			}
			// 城市配额申请审批
			quotaMapper.apprCityQuota(bean);

		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "apprCityQuota",
					BSRespCode.BS_CITY_QUOTA_APPR_FAIL.getCode() + ":三级区域配额审批失败," + bean, ex);
			throw new HsException(BSRespCode.BS_CITY_QUOTA_APPR_FAIL, "三级区域配额审批失败");
		}
		// 打开工单
		if (bsConfig.getWorkTaskIsOpen() != null && bsConfig.getWorkTaskIsOpen())
		{
			// 修改城市配额申请审批任务完成
			taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());
		}
	}

	/**
	 * 查询地区平台分配了配额的省
	 * 
	 * @Description:
	 * @return
	 */
	@Override
	public List<AllotProvince> queryAllotProvinceList()
	{
		return quotaMapper.selectAllotProvinceList();
	}

	/**
	 * 查询指定二级区域(省或直辖市)下可以申请城市配额的城市配额列表
	 * 
	 * @Description:
	 * @param provinceNo
	 *            省编号
	 * @return
	 */
	@Override
	public List<QuotaStatOfCity> queryAppCityAllotList(String provinceNo)
	{
		if (StringUtils.isBlank(provinceNo))
		{
			return null;
		}
		// 分组统计省下面各城市资源配额情况
		List<QuotaStatOfCity> allotCity = quotaMapper.listQuotaStatGroupByCity(provinceNo, null);
		// 获取省下的所有城市列表
		List<City> citys = commonService.getCityByProvinceNo(provinceNo);
		if (StringUtils.isBlank(citys))
		{
			return allotCity;
		}
		List<QuotaStatOfCity> resultCity = new ArrayList<>();
		// 未进行分配过
		if (StringUtils.isBlank(allotCity))
		{
			for (City city : citys)
			{
				resultCity.add(new QuotaStatOfCity(city.getCityNo(), "0", "0", "0", "0", "0", "0", false));
			}
		} else
		{
			for (City city : citys)
			{
				// 过滤掉已经分配过的
				boolean falg = true;
				for (QuotaStatOfCity statCity : allotCity)
				{
					if (city.getCityNo().equals(statCity.getCityNo()))
					{
						falg = false;
						break;
					}
				}
				if (falg)
				{
					resultCity.add(new QuotaStatOfCity(city.getCityNo(), "0", "0", "0", "0", "0", "0", false));
				}
			}
			resultCity.addAll(allotCity);
		}
		// 查询本省有正在申请配额的城市
		List<String> applyingCityNos = quotaMapper.selectApplyingCity(provinceNo, null);
		if (StringUtils.isNotBlank(applyingCityNos))
		{
			for (QuotaStatOfCity city : resultCity)
			{
				if (applyingCityNos.contains(city.getCityNo()))
				{
					// 返回结果中要去掉正在申请配额的城市
					resultCity.remove(city);
					break;
				}
			}
		}
		return resultCity;
	}

	/**
	 * 统计城市配额分配使用情况
	 * 
	 * @param provinceNo
	 *            省代码
	 * @param cityNo
	 *            城市代码
	 * @return
	 */
	@Override
	public QuotaStatOfCity statQuotaByCity(String provinceNo, String cityNo)
	{
		HsAssert.hasText(provinceNo, RespCode.PARAM_ERROR, "统计城市配额分配使用情况参数省代码为null");
		HsAssert.hasText(cityNo, RespCode.PARAM_ERROR, "统计城市配额分配使用情况参数城市代码为null");
		QuotaStatOfCity result = quotaMapper.statQuotaByCity(provinceNo, cityNo);
		if (result == null)
		{
			result = new QuotaStatOfCity(cityNo, "0", "0", "0", "0", "0", "0", true);
		}
		return result;
	}

	/**
	 * 统计管理公司下的资源数据
	 * 
	 * @Description:
	 * @param mEntResNo
	 *            管理公司互生号
	 * @return
	 */
	@Override
	public QuotaStatOfManager statResDetailOfManager(String mEntResNo)
	{
		if (StringUtils.isBlank(mEntResNo) || !HsResNoUtils.isManageResNo(mEntResNo))
		{
			return null;
		}
		QuotaStatOfManager result = quotaMapper.statResDetailOfManager(mEntResNo);
		if (null != result)
		{
			result.setProvinceList(quotaMapper.listQuotaStatGroupByProvince(mEntResNo));
		}
		return result;
	}

	/**
	 * 统计省级(二级区域)下的资源数据
	 * 
	 * @Description:
	 * @param provinceNo
	 *            省编号
	 * @param cityNo
	 *            城市编号
	 * @return
	 */
	@Override
	public QuotaDetailOfProvince statResDetailOfProvince(String provinceNo, String cityNo)
	{
		QuotaDetailOfProvince result = quotaMapper.statResDetailOfProvince(provinceNo);
		if (null != result)
		{
			result.setCityList(quotaMapper.listQuotaStatGroupByCity(provinceNo, cityNo));
		}
		return result;
	}

	/**
	 * 城市(三级区域)下的资源列表
	 * 
	 * @Description:
	 * @param cityNo
	 *            城市编号
	 * @param status
	 *            资源状态
	 * @return
	 */
	@Override
	public List<ResInfo> listResInfoOfCity(String cityNo, String[] status)
	{
		return quotaMapper.listResInfoOfCity(cityNo, status);
	}

	/**
	 * 根据管理公司互生号查询可申请二级区域分配的省份 包含已分配该管理公司下级服务资源的省份以及从未进行二级区域分配的省份
	 * 
	 * @Description:
	 * @param mEntResNo
	 *            管理公司互生号
	 * @return
	 */
	@Override
	public List<String> listProvinceNoForAllot(String mEntResNo)
	{
		if (StringUtils.isBlank(mEntResNo) || !HsResNoUtils.isManageResNo(mEntResNo))
		{
			return null;
		}
		// 获取地区平台所有二级区域代码
		List<String> allProvince = commonService.getAreaPlatProvinceNo();
		if (StringUtils.isBlank(allProvince))
		{
			return null;
		}
		// 去掉已经分配了其它管理公司配额的二级区域代码
		allProvince.removeAll(quotaMapper.queryProvinceNoForAllotExcludeM(mEntResNo));
		// 删除掉正在申请配额中的省编号，其它管理公司正在申请配额的省份也不允许申请
		allProvince.removeAll(quotaMapper.selectApplyingProvince(null, null));
		return allProvince;
	}

	/**
	 * 管理公司二级配额分配详情
	 * 
	 * @Description:
	 * @param mEntResNo
	 *            管理公司互生号
	 * @return
	 */
	@Override
	public PlatAppManage queryManageAllotDetail(String mEntResNo)
	{
		if (StringUtils.isBlank(mEntResNo) || !HsResNoUtils.isManageResNo(mEntResNo))
		{
			return null;
		}
		return quotaMapper.selectManageAppProvinceDetail(mEntResNo);
	}

	/**
	 * 判断省份是否已进行首次配置
	 * 
	 * @Description:
	 * @param provinceNo
	 *            省编号
	 * @throws HsException
	 */
	@Override
	public void isProvinceFristAllot(String provinceNo) throws HsException
	{
		int count = quotaMapper.isProvinceFristAllot(provinceNo);
		if (count > 0)
		{
			throw new HsException(BSRespCode.BS_IS_NOT_FIRST_ALLOT, "不是首次配置," + provinceNo);
		}
	}

	/**
	 * 判断城市是否已进行首次配置
	 * 
	 * @Description:
	 * @param cityNo
	 *            城市编码
	 * @throws HsException
	 */
	@Override
	public void isCityFristAllot(String cityNo) throws HsException
	{
		int count = quotaMapper.isCityFristAllot(cityNo);
		if (count > 0)
		{
			throw new HsException(BSRespCode.BS_IS_NOT_FIRST_ALLOT, "不是首次配置," + cityNo);
		}
	}

	/**
	 * 查询二级区域（省）的总配额数量(用于省份减少配额)
	 * 
	 * @Description:
	 * @param provinceNo
	 *            省编号
	 * @return
	 */
	@Override
	public Integer countProvinceQuota(String provinceNo)
	{
		return quotaMapper.countProvinceQuota(provinceNo);
	}

	/**
	 * 查询三级区域（城市）的总配额数量(用于城市减少配额)
	 * 
	 * @Description:
	 * @param cityNo
	 *            城市编号
	 * @return
	 */
	@Override
	public Integer countCityQuota(String cityNo)
	{
		return quotaMapper.countCityQuota(cityNo);
	}

	/**
	 * 统计管理号未分配到二级区域(省)的资源配额数量 (包括待审批数量)
	 * 
	 * @Description:
	 * @param mEntResNo
	 *            管理公司互生号
	 * @return
	 */
	@Override
	public Integer countMResNoFreeQuota(String mEntResNo)
	{
		return quotaMapper.countFreePlatQuotaOfManager(mEntResNo);
	}

	/**
	 * 查询省下未分配到城市的配额数量 查询已分配到二级区域（省份），但未分配到三级区域（城市）的资源配额数量 (包括待审批数量)
	 * 
	 * @Description:
	 * @param provinceNo
	 *            省编号
	 * @return
	 */
	@Override
	public Integer countFreeProvinceQuota(String provinceNo)
	{
		return quotaMapper.countFreeProvinceQuotaAndW(provinceNo);
	}

	/**
	 * 查询城市下未申报的配额数量 城市配额中既没有被使用，也没有被占用的资源号数量
	 * 
	 * @Description:
	 * @param cityNo
	 *            城市编号
	 * @return
	 */
	@Override
	public Integer countFreeCityQuota(String cityNo)
	{
		return quotaMapper.countFreeCityQuota(cityNo);
	}

	/**
	 * 统计管理公司下的企业资源
	 * 
	 * @Description:
	 * @param mEntResNo
	 *            管理公司资源号
	 * @return
	 */
	@Override
	public CompanyRes statResCompanyResM(String mEntResNo)
	{
		if (StringUtils.isBlank(mEntResNo) || !HsResNoUtils.isManageResNo(mEntResNo))
		{
			return null;
		}
		return quotaMapper.statResCompanyResM(mEntResNo);
	}

	/**
	 * 分页分组统计服务公司的企业资源
	 * 
	 * @Description:
	 * @param mEntResNo
	 * @param page
	 * @return
	 */
	@Override
	public PageData<CompanyResS> queryCompanyResMByPage(String mEntResNo, Page page)
	{
		if (StringUtils.isBlank(mEntResNo) || !HsResNoUtils.isManageResNo(mEntResNo) || null == page)
		{
			return new PageData<>(0, null);
		}
		// 设置分页参数
		PageContext.setPage(page);
		// 分组统计服务公司的企业资源列表
		List<CompanyResS> companys = quotaMapper.queryCompanyResMByListPage(mEntResNo);
		if (StringUtils.isNotBlank(companys))
		{
			return new PageData<>(page.getCount(), companys);
		}
		return new PageData<>(0, null);
	}
}
