/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.worker.fileupload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.springframework.core.task.TaskRejectedException;

import com.gy.hsi.fs.common.bean.ResultCounter;
import com.gy.hsi.fs.common.bean.SystemConfig;
import com.gy.hsi.fs.common.constant.TotalContant;
import com.gy.hsi.fs.common.spring.SpringContextLoader;
import com.gy.hsi.fs.common.utils.FileIdHelper;
import com.gy.hsi.fs.common.utils.FtpUtils;
import com.gy.hsi.fs.common.utils.PrintDownloadUtils;
import com.gy.hsi.fs.common.utils.StringUtils;
import com.gy.hsi.fs.factory.BeansFactory;
import com.gy.hsi.fs.mapper.vo.TFsFileMetaData;
import com.gy.hsi.fs.service.IFSDataTransferService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-datatransfer
 * 
 *  Package Name    : com.gy.hsi.fs.worker
 * 
 *  File Name       : DownloadWorker.java
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
public class CopyOfDownloadWorker {
	private static final Logger logger = Logger.getLogger(CopyOfDownloadWorker.class);

	private static BeansFactory beanFactory = SpringContextLoader
			.getBeansFactory();

	private static int PAGE_SIZE = 250;
	private static final List<String> statisticResultList = new ArrayList<String>();	
	public static List<String> handledList = new ArrayList<String>();

