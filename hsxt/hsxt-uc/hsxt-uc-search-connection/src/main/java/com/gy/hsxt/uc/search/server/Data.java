package com.gy.hsxt.uc.search.server;

/**
 * 存入搜索的数据对象
 * 
 * @Package: com.gy.hsxt.uc.search.server  
 * @ClassName: Data 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-11-19 上午11:50:06 
 * @version V1.0
 */
public class Data {
	String fieldName;
	Object value;
	boolean isIncrease = false;
	
	/**
	 * 
	 * @param fieldName 字段名称
	 * @param value 字段值
	 * @param isIncrease 是否自增长
	 */
	public Data(String fieldName, Object value,boolean isIncrease){
		this.fieldName = fieldName;
		this.value = value;
		this.isIncrease = isIncrease;
	}
	
	/**
	 * 获取字段名称
	 * @return
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * 设置字段名称
	 * @param fieldName
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * 获取值
	 * @return
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * 设置值
	 * @param value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * 是否自增长
	 * @return
	 */
	public boolean isIncrease() {
		return isIncrease;
	}

	/**
	 * 设置是否自增长
	 * @param isIncrease
	 */
	public void setIncrease(boolean isIncrease) {
		this.isIncrease = isIncrease;
	}

}
