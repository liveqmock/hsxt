package com.gy.hsxt.uc.as.api.filter;

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
    private int retCode = 0;

    /** 结果描述 **/
    private String resultDesc;

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
     * 返回码
     * @return
     */
    public int getRetCode() {
        return retCode;
    }

    /**
     * 返回码
     * @param retCode
     */
    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    /**
     * 结果描述
     * @return
     */
    public String getResultDesc() {
        return resultDesc;
    }

    /**
     * 结果描述
     * @param resultDesc
     */
    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }
}
