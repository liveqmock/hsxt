/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.interfaces;

import java.util.List;

import com.gy.hsxt.gpf.gcs.bean.Version;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.interfaces
 * 
 *  File Name       : VersionService.java
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
	 * 添加版本
	 * 
	 * @param version
	 * @return
	 */
	public boolean addVersion(Version version);

	/**
	 * 更新版本
	 * 
	 * @param version
	 * @return
	 */
	public boolean updateVersion(Version version);

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

	public void addOrUpdateVersion4SyncData(Version version);
}
