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

package com.gy.hsxt.uc.operator.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.util.StringEncrypt;

/**
 * 
 * @Package: com.gy.hsxt.uc.operator.test
 * @ClassName: OpPwdTest
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2016-1-18 下午3:07:40
 * @version V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-uc-test.xml" })
public class OpPwdTest {
	@Autowired
	private IUCAsEntService asEntService;
	@Resource
	private IUCAsOperatorService operatorService;
	@Autowired
	CommonCacheService commonCacheService;

	/*06001000006 这个成员企业 前两天测试销户的时候注销了
	 * 罗宾 14:12 06001000006\0000\666666 06005009000\0000\666666 托管企业：
	 * 06002110000\0000 06010110000\0000
	 * 企业目前只有这四个账户，请把登录密码都设置为666666，交易密码设置为88888888，谢谢 **
	 */
	// 06002110000  06005009000 06001000006
	@Test
	public void testInitPwd() {//重置企业密码，管理员登陆密码设为666666，交易密码设为88888888
		String entResNo = "06001020000"; // 企业互生号
		
		final String userName = "0000";
		final String loginPwd = "666666";
		final String tradePwd = "88888888";
		updateLoginPwd(entResNo, userName, loginPwd);
	//	updateTrade(entResNo, tradePwd);

		System.out
				.print("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE end，请点击停止 按钮，避免日志输出干扰");
		try {
			Thread.currentThread().sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test2_updateOPerLoginPwd() {
		// 06007010000 0000 111222
		// 06007010000 0002 333444
		// 06007110000 0000 111222
		// 操作员客户号，新密码
		// 06002110000\0000\密码不知道 luobin
		String entResNo = "06001020000"; // 企业互生号 06002110000 0000  06010110000 0000
		String userName = "0000"; // 登陆用户名

		String operCustId = commonCacheService.findOperCustId(entResNo, userName);
		updatePwd(operCustId, "666666");
		System.out
				.print("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE end，请点击停止 按钮，避免日志输出干扰");
		try {
			Thread.currentThread().sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test2_setEntTransPwd() {
		String entResNo = "06002110000"; // 企业互生号
		updateTrade(entResNo, "88888888");
		System.out
				.print("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE end，请点击停止 按钮，避免日志输出干扰");
		try {
			Thread.currentThread().sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateTrade(String entResNo, String pwd) {
		String userName = "0000"; // 登陆用户名

		String entCustId = commonCacheService.findEntCustIdByEntResNo(entResNo);
		// 0601020000020160109 888888
		// 清空登陆缓存//企业互生号，操作号
		commonCacheService.removeEntTradeFailTimesCache(entResNo);
		String pwd2 = StringEncrypt.string2MD5(pwd);
		// 企业客户号
		asEntService.setTradePwd(entCustId, pwd2);
	}

	/**
	 * 
	 * @param operCustId
	 *            操作员客户号
	 * @param pwd
	 *            新密码
	 * */

	private void updatePwd(String operCustId, String pwd) {
		AsOperator oper = operatorService.searchOperByCustId(operCustId);
		String md5 = StringEncrypt.string2MD5(pwd);
		md5 = md5 + oper.getLoginPWdSaltValue();
		md5 = StringEncrypt.sha256(md5);
		operatorService.setLoginPwd(operCustId, md5);
	}

	private void updateLoginPwd(String entResNo, String userName, String pwd) {
		String operCustId = commonCacheService.findOperCustId(entResNo, userName);
		updatePwd(operCustId, pwd);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
