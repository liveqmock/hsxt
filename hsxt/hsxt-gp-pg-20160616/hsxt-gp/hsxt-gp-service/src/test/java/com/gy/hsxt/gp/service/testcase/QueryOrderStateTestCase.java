package com.gy.hsxt.gp.service.testcase;

import static org.junit.Assert.fail;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gp.api.IGPPaymentService;
import com.gy.hsxt.gp.bean.FirstQuickPayBean;
import com.gy.hsxt.gp.bean.QuickPayBean;
import com.gy.hsxt.gp.bean.QuickPaySmsCodeBean;
import com.gy.hsxt.gp.constant.GPConstant;
import com.gy.hsxt.gp.constant.GPConstant.BankCardType;
import com.gy.hsxt.gp.constant.GPConstant.GPErrorCode;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test-resources/spring-global-test.xml" })
public class QueryOrderStateTestCase {

	private String jumpUrl = "http://192.168.1.119:8083/pg-test/index.jsp";
	private String bankCardNo = "6221558812340000";// 6221558812340000
	private int bankCardType = BankCardType.CREDIT_CARD;
	private Date orderDate = new Date();
	private String bankId = "0019";// 0004
	private String transAmount = "1000000"; //"1000000";
	private String merId = GPConstant.MerId.HS;
	private String custId = "custId000001";
	private String bingingNo = "201503050109140001010000";
	private String smsCode = "111112";
	
	private String srcSubsysId="AO";
	
	private static String orderNo = "";

	@Resource(name = "gpPaymentService2")
	public IGPPaymentService gpPaymentService;
	
	/**
	 * 获取快捷支付URL(首次)
	 */
	// @Test
	public void FirstQuickPayUrlTest() {

		try {
			FirstQuickPayBean firstQuickPayBean = new FirstQuickPayBean(merId,
					orderNo, orderDate, transAmount, "", custId,
					bankCardNo, bankCardType, bankId);

			firstQuickPayBean.setCurrencyCode("CNY");
			firstQuickPayBean.setTransDesc("交易描述");
			firstQuickPayBean.setPrivObligate("私有内容");
			System.out.println(gpPaymentService
					.getFirstQuickPayUrl(firstQuickPayBean,srcSubsysId));

		} catch (HsException ex) {
			ex.printStackTrace();
			
			if(ex.getErrorCode()==GPErrorCode.REPEAT_BINDING){
				QuickPaySmsCodeTest();
			}
		}

	}
		
	/**
	 * 获取快捷支付短信验证码
	 * 
	 */
	@Test
	public void QuickPaySmsCodeTest() {

		orderNo = "2016051400000000017";

		try {

			QuickPaySmsCodeBean quickPaySmsCodeBean = new QuickPaySmsCodeBean(
					merId, orderNo, orderDate, transAmount, "", bingingNo);
			quickPaySmsCodeBean.setCurrencyCode("CNY");
			quickPaySmsCodeBean.setTransDesc("交易描述");
			quickPaySmsCodeBean.setPrivObligate("私有内容");
			
			boolean success = gpPaymentService.getQuickPaySmsCode(
					quickPaySmsCodeBean, srcSubsysId);
			
			if (success) {
				QuickPayUrlTest();
			}
		} catch (HsException ex) {
			fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:"
					+ ex.getMessage());
		}
	}

	/**
	 * 获取快捷支付URL(非首次)
	 */
	@Test
	public void QuickPayUrlTest() {
//		orderNo = "2016051300000000007";
		
		smsCode = "333324";
		
		try {
			QuickPayBean quickPayBean = new QuickPayBean(merId, orderNo,
					bingingNo, smsCode);
			System.out.println(gpPaymentService.getQuickPayUrl(quickPayBean,srcSubsysId));

		} catch (HsException ex) {
			fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:"
					+ ex.getMessage());
		}

	}

	/**
	 * 支付结果查询
	 */
	@Test
	public void getPaymentOrderStateTest() {
		try {
			System.out.println(gpPaymentService.getPaymentOrderState(merId,
					"2016051300000000002"));

		} catch (HsException ex) {
			fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:"
					+ ex.getMessage());
		}

	}
}
