package com.gy.hsxt.ps.points.serivce;

import com.gy.hsxt.ps.common.PsRedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * @description 消费积分测试用例
 * @author chenhz
 * @createDate 2015-7-30 上午10:32:30
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisTestCase extends BaseTestCase  {

	@Test
	public void test3() {

		List<String> entResNoSet= new ArrayList<>();
		entResNoSet.add("06002110000");
		entResNoSet.add("00000000156");
		entResNoSet.add("06002000000");
		entResNoSet.add("06000000000");

		 Map mapCustId=PsRedisUtil.getCustIdMap(entResNoSet);

		System.out.println(mapCustId+"===============================");


		//List<Object> lists= PsRedisUtil.getCustIdList(entResNoSet);

		//System.out.println(lists+"===============================");


		Map mapTax=PsRedisUtil.getEntTaxMap(entResNoSet);
		System.out.println(mapTax+"===============================");
	}
}
