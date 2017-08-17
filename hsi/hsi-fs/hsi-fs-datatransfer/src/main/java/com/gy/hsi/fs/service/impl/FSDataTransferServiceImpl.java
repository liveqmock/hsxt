/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gy.hsi.fs.common.constant.Constant;
import com.gy.hsi.fs.common.constant.FsApiConstant.FilePermission;
import com.gy.hsi.fs.common.constant.FsConstant;
import com.gy.hsi.fs.common.exception.HsDuplicatedException;
import com.gy.hsi.fs.common.exception.HsHasHandledException;
import com.gy.hsi.fs.common.exception.HsIllegalFileIdException;
import com.gy.hsi.fs.common.utils.FileIdHelper;
import com.gy.hsi.fs.common.utils.StringUtils;
import com.gy.hsi.fs.mapper.FSMapperSupporter;
import com.gy.hsi.fs.mapper.vo.TFsFileMetaData;
import com.gy.hsi.fs.mapper.vo.TFsFileMetaDataExample;
import com.gy.hsi.fs.service.IFSDataTransferService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-datatransfer
 * 
 *  Package Name    : com.gy.hsi.fs.service.impl
 * 
 *  File Name       : FSDataTransferServiceImpl.java
 * 
 *  Creation Date   : 2015年11月20日
 * 
 *  Author          : Administrator
 * 
 *  Purpose         : TODO
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Service
public class FSDataTransferServiceImpl extends FSMapperSupporter implements
		IFSDataTransferService {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger
			.getLogger(FSDataTransferServiceImpl.class);

	private static final long TEMP_FOR_UC = -100;
	private static final String TEMP_TRANS_USER_FOR_UC = FsConstant.DEFAULT_TRANSFER_USER
			+ TEMP_FOR_UC;

	private static List<String> blackList = new ArrayList<String>();

	static {
		blackList.add("efa72439-df58-4c84-9f1e-fda5b60b33fc");
		blackList.add("1ba77304-7ff8-4838-94e5-5a6e747ec945");
		blackList.add("25328de7-306c-4005-a5ad-505160b4152b");
		blackList.add("e541064e-1e61-4848-b130-a3c5559e2633");
		blackList.add("70ab8042-2068-488d-a67a-9546d93ac3fc");
		blackList.add("7883d827-f1f1-4cd8-ac81-5a8d0e5cd19d");
		blackList.add("4fab0ed5-d3bf-46cc-ad47-1ebfb1c80323");
		blackList.add("b191342a-1584-4a56-b8a7-c57377bf41b1");
		blackList.add("1d2eba45-acf8-415b-ba0f-7f3af5df6aa9");
		blackList.add("eeb5f613-c63d-41e6-ab3f-6ddc695b0294");
		blackList.add("de6ef6bb-8bba-4f39-8597-e95158f9c55b");
		blackList.add("6b6356c7-66db-413c-b9aa-0d00a52d6ba9");
		blackList.add("349baa0c-6d2e-4f72-9624-9bab3516ca07");
		blackList.add("8eb78d3a-b5a6-4710-aa0b-41d06ea3b809");
		blackList.add("64930486-1b0d-4611-ae78-ecf447578cda");
		blackList.add("e3e3deaf-ce39-4c6f-bde1-010264dbeec1");
		blackList.add("4f723cae-e4eb-4c2b-81f4-28ecb408d95a");
		blackList.add("44859056-b421-47ca-b20e-c58743bce7e6");
		blackList.add("ec25c930-6316-4a6d-a059-b5fe7346db98");
		blackList.add("e4aae9da-fb3f-45cd-9139-ed3e5151723d");
		blackList.add("5890b772-75c9-4869-90c0-1e6d9d0468c7");
		blackList.add("2ba00fe5-91f2-4787-8805-6cf8933d8431");
		blackList.add("b0d90057-7c25-4b3c-9d27-45f45a9fb996");
		blackList.add("87dcfec0-a86c-4c31-82e7-f7ae52d15546");
		blackList.add("a6d9969a-acaa-471f-8113-1cbf244ea908");
		blackList.add("18761504-aa65-491a-8367-6aadfcc09dd6");
		blackList.add("453f5c64-7257-4b86-8e45-45b47453d16f");
		blackList.add("df3bca59-75c2-4a6d-a7de-6d4eca46b771");
		blackList.add("2507fcfe-8b3d-4bb3-9bbe-c43044ea7765");
		blackList.add("89b9a2ad-77c9-427f-87b4-19d0d37febc2");
		blackList.add("21c1954a-ce8d-4a7c-835c-cb065a0805b9");
		blackList.add("ebb85012-44d9-4850-a5db-15352f5056dc");
		blackList.add("2f2d2a0f-7181-4c32-aa6c-a62c668f1b4b");
		blackList.add("7acd2e1f-e141-4927-9092-8de30b5e273d");
		blackList.add("9388dacf-4870-409e-bcc8-37640cfdb719");
		
		blackList.add("8eb78d3a-b5a6-4710-aa0b-41d06ea3b809.jpg");
		blackList.add("64930486-1b0d-4611-ae78-ecf447578cda.jpg");
		blackList.add("9388dacf-4870-409e-bcc8-37640cfdb719.jpg");

		blackList.add("L1Zt_TByxj1R4cSCrK.png");
		blackList.add("L1Zt_TByxj1R4cSCrK.PNG");
		blackList.add("L1Zt_TByxj1R4cSCrK.JPG");
		blackList.add("L1Zt_TByxj1R4cSCrK.jpg");
		blackList.add("L1Zt_TByxj1R4cSCrK.JPEG");
		blackList.add("L1Zt_TByxj1R4cSCrK.jpeg");		
		blackList.add("L1Zt_TByxj1R4cSCrK");
		
		blackList.add("91310107MAIG03JJ80");
		blackList.add("913102305758281087");
		blackList.add("91420100MA4KL2C17W");
		blackList.add("91500105075674480H");
		blackList.add("91440104331501424M");
		blackList.add("91440700345280714L");
		blackList.add("91442000MA4UNN6R9A");
		blackList.add("91440605084500079F");
		blackList.add("NO_FILE");
		blackList.add("baskinfo_df.png");
		blackList.add("null.png");
	}

	@Override
	public String insert2FsDb(String tfsFileId, boolean isUCHeadshot) {

		if (StringUtils.isEmpty(tfsFileId)) {
			return Constant.NO_FILE;
		}
		
		tfsFileId = tfsFileId.trim();

		if (isIllegalTfsFileId(tfsFileId)) {
			throw new HsIllegalFileIdException("TFS文件Id不合法！ tfsFileId="
					+ tfsFileId, tfsFileId);
		}

		// TFS文件ID以T或者L开头的
		if (!tfsFileId.startsWith("05001010862")) {
			if (FileIdHelper.checkFileId(tfsFileId)) {
				throw new HsHasHandledException("文件id已经转换过，无需重复处理，略过！ fileId="
						+ tfsFileId, tfsFileId);
			}
		}

		String fileNameWithoutSuffix = tfsFileId;
		String fileSuffix = "";

		if (isTfsFileId(tfsFileId)) {
			fileNameWithoutSuffix = tfsFileId.substring(0, 18);
			fileSuffix = StringUtils.avoidNull(tfsFileId.substring(18))
					.replaceAll("^\\.", "");
		}

		TFsFileMetaData record = doQueryByFileStorageId(fileNameWithoutSuffix);

		if (null == record) {
			// 将之前的清理掉!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			// RemoveTfsFilesWorker.doDelete(null, fileNameWithoutSuffix);

			String fileId = generateFsFileId();

			record = new TFsFileMetaData();
			record.setFileId(fileId);
			record.setFileStorageId(fileNameWithoutSuffix);
			record.setOwnerUserId(FsConstant.DEFAULT_TRANSFER_USER);
			record.setFileSuffix(fileSuffix);
			record.setFileSizeBytes((long) -1);
			record.setIsAnonymous(false);
			record.setFileStatus((byte)0);
			record.setCreatedDate(new Date());
			
			if (isUCHeadshot) {
				// 用户中心头像, 必须为公共的文件
				record.setFilePermission(FilePermission.PUBLIC_READ.getValue());
			} else {
				record.setFilePermission(FilePermission.PROTECTED_READ_WRITE
						.getValue());
			}

			getTFsFileMetaDataMapper().insert(record);
		} else {
			throw new HsDuplicatedException("重复的tfsFileId, fileid="
					+ record.getFileId() + ", src tfsFileId="
					+ fileNameWithoutSuffix, record.getFileId());
		}

		return record.getFileId();
	}

	@Override
	public List<TFsFileMetaData> queryTFsFileMetaDatasForDownloadTFS(int start,
			int end) {

		TFsFileMetaDataExample example = new TFsFileMetaDataExample();
		example.setStart(start);
		example.setEnd(end);

		//example.createCriteria().andFileStorageIdLike("T%");
		//example.or(example.createCriteria().andFileStorageIdLike("L%"));

		// T% L%
		return getTFsFileMetaDataMapper().selectByExampleForDownload(example);
	}

	@Override
	public List<TFsFileMetaData> queryTFsFileMetaDatasForDeleteAllUploaded(
			int start, int end) {
		TFsFileMetaDataExample example = new TFsFileMetaDataExample();
		example.setStart(start);
		example.setEnd(end);
		example.createCriteria().andOwnerUserIdLike("$transfer_user%");

		return getTFsFileMetaDataMapper().selectByExample(example);
	}
	
	@Override
	public List<TFsFileMetaData> queryTFsFileMetaDatasForDeleteAllInDB(
			int start, int end) {
		TFsFileMetaDataExample example = new TFsFileMetaDataExample();
		example.setStart(start);
		example.setEnd(end);
		
		return getTFsFileMetaDataMapper().selectByExample(example);
	}

	@Override
	public List<TFsFileMetaData> queryTFsFileMetaDatasForUpload(int start,
			int end) {
		TFsFileMetaDataExample example = new TFsFileMetaDataExample();
		example.setStart(start);
		example.setEnd(end);
		example.createCriteria().andOwnerUserIdLike("$transfer_user%")
				.andFileSizeBytesLessThanOrEqualTo(0L);

		return getTFsFileMetaDataMapper().selectByExample(example);
	}

	@Override
	public TFsFileMetaData queryByFileId(String fileId) {
		TFsFileMetaDataExample example = new TFsFileMetaDataExample();
		example.createCriteria().andFileIdEqualTo(fileId);

		List<TFsFileMetaData> results = getTFsFileMetaDataMapper()
				.selectByExample(example);

		if ((null != results) && (0 < results.size())) {
			return results.get(0);
		}

		return null;
	}

	@Override
	public boolean removeAllTempDataForUCHeadshot() {

		TFsFileMetaDataExample example = new TFsFileMetaDataExample();
		example.createCriteria().andOwnerUserIdEqualTo(TEMP_TRANS_USER_FOR_UC)
		/** .andFileSizeBytesEqualTo(TEMP_FOR_UC) */
		;

		getTFsFileMetaDataMapper().deleteByExample(example);

		return true;
	}

	@Override
	public boolean updateByFileStoreId(String oldFileStoreId,
			String newFileStoreId, long size) {

		String fileNameWithoutSuffix = newFileStoreId;
		String fileSuffix = "";

		if (isTfsFileId(newFileStoreId)) {
			fileNameWithoutSuffix = newFileStoreId.substring(0, 18);
			fileSuffix = StringUtils.avoidNull(newFileStoreId.substring(18))
					.replaceAll("^\\.", "");
		}

		TFsFileMetaData record = new TFsFileMetaData();
		record.setFileStorageId(fileNameWithoutSuffix);
		record.setUpdatedDate(new Date());

		if (0 < size) {
			record.setFileSizeBytes(size);
		}

		if (!StringUtils.isEmpty(fileSuffix)) {
			record.setFileSuffix(fileSuffix);
		}

		TFsFileMetaDataExample example = new TFsFileMetaDataExample();
		example.createCriteria().andFileStorageIdEqualTo(oldFileStoreId);

		int rows = getTFsFileMetaDataMapper().updateByExampleSelective(record,
				example);

		return (0 < rows);
	}

	@Override
	public boolean deleteByFileIdOrStoreId(String fileId, String fileStoreId) {
		TFsFileMetaDataExample example = new TFsFileMetaDataExample();

		if (!StringUtils.isEmpty(fileId)) {
			example.createCriteria().andFileIdEqualTo(fileId);
		}

		else if (!StringUtils.isEmpty(fileStoreId)) {
			example.createCriteria().andFileStorageIdEqualTo(fileStoreId);
		}

		int rows = getTFsFileMetaDataMapper().deleteByExample(example);

		return (0 < rows);
	}

	@Override
	public TFsFileMetaData queryByFileStorageId(String fileStorageId) {
		return doQueryByFileStorageId(fileStorageId);
	}

	@Override
	public int countTFSFiles() {

		TFsFileMetaDataExample example = new TFsFileMetaDataExample();

		example.or(example.createCriteria().andFileStorageIdLike("T%"));
		example.or(example.createCriteria().andFileStorageIdLike("L%"));

		return getTFsFileMetaDataMapper().countByExample(example);
	}

	@Override
	public int countFTPFiles() {
		TFsFileMetaDataExample example = new TFsFileMetaDataExample();
		example.createCriteria().andFileStorageIdIsNotNull();

		int total = getTFsFileMetaDataMapper().countByExample(example);

		return total - countTFSFiles();
	}

	private TFsFileMetaData doQueryByFileStorageId(String fileStorageId) {
		TFsFileMetaDataExample example = new TFsFileMetaDataExample();
		example.createCriteria().andFileStorageIdEqualTo(fileStorageId);

		List<TFsFileMetaData> results = getTFsFileMetaDataMapper()
				.selectByExample(example);

		if ((null == results) || (0 >= results.size())) {
			return null;
		}

		return results.get(0);
	}

	private String generateFsFileId() {
		String fileId = "";

		for (int i = 0; i < 10; i++) {
			fileId = FileIdHelper.generateSecureFileId();

			if (null == getTFsFileMetaDataMapper().selectByPrimaryKey(fileId)) {
				break;
			}
		}

		return fileId;
	}

	private boolean isIllegalTfsFileId(String tfsFileId) {
		if(tfsFileId.startsWith("05001010862")) {
			return false;
		}
		
		boolean isSpecial = (42 < tfsFileId.length()
				|| blackList.contains(tfsFileId) || 18 > tfsFileId.length());

		return isSpecial;
	}

	private boolean isTfsFileId(String tfsFileId) {

		boolean isTfsFileId = tfsFileId.startsWith("T")
				|| tfsFileId.startsWith("L");

		return (18 <= tfsFileId.length()) && isTfsFileId;
	}

	public static void main(String[] args) {

		String tfsFileId = "L1.RbTBCCT1RXrhCrK.doc";
		String fileNameWithoutSuffix = "";
		String fileSuffix = "";

		if (18 <= tfsFileId.length()) {
			fileNameWithoutSuffix = tfsFileId.substring(0, 18);
			fileSuffix = StringUtils.avoidNull(tfsFileId.substring(18))
					.replaceAll("^\\.", "");
		}

		System.out.println(fileNameWithoutSuffix);
		System.out.println(fileSuffix);
	}

	// CREATE TABLE T_GUID_FILE_ID
	// (
	// TABLE_NAME VARCHJAR2(80), --表名
	// COLUMN_NAME VARCHJAR2(80), --字段名
	// VALUE_AFT_MIGRATE VARCHJAR2(80), --迁移后的文件ID
	// VALUE_BEF_MIGRATE VARCHJAR2(512) --迁移前的文件完整路径名
	// );

}
