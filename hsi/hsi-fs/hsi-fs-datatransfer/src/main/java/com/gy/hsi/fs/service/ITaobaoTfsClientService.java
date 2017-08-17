/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.service;

import java.io.OutputStream;

import com.taobao.common.tfs.packet.FileInfo;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.service
 * 
 *  File Name       : ITaobaoTfsClientService.java
 * 
 *  Creation Date   : 2015年5月21日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 调用淘宝TFS客户端操作文件的service接口
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface ITaobaoTfsClientService {
	/**
	 * 保存一个文件到 tfs中，文件小于2M
	 *
	 * @param localFileName
	 *            本地文件名
	 * @param tfsSuffix
	 *            文件后缀
	 * @return 保存成功，返回成功后 的tfs文件名，失败返回null
	 */
	public String saveFile(String localFileName, String tfsSuffix);

	/**
	 * 保存一个文件字节流到 tfs中，文件小于2M
	 *
	 * @param data
	 *            文件字节流
	 * @param tfsSuffix
	 *            文件后缀
	 * @return 保存成功，返回成功后 的tfs文件名，失败返回null
	 */
	public String saveFile(byte[] data, String tfsSuffix);

	/**
	 * 保存一个大文件到tfs(文件大于2M)，成功返回tfs文件名(L开头），失败返回null
	 * tfs文件写大文件，为了断点续传，必须传入一个key参数，
	 * 来标识此次大文件的写。一次写失败后，再次传入相同的key写，tfsclient会根据key找到前一次已经写完成的部分重用
	 * 默认使用localFileName作为key
	 *
	 * @param localFileName
	 *            本地文件名
	 * @param tfsFileName
	 *            保存到tfs中的文件名 (目前都为null，不支持自定义文件名）
	 * @param tfsSuffix
	 *            文件后缀
	 * @return 保存成功，返回成功后 的tfs文件名，失败返回null
	 */
	public String saveLargeFile(String localFileName, String tfsSuffix);

	/**
	 * 保存一个字节流data到tfs(文件大于2M)，成功返回tfs文件名，失败返回null,
	 * tfs文件写大文件，为了断点续传，必须传入一个key参数，
	 * 来标识此次大文件的写。一次写失败后，再次传入相同的key写，tfsclient会根据key找到前一次已经写完成的部分重用
	 * 
	 * @param data
	 * @param tfsSuffix
	 * @param key
	 *            key name
	 * @return
	 */
	public String saveLargeFile(byte[] data, String tfsSuffix, String key);

	/**
	 * 删除一个文件
	 *
	 * @param tfsFileName
	 *            文件名
	 * @param tfsSuffix
	 *            文件后缀
	 * @return true if delete successully, or false if fail
	 */
	public boolean deleteFile(String tfsFileName, String tfsSuffix);

	/**
	 * 从tfs 获取文件到本地
	 *
	 * @param tfsFileName
	 *            需要读取的tfs文件名
	 * @param tfsSuffix
	 *            需要读取的文件名后缀，需要和存入时后缀相同。
	 * @param localFileName
	 *            本地文件名
	 * @return 读操作成功返回true，读操作失败返回false
	 */
	public boolean fetchFile(String tfsFileName, String tfsSuffix,
			String localFileName);

	/**
	 * 从tfs 获取文件到本地，数据存到输出流
	 * 
	 * @param tfsFileName
	 * @param tfsSuffix
	 * @param output
	 *            数据流
	 * @return 读操作成功返回true，读操作失败返回false
	 */
	public boolean fetchFile(String tfsFileName, String tfsSuffix,
			OutputStream output);

	/**
	 * stat一个tfs文件 文件的状态有0（正常）， 1（删除）， 4（隐藏）
	 * 
	 * @param tfsFileName
	 *            需要读取的tfs文件名
	 * @param tfsSuffix
	 *            需要读取的文件名后缀，需要和存入时后缀相同
	 * @return 操作成功返回FileInfo，操作失败返回null
	 */
	public FileInfo statFile(String tfsFileName, String tfsSuffix);
}
