/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.common;

import com.gy.hsxt.common.constant.IRespCode;

/**
 * 增值系统的错误码
 * 号段 14000~14999	BM	增值系统
 *
 * @Package :com.gy.hsxt.gpf.bm.common
 * @ClassName : BMRespCode
 * @Description : 增值系统的错误码
 * @Author : chenm
 * @Date : 2015/9/30 13:17
 * @Version V3.0.0.0
 */
public enum BMRespCode implements IRespCode {

    /**
     * 参数为null
     */
   BM_PARAM_NULL_ERROR(14001),
    /**
     * 参数为空
     */
   BM_PARAM_EMPTY_ERROR(14002),
    /**
     * 参数类型错误
     */
    BM_PARAM_TYPE_ERROR(14003),
    /**
     * 有效增值节点已存在
     */
    BM_ICN_NODE_EXIST(14004),
    /**
     * 查询数据异常
     */
   BM_QUERY_DATA_ERROR(14101),
    /**
     * 保存增值节点数据异常
     */
   BM_SAVE_MLM_ERROR(14201),
    /**
     * 保存再增值节点数据异常
     */
   BM_SAVE_BMLM_ERROR(14202),

    /**
     * 保存操作记录异常
     */
   BM_SAVE_OPER_ERROR(14203),

    /**
     * 保存申报记录异常
     */
    BM_SAVE_APPLY_RECORD_ERROR(14204),

    /**
     * 删除数据
     */
    BM_DELETE_DATA_ERROR(14401),
    /**
     * 文件解析异常
     */
   BM_FILE_PARSE_ERROR(14701),
    /**
     * 增值系统异常
     */
    BM_SYSTEM_ERROR(14998),
    /**
     * 未知异常
     */
    BM_UNKNOWN_ERROR(14999);


    /**
     * 异常代码
     */
    private int code;

    /**
     * 构造函数
     * @param code
     */
    BMRespCode(int code) {
        this.code = code;
    }

    /**
     * 获取异常代码
     * @return
     */
    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return null;
    }


}
