/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.gpf.gcs;

import java.util.HashSet;
import java.util.Set;

import com.gy.hsxt.common.constant.IRespCode;

/**
 * @Description:GCS错误码定义，错误码范围：45000~45999
 * 
 * @Package: com.gy.hsxt.gpf.gcs
 * @ClassName: GCSErrorCode
 * 
 * @author: yangjianguo
 * @date: 2016-1-13 上午11:02:31
 * @version V1.0
 */
public enum GCSErrorCode implements IRespCode {
    /** 数据重复 **/
    DATA_DUPLICATE(45000, "数据重复"),
    /** 数据操作失败 **/
    DATA_OPERATE_FAIL(45001, "数据操作失败"),
    /** 数据没有找到 **/
    DATA_NOT_FOUND(45002, "数据没有找到"),

    ;
    /**
     * 错误代码
     */
    private int errorCode;

    /**
     * 错误描述
     */
    private String erroDesc;

    GCSErrorCode(int code, String desc) {
        this.errorCode = code;
        this.erroDesc = desc;
    }

    /**
     * @return
     * @see com.gy.hsxt.common.constant.IRespCode#getCode()
     */
    @Override
    public int getCode() {
        return errorCode;
    }

    /**
     * @return
     * @see com.gy.hsxt.common.constant.IRespCode#getDesc()
     */
    @Override
    public String getDesc() {
        return erroDesc;
    }

    public static void main(String[] args)

    {
        /**
         * 检查代码是否有重复
         */
        Set<Integer> codeSet = new HashSet<Integer>();
        for (GCSErrorCode item : GCSErrorCode.values())
        {
            System.out.println("[" + item.getCode() + ":" + item.getDesc() + "]");
            if (codeSet.contains(item.getCode()))
            {
                System.err.println(item.name() + ":" + item.getCode() + " 代码有重复冲突，请修改！");
            }
            else
            {
                codeSet.add(item.getCode());
            }
        }
    }

}
