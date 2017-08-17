/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.bean.common;

import java.io.Serializable;
import java.sql.Timestamp;

import com.alibaba.fastjson.JSONObject;

/**
 * 隐私信息对象
 * 
 * @Package: com.gy.hsxt.uc.as.bean.common
 * @ClassName: AsCustPrivacy 
 * @Description: TODO
 *
 * @author: lvyan 
 * @date: 2016-06-30 下午6:53:54 
 * @version V1.0
 */
public class AsCustPrivacy implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7880687272087226350L;
	/**
	 * 客户号
	 */
    private String perCustId;
    /**
	 * 加好友允许状态；0 否；1 允许；2 需我同意
	 */
    private String addFriends;
    /**
	 * 搜索友允许状态；0 否；1 允许；2 互生号允许；3 手机号允许
	 */
    private String searchMe;
    /**
	 * 预留字段1
	 */
    private String ext1;
    /**
	 * 预留字段2
	 */
    private String ext2;
    /**
	 * 预留字段3
	 */
    private String ext3;
    
    /**
   	 * 创建人
   	 */
       private String createdby;

       /**
   	 * 更新人
   	 */
       private String updatedby;
	/**
	 * @return the 客户号
	 */
	public String getPerCustId() {
		return perCustId;
	}
	/**
	 * @param 客户号 the perCustId to set
	 */
	public void setPerCustId(String perCustId) {
		this.perCustId = perCustId;
	}
	/**
	 * @return the 加好友允许状态；0 否；1 允许；2 需我同意
	 */
	public String getAddFriends() {
		return addFriends;
	}
	/**
	 * @param 加好友允许状态；0 否；1 允许；2 需我同意 the addFriends to set
	 */
	public void setAddFriends(String addFriends) {
		this.addFriends = addFriends;
	}
	/**
	 * @return the 搜索友允许状态；0 否；1 允许；2 互生号允许；3 手机号允许
	 */
	public String getSearchMe() {
		return searchMe;
	}
	/**
	 * @param 搜索友允许状态 ；0 否；1 允许；2 互生号允许；3 手机号允许 the searchMe to set
	 */
	public void setSearchMe(String searchMe) {
		this.searchMe = searchMe;
	}
	/**
	 * @return the 预留字段1
	 */
	public String getExt1() {
		return ext1;
	}
	/**
	 * @param 预留字段1 the ext1 to set
	 */
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	/**
	 * @return the 预留字段2
	 */
	public String getExt2() {
		return ext2;
	}
	/**
	 * @param 预留字段2 the ext2 to set
	 */
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	/**
	 * @return the 预留字段3
	 */
	public String getExt3() {
		return ext3;
	}
	/**
	 * @param 预留字段3 the ext3 to set
	 */
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	/**
	 * @return the 创建人
	 */
	public String getCreatedby() {
		return createdby;
	}
	/**
	 * @param 创建人 the createdby to set
	 */
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	/**
	 * @return the 更新人
	 */
	public String getUpdatedby() {
		return updatedby;
	}
	/**
	 * @param 更新人 the updatedby to set
	 */
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}
   

}
