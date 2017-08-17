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
public class IPointAllocService_POINT_Test {
	@Autowired
	private IPointAllocService pointAllocService;
	PointDetail pointDetail=null;
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
	}
 
	@Test
	/**
	 * 消费积分持卡人本地消费  消费
	 */
	public void allocPoint() { 
		pointDetail.setTransType("A30000"); 
		try {
			List<Alloc> list=pointAllocService.allocPoint(pointDetail);
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
		try {
			List<Alloc> list=pointAllocService.allocPoint(pointDetail);
			outList(list);
			assertEquals(list.size(), 5); 
		} catch (HsException e) { 
			fail("ErrorCode:" + e.getErrorCode() + " ErrorMessage:" + e.getMessage() + "\n" + e.getStackTrace());
		}
	}
	
	@Test
	/**
	 *  消费积分持卡人本地消费  流通互生币  消费
	 */
	public void allocPoint2(){
		pointDetail.setTransType("A10000"); 
		try {
			List<Alloc> list=pointAllocService.allocPoint(pointDetail);
			outList(list);
			assertEquals(list.size(), 5); 
		} catch (HsException e) { 
			fail("ErrorCode:" + e.getErrorCode() + " ErrorMessage:" + e.getMessage() + "\n" + e.getStackTrace());

		}
	}
	 
	@Test
	/**
	 *  消费积分持卡人本地消费    预留 消费
	 */
	public void allocPoint3(){
		pointDetail.setTransType("A30300"); 
		try {
			List<Alloc> list=pointAllocService.allocPoint(pointDetail);
			outList(list);
			assertEquals(list.size(), 2); 
		} catch (HsException e) { 
			fail("ErrorCode:" + e.getErrorCode() + " ErrorMessage:" + e.getMessage() + "\n" + e.getStackTrace());

		}
	}
	 
	@Test
	/**
	 *  消费积分持卡人本地消费    预留  互生币消费
	 */
	public void allocPoint4(){
		pointDetail.setTransType("A10300"); 
		try {
			List<Alloc> list=pointAllocService.allocPoint(pointDetail);
			outList(list);
			assertEquals(list.size(), 4); 
		} catch (HsException e) { 
			fail("ErrorCode:" + e.getErrorCode() + " ErrorMessage:" + e.getMessage() + "\n" + e.getStackTrace());

		}
	}
	 
	@Test
	/**
	 *  消费积分持卡人本地消费    结单  消费
	 */
	public void allocPoint5(){
		pointDetail.setTransType("A30400"); 
		try {
			List<Alloc> list=pointAllocService.allocPoint(pointDetail);
			outList(list);
			assertEquals(list.size(), 4);
			
		} catch (HsException e) { 
			fail("ErrorCode:" + e.getErrorCode() + " ErrorMessage:" + e.getMessage() + "\n" + e.getStackTrace());

		}
	}
	 
	@Test
	/**
	 *  消费积分持卡人本地消费    结单  互生币消费
	 */
	public void allocPoint6(){
		pointDetail.setTransType("A10400"); 
		try {
			List<Alloc> list=pointAllocService.allocPoint(pointDetail);
			outList(list);
			assertEquals(list.size(), 5);
			
		} catch (HsException e) { 
			fail("ErrorCode:" + e.getErrorCode() + " ErrorMessage:" + e.getMessage() + "\n" + e.getStackTrace());

		}
	}
	 
	@Test
	/**
	 *  消费积分无卡   消费
	 */
	public void allocPoint_notCard(){
		pointDetail.setTransType("B30000"); 
		try {
			List<Alloc> list=pointAllocService.allocPoint(pointDetail);
			outList(list);
			assertEquals(list.size(), 3); 
		} catch (HsException e) { 
			fail("ErrorCode:" + e.getErrorCode() + " ErrorMessage:" + e.getMessage() + "\n" + e.getStackTrace());

		} 
	}	
	
	@Test
	/**
	 *  消费积分无卡   预留    消费
	 */
	public void allocPoint_notCard1(){
		pointDetail.setTransType("B30300"); 
		try {
			List<Alloc> list=pointAllocService.allocPoint(pointDetail);
			outList(list);
			assertEquals(list.size(), 2); 
		} catch (HsException e) { 
			fail("ErrorCode:" + e.getErrorCode() + " ErrorMessage:" + e.getMessage() + "\n" + e.getStackTrace());

		} 
	}
	
	
	@Test
	/**
	 *  消费积分无卡   结单    消费
	 */
	public void allocPoint_notCard2(){
		pointDetail.setTransType("B30400"); 
		try {
			List<Alloc> list=pointAllocService.allocPoint(pointDetail);
			outList(list);
			assertEquals(list.size(), 3); 
		} catch (HsException e) { 
			fail("ErrorCode:" + e.getErrorCode() + " ErrorMessage:" + e.getMessage() + "\n" + e.getStackTrace());

		} 
	}
	
	@Test
	/**
	 *  消费积分本地卡异地消费    消费
	 */
	public void allocPoint_loacl(){
		pointDetail.setTransType("C30000"); 
		try {
			List<Alloc> list=pointAllocService.allocPoint(pointDetail);
			outList(list);
			assertEquals(list.size(), 2); 
		} catch (HsException e) { 
			fail("ErrorCode:" + e.getErrorCode() + " ErrorMessage:" + e.getMessage() + "\n" + e.getStackTrace());

		} 
		
	}
	
	
	@Test
	/**
	 *  消费积分本地卡异地消费    互生币  消费
	 */
	public void allocPoint_loacl1(){
		pointDetail.setTransType("C10000"); 
		try {
			List<Alloc> list=pointAllocService.allocPoint(pointDetail);
			outList(list);
			assertEquals(list.size(), 3); 
		} catch (HsException e) { 
			fail("ErrorCode:" + e.getErrorCode() + " ErrorMessage:" + e.getMessage() + "\n" + e.getStackTrace());

		} 
		
	}
	
	
	
	@Test
	/**
	 *  消费积分异地卡本地消费    消费
	 */
	public void allocPoint_Allopatry(){
		pointDetail.setTransType("E30000"); 
		try {
			List<Alloc> list=pointAllocService.allocPoint(pointDetail);
			outList(list);
			assertEquals(list.size(), 2); 
		} catch (HsException e) { 
			fail("ErrorCode:" + e.getErrorCode() + " ErrorMessage:" + e.getMessage() + "\n" + e.getStackTrace());

		} 
		
	}
	
	@Test
	/**
	 *  消费积分异地卡本地消费    互生币  消费
	 */
	public void allocPoint_Allopatry1(){
		pointDetail.setTransType("E10000"); 
		try {
			List<Alloc> list=pointAllocService.allocPoint(pointDetail);
			outList(list);
			assertEquals(list.size(), 2); 
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

