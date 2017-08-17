package com.gy.hsxt.uc.check;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.uc.as.bean.checker.AsDoubleChecker;
import com.gy.hsxt.uc.checker.bean.DoubleChecker;
import com.gy.hsxt.uc.checker.service.UCAsDoubleCheckerInfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-uc.xml" })
public class UCAsDoubleCheckerInfoServiceTest {
	
	@Autowired
	UCAsDoubleCheckerInfoService doubleCheckerInfoService;
	
	@SuppressWarnings("unused")
	@Test
	public void ListDoubleChecker(){
		Page page = new Page(1,10);
		String platformCode = "341";
		String subSystemCode = "121";
		List<AsDoubleChecker> list = doubleCheckerInfoService.ListDoubleChecker(platformCode, subSystemCode, page);
	}
	
	@Test
	public void modifyAdminLogPwd(){
		AsDoubleChecker record = new AsDoubleChecker();
		record.setOperCustId("899163253859361794");
		record.setPwdLogin("n+LCw3dBNAPVk534hSda7g==");
		record.setNewPwdLogin("n+LCw3dBNAPVk534hSda7g==");
		record.setSecretKey("5030dd8eeb715004");
		doubleCheckerInfoService.modifyAdminLogPwd(record);
	}

	@SuppressWarnings("unused")
	@Test
	public void regDoubleChecker(){
		AsDoubleChecker record = new AsDoubleChecker();
		record.setOperCustId("899167246299907074");
		record.setDuty("老孙");
		record.setPwdLogin("n+LCw3dBNAPVk534hSda7g==");
		record.setSecretKey("5030dd8eeb715004");
//		doubleCheckerInfoService.regDoubleChecker(record);
	}
	@SuppressWarnings("unused")
	@Test
	public void searchDoubleCheckerByCustId(){
		String checkCustId = "899167246299907072";
		AsDoubleChecker doubleChecker = doubleCheckerInfoService.searchDoubleCheckerByCustId(checkCustId);
	
	}
	
	@Test
	public void updateDoubleCheckerInfo(){
		AsDoubleChecker record = new AsDoubleChecker();
		record.setOperCustId("899163253859361794");
		record.setDuty("老孙");
		doubleCheckerInfoService.updateDoubleCheckerInfo(record);
	}
}
