/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

package com.gy.hsxt.ac.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 批处理任务记录
 * @author 作者 : maocan
 * @ClassName: 类名: AccountType
 * @Description: 描述 :批处理任务记录
 * @createDate 创建时间: 2015-8-24 下午4:47:29
 * @version 1.0
 */
public class AccountBatchJob implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1483037380053483027L;

	/** 批任务名称 **/
	private String batchJobName;
	
	/** 任务日期（YYYYMMDD） **/
	private String batchDate;
	
	/** 任务状态（0完成；1执行中；2执行失败） **/
	private Integer batchStatus;
	
	/**
	 * 批记账子文件名称
	 */
	private String batchFileName;
	
	/**
	 * 批记账子文件地址
	 */
	private String batchFileAddress;
	
	/**
	 * 批记账子文件记录数
	 */
	private Integer batchLines;
	
	/**
	 * 批记账子文件记录完成数
	 */
	private Integer batchAchieveLines;
	
	/**
	 * 批记账子文件MD5签名
	 */
	private String batchFileMDFive;
	
	/** 任务描述  **/
	private String batchDescription;
	
	/** 任务开始时间 **/
	private Timestamp batchBeginDate;
	
	/** 任务结束时间**/
	private Timestamp batchEndDate;
	
	/** 是否已记账**/
	private boolean isAccount;
	
	/** 是否返回所有信息文件 **/
	private boolean isReturnInforFile;
	
	
	
	/** 批任务名称 **/
	public String getBatchJobName() {
		return batchJobName;
	}

	/** 批任务名称 **/
	public void setBatchJobName(String batchJobName) {
		this.batchJobName = batchJobName;
	}

	/** 任务日期（YYYYMMDD） **/
	public String getBatchDate() {
		return batchDate;
	}

	/** 任务日期（YYYYMMDD） **/
	public void setBatchDate(String batchDate) {
		this.batchDate = batchDate;
	}

	/** 任务状态（0完成；1执行中；2执行失败） **/
	public int getBatchStatus() {
		return batchStatus;
	}

	/** 任务状态（0完成；1执行中；2执行失败） **/
	public void setBatchStatus(Integer batchStatus) {
		this.batchStatus = batchStatus;
	}

	/**
	 * 获取批记账子文件名称
	 * @return batchFileName 批记账子文件名称
	 */
	public String getBatchFileName() {
		return batchFileName;
	}

	/**
	 * 设置批记账子文件名称
	 * @param batchFileName 批记账子文件名称
	 */
	public void setBatchFileName(String batchFileName) {
		this.batchFileName = batchFileName;
	}

	/**
	 * 获取批记账子文件地址
	 * @return batchFileAddress 批记账子文件地址
	 */
	public String getBatchFileAddress() {
		return batchFileAddress;
	}

	/**
	 * 设置批记账子文件地址
	 * @param batchFileAddress 批记账子文件地址
	 */
	public void setBatchFileAddress(String batchFileAddress) {
		this.batchFileAddress = batchFileAddress;
	}

	/**
	 * 获取批记账子文件记录数
	 * @return lines 批记账子文件记录数
	 */
	public Integer getBatchLines() {
		return batchLines;
	}

	/**
	 * 设置批记账子文件记录数
	 * @param lines 批记账子文件记录数
	 */
	public void setBatchLines(Integer batchLines) {
		this.batchLines = batchLines;
	}

	/**
	 * 获取批记账子文件记录完成数
	 * @return batchAchieveLines 批记账子文件记录数
	 */
	public Integer getBatchAchieveLines() {
		return batchAchieveLines;
	}

	/**
	 * 设置批记账子文件记录完成数
	 * @param batchAchieveLines 批记账子文件记录数
	 */
	public void setBatchAchieveLines(Integer batchAchieveLines) {
		this.batchAchieveLines = batchAchieveLines;
	}

	/**
	 * 获取批记账子文件MD5签名
	 * @return batchFileMDFive 批记账子文件MD5签名
	 */
	public String getBatchFileMDFive() {
		return batchFileMDFive;
	}

	/**
	 * 设置批记账子文件MD5签名
	 * @param batchFileMDFive 批记账子文件MD5签名
	 */
	public void setBatchFileMDFive(String batchFileMDFive) {
		this.batchFileMDFive = batchFileMDFive;
	}

	/** 任务描述  **/
	public String getBatchDescription() {
		return batchDescription;
	}

	/** 任务描述  **/
	public void setBatchDescription(String batchDescription) {
		this.batchDescription = batchDescription;
	}
	
	/** 任务开始时间 **/
	public Timestamp getBatchBeginDate() {
		return batchBeginDate;
	}

	/** 任务开始时间 **/
	public void setBatchBeginDate(Timestamp batchBeginDate) {
		this.batchBeginDate = batchBeginDate;
	}

	/** 任务结束时间**/
	public Timestamp getBatchEndDate() {
		return batchEndDate;
	}

	/** 任务结束时间**/
	public void setBatchEndDate(Timestamp batchEndDate) {
		this.batchEndDate = batchEndDate;
	}

	/**
	 * 获取是否已记账
	 * @return isAccount 是否已记账
	 */
	public boolean isAccount() {
		return isAccount;
	}

	/**
	 * 设置是否已记账
	 * @param isAccount 是否已记账
	 */
	public void setAccount(boolean isAccount) {
		this.isAccount = isAccount;
	}

	/**
	 * 是否返回所有信息文件
	 * @return isReturnInforFile 是否返回所有信息文件
	 */
	public boolean isReturnInforFile() {
		return isReturnInforFile;
	}

	/**
	 * 设置是否返回所有信息文件
	 * @param isReturnInforFile 是否返回所有信息文件
	 */
	public void setReturnInforFile(boolean isReturnInforFile) {
		this.isReturnInforFile = isReturnInforFile;
	}

}
