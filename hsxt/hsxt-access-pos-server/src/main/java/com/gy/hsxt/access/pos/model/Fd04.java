/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.model;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.util.CommonUtil;


/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.model  
 * @ClassName: Fd04 
 * @Description:  4域 bcd 交易金额
 *                  当49域交易币种为外币时，如果该币种没有小数位，则该域的值代表实际交易金额；
 *                  如果该币种有两个小数位，则表示方法同人民币；若有三个小数位，则最后一个小数位必须为零。
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:04:21 
 * @version V1.0
 */
public class Fd04 extends Afd {

	/**
	 * 4域特殊，使用此方法返回BigDecimal类型
	 * @throws Exception 
	 */
	@Override
	public Object doRequestProcess(String messageId, String data) throws Exception {
		CommonUtil.checkState(StringUtils.isEmpty(data)
				|| PosConstant.AMOUNT_12_LENGTH != data.length(),
				"TransAmount:" + data + ", invalid format.",
				PosRespCode.REQUEST_PACK_FORMAT);

		return CommonUtil.changePackDataToMoney(data);
	}

	/**
	 * 冲正。
	 * 实际没有走过这行。2015/04/08
	 */
	@Override
	public String doResponseProcess(Object messageId) {
		return "000000000000";
	}

	/**
	 * 黄福华：待修改
	 * 2015/04/27  新国都华工的测试结果：完全不支持外币。
	 * 2015/04/27 启用旧代码，支持外币。 
	 * @throws Exception 
	 */
	@Override
	public String doResponseProcess(String messageId, Object data, Object extend, byte[] pversion) 
																					throws Exception {
		final BigDecimal money = (BigDecimal) data;
		final Integer isLocalCurrency = (Integer) extend;
		
		if (PosConstant.ISLOCAL_CURRENCY == isLocalCurrency.intValue()) {
			return CommonUtil.fill0InMoney(money);
		} else {//弱化外币概念，不予考虑。 
			String moneyStr = String.valueOf(data);
			int index = moneyStr.indexOf(".");
			if(-1 == index){
				moneyStr += "00";
				return StringUtils.leftPad(moneyStr, PosConstant.AMOUNT_12_LENGTH, PosConstant.ZERO_CHAR);
			}else {
				String left = moneyStr.substring(0, index);
				String right = moneyStr.substring(index + 1);
				if (right.length() <= PosConstant.AMOUNT_2_DECIMAL_LENGTH) {
					return CommonUtil.fill0InMoney(money);
				}else if(right.length() > PosConstant.AMOUNT_2_DECIMAL_LENGTH) {
					right = right.substring(0, PosConstant.AMOUNT_2_DECIMAL_LENGTH);
					return StringUtils.leftPad(left+right, PosConstant.AMOUNT_12_LENGTH, PosConstant.ZERO_CHAR);
				}
			}
			
		}
		return null;
	}


}
