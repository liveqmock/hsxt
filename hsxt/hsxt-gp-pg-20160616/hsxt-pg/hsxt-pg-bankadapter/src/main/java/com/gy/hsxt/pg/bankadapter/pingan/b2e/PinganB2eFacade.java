/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.pingan.b2e;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;

import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants.ErrorCode;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.bankadapter.common.utils.CommonEnumHelper;
import com.gy.hsxt.pg.bankadapter.common.utils.MoneyAmountHelper;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.abstracts.AbstractPingAnB2eFacade;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.params.B2eBatchTransDetailParam;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.params.B2eBatchTransParam;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.params.B2eQryBatchTransParam;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.params.B2eSingleTransParam;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.AddrFlag;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.PinganRespCode;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.TradeCode;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.UnionFlag;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean.DetectionSystemReqDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean.MaxBatchTransferReqDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean.MaxBatchTransferReqDetailDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean.QryBankNodeCodeReqDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean.QryMaxBatchTransferReqDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean.QryMaxBatchTransferReqDetailDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean.QrySingleTransferReqDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean.SingleTransferReqDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean.TradeRecordlReqDTO;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.pingan.b2e
 * 
 *  File Name       : PingAnBankB2eFacade.java
 * 
 *  Creation Date   : 2014-11-4
 * 
 *  Author          : zhangysh 
 * 
 *  Purpose         : 平安银行B2E银企直连门面类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/

public class PinganB2eFacade extends AbstractPingAnB2eFacade {
	/**
	 * 构造函数
	 */
	public PinganB2eFacade() {
	}

	/**
	 * 企业单笔资金划转[4004]
	 * 
	 * @param reqParam 请求参数封装对象
	 * @return PackageDTO\SingleTransferResDTO
	 * @throws BankAdapterException
	 */
	public PackageDTO doSingleTransfer(B2eSingleTransParam reqParam)
			throws BankAdapterException {
		// 对传入的参数进行检查
		B2eParamCheckHelper.checkB2eTransCashReqParam(reqParam);

		// 使用转账凭证作为请求流水号
		String sequence = generateReqSequence(reqParam.getThirdVoucher());

		// 由分转换为元
		String yuan = MoneyAmountHelper.fromFenToYuan(reqParam.getTranAmount());

		// 将CNY转化为RMB
		String ccyCode = MoneyAmountHelper.formatCNY2RMB(reqParam.getCcyCode());

		SingleTransferReqDTO.Builder build = new SingleTransferReqDTO.Builder();
		build.setThirdVoucher(reqParam.getThirdVoucher());
		build.setOutAcctNo(reqParam.getOutAcctNo());
		build.setOutAcctName(reqParam.getOutAcctName());
		build.setInAcctBankNode(reqParam.getInAcctBankNode());
		build.setInAcctNo(reqParam.getInAcctNo());
		build.setInAcctName(reqParam.getInAcctName());
		build.setInAcctBankName(reqParam.getInAcctBankName());
		build.setInAcctProvinceCode(reqParam.getInAcctProvinceCode());
		build.setInAcctCityName(reqParam.getInAcctCityName());
		build.setUnionFlag(String.valueOf(reqParam.getUnionFlag()));
		build.setSysFlag(reqParam.getSysFlag());
		build.setAddrFlag(String.valueOf(reqParam.getAddrFlag()));
		build.setMobile(reqParam.getMobile());
		build.setCcyCode(ccyCode);
		build.setTranAmount(yuan);

		PackageDTO packageDTO = performCall(TradeCode.SingleTransfer,
				build.build(), sequence);

		// 校验银行响应报文
		this.checkBankTransferRespPacket(packageDTO);

		return packageDTO;
	}

