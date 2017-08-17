/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.services.accountManagement;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.bean.accountManage.NetPay;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.ao.api.IAOBankTransferService;
import com.gy.hsxt.ao.api.IAOCurrencyConvertService;
import com.gy.hsxt.ao.api.IAOPaymentService;
import com.gy.hsxt.ao.bean.HsbToCash;
import com.gy.hsxt.ao.bean.PayInfo;
import com.gy.hsxt.ao.bean.PvToHsb;
import com.gy.hsxt.ao.bean.TransOut;
import com.gy.hsxt.bs.api.order.IBSInvestProfitService;
import com.gy.hsxt.bs.bean.order.PointInvest;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gp.api.IGPTransCashService;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.client.LcsClient;

/**
 * *************************************************************************
 * 
 * <PRE>
 * Description      : 账户余额查询服务
 * 
 * Project Name     : hsxt-access-web-company 
 * 
 * Package Name     : com.gy.hsxt.access.web.company.services.accountManagement  
 * 
 * File Name        : BalanceService 
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-10-8 下午4:11:39
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-10-8 下午4:11:39
 * 
 * UpdateRemark     : 说明本次修改内容
 * 
 * Version          : v1.0
 * 
 * </PRE>
 ************************************************************************** 
 */
@Service
public class TransInnerService extends BaseServiceImpl implements ITransInnerService {
    @Autowired
    private IAOCurrencyConvertService aoCurrencyConvertService; // 账户操作服务类

    /**
     * 支付系统：单笔提现接口
     */
    @Resource
    private IGPTransCashService gpTransCashService;
    
    /**
     * 账户操作：转银行接口
     */
    @Resource
    private IAOBankTransferService aoBankTransferService;
    
    /**
     * 积分操作：积分投资接口
     */
    @Resource
    private IBSInvestProfitService  bsInvestProfitService;
    
    /** 地区平台配送Service **/
    @Autowired
    private LcsClient lcsClient;

    /** 支付服务 **/
    @Resource
    private IAOPaymentService aoPaymentService;

    /**
     * 积分转互生币
     * 
     * @param pvToHsb
     *            积分转互生实体
     * @return 无异常表示成功
     * @throws HsException
     */
    @Override
    public void savePvToHsb(PvToHsb pvToHsb) throws HsException {
        aoCurrencyConvertService.savePvToHsb(pvToHsb);
    }

    /**
     * 保存互生币转货币
     * 
     * @param hsbToCash
     *            互生币转货币实体
     * @return 无异常表示成功
     * @throws HsException
     */
    @Override
    public void saveHsbToCash(HsbToCash hsbToCash) throws HsException {
        aoCurrencyConvertService.saveHsbToCash(hsbToCash);
    }

    /**
     * 获取转银行的手续费金额
     * 
     * @param transOut 转银行实体类
     * @param sysFlag  转账加急标志[1-大额(>=5万)，等同Y][2-小额(<5万)，等同N ][S：特急] [Y：加急][N：普通（默认）]
     * 
     * @return 执行的手续费
     * @throws HsException
     */
    @Override
    public String getBankTransFee(TransOut transOut, String sysFlag) throws HsException {
//        return "4.21";
        return this.gpTransCashService.getBankTransFee(transOut.getAmount(), transOut.getBankNo(), transOut.getBankCityNo(),sysFlag);
    }

    /**
     * 货币转银行操作
     * @param transOut 转银行实体
     * @param accId 账户id
     * @return 
     * @throws HsException
     */
    @Override
    public void saveTransOut(TransOut transOut, Long accId) throws HsException {
        this.aoBankTransferService.saveTransOut(transOut, accId);
    }

    /**
     * 保存积分投资接口
     * @param pointInvest 积分投资账户
     * @throws HsException
     */
    @Override
    public void savePointInvest(PointInvest pointInvest) throws HsException {
       this.bsInvestProfitService.savePointInvest(pointInvest);
    }
    
    /**
     * 兑换互生币支付
     * @param netPay
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.company.services.accountManagement.ITransInnerService#convertHSBPay(com.gy.hsxt.access.web.bean.accountManage.NetPay)
     */
    @Override
    public String convertHSBPay(NetPay netPay) throws HsException {
        // 地区平台信息
        LocalInfo info = lcsClient.getLocalInfo();

        // 构造支付参数对象
        PayInfo payInfo = new PayInfo();
        payInfo.setPayChannel(netPay.getPayChannel());
        payInfo.setTransNo(netPay.getTransNo());
        payInfo.setCallbackUrl(info.getWebPayJumpUrl());
        payInfo.setGoodsName(netPay.getGoodsName());

        //银行卡支付
        if (netPay.getPayChannel() == PayChannel.CARD_PAY.getCode()) {
            return aoPaymentService.getPayUrl(payInfo);
        } else {
            throw new HsException(ASRespCode.AS_PAY_CHANNEL_ERROR);
        }
    }

}
