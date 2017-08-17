/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.tc.service.test;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.tc.api.ITcBsAcService;
import com.gy.hsxt.tc.api.ITcBsGpService;
import com.gy.hsxt.tc.api.ITcGpChService;
import com.gy.hsxt.tc.api.ITcJobService;
import com.gy.hsxt.tc.api.ITcPsAcService;
import com.gy.hsxt.tc.bean.BsAcImbalance;
import com.gy.hsxt.tc.bean.BsAcSummary;
import com.gy.hsxt.tc.bean.BsGpImbalance;
import com.gy.hsxt.tc.bean.BsGpSummary;
import com.gy.hsxt.tc.bean.GpChDoubt;
import com.gy.hsxt.tc.bean.GpChImbalance;
import com.gy.hsxt.tc.bean.GpChSummary;
import com.gy.hsxt.tc.bean.PsAcImbalance;
import com.gy.hsxt.tc.bean.PsAcSummary;
import com.gy.hsxt.tc.bean.TcJob;

/**
 * 对账中心接口测试类
 * 
 * @Package: com.gy.hsxt.tc.service.test
 * @ClassName: TcServiceTest
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-18 下午5:36:18
 * @version V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-global.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TcServiceTest {

	private static String tcSys = "PS-AC";

	private static Integer tcState = 1;

	private static Integer tcResult = 0;

	private static Integer imbalanceType = 1;

	private static String endDate = "2016-11-16";

	private static String beginDate = "2014-11-12";

	private static Page page = new Page(1, 2);

	@Autowired
	private ITcBsAcService bsAcService;

	@Autowired
	private ITcGpChService gpChService;

	@Autowired
	private ITcBsGpService bsgpService;

	@Autowired
	private ITcPsAcService psAcService;

	@Autowired
	private ITcJobService jobService;

	@Test
	public void test_1_0() {
		PageData<BsAcSummary> list = bsAcService.querySummary(beginDate,
				endDate, tcResult, page);
		System.out.println(JSON.toJSONString(list));
		if (!list.getResult().isEmpty()) {
			BsAcSummary vo = list.getResult().get(0);
			Assert.assertEquals(tcResult, vo.getTcResult());
		}
	}

	@Test
	public void test_1_1() {
		PageData<BsAcImbalance> list = bsAcService.queryImbalance(beginDate,
				endDate, imbalanceType, page);
		System.out.println(JSON.toJSONString(list));
		if (!list.getResult().isEmpty()) {
			BsAcImbalance vo = list.getResult().get(0);
			Assert.assertEquals(imbalanceType, vo.getImbalanceType());
		}
	}

	@Test
	public void test_2_0() {
		PageData<GpChSummary> list = gpChService.querySummary(beginDate,
				endDate, tcResult, page);
		System.out.println(JSON.toJSONString(list));
		if (!list.getResult().isEmpty()) {
			GpChSummary vo = list.getResult().get(0);
			Assert.assertEquals(tcResult, vo.getTcResult());
		}
	}

	@Test
	public void test_2_1() {
		PageData<GpChImbalance> list = gpChService.queryImbalance(beginDate,
				endDate, imbalanceType, page);
		System.out.println(JSON.toJSONString(list));
		if (!list.getResult().isEmpty()) {
			GpChImbalance vo = list.getResult().get(0);
			Assert.assertEquals(imbalanceType, vo.getImbalanceType());
		}
	}

	@Test
	public void test_2_3() {
		PageData<GpChDoubt> list = gpChService.queryDoubt(beginDate, endDate,
				imbalanceType, page);
		System.out.println(JSON.toJSONString(list));
		if (!list.getResult().isEmpty()) {
			GpChDoubt vo = list.getResult().get(0);
			Assert.assertEquals(imbalanceType, vo.getImbalanceType());
		}

	}

	@Test
	public void test_3_0() {
		PageData<BsGpSummary> list = bsgpService.querySummary(beginDate,
				endDate, tcResult, page);
		System.out.println(JSON.toJSONString(list));
		if (!list.getResult().isEmpty()) {
			BsGpSummary vo = list.getResult().get(0);
			Assert.assertEquals(tcResult, vo.getTcResult());
		}
	}

	@Test
	public void test_3_1() {
		PageData<BsGpImbalance> list = bsgpService.queryImbalance(beginDate,
				endDate, imbalanceType, page);
		System.out.println(JSON.toJSONString(list));
		if (!list.getResult().isEmpty()) {
			BsGpImbalance vo = list.getResult().get(0);
			Assert.assertEquals(imbalanceType, vo.getImbalanceType());
		}
	}

	@Test
	public void test_4_0() {
		PageData<PsAcSummary> list = psAcService.querySummary(beginDate,
				endDate, tcResult, page);
		System.out.println(JSON.toJSONString(list));
		if (!list.getResult().isEmpty()) {
			PsAcSummary vo = list.getResult().get(0);
			Assert.assertEquals(tcResult, vo.getTcResult());
		}
	}

	@Test
	public void test_4_1() {
		PageData<PsAcImbalance> list = psAcService.queryImbalance(beginDate,
				endDate, imbalanceType, page);
		System.out.println(JSON.toJSONString(list));
		if (!list.getResult().isEmpty()) {
			PsAcImbalance vo = list.getResult().get(0);
			Assert.assertEquals(imbalanceType, vo.getImbalanceType());
		}
	}

	@Test
	public void test_5_0() {
		PageData<TcJob> list = jobService.queryProgress(beginDate, endDate,
				tcState, tcSys, page);
		System.out.println(JSON.toJSONString(list));
		if (!list.getResult().isEmpty()) {
			TcJob vo = list.getResult().get(0);
			Assert.assertEquals(tcResult, vo.getTcState());
			Assert.assertEquals(tcSys, vo.getTcSys());
		}
	}
}
