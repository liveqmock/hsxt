package com.gy.hsxt.service.testcase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.PinganB2eFacade;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.orm.Iserializer;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean.QrySingleTransferResDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/spring-global.xml" })
public class PingAnTestCase {

	@Autowired
	private PinganB2eFacade pinganB2eFacade;

	@Test
	public void qruerySingleTransfer() throws HsException {

		String bankOrderNo = "80101618416103752541";

		PackageDTO packageDTO = null;

		try {
			// 调用平安银行接口进行查询
			packageDTO = pinganB2eFacade.qrySingleTransfer(bankOrderNo, null,
					null);
		} catch (BankAdapterException e) {
			System.out.println("查询提现记录时出现异常：" + e.getMessage());

			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("查询提现记录时出现异常：" + e.getMessage());

			e.printStackTrace();
		}

		if (null != packageDTO) {
			Iserializer resBody = packageDTO.getBody();

			if (null != resBody) {
				System.out.println(JSON
						.toJSON((QrySingleTransferResDTO) resBody));
			}
		}

	}
	
	@Test
	public void qrueryBankNodeCode() throws HsException {

		PackageDTO packageDTO = null;

		try {
			// 调用平安银行接口进行查询
			packageDTO = pinganB2eFacade.qryBankNodeCode("307", "", "深圳");
		} catch (BankAdapterException e) {
			System.out.println("查询提现记录时出现异常：" + e.getMessage());

			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("查询提现记录时出现异常：" + e.getMessage());

			e.printStackTrace();
		}

		if (null != packageDTO) {
			Iserializer resBody = packageDTO.getBody();

			if (null != resBody) {
				System.out.println(JSON
						.toJSON(resBody));
			}
		}
	}
	
	@Test
	public void detectionSystemStatus() throws HsException {

		try {
			// 调用平安银行接口进行查询
			boolean success = pinganB2eFacade.doDetectionSystem();

			System.out.println("探测结果：" + success);
		} catch (BankAdapterException e) {
			System.out.println("查询提现记录时出现异常：" + e.getMessage());

			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("查询提现记录时出现异常：" + e.getMessage());

			e.printStackTrace();
		}
	}
}
