/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service.impl.parent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;
import com.gy.hsxt.pg.bankadapter.common.utils.MoneyAmountHelper;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.PinganB2eFacade;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.orm.Iserializer;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.params.B2eQryBatchTransParam;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.BatchStt;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.IsBack;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.Stt;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean.QryMaxBatchTransferResDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean.QryMaxBatchTransferResDetailDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean.QrySingleTransferResDTO;
import com.gy.hsxt.pg.common.bean.TransferRespData;
import com.gy.hsxt.pg.common.constant.Constant.PGAmountScale;
import com.gy.hsxt.pg.common.utils.StringUtils;
import com.gy.hsxt.pg.constant.PGConstant.PGTransStateCode;
import com.gy.hsxt.pg.mapper.TPgBankTransOrderMapper;
import com.gy.hsxt.pg.mapper.vo.TPgBankTransOrder;
import com.gy.hsxt.pg.service.ITransCashPost;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service.impl.parent
 * 
 *  File Name       : ParentTransCashPost.java
 * 
 *  Creation Date   : 2015年12月22日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国平安转账处理
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public abstract class ParentTransCashPost implements ITransCashPost {

	protected Logger logger = Logger.getLogger(getClass());

	@Autowired
	protected PinganB2eFacade pinganB2eFacade;

	@Autowired
	protected TPgBankTransOrderMapper pinganOrderMapper;

	/**
	 * 发起向银行查询批量转账结果, 并进行通知GP
	 * 
	 * @param bankBatchNo
	 * @return
	 */
	@Deprecated
	public boolean qryAndNotifyBatchTransfer(String bankBatchNo) {
		// pinganB2eFacade.qryMaxBatchTransferFunds(param);
		return false;
	}

	/**
	 * 从平安银行查询单笔转账结果
	 * 
	 * @param bankOrderNo
	 * @return
	 */
	@Override
	public TPgBankTransOrder qrySingleTransferFromPA(String bankOrderNo) {
		PackageDTO packageDTO = null;

		try {
			// 调用平安银行接口进行查询
			packageDTO = pinganB2eFacade.qrySingleTransfer(bankOrderNo, null,
					null);

			if (null != packageDTO) {
				Iserializer resBody = packageDTO.getBody();

				if (null != resBody) {
					TransferRespData resp = TransferRespData
							.build((QrySingleTransferResDTO) resBody);

					return convert2TPgPinganOrder(bankOrderNo, resp);
				}
			}
		} catch (Exception e) {
			logger.warn("查询转账记录时出现异常：" + e.getMessage());
		}

		return null;
	}

	/**
	 * 从平安银行查询批量转账结果
	 * 
	 * @param bankBatchNo
	 * @return
	 */
	@Override
	public List<TPgBankTransOrder> qryBatchTransferFromPA(String bankBatchNo) {

		PackageDTO packageDTO = null;
		TPgBankTransOrder pinganOrder = null;

		List<TPgBankTransOrder> resultList = new ArrayList<TPgBankTransOrder>(10);
		B2eQryBatchTransParam param = new B2eQryBatchTransParam(bankBatchNo);

		try {
			// 调用平安银行接口进行查询
			packageDTO = pinganB2eFacade.qryMaxBatchTransferFunds(param);

			if ((null != packageDTO) && (null != packageDTO.getBody())) {
				QryMaxBatchTransferResDTO respDto = (QryMaxBatchTransferResDTO) packageDTO
						.getBody();

				List<QryMaxBatchTransferResDetailDTO> detailList = respDto
						.getList();

				if (null != detailList) {
					for (QryMaxBatchTransferResDetailDTO detailDto : detailList) {
						pinganOrder = convert2TPgPinganOrder(
								detailDto.getsThirdVoucher(),
								TransferRespData.build(detailDto));

						resultList.add(pinganOrder);
					}
				}
			}

			return resultList;
		} catch (Exception e) {
			logger.warn("查询转账记录时出现异常：" + e.getMessage(), e);
		}

		return null;
	}

	/**
	 * 将银行响应的结构体转换为TPgPinganOrder对象
	 * 
	 * @param bankOrderNo
	 * @param resp
	 * @return
	 */
	protected TPgBankTransOrder convert2TPgPinganOrder(String bankOrderNo,
			TransferRespData resp) {
		TPgBankTransOrder pinganOrder = new TPgBankTransOrder();

		// 从银行端查询到相关记录
		int transStatus = parseFinalTransState(resp.getStt(), resp.getIsBack());

		// 转账成功、失败、处理中均发通知
		Date bankSubmitDate = DateUtils.stringToDate(resp.getSubmitTime(),
				"yyyyMMddHHmmss");
		
		Date bankAccountDate = DateUtils.stringToDate(resp.getAccountTime(),
				"yyyyMMdd");

		// 银行手续费
		String bankFee = MoneyAmountHelper.formatAmountScale(resp.getFee(),
				PGAmountScale.SIX);

		// 银行流水号
		String bankOrderSeqId = resp.getFrontLogNo();

		// 根据姜方春意见修改
		String failedReason = StringUtils.avoidNull(resp.getYhcljg());
		
		// 退票原因
		String backRem = StringUtils.avoidNull(resp.getBackRem());

		if ((PGTransStateCode.TRANS_FAILED == transStatus)
				&& !StringUtils.isEmpty(failedReason)) {
			failedReason = StringHelper.joinString("[来自银行提示] ", failedReason);
		} else if ((PGTransStateCode.REFUNDED == transStatus)
				&& !StringUtils.isEmpty(backRem)) {
			failedReason = StringHelper
					.joinString("[来自银行提示] 被退票, 原因:", backRem);
		}

		pinganOrder.setBankOrderNo(bankOrderNo);
		pinganOrder.setBankOrderSeqId(bankOrderSeqId);
		pinganOrder.setBankFee(new BigDecimal(bankFee));
		pinganOrder.setBankSubmitDate(bankSubmitDate);
		pinganOrder.setBankAccountDate(bankAccountDate);
		pinganOrder.setTransStatus(transStatus);
		// pinganOrder.setUnionFlag(resBody.getUnionFlag());
		// pinganOrder.setSysFlag(resBody.getSysFlag());

		if ((PGTransStateCode.TRANS_SUCCESS == transStatus)
				|| (PGTransStateCode.BANK_HANDLING == transStatus)) {
			pinganOrder.setFailReason(null);
		} else {
			pinganOrder.setFailReason(failedReason);
		}

		return pinganOrder;
	}

	/**
	 * 转换银行状态为PG支付网关的状态
	 * 
	 * @param stt
	 * @param isBack
	 * @return
	 */
	protected int parseFinalTransState(String stt, String isBack) {

		// 默认为银行处理中
		int transStatus = PGTransStateCode.BANK_HANDLING;

		// 转账成功(平安银行定义的状态码：20-成功、30-失败、其他为银行受理成功处理中)
		if (Stt.SUCCESS.valueEquals(stt)) {
			transStatus = PGTransStateCode.TRANS_SUCCESS;
		}
		// 转账失败
		else if (Stt.FAILED.valueEquals(stt)) {
			transStatus = PGTransStateCode.TRANS_FAILED;
		}
		// 银行内部通信故障或交易错误
		else if (Stt.BANK_ERROR.valueEquals(stt)) {
			transStatus = PGTransStateCode.TRANS_ERROR;
		}

		// 被退票(平安银行定义的状态码：0:未退票; 1:退票;)
		if (IsBack.YES.valueEquals(isBack)) {
			transStatus = PGTransStateCode.REFUNDED;
		}

		return transStatus;
	}

	/**
	 * 解析批量处理状态
	 * 
	 * @param batchStt
	 * @return
	 */
	protected int parseBatchStt(String batchStt) {
		// 20：成功, 30：失败, 其他为银行受理成功处理中
		if (Stt.SUCCESS.valueEquals(batchStt)) {
			return BatchStt.SUCESS.getValue();
		}

		if (Stt.FAILED.valueEquals(batchStt)) {
			return BatchStt.FAILED.getValue();
		}

		return BatchStt.PROCCESSING.getValue();
	}
}