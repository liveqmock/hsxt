/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.ds.job.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.common.spring.MyPropertyConfigurer;
import com.gy.hsi.ds.job.dao.impl.JobExecuteDetailsDaoImpl;
import com.gy.hsi.ds.job.service.ICleanExecuteDetailLogService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-ds-param
 * 
 *  Package Name    : com.gy.hsi.ds.job.web.service.common
 * 
 *  File Name       : CleanExecuteDetailLogService.java
 * 
 *  Creation Date   : 2016年1月26日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 每个月第一天扫描T_DS_JOB_EXECUTE_DETAILS表, 删除60天以前的数据。
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service(value = "cleanExecuteDetailLogService")
public class CleanExecuteDetailLogService implements
		ICleanExecuteDetailLogService, Serializable {

	private static final long serialVersionUID = 6154329728645011915L;

	@Autowired
	private JobExecuteDetailsDaoImpl detailDao;

	public void cleanGarbageDatas() {
		try {
			// 可配置, 默认30天
			int days = MyPropertyConfigurer
					.getPropertyIntValue("execute_detail_clean_days");

			if (0 >= days) {
				days = 30;
			}

			detailDao.deleteDetailDataByDate(days);
		} catch (Exception e) {
		}
	}

}
