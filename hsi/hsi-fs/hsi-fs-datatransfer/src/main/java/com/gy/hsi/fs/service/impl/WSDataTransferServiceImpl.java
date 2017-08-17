/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gy.hsi.fs.common.constant.StartClassPath;
import com.gy.hsi.fs.mapper.WSMapperSupporter;
import com.gy.hsi.fs.mapper.vo.dbws01.TWsImg;
import com.gy.hsi.fs.mapper.vo.dbws01.TWsImgExample;
import com.gy.hsi.fs.service.IWSDataTransferService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-datatransfer
 * 
 *  Package Name    : com.gy.hsi.fs.service.impl
 * 
 *  File Name       : WSDataTransferServiceImpl.java
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
public class WSDataTransferServiceImpl extends WSMapperSupporter implements
		IWSDataTransferService {

	@Override
	public List<TWsImg> queryTWsImgList(int start, int end) {
		TWsImgExample example = new TWsImgExample();
		example.setStart(start);
		example.setEnd(end);

		return getTWsImgMapper().selectByExample(example);
	}

	@Override
	public boolean updateTWsImg(TWsImg record) {
		if (!StartClassPath.ONLY_TEST) {
		}
		
		if(!StartClassPath.IS_UPDATE) {
			return false;
		}

		TWsImgExample example = new TWsImgExample();
		example.createCriteria().andImgIdEqualTo(record.getImgId());
		
		int row = getTWsImgMapper().updateByExample(record, example);

		return (0 < row);
	}

}
