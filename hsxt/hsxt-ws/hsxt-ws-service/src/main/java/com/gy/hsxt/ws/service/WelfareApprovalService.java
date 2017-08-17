package com.gy.hsxt.ws.service;

import static com.gy.hsxt.common.utils.DateUtil.DateTimeToString;
import static com.gy.hsxt.common.utils.StringUtils.isBlank;
import static com.gy.hsxt.common.utils.StringUtils.isNotBlank;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.bp.bean.BusinessSysParamItemsRedis;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.constant.TaskStatus;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.lcs.api.ILCSBaseDataService;
import com.gy.hsxt.tm.api.ITMTaskService;
import com.gy.hsxt.tm.bean.TmTask;
import com.gy.hsxt.tm.enumtype.TaskType;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderStatusInfoService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.ws.api.IWsApplyService;
import com.gy.hsxt.ws.api.IWsApprovalService;
import com.gy.hsxt.ws.api.IWsWelfareService;
import com.gy.hsxt.ws.bean.ApprovalDetail;
import com.gy.hsxt.ws.bean.ApprovalInfo;
import com.gy.hsxt.ws.bean.ApprovalQueryCondition;
import com.gy.hsxt.ws.bean.ApprovalRecord;
import com.gy.hsxt.ws.bean.GrantInfo;
import com.gy.hsxt.ws.bean.Task;
import com.gy.hsxt.ws.bean.WelfareApplyDetail;
import com.gy.hsxt.ws.bean.WelfareApplyInfo;
import com.gy.hsxt.ws.bean.WelfareQualification;
import com.gy.hsxt.ws.bean.WelfareQualify;
import com.gy.hsxt.ws.common.ApplyStatus;
import com.gy.hsxt.ws.common.ApprovalStatus;
import com.gy.hsxt.ws.common.WsErrorCode;
import com.gy.hsxt.ws.mapper.ApprovalRecordMapper;
import com.gy.hsxt.ws.mapper.GrantInfoMapper;
import com.gy.hsxt.ws.mapper.TaskMapper;
import com.gy.hsxt.ws.mapper.WelfareApplyRecordMapper;
import com.gy.hsxt.ws.mapper.WelfareQualificationMapper;

/**
 * 积分福利审批服务
 * 
 * @Package: com.gy.hsxt.ws.service
 * @ClassName: WelfareApprovalService
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2015-12-26 上午9:19:26
 * @version V1.0
 */
@Service
public class WelfareApprovalService extends BasicService implements IWsApprovalService {

	@Autowired
	ITMTaskService taskService;

	@Autowired
	IWsApplyService applyService;

	@Autowired
	private TaskMapper taskMapper;

	@Autowired
	private ApprovalRecordMapper approvalMapper;

	@Autowired
	private WelfareApplyRecordMapper applyMapper;

	@Autowired
	private IWsWelfareService wsWelfareService;

	@Autowired
	private GrantInfoMapper grantMapper;

	@Autowired
	public BusinessParamSearch businessParamSearch;

	@Autowired
	private IUCAsEntService entService;

	@Resource
	private ILCSBaseDataService lcsBaseDataService;

	@Autowired
	private WelfareQualificationMapper welfareQualificationMapper;

	@Autowired
	private IUCAsCardHolderStatusInfoService cardHolderStatusInfoService;

