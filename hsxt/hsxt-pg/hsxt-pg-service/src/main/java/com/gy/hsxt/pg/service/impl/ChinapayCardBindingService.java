/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.inject.Singleton;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.gy.hsi.common.utils.DistributedSyncLock;
import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.pg.api.IPGNotifyService;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.ChinapayUpopFacade;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopConst.UpopCardBindingStat;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.params.UpopBindingNoResult;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.params.UpopCardBindingParam;
import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;
import com.gy.hsxt.pg.bankadapter.common.utils.MoneyAmountHelper;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;
import com.gy.hsxt.pg.bean.PGQuickPayBindingNo;
import com.gy.hsxt.pg.common.constant.ConfigConst;
import com.gy.hsxt.pg.common.constant.Constant;
import com.gy.hsxt.pg.common.constant.Constant.PGCallbackBizType;
import com.gy.hsxt.pg.common.constant.Constant.PGQuickBindingStatus;
import com.gy.hsxt.pg.common.utils.CallbackUrlHelper;
import com.gy.hsxt.pg.common.utils.StringUtils;
import com.gy.hsxt.pg.common.utils.ThreadHelper;
import com.gy.hsxt.pg.constant.PGConstant.PGChannelProvider;
import com.gy.hsxt.pg.constant.PGConstant.PGPayChannel;
import com.gy.hsxt.pg.mapper.TPgBankcardBindingMapper;
import com.gy.hsxt.pg.mapper.TPgBankcardBindingTmpMapper;
import com.gy.hsxt.pg.mapper.vo.TPgBankcardBinding;
import com.gy.hsxt.pg.mapper.vo.TPgBankcardBindingTmp;
import com.gy.hsxt.pg.service.IChinapayCardBindingService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service.impl
 * 
 *  File Name       : ChinapayCardBindingService.java
 * 
 *  Creation Date   : 2016年6月7日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 处理银行卡绑定签约逻辑(包括：获取、通知、本地表更新)
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service("chinapayCardBindingService")
@Singleton
public class ChinapayCardBindingService implements IChinapayCardBindingService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IPGNotifyService pgNotifyService;

	@Autowired
	private ChinapayUpopFacade chinapayUpopFacade;

	@Autowired
	private TPgBankcardBindingMapper bankcardBindingMapper;
	
	@Autowired
	private TPgBankcardBindingTmpMapper bankcardBindingTemp$Mapper;

	/** 分布式同步锁 */
	private DistributedSyncLock syncLock;

	/**
	 * 通知签约号
	 * 
	 * @param quickPayBindingNo
	 * @param merType
	 */
	public void notifyBindingNo(PGQuickPayBindingNo quickPayBindingNo,
			int merType) {

		if (null != quickPayBindingNo) {
			// 更新签约号
			this.updateBankCardBinding(quickPayBindingNo);

			// 通知签约号
			this.doNotifyBindingNo(quickPayBindingNo, merType);
		}
	}

	/**
	 * 获取并通知签约号到GP->UC
	 * 
	 * @param bankCardNo
	 * @param bankId
	 * @param bankCardType
	 * @param bankOrderNo
	 * @param merType
	 * @throws Exception
	 */
	public void fetchBindingNoFromCP(String bankCardNo, String bankId,
			int bankCardType, String bankOrderNo, int merType) {

		// 检查是否已经签约过, 如果签约过直接从本地取, 并通知GP->UC
		boolean isBinding = checkAndNotifyBindingNo(bankCardNo, bankOrderNo,
				merType);

		// 未签约过
		if (!isBinding) {
			// 同步更新本地的签约信息(不带签约号)
			this.insertTempBankCardBinding(bankCardNo, bankId, bankCardType);

			// 组装查询所用数据调用银行接口，获取URL
			int payChannel = PGPayChannel.UPOP;
			int bizType = PGCallbackBizType.CALLBACK_BINDING;

			// 后台通知url
			String notifyUrl = CallbackUrlHelper.getNotifyUrl(
					PGChannelProvider.CHINAPAY, payChannel, merType, bizType,
					bankOrderNo);

			UpopCardBindingParam params = new UpopCardBindingParam();
			params.setBankId(bankId);
			params.setCardNo(bankCardNo);
			params.setCardType(bankCardType);
			params.setOrderDate(DateUtils.dateToString(new Date(), "yyyyMMdd"));
			params.setNotifyURL(notifyUrl);

			// 向银联发起签约确认请求
			boolean success = chinapayUpopFacade.getCardBinding()
					.doBankCardBindingRequest(params);

			logger.info("向银联发起签约确认请求, 成功or失败?  结果：" + success);
		}
	}

	/**
	 * 插入本地的签约信息[临时表插入]
	 * 
	 * @param bankCardNo
	 * @param bankId
	 * @param bankCardType
	 */
	public void insertTempBankCardBinding(String bankCardNo, String bankId,
			int bankCardType) {

		try {
			TPgBankcardBindingTmp tempBankcardBinding = bankcardBindingTemp$Mapper
					.selectByPrimaryKey(bankCardNo);

			if (null == tempBankcardBinding) {
				TPgBankcardBindingTmp tempRecord = new TPgBankcardBindingTmp();
				tempRecord.setBankCardNo(bankCardNo);
				tempRecord.setBankId(bankId);
				tempRecord.setBankCardType(bankCardType);
				tempRecord.setBindingStatus(PGQuickBindingStatus.UNKNOWN);
				tempRecord.setCreatedDate(DateUtils.getCurrentDate());

				bankcardBindingTemp$Mapper.insert(tempRecord);
			}
		} catch (Exception e) {
			logger.info("插入本地签约号状态异常：", e);
		}
	}

	/**
	 * 定时从银联查询签约号(30秒一次)
	 */
	@Scheduled(initialDelay = 30 * 1000, fixedDelay = 120 * 1000)
	protected void queryBindingNoFromCPByInterval() {

		// 初始化分布式同步锁对象
		if (null == syncLock) {
			String dubboRegAddress = HsPropertiesConfigurer
					.getProperty(ConfigConst.DUBBO_REG_ADDRESS);

			syncLock = DistributedSyncLock.build(Constant.ZK_ROOT_NODE_BINDING,
					Constant.ZK_SUB_NODE_BINDING, dubboRegAddress);
		}

		// 获取分布式同步锁对象!!!!!!!!!!!!!!!!!!!
		if (!syncLock.getLock()) {
			return;
		}

		logger.debug("---queryBindingNoFromCPByInterval--");

		// 只查询20秒之前的记录
		Date dateBefore = DateUtils.addSeconds(DateUtils.getCurrentDate(), -20);
		String strDateBefore = DateUtils.dateToString(dateBefore,
				"yyyy-MM-dd HH:mm:ss");

		// 如果不存在要处理的记录, 直接返回
		if (!isExistUnknownBinding(strDateBefore)) {
			return;
		}

		List<TPgBankcardBindingTmp> unknownBindingList;

		int startIndex = 0;
		int pageSize = 100;

		while (true) {
			// 查询2分钟之前、签约状态为未知的记录
			unknownBindingList = bankcardBindingTemp$Mapper
					.selectByBindingStatus(strDateBefore,
							PGQuickBindingStatus.UNKNOWN, startIndex, pageSize);

			if ((null == unknownBindingList)
					|| (0 >= unknownBindingList.size())) {
				break;
			}

			logger.debug("--- unknownBindingList size="
					+ unknownBindingList.size() + "---");

			for (TPgBankcardBindingTmp tempRecord : unknownBindingList) {
				if (StringHelper.isEmpty(tempRecord.getBindingNo())) {
					// 从银联拉取签约号
					this.fetchAndNotifyBindingNoFromCP(
							tempRecord.getBankCardNo(), tempRecord.getBankId(),
							tempRecord.getBankCardType());

					// 考虑到银联的流量限制(35秒一次)
					ThreadHelper.sleepAmoment(35000);
				} else {
					tempRecord
							.setBindingStatus(PGQuickBindingStatus.IS_BINDING);
					bankcardBindingMapper.updateByPrimaryKey(tempRecord);
				}
			}

			startIndex += pageSize;
		}
	}
	
	/**
	 * 判断是否存在未知签约号
	 * 
	 * @param strDateBefore
	 * @return
	 */
	private boolean isExistUnknownBinding(String strDateBefore) {
		
		// 查询是否存在未知的绑定签约
		int count = bankcardBindingTemp$Mapper.countByBindingStatus(
				strDateBefore, PGQuickBindingStatus.UNKNOWN);

		return (0 < count);
	}

	/**
	 * 更新本地的签约信息[临时表和正式表一起更新]
	 * 
	 * @param pgQuickPayBindingNo
	 */
	private void updateBankCardBinding(PGQuickPayBindingNo pgQuickPayBindingNo) {

		try {
			String bindingNo = pgQuickPayBindingNo.getBindingNo();
			String bankCardNo = pgQuickPayBindingNo.getBankCardNo();

			if (StringHelper.isExistEmpty(bindingNo, bankCardNo)) {
				logger.info("Either bindingNo or bankCardNo is null !");

				return;
			}

			BigDecimal transLimit = MoneyAmountHelper
					.formatAmount2BigDecimal(pgQuickPayBindingNo
							.getTransLimit());
			BigDecimal sumLimit = MoneyAmountHelper
					.formatAmount2BigDecimal(pgQuickPayBindingNo.getSumLimit());

			TPgBankcardBindingTmp tempRecord = bankcardBindingTemp$Mapper
					.selectByPrimaryKey(bankCardNo);

			if (null == tempRecord) {
				tempRecord = new TPgBankcardBindingTmp();
			}

			tempRecord.setBankCardNo(bankCardNo);
			tempRecord.setExpireDate(pgQuickPayBindingNo.getExpireDate());
			tempRecord.setUpdatedDate(DateUtils.getCurrentDate());
			tempRecord.setBindingStatus(PGQuickBindingStatus.IS_BINDING); // 已签约
			tempRecord.setBindingNo(bindingNo);
			tempRecord.setTransLimit(transLimit);
			tempRecord.setSumLimit(sumLimit);

			// 更新临时表
			bankcardBindingTemp$Mapper.updateByPrimaryKey(tempRecord);

			// 签约信息从临时表转移到正式表
			this.moveTemp2TPgBankcardBinding(tempRecord);
		} catch (Exception e) {
			String json = StringUtils.change2Json(pgQuickPayBindingNo);

			logger.info("更新本地签约号状态异常：" + json, e);
		}
	}

	/**
	 * 检查并通知签约号
	 * 
	 * @param bankCardNo
	 * @param bankOrderNo
	 * @param merType
	 * @return
	 */
	private boolean checkAndNotifyBindingNo(String bankCardNo,
			String bankOrderNo, int merType) {

		// 从正式表查询是否已经绑定
		TPgBankcardBinding bankcardBinding = bankcardBindingMapper
				.selectByPrimaryKey(bankCardNo);

		boolean isBinding = false;

		if (null != bankcardBinding) {
			String bindingNo = bankcardBinding.getBindingNo();
			Date expireDate = bankcardBinding.getExpireDate();

			if (!StringHelper.isEmpty(bindingNo)
					&& !DateUtils.isCurrDateAfter(expireDate)) {
				isBinding = true;
			}
		}

		// 如果已经绑定, 则只需把签约号通知给UC即可
		if (isBinding) {
			BigDecimal transLimit = bankcardBinding.getTransLimit();
			BigDecimal sumLimit = bankcardBinding.getSumLimit();

			PGQuickPayBindingNo quickPayBindingNo = new PGQuickPayBindingNo();
			quickPayBindingNo.setBankCardNo(bankCardNo);
			quickPayBindingNo.setBankOrderNo(bankOrderNo);
			quickPayBindingNo.setBindingNo(bankcardBinding.getBindingNo());
			quickPayBindingNo.setExpireDate(bankcardBinding.getExpireDate());

			if (null != sumLimit) {
				quickPayBindingNo.setSumLimit(sumLimit.toString());
			}

			if (null != transLimit) {
				quickPayBindingNo.setTransLimit(transLimit.toString());
			}

			// 通知签约号到GP-->UC
			this.doNotifyBindingNo(quickPayBindingNo, merType);
		}

		return isBinding;
	}

	/**
	 * 通知签约号
	 * 
	 * @param quickPayBindingNo
	 * @param merType
	 */
	private void doNotifyBindingNo(PGQuickPayBindingNo quickPayBindingNo,
			int merType) {

		if (null == quickPayBindingNo) {
			logger.info("quickPayBindingNo is null !!");

			return;
		}

		try {
			// 向UC用户中心进行签约号通知(通过互生系统的GP通知即可)
			boolean success = pgNotifyService
					.notifyQuickPayBindingNo(quickPayBindingNo);

			logger.info(StringHelper.joinString("通知GP同步签约号, 成功or失败?  结果："
					+ success, " quickPayBindingNo=",
					StringUtils.change2Json(quickPayBindingNo)));
		} catch (Exception e) {
			logger.error("获取并通知签约号到用户中心时, 发生异常！！", e);
		}
	}

	/**
	 * 从银联获取并通知签约号到GP
	 * 
	 * @param bankCardNo
	 * @param bankId
	 * @param bankCardType
	 * @throws Exception
	 */
	private void fetchAndNotifyBindingNoFromCP(String bankCardNo,
			String bankId, int bankCardType) {

		// 查询签约号
		UpopBindingNoResult result = null;

		try {
			result = chinapayUpopFacade.getBindingNoQuery().doQueryBindingNo(
					bankCardNo, bankId, String.valueOf(bankCardType));
		} catch (Exception e) {
			logger.error("查询UPOP快捷支付签约号失败：" + e.getMessage(), e);
		}

		if (null == result) {
			return;
		}

		if (logger.isInfoEnabled()) {
			logger.info("查询到的UPOP快捷支付签约号：" + StringUtils.change2Json(result));
		}

		// 签约成功
		if (UpopCardBindingStat.SUCCESS.equals(result.getStat())) {
			Date expiryDate = null;

			if (null != result.getExpiry()) {
				expiryDate = DateUtils.stringToDate(result.getExpiry(),
						"yyyyMMdd");
			}

			PGQuickPayBindingNo quickPayBindingNo = new PGQuickPayBindingNo(
					bankCardNo, result.getBindingNo(), expiryDate,
					result.getTransLimitStrValue(),
					result.getSumLimitStrValue());

			// 更新本地的签约信息
			this.updateBankCardBinding(quickPayBindingNo);

			try {
				// 向UC用户中心进行签约号通知(通过互生系统的GP通知即可)
				boolean success = pgNotifyService
						.notifyQuickPayBindingNo(quickPayBindingNo);

				logger.info("通知GP同步签约号, 成功or失败?  结果：" + success);
			} catch (Exception e) {
				logger.info("通知GP同步签约号, 成功or失败?  结果：" + false, e);
			}
		}
		// 未签约或未知
		else {
			this.update2NonBinding(bankCardNo);
		}
	}

	/**
	 * 设置为未签约
	 * 
	 * @param bankCardNo
	 */
	private void update2NonBinding(String bankCardNo) {
		try {
			TPgBankcardBindingTmp record = bankcardBindingTemp$Mapper
					.selectByPrimaryKey(bankCardNo);

			if ((null != record) && StringHelper.isEmpty(record.getBindingNo())) {
				record.setUpdatedDate(DateUtils.getCurrentDate());
				record.setBindingStatus(PGQuickBindingStatus.NON_BINDING); // 未签约

				bankcardBindingTemp$Mapper.updateByPrimaryKey(record);
			}
		} catch (Exception e) {
			logger.info("setBankCardNonBinding() 异常：", e);
		}
	}
	
	/**
	 * 签约信息从临时表转移到正式表
	 * 
	 * @param tmpRecord
	 */
	private void moveTemp2TPgBankcardBinding(TPgBankcardBindingTmp tmpRecord) {

		String bankCardNo = tmpRecord.getBankCardNo();

		try {
			boolean flag = true;

			// 首先, 从正式表中删除可能存在的
			TPgBankcardBinding bankcardBinding = bankcardBindingMapper
					.selectByPrimaryKey(bankCardNo);

			if (null != bankcardBinding) {
				BeanUtils.copyProperties(tmpRecord, bankcardBinding);

				flag = (0 < bankcardBindingMapper
						.updateByPrimaryKey(bankcardBinding));
			} else {
				TPgBankcardBinding record = new TPgBankcardBinding();
				
				BeanUtils.copyProperties(tmpRecord, record);
				
				flag = (0 < bankcardBindingMapper.insert(record));
			}

			if (flag) {
				bankcardBindingTemp$Mapper.deleteByPrimaryKey(bankCardNo);
			}
		} catch (Exception e) {
			logger.info("", e);
		}
	}
}