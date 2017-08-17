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
import com.gy.hsxt.bs.api.tool.IBSToolAfterService;
import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.order.DeliverInfo;
import com.gy.hsxt.bs.bean.tool.AfterService;
import com.gy.hsxt.bs.bean.tool.AfterServiceConfig;
import com.gy.hsxt.bs.bean.tool.AfterServiceDetail;
import com.gy.hsxt.bs.bean.tool.ImportAfterService;
import com.gy.hsxt.bs.bean.tool.Shipping;
import com.gy.hsxt.bs.bean.tool.resultbean.EntDeviceInfo;
import com.gy.hsxt.bs.bean.tool.resultbean.SecretKeyOrderInfoPage;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolConfigPage;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolOrderPage;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolShippingPage;
import com.gy.hsxt.bs.common.StringUtil;
import com.gy.hsxt.bs.common.enumtype.ApprStatus;
import com.gy.hsxt.bs.common.enumtype.tool.CategoryCode;
import com.gy.hsxt.bs.common.enumtype.tool.DisposeType;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsResNoUtils;

@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-global.xml")
// @ContextConfiguration(locations = "classpath:test/spring-global.xml")
/**
 * 工具售后测试类
 * 
 * @Package: com.gy.hsxt.bs.service.testcase.tool
 * @ClassName: ToolAfterTest
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月30日 上午9:07:21
 * @company: gyist
 * @version V3.0.0
 */
public class ToolAfterTest {

	@Autowired
	IBSToolAfterService toolAfterService;

	String operNo = "likui";

	String platOptId = "00000000156163438271051776";

