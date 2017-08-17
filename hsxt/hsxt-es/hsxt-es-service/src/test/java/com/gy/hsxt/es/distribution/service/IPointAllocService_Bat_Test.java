package com.gy.hsxt.es.distribution.service;

  

 import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner; 

 
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.es.distribution.service.IPointAllocService;
 
 
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
public class IPointAllocService_Bat_Test {
	@Autowired
	private IPointAllocService pointAllocService;
  


	/**
	 * 日终积分汇总
	 */
	@Test
	public void settlementDayPoint() { 
		try {
		//	pointAllocService.settlementDayPoint();
		} catch (HsException e) { 
			e.printStackTrace();
		}
	}
	
	/**
	 * 月终积分汇总
	 */
	@Test
	public void settlementMonthPoint() { 
//		try {
//			pointAllocService.settlementMonthPoint();
//		} catch (HsException e) { 
//			e.printStackTrace();
//		}
	}
}

	