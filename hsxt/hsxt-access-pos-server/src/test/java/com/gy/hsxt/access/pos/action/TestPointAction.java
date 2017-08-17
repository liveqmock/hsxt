package com.gy.hsxt.access.pos.action;

import java.math.BigDecimal;
import java.util.concurrent.ArrayBlockingQueue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.access.pos.NettyServer;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.handler.PosInitializer;
import com.gy.hsxt.access.pos.model.BitMap;
import com.gy.hsxt.access.pos.point.data.PosReqBuilderHandler;
import com.gy.hsxt.access.pos.point.data.PreData;

/***************************************************************************
 * <PRE>
 * Description 		: Simple to Introduction
 * 
 * Project Name   	: hsxt-access-pos-api
 * 
 * Package Name     : com.gy.hsxt.access.pos.action
 * 
 * File Name        : TestSigninAction
 * 
 * Author           : weiyq
 * 
 * Create Date      : 2015-11-10 上午9:56:36  
 * 
 * Version          : v0.0.1
 * 
 * </PRE>
 ***************************************************************************/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml" })
public class TestPointAction {

    @Autowired
    @Qualifier("nettyServer")
    private NettyServer nettyServer;

    @Autowired
    @Qualifier("reqBuilder")
    private PosReqBuilderHandler reqBuilder;

    @Autowired
    @Qualifier("posInitializer")
    private PosInitializer posInitializer;

    public PreData preData;

    final ArrayBlockingQueue<byte[]> que = new ArrayBlockingQueue<>(360);

    byte[] result;

    public static final String PACK_SUCCESS_STR = String.valueOf(PosRespCode.SUCCESS.getCode()).substring(2);

    @Before
    public void setUp() throws Exception {

        preData = new PreData();

        // nettyServer = new NettyServer();
        // nettyServer.init(8001, 1, 60, posInitializer);
        nettyServer.start();
        System.out.println("---------setUp");
        que.clear();
        Thread.sleep(3_000);
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * 积分
     * 
     * @throws Exception
     */
    @Test
    public void testPoint() throws Exception {

        try
        {
            BigDecimal tradeAmount = new BigDecimal(100.0);
            BigDecimal pointRate = new BigDecimal(0.1);
            BigDecimal cashAmount = new BigDecimal(100.0);
            boolean serviceWay = false;

            String[] bodyArr = null;
            System.out.println("---------testPoint");
            String packPin = reqBuilder.getPackPin(preData.entNo, preData.posNo, preData.operNo, preData.cardNo,
                    preData.tradePwd);
            // 消费积分
            bodyArr = preData.preparePoint(tradeAmount, pointRate, cashAmount, serviceWay, packPin);
            result = reqBuilder.sendPubParam(PosConstant.POINTTYPEREQ, bodyArr, preData.entNo, preData.posNo,
                    preData.operNo, PreData.PVERSION);
            BitMap[] bitMaps = preData.checkResult(result, PACK_SUCCESS_STR);

            // 消费积分 冲正
            String posRunCode = bitMaps[11].getStr();
            String tradeMoneyStr = bitMaps[4].getStr();
            bodyArr = preData.preparePointReverse(posRunCode, tradeMoneyStr);
            result = reqBuilder.sendPubParam(PosConstant.POINTREVERSETYPEREQ, bodyArr, preData.entNo, preData.posNo,
                    preData.operNo, PreData.PVERSION);
            preData.checkResult(result, PACK_SUCCESS_STR);

            // 消费积分 撤单
            String orderNo = bitMaps[37].getStr();// 原流水号

            bodyArr = preData.preparePointCancle(orderNo, true, true, packPin);
            result = reqBuilder.sendPubParam(PosConstant.POINTTYPEREQ, bodyArr, preData.entNo, preData.posNo,
                    preData.operNo, PreData.PVERSION);
            // bitMaps = preData.checkResult(result, INVALID_POINT_ORDER_STR);
            bitMaps = preData.checkResult(result, PACK_SUCCESS_STR);

            // 撤单 冲正
            posRunCode = bitMaps[11].getStr();// 原流水号

            posRunCode = "000009";// 原终端流水号
            BigDecimal tradeMoney = new BigDecimal(0);// 0为没4域
            bodyArr = preData.preparePointCancleReverse(posRunCode, tradeMoney);
            result = reqBuilder.sendPubParam(PosConstant.POINTREVERSETYPEREQ, bodyArr, preData.entNo, preData.posNo,
                    preData.operNo, PreData.PVERSION);
            preData.checkResult(result, PACK_SUCCESS_STR);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 订单查询
     * @throws Exception
     */
    @Test
    public void testPointOrderSearch() throws Exception{
        BitMap[] bitMaps = null;
        String[] bodyArr = null;
        String packPin = reqBuilder.getPackPin(preData.entNo, preData.posNo, preData.operNo, preData.cardNo,
                preData.tradePwd);
        System.out.println("----------------------"+packPin);
        //消费积分
        bodyArr = preData.preparePoint(new BigDecimal(111), new BigDecimal(0.15), new BigDecimal(0), true,packPin);
        result = reqBuilder.sendPubParam(PosConstant.POINTTYPEREQ, bodyArr, preData.entNo, preData.posNo, preData.operNo, PreData.PVERSION);
        System.out.println("测试接口调用完毕");
        bitMaps = preData.checkResult(result, PACK_SUCCESS_STR);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>积分完成");
        
        String orderNo = bitMaps[37].getStr();//原流水号
        bodyArr = preData.prepareOrderSearch(orderNo);
        result = reqBuilder.sendPubParam(PosConstant.POINTSEARCHTYPEREQ, bodyArr, preData.entNo, preData.posNo, preData.operNo, PreData.PVERSION);
        bitMaps = preData.checkResult(result, PACK_SUCCESS_STR);
        
        String str48 = bitMaps[48].getStr();
        
        System.out.println("48:" + str48);
    }
}
