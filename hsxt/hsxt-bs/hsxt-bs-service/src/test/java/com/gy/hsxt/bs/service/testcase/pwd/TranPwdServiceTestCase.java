/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.service.testcase.pwd;

import javax.annotation.Resource;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.common.enumtype.ApprStatus;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.bs.api.pwd.IBSTransPwdService;
import com.gy.hsxt.bs.bean.pwd.ResetTransPwd;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 企业交易密码重置 测试类
 * 
 * @Package: com.gy.hsxt.bs.service.testcase.tranpwd
 * @ClassName: TranPwdServiceTestCase
 * @Description:
 * 
 * @author: liuhq
 * @date: 2015-10-15 下午2:39:58
 * @version V1.0
 */
@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class TranPwdServiceTestCase
{
	@Resource
	IBSTransPwdService tranPwdService;
	
	/**
	 * 创建 重置交易密码申请记录
	 *            交易密码重置申请 实体类 非null
	 * 
	 *            无异常便成功，Exception失败
	 * @throws HsException
	 */
	@Test
	public void createResetTranPwdApply()
	{
		ResetTransPwd resetTransPwd = new ResetTransPwd();
		resetTransPwd.setApplyPath("applyPath");
		resetTransPwd.setApplyReason("applyReason");
		resetTransPwd.setCreatedby("createdby");
		resetTransPwd.setLinkman("linkman");
		resetTransPwd.setMobile("158888888");
		resetTransPwd.setEntResNo("entResNo");
		resetTransPwd.setEntCustId("entCustId");
		resetTransPwd.setEntCustName("entCustName");
		resetTransPwd.setCustType(3);
		try
		{
			tranPwdService.createResetTransPwdApply(resetTransPwd);
			System.out.println("操作成功！");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 分页查询 重置交易密码申请记录
	 *            企业名称 非空按条件查询
	 * @throws HsException
	 */
	@Test
	public void queryResetTranPwdApplyListPage() throws HsException
	{
		PageData<ResetTransPwd> pageList = null;
		Page page = new Page(1, 2);
		String entResNo = null;
		String entCustName = null;
		try
		{
			pageList = tranPwdService.queryResetTransPwdApplyListPage(page,null);
			for (ResetTransPwd ent : pageList.getResult())
			{
				System.out.println(ent.getApplyId());
				System.out.println(ent.getMobile());
				System.out.println(ent.getLinkman());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取 某一条重置交易密码申请记录
	 * 
	 *            申请编号 必须 非null
	 * @return 返回某一条重置交易密码申请记录
	 * @throws HsException
	 */
	@Test
	public void getResetTranPwdApplyBean()
	{
		String applyId = "1120151015031058000000";
		ResetTransPwd resetTransPwd = null;
		try
		{
			resetTransPwd = tranPwdService.getResetTransPwdApplyBean(applyId);
			System.out.println(resetTransPwd.getApplyId());
			System.out.println(resetTransPwd.getMobile());
			System.out.println(resetTransPwd.getLinkman());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 审批 某一条重置交易密码申请记录
	 * 
	 *            审批状态 必须 非null
	 * @throws HsException
	 */
	@Test
	public void apprResetTranPwdApply()
	{
		ResetTransPwd resetTransPwd = new ResetTransPwd();
		resetTransPwd.setApplyId("110120151203084459000000");
		resetTransPwd.setUpdatedby("46186000000164123662798848");
		resetTransPwd.setStatus(ApprStatus.REJECT.getCode());
		resetTransPwd.setEntCustId("46186000000162791814075392");

		try
		{
			tranPwdService.apprResetTranPwdApply(resetTransPwd);
			System.out.println("操作成功！");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testLog() {
		SystemLog.debug("BS", "function:apprResetTransPwdApply", "重置交易密码：审批某一条重置交易密码申请记录[resetTransPwd]:" );
	}

}
