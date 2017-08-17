/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.interfaces;

import java.util.List;

import com.gy.hsxt.bs.bean.apply.DeclareInfo;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @Package: com.gy.hsxt.bs.apply.interfaces
 * @ClassName: IResFeeService
 * @Description: 申报资源费分解
 * 
 * @author: xiaofl
 * @date: 2015-12-16 下午6:06:02
 * @version V1.0
 */
public interface IResFeeResolveService {

    /**
     * 资源费分解
     * 
     * @param declareInfo
     *            申报信息
     * @throws HsException
     */
    public void resolveResFee(DeclareInfo declareInfo) throws HsException;

    /**
     * 对一个申报单进行分配记账
     * @param applyId 申报编号
     * @param fiscalDate 会计日期
     */
    void accoutingForApply(String applyId, String fiscalDate);

    /**
     * 获取全部未完成分配的申报单号
     * @return
     */
    List<String> queryUnAllocApplyIds();
}
