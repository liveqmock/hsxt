/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ac.api.IAccountSearchService;
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ao.AOErrorCode;
import com.gy.hsxt.ao.api.IAOBankTransferService;
import com.gy.hsxt.ao.bean.ExcelExportData;
import com.gy.hsxt.ao.bean.PropertyConfigurer;
import com.gy.hsxt.ao.bean.TransOut;
import com.gy.hsxt.ao.bean.TransOutCustom;
import com.gy.hsxt.ao.bean.TransOutFail;
import com.gy.hsxt.ao.bean.TransOutQueryParam;
import com.gy.hsxt.ao.common.BizType;
import com.gy.hsxt.ao.common.PageContext;
import com.gy.hsxt.ao.disconf.AoConfig;
import com.gy.hsxt.ao.enumtype.CommitType;
import com.gy.hsxt.ao.enumtype.ProcessMethod;
import com.gy.hsxt.ao.enumtype.ProcessResult;
import com.gy.hsxt.ao.enumtype.TransOutResult;
import com.gy.hsxt.ao.enumtype.TransReason;
import com.gy.hsxt.ao.enumtype.TransStatus;
import com.gy.hsxt.ao.interfaces.IAccountRuleService;
import com.gy.hsxt.ao.interfaces.IAccountingService;
import com.gy.hsxt.ao.interfaces.ICommonService;
import com.gy.hsxt.ao.interfaces.IDubboInvokService;
import com.gy.hsxt.ao.interfaces.ITransOutNotifyService;
import com.gy.hsxt.ao.interfaces.ITransOutService;
import com.gy.hsxt.ao.mapper.TransOutFailMapper;
import com.gy.hsxt.ao.mapper.TransOutMapper;
import com.gy.hsxt.ao.util.ExcelUtil;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.Channel;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gp.api.IGPNotifyService;
import com.gy.hsxt.gp.api.IGPTransCashService;
import com.gy.hsxt.gp.bean.TransCash;
import com.gy.hsxt.gp.bean.TransCashOrderState;
import com.gy.hsxt.gp.constant.GPConstant;
import com.gy.hsxt.lcs.bean.City;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uc.as.api.common.IUCAsNetworkInfoService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderAuthInfoService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.common.AsNetworkInfo;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameAuth;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;
import com.gy.hsxt.uc.bs.api.common.IUCBsBankAcctInfoService;
import com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderStatusInfoService;
import com.gy.hsxt.uc.bs.bean.common.BsBankAcctInfo;
import com.gy.hsxt.uc.bs.enumerate.UserTypeEnum;

/**
 * 银行转账接口实现类
 * 
 * @Package: com.gy.hsxt.ao.service
 * @ClassName: BankTransferService
 * @Description:
 * 
 * @author: kongsl
 * @date: 2015-11-30 下午5:59:46
 * @version V3.0.0
 */
@Service
public class BankTransferService implements IAOBankTransferService, ITransOutService, ITransOutNotifyService {
    /**
     * 帐户操作私有配置参数
     **/
    @Resource
    AoConfig aoConfig;

    /**
     * 注入记帐分解对象
     */
    @Resource
    IAccountingService accountingService;

    /**
     * dubbo调用服务对象
     */
    @Resource
    IDubboInvokService dubboInvokService;

    /**
     * 银行转账mapper
     */
    @Resource
    TransOutMapper transOutMapper;

    /**
     * 银行转账失败mapper
     */
    @Autowired
    TransOutFailMapper transOutFailMapper;

    /**
     * 用户中心：银行账户管理
     */
    @Resource
    IUCBsBankAcctInfoService ucBankAcctInfoService;

    /**
     * 用户中心：查询重要信息变更状态
     */
    @Resource
    IUCBsCardHolderStatusInfoService ucCardHolderStatusInfoService;

    /**
     * 用户中心：查询企业重要信息
     */
    @Resource
    IUCAsEntService ucAsEntService;

    /**
     * 用户中心：查询非持卡人信息
     */
    @Resource
    IUCAsNetworkInfoService ucNetworkInfoService;

    /**
     * 用户中心：查询持卡人认证信息
     */
    @Resource
    IUCAsCardHolderAuthInfoService ucCardHolderAuthInfoService;

    /**
     * 帐务系统：帐户查询
     */
    @Resource
    IAccountSearchService accountSearchService;

    /**
     * 支付系统：单笔提现接口
     */
    @Resource
    IGPTransCashService gpTransCashService;

    /**
     * 支付系统：通知接口
     */
    @Resource
    IGPNotifyService gpNotifyService;

    /**
     * 公共服务接口
     */
    @Resource
    ICommonService commonService;

    /**
     * 参数配置系统：帐户规则服务
     */
    @Resource
    IAccountRuleService accountRuleService;

    /** 地区平台配送Service **/
    @Autowired
    private LcsClient baseDataService;

    // 声明锁对象，主要用于重复通知支付成功时重复记帐问题
    Lock lock = new ReentrantLock();

