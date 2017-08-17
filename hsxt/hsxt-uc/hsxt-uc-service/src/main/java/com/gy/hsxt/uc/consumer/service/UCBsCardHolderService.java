/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.consumer.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BeanCopierUtils;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;
import com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderService;
import com.gy.hsxt.uc.bs.bean.consumer.BsCardHolder;
import com.gy.hsxt.uc.bs.bean.consumer.BsHsCard;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.common.bean.NetworkInfo;
import com.gy.hsxt.uc.common.bean.PwdQuestion;
import com.gy.hsxt.uc.common.mapper.PwdQuestionMapper;
import com.gy.hsxt.uc.consumer.bean.CardHolder;
import com.gy.hsxt.uc.consumer.bean.HsCard;
import com.gy.hsxt.uc.consumer.service.runable.BatchRollbackThread;
import com.gy.hsxt.uc.consumer.service.runable.BatchSaveCardHolderInfo;
import com.gy.hsxt.uc.consumer.service.runable.BatchSaveHsCardInfo;
import com.gy.hsxt.uc.consumer.service.runable.BatchSaveNetworkInfo;
import com.gy.hsxt.uc.consumer.service.runable.IBatchRollback;
import com.gy.hsxt.uc.ent.bean.EntStatusInfo;
import com.gy.hsxt.uc.search.api.IUCUserInfoService;
import com.gy.hsxt.uc.search.bean.SearchUserInfo;
import com.gy.hsxt.uc.util.CSPRNG;
import com.gy.hsxt.uc.util.CustIdGenerator;

/**
 * 
 * @Package: com.gy.hsxt.uc.consumer.service
 * @ClassName: IUCBsCardHolderService
 * @Description: 持卡人基本信息管理实现（BS系统调用）
 * 
 * @author: tianxh
 * @date: 2015-10-20 下午7:46:40
 * @version V1.0
 */
@Service
public class UCBsCardHolderService implements IUCBsCardHolderService {
	private final static String MOUDLENAME = "com.gy.hsxt.uc.consumer.service.UCBsCardHolderService";
	@Autowired
	private UCAsCardHolderService uCAsCardHolderService;
	@Autowired
	private PwdQuestionMapper pwdQuestionMapper;
	@Autowired
	private IUCAsOperatorService iUCAsOperatorService;
	@Autowired
	SysConfig config;
	@Autowired
	CommonCacheService commonCacheService;
	@Autowired
	IUCUserInfoService searchUserService;
	@Autowired
	IUCAsEntService entService;
	private ConcurrentHashMap<String, ArrayList<IBatchRollback>> rollbackServMap = new ConcurrentHashMap<String, ArrayList<IBatchRollback>>();

	private ConcurrentHashMap<String, String> rollbackKey = new ConcurrentHashMap<String, String>();

