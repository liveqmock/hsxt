/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.Log4jConfigurer;

import com.gy.hsi.fs.common.constant.StartClassPath;
import com.gy.hsi.fs.common.constant.TotalContant;
import com.gy.hsi.fs.common.spring.SpringContextLoader;
import com.gy.hsi.fs.common.utils.StringUtils;
import com.gy.hsi.fs.worker.BsFileIdWorker;
import com.gy.hsi.fs.worker.UcFileIdWorker;
import com.gy.hsi.fs.worker.WsFileIdWorker;
import com.gy.hsi.fs.worker.fileupload.DownloadWorker;
import com.gy.hsi.fs.worker.fileupload.UploadWorker;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf
 * 
 *  File Name       : ServerStarter.java
 * 
 *  Creation Date   : 2015-9-26
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : Netty Server启动入口(服务器启动唯一入口)
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public final class Starter {
	
	private static final Logger logger = Logger.getLogger(BsFileIdWorker.class);
	
	public static void main(String[] args) {
		// 初始化classpath
		initClasspath(args);

		try {
			Log4jConfigurer.initLogging("file:${user.dir}/conf/fs/log4j.xml");
		} catch (FileNotFoundException e) {
		}

		SpringContextLoader.getAppContext().start();
		
		long beginTime = System.currentTimeMillis();

		List<String> statisticResultList = new ArrayList<String>();
		
		String action = System.getProperty(StartClassPath.ACTION);

		// 数据转换
		if ("DATA_TRANS".equals(action)) {
			String subsysNo = System.getProperty(StartClassPath.SUBSYS_NO);

			if ("BS".equalsIgnoreCase(subsysNo)) {
				statisticResultList = BsFileIdWorker.doIt();
			} else if ("UC".equalsIgnoreCase(subsysNo)) {
				statisticResultList = UcFileIdWorker.doIt();
			} else if ("WS".equalsIgnoreCase(subsysNo)) {
				statisticResultList = WsFileIdWorker.doIt();
			} else {
				statisticResultList.addAll(BsFileIdWorker.doIt());
				statisticResultList.addAll(UcFileIdWorker.doIt());
				statisticResultList.addAll(WsFileIdWorker.doIt());
			}
		}
		// 下载
		else if ("DOWNLOAD".equals(action)) {
			statisticResultList = DownloadWorker.doIt();
		}
		// 上传
		else if ("UPLOAD".equals(action)) {
			statisticResultList = UploadWorker.doIt();
		}
		
		float timeEclipsed = (float) ((float)(System.currentTimeMillis() - beginTime) / (60.0 * 1000));

		statisticResultList.add("");
		statisticResultList.add("【总耗时】：" + timeEclipsed + "分钟");

		printStatisticInfo(statisticResultList, action);
	}
	
	/**
	 * 打印统计信息
	 * 
	 * @param statisticResultList
	 */
	private static void printStatisticInfo(List<String> statisticResultList, String action) {

		StringBuilder buffer = new StringBuilder();

		buffer.append(System.lineSeparator());
		buffer.append(System.lineSeparator());
		buffer.append("--------------------------"+action+" 处理完毕----------------------");
		buffer.append(System.lineSeparator());

		for (String msg : statisticResultList) {
			buffer.append(System.lineSeparator() + " " + msg);
		}

		buffer.append(System.lineSeparator());
		buffer.append(System.lineSeparator() + " 【最终统计】: total="
				+ TotalContant.total + ", success="
				+ TotalContant.totalSuccessRows + ", failed="
				+ TotalContant.totalFailedRows + ", nullValue="
				+ TotalContant.totalNullRows + ", duplicated="
				+ TotalContant.totalDuplicatedRows);
		
		if(0 < TotalContant.totalDirtyRows) {
			buffer.append(", dirty=" + TotalContant.totalDirtyRows);
		}

		buffer.append(System.lineSeparator());
		buffer.append(System.lineSeparator());
		buffer.append("--------------------------"+action+" 处理完毕----------------------");

		logger.info(buffer.toString());
	}

	/**
	 * 初始classpath
	 * 
	 * @param args
	 */
	private static void initClasspath(String[] args) {
		if (StringUtils.isAllEmpty(args)) {
			return;
		}

		for (String arg : args) {
			int index = arg.lastIndexOf("=");

			String key = arg.substring(0, index);
			String value = arg.substring(index + 1);

			if (StringUtils.isExistEmpty(key, value)) {
				continue;
			}

			try {
				// 如果是user.dir或log.home, 需做特殊处理
				if (StartClassPath.USER_DIR.equals(key)
						|| StartClassPath.LOG_HOME.equals(key)) {
					if (!value.endsWith(File.separator)) {
						value += File.separator;
					}

					value = new File(value).getCanonicalPath()
							.replaceFirst(File.separator + "*$", "").trim();

					if (StartClassPath.USER_DIR.equals(key)) {
						key = "-D" + key.trim();
					}
				}

				// 缓存key-value到系统属性
				System.setProperty(key, value);
			} catch (Exception e) {
			}
		}
	}
	
//	/**
//	 * 打印统计信息
//	 * 
//	 * @param statisticResultList
//	 */
//	private static void printStatisticInfo(List<String> statisticResultList) {
//		
//		logger.info("");
//		logger.info("--------------------------------处理完毕---------------------------");		
//		logger.info("-");
//		
//		for(String msg : statisticResultList) {
//			logger.info("-   "+msg);
//		}
//		
//		logger.info("-");
//		logger.info("-->  最终统计(total=" + TotalContant.total
//				+ ", success=" + TotalContant.totalSuccessRows + ", failed="
//				+ TotalContant.totalFailedRows + ", nullValue="
//				+ TotalContant.totalNullRows);
//		
//		logger.info("-");
//		logger.info("-------------------------------------------------------------------");
//	}
}