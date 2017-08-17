/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.es.reconciliation.service;

import com.gy.hsxt.es.distribution.service.IPointAllocService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.common.exception.HsException;

/** 
 * @Package: com.gy.hsxt.ps.reconciliation.service  
 * @ClassName: EcOrderTest 
 * @Description: TODO
 *
 * @author: chenhongzhi 
 * @date: 2015-10-21 下午5:07:47 
 * @version V3.0  
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/spring-global.xml" })
public class EcOrderTest
{
	@Autowired
	private IPointAllocService pointAllocService;
	/**
	 * 电商通过文件批量结算
	 */
	@Test
	public void settlement(){
	}


}
