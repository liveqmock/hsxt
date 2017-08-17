/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.service.testcase.tool;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.common.StringUtil;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.tool.bean.ApplyOrderConfig;
import com.gy.hsxt.bs.tool.interfaces.IInsideInvokeCall;
import com.gy.hsxt.common.exception.HsException;

@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global_all.xml")
/**
 * 公共测试类
 * 
 * @Package: com.gy.hsxt.bs.service.testcase.tool
 * @ClassName: CommonTest
 * @Description: TODO
 * @author: likui
 * @date: 2015年11月11日 下午5:20:25
 * @company: gyist
 * @version V3.0.0
 */
public class CommonTest {

	@Autowired
	ICommonService commonService;

	@Autowired
	IInsideInvokeCall insideInvokeCall;

	@Test
	public void getAreaPlatInfo()
	{
		System.out.println("================" + commonService.getAreaPlatInfo());
	}

	@Test
	public void getAreaPlatCustId()
	{
		System.out.println("================" + commonService.getAreaPlatCustId());
	}

	@Test
	public void getAreaPlatProvince()
	{
		System.out.println("================" + commonService.getAreaPlatProvince());
	}

	@Test
	public void getCityByProvinceNo()
	{
		System.out.println(commonService.getCityByProvinceNo("44"));
	}

	@Test
	public void getEntAllInfoToUc()
	{
		System.out.println(JSON.toJSON(commonService.getEntAllInfoToUc("", "0600102000020151215")));
	}

	@Test
	public void getEntMainInfoToUc()
	{
		System.out.println(JSON.toJSON(commonService.getEntMainInfoToUc("06001020000", "")));
	}

	@Test
	public void getEntBaseInfoToUc()
	{
		System.out.println(JSON.toJSON(commonService.getEntBaseInfoToUc("", "0600102000020151215")));
	}

	@Test
	public void getEntStatusInfoToUc()
	{
		System.out.println(JSON.toJSONString(commonService.getEntStatusInfoToUc("06001020000", "")));
	}

	@Test
	public void queryNotFinishToolOrder()
	{
		System.out.println(insideInvokeCall.queryNotFinishToolOrder("0600102000020151215"));
	}

	// 申报工具下单
	@Test
	public void addApplyOrderToolConfig()
	{
		String operNo = "likui";
		ApplyOrderConfig bean = new ApplyOrderConfig();
		bean.setOrderNo(StringUtil.getDateTime());
		bean.setOperNo(operNo);
		bean.setProvinceNo("44");
		bean.setResFeeId("");
		try
		{
			long start = System.currentTimeMillis();
			insideInvokeCall.addApplyOrderToolConfig(bean);
			System.out.println("工具内部下单消耗时间：" + (System.currentTimeMillis() - start));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 测试同步卡数据到UC
	@Test
	public void testSyncCardInfoToUc()
	{
		try
		{
			long start = System.currentTimeMillis();
			insideInvokeCall.testSyncCardInfoToUc("110120151126040132000002", "06001010000",
					"06001010000163521987508224");
			System.out.println("同步消费者数据到UC消耗时间：" + (System.currentTimeMillis() - start));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}
}
