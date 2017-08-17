/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.pingan.b2e;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants.ErrorCode;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.bankadapter.common.utils.CommonEnumHelper;
import com.gy.hsxt.pg.bankadapter.common.utils.MoneyAmountHelper;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;
import com.gy.hsxt.pg.bankadapter.common.utils.StringParamChecker;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.params.B2eBatchTransDetailParam;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.params.B2eBatchTransParam;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.params.B2eQryBatchTransParam;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.params.B2eSingleTransParam;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.AddrFlag;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.BSysFlag;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.CcyCode;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.PayType;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.UnionFlag;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.pingan.b2e
 * 
 *  File Name       : B2eParamCheckHelper.java
 * 
 *  Creation Date   : 2014-11-7
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 平安银行B2e传递的参数值校验帮助类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class B2eParamCheckHelper {
	/**
	 * 校验查询单笔转现的参数
	 * 
	 * @param origThirdVoucher
	 * @param origFrontLogNo
	 * @param origThirdLogNo
	 * @return
	 */
	public static boolean checkQrySingleTransParam(String origThirdVoucher,
			String origFrontLogNo, String origThirdLogNo) {
		// 判断这三个变量是否至少有一个不为空
		if (checkParamNull(origThirdVoucher, origFrontLogNo, origThirdLogNo)) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM,
					"'origThirdVoucher', 'origFrontLogNo' 或 'origThirdLogNo', "
							+ "这三个参数至少有一个不为空才可以.");
		}

		// 参数校验
		{
			StringParamChecker.check("origThirdVoucher", origThirdVoucher, 0, 20);
			StringParamChecker.check("origFrontLogNo", origFrontLogNo, 0, 20);
			StringParamChecker.check("origThirdLogNo", origThirdLogNo, 0, 14);
		}

		return true;
	}

	/**
	 * 校验批量转现参数
	 * 
	 * @param param
	 * @return
	 */
	public static boolean checkBatchTransParam(B2eBatchTransParam param) {
		// 批量转账凭证号, C(20) 必输 标示交易唯一性，同一客户上送的不可重复，建议格式：yyyymmddHHSS+8位系列
		String thirdVoucher = param.getThirdVoucher();
		// 付款人账户, C(14) 必输 扣款账户
		String outAcctNo = param.getOutAcctNo();
		// 付款人名称, C(60) 必输 付款账户户名
		String outAcctName = param.getOutAcctName();
		// 扣款类型, C(1) 非必输，默认为0 0：单笔扣划,1：汇总扣划
		int payType = param.getPayType();
		// 货币类型, C(3) 必输
		String ccycode = param.getCcycode();
		// 整批转账加急标志, C(1) 必输 Y：加急 , N：不加急, S：特急（汇总扣款模式不支持）
		String bSysFlag = param.getbSysFlag();

		// 参数校验
		{
			StringParamChecker.check("thirdVoucher", thirdVoucher, 1, 20);
			StringParamChecker.check("outAcctNo", outAcctNo, 1, 14);
			StringParamChecker.check("outAcctName", outAcctName, 1, 60);
			StringParamChecker.check("ccycode", ccycode, 1, 3);
			StringParamChecker.check("bSysFlag", bSysFlag, 1, 1);
		}

		// 总金额, C(16), 单位：分
		BigInteger totalAmt = param.getTotalAmt();

		if (0 >= totalAmt.doubleValue()) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM,
					"转账金额totalAmt不能小于等于0 ！  totalAmt=" + totalAmt.toString());
		}

		// 扣款类型
		try {
			CommonEnumHelper.checkValue(String.valueOf(payType), PayType.values());
		} catch (Exception e) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM,
					"扣款类型payType值不合法, 枚举值定义：0-单笔扣划,1-汇总扣划！  payType=" + payType);
		}

		// 货币类型
		if (!checkCurrency(ccycode)) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM,
					"所传入的币种不支持(目前只支持：RMB/CNY, USD, HKD)! 但你传入的币种是： " + ccycode);
		}

		// 整批转账加急标志, C(1) 必输 Y：加急 , N：不加急, S：特急(汇总扣款模式不支持)
		try {
			CommonEnumHelper.checkValue(String.valueOf(bSysFlag), BSysFlag.values());
		} catch (Exception e) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM,
					"整批转账加急标志bSysFlag值不合法！  枚举值定义： Y：加急 , N：不加急, S：特急,  你传递的bSysFlag="
							+ bSysFlag);
		}

		return checkDetailParamList(param.getDetailList());
	}

	/**
	 * 校验detailList
	 * 
	 * @param detailList
	 * @return
	 */
	public static boolean checkDetailParamList(List<B2eBatchTransDetailParam> detailList) {
		if (null == detailList) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM,
					"传递的detailList不能为空！");
		}

		// 收款人账户, C(32) 必输
		String inAcctNo;
		// 收款人账户户名, C(60) 必输
		String inAcctName;
		// 收款人开户行名称, C(60) 必输 建议格式：xxx银行xx分行xx支行
		String inAcctBankName;
		// 转出金额, C(16) 必输 单位：分
		BigInteger tranAmount;

		for (B2eBatchTransDetailParam detailParam : detailList) {
			inAcctNo = detailParam.getInAcctNo();
			inAcctName = detailParam.getInAcctName();
			inAcctBankName = detailParam.getInAcctBankName();
			tranAmount = detailParam.getTranAmount();
			
			// 参数校验
			StringParamChecker.check("inAcctNo", inAcctNo, 1, 32);
			StringParamChecker.check("inAcctName", inAcctName, 1, 60);
			StringParamChecker.check("inAcctBankName", inAcctBankName, 1, 60);

			// 总金额, C(16), 单位：分
			if (0 >= tranAmount.doubleValue()) {
				throw new BankAdapterException(ErrorCode.INVALID_PARAM,
						"转账金额tranAmount不能小于等于0 ！  tranAmount=" + tranAmount.doubleValue());
			}

			// 行内跨行标志 1：行内转账，0：跨行转账
			try {
				CommonEnumHelper.checkValue(detailParam.getUnionFlagStr(),
						UnionFlag.values());
			} catch (Exception e) {
				throw new BankAdapterException(ErrorCode.INVALID_PARAM,
						" 行内跨行标志unionFlag传递不合法,枚举值定义：1：行内转账，0：跨行转账,   你传递的unionFlag="
								+ detailParam.getUnionFlagStr());
			}

			// 同城/异地标志“1”—同城 “2”—异地
			try {
				CommonEnumHelper.checkValue(detailParam.getAddrFlagStr(),
						AddrFlag.values());
			} catch (Exception e) {
				throw new BankAdapterException(ErrorCode.INVALID_PARAM,
						"同城/异地标志addrFlag传递不合法,枚举值定义：1—同城 ,2—异地,  你传递的addrFlag="
								+ detailParam.getAddrFlagStr());
			}
		}

		return true;
	}

	/**
	 * 参数校验:单笔转现
	 * 
	 * @param reqParam
	 * @return
	 */
	public static boolean checkB2eTransCashReqParam(B2eSingleTransParam reqParam) {
		if (null == reqParam) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM,
					"传入的B2eTransCashReqParam对象不能为空 ！");
		}

		// 转账凭证号, C(20) [必输] 标示交易唯一性，同一客户上送的不可重复，建议格式：yyyymmddHHSS+8位系列
		String thirdVoucher = reqParam.getThirdVoucher();

		// 付款人账户, C(14) [必输] 扣款账户
		String outAcctNo = reqParam.getOutAcctNo();

		// 付款人名称, C(60) [必输] 付款账户户名
		String outAcctName = reqParam.getOutAcctName();

		// 货币类型 ,C(3) [必输]
		String ccyCode = reqParam.getCcyCode();

		// 收款人开户行行号, C(12) [非必输] 跨行转账建议必输。为人行登记在册的商业银行号
		String inAcctBankNode = reqParam.getInAcctBankNode();

		// 收款人账户, C(32) [必输]
		String inAcctNo = reqParam.getInAcctNo();

		// 收款人账户户名, C(60) [必输]
		String inAcctName = reqParam.getInAcctName();

		// 收款人开户行名称, C(60) [必输] 建议格式：xxx银行
		String inAcctBankName = reqParam.getInAcctBankName();

		// 收款账户银行开户省代码或省名称, C(10) [非必输]
		// 建议跨行转账输入；对照码参考“附录-省对照表”；也可输入“附录-省对照表”中的省名称
		String inAcctProvinceCode = reqParam.getInAcctProvinceCode();

		// 收款账户开户市, C(12) [非必输] 建议跨行转账输入
		String inAcctCityName = reqParam.getInAcctCityName();

		// 转账加急标志, C(1) [非必输] ‘1’—大额 ，等同Y, ‘2’—小额”等同N, Y：加急, N：普通, S：特急 ( 默认为N)
		String sysFlag = reqParam.getSysFlag();

		// 转账短信通知手机号码 C(100) [非必输] 格式为：“13412341123,
		// 12312341234”，多个手机号码使用半角逗号分隔.
		// 如果为空或者手机号码长度不足11位，就不发送。
		String mobile = reqParam.getMobile();
		
		// 参数校验
		{
			// 转账凭证号, C(20) [必输]
			StringParamChecker.check("thirdVoucher", thirdVoucher, 1, 20);
			// 付款人账户, C(14) [必输]
			StringParamChecker.check("outAcctNo", outAcctNo, 1, 14);
			// 付款人名称, C(60) [必输] 付款账户户名
			StringParamChecker.check("outAcctName", outAcctName, 1, 60);
			// 货币类型 ,C(3) [必输]
			StringParamChecker.check("ccyCode", ccyCode, 1, 3);
			// 接收行行号, C(12) [非必输]
			StringParamChecker.check("inAcctBankNode", inAcctBankNode, 0, 12);
			// 收款人账户, C(32) [必输]
			StringParamChecker.check("inAcctNo", inAcctNo, 1, 32);
			// 收款人账户户名, C(60) [必输]
			StringParamChecker.check("inAcctName", inAcctName, 1, 60);
			// 收款人开户行名称, C(60) [必输]
			StringParamChecker.check("inAcctBankName", inAcctBankName, 1, 60);
			// 收款账户银行开户省代码或省名称, C(10) [非必输]
			StringParamChecker.check("inAcctProvinceCode", inAcctProvinceCode, 0, 10);
			// 收款账户开户市, C(12) [非必输]
			StringParamChecker.check("inAcctCityName", inAcctCityName, 0, 12);
			// 转账加急标志, C(1) [非必输]
			StringParamChecker.check("sysFlag", sysFlag, 0, 1);
			// 转账短信通知手机号码 C(100) [非必输]
			StringParamChecker.check("mobile", mobile, 0, 100);
		}
		
		if (!checkCurrency(ccyCode)) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM,
					"所传入的币种不支持(目前只支持：RMB/CNY, USD, HKD)! 但你传入的币种是： " + ccyCode);
		}

		// 转出金额, C(16) [必输] (单位：分), 但是平安接口中是以'元'为单位, 小数点后2位, 需要转换一下
		BigInteger tranAmount = reqParam.getTranAmount();

		if (0 >= tranAmount.doubleValue()) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM,
					"转账金额tranAmount不能小于等于0 ！");
		}

		// 行内跨行标志, C(1) [必输] 1：行内转账，0：跨行转账
		int unionFlag = reqParam.getUnionFlag();

		try {
			CommonEnumHelper.checkValue(String.valueOf(unionFlag), UnionFlag.values());
		} catch (Exception e) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM,
					"行内跨行标志unionFlag值不合法！  unionFlag=" + unionFlag);
		}

		// 同城/异地标志, C(1) [必输] “1”—同城, “2”—异地
		int addrFlag = reqParam.getAddrFlag();

		try {
			CommonEnumHelper.checkValue(String.valueOf(addrFlag), AddrFlag.values());
		} catch (Exception e) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM,
					"同城/异地标志addrFlag不合法 ！  addrFlag=" + addrFlag);
		}

		return true;
	}

	/**
	 * 校验批量查询转现参数
	 * 
	 * @param param
	 * @return
	 */
	public static boolean checkQryBatchTransParam(B2eQryBatchTransParam param) {
		// 批量转账凭证号, C(20) 必须 批量转账发起时上送的凭证号
		String origThirdVoucher = param.getOrigThirdVoucher();
		// 查询类型, C(1) 非必须，默认为0, 0或者空：全部；1:成功, 2:失败, 3:处理中, 4:退票
		String queryType = param.getQueryType();
		// 每页笔数, C(10) 非必须，默认单次返回上限500笔 每次查询返回的笔数，用于分页查询。建议为50，最大500,
		// 单批多次分页查询每页笔数必须保持一致。
		int pageCts = param.getPageCts();
		// 页码, C(10) 非必须，默认为1 当前查询的页码，用于分页查询。从1开始递增
		int pageNo = param.getPageNo();

		// 参数校验
		{
			StringParamChecker.check("origThirdVoucher", origThirdVoucher, 1, 20);
			StringParamChecker.check("queryType", queryType, 0, 1);
		}

		// 每页笔数
		if (0 >= pageCts) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM,
					"每页笔数pageCts必须大于0,  你传递的pageCts=" + pageCts);
		}

		// 页码
		if (0 >= pageNo) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM,
					"页码pageNo必须大于0,  你传递的pageNo=" + pageNo);
		}

		// 批量转账凭证号, C(20) 非必须   批量转账发起时上送的凭证号
		List<String> thirdVoucherList = param.getThirdVoucherList();

		if ((null != thirdVoucherList) && (500 < thirdVoucherList.size())) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM,
					"thirdVoucherList最多不能超过500个,  你传递的个数："
							+ thirdVoucherList.size());
		}

		return true;
	}

	/**
	 * 校验'查询账户历史交易明细'参数
	 * 
	 * @param accNo
	 * @param pageNo
	 * @param ccycode
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static boolean checkQryHisTradeParam(String accNo, int pageNo, String ccycode,
			Date beginDate, Date endDate) {
		// accNo 参数校验, C(14), 必输
		StringParamChecker.check("accNo", accNo, 1, 14);

		// 页码
		if (0 >= pageNo) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM,
					"页码pageNo必须大于0,  你传递的pageNo=" + pageNo);
		}

		if (!checkCurrency(ccycode)) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM,
					"所传入的币种不支持(目前只支持：RMB/CNY, USD, HKD)! 但你传入的币种是： " + ccycode);
		}

		if (null == beginDate) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM,
					"beginDate不能为空,  你传递的beginDate=" + beginDate);
		}

		if (null == endDate) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM,
					"endDate不能为空,  你传递的endDate=" + endDate);
		}

		if (beginDate.after(endDate)) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM,
					"beginDate只能小于或等于endDate ！");
		}

		return true;
	}

	/**
	 * 校验'查询银行联行号'参数
	 * 
	 * @param bankCode
	 * @param bankName
	 * @param keyWord
	 * @return
	 */
	public static boolean checkQryBankNodeCode(String bankCode, String bankName,
			String keyWord) {
		// 参数校验
		if (StringHelper.isEmpty(bankCode)) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM, "银行代码bankCode不能为空！");
		}

		if (StringHelper.isEmpty(keyWord)) {
			throw new BankAdapterException(ErrorCode.INVALID_PARAM, "关键字keyWord不能为空！");
		}

		return false;
	}

	/**
	 * 生成银行接口的请求流水号
	 * 
	 * @param sources
	 * @return
	 */
	public static boolean checkParamNull(String... sources) {
		for (String source : sources) {
			if (!StringUtils.isEmpty(source)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 判断是否为支持的货币类型
	 * 
	 * @param ccycode
	 * @return
	 */
	public static boolean checkCurrency(String ccycode) {
		String strCcycode = MoneyAmountHelper.formatCNY2RMB(ccycode);

		CcyCode[] ccyCodes = new CcyCode[] { CcyCode.RMB, CcyCode.USD, CcyCode.HKD };

		for (CcyCode c : ccyCodes) {
			if (strCcycode.equals(c.getValue())) {
				return true;
			}
		}

		return false;
	}
}
