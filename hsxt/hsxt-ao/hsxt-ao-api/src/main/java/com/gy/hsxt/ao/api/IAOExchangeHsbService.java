/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.api;

import com.gy.hsxt.ao.bean.BuyHsb;
import com.gy.hsxt.ao.bean.BuyHsbCancel;
import com.gy.hsxt.ao.bean.PaymentResult;
import com.gy.hsxt.ao.bean.PosBuyHsbCancel;
import com.gy.hsxt.ao.bean.ProxyBuyHsb;
import com.gy.hsxt.ao.bean.ProxyBuyHsbCancel;
import com.gy.hsxt.common.bean.OpenQuickPayBean;
import com.gy.hsxt.common.exception.HsException;

/**
 * 兑换互生币接口定义
 * 
 * @Package: com.gy.hsxt.ao.api
 * @ClassName: IAOExchangeHsbService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-26 下午12:07:15
 * @version V3.0.0
 */
public interface IAOExchangeHsbService {

    /**
     * 保存兑换互生币记录
     * 
     * @param buyHsb
     *            兑换互生币信息
     * @return 货币兑换：交易流水号、网银兑换：网银支付地址
     * @throws HsException
     */
    public String saveBuyHsb(BuyHsb buyHsb) throws HsException;

    /**
     * 保存企业代兑互生币记录
     * 
     * @param proxyBuyHsb
     *            代兑互生币信息
     * @throws HsException
     */
    public void saveEntProxyBuyHsb(ProxyBuyHsb proxyBuyHsb) throws HsException;

    /**
     * 保存POS兑换互生币记录
     * 
     * @param buyHsb
     *            兑换互生币信息
     * @return 兑换互生币数量
     * @throws HsException
     */
    public String savePosBuyHsb(BuyHsb buyHsb) throws HsException;

    /**
     * 保存POS代兑互生币记录
     * 
     * @param proxyBuyHsb
     *            代兑互生币信息
     * @return 兑换互生币数量
     * @throws HsException
     */
    public String savePosProxyBuyHsb(ProxyBuyHsb proxyBuyHsb) throws HsException;

    /**
     * 冲正货币兑换互生币
     * 
     * @param buyHsbCancel
     *            冲正信息
     * @param remark
     *            备注
     * @throws HsException
     */
    public void reverseBuyHsb(BuyHsbCancel buyHsbCancel, String remark) throws HsException;

    /**
     * 冲正POS兑换互生币
     * 
     * @param posBuyHsbCancel
     *            pos冲正信息
     * @param remark
     *            备注
     * @throws HsException
     */
    public void reversePosBuyHsb(PosBuyHsbCancel posBuyHsbCancel) throws HsException;

    /**
     * 冲正企业代兑互生币
     * 
     * @param proxyBuyHsbCancel
     *            冲正信息
     * @param remark
     *            备注
     * @throws HsException
     */
    public void reverseProxyBuyHsb(ProxyBuyHsbCancel proxyBuyHsbCancel, String remark) throws HsException;

    /**
     * 冲正POS代兑互生币
     * 
     * @param posBuyHsbCancel
     *            pos冲正信息
     * @throws HsException
     */
    public void reversePosProxyBuyHsb(PosBuyHsbCancel posBuyHsbCancel) throws HsException;

    /**
     * 查询兑换互生币详情
     * 
     * @param transNo
     *            交易流水号
     * @return 兑换互生币信息
     * @throws HsException
     */
    public BuyHsb getBuyHsbInfo(String transNo) throws HsException;

    /**
     * 查询企业代兑互生币信息
     * 
     * @param transNo
     *            交易流水号
     * @return 企业代兑互生币信息
     * @throws HsException
     */
    public ProxyBuyHsb getEntProxyBuyHsbInfo(String transNo) throws HsException;

    /**
     * 查询POS兑换互生币详情
     * 
     * @param hsResNo
     *            互生号
     * @param originNo
     *            POS流水号
     * @return 兑换互生币信息
     * @throws HsException
     */
    public BuyHsb getPosBuyHsbInfo(String hsResNo, String originNo) throws HsException;

    /**
     * 查询POS代兑互生币详情
     * 
     * @param hsResNo
     *            互生号
     * @param originNo
     *            POS流水号
     * @return 代兑互生币信息
     * @throws HsException
     */
    public ProxyBuyHsb getPosProxyBuyHsbInfo(String hsResNo, String originNo) throws HsException;

    /**
     * 获取网银支付URL
     * 
     * @param transNo
     *            交易流水号
     * @param jumpUrl
     *            回跳地址
     * @param privObligate
     *            私有数据
     * @return 网银支付URL
     * @throws HsException
     */
    public String getNetPayUrl(String transNo, String jumpUrl, String privObligate) throws HsException;

    /**
     * 开通快捷支付并获取支付URL
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
     * 获取手机支付TN码
     * 
     * @param transNo
     *            交易流水号
     * @param privObligate
     *            私有数据
     * @return TN交易流水号
     * @throws HsException
     */
    public String getPaymentByMobileTN(String transNo, String privObligate) throws HsException;

    /**
     * 获取快捷支付短信验证码
     * 
     * @param transNo
     *            交易流水号
     * @param bindingNo
     *            签约号
     * @param privObligate
     *            私有数据
     * @throws HsException
     */
    public void getQuickPaySmsCode(String transNo, String bindingNo, String privObligate) throws HsException;

    /**
     * 获取兑换互生币支付状态
     * 
     * @param transNo
     *            交易流水号
     * @return true:支付成功 false:支付失败或暂未收到通知
     * @throws HsException
     */
    public boolean getPayStatus(String transNo) throws HsException;

    /**
     * 货币支付
     * 
     * @param transNo
     *            交易流水号
     * @throws HsException
     */
    public void paymentByCurrency(String transNo) throws HsException;

    /**
     * 同步更新支付状态
     * 
     * @param paymentResult
     *            支付结果实体
     * @throws HsException
     */
    public void synPayStatus(PaymentResult paymentResult) throws HsException;
}
