package com.gy.hsxt.access.pos.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.dubbo.common.json.JSON;
import com.gy.hsxt.common.constant.Channel;
import com.gy.hsxt.common.constant.CommonConstant;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.ps.bean.Back;
import com.gy.hsxt.ps.bean.BackResult;
import com.gy.hsxt.ps.bean.BatSettle;
import com.gy.hsxt.ps.bean.BatUpload;
import com.gy.hsxt.ps.bean.Cancel;
import com.gy.hsxt.ps.bean.CancelResult;
import com.gy.hsxt.ps.bean.Correct;
import com.gy.hsxt.ps.bean.Point;
import com.gy.hsxt.ps.bean.PointResult;
import com.gy.hsxt.ps.bean.QueryResult;
		
/***************************************************************************
 * <PRE>
 * Description 		: Simple to Introduction
 *
 * Project Name   	: hsxt-access-pos-api
 *
 * Package Name     : com.gy.hsxt.access.pos.service
 *
 * File Name        : TestPsApiService
 * 
 * Author           : wucl
 *
 * Create Date      : 2015-10-28 下午4:06:35  
 *
 * Update User      : wucl
 *
 * Update Date      : 2015-10-28 下午4:06:35
 *
 * UpdateRemark 	: 说明本次修改内容
 *
 * Version          : v0.0.1
 *
 * </PRE>
 ***************************************************************************/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext.xml"})
public class TestPsApiService {

	@Autowired
	@Qualifier("psApiService")
	private PsApiService psApiService;
	
	@Before  
    public void setUp() throws Exception { 
    }  
  
    @After  
    public void tearDown() throws Exception {  
    } 
    
    
    
