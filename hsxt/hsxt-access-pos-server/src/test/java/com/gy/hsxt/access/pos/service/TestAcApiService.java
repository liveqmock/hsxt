package com.gy.hsxt.access.pos.service;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.dubbo.common.json.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ac.bean.AccountBalance;
		
/***************************************************************************
 * <PRE>
 * Description 		: Simple to Introduction
 *
 * Project Name   	: hsxt-access-pos-api
 *
 * Package Name     : com.gy.hsxt.access.pos.service
 *
 * File Name        : TestAcApiService
 * 
 * Author           : wucl
 *
 * Create Date      : 2015-10-28 下午4:33:47  
 *
 * Update User      : wucl
 *
 * Update Date      : 2015-10-28 下午4:33:47
 *
 * UpdateRemark 	: 说明本次修改内容
 *
 * Version          : v0.0.1
 *
 * </PRE>
 ***************************************************************************/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext.xml"})
public class TestAcApiService {
	@Autowired
	@Qualifier("acApiService")
	private AcApiService acApiService;
	
	@Before  
    public void setUp() throws Exception { 
    }  
  
    @After  
    public void tearDown() throws Exception {  
    } 
    
    @Test
    public void testPoint()throws Exception{
    	
    	try{
    		
    	    
    	    
    		AccountBalance r = acApiService.searchAccBalance("6186115648", "10110");
        	
        	System.out.println(JSON.json(r));
        	SystemLog.info("com.gy.hsxt.access.pos.service", "testPoint", JSON.json(r));
        	System.out.println("-----------------");
        	
        	Assert.assertEquals("10110", r.getAccType());
        	assertEquals("5", r.getAccBalance());
        	
        	AccountBalance r2 = acApiService.searchAccNormal("6186115648", "10110");
        	
        	System.out.println(JSON.json(r2));
        	System.out.println("-----------------");
        	Assert.assertEquals("10110", r2.getAccType());
        	assertEquals("5", r2.getAccBalance());
        	
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}

	