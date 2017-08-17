/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gy.hsxt.bs.common.enumtype.apply.ResFeeChargeMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.apply.interfaces.IDeclareService;
import com.gy.hsxt.bs.apply.interfaces.IResFeeResolveService;
import com.gy.hsxt.bs.apply.mapper.ResFeeAllocMapper;
import com.gy.hsxt.bs.bean.apply.DeclareInfo;
import com.gy.hsxt.bs.bean.apply.ResFeeAlloc;
import com.gy.hsxt.bs.bean.order.AccountDetail;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.resfee.ResFee;
import com.gy.hsxt.bs.bean.resfee.ResFeeRule;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.apply.ResType;
import com.gy.hsxt.bs.common.enumtype.resfee.AllocTarget;
import com.gy.hsxt.bs.common.enumtype.resfee.AllocType;
import com.gy.hsxt.bs.common.enumtype.resfee.AllocWay;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.order.interfaces.IAccountDetailService;
import com.gy.hsxt.bs.order.interfaces.IOrderService;
import com.gy.hsxt.bs.resfee.interfaces.IResFeeService;
import com.gy.hsxt.bs.tax.interfaces.ITaxrateChangeService;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.CurrencyCode;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntMainInfo;

/**
 * 
 * @Package: com.gy.hsxt.bs.apply.service
 * @ClassName: ResFeeResolveService
 * @Description: 资源费分解
 * 
 * @author: xiaofl
 * @date: 2015-12-16 下午6:41:10
 * @version V1.0
 */
@Service
public class ResFeeResolveService implements IResFeeResolveService {

    @Autowired
    private IResFeeService resFeeService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ICommonService commonService;

    @Autowired
    private ITaxrateChangeService taxrateChangeService;

    @Autowired
    private IAccountDetailService accountDetailService;

    @Autowired
    private ResFeeAllocMapper resFeeAllocMapper;

    @Autowired
    private BsConfig bsConfig;
    
    @Autowired
    IDeclareService declareService;

