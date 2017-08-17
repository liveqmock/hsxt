/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.testcase;

import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ao.api.IAOExchangeHsbService;
import com.gy.hsxt.ao.bean.BuyHsb;
import com.gy.hsxt.ao.bean.BuyHsbCancel;
import com.gy.hsxt.ao.bean.PosBuyHsbCancel;
import com.gy.hsxt.ao.bean.ProxyBuyHsb;
import com.gy.hsxt.ao.bean.ProxyBuyHsbCancel;
import com.gy.hsxt.ao.disconf.AoConfig;
import com.gy.hsxt.ao.enumtype.BuyHsbTransResult;
import com.gy.hsxt.ao.interfaces.IAccountRuleService;
import com.gy.hsxt.ao.interfaces.IDubboInvokService;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.common.bean.OpenQuickPayBean;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.Channel;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.utils.DateUtil;

/**
 * 
 * 兑换互生币接口测试用例
 * 
 * @Package: com.gy.hsxt.ao.testcase
 * @ClassName: ExchangeHsbServiceTest
 * @Description: TODO
 * 
 * @author: liyh
 * @date: 2015-12-17 下午2:25:11
 * @version V1.0
 */
public class ExchangeHsbServiceTest extends BaseTest {
    /**
     * 帐户操作私有配置参数
     **/
    @Resource
    AoConfig aoConfig;

    @Resource
    IAOExchangeHsbService exchangeHsbService;

    @Resource
    IDubboInvokService dubboInvokService;

    @Autowired
    public BusinessParamSearch businessParamSearch;

    @Autowired
    IAccountRuleService accountRuleService;

    // /**
    // * 清除客户缓存
    // */
    // @Test
    // public void testCleanCustRedis() {
    // accountRuleService.setSysItemsValue("0601011000020160109");
    // }

    /**
     * 兑换互生币
     */
    @Test
    public void testSaveExchangeHsb() {
        BuyHsb buyHsb = null;
        for (int i = 0; i < 5; i++)
        {
            buyHsb = new BuyHsb();
            buyHsb.setCustId("0601011000020160109");
            buyHsb.setHsResNo("06010110000");
            // buyHsb.setCustId(getCustId());
            // buyHsb.setHsResNo(getHsResNo());
            buyHsb.setCustName(getCustName());
            buyHsb.setCustType(CustType.PERSON.getCode());
            buyHsb.setCustType(CustType.SERVICE_CORP.getCode());
            buyHsb.setBuyHsbAmt("1000");
            buyHsb.setOptCustId(getOptCustId());
            buyHsb.setOptCustName(getOptCustName());
            buyHsb.setChannel(Channel.WEB.getCode());
            buyHsb.setRemark("兑换互生币");
            buyHsb.setTermNo("88888");
            buyHsb.setBatchNo("99999");
            System.out.println(exchangeHsbService.saveBuyHsb(buyHsb));
        }
    }

    /**
     * 保存企业代兑互生币
     */
    @Test
    public void testSaveEntProxyBuyHsb() {
        ProxyBuyHsb proxyBuyHsb = null;
        for (int i = 0; i < 1; i++)
        {
            proxyBuyHsb = new ProxyBuyHsb();
            proxyBuyHsb.setEntCustId("0601011000020160109");
            proxyBuyHsb.setEntResNo("06010110000");
            proxyBuyHsb.setEntCustName(getCustName());
            proxyBuyHsb.setEntCustType(CustType.TRUSTEESHIP_ENT.getCode());
            proxyBuyHsb.setPerCustId(getCustId());
            proxyBuyHsb.setPerResNo(getHsResNo());
            proxyBuyHsb.setPerCustName(getCustName());
            proxyBuyHsb.setPerCustType(CustType.PERSON.getCode());
            proxyBuyHsb.setBuyHsbAmt("100");
            proxyBuyHsb.setOptCustId(getOptCustId());
            proxyBuyHsb.setOptCustName(getOptCustName());
            proxyBuyHsb.setChannel(Channel.WEB.getCode());
            proxyBuyHsb.setTransResult(BuyHsbTransResult.TRANS_SUCCESS.getCode());
            proxyBuyHsb.setRemark("代兑互生币");
            proxyBuyHsb.setTermNo("88888");
            proxyBuyHsb.setBatchNo("99999");
            exchangeHsbService.saveEntProxyBuyHsb(proxyBuyHsb);
        }
    }