	/**
	 * 审批积分福利
	 * 
	 * @param approvalId
	 *            审批记录编号（主键ID） 必传
	 * @param approvalResult
	 *            审批结果 1 通过 2 不通过驳回 必传
	 * @param approvalStep
	 *            审批步骤 0：初审 1、复审 必传
	 * @param replyAmount
	 *            批复金额
	 * @param remark
	 *            审批备注信息 选传
	 * @throws HsException
	 * @see com.gy.hsxt.ws.api.IWsApprovalService#approvalWelfare(java.lang.String,
	 *      java.lang.Integer, java.lang.String, java.lang.Integer,
	 *      java.lang.String)
	 */
	@Transactional
	public void approvalWelfare(String approvalId, Integer approvalResult, String replyAmount,
			String remark) throws HsException {
		// 数据验证
		validParamIsEmptyOrNull(approvalId, "审批单编号[approvalId]为空");
		validParamIsEmptyOrNull(approvalResult, "审批结果参数[approvalResult]为空");
		if (approvalResult == 1) {
			validParamIsEmptyOrNull(replyAmount, "批复金额参数[replyAmount]为空");
		} else {
			replyAmount = null;
		}
		logInfo("审批单编号:--" + approvalId + "审批结果:--" + approvalResult + "批复金额:----" + replyAmount);

		// 更新审批记录
		ApprovalInfo approvalInfo = approvalMapper.selectByPrimaryKey(approvalId);
		if (isBlank(approvalInfo)) {
			throw new HsException(WsErrorCode.APPROVAL_NOT_EXIST.getCode(),
					WsErrorCode.APPROVAL_NOT_EXIST.getDesc());
		}
		if(ApprovalStatus.PENDING.getStatus() != approvalInfo.getApprovalStatus() ){
			throw new HsException(WsErrorCode.APPROVAL_NOT_EXIST.getCode(),
					"此审批单已经审批过了");
		}

		if (replyAmount != null) {
			approvalInfo.setApprovalAmount(new BigDecimal(replyAmount));
			validReplyAmount(approvalInfo);
		}
		approvalInfo.setApprovalReason(remark);
		approvalInfo.setApplyDate(DateTimeToString(new Date()));
		approvalInfo.setApprovalDate(new Timestamp(new Date().getTime()));
		approvalInfo.setApprovalStatus(approvalResult);

		// 如果审批驳回
		if (ApprovalStatus.REJECT.getStatus() == approvalResult) {
			approvalReject(approvalInfo);
			return;
		}

		// 如果是初审通过 生成一条复审记录 、生成一条工单记录，通知工单系统分配审批人
		if (approvalInfo.getApprovalStep() == 0) {
			firstApprovalPass(approvalInfo);
			return;
		}

		// 如果复审通过，需生成一张 发放记录单、通知工单系统 ，分配发放人 发放福利
		reApprovalPass(approvalInfo, replyAmount);
	}

	/**
	 * 验证赔付金额是否合理
	 * 
	 * @param approvalInfo
	 */
	private void validReplyAmount(ApprovalInfo approvalInfo) {
		// 意外最大赔付金额
		BigDecimal accidentInsuranceMaxPayAmount = new BigDecimal(3000);
		// 身故最大赔付金额
		BigDecimal deathMaxPayAmount = new BigDecimal(60000);
		// 从业务参数系统 获取积分福利相关参数组
		Map<String, BusinessSysParamItemsRedis> parmGroup = businessParamSearch
				.searchSysParamItemsByGroup(BusinessParam.JF_WELFARE.getCode());

		// 获取意外保障最大赔付金额参数
		BusinessSysParamItemsRedis paramItem = parmGroup
				.get(BusinessParam.P_ACCIDENT_INSURANCE_SUBSIDIES_YEAR_MAX.getCode());
		if (paramItem != null) {
			accidentInsuranceMaxPayAmount = new BigDecimal(paramItem.getSysItemsValue().trim());
		}
		// 获取身故保障保障最大赔付金额参数
		paramItem = parmGroup.get(BusinessParam.P_DEATH_SUBSIDIES_YEAR_MAX.getCode());
		if (paramItem != null) {
			deathMaxPayAmount = new BigDecimal(paramItem.getSysItemsValue().trim());
		}
		/** 福利类型 0 意外伤害 1 免费医疗 2 他人身故 */
		int welfareType = approvalInfo.getWelfareType();
		if (2 == welfareType) {
			if (approvalInfo.getApprovalAmount().longValue() > deathMaxPayAmount.longValue()) {
				throw new HsException(WsErrorCode.APPROVAL_AMOUNT_MORE_THAN_THRESHOLD.getCode(),
						"审批金额超过最大补贴余额");
			}
		}
		if (0 == welfareType) {
			if (approvalInfo.getApprovalAmount().longValue() > accidentInsuranceMaxPayAmount
					.longValue()) {
				throw new HsException(WsErrorCode.APPROVAL_AMOUNT_MORE_THAN_THRESHOLD.getCode(),
						"审批金额超过最大补贴余额");
			}
			if (approvalInfo.getApprovalAmount().longValue() > approvalInfo.getSubsidyBalance()
					.longValue()) {
				throw new HsException(WsErrorCode.APPROVAL_AMOUNT_MORE_THAN_THRESHOLD.getCode(),
						"审批金额超过最大补贴余额");
			}
		}
	}

