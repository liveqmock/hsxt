/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.queryReport.testcase;

import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.ps.common.Constants;
import com.gy.hsxt.ps.common.PsTools;

/**
 * @Package: com.gy.hsxt.ps.queryReport.testcase
 * @ClassName: sfdsf
 * @Description: TODO
 * 
 * @author: chenhongzhi
 * @date: 2015-12-10 上午11:32:58
 * @version V3.0
 */
public class Test01
{

	public void sdsdf11()
	{
	}
	
	public static void test123(){
		System.out.println(Test11111.getMethod(new Throwable().getStackTrace()[0]));
	}
	

	public static void main(String[] args)
	{
		String s1 = GuidAgent.getStringGuid(Constants.NO_SERVICE_FEE32+PsTools.getInstanceNo());
		String s2 = GuidAgent.getStringGuid(Constants.NO_SERVICE_FEE32+PsTools.getInstanceNo());
		System.out.println(s1);
		System.out.println(s2);
	}
}
