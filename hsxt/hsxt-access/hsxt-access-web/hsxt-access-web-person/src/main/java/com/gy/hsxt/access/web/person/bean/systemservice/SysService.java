/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.bean.systemservice;

/**
 * 
 * @projectName   : hsxt-access-web-person
 * @package       : com.gy.hsxt.access.web.person.bean.systemservice
 * @className     : SysService.java
 * @description   : 系统服务自定义实体
 * @author        : maocy
 * @createDate    : 2016-01-21
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
public class SysService {
	
	/**业务流水号*/
	private String no;
	
	/**日期*/
	private String bsDate;
	
	/**受理结果*/
	private Object bsResult;
	
	/**备注*/
	private String remark;

	public SysService() {
		super();
	}
	
	public SysService(String no, String bsDate, Object bsResult) {
		super();
		this.no = no;
		this.bsDate = bsDate;
		this.bsResult = bsResult;
	}

	public SysService(String no, String bsDate, Object bsResult, String remark) {
		super();
		this.no = no;
		this.bsDate = bsDate;
		this.bsResult = bsResult;
		this.remark = remark;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getBsDate() {
		return bsDate;
	}

	public void setBsDate(String bsDate) {
		this.bsDate = bsDate;
	}

	public Object getBsResult() {
		return bsResult;
	}

	public void setBsResult(Object bsResult) {
		this.bsResult = bsResult;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
