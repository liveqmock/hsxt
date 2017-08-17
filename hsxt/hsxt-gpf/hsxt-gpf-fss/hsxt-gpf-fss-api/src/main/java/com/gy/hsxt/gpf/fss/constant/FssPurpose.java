/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.constant;

/**
 * @Package :com.gy.hsxt.gpf.fss.constant
 * @ClassName : FssPurpose
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/21 19:46
 * @Version V3.0.0.0
 */
public enum FssPurpose implements IfssPurpose{
    /**
     * 增值系统：
     * 再增值积分汇总统计用（业务系统和账务系统）
     * 1、各个平台业务系统统计的服务公司申报企业数量
     * 2、各个平台账务系统统计的托管企业和成员企业赠送积分总数
     * 3、再增值积分汇总统计结果（业务系统）
     */
    BM_BMLM("bmlm"),
    /**
     * 增值系统：
     * 增值积分汇总统计结果
     */
    BM_MLM("mlm")
    ;


    private String purposeCode;

    FssPurpose(String purposeCode) {
        this.purposeCode = purposeCode;
    }

    /**
     * 获取用途代码
     *
     * @return
     */
    @Override
    public String getCode() {
        return purposeCode;
    }

    /**
     * 根据代码获取对应类型
     *
     * @param code
     * @return
     */
    public static FssPurpose typeOf(String code) {
        for (FssPurpose purpose : FssPurpose.values()) {
            if (purpose.getCode().equals(code)) {
                return purpose;
            }
        }
        return null;
    }

}
