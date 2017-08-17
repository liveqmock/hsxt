/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.ds.common.utils;

import java.util.Random;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-ds-service
 * 
 *  Package Name    : com.gy.hsi.ds.common.utils
 * 
 *  File Name       : ThreadHelper.java
 * 
 *  Creation Date   : 2016年6月6日
 * 
 *  Author          : Administrator
 * 
 *  Purpose         : none
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class ThreadHelper {
	
	/**
	 * 睡眠一会
	 * 
	 * @param time
	 */
	public static void sleepAmoment(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}
	
	/**
	 * 随机睡眠一会
	 * 
	 * @param time
	 */
	public static void sleepAmomentByRandom() {
		try {
			Thread.sleep((new Random().nextInt(2000) + 500));
		} catch (InterruptedException e) {
		}
	}
}
