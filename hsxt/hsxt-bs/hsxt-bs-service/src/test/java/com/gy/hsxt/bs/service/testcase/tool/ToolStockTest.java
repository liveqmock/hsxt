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
import com.gy.hsxt.bs.api.tool.IBSToolStockService;
import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.tool.DeviceUseRecord;
import com.gy.hsxt.bs.bean.tool.Enter;
import com.gy.hsxt.bs.bean.tool.InstorageInspection;
import com.gy.hsxt.bs.bean.tool.Supplier;
import com.gy.hsxt.bs.bean.tool.ToolConfig;
import com.gy.hsxt.bs.bean.tool.Warehouse;
import com.gy.hsxt.bs.bean.tool.WhArea;
import com.gy.hsxt.bs.bean.tool.WhInventory;
import com.gy.hsxt.bs.bean.tool.pageparam.ToolProductVo;
import com.gy.hsxt.bs.bean.tool.resultbean.DeviceInfoPage;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolEnterOutPage;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolStock;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolStockWarning;
import com.gy.hsxt.bs.common.enumtype.tool.CategoryCode;
import com.gy.hsxt.bs.common.enumtype.tool.UseType;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-global.xml")
// @ContextConfiguration(locations = "classpath:test/spring-global.xml")
/**
 * 工具库存管理
 * 
 * @Package: com.gy.hsxt.bs.service.testcase.tool
 * @ClassName: ToolStockTest
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月13日 下午6:11:44
 * @company: gyist
 * @version V3.0.0
 */
public class ToolStockTest {

	String operNo = "likui";

	@Autowired
	IBSToolStockService toolStockService;

