package com.gy.hsxt.es.points.serivce;

import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.es.api.IEsPointService;
import com.gy.hsxt.es.bean.Back;
import com.gy.hsxt.es.bean.BackResult;
import com.gy.hsxt.es.bean.Cancel;
import com.gy.hsxt.es.bean.CancelResult;
import com.gy.hsxt.es.bean.Correct;
import com.gy.hsxt.es.bean.Point;
import com.gy.hsxt.es.bean.PointResult;
import com.gy.hsxt.es.bean.QuetyPaying;
		
/**   
 * Simple to Introduction
 * @category          Simple to Introduction
 * @projectName   hsec-template-service
 * @package           com.gy.hsec.template.AreaTemServiceTest.java
 * @className       AreaTemServiceTest
 * @description      一句话描述该类的功能 
 * @author              penggs
 * @createDate       2015-5-15 上午11:41:25  
 * @updateUser      penggs
 * @updateDate      2015-5-15 上午11:41:25
 * @updateRemark 说明本次修改内容
 * @version              v0.0.1
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={ "classpath:/spring/spring-global.xml" })
public class PointServiceTest {
	
	@Autowired
	private IEsPointService pointService;
	
	@Test
	public void proceedsDetail(){
		//queryService.proceedsDetail("");
	}
	

	@Test
	public void point()
	{
		Point point = new Point();
		point.setTransType("A11300");
		point.setEquipmentType(8);
		point.setSourceBatchNo("20151216");
		point.setChannelType(4);
		point.setOperNo("消费积分");

		point.setEntCustId("06186630000161594661969920");
		point.setEntResNo("06186630000");
		point.setEntName("hahaha");
		point.setOrderNo("11111111111111111111");
		point.setEquipmentNo("12345678900");
		point.setPerCustId("0618601000620151130");
		point.setPerResNo("06186010006");
		point.setSourceTransNo("10120151013064222122112");
		point.setEntPoint("10.00");
		point.setSourceCurrencyCode("001");
		point.setSourceTransAmount("100.00");
		point.setSourceTransDate(new Timestamp(new Date().getTime()).toString());
		point.setTransAmount("100.00");
		
		point.setIsDeduction(0);
		
		try
		{
			PointResult pointResult = pointService.point(point);
			System.out.println("消费者的积分：" + pointResult.getPerPoint());
			System.out.println("交易流水号：" + pointResult.getTransNo());
		} catch (HsException he)
		{
			he.getStackTrace();
		}
	}
	
	// 退货预扣
	@Test
	public void pointWithhold()
	{
		Point point = new Point();
		point.setTransType("A11500");
		point.setEquipmentType(8);
		point.setSourceBatchNo("20160122");
		point.setChannelType(4);
		point.setOperNo("退货预扣");

		point.setEntCustId("06186630000161594661969920");
		point.setEntResNo("06186630000");
		point.setEquipmentNo("12345678900");
		point.setPerCustId("0618601000620151130");
		point.setPerResNo("06186010006");
		point.setSourceTransNo("10120151013064222122012");
		point.setEntPoint("10.00");
		point.setSourceCurrencyCode("001");
		point.setSourceTransAmount("100.00");
		point.setSourceTransDate(new Timestamp(new Date().getTime()).toString());
		point.setTransAmount("100.00");
		
		try
		{
			PointResult pointResult = pointService.point(point);
			System.out.println("消费者的积分：" + pointResult.getPerPoint());
			System.out.println("交易流水号：" + pointResult.getTransNo());
		} catch (HsException he)
		{
			he.getStackTrace();
		}
	}
	
	@Test
	public void reservedPoint()
	{
		List<Point> pointList = new ArrayList<Point>();
		Point point = new Point();
		point.setTransType("A11300");
		point.setEquipmentType(8);
		point.setSourceBatchNo("20151216");
		point.setChannelType(4);
		point.setOperNo("消费积分");

		point.setEntCustId("06186630000161594661969920");
		point.setEntResNo("06002110000");
		point.setEquipmentNo("12345678900");
		point.setPerCustId("0618601000620151130");
		point.setPerResNo("06002111724");
		point.setSourceTransNo("10120151013064222122000");
		point.setEntPoint("10.00");
		point.setSourceCurrencyCode("001");
		point.setSourceTransAmount("100.00");
		point.setSourceTransDate(new Timestamp(new Date().getTime()).toString());
		point.setTransAmount("100.00");
		
		point.setIsDeduction(0);
		
		pointList.add(point);
		
		try
		{
			List<PointResult> pointResultList = pointService.reservedPoint(pointList);
			for (int i = 0; i < pointResultList.size(); i++)
			{
				PointResult pointResult = pointResultList.get(i);
				System.out.println("消费者的积分：" + pointResult.getPerPoint());
				System.out.println("交易流水号：" + pointResult.getTransNo());
			}
		} catch (HsException he)
		{
			he.getStackTrace();
		}
	}
	@Test
	public void statementPoint()
	{
		List<Point> pointList = new ArrayList<Point>();
		Point point = new Point();
		point.setTransType("A11400");
		point.setEquipmentType(8);
		point.setSourceBatchNo("20151216");
		point.setChannelType(4);
		point.setOperNo("消费积分");

		point.setOldTransNo("1020160226104344000000");
		point.setEntCustId("06186630000161594661969920");
		point.setEntResNo("06002110000");
		point.setEquipmentNo("12345678900");
		point.setPerCustId("0618601000620151130");
		point.setPerResNo("06002111724");
		point.setSourceTransNo("10120151013064222122000");
		point.setEntPoint("10.00");
		point.setSourceCurrencyCode("001");
		point.setSourceTransAmount("100.00");
		point.setSourceTransDate(new Timestamp(new Date().getTime()).toString());
		point.setTransAmount("100.00");
		
		point.setIsDeduction(0);
		pointList.add(point);
		
		try
		{
			List<PointResult> pointResultList = pointService.statementPoint(pointList);
			for (int i = 0; i < pointResultList.size(); i++)
			{
				PointResult pointResult = pointResultList.get(i);
				System.out.println("消费者的积分：" + pointResult.getPerPoint());
				System.out.println("交易流水号：" + pointResult.getTransNo());
			}
		} catch (HsException he)
		{
			fail("ErrorCode:" + he.getErrorCode() + " ErrorMessage:" + he.getMessage() + "\n" + he.getStackTrace());
		}
	}
	
	@Test
	public void cancelPoint()
	{
		Cancel cancel = new Cancel();
		cancel.setTransType("A11300");
		cancel.setEquipmentNo("0001");
		cancel.setOldTransNo("1020160130120818000000");
		cancel.setOperNo("小黄蓉");
		cancel.setSourceBatchNo("000123");
	//	cancel.setSourceTransDate(new Timestamp(new Date().getTime()).toString());
		cancel.setSourceTransDate(String.valueOf(new Timestamp(new Date().getTime())));
		cancel.setEntResNo("06010110000");
		cancel.setSourceTransNo("2708142800372736");

		try
		{
			CancelResult cancelResult = pointService.cancelPoint(cancel);
			System.out.println("消费者的积分：" + cancelResult.getPerPoint());
			System.out.println("交易流水号：" + cancelResult.getTransNo());

		} catch (HsException he)
		{
			fail("ErrorCode:" + he.getErrorCode() + " ErrorMessage:" + he.getMessage() + "\n" + he.getStackTrace());
		}
	}
	@Test
	public void backPoint()
	{
		Back back = new Back();
		back.setTransType("A11200");
		back.setBackPoint("3");
		back.setSourceBatchNo("000123");
		back.setEquipmentNo("0001");
		back.setOldTransNo("1020160120135818000000");
		back.setOperNo("测试");
		back.setEntResNo("06186630000");
		back.setSourceTransAmount("22.000000");
		back.setSourceTransDate(DateUtil.DateToString(new Date()));
		back.setSourceTransNo("10120151013064222122112");
		back.setTransAmount("22.000000");

		try
		{
			BackResult backResult = pointService.backPoint(back);
			System.out.println("消费者的积分：" + backResult.getPerPoint());
			System.out.println("交易流水号：" + backResult.getTransNo());

		} catch (HsException he)
		{
			fail("ErrorCode:" + he.getErrorCode() + " ErrorMessage:" + he.getMessage() + "\n" + he.getStackTrace());
		}

	}
	@Test
	public void correctPoint()
	{
		Correct correct = new Correct();
		correct.setTransType("A10010");
		correct.setSourceBatchNo("000123");
//		correct.setChannelType(1);
//		correct.setEquipmentType(2);
		correct.setEntResNo("06186630000");
//		correct.setEquipmentNo("0001");
		correct.setInitiate("POS");
		correct.setReturnReason("退货造成账不平2");
		correct.setSourceTransNo("151204154208");
		correct.setTransDate(DateUtil.DateToString(new Date()));

		try
		{
			pointService.returnPoint(correct);
		} catch (HsException he)
		{
			fail("ErrorCode:" + he.getErrorCode() + " ErrorMessage:" + he.getMessage() + "\n" + he.getStackTrace());
		}
	}
	
	//@Test
	// 根据流水号查询详情
	/*public void queryDetailsByTransNo()
	{
		try
		{
			QueryResult queryResult = queryService.queryDetailsByTransNo("1020160127111133000000");

			System.out.println("根据流水号查询详情" + JSON.toJSONString(queryResult));

		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}
	*/
	
	@Test
	//订单是否支付查询
	public void queryPayingTest(){
	    List<QuetyPaying> queryPayingList = new ArrayList<>();
	    
	    QuetyPaying quetyPaying1 = new QuetyPaying();
	    quetyPaying1.setOrderNo("12821912974705664");
	    quetyPaying1.setTransType("A11000");
	    quetyPaying1.setSourceTransNo("2822615905616896");
	    queryPayingList.add(quetyPaying1);
	    
	    QuetyPaying quetyPaying2 = new QuetyPaying();
	    quetyPaying2.setOrderNo("12821912974705664");
	    quetyPaying2.setTransType("A11500");
	    quetyPaying2.setSourceTransNo("2822615905879041");
        queryPayingList.add(quetyPaying2);
	}
}

	