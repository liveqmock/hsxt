package com.gy.hsxt.es.points.serivce;

import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

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
import com.gy.hsxt.es.bean.QueryResult;
import com.gy.hsxt.es.bean.QuerySingle;

/**
 * @description 消费积分测试用例
 * @author chenhz
 * @createDate 2015-7-30 上午10:32:30
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
@ContextConfiguration(locations =
	{ "classpath:/spring/spring-global.xml" })
public class BaseTestCase
{

	@Autowired
	public IEsPointService pointService;
	
	public void pointRegister()
	{
		Point point = new Point();
		point.setTransType("A13000");
		point.setEquipmentType(8);
		point.setSourceBatchNo("000123");
		point.setChannelType(1);
		point.setOperNo("消费积分(陈宏志)");
		point.setEntCustId("06186630000161594661969920");
		point.setEntResNo("06186630000");
		point.setEquipmentNo("0001");
		point.setPerCustId("0618601000620151130");
		point.setPerResNo("06186010006");
		point.setSourceTransNo("151204154208");
		point.setPointRate("0.10");
		point.setEntPoint("10.000000");
		point.setSourceCurrencyCode("001");
		point.setSourceTransAmount("100.000000");
		point.setSourceTransDate(String.valueOf(new Timestamp(new Date().getTime())));
		point.setTransAmount("100.000000");
		point.setIsDeduction(1);
		try
		{
			PointResult pointResult = pointService.point(point);
			System.out.println("消费者的积分：" + pointResult.getPerPoint());
			System.out.println("交易流水号：" + pointResult.getTransNo());

		} catch (HsException he)
		{
			fail("ErrorCode:" + he.getErrorCode() + " ErrorMessage:" + he.getMessage() + "\n" + he.getStackTrace());
		}
	}

	public void reservedPoint(String transType)
	{
		List<Point> pointList = new ArrayList<Point>();
		Point point = new Point();
		point.setTransType(transType);
		point.setEquipmentType(8);
		point.setSourceBatchNo("2016-03-11");
		point.setChannelType(4);
		point.setOperNo("test");

		point.setEntCustId("06002110000164063559693312");
		point.setEntResNo("06002110000");
		point.setEquipmentNo("12345678900");
		point.setPerCustId("0600211811520151207");
		point.setPerResNo("06002118115");
		point.setSourceTransNo("1000006");
		point.setEntPoint("10.00");
		point.setPointRate("0.20");
		point.setSourceCurrencyCode("001");
		point.setSourceTransAmount("100.00");
		point.setSourceTransDate(new Timestamp(new Date().getTime()).toString());
		point.setTransAmount("100.00");
		point.setOrderNo("18025487888");
		point.setIsDeduction(0);

		pointList.add(point);
		
//		Point point1 = new Point();
//		point1.setTransType(transType);
//		point1.setEquipmentType(8);
//		point1.setSourceBatchNo("20151109");
//		point1.setChannelType(4);
//		point1.setOperNo("消费积分(陈宏志2)");
//
//		point1.setEntCustId("12345698712");
//		point1.setEntResNo("12345678910");
//		point1.setEquipmentNo("12345678900");
//		point1.setPerCustId("00123456789");
//		point1.setPerResNo("07418522986");
//		point1.setSourceTransNo("PS120151013064222222222");
//		point1.setEnterprisePoint("22.00");
//		point1.setSourceCurrencyCode("002");
//		point1.setSourceTradeAmount("88.88");
//		point1.setSourceTradeDate(new Timestamp(new Date().getTime()).toString());
//		point1.setTradeAmount("88.88");
//
//		pointList.add(point1);

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
			fail("ErrorCode:" + he.getErrorCode() + " ErrorMessage:" + he.getMessage() + "\n" + he.getStackTrace());
		}
	}
	
	public void statementPoint(String transType)
	{
		List<Point> pointList = new ArrayList<Point>();
		Point point = new Point();
		point.setTransType(transType);
		point.setEquipmentType(8);
		point.setSourceBatchNo("2016-03-10");
		point.setChannelType(4);
		point.setOperNo("消费积分(陈宏志111)");

		point.setOldTransNo("1020160311174819000000");
		point.setEntCustId("06002110000164063559693312");
		point.setEntResNo("06002110000");
		point.setEquipmentNo("12345678900");
		point.setPerCustId("0600211811520151207");
		point.setPerResNo("06002118115");
		point.setSourceTransNo("1000006");
		point.setEntPoint("10.00");
		point.setSourceCurrencyCode("001");
		point.setSourceTransAmount("100.00");
		point.setSourceTransDate(new Timestamp(new Date().getTime()).toString());
		point.setTransAmount("100.00");
		point.setOrderNo("123456789");
		
		point.setIsDeduction(0);

		pointList.add(point);
		
//		Point point1 = new Point();
//		point1.setTransType(transType);
//		point1.setEquipmentType(8);
//		point1.setSourceBatchNo("20151109");
//		point1.setChannelType(4);
//		point1.setOperNo("消费积分(陈宏志2)");
//
//		point1.setEntCustId("12345698712");
//		point1.setEntResNo("12345678910");
//		point1.setEquipmentNo("12345678900");
//		point1.setPerCustId("00123456789");
//		point1.setPerResNo("07418522986");
//		point1.setSourceTransNo("PS120151013064222222222");
//		point1.setEnterprisePoint("22.00");
//		point1.setSourceCurrencyCode("002");
//		point1.setSourceTradeAmount("88.88");
//		point1.setSourceTradeDate(new Timestamp(new Date().getTime()).toString());
//		point1.setTradeAmount("88.88");
//
//		pointList.add(point1);

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

	// 积分
	public void point(String transType)
	{
		Point point = new Point();

		String[] arr =
			{ "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] arr2 =
			{ "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String a = "";
		String b = "";
		String c = "";
		String d = "";
		String e = "";
		String f = "";
		String str = "";
		for (int i = 0; i < 3; i++)
		{
			str += arr2[(int) (Math.random() * 8 + 1)];
		}
		int amount = Integer.parseInt(str);
		for (int i = 0; i < 11; i++)
		{
			a += arr[(int) (Math.random() * 9 + 1)];
			b += arr[(int) (Math.random() * 9 + 1)];
			c += arr[(int) (Math.random() * 9 + 1)];
			d += arr[(int) (Math.random() * 9 + 1)];
			e += arr[(int) (Math.random() * 9 + 1)];
			f += arr[(int) (Math.random() * 9 + 1)];
		}

		point.setTransType(transType);
		point.setEquipmentType(1);
		point.setSourceBatchNo("20150909");
		point.setChannelType(1);
		point.setOperNo("消费积分(陈宏志)");
		point.setEntCustId(a);
		point.setEntResNo(b);
		point.setEquipmentNo(c);
		point.setPerCustId(d);
		point.setPerResNo(e);
		point.setSourceTransNo(f);

		// point.setEntCustId("12345698712");
		// point.setEntResNo("12345678910");
		// point.setEquipmentNo("12345678900");
		// point.setPerCustId("00123456789");
		// point.setPerResNo("07418522986");
		// point.setSourceTransNo("78945612300");
		point.setPointRate(String.valueOf(new BigDecimal(0.20)));
		point.setSourceCurrencyCode("001");
		point.setSourceTransAmount(String.valueOf(new BigDecimal(amount)));
		point.setSourceTransDate(String.valueOf(new Timestamp(new Date().getTime())));
		point.setTransAmount(String.valueOf(new BigDecimal(amount)));

		try
		{
			//PointResult pointResult = pointService.point(point);
			// System.out.println("消费者的积分：" + pointResult.getPerPoint());
			// System.out.println("交易流水号：" + pointResult.getTransNo());

		} catch (HsException he)
		{
			fail("ErrorCode:" + he.getErrorCode() + " ErrorMessage:" + he.getMessage() + "\n" + he.getStackTrace());
		}

	}

	// 退货
	public void back(String transNo, String transType)
	{
		Back back = new Back();
		back.setTransType(transType);
		back.setBackPoint("10");
		back.setSourceBatchNo("2016-03-11");
		back.setEquipmentNo("0001");
		back.setOldTransNo(transNo);
		back.setOperNo("刘超");
		back.setEntResNo("06002110000");
		back.setSourceTransAmount("100");
		back.setSourceTransDate(DateUtil.DateToString(new Date()));
		back.setSourceTransNo("1000006");
		back.setTransAmount("100");
		back.setOrderNo("123456789");
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

	// 退货
	public void returnBackPoint(String transNo, String transType)
	{
		Back back = new Back();
		back.setTransType(transType);
		back.setBackPoint("0.08");
		back.setSourceBatchNo("2016-03-02");
		//back.setEquipmentNo("0001");
		back.setOldTransNo(transNo);
		back.setOperNo("刘超");
		back.setEntResNo("06002110000");
		back.setSourceTransAmount("1.2");
		back.setSourceTransDate(DateUtil.DateToString(new Date()));
		back.setSourceTransNo("2753269941126144");
		back.setTransAmount("1.2");

		try
		{
			BackResult backResult = pointService.returnBackPoint(back);
			System.out.println("消费者的积分：" + backResult.getPerPoint());
			System.out.println("交易流水号：" + backResult.getTransNo());

		} catch (HsException he)
		{
			fail("ErrorCode:" + he.getErrorCode() + " ErrorMessage:" + he.getMessage() + "\n" + he.getStackTrace());
		}

	}


	// 退货退款
	public void returnBackAmount(String transNo, String transType)
	{
		Back back = new Back();
		back.setTransType(transType);
		back.setBackPoint("0.08");
		back.setSourceBatchNo("2016-03-02");
		//back.setEquipmentNo("0001");
		back.setOldTransNo(transNo);
		back.setOperNo("刘超");
		back.setEntResNo("06002110000");
		back.setSourceTransAmount("1.2");
		back.setSourceTransDate(DateUtil.DateToString(new Date()));
		back.setSourceTransNo("2753574924387328");
		back.setTransAmount("1.2");

		try
		{
			BackResult backResult = pointService.returnBackAmount(back);
			System.out.println("消费者的积分：" + backResult.getPerPoint());
			System.out.println("交易流水号：" + backResult.getTransNo());

		} catch (HsException he)
		{
			fail("ErrorCode:" + he.getErrorCode() + " ErrorMessage:" + he.getMessage() + "\n" + he.getStackTrace());
		}

	}


	// 撤单
	public void cancel()
	{
		Cancel cancel = new Cancel();
		cancel.setTransType("A11100");
		cancel.setEquipmentNo("0001");
		cancel.setOldTransNo("1020160311172628000000");
		cancel.setOperNo("小黄蓉");
		cancel.setSourceBatchNo("2016-03-11");
		cancel.setSourceTransDate(DateUtil.DateToString(new Date()));
		cancel.setEntResNo("06002110000");
		cancel.setSourceTransNo("1000004");
		cancel.setOrderNo("123456789");

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

	// 冲正
	public void correct()
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

	// 单笔交易查询
	public void singleQuery(String transNumber)
	{
		try
		{
			QueryResult queryResult = new QueryResult();
			//queryService.singleQuery(transNumber,"6186115648");
			String transNo = queryResult.getTransNo();
			String transType = queryResult.getTransType();
			String entPoint = queryResult.getEntPoint().toString();
			String pointRate = queryResult.getPointRate().toString();
			String batchNo = queryResult.getBatchNo();
			String entResNo = queryResult.getEntResNo();
			String perResNo = queryResult.getPerResNo();
			String sourceTransNo = queryResult.getSourceTransNo();
			String timestamp = queryResult.getSourceTransDate();
			System.out.println("【交易流水号】 " + transNo);
			System.out.println("【交易类型】" + transType);
			System.out.println("【积分预付款】" + entPoint);
			System.out.println("【积分比例】" + pointRate);
			System.out.println("【批次号】" + batchNo);
			System.out.println("【企业互生号】" + entResNo);
			System.out.println("【消费者互生号】" + perResNo);
			System.out.println("【原始交易号】" + sourceTransNo);
			System.out.println("【原始交易时间】" + timestamp);

		} catch (HsException he)
		{
			fail("ErrorCode:" + he.getErrorCode() + " ErrorMessage:" + he.getMessage() + "\n" + he.getStackTrace());
		}
	}
	// 单笔POS 交易查询
	public void singlePosQuery(QuerySingle querySingle)
	{
		try
		{
			QueryResult queryResult = new QueryResult();
//			queryService.singlePosQuery(querySingle);
			String transNo = queryResult.getTransNo();
			String transType = queryResult.getTransType();
			String entPoint = queryResult.getEntPoint().toString();
			String pointRate = queryResult.getPointRate().toString();
			String batchNo = queryResult.getBatchNo();
			String entResNo = queryResult.getEntResNo();
			String perResNo = queryResult.getPerResNo();
			String sourceTransNo = queryResult.getSourceTransNo();
			String timestamp = queryResult.getSourceTransDate();
			System.out.println("【交易流水号】 " + transNo);
			System.out.println("【交易类型】" + transType);
			System.out.println("【积分预付款】" + entPoint);
			System.out.println("【积分比例】" + pointRate);
			System.out.println("【批次号】" + batchNo);
			System.out.println("【企业互生号】" + entResNo);
			System.out.println("【消费者互生号】" + perResNo);
			System.out.println("【原始交易号】" + sourceTransNo);
			System.out.println("【原始交易时间】" + timestamp);

		} catch (HsException he)
		{
			fail("ErrorCode:" + he.getErrorCode() + " ErrorMessage:" + he.getMessage() + "\n" + he.getStackTrace());
		}
	}
	

}
