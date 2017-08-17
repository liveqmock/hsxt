/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.common;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;

/**
 * @Package: com.gy.hsxt.ps.common
 * @ClassName: PsException
 * @Description: POS消费积分
 * 
 * @author: chenhongzhi
 * @date: 2015-12-10 下午1:31:58
 * @version V3.0
 */
public class PsException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2477246295749302685L;

	/**
	 * POS消费积分异常
	 *
	 * @param ste
	 * @param errorCode
	 * @param message
	 * @return
	 */
	public static void psHsThrowException(StackTraceElement ste, int errorCode, String message) throws HsException
	{
		String dateTime = "\n时间: " + DateUtil.DateTimeToString(DateUtil.getCurrentDate());
		String misaddress = "\n异常出处: [hsxt-ps-service] " + ste.toString();
		String describe = "\n异常描述: " + message;
		SystemLog.error("[hsxt-ps-service]", dateTime, misaddress, new HsException(errorCode, describe));
		throw new HsException(errorCode, dateTime + misaddress + describe);
	}

	/** added by liuzh 2016-05-19
	 * POS消费积分异常
	 *
	 * @param ste
	 * @param errorCode
	 * @param message
	 * @return
	 */
	public static void psThrowException(StackTraceElement ste, 
			int errorCode, String message,Exception ex) throws HsException
	{
		String dateTime = "\n时间: " + DateUtil.DateTimeToString(DateUtil.getCurrentDate());
		String misaddress = "\n异常出处: [hsxt-ps-service] " + ste.toString();
		String describe = "\n异常描述: " + message;
		
		SystemLog.error("[hsxt-ps-service]", dateTime, misaddress, new HsException(errorCode, describe));
		//打印异常信息
		SystemLog.error("[hsxt-ps-service]", "psThrowException", describe, ex);
		
		throw new HsException(errorCode, dateTime + misaddress + describe);
	}
	
	/**
	 * POS消费积分异常 不抛
	 *
	 * @param ste
	 * @param errorCode
	 * @param message
	 * @return
	 */
	public static  void psExceptionNotThrow(StackTraceElement ste, int errorCode, String message) throws HsException
	{
/*		ste = (new Exception()).getStackTrace()[1];*/
		String dateTime = "\n时间: " + DateUtil.DateTimeToString(DateUtil.getCurrentDate());
		String misaddress = "\n异常出处: [hsxt-ps-service] " + ste.toString();
		String describe = "\n异常描述: " + message;
		SystemLog.error("[hsxt-ps-service]", dateTime, misaddress+"----------"+describe,null);
	}

	/**
	 * 获取当前文件的文件名
	 * 
	 * @param ste
	 * @return
	 */
	public String getFileName(StackTraceElement ste)
	{
		return (ste.getFileName());
	}

}
