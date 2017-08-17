package com.gy.hsxt.gp.service.testcase;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gp.api.IGPPaymentService;
import com.gy.hsxt.gp.bean.FirstQuickPayBean;
import com.gy.hsxt.gp.bean.MobilePayBean;
import com.gy.hsxt.gp.bean.NetPayBean;
import com.gy.hsxt.gp.bean.QuickPayBean;
import com.gy.hsxt.gp.bean.QuickPayCardBindingBean;
import com.gy.hsxt.gp.bean.QuickPaySmsCodeBean;
import com.gy.hsxt.gp.constant.GPConstant;
import com.gy.hsxt.gp.constant.GPConstant.BankCardType;
import com.gy.hsxt.gp.constant.GPConstant.GPErrorCode;

/**
 * @author 作者 : zhangysh
 * @ClassName: 类名:GPPaymentServiceTestCase
 * @Description: 描述 :支付测试类
 * @createDate 创建时间: 2015-8-25 上午11:02:40
 * @version 3.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-global.xml" })
public class GPPaymentServiceTestCase {

	private String jumpUrl = "http://192.168.1.119:8083/pg-test/index.jsp";
	private String bankCardNo = "6221558812340000";// 6221558812340000
	private int bankCardType = BankCardType.CREDIT_CARD;
	private Date orderDate = new Date();
	private String bankId = "0019";// 0004
	private String transAmount = "1000000";
	private String merId = GPConstant.MerId.HS;
	private String custId = "custId000001";
	private String bingingNo = "201503050109140001010000";
	private String smsCode = "111111";
	private String orderNo1 = "2016050600000000006";
	private String orderNo2 = "2016053000000000005";
	private String orderNo3 = "2016050900000000009";
	private String orderNo4 = "2016053000000000005";
	@Autowired
	public IGPPaymentService gpPaymentService;
	private String srcSubsysId="AO";

	/**
	 * 网银支付
	 */
	@Test
	public void NetPayTest() {
		try {
			NetPayBean netPayBean = new NetPayBean(merId, orderNo1, orderDate,
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
	// @Test
	public void FirstQuickPayUrlTest() {

		try {
			FirstQuickPayBean firstQuickPayBean = new FirstQuickPayBean(merId,
					orderNo2, orderDate, transAmount, "", custId,
					bankCardNo, bankCardType, bankId);

			firstQuickPayBean.setCurrencyCode("CNY");
			firstQuickPayBean.setTransDesc("交易描述");
			firstQuickPayBean.setPrivObligate("私有内容");
			System.out.println(gpPaymentService
					.getFirstQuickPayUrl(firstQuickPayBean,srcSubsysId));

		} catch (HsException ex) {
			ex.printStackTrace();
			
			if(ex.getErrorCode()==GPErrorCode.REPEAT_BINDING){
				orderNo3=orderNo2;
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
		try {

			QuickPaySmsCodeBean quickPaySmsCodeBean = new QuickPaySmsCodeBean(
					merId, orderNo3, orderDate, transAmount, "", bingingNo);
			quickPaySmsCodeBean.setCurrencyCode("CNY");
			quickPaySmsCodeBean.setTransDesc("交易描述");
			quickPaySmsCodeBean.setPrivObligate("私有内容");
			boolean success =gpPaymentService
					.getQuickPaySmsCode(quickPaySmsCodeBean,srcSubsysId);
			if(success){
				QuickPayUrlTest();
			}
		} catch (HsException ex) {
			fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:"
					+ ex.getMessage());
		}

	}

	/**
	 * 获取快捷支付URL(非首次)
	 * 
	 */
	// @Test
	public void QuickPayUrlTest() {
		try {
			QuickPayBean quickPayBean = new QuickPayBean(merId, orderNo3,
					bingingNo, smsCode);
			System.out.println(gpPaymentService.getQuickPayUrl(quickPayBean,srcSubsysId));

		} catch (HsException ex) {
			fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:"
					+ ex.getMessage());
		}

	}

	/**
	 * 获取手机移动支付TN码
	 * 
	 */
	@Test
	public void MobilePayTnTest() {
		try {
			MobilePayBean mobilePayBean = new MobilePayBean(merId, orderNo4,
					orderDate, transAmount);
			mobilePayBean.setCurrencyCode("CNY");
			mobilePayBean.setTransDesc("交易描述");
			mobilePayBean.setPrivObligate("私有内容");
			System.out.println(gpPaymentService.getMobilePayTn(mobilePayBean,srcSubsysId));
			// 201510210000000046302
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
					"2016050900000000009"));

		} catch (HsException ex) {
			fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:"
					+ ex.getMessage());
		}

	}
}
