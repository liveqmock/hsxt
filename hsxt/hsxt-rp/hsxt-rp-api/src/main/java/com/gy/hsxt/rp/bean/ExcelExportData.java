/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.rp.bean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 账户余额对象
 * @author 作者 : weixz
 * @ClassName: 类名:AccountBalance
 * @Description: TODO
 * @createDate 创建时间: 2015-8-25 下午2:35:52
 * @version 1.0
 */
public class ExcelExportData implements Serializable{

	private static final long serialVersionUID = 4156407271873114973L;

	/**
     * 导出数据 key:String 表示每个Sheet的名称 value:List<?> 表示每个Sheet里的所有数据行
     */
    private LinkedHashMap<String, List<?>> dataMap;

    /**
     * 每个Sheet里的顶部大标题
     */
    private String[] titles;

    /**
     * 单个sheet里的数据列标题
     */
    private List<String[]> columnNames;

    /**
     * 单个sheet里每行数据的列对应的对象属性名称
     */
    private List<String[]> fieldNames;

	/**
	 * @return the 导出数据key:String表示每个Sheet的名称value:List<?>表示每个Sheet里的所有数据行
	 */
	public LinkedHashMap<String, List<?>> getDataMap() {
		return dataMap;
	}

	/**
	 * @param 导出数据key:String表示每个Sheet的名称value:List<?>表示每个Sheet里的所有数据行 the dataMap to set
	 */
	public void setDataMap(LinkedHashMap<String, List<?>> dataMap) {
		this.dataMap = dataMap;
	}

	/**
	 * @return the 每个Sheet里的顶部大标题
	 */
	public String[] getTitles() {
		return titles;
	}

	/**
	 * @param 每个Sheet里的顶部大标题 the titles to set
	 */
	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	/**
	 * @return the 单个sheet里的数据列标题
	 */
	public List<String[]> getColumnNames() {
		return columnNames;
	}

	/**
	 * @param 单个sheet里的数据列标题 the columnNames to set
	 */
	public void setColumnNames(List<String[]> columnNames) {
		this.columnNames = columnNames;
	}

	/**
	 * @return the 单个sheet里每行数据的列对应的对象属性名称
	 */
	public List<String[]> getFieldNames() {
		return fieldNames;
	}

	/**
	 * @param 单个sheet里每行数据的列对应的对象属性名称 the fieldNames to set
	 */
	public void setFieldNames(List<String[]> fieldNames) {
		this.fieldNames = fieldNames;
	}

}
