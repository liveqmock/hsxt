package com.gy.hsxt.ps.points.serivce;

import com.gy.hsxt.common.utils.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.ps.distribution.service.HsbAllocService;
import com.gy.hsxt.ps.distribution.service.PointAllocService;

/**
 * Simple to Introduction
 * 
 * @category Simple to Introduction
 * @projectName hsxt-ps-service
 * @package
 * @className
 * @description 互生币分配测试用例
 * @author chenhongzhi
 * @createDate 2015-8-7 上午9:25:26
 * @updateDate 2015-8-7 上午9:25:26
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
	{ "classpath:/spring/spring-global.xml" })
public class StatementService
{
	@Autowired
	private PointAllocService pointAllocService;
	@Autowired
	private HsbAllocService hsbAllocService;
	
	/**
	 * 互生币汇总
	 */
	@Test
	public void hsbSum(){
		String  runDate= DateUtil.DateToString(DateUtil.addDays(DateUtil.today(),-1));
		hsbAllocService.hsbSummary(runDate);
	}
	
	/**
	 * 积分汇总
	 */
	@Test
	public void pointSum(){
		
		pointAllocService.pointSummary(null);
	}
	
	/**
	 * 积分分配
	 */
	@Test
	public void pointAlloc() {
	//	pointAllocService.batAllocPoint(null);
	}
	
	
}
