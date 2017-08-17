/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsi.ds.job.beans.bo;

import java.util.Date;
import java.util.Map;

import com.github.knightliao.apollo.db.bo.BaseObject;
import com.gy.hsi.ds.common.constant.Columns;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.annotation.Column;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.annotation.Table;
import com.gy.hsi.ds.common.utils.DateUtils;
import com.gy.hsi.ds.common.utils.ValuesUtils;

@Table(db = "", name = "T_DS_JOB_TEMP_ARGS", keyColumn = Columns.ARGS_ID)
public class JobTempArgs extends BaseObject<Long> {

	private static final long serialVersionUID = 3119980028296368591L;

	@Column(value = Columns.SERVICE_NAME)
	private String serviceName;

	@Column(value = Columns.TEMP_ARGS)
	private String tempArgs;

	@Column(value = Columns.TEMP_ARGS_KEYS)
	private String tempArgsKeys;

	@Column(value = Columns.EXECUTE_ID)
	private String executeId;

	@Column(value = Columns.EXECUTE_METHOD)
	private Integer executeMethod;

	@Column(value = Columns.CREATE_DATE)
	private Date createDate;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getTempArgs() {
		return tempArgs;
	}

	public void setTempArgs(String tempArgs) {
		this.tempArgs = tempArgs;
	}

	public String getTempArgsKeys() {
		return tempArgsKeys;
	}

	public void setTempArgsKeys(String tempArgsKeys) {
		this.tempArgsKeys = tempArgsKeys;
	}

	public String getExecuteId() {
		return executeId;
	}

	public void setExecuteId(String executeId) {
		this.executeId = executeId;
	}

	public Integer getExecuteMethod() {
		return executeMethod;
	}

	public void setExecuteMethod(Integer executeMethod) {
		this.executeMethod = executeMethod;
	}

	public Map<String, String> getTempArgsMap() {
		return ValuesUtils.getParmMap(tempArgs);
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public boolean isExpired() {
		if (null == createDate) {
			return true;
		}

		return DateUtils.getDateBeforeDays(2).getTime() >= createDate.getTime();
	}

	@Override
	public String toString() {
		return "JobTempArgs [serviceName=" + serviceName + ", tempArgs="
				+ tempArgs + ", tempArgsKeys=" + tempArgsKeys + ", executeId="
				+ executeId + ", executeMethod=" + executeMethod
				+ ", createDate=" + createDate + "]";
	}

}