	/**
	 * 
	 * @param operCustId
	 *            操作员客户号
	 * @param list
	 *            批量添加的互生卡
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderService#batchAddCards(java.lang.String,
	 *      java.util.List)
	 */
	@SuppressWarnings("static-access")
	@Transactional(rollbackFor = Exception.class)
	public void batchAddCards_old(String operCustId, List<BsHsCard> list,
			String entResNo) throws HsException {
		long begin = System.currentTimeMillis();
		long end = 0l;
		if (StringUtils.isBlank(operCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数操作员客户号为空");
		}
		if (StringUtils.isBlank(entResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数隶属企业的互生号为空");
		}
		if (null == list) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数互生卡列表为空");
		}
		String newOperCustId = operCustId.trim();
		List<CardHolder> cardHolderList = new ArrayList<CardHolder>();
		List<HsCard> hscardList = new ArrayList<HsCard>();
		List<PwdQuestion> pwdQuestList = new ArrayList<PwdQuestion>();
		List<NetworkInfo> networkInfoList = new ArrayList<NetworkInfo>();
		CardHolder cardHolder = null;
		NetworkInfo networkInfo = null;
		HsCard hsCard = null;
		PwdQuestion pwdQuestion = null;
		String resNo = "";
		String custId = "";
		String salt = "";
		String encryptLoginPwd = "";
		String cryptogram = "";
		List<SearchUserInfo> users = new ArrayList<SearchUserInfo>();
		end = System.currentTimeMillis();
		SystemLog.info(MOUDLENAME, "batchAddCards_old", "循环前耗时="
				+ (end - begin));
		begin = end;
		for (BsHsCard bsCard : list) {
			resNo = bsCard.getPerResNo().trim();
			custId = resNo + StringUtils.getSimpleDate(new Date());// 客户号
			salt = CSPRNG.generateRandom(config.getCsprLen());// 盐值
			encryptLoginPwd = uCAsCardHolderService.encryptNakedPwd(bsCard
					.getLoginPwd().trim(), salt);// 对明文密码加密
			cryptogram = bsCard.getCryptogram().trim();
			// 持卡人信息装载
			cardHolder = new CardHolder();
			cardHolder.setPerCustId(custId);
			cardHolder.setPerResNo(resNo);
			cardHolder.setEntResNo(entResNo.trim());
			cardHolder.setPwdLogin(encryptLoginPwd);
			cardHolder.setCreatedby(newOperCustId);
			cardHolder.setPwdLoginSaltValue(salt);
			cardHolderList.add(cardHolder);
			// 互生卡信息装载
			hsCard = new HsCard();
			hsCard.setPerCustId(custId);
			hsCard.setCryptogram(cryptogram);
			hsCard.setPerResNo(resNo);
			hsCard.setCreatedby(newOperCustId);
			hscardList.add(hsCard);
			// 密保问题信息装载
			pwdQuestion = new PwdQuestion();
			pwdQuestion.setCustId(custId);
			pwdQuestion.setCreateBy(newOperCustId);
			pwdQuestList.add(pwdQuestion);
			// 网络信息信息装载
			networkInfo = new NetworkInfo();
			networkInfo.setPerCustId(custId);
			networkInfo.setCreatedby(newOperCustId);
			networkInfo.setNickname(resNo);// 昵称如果为空，则用互生号代替
			networkInfoList.add(networkInfo);

			SearchUserInfo user = new SearchUserInfo();
			user.setUserType(1);
			user.setCustId(custId);
			user.setNickName(resNo);
			user.setUsername(resNo);
			user.setResNo(resNo);
			user.setEntResNo(cardHolder.getEntResNo());
			user.setIsLogin(0);
			users.add(user);
		}
		end = System.currentTimeMillis();
		SystemLog.info(MOUDLENAME, "batchAddCards_old", "循环准备数据耗时="
				+ (end - begin));
		begin = end;
		uCAsCardHolderService.batchSaveCardHolderInfo(cardHolderList);// 保存持卡人信息
		uCAsCardHolderService.batchSaveHsCardInfo(hscardList);// 保存互生卡信息
		uCAsCardHolderService.batchSavePwdQuestion(pwdQuestList);// 保存密保信息
		uCAsCardHolderService.batchSaveNetworkInfo(networkInfoList);// 保存网络信息
		// 通知搜索引擎
		end = System.currentTimeMillis();
		SystemLog.info(MOUDLENAME, "batchAddCards_old", "用户添加完成，耗时:"
				+ (end - begin));
		try {
			begin = end;
			searchUserService.batchAdd(users);
			end = System.currentTimeMillis();
			SystemLog.info(MOUDLENAME, "batchAddCards_old", "通知搜索耗时："
					+ (end - begin));
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "batchAddCards_old", "通知搜索引擎异常：", e);
		}
		begin = end;
		// 执行耗时分析
		end = System.currentTimeMillis();
		SystemLog.info(MOUDLENAME, "batchAddCards_old", "批量入库耗时="
				+ (end - begin));
		begin = end;

	}

