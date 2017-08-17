package com.gy.hsxt.ws.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.ac.api.IAccountEntryService;
import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.ac.bean.AccountWriteBack;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.TaskStatus;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.lcs.api.ILCSBaseDataService;
import com.gy.hsxt.tm.api.ITMTaskService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.ws.api.IWsGrantService;
import com.gy.hsxt.ws.bean.EntryInfo;
import com.gy.hsxt.ws.bean.GrantDetail;
import com.gy.hsxt.ws.bean.GrantInfo;
import com.gy.hsxt.ws.bean.GrantQueryCondition;
import com.gy.hsxt.ws.bean.GrantRecord;
import com.gy.hsxt.ws.common.GrantStatus;
import com.gy.hsxt.ws.common.WelfareType;
import com.gy.hsxt.ws.common.WsErrorCode;
import com.gy.hsxt.ws.common.WsTools;
import com.gy.hsxt.ws.mapper.EntryInfoMapper;
import com.gy.hsxt.ws.mapper.GrantInfoMapper;
import com.gy.hsxt.ws.mapper.WelfareApplyRecordMapper;
import com.gy.hsxt.ws.mapper.WelfareQualificationMapper;

/**
 * 积分福利发放服务接口实现类
 * 
 * @Package: com.gy.hsxt.ws.service
 * @ClassName: WelfareGrantService
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-12-11 下午5:02:10
 * @version V1.0
 */
@Service
public class WelfareGrantService extends BasicService implements IWsGrantService {

	private static final String KEY_WS_WRITE_BACK_ACCOUNT = "ws.write_back_account";

	@Autowired
	ITMTaskService taskService;

	@Autowired
	private WelfareApplyRecordMapper applyMapper;

	@Autowired
	private GrantInfoMapper grantMapper;

	@Autowired
	private EntryInfoMapper entryMapper;

	@Autowired
	private IAccountEntryService accountService;

	@Autowired
	private WelfareQualificationMapper welfareQualificationMapper;

	@Autowired
	private IUCAsEntService entService;

	@Resource
	private ILCSBaseDataService lcsBaseDataService;

	/**
	 * 发放积分福利
	 * 
	 * @param grantId
	 *            发放记录编号（主键） 必传
	 * @param remark
	 *            发放备注信息
	 * @throws HsException
	 * @see com.gy.hsxt.ws.api.IWsGrantService#grantWelfare(java.lang.String,
	 *      java.lang.String)
	 */
	@Transactional
	public void grantWelfare(String grantId, String remark) throws HsException {
		logInfo("grant welfare begin!---");
		logInfo("grantId:---" + grantId);
		// 参数验证
		validParamIsEmptyOrNull(grantId, "发放流水号为空");

		// 查询发放信息
		GrantInfo grantInfo = grantMapper.selectByPrimaryKey(grantId);
		if (grantInfo == null) {
			throw new HsException(WsErrorCode.GRANT_NOT_EXIST.getCode(), "发放单未找到!");
		}
		if(GrantStatus.GRANT_OVER.getStatus()==grantInfo.getIfgiving()){
			throw new HsException(WsErrorCode.GRANT_NOT_EXIST.getCode(), "已发放此申请单了!");
		}
		
		// 更新发放单状态
		grantInfo.setIfgiving(GrantStatus.GRANT_OVER.getStatus());
		grantInfo.setDescription(remark);
		if (null != grantInfo.getSubsidyBalance()) {
			grantInfo.setSubsidyBalance(grantInfo.getSubsidyBalance().subtract(
					grantInfo.getHsPayAmount()));
		}
		grantInfo.setUpdatedDate(Timestamp.valueOf(DateUtil.getCurrentDateTime()));
		grantMapper.updateByPrimaryKeySelective(grantInfo);
		logInfo("update grant sucess! grantId:----" + grantId);

		// 构建、插入积分 分录信息（对账用）
		EntryInfo custEntryInfo = buildCustEntryInfo(grantInfo);
		EntryInfo entEntryInfo = buildEntEntryInfo(grantInfo);
		// entEntryInfo.setCustId(HsPropertiesConfigurer.getProperty(KEY_WS_CUST_ID_AREA_PLATFORM));//
		// 地区平台客户号
		// entEntryInfo.setHsResNo(HsPropertiesConfigurer.getProperty(KEY_WS_HS_RES_NO_AREA_PLATFORM));//
		// 地区平台互生号
		entEntryInfo.setHsResNo(lcsBaseDataService.getLocalInfo().getPlatResNo());// 地区平台互生号
		String paltFormCustId = entService.findEntCustIdByEntResNo(entEntryInfo.getHsResNo());
		entEntryInfo.setCustId(paltFormCustId);// 设置 地区平台客户号
		entryMapper.insertSelective(entEntryInfo);
		entryMapper.insertSelective(custEntryInfo);
		logInfo("insert Entry sucess! ");

		try {
			// 通知工单系统更新工单状态为处理完成
			taskService.updateTaskStatus(grantInfo.getTaskId(), TaskStatus.COMPLETED.getCode(), "");
		} catch (Exception e) {
			logError("call HS-TM sys updateTaskStatus method error!", e);
			throw new HsException(WsErrorCode.INVOKE_TM_SYS_ERROR.getCode(),
					WsErrorCode.INVOKE_TM_SYS_ERROR.getDesc());
		}

		// 调用账务记账
		List<AccountEntry> list = new ArrayList<AccountEntry>();
		list.add(custEntryInfo.generateAccountEntry());
		list.add(entEntryInfo.generateAccountEntry());
		try {
			accountService.actualAccount(list);
			System.out.println(JSON.toJSONString(list));
			logInfo("call HS-AC sys actualAccount sucess! bizNo:---" + grantId);
			logInfo("grant welfare over!---");
		} catch (HsException e) {
			// 如果是HsException 直接抛出
			logError("call HS-AC sys error" + e.getMessage(), e);
			throw new HsException(WsErrorCode.INVOKE_AC_SYS_ERROR.getCode(), e.getMessage());
		} catch (Exception e) {
			// 其他异常 做 冲正处理
			logError("try to reversal! applyWelfareNo:" + grantInfo.getApplyWelfareNo(), e);
			reversal(list.get(0), grantInfo);
		}
	}