    /**
     * 保存POS代兑互生币
     */
    @Test
    public void testSavePosProxyBuyHsb() {
        ProxyBuyHsb proxyBuyHsb = null;
        for (int i = 0; i < 1; i++)
        {
            proxyBuyHsb = new ProxyBuyHsb();
            proxyBuyHsb.setEntCustId(getCustId());
            proxyBuyHsb.setEntResNo(getHsResNo());
            proxyBuyHsb.setEntCustName(getCustName());
            proxyBuyHsb.setEntCustType(CustType.MEMBER_ENT.getCode());
            proxyBuyHsb.setPerCustId(getCustId());
            proxyBuyHsb.setPerResNo(getHsResNo());
            proxyBuyHsb.setPerCustName(getCustName());
            proxyBuyHsb.setPerCustType(CustType.PERSON.getCode());
            proxyBuyHsb.setCashAmt("88");
            proxyBuyHsb.setOptCustId(getOptCustId());
            proxyBuyHsb.setOptCustName(getOptCustName());
            proxyBuyHsb.setOriginNo(String.valueOf(new Random().nextInt(10000)));
            // POS流水号
            proxyBuyHsb.setTermRuncode(String.valueOf(new Random().nextInt(10000)));
            // 小票凭证号 proxyBuyHsb.setRemark("POS代兑互生币");
            proxyBuyHsb.setTermNo(String.valueOf(new Random().nextInt(10000)));
            proxyBuyHsb.setBatchNo(String.valueOf(new Random().nextInt(10000)));
            exchangeHsbService.savePosProxyBuyHsb(proxyBuyHsb);
        }
    }

    /**
     * 保存POS兑换互生币
     */
    @Test
    public void testSavePosBuyHsb() {
        BuyHsb buyHsb = null;
        for (int i = 0; i < 1; i++)
        {
            buyHsb = new BuyHsb();
            buyHsb.setCustId(getCustId());
            buyHsb.setHsResNo(getHsResNo());
            buyHsb.setCustName(getCustName());
            buyHsb.setCustType(CustType.MEMBER_ENT.getCode());
            buyHsb.setCashAmt("100");
            buyHsb.setOptCustId(getOptCustId());
            buyHsb.setOptCustName(getOptCustName());
            buyHsb.setRemark("POS兑换互生币");
            buyHsb.setOriginNo(String.valueOf(new Random().nextInt(10000)));
            buyHsb.setTermNo(String.valueOf(new Random().nextInt(10000)));
            buyHsb.setBatchNo(String.valueOf(new Random().nextInt(10000)));
            buyHsb.setTermRuncode(String.valueOf(new Random().nextInt(10000)));
            System.out.println(exchangeHsbService.savePosBuyHsb(buyHsb));
        }
    }

    /**
     * 兑换互生币冲正
     */
    @Test
    public void testReverseBuyHsb() {
        BuyHsbCancel buyHsbCancel = new BuyHsbCancel();
        buyHsbCancel.setOrigTransNo("130120160201192713000002");
        buyHsbCancel.setOptCustId(getOptCustId());
        buyHsbCancel.setOptCustName(getOptCustName());
        exchangeHsbService.reverseBuyHsb(buyHsbCancel, "货币兑换互生币：未知异常发起的冲正");
    }