    /**
     * 资源费分解
     * 
     * @param declareInfo
     *            申报信息
     * @throws HsException
     */
    @Override
    @Transactional
    public void resolveResFee(DeclareInfo declareInfo) throws HsException {
        try
        {
            ResFee resFee = resFeeService.getResFeeBean(declareInfo.getResFeeId());
            Order order = orderService.getOrderByNo(declareInfo.getOrderNo());
            Integer payChannel;// 付款渠道
            if (null != order.getPayChannel() && PayChannel.E_BANK_PAY.getCode().equals(order.getPayChannel()))
            {// 网银
                payChannel = PayChannel.E_BANK_PAY.getCode();// 网银支付
            }
            else
            {// 临账
                payChannel = PayChannel.TRANSFER_REMITTANCE.getCode();// 转账汇款(临帐)
            }

            // 查询资源费方案的分配规则
            List<ResFeeRule> resFeeRuleList = resFeeService.queryResFeeRuleList(declareInfo.getResFeeId());

            // 查询地区平台信息
            LocalInfo localInfo = commonService.getAreaPlatInfo();
            String platResNo = localInfo.getPlatResNo();
            BsEntMainInfo platInfo = commonService.getEntMainInfoToUc(platResNo, null);
            String platCustId = platInfo.getEntCustId();
            String platCustName = platInfo.getEntName();
            Integer platCustType = CustType.AREA_PLAT.getCode();// 地区平台客户类型

            String applyId = declareInfo.getApplyId();// 申报单申请编号
            String platAddAmount = "0";// 平台增加金额
            String addAmount = null;// 增加金额
            String currencyCode = resFee.getCurrencyCode();
            String fiscalDate = DateUtil.DateTimeToString(new Date());
            // 实时记帐分解列表
            List<AccountDetail> realTimeAccountDetail = new ArrayList<AccountDetail>();

            // 资源费分解记录列表
            List<ResFeeAlloc> resFeeAllocList = new ArrayList<ResFeeAlloc>();

            for (ResFeeRule resFeeRule : resFeeRuleList)
            {
                if (AllocWay.AMOUNT.getCode() == resFeeRule.getAllocWay())
                {// 按金额分配
                    addAmount = resFeeRule.getAllocAmount();
                }
                else
                {// 按比例分配
                    addAmount = BigDecimalUtils.ceilingMul(resFeeRule.getAllocAmount(), resFee.getPrice()).toString();
                }

                if (AllocTarget.EXTENSION.getCode() == resFeeRule.getAllocTarget())
                {// 推广公司
                    String entResNo = declareInfo.getSpreadEntResNo();
                    String entCustId = declareInfo.getSpreadEntCustId();
                    String entCustName = declareInfo.getSpreadEntCustName();
                    Integer custType = HsResNoUtils.getCustTypeByHsResNo(entResNo);

                    if (CustType.AREA_PLAT.getCode() == custType)
                    {// 地区平台，不收税

                        platAddAmount = BigDecimalUtils.floorAdd(platAddAmount, addAmount).toString();
                    }
                    else if (CustType.SERVICE_CORP.getCode() == custType)
                    {// 服务公司
                        String taxRate = taxrateChangeService.queryValidTaxrateByCustId(entCustId);// 税率
                        BigDecimal tax = BigDecimalUtils.ceilingMul(addAmount, taxRate);// 税收
                        // 资源费分配记录ID
                        String allocDetailId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());

                        if (BigDecimalUtils.compareTo(addAmount, "0") > 0)
                        {
                            // 资源费分配记录(服务公司)
                            ResFeeAlloc resFeeAlloc = new ResFeeAlloc();
                            resFeeAlloc.setDetailId(allocDetailId);
                            resFeeAlloc.setTotalId(applyId);
                            resFeeAlloc.setCustId(entCustId);
                            resFeeAlloc.setCustType(custType);
                            resFeeAlloc.setResNo(entResNo);
                            resFeeAlloc.setCustName(entCustName);
                            resFeeAlloc.setAllocAmt(BigDecimalUtils.floor(addAmount, 2).toString());
                            resFeeAlloc.setCurrencyCode(currencyCode);
                            resFeeAlloc.setNeedTax(true);
                            resFeeAlloc.setTaxRate(taxRate);
                            resFeeAlloc.setTaxAmount(tax.toString());
                            resFeeAlloc.setExChangeRate(null);
                            resFeeAlloc.setAllocType(AllocType.SERVICE_FEE.getCode()); // 劳务费收入
                            resFeeAlloc.setAllocated(false);// 日终分配时标记未分配
                            resFeeAllocList.add(resFeeAlloc);
                        }
                    }

                }
                else if (AllocTarget.MANAGE.getCode() == resFeeRule.getAllocTarget())
                {// 上级管理公司
                    String entResNo = declareInfo.getToEntResNo().substring(0, 2) + "000000000";
                    BsEntMainInfo bsEntMainInfo = commonService.getEntMainInfoToUc(entResNo, null);
                    String entCustId = bsEntMainInfo.getEntCustId();
                    String entCustName = bsEntMainInfo.getEntName();
                    Integer custType = CustType.MANAGE_CORP.getCode();

                    if (BigDecimalUtils.compareTo(addAmount, "0") > 0)
                    {
                        // 资源费分配记录ID
                        String allocDetailId =
                                GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
                        

                        // 资源费分配记录(管理公司)
                        ResFeeAlloc resFeeAlloc = new ResFeeAlloc();
                        resFeeAlloc.setDetailId(allocDetailId);
                        resFeeAlloc.setTotalId(applyId);
                        resFeeAlloc.setCustId(entCustId);
                        resFeeAlloc.setCustType(custType);
                        resFeeAlloc.setResNo(entResNo);
                        resFeeAlloc.setCustName(entCustName);
                        resFeeAlloc.setAllocAmt(BigDecimalUtils.floor(addAmount, 2).toString());
                        resFeeAlloc.setCurrencyCode(currencyCode);
                        resFeeAlloc.setNeedTax(false);
                        resFeeAlloc.setTaxRate(null);
                        resFeeAlloc.setTaxAmount(null);
                        resFeeAlloc.setExChangeRate(null);
                        resFeeAllocList.add(resFeeAlloc);
                        resFeeAlloc.setAllocType(AllocType.MANAGE_FEE.getCode()); // 管理费收入
                        resFeeAlloc.setAllocated(false);// 日终分配时标记未分配
                    }
                }
                else if (AllocTarget.AREA.getCode() == resFeeRule.getAllocTarget())
                {// 地区平台

                    platAddAmount = BigDecimalUtils.floorAdd(platAddAmount, addAmount).toString();

                }
            }

            if (BigDecimalUtils.compareTo(platAddAmount, "0") > 0)
            {
                // 资源费分配记录ID
                String allocDetailId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());

                // 资源费分解记录(平台)
                ResFeeAlloc resFeeAlloc = new ResFeeAlloc();
                resFeeAlloc.setDetailId(allocDetailId);
                resFeeAlloc.setTotalId(applyId);
                resFeeAlloc.setCustId(platCustId);
                resFeeAlloc.setCustType(platCustType);
                resFeeAlloc.setResNo(platResNo);
                resFeeAlloc.setCustName(platCustName);
                resFeeAlloc.setAllocAmt(BigDecimalUtils.floor(platAddAmount, 2).toString());
                resFeeAlloc.setCurrencyCode(currencyCode);
                resFeeAlloc.setNeedTax(false);
                resFeeAlloc.setTaxRate(null);
                resFeeAlloc.setTaxAmount(null);
                resFeeAlloc.setExChangeRate(null);
                resFeeAllocList.add(resFeeAlloc);
                resFeeAlloc.setAllocType(AllocType.PLAT_RES_FEE.getCode()); // 平台资源费收入
                resFeeAlloc.setAllocated(false);// 日终分配时标记未分配

            }

