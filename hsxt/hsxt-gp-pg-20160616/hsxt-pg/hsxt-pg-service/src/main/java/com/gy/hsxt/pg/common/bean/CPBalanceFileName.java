/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.common.bean;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.common.bean
 * 
 *  File Name       : CPBalanceFileName.java
 * 
 *  Creation Date   : 2016年2月26日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : none
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class CPBalanceFileName {
	private String merId;
	private String transDate;
	private String fullFileName;

	public CPBalanceFileName(String fullFileName) throws IllegalAccessException {
		this.fullFileName = fullFileName;

		String[] result = fullFileName.split("_");

		if ((null != result) && (3 == result.length)) {
			this.merId = result[0];
			this.transDate = result[1];
		} else {
			throw new IllegalAccessException(
					"银联通知的对账文件名称规则不合法！！！ fullFileName=" + fullFileName);
		}
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getFullFileName() {
		return fullFileName;
	}

	public void setFullFileName(String fullFileName) {
		this.fullFileName = fullFileName;
	}

}