	/**
	 * 单笔转账指令查询[4005]
	 * 
	 * @param origThirdVoucher 转账凭证
	 * @param origFrontLogNo 银行流水号
	 * @param origThirdLogNo 请求流水号
	 * @return PackageDTO\QrySingleTransferResDTO
	 * @throws BankAdapterException
	 * @throws UnsupportedEncodingException 
	 */
	public PackageDTO qrySingleTransfer(String origThirdVoucher,
			String origFrontLogNo, String origThirdLogNo)
			throws BankAdapterException, UnsupportedEncodingException {
		// 参数校验
		B2eParamCheckHelper.checkQrySingleTransParam(origThirdVoucher, origFrontLogNo,
				origThirdLogNo);

		// 分别试图使用转账凭证(C20)、银行流水号(C20)、请求流水号(C14)
		String sequence = generateReqSequence(origThirdVoucher, origFrontLogNo,
				origThirdLogNo);

		QrySingleTransferReqDTO body = new QrySingleTransferReqDTO.Builder()
				.setOrigThirdVoucher(origThirdVoucher).setOrigFrontLogNo(origFrontLogNo)
				.setOrigThirdLogNo(origThirdLogNo).build();

		PackageDTO packageDTO = performCall(TradeCode.QrySingleTransfer, body, sequence);

		// 校验响应报文
		this.checkQryBankTransferRespPacket(packageDTO);

		return packageDTO;
	}

	/**
	 * 查询账户历史交易明细 [4013]
	 * 
	 * @param accNo 账号 Char(14)
	 * @param pageNo 查询页码 Char(6) Y 000001：第一页，依次类推
	 *            A:一次查询回所有的记录，不分页，但是开始和结束时间必须为同一天
	 * @param ccycode 币种
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @return PackageDTO\TradeRecordlResDTO
	 * @throws BankAdapterException 
	 * @throws UnsupportedEncodingException
	 */
	public PackageDTO qryHistoryTradeList(String accNo, int pageNo, String ccycode,
			Date beginDate, Date endDate) throws BankAdapterException, UnsupportedEncodingException {
		// 参数校验
		B2eParamCheckHelper.checkQryHisTradeParam(accNo, pageNo, ccycode, beginDate,
				endDate);

		// 请求方流水号, 唯一标识一笔交易, 备注：(如果某种交易要有多次请求的才能完成，多个交易请求包流水号要保持一致)
		String sequence = generateReqSequence();

		// 货币类型
		ccycode = MoneyAmountHelper.formatCNY2RMB(ccycode);

		// 日期格式：yyyyMMdd
		SimpleDateFormat dateFormat = StringHelper.getYYYYMMddDateFormat();

		String strBeginDate = dateFormat.format(beginDate);
		String strEndDate = dateFormat.format(endDate);
		String strPageNo = StringHelper.leftPad(String.valueOf(pageNo), 6, '0');

		// 构造包体实例 TradeRecordlReqDTO
		TradeRecordlReqDTO body = new TradeRecordlReqDTO.Builder(accNo)
				.setCcyCode(ccycode).setBeginDate(strBeginDate).setEndDate(strEndDate)
				.setPageNo(strPageNo).build();

		PackageDTO packageDTO = performCall(TradeCode.TradeDetailReq, body, sequence);

		if (null == packageDTO) {
			throw new BankAdapterException("查询账户历史交易明细失败，原因：平安银行响应的报文异常！");
		}

		return packageDTO;
	}

	/**
	 * 企业大批量资金划转 [4018]
	 * 
	 * @param param
	 * @return PackageDTO\MaxBatchTransferResDTO
	 * @throws BankAdapterException
	 * @throws UnsupportedEncodingException
	 */
	public PackageDTO maxBatchTransferFunds(B2eBatchTransParam param)
			throws BankAdapterException, UnsupportedEncodingException {
		// 参数校验
		B2eParamCheckHelper.checkBatchTransParam(param);

		// 批量转账凭证号, C(20) 必输 标示交易唯一性，同一客户上送的不可重复，建议格式：yyyymmddHHSS+8位系列
		String thirdVoucher = param.getThirdVoucher();
		// 付款人账户, C(14) 必输 扣款账户
		String outAcctNo = param.getOutAcctNo();
		// 付款人名称, C(60) 必输 付款账户户名
		String outAcctName = param.getOutAcctName();
		// 扣款类型, C(1) 非必输，默认为0 0：单笔扣划,1：汇总扣划
		int payType = param.getPayType();
		// 货币类型, C(3) 必输, 将CNY转化为RMB
		String ccyCode = MoneyAmountHelper.formatCNY2RMB(param.getCcycode());
		// 整批转账加急标志, C(1) 必输 Y：加急 , N：不加急, S：特急（汇总扣款模式不支持）
		String bSysFlag = param.getbSysFlag();
		// 总金额, C(16), 单位：元
		String totalAmountYuan = MoneyAmountHelper.fromFenToYuan(param.getTotalAmt());

		// 多条记录
		List<MaxBatchTransferReqDetailDTO> detailDtoList = this.getDetailDtoList(param
				.getDetailList());

		// 构造包体实例 MaxBatchTransferReqDTO
		MaxBatchTransferReqDTO body = new MaxBatchTransferReqDTO.Builder(thirdVoucher,
				totalAmountYuan, outAcctNo, outAcctName, detailDtoList)
				.setCcyCode(ccyCode).setPayType(String.valueOf(payType))
				.setBSysFlag(bSysFlag).build();

		PackageDTO packageDTO = performCall(TradeCode.MaxBatchTransfer, body,
				thirdVoucher);

		// 校验银行响应报文
		this.checkBankTransferRespPacket(packageDTO);

		return packageDTO;
	}

