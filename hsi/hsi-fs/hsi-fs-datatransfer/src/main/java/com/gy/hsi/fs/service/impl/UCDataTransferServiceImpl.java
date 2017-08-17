/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gy.hsi.fs.common.constant.FsConstant;
import com.gy.hsi.fs.common.constant.StartClassPath;
import com.gy.hsi.fs.mapper.UCMapperSupporter;
import com.gy.hsi.fs.mapper.vo.dbuc01.TCustIdInfo;
import com.gy.hsi.fs.mapper.vo.dbuc01.TCustIdInfoExample;
import com.gy.hsi.fs.mapper.vo.dbuc01.TEntExtend;
import com.gy.hsi.fs.mapper.vo.dbuc01.TEntExtendExample;
import com.gy.hsi.fs.mapper.vo.dbuc01.TNetworkInfo;
import com.gy.hsi.fs.mapper.vo.dbuc01.TNetworkInfoExample;
import com.gy.hsi.fs.service.IUCDataTransferService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-datatransfer
 * 
 *  Package Name    : com.gy.hsi.fs.service.impl
 * 
 *  File Name       : UCDataTransferServiceImpl.java
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
@Service
public class UCDataTransferServiceImpl extends UCMapperSupporter implements
		IUCDataTransferService {

	@Override
	public List<TCustIdInfo> queryTCustIdInfoList(int start, int end) {
		TCustIdInfoExample example = new TCustIdInfoExample();
		example.setStart(start);
		example.setEnd(end);

		return getTCustIdInfoMapper().selectByExample(example);
	}

	@Override
	public List<TEntExtend> queryTEntExtendList(int start, int end) {
		TEntExtendExample example = new TEntExtendExample();
		example.setStart(start);
		example.setEnd(end);

		return getTEntExtendMapper().selectByExample(example);
	}

	@Override
	public List<TNetworkInfo> queryTNetworkInfoList(int start, int end) {
		TNetworkInfoExample example = new TNetworkInfoExample();
		example.setStart(start);
		example.setEnd(end);

		return getTNetworkInfoMapper().selectByExample(example);
	}

	@Override
	public boolean updateTCustIdInfo(TCustIdInfo record) {
		try {
			if (!StartClassPath.ONLY_TEST) {
				record.setUpdatedby(FsConstant.DEFAULT_TRANSFER_USER);
				record.setUpdateDate(new Date());
			}
			
			if(!StartClassPath.IS_UPDATE) {
				return false;
			}

			int row = getTCustIdInfoMapper().updateByPrimaryKeySelective(record);

			return (0 < row);
		} catch (Exception e) {
		}

		return false;
	}

	@Override
	public boolean updateTEntExtend(TEntExtend record) {
		if (!StartClassPath.ONLY_TEST) {
			record.setUpdatedby(FsConstant.DEFAULT_TRANSFER_USER);
			record.setUpdateDate(new Date());
		}
		
		if(!StartClassPath.IS_UPDATE) {
			return false;
		}

		int row = getTEntExtendMapper().updateByPrimaryKeySelective(record);

		return (0 < row);
	}

	@Override
	public boolean updateTNetworkInfo(TNetworkInfo record) {
		if (!StartClassPath.ONLY_TEST) {
			record.setUpdatedby(FsConstant.DEFAULT_TRANSFER_USER);
			record.setUpdateDate(new Date());
		}
		
		if(!StartClassPath.IS_UPDATE) {
			return false;
		}

		int row = getTNetworkInfoMapper().updateByPrimaryKeySelective(record);

		return (0 < row);
	}

}
