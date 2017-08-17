package com.gy.apply.web.increment.service.impl;

import com.gy.hsxt.gpf.bm.service.PointValueService;
import com.gy.hsxt.gpf.bm.utils.TimeUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-global.xml")
public class PointValueServiceImplTest {
	@Autowired
	private PointValueService pointValueService;

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testFindByRowKeyRange() throws Exception{
		TimeUtil tt = new TimeUtil();
		String startRowKey = tt.getMondayOFWeek();
		String endRowKey = tt.getCurrentWeekday();
//
//		Collection<PointValue> collection = pointValueService.findByRowKeyRange(startRowKey, endRowKey);
//		Iterator<PointValue> ite = collection.iterator();
//
//		while(ite.hasNext()) {
//			PointValue pv = ite.next();
//			System.out.println(pv.getResNo()+":"+pv.getPv());
//		}
		
	}

}