	/**
	 * 批量转账指令查询 [4015]
	 * 
	 * @param param
	 * @return 
	 *         PackageDTO\QryMaxBatchTransferResDTO\QryMaxBatchTransferResDetailDTO
	 * @throws BankAdapterException
	 * @throws UnsupportedEncodingException
	 */
	public PackageDTO qryMaxBatchTransferFunds(B2eQryBatchTransParam param)
			throws BankAdapterException, UnsupportedEncodingException {
		// 参数校验
		B2eParamCheckHelper.checkQryBatchTransParam(param);

		// 批量转账凭证号, C(20) 必须 批量转账发起时上送的凭证号
		String origThirdVoucher = param.getOrigThirdVoucher();
		// 查询类型, C(1) 非必须，默认为0, 0或者空：全部；1:成功, 2:失败, 3:处理中, 4:退票
		String queryType = param.getQueryType();
		// 每页笔数, C(10) 非必须，默认单次返回上限500笔 每次查询返回的笔数，用于分页查询。建议为50，最大500,
		// 单批多次分页查询每页笔数必须保持一致。
		int pageCts = param.getPageCts();
		// 页码, C(10) 非必须，默认为1 当前查询的页码，用于分页查询。从1开始递增
		int pageNo = param.getPageNo();

		// 批量转账凭证号, C(20) 必须 批量转账发起时上送的凭证号
		List<String> thirdVoucherList = param.getThirdVoucherList();

		List<QryMaxBatchTransferReqDetailDTO> detailDtoist = new ArrayList<QryMaxBatchTransferReqDetailDTO>(
				5);
		
		if(null != thirdVoucherList) {
			for (String thirdVoucher : thirdVoucherList) {
				detailDtoist.add(QryMaxBatchTransferReqDetailDTO.getInstance(thirdVoucher));
			}
		}
		
		// 构造包体实例 MaxBatchTransferReqDTO
		QryMaxBatchTransferReqDTO body = new QryMaxBatchTransferReqDTO.Builder(
				origThirdVoucher, detailDtoist).setQueryType(queryType)
				.setPageCts(String.valueOf(pageCts)).setPageNo(String.valueOf(pageNo))
				.build();

		PackageDTO packageDTO = performCall(TradeCode.QryMaxBatchTransfer, body,
				origThirdVoucher);

		// 校验响应报文
		this.checkQryBankTransferRespPacket(packageDTO);

		return packageDTO;
	}

	/**
	 * 银行联行号查询[4017]
	 * 
	 * @param bankCode 银行代码 Char(14) (必填) 代码参考：商业银行-省市代码对照表(联行号查询交易使用).xls
	 * @param bankName 银行名称 (可选)
	 * @param keyWord 匹配关键字 (必填)
	 * @return
	 * @throws BankAdapterException
	 * @throws UnsupportedEncodingException
	 */
	public PackageDTO qryBankNodeCode(String bankCode, String bankName, String keyWord)
			throws BankAdapterException, UnsupportedEncodingException {
		// 参数校验
		B2eParamCheckHelper.checkQryBankNodeCode(bankCode, bankName, keyWord);

		// 构造包体实例 QryBankNodeCodeReqDTO
		QryBankNodeCodeReqDTO body = new QryBankNodeCodeReqDTO.Builder(bankCode,
				bankName, keyWord).build();

		PackageDTO packageDTO = performCall(TradeCode.QryBankNodeCode, body, null);

		if (null == packageDTO) {
			throw new BankAdapterException("查询银行联行号失败，原因：平安银行响应的报文异常！");
		}

		return packageDTO;
	}
	
