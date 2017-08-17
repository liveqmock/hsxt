package com.gy.hsi.nt.server.entity;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * 
 * @className:ExcelResources
 * @author:likui
 * @date:2015年7月27日
 * @desc:读取excel
 * @company:gyist
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelResources {

	/**
	 * 属性的标题名称
	 * @return
	 */
	String title();
	
	/**
	 * 在excel的顺序
	 * @return
	 */
	int order() default 9999;
}