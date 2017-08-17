/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.worker.fileupload;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.springframework.core.task.TaskRejectedException;

import com.gy.hsi.fs.common.bean.ResultCounter;
import com.gy.hsi.fs.common.spring.SpringContextLoader;
import com.gy.hsi.fs.common.utils.PrintUploadUtils;
import com.gy.hsi.fs.factory.BeansFactory;
import com.gy.hsi.fs.mapper.vo.TFsFileMetaData;
import com.gy.hsi.fs.service.IFSDataTransferService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-datatransfer
 * 
 *  Package Name    : com.gy.hsi.fs.worker
 * 
 *  File Name       : UploadWorker.java
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
public class UploadWorker {
	private static final Logger logger = Logger.getLogger(DownloadWorker.class);

	private static BeansFactory beanFactory = SpringContextLoader
			.getBeansFactory();

	private static int PAGE_SIZE = 5000;
	private static final List<String> statisticResultList = new ArrayList<String>();
	public static List<String> handledList = new ArrayList<String>();

	public static List<String> doIt() {
		System.out.println();
		System.out.println("======================开始上传文件到TFS========================");

		logger.info("======================开始上传文件到TFS========================");

		int start = 0;
		int totalRows = 0;
		
		List<TFsFileMetaData> list = null;

		// CountDownLatch latch;

		IFSDataTransferService fsService = beanFactory
				.getFsDataTransferService();

		List<ResultCounter> counterList = new ArrayList<ResultCounter>();
		counterList.add(new ResultCounter(""));

		while (true) {
			// 查询所有: 包括TFS和FTP
			list = fsService.queryTFsFileMetaDatasForUpload(start, PAGE_SIZE);

			if ((null == list) || (0 >= list.size())) {
				// 打印结果
				PrintUploadUtils.printUploadFinishLog(totalRows, counterList,
						statisticResultList);

				break;
			} else {
				System.out.println("正在努力上传中 ....");

				totalRows += list.size();
			}

			// latch = new CountDownLatch(list.size());

			for (TFsFileMetaData d : list) {
				// actionUploadTfsInThreadpool(d.getFileStorageId(),
				// d.getFileSuffix(), counterList, latch, d);

				PrintUploadUtils.uploadLocalFile2Tfs(d.getFileStorageId(),
						d.getFileSuffix(), counterList, d);
			}

			// try {
			// latch.await();
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			
			start += PAGE_SIZE;
		}

		{
			statisticResultList.add("");

			int countInDB = beanFactory.getFsDataTransferService()
					.countTFSFiles()
					+ beanFactory.getFsDataTransferService().countFTPFiles();

			if (counterList.get(0).getSuccessRows() == countInDB) {
				statisticResultList.add("文件全部上传成功: 数据库中文件数据量  == 实际上传成功文件数据量("
						+ countInDB + ")。");

				// 从FS表中清理掉UC头像临时数据
				// beanFactory.getFsDataTransferService().removeAllTempDataForUCHeadshot();
			} else if (counterList.get(0).getSuccessRows() != countInDB) {
				statisticResultList.add("TFS部分文件上传失败： 数据库中文件数据量=" + countInDB
						+ ", 实际上传成功文件数据量="
						+ counterList.get(0).getSuccessRows());
			}
		}

		return statisticResultList;
	}

	/**
	 * 执行上传
	 * 
	 * @param tfsOrFtpFileId
	 * @param fileNameSuffix
	 * @param counterList
	 * @param latch
	 */
	@SuppressWarnings("unused")
	private static void actionUploadTfsInThreadpool(
			final String tfsOrFtpFileId, final String fileNameSuffix,
			final List<ResultCounter> counterList, final CountDownLatch latch,
			final TFsFileMetaData d) {
		try {
			// 线程池中运行
			beanFactory.getUploadExecutor().execute(new Runnable() {
				@Override
				public void run() {
					try {
						PrintUploadUtils.uploadLocalFile2Tfs(tfsOrFtpFileId,
								fileNameSuffix, counterList, d);
					} finally {
						latch.countDown();
					}
				}
			});
		} catch (TaskRejectedException ex) {
			try {
				PrintUploadUtils.uploadLocalFile2Tfs(tfsOrFtpFileId,
						fileNameSuffix, counterList, d);
			} finally {
				latch.countDown();
			}
		}
	}
}