	/**
	 * 冲正记账
	 * 
	 * @param srcAccountEntry
	 * @throws HsException
	 */
	private void reversal(AccountEntry srcAccountEntry, GrantInfo grantInfo) throws HsException {
		AccountWriteBack wirteBack = new AccountWriteBack();
		wirteBack.setRelTransNo(srcAccountEntry.getTransNo());
		// 设置为自动冲正
		wirteBack.setWriteBack(1);
		wirteBack.setTransType(getTransType(grantInfo.getWelfareType()));
		// 获取冲正次数
		int wirteBackAccount = HsPropertiesConfigurer
				.getPropertyIntValue(KEY_WS_WRITE_BACK_ACCOUNT);
		logInfo("wirteBackAccount:---" + wirteBackAccount);
		// 循环遍历 调用账务进行冲正
		for (int i = 1; i <= wirteBackAccount; i++) {
			try {
				accountService.correctSingleAccount(wirteBack);
			} catch (HsException e) {
				// 如果是HsException 直接抛出
				logError("call HS-AC correctSingleAccount exception!", e);
				throw new HsException(WsErrorCode.INVOKE_AC_SYS_ERROR.getCode(), e.getMessage());
			} catch (Exception e) {
				if (i == wirteBackAccount) {
					logError("call HS-AC sys actualAccount、correctSingleAccount exception!", e);
					throw new HsException(WsErrorCode.INVOKE_AC_SYS_ERROR.getCode(),
							"调用账务记账异常、冲正也异常!");
				}
			}
		}
	}

	private EntryInfo buildCustEntryInfo(GrantInfo grantInfo) {
		EntryInfo entryInfo = new EntryInfo();
		// 消费者账号类型 >流通币账号 互生币流通
		entryInfo.setAccType(AccountType.ACC_TYPE_POINT20110.getCode());
		entryInfo.setAddAmount(grantInfo.getHsPayAmount());
		entryInfo.setBatchNo(DateUtil.getDateNoSign(new Date()));
		entryInfo.setCreatedBy(grantInfo.getGivingPersonCustId());
		entryInfo.setCustId(grantInfo.getCustId());
		entryInfo.setCustType(CustType.PERSON.getCode());
		entryInfo.setEntryNo(WsTools.getGUID());
		entryInfo.setGrantId(grantInfo.getGivingId());
		entryInfo.setHsResNo(grantInfo.getHsResNo());
		entryInfo.setSubAmount(new BigDecimal(0));
		entryInfo.setTransType(getTransType(grantInfo.getWelfareType()));
		return entryInfo;
	}

	private EntryInfo buildEntEntryInfo(GrantInfo grantInfo) {
		EntryInfo entryInfo = new EntryInfo();
		// 设置企业账号类型
		entryInfo.setAccType(getAccType(grantInfo.getWelfareType()));
		entryInfo.setAddAmount(new BigDecimal(0));
		entryInfo.setBatchNo(DateUtil.getDateNoSign(new Date()));
		entryInfo.setCreatedBy(grantInfo.getGivingPersonCustId());
		entryInfo.setCustType(CustType.AREA_PLAT.getCode());
		entryInfo.setEntryNo(WsTools.getGUID());
		entryInfo.setGrantId(grantInfo.getGivingId());
		entryInfo.setSubAmount(grantInfo.getHsPayAmount());
		entryInfo.setTransType(getTransType(grantInfo.getWelfareType()));
		return entryInfo;
	}

