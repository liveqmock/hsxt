package com.gy.hsi.ds.param.beans.bo;

import java.util.Date;

import org.apache.log4j.Logger;

import com.github.knightliao.apollo.db.bo.BaseObject;
import com.gy.hsi.ds.common.constant.Columns;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.annotation.Column;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.annotation.Table;

/**
 * @author liaoqiqi
 * @version 2014-6-16
 */
@Table(db = "", name = "T_DS_APP", keyColumn = Columns.APP_ID)
public class App extends BaseObject<Long> implements Comparable<App> {

	protected static final Logger LOG = Logger.getLogger(App.class);

	/**
     *
     */
	private static final long serialVersionUID = -2217832889126331664L;

	/**
     *
     */
	@Column(value = Columns.APP_NAME)
	private String appName;

	/**
     *
     */
	@Column(value = Columns.DESC)
	private String desc;

	/**
     *
     */
	@Column(value = Columns.EMAILS)
	private String emails;

	/**
	 * 创建时间
	 */
	@Column(value = Columns.CREATE_DATE)
	private Date createDate;

	/**
	 * 更新时间
	 */
	@Column(value = Columns.UPDATE_DATE)
	private Date updateDate;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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

	@Override
	public String toString() {
		return appName;
	}

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

	@Override
	public int compareTo(App o) {
		return this.appName.compareTo(o.toString());
	}

}
