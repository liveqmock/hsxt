/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service.impl.parent;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.pg.common.constant.ConfigConst.TcConfigConsts;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service.impl.parent
 * 
 *  File Name       : BaseAccountingFileService.java
 * 
 *  Creation Date   : 2016年2月23日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : PG对账文件生成的父类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public abstract class BaseAccountingFileService {

	protected final Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 删除银联对账文件
	 * 
	 * @param balanceDate
	 * @param fileName
	 * @param isStartWith
	 */
	protected void deleteFile(String balanceDate, String fileName,
			boolean isStartWith) {

		String tcNfsShareRootDir = getTcNfsShareRootDir(balanceDate);

		File dir = new File(tcNfsShareRootDir);

		// 判断目标文件所在的目录是否存在
		if (!dir.exists() && !dir.mkdirs()) {
			logger.error("创建目录失败！！ tcNfsShareRootDir=" + tcNfsShareRootDir);
		}

		// 如果目标文件所在的目录存在，则强制删除之
		File[] files = dir.listFiles();

		if (null == files) {
			return;
		}

		for (File oldfile : files) {
			String oldFileName = oldfile.getName();

			if (isStartWith) {
				if (!oldFileName.startsWith(fileName)) {
					continue;
				}
			} else {
				if (!oldFileName.equals(fileName)) {
					continue;
				}
			}

			try {
				FileUtils.forceDelete(oldfile);
			} catch (IOException e) {
				logger.info("清理原始文件失败: file=" + oldfile.getAbsolutePath());
			}
		}
	}

	/**
	 * 使用替换符替换掉占位符
	 * 
	 * @param configKey
	 * @param placeholders
	 * @return
	 */
	protected String format2Placeholder(String configKey,
			String... placeholders) {

		String configValue = HsPropertiesConfigurer.getProperty(configKey);

		if (0 == placeholders.length % 2) {
			for (int i = 0; i < placeholders.length; i++) {
				if (0 < i % 2) {
					continue;
				}

				configValue = configValue.replace(placeholders[i],
						placeholders[i + 1]);
			}
		}

		return configValue;
	}
	
	/**
	 * 获取NFS保存对账文件的根目录
	 * 
	 * @param balanceDate
	 * @return
	 */
	protected String getTcNfsShareRootDir(String balanceDate) {
		
		String tcNfsShareRootDir = format2Placeholder(
				TcConfigConsts.KEY_SYS_TC_NFS_SHARE_DIR,
				TcConfigConsts.$YYYYMMDD, balanceDate);
		
		return tcNfsShareRootDir;
	}
	
	/**
	 * 计算页数
	 * 
	 * @param sumCounts
	 * @return
	 */
	protected int calculatePageSize(int sumCounts, int rowsPerPage) {

		double pageSize = (sumCounts * 1.0) / rowsPerPage;

		return (int) Math.ceil(pageSize);
	}
}
