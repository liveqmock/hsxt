package com.gy.hsi.ds.job.beans.bo;

import java.util.Date;

import com.github.knightliao.apollo.db.bo.BaseObject;
import com.gy.hsi.ds.common.constant.Columns;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.annotation.Column;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.annotation.Table;

@Table(db = "", name = "T_DS_JOB_COUNTER", keyColumn = Columns.COUNT_ID)
public class JobCounter extends BaseObject<Long> {

	private static final long serialVersionUID = -7296955595637116299L;

	@Column(value = Columns.INST_NO)
	private String instNo;
	
	@Column(value = Columns.EXECUTE_ID)
	private String executeId;

	@Column(value = Columns.COUNT_VALUE)
	private int countValue;

	@Column(value = Columns.CREATE_DATE)
	private Date createDate;

	@Column(value = Columns.UPDATE_DATE)
	private Date updateDate;

	public String getInstNo() {
		return instNo;
	}

	public void setInstNo(String instNo) {
		this.instNo = instNo;
	}

	public String getExecuteId() {
		return executeId;
	}

	public void setExecuteId(String executeId) {
		this.executeId = executeId;
	}

	public int getCountValue() {
		return countValue;
	}

	public void setCountValue(int countValue) {
		this.countValue = countValue;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