	/**
	 * 
	 * @param operCustId
	 *            操作员客户号
	 * @param list
	 *            批量添加的互生卡
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderService#batchAddCards(java.lang.String,
	 *      java.util.List)
	 */
	@Transactional()
	public void batchAddCards(String operCustId, List<BsHsCard> list,
			String entResNo) throws HsException {
		// this.batchAddCards_old(operCustId, list, entResNo);
		this.batchAddCardsInThreads(operCustId, list, entResNo);
	}

	/**
	 * 
	 * @param operCustId
	 *            操作员客户号
	 * @param list
	 *            批量添加的互生卡
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderService#batchAddCards(java.lang.String,
	 *      java.util.List)
	 */
	@Transactional
	public void batchAddCardsInThreads(String operCustId, List<BsHsCard> list,
			String entResNo) throws HsException {
		long begin = System.currentTimeMillis();
		long end = 0l;
		if (StringUtils.isBlank(operCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数操作员客户号为空");
		}
		if (StringUtils.isBlank(entResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数隶属企业的互生号为空");
		}
		if (null == list || list.isEmpty()) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数互生卡列表为空");
		}
		// 检查企业是否存在
		String entCustId = commonCacheService.findEntCustIdByEntResNo(entResNo);
		if(StringUtils.isBlank(entCustId)){
			throw new HsException(ErrorCodeEnum.ENT_IS_NOT_FOUND.getValue(),
					ErrorCodeEnum.ENT_IS_NOT_FOUND.getDesc()+",entResNo["+entResNo+"]");
		}
		//抽样检查首条和最后一条的记录已开卡
		checkCarderIsExist(list);
		EntStatusInfo statusInfo = commonCacheService
				.searchEntStatusInfo(entCustId);
		CustIdGenerator.isEntStatusInfoExist(statusInfo, entCustId);
		int addCards = list.size();
		String newOperCustId = operCustId.trim();
		List<CardHolder> cardHolderList = new ArrayList<CardHolder>(addCards);
		List<HsCard> hscardList = new ArrayList<HsCard>(addCards);
		List<PwdQuestion> pwdQuestList = new ArrayList<PwdQuestion>(addCards);
		List<NetworkInfo> networkInfoList = new ArrayList<NetworkInfo>(addCards);
		CardHolder cardHolder = null;
		NetworkInfo networkInfo = null;
		HsCard hsCard = null;
		PwdQuestion pwdQuestion = null;
		String resNo = "";
		String custId = "";
		String salt = "";
		String encryptLoginPwd = "";
		String cryptogram = "";
		List<SearchUserInfo> users = new ArrayList<SearchUserInfo>();
		String curDate = StringUtils.getSimpleDate(new Date());
		String salt0 = CSPRNG.generateRandom(config.getCsprLen() - 1);// 盐值
		int cards = 0;
		// 记录操作耗时
		end = System.currentTimeMillis();
		SystemLog.info(MOUDLENAME, "batchAddCardsInThreads", "循环前耗时="
				+ (end - begin));
		begin = end;

		for (BsHsCard bsCard : list) {
			cards++;
			resNo = bsCard.getPerResNo();
			custId = resNo + curDate;// 客户号
			salt = salt0 + (cards & 7);// 盐值+ cards按8取模
			encryptLoginPwd = uCAsCardHolderService.encryptNakedPwd(
					bsCard.getLoginPwd(), salt);// 对明文密码加密
			cryptogram = bsCard.getCryptogram();
			// 持卡人信息装载
			cardHolder = new CardHolder();
			cardHolder.setPerCustId(custId);
			cardHolder.setPerResNo(resNo);
			cardHolder.setEntResNo(entResNo);
			cardHolder.setPwdLogin(encryptLoginPwd);
			cardHolder.setCreatedby(newOperCustId);
			cardHolder.setPwdLoginSaltValue(salt);
			cardHolder.setHsCardActiveStatus(0);// 设置互生卡的激活状态为0（0:启用：已经开卡状态（系统资源使用数即为此状态的数据）
												// 1:激活：有积分 或 消费者已实名注册）
			cardHolderList.add(cardHolder);
			// 互生卡信息装载
			hsCard = new HsCard();
			hsCard.setPerCustId(custId);
			hsCard.setCryptogram(cryptogram);
			hsCard.setPerResNo(resNo);
			hsCard.setCreatedby(newOperCustId);
			hscardList.add(hsCard);
			// 密保问题信息装载
			pwdQuestion = new PwdQuestion();
			pwdQuestion.setCustId(custId);
			pwdQuestion.setCreateBy(newOperCustId);
			pwdQuestList.add(pwdQuestion);
			// 网络信息信息装载
			networkInfo = new NetworkInfo();
			networkInfo.setPerCustId(custId);
			networkInfo.setCreatedby(newOperCustId);
			networkInfo.setNickname(resNo);// 昵称如果为空，则用互生号代替
			networkInfoList.add(networkInfo);

			SearchUserInfo user = new SearchUserInfo();
			user.setUserType(1);
			user.setCustId(custId);
			user.setNickName(resNo);
			user.setUsername(resNo);
			user.setResNo(resNo);
			user.setEntResNo(cardHolder.getEntResNo());
			user.setIsLogin(0);
			users.add(user);
		}
		// 记录操作耗时
		end = System.currentTimeMillis();
		SystemLog.info(MOUDLENAME, "batchAddCardsInThreads", "循环准备数据耗时="
				+ (end - begin));
		begin = end;

		int threadNum = 3;
		CountDownLatch latch = new CountDownLatch(threadNum);
		String mainThreadKey = getRollbackKey();
		ExecutorService executor = Executors.newFixedThreadPool(threadNum);
		// 把任务提交线程池，线程池会创建一个新线程(或者从池中获取一个空闲线程)执行任务
		executor.execute(new BatchSaveCardHolderInfo(mainThreadKey, this,
				uCAsCardHolderService, cardHolderList, latch));// 保存持卡人信息
		executor.execute(new BatchSaveHsCardInfo(mainThreadKey, this,
				uCAsCardHolderService, hscardList, latch));// 保存互生卡信息
		// executor.execute(new BatchSavePwdQuestion(mainThreadKey, this,
		// uCAsCardHolderService, pwdQuestList, latch));// 保存密保信息
		executor.execute(new BatchSaveNetworkInfo(mainThreadKey, this,
				uCAsCardHolderService, networkInfoList, latch));// 保存网络信息

		try {
			latch.await(); // 当前线程阻塞，等待所有子线程任务执行完毕 （countDown==0）
		} catch (InterruptedException e1) {
			SystemLog.error(MOUDLENAME, "batchAddCardsInThreads",
					getRollbackKey() + "线程被打断" + e1.getMessage(), e1);
			throw new HsException(
					ErrorCodeEnum.CARDHOLDER_SAVE_ERROR.getValue(),
					e1.getMessage());
		}

		// 执行耗时分析
		end = System.currentTimeMillis();
		SystemLog.info(MOUDLENAME, "batchAddCardsInThreads", "批量入库耗时="
				+ (end - begin));
		begin = end;

		// 检查多线程执行情况，若有异常，做回滚处理。
		batchAddCardsCheck(mainThreadKey);
		executor.shutdown();

		// 更新企业开卡总数
		addCountBuyCards(addCards, entResNo, newOperCustId);
		// 记录操作耗时
		end = System.currentTimeMillis();
		SystemLog.info(MOUDLENAME, "batchAddCardsInThreads", "更新开卡数耗时="
				+ (end - begin));
		begin = end;
		try {// 通知搜索引擎
			searchUserService.batchAdd(users);
			end = System.currentTimeMillis();
			SystemLog.info(MOUDLENAME, "batchAddCardsInThreads", "通知搜索耗时："
					+ (end - begin));
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "batchAddCardsInThreads", "通知搜索引擎异常：",
					e);
		}
	}

