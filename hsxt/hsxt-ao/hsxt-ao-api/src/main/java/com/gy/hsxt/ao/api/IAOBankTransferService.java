/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gy.hsxt.ao.bean.TransOut;
import com.gy.hsxt.ao.bean.TransOutCustom;
import com.gy.hsxt.ao.bean.TransOutFail;
import com.gy.hsxt.ao.bean.TransOutQueryParam;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 银行转帐接口定义
 * 
 * @Package: com.gy.hsxt.ao.api
 * @ClassName: IAOBankTransferService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-26 下午2:32:35
 * @version V3.0.0
 */
public interface IAOBankTransferService {

    /**
     * 保存银行转帐记录
     * 
     * @param transOut
     *            银行转帐信息
     * @param accId
     *            银行账户ID
     * @throws HsException
     */
    public void saveTransOut(TransOut transOut, Long accId) throws HsException;

    /**
     * 查询银行转帐详情
     * 
     * @param transNo
     *            交易流水号
     * @return 银行转帐信息
     * @throws HsException
     */
    public TransOut getTransOutInfo(String transNo) throws HsException;

    /**
     * 查询银行转帐列表
     * 
     * @param transOutQueryParam
     *            包装银行转帐查询条件
     * @param page
     *            分页信息
     * @return 银行转帐列表
     * @throws HsException
     */
    public PageData<TransOut> getTransOutList(TransOutQueryParam transOutQueryParam, Page page) throws HsException;

    /**
     * 查询银行转帐列表统计信息
     * 
     * @param transOutQueryParam
     *            包装银行转帐查询条件
     * @return 统计信息
     * @throws HsException
     */
    public TransOutCustom getTransOutListCount(TransOutQueryParam transOutQueryParam) throws HsException;

    /**
     * 查询银行转帐对帐列表
     * 
     * @param transOutQueryParam
     *            包装查询条件
     * @param page
     *            分页信息
     * @return 银行转帐列表
     * @throws HsException
     */
    public PageData<TransOut> getCheckUpList(TransOutQueryParam transOutQueryParam, Page page) throws HsException;

    /**
     * 获取银行转帐失败列表
     * 
     * @param page
     *            分页信息
     * @return 成功返回银行转帐失败列表
     * @throws HsException
     */
    public PageData<TransOut> getTransOutFailList(Page page) throws HsException;

    /**
     * 导出银行转账数据
     * 
     * @param transOutQueryParam
     *            包装银行转帐查询条件
     * @return 银行转帐列表
     * @throws HsException
     */
    public String exportTransOutData(TransOutQueryParam transOutQueryParam) throws HsException;

    /**
     * 银行转账对账
     * 
     * @param transNos
     *            交易流水号列表
     * @return 被退票的银行转账记录列表
     * @throws HsException
     */
    public List<TransOut> transCheckUpAccount(List<String> transNos) throws HsException;

    /**
     * 转帐失败处理
     * 
     * @param transNos
     *            交易流水号列表
     * @return 无异常表示成功
     * @throws HsException
     */
    public List<TransOut> transFailBack(List<String> transNos) throws HsException;

    /**
     * 撤单
     * 
     * @param revokes
     *            撤单列表Map<String,String>key为transNo，value为备注
     * @param apprOptId
     *            操作员编号
     * @param apprOptName
     *            操作员名称
     * @param apprRemark
     *            备注
     * @throws HsException
     */
    public void transRevoke(Map<String, String> revokes, String apprOptId, String apprOptName, String apprRemark)
            throws HsException;

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

    /**
     * 查询银行转账失败详情
     * 
     * @param transNo
     *            交易流水号
     * @return 转账失败详情
     * @throws HsException
     */
    public TransOutFail getTransOutFailInfo(String transNo) throws HsException;

    /**
     * 查询销户退票列表
     * 
     * @return 销户退票列表
     * @throws HsException
     */
    public PageData<TransOut> getCloseTransBackList(Page page) throws HsException;

}
