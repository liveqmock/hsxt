/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service;

import java.util.List;

import com.gy.hsxt.pg.mapper.vo.TPgChinapayDayBalance;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service
 * 
 *  File Name       : IChinapayDayBalanceFileService.java
 * 
 *  Creation Date   : 2015年12月25日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联每日对账文件操作接口
 * 
 * 
 *  History         : None
 * 
 * </PRE>
 ***************************************************************************/
public interface IChinapayDayBalanceFileService {

	/**
	 * 下载银联的对账文件
	 * 
	 * @param dayBalance
	 * @param isRetryWhenFailed
	 *            下载失败是否重试
	 * @return
	 * @throws Exception
	 */
	public boolean dowloadDayBalanceFile(TPgChinapayDayBalance dayBalance,
			boolean isRetryWhenFailed) throws Exception;

	/**
	 * 银联的对账文件验签
	 * 
	 * @param dayBalance
	 * @return
	 * @throws Exception
	 */
	public boolean verifyDayBalanceFile(TPgChinapayDayBalance dayBalance)
			throws Exception;

	/**
	 * 将银联通知的对账文件基本信息保存到数据库中
	 * 
	 * @param downloadUrl
	 * @throws Exception
	 * @return
	 */
	public TPgChinapayDayBalance insertDayBalanceFileInfo2DB(String downloadUrl)
			throws Exception;

	/**
	 * 修改对账文件下载信息
	 * 
	 * @param fileName
	 * @param downloadStaus
	 */
	public void updateDayBalanceFileInfo(String fileName, int downloadStatus);

	/**
	 * 查询每日对账文件保存的路径
	 * 
	 * @param balanceDate
	 * @return
	 */
	public List<TPgChinapayDayBalance> queryDayBalanceFileInfo(
			String balanceDate);

	/**
	 * 根据文件名称查询每日对账文件信息
	 * 
	 * @param fileName
	 * @return
	 */
	public TPgChinapayDayBalance queryDayBalanceFileInfoByFileName(
			String fileName);
}
