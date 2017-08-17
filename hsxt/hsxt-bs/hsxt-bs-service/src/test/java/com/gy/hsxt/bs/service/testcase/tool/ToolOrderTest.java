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
import com.gy.hsxt.bs.api.tool.IBSToolMarkService;
import com.gy.hsxt.bs.api.tool.IBSToolOrderService;
import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.order.DeliverInfo;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.tool.CardStyle;
import com.gy.hsxt.bs.bean.tool.OrderBean;
import com.gy.hsxt.bs.bean.tool.ProxyOrder;
import com.gy.hsxt.bs.bean.tool.ProxyOrderDetail;
import com.gy.hsxt.bs.bean.tool.ToolConfig;
import com.gy.hsxt.bs.bean.tool.ToolOrderPay;
import com.gy.hsxt.bs.bean.tool.resultbean.OrderEnt;
import com.gy.hsxt.bs.bean.tool.resultbean.SpecialCardStyle;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolOrderPage;
import com.gy.hsxt.bs.common.ObjectFactory;
import com.gy.hsxt.bs.common.StringUtil;
import com.gy.hsxt.bs.common.enumtype.ApprStatus;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.bs.common.enumtype.tool.CategoryCode;
import com.gy.hsxt.bs.common.enumtype.tool.ToolService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.HsResNoUtils;

@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-global.xml")
// @ContextConfiguration(locations = "classpath:test/spring-global.xml")
/**
 * 工具订单测试用例
 * 
 * @Package: com.gy.hsxt.bs.service.testcase.tool
 * @ClassName: ToolOrderTest
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月12日 上午11:25:38
 * @company: gyist
 * @version V3.0.0
 */
public class ToolOrderTest {

	@Autowired
	IBSToolOrderService toolOrderService;

	@Autowired
	IBSToolMarkService toolMarkService;

	String operNo = "likui";

	String custId = "0600102000020151215";

	String exeCustId = "00000000156163438271051776";

	String custName = "托管企业-2015-12-22";

	// 查询可以申购的工具
	@Test
	public void queryMayBuyToolProduct()
	{
		System.out.println(toolOrderService.queryMayBuyToolProduct(CustType.TRUSTEESHIP_ENT.getCode()));
	}

	// 根据工具服务查询客户可以购买的工具
	@Test
	public void queryMayBuyToolProductByToolSevice()
	{
		System.out.println(toolOrderService.queryMayBuyToolProductByToolSevice(CustType.MEMBER_ENT.getCode(),
				ToolService.CARD.name()));
	}

	// 查询企业的所有个性卡样
	@Test
	public void queryEntSpecialCardStyle()
	{
		System.out.println(toolOrderService.queryEntSpecialCardStyle("06002110000"));
	}

	// 查询企业的所有卡样(标准 + 个性)
	@Test
	public void queryEntCardStyleByAll()
	{
		System.out.println(toolOrderService.queryEntCardStyleByAll("06035120000"));
	}

	// 查询可以购买的数量
	@Test
	public void queryMayBuyToolNum()
	{
		System.out.println(toolOrderService.queryMayBuyToolNum("0600102000020151215", CategoryCode.POINT_MCR.name()));
	}

	// 查询企业资源段
	@Test
	public void queryEntResourceSegment()
	{
		System.out.println(toolOrderService.queryEntResourceSegment("0603311000020160416"));
	}

