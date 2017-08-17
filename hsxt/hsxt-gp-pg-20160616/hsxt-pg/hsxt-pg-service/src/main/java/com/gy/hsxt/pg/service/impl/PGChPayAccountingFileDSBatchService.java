/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.pg.bankadapter.chinapay.ChinapayOrderResultDTO;
import com.gy.hsxt.pg.bankadapter.chinapay.IChinapayBalanceCallback;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.ChinapayFacade;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.ChinapayUpopFacade;
import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants.ErrorCode;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.bankadapter.common.utils.MoneyAmountHelper;
import com.gy.hsxt.pg.common.constant.ConfigConst.TcConfigConsts;
import com.gy.hsxt.pg.common.constant.Constant;
import com.gy.hsxt.pg.common.constant.Constant.DownloadStatus;
import com.gy.hsxt.pg.common.constant.Constant.FileStatus;
import com.gy.hsxt.pg.common.constant.Constant.GPCheckType;
import com.gy.hsxt.pg.common.constant.Constant.LockBizType;
import com.gy.hsxt.pg.common.constant.Constant.PGAmountScale;
import com.gy.hsxt.pg.common.utils.StringUtils;
import com.gy.hsxt.pg.constant.PGConstant.PGErrorCode;
import com.gy.hsxt.pg.constant.PGConstant.PGPayChannel;
import com.gy.hsxt.pg.mapper.TPgChinapayOrderMapper;
import com.gy.hsxt.pg.mapper.vo.CHBalanceVo;
import com.gy.hsxt.pg.mapper.vo.TPgChinapayDayBalance;
import com.gy.hsxt.pg.service.IChinapayDayBalanceFileService;
import com.gy.hsxt.pg.service.IChinapayOrderStateSyncService;
import com.gy.hsxt.pg.service.IOperationLockService;
import com.gy.hsxt.pg.service.impl.parent.ParentAccountingFileDSBatchService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service.impl
 * 
 *  File Name       : PGChPayAccountingFileDSBatchService.java
 * 
 *  Creation Date   : 2016年2月19日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : PG支付网关生成银联对账文件生成调度接口(提供给DS系统调用)
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service("pgChPayAccountingFileDSBatchService")
public class PGChPayAccountingFileDSBatchService extends
		ParentAccountingFileDSBatchService {

	@Autowired
	private TPgChinapayOrderMapper chinapayOrderMapper;

	@Autowired
	private ChinapayFacade chinapayB2cFacade;

	@Autowired
	private ChinapayUpopFacade chinapayUpopFacade;

	@Autowired
	private IChinapayDayBalanceFileService balanceFileService;

	@Autowired
	private IChinapayOrderStateSyncService orderStateSyncService;

	@Autowired
	private IOperationLockService lockService;

	/**
	 * 获取CHK文件配置的key
	 * 
	 * @return
	 */
	protected String getTcChkFileNameKey() {
		return TcConfigConsts.KEY_SYS_TC_CHK_CH_GP_PAY;
	}

	/**
	 * 获取DET文件配置的key
	 * 
	 * @return
	 */
	protected String getTcDetFileNameKey() {
		return TcConfigConsts.KEY_SYS_TC_DET_CH_GP_PAY;
	}

	/**
	 * 创建对账文件
	 * 
	 * @param balanceDate
	 *            前一天日期
	 * @throws Exception
	 */
	@Override
	protected void createBalanceFile(String balanceDate) throws Exception {
		
		// 形如: CH_GP_20160228
		String tcFileNamePrefix = parseFileNamePrefixByDate(balanceDate);

		try {
			// 1、首先判断是否已经成功下载了银联所有的对账文件(B2C、快捷、手机支付)
			this.prepareDataBeforeDoing(balanceDate);

			// 2、检测对账文件是否正在生成中
			this.checkAccFileStatus(tcFileNamePrefix);

			// 3、向表T_GP_CHECK_ACCOUNTS_FILE中插入对账文件信息
			this.insertAccFileInfo2DB(balanceDate, GPCheckType.PAYMENT,
					tcFileNamePrefix);

			// 4、处理生成对账文件逻辑
			this.actionCreateBalanceFile(balanceDate, tcFileNamePrefix);

		} catch (Exception ex) {
			// 释放锁(如果不释放, 下次将无法正常重新做该业务)
			lockService.deleteLock(balanceDate, LockBizType.TRIGGER_DS);

			throw ex;
		}
	}

	/**
	 * 判断银联的所有的对账文件下载已经全部成功
	 * 
	 * @param balanceDate
	 * @return
	 */
	private boolean prepareDataBeforeDoing(String balanceDate) throws Exception {

		// 获得银联对账文件下载到本地的路径
		List<TPgChinapayDayBalance> balanceDatas = balanceFileService
				.queryDayBalanceFileInfo(balanceDate);

		if ((null == balanceDatas) || (0 >= balanceDatas.size())) {
			throw new HsException(PGErrorCode.INNER_ERROR,
					"中国银联尚未发送对账文件下载通知, 对账文件还不存在, 不能执行对账操作！");
		}

		Map<Integer, TPgChinapayDayBalance> balanceDatasMap = toBalanceDatasMap(balanceDatas);

		// 暂时只有B2C、UPOP才能对账, 手机支付暂时银联不提供自动对账功能
		int[] payChannels = new int[] { PGPayChannel.B2C, PGPayChannel.UPOP };

		for (int payChannel : payChannels) {
			TPgChinapayDayBalance dayBalance = balanceDatasMap.get(payChannel);

			if (null == dayBalance) {
				throw new HsException(PGErrorCode.INNER_ERROR,
						"中国银联尚未发送对账文件下载通知, 对账文件还不存在, 不能执行对账操作！ payChannel="
								+ payChannel);
			}

			// 下载状态
			int downloadStatus = dayBalance.getDownloadStatus();

			if (DownloadStatus.READY == downloadStatus) {
				throw new HsException(PGErrorCode.INNER_ERROR,
						"正在下载银联的对账文件, 暂时不能执行对账操作, 请10分钟后再试！");
			}

			// 重新发起下载, 如果下载失败, 不进行重试
			if ((DownloadStatus.FAILED == downloadStatus)
					|| (DownloadStatus.VER_FAILED == downloadStatus)) {
				balanceFileService.dowloadDayBalanceFile(dayBalance, false);
			}
		}

		return true;
	}

	/**
	 * 生成对账文件
	 * 
	 * @param balanceDate
	 * @param tcFileNamePrefix
	 * @throws Exception
	 */
	private void actionCreateBalanceFile(String balanceDate,
			String tcFileNamePrefix) throws Exception {

		try {
			// 更新对账文件状态：正在创建中...
			updateAccFileInfoByFileName(tcFileNamePrefix, FileStatus.CREATING);

			// 解析对账文件
			doParsingBalanceFiles(balanceDate);

			// 更新对账文件状态：创建成功
			updateAccFileInfoByFileName(tcFileNamePrefix, FileStatus.SUCCESS);
		} catch (Exception ex) {

			// 更新对账文件状态：创建失败
			updateAccFileInfoByFileName(tcFileNamePrefix, FileStatus.FAILED);

			throw ex;
		}
	}

	/**
	 * 解析对账文件
	 * 
	 * @param balanceDate
	 * @throws Exception
	 */
	private void doParsingBalanceFiles(String balanceDate) throws Exception {

		ChinapayBalanceCallback callback = new ChinapayBalanceCallback(
				balanceDate);

		// 获得银联对账文件下载到本地的路径
		List<TPgChinapayDayBalance> balanceDatas = balanceFileService
				.queryDayBalanceFileInfo(balanceDate);

		// 开始解析银联对账文件...
		for (TPgChinapayDayBalance balance : balanceDatas) {

			// 文件保存路径
			String localSavePath = balance.getFileSavePath()
					+ balance.getFileName();

			try {
				// B2C网银支付
				if (PGPayChannel.B2C == balance.getPayChannel()) {
					chinapayB2cFacade.getDayBalanceWorker()
							.doParsingBalanceFile(localSavePath, callback);
				}
				// UPOP快捷支付
				else if (PGPayChannel.UPOP == balance.getPayChannel()) {
					chinapayUpopFacade.getDayBalance().doParsingBalanceFile(
							localSavePath, callback);
				}
			} catch (BankAdapterException ex) {
				// 严重错误：对账文件验证签名失败！！！
				if (ErrorCode.FAILED_CHECK_SIGN == ex.getErrorCode()) {
					balanceFileService.updateDayBalanceFileInfo(
							balance.getFileName(), DownloadStatus.VER_FAILED);
				}

				// 异常一定要抛出！！！！！
				throw ex;
			}
		}

		// 生成CHK文件
		callback.createChkFileAfterFinished();
	}

	/**
	 * 转换为map结构
	 * 
	 * @param balanceDatas
	 * @return
	 */
	private Map<Integer, TPgChinapayDayBalance> toBalanceDatasMap(
			List<TPgChinapayDayBalance> balanceDatas) {
		Map<Integer, TPgChinapayDayBalance> map = new HashMap<Integer, TPgChinapayDayBalance>(
				3);

		for (TPgChinapayDayBalance balance : balanceDatas) {
			map.put(balance.getPayChannel(), balance);
		}

		return map;
	}

	/** 解析银联对账文件时, 回调解析后的数据 **/
	private class ChinapayBalanceCallback implements IChinapayBalanceCallback {

		// 总页数
		private int totalPages = 0;
		
		// 当前支付网关关注的银联订单号特征表达式
		private String orderIdRangeRegex = null;

		// 对账日期
		private String balanceDate = null;

		// CHK文件内容
		private List<String> chkFileContents = new ArrayList<String>(5);

		public ChinapayBalanceCallback(String balanceDate) {

			this.doInitialize();

			this.balanceDate = balanceDate;
		}

		@Override
		public void notifyPageData(List<ChinapayOrderResultDTO> pageDatas,
				int currPage) throws IOException {
			
			currPage += totalPages;
			
			// 形如: CH_GP_20160228
			String tcFileNamePrefix = parseFileNamePrefixByDate(balanceDate);

			// 更新对账文件状态：正在创建中...
			updateAccFileInfoByFileName(tcFileNamePrefix, FileStatus.CREATING);

			// 文件名：GP_BS_PAY_$YYYYMMDD_DET_$SN
			String tcDetFileName = getTcDetFileName(balanceDate, currPage);
			String tcNfsShareRootDir = getTcNfsShareRootDir(balanceDate);

			List<CHBalanceVo> chinapayOrders = convert2CHBalanceVo(pageDatas);

			// ---------------生成对账DET明细文件--------------------
			FileUtils.writeLines(new File(tcNfsShareRootDir + tcDetFileName),
					Constant.ENCODING_UTF8, chinapayOrders,
					Constant.LINE_SEPARATOR);

			// 添加记录到CHK中, 计算行数时去除结尾的end这一行
			chkFileContents.add(tcDetFileName + "|" + (chinapayOrders.size() - 1));

			// ====== 进行支付单状态同步, 数据量大时, 可能会减缓文件生成效率 =====
			orderStateSyncService.actionOrderStateSync(pageDatas);
		}

		@Override
		public void notifyFinished(int totalRows, int totalPages)
				throws IOException {
			this.totalPages += totalPages;
		}

		@Override
		public boolean isMyFocus(ChinapayOrderResultDTO orderItem) {
			return StringUtils.avoidNull(orderItem.getOrderNo()).matches(
					orderIdRangeRegex);
		}

		@Override
		public int getRowsPerPage() {
			return HsPropertiesConfigurer
					.getPropertyIntValue(TcConfigConsts.KEY_SYS_TC_BALANCE_COUNT);
		}

		/**
		 * 生成check文件
		 * 
		 * @throws IOException
		 */
		public void createChkFileAfterFinished() throws IOException {

			// 文件名：GP_BS_PAY_$YYYYMMDD_CHK
			String tcChkFileName = getTcChkFileName(balanceDate);
			String tcNfsShareRootDir = getTcNfsShareRootDir(balanceDate);

			// 生成对账CHK校验文件处理, CHK文件内容：文件名|数据记录总数
			chkFileContents.add(0, String.valueOf(totalPages));
			chkFileContents.add(HsPropertiesConfigurer
					.getProperty(TcConfigConsts.KEY_SYS_TC_BALANCE_DATA_END));

			// ----------------生成对账CHK校验文件-------------------
			FileUtils.writeLines(new File(tcNfsShareRootDir + tcChkFileName),
					Constant.ENCODING_UTF8, chkFileContents,
					Constant.LINE_SEPARATOR);
		}

		/**
		 * 执行初始化
		 */
		private void doInitialize() {
			String[] arr = HsPropertiesConfigurer.getProperty(
					TcConfigConsts.KEY_SYS_TC_CHINAPAY_ORDERNO_RANGE).split(
					"\\|");

			StringBuilder buf = new StringBuilder("^([");

			for (String s : arr) {
				buf.append(s).append("|");
			}

			buf.append("])\\d{1,}$");

			orderIdRangeRegex = buf.toString();
		}

		/**
		 * 数据结构转换, 转换为互生系统内部约定的对账文件格式
		 * 
		 * @param pageDatas
		 * @return
		 */
		private List<CHBalanceVo> convert2CHBalanceVo(
				List<ChinapayOrderResultDTO> pageDatas) {

			List<CHBalanceVo> chBalanceVoList = new ArrayList<CHBalanceVo>(
					getRowsPerPage());

			if (null != pageDatas) {
				for (ChinapayOrderResultDTO orderItem : pageDatas) {
					chBalanceVoList.add(conver2CHGPBalanceVo(orderItem));
				}
			}

			if (0 < chBalanceVoList.size()) {
				chBalanceVoList.add(new CHBalanceVo(true));
			}

			return chBalanceVoList;
		}

		/**
		 * 数据结构转换, 转换为互生系统内部约定的对账文件格式
		 * 
		 * @param orderItem
		 * @return
		 */
		private CHBalanceVo conver2CHGPBalanceVo(
				ChinapayOrderResultDTO orderItem) {

			String transAmount = MoneyAmountHelper.fromFenToYuan(orderItem
					.getPayAmount(), PGAmountScale.SIX);

			int transType = StringUtils.str2Int(orderItem.getTransType());

			// 添加转换的代码
			CHBalanceVo dto = new CHBalanceVo();
			dto.setBankOrderNo(orderItem.getOrderNo());
			dto.setBankOrderDate(orderItem.getTranTime());
			dto.setTransSeqId(orderItem.getBankSeqNo());
			dto.setTransStatus(orderItem.getOrderStatus().getIntValue());
			dto.setTransAmount(new BigDecimal(transAmount));
			dto.setTransType(transType);

			return dto;
		}
	}
}
