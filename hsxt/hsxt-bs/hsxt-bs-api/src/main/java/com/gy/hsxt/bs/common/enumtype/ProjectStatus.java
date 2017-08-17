/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype;

/**
 * @version V1.0
 * @Package: com.gy.hsxt.bs.common.enumtype.annualfee
 * @ClassName: ProjectStatus
 * @Description: 方案状态 枚举类
 * @author: liuhq
 * @date: 2015-9-1 上午11:56:25
 */
public enum ProjectStatus {

    /**
     * 待审批
     */
    PENDING(0),
    /**
     * 已启用
     */
    ENABLE(1),
    /**
     * 审批驳回
     */
    REJECT(2),
    /**
     * 已停用
     */
    DISABLE(3);
    private int code;

    ProjectStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    /**
     * 检查参数是否符合条件
     *
     * @param code 状态码
     * @return boolean j检查结果
     */
    public static boolean checkStatus(int code) {
        boolean pass = false;
        for (ProjectStatus projectStatus : ProjectStatus.values()) {
            if (projectStatus.getCode() == code) {
                pass = true;
                break;
            }
        }
        return pass;
    }

}
