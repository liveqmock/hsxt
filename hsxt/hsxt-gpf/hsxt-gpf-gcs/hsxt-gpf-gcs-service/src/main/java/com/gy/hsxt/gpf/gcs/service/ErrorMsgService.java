/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.gpf.gcs.bean.ErrorMsg;
import com.gy.hsxt.gpf.gcs.common.Constant;
import com.gy.hsxt.gpf.gcs.interfaces.IErrorMsgService;
import com.gy.hsxt.gpf.gcs.mapper.ErrorMsgMapper;
/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.service
 * 
 *  File Name       : ErrorMsgService.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 错误信息接口实现
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Service("errorMsgService")
public class ErrorMsgService implements IErrorMsgService {
	
	
	@Autowired
	private ErrorMsgMapper errorMsgMapper;
	
	@Autowired
	private VersionService veresionService;

	@Override
	@Transactional
	public int addErrorMsg(ErrorMsg errorMsg) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_ERROR_MSG);
		errorMsg.setVersion(version);
		return errorMsgMapper.addErrorMsg(errorMsg);
	}

	@Override
	@Transactional
	public boolean deleteErrorMsg(String languageCode,int errorCode) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_ERROR_MSG);
		ErrorMsg errorMsg = new ErrorMsg(languageCode,errorCode);
		errorMsg.setVersion(version);
		return errorMsgMapper.deleteErrorMsg(errorMsg);
	}

	@Override
	@Transactional
	public boolean updateErrorMsg(ErrorMsg errorMsg) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_ERROR_MSG);
		errorMsg.setVersion(version);
		return errorMsgMapper.updateErrorMsg(errorMsg);
	}

	@Override
	public ErrorMsg queryErrorMsgWithPK(String languageCode,int errorCode) {
		return errorMsgMapper.queryErrorMsgWithPK(languageCode,errorCode);
	}

	@Override
	public List<ErrorMsg> queryErrorMsg(ErrorMsg errorMsg) {
		String msg =errorMsg.getErrorMsg();
		if(null != msg && !"".equals(msg)){
			msg = msg.replaceAll("_", "/_").replaceAll("%", "/%");
			errorMsg.setErrorMsg(msg);
		}
		return errorMsgMapper.queryErrorMsgListPage(errorMsg);
	}

	@Override
	public boolean existErrorMsg(String languageCode,int errorCode) {
		boolean isExist =  "1".equals(errorMsgMapper.existErrorMsg(languageCode,errorCode));
		return isExist;
	}

	@Override
	public List<ErrorMsg> queryErrorMsg4SyncData(Long version) {
		return errorMsgMapper.queryErrorMsg4SyncData(version);
	}
}
