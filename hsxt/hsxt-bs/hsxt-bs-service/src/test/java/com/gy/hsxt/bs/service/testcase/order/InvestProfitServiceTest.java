/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.service.testcase.order;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gy.hsxt.bs.api.order.IBSInvestProfitService;
import com.gy.hsxt.bs.bean.order.CustomPointDividend;
import com.gy.hsxt.bs.bean.order.DividendDetail;
import com.gy.hsxt.bs.bean.order.DividendRate;
import com.gy.hsxt.bs.bean.order.PointDividend;
import com.gy.hsxt.bs.bean.order.PointInvest;
import com.gy.hsxt.bs.order.interfaces.IAccountRuleService;
import com.gy.hsxt.bs.order.interfaces.IInvokeRemoteService;
import com.gy.hsxt.bs.order.service.InvestProfitService;
import com.gy.hsxt.bs.order.service.InvestProfitTaskService;
import com.gy.hsxt.bs.service.testcase.BaseTest;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.Channel;
import com.gy.hsxt.common.constant.CustType;

/**
 * 积分投资单元测试类
 * 
 * @Package: com.gy.hsxt.bs.service.testcase.order
 * @ClassName: InvestProfitServiceTest
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-10-16 下午3:30:49
 * @version V3.0.0
 */
public class InvestProfitServiceTest extends BaseTest {

    @Autowired
    IBSInvestProfitService investProfitService;

    @Autowired
    InvestProfitTaskService investProfitTaskService;

    @Autowired
    InvestProfitService ips;

    @Autowired
    IAccountRuleService accountRuleService;

    @Autowired
    IInvokeRemoteService invokeRemoteService;

    /**
     * 新增年度分红比率单元测试
     */
    @Test
    public void saveDividendRateTest() {

        DividendRate dividendRate = new DividendRate();
        for (int i = 0; i < 1; i++)
        {
            dividendRate.setDividendYear("2002");
            dividendRate.setDividendRate("40");
            investProfitService.saveDividendRate(dividendRate);
        }
    }

    /**
     * 获取年度分红比率列表单元测试
     */
    @Test
    public void getDividendRateListTest() {
        Page page = new Page(1);

        page.setCurPage(1);
        page.setPageSize(10);
        PageData<DividendRate> pageDividendRateList = investProfitService.getDividendRateList("2015", page);
        List<DividendRate> dividendRateList = pageDividendRateList.getResult();
        for (DividendRate dividendRate : dividendRateList)
        {
            System.out.println(dividendRate);
        }
    }

    /**
     * 获取年度分红比率列表单元测试
     */
    @Test
    public void getHisDividendListTest() {
        Page page = new Page(1);
        page.setCurPage(1);
        page.setPageSize(10);
        PageData<CustomPointDividend> hisDividendPageData = investProfitService.getHisDividendList(page);
        List<CustomPointDividend> hisDividendList = hisDividendPageData.getResult();
        System.out.println(hisDividendList);
    }