	/**
	 * 获取交易类型
	 * 
	 * @param applyType
	 *            福利类型
	 * @return
	 */
	private String getTransType(int applyType) {
		if (applyType == WelfareType.ACCIDENT_SECURITY.getType()) {
			return TransType.W_ACCIDENT_SECURITY_APPLY.getCode();
		}
		if (applyType == WelfareType.DIE_SECURITY.getType()) {
			return TransType.W_DIE_SECURITY_APPLY.getCode();
		}
		if (applyType == WelfareType.MEDICAL_SUBSIDIES.getType()) {
			return TransType.W_MEDICAL_SUBSIDIES_APPLY.getCode();
		}
		return null;
	}

	/**
	 * 获取企业账号类型
	 * 
	 * @param applyType
	 *            福利类型
	 * @return
	 */
	private String getAccType(int applyType) {
		if (applyType == WelfareType.ACCIDENT_SECURITY.getType()) {
			return AccountType.ACC_TYPE_POINT10320.getCode();
		}
		if (applyType == WelfareType.DIE_SECURITY.getType()) {
			return AccountType.ACC_TYPE_POINT10320.getCode();
		}
		if (applyType == WelfareType.MEDICAL_SUBSIDIES.getType()) {
			return AccountType.ACC_TYPE_POINT20310.getCode();
		}
		return null;
	}

	/**
	 * 查询待发放的积分福利记录
	 * 
	 * @param queryCondition
	 *            积分福利发放记录查询条件 必传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.ws.api.IWsGrantService#listPendingGrant(com.gy.hsxt.ws.bean.GrantQueryCondition,
	 *      com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<GrantRecord> listPendingGrant(GrantQueryCondition queryCondition, Page page)
			throws HsException {
		// 数据验证
		validParamIsEmptyOrNull(queryCondition, "查询条件参数[queryCondition]为null");
		validParamIsEmptyOrNull(page, "查询条件参数[page]为null");

		return pageGrantInfo(queryCondition, page, GrantStatus.PENDING.getStatus());
	}

	/**
	 * 查询已经发放的积分福利
	 * 
	 * @param queryCondition
	 *            积分福利发放记录查询条件 必传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.ws.api.IWsGrantService#listHistoryGrant(com.gy.hsxt.ws.bean.GrantQueryCondition,
	 *      com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<GrantRecord> listHistoryGrant(GrantQueryCondition queryCondition, Page page)
			throws HsException {
		// 数据验证
		validParamIsEmptyOrNull(queryCondition, "查询条件参数[queryCondition]为null");
		validParamIsEmptyOrNull(page, "查询条件参数[page]为null");

		return pageGrantInfo(queryCondition, page, GrantStatus.GRANT_OVER.getStatus());
	}

	private PageData<GrantRecord> pageGrantInfo(GrantQueryCondition queryCondition, Page page,
			int grantStatus) {
		// 查询总记录数
		int count = grantMapper.countGrantInfo(queryCondition, grantStatus);
		if (count == 0) {
			return null;
		}

		// 分页查询发放记录
		List<GrantInfo> list = grantMapper.pageGrantInfo(queryCondition, page, grantStatus);

		// 构建返回数据
		List<GrantRecord> result = new ArrayList<GrantRecord>();
		for (GrantInfo grantInfo : list) {
			result.add(grantInfo.generateGrantRecord());
		}
		return new PageData<>(count, result);
	}

	/**
	 * 查询积分福利审批记录详情
	 * 
	 * @param approvalId
	 *            审批记录编号 必传
	 * @throws HsException
	 * @see com.gy.hsxt.ws.api.IWsGrantService#queryGrantDetail(java.lang.String)
	 */
	@Override
	public GrantDetail queryGrantDetail(String grantId) throws HsException {
		// 参数验证
		validParamIsEmptyOrNull(grantId, "发放流水号为空");

		// 查询发放详情记录
		GrantInfo grantInfo = grantMapper.selectByPrimaryKey(grantId);
		if (grantInfo == null) {
			throw new HsException(WsErrorCode.GRANT_NOT_EXIST.getCode(),
					WsErrorCode.GRANT_NOT_EXIST.getDesc());
		}

		// 构建返回数据
		GrantRecord grantRecord = grantInfo.generateGrantRecord();
		GrantDetail detail = new GrantDetail();
		BeanUtils.copyProperties(grantRecord, detail);
		return detail;
	}

}
