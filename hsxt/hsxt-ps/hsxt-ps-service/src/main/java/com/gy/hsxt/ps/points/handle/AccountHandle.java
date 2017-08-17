/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.points.handle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.ac.bean.AccountWriteBack;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.utils.BeanCopierUtils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.ps.bean.Correct;
import com.gy.hsxt.ps.common.Constants;
import com.gy.hsxt.ps.common.PsRedisUtil;
import com.gy.hsxt.ps.common.TransTypeUtil;
import com.gy.hsxt.ps.distribution.bean.Alloc;

/**
 * @version V3.0
 * @Package: com.gy.hsxt.ps.points.handle
 * @ClassName: PointHandle
 * @Description: 账务记账处理
 * @author: chenhongzhi
 * @date: 2015-12-8 上午9:20:21
 */
public class AccountHandle {

    /**
     * 账务记账数据处理(消费积分) 组装数据
     * 
     * @param list
     *            分录信息
     * @return 返回处理后的分录信息
     */
    public static List<AccountEntry> accountPointEntry(final List<Alloc> list) {
        List<AccountEntry> acList = new ArrayList<>();
        // 循环遍历组装账务系统的参数对象数据信息
        for (Alloc alloc : list)
        {
            AccountEntry accountEntry = new AccountEntry();
            BeanCopierUtils.copy(alloc, accountEntry);
            if (accountEntry.getAccType().equals(AccountType.ACC_TYPE_POINT20111.getCode()))
            {
                continue;
            }
            // 取第三位交易类型
            String transWay3 = TransTypeUtil.transWay(alloc.getTransType());

            // 取第四位交易类型
            String transWay4 = TransTypeUtil.transStatus(alloc.getTransType());
            // 企业网上积分登记和现金支付的数据不过滤
            if (accountEntry.getCustType() != CustType.PERSON.getCode()
                    && accountEntry.getAccType().equals(AccountType.ACC_TYPE_POINT20110.getCode())
                    && !transWay3.equals(Constants.POINT_CASH))
            {
                continue;
            }

            if ((accountEntry.getCustType() == CustType.PERSON.getCode() || accountEntry.getCustType() == CustType.NOT_HS_PERSON
                    .getCode())
                    && accountEntry.getAccType().equals(AccountType.ACC_TYPE_POINT20110.getCode()))
            {
                // 预留网银支付
                if (transWay3.equals(Constants.POINT_CYBER)
                        && (transWay4.equals(Constants.POINT_BUSS_TYPE3) || transWay4
                                .equals(Constants.POINT_BUSS_TYPE0)))
                {
                    String resNo = PsRedisUtil.getLocalInfo().getPlatResNo();
                    accountEntry.setAccType(AccountType.ACC_TYPE_POINT50130.getCode());
                    accountEntry.setHsResNo(resNo);
                    if (alloc.getAddAmount() == null || alloc.getAddAmount() == BigDecimal.ZERO)
                    {
                        alloc.setAddAmount(alloc.getSubAmount());
                        alloc.setSubAmount(BigDecimal.ZERO);
                    }
                    List<String> entResNoSet = new ArrayList<>();
                    entResNoSet.add(resNo);
                    Map map = PsRedisUtil.getCustIdMap(entResNoSet);
                    alloc.setCustId(String.valueOf(map.get(resNo)));
                    accountEntry.setCustType(CustType.AREA_PLAT.getCode());
                }
            }

            if (accountEntry.getCustType() == CustType.PERSON.getCode()
                    && accountEntry.getAccType().equals(AccountType.ACC_TYPE_POINT20110.getCode()))
            {
                String accTypes = AccountType.ACC_TYPE_POINT20120.getCode() + "|" + accountEntry.getAccType();
                accountEntry.setAccTypes(accTypes);
            }
            else
            {
                accountEntry.setAccTypes(accountEntry.getAccType());
            }

            accountEntry.setAddAmount(String.valueOf(alloc.getAddAmount()));
            accountEntry.setSubAmount(String.valueOf(alloc.getSubAmount()));

            accountEntry.setSysEntryNo(alloc.getEntryNo());
            accountEntry.setRelSysEntryNo(alloc.getRelEntryNo());
            // accountEntry.setTransNo(alloc.getRelTransNo());
            accountEntry.setRelTransNo(alloc.getTransNo());
            accountEntry.setBatchNo(alloc.getBatchNo());
            accountEntry.setCustID(alloc.getCustId());
            accountEntry.setRemark(alloc.getRemark());
            accountEntry.setSourceTransNo(alloc.getSourceTransNo());

            accountEntry.setTransDate(DateUtil.DateTimeToString(new Date(alloc.getSourceTransDate().getTime())));
            accountEntry.setFiscalDate(DateUtil.DateTimeToString(new Date(alloc.getSourceTransDate().getTime())));
            accountEntry.setWriteBack(Integer.parseInt(alloc.getWriteBack()));
            accountEntry.setTransSys(Constants.TRANS_SYSTEM);
            acList.add(accountEntry);
        }
        return acList;
    }

