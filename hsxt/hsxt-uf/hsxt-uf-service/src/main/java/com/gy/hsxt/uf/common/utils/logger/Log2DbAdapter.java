/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.common.utils.logger;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.core.task.TaskExecutor;

import com.gy.hsxt.uf.bean.packet.PacketHandleResult;
import com.gy.hsxt.uf.common.bean.packet.SecureRegionPacket;
import com.gy.hsxt.uf.common.bean.packet.SecureRegionPacketHeader;
import com.gy.hsxt.uf.common.constant.ConfigConst;
import com.gy.hsxt.uf.common.constant.Constant.OptType;
import com.gy.hsxt.uf.common.constant.Constant.OptStatus;
import com.gy.hsxt.uf.common.constant.Constant.UfLogFlag;
import com.gy.hsxt.uf.common.spring.SpringContextLoader;
import com.gy.hsxt.uf.common.spring.UfPropertyConfigurer;
import com.gy.hsxt.uf.common.utils.BytesUtils;
import com.gy.hsxt.uf.common.utils.StringUtils;
import com.gy.hsxt.uf.common.utils.UfUtils;
import com.gy.hsxt.uf.factory.BeansFactory;
import com.gy.hsxt.uf.mapper.vo.TUfPacketLog;
import com.gy.hsxt.uf.mapper.vo.TUfPacketLogDetail;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.common.utils
 * 
 *  File Name       : LogAdapter.java
 * 
 *  Creation Date   : 2015-10-30
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 日志数据适配
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public final class Log2DbAdapter {
	/** 记录日志对象 **/
	private static final Logger logger = Logger.getLogger(Log2DbAdapter.class);

	/** bean工厂 **/
	private static BeansFactory beansFactory = SpringContextLoader
			.getBeansFactory();

	/** spring线程池 **/
	private static TaskExecutor taskExecutor = beansFactory.getLogTaskExecutor();

	private Log2DbAdapter() {
	}

	/**
	 * 异步记录发送日志
	 * 
	 * @param regionPacket
	 * @param handleResult
	 * @param elapsedMillsTime
	 */
	public static void recordSendingLog(final SecureRegionPacket regionPacket,
			PacketHandleResult handleResult, final long elapsedMillsTime) {
		if (!canLog()) {
			return;
		}

		final PacketHandleResult _handleResult = (PacketHandleResult) handleResult
				.clone();

		taskExecutor.execute(new Runnable() {
			public void run() {
				recordLogInfo(regionPacket, _handleResult,
						OptType.SENDING, elapsedMillsTime);
			}
		});
	}

	/**
	 * 异步记录接收日志
	 * 
	 * @param regionPacket
	 * @param handleResult
	 * @param elapsedMillsTime
	 */
	public static void recordReceivingLog(
			final SecureRegionPacket regionPacket,
			PacketHandleResult handleResult, final long elapsedMillsTime) {
		if (!canLog()) {
			return;
		}

		final PacketHandleResult _handleResult = (PacketHandleResult) handleResult
				.clone();

		taskExecutor.execute(new Runnable() {
			public void run() {
				recordLogInfo(regionPacket, _handleResult,
						OptType.RECEIVING, elapsedMillsTime);
			}
		});
	}

	/**
	 * 记录日志
	 * 
	 * @param regionPacket
	 * @param handleResult
	 * @param operationType
	 * @param elapsedMillsTime
	 */
	private static void recordLogInfo(SecureRegionPacket regionPacket,
			PacketHandleResult handleResult, int operationType,
			long elapsedMillsTime) {
		if ((null == regionPacket) || (null == handleResult)) {
			return;
		}

		// 跨地区平台报文头对象
		SecureRegionPacketHeader header = regionPacket.getHeader();

		// 请求字节
		byte[] reqBytes = BytesUtils.object2ByteByzip(regionPacket);

		// 请求报文大小
		long reqPacketSize = (null == reqBytes) ? 0 : reqBytes.length;

		// 响应报文大小(这里只粗略计算对象的字节大小, 误差可以忽略不计)
		long respPacketSize = getRespPacketBytesSize(regionPacket, handleResult);

		// 总响应时间
		float totalRespTime = elapsedMillsTime / (float) 1000;

		// 拼装日志对象
		TUfPacketLog log = new TUfPacketLog();
		log.setPacketId(header.getPacketId());
		log.setSrcPlatformId(header.getSrcPlatformId());
		log.setSrcSubsysId(header.getSrcSubsysId());
		log.setDestPlatformId(header.getDestPlatformId());
		log.setDestSubsysId(header.getDestSubsysId());
		log.setDestBizCode(header.getDestBizCode());
		log.setPacketOptType(operationType);
		log.setReqPacketSize(reqPacketSize);
		log.setRespPacketSize(respPacketSize);
		log.setTotalRespTime(totalRespTime);
		log.setCreateDate(new Date());

		// 日志堆栈详情对象
		TUfPacketLogDetail logDetail = null;

		// 处理成功
		if (handleResult.isSuccess()) {
			log.setPacketOptStatus(OptStatus.NORMAL);
		} else {
			String errorDesc = "";
			String traceInfo = "";

			if (null != handleResult) {
				errorDesc = handleResult.getResultDesc();
				traceInfo = UfUtils.getStackTraceInfo(handleResult.getCause());
			}

			String logStackTraceId = UfUtils.generateUUID(log.hashCode());

			log.setPacketOptStatus(OptStatus.ABNORMAL);
			log.setLogErrDesc(StringUtils.cut2SpecialLength(errorDesc, 100));
			log.setLogStackTraceId(logStackTraceId);

			// 日志堆栈详情
			logDetail = new TUfPacketLogDetail();
			logDetail.setLogStackTraceId(logStackTraceId);
			logDetail.setLogStackTraceDetail(traceInfo);
			logDetail.setCreateDate(new Date());
		}

		try {
			beansFactory.getPacketLogService().insertLogData(log, logDetail);
		} catch (Exception e) {
			logger.error("向MYSQL数据库插入日志时报错：", e);
		}
	}

	/**
	 * 取得响应报文的字节大小
	 * 
	 * @param regionPacket
	 * @param handleResult
	 * @return
	 */
	private static long getRespPacketBytesSize(SecureRegionPacket regionPacket,
			PacketHandleResult handleResult) {
		long size = 0;

		// 报文头字节
		byte[] headerBytes = BytesUtils.object2ByteByzip(regionPacket
				.getHeader());

		if (null != headerBytes) {
			size = headerBytes.length;
		}

		// 响应字节(这里只粗略计算对象的字节大小, 误差可以忽略不计)
		byte[] resultBytes = BytesUtils.object2ByteByzip(handleResult);

		if (null != resultBytes) {
			size += resultBytes.length;
		}

		return size;
	}

	/**
	 * 可以记录日志
	 * 
	 * @return
	 */
	private static boolean canLog() {
		String logFlag = UfPropertyConfigurer
				.getProperty(ConfigConst.SYSTEM_PLATFORM_LOGFLAG);

		return !UfLogFlag.FALSE.equals(logFlag);
	}
}
