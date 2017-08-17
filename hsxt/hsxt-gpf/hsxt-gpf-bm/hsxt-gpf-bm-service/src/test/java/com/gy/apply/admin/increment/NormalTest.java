package com.gy.apply.admin.increment;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**   
 * 赠送积分测试类
 * @category          赠送积分测试类
 * @projectName   apply-incurement
 * @package           com.gy.apply.admin.increment.PointValueTest.java
 * @className       PointValueTest
 * @description     赠送积分测试类
 * @author              zhucy
 * @createDate       2014-6-24 上午11:56:10  
 * @updateUser      zhucy
 * @updateDate      2014-6-24 上午11:56:10
 * @updateRemark    新增
 * @version              v0.0.1
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:spring/spring-global.xml")
public class NormalTest {

	@Test
	public void mapTest() throws Exception {

		Map<String, Map<String, String>> superMap = new HashMap<>();

		Map<String, String> childMap = superMap.get("child");
		if (childMap == null) {
			childMap = new HashMap<>();
			superMap.put("child", childMap);
		}

		childMap.put("key", "value");

		System.out.println(superMap.get("child").get("key"));

		for (final Map<String, String> map : superMap.values()) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					map.put("key", "myValue");
					System.out.println("thread:"+map.get("key"));
				}
			}).start();
		}

		Thread.sleep(2000);
		System.out.println(superMap.get("child").get("key"));



	}

	@Test
	public void threadTest() throws Exception {
//		final Map<String, Object> map = new HashMap<>();
//
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				map.put("a", "A");
//			}
//		}).start();
//
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				map.put("b", "B");
//			}
//		}).start();
//
//		Thread.sleep(5000);
//		System.out.println(map);

//		String content = "0100200000,102";
//		int index = content.indexOf(",");
//		String resNo = content.substring(0, index);
//		String point = content.substring(index+1);
//
//		System.out.println(resNo);
//		System.out.println(point);
//
//		NfsBean nfsBean = new NfsBean();
//		System.out.println(nfsBean.buildBmlmDownloadAddress("011"));

		String str = "01001000000";
		boolean b = !StringUtils.endsWithAny(str, "L", "R");

		System.out.println(b);
	}

	@Test
	public void collectionTest() {
		ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
		queue.add("a");
		queue.add("b");
		for (String s : queue) {
			System.out.println(s);
		}
		System.out.println("poll:"+queue.poll());
		for (String s : queue) {
			System.out.println(s);
		}
	}
}

	