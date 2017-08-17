package com.gy.hsxt.gp.service.testcase;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gp.api.IGPTransCashService;
import com.gy.hsxt.gp.bean.TransCash;
import com.gy.hsxt.gp.constant.GPConstant;
import com.gy.hsxt.gp.constant.GPConstant.GPTransSysFlag;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test-resources/spring-global-test.xml" })
public class PingAnTestTestCase {

	@Autowired
	public IGPTransCashService gpTransCashService;
		
	@Test
	public void TransCashTest_CrossBank() {
		Date orderDate = new Date();
		
		String transAmount = "100.00";
		String merId = GPConstant.MerId.HS;
		String orderNo = "20160630100000000001";
		String inAccNo = "6225887556045587";
		String inAccName = "姜方春";
		String inAccBankName = "招商银行";
		String inAccBankNode = "206";
		String inAccCityCode = "440104";
		String inAccCityName = "深圳市";
		String inAccProvinceCode = "44";
		String sysFlag = GPTransSysFlag.MORE_URGENT;
		
		List<String> notifyMobile = new ArrayList<String>();
		String srcSubsysId = "PS";
		
		try {
			notifyMobile.add("13751080605");
			notifyMobile.add("13923709455");
			
			TransCash transCash = new TransCash(inAccNo, inAccName,
					inAccBankName, inAccBankNode, inAccCityCode, orderNo,
					orderDate, transAmount);
			transCash.setUseDesc("测试转账:"+transAmount+"元,同城跨行个人");
			transCash.setNotifyMobile(notifyMobile);
			transCash.setInAccCityName(inAccCityName);
			transCash.setInAccProvinceCode(inAccProvinceCode);
			transCash.setSysFlag(sysFlag);
			
			System.out.println(gpTransCashService.transCash(merId, transCash,
					srcSubsysId));
		} catch (HsException ex) {
			fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:"
					+ ex.getMessage());
		}
	}
	
	@Test
	public void TransCashTest_SameBank() {
		Date orderDate = new Date();
		
		String transAmount = "1.00";
		String merId = GPConstant.MerId.HS;
		String orderNo = "20160616200000001004";
		String inAccNo = "6225350000054382";
		String inAccName = "PA测试86433";
		String inAccBankName = "深圳发展银行";
		String inAccBankNode = "117";
		String inAccCityCode = "440224";
		String inAccCityName = "深圳市";
		String inAccProvinceCode = "44";
		String sysFlag = GPTransSysFlag.MORE_URGENT;
		
		List<String> notifyMobile = new ArrayList<String>();

		String srcSubsysId = "PS";
		
		try {
			notifyMobile.add("13751080605");
			notifyMobile.add("13923709455");
			
			TransCash transCash = new TransCash(inAccNo, inAccName,
					inAccBankName, inAccBankNode, inAccCityCode, orderNo,
					orderDate, transAmount);
			transCash.setUseDesc("测试转账:"+transAmount+"元,同城同行个人");
			transCash.setNotifyMobile(notifyMobile);
			transCash.setInAccCityName(inAccCityName);
			transCash.setInAccProvinceCode(inAccProvinceCode);
			transCash.setSysFlag(sysFlag);
			
			System.out.println(gpTransCashService.transCash(merId, transCash,
					srcSubsysId));
		} catch (HsException ex) {
			fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:"
					+ ex.getMessage());
		}
	}
	
	/**
	 * 批量提现
	 */
	@Test
	public void BatchTransCashTest() {
		
		Date orderDate = new Date();
		
		String transAmount = "1.00";
		String merId = GPConstant.MerId.HS;
		String orderNo = "20160610400000000004";
		String inAccNo = "6225350000054382";
		String inAccName = "PA测试86433";
		String inAccBankName = "深圳发展银行";
		String inAccBankNode = "116";
		String inAccCityCode = "440227";
		String inAccCityName = "深圳市";
		String inAccProvinceCode = "44";
		String sysFlag = GPTransSysFlag.MORE_URGENT;
		
		String batchNo = "20161120091900000005";
		
		
		List<TransCash> transCashList = new ArrayList<TransCash>();

		try {
			for (int i = 0; i < 10; i++) {
				TransCash transCash = new TransCash(inAccNo, inAccName,
						inAccBankName, inAccBankNode, inAccCityCode, orderNo
								+ i, orderDate, transAmount);
				transCash.setUseDesc("资金用途");
				transCash.setInAccCityName(inAccCityName);
				transCash.setInAccProvinceCode(inAccProvinceCode);
				transCash.setSysFlag(sysFlag);
				transCashList.add(transCash);
			}

			System.out.println(gpTransCashService.batchTransCash(merId,
					batchNo, transCashList, "AO"));
		} catch (HsException ex) {
			fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:"
					+ ex.getMessage());
		}

	}

	/**
	 * 提现结果查询
	 */
	@Test
	public void getTransCashOrderStateTest() {
		
		String merId = GPConstant.MerId.HS;

		try {
			
//			String[] orderNos = new String[] { "201600528200000000001",
//					"201600528200000000002", "201600528200000000003",
//					"201600528200000000004", "20160528100000000004",
//					"20160528100000000005", "20160528100000000006",
//					"20160528100000000007", };
			
			String[] orderNos = new String[] { "20160616200000001002", "20160616200000001003", "20160616200000001004"};
			
			for (String orderNo:orderNos) {
				System.out.println(gpTransCashService.getTransCashOrderState(merId,
						orderNo));
			}
		} catch (HsException ex) {
			fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:"
					+ ex.getMessage());
		}

	}
}
