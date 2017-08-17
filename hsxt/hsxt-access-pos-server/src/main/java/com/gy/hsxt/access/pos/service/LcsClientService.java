package com.gy.hsxt.access.pos.service;

import com.gy.hsxt.common.exception.HsException;

/**
 * @Description: 包装全局配置的基础参数服务功能
 * @author: kend 
 * @date: 2016-01-07
 * @version V1.0
 */
public interface LcsClientService {
	
	/**
	 * 根据互生卡号获取对应发卡平台编码
	 * @param resNo
	 * @return 地方平台代码
	 */
	public String getPlatByHscardNo(String hscardNo) throws HsException;
	
	/**
	 * 判断消费者互生卡号是否属于本地方平台. 
	 * @param perHscardNo
	 * @return  true:本地;false:异地
	 */
	public boolean isLocalPlat(String perHscardNo) throws HsException;
	

}
