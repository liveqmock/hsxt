/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.tc.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 对账任务执行进度
 * 
 * @Package: com.gy.hsxt.tc.bean
 * @ClassName: TcJob
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-18 下午8:25:26
 * @version V1.0
 */
public class TcJob implements Serializable {
	private static final long serialVersionUID = 2064832897401068793L;

	/** 主键，自增长 */
	private Long jobId;

	/**
	 * 对账间业务简称 GP-CH:支付系统与上海银联 BS-GP-PAY:业务服务与支付系统支付业务 BS-GP-TWD:业务服务与支付系统转账业务
	 * BS-AC:业务服务与账务系统 PS-AC:消费积分与账务系统
	 */
	private String tcSys;

	/** 对账日期，格式YYYY-MM-DD */
	private String tcDate;

	/**
	 * 处理进度： 0：预处理 1：明细对账
	 */
	private Integer tcProgress;

	/**
	 * 处理状态 0：成功； 1：失败 2：处理中
	 */
	private Integer tcState;

	/** 描述 */
	private String desc;

	/** 更新时间，取记录更新时的系统时间 */
	private Timestamp updatedDate;

	/**
	 * @return the 主键，自增长
	 */
	public Long getJobId() {
		return jobId;
	}

	/**
	 * @param 主键
	 *            ，自增长 the jobId to set
	 */
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	/**
	 * @return the
	 *         对账间业务简称GP-CH:支付系统与上海银联BS-GP-PAY:业务服务与支付系统支付业务BS-GP-TWD:业务服务与支付系统转账业务BS
	 *         -AC:业务服务与账务系统PS-AC:消费积分与账务系统
	 */
	public String getTcSys() {
		return tcSys;
	}

	/**
	 * @param 对账间业务简称GP
	 *            -CH:支付系统与上海银联BS-GP-PAY:业务服务与支付系统支付业务BS-GP-TWD:业务服务与支付系统转账业务BS-
	 *            AC:业务服务与账务系统PS-AC:消费积分与账务系统 the tcSys to set
	 */
	public void setTcSys(String tcSys) {
		this.tcSys = tcSys;
	}

	/**
	 * @return the 对账日期，格式YYYY-MM-DD
	 */
	public String getTcDate() {
		return tcDate;
	}

	/**
	 * @param 对账日期
	 *            ，格式YYYY-MM-DD the tcDate to set
	 */
	public void setTcDate(String tcDate) {
		this.tcDate = tcDate;
	}

	/**
	 * @return the 处理进度：0：预处理1：明细对账
	 */
	public Integer getTcProgress() {
		return tcProgress;
	}

	/**
	 * @param 处理进度
	 *            ：0：预处理1：明细对账 the tcProgress to set
	 */
	public void setTcProgress(Integer tcProgress) {
		this.tcProgress = tcProgress;
	}

	/**
	 * @return the 处理状态0：成功；1：失败2：处理中
	 */
	public Integer getTcState() {
		return tcState;
	}

	/**
	 * @param 处理状态0
	 *            ：成功；1：失败2：处理中 the tcState to set
	 */
	public void setTcState(Integer tcState) {
		this.tcState = tcState;
	}

	/**
	 * @return the 描述
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param 描述
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the 更新时间，取记录更新时的系统时间
	 */
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param 更新时间
	 *            ，取记录更新时的系统时间 the updatedDate to set
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

}