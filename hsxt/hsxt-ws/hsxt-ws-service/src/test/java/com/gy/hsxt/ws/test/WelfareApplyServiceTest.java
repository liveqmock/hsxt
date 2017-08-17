/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.ws.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.ws.api.IWsApplyService;
import com.gy.hsxt.ws.api.IWsApprovalService;
import com.gy.hsxt.ws.api.IWsClaimsAccountingService;
import com.gy.hsxt.ws.api.IWsGrantService;
import com.gy.hsxt.ws.api.IWsWelfareService;
import com.gy.hsxt.ws.bean.AccidentSecurityApply;
import com.gy.hsxt.ws.bean.ApprovalDetail;
import com.gy.hsxt.ws.bean.ApprovalQueryCondition;
import com.gy.hsxt.ws.bean.ApprovalRecord;
import com.gy.hsxt.ws.bean.ClaimsAccountingDetail;
import com.gy.hsxt.ws.bean.ClaimsAccountingQueryCondition;
import com.gy.hsxt.ws.bean.ClaimsAccountingRecord;
import com.gy.hsxt.ws.bean.GrantDetail;
import com.gy.hsxt.ws.bean.GrantQueryCondition;
import com.gy.hsxt.ws.bean.GrantRecord;
import com.gy.hsxt.ws.bean.MedicalDetail;
import com.gy.hsxt.ws.bean.MedicalSubsidiesApply;
import com.gy.hsxt.ws.bean.WelfareApplyDetail;
import com.gy.hsxt.ws.bean.WelfareApplyRecord;
import com.gy.hsxt.ws.bean.WelfareQualification;
import com.gy.hsxt.ws.bean.WelfareQualify;
import com.gy.hsxt.ws.common.MedicalUnit;
import com.gy.hsxt.ws.common.WsTools;
import com.gy.hsxt.ws.mapper.AccumulativePointMapper;
import com.gy.hsxt.ws.mapper.MedicalDetailInfoMapper;
import com.gy.hsxt.ws.mapper.WelfareQualificationMapper;
import com.gy.hsxt.ws.service.WelfareBatchService;