    /**
     * 代兑互生币冲正
     */
    @Test
    public void testReverseProxyBuyHsb() {
        ProxyBuyHsbCancel proxyBuyHsbCancel = new ProxyBuyHsbCancel();
        proxyBuyHsbCancel.setOrigTransNo("130120160201182948000000");
        proxyBuyHsbCancel.setOptCustId(getOptCustId());
        proxyBuyHsbCancel.setOptCustName(getOptCustName());
        exchangeHsbService.reverseProxyBuyHsb(proxyBuyHsbCancel, "代兑互生币冲正");
    }

    /**
     * 冲正POS兑换互生币
     */
    @Test
    public void testReversePosBuyHsb() {
        PosBuyHsbCancel posBuyHsbCancel = new PosBuyHsbCancel();
        posBuyHsbCancel.setHsResNo("07001010000");
        posBuyHsbCancel.setOptCustId("0001");
        // posBuyHsbCancel.setHsResNo(getHsResNo());
        // posBuyHsbCancel.setOptCustId(getOptCustId());
        posBuyHsbCancel.setTermNo("0001");
        posBuyHsbCancel.setBatchNo("000016");
        posBuyHsbCancel.setTermRuncode("000003");
        exchangeHsbService.reversePosBuyHsb(posBuyHsbCancel);
    }

    /**
     * 冲正POS代兑互生币
     */
    @Test
    public void testReversePosProxyBuyHsb() {
        PosBuyHsbCancel posBuyHsbCancel = new PosBuyHsbCancel();
        posBuyHsbCancel.setHsResNo("05001081230");
        posBuyHsbCancel.setOptCustId(getOptCustId());
        // posBuyHsbCancel.setHsResNo(getHsResNo());
        // posBuyHsbCancel.setOptCustId(getOptCustId());
        posBuyHsbCancel.setTermNo("15615042110000000455");
        posBuyHsbCancel.setBatchNo("000012");
        posBuyHsbCancel.setTermRuncode("000002");
        exchangeHsbService.reversePosProxyBuyHsb(posBuyHsbCancel);
    }

    @Test
    public void testGetBuyHsbInfo() {
        System.out.println(exchangeHsbService.getBuyHsbInfo("130120151130090919000000"));
    }

    @Test
    public void testGetEntProxyBuyHsbInfo() {
        System.out.println(exchangeHsbService.getEntProxyBuyHsbInfo("130120151130112642000000"));
    }

    /**
     * 获取网银支付URL
     */
    @Test
    public void testGetNetPayUrl() {
        System.out.println("网银支付URL："
                + exchangeHsbService.getNetPayUrl("130120151225175837000000", "http://www.hsxt.com", "110"));
    }

    /**
     * 开通快捷支付并获取支付URL
     */
    @Test
    public void testGetOpenQuickPayUrl() {
        // BuyHsb buyHsb =
        // exchangeHsbService.getBuyHsbInfo("130120151215153506000000");
        // OpenQuickPayBean openQuickPayBean = new OpenQuickPayBean();
        // openQuickPayBean.setOrderNo(buyHsb.getTransNo());
        // openQuickPayBean.setOrderDate(DateUtil.StringToDate(buyHsb.getReqTime()));
        // openQuickPayBean.setCustId(getCustId());
        // openQuickPayBean.setBankCardNo("6221558812340001");
        // openQuickPayBean.setBankCardType(2);
        // openQuickPayBean.setBankId("0019");
        // openQuickPayBean.setJumpUrl("http://www.hsxt.com");
        // openQuickPayBean.setPrivObligate("110");
        // openQuickPayBean.setTransAmount(buyHsb.getCashAmt());
        // System.out.println("开通快捷支付并获取支付URL：" +
        // exchangeHsbService.getOpenQuickPayUrl(openQuickPayBean));
        BuyHsb buyHsb = exchangeHsbService.getBuyHsbInfo("130120160113150216000000");
        OpenQuickPayBean openQuickPayBean = new OpenQuickPayBean();
        openQuickPayBean.setOrderNo(buyHsb.getTransNo());
        openQuickPayBean.setOrderDate(DateUtil.StringToDate(buyHsb.getReqTime()));
        openQuickPayBean.setCustId(getCustId());
        openQuickPayBean.setBankCardNo("6221558812340001");
        openQuickPayBean.setBankCardType(1);
        openQuickPayBean.setBankId("1238");
        openQuickPayBean.setJumpUrl("http://www.hsxt.com");
        openQuickPayBean.setPrivObligate("110");
        openQuickPayBean.setTransAmount(buyHsb.getCashAmt());
        System.out.println("开通快捷支付并获取支付URL：" + exchangeHsbService.getOpenQuickPayUrl(openQuickPayBean));
    }

