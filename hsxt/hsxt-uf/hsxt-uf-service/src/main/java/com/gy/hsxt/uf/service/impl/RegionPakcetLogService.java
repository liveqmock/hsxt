/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.uf.common.utils.DateUtils;
import com.gy.hsxt.uf.mapper.TUfPacketLogDetailMapper;
import com.gy.hsxt.uf.mapper.TUfPacketLogMapper;
import com.gy.hsxt.uf.mapper.vo.TUfPacketLog;
import com.gy.hsxt.uf.mapper.vo.TUfPacketLogDetail;
import com.gy.hsxt.uf.mapper.vo.TUfPacketLogDetailExample;
import com.gy.hsxt.uf.mapper.vo.TUfPacketLogExample;
import com.gy.hsxt.uf.service.IRegionPacketLogService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.service.impl
 * 
 *  File Name       : RegionPakcetLogService.java
 * 
 *  Creation Date   : 2015年10月21日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 记录来往报文日志
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/

@Service(value = "regionPakcetLogService")
public class RegionPakcetLogService implements IRegionPacketLogService {

	@Autowired
	private TUfPacketLogMapper logMapper;

	@Autowired
	private TUfPacketLogDetailMapper logDetailMapper;

	@Override
	public boolean insertLogData(TUfPacketLog log,
			TUfPacketLogDetail logDetail) {
		int rows1 = logMapper.insert(log);

		if (null != logDetail) {
			int rows2 = logDetailMapper.insert(logDetail);

			return (0 < rows1) && (0 < rows2);
		}

		return (0 < rows1);
	}

	@Override
	public void cleanGarbageDatas() {
		try {
			// 清理3个月之前的过期日志
			Date oldDate = DateUtils.getDateBeforeDays(90);

			Date currDateZeroTime = DateUtils.parseDate("yyyy-MM-dd",
					DateUtils.getDateByformat(oldDate, "yyyy-MM-dd 00:00:00"));

			TUfPacketLogExample example = new TUfPacketLogExample();
			example.createCriteria().andCreateDateLessThan(currDateZeroTime);

			TUfPacketLogDetailExample detailExample = new TUfPacketLogDetailExample();
			detailExample.createCriteria().andCreateDateLessThan(currDateZeroTime);

			logMapper.deleteByExample(example);
			logDetailMapper.deleteByExample(detailExample);
		} catch (Exception e) {
		}
	}
}
