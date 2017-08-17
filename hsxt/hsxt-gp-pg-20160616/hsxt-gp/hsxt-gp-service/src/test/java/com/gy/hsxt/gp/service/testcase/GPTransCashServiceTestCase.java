package com.gy.hsxt.gp.service.testcase;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gp.api.IGPTransCashService;
import com.gy.hsxt.gp.bean.TransCash;
import com.gy.hsxt.gp.bean.TransCashOrderState;
import com.gy.hsxt.gp.constant.GPConstant;

/**
 * @author 作者 : zhangysh
 * @ClassName: 类名:GPTransCashServiceTestCase
 * @Description: 描述 :提现测试类
 * @createDate 创建时间: 2015-10-10 上午11:02:40
 * @version 3.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/spring-global.xml" })
public class GPTransCashServiceTestCase {

	@Autowired
	public IGPTransCashService gpTransCashService;
		
	private Date orderDate = new Date();
	private String transAmount = "0.12";
	private String merId = GPConstant.MerId.HS;
	private String orderNo = "20160420100000000034";
	private String inAccNo = "6216260000000000548";
	private String inAccName = "PA测试67336";
	private String inAccBankName = "平安银行";
	private String inAccBankCode = "307584008056";
	private String inAccCityCode = "440224";
	private String inAccCityName = "潮州";
	private String inAccProvinceCode = "44";
	private String sysFlag = "N";
	private String batchNo = "20151120091900000001";
	List<String> notifyMobile = new ArrayList<String>();
	List<String> orderNolist = new ArrayList<String>();
	private String srcSubsysId = "BS";
	
	/**
	 * 单笔提现
	 */	
	@Test
	public void TransCashTest() {
		try {
			notifyMobile.add("17727465180");
			notifyMobile.add("18688994776");
			TransCash transCash = new TransCash(inAccNo, inAccName,
					inAccBankName, inAccBankCode, inAccCityCode, orderNo,
					orderDate, transAmount);
			transCash.setUseDesc("资金用途");
			transCash.setNotifyMobile(notifyMobile);
			transCash.setInAccCityName(inAccCityName);
			transCash.setInAccProvinceCode(inAccProvinceCode);
			transCash.setSysFlag(sysFlag);
			
			System.out.println(gpTransCashService.transCash(merId, transCash,
					srcSubsysId));

		} catch (HsException ex) {
			fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:"
					+ ex.getMessage());
		}

	}

	/**
	 * 提现结果查询
	 */
	@Test
	public void getTransCashOrderStateTest() {

		try {
			System.out.println(gpTransCashService.getTransCashOrderState(merId,
					"2016041910000000006"));

		} catch (HsException ex) {
			fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:"
					+ ex.getMessage());
		}

	}

	/**
	 * 批量提现
	 */
	@Test
	public void BatchTransCashTest() {
		List<TransCash> transCashList = new ArrayList<TransCash>();
		notifyMobile.add("18929362050");
		notifyMobile.add("17727465180");
		try {
			for (int i = 0; i < 50; i++) {
				TransCash transCash = new TransCash(inAccNo, inAccName,
						inAccBankName, inAccBankCode, inAccCityCode, orderNo
								+ i, orderDate, transAmount);
				transCash.setUseDesc("资金用途");
				transCash.setNotifyMobile(notifyMobile);
				transCash.setInAccCityName(inAccCityName);
				transCash.setInAccProvinceCode(inAccProvinceCode);
				transCash.setSysFlag(sysFlag);
				transCashList.add(transCash);
			}

			System.out.println(gpTransCashService.batchTransCash(merId,
					batchNo, transCashList, srcSubsysId));
		} catch (HsException ex) {
			fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:"
					+ ex.getMessage());
		}

	}

	/**
	 * 批量提现结果查询(根据批次号)
	 */
	@Test
	public void getBatchTransCashOrderStateTest() {
		try {
			List<TransCashOrderState> list = gpTransCashService
					.getBatchTransCashOrderState(merId, "130120160316155923000000");
			for (TransCashOrderState e : list) {
				System.out.println(e.toString());
			}
		} catch (HsException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 批量提现结果查询(根据订单号)
	 */
	//@Test
	public void getBatchTransCashOrderStatesTest() {
		orderNolist.add(orderNo);
		try {
			List<TransCashOrderState> list = gpTransCashService
					.getBatchTransCashOrderStates(merId, orderNolist);
			for (TransCashOrderState e : list) {
				System.out.println(e);
			}
		} catch (HsException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 查询提现手续费
	 */
	@Test
	public void getBankTransFeeTest() {
		transAmount="500";
		try {
			String bankfee = gpTransCashService.getBankTransFee(transAmount,
					inAccBankCode, inAccCityCode, sysFlag);

			System.out.println(bankfee);

		} catch (HsException e) {
			e.printStackTrace();
		}

	}
}
