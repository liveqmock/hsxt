/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.service.impl;

import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.fs.common.utils.StringUtils;
import com.gy.hsi.fs.service.ITaobaoTfsClientService;
import com.taobao.common.tfs.DefaultTfsManager;
import com.taobao.common.tfs.packet.FileInfo;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.service.impl
 * 
 *  File Name       : TaobaoTfsClientServiceImpl.java
 * 
 *  Creation Date   : 2015年5月21日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 调用淘宝TFS客户端操作文件的service接口实现类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/

@Service(value = "taobaoTfsClientService")
public class TaobaoTfsClientServiceImpl implements ITaobaoTfsClientService {
	/** 记录日志对象 **/
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * tfs 管理器
	 */
	@Autowired
	private DefaultTfsManager tfsManager;

	/**
	 * 保存一个文件到 tfs中，文件小于2M
	 *
	 * @param localFileName
	 *            本地文件名
	 * @param tfsSuffix
	 *            文件后缀
	 * @return 保存成功，返回成功后 的tfs文件名，失败返回null
	 */
	public String saveFile(String localFileName, String tfsSuffix) {
		if (StringUtils.isEmpty(localFileName)) {
			throw new IllegalArgumentException("保存文件时参数localFileName不能为空！");
		}

		return tfsManager.saveFile(localFileName, null, tfsSuffix);
	}

	/**
	 * 保存一个文件字节流到 tfs中，文件小于2M
	 *
	 * @param data
	 *            文件字节流
	 * @param tfsSuffix
	 *            文件后缀
	 * @return 保存成功，返回成功后 的tfs文件名，失败返回null
	 */
	public String saveFile(byte[] data, String tfsSuffix) {
		if ((null == data) /* || StringUtils.isEmpty(tfsSuffix) */) {
			throw new IllegalArgumentException("保存文件字节流入参不能为空! data="
					+ String.valueOf(data) + ",tfsSuffix=" + tfsSuffix);
		}

		return tfsManager.saveFile(data, null, tfsSuffix);
	}

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
	public String saveLargeFile(String localFileName, String tfsSuffix) {
		if (StringUtils.isEmpty(localFileName)) {
			throw new IllegalArgumentException("保存大文件时参数 localFileName不能为空！");
		}

		return tfsManager.saveLargeFile(localFileName, null, tfsSuffix);
	}

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
	public String saveLargeFile(byte[] data, String tfsSuffix, String key) {
		if (null == data) {
			throw new IllegalArgumentException("保存大文件字节时参数data不能为空！");
		}

		return tfsManager.saveLargeFile(data, null, tfsSuffix, key);
	}

	/**
	 * 删除一个文件
	 *
	 * @param tfsFileName
	 *            文件名
	 * @param tfsSuffix
	 *            文件后缀
	 * @return true if delete successully, or false if fail
	 */
	public boolean deleteFile(String tfsFileName, String tfsSuffix) {
		if (StringUtils.isEmpty(tfsFileName)) {
			throw new IllegalArgumentException("删除文件名时参数tfsFileName不能为空！");
		}

		try {
			return tfsManager.unlinkFile(tfsFileName, tfsSuffix);
		} catch (Exception e) {
			logger.error("删除文件时发生异常：", e);
		}

		return false;
	}

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
			String localFileName) {
		try {
			return tfsManager.fetchFile(tfsFileName, tfsSuffix, localFileName);
		} catch (Exception e) {
			logger.error("获取文件时发生异常：", e);
		}

		return false;
	}

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
			OutputStream output) {
		if (StringUtils.isEmpty(tfsFileName) || (null == output)) {
			throw new IllegalArgumentException("获取文件入参不能为空! tfsFileName="
					+ tfsFileName + ",output=" + output);
		}

		try {
			return tfsManager.fetchFile(tfsFileName, null, output);
		} catch (Exception e) {
			logger.error("向tfs获取文件时发生异常：", e);
		}

		return false;
	}

	/**
	 * stat一个tfs文件 文件的状态有0（正常）， 1（删除）， 4（隐藏）
	 * 
	 * @param tfsFileName
	 *            需要读取的tfs文件名
	 * @param tfsSuffix
	 *            需要读取的文件名后缀，需要和存入时后缀相同
	 * @return 操作成功返回FileInfo，操作失败返回null
	 */
	public FileInfo statFile(String tfsFileName, String tfsSuffix) {
		if (StringUtils.isEmpty(tfsFileName)) {
			throw new IllegalArgumentException("stat文件入参不能为空! tfsFileName="
					+ tfsFileName);
		}

		return tfsManager.statFile(tfsFileName, tfsSuffix);
	}

	public void setTfsManager(DefaultTfsManager tfsManager) {
		this.tfsManager = tfsManager;
	}

	public DefaultTfsManager getTfsManager() {
		return this.tfsManager;
	}
}
