/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.gy.hsxt.lcs.bean.Version;
/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.mapper
 * 
 *  File Name       : VersionMapper.java
 * 
 *  Creation Date   : 2015-7-1
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 版本号mapper接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public interface VersionMapper {
	
    /**
     * 查询指定基础数据版本信息
     * @param versionCode
     * @return
     */
	public Version queryVersion(@Param("versionCode")String versionCode);
	
	/**
	 * 添加基础数据版本信息
	 * @param version
	 * @return
	 */
	public boolean addVersion(Version version);

	/**
	 * 更新基础数据版本信息
	 * @param version
	 * @return
	 */
	public boolean updateVersion(Version version);

	/**
	 * 查询全部基础数据版本信息
	 * @return
	 */
	public List<Version> findAll();
}
