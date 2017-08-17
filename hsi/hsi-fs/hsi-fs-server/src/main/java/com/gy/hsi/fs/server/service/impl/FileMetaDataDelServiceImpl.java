/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.server.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.gy.hsi.fs.common.constant.FsApiConstant.FileStatus;
import com.gy.hsi.fs.common.utils.DateUtils;
import com.gy.hsi.fs.common.utils.StringUtils;
import com.gy.hsi.fs.server.common.framework.mybatis.MapperSupporter;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsFileMetaDataDel;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.ext.ExtendedFileMetaDataDelExample;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.ext.SimpleFileMetaDataDel;
import com.gy.hsi.fs.server.service.IFileMetaDataDelService;
import com.gy.hsi.fs.server.service.ITaobaoTfsClientService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.service.impl
 * 
 *  File Name       : FileMetaDataDelServiceImpl.java
 * 
 *  Creation Date   : 2015-05-15
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 删除的文件元数据访问serice实现类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/

@Service(value = "fileMetaDataDelService")
public class FileMetaDataDelServiceImpl extends MapperSupporter implements
		IFileMetaDataDelService {
	/** 记录日志对象 **/
	private final Logger logger = Logger.getLogger(this.getClass());

	@Resource
	private ITaobaoTfsClientService taobaoTfsClientService;

	@Resource(name = "removeFileFromTFSExecutor")
	private ThreadPoolTaskExecutor removeFileFromTFSExecutor;

	@Override
	public List<SimpleFileMetaDataDel> selectWaitingDeletedByExample(
			int offset, int limit) {
		// 1天前
		Date oldDate = DateUtils.getDateBeforeDays(1);

		Date currDateZeroTime = DateUtils.parseDate("yyyy-MM-dd",
				DateUtils.getDateByformat(oldDate, "yyyy-MM-dd 00:00:00"));

		Integer maxFailedCounts = 3;

		ExtendedFileMetaDataDelExample example = new ExtendedFileMetaDataDelExample();
		example.createCriteria()
				.andFileStatusNotEqualTo(FileStatus.DELETED.getValue())
				.andFailedCountsLessThan(maxFailedCounts)
				.andLastFailedDateLessThanOrEqualTo(currDateZeroTime);
		example.or(example.createCriteria()
				.andFileStatusNotEqualTo(FileStatus.DELETED.getValue())
				.andFailedCountsLessThan(maxFailedCounts)
				.andLastFailedDateIsNull());
		example.setOffset(offset);
		example.setLimit(limit);

		return getExtendedFileMetaDataDelMapper()
				.selectWaitingDeletedByExample(example);
	}

	@Override
	public SimpleFileMetaDataDel selectWaitingDeletedByFileId(String fileId) {
		return getExtendedFileMetaDataDelMapper().selectWaitingDeletedByFileId(
				fileId);
	}

	@Override
	public boolean insertFileMetaDataDel(TFsFileMetaDataDel fileMetaDataDel) {
		int rows = getTFsFileMetaDataDelMapper().insert(fileMetaDataDel);

		return (0 < rows);
	}

	@Override
	public boolean updateFailedCounts(Long id, Integer newFailedCounts) {

		TFsFileMetaDataDel record = new TFsFileMetaDataDel();
		record.setId(id);
		record.setFailedCounts(newFailedCounts);
		record.setLastFailedDate(new Date());

		int rows = getTFsFileMetaDataDelMapper().updateByPrimaryKeySelective(
				record);

		return (0 < rows);
	}

	@Override
	public boolean updateFileStatus(Long id, Byte fileStatus) {
		TFsFileMetaDataDel record = new TFsFileMetaDataDel();
		record.setId(id);
		record.setFileStatus(fileStatus);

		int rows = getTFsFileMetaDataDelMapper().updateByPrimaryKeySelective(
				record);

		return (0 < rows);
	}

	@Override
	public boolean deleteFileFromTFSByTask() {
		int offset = 0;
		int limit = 100;

		while (true) {
			// 一次最多查询出100条
			final List<SimpleFileMetaDataDel> list = this
					.selectWaitingDeletedByExample(offset, limit);

			if ((null == list) || (0 >= list.size())) {
				break;
			}

			removeFileFromTFSExecutor.execute(new Runnable() {
				@Override
				public void run() {
					for (final SimpleFileMetaDataDel record : list) {
						actionDeleteFileFromTFS(record);
					}
				}
			});

			offset += limit;
		}

		return true;
	}

	@Override
	public boolean deleteFileFromTFSByFileId(final String fileId) {
		removeFileFromTFSExecutor.execute(new Runnable() {
			@Override
			public void run() {
				SimpleFileMetaDataDel record = selectWaitingDeletedByFileId(fileId);

				actionDeleteFileFromTFS(record);
			}
		});

		return true;
	}

	/**
	 * 执行删除
	 * 
	 * @param record
	 */
	private void actionDeleteFileFromTFS(SimpleFileMetaDataDel record) {
		// 从TFS中删除文件
		boolean success = taobaoTfsClientService.deleteFile(
				record.getFileStorageId(), record.getFileSuffix());

		Long id = record.getId();

		if (!success) {
			String fileId = record.getFileId();
			String fileStoreId = record.getFileStorageId();

			// 追加失败次数
			this.updateFailedCounts(id, record.getFailedCounts() + 1);

			logger.info("The task failed to remove the file from TFS, "
					+ StringUtils.catString("fileId=", fileId,
							", fileStoreId=", fileStoreId));
		} else {
			// 更新文件状态未"2：已删除"
			this.updateFileStatus(id, FileStatus.DELETED.getValue());
		}
	}
}