    /**
     * 保存银行转账记录
     * 
     * @param transOut
     *            银行转账信息
     * @param accId
     *            银行账户ID
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOBankTransferService#saveTransOut(com.gy.hsxt.ao.bean.TransOut)
     */
    @Override
    @Transactional
    public void saveTransOut(TransOut transOut, Long accId) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "保存银行转账记录,params[" + transOut + ",accId:" + accId + "]");
        // 实体参数为空
        HsAssert.notNull(transOut, AOErrorCode.AO_PARAMS_NULL, "保存银行转账记录：实体参数为空");
        // 银行账户ID为空
        HsAssert.notNull(accId, AOErrorCode.AO_PARAMS_NULL, "保存银行转账记录：银行帐户ID参数为空");
        // 成功记录数
        int returnNum = 0;
        // 手续费金额
        String bankfeeAmount = "0";
        // 是否符合规则转账
        boolean isTransCash = false;
        // 是否大额转账
        boolean isBigAmount = false;
        // 银行账户信息对象
        BsBankAcctInfo bankAcctInfo = null;
        try
        {
            // 校验客户状态
            dubboInvokService.checkCustInfo(transOut.getCustType(), transOut.getCustId(), BizType.CASH_TO_BANK
                    .getCode(), true, true, true);

            // 校验客户名与银行帐户名是否一致
            bankAcctInfo = getBankAcctInfo(transOut, accId);
            // 校验银行卡持卡人姓名与认证名称一致
            verifyBankCardNameEqCertif(transOut.getCustId(), transOut.getCustType(), bankAcctInfo.getBankAccName());

            // 转账原因为空时，设置默认转账原因
            if (transOut.getTransReason() == null)
            {
                transOut.setTransReason(TransReason.ACCORD_CASH.getCode());
            }
            // 转账原因为销户时，不进行限额限次规则检查
            if (TransReason.CLOSE_ACCOUNT.getCode().intValue() == transOut.getTransReason())
            {
                isTransCash = true;
            }
            else
            {
                // 校验货币转银行是否符合限额限次规则
                isTransCash = accountRuleService.checkCashToBankRule(transOut.getCustId(), transOut.getCustType(),
                        transOut.getAmount());
                // 校验帐户余额并计算出手续费（主动提现才校验）
                bankfeeAmount = verifyTransBalanceToAc(transOut, bankAcctInfo);
            }
            // 可转账
            if (isTransCash)
            {
                // 设置银行帐户ID
                transOut.setAccId(accId);
                // 初始化转账数据
                initTransData(transOut, bankfeeAmount, bankAcctInfo);
                // 检验是否大额转账
                isBigAmount = verifyBigAmount(transOut);

                // 转账金额大于大额金额需要平台审批
                if (isBigAmount)
                {
                    // 大额转账状态初始化为：1（申请中）
                    transOut.setTransStatus(TransStatus.APPLYING.getCode());

                    // 插入银行转账记录
                    returnNum = transOutMapper.insertTransOut(transOut);

                    // 转账记录保存成功
                    if (returnNum > 0)
                    {
                        // 更新累计转账金额、转账次数等
                        accountRuleService.afterCashToBank(transOut.getCustId(), transOut.getCustType(), transOut
                                .getAmount());
                        // 执行银行转账记帐分解
                        accountingService.saveAccounting(transOut);
                    }
                    BizLog.biz(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                            "params", "保存银行转账记录，大额转账需要审批[" + transOut + "]");
                }
                else
                // 不需要平台审批的
                {
                    // 转账状态：2（付款中）
                    transOut.setTransStatus(TransStatus.PAYING.getCode());
                    // 设置提交类型为单笔自动提交
                    transOut.setCommitType(CommitType.ONE_AUTO.getCode());

                    // 插入银行转账记录
                    returnNum = transOutMapper.insertTransOut(transOut);
                    // 大于0表示插入成功，执行记帐分解
                    if (returnNum > 0)
                    {
                        // 更新累计转账金额、转账次数等
                        accountRuleService.afterCashToBank(transOut.getCustId(), transOut.getCustType(), transOut
                                .getAmount());
                        // 执行银行转账记帐分解
                        accountingService.saveAccounting(transOut);
                        // 调用支付系统进行转账
                        dubboInvokService.commitTransCash(transOut);
                    }
                    BizLog.biz(this.getClass().getName(), "method:"
                            + Thread.currentThread().getStackTrace()[1].getMethodName(), "params", "保存银行转账记录，不需要平台审批["
                            + transOut + "]");
                }
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(aoConfig.getSysName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_SAVE_TRANS_OUT_ERROR
                    .getCode()
                    + ":保存银行转账记录异常,param=" + JSON.toJSONString(transOut), e);
            throw new HsException(AOErrorCode.AO_SAVE_TRANS_OUT_ERROR.getCode(), "保存银行转账记录异常,param="
                    + JSON.toJSONString(transOut) + "\n" + e);
        }
    }

    /**
     * 保存银行转账失败记录
     * 
     * @param originalTransNo
     *            原始转账交易单号
     * @param optCustId
     *            操作员编号
     * @param optCustName
     *            操作员名称
     * @param remark
     *            处理说明
     * @param transReason
     *            转账原因
     * @see com.gy.hsxt.ao.interfaces.ITransOutService#saveTransOutFail(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.Integer)
     */
    @Override
    public void saveTransOutFail(String originalTransNo, String optCustId, String optCustName, String remark,
            Integer transReason, Integer transResult) {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "保存银行转账失败记录,params[originalTransNo:" + originalTransNo + ",optCustId:" + optCustId + ",optCustName:"
                        + optCustName + ",remark:" + remark + ",reviewResult:" + transReason + "]");
        try
        {
            // 初始化转账失败数据
            TransOutFail transOutFail = new TransOutFail();
            transOutFail.setTransNo(GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode()));// 记录编号
            transOutFail.setOrigTransNo(originalTransNo);// 原始转账交易单号
            transOutFail.setReturnFee(TransStatus.BANK_BACK.getCode().intValue() == transResult ? false : true);// 手续费是否要退还(业务确定银行退票平台不退还手续)
            // 根据转账原因设置处理方式
            transOutFail.setProcessMode(TransReason.ACCORD_CASH.getCode().intValue() == transReason
                    ? ProcessMethod.AUTO_BACK.getCode() : ProcessMethod.OFFLINE_PROCESS.getCode());// 处理方式
            // 转账原因为销户退款，设置处理结果为已处理
            if (TransReason.CLOSE_ACCOUNT.getCode().intValue() == transReason)
            {
                transOutFail.setProcessResult(ProcessResult.PROCESSED.getCode());// 处理结果
            }
            else
            {// 默认情况，转账失败都需要线下人工处理退回
                transOutFail.setProcessResult(ProcessResult.WAIT_PROCESS.getCode());// 处理结果
            }
            transOutFail.setOptCustId(optCustId);// 操作员编号
            transOutFail.setOptCustName(optCustName);// 操作员名称
            transOutFail.setRemark(remark);// 处理说明

            // 插入转账失败记录
            transOutFailMapper.insertTransOutFail(transOutFail);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_SAVE_TRANS_OUT_FAIL_ERROR.getCode() + ":保存银行转账失败记录异常,param=[originalTransNo:"
                            + originalTransNo + ",optCustId:" + optCustId + ",optCustName:" + optCustName + ",remark:"
                            + remark + ",reviewResult:" + transReason + "]", e);
            throw new HsException(AOErrorCode.AO_SAVE_TRANS_OUT_FAIL_ERROR.getCode(),
                    "保存银行转账失败记录异常,param=[originalTransNo:" + originalTransNo + ",optCustId:" + optCustId
                            + ",optCustName:" + optCustName + ",remark:" + remark + ",reviewResult:" + transReason
                            + "]" + e);
        }
    }

    /**
     * 查询银行转账详情
     * 
     * @param transNo
     *            交易流水号
     * @return 银行转账信息
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOBankTransferService#getTransOutInfo(java.lang.String)
     */
    @Override
    public TransOut getTransOutInfo(String transNo) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "查询银行转账详情,params[transNo:" + transNo + "]");
        // 参数为空
        HsAssert.hasText(transNo, AOErrorCode.AO_PARAMS_NULL, "查询银行转账详情：交易流水号为空");
        try
        {
            // 查询银行转账详情
            return transOutMapper.findTransOutInfo(transNo);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_QUERY_TRANS_OUT_DETAIL_ERROR.getCode() + ":查询银行转账详情异常,param=[transNo:" + transNo
                            + "]", e);
            throw new HsException(AOErrorCode.AO_QUERY_TRANS_OUT_DETAIL_ERROR.getCode(), "查询银行转账详情异常,param=[transNo:"
                    + transNo + "]\n" + e);
        }
    }

    /**
     * 查询银行转账列表
     * 
     * @param transOutQueryParam
     *            包装银行转账查询条件
     * @param page
     *            分页信息
     * @return 银行转账列表
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOBankTransferService#getTransOutList(com.gy.hsxt.ao.bean.TransOutQueryParam,
     *      com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<TransOut> getTransOutList(TransOutQueryParam transOutQueryParam, Page page) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "查询银行转账列表,params[" + transOutQueryParam + "]");
        // 分页参数为空
        HsAssert.notNull(page, AOErrorCode.AO_PARAMS_NULL, "查询银行转账列表：分页参数为空");

        PageData<TransOut> transOutListPage = null;
        // 设置分页信息
        PageContext.setPage(page);
        // 设置查询日期格式
        transOutQueryParam.setQueryDate();
        try
        {
            // 执行查询
            List<TransOut> transOutList = transOutMapper.findTransOutListPage(transOutQueryParam);
            // 验证查询结果
            if (transOutList != null && transOutList.size() > 0)
            {
                // 为公用分页查询设置查询结果集
                transOutListPage = new PageData<TransOut>(page.getCount(), transOutList);
            }
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_QUERY_TRANS_OUT_LIST_ERROR.getCode() + ":查询银行转账列表异常,param="
                            + JSON.toJSONString(transOutQueryParam), e);
            throw new HsException(AOErrorCode.AO_QUERY_TRANS_OUT_LIST_ERROR.getCode(), "查询银行转账列表异常,param="
                    + JSON.toJSONString(transOutQueryParam) + "\n" + e);
        }
        return transOutListPage;
    }

    /**
     * 查询银行转账列表统计信息
     * 
     * @param transOutQueryParam
     *            包装银行转账查询条件
     * @return 统计信息
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOBankTransferService#getTransOutListCount(com.gy.hsxt.ao.bean.TransOutQueryParam)
     */
    @Override
    public TransOutCustom getTransOutListCount(TransOutQueryParam transOutQueryParam) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "查询银行转账列表统计信息,params[" + transOutQueryParam + "]");
        // 设置查询日期格式
        transOutQueryParam.setQueryDate();
        TransOutCustom transOutCustom = transOutMapper.findTransOutListCount(transOutQueryParam);
        if (transOutCustom == null)
        {
            transOutCustom = new TransOutCustom(0, "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
        }
        return transOutCustom;
    }

    /**
     * 查询银行转账对帐列表
     * 
     * @param transOutQueryParam
     *            包装查询条件
     * @param page
     *            分页信息
     * @return 银行转账列表
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOBankTransferService#getCheckUpList(com.gy.hsxt.ao.bean.TransOutQueryParam,
     *      com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<TransOut> getCheckUpList(TransOutQueryParam transOutQueryParam, Page page) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "查询银行转账对帐列表,params[" + transOutQueryParam + "]");
        // 分页参数为空
        HsAssert.notNull(page, AOErrorCode.AO_PARAMS_NULL, "查询银行转账对帐列表：分页参数为空");
        PageData<TransOut> transOutListPage = null;
        // 设置分页信息
        PageContext.setPage(page);
        // 设置查询日期格式
        transOutQueryParam.setQueryDate();
        try
        {
            // 执行查询
            List<TransOut> transOutList = transOutMapper.findCheckUpListPage(transOutQueryParam);
            // 验证查询结果
            if (transOutList != null && transOutList.size() > 0)
            {
                // 为公用分页查询设置查询结果集
                transOutListPage = new PageData<TransOut>(page.getCount(), transOutList);
            }
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_QUERY_TRANS_OUT_LIST_ERROR.getCode() + ":查询银行转账对帐列表异常,param="
                            + JSON.toJSONString(transOutQueryParam), e);
            throw new HsException(AOErrorCode.AO_QUERY_TRANS_OUT_LIST_ERROR.getCode(), "查询银行转账对帐列表异常,param="
                    + JSON.toJSONString(transOutQueryParam) + "\n" + e);
        }
        return transOutListPage;
    }

    /**
     * 获取银行转账失败列表
     * 
     * @param page
     *            分页信息
     * @return 成功返回银行转账失败列表
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOBankTransferService#getTransOutFailList(com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<TransOut> getTransOutFailList(Page page) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "查询银行转账失败列表,params[]");
        // 分页参数为空
        HsAssert.notNull(page, AOErrorCode.AO_PARAMS_NULL, "查询银行转账失败列表：分页参数为空");
        PageData<TransOut> transOutListPage = null;
        // 设置分页信息
        PageContext.setPage(page);
        try
        {
            // 执行查询
            List<TransOut> transOutList = transOutMapper.findTransFailListPage();
            // 验证查询结果
            if (transOutList != null && transOutList.size() > 0)
            {
                // 为公用分页查询设置查询结果集
                transOutListPage = new PageData<TransOut>(page.getCount(), transOutList);
            }
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_QUERY_TRANS_OUT_LIST_ERROR.getCode() + ":查询银行转账失败列表异常", e);
            throw new HsException(AOErrorCode.AO_QUERY_TRANS_OUT_LIST_ERROR.getCode(), "查询银行转账失败列表异常" + e);
        }
        return transOutListPage;
    }

    /**
     * 查询银行转账失败详情
     * 
     * @param transNo
     *            交易流水号
     * @return 转账失败详情
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOBankTransferService#getTransOutFailInfo(java.lang.String)
     */
    @Override
    public TransOutFail getTransOutFailInfo(String transNo) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "查询银行转账失败详情,params[transNo:" + transNo + "]");
        HsAssert.hasText(transNo, AOErrorCode.AO_PARAMS_NULL, "查询银行转账失败详情：交易流水号参数为空");
        return transOutFailMapper.findTransFailDetail(transNo);
    }

    /**
     * 查询销户退票列表
     * 
     * @return 销户退票列表
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOBankTransferService#getCloseTransBackList(com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<TransOut> getCloseTransBackList(Page page) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "查询销户退票列表,params[]");
        // 分页参数为空
        HsAssert.notNull(page, AOErrorCode.AO_PARAMS_NULL, "查询销户退票列表：分页参数为空");
        PageData<TransOut> transOutListPage = null;
        // 设置分页信息
        PageContext.setPage(page);
        // 执行查询
        List<TransOut> transOutList = transOutMapper.findCloseTransBackListPage();

        // 验证查询结果
        if (transOutList != null && transOutList.size() > 0)
        {
            // 为公用分页查询设置查询结果集
            transOutListPage = new PageData<TransOut>(page.getCount(), transOutList);
        }
        return transOutListPage;
    }

    /**
     * 导出银行转账数据
     * 
     * @param transOutQueryParam
     *            包装银行转账查询条件
     * @return 银行转账列表
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOBankTransferService#exportTransOutData(com.gy.hsxt.ao.bean.TransOutQueryParam)
     */
    @Override
    public String exportTransOutData(TransOutQueryParam transOutQueryParam) throws HsException {
        /**
         * 导出最大数量值
         */
        int exportMaxRow = 60000;

        String dirRoot = PropertyConfigurer.getProperty("export.savePath");

        String newDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

        String timeDate = new SimpleDateFormat("yyyy-MM-dd HHmmss").format(new Date());

        //
        String excelFileName = "银行转账数据列表" + timeDate;

        // 文件地址
        String fileAddress = dirRoot + File.separator + "AO" + File.separator + "EXPORT" + File.separator + newDate
                + File.separator + excelFileName + ".xls";

        // 导出数据列名
        List<String[]> columNames = new ArrayList<String[]>();
        columNames.add(new String[] { "交易流水号", "互生号/手机号", "单位名称", "申请时间", "转账金额", "银行扣除手续费", "银行流水号", "状态", "状态日期",
                "提交类型" });

        // 导出数据对应数据字段
        List<String[]> fieldNames = new ArrayList<String[]>();
        fieldNames.add(new String[] { "transNo", "hsResNo", "custName", "reqTime", "amount", "feeAmt", "bankTransNo",
                "transStatusName", "resultTime", "commitTypeName" });

        LinkedHashMap<String, List<?>> map = new LinkedHashMap<String, List<?>>();
        // 设置查询日期格式
        transOutQueryParam.setQueryDate();
        try
        {
            // 执行查询
            List<TransOut> transOutList = transOutMapper.findTransOutList(transOutQueryParam);
            if (transOutList != null && transOutList.size() > exportMaxRow)
            {
                // 导出数量上限
                HsAssert.isTrue(true, AOErrorCode.AO_EXPORT_EXCEL_MORE_THAN_MAX_NUM, "导出Excel超出最大条数");
            }
            map.put("银行转账数据", transOutList);

            ExcelExportData setInfo = new ExcelExportData();
            setInfo.setDataMap(map);
            setInfo.setFieldNames(fieldNames);
            setInfo.setTitles(new String[] { "银行转账数据列表" });
            setInfo.setColumnNames(columNames);

            ExcelUtil.export2File(setInfo, fileAddress);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_EXPORT_TRANS_OUT_EXCEL_DATA_ERROR.getCode() + ":导出银行转账Excel数据异常,param="
                            + JSON.toJSONString(transOutQueryParam), e);
            throw new HsException(AOErrorCode.AO_EXPORT_TRANS_OUT_EXCEL_DATA_ERROR.getCode(), "导出银行转账Excel数据异常,param="
                    + JSON.toJSONString(transOutQueryParam) + "\n" + e);
        }

        return fileAddress;
    }

    /**
     * 转账失败处理（即退款业务）
     * 
     * @param transNos
     *            原交易流水号列表
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOBankTransferService#transFailBack(java.util.List)
     */
    @Override
    @Transactional
    public List<TransOut> transFailBack(List<String> transNos) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "转账失败处理,params[" + JSON.toJSONString(transNos) + "]");
        // 交易流水号为空
        HsAssert.isTrue(!transNos.isEmpty(), AOErrorCode.AO_PARAMS_NULL, "转账失败处理：交易流水号列表为空");
        List<TransOut> failBackTransList = new ArrayList<TransOut>();
        TransOut transOut = null;
        String transFailNo = "";
        int returnNum = 0;
        try
        {
            for (String transNo : transNos)
            {
                // 还未处理的才执行
                if (isNotRefund(transNo))
                {
                    // 查询转账业务数据
                    transOut = transOutMapper.findTransOutInfo(transNo);
                    // 查询原交易对应的失败流水号
                    transFailNo = transOutFailMapper.findTransFailNo(transNo);
                    if (StringUtils.isNotBlank(transFailNo))
                    {
                        // 重新设置失败记帐流水号为失败流水号
                        transOut.setTransFailNo(transFailNo);
                    }
                    // 更新转账状态为转账退款完成
                    returnNum = transOutMapper.updateTransFailBackStatus(transNo, TransStatus.BANK_BACK_COMPLETE
                            .getCode());

                    if (returnNum > 0)
                    {
                        // 更新转账失败处理结果
                        transOutFailMapper.updateProcessResult(transNo, ProcessResult.PROCESSED.getCode());
                        // 记账分解
                        accountingService.saveAccounting(transOut);
                        // 添加处理成功数据到返回列表
                        failBackTransList.add(transOut);
                    }
                }
                else
                {
                    HsAssert.isTrue(transNos.size() != 1, AOErrorCode.AO_NOT_QUERY_DATA,
                            "转账失败处理：未查询到该交易流水号的待退款记录,params[transNo:" + transNo + "]");
                }
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_TRANS_OUT_FAIL_BACK_ERROR.getCode() + ":转账失败处理异常,param="
                            + JSON.toJSONString(transNos), e);
            throw new HsException(AOErrorCode.AO_TRANS_OUT_FAIL_BACK_ERROR.getCode(), "转账失败处理异常,param="
                    + JSON.toJSONString(transNos) + "\n" + e);
        }
        return failBackTransList;
    }

    /**
     * 银行转账对账
     * 
     * @param transNos
     *            交易流水号列表
     * @return 被退票的银行转账记录列表
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOBankTransferService#transCheckUpAccount(java.util.List)
     */
    @Override
    @Transactional
    public List<TransOut> transCheckUpAccount(List<String> transNos) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "银行转账对账,params[" + JSON.toJSONString(transNos) + "]");
        // 交易流水号列表为空
        HsAssert.notNull(transNos, AOErrorCode.AO_PARAMS_NULL, "银行转账对账:交易流水号列表为空");
        List<TransOut> checkUpTransList = null;
        // 转账通知结果状态对象
        TransCashOrderState transOrderState = null;
        // 对帐结果列表
        List<TransCashOrderState> transCashOrderStateList = null;
        TransOut transOut = null;
        try
        {
            // 交易流水号列表不为空
            if (transNos != null && transNos.size() > 0)
            {
                // 支付系统最大只支付50个对象
                HsAssert.isTrue(transNos.size() < aoConfig.getTransCashBatchNum(), AOErrorCode.AO_MAX_LIMIT,
                        "批量银行转账对账：对账数量超过最大限制" + aoConfig.getTransCashBatchNum());
                checkUpTransList = new ArrayList<TransOut>();
                // 调用支付系统批量提现结果查询
                transCashOrderStateList = gpTransCashService.getBatchTransCashOrderStates(aoConfig.getMerchantNo(),
                        transNos);
                // 对帐结果列表不为空
                if (transCashOrderStateList != null && transCashOrderStateList.size() > 0)
                {
                    // 循环对帐列表
                    for (int i = 0; i < transCashOrderStateList.size(); i++)
                    {
                        transOrderState = transCashOrderStateList.get(i);
                        if (transOrderState != null)
                        {
                            transOut = transOutMapper.findTransOutInfo(transOrderState.getOrderNo());
                            // 未收到通知，对帐时查询到的交易状态为成功
                            if (transOrderState.getTransStatus() == TransOutResult.TRANS_CASH_OUT.getCode()// 支付系统状态：转账成功
                                    && TransStatus.PAYING.getCode().intValue() == transOut.getTransStatus() // 转账状态为:付款中
                                    && (TransOutResult.TRANS_CASH_PRE.getCode().intValue() == transOut.getTransResult() // 转账结果：待提交银行
                                    || TransOutResult.REFUNDED.getCode().intValue() == transOut.getTransResult())// 转账结果：银行处理中
                            )
                            {
                                // 主动调用通知接口
                                dubboInvokService.notifyTransCashOrderState(transOrderState);
                                BizLog.biz(this.getClass().getName(), Thread.currentThread().getStackTrace()[1]
                                        .getMethodName(), "params", "银行转账对账：业务订单号[" + transOut.getTransNo()
                                        + "]未收到通知，对帐查询到支付系统已成功");
                            }
                            else
                            // 已转账成功，对帐时为银行退票
                            if (transOrderState.getTransStatus() == TransOutResult.TRANS_CASH_BANK_BACK.getCode()// 银行退票
                                    && TransOutResult.TRANS_CASH_OUT.getCode().intValue() == transOut.getTransResult()// 已转账成功的
                            )
                            {
                                // 主动调用通知接口
                                dubboInvokService.notifyTransCashOrderState(transOrderState);
                                // 将对帐退票条目添加到返回列表
                                checkUpTransList.add(transOut);
                                BizLog.biz(this.getClass().getName(), Thread.currentThread().getStackTrace()[1]
                                        .getMethodName(), "params", "银行转账对账：业务订单号[" + transOut.getTransNo()
                                        + "]已转账成功，对帐查询到支付系统为银行退票");
                            }
                        }
                    }
                }
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_TRANS_OUT_CHECK_UP_ACCOUNT_ERROR.getCode() + ":银行转账对账异常,param="
                            + JSON.toJSONString(transNos), e);
            throw new HsException(AOErrorCode.AO_TRANS_OUT_CHECK_UP_ACCOUNT_ERROR.getCode(), "银行转账对账异常,param="
                    + JSON.toJSONString(transNos) + "\n" + e);
        }
        return checkUpTransList;
    }

    /**
     * 批量提交转账（大额允许转出）
     * 
     * @param transNos
     *            交易流水号
     * @param commitType
     *            提交类型
     * @param apprOptId
     *            复核操作员编号
     * @param apprOptName
     *            复核操作员名称
     * @param apprRemark
     *            复核备注
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOBankTransferService#transBatch(java.util.Set,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public void transBatch(Set<String> transNos, Integer commitType, String apprOptId, String apprOptName,
            String apprRemark) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "批量提交转账,params[" + JSON.toJSONString(transNos) + ",apprOptId:" + apprOptId + ",apprOptName:"
                        + apprOptName + ",apprRemark:" + apprRemark + "]");
        // 集合为空
        HsAssert.notNull(transNos, AOErrorCode.AO_PARAMS_NULL, "批量提交转账：交易流水号参数为空");
        // 交易流水号
        String transNo = "";
        // 银行转账对象
        TransOut transOut = null;
        // 提现信息集合
        List<TransCash> batchTransCash = new ArrayList<TransCash>();
        // 生成批次号
        String batchNo = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
        // 存储查询到的交易流水号
        Set<String> transNoSet = new HashSet<String>();
        try
        {
            // 迭代交易流水号
            Iterator<String> iterator = transNos.iterator();
            while (iterator.hasNext())
            {
                transNo = iterator.next();
                // 查询银行转账信息
                transOut = transOutMapper.findTransOutReview(transNo);
                if (transOut != null)
                {
                    // 查询出的交易流水号
                    transNoSet.add(transOut.getTransNo());
                    // 将转账信息添加到提现信息集合
                    batchTransCash.add(initTransCash(transOut));
                }
            }

            if (batchTransCash != null && batchTransCash.size() > 0)
            {
                // 支付系统最大只支付50个对象
                HsAssert.isTrue(batchTransCash.size() < aoConfig.getTransCashBatchNum(), AOErrorCode.AO_MAX_LIMIT,
                        "批量提交转账：提交数量超过最大限制" + aoConfig.getTransCashBatchNum());

                // 批量转账更新转账单
                transOutMapper.updateBatchTransOut(transNoSet, batchNo, TransStatus.PAYING.getCode(), apprOptId,
                        apprOptName, apprRemark);

                // 批量更新提交类型为手工提交
                transOutMapper.updateBatchCommitType(transNos, commitType);

                // 调用支付系统：批量提现接口
                gpTransCashService.batchTransCash(aoConfig.getMerchantNo(), batchNo, batchTransCash, aoConfig
                        .getSysName());
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_BATCH_TRANS_OUT_ERROR
                    .getCode()
                    + ":批量转账异常,param=["
                    + JSON.toJSONString(transNos)
                    + ",apprOptId:"
                    + apprOptId
                    + ",apprOptName:"
                    + apprOptName + ",apprRemark:" + apprRemark + "]", e);
            throw new HsException(AOErrorCode.AO_BATCH_TRANS_OUT_ERROR.getCode(), "批量转账异常,param=["
                    + JSON.toJSONString(transNos) + ",apprOptId:" + apprOptId + ",apprOptName:" + apprOptName
                    + ",apprRemark:" + apprRemark + "]" + e);
        }
    }

    /**
     * 撤单
     * 
     * @param revokes
     *            撤单列表Map<String,String>key为transNo，value为备注
     * @param apprOptId
     *            复核操作员编号
     * @param apprOptName
     *            复核操作员名称
     * @param apprRemark
     *            复核备注
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOBankTransferService#transRevoke(java.util.Map,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public void transRevoke(Map<String, String> revokes, String apprOptId, String apprOptName, String apprRemark)
            throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "银行转账撤单,params[" + JSON.toJSONString(revokes) + ",apprOptId:" + apprOptId + ",apprOptName:"
                        + apprOptName + ",apprRemark:" + apprRemark + "]");
        HsAssert.notNull(revokes, AOErrorCode.AO_PARAMS_NULL, "银行转账撤单：撤单列表参数为空");
        int resutNum = 0;
        // 校验撤单列表
        if (revokes != null && revokes.size() > 0)
        {
            try
            {
                TransOut transOut = null;
                Iterator<Entry<String, String>> iRevokes = revokes.entrySet().iterator();
                Map.Entry<String, String> entry = null;
                String transNo = "";
                String revokeRemark = "";
                while (iRevokes.hasNext())
                {
                    entry = iRevokes.next();
                    transNo = entry.getKey().toString();
                    revokeRemark = entry.getValue().toString();
                    // 获取银行转账记录为记帐分解提供数据
                    transOut = transOutMapper.findTransOutRevokeInfo(transNo);
                    HsAssert.notNull(transOut, AOErrorCode.AO_NOT_QUERY_REVOKE_DATA,
                            "银行转账撤单：未查询到交易流水号对应的撤单数据,params[transNo:" + transNo + "]");
                    // 还未生成失败记录才执行
                    if (transOut != null && !isNotRefund(transNo) // 还未生成失败记录
                            && TransReason.ACCORD_CASH.getCode().intValue() == transOut.getTransReason() // 转账原因为主动提现才能撤单，排除销户
                    )
                    {
                        // 更新原交易状态：3撤单
                        resutNum = transOutMapper.updateTransRevokeStatus(transNo, TransStatus.REVOKED.getCode(),
                                apprOptId, apprOptName, StringUtils.isBlank(revokeRemark) ? "货币提现撤单" : revokeRemark);
                        if (resutNum > 0)
                        {
                            // 交易状态：3撤单为记账分解提供依据
                            transOut.setTransStatus(TransStatus.REVOKED.getCode());
                            // 执行银行转账记帐分解
                            accountingService.saveAccounting(transOut);
                            BizLog.biz(this.getClass().getName(), "method:"
                                    + Thread.currentThread().getStackTrace()[1].getMethodName(), "params",
                                    "银行转账撤单,交易流水号[" + transOut.getTransNo() + "]");
                        }
                    }
                }
            }
            catch (HsException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                SystemLog.error(this.getClass().getName(), "method:"
                        + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_REVOKE_TRANS_ERROR
                        .getCode()
                        + ":银行转账撤单异常,param=["
                        + JSON.toJSONString(revokes)
                        + ",apprOptId:"
                        + apprOptId
                        + ",apprOptName:" + apprOptName + ",apprRemark:" + apprRemark + "]", e);
                throw new HsException(AOErrorCode.AO_REVOKE_TRANS_ERROR.getCode(), "银行转账撤单异常,param=["
                        + JSON.toJSONString(revokes) + ",apprOptId:" + apprOptId + ",apprOptName:" + apprOptName
                        + ",apprRemark:" + apprRemark + "]" + e);
            }
        }
    }

    /**
     * 更新转账结果
     * 
     * @param transCashOrderState
     *            转账结果实体类
     * @return true更新成功false更新失败
     * @throws HsException
     * @see com.gy.hsxt.ao.interfaces.ITransOutNotifyService#updateTransStatus(com.gy.hsxt.gp.bean.TransCashOrderState)
     */
    @Override
    // @Transactional(propagation = Propagation.SUPPORTS, isolation =
    // Isolation.SERIALIZABLE)
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean updateTransStatus(TransCashOrderState transCashOrderState) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "更新转账结果,params[" + JSON.toJSONString(transCashOrderState) + "]");
        int returnNum = 0;
        try
        {
            lock.lock();
            try
            {
                // 查询业务订单
                TransOut transOut = transOutMapper.findTransOutInfo(transCashOrderState.getOrderNo());
                // 16.2.24应支付系统需要，未查询到原始业务数据也返回true
                if (transOut == null)
                {
                    return true;
                }
                // HsAssert.notNull(transOut, AOErrorCode.AO_NOT_QUERY_DATA,
                // "银行转账通知：根据支付系统返回订单号未查询到业务数据");
                // 转账成功的情况
                if (GPConstant.TransStateCode.TRANS_SUCCESS == transCashOrderState.getTransStatus()// 支付系统通知提现成功
                        && (GPConstant.TransStateCode.READY == transOut.getTransResult()// 待提交银行
                        || GPConstant.TransStateCode.BANK_HANDLING == transOut.getTransResult())// 银行处理中
                        && TransStatus.PAYING.getCode().intValue() == transOut.getTransStatus()// 转账状态为：支付中
                )
                {
                    BizLog.biz(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                            "params", "更新转账结果,[" + transOut.getTransNo() + "]收到支付系统提现成功通知");
                    // 更新转账结果
                    returnNum = transOutMapper.updateTransSuccess(transOut.getAmount(), transOut.getTransNo(),
                            TransStatus.TRANS_SUCCESS.getCode(), transCashOrderState.getTransStatus(),
                            transCashOrderState.getBankFee(), transCashOrderState.getBankOrderSeqId(), DateUtil
                                    .DateTimeToString(transCashOrderState.getBankSubmitDate()));
                    if (returnNum <= 0)
                    {
                        BizLog.biz(this.getClass().getName(),
                                Thread.currentThread().getStackTrace()[1].getMethodName(), "params", "更新转账结果,["
                                        + transOut.getTransNo() + "]重复收到支付系统提现成功通知");
                        return true;
                    }
                    /**
                     * 记帐{
                     */
                    // 设置手续费为银行返回的
                    transOut.setFeeAmt(transCashOrderState.getBankFee());
                    // 设置转账状态
                    transOut.setTransStatus(TransStatus.TRANS_SUCCESS.getCode());
                    // 分解记账
                    accountingService.saveAccounting(transOut);
                    /**
                     * }
                     */
                    // 16.2.18收到BUG15678，添加转账成功后调用UC更新银行卡为已验证状态业务
                    updateBankCardValidStatus(transOut, "1");
                }
                else
                // 1002 银行处理中 -1 待提交银行
                if ((GPConstant.TransStateCode.BANK_HANDLING == transCashOrderState.getTransStatus()// 支付系统通知银行处理中
                        || GPConstant.TransStateCode.READY == transCashOrderState.getTransStatus() // 待提交银行
                        )
                        && GPConstant.TransStateCode.READY == transOut.getTransResult()// 待提交银行
                        && TransStatus.PAYING.getCode().intValue() == transOut.getTransStatus()// 转账状态为：支付中
                )
                {
                    BizLog.biz(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                            "params", "更新转账结果,[" + transOut.getTransNo() + "]收到支付系统银行处理中通知");
                    // 更新转账结果
                    transOutMapper.updateTransResult(transCashOrderState.getTransStatus(), transOut.getTransNo());
                }
                else
                // 转账失败1001
                if ((GPConstant.TransStateCode.TRANS_FAILED == transCashOrderState.getTransStatus() // 转账失败1001
                        || GPConstant.TransStateCode.TRANS_ERROR == transCashOrderState.getTransStatus())// 交易错误-99
                        && (GPConstant.TransStateCode.READY == transOut.getTransResult()// 待提交银行
                        || GPConstant.TransStateCode.BANK_HANDLING == transOut.getTransResult())// 银行处理中
                        && TransStatus.PAYING.getCode().intValue() == transOut.getTransStatus()// 转账状态为：支付中
                )
                {
                    BizLog.biz(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                            "params", "更新转账结果,[" + transOut.getTransNo() + "]收到支付系统转账失败通知");
                    // 更新转账结果
                    transOutMapper.updateTransFail(transOut.getTransNo(), TransStatus.TRANS_FAILED.getCode(),
                            transCashOrderState.getTransStatus(), DateUtil.DateTimeToString(transCashOrderState
                                    .getBankSubmitDate()), transCashOrderState.getFailReason(), transCashOrderState
                                    .getBankFee(), transCashOrderState.getBankOrderSeqId());

                    // 保存转账失败记录
                    saveTransOutFail(transOut.getTransNo(), "", "", "支付系统通知转账失败："
                            + transCashOrderState.getTransStatus(), transOut.getTransReason(), transCashOrderState
                            .getTransStatus());

                    // 分解记账:16.1.18业务确定转账失败需要人工处理退回，固不自动记账
                    // transOut.setTransStatus(TransStatus.TRANS_FAILED.getCode());
                    // accountingService.saveAccounting(transOut);
                }
                else
                // 银行退票退回：已提现成功现要做退回处理********支付系统不通知：因此业务银行两天之后才通知，对帐查询时处理退票***
                if (GPConstant.TransStateCode.REFUNDED == transCashOrderState.getTransStatus() // 银行退票
                        && GPConstant.TransStateCode.TRANS_SUCCESS == transOut.getTransResult() // 提现成功
                        && TransStatus.TRANS_SUCCESS.getCode().intValue() == transOut.getTransStatus()// 转账成功
                )
                {
                    BizLog.biz(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                            "params", "更新转账结果,[" + transOut.getTransNo() + "]收到支付系统银行退票通知");
                    // 更新转账结果
                    transOutMapper.updateTransFail(transOut.getTransNo(), TransStatus.BANK_BACK.getCode(),
                            transCashOrderState.getTransStatus(), DateUtil.DateTimeToString(transCashOrderState
                                    .getBankSubmitDate()), transCashOrderState.getFailReason(), transCashOrderState
                                    .getBankFee(), transCashOrderState.getBankOrderSeqId());

                    // 转账原因为销户退款时，保存失败记录时为已处理加上备注为已线下退款
                    if (TransReason.CLOSE_ACCOUNT.getCode().intValue() == transOut.getTransReason())
                    {
                        // 保存转账失败记录
                        saveTransOutFail(transOut.getTransNo(), "", "", "销户提现退票：已线下进行转账汇款", transOut.getTransReason(),
                                transCashOrderState.getTransStatus());
                    }
                    else
                    {
                        // 保存转账失败记录
                        saveTransOutFail(transOut.getTransNo(), "", "", "支付系统通知退票："
                                + transCashOrderState.getTransStatus(), transOut.getTransReason(), transCashOrderState
                                .getTransStatus());

                        // 分解记账:16.1.18业务确定退票需要人工处理退回，固不自动记账
                        // transOut.setTransStatus(TransStatus.BANK_BACK.getCode());
                        // accountingService.saveAccounting(transOut);

                        // 16.2.18收到BUG15678，添加转账成功后调用UC更新银行卡为已验证状态业务
                        // 如当前未业务为未验证，实际已更新UC为已验证，此时退票改为未验证状态
                        updateBankCardValidStatus(transOut, "0");
                    }
                }
            }
            finally
            {
                lock.unlock();
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_TRANS_OUT_FAIL_BACK_ERROR.getCode() + "银行转账通知：更新转账结果异常,param="
                            + JSON.toJSONString(transCashOrderState), e);
            throw new HsException(AOErrorCode.AO_TRANS_OUT_FAIL_BACK_ERROR.getCode(), "银行转账通知：更新转账结果异常,param="
                    + JSON.toJSONString(transCashOrderState) + e);
        }
        return true;
    }

    /**
     * 初始化转账数据
     * 
     * @param transOut
     *            转账信息
     * @param bankfeeAmount
     *            手续费
     * @param bankAcctInfo
     *            银行账户信息
     */
    private void initTransData(TransOut transOut, String bankfeeAmount, BsBankAcctInfo bankAcctInfo) {
        try
        {
            // 兼容APP无转账原因
            if (transOut.getChannel() == Channel.MOBILE.getCode())
            {
                // 设置转账原因为主动提现
                transOut.setTransReason(TransReason.ACCORD_CASH.getCode());
            }
            // 生成交易流水号
            transOut.setTransNo(GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode()));
            // 生成交易时间
            transOut.setReqTime(DateUtil.getCurrentDateTime());
            // 手续费金额：申请时只计算不存储（1648改为初始化0）
            transOut.setFeeAmt("0");
            // 实收货币金额16.2.16改为通知成功后修改
            // transOut.setRealAmt(transOut.getAmount());

            // 货币币种
            transOut.setCurrencyCode(commonService.getLocalInfo().getCurrencyCode());
            // 转账结果
            transOut.setTransResult(TransOutResult.TRANS_CASH_PRE.getCode());
            /**
             * 设置银行帐户信息
             */
            // 银行户名
            transOut.setBankAcctName(bankAcctInfo.getBankAccName());
            // 银行账号
            transOut.setBankAcctNo(bankAcctInfo.getBankAccNo());
            // 银行帐号：测试失败用
            // transOut.setBankAcctNo(transOut.getBankAcctNo());
            // 开户银行代码
            transOut.setBankNo(bankAcctInfo.getBankCode());
            // 开户银行支行
            transOut.setBankBranch(bankAcctInfo.getBankName());
            // 开户银行省代码
            transOut.setBankProvinceNo(bankAcctInfo.getProvinceNo());
            // 开户银行城市代码
            transOut.setBankCityNo(bankAcctInfo.getCityNo());
            // 开户银行地址
            // transOut.setBankAddress();
            // 帐户是否是验证
            transOut.setVerify(bankAcctInfo.getIsValidAccount() == 1 ? true : false);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(aoConfig.getSysName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_RULE_VERIFY_LIMIT_FAILED.getCode() + ":保存银行转账记录：初始化转账信息异常|param["
                            + JSON.toJSONString(transOut) + ",bankfeeAmount:" + bankfeeAmount + ","
                            + JSON.toJSONString(bankAcctInfo) + "]", e);
            throw new HsException(AOErrorCode.AO_RULE_VERIFY_LIMIT_FAILED.getCode(), "保存银行转账记录：初始化转账信息异常|param["
                    + JSON.toJSONString(transOut) + ",bankfeeAmount:" + bankfeeAmount + ","
                    + JSON.toJSONString(bankAcctInfo) + "]" + "\n" + e);
        }
    }

    /**
     * 初始化提现信息
     * 
     * @param transOutInfo
     *            转账停止
     * @return 提现信息
     */
    private TransCash initTransCash(TransOut transOutInfo) {
        // 初始化支付系统提现参数实体类
        TransCash transCash = new TransCash();
        transCash.setOrderNo(transOutInfo.getTransNo());// 业务流水号
        transCash.setOrderDate(DateUtil.StringToDate(transOutInfo.getReqTime(), DateUtil.DEFAULT_DATE_TIME_FORMAT));// 业务申请时间
        transCash.setTransAmount(transOutInfo.getAmount());// 交易金额
        transCash.setCurrencyCode(transOutInfo.getCurrencyCode()); // 币种
        transCash.setInAccNo(transOutInfo.getBankAcctNo());// 收款人账户
        transCash.setInAccName(transOutInfo.getBankAcctName()); // 收款人账户名
        transCash.setInAccBankName(transOutInfo.getBankBranch());// 收款人开户行名称
        transCash.setInAccBankNode(transOutInfo.getBankNo());// 收款人开户银行代码
        transCash.setInAccProvinceCode(transOutInfo.getBankProvinceNo()); // 收款账户银行开户省代码或省名称
        transCash.setInAccCityCode(transOutInfo.getBankCityNo());// 收款账户开户城市代码
        try
        {
            // 添加城市名称到支付系统 16.6.12
            LocalInfo localInfo = baseDataService.getLocalInfo();
            if (localInfo != null)
            {
                City city = baseDataService.getCityById(localInfo.getCountryNo(), transOutInfo.getBankProvinceNo(),
                        transOutInfo.getBankCityNo());
                if (city != null && StringUtils.isNotBlank(city.getCityName()))
                {
                    transCash.setInAccCityName(city.getCityName());
                }
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_BATCH_TRANS_OUT_ERROR.getCode() + ":批量转账初始化数据异常", e);
            throw new HsException(AOErrorCode.AO_BATCH_TRANS_OUT_ERROR.getCode(), "批量转账初始化数据异常" + e);
        }
        return transCash;
    }

    /**
     * 查询退款是否还未处理
     * 
     * @param transNo
     *            交易流水号
     * @return true/false
     */
    private boolean isNotRefund(String transNo) {
        int resultNum = transOutFailMapper.findUnCheckRefund(transNo);
        if (resultNum > 0)
        {
            return true;
        }
        return false;
    }

    /**
     * 是否大额转账
     * 
     * @param transOut
     *            转账信息
     * @return true/false
     */
    private boolean verifyBigAmount(TransOut transOut) {
        boolean isBigAmount = false;
        try
        {
            // 是否大额转账
            isBigAmount = accountRuleService.isNeedReviewForTransOut(transOut.getCustType(), transOut.getAmount());
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(aoConfig.getSysName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_RULE_VERIFY_LIMIT_FAILED.getCode() + ":保存银行转账记录：校验是否大额转账异常|"
                            + accountRuleService.getClass().getName() + "." + "isNeedReviewForTransOut("
                            + transOut.getCustType() + "," + transOut.getAmount() + ")", e);
            throw new HsException(AOErrorCode.AO_RULE_VERIFY_LIMIT_FAILED.getCode(), "保存银行转账记录：校验是否大额转账异常|"
                    + accountRuleService.getClass().getName() + "." + "isNeedReviewForTransOut("
                    + transOut.getCustType() + "," + transOut.getAmount() + ")" + "\n" + e);
        }
        return isBigAmount;
    }

    /**
     * 校验帐户余额并计算出手续费
     * 
     * @param transOut
     *            转账信息
     * @param bankAcctInfo
     *            银行账户信息
     * @return 手续费
     */
    private String verifyTransBalanceToAc(TransOut transOut, BsBankAcctInfo bankAcctInfo) {
        // 手续费
        String bankfeeAmount = "";
        // 帐户信息
        AccountBalance accountBalance = null;
        try
        {
            // 调用AC获取货币帐户余额
            accountBalance = accountSearchService.searchAccNormal(transOut.getCustId(), AccountType.ACC_TYPE_POINT30110
                    .getCode());
            // 未查询到数据
            HsAssert.notNull(accountBalance, AOErrorCode.AO_INVOKE_AC_NOT_QUERY_DATA, "保存银行转账记录：调用帐务系统查询余额未查询到记录");
            // 调用支付系统计算手续费金额
            bankfeeAmount = gpTransCashService.getBankTransFee(transOut.getAmount(), bankAcctInfo.getBankCode(),
                    bankAcctInfo.getCityNo(), aoConfig.getUrgentFlag());
            // 计算转账金额与手续费金额的和
            String targetAmount = BigDecimalUtils.ceilingAdd(transOut.getAmount(), bankfeeAmount).toString();
            // 帐户余额小于转账金额（货币转银行所产生的银行手续费从货币账户的余额中扣除）
            HsAssert.isTrue(BigDecimalUtils.compareTo(targetAmount, accountBalance.getAccBalance()) <= 0,
                    AOErrorCode.AO_ACC_BALANCE_NOT_ENOUGH, "保存银行转账记录：货币帐户余额不足");
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(aoConfig.getSysName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_TO_GP_CALC_FEE_AMOUNT_ERROR.getCode() + ":保存银行转账记录：调用支付系统计算手续费金额异常|"
                            + gpTransCashService.getClass().getName() + "." + "getBankTransFee(" + transOut.getAmount()
                            + "," + bankAcctInfo.getBankCode() + "," + bankAcctInfo.getCityNo() + ","
                            + aoConfig.getUrgentFlag() + ")", e);
            throw new HsException(AOErrorCode.AO_TO_GP_CALC_FEE_AMOUNT_ERROR.getCode(), "保存银行转账记录：调用支付系统计算手续费金额异常|"
                    + gpTransCashService.getClass().getName() + "." + "getBankTransFee(" + transOut.getAmount() + ","
                    + bankAcctInfo.getBankCode() + "," + bankAcctInfo.getCityNo() + "," + aoConfig.getUrgentFlag()
                    + ")" + "\n" + e);
        }
        return bankfeeAmount;
    }

    /**
     * 获取银行卡信息
     * 
     * @param transOut
     *            转账信息
     * @param accId
     *            银行帐户ID
     * @return
     */
    private BsBankAcctInfo getBankAcctInfo(TransOut transOut, Long accId) {
        // 用户中心用户类型
        String userType = "";
        // 银行帐户信息
        BsBankAcctInfo bankAcctInfo = null;
        try
        {
            // 根据客户类型转换成用户中心的用户类型
            if (transOut.getCustType() == CustType.PERSON.getCode())// 持卡人
            {
                userType = UserTypeEnum.CARDER.getType();
            }
            else
            // 非持卡人
            if (transOut.getCustType() == CustType.NOT_HS_PERSON.getCode())
            {
                userType = UserTypeEnum.NO_CARDER.getType();
            }
            // 企业
            else
            {
                userType = UserTypeEnum.ENT.getType();
            }
            // 调用UC查询银行账户信息
            bankAcctInfo = ucBankAcctInfoService.findBankAccByAccId(accId, userType);
            // 银行帐户信息为空
            HsAssert.notNull(bankAcctInfo, AOErrorCode.AO_INVOKE_UC_NOT_QUERY_DATA, "保存银行转账记录：调用用户中心查询银行帐户信息为空");
            // 客户名与帐户名不一致（16.5.27改为校验银行卡户名与认证名称匹配）
            // HsAssert.isTrue(transOut.getCustName().equals(bankAcctInfo.getBankAccName()),
            // AOErrorCode.AO_TRANS_OUT_CUSTNAME_BANKNAME_DISCORD,
            // "保存银行转账记录：转账客户名与用户中心帐户名不一致 ");
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(aoConfig.getSysName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_INVOKE_UC_QUERY_BANK_INFO_ERROR.getCode() + ":保存银行转账记录：调用用户中心查询银行帐户信息异常|"
                            + ucBankAcctInfoService.getClass().getName() + "." + "findBankAccByAccId(" + accId + ","
                            + userType + ")", e);
            throw new HsException(AOErrorCode.AO_INVOKE_UC_QUERY_BANK_INFO_ERROR.getCode(),
                    "保存银行转账记录：调用用户中心查询银行帐户信息异常|" + ucBankAcctInfoService.getClass().getName() + "."
                            + "findBankAccByAccId(" + accId + "," + userType + ")" + "\n" + e);
        }
        return bankAcctInfo;
    }

    /**
     * 当银行账户名称与证件姓名不一致时，禁止做货币转银行业务（16.5.26田学化需求）
     * 
     * @param custId
     *            客户号
     * @param custType
     *            客户类型
     * @param cardName
     *            银行卡持卡人姓名
     */
    private void verifyBankCardNameEqCertif(String custId, int custType, String cardName) {
        // 认证名称
        String realAuthName = "";
        try
        {
            // 根据客户类型获取名称
            if (CustType.PERSON.getCode() == custType)// 持卡人
            {
                // 查询个人认证信息
                AsRealNameAuth realNameAuth = ucCardHolderAuthInfoService.searchRealNameAuthInfo(custId);
                HsAssert.notNull(realNameAuth, AOErrorCode.AO_TO_UC_NOT_QUERY_AUTH_DATA, "保存银行转账记录：调用UC未查询到认证信息");
                /** 证件类型 1：身份证 2：护照:3：营业执照 */
                if (realNameAuth.getCerType().equals("1") || realNameAuth.getCerType().equals("2"))
                {
                    // 证件名称
                    realAuthName = realNameAuth.getUserName();
                }
                else if (realNameAuth.getCerType().equals("3"))
                {
                    // 证件名称
                    realAuthName = realNameAuth.getEntName();
                }
            }
            else // 非持卡人
            if (CustType.NOT_HS_PERSON.getCode() == custType)
            {
                AsNetworkInfo networkInfo = ucNetworkInfoService.searchByCustId(custId);
                HsAssert.notNull(networkInfo, AOErrorCode.AO_TO_UC_NOT_QUERY_AUTH_DATA, "保存银行转账记录：调用UC未查询到认证信息");

                // 证件名称
                realAuthName = networkInfo.getName();
            }
            else
            {
                // 查询企业信息
                AsEntExtendInfo entExtendInfo = ucAsEntService.searchEntExtInfo(custId);
                HsAssert.notNull(entExtendInfo, AOErrorCode.AO_TO_UC_NOT_QUERY_AUTH_DATA, "保存银行转账记录：调用UC未查询到认证信息");
                // 证件名称
                realAuthName = entExtendInfo.getEntCustName();
            }
            // 客户名与帐户名不一致
            HsAssert.isTrue(realAuthName.equals(cardName), AOErrorCode.AO_TRANS_OUT_CARDNAME_CERTIFNAME_DISCORD,
                    "保存银行转账记录：银行账户名称与证件姓名不一致");
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(aoConfig.getSysName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_TO_UC_QUERY_CERTIF_INFO_ERROR.getCode() + ":保存银行转账记录：调用UC查询认证信息异常", e);
            throw new HsException(AOErrorCode.AO_TO_UC_QUERY_CERTIF_INFO_ERROR.getCode(), "保存银行转账记录：调用UC查询认证信息异常" + e);
        }
    }

    /**
     * 更新银行卡验证状态
     * 
     * @param transOut
     *            转账信息
     * @param isValidAccount
     *            是否已验证账户1:是 0：否
     */
    private void updateBankCardValidStatus(TransOut transOut, String isValidAccount) {
        // 银行账户信息对象
        BsBankAcctInfo bankAcctInfo = null;
        try
        {
            if (transOut.getAccId() != null)
            {
                // 校验客户名与银行帐户名是否一致
                bankAcctInfo = getBankAcctInfo(transOut, transOut.getAccId());
                if (bankAcctInfo != null)
                {
                    if (bankAcctInfo.getIsValidAccount() == 0)
                    {
                        // 调用用户中心更新银行卡为已验证状态（参数涵义：银行账户编号、是否已验证账户1:是
                        // 0：否、用户类型）
                        ucBankAcctInfoService.updateTransStatus(transOut.getAccId(), isValidAccount, transOut
                                .getCustType() == CustType.PERSON.getCode() ? UserTypeEnum.CARDER.getType()
                                : UserTypeEnum.NO_CARDER.getType());
                    }
                    // 若转账成功后又失败时，AO当前是未验证的，但已被更新到UC为已验证，修改为未验证的
                    if (bankAcctInfo.getIsValidAccount() == 1 && !transOut.isVerify())
                    {
                        // 调用用户中心更新银行卡为已验证状态（参数涵义：银行账户编号、是否已验证账户1:是
                        // 0：否、用户类型）
                        ucBankAcctInfoService.updateTransStatus(transOut.getAccId(), isValidAccount, transOut
                                .getCustType() == CustType.PERSON.getCode() ? UserTypeEnum.CARDER.getType()
                                : UserTypeEnum.NO_CARDER.getType());
                    }
                }
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_UPDATE_BANK_CARD_VALID_STATUS_ERROR.getCode() + "银行转账通知：更新银行帐户验证状态异常：："
                            + ucBankAcctInfoService.getClass().getName() + "." + "actualAccount("
                            + JSON.toJSONString(transOut) + "," + isValidAccount + ")\n", e);
            throw new HsException(AOErrorCode.AO_UPDATE_BANK_CARD_VALID_STATUS_ERROR.getCode(), "银行转账通知：更新银行帐户验证状态异常："
                    + ucBankAcctInfoService.getClass().getName() + "." + "actualAccount(" + JSON.toJSONString(transOut)
                    + "," + isValidAccount + ")\n" + e);
        }
    }
}
