/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.client.common;

import java.io.OutputStream;
import java.util.List;

import com.gy.hsi.fs.common.beans.SecurityToken;
import com.gy.hsi.fs.common.constant.FsApiConstant.FilePermission;
import com.gy.hsi.fs.common.exception.FsException;
import com.gy.hsi.fs.common.exception.FsParameterException;
import com.gy.hsi.fs.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-client
 * 
 *  Package Name    : com.gy.hsi.fs.client.common
 * 
 *  File Name       : ParameterChecker.java
 * 
 *  Creation Date   : 2015年6月2日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 参数校验
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class ParameterChecker {

	public static boolean checkUploadFile(String localFileName,
			String fileSuffix, FilePermission filePermission, String userId,
			String token, String channel) throws FsException {
		checkString("localFileName", localFileName);
		checkSecureToken(userId, token, channel);

		return true;
	}

	public static boolean checkUploadFile(String localFileName,
			String fileSuffix, FilePermission filePermission,
			SecurityToken securityToken) throws FsException {
		checkString("localFileName", localFileName);
		checkSecureToken(securityToken);

		return true;
	}

	public static boolean checkUploadFile(byte[] fileBytesData,
			String fileSuffix, FilePermission filePermission, String userId,
			String token, String channel) throws FsException {
		checkBytes("fileBytesData", fileBytesData);
		checkSecureToken(userId, token, channel);

		return true;
	}

	public static boolean checkUploadFile(byte[] fileBytesData,
			String fileSuffix, FilePermission filePermission,
			SecurityToken securityToken) throws FsException {
		checkBytes("fileBytesData", fileBytesData);
		checkSecureToken(securityToken);

		return true;
	}

	public static boolean checkOverrideUploadFile(String fileId,
			String localFileName, String fileSuffix, String userId,
			String token, String channel) throws FsException {
		checkString("fileId", fileId);
		checkString("localFileName", localFileName);
		checkSecureToken(userId, token, channel);

		return true;
	}

	public static boolean checkOverrideUploadFile(String fileId,
			String localFileName, String fileSuffix, SecurityToken securityToken)
			throws FsException {
		checkString("fileId", fileId);
		checkString("localFileName", localFileName);
		checkSecureToken(securityToken);

		return true;
	}

	public static boolean checkOverrideUploadFile(String fileId,
			byte[] fileBytesData, String fileSuffix, String userId,
			String token, String channel) throws FsException {
		checkString("fileId", fileId);
		checkBytes("fileBytesData", fileBytesData);
		checkSecureToken(userId, token, channel);

		return true;
	}

	public static boolean checkOverrideUploadFile(String fileId,
			byte[] fileBytesData, String fileSuffix, SecurityToken securityToken)
			throws FsException {
		checkString("fileId", fileId);
		checkBytes("fileBytesData", fileBytesData);
		checkSecureToken(securityToken);

		return true;
	}

	public static boolean checkUploadPublicFile(String localFileName,
			String fileSuffix, String userId, String token, String channel)
			throws FsException {
		checkString("localFileName", localFileName);
		checkSecureToken(userId, token, channel);

		return true;
	}

	public static boolean checkUploadPublicFile(String localFileName,
			String fileSuffix, SecurityToken securityToken) throws FsException {
		checkString("localFileName", localFileName);
		checkSecureToken(securityToken);

		return true;
	}

	public static boolean checkUploadPublicFile(byte[] fileBytesData,
			String fileSuffix, String userId, String token, String channel)
			throws FsException {
		checkBytes("fileBytesData", fileBytesData);
		checkSecureToken(userId, token, channel);

		return true;
	}

	public static boolean checkUploadPublicFile(byte[] fileBytesData,
			String fileSuffix, SecurityToken securityToken) throws FsException {
		checkBytes("fileBytesData", fileBytesData);
		checkSecureToken(securityToken);

		return true;
	}

	public static boolean checkUploadFileByBatch(String[] localFileNames,
			String[] fileSuffixes, FilePermission filePermission,
			String userId, String token, String channel) throws FsException {

		checkStringArray("localFileNames", localFileNames, -1);
		checkStringArray("fileSuffixes", fileSuffixes, localFileNames.length);
		checkSecureToken(userId, token, channel);

		return true;
	}

	public static boolean checkUploadFileByBatch(String[] localFileNames,
			String[] fileSuffixes, FilePermission filePermission,
			SecurityToken securityToken) throws FsException {

		checkStringArray("localFileNames", localFileNames, -1);
		checkStringArray("fileSuffixes", fileSuffixes, localFileNames.length);

		checkSecureToken(securityToken);

		return true;
	}

	public static boolean checkUploadFileByBatch(
			List<byte[]> fileBytesDataList, List<String> fileSuffixList,
			FilePermission filePermission, String userId, String token,
			String channel) throws FsException {

		if ((null == fileBytesDataList) || (0 >= fileBytesDataList.size())) {
			throw new FsParameterException(
					"The parameter 'fileBytesDataList' can't be null !");
		}

		int maxLen = fileBytesDataList.size();

		if ((null != fileSuffixList) && (maxLen < fileSuffixList.size())) {
			throw new FsParameterException(
					"The size of parameter 'fileSuffixList' can't exceed"
							+ maxLen + "!");
		}

		checkSecureToken(userId, token, channel);

		return true;
	}

	public static boolean checkUploadFileByBatch(
			List<byte[]> fileBytesDataList, List<String> fileSuffixList,
			FilePermission filePermission, SecurityToken securityToken)
			throws FsException {

		if ((null == fileBytesDataList) || (0 >= fileBytesDataList.size())) {
			throw new FsParameterException(
					"The parameter 'fileBytesDataList' can't be null !");
		}

		int maxLen = fileBytesDataList.size();

		if ((null != fileSuffixList) && (maxLen < fileSuffixList.size())) {
			throw new FsParameterException(
					"The size of parameter 'fileSuffixList' can't exceed"
							+ maxLen + "!");
		}

		checkSecureToken(securityToken);

		return true;
	}

	/**
	 * 文件下载：下载指定的公共权限文件
	 * 
	 * @param fileId
	 *            文件id
	 * @param localFileName
	 *            下载到本地存储的路径和文件名称, 如果为空, 则:默认保存路径为"./",文件名称为fileId
	 * @return
	 */
	public static boolean checkDownloadPublicFile(String fileId,
			String localFileName) throws FsException {
		checkString("fileId", fileId);
		checkString("localFileName", localFileName);

		return true;
	}

	/**
	 * 文件下载：下载指定的公共权限文件
	 * 
	 * @param fileId
	 *            文件id
	 * @param output
	 *            文件输出流
	 * @return
	 */
	public static boolean checkDownloadPublicFile(String fileId,
			OutputStream output) throws FsException {
		checkString("fileId", fileId);

		if (null == output) {
			throw new FsParameterException(
					"The parameter 'output' can't be null !");
		}

		return true;
	}

	/**
	 * 文件下载：下载带访问权限的文件[公共权限文件、受限权限文件、私有权限文件]
	 * 
	 * @param fileId
	 *            文件id
	 * @param localFileName
	 *            下载到本地存储的路径和文件名称, 如果为空, 则:默认保存路径为"./",文件名称为fileId
	 * @param userId
	 *            用户id
	 * @param token
	 *            安全令牌
	 * @param channel
	 *            渠道
	 * @return
	 */
	public static boolean checkDownloadFile(String fileId,
			String localFileName, String userId, String token, String channel)
			throws FsException {
		checkString("fileId", fileId);
		checkString("localFileName", localFileName);
		// checkSecureToken(userId, token, channel); 在服务端校验

		return true;
	}

	/**
	 * 文件下载：下载带访问权限的文件[公共权限文件、受限权限文件、私有权限文件]
	 * 
	 * @param fileId
	 *            文件id
	 * @param output
	 *            文件输出流
	 * @param userId
	 *            用户id
	 * @param token
	 *            安全令牌
	 * @param channel
	 *            渠道
	 * @return
	 */
	public static boolean checkDownloadFile(String fileId, OutputStream output,
			String userId, String token, String channel) throws FsException {
		checkString("fileId", fileId);

		if (null == output) {
			throw new FsParameterException(
					"The parameter 'output' can't be null !");
		}

		// checkSecureToken(userId, token, channel); 在服务端校验

		return true;
	}

	/**
	 * 文件删除：删除自己'实名'上传的文件, 匿名方式上传的无权删除
	 * 
	 * @param fileId
	 *            文件id
	 * @param userId
	 *            用户id
	 * @param token
	 *            安全令牌
	 * @param channel
	 *            渠道
	 * @return
	 */
	public static boolean checkDeleteFile(String fileId, String userId,
			String token, String channel) throws FsException {
		checkString("fileId", fileId);
		checkSecureToken(userId, token, channel);

		return true;
	}

	/**
	 * 文件批量删除：删除自己'实名'上传的文件, 匿名方式上传的无权删除
	 * 
	 * @param fileIds
	 *            文件id
	 * @param userId
	 *            用户id
	 * @param token
	 *            安全令牌
	 * @param channel
	 *            渠道
	 * @return
	 */
	public static boolean checkDeleteFile(String[] fileIds, String userId,
			String token, String channel) throws FsException {
		if (StringUtils.isStrArrayEmpty(fileIds)) {
			throw new FsParameterException(
					"You must specify a list of files to be deleted with the parameter 'fileIds'!");
		}

		checkSecureToken(userId, token, channel);

		return true;
	}

	/**
	 * 查看文件信息(只有文件属主才可以查看文件信息)
	 * 
	 * @param fileId
	 *            文件id
	 * @param userId
	 *            用户id
	 * @param token
	 *            安全令牌
	 * @param channel
	 *            渠道
	 * @return
	 */
	public static boolean checkViewFileInfo(String fileId, String userId,
			String token, String channel) throws FsException {
		checkString("fileId", fileId);
		// checkSecureToken(userId, token, channel); 因为游客是可以查看匿名上传文件信息的

		return true;
	}

	/**
	 * 设置文件权限, 匿名上传的公共文件的权限不能修改
	 * 
	 * @param fileId
	 *            文件id
	 * @param filePermission
	 *            文件权限
	 * @param userId
	 *            用户id
	 * @param token
	 *            安全令牌
	 * @param channel
	 *            渠道
	 * @return
	 */
	public static boolean checkSetFilePermission(String fileId,
			FilePermission filePermission, String userId, String token,
			String channel) throws FsException {
		checkString("fileId", fileId);
		checkSecureToken(userId, token, channel);

		checkFilePermission(filePermission, true);

		return true;
	}

	/**
	 * 校验字符串
	 * 
	 * @param paramName
	 * @param paramValue
	 * @return
	 * @throws FsParameterException
	 */
	private static boolean checkString(String paramName, String paramValue)
			throws FsParameterException {
		if (StringUtils.isEmpty(paramValue)) {
			throw new FsParameterException("The parameter '" + paramName
					+ "' can't be null !");
		}

		return true;
	}

	/**
	 * 校验安全令牌
	 * 
	 * @param userId
	 * @param token
	 * @param channel
	 * @return
	 * @throws FsParameterException
	 */
	private static boolean checkSecureToken(String userId, String token,
			String channel) throws FsParameterException {

		if (StringUtils.isEmpty(userId)) {
			throw new FsParameterException("The user id can't be null!");
		}

		if (StringUtils.isEmpty(token)) {
			throw new FsParameterException(
					"The secure token can't be null! Please provide a secure token in advance!");
		}

		if (StringUtils.isEmpty(channel)) {
			throw new FsParameterException("The channel can't be null!");
		}

		return true;
	}

	/**
	 * 校验安全令牌对象
	 * 
	 * @param securityToken
	 *            安全令牌对象
	 * @return
	 * @throws FsException
	 */
	public static boolean checkSecureToken(SecurityToken securityToken)
			throws FsException {
		if (null == securityToken) {
			throw new FsParameterException(
					"The parameter 'securityToken' can't be null !");
		}

		String userId = securityToken.getUserId();

		if (StringUtils.isEmpty(userId)) {
			throw new IllegalArgumentException(
					"The attribute 'userId' of securityToken can't be null! ");
		}

		String token = securityToken.getToken();

		if (StringUtils.isEmpty(token)) {
			throw new IllegalArgumentException(
					"The attribute 'token' of securityToken can't be null! ");
		}

		String channel = securityToken.getChannel();

		if (StringUtils.isEmpty(channel)) {
			throw new IllegalArgumentException(
					"The attribute 'channel' of securityToken can't be null! ");
		}

		return true;
	}

	/**
	 * 校验字符串
	 * 
	 * @param paramName
	 * @param paramValue
	 * @return
	 * @throws FsParameterException
	 */
	private static boolean checkStringArray(String paramName,
			String[] paramValue, int maxLen) throws FsParameterException {
		if (StringUtils.isEmpty(paramValue) || (0 >= paramValue.length)) {
			throw new FsParameterException("The parameter '" + paramName
					+ "' can't be null !");
		}

		if ((0 < maxLen) && (maxLen < paramValue.length)) {
			throw new FsParameterException("Length of the array parameter '"
					+ paramName + "' can't exceed " + maxLen
					+ " ! Your length is:" + paramValue.length);
		}

		return true;
	}

	/**
	 * checkFilePermission
	 * 
	 * @param filePermission
	 * @param isMust
	 * @return
	 * @throws FsParameterException
	 */
	private static boolean checkFilePermission(FilePermission filePermission,
			boolean isMust) throws FsParameterException {
		if (isMust && (null == filePermission)) {
			throw new FsParameterException(
					"The parameter 'filePermission' can't be null !");
		}

		return true;
	}

	/**
	 * checkBytes
	 * 
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 * @throws FsParameterException
	 */
	private static boolean checkBytes(String fieldName, byte[] fieldValue)
			throws FsParameterException {
		if ((null == fieldValue) || (0 >= fieldValue.length)) {
			throw new FsParameterException("The parameter '" + fieldName
					+ "' can't be null !");
		}

		return true;
	}
}
