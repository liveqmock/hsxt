/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 请求返回实体
 *
 * @Package : com.gy.hsxt.gpf.um.bean
 * @ClassName : RespInfo
 * @Description : 请求返回实体
 * 统一一下所有请求的返回实体，请大家遵守
 * @Author : chenm
 * @Date : 2016/2/2 15:44
 * @Version V3.0.0.0
 */
public class RespInfo<T> implements Serializable {

    private static final long serialVersionUID = -5166504065928017578L;
    /**
     * 请求是否成功 true-成功 false - 失败
     */
    private boolean success;

    /**
     * 错误日志
     */
    private Integer errorCode;

    /**
     * 结果数据
     */
    private T data;

    public RespInfo() {
    }

    public RespInfo(T data) {
        this.success = true;
        this.data = data;
    }

    public RespInfo(Integer errorCode) {
        this.success = false;
        this.errorCode = errorCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 构建返回信息
     *
     * @param data 数据
     * @return {@code RespInfo}
     */
    public static <T> RespInfo<T> bulid(T data) {
        return new RespInfo<>(data);
    }
    /**
     * 构建返回信息
     *
     * @param errorCode 错误码
     * @return {@code RespInfo}
     */
    public static RespInfo bulid(Integer errorCode) {
        return new RespInfo(errorCode);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
