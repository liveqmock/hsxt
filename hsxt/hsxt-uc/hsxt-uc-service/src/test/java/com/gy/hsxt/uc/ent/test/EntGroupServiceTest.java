///*
// * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
// *
// * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
// */
//
//package com.gy.hsxt.uc.ent.test;
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
//import com.gy.hsxt.common.bean.Page;
//import com.gy.hsxt.common.bean.PageData;
//import com.gy.hsxt.uc.as.api.ent.IUCAsEntGroupService;
//import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
//import com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService;
//import com.gy.hsxt.uc.as.bean.ent.AsEntGroup;
//import com.gy.hsxt.uc.as.bean.operator.AsOperator;
//import com.gy.hsxt.uc.cache.service.CommonCacheService;
//import com.gy.hsxt.uc.ent.bean.EntGroup;
//import com.gy.hsxt.uc.ent.mapper.EntGroupMapper;
//
///**
// * 
// * @Package: com.gy.hsxt.uc.ent.test
// * @ClassName: EntGroupServiceTest
// * @Description: TODO
// * 
// * @author: huanggaoyang
// * @date: 2015-11-25 下午12:09:33
// * @version V1.0
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:spring/spring-uc.xml" })
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class EntGroupServiceTest {
//	private static final String groupName = "客服组";
//	private static final String operator = "hxx81849";
//	private static String entCustId = null;
//	private static Long groupId = null;
//	@Autowired
//	private IUCAsEntService asEntService;
//	@Autowired
//	private IUCAsEntGroupService groupService;
//	@Autowired
//	private IUCAsOperatorService operatorService;
//
//	@Autowired
//	private EntGroupMapper groupMappger;
//	
//	@Autowired
//	CommonCacheService commonCacheService;
//
//	@Test
//	public void test_1_getEntCustId() {
//		entCustId = commonCacheService.findEntCustIdByEntResNo(EntServiceTest.ENT_RES_NO);
//		EntGroup entGroup = groupMappger.selectByGroupName(entCustId, groupName);
//		if (entGroup != null) {
//			groupId = entGroup.getGroupId();
//		}
//	}
//
//	@Test
//	public void test_2_addGroup() {
//		AsEntGroup group = new AsEntGroup();
//		group.setGroupName(groupName);
//		group.setGroupDesc("系统重构组");
//		group.setEntCustId(entCustId);
//		groupService.addGroup(group, operator);
//
//		EntGroup entGroup = groupMappger.selectByGroupName(entCustId, groupName);
//
//		Assert.assertEquals(groupName, entGroup.getGroupName());
//
//		groupId = entGroup.getGroupId();
//	}
//
//	@Test
//	public void updateGroup() {
//		AsEntGroup group = new AsEntGroup();
//		group.setGroupId(128L);
//		group.setGroupDesc("测试用户组002");
//		group.setEntCustId("00000000156163438270977024");
//		group.setGroupName("测试用户组003");
////		group.set
//		groupService.updateGroup(group, "00000000156000320160204");
//
//		EntGroup entGroup = groupMappger.selectByGroupId(128L);
//
//		Assert.assertEquals("测试用户组002", entGroup.getGroupDesc());
//	}
//
//	@Test
//	public void test_4_addGroupUser() {
//		List<AsOperator> opers = operatorService.listOperByEntCustId("06002110000164063559693312");
//		List<String> operCustIds = new ArrayList<String>();
//		for (AsOperator asOper : opers) {
//			operCustIds.add(asOper.getOperCustId());
//		}
//		groupService.addGroupUser(operCustIds, 91L, operator);
//	}
//
//	@Test
//	public void test_5_listGroup() {
//		PageData<AsEntGroup> pageData = groupService.listGroup(entCustId, new Page(1, 2));
//		System.out.println(JSON.toJSONString(pageData));
//	}
//
//	// @Test
//	public void test_6_deleteGroupUser() {
//		List<AsOperator> opers = operatorService.listOperByEntCustId(entCustId);
//		for (AsOperator asOper : opers) {
//			groupService.deleteGroupUser(asOper.getOperCustId(), groupId, operator);
//		}
//	}
//
//	// @Test
//	public void test_7_deleteGroup() {
//		groupService.deleteGroup(groupId, operator);
//	}
//
//	@Test
//	public void test_8_findGroup() {
//		AsEntGroup findGroup = groupService.findGroup("06186630000161594661969920", "重构组");
//		System.out.println(JSON.toJSONString(findGroup));
//	}
//
//}