    /**
     * 积分
     * @throws Exception
     */
    @Test
    public void testPoint() throws Exception{
    	try{
    		Point point = new Point();
    		//交易类型 必输
    		point.setTransType(TransType.LOCAL_CARD_LOCAL_POINT.getCode());
    		
    		//企业互生号(42企业编号) 必输
    		point.setEntResNo("06186630000");
    		//消费者互生号(卡号) 非必输
    		point.setPerResNo("06186010006");//06186010006
    		
    		//设备编号（POS编号？） 非必输
    		point.setEquipmentNo("0001");
    		
    		//渠道类型 必输
    		point.setChannelType(Channel.POS.getCode());//1
    		//设备类型 必输
    		point.setEquipmentType(CommonConstant.EQUIPMENT_POS.getCode());//2
    		
    		//原始交易号(11 终端流水号？) 必输
    		point.setSourceTransNo("000025305963");
    		
    		//原始交易时间 必输
    		point.setSourceTransDate("2015-11-18 09:59:40");
    		
    		//原始交易币种代号（49货币代号） 必输
    		point.setSourceCurrencyCode("156");//313536
    		
    		//原始币种金额 必输
    		point.setSourceTransAmount("100.00");
    		
    		//交易金额（4 交易金额） 必输
    		point.setTransAmount("100.00");
    		
    		//积分比例 必输
    		point.setPointRate("0.1");
    		
    		//企业客户号（UC查询） 必输
    		point.setEntCustId("1111");
    		//消费者客户号（UC查询） 非必输
    		point.setPerCustId("6186115648");
    		
    		//操作员（63操作员编号） 非必输
    		point.setOperNo("0001");
    		
    		point.setSourceBatchNo("000001");
    		
    		point.setTermTypeCode("0000");
    		point.setTermTradeCode("63");
    		point.setTermRunCode("000001");
    		
    		System.out.println("--------------------");
    		
    		PointResult pr = psApiService.point(point);
    		
    		System.out.println("---------------------返回的流水号："+pr.getTransNo());
    		
    		System.out.println("---------------------返回消费者的积分："+pr.getPerPoint());
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    @Test
    public void testPointCancel() {
    	Cancel cancel = new Cancel();
    	//撤销
    	final String transType = TransType.LOCAL_CARD_LOCAL_POINT_CANCEL.getCode();
    	cancel.setTransType(transType);
    			
    	cancel.setOldTransNo("000025305961");
    	cancel.setSourceTransNo("000025305962");
    	cancel.setSourceTransDate("2015-11-18 11:15:40");
    	cancel.setSourceBatchNo("000001");
    	//设备编号（POS编号？） 非必输
    	cancel.setEquipmentNo("0001");
    	//操作员（63操作员编号） 非必输
    	cancel.setOperNo("0001");
    	cancel.setEntResNo("06186630000");
    	try{
    		CancelResult cr = psApiService.cancelpoint(cancel);
    		System.out.println("-----返回的"+cr.getTransNo()+"   "+cr.getPerPoint()+"   "+cr.getTransAmount());
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    //@Test
    public void testReturn(){
    	Correct correct = new Correct();
    	correct.setTransType(TransType.LOCAL_CARD_LOCAL_POINT_AUTO_REVERSE.getCode());
    	//企业互生号(42企业编号) 必输
    	correct.setEntResNo("06186630000");
    	//设备编号（POS编号？） 非必输
    	correct.setEquipmentNo("0001");
    	//设备类型 必输
    	correct.setEquipmentType(CommonConstant.EQUIPMENT_POS.getCode());//2
    	//渠道类型 必输
    	correct.setChannelType(Channel.POS.getCode());//1
    	correct.setSourceTransNo("000025305938");
    	correct.setSourceBatchNo("000001");
    	correct.setReturnReason("0");
    	correct.setInitiate("POS");
    	correct.setTransDate("2015-11-05 10:15:40");
    	try{
    		psApiService.reversePoint(correct);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    /**
     * 互生币积分
     * @throws Exception
     */
    @Test
    public void testHSBPoint() throws Exception{
    	try{
    		Point point = new Point();
    		//交易类型 必输
    		point.setTransType("A10000");
    		
    		//企业互生号(42企业编号) 必输
    		point.setEntResNo("06186630000");
    		//消费者互生号(卡号) 非必输
    		point.setPerResNo("06186115648");//06186010006
    		
    		//设备编号（POS编号？） 非必输
    		point.setEquipmentNo("0001");
    		
    		//渠道类型 必输
    		point.setChannelType(Channel.POS.getCode());//1
    		//设备类型 必输
    		point.setEquipmentType(CommonConstant.EQUIPMENT_POS.getCode());//2
    		
    		//原始交易号(11 终端流水号？) 必输
    		point.setSourceTransNo("000025305967");
    		
    		//原始交易时间 必输
    		point.setSourceTransDate("2015-11-19 09:12:40");
    		
    		//原始交易币种代号（49货币代号） 必输
    		point.setSourceCurrencyCode("156");//313536
    		
    		//原始币种金额 必输
    		point.setSourceTransAmount("100.00");
    		
    		//交易金额（4 交易金额） 必输
    		point.setTransAmount("100.00");
    		
    		//积分比例 必输
    		point.setPointRate("0.1");
    		
    		//企业客户号（UC查询） 必输
    		point.setEntCustId("1111");
    		//消费者客户号（UC查询） 非必输
    		point.setPerCustId("6186115649");
    		
    		//操作员（63操作员编号） 非必输
    		point.setOperNo("0001");
    		
    		point.setSourceBatchNo("000001");
    		
    		System.out.println("---------------------");
    		
    		PointResult pr = psApiService.point(point);
    		
    		System.out.println("---------------------返回的流水号："+pr.getTransNo());
    		
    		System.out.println("---------------------返回消费者的积分："+pr.getPerPoint());
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    @Test
	public void testBackPoint() {
		Back obj = new Back();
		obj.setTransType(TransType.LOCAL_CARD_LOCAL_HSB_BACK.getCode());
		//原交易号
		obj.setOldTransNo("000025305938");
		//设备编号（POS编号？）
		obj.setEquipmentNo("0001");
		//原始交易号(11 终端流水号？) 必输
		obj.setSourceTransNo("000025305938");
		obj.setSourceBatchNo("000001");
		//原始交易时间 必输
		obj.setSourceTransDate("2015-11-05 09:59:40");
		//原始币种金额 必输
		obj.setSourceTransAmount("100.00");
		//交易金额（4 交易金额） 必输
		obj.setTransAmount("100.00");
		obj.setBackPoint("");
		//操作员（63操作员编号） 非必输
		obj.setOperNo("0001");
		BackResult ret = null;
		try {
			ret = psApiService.backPoint(obj);
			System.out.println("-----------------------返回来的数据"+ret.getTransNo()+"   "+ret.getPerPoint());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    @Test
    public void testSinglePosQuery(){
    	QueryResult ret = null;
    	try{
    		System.out.println("--------------------");
    		//ret = psApiService.singlePosQuery("000025305953", "6186115648");
//    		System.out.println("<<<<<<<<<<<<<<<"+ret.getOperNo());
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    @Test
    public void testBatSettle() {
        int pointBackCnt = 0;
        String PointBackSum = "0.0";
        int pointCancelCnt = 0;
        String pointCancelSum ="0.0";
        int pointCnt = 0;
        String pointSum = "0.0";
        int hsbBackCnt = 0;
        String hsbBackSum = "0.0";
        int hsbCnt = 0;
        String hsbSum = "0.0";
        
        BatSettle obj = new BatSettle();
        obj.setSourceBatchNo("000001");
        obj.setEntResNo("06186630000");
        obj.setEquipmentNo("0001");
        obj.setEquipmentType(CommonConstant.EQUIPMENT_POS.getCode());
        obj.setPointBackCnt(pointBackCnt);
        obj.setPointBackSum(PointBackSum);
        obj.setPointCancelCnt(pointCancelCnt);
        obj.setPointCancelSum(pointCancelSum);
        obj.setPointCnt(pointCnt);
        obj.setPointSum(pointSum);
        obj.setHsbBackCnt(hsbBackCnt);
        obj.setHsbBackSum(hsbBackSum);
        obj.setHsbCnt(hsbCnt);
        obj.setHsbSum(hsbSum);
        try
        {
            System.out.println("-----------------");
            psApiService.batSettle(obj);
            System.out.println("-----------------");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void batUpload() {
        List<BatUpload> obj = new LinkedList<BatUpload>();

        String sourceTransNo = "000025305956";
        String entPoint = "5";
        String perPoint = "5";
        String pointRate = "0.1";
        String transAmount = "100";
        String transNo = "000025305939";
        
        BatUpload bu = new BatUpload();
        
        bu.setBatchNo("000002");
        bu.setEntPoint(entPoint);
        bu.setEntResNo("06186630000");
        bu.setEquipmentNo("0001");
        bu.setEquipmentType(CommonConstant.EQUIPMENT_POS.getCode());
        bu.setPerPoint(perPoint);
        bu.setPerResNo("06186115648");
        bu.setPointRate(pointRate);
        bu.setSourceTransNo(sourceTransNo);
        bu.setTransAmount(transAmount);
        bu.setTransNo(transNo);
        obj.add(bu);
        try
        {
            System.out.println("-----------------");
            psApiService.batUpload(obj);
            System.out.println("-----------------");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

	