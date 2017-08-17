/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.server.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gy.hsi.fs.common.utils.DateUtils;
import com.gy.hsi.fs.server.common.framework.mybatis.MapperSupporter;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsFileOperationLog;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsFileOperationLogExample;
import com.gy.hsi.fs.server.service.IOperationLogService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.service.impl
 * 
 *  File Name       : OperationLogServiceImpl.java
 * 
 *  Creation Date   : 2015-05-14
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 操作日志service接口实现类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/

@Service(value = "operationLogService")
public class OperationLogServiceImpl extends MapperSupporter implements
		IOperationLogService {

	@Override
	public boolean insertOperationLog(TFsFileOperationLog log) {
		log.setCreatedDate(new Date());

		int rows = getTFsFileOperationLogMapper().insert(log);

		return (0 < rows);
	}

	@Override
	public TFsFileOperationLog queryOperationLogByOptuserId(String userId) {

		TFsFileOperationLogExample example = new TFsFileOperationLogExample();
		example.createCriteria().andOptUserIdEqualTo(userId);

		List<TFsFileOperationLog> resultList = getTFsFileOperationLogMapper()
				.selectByExample(example);

		if ((null != resultList) && (0 < resultList.size())) {
			return resultList.get(0);
		}

		return null;
	}

	@Override
	public TFsFileOperationLog queryOperationLogByFileId(String fileId) {
		TFsFileOperationLogExample example = new TFsFileOperationLogExample();
		example.createCriteria().andFileIdEqualTo(fileId);

		List<TFsFileOperationLog> resultList = getTFsFileOperationLogMapper()
				.selectByExample(example);

		if ((null != resultList) && (0 < resultList.size())) {
			return resultList.get(0);
		}

		return null;
	}

	@Override
	public TFsFileOperationLog queryOperationLogByFuncId(String functionId) {
		TFsFileOperationLogExample example = new TFsFileOperationLogExample();
		example.createCriteria().andFunctionIdEqualTo(functionId);

		List<TFsFileOperationLog> resultList = getTFsFileOperationLogMapper()
				.selectByExample(example);

		if ((null != resultList) && (0 < resultList.size())) {
			return resultList.get(0);
		}

		return null;
	}

	@Override
	public void cleanGarbageDatas() {
		// 清理3个月之前的过期日志
		Date oldDate = DateUtils.getDateBeforeDays(90);

		Date currDateZeroTime = DateUtils.parseDate("yyyy-MM-dd",
				DateUtils.getDateByformat(oldDate, "yyyy-MM-dd 00:00:00"));

		TFsFileOperationLogExample example = new TFsFileOperationLogExample();
		example.createCriteria().andCreatedDateLessThan(currDateZeroTime);

		getTFsFileOperationLogMapper().deleteByExample(example);
	}
}
