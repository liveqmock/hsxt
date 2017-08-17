/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.interfaces;

import java.util.Set;

import com.gy.hsxt.common.exception.HsException;

/**
 * 银行转帐内部接口定义
 * 
 * @Package: com.gy.hsxt.ao.interfaces
 * @ClassName: ITransOutService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-12-2 下午2:18:44
 * @version V3.0.0
 */
public interface ITransOutService {
    /**
     * 保存银行转帐失败记录
     * 
     * @param originalTransNo
     *            原始转账交易单号
     * @param optCustId
     *            操作员编号
     * @param optCustName
     *            操作员名称
     * @param remark
     *            处理说明
     * @param reviewResult
     *            复核结果
     * @param transResul
     *            转账结果
     */
    public void saveTransOutFail(String originalTransNo, String optCustId, String optCustName, String remark,
            Integer reviewResult, Integer transResult) throws HsException;

    /**
     * 批量提交转帐
     * 
     * @param transNos
     *            交易流水号列表
     * @param commitType
     *            提交类型
     * @param apprOptId
     *            复核操作员编号
     * @param apprOptName
     *            复核操作员名称
     * @param apprRemark
     *            复核备注
     * @throws HsException
     */
    public void transBatch(Set<String> transNos, Integer commitType, String apprOptId, String apprOptName,
            String apprRemark) throws HsException;
}
