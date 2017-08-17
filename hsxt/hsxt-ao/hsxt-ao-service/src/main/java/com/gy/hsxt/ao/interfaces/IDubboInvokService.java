/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.interfaces;

import java.util.List;

import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ao.bean.AccountingEntry;
import com.gy.hsxt.ao.bean.BuyHsb;
import com.gy.hsxt.ao.bean.TransOut;
import com.gy.hsxt.common.bean.OpenQuickPayBean;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gp.bean.TransCashOrderState;
import com.gy.hsxt.uc.bs.bean.ent.BsEntStatusInfo;

/**
 * Dubbo 调用接口声明
 * 
 * @Package: com.gy.hsxt.ao.interfaces
 * @ClassName: IDubboInvokService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-30 下午3:44:05
 * @version V3.0.0
 */
public interface IDubboInvokService {

    /**
     * 实时记帐：数据拼装
     * 
     * @param actDetails
     *            记帐分解对象列表
     * @throws HsException
     */
    public void actualAccount(List<AccountingEntry> actDetails) throws HsException;

    /**
     * 单笔冲正红冲记账
     * 
     * @param writeBack
     *            红冲标识
     * @param transType
     *            交易类型
     * @param transNo
     *            交易流水号
     * @throws HsException
     */
    public void correctSingleAccount(Integer writeBack, String transType, String transNo) throws HsException;

    /**
     * 单笔转帐
     * 
     * @param transOut
     *            转帐信息
     * @return true：转帐成功 false：转帐失败
     * @throws HsException
     */
    public boolean commitTransCash(TransOut transOut) throws HsException;

    /**
     * 获取网银支付URL
     * 
     * @param buyHsb
     *            兑换互生币信息
     * @param jumpUrl
     *            回跳地址
     * @param privObligate
     *            私有数据
     * @return 网银支付URL
     * @throws HsException
     */
    public String getNetPayUrl(BuyHsb buyHsb, String jumpUrl, String privObligate) throws HsException;

    /**
     * 开通快捷支付并获取URL
     * 
     * @param openQuickPayBean
     *            开通快捷支付实体类
     * @return 首次快捷支付URL
     * @throws HsException
     */
    public String getOpenQuickPayUrl(OpenQuickPayBean openQuickPayBean) throws HsException;

    /**
     * 获取快捷支付URL
     * 
     * @param transNo
     *            交易流水号
     * @param bindingNo
     *            签约号
     * @param smsCode
     *            短信验证码
     * @return 快捷支付URL
     * @throws HsException
     */
    public String getQuickPayUrl(String transNo, String bindingNo, String smsCode) throws HsException;

    /**
     * 获取手机支付TN
     * 
     * @param buyHsb
     *            兑换互生币信息
     * @param privObligate
     *            私有数据
     * @return TN交易流水号
     * @throws HsException
     */
    public String getMobilePayTn(BuyHsb buyHsb, String privObligate) throws HsException;

    /**
     * 获取快捷支付短信验证码
     * 
     * @param buyHsb
     *            兑换互生币信息
     * @param merId
     *            商户号
     * @param bindingNo
     *            签约号
     * @param privObligate
     *            私有数据
     * @throws HsException
     */
    public void getQuickPaySmsCode(BuyHsb buyHsb, String bindingNo, String privObligate) throws HsException;

    /**
     * 获取帐务余额
     * 
     * @param custId
     *            客户号
     * @param accountType
     *            帐户类型
     * @return 账户余额实体
     * @throws HsException
     */
    public AccountBalance getAccountBlance(String custId, String accountType) throws HsException;

    /**
     * 检验帐户余额是否足够
     * 
     * @param custId
     *            客户号
     * @param accountType
     *            帐户类型
     * @param appAmount
     *            申请金额
     * @return 结果等于0 为相等 结果大于0(正数) appAmount大 结果小于0(负数) appAmount小
     * 
     * @throws HsException
     */
    public int isBlanceInsufficient(String custId, String accountType, String appAmount) throws HsException;

    /**
     * 仿支付系统提现通知：对账时使用
     * 
     * @param paymentState
     * @return
     */
    public boolean notifyTransCashOrderState(TransCashOrderState transCashOrderState);

    /**
     * 查询客户状态信息
     * 
     * @param custType
     *            客户类型
     * @param custId
     *            客户号
     * @return 客户状态信息
     */
    public BsEntStatusInfo getCustInfo(Integer custType, String custId);

    /**
     * 检查客户信息
     * 
     * @param custType
     *            客户类型
     * @param custId
     *            客户号
     * @param bizType
     *            业务类型
     * @param isImportant
     *            是否校验重要信息变更
     * @param isOwnFee
     *            是否校验年费
     * @param isTrueNameReg
     *            是否校验实名注册
     */
    public void checkCustInfo(Integer custType, String custId, Integer bizType, boolean isImportant, boolean isOwnFee, boolean isTrueNameReg);

    /**
     * 校验是否通过实名认证
     * 
     * @param custType
     *            客户类型
     * @param custId
     *            客户号
     * @return true 通过,false 未通过
     */
    public boolean isPassRealName(Integer custType, String custId);
}
