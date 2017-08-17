package com.gy.hsxt.service.testcase;

import java.util.Arrays;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.common.constant.BankCardType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.pg.bean.PGMobilePayTnBean;
import com.gy.hsxt.pg.constant.PGConstant.MerType;
import com.gy.hsxt.pg.service.impl.PGPaymentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/spring-global.xml" })
public class ChinapayTestCase {

	@Autowired
	private PGPaymentService paymentService;

	@Test
	public void qrueryPayment() throws HsException {
		System.out.println(paymentService.getPaymentOrderState(100,
				Arrays.asList(new String[] { "9010160404111551" })));
	}
	
	@Test
	public void qrueryBindingNo() throws HsException {
		System.out.println(paymentService.getQuickPayBindingNo(
				"6221558812340000", BankCardType.CREDIT_CARD.getCode(), "0019"));
	}
	
	@Test
	public void getTn() throws HsException {
		
		for (int i = 39; i < 41; i++) {
			PGMobilePayTnBean mobilePayTnBean = new PGMobilePayTnBean();
			mobilePayTnBean.setBankOrderNo("201605020000000000"+i);
			mobilePayTnBean.setMerType(MerType.HSXT);
			mobilePayTnBean.setTransAmount("0.9");
			mobilePayTnBean.setTransDate(new Date());
			
			System.out.println(paymentService.getMobilePayTn(mobilePayTnBean));
		}
		
//		PGMobilePayTnBean mobilePayTnBean = new PGMobilePayTnBean();
//		mobilePayTnBean.setBankOrderNo("20160427000000000007");
//		mobilePayTnBean.setMerType(MerType.HSXT);
//		mobilePayTnBean.setTransAmount("0.9");
//		mobilePayTnBean.setTransDate(new Date());
//		
//		System.out.println(paymentService.getMobilePayTn(mobilePayTnBean));
	}
}
