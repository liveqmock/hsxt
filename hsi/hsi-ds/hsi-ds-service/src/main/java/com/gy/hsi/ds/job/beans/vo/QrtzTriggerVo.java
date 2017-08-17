/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsi.ds.job.beans.vo;

/**
 * 
 * TriggerVo类
 * 
 * @Package: com.gy.hsi.ds.job.web.service.trigger.vo
 * @ClassName: QrtzTriggerVo
 * @Description: TODO
 *
 * @author: yangyp
 * @date: 2015年10月14日 下午4:26:34
 * @version V3.0
 */
public class QrtzTriggerVo {

	/**
	 * trigger的名字,对应业务名称
	 */
	private String randomId;

	/**
	 * trigger的名字,对应业务名称
	 */
	private String triggerName;

	/**
	 * trigger所属组的名字，对应DUBBO服务名
	 */
	private String triggerGroup;

	/**
	 * trigger所属组的名字，对应DUBBO服务版本
	 */
	private String version;

	/**
	 * 描述
	 */
	private String desc;

	/**
	 * 下次执行时间
	 */
	private String nextFireTime;

	/**
	 * 上次执行时间
	 */
	private String prevFireTime;

	/**
	 * 当前trigger状态
	 */
	private String triggerState;

	/**
	 * 开始时间
	 */
	private String startTime;

	/**
	 * 结束时间
	 */
	private String endTime;

	/**
	 * cron表达式
	 */
	private String cron;

	public String getRandomId() {
		return randomId;
	}

	public void setRandomId(String randomId) {
		this.randomId = randomId;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getTriggerGroup() {
		return triggerGroup;
	}

	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getNextFireTime() {
		return nextFireTime;
	}

	public void setNextFireTime(String nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	public String getPrevFireTime() {
		return prevFireTime;
	}

	public void setPrevFireTime(String prevFireTime) {
		this.prevFireTime = prevFireTime;
	}

	public String getTriggerState() {
		return triggerState;
	}

	public void setTriggerState(String triggerState) {
		this.triggerState = triggerState;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "QrtzTriggerVo [triggerName=" + triggerName + ", triggerGroup="
				+ triggerGroup + ", desc=" + desc + ", nextFireTime="
				+ nextFireTime + ", prevFireTime=" + prevFireTime
				+ ", triggerState=" + triggerState + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", cron=" + cron + "]";
	}

}
