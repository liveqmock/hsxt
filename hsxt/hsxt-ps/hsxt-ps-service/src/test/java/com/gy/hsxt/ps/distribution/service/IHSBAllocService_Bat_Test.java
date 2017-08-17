package com.gy.hsxt.ps.distribution.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gy.hsxt.common.utils.DateUtil;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.bean.BatUpload;
import com.gy.hsxt.ps.common.PsTools;
import com.gy.hsxt.ps.settlement.service.PosSettleService;

/**
 * Simple to Introduction
 * 
 * @category Simple to Introduction
 * @projectName hsxt-ps-service
 * @package
 * @className
 * @description 互生币分配测试用例
 * @author chenhongzhi
 * @createDate 2015-8-7 上午9:25:26
 * @updateUser liuchao
 * @updateDate 2015-8-7 上午9:25:26
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{ "classpath:/spring/spring-global.xml" })
public class IHSBAllocService_Bat_Test
{
	@Autowired
	private PointAllocService pointAllocService;
	@Autowired
	private HsbAllocService hsbAllocService;
	@Autowired
	private PosSettleService posSettleService;

	/**
	 * 月结商业服务费
	 */
	@Test
	public void monthServiceFee()
	{
		String runDate = DateUtil.DateToString(DateUtil.addDays(DateUtil.today(), -1));

		Map<String, String> args = new HashMap<String, String>();
		args.put("BATCH_DATE", "20160331");

		// 获得前一天日期
		if (!CollectionUtils.isEmpty(args))
		{
			String batchDate = args.get("BATCH_DATE");
			if (!PsTools.isEmpty(batchDate))
			{
				Date date = DateUtil.StringToDate(batchDate, DateUtil.DATE_FORMAT);
				System.out.println("date " + date);
				runDate = DateUtil.DateToString(DateUtils.addDays(date, -1));
			}
		}
		hsbAllocService.monthlyBusinessServiceFee(runDate);
	}

	@Test
	public void batchSettle() throws HsException
	{

		String runDate = DateUtil.DateToString(DateUtil.addDays(DateUtil.today(), -1));
		// 分录信息(互生币汇总信息、商业服务费信息)
		hsbAllocService.hsbSummary(runDate);

		// 积分分配
		pointAllocService.batAllocPoint(runDate);

		// 积分税收
		pointAllocService.pointTax(runDate);
		// 积分税后汇总
		pointAllocService.pointSummary(runDate);

	}

	/**
	 * 积分分配
	 */
	@Test
	public void batAllocPoint()
	{
		String runDate = DateUtil.DateToString(DateUtil.addDays(DateUtil.today(), -1));
		Map<String, String> args = new HashMap<String, String>();
		args.put("BATCH_DATE", "20160318");

		// 获得前一天日期
		if (!CollectionUtils.isEmpty(args))
		{
			String batchDate = args.get("BATCH_DATE");
			if (!PsTools.isEmpty(batchDate))
			{
				Date date = DateUtil.StringToDate(batchDate, DateUtil.DATE_FORMAT);
				System.out.println("date " + date);
				runDate = DateUtil.DateToString(DateUtils.addDays(date, -1));
			}
		}
		pointAllocService.batAllocPoint(runDate);
	}

	/**
	 * 互生币汇总(日终商业服务费暂存)
	 */
	@Test
	public void hsbSummary()
	{
		String runDate = DateUtil.DateToString(DateUtil.addDays(DateUtil.today(), -1));

		Map<String, String> args = new HashMap<String, String>();
		args.put("BATCH_DATE", "20160315");

		// 获得前一天日期
		if (!CollectionUtils.isEmpty(args))
		{
			String batchDate = args.get("BATCH_DATE");
			if (!PsTools.isEmpty(batchDate))
			{
				Date date = DateUtil.StringToDate(batchDate, DateUtil.DATE_FORMAT);
				System.out.println("date " + date);
				runDate = DateUtil.DateToString(DateUtils.addDays(date, -1));
			}
		}
		hsbAllocService.hsbSummary(runDate);
	}

	/**
	 * 积分汇总
	 */
	@Test
	public void pointSummary()
	{
		String runDate = DateUtil.DateToString(DateUtil.addDays(DateUtil.today(), -1));
		Map<String, String> args = new HashMap<String, String>();
		args.put("BATCH_DATE", "20160326");

		// 获得前一天日期
		if (!CollectionUtils.isEmpty(args))
		{
			String batchDate = args.get("BATCH_DATE");
			if (!PsTools.isEmpty(batchDate))
			{
				Date date = DateUtil.StringToDate(batchDate, DateUtil.DATE_FORMAT);
				System.out.println("date " + date);
				runDate = DateUtil.DateToString(DateUtils.addDays(date, -1));
			}
		}
		pointAllocService.pointSummary(runDate);
	}

	/**
	 * 积分税收
	 */
	@Test
	public void pointTax()
	{
		String runDate = DateUtil.DateToString(DateUtil.addDays(DateUtil.today(), -1));
		pointAllocService.pointTax(runDate);
	}

	@Test
	public void batUpload()
	{
		List<BatUpload> batUpload = new ArrayList<BatUpload>();
		BatUpload bu = new BatUpload();
		// bu.setBatchNo("02181127");
		bu.setEntResNo("06002110000");
		bu.setEquipmentNo("15615042110000000460");
		bu.setEquipmentType(1);
		bu.setPerResNo("06002111711");
		bu.setPointRate("0.12");
		bu.setSourceTransNo("15615042110000000460160217110024");
		bu.setTransAmount("250.0");
		bu.setTransNo("100120160217110027000000");
		batUpload.add(bu);

		posSettleService.batUpload(batUpload);
	}

}