    /**
     * 账务记账数据处理(退货) 组装数据
     * 
     * @param list
     *            分录信息
     * @return 返回处理后的分录信息
     */
    public static List<AccountEntry> accountBackEntry(final List<Alloc> list) {
        List<AccountEntry> acList = new ArrayList<>();
        // 循环遍历组装账务系统的参数对象数据信息
        for (Alloc alloc : list)
        {
            AccountEntry accountEntry = new AccountEntry();
            BeanCopierUtils.copy(alloc, accountEntry);

            // 取第三位交易类型
            String transWay3 = TransTypeUtil.transWay(alloc.getTransType());

            // 隔日
            if (alloc.getIsSettle() != Constants.IS_SETTLE1)
            {
                if ((alloc.getCustType() != CustType.PERSON.getCode())
                        && (!alloc.getAccType().equals(AccountType.ACC_TYPE_POINT20111.getCode()) || Constants.POINT_CASH
                                .equals(transWay3)))
                {
                    continue;
                }
            }

            // 单日
            if (alloc.getIsSettle() == Constants.IS_SETTLE1)
            {
                if (alloc.getCustType() != CustType.PERSON.getCode())
                {
                    continue;
                }
            }

            accountEntry.setSubAmount(String.valueOf(alloc.getSubAmount()));
            if (accountEntry.getCustType() == CustType.PERSON.getCode()
                    && accountEntry.getAccType().equals(AccountType.ACC_TYPE_POINT10110.getCode()))
            {
                // 如果积分为0，最低扣除0.1
                if (alloc.getSubAmount() == null || alloc.getSubAmount().compareTo(BigDecimal.ZERO) == 0)
                {
                    accountEntry.setSubAmount(String.valueOf(Constants.MIN_POINT2));
                }

            }
            if (alloc.getAccType().equals(AccountType.ACC_TYPE_POINT20111.getCode()))
            {
                accountEntry.setAccType(AccountType.ACC_TYPE_POINT20110.getCode());
            }

            if (accountEntry.getCustType() == CustType.PERSON.getCode() && !transWay3.equals(Constants.POINT_CYBER)
                    && accountEntry.getAccType().equals(AccountType.ACC_TYPE_POINT20110.getCode()))
            {
                String accTypes = AccountType.ACC_TYPE_POINT20120.getCode() + "|" + accountEntry.getAccType();
                accountEntry.setAccTypes(accTypes);
            }
            else
            {
                accountEntry.setAccTypes(accountEntry.getAccType());
            }

            accountEntry.setAddAmount(String.valueOf(alloc.getAddAmount()));
            accountEntry.setSysEntryNo(alloc.getEntryNo());
            accountEntry.setRelSysEntryNo(alloc.getRelEntryNo());
            accountEntry.setTransNo(alloc.getRelTransNo());
            accountEntry.setRelTransNo(alloc.getTransNo());
            accountEntry.setBatchNo(alloc.getBatchNo());
            accountEntry.setCustID(alloc.getCustId());
            accountEntry.setRemark(alloc.getRemark());
            accountEntry.setTransDate(DateUtil.DateTimeToString(new Date(alloc.getSourceTransDate().getTime())));
            accountEntry.setFiscalDate(DateUtil.DateTimeToString(new Date(alloc.getSourceTransDate().getTime())));
            accountEntry.setWriteBack(Integer.parseInt(alloc.getWriteBack()));
            accountEntry.setTransSys(Constants.TRANS_SYSTEM);
            acList.add(accountEntry);
        }
        return acList;
    }

