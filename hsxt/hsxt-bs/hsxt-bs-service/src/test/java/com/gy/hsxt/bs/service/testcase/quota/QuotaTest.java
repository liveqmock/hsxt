/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.service.testcase.quota;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.api.quota.IBSQuotaService;
import com.gy.hsxt.bs.bean.quota.CityQuotaApp;
import com.gy.hsxt.bs.bean.quota.CityQuotaQueryParam;
import com.gy.hsxt.bs.bean.quota.PlatQuotaApp;
import com.gy.hsxt.bs.bean.quota.ProvinceQuotaApp;
import com.gy.hsxt.bs.bean.quota.result.CompanyResS;
import com.gy.hsxt.bs.common.StringUtil;
import com.gy.hsxt.bs.common.enumtype.ApprStatus;
import com.gy.hsxt.bs.common.enumtype.quota.AppReason;
import com.gy.hsxt.bs.common.enumtype.quota.AppType;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:spring/spring-global.xml")
 @ContextConfiguration(locations = "classpath:test/spring-global.xml")
public class QuotaTest {

	@Autowired
	IBSQuotaService quotaService;

	String operNo = "likui";

	// 一级区域配额分配申请(管理公司列表)
	@Test
	public void queryPlatAppManageList()
	{
		System.out.println(quotaService.queryPlatAppManageList());
	}