    /**
     * 插入投资记录单元测试
     */
    @Test
    public void savePointInvestTest() {
        PointInvest pointInvest = null;
        // 插入持卡人投资记录
        // String startTime = DateUtil.getCurrentDateTime();
        // int count = 100;
        // ExecutorService executorService =
        // Executors.newFixedThreadPool(count);
        // for (int i = 0; i < count; i++)
        // {
        // executorService.execute(new Thread(new Runnable() {
        // @Override
        // public void run() {
        // PointInvest pointInvest = new PointInvest();
        // pointInvest.setInvestAmount(100 + "");
        // pointInvest.setCustId("0600102000020151215");
        // pointInvest.setHsResNo("06001020000");
        // // pointInvest.setCustId("0601000000020151231");
        // // pointInvest.setHsResNo("06010000000");
        // pointInvest.setCustName("托管企业06001020000");
        // // pointInvest.setCustName("服务公司06010000000");
        // // pointInvest.setCustType(3);
        // pointInvest.setCustType(CustType.TRUSTEESHIP_ENT.getCode());
        // pointInvest.setChannel(Channel.WEB.getCode());
        // investProfitService.savePointInvest(pointInvest);
        //
        // PointInvest pointInvest1 = new PointInvest();
        // pointInvest1.setInvestAmount(100 + "");
        // pointInvest1.setCustId("0600211811520151207");
        // pointInvest1.setHsResNo("06002118115");
        // // pointInvest.setCustId("0601000000020151231");
        // // pointInvest.setHsResNo("06010000000");
        // pointInvest1.setCustName("消费者06002118115");
        // // pointInvest.setCustName("服务公司06010000000");
        // // pointInvest.setCustType(3);
        // pointInvest1.setCustType(CustType.PERSON.getCode());
        // pointInvest1.setChannel(Channel.WEB.getCode());
        // investProfitService.savePointInvest(pointInvest1);
        // }
        // }));
        // }
        //
        // executorService.shutdown();
        // while (!executorService.isTerminated())
        // {
        // try
        // {
        // Thread.sleep(10);
        // }
        // catch (InterruptedException e)
        // {
        // e.printStackTrace();
        // }
        // }

        for (int i = 1; i < 10; i++)
        {
            pointInvest = new PointInvest();
            pointInvest.setInvestAmount((i * 100) + "");
            pointInvest.setCustId("0603511001120160416");
            pointInvest.setHsResNo("06035110011");
            pointInvest.setCustName("消费者06035110011");
            pointInvest.setCustType(CustType.PERSON.getCode());
            pointInvest.setChannel(Channel.WEB.getCode());
            investProfitService.savePointInvest(pointInvest); //

            PointInvest pointInvest1 = new PointInvest();
            pointInvest1.setInvestAmount((i * 100) + "");
            pointInvest1.setCustId("0604712000020160418");
            pointInvest1.setHsResNo("06047120000");
            pointInvest1.setCustName("06047120000");
            pointInvest1.setCustType(CustType.TRUSTEESHIP_ENT.getCode());
            pointInvest1.setChannel(Channel.WEB.getCode());
            investProfitService.savePointInvest(pointInvest1); //

            PointInvest pointInvest4 = new PointInvest();
            pointInvest4.setInvestAmount((i * 100) + "");
            pointInvest4.setCustId("0604700000020160418");
            pointInvest4.setHsResNo("06047000000");
            pointInvest4.setCustName("06047000000");
            pointInvest4.setCustType(CustType.SERVICE_CORP.getCode());
            pointInvest4.setChannel(Channel.WEB.getCode());
            investProfitService.savePointInvest(pointInvest4); //
        }

        // // 插入企业投资记录
        // for (int i = 0; i < 2; i++)
        // {
        // pointInvest = new PointInvest();
        // pointInvest.setInvestAmount("888");
        // pointInvest.setCustId("0609900000020160109");
        // pointInvest.setHsResNo("06099000000");
        // pointInvest.setCustName("服务公司06099000000");
        // pointInvest.setCustType(CustType.SERVICE_CORP.getCode());
        // pointInvest.setChannel(Channel.WEB.getCode());
        // // 获取货币帐户余额
        // AccountBalance accountBalance =
        // invokeRemoteService.getAccountBlance(pointInvest.getCustId(),
        // AccountType.ACC_TYPE_POINT10110.getCode());
        // System.out.println("转账前：积分帐户获取帐户余额：" +
        // accountBalance.getAccBalance());
        // accountBalance =
        // invokeRemoteService.getAccountBlance(pointInvest.getCustId(),
        // AccountType.ACC_TYPE_POINT10410.getCode());
        // System.out.println("转账前：积分帐户获取帐户余额：" +
        // accountBalance.getAccBalance());
        //
        // investProfitService.savePointInvest(pointInvest); //
        //
        // accountBalance =
        // invokeRemoteService.getAccountBlance(pointInvest.getCustId(),
        // AccountType.ACC_TYPE_POINT10110.getCode());
        // System.out.println("转账后：积分帐户获取帐户余额：" +
        // accountBalance.getAccBalance());
        // accountBalance =
        // invokeRemoteService.getAccountBlance(pointInvest.getCustId(),
        // AccountType.ACC_TYPE_POINT10410.getCode());
        // System.out.println("转账后：积分帐户获取帐户余额：" +
        // accountBalance.getAccBalance());

        // }
        // System.out.println("结束时间：" + new Date().getSeconds());
        // System.out.println("开始时间：" + startTime + "  结束时间：" +
        // DateUtil.getCurrentDateTime());
    }

