/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.toolorder.impl;


import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.toolorder.HSCardToolApplyService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.apply.IBSOfficialWebService;
import com.gy.hsxt.bs.bean.tool.CardProvideApply;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 刷卡工具Service
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.toolorder.impl
 * @ClassName: HSCardToolApplyServiceImpl
 * @Description: TODO
 * @author: liyh
 * @date: 2016年6月28日 上午午9:59:00
 * @company: gyist
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
@Service
public class HSCardToolApplyServiceImpl extends BaseServiceImpl implements
HSCardToolApplyService {

	/**
	 * BS工具制作Service
	 */
	@Autowired
	private IBSOfficialWebService iBSOfficialWebService;

	

	/**
	 * 分页查询刷卡工具制作
	 * 
	 * @Description:
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	@Override
	public PageData<CardProvideApply> findScrollResult(Map filterMap,
			Map sortMap, Page page) throws HsException {
		try {
			PageData<CardProvideApply> result = iBSOfficialWebService.queryCardProvideApplyByPage(filterMap.get("receiver").toString(), filterMap.get("mobile").toString(),page);
			return result == null || result.getCount() == 0 ? null : result;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "findScrollResult",
					"调用BS分页查询互生卡发放申请失败", ex);
			return null;
		}
	}

	
}
