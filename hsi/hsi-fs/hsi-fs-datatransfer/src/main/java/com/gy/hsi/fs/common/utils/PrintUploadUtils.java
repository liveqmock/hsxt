package com.gy.hsi.fs.common.utils;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.gy.hsi.fs.common.bean.ResultCounter;
import com.gy.hsi.fs.common.bean.SystemConfig;
import com.gy.hsi.fs.common.constant.Constant;
import com.gy.hsi.fs.common.spring.SpringContextLoader;
import com.gy.hsi.fs.factory.BeansFactory;
import com.gy.hsi.fs.mapper.vo.TFsFileMetaData;

public class PrintUploadUtils {

	private static final Logger logger = Logger
			.getLogger(PrintUploadUtils.class);

	private static BeansFactory beanFactory = SpringContextLoader
			.getBeansFactory();

	public static void printUploadFinishLog(int totalRows,
			List<ResultCounter> counterList, List<String> statisticResultList) {

		if ((null == counterList) || (0 >= counterList.size())) {
			logger.info("处理完毕, 没有需要上传的文件。");

			return;
		}

		PrintDownloadUtils.printDownloadFinishLog(totalRows, counterList,
				statisticResultList, null);
	}

	/**
	 * 将文件id保存到FS数据库中
	 * 
	 * @param tfsOrFtpFileId
	 * @param counterList
	 * @return
	 */
	public static void uploadLocalFile2Tfs(String tfsOrFtpFileId,
			String fileNameSuffix, List<ResultCounter> counterList,
			TFsFileMetaData fileMetaData) {
		
		int $$$$FIELDS_NAME_INDEX = 0;
		
		if (null != fileMetaData) {
			Long byteSize = fileMetaData.getFileSizeBytes();

			if ((null != byteSize) && (0 < byteSize)) {
				counterList.get($$$$FIELDS_NAME_INDEX).addSuccessRows();
				
				logger.info("success: src tfsFileId=" + tfsOrFtpFileId
						+ ", new tfsFileId=" + fileMetaData.getFileStorageId());

				return;
			}
		}

		try {
			SystemConfig systemConfig = beanFactory.getSystemConfig();

			String localFilePath = StringUtils.joinFilePath(
					systemConfig.getDownloadLocalpath(),
					FileIdHelper.formatFileId(tfsOrFtpFileId));

			String newTfsFileId = "";
			long size = FileUtils.sizeOf(new File(localFilePath));

			// 小文件
			if (size >= Constant.SMALL_FILE_SIZE_UPPER_LIMIT) {
				newTfsFileId = beanFactory.getTaobaoTfsClientService().saveFile(
						localFilePath, fileNameSuffix);
			}
			// 上传大文件
			else {
				newTfsFileId = beanFactory.getTaobaoTfsClientService()
						.saveLargeFile(localFilePath, fileNameSuffix);
			}

			// 如果文件存储id不为空, 则表示上传成功
			if (!StringUtils.isEmpty(newTfsFileId)) {
				counterList.get($$$$FIELDS_NAME_INDEX).addSuccessRows();
				logger.info("success upload: src tfsFileId=" + tfsOrFtpFileId
						+ ", new tfsFileId=" + newTfsFileId);

				// ==================将FS对应表中的FILE_STORAGE_ID修改为新的TFS文件ID=================
				updateFileStoreId2New(tfsOrFtpFileId, newTfsFileId, size);
			} else {
				counterList.get($$$$FIELDS_NAME_INDEX).addFailedRows();
				logger.error("[upload failed] 上传文件失败：src tfsFileId=" + tfsOrFtpFileId);
			}
		} catch (Exception ex) {
			counterList.get($$$$FIELDS_NAME_INDEX).addFailedRows();

			logger.error("[upload failed] 上传文件失败：src tfsFileId=" + tfsOrFtpFileId, ex);
		}
	}
	
	private static boolean updateFileStoreId2New(String oldFileStoreId,
			String newFileStoreId, long size) {

		boolean success = beanFactory.getFsDataTransferService()
				.updateByFileStoreId(oldFileStoreId, newFileStoreId, size);

		return success;
	}
}
