/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.client.service;

import java.io.OutputStream;
import java.util.List;

import com.gy.hsi.fs.common.beans.FileMetaInfo;
import com.gy.hsi.fs.common.beans.SecurityToken;
import com.gy.hsi.fs.common.constant.FsApiConstant.FilePermission;
import com.gy.hsi.fs.common.exception.FsException;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-client
 * 
 *  Package Name    : com.gy.hsi.fs.client.service
 * 
 *  File Name       : IFsClientService.java
 * 
 *  Creation Date   : 2015年5月28日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件系统客户端service服务接口
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface IFsClientService {
	/**
	 * 文件上传: 上传带访问权限的文件[公共文件、受限文件、私有文件]
	 * 
	 * @param localFileName
	 *            本地存放的文件路径和名称
	 * @param fileSuffix
	 *            文件后缀(可选输入)
	 * @param filePermission
	 *            文件权限(可选输入, 如果为null, 默认为FilePermission.PROTECTED_READ)
	 * @param userId
	 *            用户id
	 * @param token
	 *            安全令牌
	 * @param channel
	 *            渠道
	 * @return 文件id
	 * @throws FsException
	 */
	public String uploadFile(String localFileName, String fileSuffix,
			FilePermission filePermission, String userId, String token,
			String channel) throws FsException;

	/**
	 * 文件上传: 上传带访问权限的文件[公共文件、受限文件、私有文件]
	 * 
	 * @param localFileName
	 *            本地存放的文件路径和名称
	 * @param fileSuffix
	 *            文件后缀(可选输入)
	 * @param filePermission
	 *            文件权限(可选输入, 如果为null, 默认为FilePermission.PROTECTED_READ)
	 * @param securityToken
	 *            安全令牌
	 * @return 文件id
	 * @throws FsException
	 */
	public String uploadFile(String localFileName, String fileSuffix,
			FilePermission filePermission, SecurityToken securityToken)
			throws FsException;

	/**
	 * 文件上传: 上传带访问权限的文件[公共文件、受限文件、私有文件]
	 * 
	 * @param fileBytesData
	 *            文件内容字节数据 文件内容字节数据
	 * @param fileSuffix
	 *            文件后缀(可选输入)
	 * @param filePermission
	 *            文件权限(可选输入, 如果为null, 默认为FilePermission.PROTECTED_READ)
	 * @param userId
	 *            用户id
	 * @param token
	 *            安全令牌
	 * @param channel
	 *            渠道
	 * @return 文件id
	 * @throws FsException
	 */
	public String uploadFile(byte[] fileBytesData, String fileSuffix,
			FilePermission filePermission, String userId, String token,
			String channel) throws FsException;

	/**
	 * 文件上传: 上传带访问权限的文件[公共文件、受限文件、私有文件]
	 * 
	 * @param fileBytesData
	 *            文件内容字节数据 文件内容字节数据
	 * @param fileSuffix
	 *            文件后缀(可选输入)
	 * @param filePermission
	 *            文件权限(可选输入, 如果为null, 默认为FilePermission.PROTECTED_READ)
	 * @param securityToken
	 *            安全令牌
	 * @return 文件id
	 * @throws FsException
	 */
	public String uploadFile(byte[] fileBytesData, String fileSuffix,
			FilePermission filePermission, SecurityToken securityToken)
			throws FsException;

	/**
	 * 覆盖文件上传: 上传带访问权限的文件[公共文件、受限文件、私有文件]
	 * 
	 * @param fileId
	 *            原有的文件id
	 * @param localFileName
	 *            本地存放的文件路径和名称
	 * @param fileSuffix
	 *            文件后缀(可选输入)
	 * @param userId
	 *            用户id
	 * @param token
	 *            安全令牌
	 * @param channel
	 *            渠道
	 * @return 文件id
	 * @throws FsException
	 */
	public boolean overrideUploadFile(String fileId, String localFileName,
			String fileSuffix, String userId, String token, String channel)
			throws FsException;

	/**
	 * 覆盖文件上传: 上传带访问权限的文件[公共文件、受限文件、私有文件]
	 * 
	 * @param fileId
	 *            原有的文件id
	 * @param localFileName
	 *            本地存放的文件路径和名称
	 * @param fileSuffix
	 *            文件后缀(可选输入)
	 * @param securityToken
	 *            安全令牌
	 * @return 是否操作成功
	 * @throws FsException
	 */
	public boolean overrideUploadFile(String fileId, String localFileName,
			String fileSuffix, SecurityToken securityToken) throws FsException;

	/**
	 * 覆盖文件上传: 上传带访问权限的文件[公共文件、受限文件、私有文件]
	 * 
	 * @param fileId
	 *            原有的文件id
	 * @param fileBytesData
	 *            文件内容字节数据 文件内容字节数据
	 * @param fileSuffix
	 *            文件后缀(可选输入)
	 * @param userId
	 *            用户id
	 * @param token
	 *            安全令牌
	 * @param channel
	 *            渠道
	 * @return 是否操作成功
	 * @throws FsException
	 */
	public boolean overrideUploadFile(String fileId, byte[] fileBytesData,
			String fileSuffix, String userId, String token, String channel)
			throws FsException;

	/**
	 * 覆盖文件上传: 上传带访问权限的文件[公共文件、受限文件、私有文件]
	 * 
	 * @param fileId
	 *            原有的文件id
	 * @param fileBytesData
	 *            文件内容字节数据 文件内容字节数据
	 * @param fileSuffix
	 *            文件后缀(可选输入)
	 * @param securityToken
	 *            安全令牌
	 * @return 是否操作成功
	 * @throws FsException
	 */
	public boolean overrideUploadFile(String fileId, byte[] fileBytesData,
			String fileSuffix, SecurityToken securityToken) throws FsException;

	/**
	 * 文件上传: 上传公共文件, 即:上传任何人都可以访问的文件
	 * 
	 * @param localFileName
	 *            本地存放的文件路径和名称
	 * @param fileSuffix
	 *            文件后缀(可选输入)
	 * @param userId
	 *            用户id
	 * @param token
	 *            安全令牌
	 * @param channel
	 *            渠道
	 * @param byAnonymousMode
	 *            是否通过匿名方式上传(如果为true, 则是匿名方式上传, 上传的文件将没有文件属主, 此文件权限将永久是公共可读,
	 *            且权限不能修改; 如果为false, 则是实名上传, 文件属主就是 上传用户, 文件权限是公共可读,
	 *            文件属主可以修改该权限)
	 * @return 文件id
	 * @throws FsException
	 */
	public String uploadPublicFile(String localFileName, String fileSuffix,
			String userId, String token, String channel, boolean byAnonymousMode)
			throws FsException;

	/**
	 * 文件上传: 上传公共文件, 即:上传任何人都可以访问的文件
	 * 
	 * @param localFileName
	 *            本地存放的文件路径和名称
	 * @param fileSuffix
	 *            文件后缀(可选输入)
	 * @param securityToken
	 *            安全令牌
	 * @param byAnonymousMode
	 *            是否通过匿名方式上传(如果为true, 则是匿名方式上传, 上传的文件将没有文件属主, 此文件权限将永久是公共可读,
	 *            且权限不能修改; 如果为false, 则是实名上传, 文件属主就是 上传用户, 文件权限是公共可读,
	 *            文件属主可以修改该权限)
	 * @return 文件id
	 * @throws FsException
	 */
	public String uploadPublicFile(String localFileName, String fileSuffix,
			SecurityToken securityToken, boolean byAnonymousMode)
			throws FsException;

	/**
	 * 公共文件上传: 上传公共文件, 即:上传任何人都可以访问的文件
	 * 
	 * @param fileBytesData
	 *            文件内容字节数据
	 * @param fileSuffix
	 *            文件后缀(可选输入)
	 * @param userId
	 *            用户id
	 * @param token
	 *            安全令牌
	 * @param channel
	 *            渠道
	 * @param byAnonymousMode
	 *            是否通过匿名方式上传(如果为true, 则是匿名方式上传, 上传的文件将没有文件属主, 此文件权限将永久是公共可读,
	 *            且权限不能修改; 如果为false, 则是实名上传, 文件属主就是 上传用户, 文件权限是公共可读,
	 *            文件属主可以修改该权限)
	 * @return 文件id
	 * @throws FsException
	 */
	public String uploadPublicFile(byte[] fileBytesData, String fileSuffix,
			String userId, String token, String channel, boolean byAnonymousMode)
			throws FsException;

	/**
	 * 公共文件上传: 上传公共文件, 即:上传任何人都可以访问的文件
	 * 
	 * @param fileBytesData
	 *            文件内容字节数据
	 * @param fileSuffix
	 *            文件后缀(可选输入)
	 * @param securityToken
	 *            安全令牌
	 * @param byAnonymousMode
	 *            是否通过匿名方式上传(如果为true, 则是匿名方式上传, 上传的文件将没有文件属主, 此文件权限将永久是公共可读,
	 *            且权限不能修改; 如果为false, 则是实名上传, 文件属主就是 上传用户, 文件权限是公共可读,
	 *            文件属主可以修改该权限)
	 * @return 文件id
	 * @throws FsException
	 */
	public String uploadPublicFile(byte[] fileBytesData, String fileSuffix,
			SecurityToken securityToken, boolean byAnonymousMode)
			throws FsException;

	/**
	 * 批量文件上传: 上传带访问权限的文件[公共文件、受限文件、私有文件]
	 * 
	 * @param localFileNames
	 *            本地存放的文件路径和名称
	 * @param fileSuffix
	 *            文件后缀(可选输入)
	 * @param filePermission
	 *            文件权限(可选输入, 如果为null, 默认为FilePermission.PROTECTED_READ)
	 * @param userId
	 *            用户id
	 * @param token
	 *            安全令牌
	 * @param channel
	 *            渠道
	 * @return 文件id列表
	 * @throws FsException
	 */
	public List<String> uploadFileByBatch(String[] localFileNames,
			String[] fileSuffixes, FilePermission filePermission,
			String userId, String token, String channel) throws FsException;

	/**
	 * 批量文件上传: 上传带访问权限的文件[公共文件、受限文件、私有文件]
	 * 
	 * @param localFileNames
	 *            本地存放的文件路径和名称
	 * @param fileSuffixes
	 *            文件后缀(可选输入)
	 * @param filePermission
	 *            文件权限(可选输入, 如果为null, 默认为FilePermission.PROTECTED_READ)
	 * @param securityToken
	 *            安全令牌
	 * @return 文件id列表
	 * @throws FsException
	 */
	public List<String> uploadFileByBatch(String[] localFileNames,
			String[] fileSuffixes, FilePermission filePermission,
			SecurityToken securityToken) throws FsException;

	/**
	 * 批量文件上传: 上传带访问权限的文件[公共文件、受限文件、私有文件]
	 * 
	 * @param fileBytesDataList
	 *            文件内容字节数据 文件内容字节数据
	 * @param fileSuffixList
	 *            文件后缀(可选输入)
	 * @param filePermission
	 *            文件权限(可选输入, 如果为null, 默认为FilePermission.PROTECTED_READ)
	 * @param userId
	 *            用户id
	 * @param token
	 *            安全令牌
	 * @param channel
	 *            渠道
	 * @return 文件id列表
	 * @throws FsException
	 */
	public List<String> uploadFileByBatch(List<byte[]> fileBytesDataList,
			List<String> fileSuffixList, FilePermission filePermission,
			String userId, String token, String channel) throws FsException;

	/**
	 * 批量文件上传: 上传带访问权限的文件[公共文件、受限文件、私有文件]
	 * 
	 * @param fileBytesDataList
	 *            文件内容字节数据 文件内容字节数据
	 * @param fileSuffixList
	 *            文件后缀(可选输入)
	 * @param filePermission
	 *            文件权限(可选输入, 如果为null, 默认为FilePermission.PROTECTED_READ)
	 * @param securityToken
	 *            安全令牌
	 * @return 文件id列表
	 * @throws FsException
	 */
	public List<String> uploadFileByBatch(List<byte[]> fileBytesDataList,
			List<String> fileSuffixList, FilePermission filePermission,
			SecurityToken securityToken) throws FsException;

	/**
	 * 文件下载：下载指定的公共文件
	 * 
	 * @param fileId
	 *            文件id
	 * @param localFileName
	 *            下载到本地存储的路径和文件名称, 如果为空, 则:默认保存路径为"./",文件名称为fileId
	 * @return 是否操作成功
	 * @throws FsException
	 */
	public boolean downloadPublicFile(String fileId, String localFileName)
			throws FsException;

	/**
	 * 文件下载：下载指定的公共文件
	 * 
	 * @param fileId
	 *            文件id
	 * @param output
	 *            文件输出流
	 * @return 是否操作成功
	 * @throws FsException
	 */
	public boolean downloadPublicFile(String fileId, OutputStream output)
			throws FsException;

	/**
	 * 文件下载：下载带访问权限的文件[公共文件、受限文件、私有文件]
	 * 
	 * @param fileId
	 *            文件id
	 * @param localFileName
	 *            下载到本地存储的路径和文件名称, 如果为空, 则:默认保存路径为"./",文件名称为fileId
	 * @param userId
	 *            用户id(如果下载的文件是公共可读权限的, userId可以为null)
	 * @param token
	 *            安全令牌(如果下载的文件是公共可读权限的, token可以为null)
	 * @param channel
	 *            渠道
	 * @return 是否操作成功
	 * @throws FsException
	 */
	public boolean downloadFile(String fileId, String localFileName,
			String userId, String token, String channel) throws FsException;

	/**
	 * 文件下载：下载带访问权限的文件[公共文件、受限文件、私有文件]
	 * 
	 * @param fileId
	 *            文件id
	 * @param localFileName
	 *            下载到本地存储的路径和文件名称, 如果为空, 则:默认保存路径为"./",文件名称为fileId
	 * @param securityToken
	 *            安全令牌(如果下载的文件是公共可读权限的, 则securityToken可以为null, 否则必须输入)
	 * @return 是否操作成功
	 * @throws FsException
	 */
	public boolean downloadFile(String fileId, String localFileName,
			SecurityToken securityToken) throws FsException;

	/**
	 * 文件下载：下载带访问权限的文件[公共文件、受限文件、私有文件]
	 * 
	 * @param fileId
	 *            文件id
	 * @param output
	 *            文件输出流
	 * @param userId
	 *            用户id(如果下载的文件是公共可读权限的, userId可以为null)
	 * @param token
	 *            安全令牌(如果下载的文件是公共可读权限的, token可以为null)
	 * @param channel
	 *            渠道
	 * @return 是否操作成功
	 * @throws FsException
	 */
	public boolean downloadFile(String fileId, OutputStream output,
			String userId, String token, String channel) throws FsException;

	/**
	 * 文件下载：下载带访问权限的文件[公共文件、受限文件、私有文件]
	 * 
	 * @param fileId
	 *            文件id
	 * @param output
	 *            文件输出流
	 * @param securityToken
	 *            安全令牌(如果下载的文件是公共可读权限的, 则securityToken可以为null)
	 * @return 是否操作成功
	 * @throws FsException
	 */
	public boolean downloadFile(String fileId, OutputStream output,
			SecurityToken securityToken) throws FsException;

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
	 * @return 是否操作成功
	 * @throws FsException
	 */
	public boolean deleteFile(String fileId, String userId, String token,
			String channel) throws FsException;

	/**
	 * 文件删除：删除自己'实名'上传的文件, 匿名方式上传的无权删除
	 * 
	 * @param fileId
	 *            文件id
	 * @param securityToken
	 *            安全令牌
	 * @return 是否操作成功
	 * @throws FsException
	 */
	public boolean deleteFile(String fileId, SecurityToken securityToken)
			throws FsException;

	/**
	 * 文件批量删除：删除自己'实名'上传的文件, 匿名方式上传的无权删除
	 * 
	 * @param fileIds
	 *            要删除的文件id列表
	 * @param userId
	 *            用户id
	 * @param token
	 *            安全令牌
	 * @param channel
	 *            渠道
	 * @return 是否操作成功
	 * @throws FsException
	 */
	public boolean deleteFile(String[] fileIds, String userId, String token,
			String channel) throws FsException;

	/**
	 * 文件批量删除：删除自己'实名'上传的文件, 匿名方式上传的无权删除
	 * 
	 * @param fileIds
	 *            要删除的文件id列表
	 * @param securityToken
	 *            安全令牌
	 * @return 是否操作成功
	 * @throws FsException
	 */
	public boolean deleteFile(String[] fileIds, SecurityToken securityToken)
			throws FsException;

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
	 * @return 文件元数据对象
	 * @throws FsException
	 */
	public FileMetaInfo viewFileInfo(String fileId, String userId,
			String token, String channel) throws FsException;

	/**
	 * 查看文件信息(只有文件属主才可以查看文件信息)
	 * 
	 * @param fileId
	 *            文件id
	 * @param securityToken
	 *            安全令牌
	 * @return 文件元数据对象
	 * @throws FsException
	 */
	public FileMetaInfo viewFileInfo(String fileId, SecurityToken securityToken)
			throws FsException;

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
	 * @return 是否操作成功
	 * @throws FsException
	 */
	public boolean setFilePermission(String fileId,
			FilePermission filePermission, String userId, String token,
			String channel) throws FsException;

	/**
	 * 设置文件权限, 匿名上传的公共文件的权限不能修改
	 * 
	 * @param fileId
	 *            文件id
	 * @param filePermission
	 *            文件权限
	 * @param securityToken
	 *            安全令牌
	 * @return 是否操作成功
	 * @throws FsException
	 */
	public boolean setFilePermission(String fileId,
			FilePermission filePermission, SecurityToken securityToken)
			throws FsException;

	/**
	 * 判断是否为匿名上传的文件
	 * 
	 * @param fileId
	 * @return
	 */
	public boolean isAnonyUploadedFile(String fileId);
}