	/**
	 * 探测系统状态[S001]
	 * 
	 * @return PackageDTO\DetectionSystemResDTO
	 * @throws BankAdapterException 
	 */
	public boolean doDetectionSystem() throws BankAdapterException {

		// 使用转账凭证作为请求流水号
		String sequence = generateReqSequence(""
				+ RandomUtils.nextInt(10000000, 30000000));

		DetectionSystemReqDTO.Builder build = new DetectionSystemReqDTO.Builder();

		PackageDTO packageDTO = performCall(TradeCode.DetectionSysReq,
				build.build(), sequence);

		if ((null != packageDTO) && (null != packageDTO.getHeader())) {
			// 解析响应码和描述
			String respCode = packageDTO.getHeader().getReturndCode();

			// 响应成功, 直接返回
			return PinganRespCode.SUCCESS.valueEquals(respCode);
		}

		return false;
	}

	/**
	 * getDetailDtoList
	 * 
	 * @param detailList
	 * @return
	 * @throws Exception
	 */
	private List<MaxBatchTransferReqDetailDTO> getDetailDtoList(
			List<B2eBatchTransDetailParam> detailList) //throws Exception 
			{
		if (null == detailList) {
			return null;
		}

		MaxBatchTransferReqDetailDTO.Builder detailDtoBuilder;

		// 单笔转账凭证号(批次中的流水号)/序号, C(20)
		String sThirdVoucher;
		// 收款人账户, C(32) 必输
		String inAcctNo;
		// 收款人账户户名, C(60) 必输
		String inAcctName;
		// 收款人开户行名称, C(60) 必输 建议格式：xxx银行xx分行xx支行
		String inAcctBankName;
		// 转出金额, C(16) 必输 单位：元
		String tranAmountYuan;

		String unionFlagStr;
		String addrFlagStr;

		UnionFlag[] unionFlagValues = UnionFlag.values();
		AddrFlag[] addrFlagValues = AddrFlag.values();

		UnionFlag unionFlag;
		AddrFlag addrFlag;

		// 资金用途, C(30) 必输
		String useEx;
		// 收款人开户行行号, C(12) 非必输, 跨行转账不落地，则必输。为人行登记在册的商业银行号
		String inAcctBankNode;
		// 收款账户开户省代码, C(2) 非必输 建议上送，减少跨行转账落单率。对照码参考“附录-省对照表”
		String inAcctProvinceCode;
		// 收款账户开户市, C(12) 非必输 建议上送，减少跨行转账落单率。
		String inAcctCityName;

		List<MaxBatchTransferReqDetailDTO> detailDtoList = new ArrayList<MaxBatchTransferReqDetailDTO>(
				10);

		for (B2eBatchTransDetailParam detailParam : detailList) {
			sThirdVoucher = detailParam.getsThirdVoucher();
			inAcctNo = detailParam.getInAcctNo();
			inAcctName = detailParam.getInAcctName();
			inAcctBankName = detailParam.getInAcctBankName();
			tranAmountYuan = MoneyAmountHelper.fromFenToYuan(detailParam.getTranAmount());

			unionFlagStr = detailParam.getUnionFlagStr();
			addrFlagStr = detailParam.getAddrFlagStr();

			unionFlag = (UnionFlag) CommonEnumHelper
					.toEnum(unionFlagStr, unionFlagValues);
			addrFlag = (AddrFlag) CommonEnumHelper.toEnum(addrFlagStr, addrFlagValues);

			useEx = detailParam.getUseEx();
			inAcctBankNode = detailParam.getInAcctBankNode();
			inAcctProvinceCode = detailParam.getInAcctProvinceCode();
			inAcctCityName = detailParam.getInAcctCityName();

			detailDtoBuilder = new MaxBatchTransferReqDetailDTO.Builder(sThirdVoucher,
					inAcctNo, inAcctName, inAcctBankName, tranAmountYuan, unionFlag,
					addrFlag, useEx);
			detailDtoBuilder.setInAcctProvinceCode(inAcctProvinceCode);
			detailDtoBuilder.setInAcctBankNode(inAcctBankNode);
			detailDtoBuilder.setInAcctCityName(inAcctCityName);

			detailDtoList.add(detailDtoBuilder.build());
		}

		return detailDtoList;
	}
	