	private void approvalReject(ApprovalInfo approvalInfo) throws HsException {
		// 更新审批单信息
		approvalMapper.updateByPrimaryKeySelective(approvalInfo);

		// 更新申请单信息
		WelfareApplyInfo applyInfo = new WelfareApplyInfo();
		applyInfo.setApplyWelfareNo(approvalInfo.getApplyWelfareNo());
		applyInfo.setApprovalStatus(ApplyStatus.REJECT.getStatus());
		applyMapper.updateByPrimaryKeySelective(applyInfo);

		try {
			// 通知工单系统更改 工单状态
			taskService.updateTaskStatus(approvalInfo.getTaskId(), TaskStatus.COMPLETED.getCode(),
					null);
		} catch (Exception e) {
			logError("call HS-TM sys updateTaskStatus method error!", e);
			throw new HsException(WsErrorCode.INVOKE_TM_SYS_ERROR.getCode(),
					WsErrorCode.INVOKE_TM_SYS_ERROR.getDesc());
		}
	}

	private void firstApprovalPass(ApprovalInfo approvalInfo) throws HsException {
		// 更新初审单信息
		approvalMapper.updateByPrimaryKeySelective(approvalInfo);

		// 生成复审工单
		TmTask reApporvalTask = generateTmTask(approvalInfo.getApplyWelfareNo(),
				TaskType.TASK_TYPE117.getCode());
		reApporvalTask.setHsResNo(approvalInfo.getProposerResNo());
		reApporvalTask.setCustName(approvalInfo.getProposerName());

		// 生成复审单
		ApprovalInfo reApprovalInfo = ApprovalInfo.buildApprovalRecord();
		reApprovalInfo.setApprovalStep(1);
		reApprovalInfo.setApplyWelfareNo(approvalInfo.getApplyWelfareNo());
		reApprovalInfo.setTaskId(reApporvalTask.getTaskId());
		reApprovalInfo.setSubsidyBalance(approvalInfo.getSubsidyBalance());
		approvalMapper.insertSelective(reApprovalInfo);

		try {
			// 通知工单系统更改 初审工单状态
			taskService.updateTaskStatus(approvalInfo.getTaskId(), TaskStatus.COMPLETED.getCode(),
					null);
			taskService.saveTMTask(reApporvalTask);
		} catch (Exception e) {
			logError("call HS-TM sys updateTaskStatus/saveTMTask method error!", e);
			throw new HsException(WsErrorCode.INVOKE_TM_SYS_ERROR.getCode(),
					WsErrorCode.INVOKE_TM_SYS_ERROR.getDesc() + e.getMessage());
		}
	}

