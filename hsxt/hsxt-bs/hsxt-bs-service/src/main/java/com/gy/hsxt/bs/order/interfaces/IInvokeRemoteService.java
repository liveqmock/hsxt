/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.order.interfaces;

import java.util.List;

import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.bs.bean.order.AccountDetail;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.common.bean.PayUrl;
import com.gy.hsxt.common.bean.OpenQuickPayBean;
import com.gy.hsxt.common.exception.HsException;

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
public interface IInvokeRemoteService {

    /**
     * 实时记帐：数据拼装
     * 
     * @param accountDetails
     *            记帐分解对象列表
     * @throws HsException
     */
    public void actualAccount(List<AccountDetail> accountDetails) throws HsException;

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
     * 获取网银支付URL
     * 
     * @param order
     *            订单信息
     * @param jumpUrl
     *            回跳地址
     * @return 网银支付URL
     * @throws HsException
     */
    public String getNetPayUrl(Order order, String jumpUrl) throws HsException;

    /**
     * 获取银行卡支付URL
     * 
     * @param payUrl
     *            支付信息
     * @return 银行卡支付URL
     * @throws HsException
     */
    public String getCardPayUrl(PayUrl payUrl) throws HsException;

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
     * 获取快捷支付短信验证码
     * 
     * @param buyHsb
     *            兑换互生币信息
     * @param bindingNo
     *            签约号
     * @param privObligate
     *            私有数据
     * @throws HsException
     */
    public void getQuickPaySmsCode(Order order, String bindingNo, String privObligate) throws HsException;

    /**
     * 获取手机支付TN
     * 
     * @param order
     *            兑换互生币信息
     * @param privObligate
     *            私有数据
     * @return TN交易流水号
     * @throws HsException
     */
    public String getMobilePayTn(Order order, String privObligate) throws HsException;

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
     * @return true/false
     * @throws HsException
     */
    public int isBlanceInsufficient(String custId, String accountType, String appAmount) throws HsException;
}
