/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.common.beans.param;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.fileupload.FileItem;

import com.gy.hsi.fs.common.constant.FsConstant;
import com.gy.hsi.fs.common.constant.HttpRequestParam.UploadRequestKey;
import com.gy.hsi.fs.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-common
 * 
 *  Package Name    : com.gy.hsi.fs.common.beans.param
 * 
 *  File Name       : UploadRequest.java
 * 
 *  Creation Date   : 2015年5月23日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件上传请求参数
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class UploadRequest {
	private String fileName;
	private String fileSuffix;

	private byte[] fileContentBytes;
	private Long fileSizeBytes = 0L;

	private String filePermission;

	private String userId;
	private String token;
	protected String channel;

	// 标识大文件的写,一次写失败后,再次传入相同的key,可以断电续传
	private String fileUploadUUID;

	// 仅用于覆盖上传
	private String fileId;

	// 内部使用字段, 不对外开放
	private Boolean isValidFileName = false;

	// 判断是否通过Fs客户端端上传
	private boolean isUploadByFsClient = false;

	// 判断是否通过匿名方式上传
	private boolean byAnonymousMode = false;

	public UploadRequest() {
	}

	public UploadRequest(List<FileItem> formFields, FileItem fileField) {
		this.doInit(formFields, fileField);
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public byte[] getFileContentBytes() {
		return fileContentBytes;
	}

	public Long getFileSizeBytes() {
		return fileSizeBytes;
	}

	public String getFilePermission() {
		return filePermission;
	}

	public String getUserId() {
		return userId;
	}

	public String getToken() {
		return token;
	}

	public String getChannel() {
		return channel;
	}

	public boolean isByAnonymousMode() {
		return byAnonymousMode;
	}

	public String getFileUploadUUID() {
		return fileUploadUUID;
	}

	public String getFileId() {
		return fileId;
	}

	public void clearFileContentBytes() {
		fileContentBytes = new byte[1];
	}

	/**
	 * 初始化
	 * 
	 * @param formFields
	 * @param fileField
	 */
	private void doInit(List<FileItem> formFields, FileItem fileField) {

		List<String> fileNames = null;
		List<String> fileSuffixes = null;

		// 表单普通域
		for (FileItem formField : formFields) {
			String fieldName = formField.getFieldName();
			String fieldValue = "";

			try {
				fieldValue = formField.getString(FsConstant.ENCODING_UTF8);
			} catch (UnsupportedEncodingException e) {
			}

			if (UploadRequestKey.FILE_ID.equals(fieldName)) {
				this.fileId = fieldValue;
			}

			if (UploadRequestKey.FILE_PERMISSION.equals(fieldName)) {
				this.filePermission = fieldValue;
			}

			if (UploadRequestKey.USER_ID.equals(fieldName)) {
				this.userId = fieldValue;
			}

			if (UploadRequestKey.TOKEN.equals(fieldName)) {
				this.token = fieldValue;
			}

			if (UploadRequestKey.CHANNEL.equals(fieldName)) {
				this.channel = fieldValue;
			}

			if (UploadRequestKey.BY_ANONYMOUS_MODE.equals(fieldName)) {
				this.byAnonymousMode = "true".equals(fieldValue);
			}

			// 有这个字段表示通过Fs客户端上传的
			if (UploadRequestKey.$IS_VALID_FILENAME.equals(fieldName)) {
				this.isValidFileName = "true".equals(fieldValue);
				this.isUploadByFsClient = true;
			}

			if (UploadRequestKey.FILE_NAME.equals(fieldName)) {
				fileNames = this.changeStr2List(fieldValue);
			}

			if (UploadRequestKey.FILE_SUFFIX.equals(fieldName)) {
				fileSuffixes = this.changeStr2List(fieldValue);
			}
		}

		// 表单文件域
		{
			// 文件名称
			String realFileName = StringUtils.cut2SpecialLength(
					extractFileName(fileField.getName()), 45, "..");
			// 文件域的索引
			int fileFieldIndex = StringUtils.str2Int(fileField.getFieldName(),
					99);

			this.fileContentBytes = fileField.get();
			this.fileSizeBytes = fileField.getSize();

			this.fileName = this.parseValidFileName(fileNames, fileFieldIndex,
					realFileName);
			this.fileSuffix = this
					.parseFileSuffix(fileSuffixes, fileFieldIndex);

			this.fileUploadUUID = "";
		}
	}

	/**
	 * 解析有效的文件名称
	 * 
	 * @param reqFileSuffixlist
	 * @param index
	 * @return
	 */
	private String parseFileSuffix(List<String> reqFileSuffixlist, int index) {
		if ((null == reqFileSuffixlist) || (0 >= reqFileSuffixlist.size())) {
			return "";
		}

		boolean isValidIndex = (reqFileSuffixlist.size() - 1 >= index);

		if (isValidIndex) {
			String suffix = reqFileSuffixlist.get(index);

			if (!StringUtils.isEmpty(suffix)) {
				return suffix.replaceFirst("^\\.", "");
			}
		}

		return "";
	}

	/**
	 * 解析有效的文件名称
	 * 
	 * @param reqFileNamelist
	 * @param index
	 * @param realFileName
	 * @return
	 */
	private String parseValidFileName(List<String> reqFileNamelist, int index,
			String realFileName) {
		if ((null == reqFileNamelist) || (0 >= reqFileNamelist.size())) {
			// 通过网页直接请求Fs地址上传
			if (!this.isUploadByFsClient) {
				return realFileName;
			}

			return "";
		}

		boolean isValidIndex = (reqFileNamelist.size() - 1 >= index);

		if (isValidIndex) {
			if (this.isUploadByFsClient) {
				if (this.isValidFileName) {
					return reqFileNamelist.get(index);
				}
			}

			return reqFileNamelist.get(index);
		}

		return "";
	}

	/**
	 * 解析文件名称
	 * 
	 * @param originalFileName
	 * @return
	 */
	private String extractFileName(String originalFileName) {
		int lastIndexOfSlash = originalFileName.lastIndexOf("/");
		int lastIndexOfBackSlash = originalFileName.lastIndexOf("\\");

		int length = originalFileName.length();

		if ((0 < lastIndexOfSlash) && (length - 1 > lastIndexOfSlash)
				|| (0 < lastIndexOfBackSlash)
				&& (length - 1 > lastIndexOfBackSlash)) {
			int newIndex = (lastIndexOfSlash > lastIndexOfBackSlash) ? lastIndexOfSlash
					: lastIndexOfBackSlash;

			String fileNameWithSuffix = originalFileName
					.substring(newIndex + 1);

			return fileNameWithSuffix;
		}

		return "";
	}

	/**
	 * 解析文件名称
	 * 
	 * @param originalFileName
	 * @return [文件名称][文件后缀]
	 */
	@SuppressWarnings("unused")
	private String[] splitFileNameAndsuffix(String originalFileName) {
		String[] resultArr = new String[] { "", "" };

		int lastIndexOfSlash = originalFileName.lastIndexOf("/");
		int lastIndexOfBackSlash = originalFileName.lastIndexOf("\\");

		int length = originalFileName.length();

		if ((0 < lastIndexOfSlash) && (length - 1 > lastIndexOfSlash)
				|| (0 < lastIndexOfBackSlash)
				&& (length - 1 > lastIndexOfBackSlash)) {
			int newIndex = (lastIndexOfSlash > lastIndexOfBackSlash) ? lastIndexOfSlash
					: lastIndexOfBackSlash;

			String fileNameWithSuffix = originalFileName
					.substring(newIndex + 1);
			int lastIndexOfDot = fileNameWithSuffix.lastIndexOf(".");

			resultArr[0] = fileNameWithSuffix.substring(0, lastIndexOfDot);
			resultArr[1] = fileNameWithSuffix.substring(lastIndexOfDot + 1);
		}

		return resultArr;
	}

	/**
	 * 解析出用户访问列表
	 * 
	 * @param str
	 * @return
	 */
	private List<String> changeStr2List(String str) {
		String[] array;

		if (!StringUtils.isEmpty(str)) {
			array = str.split(FsConstant.COMMA_CHAR);

			return Arrays.asList(array);
		}

		return null;
	}
}
