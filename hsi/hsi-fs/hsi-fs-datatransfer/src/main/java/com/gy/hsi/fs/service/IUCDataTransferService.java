/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.service;

import java.util.List;

import com.gy.hsi.fs.mapper.vo.dbuc01.TCustIdInfo;
import com.gy.hsi.fs.mapper.vo.dbuc01.TEntExtend;
import com.gy.hsi.fs.mapper.vo.dbuc01.TNetworkInfo;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-datatransfer
 * 
 *  Package Name    : com.gy.hsi.fs.service
 * 
 *  File Name       : IUCDataTransferService.java
 * 
 *  Creation Date   : 2015年11月20日
 * 
 *  Author          : Administrator
 * 
 *  Purpose         : TODO
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public interface IUCDataTransferService {
	
	public List<TCustIdInfo> queryTCustIdInfoList(int start, int end);

	public List<TEntExtend> queryTEntExtendList(int start, int end);
	
	public List<TNetworkInfo> queryTNetworkInfoList(int start, int end);

	public boolean updateTCustIdInfo(TCustIdInfo record);

	public boolean updateTEntExtend(TEntExtend record);
	
	public boolean updateTNetworkInfo(TNetworkInfo record);

}