    /**
     * 账务记账数据处理(撤单) 组装数据
     * 
     * @param list
     *            分录信息
     * @return 返回处理后的分录信息
     */
    public static List<AccountEntry> accountCancelEntry(final List<Alloc> list) {
        List<AccountEntry> acList = new ArrayList<AccountEntry>();
        // 循环遍历组装账务系统的参数对象数据信息
        for (Alloc alloc : list)
        {
            AccountEntry accountEntry = new AccountEntry();
            BeanCopierUtils.copy(alloc, accountEntry);

            // 取第三位交易类型
            String transWay3 = TransTypeUtil.transWay(alloc.getTransType());
            // 撤单，隔日
            if (alloc.getIsSettle() != Constants.IS_SETTLE1)
            {
                // 1、互生币
                if (transWay3.equals(Constants.POINT_HSB))
                {
                    if ((alloc.getCustType() != CustType.PERSON.getCode())
                            && !alloc.getAccType().equals(AccountType.ACC_TYPE_POINT20111.getCode()))
                    {
                        continue;
                    }
                    // 把20111修改成20110
                    if (alloc.getAccType().equals(AccountType.ACC_TYPE_POINT20111.getCode()))
                    {
                        accountEntry.setAccType(AccountType.ACC_TYPE_POINT20110.getCode());
                    }
                }
                // 2现金
                if (transWay3.equals(Constants.POINT_CASH))
                {
                    if (alloc.getCustType() != CustType.PERSON.getCode())
                    {
                        continue;
                    }
                }
            }
            // 当日撤单
            else
            {
                // 1、互生币
                if (transWay3.equals(Constants.POINT_HSB) || transWay3.equals(Constants.POINT_CYBER))
                {
                    if ((alloc.getCustType() != CustType.PERSON.getCode()))
                    {
                        continue;
                    }
                }
                // 2现金
                if (transWay3.equals(Constants.POINT_CASH))
                {
                    if (alloc.getCustType() != CustType.PERSON.getCode())
                    {
                        /*
                         * if (!alloc.getAccType().equals(AccountType.
                         * ACC_TYPE_POINT20110.getCode())) {
                         */
                        continue;
                        /* } */
                    }
                }
            }

            if (accountEntry.getCustType() == CustType.PERSON.getCode() && !transWay3.equals(Constants.POINT_CYBER)
                    && accountEntry.getAccType().equals(AccountType.ACC_TYPE_POINT20110.getCode()))
            {
                String accTypes = AccountType.ACC_TYPE_POINT20120.getCode() + "|" + accountEntry.getAccType();
                accountEntry.setAccTypes(accTypes);
            }
            else
            {
                accountEntry.setAccTypes(accountEntry.getAccType());
            }

            accountEntry.setAddAmount(String.valueOf(alloc.getAddAmount()));
            accountEntry.setSubAmount(String.valueOf(alloc.getSubAmount()));

            accountEntry.setSysEntryNo(alloc.getEntryNo());
            accountEntry.setRelSysEntryNo(alloc.getRelEntryNo());
            accountEntry.setTransNo(alloc.getRelTransNo());
            accountEntry.setRelTransNo(alloc.getTransNo());
            accountEntry.setBatchNo(alloc.getBatchNo());
            accountEntry.setCustID(alloc.getCustId());
            accountEntry.setRemark(alloc.getRemark());
            accountEntry.setTransDate(DateUtil.DateTimeToString(new Date(alloc.getSourceTransDate().getTime())));
            accountEntry.setFiscalDate(DateUtil.DateTimeToString(new Date(alloc.getSourceTransDate().getTime())));
            accountEntry.setWriteBack(Integer.parseInt(alloc.getWriteBack()));
            accountEntry.setTransSys(Constants.TRANS_SYSTEM);
            acList.add(accountEntry);
        }

        return acList;
    }

    /**
     * 账务记账数据处理(冲正) 组装数据
     * 
     * @param list
     *            分录信息
     * @return 返回处理后的分录信息
     */
    public static AccountWriteBack correctAccount(final List<Alloc> list, Correct correct) {
        AccountWriteBack accountWriteBack = new AccountWriteBack();
        for (Alloc alloc : list)
        {
            // 设置冲正标示
            accountWriteBack.setWriteBack(Integer.parseInt(TransTypeUtil.writeBack(correct.getTransType())));

            // 设置交易类型
            accountWriteBack.setTransType(alloc.getTransType());

            // 设置关联的流水号
            accountWriteBack.setRelTransNo(alloc.getRelTransNo());
        }
        return accountWriteBack;
    }

}