	/**
	 * 抽样检查首条和最后一条的记录已开卡
	 * @param list
	 */
	private void checkCarderIsExist(List<BsHsCard> list)throws HsException{
		String firstResNo = list.get(0).getPerResNo();
		String lastResNo = list.get(list.size() - 1).getPerResNo();
		int firstCount = 0;
		int lastCount = 0;
		if(StringUtils.isNotBlank(firstResNo)){
			firstCount = uCAsCardHolderService.getHsCardCountsByResNo(firstResNo);
		}
		if(StringUtils.isNotBlank(lastResNo)){
			lastCount = uCAsCardHolderService.getHsCardCountsByResNo(lastResNo);
		}
		if(firstCount > 0 || lastCount > 0){
			throw new HsException(ErrorCodeEnum.USER_EXIST.getValue(),
					firstResNo+ErrorCodeEnum.USER_EXIST.getDesc()+lastResNo);
		}
	}
	
	/**
	 * 更新企业购买积分卡数量
	 * 
	 * @param count 开卡数量
	 * @param entResNo 企业互生号
	 * @param operCustId 操作员客户号
	 */
	private void addCountBuyCards(int count, String entResNo, String operCustId) throws HsException {
		// Operator operator =
		// commonCacheService.searchOperByCustId(operCustId);
		// String entCustId = StringUtils.nullToEmpty(operator.getEntCustId());
		String entCustId = commonCacheService.findEntCustIdByEntResNo(entResNo);
		if(StringUtils.isBlank(entCustId)){
			throw new HsException(ErrorCodeEnum.ENT_IS_NOT_FOUND.getValue(),
					ErrorCodeEnum.ENT_IS_NOT_FOUND.getDesc()+",entResNo["+entResNo+"]");
		}
		EntStatusInfo statusInfo = commonCacheService
				.searchEntStatusInfo(entCustId);
		CustIdGenerator.isEntStatusInfoExist(statusInfo, entCustId);
		Long currentCount = 0L;
		if (null != statusInfo.getCountBuyCards()) {
			currentCount = statusInfo.getCountBuyCards();
		}
		currentCount = currentCount + count;
		AsEntStatusInfo changeCount = new AsEntStatusInfo();
		changeCount.setEntCustId(entCustId);
		changeCount.setCountBuyCards(currentCount);
		entService.updateEntStatusInfo(changeCount, operCustId);
	}

