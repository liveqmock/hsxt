package com.gy.apply.admin.increment;

import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.gpf.bm.bean.ApplyRecord;
import com.gy.hsxt.gpf.bm.bean.IncNode;
import com.gy.hsxt.gpf.bm.common.Constants;
import com.gy.hsxt.gpf.bm.database.HBaseDB;
import com.gy.hsxt.gpf.bm.service.IncrementService;
import com.gy.hsxt.gpf.bm.service.MlmService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**   
 * Simple to Introduction
 * @category          增值系统初始化数据
 * @projectName   apply-incurement
 * @package           com.gy.apply.admin.Increment.IncurementTest.java
 * @className       IncurementTest
 * @description      增值系统测试
 * @author              zhucy
 * @createDate       2014-6-17 下午6:25:49  
 * @updateUser      zhucy
 * @updateDate      2014-6-17 下午6:25:49
 * @updateRemark 说明本次修改内容
 * @version              v0.0.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-global.xml")
public class IncurementTest {
	
	@Resource
	private IncrementService incrementService;

	@Resource
	private MlmService mlmService;

	@Resource
	private HBaseDB hBaseDb;
	
	@SuppressWarnings("rawtypes")
	private Collection values = null;
	
	@SuppressWarnings("unchecked")
	@Test
	public void initData() throws Exception{
		//初始化增值节点前8层数据
		//IncrementUtils.getInstance().initIncrementData();
		
		values = incrementService.queryAllRows();
		
		IncNode value = null;
		for(Iterator<IncNode> it = values.iterator(); it.hasNext();){
			value = it.next();
			System.out.println("key:"+value.getResNo() + " parent:"+value.getParent() +
					"left:"+value.getLeft()+" right:"+value.getRight()
					+" lCount:"+value.getLCount() + " rCount:"+value.getRCount() + 
					" lP:"+value.getLP() + " rP:"+value.getRP() + " level:"+value.getLevel() + 
					" type:"+value.getType());
		}
		
		//跨库申报01000000000下的第一个服务公司
		
	
		ApplyRecord applyRecord = new ApplyRecord();
//		applyRecord.setAppNo("02001000000");
		applyRecord.setArea(null);
		applyRecord.setFlag(Constants.FLAG_0);
//		applyRecord.setManageNo("01000000000");
		applyRecord.setPopNo("01001000000");
		applyRecord.setType(CustType.SERVICE_CORP.getCode());
		
		boolean result = incrementService.addNode(applyRecord);
		
		
		//跨库申报01000000000下的第二个服务公司
	
		applyRecord = new ApplyRecord();
//		applyRecord.setAppNo("02001000000");
		applyRecord.setArea(null);
		applyRecord.setFlag(Constants.FLAG_0);
//		applyRecord.setManageNo("01000000000");
		applyRecord.setPopNo("01002000000");
		applyRecord.setType(CustType.SERVICE_CORP.getCode());
		
		result = incrementService.addNode(applyRecord);
		
		
		//申报01001000000下的第一个托管企业
	
		applyRecord = new ApplyRecord();
//		applyRecord.setAppNo("01001000000L");
		applyRecord.setArea("right");
		applyRecord.setFlag(Constants.FLAG_1);
//		applyRecord.setManageNo("01000000000");
		applyRecord.setPopNo("01001010000");
		applyRecord.setType(CustType.TRUSTEESHIP_ENT.getCode());
		
		result = incrementService.addNode(applyRecord);
	
		
		//申报01001000000下的第一个托管企业
	
		applyRecord = new ApplyRecord();
//		applyRecord.setAppNo("01001000000R");
		applyRecord.setArea("left");
		applyRecord.setFlag(Constants.FLAG_1);
//		applyRecord.setManageNo("01000000000");
		applyRecord.setPopNo("01001020000");
		applyRecord.setType(CustType.TRUSTEESHIP_ENT.getCode());
		
		result = incrementService.addNode(applyRecord);
		
		//申报01001000000下的一个成员企业

		applyRecord = new ApplyRecord();
//		applyRecord.setAppNo("01001000000L");
		applyRecord.setArea("left");
		applyRecord.setFlag(Constants.FLAG_1);
//		applyRecord.setManageNo("01000000000");
		applyRecord.setPopNo("01001000001");
		applyRecord.setType(CustType.MEMBER_ENT.getCode());
		
		result = incrementService.addNode(applyRecord);

		
		//第三个托管企业
		applyRecord = new ApplyRecord();
//		applyRecord.setAppNo("01001000000R");
		applyRecord.setArea("right");
		applyRecord.setFlag(Constants.FLAG_1);
//		applyRecord.setManageNo("01000000000");
		applyRecord.setPopNo("01001030000");
		applyRecord.setType(CustType.TRUSTEESHIP_ENT.getCode());
		
		result = incrementService.addNode(applyRecord);
		System.out.println("申报结果："+result);
	}

	@Test
	public void updateData() throws Exception {
		String custId = "0100200000120151020";
		IncNode object = (IncNode)incrementService.getValueByKey(custId);
		System.out.println(object.toString());
		object.setType(CustType.MEMBER_ENT.getCode());
		incrementService.save(custId, object);
		IncNode object1 = (IncNode)incrementService.getValueByKey(custId);
		System.out.println(object1);
	}

	@Test
	public void queryDataTest() throws Exception {

//		List<Values> valuesList  = hBaseDb.queryFilterList(Constants.T_APP_INCREMENT, "resNo", null, "01001000000", Values.class);
//
//		System.out.println(valuesList.size());

		List<IncNode> incNodes = incrementService.queryIncNodesByResNo("01001000000");
		System.out.println(incNodes);
//		List<IncNode> detailValues = hBaseDb.queryAllRows(Constants.T_APP_INCREMENT, IncNode.class);
//		IncVo vo = IncVo.bulid(detailValues.get(0));
//		System.out.println(detailValues);
	}

	@Test
	public void removeDataTest() {
		//第三个托管企业
		ApplyRecord applyRecord = new ApplyRecord();
		applyRecord.setAppCustId("06000000000");
		applyRecord.setArea("left");
		applyRecord.setFlag(Constants.FLAG_1);
		applyRecord.setManageCustId("06000000000");
		applyRecord.setPopNo("06002000000");
		applyRecord.setPopCustId("0600200000020151221");
		applyRecord.setType(CustType.SERVICE_CORP.getCode());

		boolean result = incrementService.addNode(applyRecord);
		System.out.println("申报结果："+result);
	}

}

	