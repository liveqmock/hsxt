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
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.lcs.api.ILCSBaseDataService;
import com.gy.hsxt.tm.api.ITMTaskService;
import com.gy.hsxt.tm.bean.TmTask;
import com.gy.hsxt.tm.enumtype.TaskType;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.ws.api.IWsApplyService;
import com.gy.hsxt.ws.bean.AccidentSecurityApply;
import com.gy.hsxt.ws.bean.ApplyQueryCondition;
import com.gy.hsxt.ws.bean.ApprovalInfo;
import com.gy.hsxt.ws.bean.ClaimsAccountingInfo;
import com.gy.hsxt.ws.bean.ImgInfo;
import com.gy.hsxt.ws.bean.MedicalSubsidiesApply;
import com.gy.hsxt.ws.bean.OthersDieSecurityApply;
import com.gy.hsxt.ws.bean.Task;
import com.gy.hsxt.ws.bean.WelfareApplyDetail;
import com.gy.hsxt.ws.bean.WelfareApplyInfo;
import com.gy.hsxt.ws.bean.WelfareApplyRecord;
import com.gy.hsxt.ws.bean.WelfareQualification;
import com.gy.hsxt.ws.common.WelfareType;
import com.gy.hsxt.ws.common.WsErrorCode;
import com.gy.hsxt.ws.mapper.ApprovalRecordMapper;
import com.gy.hsxt.ws.mapper.ClaimsAccountingInfoMapper;
import com.gy.hsxt.ws.mapper.ImgInfoMapper;
import com.gy.hsxt.ws.mapper.TaskMapper;
import com.gy.hsxt.ws.mapper.WelfareApplyRecordMapper;
import com.gy.hsxt.ws.mapper.WelfareQualificationMapper;

/**
 * 积分福利申请服务实现类
 * 
 * @Package: com.gy.hsxt.ws.welfareApply.service.WelfareApplyService
 * @ClassName: WelfareApplyMapper
 * @Description: 积分福利申请
 * 
 * @author: chenhongzhi
 * @date: 2015-11-11 下午8:14:19
 * @version V3.0
 */
@Service
public class WelfareApplyService extends BasicService implements IWsApplyService {
	@Autowired
	private WelfareApplyRecordMapper welfareApplyMapper;
	@Autowired
	private ImgInfoMapper imgMapper;
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private ITMTaskService taskService;
	@Autowired
	private ApprovalRecordMapper approvalMapper;
	@Autowired
	private ClaimsAccountingInfoMapper accountingInfoMapper;
	@Autowired
	public BusinessParamSearch businessParamSearch;
	@Autowired
	private WelfareQualificationMapper welfareQualifyMapper;
	@Autowired
	private IUCAsEntService entService;
	@Resource
	private ILCSBaseDataService lcsBaseDataService;

	/**
	 * 意外伤害保障金申请
	 * 
	 * @param accidentSecurityApply
	 * @throws HsException
	 * @see com.gy.hsxt.ws.api.IWsApplyService#applyAccidentSecurity(com.gy.hsxt.ws.bean.AccidentSecurityApply)
	 */
	@Transactional
	public void applyAccidentSecurity(AccidentSecurityApply accidentSecurityApply)
			throws HsException {
		// 数据验证
		validParamIsEmptyOrNull(accidentSecurityApply, "申请对象参数为null");
		// 基本参数验证
		accidentSecurityApply.basicValid();

		WelfareApplyInfo record = WelfareApplyInfo.buildWelfareApplyRecord(accidentSecurityApply);
		record.setWelfareType(WelfareType.ACCIDENT_SECURITY.getType());
		// 验证是否资格申请
		WelfareQualification welfareQualify = welfareQualifyMapper.findWelfareQualify(accidentSecurityApply.getHsResNo());
		if (null== welfareQualify||1==welfareQualify.getWelfareType()) {
			throw new HsException(WsErrorCode.NO_QUALIFY_TO_APPLY.getCode(), "您还没有达到申请福利的条件");
		}
		record.setWelfareId(welfareQualify.getWelfareId());
		//验证意外保障金余额
		if(welfareQualify.getSubsidyBalance().intValue()<1){
			throw new HsException(WsErrorCode.APPROVAL_AMOUNT_MORE_THAN_THRESHOLD.getCode(), "你的意外保障金余额不足");
		}
		apply(record,welfareQualify);
	}

