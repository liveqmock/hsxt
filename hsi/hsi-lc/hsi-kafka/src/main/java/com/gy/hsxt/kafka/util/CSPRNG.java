/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.kafka.util;

import java.security.SecureRandom;

import org.apache.log4j.Logger;

/**
 * 生成随机数，主要用于提供CSPR的随机数
 * 
 * @Package: com.gy.hsxt.uc.util  
 * @ClassName: CSPRNG 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-10-20 下午4:13:07 
 * @version V1.0
 */
public class CSPRNG {
	private static final SecureRandom RANDOM = new SecureRandom();

	/**
	 * 生成随机数字（不包括字符），长度等于len
	 * @param len
	 * @return
	 */
	public static String generateRandom(int len) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len; i++) {
			sb.append(Math.abs(RANDOM.nextInt(10)));
		}
		return sb.toString();

	}
	
	public static void main(String[] args){
		String s = CSPRNG.generateRandom(10);
		System.out.println(s);
	}
}
