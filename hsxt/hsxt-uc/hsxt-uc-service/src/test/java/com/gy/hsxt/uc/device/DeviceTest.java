///*
// * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
// *
// * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
// */
//
//package com.gy.hsxt.uc.device;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Assert;
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.MethodSorters;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.alibaba.fastjson.JSON;
//import com.gy.hsxt.common.bean.PageData;
//import com.gy.hsxt.uc.as.api.device.IUCAsDeviceService;
//import com.gy.hsxt.uc.as.bean.device.AsDevice;
//import com.gy.hsxt.uc.as.bean.device.AsPos;
//import com.gy.hsxt.uc.as.bean.enumerate.AsDeviceTypeEumn;
//import com.gy.hsxt.uc.bs.api.device.IUCBsDeviceService;
//import com.gy.hsxt.uc.bs.bean.device.BsCardReader;
//import com.gy.hsxt.uc.bs.bean.device.BsDevice.BsDeviceStatusEnum;
//import com.gy.hsxt.uc.bs.bean.device.BsPad;
//import com.gy.hsxt.uc.bs.bean.device.BsPos;
//import com.gy.hsxt.uc.bs.enumerate.BsDeviceTypeEumn;
//import com.gy.hsxt.uc.ent.test.EntServiceTest;
//import com.gy.hsxt.uc.ent.test.PrintClassProperty;
//
///**
// * 设备相关服务接口测试类
// * 
// * @Package: com.gy.hsxt.uc.device
// * @ClassName: DeviceTest
// * @Description: TODO
// * 
// * @author: huanggaoyang
// * @date: 2015-10-30 上午11:47:07
// * @version V1.0
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:spring/spring-uc.xml" })
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class DeviceTest {
//
//	private static final String SEPARATOR = "\\|";
//
//	private static final String MAC_NO = "mac100001";
//
//	private static final String INSERT_POINT_RATE = "1.0|3.0|2.0|4.0|5";
//
//	private static final String UPDATE_POINT_RATE = "1|2|3|4|6";
//
//	private static final String PAD_DEVICE_NO = "pad0001";
//
//	private static final String CR_DEVICE_NO = "cr0001";
//
//	private static final String UPDATE_BY = "hwx81859";
//
//	private static final String POS_DEVICE_NO = "0001";
//
//	private static String POS_DEVICE_CUST_ID = "";
//	private static String PAD_DEVICE_CUST_ID = "";
//	private static String CR_DEVICE_CUST_ID = "";
//
//	@Autowired
//	private IUCAsDeviceService asDeviceService;
//
//	@Autowired
//	private IUCBsDeviceService bsDeviceService;
//
//	// @Test
//	public void test1_0_addPos() {
//		ArrayList<BsPos> list = new ArrayList<BsPos>();
//		BsPos bsPos = new BsPos();
//		bsPos.setDeviceNO(POS_DEVICE_NO);
//		bsPos.setEntCustId(EntServiceTest.ENT_CUST_ID);
//		bsPos.setEntResNo(EntServiceTest.ENT_RES_NO);
//		bsPos.setPointRate(INSERT_POINT_RATE.split(SEPARATOR));
//		bsPos.setMachineNo(MAC_NO);
//		list.add(bsPos);
//		List<BsPos> poslist = bsDeviceService.batchAddPos(list, UPDATE_BY);
//		POS_DEVICE_CUST_ID = poslist.get(0).getDeviceCustId();
//		Assert.assertEquals(1, poslist.size());
//	}
//
//	// @Test
//	public void test1_1_addPad() {
//		ArrayList<BsPad> list = new ArrayList<BsPad>();
//		BsPad bsPad = new BsPad();
//		bsPad.setDeviceNO(PAD_DEVICE_NO);
//		bsPad.setEntCustId(EntServiceTest.ENT_CUST_ID);
//		bsPad.setEntResNo(EntServiceTest.ENT_RES_NO);
//		bsPad.setPointRate(INSERT_POINT_RATE.split(SEPARATOR));
//		bsPad.setMachineNo(MAC_NO);
//		list.add(bsPad);
//		List<BsPad> padlist = bsDeviceService.batchAddPad(list, UPDATE_BY);
//		PAD_DEVICE_CUST_ID = padlist.get(0).getDeviceCustId();
//		Assert.assertEquals(1, padlist.size());
//	}
//
//	// @Test
//	public void test1_2_addCardReader() {
//		ArrayList<BsCardReader> list = new ArrayList<BsCardReader>();
//		BsCardReader bsCardreader = new BsCardReader();
//		bsCardreader.setDeviceNO(CR_DEVICE_NO);
//		bsCardreader.setEntCustId(EntServiceTest.ENT_CUST_ID);
//		bsCardreader.setEntResNo(EntServiceTest.ENT_RES_NO);
//		bsCardreader.setPointRate(INSERT_POINT_RATE.split(SEPARATOR));
//		bsCardreader.setMachineNo(MAC_NO);
//		list.add(bsCardreader);
//		List<BsCardReader> crlist = bsDeviceService.batchAddCardReader(list, UPDATE_BY);
//		CR_DEVICE_CUST_ID = crlist.get(0).getDeviceCustId();
//		Assert.assertEquals(1, crlist.size());
//	}
//
//	// @Test
//	public void test2_1_findPosByDeviceNo() {
//		BsPos pos = bsDeviceService.findPosByDeviceNo(EntServiceTest.ENT_RES_NO, POS_DEVICE_NO);
//		PrintClassProperty.printProerty(pos);
//		Assert.assertEquals(pos.getDeviceCustId(), POS_DEVICE_CUST_ID);
//	}
//
//	// @Test
//	public void test2_2_findPadByDeviceNo() {
//		BsPad pad = bsDeviceService.findPadByDeviceNo(EntServiceTest.ENT_RES_NO, PAD_DEVICE_NO);
//		PrintClassProperty.printProerty(pad);
//		Assert.assertEquals(pad.getDeviceCustId(), PAD_DEVICE_CUST_ID);
//	}
//
//	// @Test
//	public void test2_3_findCardReaderByDeviceNo() {
//		BsCardReader cr = bsDeviceService.findCardReaderByDeviceNo(EntServiceTest.ENT_RES_NO,
//				CR_DEVICE_NO);
//		PrintClassProperty.printProerty(cr);
//		Assert.assertEquals(cr.getDeviceCustId(), CR_DEVICE_CUST_ID);
//	}
//
//	// @Test
//	public void test3_1_updateDevicePointRate() {
//		bsDeviceService.updatePointRate(EntServiceTest.ENT_RES_NO, BsDeviceTypeEumn.POS.getType(),
//				POS_DEVICE_NO, UPDATE_POINT_RATE.split(SEPARATOR), UPDATE_BY);
//		BsPos pos = bsDeviceService.findPosByDeviceNo(EntServiceTest.ENT_RES_NO, POS_DEVICE_NO);
////		Assert.assertEquals(UPDATE_POINT_RATE, pos.getPointRate());
//	}
//
//	// @Test
//	public void test3_2_updateDeviceStatus() {
//		bsDeviceService.updateDeviceStatus(EntServiceTest.ENT_RES_NO,
//				BsDeviceTypeEumn.POS.getType(), POS_DEVICE_NO,
//				BsDeviceStatusEnum.DISABLED.getValue(), "hwx81869");
//		BsPos pos = bsDeviceService.findPosByDeviceNo(EntServiceTest.ENT_RES_NO, POS_DEVICE_NO);
////		Assert.assertEquals(BsDeviceStatusEnum.DISABLED, pos.getStatus());
//	}
//
//	// @Test
//	public void test4_1_findDeviceCustId() {
//		String deviceCustId = asDeviceService.findDeviceCustId(EntServiceTest.ENT_RES_NO,
//				AsDeviceTypeEumn.POS.getType(), POS_DEVICE_NO);
//		Assert.assertEquals(POS_DEVICE_CUST_ID, deviceCustId);
//	}
//
//	// @Test
//	public void test4_2_findPointRate() {
//		String[] pointRate = asDeviceService.findPointRate(EntServiceTest.ENT_RES_NO,
//				AsDeviceTypeEumn.POS.getType(), POS_DEVICE_NO);
//		//Assert.assertEquals(UPDATE_POINT_RATE.split("\\|"), pointRate);
//	}
//
//	// @Test
//	public void test5_1_findPosByDeviceNo() {
//		AsPos asPos = asDeviceService.findPosByDeviceNo(EntServiceTest.ENT_RES_NO, POS_DEVICE_NO);
//		Assert.assertEquals(POS_DEVICE_CUST_ID, asPos.getDeviceCustId());
//	}
//
//	 @Test
//	public void listAsDevice() {
//		 String entResNo = "06002110000";
//		 PageData<AsDevice> list = asDeviceService.listAsDevice(entResNo,
//		 null,
//		 null, 1, 10);
//		 
//		 System.out.println(JSON.toJSON(list));
//		 
//		 try {
//			 System.out.println();
//			 System.out.println();
//			 System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEE end");
//			 System.out.println();
//			 System.out.println();
//			Thread.currentThread().sleep(11111);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		 
////		 Assert.assertEquals(3, list.getCount());
////		 bsDeviceService.createPosPMK("06001020000", "123456", "K3202505947",
////		 true, "0600102000020151215");
//	}
//
//	// 06002110000
//	@Test
//	public void findEntPointRate() {
//		// String entResNo = "06002110000";
//		// String[] ss = asDeviceService.findEntPointRate(entResNo);
//		asDeviceService.updateDeviceStatus("06002110000", 2, "060021100000003", 2,
//				"205165217167413248");
//	}
//
//	@Test
//	public void setEntPointRate() {
//		String entResNo = "06010110000";
//		String[] pointRate = new String[] { "10", "9", "7", "6", "5" };
//		String operator = "06010110000000020160109";
//		asDeviceService.setEntPointRate(entResNo, pointRate, operator);
//	}
//
//	public static void main(String[] args) {
//		String s = "9|8|7|6|5";
//		String[] ss = s.split("\\|");
//	}
//	
//	@Test
//	public void findPosByDeviceNo(){
//		String entResNo = "06031120000";
//		String deviceNo = "0001";
//		BsPos  b = bsDeviceService.findPosByDeviceNo(entResNo, deviceNo);
//	}
//
//}
