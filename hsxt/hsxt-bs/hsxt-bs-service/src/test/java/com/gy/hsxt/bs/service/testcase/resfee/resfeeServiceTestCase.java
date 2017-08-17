/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.service.testcase.resfee;

import java.util.List;

import javax.annotation.Resource;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.bs.api.resfee.IBSResFeeService;
import com.gy.hsxt.bs.bean.resfee.ResFee;
import com.gy.hsxt.bs.bean.resfee.ResFeeRule;
import com.gy.hsxt.bs.bean.resfee.ResFeeTool;
import com.gy.hsxt.bs.resfee.interfaces.IResFeeService;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.StartResType;
import com.gy.hsxt.common.exception.HsException;

/**
 * 资源费测试类
 * 
 * @Package: com.gy.hsxt.bs.service.testcase.resfee
 * @ClassName: resfeeServiceTestCase
 * @Description: TODO
 * 
 * @author: liuhq
 * @date: 2015-10-14 下午8:04:42
 * @version V1.0
 */
@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-global.xml")
public class resfeeServiceTestCase {
	@Resource
	IBSResFeeService resFeeService;

	@Resource
	IResFeeService icResFeeService;

	/**
	 * 创建 资源费方案
	 * 
	 * @param resFee
	 *            实体类 非null
	 * @return 返回true成功，false or Exception失败
	 * @throws HsException
	 */
	@Test
	public void createResFee()
	{
		ResFee resFee = new ResFee();
		resFee.setToCustType(CustType.MEMBER_ENT.getCode());
		resFee.setToBuyResRange(StartResType.FREE_MEMBER_ENT.getCode());
		resFee.setResFeeDesc("免费成员企业源费方案");
		resFee.setPrice("0");
		resFee.setCurrencyCode("HSB");
		resFee.setIncludePrepayAmount("0");
		resFee.setFreeAnnualFeeCnt(-1);
		resFee.setBeginTime("2015-11-12");
		resFee.setEndTime("2017-11-12");
		resFee.setReqOperator("reqOperator");
		resFee.setReqRemark("reqRemark");
		try
		{
			resFeeService.createResFee(resFee);
			System.out.println("操作成功！");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 查询 资源费方案列表
	 * 
	 * @param resFee
	 *            实体类 通过指定bean的属性进行条件查询
	 * @return 返回按条件查询List数据列表
	 * @throws HsException
	 */
	@Test
	public void queryResFeeList()
	{
		ResFee resFee = new ResFee();
		List<ResFee> list = null;
		try
		{
			resFee.setToCustType(2);
			resFee.setToBuyResRange(1);
			resFee.setStatus(1);
			list = resFeeService.queryResFeeList(resFee);
			for (ResFee ent : list)
			{
				System.out.println(ent.getResFeeId());
				System.out.println(ent.getPrice());
				System.out.println(ent.getBeginTime());
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 获取 某一条资源费方案
	 * 
	 * @param resFeeId
	 *            资源费方案编号 必须 非null
	 * @return 返回一条资源费方案记录
	 * @throws HsException
	 */
	@Test
	public void getResFeeBean()
	{
		ResFee resFee = null;
		String resFeeId = "1120151014083544000000";
		try
		{
			resFee = resFeeService.getResFeeBean(resFeeId);
			System.out.println(resFee.getResFeeId());
			System.out.println(resFee.getPrice());
			System.out.println(resFee.getBeginTime());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 审批 资源费方案
	 * 
	 * @param resFee
	 *            实体类 非null
	 * @return 返回true成功，false or Exception失败
	 * @throws HsException
	 */
	@Test
	public void apprResFee()
	{
		ResFee resFee = new ResFee();
		resFee.setResFeeId("110120151224171600000000");
		resFee.setStatus(1);
		resFee.setApprOperator("apprOperator");
		resFee.setApprRemark("apprRemark");
		try
		{
			resFeeService.apprResFee(resFee);
			System.out.println("操作成功！");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 创建 资源费分配规则
	 * 
	 * @param resFeeRule
	 *            实体类 非null
	 * @return 返回true成功，false or Exception失败
	 * @throws HsException
	 */
	@Test
	public void createResFeeRule()
	{
		ResFeeRule resFeeRule = new ResFeeRule();
		resFeeRule.setResFeeId("110120151124031951000000");
		resFeeRule.setAllocTarget(3);
		resFeeRule.setAllocWay(2);
		resFeeRule.setAllocAmount("223900");
		try
		{
			resFeeService.createResFeeRule(resFeeRule);
			System.out.println("操作成功！");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 查询 某一个资源费方案的分配规则
	 * 
	 * @param resFeeId
	 *            资源费方案编号 必须 非null
	 * @return 返回按条件查询的数据List
	 * @throws HsException
	 */
	@Test
	public void queryResFeeRuleList()
	{
		List<ResFeeRule> list = null;
		String resFeeId = "1120151014083544000000";
		try
		{
			list = resFeeService.queryResFeeRuleList(resFeeId);
			for (ResFeeRule ent : list)
			{
				System.out.println(ent.getAllocAmount());
				System.out.println(ent.getAllocWay());
				System.out.println(ent.getResFeeId());
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 获取 某一条分配规则
	 * 
	 * @param allocItemId
	 *            分配规则项编号 非null
	 * @return 返回某一条分配规则记录
	 * @throws HsException
	 */
	@Test
	public void getResFeeRuleBean()
	{
		ResFeeRule resFeeRule = null;
		String allocItemId = "1120151014085742000000";
		try
		{
			resFeeRule = resFeeService.getResFeeRuleBean(allocItemId);
			System.out.println(resFeeRule.getAllocAmount());
			System.out.println(resFeeRule.getAllocWay());
			System.out.println(resFeeRule.getResFeeId());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 创建 资源费包含工具
	 * 
	 * @param resFeeTool
	 *            实体类 非null
	 * @return 返回true成功，false or Exception失败
	 * @throws HsException
	 */
	@Test
	public void createResFeeTool()
	{
		ResFeeTool resFeeTool = new ResFeeTool();
		resFeeTool.setResFeeId("110120151124031951000000");
		resFeeTool.setProductId("0000000005");
		resFeeTool.setQuantity(9999);
		try
		{
			resFeeService.createResFeeTool(resFeeTool);
			System.out.println("操作成功！");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 查询 某一个资源费包含工具
	 * 
	 * @param resFeeId
	 *            资源费方案编号 必须 非null
	 * @return 返回按条件查询的数据List
	 * @throws HsException
	 */
	@Test
	public void queryResFeeToolList()
	{
		List<ResFeeTool> list = null;
		String resFeeId = "1120151014083544000000";
		try
		{
			list = resFeeService.queryResFeeToolList(resFeeId);
			for (ResFeeTool ent : list)
			{
				System.out.println(ent.getProductId());
				System.out.println(ent.getQuantity());
				System.out.println(ent.getResFeeId());
			}
			System.out.println("操作成功！");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 查询 已启用的资源费方案
	 * 
	 * @param toCustType
	 *            被申报企业客户类型 非null
	 * @param toBuyResRange
	 *            被申报企业购买资源段 非null
	 * @return
	 * @throws HsException
	 */
	@Test
	public void getEnableResFee()
	{
		ResFee resFee = null;
		Integer toCustType = 2;
		Integer toBuyResRange = 1;
		try
		{
			resFee = icResFeeService.getEnableResFee(toCustType, toBuyResRange);
			System.out.println(resFee.getResFeeId());
			System.out.println(resFee.getPrice());
			System.out.println(resFee.getCurrencyCode());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
