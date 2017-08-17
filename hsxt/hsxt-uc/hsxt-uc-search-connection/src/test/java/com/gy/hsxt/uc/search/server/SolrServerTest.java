/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.uc.search.server;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试solr连接
 * 
 * @Package: java.com.gy.hsxt.uc.search.server  
 * @ClassName: SolrServerContextTest 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-11-19 上午10:58:06 
 * @version V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-solr-server.xml" })
public class SolrServerTest {

	@Autowired
	SolrServerContext ctx;
	
	@Test
	public void connect(){
		UpdateData data = new UpdateData("custId");
		data.addData(new Data("mobile","40",false));
		data.addData(new Data("custId","123456789",false));
		
		List<UpdateData> list = new ArrayList<UpdateData>();
		list.add(data);
		try{
			//ctx.update("userInfo", list);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