	private void reApprovalPass(ApprovalInfo approvalInfo, String replyAmount) throws HsException {
		BigDecimal subsidyBalanceBeforeApproval = approvalInfo.getSubsidyBalance();// 审批前余额
		// 更新复审单信息
		if (null != approvalInfo.getSubsidyBalance()) {
			approvalInfo.setSubsidyBalance(approvalInfo.getSubsidyBalance().subtract(
					new BigDecimal(replyAmount)));
		}
		approvalMapper.updateByPrimaryKeySelective(approvalInfo);

		// 生成发放工单
		TmTask grantTask = generateTmTask(approvalInfo.getApplyWelfareNo(),
				TaskType.TASK_TYPE118.getCode());
		grantTask.setHsResNo(approvalInfo.getProposerResNo());
		grantTask.setCustName(approvalInfo.getProposerName());

		// 更新申请单信息
		WelfareApplyInfo applyInfo = applyMapper.selectByPrimaryKey(approvalInfo
				.getApplyWelfareNo());
		applyInfo.setApprovalStatus(ApplyStatus.SUCCESS.getStatus());
		applyInfo.setHsPayAmount(new BigDecimal(replyAmount));
		applyInfo.setUpdatedDate(Timestamp.valueOf(DateUtil.getCurrentDateTime()));
		applyMapper.updateByPrimaryKeySelective(applyInfo);
		logInfo("update apply info sucess! applyWelfareNo:----" + approvalInfo.getApplyWelfareNo());

		/** 福利类型 0 意外伤害 1 免费医疗 2 他人身故 */
		int welfareType = applyInfo.getWelfareType();
		WelfareQualification welfareQualify = null;
		if (0 == welfareType) {
			// 更新福利补贴余额
			welfareQualify = welfareQualificationMapper
					.selectByPrimaryKey(applyInfo.getWelfareId());
			if (welfareQualify.getSubsidyBalance() != null) {
				welfareQualify.setSubsidyBalance(welfareQualify.getSubsidyBalance().subtract(
						new BigDecimal(replyAmount)));
			}
			welfareQualify.setUpdatedDate(Timestamp.valueOf(DateUtil.getCurrentDateTime()));
			welfareQualificationMapper.updateByPrimaryKeySelective(welfareQualify);
			// 更新其他待审批单的补贴余额
			approvalMapper.updateSubsidyBalance(applyInfo.getHsResNo(), welfareType,
					welfareQualify.getSubsidyBalance());

		}

		if (2 == welfareType) {
			// 更新福利补贴余额为0
			welfareQualify = welfareQualificationMapper
					.selectByPrimaryKey(applyInfo.getWelfareId());
			welfareQualify.setSubsidyBalance(new BigDecimal(0));// 设置补贴余额为0
			welfareQualify.setUpdatedDate(Timestamp.valueOf(DateUtil.getCurrentDateTime()));
			welfareQualificationMapper.updateByPrimaryKeySelective(welfareQualify);
			// 更新其他待审批单的补贴余额
			approvalMapper.updateSubsidyBalance(applyInfo.getDeathResNo(), welfareType,
					new BigDecimal(0));
		}

		// 生成发放单
		GrantInfo grantInfo = GrantInfo.buildDefaultGrantInfo();
		grantInfo.setApplyWelfareNo(approvalInfo.getApplyWelfareNo());
		grantInfo.setHsPayAmount(new BigDecimal(replyAmount));
		grantInfo.setSubsidyBalance(subsidyBalanceBeforeApproval);
		grantInfo.setApprovalDate(new Timestamp(new Date().getTime()));
		grantInfo.setTaskId(grantTask.getTaskId());
		grantMapper.insertSelective(grantInfo);

		// 如果是申请身故保障，修改互生卡状态为停用
		if (2 == welfareType) {
			cardHolderStatusInfoService.updateHsCardStatus(welfareQualify.getCustId(), 3,
					"已申请身故保障金，修改互生卡状态为停用", approvalInfo.getApproverCustId());
		}

		try {
			// 通知工单系统更改 复审工单状态
			taskService.updateTaskStatus(approvalInfo.getTaskId(), TaskStatus.COMPLETED.getCode(),
					null);
			taskService.saveTMTask(grantTask);
		} catch (Exception e) {
			logError("call HS-TM sys updateTaskStatus/saveTMTask method error!", e);
			throw new HsException(WsErrorCode.INVOKE_TM_SYS_ERROR.getCode(),
					WsErrorCode.INVOKE_TM_SYS_ERROR.getDesc());
		}
	}

	private TmTask generateTmTask(String applyWelfareNo, String taskType) {
		TmTask tmTask = Task.buildTmTask();
		String paltFormCustId = entService.findEntCustIdByEntResNo(lcsBaseDataService
				.getLocalInfo().getPlatResNo());
		tmTask.setEntCustId(paltFormCustId);
		tmTask.setBizNo(applyWelfareNo);
		tmTask.setBizType(taskType);
		return tmTask;
	}

