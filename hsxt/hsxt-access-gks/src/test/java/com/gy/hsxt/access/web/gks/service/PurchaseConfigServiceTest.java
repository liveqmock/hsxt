/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.gks.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.access.web.gks.bean.PmkSecretKeyParam;
import com.gy.hsxt.bs.bean.tool.resultbean.DeviceTerminalNo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-consumer.xml" })
public class PurchaseConfigServiceTest {

	@Autowired
	IPurchaseConfigService purchaseConfigService;
	

	 @Test
	public void queryConifgDeviceTerminalNo(){
	 // DeviceTerminalNo no =	purchaseConfigService.queryConifgDeviceTerminalNo("06002110000164063559693312", "110120151202070553000000", "P_POS");
	  
	  System.out.println("-----------" );
	}
	
	//@Test
	public void genGmk(){
		PmkSecretKeyParam pskb = new PmkSecretKeyParam();
		pskb.setConfNo("110120151202070553000000");
		pskb.setDeviceNo("060021100000009");
		pskb.setEntCustId("06002110000164063559693312");
		pskb.setMachineNo("37002107773");
		pskb.setOperCustId("06002110000164063559726080");
		purchaseConfigService.createPosPMK(pskb);
	}
}