	/**
	 * 医疗补贴申请
	 * 
	 * @param medicalSubsidiesApply
	 * @throws HsException
	 * @see com.gy.hsxt.ws.api.IWsApplyService#applyMedicalSubsidies(com.gy.hsxt.ws.bean.MedicalSubsidiesApply)
	 */
	@Transactional
	public void applyMedicalSubsidies(MedicalSubsidiesApply medicalSubsidiesApply)
			throws HsException {
		// 数据验证
		validParamIsEmptyOrNull(medicalSubsidiesApply, "申请对象参数为null");
		// 基本参数验证
		medicalSubsidiesApply.basicValid();

		WelfareApplyInfo record = WelfareApplyInfo.buildWelfareApplyRecord(medicalSubsidiesApply);
		if (isNotBlank(medicalSubsidiesApply.getStartDate())) {
			Timestamp startDate = Timestamp.valueOf(medicalSubsidiesApply.getStartDate()
					+ " 00:00:00");
			record.setStartDate(startDate);
		}
		if (isNotBlank(medicalSubsidiesApply.getEndDate())) {
			Timestamp endDate = Timestamp.valueOf(medicalSubsidiesApply.getEndDate() + " 00:00:00");
			record.setEndDate(endDate);
		}
		record.setWelfareType(WelfareType.MEDICAL_SUBSIDIES.getType());
		
		// 验证是否资格申请
		WelfareQualification welfareQualify = welfareQualifyMapper.findWelfareQualify(record.getHsResNo());
		if (null== welfareQualify||0==welfareQualify.getWelfareType()) {
			throw new HsException(WsErrorCode.NO_QUALIFY_TO_APPLY.getCode(), "您还没有达到申请福利的条件");
		}
		record.setWelfareId(welfareQualify.getWelfareId());
		apply(record,welfareQualify);
	}

	/**
	 * 代他人身故保障金申请
	 * 
	 * @param othersDieSecurityApply
	 * @throws HsException
	 * @see com.gy.hsxt.ws.api.IWsApplyService#applyDieSecurity(com.gy.hsxt.ws.bean.OthersDieSecurityApply)
	 */
	@Transactional
	public void applyDieSecurity(OthersDieSecurityApply othersDieSecurityApply) throws HsException {
		// 数据验证
		validParamIsEmptyOrNull(othersDieSecurityApply, "申请对象参数为null");
		// 基本参数验证
		othersDieSecurityApply.basicValid();
		// 验证申请人跟身故人是否同一人
		if (othersDieSecurityApply.getHsResNo().equals(othersDieSecurityApply.getDeathResNo())) {
			throw new HsException(WsErrorCode.APPLY_PERSON_IS_SAME_OF_DEATH_PERSION.getCode(),
					WsErrorCode.APPLY_PERSON_IS_SAME_OF_DEATH_PERSION.getDesc());
		}
		// 验证是否有资格申请
		WelfareQualification welfareQualify = welfareQualifyMapper.findWelfareQualify(othersDieSecurityApply.getDeathResNo());
		if (null== welfareQualify||1==welfareQualify.getWelfareType()) {
			throw new HsException(WsErrorCode.NO_QUALIFY_TO_APPLY.getCode(), "您还没有达到申请福利的条件");
		}
		// 验证身故人是否有人代它申请或已经受理成功了
		List<WelfareApplyInfo> applyRecordList = welfareApplyMapper
				.queryApplyRecordByDeathPersonResNo(othersDieSecurityApply.getDeathResNo());
		
		if (applyRecordList != null && applyRecordList.size() > 0) {
			WelfareApplyInfo applyInfo = applyRecordList.get(0);
			if (0 == applyInfo.getApprovalStatus()) {
				if (othersDieSecurityApply.getHsResNo().equals(applyInfo.getHsResNo())) {
					throw new HsException(WsErrorCode.SELF_APPLYING_DEATH.getCode(),
							WsErrorCode.SELF_APPLYING_DEATH.getDesc());
				} else {
					throw new HsException(WsErrorCode.OTHER_APPLYING_DEATH.getCode(),
							WsErrorCode.OTHER_APPLYING_DEATH.getDesc());

				}
			}
			if (1 == applyInfo.getApprovalStatus()) {
				throw new HsException(WsErrorCode.DEATH_APPLY_PASS.getCode(),
						WsErrorCode.DEATH_APPLY_PASS.getDesc());
			}
		}

		WelfareApplyInfo record = WelfareApplyInfo.buildWelfareApplyRecord(othersDieSecurityApply);
		record.setWelfareId(welfareQualify.getWelfareId());
		record.setWelfareType(WelfareType.DIE_SECURITY.getType());
		apply(record,welfareQualify);
	}

