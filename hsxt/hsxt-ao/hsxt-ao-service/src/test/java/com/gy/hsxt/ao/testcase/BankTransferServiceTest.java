/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.testcase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;

import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ao.api.IAOBankTransferService;
import com.gy.hsxt.ao.bean.TransOut;
import com.gy.hsxt.ao.bean.TransOutQueryParam;
import com.gy.hsxt.ao.enumtype.CommitType;
import com.gy.hsxt.ao.enumtype.TransOutResult;
import com.gy.hsxt.ao.enumtype.TransReason;
import com.gy.hsxt.ao.interfaces.IAccountRuleService;
import com.gy.hsxt.ao.interfaces.IDubboInvokService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.Channel;
import com.gy.hsxt.common.constant.CurrencyCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.gp.api.IGPNotifyService;
import com.gy.hsxt.gp.api.IGPTransCashService;
import com.gy.hsxt.gp.bean.TransCashOrderState;

/**
 * 银行转帐测试用例
 * 
 * @Package: com.gy.hsxt.ao.testcase
 * @ClassName: BankTransferServiceTest
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-12-17 下午6:20:43
 * @version V3.0.0
 */
public class BankTransferServiceTest extends BaseTest {
    @Resource
    IAOBankTransferService bankTransferService;

    @Resource
    IGPNotifyService gpNotifyService;

    @Resource
    IAccountRuleService accountRuleService;

    @Resource
    IDubboInvokService dubboInvokService;

    /**
     * 支付系统：单笔提现接口
     */
    @Resource
    IGPTransCashService gpTransCashService;

    @Test
    public void testGetBankFeeAmount() {
        // 调用支付系统计算手续费金额
        System.out.println(gpTransCashService.getBankTransFee("100", "1003", "440104", "N"));
        System.out.println(gpTransCashService.getBankTransFee("100", "116", "440224", "N"));
    }

    /**
     * ustId :"0618601000620151130", //客户号 hsResNo :"06186010006", //互生号
     * 保存银行转帐记录
     */
    @Test
    public void testSaveTransOut() {

        TransOut transOut = null;
        for (int i = 0; i < 1; i++)
        {
            transOut = new TransOut();

            transOut.setCustId("0600102000020151215");
            transOut.setHsResNo("06001020000");
            transOut.setTransReason(TransReason.ACCORD_CASH.getCode());
            transOut.setCustName("互生企业23");
            transOut.setCustType(3);
            transOut.setAmount("1000");
            transOut.setCurrencyCode("RMB");
            transOut.setVerify(true);
            transOut.setReqOptId("0600211172220151207");
            transOut.setReqOptName("gyist");
            transOut.setReqRemark("没钱花了转点出去！");
            transOut.setChannel(Channel.WEB.getCode());
            System.out.println(transOut);
            // accountRuleService.setSysItemsValue(transOut.getCustId()); //
            // 清空缓存

            // 获取货币帐户余额
            AccountBalance accountBalance = dubboInvokService.getAccountBlance(transOut.getCustId(),
                    AccountType.ACC_TYPE_POINT30110.getCode());
            System.out.println("转账前：货币帐户获取帐户余额：" + accountBalance.getAccBalance());
            bankTransferService.saveTransOut(transOut, 326L);
            accountBalance = dubboInvokService.getAccountBlance(transOut.getCustId(), AccountType.ACC_TYPE_POINT30110
                    .getCode());
            System.out.println("转账后：货币帐户获取帐户余额：" + accountBalance.getAccBalance());
        }
    }

