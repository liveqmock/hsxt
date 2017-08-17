/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.interfaces;

import java.util.List;

import com.gy.hsxt.lcs.bean.Version;


/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.interfaces
 * 
 *  File Name       : IVersionService.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 版本号接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public interface IVersionService {

	/**
	 * 查询版本
	 * 
	 * @param versionCode
	 * @return
	 */
	public Version queryVersion(String versionCode);

	/**
	 * 添加或更新版本
	 * 
	 * @param versionCode
	 * @return
	 */
	public long addOrUpdateVersion(String versionCode);
	
	/**
	 * 获得所有版本实体
	 * @return
	 */
	public List<Version> findAll();
	
	/**
	 * 更新版本号
	 * @param version
	 */
	public void addOrUpdateVersion4SyncData(Version version);

}
