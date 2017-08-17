/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.service.testcase.tool;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.api.tool.IBSToolSendService;
import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.tool.DeliveryCorp;
import com.gy.hsxt.bs.bean.tool.Shipping;
import com.gy.hsxt.bs.bean.tool.ShippingMethod;
import com.gy.hsxt.bs.bean.tool.ToolConfig;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolConfigPage;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolShippingPage;
import com.gy.hsxt.bs.common.StringUtil;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.bs.common.enumtype.tool.CategoryCode;
import com.gy.hsxt.bs.common.enumtype.tool.ShippingType;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;
import com.gy.hsxt.uc.bs.bean.ent.BsEntAllInfo;

@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-global.xml")
// @ContextConfiguration(locations = "classpath:test/spring-global.xml")
/**
 * 工具发货测试类
 * 
 * @Package: com.gy.hsxt.bs.service.testcase.tool
 * @ClassName: ToolSendTest
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月20日 下午6:01:15
 * @company: gyist
 * @version V3.0.0
 */
public class ToolSendTest {

	String operNo = "likui";

	String custId = "0600102000020151215";

	@Autowired
	private IBSToolSendService toolSendService;

	/** 用户中心企业Service **/
	@Autowired
	private IUCBsEntService bsEntService;

	// 添加配送方式
	@Test
	public void addShippingMethod()
	{
		ShippingMethod bean = new ShippingMethod(null, "慢速", StringUtil.getDateTime(), 1, "慢速");
		bean.setCreatedBy(operNo);
		try
		{
			toolSendService.addShippingMethod(bean);
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 查询配送方式
	@Test
	public void queryShippingMethodById()
	{
		System.out.println(JSON.toJSONString(toolSendService.queryShippingMethodById("1120151020062022000000")));
	}

	// 修改配送方式
	@Test
	public void modifyShippingMethod()
	{
		ShippingMethod bean = toolSendService.queryShippingMethodById("1120151020062022000000");
		bean.setSmName("超速01");
		bean.setSmDesc("超速01");
		bean.setSort(2);
		bean.setUpdatedBy(operNo);
		try
		{
			toolSendService.modifyShippingMethod(bean);
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 删除配送方式
	@Test
	public void removeShippingMethod()
	{
		try
		{
			toolSendService.removeShippingMethod("1120151020062022000000");
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 分页查询配送方式
	@Test
	public void queryShippingMethodByPage()
	{
		PageData<ShippingMethod> data = toolSendService.queryShippingMethodByPage("", new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 添加快递公司
	@Test
	public void addDeliveryCorp()
	{
		DeliveryCorp bean = new DeliveryCorp();
		bean.setCorpName("申通快递公司");
		bean.setCorpCode("A556");
		bean.setCorpUrl("http://shentong.com");
		bean.setLinkMan(operNo);
		bean.setPhone("13523654512");
		bean.setAddr("深圳市福田区");
		bean.setOfficePhone("0755-12398165");
		bean.setDescription("申通快递公司");
		bean.setSort(1);
		bean.setCreatedBy(operNo);
		try
		{
			toolSendService.addDeliveryCorp(bean);
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 查询快递公司
	@Test
	public void queryDeliveryCorpById()
	{
		System.out.println(JSON.toJSONString(toolSendService.queryDeliveryCorpById("1120151020065002000000")));
	}

	// 修改快递公司
	@Test
	public void modifyDeliveryCorp()
	{
		DeliveryCorp bean = toolSendService.queryDeliveryCorpById("1120151020065002000000");
		bean.setCorpName("XXX快递公司");
		bean.setCorpCode("1234");
		bean.setCorpUrl("http://1sdf231.com");
		bean.setPhone("13523ss124512");
		bean.setAddr("XXXXXXXXXXXXrrXXXXXXXXXX");
		bean.setOfficePhone("05s77-123461");
		bean.setDescription("XXXXXssXXXXXXXXXXXx");
		bean.setSort(2);
		bean.setUpdatedBy(operNo);
		try
		{
			toolSendService.modifyDeliveryCorp(bean);
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 删除快递公司
	@Test
	public void removeDeliveryCorp()
	{
		try
		{
			toolSendService.removeDeliveryCorp("1120151020065002000000");
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 分页查询快递公司
	@Test
	public void queryDeliveryCorpByPage()
	{
		PageData<DeliveryCorp> data = toolSendService.queryDeliveryCorpByPage(null, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 分页查询工具配置单(生成发货单)--新增申购
	@Test
	public void queryToolConfigDeliveryPage()
	{
		BaseParam bean = new BaseParam();
		// bean.setStartDate("2015-10-19");
		// bean.setEndDate("2015-10-19");
		// bean.setHsResNo("06186010000");
		bean.setRoleIds(new String[] { "603", "HS20151210", "HS20151211" });
		PageData<ToolConfigPage> data = toolSendService.queryToolConfigDeliveryPage(bean, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 分页查询工具配送单(生成发货单)申报申购
	@Test
	public void queryToolConfigDeliveryAppPage()
	{
		BaseParam bean = new BaseParam();
		// bean.setStartDate("2015-10-19");
		// bean.setEndDate("2015-10-19");
		// bean.setHsResNo("06186010000");
		bean.setRoleIds(new String[] { "603", "HS20151210", "HS20151211" });
		PageData<ToolConfigPage> data = toolSendService.queryToolConfigDeliveryAppPage(bean, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 查询发货单的数据(页面显示)
	@Test
	public void queryShipingData()
	{
		String[] params = new String[] { "110120151222115622000002" };
		try
		{
			System.out
					.println(JSON.toJSONString(toolSendService.queryShipingData(OrderType.BUY_TOOL.getCode(), params)));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 查询入库的免费工具
	@Test
	public void queryToolEnterByForFree()
	{
		System.out.println(toolSendService.queryToolEnterByForFree("0000000002", "110120151124102939000000"));
	}

	// 增加发货单
	@Test
	public void addToolShipping()
	{
		String entResNo = custId.substring(0, 11);
		List<ToolConfig> configs = new ArrayList<ToolConfig>();
		Shipping bean = new Shipping();

		bean.setShippingType(ShippingType.APPLY_TOOL.getCode());
		bean.setHsResNo(entResNo);
		bean.setCustId(custId);
		bean.setCustName(entResNo + "托管企业");
		bean.setCustType(HsResNoUtils.getCustTypeByHsResNo(entResNo));
		bean.setReceiver("李四");
		bean.setMobile("13632637934");
		bean.setReceiverAddr("深圳福田");
		bean.setPostCode("134564");
		bean.setSmName("快速");
		bean.setTrackingNo(StringUtil.getDateTime());
		bean.setShippingFee("20");
		bean.setConsignor(operNo);
		bean.setDeliveryDesc("申报工具发货");

		ToolConfig conf = null;

		conf = new ToolConfig();
		conf.setHsResNo(entResNo);
		conf.setHsCustId(custId);
		conf.setCategoryCode(CategoryCode.GIFT.name());
		conf.setProductId("0000000006");
		conf.setProductName("积分手册");
		conf.setUnit("本");
		conf.setPrice("0");
		conf.setQuantity(100);
		conf.setTotalAmount("0");
		conf.setConfUser(operNo);
		conf.setWhId("110120151124102939000000");
		// configs.add(conf);

		conf = new ToolConfig();
		conf.setHsResNo(entResNo);
		conf.setHsCustId(custId);
		conf.setCategoryCode(CategoryCode.SUPPORT.name());
		conf.setProductId("0000000007");
		conf.setProductName("互生标示");
		conf.setUnit("个");
		conf.setPrice("0");
		conf.setQuantity(200);
		conf.setTotalAmount("0");
		conf.setConfUser(operNo);
		conf.setWhId("110120151124102939000000");
		// configs.add(conf);

		bean.setConfigs(configs);

		String[] confNo = new String[] { "110120151222115622000002" };
		bean.setConfNos(confNo);
		try
		{
			long start = System.currentTimeMillis();
			toolSendService.addToolShipping(bean);
			System.out.println("生成发货单消耗时间:" + (System.currentTimeMillis() - start));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 分页查询发货单
	@Test
	public void queryShippingPage()
	{
		BaseParam bean = new BaseParam();
		bean.setStartDate("2016-04-16");
		bean.setEndDate("2016-04-20");
		// bean.setHsResNo("06001110001");
		bean.setOperNo("0000000015600100000");
		bean.setType("2");
		// bean.setHsCustName("熊大");
		PageData<ToolShippingPage> data = toolSendService.queryShippingPage(bean, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 查询发货单详情
	@Test
	public void queryShippingById()
	{
		System.out.println(JSON.toJSONString(toolSendService.queryShippingById("110120160412170009000000")));
	}

	// 发货单签收
	@Test
	public void toolSignAccept()
	{
		Shipping bean = toolSendService.queryShippingById("110120151023024637000000");
		bean.setSignPeople(operNo);
		bean.setSignDesc("工具签收");
		try
		{
			// toolSendService.toolSignAccept(bean);
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void aa()
	{
		try
		{
			// BsEntMainInfo entInfo = bsEntService
			// .getMainInfoByResNo("06001000000");

			BsEntAllInfo entInfo = bsEntService.searchEntAllInfoByResNo("06001010000");

			// .searchEntAllInfo("06001010000163521987431424");
			System.out.println(JSON.toJSONString(entInfo));
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
