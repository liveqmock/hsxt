/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ao.AOErrorCode;
import com.gy.hsxt.ao.bean.AccountingEntry;
import com.gy.hsxt.ao.bean.BuyHsb;
import com.gy.hsxt.ao.bean.HsbToCash;
import com.gy.hsxt.ao.bean.ProxyBuyHsb;
import com.gy.hsxt.ao.bean.PvToHsb;
import com.gy.hsxt.ao.bean.TransOut;
import com.gy.hsxt.ao.common.TransCodeUtil;
import com.gy.hsxt.ao.disconf.AoConfig;
import com.gy.hsxt.ao.enumtype.MoneyType;
import com.gy.hsxt.ao.enumtype.ProxyTransMode;
import com.gy.hsxt.ao.enumtype.TransStatus;
import com.gy.hsxt.ao.interfaces.IAccountRuleService;
import com.gy.hsxt.ao.interfaces.IAccountingService;
import com.gy.hsxt.ao.interfaces.ICommonService;
import com.gy.hsxt.ao.interfaces.IDubboInvokService;
import com.gy.hsxt.ao.mapper.AccountingHisMapper;
import com.gy.hsxt.ao.mapper.AccountingMapper;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.CurrencyCode;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.uc.bs.bean.ent.BsEntStatusInfo;

/**
 * 记帐分解service
 * 
 * @Package: com.gy.hsxt.ao.service
 * @ClassName: AccountingService
 * @Description:
 * 
 * @author: kongsl
 * @date: 2015-11-27 上午11:00:07
 * @version V3.0.0
 */
@Service
public class AccountingService implements IAccountingService {

    /** 业务服务私有配置参数 **/
    @Autowired
    private AoConfig aoConfig;

    // 注入记帐分解mapper
    @Autowired
    AccountingMapper accountingMapper;

    // 注入记帐分解历史 mapper
    @Autowired
    AccountingHisMapper accountingHisMapper;

    // 远程调用
    @Autowired
    IDubboInvokService dubboInvokService;

    // 公共服务，用于获取平台企业信息
    @Autowired
    ICommonService commonService;

    /**
     * 参数配置系统：帐户规则服务
     */
    @Resource
    IAccountRuleService accoountRuleService;

    /**
     * 保存记帐分解
     * 
     * @param obj
     *            分解对象
     * @throws HsException
     * @see com.gy.hsxt.ao.interfaces.IAccountingService#saveAccounting(java.lang.Object)
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void saveAccounting(Object obj) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "保存记帐分解,params[" + obj + "]");
        // 交易码
        String transCode = "";
        // 对象为空
        HsAssert.notNull(obj, RespCode.PARAM_ERROR, "保存记帐分解：实体参数为空");

        // 校验传入对象类型为积分转互生币
        if (obj instanceof PvToHsb)
        {
            PvToHsb pvToHsb = (PvToHsb) obj;
            // 获取交易码
            transCode = TransCodeUtil.checkPvToHsbTransCode(pvToHsb.getCustType());
            // 会计日期处理
            if (StringUtils.isNotBlank(getFiscalDate(pvToHsb.getTransNo())))
            {
                pvToHsb.setReqTime(getFiscalDate(pvToHsb.getTransNo()));
            }
            // 积分转互生币规则
            saveIntegralToHsCoin(pvToHsb, transCode);
        }
        // 校验传入对象类型为互生币转货币
        if (obj instanceof HsbToCash)
        {
            HsbToCash hsbToCash = (HsbToCash) obj;
            // 获取交易码
            transCode = TransCodeUtil.checkHsbToCurrencyTransCode(hsbToCash.getCustType());
            // 会计日期处理
            if (StringUtils.isNotBlank(getFiscalDate(hsbToCash.getTransNo())))
            {
                hsbToCash.setReqTime(getFiscalDate(hsbToCash.getTransNo()));
            }
            // 保存互生币转货币记帐分解
            saveHsCoinToMoney(hsbToCash, transCode);
        }
        // 校验传入对象类型为兑换互生币
        if (obj instanceof BuyHsb)
        {
            BuyHsb buyHsb = (BuyHsb) obj;
            // 会计日期处理
            if (StringUtils.isNotBlank(getFiscalDate(buyHsb.getTransNo())))
            {
                buyHsb.setReqTime(getFiscalDate(buyHsb.getTransNo()));
            }

            // 非持卡人不传将手机号存储到互生号字段的互生号给到帐务
            if (CustType.NOT_HS_PERSON.getCode() == buyHsb.getCustType())
            {
                buyHsb.setHsResNo("");
            }

            // 保存内部转帐记帐分解记录
            saveBuyHsbOrderAccountDetail(buyHsb);
        }
        // 校验传入对象类型为代兑互生币
        if (obj instanceof ProxyBuyHsb)
        {
            ProxyBuyHsb proxyBuyHsb = (ProxyBuyHsb) obj;
            // 会计日期处理
            if (StringUtils.isNotBlank(getFiscalDate(proxyBuyHsb.getTransNo())))
            {
                proxyBuyHsb.setReqTime(getFiscalDate(proxyBuyHsb.getTransNo()));
            }
            // 保存内部转帐记帐分解记录
            saveProxyBuyHsbAccountDetail(proxyBuyHsb);
        }
        // 校验传入对象类型为银行转帐
        if (obj instanceof TransOut)
        {
            TransOut transOut = (TransOut) obj;
            // 会计日期处理
            if (StringUtils.isNotBlank(getFiscalDate(transOut.getTransNo())))
            {
                transOut.setReqTime(getFiscalDate(transOut.getTransNo()));
            }
            // 非持卡人不传将手机号存储到互生号字段的互生号给到帐务
            if (CustType.NOT_HS_PERSON.getCode() == transOut.getCustType())
            {
                transOut.setHsResNo("");
            }
            // 保存银行转帐记帐分解记录
            saveTransOutAccountDetail(transOut);
        }
    }

    /**
     * 销户记帐
     * 
     * @param obj
     *            记帐对象
     * @throws HsException
     * @see com.gy.hsxt.ao.interfaces.IAccountingService#closeAccountAccounting(java.lang.Object)
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void closeAccountAccounting(Object obj) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "销户记帐分解,params[" + obj + "]");
        // 交易码
        String transCode = "";
        // 对象为空
        HsAssert.notNull(obj, RespCode.PARAM_ERROR, "销户记帐分解：实体参数为空");

        // 校验传入对象类型为积分转互生币
        if (obj instanceof PvToHsb)
        {
            PvToHsb pvToHsb = (PvToHsb) obj;
            // 获取交易码
            transCode = TransCodeUtil.checkPvToHsbTransCode(pvToHsb.getCustType());
            // 会计日期处理
            if (StringUtils.isNotBlank(getFiscalDate(pvToHsb.getTransNo())))
            {
                pvToHsb.setReqTime(getFiscalDate(pvToHsb.getTransNo()));
            }
            // 积分转互生币规则
            closeAccountPvToHsb(pvToHsb, transCode);
        }
        // 校验传入对象类型为互生币转货币
        if (obj instanceof HsbToCash)
        {
            HsbToCash hsbToCash = (HsbToCash) obj;
            // 获取交易码
            transCode = TransCodeUtil.checkHsbToCurrencyTransCode(hsbToCash.getCustType());
            // 会计日期处理
            if (StringUtils.isNotBlank(getFiscalDate(hsbToCash.getTransNo())))
            {
                hsbToCash.setReqTime(getFiscalDate(hsbToCash.getTransNo()));
            }
            // 保存互生币转货币记帐分解
            closeAccountHsbToCash(hsbToCash, transCode);
        }
    }

    /**
     * 银行转帐转出分解
     * 
     * @param transOut
     *            银行转帐信息
     * @param transCode
     *            交易码
     */
    // @Transactional
    // private void saveBankTransOut(TransOut transOut) {
    // // 交易码
    // String transCode =
    // TransCodeUtil.checkTransCashTransCode(transOut.getCustType());
    // // 校验客户号，帐务要求必传客户号
    // HsAssert.hasText(transOut.getCustId(), RespCode.AO_PARAMS_NULL,
    // "银行转帐记帐分解：客户号为空");
    // // 记帐分解LIST
    // List<AccountingEntry> actDetails = new ArrayList<AccountingEntry>();
    // // 记帐分解对象
    // AccountingEntry detail = null;
    //
    // /******************************* 银行转帐转出平台网银转出(本币)帐户增加
    // ***************************************/
    // // 实例化记帐分解对象
    // detail = new AccountingEntry(//
    // guid, // 记帐流水号
    // transOut.getTransNo(),// 交易流水号
    // transCode,// 设置交易类型
    // platResNo + DateUtil.getCurrentDateNoSign(), // 客户号
    // platResNo, // 互生号
    // CustType.AREA_PLAT.getCode(), // 客户类型
    // AccountType.ACC_TYPE_POINT50120.getCode(), // 帐户类型:50120平台网银转出(本币)
    // transOut.getAmount(),// 增加金额
    // "0", // 减少金额
    // CurrencyCode.CNY.getCode(),// 货币币种
    // transOut.getReqTime(),// 记帐日期
    // "银行转帐转出平台网银转出(本币)帐户增加", // 备注
    // true// 记账状态
    // );
    // // 添加到集合
    // actDetails.add(detail);
    //
    // /******************************* 平台网银转出手续费帐户增加
    // ***************************************/
    // // 实例化记帐分解对象
    // detail = new AccountingEntry(//
    // guid, // 记帐流水号
    // transOut.getTransNo(),// 交易流水号
    // transCode,// 设置交易类型
    // platResNo + DateUtil.getCurrentDateNoSign(), // 客户号
    // platResNo, // 互生号
    // CustType.AREA_PLAT.getCode(), // 客户类型(地区平台)
    // AccountType.ACC_TYPE_POINT30410.getCode(), // 帐户类型:30410手续费金额
    // transOut.getFeeAmt(),// 增加金额(手续费金额)
    // "0", // 减少金额
    // CurrencyCode.CNY.getCode(),// 货币币种
    // transOut.getReqTime(),// 记帐日期
    // "银行转帐平台网银转出手续费帐户增加金额", // 备注
    // true// 记账状态
    // );
    // actDetails.add(detail);
    //
    // /******************************* 平台网银转出待结算网银转出减少
    // ***************************************/
    // // 实例化记帐分解对象
    // detail = new AccountingEntry(//
    // guid, // 记帐流水号
    // transOut.getTransNo(),// 交易流水号
    // transCode,// 设置交易类型
    // platResNo + DateUtil.getCurrentDateNoSign(), // 客户号
    // platResNo, // 互生号
    // CustType.AREA_PLAT.getCode(), // 客户类型(地区平台)
    // AccountType.ACC_TYPE_POINT50121.getCode(), // 帐户类型:50121待结算网银转出帐户
    // "0",// 增加金额(手续费金额)
    // transOut.getAmount(), // 减少金额
    // CurrencyCode.CNY.getCode(),// 货币币种
    // transOut.getReqTime(),// 记帐日期
    // "银行转帐平台待结算网银转出帐户减少金额", // 备注
    // true// 记账状态
    // );
    // actDetails.add(detail);
    //
    // try
    // {
    // // 批量插入记帐分解
    // accountingMapper.insertBatchAccounting(actDetails);
    //
    // // 调用帐务系统记帐
    // }
    // catch (Exception e)
    // {
    // SystemLog.error(this.getClass().getName(), "function:saveBankTransOut",
    // RespCode.AO_SAVE_ACCOUNTING_ERROR
    // .getCode()
    // + ":银行转帐记帐分解异常", e);
    // throw new HsException(RespCode.AO_SAVE_ACCOUNTING_ERROR.getCode(),
    // "银行转帐记帐分解异常");
    // }
    // }

