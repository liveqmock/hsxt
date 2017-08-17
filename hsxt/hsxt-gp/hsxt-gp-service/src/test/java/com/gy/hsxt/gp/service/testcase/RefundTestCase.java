package com.gy.hsxt.gp.service.testcase;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gp.service.impl.RefundService;

/**
 * @author 作者 : zhangysh
 * @ClassName: 类名:GPPaymentServiceTestCase
 * @Description: 描述 :异常重复支付自动退款测试类
 * @createDate 创建时间: 2015-10-14 上午11:02:40
 * @version 3.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/spring-global.xml" })
public class RefundTestCase {

	@Autowired
	public RefundService refundService;

	/**
	 * 自动退款测试类
	 */

	@Test
	public void RefundTest() {
		try {
			long start = System.currentTimeMillis();
			refundService.refund();
			System.out.println(System.currentTimeMillis() - start);

		} catch (HsException ex) {
			fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:"
					+ ex.getMessage());
		}
	 	
	}
}
