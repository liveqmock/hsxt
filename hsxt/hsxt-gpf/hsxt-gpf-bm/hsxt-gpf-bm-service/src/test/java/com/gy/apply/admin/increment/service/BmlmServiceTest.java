package com.gy.apply.admin.increment.service;

import java.util.HashMap;
import java.util.Map;

import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.gpf.bm.bean.Bmlm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.gy.hsxt.gpf.bm.service.BmlmService;
		
/**   
 * 再增值测试（计算积分参考值、基数）
 * @category          再增值测试（计算积分参考值、基数）
 * @projectName   apply-incurement
 * @package           com.gy.apply.admin.increment.service.BmlmServiceTest.java
 * @className       BmlmServiceTest
 * @description      再增值测试（计算积分参考值、基数）
 * @author              zhucy
 * @createDate       2014-7-4 下午1:10:35  
 * @updateUser      zhucy
 * @updateDate      2014-7-4 下午1:10:35
 * @updateRemark    新增
 * @version              v0.0.1
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-global.xml")
public class BmlmServiceTest {
	
	@Autowired
	private BmlmService bmlmService;
	
	/**
	 * 再增值测试（计算积分参考值、基数）
	 */
//	@Test
	public void calc(){
		Map<String,Bmlm> parMap = new HashMap<String,Bmlm>();
		
		Bmlm bmlm = new Bmlm();
		bmlm.setPoint("100");
		bmlm.setType(CustType.TRUSTEESHIP_ENT.getCode());
		parMap.put("01001000000", bmlm);
		
		
		bmlm = new Bmlm();
		bmlm.setPoint("99999");
		bmlm.setType(CustType.TRUSTEESHIP_ENT.getCode());
		parMap.put("01001010000", bmlm);
		
		bmlm = new Bmlm();
		bmlm.setPoint("12345");
		bmlm.setType(CustType.MEMBER_ENT.getCode());
		parMap.put("01001000001", bmlm);
		
		
		bmlm = new Bmlm();
		bmlm.setPoint("100");
		bmlm.setType(CustType.TRUSTEESHIP_ENT.getCode());
		parMap.put("01002010000", bmlm);
		
		
		bmlm = new Bmlm();
		bmlm.setPoint("9");
		bmlm.setType(CustType.SERVICE_CORP.getCode());
		parMap.put("01003000000", bmlm);
		
		
		//bmlmService.calcBmlmPv(parMap);
	}
}

	