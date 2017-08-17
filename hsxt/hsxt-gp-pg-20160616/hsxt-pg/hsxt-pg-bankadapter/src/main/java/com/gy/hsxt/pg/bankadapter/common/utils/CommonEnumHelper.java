/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.common.utils;


/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.common.utils
 * 
 *  File Name       : CommonEnumHelper.java
 * 
 *  Creation Date   : 2014-11-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : CommonEnumHelper
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class CommonEnumHelper {

	/**
	 * 枚举值校验,判断是否为合法的枚举值
	 * 
	 * @param value
	 * @param enumz
	 * @return
	 */
	public static boolean checkValue(String value, IBankAdapterEnum[] enumz) {

		for (IBankAdapterEnum currValue : enumz) {
			if (value.equals(currValue.getValue())) {
				return true;
			}
		}

		throw new IllegalArgumentException("Invalid value!");
	}

	/**
	 * 转换为对应的枚举对象
	 * 
	 * @param value
	 * @param enumz
	 * @return
	 */
	public static IBankAdapterEnum toEnum(String value, IBankAdapterEnum[] enumz) {
		for (IBankAdapterEnum currValue : enumz) {
			if (value.equals(currValue.getValue())) {
				return currValue;
			}
		}

		return null;
	}
	
	public static interface IBankAdapterEnum {
		public String getValue();
		public IBankAdapterEnum[] allValues();
	}
}
