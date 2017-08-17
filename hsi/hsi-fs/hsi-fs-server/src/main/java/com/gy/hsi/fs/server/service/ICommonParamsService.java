/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.server.service;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.service
 * 
 *  File Name       : ICommonParamsService.java
 * 
 *  Creation Date   : 2015年5月19日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件系统公共参数Service
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface ICommonParamsService {

	/**
	 * 通过key查询对应的value
	 * 
	 * @param paramKey
	 * @return
	 */
	public String queryCommonParamValueByKey(String paramKey);

	/**
	 * 通过key查询对应的value[值为int类型]
	 * 
	 * @param paramKey
	 * @return
	 */
	public Integer queryCommonParamIntValueByKey(String paramKey);

	/**
	 * 通过key查询对应的value[值为long类型]
	 * 
	 * @param paramKey
	 * @return
	 */
	public Long queryCommonParamLongValueByKey(String paramKey);

	/**
	 * 通过key查询对应的value[值为float类型]
	 * 
	 * @param paramKey
	 * @return
	 */
	public Float queryCommonParamDoubleValueByKey(String paramKey);

}
