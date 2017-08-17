package com.gy.hsxt.access.pos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.service.LcsClientService;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.client.LcsClient;

/**
 * @Description: 包装全局配置的基础参数服务功能
 * @author: kend 
 * @date: 2016-01-07
 * @version V1.0
 */
@Service("lcsClientService")
public class LcsClientServiceImpl implements LcsClientService {
	
	@Autowired
	@Qualifier("lcsClient")
	private LcsClient lcsClient;
	
	
	@Override
	public String getPlatByHscardNo(String hscardNo) throws HsException{
		try{
			return lcsClient.getPlatByResNo(hscardNo);
		}catch(Exception e){
			throw new HsException(10001,"综合配置异常，无法获取平台号");
		}
		
	}


	@Override
	public boolean isLocalPlat(String perHscardNo) throws HsException {
		boolean  isLocalPlat = false;
		try{
			SystemLog.debug("LcsClientServiceImpl", "isLocalPlat()", 
						"调用地区平台服务 请求参数 perHscardNo：" + perHscardNo);
			isLocalPlat = !lcsClient.isAcrossPlat(perHscardNo);
			SystemLog.debug("LcsClientServiceImpl", "isLocalPlat()", 
					"调用地区平台服务返回结果 isLocalPlat：" + isLocalPlat);
		}catch(HsException he){
			if(he.getErrorCode() == RespCode.GL_DATA_NOT_FOUND.getCode())
				throw he;//未找到卡
			else
				throw new HsException(10002,"综合配置异常，无法判断本地异地卡");
		}catch(Exception e){
			throw new HsException(10003,"综合配置异常，服务无法使用");
		}
		
		return isLocalPlat;
		
	}
	

}