    @Test
    public void getPointInvestListTest() {

        Page page = new Page(1);

        PageData<PointInvest> pagePointInvestList = investProfitService.getPointInvestList("0600701000020151231",
                "2016-01-15", "2016-01-15", page);

        List<PointInvest> pointInvestList = pagePointInvestList.getResult();

        for (PointInvest pointInvest : pointInvestList)
        {
            System.out.println(pointInvest);
        }
    }

    /**
     * 获取积分投资分红列表
     */
    @Test
    public void getPointDividendListTest() {
        Page page = new Page(1, 100);

        PageData<PointDividend> pagePointDividendList = investProfitService.getPointDividendList("0601000000020151231",
                "2015", "", page);

        if (pagePointDividendList != null && pagePointDividendList.getResult().size() > 0)
        {
            List<PointDividend> pointDividendList = pagePointDividendList.getResult();

            for (PointDividend pointDividend : pointDividendList)
            {
                System.out.println("获取积分投资分红列表" + pointDividend);
            }
        }

    }

    /**
     * 获取积分投资分红明细列表
     */
    @Test
    public void getDividendDetailListTest() {
        Page page = new Page(1, 100);

        PageData<DividendDetail> dividendDetailList = investProfitService.getDividendDetailList("06002118145", "2016",
                page);

        if (dividendDetailList != null && dividendDetailList.getResult().size() > 0)
        {
            List<DividendDetail> dividendDetails = dividendDetailList.getResult();

            for (DividendDetail dividendDetail : dividendDetails)
            {
                System.out.println("获取积分投资分红明细列表" + dividendDetail);
            }
        }

    }

    /**
     * 查询积分投资分红信息
     */
    @Test
    public void testGetInvestDividendInfo() {
        // System.out.println("查询积分投资分红信息" +
        // investProfitService.getInvestDividendInfo("06002110000", "");
        System.out.println("查询积分投资分红信息" + investProfitService.getInvestDividendInfo("06002118145", "2014"));
    }

    /**
     * 查询积分投资详情
     */
    @Test
    public void testGetPointInvestInfo() {
        System.out.println("查询积分投资详情" + investProfitService.getPointInvestInfo("110120160406181912000000"));
    }

    @Test
    public void testGetPointDividendInfo() {
        System.out.println("查询积分投资分红详情：" + investProfitService.getPointDividendInfo("201602040451114000"));
    }

    @Test
    public void testGetDividendDetailInfo() {
        System.out.println("查询积分投资分红计算详情：" + investProfitService.getDividendDetailInfo("201512300252533043"));
    }

    @Test
    public void getInvestRate() {
        /**
         * 获取个人投资分红比率{
         */
        // 个人投资分红流通币比率
        String perDividHsbScale = accountRuleService.getPerInvesDividHsbScale();
        // 个人投资分红定向消费币比率
        String perDirectHsbScale = accountRuleService.getPerInvesDirectHsbScale();
        System.out.println("个人投资分红流通币比率：" + perDividHsbScale);
        System.out.println("个人投资分红定向消费币比率：" + perDirectHsbScale);
        /**
         * }
         */
        /**
         * 获取企业投资分红比率{
         */
        // 企业投资分红流通币比率
        String entDividHsbScale = accountRuleService.getEntInvesDividHsbScale();
        // 企业投资分红慈善救助基金比率
        String entDirectHsbScale = accountRuleService.getEntInvesDirectHsbScale();
        System.out.println("企业投资分红流通币比率：" + entDividHsbScale);
        System.out.println("企业投资分红慈善救助基金比率：" + entDirectHsbScale);
        /**
         * }
         */
    }

}
