package com.gy.hsxt.es.points.serivce;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.es.bean.Proceeds;
import com.gy.hsxt.es.bean.QuerySingle;

/**
 * @description 消费积分测试用例
 * @author chenhz
 * @createDate 2015-7-30 上午10:32:30
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class PointTestCase extends BaseTestCase {

	
	/*******************************************  积分测试用例   ****************************************/
	/**
	 * 查询积分记录(可撤单或可退货)
	 */
	@Test
	public void queryPoints(){
		//queryPoint();
	}
	/**
	 *  持卡人本地消费(预扣)
	 */
	@Test
	public void point(){
		pointRegister();
	}
	
	/**
	 *  持卡人本地消费(预扣)
	 */
	@Test
	public void reservedPoint(){
		reservedPoint("A10300");
	}
	
	/**
	 *  持卡人本地消费(结单)
	 */
	@Test
	public void statementPoint(){
		statementPoint("A10400");
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
		back("10120151210095416000000","A10200");
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
	public void querySingle(){
		singleQuery("10120151105112440000000");
	}

	/**
	 *  单笔交易查询
	 */
	@Test
	public void singleQuery(){
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
		//queryPointDetail("PS120150818090216000000");
	}
	
	/**
	 *  查询互生币明细
	 */
	@Test
	public void queryHSBDetail(){
		//queryPointDetail("PS120150818090216000000");
	}


	/**
	 *  查询查询税收详单
	 */
	@Test
	public void queryTaxResult(){
		Proceeds proceeds=new Proceeds();
		proceeds.setTransNo("220120160121181903000000");
		//queryService.queryTaxDetail(proceeds);
	}

	// 根据流水号查询详情
	public void queryDetailsByTransNo(){
		//queryDetailsByTransNo("1020160127111133000000");
	}



	/**
	 *  日志测试
	 */
	@Test
	public void testLc(){

		SystemLog.info("666666666666","88888888888888888","99999999999999");
		BizLog.biz("666666666666","88888888888888888","99999999999999","000000000000000");
	}
}