	/**
	 * 查询审批记录
	 * 
	 * @param queryCondition
	 *            查询条件 必传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.ws.api.IWsApprovalService#listApprovalRecord(com.gy.hsxt.ws.bean.ApprovalQueryCondition,
	 *      com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<ApprovalRecord> listApprovalRecord(ApprovalQueryCondition queryCondition,
			Page page) throws HsException {
		return pageApproval(queryCondition, page, null);
	}

	private PageData<ApprovalRecord> pageApproval(ApprovalQueryCondition queryCondition, Page page,
			Integer approvalStatus) {
		// 验证数据
		validParamIsEmptyOrNull(page, "分页参数为null");

		// 查询总记录数
		int count = approvalMapper.countApprovalInfo(queryCondition, approvalStatus);
		if (count == 0) {
			return null;
		}

		// 分页查询审批数据
		List<ApprovalInfo> list = approvalMapper.pageApprovalInfo(queryCondition, page,
				approvalStatus);

		// 构造返回数据
		List<ApprovalRecord> result = new ArrayList<ApprovalRecord>();
		for (ApprovalInfo approvalInfo : list) {
			ApprovalRecord record = new ApprovalRecord();
			BeanUtils.copyProperties(approvalInfo, record);
			if (approvalInfo.getSubsidyBalance() != null) {
				record.setSubsidyBalance(approvalInfo.getSubsidyBalance().toString());
			}
			result.add(record);
		}

		return new PageData<>(count, result);
	}

	/**
	 * 查询待审批记录
	 * 
	 * @param queryCondition
	 *            查询条件 必传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.ws.api.IWsApprovalService#listPendingApproval(com.gy.hsxt.ws.bean.ApprovalQueryCondition,
	 *      com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<ApprovalRecord> listPendingApproval(ApprovalQueryCondition queryCondition,
			Page page) throws HsException {
		queryCondition.validParam();
		return pageApproval(queryCondition, page, ApprovalStatus.PENDING.getStatus());
	}

	/**
	 * 查询积分福利审批记录详情
	 * 
	 * @param approvalId
	 *            审批记录编号 必传
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.ws.api.IWsApprovalService#queryApprovalDetail(java.lang.String)
	 */
	@Override
	public ApprovalDetail queryApprovalDetail(String approvalId) throws HsException {
		// 验证数据
		validParamIsEmptyOrNull(approvalId, "审批记录编号为空!");

		// 查询审批基本信息
		ApprovalInfo approvalInfo = approvalMapper.selectByPrimaryKey(approvalId);
		if (approvalInfo == null) {
			throw new HsException(WsErrorCode.APPROVAL_NOT_EXIST.getCode(),
					WsErrorCode.APPROVAL_NOT_EXIST.getDesc());
		}
		ApprovalDetail approvalDetail = new ApprovalDetail();

		// 查询申请信息
		WelfareApplyDetail applyDetail = applyService.queryWelfareApplyDetail(approvalInfo
				.getApplyWelfareNo());
		approvalDetail.setApplyInfo(applyDetail);

		// 查询福利资格信息
		if (isNotBlank(approvalInfo.getWelfareId())) {
			WelfareQualification welfareQualify = welfareQualificationMapper
					.selectByPrimaryKey(approvalInfo.getWelfareId());
			WelfareQualify qualify = welfareQualify.generateWelfareQualify();
			if (null != approvalInfo.getSubsidyBalance()) {
				qualify.setSubsidyBalance(approvalInfo.getSubsidyBalance().toString());
			}
			approvalDetail.setWelfareQualify(qualify);
		} else {
			WelfareQualify qualify = new WelfareQualify();
			approvalDetail.setWelfareQualify(qualify);
		}

		// 如果是初审
		if (approvalInfo.getApprovalStep() == 0) {
			approvalDetail.setFirstApprovalInfo(approvalInfo.generateApprovalRecord());
		}

		// 如果是复审，还要查询初审信息
		if (approvalInfo.getApprovalStep() == 1) {
			ApprovalInfo firstApprovalInfo = approvalMapper.selectByApprovalStep(0,
					approvalInfo.getApplyWelfareNo());
			approvalDetail.setFirstApprovalInfo(firstApprovalInfo.generateApprovalRecord());

			approvalDetail.setReApprovalInfo(approvalInfo.generateApprovalRecord());
		}

		return approvalDetail;
	}

	/**
	 * 更新任务
	 * 
	 * @param bizNo
	 *            业务编号
	 * @param taskStatus
	 *            任务状态
	 * @param remark
	 *            备注
	 * @throws HsException
	 * @see com.gy.hsxt.ws.api.IWsApplyService#updateTask(java.lang.String,
	 *      java.lang.Integer, java.lang.String)
	 */
	@Override
	public void updateTask(String bizNo, Integer taskStatus, String remark) throws HsException {
		// 参数验证
		validParamIsEmptyOrNull(bizNo, "必传参数[bizNo]为空");
		validParamIsEmptyOrNull(taskStatus, "必传参数[taskStatus]为空");

		taskService.updateTaskStatus(bizNo, taskStatus, remark);
	}

}
