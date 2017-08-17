package com.gy.hsxt.rabbitmq.center.framework.bean;


/**
 * 
 * @ClassName: GlobaSystemParams 
 * @Description: 全局系统参数配置表实体bean 
 * @author Lee 
 * @date 2015-6-30 下午2:18:25
 */
public class GlobaSystemParams {
	
	private String systemId;
	
	private String key;
	
	private String value;
	
	private String desc;

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