	/**
	 * 检查多线程执行情况，若有异常，做回滚处理。
	 */
	public synchronized void batchAddCardsCheck(String threadKey) {
		String errMsg = rollbackKey.get(threadKey);
		if (rollbackKey.containsKey(threadKey)) {

			ArrayList<IBatchRollback> servs = rollbackServMap.get(threadKey);
			SystemLog.info(MOUDLENAME, "batchAddCardsCheck",
					"// 开启子线程执行 数据回滚清理工作" + rollbackKey);
			(new BatchRollbackThread(servs)).start();

			rollbackKey.remove(threadKey);
			throw new HsException(
					ErrorCodeEnum.CARDHOLDER_SAVE_ERROR.getValue(), errMsg);
		}
		rollbackServMap.remove(threadKey);// 清理本次执行子线程引用,释放内存
	}

	/**
	 * 添加回滚处理类
	 * 
	 * @param rollbackService
	 */
	public void addRollbackServ(String threadKey, IBatchRollback rollbackService) {
		// String key = getRollbackKey();
		synchronized (this) {
			if (rollbackServMap.get(threadKey) == null) {
				rollbackServMap.put(threadKey, new ArrayList<IBatchRollback>());
			}
		}
		rollbackServMap.get(threadKey).add(rollbackService);
	}

	public void addRollbackKey(String threadKey, String value) {
		rollbackKey.put(threadKey, value);
	}

	private String getRollbackKey() {
		return Thread.currentThread().getId()
				+ Thread.currentThread().getName();
	}

