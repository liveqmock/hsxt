/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.uc.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.uc.as.bean.common.AsSignInInfo;
import com.gy.hsxt.uc.common.service.UCAsDeviceSignInService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-uc-test.xml" })
public class UCAsDeviceSignInServiceTest {
	@Autowired
	UCAsDeviceSignInService deviceSignInService;
	
	@Test
	public void checkVersion(){
		AsSignInInfo signInInfo = new AsSignInInfo();
		signInInfo.setDeviceNo("0001");
		signInInfo.setEntVer("0009");
		signInInfo.setEntResNO("06186630000");
		signInInfo.setPointVer("0003");
		deviceSignInService.checkVersion(signInInfo);
	}
	
	@Test
	public void parseQrTransBill() throws Exception{
		//// 2位字母数字）类型&11位企业互生号&4位pos终端编号&6位批次号&6位pos机凭证号&14位日期时间（YYYYMMDDhh24mmss）&3位货币代码（49）&12位交易金额（4）&4位积分比例（48用法六）&12企业承兑积分额（48用法六）&12互生币金额（48用法六）&8位随机扰码（数字型字符串）&8位mac校验位
		String qrStr="BH&06001020000&0003&000001&000256&201604151606045EB776DBD8685CC4CE60C9B944D1304EBEBE1E91B357F753B1BE391BE3F9F657B8C68F98BB9311F15B7C6AF61DE5B4A8F045960DCC2324C0740EDCE3F1A925BCD833ECA001C0338F";
		byte[] key={55, 49, 54, 56, 49, 54, 55, 49, 55, 57, 49, 54, 55, 49, 55, 56, 49, 55, 55, 49, 55, 56, 48, 54, 55, 49, 54, 101, 54, 101, 48, 48};
		Byte[] ks= new Byte[key.length];
		for(int i=0;i<key.length;i++){
			ks[i]=key[i];
		}
		String ret=deviceSignInService.parseQrTransBill(key, qrStr);
		System.out.println("HHHHHHHHHHHHHHHHHHHHHHH"+ret);
		Thread.sleep(111111);
		
	}
}
