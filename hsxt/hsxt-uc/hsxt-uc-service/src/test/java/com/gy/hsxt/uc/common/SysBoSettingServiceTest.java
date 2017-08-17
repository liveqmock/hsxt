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

package com.gy.hsxt.uc.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.common.constant.BoNameEnum;
import com.gy.hsxt.uc.as.bean.common.AsSysBoSetting;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.service.SysBoSettingService;

/**
 * 
 * @Package: com.gy.hsxt.uc.common
 * @ClassName: SysBoSettingServiceTest
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2016-1-14 下午12:07:17
 * @version V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-uc.xml" })
public class SysBoSettingServiceTest {
	@Resource()
	SysBoSettingService sysBoSettingService;
	@Resource()
	CommonCacheService commonCacheService;

	@Test
	public void testSet() {
		String custId = "123";
		List<AsSysBoSetting> list = new ArrayList<AsSysBoSetting>();
		AsSysBoSetting vo;
		vo = new AsSysBoSetting();
		vo.setCustId(custId);
		vo.setSettingName(BoNameEnum.BUY_HSB.name());
		vo.setSettingValue("1");
		list.add(vo);
		vo = new AsSysBoSetting();
		vo.setCustId(custId);
		vo.setSettingName(BoNameEnum.CASH_TO_BANK.name());
		vo.setSettingName(BoNameEnum.HSB_TO_CASH.name());
		vo.setSettingValue("1");
		list.add(vo);
		String operator = "lv";
		sysBoSettingService.setCustSettings(custId, list, operator);

		Map m = commonCacheService.getBoSetting(custId);
		System.out.println(m);
	}

	@Test
	public void testGet() {
		String custId = "123";
		List l = sysBoSettingService.loadCustSettings(custId);
		System.out.println(l);
		Map m = commonCacheService.getBoSetting(custId);
		System.out.println(m);
	}
	
	@Test
	public void testIsForbidden() {
		String custId = "123";
		String opName=BoNameEnum.HSB_TO_CASH.name();
		boolean m= commonCacheService.isForbidden(custId, opName);
		System.out.println(m);
		opName=BoNameEnum.BUY_HSB.name();
		 m= commonCacheService.isForbidden(custId, opName);
		System.out.println(m);
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
