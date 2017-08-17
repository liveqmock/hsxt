package com.gy.hsxt.access.web.gks.bean;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/***************************************************************************
 * <PRE>
 * Description 		: http响应数据信封
 * 
 * Project Name   	: hsxt-access-web-person
 * 
 * Package Name     : com.gy.hsxt.access.web.person.bean
 * 
 * File Name        : HttpRespEnvelope
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-9-1 下午4:31:29  
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-9-1 下午4:31:29
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v0.0.1
 * 
 * </PRE>
 ***************************************************************************/
public class HttpRespEnvelope {
    /** 结果返回码 默认成功 **/
    private int retCode = RespCode.AS_OPT_SUCCESS.getCode();

    /** 结果描述 **/
    private String resultDesc = "成功";
    
    private boolean success = true;
    /** 响应数据 **/
    private Object data = null;

    public HttpRespEnvelope() {
    }

    
    
    /**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}



	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}



	/**
     * 构造方法 设置返回界面结果
     * 
     * @param retCode
     *            返回结果code
     * @param resultDesc
     *            错误信息
     */
    public HttpRespEnvelope(int retCode, String resultDesc) {
        this.retCode = retCode;
        this.resultDesc = resultDesc;
    }

    /**
     * 执行成功的构造方法
     * 
     * @param resultData
     *            返回结果Data
     */
    public HttpRespEnvelope(Object resultData) {
        data = resultData;
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String toString()
    {
      return JSON.toJSONString(this);
    }
}
