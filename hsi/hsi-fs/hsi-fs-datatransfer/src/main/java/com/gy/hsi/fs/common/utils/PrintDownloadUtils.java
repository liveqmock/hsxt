package com.gy.hsi.fs.common.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.gy.hsi.fs.common.bean.ResultCounter;
import com.gy.hsi.fs.common.bean.SystemConfig;
import com.gy.hsi.fs.common.constant.TotalContant;
import com.gy.hsi.fs.common.spring.SpringContextLoader;
import com.gy.hsi.fs.factory.BeansFactory;

public class PrintDownloadUtils {

	private static final Logger logger = Logger.getLogger(PrintDownloadUtils.class);
	
	private static BeansFactory beanFactory = SpringContextLoader
			.getBeansFactory();

	public static void printDownloadFinishLog(int totalRows,
			List<ResultCounter> counterList, List<String> statisticResultList,
			ResultCounter ftpDwnldRsltCounter) {
		
		if ((null == counterList) || (0 >= counterList.size())) {
			logger.info("处理完毕, 没有需要下载的文件。");

			return;
		}

		List<String> statisticList = new ArrayList<String>();

		int successRows = 0;
		int failedRows = 0;
		int nullRows = 0;
		int duplicatedRows = 0;

		int total = 0;
		int totalSuccessRows = 0;
		int totalFailedRows = 0;
		int totalNullRows = 0;
		int totalDuplicatedRows = 0;
		
		int totalDirtyRows = (null != ftpDwnldRsltCounter) ? getFtpCounts(ftpDwnldRsltCounter)
				: 0;

		for (ResultCounter r : counterList) {
			if (null == r) {
				continue;
			}

			successRows = r.getSuccessRows();
			failedRows = r.getFailedRows();
			duplicatedRows = r.getDuplicatedRows();
			nullRows = totalRows - successRows - failedRows - duplicatedRows;

			if (0 >= nullRows) {
				nullRows = 0;
			}

			total += totalRows;

			totalSuccessRows += successRows;
			totalFailedRows += failedRows;
			totalNullRows += nullRows;
			totalDuplicatedRows += duplicatedRows;

			// String msg = "total=" + totalRows
			// + ", success=" + successRows + ", failed=" + failedRows
			// + ", nullValue=" + nullRows;

			// logger.info("下载完毕, " + msg);
			//
			// statisticList.add("- " + msg);
		}

		// statisticList.add(0, "");
		// statisticList.add(1, "(total=" + total + ", success="
		// + totalSuccessRows + ", failed=" + totalFailedRows
		// + ", nullValue=" + totalNullRows + ")");

		statisticResultList.addAll(statisticList);

		TotalContant.total += total;
		TotalContant.totalSuccessRows += totalSuccessRows;
		TotalContant.totalFailedRows += totalFailedRows;
		TotalContant.totalNullRows += totalNullRows;
		TotalContant.totalDuplicatedRows += totalDuplicatedRows;
		TotalContant.totalDirtyRows = totalDirtyRows;
		
		TotalContant.totalSuccessRows -= totalDirtyRows;
	}
	
	/**
	 * 将文件id保存到FS数据库中
	 * 
	 * @param tfsFileId
	 * @param counterList
	 * @return
	 */
	public static void downloadTFsFileId2Local(String tfsFileId,
			List<ResultCounter> counterList, int retryTimes) {

		int $$$$FIELDS_NAME_INDEX = 0;

		try {
			SystemConfig systemConfig = beanFactory.getSystemConfig();

			String remoteUrl = StringUtils.joinFilePath(
					systemConfig.getTfsAddress(), tfsFileId);

			String localFilePath = StringUtils.joinFilePath(
					systemConfig.getDownloadLocalpath(),
					FileIdHelper.formatFileId(tfsFileId));

			boolean success = FileDownloadUtils.download(remoteUrl,
					localFilePath);

			if (success) {
				counterList.get($$$$FIELDS_NAME_INDEX).addSuccessRows();
				logger.info("download success: src tfsFileId=" + tfsFileId);
			} else {
				counterList.get($$$$FIELDS_NAME_INDEX).addFailedRows();

				logger.error("[download failed] 下载TFS文件失败1：src tfsFileId=" + tfsFileId);
			}
		} catch (IOException ex) {
			if (2 >= retryTimes) {
				// ThreadUtils.sleepAmoment(2000);

				downloadTFsFileId2Local(tfsFileId, counterList, ++retryTimes);
			} else {
				counterList.get($$$$FIELDS_NAME_INDEX).addFailedRows();

				logger.error("[download failed] 下载TFS文件失败2：src tfsFileId=" + tfsFileId, ex);
			}
		} catch (Exception ex) {
			counterList.get($$$$FIELDS_NAME_INDEX).addFailedRows();

			logger.error("[download failed] 下载TFS文件失败3：src tfsFileId=" + tfsFileId, ex);
		}
	}
	
	private static int getFtpCounts(ResultCounter ftpDwnldRsltCounter) {
		
		int countInDB = beanFactory.getFsDataTransferService()
				.countFTPFiles();

		int realDownloadCount = ftpDwnldRsltCounter.getSuccessRows();
		
		int dirtyCount = (realDownloadCount >= countInDB) ? realDownloadCount
				- countInDB : 0;
		
		return dirtyCount;
	}
}
