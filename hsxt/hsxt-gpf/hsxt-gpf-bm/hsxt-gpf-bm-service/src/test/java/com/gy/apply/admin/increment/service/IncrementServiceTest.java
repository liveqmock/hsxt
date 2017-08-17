package com.gy.apply.admin.increment.service;

import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.gpf.bm.bean.Increment;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.gy.hsxt.gpf.bm.service.IncrementService;

/**   
 * 增值节点测试 
 * @category          增值节点测试 
 * @projectName   apply-incurement
 * @package           com.gy.apply.admin.increment.service.IncrementServiceTest.java
 * @className       IncrementServiceTest
 * @description     增值节点测试 
 * @author              zhucy
 * @createDate       2014-7-21 下午1:09:34  
 * @updateUser      zhucy
 * @updateDate      2014-7-21 下午1:09:34
 * @updateRemark    新增
 * @version              v0.0.1
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-global.xml")
public class IncrementServiceTest {
	
	@Autowired
	private IncrementService incrementService;
	
//	@Test
	public void filterByResNo() throws Exception{
		Increment ict = incrementService.getChildrenTB("01001000000");
		Assert.assertNotNull(ict);
	}
}

	