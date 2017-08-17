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
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.fs.common.constant.FsApiConstant.FilePermission;
import com.gy.hsi.fs.common.constant.FsApiConstant.FileStatus;
import com.gy.hsi.fs.common.exception.FsFileNotExistException;
import com.gy.hsi.fs.common.utils.StringUtils;
import com.gy.hsi.fs.server.common.framework.mybatis.MapperSupporter;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsFileMetaData;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsFileMetaDataDel;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsFileMetaDataExample;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.ext.FileName;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.ext.SimpleFileMetaInfo;
import com.gy.hsi.fs.server.service.IFileMetaDataDelService;
import com.gy.hsi.fs.server.service.IFileMetaDataService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.service.impl
 * 
 *  File Name       : FileMetaDataServiceImpl.java
 * 
 *  Creation Date   : 2015-05-15
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件元数据访问serice实现类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/

@Service(value = "fileMetaDataService")
public class FileMetaDataServiceImpl extends MapperSupporter implements
		IFileMetaDataService {
	/** 记录日志对象 **/
	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(this.getClass());

	@Resource
	private IFileMetaDataDelService fileMetaDataDelService;

	@Override
	public boolean insertFileMetaData(TFsFileMetaData fileMetaData) {
		fileMetaData.setFileStatus(FileStatus.NORMAL.getValue());
		fileMetaData.setCreatedDate(new Date());

		int rows = getTFsFileMetaDataMapper().insert(fileMetaData);

		return (0 < rows);
	}

	@Override
	public boolean updateFileMetaData(TFsFileMetaData fileMetaData) {
		fileMetaData.setUpdatedDate(new Date());

		int rows = getTFsFileMetaDataMapper().updateByPrimaryKeySelective(
				fileMetaData);

		return (0 < rows);
	}

	@Override
	public TFsFileMetaData queryFileMetaData(String fileId) {
		TFsFileMetaDataExample example = new TFsFileMetaDataExample();
		example.createCriteria().andFileIdEqualTo(fileId);

		List<TFsFileMetaData> list = getTFsFileMetaDataMapper()
				.selectByExample(example);

		if ((null != list) && (0 < list.size())) {
			return list.get(0);
		}

		return null;
	}

	@Override
	public SimpleFileMetaInfo querySimpleFileMetaInfo(String fileId) {
		return getExtendedFileMetaDataMapper().querySimpleFileMetaInfo(fileId);
	}

	@Override
	public boolean isOwnerUser(String fileId, String userId)
			throws FsFileNotExistException {
		SimpleFileMetaInfo metaInfo = querySimpleFileMetaInfo(fileId);

		if (null == metaInfo) {
			throw new IllegalArgumentException("The file does't exist!");
		}

		return metaInfo.getOwnerUserId().equals(userId);
	}

	@Override
	public boolean isAnonyUploadedFile(String fileId)
			throws FsFileNotExistException {
		SimpleFileMetaInfo metaInfo = querySimpleFileMetaInfo(fileId);

		if (null == metaInfo) {
			throw new IllegalArgumentException("The file does't exist!");
		}

		return metaInfo.isAnonymous();
	}

	@Override
	public boolean hasDumplicatedFileId(String fileId) {
		return (1 <= getExtendedFileMetaDataMapper().queryFileIdCounts(fileId));
	}

	@Override
	public boolean isExist(String fileId) {
		return (1 <= getExtendedFileMetaDataMapper().queryFileIdCounts(fileId));
	}

	@Override
	public boolean isPublicReadFile(String fileId)
			throws FsFileNotExistException {
		String filePermission = getExtendedFileMetaDataMapper()
				.queryFilePermissionByFileId(fileId);

		if (StringUtils.isEmpty(filePermission)) {
			throw new FsFileNotExistException("The file does't exist!");
		}

		return FilePermission.PUBLIC_READ.valueEquals(filePermission);
	}

	@Override
	public FileName queryFileNameByFileStorageId(String fileStorageId) {
		return getExtendedFileMetaDataMapper().queryFileNameByFileStorageId(
				fileStorageId);
	}

	@Override
	@Transactional
	public int deleteFileByFileId(String fileId) {
		TFsFileMetaData metaData = queryFileMetaData(fileId);

		if (null != metaData) {
			TFsFileMetaDataDel metaDataDel = new TFsFileMetaDataDel();

			BeanUtils.copyProperties(metaData, metaDataDel);

			metaDataDel.setFailedCounts(0);
			metaDataDel.setFileStatus(FileStatus.DELETED.getValue());
			metaDataDel.setUpdatedDate(new Date());

			// 将数据插入历史表
			fileMetaDataDelService.insertFileMetaDataDel(metaDataDel);
		}

		// 从原表中删除
		return getTFsFileMetaDataMapper().deleteByPrimaryKey(fileId);
	}
}
