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
import com.gy.hsxt.bs.api.tool.IBSToolProductService;
import com.gy.hsxt.bs.bean.base.ApprInfo;
import com.gy.hsxt.bs.bean.base.Operator;
import com.gy.hsxt.bs.bean.tool.CardStyle;
import com.gy.hsxt.bs.bean.tool.ToolProduct;
import com.gy.hsxt.bs.bean.tool.ToolProductUpDown;
import com.gy.hsxt.bs.bean.tool.pageparam.ToolProductVo;
import com.gy.hsxt.bs.common.StringUtil;
import com.gy.hsxt.bs.common.enumtype.tool.CardStyleApprStatus;
import com.gy.hsxt.bs.common.enumtype.tool.CategoryCode;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-global.xml")
// @ContextConfiguration(locations = "classpath:test/spring-global.xml")
/**
 * 工具产品测试类
 * 
 * @Package: com.gy.hsxt.bs.service.testcase
 * @ClassName: ToolProductTest
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月9日 下午6:44:55
 * @company: gyist
 * @version V3.0.0
 */
public class ToolProductTest {

	@Autowired
	IBSToolProductService toolProductService;

	String operNo = "likui";

	String optName = "likui";

	// 查询所有的工具
	@Test
	public void queryToolCategoryAll()
	{
		System.out.println(JSON.toJSON(toolProductService.queryToolCategoryAll()));

	}

	// 添加工具
	// @Test
	public void addToolProduct()
	{
		ToolProduct bean = new ToolProduct();
		bean.setCategoryCode(CategoryCode.NORMAL.name());
		bean.setProductName("测试工具产品02");
		bean.setMicroPic("123456");
		bean.setPrice("100.00");
		bean.setUnit("个");
		bean.setWarningValue(500);
		bean.setDescription("测试工具产品02");
		bean.setOptId(operNo);
		bean.setOptName(optName);
		toolProductService.addToolProduct(bean);
		System.out.println(bean);
	}

	// 修改工具
	@Test
	public void updateToolProduct()
	{
		ToolProduct bean = new ToolProduct();
		bean.setProductId("0000100452");
		bean.setProductName("测试工具产品02333333");
		bean.setMicroPic("123456");
		bean.setWarningValue(500);
		bean.setDescription("测试工具产品0233333333333333333");
		toolProductService.updateToolProduct(bean);
		System.out.println(bean);
	}

	// 根据id查询工具产品
	@Test
	public void queryToolProductById()
	{
		System.out.println(JSON.toJSONString(toolProductService.queryToolProductById("0000000020")));
	}

	// 分页查询工具产品
	@Test
	public void queryToolProductPage()
	{
		ToolProductVo bean = new ToolProductVo();
		// bean.setExeCustId("2015101221");
		PageData<ToolProduct> data = toolProductService.queryToolProductPage(bean, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 工具下架申请
	// @Test
	public void applyToolToDown()
	{
		ToolProduct bean = toolProductService.queryToolProductById("0000000020");
		try
		{
			Operator opt = new Operator();
			opt.setOptId("yangjg");
			opt.setOptName("yangjg");
			toolProductService.applyToolProductToDown(bean.getProductId(), "测试下架申请", opt);
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 工具价格审批
	// @Test
	public void apprToolUp()
	{
		ApprInfo apprInfo = new ApprInfo();
		apprInfo.setApplyId("110120160304144747000000");
		apprInfo.setApprRemark("审批工具上架");
		apprInfo.setOptId(operNo);
		apprInfo.setOptName(optName);
		apprInfo.setPass(true);
		toolProductService.apprToolProductForUp(apprInfo);
	}

	// 工具下架审批
	// @Test
	public void apprToolDown()
	{
		ApprInfo apprInfo = new ApprInfo();
		apprInfo.setApplyId("110120160304145218000000");
		apprInfo.setApprRemark("审批工具下架");
		apprInfo.setOptId(operNo);
		apprInfo.setOptName(optName);
		apprInfo.setPass(true);
		toolProductService.apprToolProductForDown(apprInfo);
	}

	// 查询所有工具或根据类别查询工具
	@Test
	public void queryToolProduct()
	{
		System.out.println(JSON.toJSONString(toolProductService.queryToolProduct(CategoryCode.P_CARD.name())));
	}

	// 查询所有工具或根据类别查询工具
	@Test
	public void selectToolProductNotByStatus()
	{
		System.out.println(JSON.toJSONString(toolProductService.selectToolProductNotByStatus(CategoryCode.P_POS.name())));
	}

	// 新增价格变更
	// @Test
	public void addToolPriceChange()
	{
		try
		{
			Operator operator = new Operator();
			operator.setOptId(operNo);
			operator.setOptName(optName);
			toolProductService.applyChangePrice("0000000009", "0.1", operator);
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 根据id查询工具上下架记录
	@Test
	public void queryToolProductUpDownById()
	{
		System.out
				.println(JSON.toJSONString(toolProductService.queryToolProductUpDownById("110120160304145218000000")));
	}

	// 分页查询工具价格变更
	@Test
	public void queryToolUpForApprPage()
	{
		PageData<ToolProductUpDown> data = toolProductService.queryToolUpForApprListPage("工具", null, null,
				new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 分页查询下架审批
	@Test
	public void queryToolDownForApprPage()
	{
		PageData<ToolProductUpDown> data = toolProductService.queryToolDownForApprListPage("工具", null, null,
				new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 新增互生卡样
	// @Test
	public void addCardStyle()
	{
		CardStyle bean = new CardStyle();
		bean.setCardStyleName("互生卡样三号");
		bean.setMicroPic(StringUtil.getDateTime());
		bean.setSourceFile(StringUtil.getDateTime());
		bean.setIsDefault(true);
		bean.setEntResNo("00000000156");
		bean.setEntCustId("0000000015620151015");
		bean.setReqOperator(operNo);
		bean.setReqRemark("互生卡样三号");
		try
		{
			toolProductService.addCardStyle(bean);
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 根据id查询互生卡样
	@Test
	public void queryCardStyleById()
	{
		System.out.println(JSON.toJSONString(toolProductService.queryCardStyleById("1120151012095519000000")));
	}

	// 互生卡样的启用或停用
	@Test
	public void cardStyleEnableOrStop()
	{
		CardStyle bean = toolProductService.queryCardStyleById("110120151030040651000000");
		bean.setStatus(CardStyleApprStatus.APP_STOP.getCode());
		bean.setReqOperator(operNo);
		bean.setReqRemark("互生卡样申请停用");
		try
		{
			toolProductService.cardStyleEnableOrStop(bean);
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 修改标准卡样为默认卡样
	@Test
	public void updateCardStyleToDefault()
	{
		try
		{
			toolProductService.updateCardStyleToDefault("1120151012113936000000");
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 互生卡样审批
	@Test
	public void cardStyleAppr()
	{
		CardStyle bean = toolProductService.queryCardStyleById("110120151124105910000000");
		bean.setStatus(CardStyleApprStatus.ENABLE.getCode());
		bean.setApprOperator(operNo);
		bean.setApprRemark("互生卡样启用审批通过");
		bean.setExeCustId("0000000015620151015");
		try
		{
			toolProductService.cardStyleAppr(bean);
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 分页查询互生卡样
	@Test
	public void queryCardStylePage()
	{
		PageData<CardStyle> data = toolProductService.queryCardStylePage(null, null, new Page(1, 10));

		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}
}