	// 增加仓库
	@Test
	public void addWarehouse()
	{
		List<WhArea> areas = new ArrayList<WhArea>();
		// 仓库
		Warehouse bean = new Warehouse();
		bean.setWhName("珠江三角洲仓库");
		bean.setWhRoleId("HS20151211");
		bean.setMobile("13623124521");
		bean.setWhAddr("深圳市福田区福中路深圳市勘察研究院7栋");
		bean.setIsDefault(false);
		bean.setRemark("珠江三角洲仓库");
		bean.setCreatedBy(operNo);

		// 区域
		WhArea area = null;
		area = new WhArea(null, "156", "43");
		areas.add(area);
		area = new WhArea(null, "156", "44");
		areas.add(area);
		area = new WhArea(null, "156", "45");
		areas.add(area);

		try
		{
			long start = System.currentTimeMillis();
			bean.setWhArea(areas);
			toolStockService.addWarehouse(bean);
			System.out.println("新增仓库消耗时间：" + (System.currentTimeMillis() - start));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 删除仓库
	@Test
	public void removeWarehouse()
	{
		try
		{
			toolStockService.removeWarehouse("1120151013063747000000");
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 修改仓库
	@Test
	public void modifyWarehouse()
	{
		List<WhArea> areas = new ArrayList<WhArea>();
		Warehouse bean = toolStockService.queryWarehouseById("1120151013070205000000");

		bean.setWhName("珠江三角洲仓库01");
		bean.setWhRoleId("HS20151211");
		bean.setMobile("13623124511");
		bean.setWhAddr("深圳市福田区福中路深圳市勘察研究院8栋");
		bean.setIsDefault(false);
		bean.setRemark("珠江三角洲仓库01");
		bean.setUpdatedBy(operNo);

		// 区域
		WhArea area = null;
		area = new WhArea(null, "156", "46");
		areas.add(area);
		area = new WhArea(null, "156", "47");
		areas.add(area);
		area = new WhArea(null, "156", "48");
		areas.add(area);
		bean.setWhArea(areas);
		try
		{
			toolStockService.modifyWarehouse(bean);
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 根据id查询仓库
	@Test
	public void queryWarehouseById()
	{
		System.out.println(JSON.toJSONString(toolStockService.queryWarehouseById("1120151013070205000000")));
	}

	// 分页查询仓库
	@Test
	public void queryWarehouseByPage()
	{
		PageData<Warehouse> data = toolStockService.queryWarehouseByPage(null, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 查询所有的仓库
	@Test
	public void queryWarehouseAll()
	{
		System.out.println(toolStockService.queryWarehouseAll());
	}

	// 新增供应商
	@Test
	public void addSupplier()
	{
		Supplier bean = new Supplier();
		bean.setSupplierName("李四供应商");
		bean.setCorpName("深圳市XX有限公司");
		bean.setLinkMan("李四");
		bean.setAddr("深圳市福田区莲花山莲花街道54号");
		bean.setMobile("13523124512");
		bean.setPhone("0755-23214521");
		bean.setFax("0755-23214521");
		bean.setEmail("1234654@qq.com");
		bean.setWebsite("https://sdfsdf.com");
		bean.setRemark("李四供应商");
		bean.setCreatedBy(operNo);
		try
		{
			toolStockService.addSupplier(bean);
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 根据id查询供应商
	@Test
	public void querySupplierById()
	{
		System.out.println(JSON.toJSONString(toolStockService.querySupplierById("1120151013080901000000")));
	}

	// 删除供应商
	@Test
	public void removeSupplier()
	{
		try
		{
			toolStockService.removeSupplier("1120151013080901000000");
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 修改供应商
	@Test
	public void modifySupplier()
	{
		Supplier bean = toolStockService.querySupplierById("1120151013083233000000");
		bean.setSupplierName("POS机供应商01");
		bean.setCorpName("深圳市XXXXX有限公司");
		bean.setLinkMan("李大官人01");
		bean.setAddr("XXXXXXXXXXXXXXXXXXx");
		bean.setMobile("1352312451233");
		bean.setPhone("1234567844");
		bean.setFax("1234654544");
		bean.setEmail("123465466@qq.com");
		bean.setWebsite("https://sdfsdf44.com");
		bean.setRemark("POS机供应商03");
		bean.setUpdatedBy(operNo);
		try
		{
			toolStockService.modifySupplier(bean);
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 分页查询供应商
	@Test
	public void querySupplierByPage()
	{
		PageData<Supplier> data = toolStockService.querySupplierByPage(null, null, null, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 查询所有的供应商
	@Test
	public void querySupplierAll()
	{
		System.out.println(toolStockService.querySupplierAll());
	}

	// 工具入库
	@Test
	public void productEnter()
	{

		Enter bean = new Enter();

		// POS机
		// List<String> posSeqNo = new ArrayList<String>();
		// bean.setCategoryCode(CategoryCode.P_POS.name());
		// bean.setProductId("0000000001");
		// bean.setPurchasePrice("1990");
		// bean.setMarketPrice("2000");
		// bean.setQuantity(1000);
		// for (int i = 0; i < 20; i++)
		// {
		// posSeqNo.add(RandomCodeUtils.getNumberCode(11));
		// }
		// posSeqNo.add("37002107774");
		// bean.setDeviceSeqNo(posSeqNo);

		// 互生平板
		// List<String> tabletSeqNo = new ArrayList<String>();
		// bean.setCategoryCode(CategoryCode.TABLET.name());
		// bean.setProductId("0000000004");
		// bean.setPurchasePrice("780");
		// bean.setMarketPrice("800");
		// bean.setQuantity(1000);
		// for (int i = 0; i < 50; i++)
		// {
		// tabletSeqNo.add("HS" + RandomCodeUtils.getMixCode(10));
		// }
		// bean.setDeviceSeqNo(tabletSeqNo);

		// 积分手册
		// bean.setCategoryCode(CategoryCode.GIFT.name());
		// bean.setProductId("0000000006");
		// bean.setPurchasePrice("0.2");
		// bean.setMarketPrice("0.2");
		// bean.setQuantity(1000);

		// 互生标示
		// bean.setCategoryCode(CategoryCode.SUPPORT.name());
		// bean.setProductId("0000000007");
		// bean.setPurchasePrice("0.01");
		// bean.setMarketPrice("0.01");
		// bean.setQuantity(1000);

		// 积分刷卡器
		// bean.setCategoryCode(CategoryCode.POINT_MCR.name());
		// bean.setProductId("0000000002");
		// bean.setPurchasePrice("140");
		// bean.setMarketPrice("150");
		// bean.setQuantity(100);
		// bean.setEnterBatchNo("110120151124104557000000");

		// 消费刷卡器
		bean.setCategoryCode(CategoryCode.CONSUME_MCR.name());
		bean.setProductId("0000000003");
		bean.setPurchasePrice("290");
		bean.setMarketPrice("300");
		bean.setQuantity(500);
		bean.setEnterBatchNo("0120151225041327000000");

		bean.setWhId("HS10000000");
		bean.setSupplierId("110120151124103415000000");
		bean.setOperNo(operNo);

		try
		{
			long start = System.currentTimeMillis();
			toolStockService.productEnter(bean);
			System.out.println("工具入库消耗时间：" + (System.currentTimeMillis() - start));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 分页查询需要配置工具库存
	@Test
	public void queryConfigToolStockByPage()
	{
		PageData<ToolStock> data = toolStockService.queryConfigToolStockByPage(null, null, null, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 查询POS机设备序列号详情
	@Test
	public void queryPosDeviceSeqNoDetail()
	{
		System.out.println(JSON.toJSONString(toolStockService.queryPosDeviceSeqNoDetail("1120151014121315000000")));
	}

	// 库存盘库
	@Test
	public void toolEnterInventory()
	{
		WhInventory bean = new WhInventory();
		bean.setEnterNo("110120151026100101000000");
		bean.setShouldQuantity(3);
		bean.setQuantity(2);
		bean.setOperNo(operNo);
		try
		{
			toolStockService.toolEnterInventory(bean);
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 入库抽检
	@Test
	public void toolEnterCheck()
	{
		InstorageInspection bean = new InstorageInspection();
		bean.setEnterNo("110120151026100101000000");
		bean.setQuantity(3);
		bean.setPassQuantity(3);
		bean.setOperNo(operNo);
		bean.setRemark("抽检");
		bean.setPassRate(bean.getPassQuantity() / bean.getQuantity() * 100 + "%");
		try
		{
			toolStockService.toolEnterCheck(bean);
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 分页查询工具库存预警
	@Test
	public void toolEnterStockWarningPage()
	{
		PageData<ToolStockWarning> data = toolStockService.toolEnterStockWarningPage(null, "", 0, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 分页查询工具设备使用
	@Test
	public void queryToolDeviceUsePage()
	{
		PageData<DeviceInfoPage> data = toolStockService.queryToolDeviceUsePage(null, null, null, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 根据设备序列号查询配置详情(领用和报损除外)
	@Test
	public void queryDeviceConfigDetail()
	{
		System.out.println(JSON.toJSONString(toolStockService.queryDeviceConfigDetail("K3702C07539")));
	}

	// 使用设备查询设备清单(报损和领用)
	@Test
	public void queryDeviceDetailByNo()
	{
		System.out.println(JSON.toJSONString(toolStockService.queryDeviceDetailByNo("15615042110000000285")));
	}

	// 添加工具使用记录
	@Test
	public void addDeviceUseRecord()
	{
		String custId = "0618601000020151015";
		DeviceUseRecord bean = new DeviceUseRecord();
		bean.setDeviceSeqNo("KB05MQ1N69T");
		bean.setBatchNo("110120151104090942000000");
		bean.setUseType(UseType.REPORTED.getCode());
		bean.setUseName(operNo);
		bean.setCreatedBy(operNo);
		bean.setDescription("工具报损");
		try
		{
			toolStockService.addDeviceUseRecord(bean, custId);
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 分页查询工具入库流水
	@Test
	public void queryToolEnterSerialPage()
	{
		ToolProductVo param = new ToolProductVo();
		param.setStartDate("2015-1-12");
		// param.setEndDate("2015-1-12");
		PageData<ToolEnterOutPage> data = toolStockService.queryToolEnterSerialPage(param, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 分页查询工具出库流水
	@Test
	public void queryToolOutSerialPage()
	{
		ToolProductVo param = new ToolProductVo();
		// param.setStartDate("2015-1-12");
		// param.setEndDate("2015-1-12");
		PageData<ToolEnterOutPage> data = toolStockService.queryToolOutSerialPage(param, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 分页统计地区平台仓库工具
	@Test
	public void statisticPlatWhTool()
	{
		PageData<ToolStock> data = toolStockService.statisticPlatWhTool(CategoryCode.P_POS.name(), new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 分页查询配置单
	@Test
	public void queryToolConfigPage()
	{
		BaseParam bean = new BaseParam();
		bean.setRoleIds(new String[] { "HS20151211", "HS20151210" });
		PageData<ToolConfig> data = toolStockService.queryToolConfigPage(bean, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 修改配置单仓库id
	@Test
	public void modifyToolConfigWhId()
	{
		ToolConfig bean = new ToolConfig();
		bean.setConfNo("110120151202070553000001");
		bean.setWhId("110120151124102939000000");
		try
		{
			toolStockService.modifyToolConfigWhId(bean);
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}
}
