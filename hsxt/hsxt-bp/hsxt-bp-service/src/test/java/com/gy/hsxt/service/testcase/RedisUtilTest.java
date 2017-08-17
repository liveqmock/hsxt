/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.service.testcase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.bp.api.IBusinessParamSearchService;
import com.gy.hsxt.bp.bean.BusinessCustParamItemsRedis;
import com.gy.hsxt.bp.common.bean.BPConstants;
import com.gy.hsxt.bp.service.BusinessParamSearchService;

/**
 * 该类用于测试单机redis或主从redis连接方式
 * 
 * @Package:   
 * @ClassName: RedisUtilTest 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-10-20 下午2:29:55 
 * @version V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-global.xml" })
public class RedisUtilTest {

    @Resource(name="fixRedisUtil")
	RedisUtil<String> fixRedisUtil;
    @Resource(name="changeRedisUtil")
	RedisUtil<String> changeRedisUtil;
	
	@Autowired
	public IBusinessParamSearchService businessParamSearchService;
	
	//@Test
	public void add() {
		// 05221081815
//		changeRedisUtil.remove("w_0522108181520151111");
//		changeRedisUtil.remove("m_0522108181520151111");
//		
//		changeRedisUtil.remove("w_0500108181620151111");
//		changeRedisUtil.remove("m_0500108181620151111");
		
		 
		
	}
	
	@SuppressWarnings("unchecked")
    @Test
    public void test() throws java.text.ParseException{
	    String custId = "0618601000620151130";
	    String custParamsRedis;
	    custParamsRedis = changeRedisUtil.get(BPConstants.SYS_NAME, custId, String.class);
	    System.out.println(custParamsRedis);
	    fixRedisUtil.remove(BPConstants.SYS_NAME, custId);
	    custParamsRedis = changeRedisUtil.get(BPConstants.SYS_NAME, custId, String.class);
	    System.out.println(custParamsRedis);
	    changeRedisUtil.remove(BPConstants.SYS_NAME, custId);
	    custParamsRedis = changeRedisUtil.get(BPConstants.SYS_NAME, custId, String.class);
	    System.out.println(custParamsRedis);
	    
	    
	 // 返回的同一客户业务参数Map集合初始化
        Map<String, BusinessCustParamItemsRedis> custParamItemsMap = null;

        
        // 获取redis上的参数
        String custParams = custParamsRedis;

        System.out.println(custParams);
        
        // 判断是否有参数
        if (custParams != null)
        {
            custParamItemsMap = new HashMap<String, BusinessCustParamItemsRedis>();
            JSONObject jsonObject = null;
            jsonObject = JSONObject.parseObject(custParams);
            Map<String, JSONObject> mapString = JSONObject.toJavaObject(jsonObject, Map.class);
            for (String key : mapString.keySet())
            {
                JSONObject jsonMap = mapString.get(key);
                custParamItemsMap.put(key, JSONObject.toJavaObject(jsonMap, BusinessCustParamItemsRedis.class));
            }
        }
        else
        {
            custParamItemsMap = businessParamSearchService.searchCustParamItemsByGroup(custId);
            if(custParamItemsMap == null)
            {
                custParamItemsMap = new HashMap<String, BusinessCustParamItemsRedis>();
            }
        }
	}
}

