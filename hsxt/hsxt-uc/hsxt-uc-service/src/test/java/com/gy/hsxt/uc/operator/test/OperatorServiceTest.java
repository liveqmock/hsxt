///*
// * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
// *
// * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
// */
//
//package com.gy.hsxt.uc.operator.test;
//
//import static com.gy.hsxt.common.utils.StringUtils.getNowTimestamp;
//
//import java.util.List;
//
//import javax.annotation.Resource;
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
//import com.gy.hsxt.common.bean.Page;
//import com.gy.hsxt.common.bean.PageData;
//import com.gy.hsxt.uc.as.bean.operator.AsOperator;
//import com.gy.hsxt.uc.as.bean.operator.AsQueryOperatorCondition;
//import com.gy.hsxt.uc.cache.service.CommonCacheService;
//import com.gy.hsxt.uc.common.mapper.NetworkInfoMapper;
//import com.gy.hsxt.uc.ent.service.AsEntService;
//import com.gy.hsxt.uc.ent.test.EntServiceTest;
//import com.gy.hsxt.uc.ent.test.PrintClassProperty;
//import com.gy.hsxt.uc.operator.bean.Operator;
//import com.gy.hsxt.uc.operator.mapper.OperatorMapper;
//import com.gy.hsxt.uc.operator.service.OperatorService;
//import com.gy.hsxt.uc.permission.service.RoleService;
//
///**
// * 
// * @Package: com.gy.hsxt.uc.operator.test
// * @ClassName: OperatorServiceTest
// * @Description: TODO
// * 
// * @author: huanggaoyang
// * @date: 2015-10-20 下午5:21:21
// * @version V1.0
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:spring/spring-uc.xml" })
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class OperatorServiceTest {
//	private static String OPER_CUST_ID = "20000100001160524402958336";
//	private static final String UPDATOR = "1000000";
//	// private static String OPER_RES_NO = "90000200001";
//	private static String OPER_RES_NO = "90000200002";
//	private static String ENT_CUST_ID="";
//	@Autowired
//	private AsEntService asEntService;
//	@Resource
//	private OperatorService oPeratorService;
//	@Autowired
//	RoleService roleService;
//	@Autowired
//	CommonCacheService commonCacheService;
//	@Autowired
//	OperatorMapper operatorMapper;
//	
//	@Autowired
//	NetworkInfoMapper networkInfoMapper;
//
//	// @Test
//	public void test1_getEntCustId() {
//
//	}
//
//	@Test
//	public void addOPerator() {
//		for (int i = 1; i < 2; i++) {
//			AsOperator oper = generateOperator("06000000000", "06001000000163432404206592", 6666);
//			OPER_CUST_ID = oPeratorService.addOper(oper, UPDATOR);
//		}
//
//	}
//
//	@Test
//	public void test3_addOPerator() {
//		String entResNo = "46186000000";
//		String entCustId = "46186000000000120151203";
//		String operResNo = "06001010007";
//		AsOperator oper = generateOperator(entResNo, entCustId, Integer.valueOf(operResNo));
//		OPER_CUST_ID = oPeratorService.addOper(oper, UPDATOR);
//	}
//
//	@Test
//	public void test3_searchOperByOperCustId() {
//		String custId = "06002110000164063559726080";
//		AsOperator asOper = oPeratorService.searchOperByCustId(custId);
//		PrintClassProperty.printProerty(asOper);
//	}
//
//	 @Test
//	public void test4_searchOperByUserName() {
//		AsOperator asOper = oPeratorService.searchOperByUserName("06010000000",
//				"0005");
//		PrintClassProperty.printProerty(asOper);
//	}
//
//	// @Test
//	public void test5_listOperByEntCustId() {
//		List<AsOperator> opers = oPeratorService.listOperByEntCustId(ENT_CUST_ID);
//		for (AsOperator oper : opers) {
//			PrintClassProperty.printProerty(oper);
//			Assert.assertEquals(ENT_CUST_ID, oper.getEntCustId());
//		}
//		System.out.println(ENT_CUST_ID + "---------------" + opers.size());
//	}
//
//	// @Test
//	public void test6_updateOper() {
//		AsOperator oper = new AsOperator();
//		oper.setOperCustId(OPER_CUST_ID);
//		oper.setRealName("张三");
//		oper.setOperDuty("测试经理");
//		oper.setEmail("update@163.com");
//		oper.setMobile("up156250000");
//		oper.setRemark("update备注");
//		oper.setLastLoginDate(getNowTimestamp().toString());
//		oper.setLastLoginIp("10.20.220.1");
//		oPeratorService.updateOper(oper, UPDATOR);
//
//		oper = oPeratorService.searchOperByCustId(OPER_CUST_ID);
//		PrintClassProperty.printProerty(oper);
//
//		Assert.assertEquals("张三", oper.getRealName());
//		Assert.assertEquals("测试经理", oper.getOperDuty());
//		Assert.assertEquals("update@163.com", oper.getEmail());
//		Assert.assertEquals("up156250000", oper.getMobile());
//		Assert.assertEquals("update备注", oper.getRemark());
//		Assert.assertEquals("10.20.220.1", oper.getLastLoginIp());
//	}
//
//	 @Test
//	public void bindCard() {
//		String operCustId = "06016110000000020160303";
//		String operResNo = "06002118141";
//		String adminCustId = "06016110000000020160303";
//		oPeratorService.bindCard(operCustId, operResNo, adminCustId);
//		AsOperator oper = oPeratorService.searchOperByCustId(operCustId);
//		Assert.assertEquals(new Integer(-1), oper.getBindResNoStatus());
//	}
//
//	// @Test
//	public void test7_confirmBindCard() {
//		String operResNo = "06186010048";
//		oPeratorService.confirmBindCard(operResNo);
//		AsOperator oper = oPeratorService.searchOperByCustId(operResNo);
//		Assert.assertEquals(new Integer(1), oper.getBindResNoStatus());
//	}
//
//	// @Test
//	public void test7_unBindCard() {
//		String operCustId = "08186630000000320160108";
//		oPeratorService.unBindCard(operCustId);
//		AsOperator oper = oPeratorService.searchOperByCustId(operCustId);
//		Assert.assertEquals(new Integer(0), oper.getBindResNoStatus());
//	}
//
//	// @Test
//	public void test7_findOperCustId() {
//		String findOperCustId = commonCacheService.findOperCustId(EntServiceTest.ENT_RES_NO, "000002");
//		Assert.assertEquals(OPER_CUST_ID, findOperCustId);
//	}
//
//	@Test
//	public void test8_findOperCustId() {
//		oPeratorService.setLoginPwd(OPER_RES_NO, "666666");
//	}
//
//	public static AsOperator generateOperator(String entResNo, String entCustId, Integer operResNo) {
//		AsOperator oper = new AsOperator();
//		oper.setEntResNo(entResNo);
//		oper.setEntCustId(entCustId);
//		oper.setOperResNo("");
//		oper.setEmail("adfa@163.com");
//		oper.setMobile("15986815920");
//		oper.setOperDuty("销售经理");
//		String pwd = "n+LCw3dBNAPVk534hSda7g==";
//		oper.setLoginPwd(pwd);
//		oper.setRandomToken("5030dd8eeb715004");
//		oper.setRealName("李明");
//		oper.setUserName(String.format("%04d",operResNo));
//		oper.setRemark("备注：sfdasda ");
//		return oper;
//	}
//
//	public static void main(String[] args) {
//		for (int i = 0; i < 20000; i++) {
//			String s = String.format("%04d", i);
//			System.out.println("s["+s+"]");
//		}
//		
//	}
//
//
//
//
//
//	@Test
//	public void searchOperAndRoleInfoByCustId() {
//		String operCustId = "06010000000000220160116";
//		String platformCode = "1000";
//		String subSystemCode = "1000";
//		AsOperator operator = oPeratorService.searchOperAndRoleInfoByCustId(operCustId,
//				platformCode, subSystemCode);
//	}
//	
//	@SuppressWarnings("unused")
//	@Test
//	public void testzz(){
//		String custId = commonCacheService.findOperCustId("40000000000", "0000");
//	}
//
//
//	@Test
//	public void listOpers() {
//		AsQueryOperatorCondition condition = new AsQueryOperatorCondition();
//		// condition.setEntCustId("00000000156163438270977024");
//		// condition.setUserName("0000");
//		condition.setRealName("李三");
//		PageData<AsOperator> listOperators = oPeratorService.listOperators(condition, new Page(1,
//				1000));
//		System.out.println(JSON.toJSONString(listOperators));
//		System.out.println("listSize------------------------------------------"
//				+ listOperators.getResult().size());
//	}
//
//	@Test
//	public void test11111111() {
//		oPeratorService.bindCard("06001010000000120160115", "06001010000",
//				"06001010000163521987508224");
//	}
//	
//	@Test
//	public void deleteOper(){
//		String operCustId = "0600100000000013242";
//		String adminCustId = "06002110000164063559726080";
//		oPeratorService.deleteOper(operCustId, adminCustId);
//	}
//	
//	@Test
//	public void listOperators(){
//		AsQueryOperatorCondition condition = new AsQueryOperatorCondition();
//		condition.setEntCustId("06000000000163439192367104");
//		condition.setEntCustId("06002110000164063559693312");
//		Page page = new Page(1,10);
//		 PageData<AsOperator> p = oPeratorService.listOperators(condition, page);
//		 
//		 System.out.print("listOperators "+JSON.toJSONString(p));
//	}
//	
//	@Test
//	public void testCountId() throws Exception{
//		String operCustId="6002110000164063559726080";
//		int count=operatorMapper.countOperCustId(operCustId);
//		
//		System.out.println("--------------"+count);
//		
//		Thread.currentThread().sleep(10000);
//		
//	}
//	
//	@Test
//	public void updateOper(){
//		AsOperator oper = new AsOperator();
//		String adminCustId = "123";
//		oper.setOperCustId("06001000000163432404219904");
//		oper.setRemark("修改remakasd");
//		oPeratorService.updateOper(oper, adminCustId);
//	}
//	
//	@SuppressWarnings("unused")
//	@Test
//	public void search(){
//		List<AsOperator> list = oPeratorService.listOperByEntCustId("0601000000020151231");
//		
//	}
//	
//	
//	@SuppressWarnings("unused")
//	@Test
//	public void searchOperSecondPwd(){
//		String operCustId = "12341000000000020151204";
//		 AsOperator  oper = oPeratorService.searchOperSecondPwd(operCustId);
//	}
//	
//	@Test
//	public void deleteOpersByEntCustId(){
//		String entCustId = "0607100000120160505";
//		String operCustId = "213231231";
//		oPeratorService.deleteOpersByEntCustId(entCustId, operCustId);
//	}
//	
//
//}