	/**
	 * 校验查询请求的响应报文
	 * 
	 * @param packageDTO
	 * @throws BankAdapterException
	 */
	private void checkQryBankTransferRespPacket(PackageDTO packageDTO)
			throws BankAdapterException {
		
		if (null == packageDTO) {
			throw new BankAdapterException(ErrorCode.RESP_PACKET_ABNORMAL,
					"查询失败，原因：平安银行响应的报文异常！");
		}

		if (null == packageDTO.getBody()) {
			throw new BankAdapterException(ErrorCode.DATA_NOT_EXIST,
					"对不起，没有查询到匹配的数据 ！");
		}

		// 解析响应码和描述
		String respCode = packageDTO.getHeader().getReturndCode();

		// MA4045： 重复的第三方流水号或凭证号
		if (!PinganRespCode.isSuccess(respCode)) {
			throw new BankAdapterException(ErrorCode.SOCKET_ERROR, "查询请求失败！");
		}
	}
	
	/**
	 * 校验银行响应异常, 用于规避银行各种失败场景导致的交易失败时状态的混淆处理
	 * 
	 * @param packageDTO
	 * @throws BankAdapterException
	 */
	private void checkBankTransferRespPacket(PackageDTO packageDTO)
			throws BankAdapterException {

		if (null == packageDTO) {
			throw new BankAdapterException(ErrorCode.RESP_PACKET_ABNORMAL,
					"处理异常, 原因：平安银行响应的报文异常！");
		}

		// 解析响应码和描述
		String respCode = packageDTO.getHeader().getReturndCode();
		String errorDesc = packageDTO.getHeader().getReturndDesc();
		
		// 响应成功, 直接返回
		if (PinganRespCode.SUCCESS.valueEquals(respCode)) {
			return;
		}
		
		// MA4045： 重复的第三方流水号或凭证号
		if (PinganRespCode.DUPLICATE_SERIAL_NUMBER.valueEquals(respCode)) {
			throw new BankAdapterException(ErrorCode.DATA_EXIST, errorDesc);
		}

		// YQ9999或EBLN00: 银企平台程序故障
		if (PinganRespCode.isHalfDead(respCode)) {
			respCode += ": 银企平台程序故障!! (异常码由银企直连系统返回, 请联系银行确认银企平台是否已经出现系统异常)";

			throw new BankAdapterException(ErrorCode.BANK_PROGRAM_FAULT,
					respCode);
		}

		// GW3002|AFE001|AFE002|AFE004|E00006|E00007|E00008|YQ9976|YQ9989: 银行的中间环节通信异常
		if (PinganRespCode.isAmbiguous(respCode)) {
			throw new BankAdapterException(ErrorCode.BANK_COMM_ABNORMAL,
					errorDesc);
		}
		
		// MA9112|SC6011: 银行系统繁忙
		if (PinganRespCode.isBankBusy(respCode)) {
			throw new BankAdapterException(ErrorCode.BANK_SYSTEM_BUSY,
					"MA9112:银行系统繁忙,请稍后提交!");
		}
		
		// YQ998*: 异常手续费[YQ9982-交易暂停, YQ9984-收款方户名输入错误]
		if (PinganRespCode.isAbnormalFee(respCode)) {
			throw new BankAdapterException(ErrorCode.BANK_ABNORMAL_FEE,
					"YQ998*:银行处理失败,但有潜在扣手续费可能!");
		}

		// AFE003: 通讯异常-发送报文至后台服务, 归类于Socket异常
		if (PinganRespCode.isB2BiNetErr(respCode)) {
			throw new BankAdapterException(ErrorCode.SOCKET_ERROR,
					"AFE003:B2Bi发送报文至后台服务通讯异常!");
		}
	}
}