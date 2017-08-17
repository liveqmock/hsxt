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

import com.gy.hsi.fs.common.exception.FsException;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-client
 * 
 *  Package Name    : com.gy.hsi.fs.client.service
 * 
 *  File Name       : IFsInnerShareClientService.java
 * 
 *  Creation Date   : 2015年5月28日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件系统代理客户端service服务接口, 仅用于子系统间文件共享, 如：对账文件、邮件附件中转, 与操作用户无关。
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface IFsInnerShareClientService {
	/**
	 * 文件上传: 上传子系统间共享的文件
	 * 
	 * @param localFileNames
	 *            本地存放的文件路径和名称
	 * @param fileSuffixes
	 *            文件后缀(可选输入)
	 * @param expiredHours
	 *            有效时长(单位：小时)
	 * @return 文件id列表
	 * @throws FsException
	 */
	public List<String> uploadInnerShareFile(String[] localFileNames,
			String[] fileSuffixes, int expiredHours) throws FsException;

	/**
	 * 文件上传: 上传子系统间共享的文件
	 * 
	 * @param fileBytesDataList
	 *            文件内容字节数据 文件内容字节数据
	 * @param fileSuffixList
	 *            文件后缀(可选输入)
	 * @param expiredHours
	 *            有效时长(单位：小时)
	 * @return 文件id列表
	 * @throws FsException
	 */
	public List<String> uploadInnerShareFile(List<byte[]> fileBytesDataList,
			List<String> fileSuffixList, int expiredHours) throws FsException;

	/**
	 * 文件下载：下载子系统间共享的文件
	 * 
	 * @param fileId
	 *            文件id
	 * @param localFileName
	 *            下载到本地存储的路径和文件名称, 如果为空, 则:默认保存路径为"./",文件名称为fileId
	 * @return 是否操作成功
	 * @throws FsException
	 */
	public boolean downloadInnerShareFile(String fileId, String localFileName)
			throws FsException;

	/**
	 * 文件下载：下载子系统间共享的文件
	 * 
	 * @param fileId
	 *            文件id
	 * @param output
	 *            文件输出流
	 * @return 是否操作成功
	 * @throws FsException
	 */
	public boolean downloadInnerShareFile(String fileId, OutputStream output)
			throws FsException;

	/**
	 * 文件删除：删除子系统间共享的文件
	 * 
	 * @param fileIds
	 *            文件id列表
	 * @return 是否操作成功
	 * @throws FsException
	 */
	public boolean deleteInnerShareFile(String[] fileIds) throws FsException;
}
