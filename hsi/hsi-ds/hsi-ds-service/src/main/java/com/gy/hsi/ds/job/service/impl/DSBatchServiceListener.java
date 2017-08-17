/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.ds.job.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsi.ds.common.constant.Columns;
import com.gy.hsi.ds.common.constant.JobConstant.ServiceState;
import com.gy.hsi.ds.common.contants.DSContants;
import com.gy.hsi.ds.common.spring.MyPropertyConfigurer;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.operator.Match;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.operator.Modify;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.param.AppenderParam;
import com.gy.hsi.ds.common.utils.StringUtils;
import com.gy.hsi.ds.common.utils.ThreadHelper;
import com.gy.hsi.ds.job.beans.bo.FrontPostJob;
import com.gy.hsi.ds.job.beans.bo.JobExecuteDetails;
import com.gy.hsi.ds.job.beans.bo.JobStatus;
import com.gy.hsi.ds.job.beans.bo.JobTempArgs;
import com.gy.hsi.ds.job.dao.IFrontPostJobDao;
import com.gy.hsi.ds.job.dao.IJobExecuteDetailsDao;
import com.gy.hsi.ds.job.dao.IJobStatusDao;
import com.gy.hsi.ds.job.service.IBusExecuteLogicServcie;
import com.gy.hsi.ds.job.service.IExecutionTempArgsCacheService;
import com.gy.hsi.ds.job.service.IJobCounterService;
import com.gy.hsi.ds.job.service.ILocalBatchServiceListener;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-ds-param
 * 
 *  Package Name    : com.gy.hsi.ds.job.service.impl
 * 
 *  File Name       : DSBatchServiceListener.java
 * 
 *  Creation Date   : 2016年1月29日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 调度状态回调监听
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service("batchServiceListener")
public class DSBatchServiceListener implements IDSBatchServiceListener,
		ILocalBatchServiceListener {

	private Logger logger = Logger.getLogger(DSBatchServiceListener.class);
	
	private String instanceNo = MyPropertyConfigurer
			.getProperty(DSContants.SYS_INST_NO);
	
	@Autowired
	private IJobStatusDao jobStatusDao;

	@Autowired
	private IJobExecuteDetailsDao jobExecuteDetailsDao;

	@Autowired
	private IFrontPostJobDao frontPostJobDao;

	@Autowired
	private IBusExecuteLogicServcie busExecuteLogicServcie;

	@Autowired
	private IExecutionTempArgsCacheService tempArgsCache;
	
	@Autowired
	private IJobCounterService jobCounterService;

	public DSBatchServiceListener() {
	}

	/**
	 * 作业执行状态上报[本地上报, 来源于自己的上报]
	 * 
	 * @param executeId 执行id
	 * @param status 作业执行状态 0 成功 1失败 2执行中
	 * @param desc 相对应执行状态的描述
	 */
	@Override
	public void reportStatusBySelf(String executeId, int status, String desc) {
		
		try {
			handleReportStatus(executeId, status, desc, true);
		} catch (Exception e) {
			logger.info("更新本地上报的进度状态时发生异常：", e);
		}
	}

	/**
	 * 作业执行状态上报[远端其他业务系统]
	 * 
	 * @param executeId 执行id
	 * @param status 作业执行状态 0 成功 1失败 2执行中
	 * @param desc 相对应执行状态的描述
	 */
	@Override
	public void reportStatus(String executeId, int status, String desc) {
		
		try {
			handleReportStatus(executeId, status, desc, false);
		} catch (Exception e) {
			logger.info("更新上报的进度状态时发生异常：", e);
		}
	}
	
	/**
	 * 处理作业执行状态上报(远端其他业务系统)
	 * 
	 * @param executeId 执行id
	 * @param status 作业执行状态 0 成功 1失败 2执行中
	 * @param desc 相对应执行状态的描述
	 * @param isFromSelf 是否来源于自己的上报
	 */
	private void handleReportStatus(String executeId, int status, String desc,
			boolean isFromSelf) {
		
		if (logger.isInfoEnabled()) {
			logger.info(StringUtils.joinString("reportStatus: executeId=", executeId
					+ ", status=" + status, ", isFromSelf=" + isFromSelf));
		}

		JobExecuteDetails detail = jobExecuteDetailsDao
				.getDetailByExecuteId(executeId);

		if (null != detail) {
			desc = getTempAndFixedArgsInDB(detail.getServiceName(),
					detail.getServiceGroup(), executeId, desc);

			detail.setServiceState(status);
			detail.setDesc(desc);
		} else {
			logger.debug("上报的执行id不存在！ executeId=" + executeId);

			return;
		}

		// added by zhangysh, date:2015-12-03
		this.updateJobStateAndDetails(detail, isFromSelf);

		// 8 成功获取后置任务列表，继续执行后置任务
		if ((ServiceState.SUCCESS == status)
				&& tempArgsCache.isRecurPost(executeId)) {
			// 查询出后置业务
			List<FrontPostJob> frontIsmeList = frontPostJobDao
					.getFrontIsMeList(detail.getServiceName(),
							detail.getServiceGroup());

			if ((null == frontIsmeList) || (0 >= frontIsmeList.size())) {
				return;
			}

			String newExecuteIdFromDb = null;
			String nextExecuteId = null;

			int size = frontIsmeList.size();
			
			if(2 <= size) {
				ThreadHelper.sleepAmomentByRandom();
			}

			for (int index = 0; index < size; index++) {
				FrontPostJob post = frontIsmeList.get(index);

				// ============== 获取新的执行id, 算法相对复杂 =============//
				if (null != post) {
					if (StringUtils.isEmpty(newExecuteIdFromDb)) {
						newExecuteIdFromDb = parseOutNextExecuteIdFrom(executeId);
					}

					if (1 < size) {
						nextExecuteId = parseOutNextExecuteId(
								newExecuteIdFromDb, index);
					} else {
						nextExecuteId = newExecuteIdFromDb;
					}

					busExecuteLogicServcie.recursiveServiceByFront(
							post.getServiceName(), post.getServiceGroup(),
							nextExecuteId);
				}
			}
		}
	}

	/**
	 * 结果状态更新： added by zhangysh, date:2015-12-03
	 * 
	 * @param detail
	 * @param isFromSelf
	 */
	private void updateJobStateAndDetails(JobExecuteDetails detail,
			boolean isFromSelf) {

		String executeId = detail.getExecuteId();
		String desc = StringUtils.cut2SpecialLength(detail.getDesc(), 2400);
		Integer status = detail.getServiceState();

		List<Modify> modifyList = new ArrayList<Modify>(3);
		modifyList.add(new Modify(Columns.EXECUTE_STATUS, status));
		modifyList.add(new Modify(Columns.EXECUTE_STATUS_DESC, desc));
		modifyList.add(new Modify(Columns.EXECUTE_STATUS_TIME, new Date()));

		try {
			// 7 更新业务执行状态
			// 更新其他业务系统上报的状态
			if (!isFromSelf) {
				jobStatusDao.updateStatus(detail.getServiceName(),
						detail.getServiceGroup(), status, executeId);
			}
			// 更新调度系统自己回写的状态
			else {
				jobStatusDao.updateStatusBySelf(detail.getServiceName(),
						detail.getServiceGroup(), status, executeId);
			}

		} catch (DataAccessException e) {
			logger.error("更新业务执行状态异常：", e);
		}

		try {
			// 8 更新详情状态
			jobExecuteDetailsDao.update(modifyList, new Match(
					Columns.DETAILS_ID, detail.getId()),
					assembleUpdatingDetailMatch(executeId));
		} catch (DataAccessException e) {
			logger.error("更新业务执行详情状态异常：", e);
		}
	}
	
	/**
	 * 生成串联执行的id
	 * 
	 * @param currExecuteId
	 * @return
	 */
	private String parseOutNextExecuteIdFrom(String currExecuteId) {

		String baseExecuteId = currExecuteId;

		int index = currExecuteId.indexOf('#');

		if (0 < index) {
			baseExecuteId = currExecuteId.substring(0, index);
		}

		int num = jobCounterService.getAndUpdateCounter(baseExecuteId);
		
		return StringUtils.joinString(baseExecuteId, "#",
				instanceNo.replaceFirst("^0", ""), "." + num);
	}
	
	/**
	 * @param newExecuteIdFromDb
	 * @param index
	 * @return
	 */
	private String parseOutNextExecuteId(String newExecuteIdFromDb, int index) {
		int _index = index + 1;

		return newExecuteIdFromDb + "-" + _index;
	}

	/**
	 * 获取任务执行详情描述信息
	 * 
	 * @param serviceName
	 * @param serviceGroup
	 * @param executeId
	 * @param desc
	 * @return
	 */
	private String getTempAndFixedArgsInDB(String serviceName,
			String serviceGroup, String executeId, String desc) {
		String argsInDB = StringUtils.joinString(getTempArgsInDB(executeId),
				getFixedArgsInDB(serviceName, serviceGroup));

		desc = StringUtils.cut2SpecialLength(desc, 2350 - argsInDB.length());

		return desc + argsInDB;
	}
	
	/**
	 * 获取临时参数
	 * 
	 * @param executeId
	 * @return
	 */
	private String getTempArgsInDB(String executeId) {

		JobTempArgs tmpArgs = tempArgsCache.getTempArgs(executeId);

		String servArgs = "";

		if (null != tmpArgs) {
			servArgs = StringUtils.avoidNull(tmpArgs.getTempArgs());
		}

		if (StringUtils.isEmpty(servArgs)) {
			servArgs = "N/A";
		} else {
			servArgs = StringUtils.joinString("{", servArgs, "}");
		}

		return servArgs = StringUtils.joinString(" |-->> 临时参数: ", servArgs);
	}
	
	/**
	 * 获取配置的固定参数
	 * 
	 * @param serviceName
	 * @param serviceGroup
	 * @return
	 */
	private String getFixedArgsInDB(String serviceName, String serviceGroup) {
		JobStatus jobStatus = jobStatusDao.getByNameAndGroup(serviceName,
				serviceGroup);

		String servArgs = "";

		if (null != jobStatus) {
			servArgs = StringUtils.avoidNull(jobStatus.getServicePara());
		}

		if (StringUtils.isEmpty(servArgs)) {
			servArgs = "N/A";
		} else {
			servArgs = StringUtils.joinString("{", servArgs, "}");
		}

		return servArgs = StringUtils.joinString(" ", "固定参数: ", servArgs);
	}

//	/**
//	 * 生成串联执行的id
//	 * 
//	 * @param currExecuteId
//	 * @param nextSablingIndex
//	 * @return
//	 */
//	private static String parseOutNextExecuteId(String currExecuteId,
//			int nextSablingIndex) {
//
//		String num = "";
//		String baseExecuteId = currExecuteId;
//
//		int index = currExecuteId.indexOf('#');
//
//		if (0 < index) {
//			num = currExecuteId.substring(index + 1);
//
//			baseExecuteId = currExecuteId.substring(0, index);
//		}
//
//		String layerNo = "0";
//		String sablingIndex = "1";
//
//		index = num.indexOf('-');
//
//		if (0 < index) {
//			layerNo = num.substring(0, index);
//			sablingIndex = num.substring(index + 1);
//		}
//
//		layerNo = String.valueOf(StringUtils.str2Int(layerNo) + 1);
//
//		sablingIndex = String.valueOf(StringUtils.str2Int(sablingIndex)
//				+ nextSablingIndex);
//
//		num = layerNo.concat("-").concat(sablingIndex);
//
//		return baseExecuteId.concat("#").concat(num);
//	}

	/**
	 * 组装Match对象
	 * 
	 * @param executeId
	 * @return
	 */
	private Match assembleUpdatingDetailMatch(String executeId) {
		StringBuilder buf = new StringBuilder();
		buf.append(" and (");
		buf.append(" (EXECUTE_ID != '").append(executeId).append("') ");
		buf.append(" or (EXECUTE_ID = '").append(executeId)
				.append("' and EXECUTE_STATUS != 0 and EXECUTE_STATUS != 1) ");
		buf.append(" )");

		return new Match(null, new AppenderParam(buf.toString()));
	}
}