    /**
     * 积分转互生币分解规则
     * 
     * @param pvToHsb
     *            积分转互生币信息
     * @param transCode
     *            交易码
     */
    @Transactional
    private void saveIntegralToHsCoin(PvToHsb pvToHsb, String transCode) {
        // 记帐分解LIST
        List<AccountingEntry> actDetails = new ArrayList<AccountingEntry>();
        // 记帐分解对象
        AccountingEntry detail = null;

        /******************************* 积分帐户减少 ***************************************/
        String guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "积分转互生币积分帐户减少时生成的主键ID1：" + guid);

        // 实例化记帐分解对象
        detail = new AccountingEntry(//
                guid,//
                pvToHsb.getTransNo(),// 交易流水号
                transCode,// 设置交易类型
                pvToHsb.getCustId(), // 客户号
                pvToHsb.getHsResNo(), // 互生号
                pvToHsb.getCustType(), // 客户类型
                AccountType.ACC_TYPE_POINT10110.getCode(), // 帐户类型:10110积分帐户
                "0", // 增加金额
                pvToHsb.getAmount(),// 减少金额
                MoneyType.PV.getCode(),// 货币币种
                pvToHsb.getReqTime(),// 记帐日期
                "积分转互生币", // 备注
                true// 记账状态
        );
        // 设置保底积分
        detail.setGuaranteedValue(accoountRuleService.getPvSaveAmount(pvToHsb.getCustId(), pvToHsb.getCustType()));
        // 添加到集合
        actDetails.add(detail);

