/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.pg.bankadapter.chinapay.b2c.ChinapayFacade;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.ChinapayUpopFacade;
import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;
import com.gy.hsxt.pg.common.bean.CPBalanceFileName;
import com.gy.hsxt.pg.common.constant.Constant.DownloadStatus;
import com.gy.hsxt.pg.common.constant.Constant.RetryBussinessType;
import com.gy.hsxt.pg.common.utils.FileDownloadUtils;
import com.gy.hsxt.pg.common.utils.StringUtils;
import com.gy.hsxt.pg.common.utils.TimeSecondsSeqWorker;
import com.gy.hsxt.pg.constant.PGConstant.PGPayChannel;
import com.gy.hsxt.pg.mapper.TPgChinapayDayBalanceMapper;
import com.gy.hsxt.pg.mapper.vo.TPgChinapayDayBalance;
import com.gy.hsxt.pg.service.IChinapayDayBalanceFileService;
import com.gy.hsxt.pg.service.impl.parent.BaseAccountingService;
import com.gy.hsxt.pg.task.RetryManager;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service.impl
 * 
 *  File Name       : ChinapayDayBalanceFileService.java
 * 
 *  Creation Date   : 2016年2月26日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联每日对账文件操作接口
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service("chinapayDayBalanceFileService")
public class ChinapayDayBalanceFileService extends BaseAccountingService
		implements IChinapayDayBalanceFileService {

	@Autowired
	private TPgChinapayDayBalanceMapper dayBalanceMapper;

	@Autowired
	private RetryManager retryManager;

	@Autowired
	private ChinapayFacade chinapayB2cFacade;

	@Autowired
	private ChinapayUpopFacade chinapayUpopFacade;

	@Override
	public boolean dowloadDayBalanceFile(TPgChinapayDayBalance dayBalance,
			boolean isRetryWhenFailed) throws Exception {

		if (null == dayBalance) {
			return false;
		}

		boolean success = false;

		String fileName = dayBalance.getFileName();
		String balanceDate = DateUtils.dateToString(
				dayBalance.getBalanceDate(), "yyyyMMdd");

		try {
			// 删除旧的每日对账文件
			this.deleteFile(balanceDate, fileName, false);

			// 修改下载状态:待下载, 可以避免连续请求时的处理错误!!!!
			this.updateDayBalanceFileInfo(fileName, DownloadStatus.READY);

			// 重新下载
			success = FileDownloadUtils.download(dayBalance.getDownloadUrl(),
					(dayBalance.getFileSavePath() + fileName));
		} catch (Exception ex) {
			logger.error("下载银联通知的对账文件失败！！downloadUrl="
					+ dayBalance.getDownloadUrl());

			throw ex;
		} finally {
			int downloadStatus = success ? DownloadStatus.SUCCESS
					: DownloadStatus.FAILED;

			// 如果下载失败失败需要重试
			if (isRetryWhenFailed) {
				// 重试下载
				boolean isContinueRetry = retryManager.insertOrUpdateRetry(
						dayBalance.getId(),
						RetryBussinessType.DOWNLOAD_BALANCE_REQ, success);

				// 重试了最大次数仍然失败, 才认为是失败, 注意：重试过程中失败不记录失败状态!!!!!!!!!!!
				if (!success && !isContinueRetry) {
					downloadStatus = DownloadStatus.FAILED;
				} else if (!success) {
					downloadStatus = DownloadStatus.READY;
				}
			}

			// 更新对账文件下载状态
			this.updateDayBalanceFileInfo(fileName, downloadStatus);
		}

		return success;
	}

	@Override
	public boolean verifyDayBalanceFile(TPgChinapayDayBalance dayBalance)
			throws Exception {

		// 文件保存路径
		String localSavePath = dayBalance.getFileSavePath()
				+ dayBalance.getFileName();

		boolean success = true;

		if (PGPayChannel.B2C == dayBalance.getPayChannel()) {
			success = chinapayB2cFacade.getDayBalanceWorker()
					.verifyBalanceFile(localSavePath);
		} else if (PGPayChannel.UPOP == dayBalance.getPayChannel()) {
			success = chinapayUpopFacade.getDayBalance().verifyBalanceFile(
					localSavePath);
		}

		// 严重错误：对账文件验证签名失败！！！
		if (!success) {
			updateDayBalanceFileInfo(dayBalance.getFileName(),
					DownloadStatus.VER_FAILED);
		}

		return success;
	}

	@Override
	public TPgChinapayDayBalance insertDayBalanceFileInfo2DB(String downloadUrl)
			throws Exception {

		// 808080290000001_20100201_20100202035959.txt
		String fullFileName = downloadUrl.replaceAll("^.*\\/", "");

		// 记录下载初始化信息
		TPgChinapayDayBalance dayBalance = dayBalanceMapper
				.selectByFileName(fullFileName);

		if (null == dayBalance) {
			dayBalance = initChinapayDayBalance(downloadUrl);

			try {
				dayBalanceMapper.insert(dayBalance);
			} catch (Exception e) {
				dayBalance = dayBalanceMapper.selectByFileName(fullFileName);

				if (null == dayBalance) {
					throw e;
				}
			}
		} else {
			dayBalance.setDownloadUrl(downloadUrl);

			dayBalanceMapper.updateStatusByFileName(dayBalance);
		}

		return dayBalance;
	}

	@Override
	public void updateDayBalanceFileInfo(String fileName, int downloadStatus) {

		TPgChinapayDayBalance dayBalance = new TPgChinapayDayBalance();
		dayBalance.setFileName(fileName);
		dayBalance.setDownloadStatus(downloadStatus);
		dayBalance.setUpdatedDate(DateUtils.getCurrentDate());

		// 如果失败, 下载失败次数加1
		if (DownloadStatus.FAILED == downloadStatus) {
			int failedCounts = dayBalance.getDownloadFailCounts() + 1;
			dayBalance.setDownloadFailCounts(failedCounts);
		}

		try {
			dayBalanceMapper.updateStatusByFileName(dayBalance);
		} catch (Exception e) {
			logger.error("更新chinapayDayBalance对账文件状态失败：", e);
		}
	}

	@Override
	public List<TPgChinapayDayBalance> queryDayBalanceFileInfo(
			String balanceDate) {

		Date date = DateUtils.parse2yyyyMMddDate(balanceDate);

		List<TPgChinapayDayBalance> queryResultList = dayBalanceMapper
				.selectByBalanceDate(date);

		if (null != queryResultList) {
			return queryResultList;
		}

		return new ArrayList<TPgChinapayDayBalance>(1);
	}

	@Override
	public TPgChinapayDayBalance queryDayBalanceFileInfoByFileName(
			String fileName) {
		return dayBalanceMapper.selectByFileName(fileName);
	}

	/**
	 * 创建TPgChinapayDayBalance对象
	 * 
	 * @param downloadUrl
	 * @return
	 * @throws Exception
	 */
	private TPgChinapayDayBalance initChinapayDayBalance(String downloadUrl)
			throws Exception {

		String fullFileName = downloadUrl.replaceAll("^.*\\/", "");

		// 解析downloadUrl，得到文件名，merid
		CPBalanceFileName balanceFileName = new CPBalanceFileName(fullFileName);

		// NFS保存的根目录
		String fileRemotePath = getTcNfsShareRootDir(balanceFileName
				.getTransDate());

		// 商户号
		String merId = balanceFileName.getMerId();

		// 支付渠道
		int payChannel = getPayChannelByMerId(merId);

		// 对账日期
		Date balanceDate = DateUtils.stringToDate(
				balanceFileName.getTransDate(), "yyyyMMdd");

		TPgChinapayDayBalance dayBalance = new TPgChinapayDayBalance();

		dayBalance.setId(TimeSecondsSeqWorker.timeNextId16());
		dayBalance.setMerId(merId);
		dayBalance.setPayChannel(payChannel);
		dayBalance.setBalanceDate(balanceDate);
		dayBalance.setDownloadFailCounts(0);
		dayBalance.setDownloadStatus(DownloadStatus.READY);
		dayBalance.setDownloadUrl(downloadUrl);
		dayBalance.setFileName(balanceFileName.getFullFileName());
		dayBalance.setFileSavePath(fileRemotePath);
		dayBalance.setCreatedDate(DateUtils.getCurrentDate());
		dayBalance.setUpdatedDate(DateUtils.getCurrentDate());

		return dayBalance;
	}

	/**
	 * 根据商户号获得对应的支付渠道枚举
	 * 
	 * @param merId
	 * @return
	 */
	private int getPayChannelByMerId(String merId) {

		if (chinapayB2cFacade.getMechantNo().equals(merId)) {
			return PGPayChannel.B2C;
		}

		if (chinapayUpopFacade.getMechantNo().equals(merId)) {
			return PGPayChannel.UPOP;
		}

		return PGPayChannel.UPMP;
	}

	public static void main(String[] args) {
		String fileName = "808080290000001_20100201_20100202035959.txt";

		String regex = "^\\d{15}_\\d{8}_\\d{14}\\.txt$";

		System.out.println(StringUtils.avoidNull(fileName).matches(regex));
	}
}
