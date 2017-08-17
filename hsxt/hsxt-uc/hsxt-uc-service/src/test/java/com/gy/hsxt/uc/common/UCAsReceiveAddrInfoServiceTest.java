/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.uc.common;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.uc.as.api.common.IUCAsReceiveAddrInfoService;
import com.gy.hsxt.uc.as.bean.common.AsReceiveAddr;
import com.gy.hsxt.uc.common.mapper.ReceiveAddrMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-uc.xml" })
public class UCAsReceiveAddrInfoServiceTest {

	@Autowired
	IUCAsReceiveAddrInfoService receiveAddrInfoService;
	
	@Autowired
	ReceiveAddrMapper receiveAddrMapper;
	@Test
	public void add(){
		String custId = "0600211814220151207";
		AsReceiveAddr addr = new AsReceiveAddr();
		addr.setAddress("测试地址");
		addr.setArea("are");
		addr.setCountryNo("cn");
		addr.setIsDefault(0);

		addr.setProvinceNo("gd");
		addr.setReceiver("amy");
		addr.setMobile("1581475988");
		addr.setTelphone("074-23093209");
		receiveAddrInfoService.addReceiveAddr(custId, addr);
//		AsReceiveAddr addr1 = new AsReceiveAddr();
//		addr1.setAddress("测试地址ttt");
//		addr1.setArea("areaName1");
//		addr1.setCity("bj");
//		addr1.setCountryNo("cn1");
//		addr1.setIsDefault(1);
//		addr1.setProvinceNo("bj");
//		addr1.setReceiver("test");
//		receiveAddrInfoService.addReceiveAddr(custId, addr1);
	}
	
	//@Test
	public void modify(){
		String custId = "";
		AsReceiveAddr addr = new AsReceiveAddr();
		addr.setAddress("测试地址2222");
		addr.setArea("areaName222");
	//	addr.setCity("cityName222");
		addr.setIsDefault(1);
		addr.setMobile("137222222");
		addr.setReceiver("amy2");
		addr.setAddrId(1161025976316928L);
		receiveAddrInfoService.updateReceiveAddr(custId,  addr);
	}
	
	//@Test
	public void delete(){
		
		//receiveAddrInfoService.deleteReceiveAddr(custId, UserTypeEnum.CARDER,"1161025976316928" );
	}
	
	@Test
	public void list(){
		String custId = "0500108123720151217";
		List<AsReceiveAddr> list = receiveAddrInfoService.listReceiveAddrByCustId(custId);
		for(AsReceiveAddr a : list){
			System.out.println(a.getAddrId());
		}
	}
	@Test
	public void searchReceiveAddrInfo(){
		String custId = "0500108123320151217";
		long addrId = 11L;
		AsReceiveAddr asReceiveAddr = receiveAddrInfoService.searchReceiveAddrInfo(custId, addrId);
	}
	
	@Test
	public void setDefaultReceiveAddr(){
		String custId = "0500108123320151217";
		long addrId = 1L;
		receiveAddrInfoService.setDefaultReceiveAddr(custId, addrId);
	}
	
	@SuppressWarnings("unused")
	@Test
	public void listReceiveAddrByCustId(){
		String custId = "0618601000620151130";
		List<AsReceiveAddr> list = receiveAddrInfoService.listReceiveAddrByCustId(custId);
	}
	
	@Test
	public void searchDefaultReceiveAddrInfo(){
		String custId = "0500108123720151217";
		AsReceiveAddr  receiveAddr  = receiveAddrInfoService.searchDefaultReceiveAddrInfo(custId);
	}
}
