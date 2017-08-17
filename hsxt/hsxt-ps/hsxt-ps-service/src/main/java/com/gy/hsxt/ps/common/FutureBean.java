/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.ps.common;

/**
 * @Package :com.gy.hsxt.ps.common
 * @ClassName : FutureBean
 * @Description : 线程返回类
 * @Author : Martin.Cubbon
 * @Date : 4/21 0021 10:32
 * @Version V3.0.0.0
 */
public class FutureBean<T> {

    /**
     * 操作是否成功 true 成功 false失败
     */
    private boolean success;

    /**
     * 失败原因
     */
    private String reason;

    /**
     * 结果对象
     */
    private T result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
