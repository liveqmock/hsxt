/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.lcs.bean.ErrorMsg;
import com.gy.hsxt.lcs.bean.Version;
import com.gy.hsxt.lcs.common.Constant;
import com.gy.hsxt.lcs.interfaces.IErrorMsgService;
import com.gy.hsxt.lcs.mapper.ErrorMsgMapper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.service
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
	public ErrorMsg queryErrorMsgWithPK(String languageCode,int errorCode) {
		return errorMsgMapper.queryErrorMsgWithPK(languageCode,errorCode);
	}

	@Override
	@Transactional
	public int addOrUpdateErrorMsg(List<ErrorMsg> list, Long version) {
		veresionService.addOrUpdateVersion4SyncData(new Version(Constant.GL_ERROR_MSG,version,true));
		List<ErrorMsg> errorMsgListAdd = new ArrayList<ErrorMsg>();
		List<ErrorMsg> errorMsgListUpdate = new ArrayList<ErrorMsg>();
		for(ErrorMsg obj : list){
			if("1".equals(errorMsgMapper.existErrorMsg(obj.getLanguageCode(), obj.getErrorCode()))){
				errorMsgListUpdate.add(obj);
			}else{
				errorMsgListAdd.add(obj);
			}
		}
		if(errorMsgListUpdate.size()>0){
			errorMsgMapper.batchUpdate(errorMsgListUpdate);
		}
		
		if(errorMsgListAdd.size()>0){
			errorMsgMapper.batchInsert(errorMsgListAdd);
		}
		return 1;
	}
}
