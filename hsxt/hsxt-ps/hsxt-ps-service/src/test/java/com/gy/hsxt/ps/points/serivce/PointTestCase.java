package com.gy.hsxt.ps.points.serivce;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.ps.api.IPsQueryService;
import com.gy.hsxt.ps.bean.*;
import com.gy.hsxt.ps.common.PageContext;
import com.gy.hsxt.ps.distribution.service.PointAllocService;
import com.gy.hsxt.ps.querys.mapper.QuerysMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @description 消费积分测试用例
 * @author chenhz
 * @createDate 2015-7-30 上午10:32:30
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class PointTestCase extends BaseTestCase {

	@Autowired
	private PointAllocService pointAllocService;
	
	/*******************************************  积分测试用例   ****************************************/
	/**
	 * 查询积分记录(可撤单或可退货)
	 */
	@Test
	public void queryPoints(){
		queryPoint();
	}
	
	/**
	 *  网上积分登记
	 */
	@Test
	public void pointR() {
		pointRegister();
	}
	
	/**
	 *  网上积分登记查询
	 */
	@Test
	public void queryRP(){
		queryRegisterPoint();
	}
	
	/**
	 *  持卡人本地消费
	 */
	@Test
	public void point(){
		point("A21000",1);
	}
	
	@Test
	public void pointLocal(){
		point("A20000");
	}
	

	/**
	 *  持卡人本地消费 预留
	 */
	@Test
	public void pointLocal3(){
		point("A20300");
	}
	
	/**
	 *  持卡人本地消费 预留结单
	 */
	@Test
	public void pointLocal4(){
		point("A30400");
	}
	
	/**
	 *  非持卡人本地消费
	 */
	@Test
	public void pointLocalNotCard(){
		point("B30020");
	}
	
	/**
	 *  非持卡人本地消费 预留
	 */
	@Test
	public void pointLocalNotCard3(){
		point("B30320");
	}
	
	/**
	 *  非持卡人本地消费 预留结单
	 */
	@Test
	public void pointLocalNotCard4(){
		point("B30420");
	}
	
	/**
	 *  本地持卡人异地消费
	 */
	@Test
	public void pointCardAllopatry(){
		point("C30020");
	}

	/**
	 *  本地持卡人异地消费
	 */
	@Test
	public void pointAllopatryCard(){
		point("E30020");
	}
	/************************************************  end  ******************************************/
	
	
	
	/*******************************************  退货测试用例   ****************************************/
	/**
	 *  持卡人本地退货
	 */
	@Test
	public void backPoint(){
		back();
	}
	/************************************************  end  ******************************************/
	
	
	/******************************************* 撤单测试用例   ****************************************/
	/**
	 *  撤单测试用例
	 */
	@Test
	public void cancelPoint(){
		cancel();
	}
	/************************************************  end  ******************************************/

	
	/******************************************* 冲正测试用例   ****************************************/
	/**
	 *  冲正测试用例
	 */
	@Test
	public void correctPoint(){
		correct();
	}
	/************************************************  end  ******************************************/
	


	/**
	 *  单笔交易查询
	 */
	@Test
	public void singlePosQuery(){
		QuerySingle querySingle = new QuerySingle();
		querySingle.setSourceTransNo("151204154208");
		querySingle.setEntResNo("06186630000");
		querySingle.setEntCustId("06186630000161594661969920");
		querySingle.setEquipmentNo("0001");
		singlePosQuery(querySingle);
	}

	/**
	 *  查询积分明细
	 */
	@Test
	public void queryPiontDetail(){
		queryPointDetail("PS120150818090216000000");
	}
	
	/**
	 *  查询互生币明细
	 */
	@Test
	public void queryHSBDetail(){
		queryPointDetail("PS120150818090216000000");
	}

	/**
	 *  日终退税
	 */
	@Test
	public void dailyBackTax(){
		String  runDate= DateUtil.DateToString(DateUtil.addDays(DateUtil.today(),-1));
		pointAllocService.dailyBackTax(runDate);
	}
	/**
	 *  日志测试
	 */
	@Test
	public void testLc(){

		SystemLog.info("666666666666","88888888888888888","99999999999999");
		BizLog.biz("666666666666","88888888888888888","99999999999999","000000000000000");
	}
	@Autowired
	private QuerysMapper queryMapper;

	@Test
	public void testPointRegisterRecord(){
		QueryPointRecord queryPointRecord=new QueryPointRecord();
		queryPointRecord.setPageNumber(0);
		queryPointRecord.setCount(100);
		queryPointRecord.setEntCustId("0600104000020151221");
		queryPointRecord.setHsResNo("0600");
		queryPointRecord.setStartDate("20150227");
		queryPointRecord.setEndDate("20160227");
		//queryPointRecord.setBusinessType("123");
		// 分页
		Page page = new Page(queryPointRecord.getPageNumber(), queryPointRecord.getCount());
		PageContext.setPage(page);
		List<PointRecordResult> prrList = queryMapper.pointRegisterRecordPage(queryPointRecord);
	}

	@Autowired
	private IPsQueryService querysService;
	@Test
	public  void testQueryDetailsByTransNo() {
		QueryResult queryResult=querysService.queryDetailsByTransNo("110120160408153950000000");
		System.out.print(JSON.toJSONString(queryResult));
	}

	@Test
	public  void queryPointNBCPage() {
		QueryPage queryPage=new QueryPage();
		Page page=new Page(1,15);
		queryPage.setPage(page);
		queryPage.setEntResNo("06100010000");
		queryPage.setPerResNo("06031990001");
		queryPage.setQueryType(3);
		PageData<QueryPageResult> queryResult=querysService.queryPointNBCPage(queryPage);
		System.out.print(JSON.toJSONString(queryResult));
	}
}
