package com.gy.hsxt.pg.controler;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;
import com.gy.hsxt.pg.bankadapter.common.utils.HttpClientHelper;
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
 *  File Name       : OutsideCommonController.java
 * 
 *  Creation Date   : 2015-09-22
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 处理来自外部第三方接入渠道的支付类请求
 * 
 *                    银联对账文件通知地址
 *                    交易对账地址：http://unionpay.hsxt.net:18084/hsxt-pg/cpnotify/trading?download=下载地址
 *                    清算对账地址：http://unionpay.hsxt.net:18084/hsxt-pg/cpnotify/clearing?download=下载地址
 * 
 *                    这个访问地址规则已经向中国银联发起过申请审核, 不得随意改动!!! 否则后果自负!!!!
 *                    
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Controller
@RequestMapping("/cpnotify")
public class OutsideCpnotifyController {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private IChinapayDayBalanceFileService dayBalanceService;

	@Autowired
	private ITriggerDSBatchService triggerDSBatchService;

	@Resource(name = "dayBalanceFileDownloadExecutor")
	private ThreadPoolTaskExecutor fileDownloadExecutor;

	private static final List<String> DOWNLOADING_LIST = Collections
			.synchronizedList(new ArrayList<String>(5));

	@RequestMapping("/trading")
	public void handleOutsideRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info(getRequestUrl(request));

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

			logger.info("接收到中国银联对账文件通知请求后, 处理失败!!", e);
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

		logger.info("下载中国银联对账文件：" + dayBalance.getFileName());

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

		// TODO 判断请求是否来自于内部机房或者银联服务器, 以后优化...

		Date todayDate = DateUtils.getCurrentDate();

		String yesterday = DateUtils.getBeforeDate(todayDate, "yyyyMMdd");
		String today = DateUtils.dateToString(todayDate, "yyyyMMdd");

		// 808080290000001_20100201_20100202035959.txt
		String regex = StringUtils.joinString("^\\d{15}_", yesterday, "_",
				today, "\\d{6}\\.txt$");

		String downloadUrl = StringUtils.avoidNull(request
				.getParameter(Constant.PARAM_CP_NOTIFY_DOWLOAD));

		// =================== 校验下载地址 ======================
		if (downloadUrl.matches("^http(s{0,1})://(\\S{1,})")) {
			throw new HsException(PGErrorCode.INVALID_PARAM, "传递的对账文件下载地址不合法！！");
		}

		String fileName = downloadUrl.replaceAll("^.*\\/", "");

		// =================== 校验文件名称 ======================
		if (!fileName.matches(regex)) {
			throw new HsException(PGErrorCode.INVALID_PARAM, "传递的对账文件名称不合法！！");
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
				logger.info("中国银联对账文件下载成功并验签成功： fileName="
						+ dayBalance.getFileName());
			} else {
				logger.error("中国银联对账文件下载成功但验签失败!! --> fileName="
						+ dayBalance.getFileName());
			}
		} else {
			logger.error("中国银联对账文件下载失败!! --> fileName="
					+ dayBalance.getFileName());
		}
	}

	/**
	 * 在另一个线程中, 通过http通知互商PG支付网关下载银联的对账文件
	 * 
	 * @param downloadUrl
	 */
	@SuppressWarnings("unused")
	private void notifyPgTwinsBalanceFileDownload(final String downloadUrl) {

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					doNotifyPgTwinsBalanceFileDownload(downloadUrl);
				} catch (Exception e) {
					logger.error("通过http通知互商PG支付网关下载银联的对账文件失败！！downloadUrl="
							+ downloadUrl);
				}
			}
		};

		// 在另一个线程中开始下载银联的对账文件
		try {
			fileDownloadExecutor.execute(runnable);
		} catch (TaskRejectedException e) {
			logger.info("", e);
		}
	}

	/**
	 * 通过http通知互商PG支付网关下载银联的对账文件
	 * 
	 * @param downloadUrl
	 */
	private void doNotifyPgTwinsBalanceFileDownload(String downloadUrl) {

		String queryString = Constant.PARAM_CP_NOTIFY_DOWLOAD + "="
				+ downloadUrl;

		String[] urls = parseBalancePgTwinsUrl();

		// 不配置则一定返回, 互商千万不要配置, 否则会有死循环!!!!!!!!
		if ((null == urls) || (0 >= urls.length)) {
			return;
		}

		StringBuilder tempBuf = new StringBuilder();

		for (String url : urls) {
			String respStr = HttpClientHelper.doGet(url, queryString);

			if (Reply2Chinapay.OK.equals(respStr)) {
				logger.info("成功通知对端PG支付网关下载对账文件。");
			}

			tempBuf.append("[").append(url).append("], ");
		}

		logger.info("通过http通知互商PG支付网关下载银联的对账文件: downloadUrl=" + downloadUrl
				+ ", 被通知方url=" + tempBuf.toString());
	}

	/**
	 * 解析互商PG支付网关url地址,
	 * 
	 * <互生>PG支付网关通知<互商>PG支付网关的对账文件下载url, 互商不用配置, 只需互生配置(最多支持3个)
	 * 
	 * @return
	 */
	private String[] parseBalancePgTwinsUrl() {

		List<String> pgTwinsUrls = new ArrayList<String>(3);

		// 通知(最多支持3个)
		// for (int i = 1; i <= 3; i++) {
		// String value = PropertyConfigurer
		// .getProperty(TcConfigConsts.KEY_SYS_TC_BALANCE_PG_TWINS_URL
		// + i);
		//
		// if (!StringUtils.isEmpty(value)) {
		// pgTwinsUrls.add(value);
		// }
		// }

		// # <互生>PG支付网关通知<互商>PG支付网关的对账文件下载url, 互商不用配置, 只需互生配置(最多支持3个)
		// system.tc.balance.pg.twins.url_1=
		// system.tc.balance.pg.twins.url_2=
		// system.tc.balance.pg.twins.url_3=

		return pgTwinsUrls.toArray(new String[] {});
	}

	/**
	 * 获取请求url及参数
	 * 
	 * @param request
	 * @return
	 */
	private String getRequestUrl(HttpServletRequest request) {
		return request.getRequestURI() + request.getQueryString();
	}
}
