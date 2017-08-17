/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.gp.common.utils.DateUtils;
import com.gy.hsxt.gp.mapper.TGpRetryMapper;
import com.gy.hsxt.gp.service.ICleanExpiredDatasService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.service.impl
 * 
 *  File Name       : CleanExpiredDatasService.java
 * 
 *  Creation Date   : 2016年3月8日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 无效数据清理接口
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service("cleanExpiredDatasService")
public class CleanExpiredDatasService implements ICleanExpiredDatasService {

	@Autowired
	private TGpRetryMapper pgRetryMapper;

	@Override
	public void cleanExpiredDatas() {

		Date date = DateUtils.getDateBeforeDays(15);
		
		String retryDate = DateUtils.getDateByformat(date,
				"yyyy-MM-dd HH:mm:ss");

		try {
			pgRetryMapper.deleteBeforeDate(retryDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
