/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.service;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.service
 * 
 *  File Name       : ISequenceService.java
 * 
 *  Creation Date   : 2016年5月27日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 序列号接口
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface ISequenceService {

	/**
	 * 获取下一个序列号[同步]
	 * 
	 * @param seqType
	 * @return
	 */
	public String getNextSequence(int seqType);
	
	/**
	 * 获取下一个序列号[快捷签约, 同步]
	 * 
	 * @param seqType
	 * @return
	 */
	public String getNextSeq4UpopBinding();
}