	private void apply(WelfareApplyInfo record,WelfareQualification welfareQualify) {
		// 生成任务单编号
		TmTask approvalTask = generateTmTask(record.getApplyWelfareNo(),
				TaskType.TASK_TYPE116.getCode());
		approvalTask.setHsResNo(record.getHsResNo());
		approvalTask.setCustName(record.getProposerName());
		logInfo("apply tmTaskId:----------" + approvalTask.getTaskId());

		// 生成一条审批记录
		ApprovalInfo approvalInfo = ApprovalInfo.buildApprovalRecord();
		approvalInfo.setApplyWelfareNo(record.getApplyWelfareNo());
		approvalInfo.setTaskId(approvalTask.getTaskId());

		// 设置意外伤害补贴余额
		if (record.getWelfareType() == 0) {
			approvalInfo.setSubsidyBalance(welfareQualify.getSubsidyBalance());
		}
		// 设置身故最大赔付余额
		if (record.getWelfareType() == 2) {
			BigDecimal subsidyBalance = new BigDecimal(60000);
			// 从业务参数系统 获取积分福利相关参数组
			Map<String, BusinessSysParamItemsRedis> parmGroup = businessParamSearch
					.searchSysParamItemsByGroup(BusinessParam.JF_WELFARE.getCode());

			// 获取意外身故保障最大赔付金额参数
			BusinessSysParamItemsRedis paramItem = parmGroup
					.get(BusinessParam.P_DEATH_SUBSIDIES_YEAR_MAX.getCode());
			if (paramItem != null) {
				subsidyBalance = new BigDecimal(paramItem.getSysItemsValue().trim());
			}
			approvalInfo.setSubsidyBalance(subsidyBalance);
		}

		// 生成一条核算单记录
		ClaimsAccountingInfo claimsAccountingInfo = ClaimsAccountingInfo
				.buildClaimsAccountingInfo();
		claimsAccountingInfo.setApplyWelfareNo(record.getApplyWelfareNo());
		claimsAccountingInfo.setTaskId(approvalTask.getTaskId());

		// 保存申请单、审批单、本地工单信息
		saveInfo(record, approvalInfo, claimsAccountingInfo);

		logInfo("apporval TaskId:----------" + approvalInfo.getTaskId());

		// 调用工单系统保存工单
		try {
			taskService.saveTMTask(approvalTask);

		} catch (Exception e) {
			logError("call HS-TM sys saveTmTask method error!", e);
			throw new HsException(WsErrorCode.INVOKE_TM_SYS_ERROR.getCode(),
					WsErrorCode.INVOKE_TM_SYS_ERROR.getDesc() + e.getMessage());
		}
	}

