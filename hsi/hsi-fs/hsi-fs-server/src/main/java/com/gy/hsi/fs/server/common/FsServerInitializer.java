/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.server.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsPermissionRule;
import com.gy.hsi.fs.server.common.utils.FileCacheHelper;
import com.gy.hsi.fs.server.service.IPermissionRuleService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.common
 * 
 *  File Name       : FsServerMgr.java
 * 
 *  Creation Date   : 2015年7月20日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件系统server数据初始化类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Component(value = "fsServerInitializer")
public class FsServerInitializer {
	@Resource
	private IPermissionRuleService permissionRuleService;

	// 权限规则列表
	private Map<String, String> allPermissionRuleMap = null;

	private final static Object LOCK = new Object();

	public FsServerInitializer() {
		this.init();
	}

	/**
	 * 执行初始化
	 */
	private void init() {
		// 创建缓存目录
		FileCacheHelper.createCacheDirectory();

		// 初始化权限规则矩阵
		new Thread() {
			public void run() {
				while (true) {
					if (null != permissionRuleService) {
						FsServerInitializer.this.initPermissionRuleDatas();
						break;
					}

					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
					}
				}
			};
		}.start();
	}

	/**
	 * 初始化权限规则数据
	 * 
	 * @return
	 */
	private boolean initPermissionRuleDatas() {
		synchronized (LOCK) {
			if (null == allPermissionRuleMap) {
				allPermissionRuleMap = new HashMap<String, String>(20);

				List<TFsPermissionRule> allRuleList = permissionRuleService
						.queryAllRule();

				for (TFsPermissionRule rule : allRuleList) {
					allPermissionRuleMap.put(
							rule.getRuleMatrixX().replace("|", ""),
							rule.getRuleMatrixY());
				}
			}
		}

		return true;
	}

	/**
	 * 根据权限规则key获取权限矩阵值
	 * 
	 * @param key
	 * @return
	 */
	public String getPerssionRuleData(String key) {
		if (null == allPermissionRuleMap) {
			initPermissionRuleDatas();
		}

		return allPermissionRuleMap.get(key);
	}
}
