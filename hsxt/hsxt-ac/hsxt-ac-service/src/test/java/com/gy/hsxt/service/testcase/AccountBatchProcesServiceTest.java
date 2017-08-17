package com.gy.hsxt.service.testcase;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.ac.api.IAccountBatchProcesService;
import com.gy.hsxt.common.exception.HsException;
		
/**   
 * Simple to Introduction
 * @category          Simple to Introduction
 * @projectName   hsxt-ac-service
 * @package           com.gy.hsxt.ac.service.AccountBatchProcesServiceTest.java
 * @className       AccountBatchProcesServiceTest
 * @description      一句话描述该类的功能 
 * @author              maocan
 * @createDate       2015-8-31 上午11:43:37  
 * @updateUser      zhucy
 * @updateDate      2015-8-31 上午11:43:37
 * @updateRemark 说明本次修改内容
 * @version              v0.0.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring/spring-global.xml"})
public class AccountBatchProcesServiceTest {
	
	@Autowired
	IAccountBatchProcesService iAccountBatchProcesService;
	

	@Test
	public void testBatchChargeAccount() {
		String fileAddress = "PS"+File.separator+"PV";
		String batchDate = "20160317";
//		String batchDate = "";
		try {
//			iAccountBatchProcesService.batchChargeAccount("消费积分分配批记账",fileAddress,batchDate,false);
//			iAccountBatchProcesService.batchChargeAccount("消费积分互生币批记账",fileAddress,batchDate,false);
//			iAccountBatchProcesService.batchChargeAccount("业务服务系统批记账",fileAddress,batchDate,false);
//			iAccountBatchProcesService.batchChargeAccount("扣年费批记账",fileAddress,batchDate,true);
//			iAccountBatchProcesService.integralActiveFile("批生成日积分活动资源列表", fileAddress);
//		    iAccountBatchProcesService.entGiveIntegralMonthFile("企业月送积分资源列表", fileAddress);
		} catch (HsException e) {
			e.printStackTrace();
		}
		
	}

}

	