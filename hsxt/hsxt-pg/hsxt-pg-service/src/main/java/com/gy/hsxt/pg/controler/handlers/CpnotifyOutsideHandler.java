package com.gy.hsxt.pg.controler.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;
import com.gy.hsxt.pg.common.constant.Constant;
import com.gy.hsxt.pg.common.constant.Constant.DownloadStatus;
import com.gy.hsxt.pg.common.constant.Constant.Reply2Chinapay;
import com.gy.hsxt.pg.common.utils.StringUtils;
import com.gy.hsxt.pg.constant.PGConstant.PGErrorCode;
import com.gy.hsxt.pg.mapper.vo.TPgChinapayDayBalance;
import com.gy.hsxt.pg.service.IChinapayDayBalanceFileService;
import com.gy.hsxt.pg.service.ITriggerDSBatchService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.controler
 * 
 *  File Name       : CpnotifyOutsideHandler.java
 * 
 *  Creation Date   : 2015-09-22
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 处理来自外部第三方接入渠道的支付类请求
 *                    
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Component("cpnotifyOutsideHandler")
public class CpnotifyOutsideHandler {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private IChinapayDayBalanceFileService dayBalanceService;

	@Autowired
	private ITriggerDSBatchService triggerDSBatchService;

	@Resource(name = "dayBalanceFileDownloadExecutor")
	private ThreadPoolTaskExecutor fileDownloadExecutor;

	private static final List<String> DOWNLOADING_LIST = Collections
			.synchronizedList(new ArrayList<String>(5));

	/**
	 * 处理每日对账请求
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void handleCpDayBanlanceRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("银联通知每日对账请求地址:" + getRequestUrl(request));

		try {
			// 对请求合法性进行校验
			this.checkRequest(request);

			// 下载地址
			String downloadUrl = request
					.getParameter(Constant.PARAM_CP_NOTIFY_DOWLOAD);

			// 将银联通知的对账文件基本信息保存到数据库中
			TPgChinapayDayBalance dayBalance = dayBalanceService
					.insertDayBalanceFileInfo2DB(downloadUrl);

			// 在另一个线程中开始下载银联的对账文件
			this.downloadBalanceFileInThread(dayBalance);

			// 通知互商的支付网关下载对账文件, 互商不会执行此动作, (屏蔽代码:zhangysh,不再使用此逻辑)
			// this.notifyPgTwinsBalanceFileDownload(downloadUrl);

			// 向银联响应ChinapayOK
			response.getWriter().write(Reply2Chinapay.OK);
		} catch (Exception e) {
			response.getWriter().write(Reply2Chinapay.ERR);

			logger.info("接收到中国银联每日对账文件通知请求后, 处理失败!!", e);
		}
	}

	/**
	 * 在另一个线程中开始下载银联的对账文件
	 * 
	 * @param dayBalance
	 */
	private void downloadBalanceFileInThread(
			final TPgChinapayDayBalance dayBalance) {

		// 对账文件名称
		final String balanceFileName = dayBalance.getFileName();

		// 如果是正在下载的, 则忽略该请求
		if (DOWNLOADING_LIST.contains(balanceFileName)) {
			return;
		}

		logger.info("下载中国银联每日对账文件：" + dayBalance.getFileName());

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					downloadAndVerifyBalanceFile(dayBalance);
				} catch (Exception e) {
					logger.info("", e);
				} finally {
					DOWNLOADING_LIST.remove(balanceFileName);
				}
			}
		};

		// 在另一个线程中开始下载银联的对账文件
		try {
			fileDownloadExecutor.execute(runnable);
		} catch (TaskRejectedException e) {
			DOWNLOADING_LIST.remove(balanceFileName);

			logger.info("", e);
		}
	}

	/**
	 * 校验请求合法性
	 * 
	 * @param request
	 */
	private void checkRequest(HttpServletRequest request) {

		Date todayDate = DateUtils.getCurrentDate();

		String yesterday = DateUtils.getBeforeDate(todayDate, "yyyyMMdd");
		String today = DateUtils.dateToString(todayDate, "yyyyMMdd");

		// 808080290000001_20100201_20100202035959.txt
		String regex = StringUtils.joinString("^\\d{15}_", yesterday, "_",
				today, "\\d{6,}\\.txt$");

		String downloadUrl = StringUtils.avoidNullTrim(request
				.getParameter(Constant.PARAM_CP_NOTIFY_DOWLOAD));

		String fileName = downloadUrl.replaceAll("^.*\\/", "");

		// =================== 校验文件名称 ======================
		if (!fileName.matches(regex)) {
			throw new HsException(PGErrorCode.INVALID_PARAM,
					"传递的每日对账文件名称不合法！！ downloadUrl=" + downloadUrl);
		}
	}

	/**
	 * 下载并验签对账文件
	 * 
	 * @param dayBalance
	 * @throws Exception
	 */
	private void downloadAndVerifyBalanceFile(TPgChinapayDayBalance dayBalance)
			throws Exception {

		dayBalanceService.updateDayBalanceFileInfo(dayBalance.getFileName(),
				DownloadStatus.READY);

		// 下载对账文件
		boolean downloadSuccess = dayBalanceService.dowloadDayBalanceFile(
				dayBalance, true);

		// 验证签名
		boolean verifySuccess = dayBalanceService
				.verifyDayBalanceFile(dayBalance);

		if (downloadSuccess && verifySuccess) {
			triggerDSBatchService.actionTrigger(dayBalance
					.getBalanceDateStrValue());
		}

		// 打印log日志
		if (downloadSuccess) {
			if (verifySuccess) {
				logger.info("中国银联每日对账文件下载成功并验签成功： fileName="
						+ dayBalance.getFileName());
			} else {
				logger.error("中国银联每日对账文件下载成功但验签失败!! --> fileName="
						+ dayBalance.getFileName());
			}
		} else {
			logger.error("中国银联每日对账文件下载失败!! --> fileName="
					+ dayBalance.getFileName());
		}
	}

	/**
	 * 获取请求url及参数
	 * 
	 * @param request
	 * @return
	 */
	private String getRequestUrl(HttpServletRequest request) {

		String queryString = StringUtils
				.avoidNullTrim(request.getQueryString());

		if (!StringUtils.isEmpty(queryString)) {
			queryString = "?".concat(queryString);
		}

		return request.getRequestURI() + queryString;
	}
}
