/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos  
 * @ClassName: MainDeploy 
 * @Description: 启动类
 *
 * @author: wucl 
 * @date: 2015-11-10 下午2:56:11 
 * @version V1.0
 */
public class MainDeploy {

	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] { "spring/applicationContext.xml" });
			NettyServer nettyServer = ctx.getBean("nettyServer",NettyServer.class);
			nettyServer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