	// 地区平台(一级区域)资源配额申请
	@Test
	public void saveApplyPlatQuota()
	{
		PlatQuotaApp bean = new PlatQuotaApp();
		bean.setEntResNo("04000000000");
		bean.setEntCustName("生四管理公司");
		bean.setPlatNo("156");
		bean.setApplyNum(50);
		bean.setApplyType(AppType.ADD.getCode());
		bean.setReqOperator(operNo);
		try
		{
			quotaService.applyPlatQuota(bean);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// 分页查询一级区域配额申请
	@Test
	public void queryPlatQuotaPage()
	{
		PageData<PlatQuotaApp> data = quotaService.queryPlatQuotaPage("09000000000", "管理", "0000000015630100000", new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 根据id查询一级区域配额申请
	@Test
	public void getPlatQuotaById()
	{
		PlatQuotaApp bean = quotaService.getPlatQuotaById("110120151118122520000000");
		Assert.assertNotNull(bean);
		System.out.println(bean.toString());
	}

	// 一级区域配额申请审批
	@Test
	public void apprPlatQuota()
	{
		String resNos = "";
		int startResNo = 1;
		PlatQuotaApp bean = quotaService.getPlatQuotaById("110120151204033445000000");
		Integer apprNum = bean.getApplyNum();
		bean.setApprNum(apprNum);
		bean.setStatus(ApprStatus.PASS.getCode());
		bean.setApprOperator(operNo);
		for (int i = 0; i < apprNum; i++)
		{
			resNos = resNos + bean.getEntResNo().substring(0, 2) + StringUtil.frontCompWithZore((startResNo + i), 3)
					+ "000000,";
		}
		resNos = resNos.substring(0, resNos.length() - 1);
		bean.setResNoList(resNos);
		try
		{
			// insideInvokeCall.apprPlatQuota(bean);
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 申请省级区域(二级区域)配额分配
	@Test
	public void saveApplyProvinceQuota()
	{
		ProvinceQuotaApp bean = new ProvinceQuotaApp();
		bean.setEntResNo("06000000000");
		bean.setCountryNo("156");
		bean.setProvinceNo("44");
		bean.setApplyNum(5);
		bean.setApplyType(AppType.ADD.getCode());
		bean.setReqOperator(operNo);
		try
		{
			quotaService.applyProvinceQuota(bean);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// 分页查询二级区域配额申请
	@Test
	public void queryProvinceQuotaPage()
	{
		PageData<ProvinceQuotaApp> data = quotaService.queryProvinceQuotaPage(null, null, null, "", new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 根据id查询二级区域申请
	@Test
	public void getProvinceQuotaById()
	{
		System.out.println(JSON.toJSONString(quotaService.getProvinceQuotaById("110120151118094701000000")));
	}

	// 二级区域配额申请审批
	@Test
	public void apprProvinceQuota()
	{
		ProvinceQuotaApp bean = quotaService.getProvinceQuotaById("110120151228145635000000");
		bean.setStatus(ApprStatus.PASS.getCode());
		bean.setApprOperator(operNo);
		bean.setApprNum(bean.getApplyNum());
		// bean.setExeCustId("00000000156163438271051776");
		try
		{
			quotaService.apprProvinceQuota(bean);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// 三级区域配额申请
	@Test
	public void applyCityQuota()
	{
		CityQuotaApp bean = new CityQuotaApp();
		bean.setEntResNo("06000000000");
		bean.setCountryNo("156");
		bean.setProvinceNo("44");
		bean.setCityNo("440104");
		bean.setApplyNum(20);
		// bean.setPopulation("10000000");
		bean.setApplyType(AppType.ADD.getCode());
		bean.setApplyReason(AppReason.PADD.getCode());
		// bean.setOtherReason("");
		bean.setReqOperator(operNo);
		try
		{
			quotaService.applyCityQuota(bean);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// 分页查询三级区域配额申请
	@Test
	public void queryCityQuotaPage()
	{
		CityQuotaQueryParam param = new CityQuotaQueryParam();
//		param.setCityNo("440104");
		param.setCityName("鸡西市");
		PageData<CityQuotaApp> data = quotaService.queryCityQuotaPage(param, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}

	// 根据id查询三级区域配额申请
	@Test
	public void getCityQuotaById()
	{
		System.out.println(quotaService.getCityQuotaById("110120151119091034000000"));
	}

	// 三级区域配额申请审批
	@Test
	public void apprCityQuota()
	{
		CityQuotaApp bean = quotaService.getCityQuotaById("110120151228145042000000");
		bean.setStatus(ApprStatus.PASS.getCode());
		bean.setApprOperator(operNo);
		bean.setApprNum(1);
		// bean.setExeCustId("00000000156163438271051776");
		try
		{
			quotaService.apprCityQuota(bean);
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 查询地区平台分配了配额的省
	@Test
	public void queryAllotProvinceList()
	{
		System.out.println(quotaService.queryAllotProvinceList());
	}

	// 查询省下申请城市配额分配的列表
	@Test
	public void queryAppCityAllotList()
	{
		System.out.println(JSON.toJSONString(quotaService.queryAppCityAllotList("12")));
	}

	// 统计管理公司下的资源数据
	@Test
	public void statResDetailOfManager()
	{
		System.out.println(quotaService.statResDetailOfManager("06000000000"));
	}

	// 统计省级(二级区域)下的资源数据
	@Test
	public void statResDetailOfProvince()
	{
		System.out.println(quotaService.statResDetailOfProvince("44", null));
	}

	// 城市(三级区域)下的资源列表
	@Test
	public void listResInfoOfCity()
	{
		System.out.println(quotaService.listResInfoOfCity("440104", null));
	}

	// 根据管理公司互生号查询可申请二级区域分配的省份 包含已分配该管理公司下级服务资源的省份以及从未进行二级区域分配的省份
	@Test
	public void listProvinceNoForAllot()
	{
		System.out.println(quotaService.listProvinceNoForAllot("06000000000"));
	}

	// 管理公司二级配额分配详情
	@Test
	public void queryManageAllotDetail()
	{
		System.out.println(quotaService.queryManageAllotDetail("06000000000"));
	}

	// 判断省份是否已进行首次配置
	@Test
	public void isProvinceFristAllot()
	{
		try
		{
			quotaService.isProvinceFristAllot("44");
		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}

	// 统计管理公司下的企业资源
	@Test
	public void statResCompanyResM()
	{
		System.out.println(quotaService.statResCompanyResM("06000000000"));
	}

	// 分页分组统计服务公司的企业资源
	@Test
	public void queryCompanyResMByPage()
	{
		String mEntResNo = "06000000000";
		PageData<CompanyResS> data = quotaService.queryCompanyResMByPage(mEntResNo, new Page(1, 10));
		System.out.println("================" + data.getCount());
		System.out.println(JSON.toJSONString(data.getResult()));
	}
	
	@Test
	public void statQuotaByCity(){
	    System.out.println(quotaService.statQuotaByCity("12","120000"));
	}
}
