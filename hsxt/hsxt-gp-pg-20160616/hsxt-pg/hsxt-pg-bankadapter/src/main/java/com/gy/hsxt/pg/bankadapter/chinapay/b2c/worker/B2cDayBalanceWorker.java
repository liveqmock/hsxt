/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.b2c.worker;

import java.util.Date;

import chinapay.SecureLink;

import com.gy.hsxt.pg.bankadapter.chinapay.BizValueMapSwapper;
import com.gy.hsxt.pg.bankadapter.chinapay.ChinapayBalanceRowItem;
import com.gy.hsxt.pg.bankadapter.chinapay.ChinapayOrderResultDTO;
import com.gy.hsxt.pg.bankadapter.chinapay.ChinapayOrderStatus;
import com.gy.hsxt.pg.bankadapter.chinapay.ChinapayOrderTransType;
import com.gy.hsxt.pg.bankadapter.chinapay.IChinapayBalanceCallback;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.abstracts.AbstractB2cDayBalanceWorker;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayB2cConst.BankTransStatus;
import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants.ErrorCode;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.b2c.worker
 * 
 *  File Name       : B2cDayBalanceWorker.java
 * 
 *  Creation Date   : 2016年3月1日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联B2C网银支付"每日对账"工作者类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class B2cDayBalanceWorker extends AbstractB2cDayBalanceWorker {

	public B2cDayBalanceWorker() {
	}

	/**
	 * 对账文件解析
	 * 
	 * @param localSavePath
	 *            文件保存路径
	 * @param callback
	 *            处理过程回调
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean doParsingBalanceFile(String localSavePath,
			IChinapayBalanceCallback callback) throws Exception {
		return super.doParsingBalanceFile(localSavePath, callback);
	}

	@Override
	protected ChinapayOrderResultDTO changeLine2OrderResultDto(String lineTxt)
			throws Exception {
		// 银联SecureLink安全链, 用于安全校验
		SecureLink secureLink = createSecureLink();

		// 将每行的数据解析成ChinapayBalanceRowItem对象
		ChinapayBalanceRowItem rowItem = new ChinapayBalanceRowItem(lineTxt,
				secureLink);

		if (!rowItem.checkAuthToken()) {
			throw new BankAdapterException(ErrorCode.FAILED_CHECK_SIGN,
					"校验对账文件的签名失败！！ lineTxt=" + lineTxt);
		}

		String strAmount = rowItem.getTransAmt().replaceFirst("^0{1,}", "");
		String bankOrderStatus = rowItem.getTransStat();

		Date transDateTime = DateUtils.getYYYYMMddHHmmssDate(rowItem
				.getTransDate());

		ChinapayOrderStatus pgOrderStatus = this.change2PgOrderStatus(
				bankOrderStatus, rowItem.getTransType());

		// 添加转换的代码
		ChinapayOrderResultDTO dto = new ChinapayOrderResultDTO();

		dto.setOrderNo(rowItem.getOrdId());
		dto.setTransType(rowItem.getTransType());
		dto.setCrrrency(rowItem.getCurid());
		dto.setOrderStatus(pgOrderStatus);
		dto.setPayAmount(strAmount);
		dto.setTranTime(transDateTime);
		dto.setBankSeqNo(rowItem.getCpSeqId());

		return dto;
	}
	
	@Override
	protected boolean verifySignature(String lineTxt) {

		// 将每行的数据解析成ChinapayBalanceRowItem对象
		ChinapayBalanceRowItem rowItem = new ChinapayBalanceRowItem(lineTxt,
				createSecureLink());

		return rowItem.checkAuthToken();
	}

	/**
	 * 转换为PG支付网关定义的状态(只适用于B2C、UPOP)
	 * 
	 * @param bankOrderStatus
	 * @param bankTransType
	 * @return
	 */
	private ChinapayOrderStatus change2PgOrderStatus(String bankOrderStatus,
			String bankTransType) {

		int pgTransType = BizValueMapSwapper.toPgTransType(bankTransType);

		ChinapayOrderStatus ufeOrderStatus = ChinapayOrderStatus.UNKNOWN;

		// 设置默认值
		{
			if (ChinapayOrderTransType.TYPE_PAY.valueEquals(pgTransType)) {
				ufeOrderStatus = ChinapayOrderStatus.PAY_FAILED;
			}

			if (ChinapayOrderTransType.TYPE_REFUND.valueEquals(pgTransType)) {
				ufeOrderStatus = ChinapayOrderStatus.REFUND_FAILED;
			}
		}

		// 1001消费交易成功
		if (BankTransStatus.PAY_SUCCESS.equals(bankOrderStatus)) {
			ufeOrderStatus = ChinapayOrderStatus.PAY_SUCCESS;
		}
		// 1003退款提交成功
		else if (BankTransStatus.REFUND_SUCCESS.equals(bankOrderStatus)) {
			ufeOrderStatus = ChinapayOrderStatus.REFUND_PROCESSING;
		}
		// 1005退款撤销成功
		else if (BankTransStatus.REFUND_CANCEL_SUCCESS.equals(bankOrderStatus)) {
			ufeOrderStatus = ChinapayOrderStatus.REFUND_CANCEL_SUCCESS;
		}

		return ufeOrderStatus;
	}

}
