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

package com.gy.hsxt.bp.bean;

import java.io.Serializable;

/** 
 * 系统参数项对象
 * @Package: com.gy.hsxt.bp.bean  
 * @ClassName: SysParamItems 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2015-11-20 下午12:03:11 
 * @version V1.0 
 

 */
public class BusinessSysParamItemsRedis implements Serializable{

    
	private static final long serialVersionUID = 6185264409362937733L;

    
    /** 参数组编号 **/
    private String      sysGroupCode;
    
    /** 参数项键名 **/
    private String      sysItemsKey;
    
    /** 参数项名称 **/
    private String      sysItemsName;
    
    /** 参数项值 **/
    private String      sysItemsValue;


	/**
	 * @return the 参数组编号
	 */
	public String getSysGroupCode() {
		return sysGroupCode;
	}

	/**
	 * @param 参数组编号 the sysGroupCode to set
	 */
	public void setSysGroupCode(String sysGroupCode) {
		this.sysGroupCode = sysGroupCode;
	}

	/**
	 * @return the 参数项键名
	 */
	public String getSysItemsKey() {
		return sysItemsKey;
	}

	/**
	 * @param 参数项键名 the sysItemsKey to set
	 */
	public void setSysItemsKey(String sysItemsKey) {
		this.sysItemsKey = sysItemsKey;
	}

	/**
     * @return the 系统参数项名
     */
    public String getSysItemsName() {
        return sysItemsName;
    }

    /**
     * @param 系统参数项名 the sysItemsName to set
     */
    public void setSysItemsName(String sysItemsName) {
        this.sysItemsName = sysItemsName;
    }

    /**
     * @return the 系统参数项值
     */
    public String getSysItemsValue() {
        return sysItemsValue;
    }

    /**
     * @param 系统参数项值 the sysItemsValue to set
     */
    public void setSysItemsValue(String sysItemsValue) {
        this.sysItemsValue = sysItemsValue;
    }
    
}
