package com.gy.hsxt.ps.points.serivce;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.api.IPsPointService;
import com.gy.hsxt.ps.api.IPsQueryService;
import com.gy.hsxt.ps.bean.Back;
import com.gy.hsxt.ps.bean.BackResult;
import com.gy.hsxt.ps.bean.Cancel;
import com.gy.hsxt.ps.bean.CancelResult;
import com.gy.hsxt.ps.bean.Correct;
import com.gy.hsxt.ps.bean.Point;
import com.gy.hsxt.ps.bean.PointRecordResult;
import com.gy.hsxt.ps.bean.PointResult;
import com.gy.hsxt.ps.bean.QueryDetail;
import com.gy.hsxt.ps.bean.AllocDetailSum;
import com.gy.hsxt.ps.bean.QueryPointRecord;
import com.gy.hsxt.ps.bean.QueryResult;
import com.gy.hsxt.ps.bean.QuerySingle;
import com.gy.hsxt.ps.common.PsException;

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
	private IPsPointService pointService;
	@Autowired
	private IPsQueryService queryService;

	public void pointRegister()
	{
		Point point = new Point();
		point.setTransType("A23000");
		point.setEquipmentType(8);
		point.setSourceBatchNo("000123");
		point.setChannelType(1);
		point.setOperNo("消费积分(陈宏志)");
		point.setEntCustId("06186630000161594661969920");
		point.setEntResNo("06186630000");
		point.setEquipmentNo("0001");
		point.setPerCustId("0500108123720151217");
		point.setPerResNo("05001081237");
		point.setSourceTransNo("151204154208");
		point.setPointRate("0.10");
		//point.setEntPoint("10.000000");
		point.setSourceCurrencyCode("001");
		point.setSourceTransAmount("108.00");
		point.setSourceTransDate(String.valueOf(new Timestamp(new Date().getTime())));
		point.setTransAmount("108.00");

		try
		{
			PointResult pointResult = pointService.pointRegister(point);
			System.out.println("消费者的积分：" + pointResult.getPerPoint());
			System.out.println("交易流水号：" + pointResult.getTransNo());

		} catch (HsException he)
		{
			fail("ErrorCode:" + he.getErrorCode() + " ErrorMessage:" + he.getMessage() + "\n" + he.getStackTrace());
		}
	}
	
	public void point(String transType, int type)
	{
		Point point = new Point();
		point.setTransType(transType);
		point.setEquipmentType(8);
		point.setSourceBatchNo("20160906");
		point.setChannelType(1);
		point.setOperNo("消费积分(陈宏志)");
		point.setEntCustId("06002110000164063559693312");
		point.setEntResNo("06002110000");
		point.setEquipmentNo("0001");
		point.setPerCustId("0500108123620151217");
		point.setPerResNo("05001081236");
		point.setTermRunCode("011341");
		point.setSourceTransNo("601012303553");
		point.setPointRate("0.220");
	//	point.setPointSum("100.000000");
		point.setSourceCurrencyCode("001");
		point.setSourceTransAmount("799.95");
		point.setSourceTransDate(String.valueOf(new Timestamp(new Date().getTime())));
		point.setTransAmount("799.95");

		try
		{
			PointResult pointResult = pointService.point(point);
			System.out.println("消费者的积分：" + pointResult.getPerPoint());
			System.out.println("交易流水号：" + pointResult.getTransNo());

		} catch (HsException he)
		{
			// 抛出互生异常
			 PsException.psThrowException(new Throwable().getStackTrace()[0], RespCode.PS_AC_ERROR.getCode(),he.getMessage(),he);
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
			PointResult pointResult = pointService.point(point);
			// System.out.println("消费者的积分：" + pointResult.getPerPoint());
			System.out.println("交易流水号：" + pointResult.getTransNo());

		} catch (HsException he)
		{
			fail("ErrorCode:" + he.getErrorCode() + " ErrorMessage:" + he.getMessage() + "\n" + he.getStackTrace());
		}

	}

	// 退货
	public void back()
	{
		Back back = new Back();
		back.setTransType("A21200");
	//	back.setBackPoint("10");
		back.setSourceBatchNo("2016-03-21");
		back.setEquipmentNo("0001");
		back.setOperNo("测试");
		back.setOldTransNo("031458542708");
		back.setEntResNo("06186630000");
		back.setTermRunCode("211040");
		back.setSourceTransAmount("60.00");
		back.setSourceTransDate(DateUtil.DateToString(new Date()));
		back.setSourceTransNo("031458542708");
		back.setOldSourceTransNo("031458542708");
		back.setTransAmount("60.00");
		
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

	// 撤单
	public void cancel()
	{
		Cancel cancel = new Cancel();
		cancel.setTransType("A11100");
		cancel.setEquipmentNo("0001");
		cancel.setOldTransNo("160301135915");
		cancel.setOperNo("小黄蓉");
		cancel.setTermRunCode("211040");
		cancel.setSourceBatchNo("2016-03-21");
		cancel.setSourceTransDate(DateUtil.DateToString(new Date()));
		cancel.setEntResNo("06186630000");
		cancel.setSourceTransNo("031457517002");
		cancel.setOldSourceTransNo("011458552338");
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
		correct.setChannelType(1);
		correct.setEquipmentType(2);
		correct.setEntResNo("06186630000");
		correct.setEquipmentNo("0001");
		correct.setInitiate("POS");
		correct.setTermRunCode("001346");
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

	// 单笔POS 交易查询
	public void singlePosQuery(QuerySingle querySingle)
	{
		try
		{
			QueryResult queryResult = queryService.singlePosQuery(querySingle);
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
	
	// 查询消费积分记录
	public void queryPoint()
	{
		try
		{
			QueryPointRecord pointRecord = new QueryPointRecord();
			
			pointRecord.setEntCustId("0500108123820151217");
			pointRecord.setHsResNo("05001081238");
			pointRecord.setSourceTransNo("");
			pointRecord.setStartDate("2016-01-12");
			pointRecord.setEndDate("2016-01-13");
			pointRecord.setBusinessType("11");
			
			List<PointRecordResult> list = queryService.pointRecord(pointRecord);
			
			for(PointRecordResult prr : list){
				
				System.out.println("【交易流水号】 " + prr.getTransNo());
				System.out.println("【交易类型】" + prr.getTransType());
				System.out.println("【原始币种金额】" + prr.getSourceCurrencyCode());
				System.out.println("【原始币种金额】" + prr.getSourceTransAmount());
				System.out.println("【交易金额】" + prr.getTransAmount());
				System.out.println("【积分预付款】" + prr.getEntPoint());
				System.out.println("【积分比例】" + prr.getPointRate());
				System.out.println("【批次号】" + prr.getBatchNo());
				System.out.println("【企业互生号】" + prr.getEntResNo());
				System.out.println("【消费者互生号】" + prr.getPerResNo());
				System.out.println("【原始交易号】" + prr.getSourceTransNo());
				System.out.println("【原始交易时间】" + prr.getSourceTransDate());
			}

		} catch (HsException he)
		{
			fail("ErrorCode:" + he.getErrorCode() + " ErrorMessage:" + he.getMessage() + "\n" + he.getStackTrace());
		}
	}
	
	
	// 查询积分明细
	public void queryPointDetail(String transNumber)
	{
		QueryDetail queryDetail = new QueryDetail();
		//queryDetail.setTransNo(transNumber);
		queryDetail.setTransDate(new Date());
		queryDetail.setCustId("");
		queryDetail.setCount(10);
		queryDetail.setNumber(1);
		queryDetail.setBatchNo("20160128");
		try
		{
			AllocDetailSum queryResult = queryService.queryPointDetailSum(queryDetail);

			System.out.println("【getBackCount】 " + queryResult.getBackCount());
			System.out.println("【getBackSum】" + queryResult.getBackSum());
			System.out.println("【getCount】" + queryResult.getCount());
			System.out.println("【getSum】" + queryResult.getSum());
			System.out.println("【getTransDate】" + queryResult.getTransDate());

		} catch (HsException e)
		{
			e.printStackTrace();
		}
	}


	
	
	// 查询消费积分记录
	public void queryRegisterPoint()
	{
		try
		{
			QueryPointRecord pointRecord = new QueryPointRecord();
			
			pointRecord.setEntCustId("0600701000020151231");
//			pointRecord.setHsResNo("06002118106");
//			pointRecord.setSourceTransNo("");
//			pointRecord.setStartDate("2016-01-12");
//			pointRecord.setEndDate("2016-01-20");
			pointRecord.setCount(5);
			pointRecord.setPageNumber(1);
			
			PageData<PointRecordResult> pointRecordResult = queryService.pointRegisterRecord(pointRecord);
			System.out.println(pointRecordResult);

		} catch (HsException he)
		{
			fail("ErrorCode:" + he.getErrorCode() + " ErrorMessage:" + he.getMessage() + "\n" + he.getStackTrace());
		}
	}


}
