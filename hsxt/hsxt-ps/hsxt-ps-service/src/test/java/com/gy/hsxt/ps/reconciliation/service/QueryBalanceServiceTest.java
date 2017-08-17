package com.gy.hsxt.ps.reconciliation.service;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.gy.hsxt.ps.api.IPsSettleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.api.IPsQueryService;
import com.gy.hsxt.ps.bean.BatSettle;
import com.gy.hsxt.ps.bean.BatUpload;

/**
 * Simple to Introduction
 *
 * @category Simple to Introduction
 * @projectName hsxt-ps-service
 * @package com.gy.hsxt.ps.queryBalance.service.QueryBalanceServiceTest.java
 * @className QueryBalanceServiceTest
 * @description 测试批结算、批处理数据对账一致性
 * @author liuchao
 * @createDate 2015-8-3 下午3:31:08
 * @updateUser liuchao
 * @updateDate 2015-8-3 下午3:31:08
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/spring-global.xml" })
public class QueryBalanceServiceTest {

	@Autowired
	private IPsSettleService settleService;

	/**
	 * 披结算功能
	 */
	@Test
	public void batSettle() {
		BatSettle batSettle = new BatSettle();
		batSettle.setSourceBatchNo("110123");
		batSettle.setEntResNo("12345678910");
		batSettle.setEquipmentNo("12345678900");
		batSettle.setEquipmentType(1);
		batSettle.setPointCnt(1);
		BigDecimal bd = new BigDecimal(Double.toString(94.568));
		batSettle.setPointSum(String.valueOf(bd));
		try {
			settleService.batSettle(batSettle);
		} catch (HsException e) {
			fail(e.getErrorCode() + "");
		}
	}



	/**
	 * 披上送明细对账
	 */
	@Test
	public void batUpload() {
		List<BatUpload> list=new ArrayList<BatUpload>();
		BatUpload bu1 = new BatUpload();
		bu1.setBatchNo("110123");
		bu1.setEntPoint("94.283");
		bu1.setEntResNo("12345678910");
		bu1.setEquipmentNo("12345678900");
		bu1.setEquipmentType(1);
		bu1.setPerPoint("93.783");
		bu1.setPerResNo("07418522986");
		bu1.setPointRate("0.2850");
		bu1.setSourceTransNo("78945612300");
		bu1.setTransAmount("94.568");
		bu1.setTransNo("PS120150804060334000000");
		list.add(bu1);
		for (int i = 0; i < 30; i++) {
			BatUpload bu = new BatUpload();
			bu.setBatchNo("1");
			bu.setEntPoint("1");
			bu.setEntResNo("1");
			bu.setEquipmentNo("1");
			bu.setEquipmentType(1);
			bu.setPerPoint("1");
			bu.setPerResNo("1");
			bu.setPointRate("1");
			bu.setSourceTransNo("1");
			bu.setTransAmount("1");
			bu.setTransNo(i + "");
			list.add(bu);
		}
		try {
			this.settleService.batUpload(list);
		} catch (HsException e) {
			fail(e.getErrorCode() + "");
		}
	}





}