	// 平台分页查询工具订单
	@Test
	public void queryAfterOrderPlatPage()
	{
		BaseParam param = new BaseParam();
		// param.setHsResNo("0600");
		// param.setOrderNo("11012016030");
		PageData<ToolOrderPage> data = toolAfterService.queryAfterOrderPlatPage(param, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 增加售后服务
	@Test
	public void validBatchImportSeqNo()
	{
		List<String> seqNos = new ArrayList<String>();
		seqNos.add("K3202505947");
		seqNos.add("15615042110000000440");
		seqNos.add("sdf");
		seqNos.add("fgfg");
		// seqNos.add("fgfg");
		// seqNos.add("fgfg");
		seqNos.add("G2001B00381");
		try
		{
			System.out.println(JSON.toJSONString(toolAfterService.validBatchImportSeqNo(seqNos)));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 分页查询企业设备信息
	@Test
	public void queryEntDeviceInfoPage()
	{
		PageData<EntDeviceInfo> data = toolAfterService.queryEntDeviceInfoPage(null, "06002110000", new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 增加售后服务
	@Test
	public void addAfterService()
	{
		String entCustId = "0603112000020160416";
		String entResNo = entCustId.substring(0, 11);

		AfterService bean = new AfterService();
		List<AfterServiceDetail> details = new ArrayList<AfterServiceDetail>();

		bean.setEntResNo(entResNo);
		bean.setEntCustId(entCustId);
		bean.setEntCustName("4.16 测试全部");
		bean.setReqOperator(operNo);
		bean.setReqRemark(entResNo + "申请售后服务");

		// 售后服务清单
		AfterServiceDetail detail = null;

		detail = new AfterServiceDetail();
		detail.setDeviceSeqNo("G2001B00381");
		detail.setTerminalNo("060311200000001");
		details.add(detail);

		detail = new AfterServiceDetail();
		detail.setDeviceSeqNo("ec000000000002");
		detail.setTerminalNo("061860100000002");
		// details.add(detail);

		// 收货信息
		DeliverInfo deliverInfo = new DeliverInfo();
		deliverInfo.setHsCustId(entCustId);
		deliverInfo.setStreetAddr("深圳市福田区XX街道XX号");
		deliverInfo.setFullAddr("深圳市福田区XX街道XX号XXXXXXXXXXXXX");
		deliverInfo.setLinkman(operNo);
		deliverInfo.setMobile("13523124512");
		deliverInfo.setCreatedby(operNo);

		bean.setDeliver(deliverInfo);
		bean.setDetail(details);
		try
		{
			long start = System.currentTimeMillis();
			toolAfterService.addAfterService(bean);
			System.out.println("增加售后服务消耗时间：" + (System.currentTimeMillis() - start));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 批量导入售后服务
	@Test
	public void batchImportAfterService()
	{
		List<ImportAfterService> beans = new ArrayList<ImportAfterService>();

		ImportAfterService bean = null;
		bean = new ImportAfterService("06001020000", "K3202505947", "060010200000004");
		beans.add(bean);

		try
		{
			long start = System.currentTimeMillis();
			toolAfterService.batchImportAfterService(beans);
			System.out.println("批量导入售后服务消耗时间：" + (System.currentTimeMillis() - start));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 根据编号查询售后单
	@Test
	public void queryAfterServiceByNo()
	{
		System.out.println(JSON.toJSON(toolAfterService.queryAfterServiceByNo("110120151113110421000000")));
	}

	// 分页查询售后单审批
	@Test
	public void queryAfterOrderApprPage()
	{
		BaseParam param = new BaseParam();
		// param.setHsResNo("06186030000");
		param.setStartDate("2016-01-01");
		param.setEndDate("2016-04-28");
		PageData<AfterService> data = toolAfterService.queryAfterOrderApprPage(param, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 审批售后服务
	@Test
	public void apprAfterService()
	{
		AfterService bean = toolAfterService.queryAfterServiceByNo("110120151230173601000000");
		bean.setStatus(ApprStatus.PASS.getCode());
		bean.setApprOperator(operNo);
		bean.setApprRemark("售后服务审批");
		bean.setExeCustId(platOptId);

		List<AfterServiceDetail> details = bean.getDetail();
		for (AfterServiceDetail detail : details)
		{
			switch (CategoryCode.getCode(detail.getCategoryCode()))
			{
			case P_POS:
				detail.setDisposeType(DisposeType.ANEW_CONFIG.getCode());
				detail.setDisposeAmount("0");
				break;
			case TABLET:
				detail.setDisposeType(DisposeType.ANEW_CONFIG.getCode());
				detail.setDisposeAmount("0");
				break;
			case POINT_MCR:
				detail.setDisposeType(DisposeType.BARTER.getCode());
				detail.setDisposeAmount("150");
				break;
			case CONSUME_MCR:
				detail.setDisposeType(DisposeType.BARTER.getCode());
				detail.setDisposeAmount("300");
				break;
			default:
				break;
			}
		}
		try
		{
			long start = System.currentTimeMillis();
			toolAfterService.apprAfterService(bean);
			System.out.println("售后服务审批消耗时间：" + (System.currentTimeMillis() - start));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 分页查询售后刷卡工具制作配置单
	@Test
	public void queryToolAfterConfigPage()
	{
		BaseParam param = new BaseParam();
		param.setRoleIds(new String[] { "603", "HS20151210", "HS20151211" });
		param.setHsResNo("0603311");
		param.setHsCustName("首段");
		PageData<ToolConfigPage> data = toolAfterService.queryToolAfterConfigPage(param, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 查询售后配置单详情
	@Test
	public void queryAfterConfigDetail()
	{
		System.out.println(JSON.toJSON(toolAfterService.queryAfterConfigDetail("110120151116013555000002")));
	}

	// 配置刷卡器售后关联
	@Test
	public void configMcrRelationAfter()
	{
		AfterServiceConfig bean = new AfterServiceConfig();
		bean.setEntCustId("0600102000020151215");
		bean.setAfterOrderNo("110120151222152407000000");
		bean.setConfNo("110120151222152713000003");
		bean.setDeviceSeqNo("ec000000000001");
		bean.setTerminalNo("060010200000001");
		bean.setNewDeviceSeqNo("ec000000000003");
		bean.setOperNo(platOptId);
		try
		{
			long start = System.currentTimeMillis();
			toolAfterService.configMcrRelationAfter(bean);
			System.out.println("配置刷卡器售后关联消耗时间：" + (System.currentTimeMillis() - start));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 验证灌秘钥设备售后(POS机、平板)
	@Test
	public void vaildSecretKeyDeviceAfter()
	{
		try
		{
			toolAfterService.vaildSecretKeyDeviceAfter("HSV83MRJS71Y");

		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 配置秘钥设备售后(POS机、平板)
	@Test
	public void configSecretKeyDeviceAfter()
	{
		AfterServiceConfig bean = new AfterServiceConfig();
		bean.setAfterOrderNo("110120151116012007000000");
		// bean.setCategoryCode(CategoryCode.TABLET.name());
		bean.setConfNo("110120151116013555000002");
		bean.setDeviceSeqNo("HSV83MRJS71Y");
		bean.setEntCustId("0618603000020151015");
		bean.setNewDeviceSeqNo("HSV83MRJS71Y");
		bean.setTerminalNo("061860300000001");
		bean.setOperNo(operNo);
		try
		{
			long start = System.currentTimeMillis();
			toolAfterService.configSecretKeyDeviceAfter(bean);
			System.out.println("配置POS机或平板售后关联消耗时间：" + (System.currentTimeMillis() - start));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 分页查询秘钥配置--售后灌秘钥
	@Test
	public void queryAfterSecretKeyConfigByPage()
	{
		BaseParam param = new BaseParam();
		param.setRoleIds(new String[] { "1000", "HS20151210", "HS20151211" });
		PageData<SecretKeyOrderInfoPage> data = toolAfterService.queryAfterSecretKeyConfigByPage(param,
				new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 查询售后秘钥设备配置清单
	@Test
	public void queryAfterSecretKeyConfigDetail()
	{
		System.out.println(toolAfterService.queryAfterSecretKeyConfigDetail("110120151113032715000001"));
	}

	// 分页查询售后工具配送配送单(生成发货单)
	@Test
	public void queryToolConfigShippingAfterPage()
	{
		BaseParam param = new BaseParam();
		param.setRoleIds(new String[] { "603", "HS20151210", "HS20151211" });
		PageData<ToolConfigPage> data = toolAfterService.queryToolConfigShippingAfterPage(param, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 查询发货单的数据(页面显示)
	@Test
	public void queryAfterShipingData()
	{
		try
		{
			System.out.println(toolAfterService.queryAfterShipingData(new String[] { "110120151222152713000003" }));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 添加售后服务发货单
	@Test
	public void addToolShippingAfter()
	{
		String entCustId = "0600102000020151215";
		String entResNo = entCustId.substring(0, 11);
		Shipping bean = new Shipping();
		bean.setHsResNo(entResNo);
		bean.setCustId(entCustId);
		bean.setCustName(entResNo + "托管企业");
		bean.setCustType(HsResNoUtils.getCustTypeByHsResNo(entResNo));
		bean.setReceiver(operNo);
		bean.setMobile("13523124512");
		bean.setReceiverAddr("深圳市福田区XX街道XX号XXXXXXXXXXXXX");
		bean.setPostCode("518000");
		bean.setSmName("超速111");
		bean.setTrackingNo(StringUtil.getDateTime());
		bean.setShippingFee("20");
		bean.setConsignor(operNo);
		bean.setDeliveryDesc("工具售后发货");
		String[] confNo = new String[] { "110120151222152713000003" };
		bean.setConfNos(confNo);
		try
		{
			long start = System.currentTimeMillis();
			toolAfterService.addToolShippingAfter(bean);
			System.out.println("生成发货单消耗时间:" + (System.currentTimeMillis() - start));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 分页查询售后发货单(打印发货单)
	@Test
	public void queryShippingAfterPage()
	{
		BaseParam param = new BaseParam();
		//param.setOperNo(operNo);
		param.setHsResNo("06001110000");
		param.setHsCustName("花开");
		PageData<ToolShippingPage> data = toolAfterService.queryShippingAfterPage(param, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 根据id查询售后发货单
	@Test
	public void queryShippingAfterById()
	{
		System.out.println(toolAfterService.queryShippingAfterById("110120160310113930000000"));
	}
}
