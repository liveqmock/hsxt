/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.pingan.b2c.common;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.pingan.b2c.common
 * 
 *  File Name       : PinganB2cConsts.java
 * 
 *  Creation Date   : 2016年6月23日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 平安网银支付常量类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PinganB2cConsts {
	
	/** 平安支付页面类型 **/
	public static interface PaymentUIType {

		/** 0-通用 **/
		public static final int ALL = 0;

		/** 1-单独显示B2B **/
		public static final int ONLY_B2B = 1;

		/** 2-单独显示B2C **/
		public static final int ONLY_B2C = 2;
	}
}