	private void saveInfo(WelfareApplyInfo record, ApprovalInfo approvalInfo,
			ClaimsAccountingInfo claimsAccountingInfo) {
		// 插入审批信息
		welfareApplyMapper.insertSelective(record);
		// 插入图片信息
		imgMapper.batchInsertImgs(record.imgs);
		// 插入审批记录
		approvalMapper.insertSelective(approvalInfo);
		// 插入理赔核算单
		accountingInfoMapper.insertSelective(claimsAccountingInfo);
	}

	private TmTask generateTmTask(String applyWelfareNo, String taskType) {
		TmTask tmTask = Task.buildTmTask();
		String paltFormCustId = entService.findEntCustIdByEntResNo(lcsBaseDataService.getLocalInfo().getPlatResNo());
		tmTask.setEntCustId(paltFormCustId);
		tmTask.setBizNo(applyWelfareNo);
		tmTask.setBizType(taskType);
		return tmTask;
	}

	/**
	 * 消费者查询积分福利申请
	 * 
	 * @param custId
	 *            申请人客户号 必传
	 * @param welfareType
	 *            申请的福利类型 福利类型 0 意外伤害 1 免费医疗 2 他人身故 选传
	 * @param approvalStatus
	 *            状态 0 受理中 1 已受理 2 驳回 选传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.ws.api.IWsApplyService#listWelfareApply(java.lang.String,
	 *      java.lang.Integer, java.lang.Integer, com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<WelfareApplyRecord> listWelfareApply(String custId, Integer welfareType,
			Integer approvalStatus, Page page) throws HsException {
		// 参数验证
		validParamIsEmptyOrNull(custId, "必传参数[custId]为空");

		// 构建查询条件
		ApplyQueryCondition condition = new ApplyQueryCondition();
		condition.setCustId(custId);
		condition.setWelfareType(welfareType);
		condition.setApprovalStatus(approvalStatus);

		return listWelfareApply(condition, page);
	}

	/**
	 * 消费者查询积分福利申请
	 * 
	 * @param condition
	 *            查询条件 必传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.ws.api.IWsApplyService#listWelfareApply(com.gy.hsxt.ws.bean.ApplyQueryCondition,
	 *      com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<WelfareApplyRecord> listWelfareApply(ApplyQueryCondition condition, Page page)
			throws HsException {
		// 参数验证
		validParamIsEmptyOrNull(condition, "必传参数[condition]为空");
		validParamIsEmptyOrNull(page, "必传参数[page]为空");

		// 查询总记录数
		int count = welfareApplyMapper.countWelfareApply(condition);
		if (count == 0) {
			return null;
		}

		// 分页查询申请记录
		List<WelfareApplyInfo> records = welfareApplyMapper.pageWelfareApply(condition, page);

		// 构建返回数据
		List<WelfareApplyRecord> result = new ArrayList<WelfareApplyRecord>();
		for (WelfareApplyInfo applyInfo : records) {
			WelfareApplyRecord record = new WelfareApplyRecord();
			BeanUtils.copyProperties(applyInfo, record);
			record.setApplyDate(DateUtil.DateTimeToString(new Date(applyInfo.getApplyDate()
					.getTime())));
			if (applyInfo.getHsPayAmount() != null) {
				record.setApprovalAmount(applyInfo.getHsPayAmount().toString());
			}
			result.add(record);
		}
		return new PageData<>(count, result);

	}

	/**
	 * 查询积分福利申请详情
	 * 
	 * @param applyWelfareNo
	 *            申请流水号
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.ws.api.IWsApplyService#queryWelfareApplyDetail(java.lang.String)
	 */
	@Override
	public WelfareApplyDetail queryWelfareApplyDetail(String applyWelfareNo) throws HsException {
		// 参数验证
		validParamIsEmptyOrNull(applyWelfareNo, "必传参数[applyWelfareNo]为空");

		// 查询申请单详情信息
		WelfareApplyInfo applyInfo = welfareApplyMapper.selectByPrimaryKey(applyWelfareNo);
		if (isBlank(applyInfo)) {
			throw new HsException(WsErrorCode.APPLY_NOT_EXIST.getCode(),
					WsErrorCode.APPLY_NOT_EXIST.getDesc());
		}

		List<ImgInfo> imgInfos = imgMapper.listImgByImgIds(applyInfo.getImgIds());
		WelfareApplyDetail detail = applyInfo.buildWelfareApplyDetail(imgInfos);

		// 查询审批信息
		ApprovalInfo approvalInfo = approvalMapper.selectByApprovalStep(1, applyWelfareNo);
		if (approvalInfo == null) {
			approvalInfo = approvalMapper.selectByApprovalStep(0, applyWelfareNo);
		}
		if (approvalInfo != null) {
			detail.setApprovalPerson(approvalInfo.getApprover());
			if (approvalInfo.getApprovalDate() != null) {
				detail.setApprovalDate(DateUtil.DateTimeToString(approvalInfo.getApprovalDate()));
			}
			detail.setApprovalReason(approvalInfo.getApprovalReason());
		}
		return detail;
	}

