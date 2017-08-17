package com.gy.hsxt.ps;

import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.ps.api.IPsPointService;
import com.gy.hsxt.ps.api.IPsQueryService;
import com.gy.hsxt.ps.api.IPsSettleService;
import com.gy.hsxt.ps.bean.*;
import com.gy.hsxt.ps.common.PsException;
import com.gy.hsxt.ps.common.PsRedisUtil;
import com.gy.hsxt.ps.distribution.service.PointAllocService;
import com.gy.hsxt.ps.settlement.bean.BatchUpload;
import com.gy.hsxt.ps.settlement.handle.PosSettleHandle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import static org.junit.Assert.fail;

/**
 * @description 消费积分测试用例
 * @author chenhz
 * @createDate 2015-7-30 上午10:32:30
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
@ContextConfiguration(locations =
	{ "classpath:/spring/spring-global.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseTestCase
{

	@Autowired
	private PointAllocService pointAllocService;

	@Test
	public void test() {

		Map<String, String> args=new HashMap<>();
		args.put("BATCH_DATE","20160420");
		pointAllocService.batAllocPoint(PosSettleHandle.getJobDate(args));
	}


	@Test
	public void testPsRedisUtil() {

		List<String> entResNoList=new ArrayList<>();
		entResNoList.add("06031990000");
/*		Map<String, String> map=PsRedisUtil.getEntTaxMap(entResNoList);
		System.out.print(map);*/

/*
		Map<String, String> map2=PsRedisUtil.getCustIdMap(entResNoList);
		System.out.print(map2);*/


		List<Object> list=PsRedisUtil.getCustIdList(entResNoList);
		System.out.print(list);


	}


	@Autowired
	private IPsQueryService querysService;
	@Test
	public void testProceedsOnLineEntryList() {


		QueryDetail queryDetail=new QueryDetail();
		queryDetail.setBatchNo("2016-05-07");
		queryDetail.setResNo("06186630000");

		PageData<QueryEntry> queryEntryPageData=querysService.proceedsOnLineEntryList(queryDetail);


		System.out.print(queryEntryPageData);


	}


	@Test
	public void testProceedsOffLineEntryList() {


		QueryDetail queryDetail=new QueryDetail();
		queryDetail.setBatchNo("2016-05-05");
		queryDetail.setResNo("06186630000");

		PageData<QueryEntry> queryEntryPageData=querysService.proceedsEntryList(queryDetail);


		System.out.print(queryEntryPageData);


	}

	@Autowired
	private IPsSettleService PosSettleService;
	@Test
	public void tesbatUpload() {
		List<BatUpload> batUploads=new ArrayList<>();
		BatUpload batUpload=new BatchUpload();
		batUpload.setEntResNo("06031990000");
		batUpload.setSourceTransNo("000000000017");
		batUpload.setEquipmentNo("0001");
		batUpload.setTransType("A23000");
		batUpload.setEquipmentType(2);
		batUpload.setTransNo("100120160418121131000000");
		batUploads.add(batUpload);

		PosSettleService.batUpload(batUploads);

	}
}
