/***************************************************************************
 * This document contains confidential and proprietary information
 * subject to non-disclosure agreements with GUIYI Technology, Ltd.
 * This information shall not be distributed or copied without written
 * permission from GUIYI technology, Ltd.
 ***************************************************************************/

package com.gy.hsxt.common.exception;

import org.apache.commons.lang3.StringUtils;

import com.gy.hsxt.common.constant.IRespCode;
import com.gy.hsxt.common.constant.RespCode;

/***************************************************************************
 * <PRE>
 * Project Name    : hsxt-common
 * <p/>
 * Package Name    : com.gy.hsxt
 * <p/>
 * File Name       : HsException.java
 * <p/>
 * Creation Date   : 2015-7-8
 * <p/>
 * Author          : yangjianguo
 * <p/>
 * Purpose         : 通用异常类
 * <p/>
 * <p/>
 * History         : TODO
 * <p/>
 * </PRE>
 ***************************************************************************/
public class HsException extends RuntimeException {

    private static final long serialVersionUID = -6465404826204357739L;

    /**
     * 错误代码
     */
    private Integer errorCode;

    /**
     * 错误代码接口
     */
    private IRespCode respCode;

    public HsException() {
        super();
        errorCode = RespCode.UNKNOWN.getCode();
        respCode = RespCode.UNKNOWN;
    }

    /**
     * 该构造方法已经被遗弃
     * 
     * @param errorCode
     *            错误代码
     * @deprecated Please use {@link HsException#HsException(IRespCode)}
     *             instead.
     */
    @Deprecated
    public HsException(int errorCode) {
        super();
        this.errorCode = errorCode;
    }

    /**
     * 该构造方法已被遗弃
     * 
     * @param errorCode
     *            错误代码
     * @param message
     *            消息
     */
    public HsException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * 添加构造方法
     * 
     * @param respCode
     *            错误代码枚举类型
     * @author LiZhi Peter
     * @see 2015-12-21
     */
    public HsException(IRespCode respCode) {
        super(respCode.getDesc());
        this.errorCode = respCode.getCode();
        this.respCode = respCode;
    }

    /**
     * 构造方法
     * 
     * @param respCode
     *            错误代码
     * @param message
     *            默认消息
     */
    public HsException(IRespCode respCode, String message) {
        super(StringUtils.isNotBlank(message) ? message : respCode.getDesc());
        this.respCode = respCode;
        this.errorCode = respCode.getCode();
    }

    public Integer getErrorCode() {
        if (respCode != null)
        {
            return respCode.getCode();
        }
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public IRespCode getRespCode() {
        return respCode;
    }

    public void setRespCode(IRespCode respCode) {
        this.respCode = respCode;
    }

    @Override
    public String toString() {
        StringBuilder messageBuilder = new StringBuilder(getClass().getName());
        messageBuilder.append(":");
        if (respCode == null)
        {
            messageBuilder.append(this.errorCode);
        }
        else
        {
            messageBuilder.append(respCode.getCode()).append(":").append(respCode.name());
        }
        String message = getLocalizedMessage();
        return StringUtils.isNotBlank(message) ? messageBuilder.append(":").append(message).toString() : messageBuilder
                .toString();
    }
}
