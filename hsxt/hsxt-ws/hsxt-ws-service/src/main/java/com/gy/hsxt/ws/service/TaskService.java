/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.ws.service;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tm.api.ITMSyncTaskService;
import com.gy.hsxt.tm.enumtype.TaskType;
import com.gy.hsxt.ws.bean.ApprovalInfo;
import com.gy.hsxt.ws.bean.ClaimsAccountingInfo;
import com.gy.hsxt.ws.bean.GrantInfo;
import com.gy.hsxt.ws.common.WsErrorCode;
import com.gy.hsxt.ws.mapper.ApprovalRecordMapper;
import com.gy.hsxt.ws.mapper.ClaimsAccountingInfoMapper;
import com.gy.hsxt.ws.mapper.GrantInfoMapper;
import com.gy.hsxt.ws.mapper.TaskMapper;

/**
 * 供工单系统 回调更新 工单审批人相关信息
 * 
 * @Package: com.gy.hsxt.ws.service
 * @ClassName: TaskService
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-12-8 下午5:54:15
 * @version V1.0
 */
@Service
public class TaskService extends BasicService implements ITMSyncTaskService {
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private ApprovalRecordMapper provalMapper;
	@Autowired
	private GrantInfoMapper grantMapper;
	@Autowired
	private ClaimsAccountingInfoMapper accountingMapper;

	/**
	 * 更新任务执行人
	 * 
	 * @param exeCustId
	 *            经办人编号
	 * @param exeCustName
	 *            经办人名称
	 * @param assignHis
	 *            派单历史
	 * @param taskId
	 *            任务编号
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.tm.api.ITMSyncTaskService#updateSrcTaskExecutor(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional
	public int updateSrcTaskExecutor(String exeCustId, String exeCustName, String assignHis,
			String taskId) throws HsException {
		logInfo("TM sys Callback end!-----");
		return 0;
	}

	/**
	 * 更新源任务执行人
	 * 
	 * @param exeCustId
	 *            经办人编号
	 * @param exeCustName
	 *            经办人名称
	 * @param assignHis
	 *            派单历史
	 * @param taskId
	 *            业务类型
	 * @param taskId
	 *            任务编号
	 * @throws HsException
	 */
	public int updateSrcTaskExecutor(String exeCustId, String exeCustName, String assignHis,
			String bizType, String taskId) throws HsException {
		logInfo("TM sys Callback begin!-----");
		logInfo("taskId：-----" + taskId);
		logInfo("exeCustId：-----" + exeCustId);
		logInfo("exeCustName：-----" + exeCustName);
		logInfo("bizType：-----" + bizType);
		// 验证参数
		validParamIsEmptyOrNull(taskId, "工单编号参数[taskId]为空");

		// 如果是积分福利审批任务
		if (TaskType.TASK_TYPE117.getCode().equals(bizType)||TaskType.TASK_TYPE116.getCode().equals(bizType)) {
			// 更新审批单信息
			ApprovalInfo approvalInfo = provalMapper.selectByTaskId(taskId);
			if (isBlank(approvalInfo)) {
				logError("approval order not exist! taskId-----:"+taskId, new HsException(WsErrorCode.APPROVAL_NOT_EXIST.getCode(),
						WsErrorCode.APPROVAL_NOT_EXIST.getDesc()));
				return 0;
			}
			approvalInfo.setApprover(exeCustName);
			approvalInfo.setApproverCustId(exeCustId);
			provalMapper.updateByPrimaryKeySelective(approvalInfo);
			// 更新核算单信息
			ClaimsAccountingInfo claimsAccountingInfo = new ClaimsAccountingInfo();
			claimsAccountingInfo.setAccountPersonCustId(exeCustId);
			claimsAccountingInfo.setAccountPersonName(exeCustName);
			claimsAccountingInfo.setTaskId(taskId);
			accountingMapper.updateByTaskId(claimsAccountingInfo);
			logInfo("TM sys Callback end!-----");
			return 1;
		}

		// 如果是积分福利发放业务
		if (TaskType.TASK_TYPE118.getCode().equals(bizType)) {
			// 更新发放单信息
			GrantInfo grantInfo = grantMapper.selectByTaskId(taskId);
			if (isBlank(grantInfo)) {
				logError("grant order not exist! taskId-----:"+taskId, new HsException(WsErrorCode.GRANT_NOT_EXIST.getCode(),
						WsErrorCode.GRANT_NOT_EXIST.getDesc()));
				return 0;
			}
			grantInfo.setGivingPersonCustId(exeCustId);
			grantInfo.setGivingPersonName(exeCustName);
			grantMapper.updateByPrimaryKeySelective(grantInfo);
			logInfo("TM sys Callback end!-----");
			return 1;
		}

		logInfo("TM sys Callback end!-----");
		return 0;
	}

}