	/**
	 * 检查是否已存在申请
	 * 
	 * @param hsResNo
	 *            互生号
	 * @param welfareType
	 *            申请的福利类型 福利类型 0 意外伤害 1 免费医疗 2 他人身故
	 * @see com.gy.hsxt.ws.api.IWsApplyService#checkExistApplying(java.lang.String,
	 *      java.lang.Integer)
	 */
	public void checkExistApplying(String hsResNo, Integer welfareType) throws HsException {
		// 参数验证
		validParamIsEmptyOrNull(hsResNo, "必传参数[hsResNo]为空");
		// 参数验证
		validParamIsEmptyOrNull(welfareType, "必传参数[welfareType]为空");
		validIsInApplying(hsResNo, welfareType);
	}

	private void validIsInApplying(String hsResNo, Integer welfareType) throws HsException {
		int count = welfareApplyMapper.countApplyingOrder(hsResNo, welfareType);
		if (count > 0) {
			throw new HsException(WsErrorCode.APPLYING_EXIST.getCode(),
					WsErrorCode.APPLYING_EXIST.getDesc());
		}
	}

	/**
	 * 查询最后一次申请记录
	 * 
	 * @param hsResNo
	 *            互生号
	 * @param welfareType
	 *            申请的福利类型 福利类型 0 意外伤害 1 免费医疗 2 他人身故
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.ws.api.IWsApplyService#queryLastApplyRecord(java.lang.String,
	 *      java.lang.Integer)
	 */
	@Override
	public WelfareApplyDetail queryLastApplyRecord(String hsResNo, Integer welfareType)
			throws HsException {
		// 参数验证
		validParamIsEmptyOrNull(hsResNo, "必传参数[hsResNo]为空");
		validParamIsEmptyOrNull(welfareType, "必传参数[welfareType]为空");
		// 查询申请记录
		List<WelfareApplyInfo> list = welfareApplyMapper.queryApplyRecord(hsResNo, welfareType);
		if (list == null || list.size() < 1) {
			return null;
		}
		// 构建申请记录详情
		WelfareApplyInfo applyInfo = list.get(0);
		WelfareApplyDetail applyDetail = new WelfareApplyDetail();
		BeanUtils.copyProperties(applyInfo, applyDetail);

		// 查询审批信息
		ApprovalInfo approvalInfo = approvalMapper.selectByApprovalStep(1,
				applyDetail.getApplyWelfareNo());
		if (approvalInfo == null) {
			approvalInfo = approvalMapper.selectByApprovalStep(0, applyDetail.getApplyWelfareNo());
		}
		if (approvalInfo != null) {
			// 设置审批信息
			applyDetail.setApprovalDate(DateTimeToString(approvalInfo.getApprovalDate()));
			applyDetail.setApprovalReason(approvalInfo.getApprovalReason());
			if (approvalInfo.getApprovalAmount() != null) {
				applyDetail.setApprovalAmount(approvalInfo.getApprovalAmount().toString());
			}
			applyDetail.setApprovalPerson(approvalInfo.getApprover());
		}
		return applyDetail;
	}

}
