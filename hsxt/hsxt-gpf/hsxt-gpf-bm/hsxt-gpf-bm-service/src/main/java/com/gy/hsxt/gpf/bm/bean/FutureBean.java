/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.bean;

import java.io.Serializable;

/**
 * 线程运行结果类
 *
 * @Package :com.gy.hsxt.gpf.bm.bean
 * @ClassName : FutureBean
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/14 21:47
 * @Version V3.0.0.0
 */
public class FutureBean<T> implements Serializable {

    private static final long serialVersionUID = -5675833033303402761L;

    /**
     * 目的平台代码
     */
    private String toPlatCode;

    /**
     * 文件路径或文件夹路径
     */
    private String filePath;

    /**
     * 是否发生异常
     */
    private boolean hasException;

    /**
     * 结果对象
     */
    private T t;

    public String getToPlatCode() {
        return toPlatCode;
    }

    public void setToPlatCode(String toPlatCode) {
        this.toPlatCode = toPlatCode;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isHasException() {
        return hasException;
    }

    public void setHasException(boolean hasException) {
        this.hasException = hasException;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
