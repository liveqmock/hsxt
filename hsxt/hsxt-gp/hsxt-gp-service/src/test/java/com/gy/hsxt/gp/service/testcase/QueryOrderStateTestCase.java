package com.gy.hsxt.gp.service.testcase;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gp.api.IGPPaymentService;
import com.gy.hsxt.gp.bean.FirstQuickPayBean;
import com.gy.hsxt.gp.bean.NetPayBean;
import com.gy.hsxt.gp.bean.QuickPayBean;
import com.gy.hsxt.gp.bean.QuickPayCardBindingBean;
import com.gy.hsxt.gp.bean.QuickPaySmsCodeBean;
import com.gy.hsxt.gp.bean.pinganpay.PinganNetPayBean;
import com.gy.hsxt.gp.constant.GPConstant;
import com.gy.hsxt.gp.constant.GPConstant.BankCardType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test-resources/spring-global-test.xml" })
public class QueryOrderStateTestCase {

	private String jumpUrl = "http://192.168.1.119:8083/pg-test/index.jsp";
	private String bankCardNo = "6221558812340000";// 6221558812340000
	private int bankCardType = BankCardType.CREDIT_CARD;
	private Date orderDate = new Date();
	private String bankId = "0019";// 0004
	private String transAmount = "1"; //"1000000";
	private String merId = GPConstant.MerId.HS;
	private String custId = "905178280087169024";
	private String bingingNo = "201503050109140001010000";
	private String smsCode = "111112";
	
	private String srcSubsysId="AO";
	
	private static String orderNo = "";

	@Resource(name = "gpPaymentService2")
	public IGPPaymentService gpPaymentService;
	
	@Test
	public void PinganNetPayTest() {
		String orderNo = "2016063040000000003";
		
		try {
			PinganNetPayBean netPayBean = new PinganNetPayBean(merId, orderNo, orderDate,
					transAmount, jumpUrl);
			netPayBean.setCurrencyCode("CNY");
			netPayBean.setTransDesc("交易描述");
			netPayBean.setPrivObligate("私有内容");
			netPayBean.setGoodsName("互生币");
			netPayBean.setPaymentUIType(1);
			netPayBean.setTransDesc("购买互生币");
			System.out.println(gpPaymentService.getNetPayUrl(netPayBean,srcSubsysId));
			
			try {
				System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (HsException ex) {
			ex.printStackTrace();
			
			fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:"
					+ ex.getMessage());
		}
	}
	
	/**
	 * 网银支付
	 */
	@Test
	public void NetPayTest() {
		String orderNo = "2016060740000000016";
		
		try {
			NetPayBean netPayBean = new NetPayBean(merId, orderNo, orderDate,
					transAmount, jumpUrl);
			netPayBean.setCurrencyCode("CNY");
			netPayBean.setTransDesc("交易描述");
			netPayBean.setPrivObligate("私有内容");
			System.out.println(gpPaymentService.getNetPayUrl(netPayBean,srcSubsysId));
			
			try {
				System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (HsException ex) {
			ex.printStackTrace();
			
			fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:"
					+ ex.getMessage());
		}
	}
	
	/**
	 * 获取银行卡签约URL(首次)
	 */
	@Test
	public void BankCardBindingUrlTest() {
		// "bankCardNo":"6227070151234567",
		// "bankOrderNo":"6016052625220059","bindingNo":"201503260110150001014567"
		
		// 姜方春
//		custId = "905178280087169024";
//		bankCardNo = "6226096556274788";
//		bankCardType = BankCardType.DEBIT_CARD;
//		bankId = "0005";
		
		// 张永顺
//		custId = "905178280087169024";
//		bankCardNo = "6217007200030180254";
//		bankCardType = BankCardType.DEBIT_CARD;
//		bankId = "0004";
		
		
		custId = "905178280087169024";
		bankCardNo = "6227070151234567";
		bankCardType = BankCardType.CREDIT_CARD;
		bankId = "0004";
		
		try {
			QuickPayCardBindingBean cardBindingBean = new QuickPayCardBindingBean(merId, custId,
					bankCardNo, bankCardType, bankId);
			
			System.out.println(gpPaymentService.getQuickPayCardBindingUrl(cardBindingBean, "BS"));

		} catch (HsException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 获取快捷支付URL(首次)
	 */
	@Test
	public void FirstQuickPayUrlTest() {
		
		custId = "0000000015630120000";
		
//		orderNo = "2016060730000000101";
//		bankCardNo = "6227070151234567"; // "6227070151234567";// 6221558812340000
//		bankCardType = BankCardType.CREDIT_CARD;
//		bankId = "0004";
		
		orderNo = "2016061630000000002";
		bankCardNo = "6227070151234567"; // "6227070151234567";// 6221558812340000
		bankCardType = BankCardType.CREDIT_CARD;
		bankId = "0004";
		
		transAmount = "1";

		try {
			FirstQuickPayBean firstQuickPayBean = new FirstQuickPayBean(merId,
					orderNo, orderDate, transAmount, "http://www.baidu.com", custId,
					bankCardNo, bankCardType, bankId);

			firstQuickPayBean.setCurrencyCode("CNY");
			firstQuickPayBean.setTransDesc("首次快捷");
			firstQuickPayBean.setPrivObligate("私有内容");
			System.out.println(gpPaymentService
					.getFirstQuickPayUrl(firstQuickPayBean,srcSubsysId));

		} catch (HsException ex) {
			ex.printStackTrace();
		}

	}
		
	/**
	 * 获取快捷支付短信验证码
	 * 
	 */
	@Test
	public void QuickPaySmsCodeTest() {

		orderNo = "2016060730000000106";
		transAmount = "1";
		// bingingNo = "201503050109140001010000"; // "6227070151234567";// 6221558812340000
		// bingingNo = "201508117746760005023987";
		// bingingNo = "201605234903960019010254";
		bingingNo = "201503260110150001014567";
		bankCardType = BankCardType.CREDIT_CARD;

		try {

			QuickPaySmsCodeBean quickPaySmsCodeBean = new QuickPaySmsCodeBean(
					merId, orderNo, orderDate, transAmount, "", bingingNo);
			quickPaySmsCodeBean.setCurrencyCode("CNY");
			quickPaySmsCodeBean.setTransDesc("张永顺支付");
			quickPaySmsCodeBean.setPrivObligate("张永顺测试");
			
			boolean success = gpPaymentService.getQuickPaySmsCode(
					quickPaySmsCodeBean, srcSubsysId);
			
			if (success) {
				QuickPayUrlTest(orderNo);
			}
		} catch (HsException ex) {
			fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:"
					+ ex.getMessage());
		}
	}

	/**
	 * 获取快捷支付URL(非首次)
	 */
	//@Test
	public void QuickPayUrlTest(String orderNo) {
		//orderNo = "2016060530000000007";
		bingingNo = "201503050109140001010000";
		smsCode = "111111";
		
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