    /**
     * 转账结果异步通知
     */
    @Test
    public void testNotifyTransCashOrderState() {
        final TransCashOrderState cashOrderState = new TransCashOrderState();
        cashOrderState.setOrderNo("130120160325164504000000");
        cashOrderState.setTransStatus(TransOutResult.TRANS_CASH_OUT.getCode());
        // cashOrderState.setTransStatus(TransOutResult.TRANS_CASH_OUT.getCode());
        cashOrderState.setTransAmount("1000");
        cashOrderState.setCurrencyCode(CurrencyCode.CNY.getCode());
        cashOrderState.setBankSubmitDate(DateUtil.now());
        cashOrderState.setBankFee("4");
        // cashOrderState.setFailReason("成功了");
        gpNotifyService.notifyTransCashOrderState(cashOrderState);
        // new Thread(new Runnable() {
        //
        // @Override
        // public void run() {
        // System.out.println("子线程1:" + Thread.currentThread().getName());
        // // TODO Auto-generated method stub
        // gpNotifyService.notifyTransCashOrderState(cashOrderState);
        //
        // }
        // }).start();
        // new Thread(new Runnable() {
        //
        // @Override
        // public void run() {
        // System.out.println("子线程2:" + Thread.currentThread().getName());
        // // TODO Auto-generated method stub
        // gpNotifyService.notifyTransCashOrderState(cashOrderState);
        //
        // }
        // }).start();
        // new Thread(new Runnable() {
        //
        // @Override
        // public void run() {
        // System.out.println("子线程3:" + Thread.currentThread().getName());
        // // TODO Auto-generated method stub
        // gpNotifyService.notifyTransCashOrderState(cashOrderState);
        //
        // }
        // }).start();
        // new Thread(new Runnable() {
        //
        // @Override
        // public void run() {
        // System.out.println("子线程4:" + Thread.currentThread().getName());
        // // TODO Auto-generated method stub
        // gpNotifyService.notifyTransCashOrderState(cashOrderState);
        //
        // }
        // }).start();
        // new Thread(new Runnable() {
        //
        // @Override
        // public void run() {
        // System.out.println("子线程5:" + Thread.currentThread().getName());
        // // TODO Auto-generated method stub
        // gpNotifyService.notifyTransCashOrderState(cashOrderState);
        //
        // }
        // }).start();
        // new Thread(new Runnable() {
        //
        // @Override
        // public void run() {
        // System.out.println("子线程6:" + Thread.currentThread().getName());
        // // TODO Auto-generated method stub
        // gpNotifyService.notifyTransCashOrderState(cashOrderState);
        //
        // }
        // }).start();
        // new Thread(new Runnable() {
        //
        // @Override
        // public void run() {
        // System.out.println("子线程7:" + Thread.currentThread().getName());
        // // TODO Auto-generated method stub
        // gpNotifyService.notifyTransCashOrderState(cashOrderState);
        //
        // }
        // }).start();
        // new Thread(new Runnable() {
        //
        // @Override
        // public void run() {
        // System.out.println("子线程8:" + Thread.currentThread().getName());
        // // TODO Auto-generated method stub
        // gpNotifyService.notifyTransCashOrderState(cashOrderState);
        //
        // }
        // }).start();
        // new Thread(new Runnable() {
        //
        // @Override
        // public void run() {
        // System.out.println("子线程9:" + Thread.currentThread().getName());
        // // TODO Auto-generated method stub
        // gpNotifyService.notifyTransCashOrderState(cashOrderState);
        //
        // }
        // }).start();
        // new Thread(new Runnable() {
        //
        // @Override
        // public void run() {
        // System.out.println("子线程10:" + Thread.currentThread().getName());
        // // TODO Auto-generated method stub
        // gpNotifyService.notifyTransCashOrderState(cashOrderState);
        //
        // }
        // }).start();
        //
        // try
        // {
        // Thread.sleep(20000);
        // }
        // catch (InterruptedException e)
        // {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
    }

    /**
     * 查询银行转帐详情
     */
    @Test
    public void testGetTransOutInfo() {
        bankTransferService.getTransOutInfo("130120160115185834000000");
    }

