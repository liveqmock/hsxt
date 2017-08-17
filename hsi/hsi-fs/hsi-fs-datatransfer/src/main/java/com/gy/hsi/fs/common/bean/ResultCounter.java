/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.common.bean;


/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-datatransfer
 * 
 *  Package Name    : com.gy.hsi.fs.common.bean
 * 
 *  File Name       : ResultCounter.java
 * 
 *  Creation Date   : 2015年11月25日
 * 
 *  Author          : Administrator
 * 
 *  Purpose         : TODO
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public class ResultCounter {
	private String fieldName = "";
	private int successRows = 0;
	private int failedRows = 0;
	private int duplicatedRows = 0;

	public ResultCounter(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getSuccessRows() {
		return successRows;
	}

	public synchronized void addSuccessRows() {
		this.successRows = successRows + 1;
	}

	public int getFailedRows() {
		return failedRows;
	}

	public synchronized void addFailedRows() {
		this.failedRows = (failedRows + 1);
	}

	public int getDuplicatedRows() {
		return duplicatedRows;
	}

	public synchronized void addDuplicatedRows() {
		this.duplicatedRows = (duplicatedRows + 1);
	}

}
