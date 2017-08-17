/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uid.common;


import static com.gy.hsi.ds.param.HsPropertiesConfigurer.getProperty;

/**
 * 
 * 
 * @Package: com.gy.hsxt.uid.common  
 * @ClassName: SysConfig 
 * @Description: TODO
 *
 * @author: lvyan 
 * @date: 2016-3-14 下午2:29:51 
 * @version V1.0
 */
public class SysConfig {
	private final static String MOUDLENAME = "com.gy.hsxt.uid.common.SysConfig";
	/**
     * 从配置文件获取属性值
     * @param propName
     * @return
     */
    public static String getPropValue(String propName){
        return getProperty(propName);
    }
	/**
	 * 系统名称
	 */
	public static String getSystemName(){
		return getProperty("system.name");
	}
	
    /**
     * 获取系统实例名
     * @return
     */
    public static String getSystemInstanceNo(){
    	return getProperty("system.instance.no");
    }
    
    /**
     * 获取默认流水号增量
     * @return
     */
    public static long getDefaultAddNum(){
    	String addNum= getProperty("default.add.num");
    	if(addNum==null){
    		return 10000l;
    	}else{
    		return Long.parseLong(addNum);
    	}
    }
   
}