/**
 * 
 * @Package: com.gy.hsxt.ws.test
 * @ClassName: WelfareApplyServiceTest
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-12-7 下午3:00:57
 * @version V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-global.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WelfareApplyServiceTest {
	@Autowired
	private IWsApplyService applyService;
	@Autowired
	private IWsApprovalService approvalService;
	@Autowired
	private IWsClaimsAccountingService accountingService;
	@Autowired
	private IWsWelfareService welfareService;
	@Autowired
	private IWsGrantService grantService;
	@Autowired
	private AccumulativePointMapper pointMapper;
	@Autowired
	MedicalDetailInfoMapper medicalDetailMapper;
	@Autowired
	WelfareQualificationMapper qualificationMapper;
	@Autowired
	WelfareBatchService batchService;

	@Test
	public void test1_0_applyAccidentSecurityApply() {
		AccidentSecurityApply apply = new AccidentSecurityApply();
		List<String> imgs = new ArrayList<String>();
		imgs.add("1111111111111111111");
		imgs.add("2222222222222222222");
		apply.setApplyDate("2015-10-15 10:22:08");
		apply.setCdlPath(imgs);
		apply.setCerPositivePath(imgs);
		apply.setCerReversePath(imgs);
		apply.setCostCountPath(imgs);
		apply.setCustId("55550");
		apply.setDdcPath(imgs);
		apply.setExplain("备注说明");
		apply.setHealthCardNo("6000220");
		apply.setHscPositivePath(imgs);
		apply.setHscReversePath(imgs);
		apply.setHsResNo("55550");
		apply.setImrPath(imgs);
		apply.setMedicalAcceptPath(imgs);
		apply.setMedicalProvePath(imgs);
		apply.setOfrPath(imgs);
		apply.setOtherProvePath(imgs);
		apply.setProposerName("黄阳");
		apply.setProposerPapersNo("06000000000");
		apply.setProposerPhone("15986815930");
		apply.setSscPath(imgs);
		applyService.applyAccidentSecurity(apply);

	}

	@Test
	public void test1_1_applyAccidentSecurityApply() {
		MedicalSubsidiesApply apply = new MedicalSubsidiesApply();
		List<String> imgs = new ArrayList<String>();
		imgs.add("888888");
		imgs.add("999999");
		apply.setCity("深圳");
		apply.setEndDate("2016-01-01");
		apply.setStartDate("2016-01-05");
		apply.setHospital("深圳市人民医院");
		apply.setApplyDate("2016-01-05");
		apply.setCdlPath(imgs);
		apply.setCerPositivePath(imgs);
		apply.setCerReversePath(imgs);
		apply.setCostCountPath(imgs);
		apply.setCustId("0600211172220151207");
		apply.setDdcPath(imgs);
		apply.setExplain("住院补贴申请");
		apply.setHealthCardNo("6000220");
		apply.setHscPositivePath(imgs);
		apply.setHscReversePath(imgs);
		apply.setHsResNo("06002111722");
		apply.setImrPath(imgs);
		apply.setMedicalAcceptPath(imgs);
		apply.setOfrPath(imgs);
		apply.setOtherProvePath(imgs);
		apply.setOmrPath(imgs);
		apply.setProposerName("黄阳");
		apply.setProposerPapersNo("06002111722");
		apply.setProposerPhone("15986815930");
		apply.setSscPath(imgs);
		applyService.applyMedicalSubsidies(apply);

	}

	@Test
	public void test1_3_0applyAccidentSecurityApply() {
		MedicalSubsidiesApply apply = new MedicalSubsidiesApply();
		List<String> imgs = new ArrayList<String>();
		imgs.add("888888");
		imgs.add("999999");
		apply.setCity("深圳");
		apply.setEndDate("2016-01-01 10:22:08");
		apply.setStartDate("2016-01-05 10:22:08");
		apply.setHospital("深圳市人民医院");
		apply.setApplyDate("2016-01-05 10:22:08");
		apply.setCdlPath(imgs);
		apply.setCerPositivePath(imgs);
		apply.setCerReversePath(imgs);
		apply.setCostCountPath(imgs);
		apply.setCustId("55550");
		apply.setDdcPath(imgs);
		apply.setExplain("住院补贴申请");
		apply.setHealthCardNo("6000220");
		apply.setHscPositivePath(imgs);
		apply.setHscReversePath(imgs);
		apply.setHsResNo("55550");
		apply.setImrPath(imgs);
		apply.setMedicalAcceptPath(imgs);
		apply.setOfrPath(imgs);
		apply.setOtherProvePath(imgs);
		apply.setOmrPath(imgs);
		apply.setProposerName("黄阳");
		apply.setProposerPapersNo("06000000000");
		apply.setProposerPhone("15986815930");
		apply.setSscPath(imgs);
		applyService.applyMedicalSubsidies(apply);

	}

	@Test
	public void test1_3_queryApplyRecord() {
		PageData<WelfareApplyRecord> list = applyService.listWelfareApply("0500108123720151217",
				null, null, new Page(1));
		System.out.println(JSON.toJSONString(list));
	}

	@Test
	public void test1_4_queryWelfareApplyDetail() {
		WelfareApplyDetail detail = applyService.queryWelfareApplyDetail("30120151231163311000000");
		System.out.println(JSON.toJSONString(detail));
	}

	@Test
	public void test1_5_listPendingApproval() {
		ApprovalQueryCondition condition = new ApprovalQueryCondition();
		// condition.setStartTime("2016-02-01");
		// condition.setEndTime("2016-03-22");
		condition.setProposerResNo("06002");
		// condition.setApprovalCustId("00000000156000220160105");
		PageData<ApprovalRecord> list = approvalService.listApprovalRecord(condition, new Page(1));
		System.out.println(JSON.toJSONString(list));
	}

	@Test
	public void test1_6_addMedicalDetail() {
		List<MedicalDetail> detailList = new ArrayList<>();
		MedicalDetail detailInfo = generateMedicalDetail();
		detailInfo.setApplyWelfareNo("30120151222115122000000");
		detailInfo.setAccountingId("30620151222115123000000");
		detailList.add(detailInfo);
		MedicalDetail detailInfo2 = generateMedicalDetail();
		detailInfo2.setApplyWelfareNo("30120151222115122000000");
		detailInfo2.setAccountingId("30620151222115123000000");
		detailInfo2.setItemName("999感冒颗粒");
		detailList.add(detailInfo2);
		accountingService.countMedicalDetail(detailList);

	}

	@Test
	public void test1_7_addMedicalDetail() {
		ClaimsAccountingDetail detailInfo = accountingService.queryClaimsAccountingDetail("1");
		System.out.println(JSON.toJSONString(detailInfo));
	}

	@Test
	public void test1_7_0_listPendingClaimsAccounting() {
		ClaimsAccountingQueryCondition queryCondition = new ClaimsAccountingQueryCondition();
		PageData<ClaimsAccountingRecord> list = accountingService.listPendingClaimsAccounting(
				queryCondition, new Page(1));
		System.out.println(JSON.toJSONString(list));
	}

	@Test
	public void test1_7_approval() {
		approvalService.approvalWelfare("30520151231183843000000", 1, "10000", "同意");
	}

	@Test
	public void test1_7_listGrant() {
		GrantQueryCondition condition = new GrantQueryCondition();
		condition.setProposerResNo("");
		condition.setProposerName("");
		condition.setGrantPersonCustId("00000000156000220160105");
		PageData<GrantRecord> list = grantService.listPendingGrant(condition, new Page(1));
		System.out.println(JSON.toJSONString(list));
	}

	@Test
	public void test1_8_queryApprovalDetail() {
		ApprovalDetail detail = approvalService.queryApprovalDetail("30520151231163311000000");
		System.out.println(JSON.toJSONString(detail));
	}

	@Test
	public void test1_8_queryGrantDetail() {
		GrantDetail detailInfo = grantService.queryGrantDetail("30820160104095047000000");
		System.out.println(JSON.toJSONString(detailInfo));
	}

	@Test
	public void test1_9_grant() {
		grantService.grantWelfare("30820160119161908000000", "ss");
	}

	@Test
	public void test2_1_handConsumePoint() {
		// qualificationMapper.handConsumePoint(300, 1, 3000);
	}

	@Test
	public void test2_2_handInvestPoint() {
		qualificationMapper.handInvestPoint(10000, new Date());
		System.out.println("sss--------------------------------------------");
	}

	@Test
	public void test2_3_point() {
		pointMapper.countAccumulativePoint("20160520");
		//pointMapper.clearTempData("20160520");
		System.out.println("sss");
	}

	@Test
	public void test2_4_batchService() {
		batchService.excuteBatch(null, null);
	}

	@Test
	public void test2_5_countWelfareQualify() {
		int count = qualificationMapper.countWelfareQualify("05001081237", 1);
		System.out.println(count);
	}

	@Test
	public void test2_6_findWelfareQualify() {
		WelfareQualification welfareQualify = qualificationMapper.findWelfareQualify("05001081237");
		System.out.println(JSON.toJSONString(welfareQualify));
	}

	@Test
	public void test2_7_countWelfareQualify() {
		int totalSize = qualificationMapper.totalSize(null, 1);
		System.out.println(JSON.toJSONString(totalSize));
	}

	@Test
	public void test2_8_pageWelfareQualify() {
		String property = HsPropertiesConfigurer.getProperty("ws.write_back_account");
		System.out.println("-----" + property);
		PageData<WelfareQualify> list = welfareService.listWelfareQualify("06033110973", 0,
				new Page(1));
		System.out.println(JSON.toJSONString(list));
	}

	@Test
	public void test2_9_testLog() {
		// SystemLog.info("xxxxcxx", "testLog", "1223333333333");
		// BizLog.biz("xxxxcxx", "testLog", "ss", "xxxxxxxxxxxxxxxxxxx");
		WelfareQualify findWelfareQualify = welfareService.findWelfareQualify("06002111722");
		System.out.println(JSON.toJSONString(findWelfareQualify));
	}

	@Test
	public void test3_1_testLog() {
		// applyService.checkExistApplying("06002118189",
		// WelfareType.MEDICAL_SUBSIDIES.getType());
		Page page = new Page(1, 1);
		PageData<WelfareQualify> aa = welfareService.queryHisWelfareQualify("06033110977", page);
		System.out.println(JSON.toJSONString(aa));
	}

	private MedicalDetail generateMedicalDetail() {
		MedicalDetail detailInfo = new MedicalDetail();
		detailInfo.setMedicalId(WsTools.getGUID());
		detailInfo.setAmount("10");
		detailInfo.setCategory("中成药");
		detailInfo.setExplain("明细说明");
		detailInfo.setHealthPayAmount("6");
		detailInfo.setHsPayAmount("3");
		detailInfo.setPersonalPayAmount("1");
		detailInfo.setItemName("益母草");
		detailInfo.setQuantity(1);
		detailInfo.setUnit(MedicalUnit.BOX.getUnit());
		detailInfo.setPrice("10");
		detailInfo.setStandard("1*10片");
		return detailInfo;
	}

	public static void main(String[] args) {
		// Field[] fields = WelfareQualify.class.getDeclaredFields();
		// Field.setAccessible(fields, true);
		// StringBuffer sb = new StringBuffer();
		// for (Field fild : fields) {
		// if (fild.getName().equals("serialVersionUID")) {
		// continue;
		// }
		// sb.append(fild.getName());
		// sb.append("=");
		// sb.append(fild.getType().getSimpleName());
		// sb.append("=");
		// sb.append("否");
		// sb.append("\n");
		// }
		// System.out.println(sb.toString());

	}
}
