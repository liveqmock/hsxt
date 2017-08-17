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
 * Project Name   	: hsxt-access-pos-server
 *
 * Package Name     : com.gy.hsxt.access.pos.action
 *
 * File Name        : TestHsbPayAction
 * 
 * Author           : wucl
 *
 * Create Date      : 2015-11-7 下午2:50:10  
 *
 * Update User      : wucl
 *
 * Update Date      : 2015-11-7 下午2:50:10
 *
 * UpdateRemark 	: 说明本次修改内容
 *
 * Version          : v0.0.1
 *
 * </PRE>
 ***************************************************************************/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext.xml"})
public class TestHsbPayAction {
	
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
		
		nettyServer.start();
		System.out.println("---------setUp");
		que.clear();
		Thread.sleep(3_000);
    }  
  
    @After  
    public void tearDown() throws Exception {  
    } 
    
    /**
     * 签到 
     * @throws Exception
     */
    @Test
    public void testSignIn() throws Exception{
    	
    	try{
    		
    	
	    	String[] bodyArr = null;
	     	System.out.println("---------testSignIn");
	    	//签到 
	    	bodyArr = preData.prepareSignIn();
	    	result = reqBuilder.sendPubParam(PosConstant.SIGNINTYPEREQ, bodyArr, preData.entNo, preData.posNo, preData.operNo, PreData.PVERSION);
	    	preData.checkResult(result, PACK_SUCCESS_STR);
	  	
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    /**
     * 互生币支付   
     * @throws Exception
     */
    @Test
    public void testHsbPay() throws Exception{
    	
        BigDecimal tradeMoney = new BigDecimal(122); 
    	String packPin = reqBuilder.getPackPin(preData.entNo, preData.posNo, preData.operNo, preData.cardNo, preData.tradePwd);
		//互生币只有刷卡
    	String[] bodyArr = preData.prepareHsbPay(tradeMoney, new BigDecimal(0.1), packPin);
		result = reqBuilder.sendPubParam(PosConstant.HSPAYTYPEREQ, bodyArr, preData.entNo, preData.posNo, preData.operNo, PreData.PVERSION);
		BitMap[] bitMaps = preData.checkResult(result, PACK_SUCCESS_STR);
//		String orderNo = bitMaps[37].getStr();//原流水号
		
		
//		//冲正
//		String posRunCode = null;
//		
//		posRunCode = bitMaps[11].getStr();
//		String tradeMoneyStr = bitMaps[4].getStr(); //冲正金额
//
//		bodyArr = preData.prepareHsbReverse(posRunCode, tradeMoneyStr, PosConstant.HS_ORDER_POS_TERM_TRADE_CODE_HSB, "63");
//		result = reqBuilder.sendPubParam(PosConstant.POINTREVERSETYPEREQ, bodyArr, preData.entNo, preData.posNo, preData.operNo, PreData.PVERSION);
//		bitMaps = preData.checkResult(result, PACK_SUCCESS_STR);
    }
    
    
    
    
    /**
     * 签退
     * @throws Exception
     */
    @Test
    public void testSignOff() throws Exception{
        
        try{
            String[] bodyArr = null;
            //签到 
            bodyArr = preData.prepareSignOff();
            result = reqBuilder.sendPubParam(PosConstant.SIGNOFFTYPEREQ, bodyArr, preData.entNo, preData.posNo, preData.operNo, PreData.PVERSION);
            preData.checkResult(result, PACK_SUCCESS_STR);
        
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

	