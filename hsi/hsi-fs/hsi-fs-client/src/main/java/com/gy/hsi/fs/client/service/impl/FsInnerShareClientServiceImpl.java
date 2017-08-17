/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.client.service.impl;

import java.io.OutputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gy.hsi.fs.client.service.IFsInnerShareClientService;
import com.gy.hsi.fs.common.exception.FsException;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-client
 * 
 *  Package Name    : com.gy.hsi.fs.client.service.impl
 * 
 *  File Name       : FsInnerShareClientServiceImpl.java
 * 
 *  Creation Date   : 2015年7月23日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件系统代理客户端service服务接口实现
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service(value = "fsInnerShareClientService")
public class FsInnerShareClientServiceImpl implements
		IFsInnerShareClientService {

	@Override
	public List<String> uploadInnerShareFile(String[] localFileNames,
			String[] fileSuffixes, int expiredHours) throws FsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> uploadInnerShareFile(List<byte[]> fileBytesDataList,
			List<String> fileSuffixList, int expiredHours) throws FsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean downloadInnerShareFile(String fileId, String localFileName)
			throws FsException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean downloadInnerShareFile(String fileId, OutputStream output)
			throws FsException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteInnerShareFile(String[] fileIds) throws FsException {
		// TODO Auto-generated method stub
		return false;
	}

}
