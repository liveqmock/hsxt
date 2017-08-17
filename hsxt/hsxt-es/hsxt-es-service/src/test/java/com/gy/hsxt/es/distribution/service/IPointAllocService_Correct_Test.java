package com.gy.hsxt.es.distribution.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner; 

import com.alibaba.dubbo.common.json.JSON;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.es.distribution.bean.Alloc;
import com.gy.hsxt.es.distribution.service.IPointAllocService;
import com.gy.hsxt.es.points.bean.CorrectDetail;
import com.gy.hsxt.es.points.bean.PointDetail;
 
/**   
 * Simple to Introduction
 * @category          Simple to Introduction
 * @projectName   hsxt-ps-service
 * @package           
 * @className       
 * @description      积分分配
 * @author              liuchao
 * @createDate       2015-8-7 上午9:25:26  
 * @updateUser      liuchao
 * @updateDate      2015-8-7 上午9:25:26
 * @updateRemark 说明本次修改内容
 * @version              v0.0.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/spring-global.xml" })
public class IPointAllocService_Correct_Test {
	@Autowired
	private IPointAllocService pointAllocService;
	PointDetail pointDetail=null;
	CorrectDetail correct=new CorrectDetail(); 
	
	@Before
	public void setUp() throws Exception {
		pointDetail =new PointDetail(); 
		pointDetail.setBatchNo("110123");
		pointDetail.setChannelType(1);
		pointDetail.setEntCustId("12345698712");
		pointDetail.setEntResNo("12345678910");
		pointDetail.setCurrencyRate(new BigDecimal(Double.toString(2)));
		pointDetail.setTransAmount(new BigDecimal(Double.toString(94.568)));
		pointDetail.setEquipmentNo("12345678900");
		pointDetail.setEquipmentType(1);
		pointDetail.setOperNo("陈宏志");
		pointDetail.setTransNo("12312312312312");
		pointDetail.setPerCustId("00123456789");
		pointDetail.setPerResNo("07418522986");
		pointDetail.setPointRate(new BigDecimal(Double.toString(0.275)));
		pointDetail.setSourceCurrencyCode("123");
		pointDetail.setSourceTransAmount(new BigDecimal(Double.toString(94.568)));
		pointDetail.setSourceTransDate(new Timestamp(new Date().getTime()));
		pointDetail.setSourceTransNo("78945612300");
		pointDetail.setTransAmount(new BigDecimal(Double.toString(94.568))); 
		pointDetail.setTransNo("0000000000");
		pointDetail.setTransType("A10000");
		
		correct.setReturnNo("9999999");
		
		
	}
 
	@Test
	/**
	 * 消费积分持卡人本地消费  消费
	 */
	public void allocPoint() { 
		pointDetail.setTransType("A30000"); 
		correct.setTransType("A30010");
		try {
			
			List<Alloc> list=pointAllocService.allocReturnPoint(correct, pointDetail);
			outList(list);
			assertEquals(list.size(), 4);  
		} catch (HsException e) { 
			fail("ErrorCode:" + e.getErrorCode() + " ErrorMessage:" + e.getMessage() + "\n" + e.getStackTrace()); 
		} 
	}
	
	
 
	@Test
	/**
	 *  消费积分持卡人本地消费  定向互生币 消费
	 */
	public void allocPoint1(){
		pointDetail.setTransType("A20000"); 
		correct.setTransType("A20010");
		try {
			List<Alloc> list=pointAllocService.allocReturnPoint(correct,pointDetail);
			outList(list);
			assertEquals(list.size(), 5); 
		} catch (HsException e) { 
			fail("ErrorCode:" + e.getErrorCode() + " ErrorMessage:" + e.getMessage() + "\n" + e.getStackTrace());

		}
	}
	
	
	
	
	   
	public void outList(List<Alloc> list){
		for (int i = 0; i < list.size(); i++) {
			Alloc a=list.get(i); 
			try {
				System.out.println(	JSON.json(a));
			} catch (IOException e) { 
				e.printStackTrace();
			}
		}
	}
}