	/**
	 * 批量重发卡
	 * 
	 * @param operCustId
	 *            操作员客户号
	 * @param list
	 *            互生卡列表
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderService#batchResendCards(java.lang.String,
	 *      java.util.List)
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void batchResendCards(String operCustId, List<BsHsCard> list,
			String entResNo) throws HsException {
		if (StringUtils.isBlank(operCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数操作员客户号为空");
		}
		if (null == list) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数重发卡列表为空");
		}
		if (StringUtils.isBlank(entResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数企业互生号为空");
		}
		String custId = ""; // 持卡人客户号
		String resNo = ""; // 持卡人互生号
		List<String> custIdList = new ArrayList<String>();
		for (BsHsCard bsHsCard : list) {
			resNo = bsHsCard.getPerResNo().trim();
			custId = commonCacheService.findCustIdByResNo(resNo);
			CustIdGenerator.isCarderExist(custId, resNo);
			custIdList.add(custId);
		}
		batchCloseAccout(operCustId.trim(), custIdList);// 批量注销互生卡
		batchAddCards(operCustId, list, entResNo);// 批量新增互生卡
	}

	/**
	 * 持卡人注销
	 * 
	 * @param perCustId
	 *            持卡人客户号
	 * @param operCustId
	 *            操作员客户号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService#closeAccout(java.lang.String,
	 *      java.lang.String)
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void closeAccout(String perCustId, String operCustId)
			throws HsException {
		uCAsCardHolderService.closeAccout(perCustId, operCustId);
		try {
			// 通知搜索引擎
			searchUserService.delete(perCustId);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "closeAccout", "通知搜索引擎失败：", e);
		}
	}

	/**
	 * 批量注销
	 * 
	 * @param operCustId
	 *            地区平台操作员客户号
	 * @param entResNo
	 *            企业互生号
	 * @param list
	 *            持卡人客户号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderService#batchCloseAccout(java.lang.String,
	 *      java.lang.String, java.util.List)
	 */
	@Override
	public void batchCloseAccout(String operCustId, List<String> list)
			throws HsException {
		uCAsCardHolderService.batchCloseAccout(operCustId, list);
		// 通知搜索引擎
		try {
			searchUserService.batchDelete(list);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "batchCloseAccout", "通知搜索引擎失败：", e);
		}
	}

	/**
	 * 持卡人补卡
	 * 
	 * @param operCustId
	 *            操作员客户号
	 * @param bsHsCard
	 *            补卡信息
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderService#remakeCard(java.lang.String,
	 *      com.gy.hsxt.uc.bs.bean.consumer.BsRegHsCard)
	 */
	@Override
	public void remakeCard(String operCustId, BsHsCard bsHsCard)
			throws HsException {
		if (StringUtils.isBlank(operCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数操作员客户号为空");
		}
		if (null == bsHsCard) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数补卡信息为空");
		}
		if (StringUtils.isBlank(bsHsCard.getPerResNo())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数互生号为空");
		}
		if (StringUtils.isBlank(bsHsCard.getCryptogram())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数暗码为空");
		}
		String cryptogram = bsHsCard.getCryptogram().trim();
		String resNo = bsHsCard.getPerResNo().trim();
		String consumerId = commonCacheService.findCustIdByResNo(resNo);
		CustIdGenerator.isCarderExist(consumerId, resNo);
		String operatorId = operCustId.trim();
		if (StringUtils.isBlank(consumerId)) {
			throw new HsException(
					ErrorCodeEnum.CUST_ID_IS_NOT_FOUND.getValue(),
					ErrorCodeEnum.CUST_ID_IS_NOT_FOUND.getDesc());
		}
		// 查询旧互生卡的数据
		HsCard oldCard = commonCacheService.searchHsCardInfo(consumerId);
		CustIdGenerator.isHsCardExist(oldCard, consumerId);
		// 更新互生卡信息
		oldCard.setCryptogram(cryptogram); // 卡暗码
		oldCard.setPerCustId(consumerId); // 客户号
		oldCard.setUpdatedby(operatorId);// 更新操作人员ID
		Timestamp now = StringUtils.getNowTimestamp();
		oldCard.setUpdateDate(now);
		uCAsCardHolderService.updateHsCardInfo(oldCard);
	}

	/**
	 * 根据互生号查询持卡人的信息
	 * 
	 * @param resNo
	 *            互生号
	 * @return CardHolder(持卡人信息)
	 */
	public BsCardHolder searchCardHolderInfoByResNo(String resNo)
			throws HsException {
		if (StringUtils.isBlank(resNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数互生号为空");
		}
		String perResNo = resNo.trim();
		String custId = commonCacheService.findCustIdByResNo(perResNo);
		CustIdGenerator.isCarderExist(custId, perResNo);
		BsCardHolder bsCardHolder = searchCardHolderInfoByCustId(custId);
		return bsCardHolder;
	}

	/**
	 * 通过互生号查询客户号
	 * 
	 * @param resNo
	 *            互生号
	 * @return 持卡人客户号
	 * @throws HsException
	 */
	@Override
	public String findCustIdByResNo(String resNo) throws HsException {
		if (StringUtils.isBlank(resNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数互生号为空");
		}
		String custId = commonCacheService.findCustIdByResNo(resNo);
		CustIdGenerator.isCarderExist(custId, resNo);
		return custId;
	}

	/**
	 * 根据客户号查询持卡人的信息
	 * 
	 * @param custId
	 *            客户号
	 * @return CardHolder(持卡人信息)
	 */
	public BsCardHolder searchCardHolderInfoByCustId(String custId)
			throws HsException {
		CardHolder cardHolder = commonCacheService.searchCardHolderInfo(custId);
		CustIdGenerator.isCarderExist(cardHolder, custId);
		BsCardHolder bsCardHolder = new BsCardHolder();
		BeanCopierUtils.copy(cardHolder, bsCardHolder);
		return bsCardHolder;
	}

	/**
	 * 通过互生号查询互生卡的信息
	 * 
	 * @param resNo
	 * @return
	 */
	public BsHsCard searchHsCardInfoByResNo(String resNo) throws HsException {
		if (StringUtils.isBlank(resNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数互生号为空");
		}
		String perResNo = resNo.trim();
		String custId = commonCacheService.findCustIdByResNo(perResNo);
		CustIdGenerator.isCarderExist(custId, perResNo);
		BsHsCard bsHsCard = searchHsCardInfoByCustId(custId);
		return bsHsCard;
	}

	/**
	 * 通过客户号查询互生卡信息
	 * 
	 * @param custId
	 * @return
	 */
	public BsHsCard searchHsCardInfoByCustId(String perCustId)
			throws HsException {
		if (StringUtils.isBlank(perCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号为空");
		}
		HsCard hsCard = commonCacheService.searchHsCardInfo(perCustId);
		CustIdGenerator.isHsCardExist(hsCard, perCustId);
		BsHsCard bsHsCard = new BsHsCard();
		BeanCopierUtils.copy(hsCard, bsHsCard);
		return bsHsCard;
	}

	/**
	 * 更新登录时间和登录IP
	 * 
	 * @param custId
	 *            客户号
	 * @param loginIp
	 *            登录IP
	 * @param date
	 *            登录时间 格式 yyyy-mm-dd hh:mm:ss
	 * @see com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService#updateLoginInfo(java.lang.String,
	 *      java.lang.String, java.util.Date)
	 */
	@Override
	public void updateLoginInfo(String custId, String loginIp, String dateStr)
			throws HsException {
		uCAsCardHolderService.updateLoginInfo(custId, loginIp, dateStr);
	}

	public static void main(String[] args) {
		int a = 1111;
		for (int i = 0; i < 133; i++)
			System.out.println(i & 7);
	}
}
