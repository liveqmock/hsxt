/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.utils;

import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.gpf.bm.bean.ApplyRecord;
import com.gy.hsxt.gpf.bm.common.BMRespCode;
import com.gy.hsxt.gpf.fss.utils.FssDateUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * 参数校验工具
 *
 * @Package : com.gy.hsxt.gpf.bm.utils
 * @ClassName : ParamsUtil
 * @Description : 参数校验工具
 * @Author : chenm
 * @Date : 2016/1/9 18:32
 * @Version V3.0.0.0
 */
public abstract class ParamsUtil {

    /**
     * 校验及配置申报记录
     *
     * @param record 申报记录
     */
    public static void validateApplyRecord(ApplyRecord record) {
        HsAssert.notNull(record, BMRespCode.BM_PARAM_NULL_ERROR, "保存节点数据的申报信息不能为null");
        HsAssert.hasText(record.getAppCustId(), BMRespCode.BM_PARAM_EMPTY_ERROR, "被申报企业的父节点客户号（管理公司可以为互生号）[appCustId]为空");
        HsAssert.isTrue(record.getAppCustId().length() >= 11, BMRespCode.BM_PARAM_TYPE_ERROR, "被申报企业的父节点客户号（管理公司可以为互生号）[appCustId]不足11位");
        String appResNo = StringUtils.left(record.getAppCustId(), 11);//父节点互生号
        HsAssert.isTrue(HsResNoUtils.isManageResNo(appResNo) || PointIncUtil.allow(appResNo), BMRespCode.BM_PARAM_TYPE_ERROR, "被申报企业的父节点客户号（管理公司可以为互生号）[appCustId]不符合规则");
        HsAssert.hasText(record.getArea(), BMRespCode.BM_PARAM_EMPTY_ERROR, "增值区域（left||right）[area]为空");
        HsAssert.hasText(record.getFlag(), BMRespCode.BM_PARAM_EMPTY_ERROR, "是否跨区（0 跨库 1 非跨库）[flag]为空");
        HsAssert.hasText(record.getPopCustId(), BMRespCode.BM_PARAM_EMPTY_ERROR, "被申报企业客户号[popCustId]为空");
        HsAssert.hasText(record.getPopNo(), BMRespCode.BM_PARAM_EMPTY_ERROR, "被申报企业互生号[popNo]为空");
        HsAssert.isTrue(PointIncUtil.allow(record.getPopNo()), BMRespCode.BM_PARAM_TYPE_ERROR, "被申报企业互生号[popNo]不符合规则");
        record.setStatus("0");//处理状态
        if (StringUtils.isBlank(record.getManageCustId())) {
            record.setManageCustId(StringUtils.left(record.getPopNo(), 2) + "000000000");//管理公司互生号
        }
        if (StringUtils.isBlank(record.getAppDate())) {
            record.setAppDate(FssDateUtil.obtainToday(FssDateUtil.DATE_TIME_FORMAT));//申报日期
        }
        record.setType(HsResNoUtils.getCustTypeByHsResNo(record.getPopNo()));//企业类型
        //如果是管理公司，父节点设置为互生号
        if (HsResNoUtils.isManageResNo(appResNo)) {
            record.setAppCustId(appResNo);
        }
    }
}
