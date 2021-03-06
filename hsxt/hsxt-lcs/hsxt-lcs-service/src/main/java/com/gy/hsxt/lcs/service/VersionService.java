/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.lcs.bean.Version;
import com.gy.hsxt.lcs.interfaces.IVersionService;
import com.gy.hsxt.lcs.mapper.VersionMapper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.service
 * 
 *  File Name       : VersionService.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 版本号接口实现
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Service("versionService")
public class VersionService implements IVersionService {

	@Autowired
	private VersionMapper versionMapper;

	@Override
	public Version queryVersion(String versionCode) {
		return versionMapper.queryVersion(versionCode);
	}

	@Override
	public long addOrUpdateVersion(String versionCode) {
		long versionLong = 1;

		Version version = this.queryVersion(versionCode);
		if (null != version) {
			versionLong = version.getVersion() + 1;

			version.setVersion(version.getVersion() + 1);
			version.setNotifyFlag(false);

			versionMapper.updateVersion(version);
		} else {
			Version newVersion = new Version(versionCode,new Long(1),false);
			versionLong = 1;

			versionMapper.addVersion(newVersion);
		}
		return versionLong;
	}

	@Override
	public List<Version> findAll() {
		return versionMapper.findAll();
	}
	
	@Override
	public void addOrUpdateVersion4SyncData(Version version) {
		Version versionDB = this.queryVersion(version.getVersionCode());
		if (null != versionDB) {
			versionMapper.updateVersion(version);
		} else {
			versionMapper.addVersion(version);
		}
	}
}
