/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.service.testcase.tool;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.tool.CardMarkConfig;
import com.gy.hsxt.bs.bean.tool.ConsumeKSN;
import com.gy.hsxt.bs.bean.tool.KsnExportRecord;
import com.gy.hsxt.bs.bean.tool.McrKsnRecord;
import com.gy.hsxt.bs.bean.tool.resultbean.SecretKeyOrderInfoPage;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolConfigPage;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.bs.common.enumtype.tool.CategoryCode;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-global.xml")
// @ContextConfiguration(locations = "classpath:test/spring-global.xml")
/**
 * 工具制作测试类
 * 
 * @Package: com.gy.hsxt.bs.service.testcase.tool
 * @ClassName: ToolMarkTest
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月14日 下午3:48:20
 * @company: gyist
 * @version V3.0.0
 */
public class ToolMarkTest {

	@Autowired
	IBSToolMarkService toolMarkService;

	String operNo = "00000000156163438271051776";

	// 分页查询刷卡器KSN生成记录
	@Test
	public void queryMcrKsnRecordPage()
	{
		PageData<McrKsnRecord> data = toolMarkService.queryMcrKsnRecordPage(null, null, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 生成积分刷卡器KSN
	@Test
	public void createPointKSNInfo()
	{
		try
		{
			long start = System.currentTimeMillis();
			System.out.println(toolMarkService.createPointKSNInfo(0, operNo));
			System.out.println("生成积分刷卡器KSN消耗时间：" + (System.currentTimeMillis() - start));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 导出积分刷卡器KSN
	@Test
	public void exportPointKSNInfo()
	{
		try
		{
			KsnExportRecord bean = new KsnExportRecord();
			bean.setBahctNo("110120151124104557000000");
			bean.setCreatedBy("likui");
			bean.setCreatedName("likui");
			System.out.println(JSON.toJSONString(toolMarkService.exportPointKSNInfo(bean)));
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	// 查询积分刷卡器KSN
	@Test
	public void queryPointKSNInfo()
	{
		System.out.println(JSON.toJSONString(toolMarkService.queryPointKSNInfo("110120151124104557000000")));
	}

	// 导入消费刷卡器KSN
	@Test
	public void importConsumeKSNInfo()
	{
		List<ConsumeKSN> bean = new ArrayList<ConsumeKSN>();
		ConsumeKSN ksn = null;

		ksn = new ConsumeKSN();
		ksn.setDeviceSeqNo("15615042110000000001");
		ksn.setKsnCodeY("15610000000001");
		ksn.setKsnCodeE("15620000000001");
		ksn.setKsnCodeS("15630000000001");
		ksn.setCiphertextY("CB55A7C60B818A65F1E4099E1883F94B");
		ksn.setCiphertextE("36D67334C463BFB50E9645ABAA14B249");
		ksn.setCiphertextS("96980AA78BD362356430CED3E177F4D2");
		ksn.setVaildY("269A88");
		ksn.setVaildE("3A8DC6");
		ksn.setVaildS("0EC5D3");
		bean.add(ksn);

		ksn = new ConsumeKSN();
		ksn.setDeviceSeqNo("15615042110000000002");
		ksn.setKsnCodeY("15610000000002");
		ksn.setKsnCodeE("15620000000002");
		ksn.setKsnCodeS("15630000000002");
		ksn.setCiphertextY("1E95CA6DFA8968C8DED549A6849C71C1");
		ksn.setCiphertextE("59D6DA7F70E347826C5551344F4AE1D5");
		ksn.setCiphertextS("55D50DA438E083B55861708EA808583F");
		ksn.setVaildY("788AC2");
		ksn.setVaildE("059321");
		ksn.setVaildS("4E57AE");
		bean.add(ksn);

		ksn = new ConsumeKSN();
		ksn.setDeviceSeqNo("15615042110000000003");
		ksn.setKsnCodeY("15610000000003");
		ksn.setKsnCodeE("15620000000003");
		ksn.setKsnCodeS("15630000000003");
		ksn.setCiphertextY("F62AA6EBDBFBCB5EFEAB567C3E3F5489");
		ksn.setCiphertextE("8181791946A5D5B1EDAB8BAE16A7988E");
		ksn.setCiphertextS("BEC0554BD2C08EC0890A8EF0722B2873");
		ksn.setVaildY("173768");
		ksn.setVaildE("FCFC81");
		ksn.setVaildS("44B581");
		bean.add(ksn);
		try
		{
			long start = System.currentTimeMillis();
			toolMarkService.importConsumeKSNInfo(bean, operNo);
			System.out.println("导入消费刷卡器KSN消耗时间：" + (System.currentTimeMillis() - start));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 导出消费刷卡器数据
	@Test
	public void exportConsumeKSNInfo()
	{
		try
		{
			KsnExportRecord bean = new KsnExportRecord();
			bean.setBahctNo("0120151124103558000000");
			bean.setCreatedBy("likui");
			bean.setCreatedName("likui");
			System.out.println(JSON.toJSONString(toolMarkService.exportConsumeKSNInfo(bean)));
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	// 查询消费刷卡器数据
	@Test
	public void queryConsumeKSNInfo()
	{
		System.out.println(JSON.toJSONString(toolMarkService.queryConsumeKSNInfo("0120151124103558000000")));
	}

	// 查询刷卡器导出记录
	@Test
	public void queryKsnExportRecord()
	{
		System.out.println(JSON.toJSONString(toolMarkService.queryKsnExportRecord("110120151126061529000000")));
	}

	// 分页查询刷卡工具制作配置单(申报新增)
	@Test
	public void queryToolConfigMarkPage()
	{
		BaseParam bean = new BaseParam();
		bean.setRoleIds(new String[] { "603", "HS20151210", "HS20151211" });
		bean.setStartDate("2016-01-14");
		bean.setEndDate("2016-03-23");
		PageData<ToolConfigPage> data = toolMarkService.queryToolConfigMarkPage(bean, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 查询设备关联详情
	@Test
	public void queryDeviceRelevanceDetail()
	{
		System.out.println(JSON.toJSONString(toolMarkService.queryDeviceRelevanceDetail("110120151202070553000001")));
	}

	// 分页查询刷卡工具配置单(申报新增)
	@Test
	public void queryServiceToolConfigPage()
	{
		BaseParam bean = new BaseParam();
		bean.setStartDate("2016-01-14");
		bean.setEndDate("2016-06-23");
		PageData<ToolConfigPage> data = toolMarkService.queryServiceToolConfigPage(bean, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 分页查询秘钥配置(申报新增)--灌秘钥
	@Test
	public void queryOrderByPosPage()
	{
		BaseParam bean = new BaseParam();
		bean.setRoleIds(new String[] { "1000", "HS20151210", "HS20151211" });
		// bean.setStartDate("2015-10-14");
		// bean.setEndDate("2015-10-15");
		bean.setHsResNo("06001020000");
		PageData<SecretKeyOrderInfoPage> data = toolMarkService.querySecretKeyConfigByPage(bean, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 验证设备信息合法(POS机、平板)
	@Test
	public void vaildDeviceInfoLawful()
	{
		try
		{
			toolMarkService.vaildDeviceInfoLawful("15615042110000000001");
		} catch (HsException ex)
		{
			ex.printStackTrace();
		}
	}

	// 查询设备的终端编号列表(POS机、平板)
	@Test
	public void queryConifgDeviceTerminalNo()
	{
		String entCustId = "06002110000164063559693312";
		String confNo = "110120151202070553000000";
		String categoryCode = CategoryCode.P_POS.name();
		System.out.println(
				JSON.toJSONString(toolMarkService.queryConifgDeviceTerminalNo(entCustId, confNo, categoryCode)));
	}

	// 配置POS机关联
	@Test
	public void configToolPos()
	{
		String entCustId = "06001010000163521987431424";
		String confNo = "110120151126040132000000";
		String deviceSeqNo = "37002107773";
		try
		{
			System.out.println(toolMarkService.configToolPos(entCustId, confNo, deviceSeqNo));
		} catch (HsException ex)
		{
			ex.printStackTrace();
		}
	}

	// 配置设备关联已使用
	@Test
	public void configToolDeviceIsUsed()
	{
		String entCustId = "06001010000163521987431424";
		String confNo = "110120151126040132000000";
		String terminalNo = "060021100000001";
		try
		{
			toolMarkService.configToolDeviceIsUsed(entCustId, confNo, terminalNo, operNo);
		} catch (HsException ex)
		{
			ex.printStackTrace();
		}
	}

	// 配置互生平板关联
	@Test
	public void configToolTablet()
	{
		String entCustId = "0618603000020151015";
		String confNo = "110120151104041428000000";
		String deviceSeqNo = "HSSM3U1BG64V";
		try
		{
			System.out.println(toolMarkService.configToolTablet(entCustId, confNo, deviceSeqNo));
		} catch (HsException ex)
		{
			ex.printStackTrace();
		}
	}

	// 配置刷卡器关联
	@Test
	public void configKsnRelation()
	{
		String entCustId = "0600102000020151215";
		String confNo = "110120151222115622000002";
		String deviceSeqNo = "ec000000000003";
		try
		{
			toolMarkService.configKsnRelation(entCustId, confNo, deviceSeqNo, operNo);
		} catch (HsException ex)
		{
			ex.printStackTrace();
		}
	}

	// 验证刷卡器设备是否合法
	@Test
	public void vaildMcrDeviceLawful()
	{
		String confNo = "110120160616101716000002";
		String deviceSeqNo = "15616011510000000502";
		try
		{
			System.out.println(JSON.toJSONString(toolMarkService.vaildMcrDeviceLawful( confNo, deviceSeqNo)));
		} catch (HsException ex)
		{
			ex.printStackTrace();
		}
	}

	// 批量配置刷卡器关联
	@Test
	public void batchConfigKsnRelation()
	{
		String entCustId = "0610001000020160511";
		String confNo = "110120160616101716000002";
		//15616011510000000504,15616011510000000502,15616011510000000501
		String [] deviceSeqNo = new String[]{"15616011510000000501","15616011510000000504","15616011510000000502"};
		try
		{
			toolMarkService.batchConfigKsnRelation(entCustId, confNo, Arrays.asList(deviceSeqNo), operNo);
		} catch (HsException ex)
		{
			ex.printStackTrace();
		}
	}


	// 分页查询互生卡制作配置单(申报新增)
	@Test
	public void queryToolConfigMarkCardPage()
	{
		BaseParam bean = new BaseParam();
		bean.setStartDate("2016-01-14");
		bean.setEndDate("2016-03-23");
		// bean.setHsResNo("06186010000");
		bean.setType(OrderType.APPLY_BUY_TOOL.getCode());
		bean.setRoleIds(new String[] { "603", "HS20151210", "HS20151211" });
		PageData<ToolConfigPage> data = toolMarkService.queryToolConfigMarkCardPage(bean, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 开卡
	@Test
	public void openCard()
	{
		try
		{
			long start = System.currentTimeMillis();
			toolMarkService.openCard("110120151202070553000002", operNo);
			System.out.println("开卡消耗时间：" + (System.currentTimeMillis() - start));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 重做互生卡开卡(补卡、重做卡)
	@Test
	public void remarkOpenCard()
	{
		try
		{
			toolMarkService.remarkOpenCard("110120151223174140000002", OrderType.REMAKE_BATCH_CARD.getCode(), operNo);
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 导出密码
	@Test
	public void exportCardPwd()
	{
		System.out.println(JSON.toJSONString(toolMarkService.exportCardPwd("110120151202070553000002")));
	}

	// 查询互生卡配置单制作数据
	@Test
	public void queryCardConfigMarkData()
	{
		System.out.println(JSON.toJSONString(toolMarkService.queryCardConfigMarkData("110120151202063144000000",
				"110120151202070553000002", "06002110000")));
	}

	// 导出暗码数据
	@Test
	public void exportCardDarkCode()
	{
		System.out.println(JSON.toJSONString(toolMarkService.exportCardDarkCode("110120151202070553000002")));
	}

	// 互生卡配置单制成
	@Test
	public void cardConfigMark()
	{
		try
		{
			toolMarkService.cardConfigMark(new CardMarkConfig("110120151202063144000000", "110120151202070553000002",
					"110120151124103415000000", "110120151204050414000000", operNo, "互生卡配置单制成"));
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 查询卡制作数据(制作单)
	@Test
	public void queryCardMarkData()
	{
		System.out.println(JSON.toJSONString(toolMarkService.queryCardMarkData("110120151202063144000000",
				"110120151202070553000002", "06002110000")));
	}

	// 查看互生卡出入库详情
	@Test
	public void queryCardInOutDetail()
	{
		System.out.println(JSON.toJSON(toolMarkService.queryCardInOutDetail("110120151202063144000000")));
	}

	// 互生卡配置入库
	@Test
	public void cardConfigEnter()
	{
		try
		{
			toolMarkService.cardConfigEnter("110120151202070553000002", "06001010000163521987508224");
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}
}
