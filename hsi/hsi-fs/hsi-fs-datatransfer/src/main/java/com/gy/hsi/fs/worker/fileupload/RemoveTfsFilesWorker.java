package com.gy.hsi.fs.worker.fileupload;

import java.util.List;

import org.apache.log4j.Logger;

import com.gy.hsi.fs.common.spring.SpringContextLoader;
import com.gy.hsi.fs.common.utils.StringUtils;
import com.gy.hsi.fs.factory.BeansFactory;
import com.gy.hsi.fs.mapper.vo.TFsFileMetaData;
import com.gy.hsi.fs.service.IFSDataTransferService;
import com.gy.hsi.fs.service.ITaobaoTfsClientService;

public class RemoveTfsFilesWorker {

	private static final Logger logger = Logger.getLogger(DownloadWorker.class);

	private static BeansFactory beanFactory = SpringContextLoader
			.getBeansFactory();

	private static ITaobaoTfsClientService taobaoTfsClientService = beanFactory
			.getTaobaoTfsClientService();

	private static IFSDataTransferService fsDataTransferService = beanFactory
			.getFsDataTransferService();
	
	public static void doDeleteFromTFS(String fileStoreId) {
		
		TFsFileMetaData record = fsDataTransferService.queryByFileStorageId(fileStoreId);
		
		if (null != record) {
			Long bytes = record.getFileSizeBytes();
			
			if((null != bytes) && (0 < bytes)) {
				taobaoTfsClientService.deleteFile(fileStoreId, null);
			}
		}
	}

	public static void doDelete(String fileId, String fileStoreId) {

		Long bytes = null;
		
		if (!StringUtils.isEmpty(fileId)) {
			TFsFileMetaData record = fsDataTransferService
					.queryByFileId(fileId);

			if (null != record) {
				fileStoreId = record.getFileStorageId();
				bytes = record.getFileSizeBytes();
			}
		}

		if (!StringUtils.isEmpty(fileStoreId)
				&& (null != fsDataTransferService
						.queryByFileStorageId(fileStoreId))) {
			try {
				if((null != bytes) && (0 < bytes)) {
					taobaoTfsClientService.deleteFile(fileStoreId, null);
				}

				beanFactory.getFsDataTransferService().deleteByFileIdOrStoreId(
						fileId, fileStoreId);
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}
	
	public static void batchDeleteAllUploaded() {

		int start = 0;
		int PAGE_SIZE = 200;
		
		List<TFsFileMetaData> list;

		IFSDataTransferService fsService = beanFactory
				.getFsDataTransferService();

		while (true) {
			// 查询TFS文件
			list = fsService.queryTFsFileMetaDatasForDeleteAllUploaded(start, PAGE_SIZE);

			if ((null == list) || (0 >= list.size())) {
				break;
			}

			for (TFsFileMetaData d : list) {
				boolean s = taobaoTfsClientService.deleteFile(
						d.getFileStorageId(), d.getFileSuffix());
				
				beanFactory.getFsDataTransferService().deleteByFileIdOrStoreId(
						d.getFileId(), null);

				System.out.println(d.getFileId() + "删除结果：" + s);
			}			
		}
	}
	
	public static void batchDeleteAllInDB() {

		int start = 0;
		int PAGE_SIZE = 200;
		
		List<TFsFileMetaData> list;

		IFSDataTransferService fsService = beanFactory
				.getFsDataTransferService();

		while (true) {
			// 查询TFS文件
			list = fsService.queryTFsFileMetaDatasForDeleteAllInDB(start, PAGE_SIZE);

			if ((null == list) || (0 >= list.size())) {
				break;
			}

			for (TFsFileMetaData d : list) {
				boolean s = taobaoTfsClientService.deleteFile(
						d.getFileStorageId(), d.getFileSuffix());
				
				beanFactory.getFsDataTransferService().deleteByFileIdOrStoreId(
						d.getFileId(), null);

				System.out.println(d.getFileId() + "删除结果：" + s);
			}			
		}
	}
}