            // 平台总账收入
            // 交易类型
            String transType = this.getTransType(declareInfo.getToCustType(), declareInfo.getToBuyResRange(),
                    payChannel);
            AccountDetail platAcc = new AccountDetail();
            platAcc.setBizNo(declareInfo.getApplyId());
            platAcc.setTransType(transType);
            platAcc.setCustId(platCustId);
            platAcc.setHsResNo(platResNo);
            platAcc.setCustName(platCustName);
            platAcc.setCustType(platCustType);
            if (PayChannel.E_BANK_PAY.getCode().equals(payChannel))
            {// 网银支付
                platAcc.setAccType(AccountType.ACC_TYPE_POINT50110.getCode());// 平台网银收款
            }
            else
            {// 转账汇款(临帐)
                platAcc.setAccType(AccountType.ACC_TYPE_POINT50210.getCode());// 平台临账收款
            }
            platAcc.setAddAmount(BigDecimalUtils.floor(resFee.getPrice(), 2).toString());
            platAcc.setDecAmount("0");
            platAcc.setCurrencyCode(currencyCode);
            platAcc.setFiscalDate(fiscalDate);
            platAcc.setRemark("系统资源费");
            platAcc.setStatus(true);
            realTimeAccountDetail.add(platAcc);

            // 申报兑换互生币实时分配
            if (BigDecimalUtils.compareTo(resFee.getIncludePrepayAmount(), "0") > 0)
            {
                // 积分预付款(货币)
                String includePrepayAmount = resFee.getIncludePrepayAmount();
                // 货币转换比率
                String exchangeRate = localInfo.getExchangeRate();
                // 积分预付款(互生币)
                String includePrepayAmountHsb = BigDecimalUtils.floor(
                        BigDecimalUtils.ceilingMul(includePrepayAmount, exchangeRate).toString(), 2).toString();

                // 资源费分配记录ID
                String bizNo = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());

                // 资源费分配记录
                ResFeeAlloc resFeeAllocHsb = new ResFeeAlloc();
                resFeeAllocHsb.setDetailId(bizNo);
                resFeeAllocHsb.setTotalId(applyId);
                resFeeAllocHsb.setCustId(declareInfo.getToEntCustId());
                resFeeAllocHsb.setCustType(declareInfo.getToCustType());
                resFeeAllocHsb.setResNo(declareInfo.getToEntResNo());
                resFeeAllocHsb.setCustName(declareInfo.getToEntCustName());
                resFeeAllocHsb.setAllocAmt(includePrepayAmount);
                resFeeAllocHsb.setCurrencyCode(currencyCode);
                resFeeAllocHsb.setNeedTax(false);
                resFeeAllocHsb.setTaxRate(null);
                resFeeAllocHsb.setTaxAmount(null);
                resFeeAllocHsb.setExChangeRate(exchangeRate);
                resFeeAllocHsb.setAllocType(AllocType.ENT_BUY_HSB.getCode()); // 申报兑换互生币，实时分配
                resFeeAllocHsb.setAllocated(true);// 实时分配时标记已分配
                resFeeAllocList.add(resFeeAllocHsb);

