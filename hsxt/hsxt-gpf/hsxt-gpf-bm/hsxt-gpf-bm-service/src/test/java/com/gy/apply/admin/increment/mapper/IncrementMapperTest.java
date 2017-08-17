package com.gy.apply.admin.increment.mapper;

import java.util.Map;

import com.gy.hsxt.gpf.bm.bean.IncNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.gy.hsxt.gpf.bm.mapper.IncNodeMapper;
		
/**   
 * Simple to Introduction
 * @category          Simple to Introduction
 * @projectName   apply-incurement
 * @package           com.gy.apply.admin.increment.mapper.IncrementMapperTest.java
 * @className       IncrementMapperTest
 * @description      一句话描述该类的功能 
 * @author              zhucy
 * @createDate       2014-7-3 下午5:48:26  
 * @updateUser      zhucy
 * @updateDate      2014-7-3 下午5:48:26
 * @updateRemark 说明本次修改内容
 * @version              v0.0.1
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-global.xml")
public class IncrementMapperTest {
	
	@Autowired
	private IncNodeMapper incNodeMapper;
	
//	@Test
	public void getRowByLevelMap() throws Exception{
		Map<String,IncNode> map = incNodeMapper.getRowByLevelMap("8");
		System.out.println(map.size());
	}
}

	