package com.gy.hsxt.ws.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.tm.bean.TmTask;
import com.gy.hsxt.tm.enumtype.Priority;
import com.gy.hsxt.tm.enumtype.TaskSrc;
import com.gy.hsxt.ws.common.WsTools;

/**
 * 工单任务 实体
 * 
 * @Package: com.gy.hsxt.ws.bean
 * @ClassName: Task
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2015-12-24 下午4:54:35
 * @version V1.0
 */
public class Task implements Serializable {
	
	public static String CUST_ID_AREA_PLATFORM  = null;
	
	//private static final String KEY_WS_CUST_ID_AREA_PLATFORM = "ws.cust_id_area_platform";
	
	public static  String  KEY_WS_RES_NO_AREA_PLATFORM ="ws.hs_res_no_area_platform";

	/** 工单编号(主键ID) */
	private String taskId;

	/** 业务类型 */
	private String bizType;

	/** 业务编号(业务流水号) */
	private String bizNo;

	/** 工单所属企业客户号 */
	private String entCustId;

	/** 工单创建时间 */
	private Timestamp createdDate;

	/** 紧急程度 0：普通(默认) 1:紧急 */
	private Integer priority;

	/** 经办人编号（客户号） */
	private String exeCustId;

	/** 经办人姓名 */
	private String exeCustName;

	/** 派单时间 */
	private Timestamp assignedTime;

	/** 受理时间 */
	private Timestamp acceptTime;

	/** 工单完成时间 */
	private Timestamp finishTime;

	/** 是否催办 0：否(默认) 1：是 */
	private Integer warnFlag;

	/** 是否转派 0：否(默认) 1：是 */
	private Integer redirectFlag;

	/**
	 * 工单状态 0：未分派(默认) 1：未受理 2：办理中 3：已完成 4：已挂起 5：已停止
	 */
	private Integer status;

	/** 派单历史 每一次分派经办人历史，按先后顺序以“>”分隔经办人名称 */
	private String assignHis;

	/** 工单来源 1：业务服务 2：积分福利 */
	private Integer taskSrc;

	private static final long serialVersionUID = 1L;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId == null ? null : taskId.trim();
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType == null ? null : bizType.trim();
	}

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo == null ? null : bizNo.trim();
	}

	public String getEntCustId() {
		return entCustId;
	}

	public void setEntCustId(String entCustId) {
		this.entCustId = entCustId == null ? null : entCustId.trim();
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getExeCustId() {
		return exeCustId;
	}

	public void setExeCustId(String exeCustId) {
		this.exeCustId = exeCustId == null ? null : exeCustId.trim();
	}

	public String getExeCustName() {
		return exeCustName;
	}

	public void setExeCustName(String exeCustName) {
		this.exeCustName = exeCustName == null ? null : exeCustName.trim();
	}

	public Timestamp getAssignedTime() {
		return assignedTime;
	}

	public void setAssignedTime(Timestamp assignedTime) {
		this.assignedTime = assignedTime;
	}

	public Timestamp getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Timestamp acceptTime) {
		this.acceptTime = acceptTime;
	}

	public Timestamp getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Timestamp finishTime) {
		this.finishTime = finishTime;
	}

	public Integer getWarnFlag() {
		return warnFlag;
	}

	public void setWarnFlag(Integer warnFlag) {
		this.warnFlag = warnFlag;
	}

	public Integer getRedirectFlag() {
		return redirectFlag;
	}

	public void setRedirectFlag(Integer redirectFlag) {
		this.redirectFlag = redirectFlag;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAssignHis() {
		return assignHis;
	}

	public void setAssignHis(String assignHis) {
		this.assignHis = assignHis == null ? null : assignHis.trim();
	}

	public Integer getTaskSrc() {
		return taskSrc;
	}

	public void setTaskSrc(Integer taskSrc) {
		this.taskSrc = taskSrc;
	}

	public static TmTask buildTmTask() {
		TmTask task = new TmTask();
		task.setTaskId(WsTools.getGUID());
		task.setTaskSrc(TaskSrc.WS.getCode());
		if(CUST_ID_AREA_PLATFORM!=null){
			task.setEntCustId(CUST_ID_AREA_PLATFORM);
		}
		task.setPriority(Priority.GENERAL.getCode());
		task.setRedirectFlag(0);
		return task;
	}
}