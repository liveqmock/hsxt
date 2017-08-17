package com.gy.hsxt.pg.bankadapter.common.utils;

import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants.BankName;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.AddrFlag;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.BSysFlag;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.UnionFlag;

/**
 * @author jbli
 * 用于获取银企接口转账银行收取的手续费
 */
public class B2eTransFeeUtil {
	/**
	 * 获取单笔转账扣费的手续费金额
	 * 
	 * @param tranAmount 转账金额 单位（元）
	 * @param unionFlag 同行/跨行
	 * @param addrFlag 同城/异地
	 * @param bsysFlag 普通/加急
	 * @return 元
	 */
	public static double getBankFee(BankName bankName,double tranAmount,UnionFlag unionFlag,AddrFlag addrFlag,BSysFlag bsysFlag) throws Exception{
		if(BankName.pingan == bankName){
			return B2ePABankTransFeeUtil.getPABankFee(tranAmount, unionFlag, addrFlag, bsysFlag);
		}else if (BankName.boc == bankName){
//			return B2eBOCBankTransFeeUtil.getBOCBankFee(tranAmount, unionFlag, addrFlag, bsysFlag);
		}
		return tranAmount;
	}	
}
