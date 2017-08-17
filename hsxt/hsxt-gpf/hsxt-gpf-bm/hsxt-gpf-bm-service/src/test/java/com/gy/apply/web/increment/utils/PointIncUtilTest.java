package com.gy.apply.web.increment.utils;

import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.gpf.bm.utils.PointIncUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PointIncUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetBmlmOutValue() {
		String pointREF = "100";
		String baseVal = "801.81";
		String pvIncrement = PointIncUtil.getBmlmOutValue(pointREF, baseVal);
		System.out.println(pvIncrement);

		double base = BigDecimalUtils.floor(baseVal, 0).doubleValue();
		System.out.println(base);
	}

}
