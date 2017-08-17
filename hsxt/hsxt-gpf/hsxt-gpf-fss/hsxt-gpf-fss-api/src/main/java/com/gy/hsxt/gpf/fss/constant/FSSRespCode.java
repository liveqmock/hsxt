/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.constant;

import com.gy.hsxt.common.constant.IRespCode;

/**
 * 文件同步系统---错误代码类
 * 号段：41000 ~ 41999
 * @Package :com.gy.hsxt.gpf.fss.constant
 * @ClassName : FSSRespCode
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/22 20:13
 * @Version V3.0.0.0
 */
public enum FSSRespCode implements IRespCode {

    /********************参数校验 错误码**********************/
    /**
     * 参数为null
     */
    PARAM_NULL_ERROR(41101),
    /**
     * 参数为空
     */
    PARAM_EMPTY_ERROR(41102),

    /**
     * 数据库中不存在
     */
    DB_NOT_EXIST_ERROR(41103),

    /********************数据库 错误码**********************/
    /**
     * 查询数据异常
     */
    QUERY_DATA_ERROR(41201),

    /**
     * 保存数据异常
     */
    SAVE_DATA_ERROR(41202),

    /**
     * 修改数据异常
     */
    MOD_DATA_ERROR(41203),

    /********************业务校验 错误码**********************/
    /**
     *文件数量错误
     */
    FILE_COUNT_ERROR(41301),

    /**
     * 文件大小错误
     */
    FILE_SIZE_ERROR(41302),

    /**
     * MD5检验码错误
     */
    MD5_CODE_ERROR(41303),

    /********************业务操作 错误码**********************/
    /**
     *文档下载错误
     */
    FILE_DOWNLOAD_ERROR(41401),

    /**
     * 文档上传错误
     */
    FILE_UPLOAD_ERROR(41402),

    /********************业务操作 错误码**********************/
    /**
     * 综合前置通知实体类型错误
     */
    UF_NOTIFY_BODY_TYPE_ERROR(41501)
    ;

    private int code;

    FSSRespCode(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return null;
    }


}
