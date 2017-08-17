/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.search.bean;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * 搜索用户信息结果
 * 
 * @Package: com.gy.hsxt.uc.search.bean
 * @ClassName: SearchUserResult
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2016-1-5 下午4:06:35
 * @version V1.0
 */
public class SearchResult<T> extends Search implements Serializable {
	private static final long serialVersionUID = -4597000676255426073L;
	private List<T> list;

	/**
	 * @return the list
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<T> list) {
		this.list = list;
	}

	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
