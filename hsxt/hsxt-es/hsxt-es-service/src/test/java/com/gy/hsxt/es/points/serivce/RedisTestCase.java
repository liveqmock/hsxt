package com.gy.hsxt.es.points.serivce;

//import com.alibaba.dubbo.common.json.JSON;
//import com.gy.hsxt.es.bean.Point;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.math.BigDecimal;
//import java.sql.Timestamp;
//import java.util.*;

/**
 * @description 消费积分测试用例
 * @author chenhz
 * @createDate 2015-7-30 上午10:32:30
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
//@RunWith(SpringJUnit4ClassRunner.class)
public class RedisTestCase extends BaseTestCase  {

	
/*	*//*******************************************  积分测试用例   ****************************************//*
	*//**
	 *  持卡人本地消费
	 *//*
	@Test
	public void redisTest(){
		System.out.println(System.getProperty("user.dir"));

		try {
			boolean success = RedisClient.set("a", "jjjjjjjjjjjjjjjjjjjjjjj");
			System.out.println(success);
			System.out.println(RedisClient.get("a"));
			boolean success2 = ShardingRedisClient.set("a", "kkkkkkkkkkkk");
			System.out.println(success2);
			System.out.println(ShardingRedisClient.get("a"));

			PointDetail detail = new PointDetail();
			detail.setUpdatedBy("00000000000_admin");
			detail.setEntPoint(new BigDecimal(2000.00));
			detail.setCreatedDate(new Timestamp(new Date().getTime()));

			RedisClient.set(RedisClient.class.getSimpleName(), detail);
			System.out.println(RedisClient.get(RedisClient.class.getSimpleName()));

			ShardingRedisClient.set(RedisClient.class.getSimpleName(), detail);
			System.out.println(ShardingRedisClient.get(RedisClient.class.getSimpleName()));

			List<String> strList = new ArrayList<String>();
			strList.add("a");
			strList.add("a");
			strList.add("a");
			strList.add("a");
			ShardingRedisClient.set(ArrayList.class.getSimpleName(), strList);
			System.out.println(ShardingRedisClient.get(ArrayList.class.getSimpleName()));

			Map<String, String> map = new HashMap<>();

			map.put("a", "aaaa");
			map.put("b", "bbbb");
			map.put("c", "ccccc");
			map.put("f", "fffff");

//			ShardingRedisClient.setSpareSyncPipes(map);
			ShardingRedisClient.setSyncPipes(map);
			System.out.println(ShardingRedisClient.get("f"));



		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/

}