                // 生成申报兑换互生币记账分解记录
                AccountDetail hsbAcc = new AccountDetail();
                hsbAcc.setBizNo(bizNo);
                hsbAcc.setTransType(TransType.DECLARE_BUYHSB_FEE_ALLOC.getCode());// 申报兑换互生币
                hsbAcc.setCustId(declareInfo.getToEntCustId());
                hsbAcc.setHsResNo(declareInfo.getToEntResNo());
                hsbAcc.setCustName(declareInfo.getToEntCustName());
                hsbAcc.setCustType(declareInfo.getToCustType());
                hsbAcc.setAccType(AccountType.ACC_TYPE_POINT20110.getCode());
                hsbAcc.setAddAmount(includePrepayAmountHsb);
                hsbAcc.setDecAmount("0");
                hsbAcc.setCurrencyCode(CurrencyCode.HSB.getCode());
                hsbAcc.setFiscalDate(fiscalDate);
                hsbAcc.setRemark("申报兑换互生币");
                hsbAcc.setStatus(true);
                realTimeAccountDetail.add(hsbAcc);
            }

            // 保存资源费分配记录
            resFeeAllocMapper.batchSaveResFeeAlloc(resFeeAllocList);

            // 保存实时资源费记账分解(积分预付款和平台总账收入)
            accountDetailService.saveGenActDetail(realTimeAccountDetail, "资源费分配实时记账", true);
        }
        catch (HsException hse)
        {
            throw hse;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "resolveResFee", BSRespCode.BS_DECLARE_RESOLVE_RESFEE_ERROR
                    .getCode()
                    + ":分解资源费失败[param=" + declareInfo + "]", e);
            throw new HsException(BSRespCode.BS_DECLARE_RESOLVE_RESFEE_ERROR, "分解资源费失败[param=" + declareInfo + "]" + e);
        }
    }

    
    /**
     * 获取全部未完成分配的申报单号
     * @return
     */
    @Override
    public List<String> queryUnAllocApplyIds(){
        return resFeeAllocMapper.queryUnAllocApplyIds(); 
    }
    

    /**
     * 对每一笔申报资源费进行分配记账
     * 
     * @param applyId 申报单号
     * @param fiscalDate 会计日期
     */
    @Override
    @Transactional
    public void accoutingForApply(String applyId, String fiscalDate) {
        SystemLog.debug(this.getClass().getName(), "accoutingForApply", "资源费分配分解记账[申报编号="
                + applyId + "\n会计日期=" + fiscalDate + "]");
        List<ResFeeAlloc> resFeeAllocs = resFeeAllocMapper.queryUnAllocByApplyId(applyId);
        List<AccountDetail> acctDetails = new ArrayList<AccountDetail>();
        for(ResFeeAlloc resFeeAlloc : resFeeAllocs){
            if(!resFeeAlloc.isAllocated()){
                //将每一笔资源费分配记录分解成记账分解记录
                acctDetails.addAll(parseAccountDetail(resFeeAlloc, fiscalDate));
            }
        }
        if(!acctDetails.isEmpty()){
            //更新资源费分配记录分配标识
            resFeeAllocMapper.batchUpdateAllocFlag(resFeeAllocs);
            //资源费分配记账
            accountDetailService.saveGenActDetail(acctDetails, "日终未分配资源费实时记账", true);
        }
    }

    /**
     * 非实时资源费分配记账分解
     * 
     * @param alloc
     *            非实时分配记录
     * @param fiscalDate
     *            会计日期
     * @return
     */
    public List<AccountDetail> parseAccountDetail(ResFeeAlloc alloc, String fiscalDate) {
        String addAmount = null;
        // 如果扣税金额,分配记账金额需要减去纳税金额
        if (alloc.getTaxAmount() != null && BigDecimalUtils.compareTo(alloc.getTaxAmount(), "0") > 0)
        {
            BigDecimal addAmountExcludeTax = BigDecimalUtils.ceilingSub(alloc.getAllocAmt(), alloc.getTaxAmount());// 减去税收后的资源费收入
            addAmount = BigDecimalUtils.floor(addAmountExcludeTax.toString(), 2).toString();
        }
        else
        {
            addAmount = alloc.getAllocAmt();
        }
        List<AccountDetail> accountDetails = new ArrayList<AccountDetail>();
        AccountDetail acctDetail = new AccountDetail();
        acctDetail.setBizNo(alloc.getDetailId());
        acctDetail.setCustId(alloc.getCustId());
        acctDetail.setHsResNo(alloc.getResNo());
        acctDetail.setCustName(alloc.getCustName());
        acctDetail.setCustType(alloc.getCustType());
        acctDetail.setFiscalDate(fiscalDate);
        acctDetail.setStatus(true);
        //平台资源费分配不同账户类型
        if(alloc.getAllocType() == AllocType.PLAT_RES_FEE.getCode()){
            acctDetail.setAccType(AccountType.ACC_TYPE_POINT30420.getCode());
        }else{
            acctDetail.setAccType(AccountType.ACC_TYPE_POINT30110.getCode());
        }
        acctDetail.setAddAmount(addAmount);
        acctDetail.setDecAmount("0");
        if (alloc.getAllocType() == AllocType.SERVICE_FEE.getCode())
        {
            acctDetail.setTransType(TransType.DECLARE_FEE_ALLOC.getCode());
            DeclareInfo declareInfo = declareService.queryDeclareById(alloc.getTotalId());
            if(declareInfo!=null){
                acctDetail.setRemark("申报("+declareInfo.getToEntResNo()+")获得劳务费收入");
            }
            
            // 税收扣税记账
            if (alloc.getTaxAmount() != null && BigDecimalUtils.compareTo(alloc.getTaxAmount(), "0") > 0)
            {
                AccountDetail taxAcctDetail = new AccountDetail();
                taxAcctDetail.setBizNo(alloc.getDetailId());
                taxAcctDetail.setCustId(alloc.getCustId());
                taxAcctDetail.setHsResNo(alloc.getResNo());
                taxAcctDetail.setCustName(alloc.getCustName());
                taxAcctDetail.setCustType(alloc.getCustType());
                taxAcctDetail.setFiscalDate(fiscalDate);
                taxAcctDetail.setStatus(true);
                taxAcctDetail.setTransType(acctDetail.getTransType());
                taxAcctDetail.setAccType(AccountType.ACC_TYPE_POINT30310.getCode());
                taxAcctDetail.setAddAmount(alloc.getTaxAmount());
                taxAcctDetail.setDecAmount("0");
                taxAcctDetail.setRemark("劳务费分配纳税");
                accountDetails.add(taxAcctDetail);
            }
        }
        else if (alloc.getAllocType() == AllocType.MANAGE_FEE.getCode())
        {
            acctDetail.setTransType(TransType.MANAGE_FEE_ALLOC.getCode());// 管理费收入
            acctDetail.setRemark("管理费收入");
        }
        else if (alloc.getAllocType() == AllocType.PLAT_RES_FEE.getCode())
        {
            acctDetail.setTransType(TransType.PLAT_RES_FEE_ALLOC.getCode());//平台资源费分配收入
            acctDetail.setRemark("系统资源费");
        }
        accountDetails.add(acctDetail);

        return accountDetails;
    }

    // 获取申报订单交易类型
    private String getTransType(Integer custType, Integer resType, Integer payChannel) {
        String transType = null;
        if (PayChannel.E_BANK_PAY.getCode().equals(payChannel))
        {// 网银
            if (CustType.MEMBER_ENT.getCode() == custType)
            {
                transType = TransType.B_INET_PAY_RES_FEE.getCode();
            }
            else if (CustType.TRUSTEESHIP_ENT.getCode() == custType)
            {
                if (ResType.FIRST_SECTION_RES.getCode() == resType)
                {
                    transType = TransType.T_INET_PAY_FIRST_RES_FEE.getCode();
                }
                else if (ResType.ENTREPRENEURSHIP_RES.getCode() == resType)
                {
                    transType = TransType.T_INET_PAY_FOUND_RES_FEE.getCode();
                }
                else if (ResType.ALL_RES.getCode() == resType)
                {
                    transType = TransType.T_INET_PAY_WHOLE_RES_FEE.getCode();
                }
            }
            else if (CustType.SERVICE_CORP.getCode() == custType)
            {
                transType = TransType.S_INET_PAY_RES_FEE.getCode();
            }
        }
        else
        {// 临账
            if (CustType.MEMBER_ENT.getCode() == custType)
            {
                transType = TransType.B_TEMP_PAY_RES_FEE.getCode();
            }
            else if (CustType.TRUSTEESHIP_ENT.getCode() == custType)
            {
                if (ResType.FIRST_SECTION_RES.getCode() == resType)
                {
                    transType = TransType.T_TEMP_PAY_FIRST_RES_FEE.getCode();
                }
                else if (ResType.ENTREPRENEURSHIP_RES.getCode() == resType)
                {
                    transType = TransType.T_TEMP_PAY_FOUND_RES_FEE.getCode();
                }
                else if (ResType.ALL_RES.getCode() == resType)
                {
                    transType = TransType.T_TEMP_PAY_WHOLE_RES_FEE.getCode();
                }
            }
            else if (CustType.SERVICE_CORP.getCode() == custType)
            {
                transType = TransType.S_TEMP_PAY_RES_FEE.getCode();
            }
        }
        return transType;
    }

}
