/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.uid.uid.service;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.zookeeper.ClientCnxn;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-uid.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UidServiceTest {

	@Resource
	UidService uidService;
	
	
	@Test
	public void testUid(){
		System.out.println("HHHHHHHHHHHHHHHHHHHHHH");
		ArrayList hs=new ArrayList();
		Long ret=null;
		for(int i=0;i<11;i++){
			ret=getUid("12345678904");
			hs.add(ret);
		}
		System.out.println("ret="+hs);
		System.out.println("HHHHHHHHHHHHHHHHHHHHHH");
		
	}
	
	Long getUid(String entResNo)
	{
		Long ret= uidService.getUid(entResNo);
		return ret;
		
//		System.out.println("ret="+ret);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print(String.format("%011d", 1));
		ClientCnxn a;

	}

}
