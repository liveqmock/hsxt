package com.gy.hsi.ds.common.thirds.dsp.common.exception.base;

import java.io.Serializable;

import com.gy.hsi.ds.common.thirds.dsp.common.constant.ErrorCode;
import com.gy.hsi.ds.common.thirds.dsp.common.constant.ModuleCode;

/**
 * @author liaoqiqi
 * @version 2013-12-2
 */
public interface Codeable extends Serializable {

    /**
     * 获取异常编码
     *
     * @return
     */
    ErrorCode getErrorCode();

    /**
     * 获取异常消息
     *
     * @return
     */
    String getErrorMessage();

    /**
     * 获取Module代码
     *
     * @return
     */
    ModuleCode getModuleCode();

}
