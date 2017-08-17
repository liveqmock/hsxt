/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.constant;

/**
 * 回调类型
 *
 * @Package :com.gy.hsxt.gpf.fss.constant
 * @ClassName : CallType
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/12 10:45
 * @Version V3.0.0.0
 */
public enum CallType {
    /**
     * 远程回调：
     * 一般为文件系统因外部消息而调用子系统
     */
    REMOTE(0),
    /**
     * 本地回调:
     * 一般为文件同步系统因子系统消息而回调子系统
     */
    LOCAL(1);

    /**
     * 类型代码
     */
    private int type;

    CallType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
