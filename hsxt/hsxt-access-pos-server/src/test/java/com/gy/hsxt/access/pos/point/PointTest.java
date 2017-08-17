package com.gy.hsxt.access.pos.point;

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
import com.gy.hsxt.access.pos.point.data.PosReqBuilderHandler;
import com.gy.hsxt.access.pos.point.data.PreData;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext.xml"})
public class PointTest {
	

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
		
//		nettyServer = new NettyServer();
//		nettyServer.init(8001, 1, 60, posInitializer);
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
	
    	//同步参数
//		String baseInfoVersion = "0001";
//		String posVersion = "0001";
//		String currencyVersion = "0001";00
//		String countryVersion = "0001";
//		
//		StringBuffer stringBuffer = new StringBuffer();
//		stringBuffer.append(baseInfoVersion);
//		stringBuffer.append(posVersion);
//		stringBuffer.append(currencyVersion);
//		stringBuffer.append(countryVersion);
//		
//		bodyArr = preData.prepareSyncParam(stringBuffer.toString());
//		result = sender.sendPubParam(Constants.PARAMTYPEREQ, bodyArr, preData.entNo, preData.posNo, preData.operNo, PreData.PVERSION);
//		preData.checkResult(result, PACK_SUCCESS_STR);
    	
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
