/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.worker;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.gy.hsi.fs.common.bean.ResultCounter;
import com.gy.hsi.fs.common.spring.SpringContextLoader;
import com.gy.hsi.fs.common.utils.PrintFileIdUtils;
import com.gy.hsi.fs.common.utils.StringUtils;
import com.gy.hsi.fs.common.utils.ThreadUtils;
import com.gy.hsi.fs.factory.BeansFactory;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsEntChangeInfoData;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsPerChangeInfoData;
import com.gy.hsi.fs.service.IBSDataTransferService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-datatransfer
 * 
 *  Package Name    : com.gy.hsi.fs.worker.bs
 * 
 *  File Name       : BsWorker3.java
 * 
 *  Creation Date   : 2015年11月25日
 * 
 *  Author          : Administrator
 * 
 *  Purpose         : none
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class BsFileId3Worker {
	/** 记录日志对象 **/
	private static final Logger logger = Logger.getLogger(BsFileId3Worker.class);

	private static BeansFactory beanFactory = SpringContextLoader
			.getBeansFactory();

	private static int PAGE_SIZE = 500;
	private static int MAX_FAILED_TIMES = 3;
	
	private static final List<String> statisticResultList = new ArrayList<String>();
	
	private static List<String> pngFtpFileList = new ArrayList<String>();
	
	static {
		pngFtpFileList.add("0aa8c907-86fd-4582-a163-0573efa6cde9");
		pngFtpFileList.add("0d728792-b23f-4f7e-b27a-bb00cbb1f680");
		pngFtpFileList.add("3a7e49fa-08fc-4cdf-9d19-c5709793581b");
		pngFtpFileList.add("96693bca-81ff-4bf8-a5b3-ebb074223c51");
		pngFtpFileList.add("53f2686b-5828-4898-ac7c-46498991584f");
		pngFtpFileList.add("981080f2-79eb-4cbb-bb52-b06d63340f0c");
		pngFtpFileList.add("7bc8f074-e11b-4ebb-bc39-631946aed00d");
		pngFtpFileList.add("c47788d1-6f88-4b17-b274-a2ee838381bb");
		pngFtpFileList.add("59100c3f-7173-4d67-a523-dfcd9a817f49");
		pngFtpFileList.add("84d94b58-5c65-4036-9bd9-39836532149c");
		pngFtpFileList.add("2c3d0798-b0c0-482b-ab40-d8710b23d2a1");
		pngFtpFileList.add("f8c30491-860f-4b83-81df-5a94deb2d15f");
		pngFtpFileList.add("b5848cb2-1870-4d7e-89d5-628aa07f85e2");
		pngFtpFileList.add("4ff9b8fa-4675-4642-bb50-87358eb6d56c");
	}

	public static List<String> doIt() {
		try {
			handleTBsEntChangeInfoData("T_BS_ENT_CHANGE_INFO_DATA");
		} catch (Exception e) {
			logger.error("处理T_BS_ENT_CHANGE_INFO_DATA时出错：", e);
		}

		try {
			handleTBsPerChangeInfoData("T_BS_PER_CHANGE_INFO_DATA");
		} catch (Exception e) {
			logger.error("处理T_BS_ENT_CHANGE_INFO_DATA时出错：", e);
		}

		return statisticResultList;
	}

	private static void handleTBsEntChangeInfoData(String tableName) {
		System.out.println("----->>开始修理表" + tableName + "......");
		logger.info("----->>开始修理表" + tableName + "......");

		int start = 1;
		int totalRows = 0;

		int queryFailedTimes = 0;

		List<TBsEntChangeInfoData> list = null;

		IBSDataTransferService bsService = beanFactory
				.getBsDataTransferService();

		List<ResultCounter> counterList = new ArrayList<ResultCounter>();
		counterList.add(new ResultCounter("BEFORE_VALUE"));
		counterList.add(new ResultCounter("AFTER_VALUE"));

		while (true) {
			try {
				list = bsService.queryTBsEntChangeInfoDataList(start, (start
						+ PAGE_SIZE - 1));
			} catch (Exception e) {
				e.printStackTrace();

				logger.error("查询" + tableName + "表时出错：start=" + start
						+ ",pageSize=" + PAGE_SIZE, e);

				queryFailedTimes++;

				if (MAX_FAILED_TIMES > queryFailedTimes) {
					ThreadUtils.sleepAmoment(1000);
					continue;
				}
			}

			if ((null == list) || (0 >= list.size())) {
				PrintFileIdUtils.printFinishLog(tableName, totalRows, counterList,
						statisticResultList);

				break;
			} else {
				System.out.println("正在努力地修理表" + tableName + ", from " + start
						+ " to " + (start + PAGE_SIZE - 1) + " ..., 累出汗了都!!!");

				totalRows += list.size();
			}

			for (TBsEntChangeInfoData record : list) {
				// -------------------------------0:BEFORE_VALUE-----------------------------
				int $$$$FIELDS_NAME_INDEX = 0;
				String tfsFileId = formatFtpFileName(record.getBeforeValue());
				record.setBeforeValue(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				// -------------------------------1:AFTER_VALUE-----------------------------
				$$$$FIELDS_NAME_INDEX++;
				tfsFileId = formatFtpFileName(record.getAfterValue());
				record.setAfterValue(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				bsService.updateTBsEntChangeInfoData(record);
			}

			// 注意一定要放到末尾
			start += PAGE_SIZE;
		}
	}

	private static void handleTBsPerChangeInfoData(String tableName) {
		System.out.println("----->>开始修理表" + tableName + "......");
		logger.info("----->>开始修理表" + tableName + "......");

		int start = 1;
		int totalRows = 0;

		int queryFailedTimes = 0;

		List<TBsPerChangeInfoData> list = null;

		IBSDataTransferService bsService = beanFactory
				.getBsDataTransferService();

		List<ResultCounter> counterList = new ArrayList<ResultCounter>();
		counterList.add(new ResultCounter("BEFORE_VALUE"));
		counterList.add(new ResultCounter("AFTER_VALUE"));

		while (true) {
			try {
				list = bsService.queryTBsPerChangeInfoDataList(start, (start
						+ PAGE_SIZE - 1));
			} catch (Exception e) {
				e.printStackTrace();

				logger.error("查询" + tableName + "表时出错：start=" + start
						+ ",pageSize=" + PAGE_SIZE, e);

				queryFailedTimes++;

				if (MAX_FAILED_TIMES > queryFailedTimes) {
					ThreadUtils.sleepAmoment(1000);
					continue;
				}
			}

			if ((null == list) || (0 >= list.size())) {
				PrintFileIdUtils.printFinishLog(tableName, totalRows, counterList,
						statisticResultList);

				break;
			} else {
				System.out.println("正在努力地修理表" + tableName + ", from " + start
						+ " to " + (start + PAGE_SIZE - 1) + " ..., 累出汗了都!!!");

				totalRows += list.size();
			}

			for (TBsPerChangeInfoData record : list) {
				// -------------------------------0:BEFORE_VALUE-----------------------------
				int $$$$FIELDS_NAME_INDEX = 0;
				String tfsFileId = formatFtpFileName(record.getBeforeValue());
				record.setBeforeValue(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				// -------------------------------1:AFTER_VALUE-----------------------------
				$$$$FIELDS_NAME_INDEX++;
				tfsFileId = formatFtpFileName(record.getAfterValue());
				record.setAfterValue(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				bsService.updateTBsPerChangeInfoData(record);
			}

			// 注意一定要放到末尾
			start += PAGE_SIZE;
		}
	}
	
	private static String formatFtpFileName(String fileId) {

		fileId = StringUtils.avoidNull(fileId);

		if (fileId.startsWith("T") || fileId.startsWith("L")) {
			return fileId;
		}

		if ((0 > fileId.lastIndexOf(".")) && (fileId.contains("-"))) {
			if(pngFtpFileList.contains(fileId)) {
				fileId = fileId + ".png";
			} else {
				fileId = fileId + ".jpg";
			}			
		}

		return fileId;
	}
}
