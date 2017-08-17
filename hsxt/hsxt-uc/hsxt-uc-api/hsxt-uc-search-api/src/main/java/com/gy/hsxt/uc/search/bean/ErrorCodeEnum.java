/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.uc.search.bean;

/**
 * 错误码
 * 
 * @Package: com.gy.hsxt.uc.search.bean  
 * @ClassName: ErrorCodeEnum 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-11-19 下午4:18:14 
 * @version V1.0
 */
public enum ErrorCodeEnum {
	SOLR_INDEX_ADD_FAILED(160374, "solr索引添加失败"),
	SOLR_INDEX_UPDATE_FAILED(160375, "solr索引修改失败"),
	SOLR_INDEX_DELETE_FAILED(160376, "solr索引删除失败"),
	SOLR_INDEX_SEARCH_FAILED(160377, "solr索引搜索失败"),
	SOLR_SEARCH_FAILED(160410, "solr索引搜索失败"),
	;
	int value;
	String desc;

	private ErrorCodeEnum(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
}
