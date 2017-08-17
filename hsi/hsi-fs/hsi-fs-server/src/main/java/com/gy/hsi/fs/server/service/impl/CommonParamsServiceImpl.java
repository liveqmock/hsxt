/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.server.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gy.hsi.fs.server.common.framework.mybatis.MapperSupporter;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsCommonParams;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsCommonParamsExample;
import com.gy.hsi.fs.common.utils.StringUtils;
import com.gy.hsi.fs.server.service.ICommonParamsService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.service.impl
 * 
 *  File Name       : CommonParamsServiceImpl.java
 * 
 *  Creation Date   : 2015年5月19日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件系统公共参数Service实现类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/

@Service(value = "commonParamsService")
public class CommonParamsServiceImpl extends MapperSupporter implements
		ICommonParamsService {

	@Override
	public String queryCommonParamValueByKey(String paramKey) {
		return queryValueByKey(paramKey);
	}

	@Override
	public Integer queryCommonParamIntValueByKey(String paramKey) {
		return StringUtils.str2Int(queryValueByKey(paramKey), 0);
	}

	@Override
	public Long queryCommonParamLongValueByKey(String paramKey) {
		return StringUtils.str2Long(queryValueByKey(paramKey), 0);
	}

	@Override
	public Float queryCommonParamDoubleValueByKey(String paramKey) {
		return StringUtils.str2Float(queryValueByKey(paramKey), 0.00f);
	}

	private String queryValueByKey(String paramKey) {
		TFsCommonParamsExample example = new TFsCommonParamsExample();
		example.createCriteria().andParamKeyEqualTo(paramKey);

		List<TFsCommonParams> resultList = getTFsCommonParamsMapper()
				.selectByExample(example);

		if ((null != resultList) && (0 < resultList.size())) {
			return resultList.get(0).getParamValue();
		}

		return "";
	}
}