	// 申购工具下单
	@Test
	public void addToolBuyOrder()
	{
		String entResNo = custId.substring(0, 11);
		List<ToolConfig> confs = new ArrayList<ToolConfig>();
		OrderBean bean = new OrderBean();

		// 订单
		Order order = ObjectFactory.createOrder(GuidAgent.getStringGuid(BizGroup.BS + "01"), custId, custName, null,
				OrderType.BUY_TOOL.getCode(), "150", "HSB", "1.0", null, null, operNo, null);

		// POS机
		ToolConfig conf = null;
		conf = new ToolConfig();
		conf.setHsResNo(entResNo);
		conf.setHsCustId(custId);
		conf.setCategoryCode(CategoryCode.P_POS.name());
		conf.setProductId("0000000001");
		conf.setProductName("POS机");
		conf.setUnit("台");
		conf.setPrice("200000");
		conf.setQuantity(9999);
		conf.setTotalAmount("4000");
		conf.setConfUser(operNo);
		confs.add(conf);

		// 平板
		conf = new ToolConfig();
		conf.setHsResNo(entResNo);
		conf.setHsCustId(custId);
		conf.setCategoryCode(CategoryCode.TABLET.name());
		conf.setProductId("0000000004");
		conf.setProductName("互生平板");
		conf.setUnit("台");
		conf.setPrice("800");
		conf.setQuantity(1);
		conf.setTotalAmount("800");
		conf.setConfUser(operNo);
		// confs.add(conf);

		// 消费刷卡器
		conf = new ToolConfig();
		conf.setHsResNo(entResNo);
		conf.setHsCustId(custId);
		conf.setCategoryCode(CategoryCode.CONSUME_MCR.name());
		conf.setProductId("0000000003");
		conf.setProductName("消费刷卡器");
		conf.setUnit("台");
		conf.setPrice("300");
		conf.setQuantity(2);
		conf.setTotalAmount("600");
		conf.setConfUser(operNo);
		// confs.add(conf);

		// 积分刷卡器
		conf = new ToolConfig();
		conf.setHsResNo(entResNo);
		conf.setHsCustId(custId);
		conf.setCategoryCode(CategoryCode.POINT_MCR.name());
		conf.setProductId("0000000002");
		conf.setProductName("积分刷卡器");
		conf.setUnit("台");
		conf.setPrice("150");
		conf.setQuantity(1);
		conf.setTotalAmount("150");
		conf.setConfUser(operNo);
		// confs.add(conf);

		// 互生卡
		conf = new ToolConfig();
		conf.setHsResNo(entResNo);
		conf.setHsCustId(custId);
		conf.setCategoryCode(CategoryCode.P_CARD.name());
		conf.setProductId("0000000005");
		conf.setProductName("互生卡");
		conf.setUnit("张");
		conf.setPrice("20");
		conf.setQuantity(1000);
		conf.setTotalAmount("20000");
		conf.setConfUser(operNo);
		// confs.add(conf);

		// 收货信息
		DeliverInfo deliverInfo = new DeliverInfo();
		deliverInfo.setHsCustId(custId);
		deliverInfo.setStreetAddr("深圳市福田区XX街道XX号");
		deliverInfo.setFullAddr("深圳市福田区XX街道XX号XXXXXXXXXXXXX");
		deliverInfo.setLinkman(operNo);
		deliverInfo.setMobile("13523124512");
		deliverInfo.setCreatedby(operNo);

		bean.setOrder(order);
		bean.setConfs(confs);
		bean.setDeliverInfo(deliverInfo);

		try
		{
			long start = System.currentTimeMillis();
			System.out.println("下单返回结果:" + JSON.toJSONString(toolOrderService.addToolBuyOrder(bean)));
			System.out.println("工具下单消耗时间:" + (System.currentTimeMillis() - start));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 新增平台代购订单
	@Test
	public void addPlatProxyOrder()
	{
		String entResNo = custId.substring(0, 11);
		List<ProxyOrderDetail> details = new ArrayList<ProxyOrderDetail>();

		// 代购订单
		ProxyOrder bean = new ProxyOrder();
		bean.setEntResNo(entResNo);
		bean.setEntCustId(custId);
		bean.setEntCustName(entResNo + "托管企业");
		bean.setCustType(HsResNoUtils.getCustTypeByHsResNo(entResNo));
		bean.setOrderAmount("4000");
		bean.setOrderType(OrderType.BUY_TOOL.getCode());
		bean.setCurrencyCode("HBS");
		bean.setOrderRemark("平台代购订单");
		bean.setReqOperator(operNo);
		bean.setReqRemark("新增平台代购订单");

		// 代购清单
		ProxyOrderDetail detail = null;
		detail = new ProxyOrderDetail();
		detail.setProductId("0000000001");
		detail.setCategoryCode(CategoryCode.P_POS.name());
		detail.setProductName("POS机");
		detail.setUnit("台");
		detail.setPrice("2000");
		detail.setQuantity(2);
		detail.setTotalAmount("4000");
		details.add(detail);

		detail = new ProxyOrderDetail();
		detail.setProductId("0000000004");
		detail.setCategoryCode(CategoryCode.TABLET.name());
		detail.setProductName("互生平板");
		detail.setUnit("台");
		detail.setPrice("800");
		detail.setQuantity(2);
		detail.setTotalAmount("1600");
		// details.add(detail);

		detail = new ProxyOrderDetail();
		detail.setProductId("0000000005");
		detail.setCategoryCode(CategoryCode.P_CARD.name());
		detail.setProductName("互生卡");
		detail.setUnit("张");
		detail.setPrice("20");
		detail.setQuantity(1000);
		detail.setTotalAmount("20000");
		detail.setCardStyleId("1120151012095519000000");
		// details.add(detail);

		detail = new ProxyOrderDetail();
		detail.setProductId("0000000003");
		detail.setCategoryCode(CategoryCode.CONSUME_MCR.name());
		detail.setProductName("消费刷卡器");
		detail.setUnit("台");
		detail.setPrice("300");
		detail.setQuantity(4);
		detail.setTotalAmount("1200");
		// details.add(detail);

		detail = new ProxyOrderDetail();
		detail.setProductId("0000000002");
		detail.setCategoryCode(CategoryCode.POINT_MCR.name());
		detail.setProductName("积分刷卡器");
		detail.setUnit("台");
		detail.setPrice("150");
		detail.setQuantity(4);
		detail.setTotalAmount("600");
		// details.add(detail);

		// 收货信息
		DeliverInfo deliverInfo = new DeliverInfo();
		deliverInfo.setHsCustId(custId);
		deliverInfo.setStreetAddr("深圳市福田区XX街道XX号");
		deliverInfo.setFullAddr("深圳福田");
		deliverInfo.setLinkman("李四");
		deliverInfo.setMobile("13632637934");
		deliverInfo.setCreatedby(operNo);

		bean.setDetail(details);
		bean.setDeliverInfo(deliverInfo);
		try
		{
			long start = System.currentTimeMillis();
			toolOrderService.addPlatProxyOrder(bean);
			System.out.println("平台代购下单消耗时间：" + (System.currentTimeMillis() - start));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 分页查询平台代购
	@Test
	public void queryPlatProxyOrderPage()
	{
		BaseParam param = new BaseParam();
		param.setHsResNo("06");
		PageData<ProxyOrder> data = toolOrderService.queryPlatProxyOrderPage(param, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 根据id查询平台代购订单
	@Test
	public void queryPlatProxyOrderById()
	{
		System.out.println(JSON.toJSONString(toolOrderService.queryPlatProxyOrderById("110120151130102744000000")));
	}

	// 平台代购订单审批
	@Test
	public void platProxyOrderAppr()
	{
		ProxyOrder bean = toolOrderService.queryPlatProxyOrderById("110120151228145947000000");
		try
		{
			long start = System.currentTimeMillis();
			bean.setStatus(ApprStatus.PASS.getCode());
			bean.setApprOperator(operNo);
			bean.setExeCustId(exeCustId);
			toolOrderService.platProxyOrderAppr(bean);
			System.out.println("平台代购审批消耗时间：" + (System.currentTimeMillis() - start));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 个人补卡下单
	@Test
	public void personMendCardOrder()
	{
		String preCustId = "0600211830020151207";
		String entResNo = preCustId.substring(0, 11);
		List<String> perResNos = new ArrayList<>();
		OrderBean bean = new OrderBean();
		// 订单
		Order order = ObjectFactory.createOrder(GuidAgent.getStringGuid(BizGroup.BS + "01"), preCustId, "XZXX", null,
				null, "20", "HSB", "1.0", null, null, operNo, null);
		// 补卡的互生号
		perResNos.add(entResNo);

		// 收货信息
		DeliverInfo deliverInfo = new DeliverInfo();
		deliverInfo.setHsCustId(preCustId);
		deliverInfo.setStreetAddr("深圳市福田区XX街道XX号");
		deliverInfo.setFullAddr("深圳市福田区XX街道XX号XXXXXXXXXXXXX");
		deliverInfo.setLinkman(operNo);
		deliverInfo.setMobile("13523124512");
		deliverInfo.setCreatedby(operNo);

		bean.setOrder(order);
		bean.setDeliverInfo(deliverInfo);
		bean.setPerResNos(perResNos);

		try
		{
			long start = System.currentTimeMillis();
			System.out.println("下单返回结果:" + JSON.toJSONString(toolOrderService.personMendCardOrder(bean)));
			System.out.println("个人补卡下单消耗时间:" + (System.currentTimeMillis() - start));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 企业重做卡下单
	@Test
	public void entMakingCardOrder()
	{
		String entResNo = custId.substring(0, 11);
		List<String> perResNos = new ArrayList<>();
		OrderBean bean = new OrderBean();
		// 订单
		Order order = ObjectFactory.createOrder(GuidAgent.getStringGuid(BizGroup.BS + "01"), custId, entResNo + "托管企业",
				null, null, null, null, "HSB", null, null, operNo, null);

		// 重做的互生号
		perResNos.add("06001020002");
		perResNos.add("06001020003");
		// perResNos.add("");
		// perResNos.add("");

		// 收货信息
		DeliverInfo deliverInfo = new DeliverInfo();
		deliverInfo.setHsCustId(custId);
		deliverInfo.setStreetAddr("深圳市福田区XX街道XX号");
		deliverInfo.setFullAddr("深圳市福田区XX街道XX号XXXXXXXXXXXXX");
		deliverInfo.setLinkman(operNo);
		deliverInfo.setMobile("13523124512");
		deliverInfo.setCreatedby(operNo);

		bean.setOrder(order);
		bean.setDeliverInfo(deliverInfo);
		bean.setPerResNos(perResNos);

		try
		{
			long start = System.currentTimeMillis();
			System.out.println("下单返回结果:" + JSON.toJSONString(toolOrderService.entMakingCardOrder(bean)));
			System.out.println("企业重做卡下单消耗时间:" + (System.currentTimeMillis() - start));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 工具订单支付(申购工具,补卡、重做卡、订制卡样)
	@Test
	public void toolOrderToPay()
	{

		ToolOrderPay bean = new ToolOrderPay();
		bean.setOrderNo("110120160426095223000000");
		bean.setPayChannel(PayChannel.E_BANK_PAY.getCode());
		bean.setJumpUrl("http://www.baidu.com");
		bean.setBindingNo("201503050109140001010000");
		bean.setSmsCode("130776");
		try
		{
			System.out.println(toolOrderService.buyToolOrderToPay(bean));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 企业分页查询工具订单
	@Test
	public void queryOrderEntByPage()
	{
		BaseParam param = new BaseParam();
		param.setHsResNo("06001040003");
		PageData<OrderEnt> data = toolOrderService.queryToolOrderEntByPage(param, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 平台分页查询工具订单
	@Test
	public void queryToolOrderPlatPage()
	{
		BaseParam param = new BaseParam();
		// param.setHsResNo("0600");
		param.setOrderNo("11012016030");
		PageData<ToolOrderPage> data = toolOrderService.queryToolOrderPlatPage(param, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 查询订单详情
	@Test
	public void queryOrderDetailByNo()
	{
		System.out.println(JSON.toJSONString(toolOrderService.queryOrderDetailByNo("110120160426095223000000")));
	}

	// 根据订单号和工具类别查询配置单
	@Test
	public void queryToolConfigByNoAndCode()
	{
		System.out.println(JSON.toJSONString(
				toolOrderService.queryToolConfigByNoAndCode("110120160416151149000000", CategoryCode.P_CARD.name())));
	}

	// 分页查询互生个性卡样(平台)
	@Test
	public void querySpecialCardStylePage()
	{
		BaseParam param = new BaseParam();
		PageData<SpecialCardStyle> data = toolOrderService.querySpecialCardStylePage(param, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 根据订单号查询互生卡样
	@Test
	public void queryCardStyleByOrderNo()
	{
		System.out.println(toolOrderService.queryCardStyleByOrderNo("110120151215151700000001"));
	}

	// 修改卡样锁定状态
	@Test
	public void modifyCaryStyleLockStatus()
	{
		try
		{
			toolOrderService.modifyCardStyleLockStatus("110120151215151700000001", true);
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 上传卡制作文件
	@Test
	public void uploadCardStyleMarkFile()
	{
		CardStyle bean = new CardStyle();
		bean.setOrderNo("110120160118164428000000");
		bean.setMicroPic("00SNd2Af405c31BA90700Ee4beUi");
		bean.setSourceFile("00SN6bf77C95e3A0d926E48570Nm");
		bean.setConfirmFile("00SN6bf77C95e3A0d926E48570Nm");
		bean.setReqOperator(operNo);
		bean.setReqRemark("互生卡个性卡");
		try
		{
			toolOrderService.uploadCardStyleMarkFile(bean);
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 定制互生卡样下单
	@Test
	public void addCardStyleFeeOrder()
	{
		String custId = "0600102000020151215";
		String entResNo = custId.substring(0, 11);
		Order bean = ObjectFactory.createOrder(GuidAgent.getStringGuid(BizGroup.BS + "01"), custId, entResNo + "托管企业",
				null, null, "1000", "HSB", "1.0", null, null, operNo, null);

		CardStyle style = new CardStyle();
		style.setCardStyleName(entResNo + "订制卡样");
		style.setMaterialMicroPic(StringUtil.getDateTime());
		style.setMaterialSourceFile(StringUtil.getDateTime());
		style.setEntResNo(entResNo);
		style.setEntCustId(custId);
		style.setReqOperator(operNo);
		style.setReqRemark("互生卡个性卡");
		try
		{
			long start = System.currentTimeMillis();
			System.out.println("定制卡样下单返回结果:" + JSON.toJSONString(toolOrderService.addCardStyleFeeOrder(bean, null)));
			System.out.println("定制卡样下单消耗时间:" + (System.currentTimeMillis() - start));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 企业分页查询个性卡样
	@Test
	public void queryEntSpecialCardStylePage()
	{

		PageData<SpecialCardStyle> data = toolOrderService.queryEntSpecialCardStylePage("06002110000", new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 企业上传卡样素材(订制卡样)
	@Test
	public void addSpecialCardStyleEnt()
	{
		CardStyle bean = new CardStyle();
		bean.setOrderNo("110120151215151700000001");
		bean.setCardStyleName("06002110000订制卡样");
		bean.setMaterialMicroPic(StringUtil.getDateTime());
		bean.setMaterialSourceFile(StringUtil.getDateTime());
		bean.setEntResNo("06002110000");
		bean.setEntCustId("06002110000164063559693312");
		bean.setReqOperator(operNo);
		bean.setReqRemark("互生卡个性卡");
		try
		{
			toolOrderService.addSpecialCardStyleEnt(bean);
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 修改企业卡样确认文件
	@Test
	public void modifyCardStyleConfirmFile()
	{
		CardStyle bean = new CardStyle();
		bean.setOrderNo("110120151215151700000001");
		bean.setConfirmFile(StringUtil.getDateTime());
		bean.setReqOperator(operNo);
		bean.setReqRemark("互生卡个性卡");
		try
		{
			toolOrderService.entConfirmCardStyle(bean);
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 企业上传互生卡制作卡样确认书
	@Test
	public void uploadCardMarkConfirmFile()
	{
		try
		{
			toolOrderService.uploadCardMarkConfirmFile("110120160119112447000002", "11111111111111111");
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 工具订单确认制作
	@Test
	public void toolOrderConfirmMark()
	{
		try
		{
			long start = System.currentTimeMillis();
			toolOrderService.toolOrderConfirmMark("110120160307173851000001");
			System.out.println("工具订单确认制作消耗时间：" + (System.currentTimeMillis() - start));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 工具订单撤单
	@Test
	public void toolOrderWithdrawals()
	{
		try
		{
			long start = System.currentTimeMillis();
			// toolOrderService.toolOrderWithdrawals("110120151219155110000000");
			System.out.println("工具订单撤单消耗时间：" + (System.currentTimeMillis() - start));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 工具订单关闭或撤单
	@Test
	public void closeOrWithdrawalsToolOrder()
	{
		try
		{
			long start = System.currentTimeMillis();
			toolOrderService.closeOrWithdrawalsToolOrder("110120160307172807000000");
			System.out.println("工具订单关闭或撤单消耗时间：" + (System.currentTimeMillis() - start));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 查询个人补卡订单
	@Test
	public void queryPersonMendCardOrderPage()
	{
		BaseParam param = new BaseParam();
		param.setHsCustId("0500108123020151217");
		PageData<Order> data = toolOrderService.queryPersonMendCardOrderPage(param, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}
}