    /**
     * 获取快捷支付URL
     */
    @Test
    public void testGetQuickPayUrl() {
        exchangeHsbService.getQuickPaySmsCode("130120160115140313000000", "201503050109140001010000", "110");
        System.out.println("获取快捷支付URL："
                + exchangeHsbService.getQuickPayUrl("130120160115140313000000", "201503050109140001010000", "111111"));
    }

    @Test
    public void testBalance() {
        BuyHsb buyHsb = exchangeHsbService.getBuyHsbInfo("130120160113095259000000");
        // 获取货币帐户余额
        AccountBalance accountBalance = dubboInvokService.getAccountBlance(buyHsb.getCustId(),
                AccountType.ACC_TYPE_POINT10110.getCode());
        // System.out.println("转账前：积分帐户获取帐户余额：" +
        // accountBalance.getAccBalance());
        accountBalance = dubboInvokService.getAccountBlance(buyHsb.getCustId(), AccountType.ACC_TYPE_POINT20110
                .getCode());
        System.out.println("转账前：互生币帐户获取帐户余额：" + accountBalance.getAccBalance());
    }

    /**
     * 获取手机支付手机码
     */
    @Test
    public void testGetPaymentByMobileTN() {
        System.out.println("获取手机支付TN：" + exchangeHsbService.getPaymentByMobileTN("130120160114155409000000", "110"));
    }

    /**
     * 获取手机支付手机码
     */
    @Test
    public void testGetQuickPaySmsCode() {
        exchangeHsbService.getQuickPaySmsCode("130120151230205228000000", "201503050109140001010000", "110");
    }

    /**
     * 货币支付方式
     */
    @Test
    public void testPaymentByCurrency() {
        BuyHsb buyHsb = exchangeHsbService.getBuyHsbInfo("130120160130160300000000");
        // 获取货币帐户余额
        AccountBalance accountBalance = dubboInvokService.getAccountBlance(buyHsb.getCustId(),
                AccountType.ACC_TYPE_POINT30110.getCode());
        System.out.println("支付前：货币帐户获取帐户余额：" + accountBalance.getAccBalance());

        exchangeHsbService.paymentByCurrency("130120160130160300000000");

        accountBalance = dubboInvokService.getAccountBlance(buyHsb.getCustId(), AccountType.ACC_TYPE_POINT30110
                .getCode());
        System.out.println("支付后：货币帐户获取帐户余额：" + accountBalance.getAccBalance());
    }

    /**
     * 获取POS兑换互生币详情
     */
    @Test
    public void testGetPosBuyHsbInfo() {
        System.out.println("获取POS兑换互生币详情：" + exchangeHsbService.getPosBuyHsbInfo("06186630000", "095350114150"));
    }

    /**
     * 获取POS代兑互生币详情
     */
    @Test
    public void testGetPosProxyBuyHsbInfo() {
        System.out.println("获取POS代兑互生币详情：" + exchangeHsbService.getPosProxyBuyHsbInfo("06186630000", "085350160802"));
    }

    /**
     * 日终生成网银支付对账文件测试用例
     */
    @Test
    public void netPayFileGenerate() {
        // exchangeHsbService.netPayFileGenerate();
    }

}
