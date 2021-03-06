package com.gy.hsi.ds.common.thirds.dsp.common.exception;

import com.gy.hsi.ds.common.thirds.dsp.common.constant.ErrorCode;
import com.gy.hsi.ds.common.thirds.dsp.common.constant.ModuleCode;
import com.gy.hsi.ds.common.thirds.dsp.common.exception.base.RuntimeGlobalException;

/**
 * @author weiwei
 * @Description: the method is not accessible to current user
 */
public class AccessDeniedException extends RuntimeGlobalException {

    private static final long serialVersionUID = 1L;

    public AccessDeniedException(String exceptionMessage) {
        super(ErrorCode.GLOBAL_ERROR, exceptionMessage);
    }

    @Override
    public ModuleCode getModuleCode() {
        return ModuleCode.OTHER;
    }

}