        /******************************* 互生币帐户增加 ***************************************/
        guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "积分转互生币互生币帐户增加时生成的主键ID2：" + guid);
        // 实例化记帐分解对象
        detail = new AccountingEntry(//
                guid,//
                pvToHsb.getTransNo(),// 交易流水号
                transCode,// 设置交易类型
                pvToHsb.getCustId(), // 客户号
                pvToHsb.getHsResNo(), // 互生号
                pvToHsb.getCustType(), // 客户类型
                AccountType.ACC_TYPE_POINT20110.getCode(), // 帐户类型:20110互生币帐户
                pvToHsb.getAmount(),// 增加金额
                "0", // 减少金额
                CurrencyCode.HSB.getCode(),// 货币币种
                pvToHsb.getReqTime(), // 记帐日期
                "积分转互生币", // 备注
                true// 记账状态
        );
        // 添加到集合
        actDetails.add(detail);
        // 保存记账分解并记账
        saveAccounting(actDetails, true);
    }

    /**
     * 销户积分转互生币分解规则
     * 
     * @param pvToHsb
     *            积分转互生币信息
     * @param transCode
     *            交易码
     */
    @Transactional
    private void closeAccountPvToHsb(PvToHsb pvToHsb, String transCode) {
        // 记帐分解LIST
        List<AccountingEntry> actDetails = new ArrayList<AccountingEntry>();
        // 记帐分解对象
        AccountingEntry detail = null;

        /******************************* 积分帐户减少 ***************************************/
        String guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "销户积分转互生币积分帐户减少时生成的主键ID1：" + guid);
        // 实例化记帐分解对象
        detail = new AccountingEntry(//
                guid,//
                pvToHsb.getTransNo(),// 交易流水号
                transCode,// 设置交易类型
                pvToHsb.getCustId(), // 客户号
                pvToHsb.getHsResNo(), // 互生号
                pvToHsb.getCustType(), // 客户类型
                AccountType.ACC_TYPE_POINT10110.getCode(), // 帐户类型:10110积分帐户
                "0", // 增加金额
                pvToHsb.getAmount(),// 减少金额
                MoneyType.PV.getCode(),// 货币币种
                pvToHsb.getReqTime(),// 记帐日期
                "积分转互生币", // 备注
                true// 记账状态
        );
        // 添加到集合
        actDetails.add(detail);

        /******************************* 互生币帐户增加 ***************************************/
        guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "销户积分转互生币互生币帐户增加时生成的主键ID2：" + guid);
        // 实例化记帐分解对象
        detail = new AccountingEntry(//
                guid,//
                pvToHsb.getTransNo(),// 交易流水号
                transCode,// 设置交易类型
                pvToHsb.getCustId(), // 客户号
                pvToHsb.getHsResNo(), // 互生号
                pvToHsb.getCustType(), // 客户类型
                AccountType.ACC_TYPE_POINT20110.getCode(), // 帐户类型:20110互生币帐户
                pvToHsb.getAmount(),// 增加金额
                "0", // 减少金额
                CurrencyCode.HSB.getCode(),// 货币币种
                pvToHsb.getReqTime(), // 记帐日期
                "积分转互生币", // 备注
                true// 记账状态
        );
        // 添加到集合
        actDetails.add(detail);
        // 保存记账分解并记账
        saveAccounting(actDetails, true);
    }

    /**
     * 互生币转货币分解规则
     * 
     * @param hsbToCash
     *            互生币转货币信息
     * @param AccountingEntry
     *            记帐分解信息
     */
    @Transactional
    private void saveHsCoinToMoney(HsbToCash hsbToCash, String transCode) {
        // 记帐分解LIST
        List<AccountingEntry> actDetails = new ArrayList<AccountingEntry>();
        // 记帐分解对象
        AccountingEntry detail = null;
        int entStatus = 0;

        /******************************* 互生币帐户减少 ***************************************/
        String guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
        // 实例化记帐分解对象
        detail = new AccountingEntry(//
                guid,//
                hsbToCash.getTransNo(),// 交易流水号
                transCode,// 设置交易类型
                hsbToCash.getCustId(), // 客户号
                hsbToCash.getHsResNo(), // 互生号
                hsbToCash.getCustType(), // 客户类型
                AccountType.ACC_TYPE_POINT20110.getCode(), // 帐户类型:20110流通互生币帐户
                "0",// 增金额
                hsbToCash.getFromHsbAmt(),// 减少金额
                MoneyType.HSB.getCode(),// 货币币种
                hsbToCash.getReqTime(), // 记帐日期
                "互生币转货币", // 备注
                true// 记账状态
        );
        // 设置保底值(企业停止积分后，互生币转货币的保底互生币取消)
        if (CustType.TRUSTEESHIP_ENT.getCode() == hsbToCash.getCustType()// 托管企业
                || CustType.MEMBER_ENT.getCode() == hsbToCash.getCustType()// 成员企业
        )
        {
            // 查询客户信息
            BsEntStatusInfo entStatusInfo = dubboInvokService.getCustInfo(hsbToCash.getCustType(), hsbToCash
                    .getCustId());
            if (entStatusInfo != null && entStatusInfo.getBaseStatus() != null)
            {
                /**
                 * "基本状态1：正常(NORMAL) 成员企业、托管企业用 2：预警(WARNING) 成员企业、托管企业用
                 * 3：休眠(DORMANT) 成员企业用 4：长眠（申请注销）成员企业 5：已注销 成员企业 6：申请停止积分活动中
                 * 托管企业用 7：停止积分活动 托管企业用"
                 */
                entStatus = entStatusInfo.getBaseStatus();
            }
            // 未停止积分活动
            if (entStatus != 7)
            {
                // 设置保底值
                detail.setGuaranteedValue(accoountRuleService.getHsbSaveAmount(hsbToCash.getCustId(), hsbToCash
                        .getCustType()));
            }
        }
        // 添加到集合
        actDetails.add(detail);

        /******************************* 货币帐户增加 ***************************************/
        guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
        // 实例化记帐分解对象
        detail = new AccountingEntry(//
                guid,//
                hsbToCash.getTransNo(),// 交易流水号
                transCode,// 设置交易类型
                hsbToCash.getCustId(), // 客户号
                hsbToCash.getHsResNo(), // 互生号
                hsbToCash.getCustType(), // 客户类型
                AccountType.ACC_TYPE_POINT30110.getCode(), // 帐户类型:30110货币帐户
                hsbToCash.getToCashAmt(), // 增金额
                "0",// 减金额
                MoneyType.CUR.getCode(),// 货币币种
                hsbToCash.getReqTime(), // 记帐日期
                "互生币转货币", // 备注
                true// 记账状态
        );
        // 添加到集合
        actDetails.add(detail);

        /******************************* 平台收取1%货币转换费互生币帐户增加 ***************************************/
        guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
        // 实例化记帐分解对象
        detail = new AccountingEntry(//
                guid, //
                hsbToCash.getTransNo(),// 交易流水号
                transCode,// 设置交易类型
                commonService.getPlatCustId(), // 客户号
                commonService.getPlatResNo(), // 互生号
                CustType.AREA_PLAT.getCode(), // 客户类型(地区平台)
                AccountType.ACC_TYPE_POINT20410.getCode(), // 帐户类型:20410货币转换费
                hsbToCash.getFeeHsbAmt(), // 增金额
                "0",// 减金额
                MoneyType.HSB.getCode(),// 货币币种
                hsbToCash.getReqTime(), // 记帐日期
                "互生币转货币", // 备注
                true// 记账状态
        );
        // 添加到集合
        actDetails.add(detail);

        // 保存记账分解并记账
        saveAccounting(actDetails, true);
    }

    /**
     * 销户互生币转货币分解规则
     * 
     * @param hsbToCash
     *            互生币转货币信息
     * @param AccountingEntry
     *            记帐分解信息
     */
    @Transactional
    private void closeAccountHsbToCash(HsbToCash hsbToCash, String transCode) {
        // 记帐分解LIST
        List<AccountingEntry> actDetails = new ArrayList<AccountingEntry>();
        // 记帐分解对象
        AccountingEntry detail = null;

        /******************************* 互生币帐户减少 ***************************************/
        String guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "销户互生币转货币互生币帐户减少时生成的主键ID1：" + guid);
        // 实例化记帐分解对象
        detail = new AccountingEntry(//
                guid,//
                hsbToCash.getTransNo(),// 交易流水号
                transCode,// 设置交易类型
                hsbToCash.getCustId(), // 客户号
                hsbToCash.getHsResNo(), // 互生号
                hsbToCash.getCustType(), // 客户类型
                AccountType.ACC_TYPE_POINT20110.getCode(), // 帐户类型:20110流通互生币帐户
                "0",// 增金额
                hsbToCash.getFromHsbAmt(),// 减少金额
                MoneyType.HSB.getCode(),// 货币币种
                hsbToCash.getReqTime(), // 记帐日期
                "互生币转货币", // 备注
                true// 记账状态
        );
        // 添加到集合
        actDetails.add(detail);

        /******************************* 货币帐户增加 ***************************************/
        guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "销户互生币转货币货币帐户增加时生成的主键ID2：" + guid);
        // 实例化记帐分解对象
        detail = new AccountingEntry(//
                guid,//
                hsbToCash.getTransNo(),// 交易流水号
                transCode,// 设置交易类型
                hsbToCash.getCustId(), // 客户号
                hsbToCash.getHsResNo(), // 互生号
                hsbToCash.getCustType(), // 客户类型
                AccountType.ACC_TYPE_POINT30110.getCode(), // 帐户类型:30110货币帐户
                hsbToCash.getToCashAmt(), // 增金额
                "0",// 减金额
                MoneyType.CUR.getCode(),// 货币币种
                hsbToCash.getReqTime(), // 记帐日期
                "互生币转货币", // 备注
                true// 记账状态
        );
        // 添加到集合
        actDetails.add(detail);

        /******************************* 平台收取1%货币转换费互生币帐户增加 ***************************************/
        guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "销户互生币转货币平台收取1%货币转换费时生成的主键ID3：" + guid);
        // 实例化记帐分解对象
        detail = new AccountingEntry(//
                guid,//
                hsbToCash.getTransNo(),// 交易流水号
                transCode,// 设置交易类型
                commonService.getPlatCustId(), // 客户号
                commonService.getPlatResNo(), // 互生号
                CustType.AREA_PLAT.getCode(), // 客户类型(地区平台)
                AccountType.ACC_TYPE_POINT20410.getCode(), // 帐户类型:20410货币转换费
                hsbToCash.getFeeHsbAmt(), // 增金额
                "0",// 减金额
                MoneyType.HSB.getCode(),// 货币币种
                hsbToCash.getReqTime(), // 记帐日期
                "互生币转货币", // 备注
                true// 记账状态
        );
        // 添加到集合
        actDetails.add(detail);

        // 保存记账分解并记账
        saveAccounting(actDetails, true);
    }

    /**
     * 代兑互生币(企业互生币转消费者互生币)分解规则
     * 
     * @param proxyBuyHsb
     *            代兑互生币信息
     */
    @Transactional
    private void saveProxyBuyHsbAccountDetail(ProxyBuyHsb proxyBuyHsb) {
        // 对象为空
        HsAssert.notNull(proxyBuyHsb, RespCode.PARAM_ERROR, "记帐分解：代兑互生币对象为空");
        // 校验客户号，帐务要求必传客户号
        HsAssert.hasText(proxyBuyHsb.getEntCustId(), RespCode.PARAM_ERROR, "银行转帐记帐分解：企业客户号为空");
        // 校验客户号，帐务要求必传客户号
        HsAssert.hasText(proxyBuyHsb.getPerCustId(), RespCode.PARAM_ERROR, "银行转帐记帐分解：持卡人客户号为空");
        // 交易码
        String transCode = TransType.C_HSB_P_HSB_RECHARGE.getCode();

        // 代兑互生币分解
        saveProxyHsbToCustomer(proxyBuyHsb, transCode);
    }

    /**
     * POS代兑互生币(企业互生币转消费者互生币)分解规则
     * 
     * @param proxyBuyHsb
     *            代兑互生币信息
     * @param transCode
     *            交易码
     * @param channelName
     *            渠道名称
     */
    @Transactional
    private void saveProxyHsbToCustomer(ProxyBuyHsb proxyBuyHsb, String transCode) {
        // 记帐分解LIST
        List<AccountingEntry> actDetails = new ArrayList<AccountingEntry>();
        // 记帐分解对象
        AccountingEntry detail = null;
        String guid = "";
        // 本地企业为本地消费者代兑互生币，记账时扣除本地企业互生币，增加本地消费者互生币
        if (proxyBuyHsb.getTransMode() == ProxyTransMode.LOCAL_ENT_TO_LOCAL_CUST.getCode().intValue())
        {
            /******************************* 企业流通币帐户减少 ***************************************/
            guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
            // 实例化记帐分解对象
            detail = new AccountingEntry(//
                    guid,//
                    proxyBuyHsb.getTransNo(),// 交易流水号
                    transCode,// 设置交易类型
                    proxyBuyHsb.getEntCustId(), // 客户号
                    proxyBuyHsb.getEntResNo(), // 互生号
                    proxyBuyHsb.getEntCustType(), // 客户类型
                    AccountType.ACC_TYPE_POINT20110.getCode(), // 帐户类型:20110流通币帐户
                    "0",// 增金额
                    proxyBuyHsb.getBuyHsbAmt(), // 减少金额
                    MoneyType.HSB.getCode(),// 货币币种
                    proxyBuyHsb.getReqTime(),// 记帐时间
                    "代兑换互生币扣除", // 备注
                    true// 记账状态
            );
            // 将POS流水号传给帐务显示
            detail.setSourceTransNo(proxyBuyHsb.getOriginNo());
            // 添加到集合
            actDetails.add(detail);

            /******************************* 消费者互生币帐户增加 ***************************************/
            guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
            // 实例化记帐分解对象
            detail = new AccountingEntry(//
                    guid,//
                    proxyBuyHsb.getTransNo(),// 交易流水号
                    transCode,// 设置交易类型
                    proxyBuyHsb.getPerCustId(), // 客户号
                    proxyBuyHsb.getPerResNo(), // 互生号
                    proxyBuyHsb.getPerCustType(), // 客户类型
                    AccountType.ACC_TYPE_POINT20110.getCode(), // 帐户类型:20110流通互生币帐户
                    proxyBuyHsb.getBuyHsbAmt(), // 增金额
                    "0",// 减金额
                    MoneyType.HSB.getCode(),// 货币币种
                    proxyBuyHsb.getReqTime(),// 记帐时间
                    "兑换互生币收入", // 备注
                    true// 记账状态
            );
            // 将POS流水号传给帐务显示
            detail.setSourceTransNo(proxyBuyHsb.getOriginNo());
            // 添加到集合
            actDetails.add(detail);
        }
        else
        // 本地企业为异地消费者代兑互生币，记账时扣除本地企业互生币
        if (proxyBuyHsb.getTransMode() == ProxyTransMode.LOCAL_ENT_TO_DIFF_CUST.getCode().intValue())
        {
            // 交易类型码
            transCode = TransType.C_HSB_P_ACROSS_RECHARGE_FOR_C.getCode();
            /******************************* 企业流通币帐户减少 ***************************************/
            guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
            // 实例化记帐分解对象
            detail = new AccountingEntry(//
                    guid,//
                    proxyBuyHsb.getTransNo(),// 交易流水号
                    transCode,// 设置交易类型
                    proxyBuyHsb.getEntCustId(), // 客户号
                    proxyBuyHsb.getEntResNo(), // 互生号
                    proxyBuyHsb.getEntCustType(), // 客户类型
                    AccountType.ACC_TYPE_POINT20110.getCode(), // 帐户类型:20110流通币帐户
                    "0",// 增金额
                    proxyBuyHsb.getBuyHsbAmt(), // 减少金额
                    MoneyType.HSB.getCode(),// 货币币种
                    proxyBuyHsb.getReqTime(),// 记帐时间
                    "代兑换互生币扣除", // 备注
                    true// 记账状态
            );
            // 将POS流水号传给帐务显示
            detail.setSourceTransNo(proxyBuyHsb.getOriginNo());
            // 添加到集合
            actDetails.add(detail);
        }
        else
        // 异地企业为本地消费者代兑互生币，记账时增加本地消费者互生币
        if (proxyBuyHsb.getTransMode() == ProxyTransMode.DIFF_ENT_TO_LOCAL_CUST.getCode().intValue())
        {
            // 交易类型码
            transCode = TransType.C_HSB_P_ACROSS_RECHARGE_FOR_P.getCode();
            /******************************* 消费者互生币帐户增加 ***************************************/
            guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
            // 实例化记帐分解对象
            detail = new AccountingEntry(//
                    guid,//
                    proxyBuyHsb.getTransNo(),// 交易流水号
                    transCode,// 设置交易类型
                    proxyBuyHsb.getPerCustId(), // 客户号
                    proxyBuyHsb.getPerResNo(), // 互生号
                    proxyBuyHsb.getPerCustType(), // 客户类型
                    AccountType.ACC_TYPE_POINT20110.getCode(), // 帐户类型:20110流通互生币帐户
                    proxyBuyHsb.getBuyHsbAmt(), // 增金额
                    "0",// 减金额
                    MoneyType.HSB.getCode(),// 货币币种
                    proxyBuyHsb.getReqTime(),// 记帐时间
                    "兑换互生币收入", // 备注
                    true// 记账状态
            );
            // 将POS流水号传给帐务显示
            detail.setSourceTransNo(proxyBuyHsb.getOriginNo());
            // 添加到集合
            actDetails.add(detail);
        }
        /******************************* 平台给企业代兑互生币服务费减少1% 16.1.8注释 ***************************************/
        /*
         * // 实例化记帐分解对象 detail = new AccountingEntry(// guid, // 记帐流水号
         * proxyBuyHsb.getTransNo(),// 交易流水号 transCode,// 设置交易类型
         * commonService.getPlatCustId(), // 客户号 commonService.getPlatResNo(),
         * // 互生号 CustType.AREA_PLAT.getCode(), // 客户类型(地区平台)
         * AccountType.ACC_TYPE_POINT20510.getCode(), // 帐户类型:20510代兑互生币服务费1%
         * "0", // 增金额 String.valueOf(Amount.sub(proxyBuyHsb.getBuyHsbAmt(),
         * aoConfig.getExchangeRate())),// 减金额 MoneyType.HSB.getCode(),// 货币币种
         * proxyBuyHsb.getReqTime(),// 记帐时间 "代兑互生币业务平台代兑服务费帐户减少1%", // 备注 true//
         * 记账状态 ); // 添加到集合 actDetails.add(detail);
         *//******************************* 企业代兑互生币服务费增加 1% ***************************************/
        /*
         * // 实例化记帐分解对象 detail = new AccountingEntry(// guid, // 记帐流水号
         * proxyBuyHsb.getTransNo(),// 交易流水号 transCode,// 设置交易类型
         * proxyBuyHsb.getEntCustId(), // 客户号 proxyBuyHsb.getEntResNo(), // 互生号
         * proxyBuyHsb.getEntCustType(), // 客户类型
         * AccountType.ACC_TYPE_POINT20110.getCode(), // 20110流通互生币帐户
         * String.valueOf(Amount.mul(proxyBuyHsb.getBuyHsbAmt(),
         * aoConfig.getExchangeRate())),// 增金额 "0", // 减少金额
         * MoneyType.HSB.getCode(),// 货币币种 proxyBuyHsb.getReqTime(),// 记帐时间
         * "代兑互生币业务企业互生币帐户增加金额", // 备注 true// 记账状态 ); // 添加到集合
         * actDetails.add(detail);
         */

        // 保存记账分解并记账
        saveAccounting(actDetails, true);
    }

    /**
     * 保存兑换互生币记帐分解
     * 
     * @param buyHsb
     *            兑换互生币信息
     */
    @Transactional
    private void saveBuyHsbOrderAccountDetail(BuyHsb buyHsb) {
        // 交易码
        String transCode = "";
        // 获取交易码
        transCode = TransCodeUtil.checkBuyHsbTransCode(buyHsb.getPayModel(), buyHsb.getCustType());

        // 货币支付方式
        if (buyHsb.getPayModel() == PayChannel.MONEY_PAY.getCode().intValue())
        {
            // 货币支付方式
            payChannelByCash(buyHsb, transCode);
        }
        else
        // 网银支付方式
        {
            // 网银支付方式
            payChannelByEbank(buyHsb, transCode);
        }
    }

    /**
     * 网银支付方式
     * 
     * @param buyHsb
     *            订单对象
     * @param transCode
     *            交易码
     */
    @Transactional
    private void payChannelByEbank(BuyHsb buyHsb, String transCode) {
        // 记帐分解LIST
        List<AccountingEntry> actDetails = new ArrayList<AccountingEntry>();
        // 记帐分解对象
        AccountingEntry detail = null;

        String remark = "";
        // 根据支付方式确定备注信息
        if (PayChannel.E_BANK_PAY.getCode().intValue() == buyHsb.getPayModel())
        {// 网支付支付
            remark = "网银支付兑换互生币";
        }
        else if (PayChannel.QUICK_PAY.getCode().intValue() == buyHsb.getPayModel())
        {// 快捷支付
            remark = "快捷支付兑换互生币";
        }
        else if (PayChannel.MOBILE_PAY.getCode().intValue() == buyHsb.getPayModel())
        {// 手机网银支付
            remark = "手机网银支付兑换互生币";
        }
        /******************************* 互生币帐户增加 ***************************************/
        String guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
        // 实例化记帐分解对象
        detail = new AccountingEntry(//
                guid,//
                buyHsb.getTransNo(),// 交易流水号
                transCode,// 设置交易类型
                buyHsb.getCustId(), // 客户号
                buyHsb.getHsResNo(), // 互生号
                buyHsb.getCustType(), // 客户类型
                AccountType.ACC_TYPE_POINT20110.getCode(), // 帐户类型:20110流通互生币帐户
                buyHsb.getBuyHsbAmt(),// 增金额
                "0",// 减金额
                MoneyType.HSB.getCode(),// 货币币种
                buyHsb.getReqTime(),// 记帐时间
                remark, // 备注
                true// 记账状态
        );
        // 将POS流水号传给帐务显示
        detail.setSourceTransNo(buyHsb.getOriginNo());
        // 添加到集合
        actDetails.add(detail);

        /******************************* 平台网银收款(本币)增加 ***************************************/
        guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
        // 实例化记帐分解对象
        detail = new AccountingEntry(//
                guid,//
                buyHsb.getTransNo(),// 交易流水号
                transCode,// 设置交易类型
                commonService.getPlatCustId(), // 客户号
                commonService.getPlatResNo(), // 互生号
                CustType.AREA_PLAT.getCode(), // 客户类型(地区平台)
                AccountType.ACC_TYPE_POINT50110.getCode(), // 帐户类型:50110平台网银收款(本币)
                buyHsb.getCashAmt(),// 增金额
                "0",// 减金额
                MoneyType.CUR.getCode(),// 货币币种
                buyHsb.getReqTime(), // 记帐时间
                remark, // 备注
                true// 记账状态
        );
        // 添加到集合
        actDetails.add(detail);

        // 保存记账分解并记账
        saveAccounting(actDetails, true);
    }

    /**
     * 货币支付方式
     * 
     * @param buyHsb
     *            订单对象
     * @param transCode
     *            交易码
     */
    @Transactional
    private void payChannelByCash(BuyHsb buyHsb, String transCode) {

        // 记帐分解LIST
        List<AccountingEntry> actDetails = new ArrayList<AccountingEntry>();
        // 记帐分解对象
        AccountingEntry detail = null;

        /******************************* 货币帐户减少 ***************************************/
        String guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
        // 实例化记帐分解对象
        detail = new AccountingEntry(//
                guid,//
                buyHsb.getTransNo(),// 交易流水号
                transCode,// 设置交易类型
                buyHsb.getCustId(), // 客户号
                buyHsb.getHsResNo(), // 互生号
                buyHsb.getCustType(), // 客户类型
                AccountType.ACC_TYPE_POINT30110.getCode(), // 帐户类型:30110货币帐户
                "0",// 增加金额
                buyHsb.getCashAmt(),// 减少金额
                MoneyType.CUR.getCode(),// 货币币种
                buyHsb.getReqTime(), // 记帐时间
                "货币支付兑换互生币", // 备注
                true// 记账状态
        );
        // 将POS流水号传给帐务显示
        detail.setSourceTransNo(buyHsb.getOriginNo());
        // 添加到集合
        actDetails.add(detail);

        /******************************* 互生币帐户增加 ***************************************/
        guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
        // 实例化记帐分解对象
        detail = new AccountingEntry(//
                guid,//
                buyHsb.getTransNo(),// 交易流水号
                transCode,// 设置交易类型
                buyHsb.getCustId(), // 客户号
                buyHsb.getHsResNo(), // 互生号
                buyHsb.getCustType(), // 客户类型
                AccountType.ACC_TYPE_POINT20110.getCode(), // 帐户类型:20110互生币帐户
                buyHsb.getBuyHsbAmt(), // 增加金额
                "0",// 减少金额
                MoneyType.HSB.getCode(),// 货币币种
                buyHsb.getReqTime(),// 记帐时间
                "货币账户支付兑换互生币", // 备注
                true// 记账状态
        );
        // 将POS流水号传给帐务显示
        detail.setSourceTransNo(buyHsb.getOriginNo());
        // 添加到集合
        actDetails.add(detail);

        // 保存记账分解并记账
        saveAccounting(actDetails, true);
    }

    /**
     * 保存记账分解，如果是立即记账，还要调用账务实时记账
     * 
     * @param actDetails
     *            记账分解记录
     * @param immediately
     *            是否立即记账
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    private void saveAccounting(List<AccountingEntry> actDetails, boolean immediately) {
        try
        {
            // 批量新增分解记录
            accountingMapper.insertBatchAccounting(actDetails);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_SAVE_ACCOUNTING_ERROR
                    .getCode()
                    + ":保存记帐分解异常,param=" + JSON.toJSONString(actDetails), e);
            throw new HsException(AOErrorCode.AO_SAVE_ACCOUNTING_ERROR, "保存记帐分解异常,param="
                    + JSON.toJSONString(actDetails) + "\n" + e);
        }

        // 如果是实时记账，调账务接口进行记账
        if (immediately)
        {
            // 调用帐务系统记帐
            dubboInvokService.actualAccount(actDetails);
        }
    }

    /**
     * 保存银行转帐记帐分解记录
     * 
     * @param transOut
     *            银行转帐信息
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    private void saveTransOutAccountDetail(TransOut transOut) {
        // 对象为空
        HsAssert.notNull(transOut, RespCode.PARAM_ERROR, "记帐分解：银行转帐对象为空");
        // 校验客户号，帐务要求必传客户号
        HsAssert.hasText(transOut.getCustId(), RespCode.PARAM_ERROR, "银行转帐记帐分解：客户号为空");
        // 获取交易码
        String transCode = TransCodeUtil.getBankTransCode(transOut.getTransStatus(), transOut.getCustType());

        // 银行转帐预转出
        if (TransStatus.APPLYING.getCode().intValue() == transOut.getTransStatus()// 申请中
                || TransStatus.PAYING.getCode().intValue() == transOut.getTransStatus()// 付款中
        )
        {
            // 银行预转出记帐分解
            bankPreTransOut(transOut, transCode);
        }
        else
        // 银行转账转出
        if (TransStatus.TRANS_SUCCESS.getCode().intValue() == transOut.getTransStatus())
        {
            // 银行转账转出记帐分解
            bankTransOut(transOut, transCode);
        }
        else
        // 撤单
        if (TransStatus.REVOKED.getCode().intValue() == transOut.getTransStatus())// 禁止转出
        {
            // 银行转账禁止转出退回分解
            bankTransOutFail(transOut, transCode, "货币提现撤单");
        }
        else
        // 银行转账失败退回
        if (TransStatus.TRANS_FAILED.getCode().intValue() == transOut.getTransStatus()// 提现失败
                || TransStatus.REVERSED.getCode().intValue() == transOut.getTransStatus()// 交易错误
        )
        {
            // 银行转账失败退回记帐分解
            bankTransOutFail(transOut, transCode, "货币提现失败退回");
        }
        else
        // 银行转账银行退票退回
        if (TransStatus.BANK_BACK.getCode().intValue() == transOut.getTransStatus())
        {
            // 银行转账银行退票退回记帐分解
            bankTransOutBack(transOut, transCode);
        }
    }

    /**
     * 银行转帐银行退票退回分解
     * 
     * @param transOut
     *            银行转帐信息
     * @param transCode
     *            交易码
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    private void bankTransOutBack(TransOut transOut, String transCode) {
        // 记帐分解LIST
        List<AccountingEntry> actDetails = new ArrayList<AccountingEntry>();
        // 记帐分解对象
        AccountingEntry detail = null;

        /******************************* 银行转帐银行退票退回：货币帐户增加 ***************************************/
        String guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
        // 实例化记帐分解对象
        detail = new AccountingEntry(//
                guid,//
                transOut.getTransNo(),// 交易流水号
                transCode,// 设置交易类型
                transOut.getCustId(), // 客户号
                transOut.getHsResNo(), // 互生号
                transOut.getCustType(), // 客户类型
                AccountType.ACC_TYPE_POINT30110.getCode(), // 帐户类型:货币帐户
                // BigDecimalUtils.floorAdd(transOut.getAmount(),
                // transOut.getFeeAmt()).toString(), // 增加金额（转账失败不收手续费）
                transOut.getAmount(), // 增加金额（业务确定退票平台不退还手续费）
                "0",// 减少金额
                commonService.getLocalInfo().getCurrencyCode(),// 货币币种
                transOut.getReqTime(),// 记帐日期
                "货币提现失败退回", // 备注
                true// 记账状态
        );
        // 失败记帐流水号为失败记录ID
        detail.setAccountTransNo(transOut.getTransFailNo());
        // 添加到集合
        actDetails.add(detail);

        /******************************* 银行转帐银行退票退回：平台网银转出(本币)帐户减少 ***************************************/
        guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
        // 实例化记帐分解对象
        detail = new AccountingEntry(//
                guid,//
                transOut.getTransNo(),// 交易流水号
                transCode,// 设置交易类型
                commonService.getPlatCustId(), // 客户号
                commonService.getPlatResNo(), // 互生号
                CustType.AREA_PLAT.getCode(), // 客户类型
                AccountType.ACC_TYPE_POINT50120.getCode(), // 帐户类型:50120平台网银转出(本币)
                "0", // 增加金额
                transOut.getAmount(),// 减少金额
                commonService.getLocalInfo().getCurrencyCode(),// 货币币种
                transOut.getReqTime(),// 记帐日期
                "货币提现失败退回", // 备注
                true// 记账状态
        );
        // 失败记帐流水号为失败记录ID
        detail.setAccountTransNo(transOut.getTransFailNo());
        // 添加到集合
        actDetails.add(detail);

        /******************************* 银行转帐银行退票退回：平台网银转出手续费帐户减少 （业务确定退票平台不退还手续费） ***************************************/
        // 手续费大于0时才生成记录
        // if (Double.parseDouble(transOut.getFeeAmt()) > 0)
        // {
        // // 实例化记帐分解对象
        // detail = new AccountingEntry(//
        // guid, // 记帐流水号
        // transOut.getTransNo(),// 交易流水号
        // transCode,// 设置交易类型
        // commonService.getPlatCustId(), // 客户号
        // commonService.getPlatResNo(), // 互生号
        // CustType.AREA_PLAT.getCode(), // 客户类型(地区平台)
        // AccountType.ACC_TYPE_POINT30410.getCode(), // 帐户类型:30410手续费金额
        // "0", // 增加金额
        // transOut.getFeeAmt(),// 减少金额(手续费金额)
        // commonService.getLocalInfo().getCurrencyCode(),// 货币币种
        // transOut.getReqTime(),// 记帐日期
        // "货币提现失败退回", // 备注
        // true// 记账状态
        // );
        // // 失败记帐流水号为失败记录ID
        // detail.setAccountTransNo(transOut.getTransFailNo());
        // actDetails.add(detail);
        // }
        // 保存记账分解并记账
        saveAccounting(actDetails, true);
    }

    /**
     * 银行转帐失败退回分解
     * 
     * @param transOut
     *            银行转帐信息
     * @param transCode
     *            交易码
     * @param remark
     *            备注
     */
    @Transactional
    private void bankTransOutFail(TransOut transOut, String transCode, String remark) {
        // 记帐分解LIST
        List<AccountingEntry> actDetails = new ArrayList<AccountingEntry>();
        // 记帐分解对象
        AccountingEntry detail = null;

        /******************************* 银行转帐失败退回：货币帐户增加 ***************************************/
        String guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
        // 实例化记帐分解对象
        detail = new AccountingEntry(//
                guid,//
                transOut.getTransNo(),// 交易流水号
                transCode,// 设置交易类型
                transOut.getCustId(), // 客户号
                transOut.getHsResNo(), // 互生号
                transOut.getCustType(), // 客户类型
                AccountType.ACC_TYPE_POINT30110.getCode(), // 帐户类型:货币帐户
                transOut.getAmount(), // 增加金额（转账失败不收手续费）
                "0",// 减少金额
                commonService.getLocalInfo().getCurrencyCode(),// 货币币种
                transOut.getReqTime(),// 记帐日期
                remark, // 备注
                true// 记账状态
        );
        // 失败记帐流水号为失败记录ID
        detail.setAccountTransNo(guid);
        // 添加到集合
        actDetails.add(detail);

        /******************************* 平台网银转出手续费帐户增加 ***************************************/
        guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
        // 获取手续费交易码
        transCode = TransCodeUtil.getBankTransCode(TransStatus.TRANS_SUCCESS.getCode(), transOut.getCustType());
        // 手续费大于0时才生成
        if (Double.parseDouble(transOut.getFeeAmt()) > 0)
        {
            // 实例化记帐分解对象
            detail = new AccountingEntry(//
                    guid,//
                    transOut.getTransNo(),// 交易流水号
                    transCode,// 设置交易类型
                    commonService.getPlatCustId(), // 客户号
                    commonService.getPlatResNo(), // 互生号
                    CustType.AREA_PLAT.getCode(), // 客户类型(地区平台)
                    AccountType.ACC_TYPE_POINT30410.getCode(), // 帐户类型:30410手续费金额
                    transOut.getFeeAmt(),// 增加金额(手续费金额)调用支付系统接口算出
                    "0", // 减少金额
                    transOut.getCurrencyCode(),// 货币币种
                    transOut.getReqTime(),// 记帐日期
                    "货币提现", // 备注
                    true// 记账状态
            );
            actDetails.add(detail);

            /******************************* 货币帐户（转账手续费）减少 ***************************************/
            guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
            // 实例化记帐分解对象
            detail = new AccountingEntry(//
                    guid,//
                    transOut.getTransNo(),// 交易流水号
                    transCode,// 设置交易类型
                    transOut.getCustId(), // 客户号
                    transOut.getHsResNo(), // 互生号
                    transOut.getCustType(), // 客户类型
                    AccountType.ACC_TYPE_POINT30110.getCode(), // 帐户类型:30110手续费金额
                    "0", // 增加金额
                    transOut.getFeeAmt(),// 减少金额(手续费金额)调用支付系统接口算出
                    transOut.getCurrencyCode(),// 货币币种
                    transOut.getReqTime(),// 记帐日期
                    "货币提现扣除转账手续费", // 备注
                    true// 记账状态
            );
            // 记帐手续费流水号为分解流水号
            detail.setAccountTransNo(guid);
            // 扣除手续费货币帐户允许为负数
            detail.setPositiveNegative("2");
            actDetails.add(detail);
        }
        /******************************* 银行转帐失败退回：平台网银转出待结算网银转出帐户减少 12.23去掉 ***************************************/
        // 实例化记帐分解对象
        // detail = new AccountingEntry(//
        // guid, // 记帐流水号
        // transOut.getTransNo(),// 交易流水号
        // transCode,// 设置交易类型
        // commonService.getPlatCustId(), // 客户号
        // commonService.getPlatResNo(), // 互生号
        // CustType.AREA_PLAT.getCode(), // 客户类型(地区平台)
        // AccountType.ACC_TYPE_POINT50121.getCode(), // 帐户类型:50121待结算网银转出帐户
        // "0",// 增加金额(手续费金额)
        // transOut.getAmount(), // 减少金额
        // transOut.getCurrencyCode(),// 货币币种
        // transOut.getReqTime(),// 记帐日期
        // "银行转帐失败退回：平台网银转出待结算网银转出帐户减少金额", // 备注
        // true// 记账状态
        // );
        // actDetails.add(detail);

        // 保存记账分解并记账
        saveAccounting(actDetails, true);
    }

    /**
     * 银行转帐转出分解
     * 
     * @param transOut
     *            银行转帐信息
     * @param transCode
     *            交易码
     */
    @Transactional
    private void bankTransOut(TransOut transOut, String transCode) {

        // 记帐分解LIST
        List<AccountingEntry> actDetails = new ArrayList<AccountingEntry>();
        // 记帐分解对象
        AccountingEntry detail = null;

        /******************************* 银行转帐转出平台网银转出(本币)帐户增加 ***************************************/
        String guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
        // 实例化记帐分解对象
        detail = new AccountingEntry(//
                guid, // 记帐流水号
                transOut.getTransNo(),// 交易流水号
                transCode,// 设置交易类型
                commonService.getPlatCustId(), // 客户号
                commonService.getPlatResNo(), // 互生号
                CustType.AREA_PLAT.getCode(), // 客户类型
                AccountType.ACC_TYPE_POINT50120.getCode(), // 帐户类型:50120平台网银转出(本币)
                transOut.getAmount(),// 增加金额
                "0", // 减少金额
                transOut.getCurrencyCode(),// 货币币种
                transOut.getReqTime(),// 记帐日期
                "货币提现", // 备注
                true// 记账状态
        );
        // 添加到集合
        actDetails.add(detail);

        /******************************* 平台网银转出手续费帐户增加 ***************************************/
        guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
        // 手续费大于0时才生成
        if (Double.parseDouble(transOut.getFeeAmt()) > 0)
        {
            // 实例化记帐分解对象
            detail = new AccountingEntry(//
                    guid,//
                    transOut.getTransNo(),// 交易流水号
                    transCode,// 设置交易类型
                    commonService.getPlatCustId(), // 客户号
                    commonService.getPlatResNo(), // 互生号
                    CustType.AREA_PLAT.getCode(), // 客户类型(地区平台)
                    AccountType.ACC_TYPE_POINT30410.getCode(), // 帐户类型:30410手续费金额
                    transOut.getFeeAmt(),// 增加金额(手续费金额)调用支付系统接口算出
                    "0", // 减少金额
                    transOut.getCurrencyCode(),// 货币币种
                    transOut.getReqTime(),// 记帐日期
                    "货币提现", // 备注
                    true// 记账状态
            );
            actDetails.add(detail);

            /******************************* 货币帐户（转账手续费）减少 ***************************************/
            guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
            // 实例化记帐分解对象
            detail = new AccountingEntry(//
                    guid,//
                    transOut.getTransNo(),// 交易流水号
                    transCode,// 设置交易类型
                    transOut.getCustId(), // 客户号
                    transOut.getHsResNo(), // 互生号
                    transOut.getCustType(), // 客户类型
                    AccountType.ACC_TYPE_POINT30110.getCode(), // 帐户类型:30110手续费金额
                    "0", // 增加金额
                    transOut.getFeeAmt(),// 减少金额(手续费金额)调用支付系统接口算出
                    transOut.getCurrencyCode(),// 货币币种
                    transOut.getReqTime(),// 记帐日期
                    "货币提现扣除转账手续费", // 备注
                    true// 记账状态
            );
            // 记帐手续费流水号为分解流水号
            detail.setAccountTransNo(guid);
            // 扣除手续费货币帐户允许为负数
            detail.setPositiveNegative("2");
            actDetails.add(detail);
        }
        /******************************* 平台网银转出待结算网银转出减少 *****************12.2去掉 **********************/
        // 实例化记帐分解对象
        // detail = new AccountingEntry(//
        // guid, // 记帐流水号
        // transOut.getTransNo(),// 交易流水号
        // transCode,// 设置交易类型
        // platResNo + DateUtil.getCurrentDateNoSign(), // 客户号
        // platResNo, // 互生号
        // CustType.AREA_PLAT.getCode(), // 客户类型(地区平台)
        // AccountType.ACC_TYPE_POINT50121.getCode(), // 帐户类型:50121待结算网银转出帐户
        // "0",// 增加金额(手续费金额)
        // transOut.getAmount(), // 减少金额
        // transOut.getCurrencyCode(),// 货币币种
        // transOut.getReqTime(),// 记帐日期
        // "银行转帐平台待结算网银转出帐户减少金额", // 备注
        // true// 记账状态
        // );
        // actDetails.add(detail);

        // 保存记账分解并记账
        saveAccounting(actDetails, true);
    }

    /**
     * 银行转帐预转出分解
     * 
     * @param transOut
     *            银行转帐信息
     * @param transCode
     *            交易代码
     */
    @Transactional
    private void bankPreTransOut(TransOut transOut, String transCode) {

        // 记帐分解LIST
        List<AccountingEntry> actDetails = new ArrayList<AccountingEntry>();
        // 记帐分解对象
        AccountingEntry detail = null;

        /******************************* 银行预转帐货币帐户减少 ***************************************/
        String guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
        // 实例化记帐分解对象
        detail = new AccountingEntry(//
                guid, // 记帐流水号
                transOut.getTransNo(),// 交易流水号
                transCode,// 设置交易类型
                transOut.getCustId(), // 客户号
                transOut.getHsResNo(), // 互生号
                transOut.getCustType(), // 客户类型
                AccountType.ACC_TYPE_POINT30110.getCode(), // 帐户类型:货币帐户
                "0",// 增加金额
                transOut.getAmount(), // 减少金额
                transOut.getCurrencyCode(),// 货币币种
                transOut.getReqTime(),// 记帐日期
                "货币提现", // 备注
                true// 记账状态
        );
        // 添加到集合
        actDetails.add(detail);

        /******************************* 平台待结算网银转出帐户增加 *****************12.2去掉 **********************/
        // detail = new AccountingEntry(//
        // guid, // 记帐流水号
        // transOut.getTransNo(),// 交易流水号
        // transCode,// 设置交易类型
        // platResNo + DateUtil.getCurrentDateNoSign(), // 客户号
        // platResNo, // 互生号
        // CustType.AREA_PLAT.getCode(), // 客户类型(地区平台)
        // AccountType.ACC_TYPE_POINT50121.getCode(), // 帐户类型:50121待结算网银转出
        // transOut.getAmount(),// 增加金额(手续费金额)
        // "0", // 减少金额
        // transOut.getCurrencyCode(),// 货币币种
        // transOut.getReqTime(),// 记帐日期
        // "银行预转帐平台待结算网银转出帐户增加金额", // 备注
        // true// 记账状态
        // );
        // actDetails.add(detail);

        // 保存记账分解并记账
        saveAccounting(actDetails, true);
    }

    /**
     * 根据业务流水号查询是否已生成过记帐记录 用于同一个业务流水号多次记帐时生成新的会计日期
     * 
     * @param bizNo
     *            原业务流水号
     * @return true:已存在分解记录，即需要使用当前日期作为会计日期；false:为第一次记录，使用业务订单时间为会计日期
     */
    private boolean isGenAccountDetail(String bizNo) {
        int resNum = 0;
        resNum = accountingMapper.findExistGenAccDetail(bizNo);
        return resNum > 0 ? true : false;
    }

    /**
     * 生成会计日期
     * 
     * @param bizNo
     *            原业务流水号
     * @return 会计日期
     */
    private String getFiscalDate(String bizNo) {
        String fiscalDate = "";
        if (isGenAccountDetail(bizNo))
        {
            fiscalDate = DateUtil.DateTimeToString(DateUtil.getCurrentDate());
        }
        return fiscalDate;
    }
}