    /**
     * 查询银行转帐列表
     */
    @Test
    public void testGetTransOutList() {
        TransOutQueryParam transOutQueryParam = new TransOutQueryParam();
        Page page = new Page(1);
        // orderQueryParam.setStartDate("2015-12-14");
        // orderQueryParam.setEndDate(DateUtil.getCurrentDateTime());
        // orderQueryParam.setStatus(2);
        // orderQueryParam.setPayChannel(PayChannel.E_BANK_PAY.getCode());
        // transOutQueryParam.setCustType(1);
        transOutQueryParam.setHsResNo("06");
        System.out.println(transOutQueryParam);
        page.setCurPage(1);
        page.setPageSize(10000);
        try
        {
            PageData<TransOut> orderList = bankTransferService.getTransOutList(transOutQueryParam, page);

            List<TransOut> transOut = orderList.getResult();
            for (TransOut o : transOut)
            {
                System.out.println("订单 = " + o);
            }
        }
        catch (HsException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 查询银行转帐列表
     */
    @Test
    public void testGetCheckUpList() {
        TransOutQueryParam transOutQueryParam = new TransOutQueryParam();
        Page page = new Page(1);
        transOutQueryParam.setHsResNo("01");
        // orderQueryParam.setStartDate("2015-12-14");
        // orderQueryParam.setEndDate(DateUtil.getCurrentDateTime());
        // orderQueryParam.setStatus(2);
        // orderQueryParam.setPayChannel(PayChannel.E_BANK_PAY.getCode());
        page.setCurPage(1);
        page.setPageSize(10000);
        try
        {
            List<String> transNos = new ArrayList<String>();
            PageData<TransOut> orderList = bankTransferService.getCheckUpList(transOutQueryParam, page);

            List<TransOut> transOut = orderList.getResult();
            for (TransOut o : transOut)
            {
                System.out.println("对帐数据 = " + o);
                transNos.add(o.getTransNo());
            }
            transOut = bankTransferService.transCheckUpAccount(transNos);
            System.out.println("对账退款业务：" + transOut);
        }
        catch (HsException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testAuditTransOut() {
    }

    @Test
    public void testCheckUpAccount() {
        List<String> transNos = new ArrayList<String>();
        transNos.add("130120160115103622000000");
        transNos.add("130120160115103442000000");
        transNos.add("130120160115103442000000");
        bankTransferService.transCheckUpAccount(transNos);
    }

    @Test
    public void testGetTransOutFailList() {
        Page page = new Page(1);
        page.setCurPage(1);
        page.setPageSize(10000);
        try
        {
            PageData<TransOut> orderList = bankTransferService.getTransOutFailList(page);

            List<TransOut> transOut = orderList.getResult();
            for (TransOut o : transOut)
            {
                System.out.println("订单号 = " + o.getTransNo());
            }
        }
        catch (HsException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 转账失败退回
     */
    @Test
    public void testTransFailBack() {
        List<String> transNos = new ArrayList<String>();
        transNos.add("130120160323090422000000");
        bankTransferService.transFailBack(transNos);
    }

    /**
     * 批量提交转帐
     */
    @Test
    public void testBatchTrans() {
        Set<String> transNos = new HashSet<String>();
        transNos.add("130120160223203308000002");
        // transNos.add("130120160115103442000000");
        // transNos.add("130120160115103442000000");
        bankTransferService.transBatch(transNos, CommitType.HAND_COMMIT.getCode(), getOptCustId(), getOptCustName(),
                "就让你转吧！");
    }

    /**
     * 撤单
     */
    @Test
    public void testTransRevoke() {
        Map<String, String> revokMaps = new HashMap<String, String>();
        revokMaps.put("130120160227152110000000", "今天不想让你转账");
        revokMaps.put("130120160227152109000000", "星期六不允许转");
        revokMaps.put("130120160227151956000000", "今天加班不太累了不让转");
        // revokMaps.put("130120160115140851000000", "本月申请已用完");
        bankTransferService.transRevoke(revokMaps, getOptCustId(), getOptCustName(), "apprRemark");
    }

    @Test
    public void testGetTransOutFailInfo() {
        System.out.println(bankTransferService.getTransOutFailInfo("130120160225121219000000"));
    }

    @Test
    public void testGetCloseTransBackList() {
        Page page = new Page(1);
        page.setCurPage(1);
        page.setPageSize(10000);
        try
        {
            PageData<TransOut> orderList = bankTransferService.getCloseTransBackList(page);

            if (orderList != null && orderList.getCount() > 0)
            {

                List<TransOut> transOut = orderList.getResult();
                for (TransOut o : transOut)
                {
                    System.out.println("销户退票订单号 = " + o.getTransNo());
                }
            }
        }
        catch (HsException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.StringToDateTime("2015-12-02 12:12:12"));
    }

}
