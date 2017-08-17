/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.common.enumtype.invoice;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.utils.HsAssert;

/**
 * @Package :com.gy.hsxt.bs.common.enumtype.invoice
 * @ClassName : BizType
 * @Description :
 * <p>企业向平台开发票(在完成设置税率前)：消费积分分配、积分互生分配、积分再增值分配、劳务费、商业服务费</p>
 * <p>平台向企业开发票：系统资源费（扣除兑换互生币的费用）、申购工具金额（新增申购）、系统使用年费、外卖月租费、消费积分扣除（减除消费积分撤单）使用互生币累计金额</p>
 * @Author : chenm
 * @Date : 2015/12/14 20:40
 * @Version V3.0.0.0
 */
public enum BizType {

    //平台向企业要开发票的业务
    /**
     * 服务公司系统资源费
     */
    PC_S_RES_FEE("PC001"),
    /**
     * 申购工具
     */
    PC_BUY_TOOL("PC002"),
    /**
     * 使用年费
     */
    PC_ANNUAL_FEE("PC003"),
    /**
     * 外卖月租费
     */
    PC_TAKE_OUT("PC004"),
    /**
     * 消费积分
     */
    PC_CONSUME_POINTS("PC005"),

    /**
     * 个性卡制定服务费
     */
    PC_SPECIAL_CARD("PC006"),

    /**
     * 成员企业系统资源费
     */
    PC_M_RES_FEE("PC007"),

    /**
     * 首段托管企业系统资源费
     */
    PC_T_F_RES_FEE("PC008"),

    /**
     * 创业托管企业系统资源费
     */
    PC_T_C_RES_FEE("PC009"),

    /**
     * 托管企业系统资源费(全部资源)
     */
    PC_T_A_RES_FEE("PC010"),

    /**
     * 其他段托管企业系统资源费
     */
    PC_T_R_RES_FEE("PC011"),

    //企业向平台开发票的业务类型
    /**
     * 企业向平台开发票总业务/包括所有业务
     */
    CP_ALL_INVOICE("CP101"),
    ;


    /**
     * 业务类型的代码
     */
    private String bizCode;

    BizType(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getBizCode() {
        return bizCode;
    }

    /**
     * 校验业务代码
     *
     * @param bizCode 业务代码
     * @return boolean
     */
    public static boolean check(String bizCode) {
        HsAssert.hasText(bizCode, RespCode.PARAM_ERROR, "校验发票业务代码的参数[bizCode]为空");
        for (BizType bizType : BizType.values()) {
            if (bizType.getBizCode().equals(bizCode)) {
                return true;
            }
        }
        return false;
    }
}
