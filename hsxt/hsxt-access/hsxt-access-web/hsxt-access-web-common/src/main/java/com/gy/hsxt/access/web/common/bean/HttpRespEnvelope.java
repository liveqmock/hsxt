package com.gy.hsxt.access.web.common.bean;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.fs.common.exception.FsException;
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
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class HttpRespEnvelope {
    /** 结果返回码 默认成功 **/
    private int retCode = RespCode.AS_OPT_SUCCESS.getCode();

    /** 结果描述 **/
    private String resultDesc = "Operation success.";

    /** 响应数据 **/
    private Object data = "";

    private Boolean success = true;

    private int totalRows = 0;

    private int curPage = 0;
    
    /** 响应数据 **/
    private Exception exception = null;
    
    public HttpRespEnvelope() {
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
     * 执行成功的工造方法
     * 
     * @param resultData
     *            返回结果Data
     */
    public HttpRespEnvelope(Object resultData) {
        data = resultData;
    }

    /**
     * 讲异常信息进行二次封装返回界面
     * 
     * @param ex
     *            异常信息
     */
    public HttpRespEnvelope(Exception ex) {
        
        // 判断是否是互生异常
        if (ex instanceof HsException)
        {
            HsException hsEx = ((HsException) ex);
            this.retCode = hsEx.getErrorCode();
            this.setResultDesc(hsEx.getMessage());
           
        }
        else if(ex instanceof FsException)
        {
            FsException fsEx = ((FsException) ex);
            this.retCode = fsEx.getErrorCode();
            this.setResultDesc(fsEx.getMessage());
        }
        else
        {
            // 不是互生异常返回固定的错误编码
            this.setException(ex); 
            this.setRetCode(RespCode.AS_OPT_FAILED.getCode());
            this.setResultDesc("The AS server internal processing error! Please report this message to us, thks.");
        }

        this.exception = ex;
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

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
