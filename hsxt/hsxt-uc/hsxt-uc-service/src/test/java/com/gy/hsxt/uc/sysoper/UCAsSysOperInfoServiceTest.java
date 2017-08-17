/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.sysoper;

import static com.gy.hsxt.common.utils.StringUtils.getNowTimestamp;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.bean.sysoper.AsSysOper;
import com.gy.hsxt.uc.sysoper.bean.AsQuerySysCondition;
import com.gy.hsxt.uc.sysoper.mapper.SysOperatorMapper;
import com.gy.hsxt.uc.sysoper.serivce.UCAsSysOperInfoService;

/**
 * 
 * @Package: com.gy.hsxt.uc.consumer
 * @ClassName: UCBsSysOperInfoServiceTest
 * @Description: TODO
 * 
 * @author: tianxh
 * @date: 2015-10-30 下午5:31:06
 * @version V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-uc.xml" })
public class UCAsSysOperInfoServiceTest {
	@Autowired
	private UCAsSysOperInfoService iUCAsSysOperInfoService;
	@Autowired
	SysOperatorMapper operatorMapper;

	@Test
	public void regSysOper() {

		AsSysOper asSysOper = new AsSysOper();
		asSysOper.setUserName("令红军");
		asSysOper.setPwdLogin("666666");
		asSysOper.setSubSystemCode("111111");
		asSysOper.setPlatformCode("hsxtuc");
		String adminCustId = "09186630000162706727874560";
		iUCAsSysOperInfoService.regSysOper(asSysOper, adminCustId);
	}

	// @Test
	public void updateSysOper() {
		String custId = "899161733139451904";
		AsSysOper asSysOper = new AsSysOper();
		asSysOper.setDuty("猪头队长");
		asSysOper.setEmail("ZhuiXun123@gmail.com");
		asSysOper.setIsAdmin((short) 1);
		asSysOper.setLastLoginDate(getNowTimestamp());
		asSysOper.setLastLoginIp("192.168.1.42");
		asSysOper.setOperCustId(custId);
		asSysOper.setPhone("18721597301");
		asSysOper.setPlatformCode("2222");
		asSysOper.setRealName("老黄");
		asSysOper.setSubSystemCode("1111");
		iUCAsSysOperInfoService.updateSysOper(asSysOper);
	}

	// @Test
	public void delSysOper() {
		String custId = "899161733139451904";
		iUCAsSysOperInfoService.delSysOper(custId);
	}

	// @Test
	public void searchSysOperInfoByCustId() {
		String custId = "899161733139451904";
		iUCAsSysOperInfoService.searchSysOperInfoByCustId(custId);
	}

	@Test
	public void listOperTest() {
		String platformCode = null;
		String subSystemCode = null;
		String operCustId = null;
		String userName = null;
		String realName = null;
		Short isAdmin = 1;
		PageData<AsSysOper> ret = iUCAsSysOperInfoService.listPermByPage(
				platformCode, subSystemCode, isAdmin, operCustId, userName,
				realName, null);
		System.out.print(JSON.toJSONString(ret));
		try {
			// System.in.read(); //避免日志太多，阻塞线程等待输入任意字符后继续
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	@Test
	public void listSysOperAndChecker(){
		List<AsSysOper> list = iUCAsSysOperInfoService.listSysOperAndChecker("%红军", "1", new Page(1,10));
	}

}
