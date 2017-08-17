/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.constant;

/**
 * 通知类型枚举
 *
 * @Package :com.gy.hsxt.gpf.fss.constant
 * @ClassName : FssNotifyType
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/22 17:02
 * @Version V3.0.0.0
 */
public enum FssNotifyType {
    /**
     * 文件下载
     */
    DOWNLOAD(0),
    /**
     * 文件上传
     */
    UPLOAD(1),
    /**
     * 文件进行MD5验证
     */
    MD5(2),
    /**
     * 文件下载并进行MD5验证
     */
    DOWNLOAD_MD5(3),
    /**
     * 文件上传并进行MD5验证
     */
    UPLOAD_MD5(4),
    /**
     * 本地回调(相对子系统本身)
     */
    LOCAL_BACK(5),
    /**
     * 远程回调(相对子系统本身)
     */
    REMOTE_BACK(6),
    /**
     * 远程结果
     */
    REMOTE_RESULT(7)
    ;

    /**
     * 类型编号
     */
    private int typeNo;

    FssNotifyType(int typeNo) {
        this.typeNo = typeNo;
    }

    /**
     * 获取类型编号
     *
     * @return
     */
    public int getTypeNo() {
        return this.typeNo;
    }

    /**
     * 根据类型编号获取对应枚举类型
     *
     * @param typeNo
     * @return
     */
    public static FssNotifyType typeOf(int typeNo) {
        FssNotifyType type = null;
        for (FssNotifyType notifyType : values()) {
            if (notifyType.typeNo == typeNo) {
                type = notifyType;
                break;
            }
        }
        return type;
    }
}