	public static List<String> doIt() {
		System.out.println();
		System.out.println("======================开始下载文件到本地处理========================");

		logger.info("======================开始下载文件到本地处理========================");
		
		logger.info("======================[1] 开始下载FTP文件========================");
		
		ResultCounter ftpDwnldRsltCounter = actionDdownloadFTPFiles();
		
		logger.info("======================[2] 开始下载TFS文件========================");

		int start = 0;
		int totalRows = 0;

		List<TFsFileMetaData> list = null;

		CountDownLatch latch;

		IFSDataTransferService fsService = beanFactory
				.getFsDataTransferService();

		List<ResultCounter> counterList = new ArrayList<ResultCounter>();
		counterList.add(new ResultCounter(""));

		while (true) {
			// 查询TFS文件
			list = fsService.queryTFsFileMetaDatasForDownloadTFS(start, PAGE_SIZE);

			if ((null == list) || (0 >= list.size())) {
				// 打印结果
				PrintDownloadUtils.printDownloadFinishLog(totalRows,
						counterList, statisticResultList, ftpDwnldRsltCounter);

				break;
			} else {
				System.out.println("正在努力下载中 ....");

				totalRows += list.size();
			}
			
			List<TFsFileMetaData> listNew = new ArrayList<TFsFileMetaData>();
			
			for (TFsFileMetaData d : list) {
				if(0 >= d.getFileSizeBytes()) {
					listNew.add(d);
				}
			}
			
			latch = new CountDownLatch(listNew.size());

			for (TFsFileMetaData d : listNew) {
				actionDownloadTfsInThreadpool(d.getFileStorageId(),
						counterList, latch);
			}

			start += PAGE_SIZE;

			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// FTP文件
		{
			statisticResultList.add("");

			int countInDB = beanFactory.getFsDataTransferService()
					.countFTPFiles();

			int realDownloadCount = ftpDwnldRsltCounter.getSuccessRows();
			int dirtyCount = (realDownloadCount >= countInDB) ? realDownloadCount
					- countInDB : 0;

			if (realDownloadCount >= countInDB) {
				statisticResultList.add("FTP文件全部下载成功: 数据库中的FTP文件数量="
						+ countInDB + ", 实际成功下载的FTP文件总数="
						+ ftpDwnldRsltCounter.getSuccessRows() + ", 其中脏文件数=" + dirtyCount);
			} else if (ftpDwnldRsltCounter.getSuccessRows() < countInDB) {
				statisticResultList.add("FTP部分文件下载失败： 数据库中TFS类型文件数据量="
						+ countInDB + ", 实际下载成功文件数据量="
						+ ftpDwnldRsltCounter.getSuccessRows());
			}
		}

		// TFS文件
		{
			statisticResultList.add("");

			int countInDB = beanFactory.getFsDataTransferService()
					.countTFSFiles();

			if (counterList.get(0).getSuccessRows() == countInDB) {
				statisticResultList
						.add("TFS文件全部下载成功: 数据库中TFS类型文件数据量==实际下载成功文件数据量("
								+ countInDB + ")。");
			} else if (counterList.get(0).getSuccessRows() != countInDB) {
				statisticResultList.add("TFS部分文件下载失败： 数据库中TFS类型文件数据量="
						+ countInDB + ", 实际下载成功文件数据量="
						+ counterList.get(0).getSuccessRows());
			}
		}

		return statisticResultList;
	}
	
	/**
	 * 执行下载
	 * 
	 * @param tfsFileId
	 * @param counterList
	 * @param latch
	 */
	private static void actionDownloadTfsInThreadpool(final String tfsFileId,
			final List<ResultCounter> counterList, final CountDownLatch latch) {
		try {
			// 线程池中运行
			beanFactory.getUploadExecutor().execute(new Runnable() {
				@Override
				public void run() {
					try {
						PrintDownloadUtils.downloadTFsFileId2Local(tfsFileId,
								counterList, 0);
					} finally {
						latch.countDown();
					}
				}
			});
		} catch (TaskRejectedException ex) {
			try {
				PrintDownloadUtils.downloadTFsFileId2Local(tfsFileId, counterList, 0);
			} finally {
				latch.countDown();
			}
		}
	}
	
	/**
	 * 下载FTP文件
	 * 
	 * @return
	 */
	private static ResultCounter actionDdownloadFTPFiles() {

		SystemConfig sysConf = beanFactory.getSystemConfig();

		ResultCounter resultCounter = new ResultCounter("");

		try {
			recurFtpFiles(sysConf.getFtpRootDir(), resultCounter);
		} catch (Exception e) {
			logger.error("下载FTP文件失败, 请分析日志!! " + e.getMessage(), e);
		}

		int total = resultCounter.getSuccessRows()
				+ resultCounter.getFailedRows();

		TotalContant.total += total;
		TotalContant.totalSuccessRows += resultCounter.getSuccessRows();
		TotalContant.totalFailedRows += resultCounter.getFailedRows();
		TotalContant.totalNullRows += 0;
		TotalContant.totalDuplicatedRows += 0;

		return resultCounter;
	}
	
	/**
	 * FTP文件下载
	 * 
	 * @param directory
	 * @param resultCounter
	 * @throws IOException
	 */
	private static void recurFtpFiles(String directory,
			ResultCounter resultCounter) throws IOException {

		SystemConfig sysConf = beanFactory.getSystemConfig();

		FTPFile[] files = FtpUtils.listFiles(sysConf.getFtpIp(),
				sysConf.getFtpPort(), sysConf.getFtpUsername(),
				sysConf.getFtpPassword(), directory);

		if ((null == files) || (0 >= files.length)) {
			return;
		}

		String path;

		for (FTPFile f : files) {
			String fileName = f.getName();

			if (f.isFile()) {
				logger.info(fileName);

				if (!handledList.contains(fileName)) {
					handledList.add(fileName);
				} else {
					continue;
				}

				path = StringUtils.joinFilePath(sysConf.getDownloadLocalpath(),
						FileIdHelper.formatFileId(f.getName()));

				if (FtpUtils.download(sysConf.getFtpIp(), sysConf.getFtpPort(),
						sysConf.getFtpUsername(), sysConf.getFtpPassword(),
						directory, f.getName(), path)) {
					resultCounter.addSuccessRows();

					logger.info("[download success]: ftpFileName=" + fileName);
				} else {
					logger.info("[download failed]: 下载失败 --> ftpFileName=" + fileName);

					resultCounter.addFailedRows();
				}

				continue;
			}

			recurFtpFiles(directory + "/" + f.getName(), resultCounter);
		}
	}
}
