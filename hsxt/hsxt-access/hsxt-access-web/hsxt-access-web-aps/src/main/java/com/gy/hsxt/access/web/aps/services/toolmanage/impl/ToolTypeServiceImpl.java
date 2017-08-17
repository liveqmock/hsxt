/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.toolmanage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.toolmanage.ToolTypeService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.tool.IBSToolProductService;
import com.gy.hsxt.bs.bean.tool.ToolCategory;
import com.gy.hsxt.common.exception.HsException;

@Service
@SuppressWarnings("rawtypes")
public class ToolTypeServiceImpl extends BaseServiceImpl implements ToolTypeService {

    @Autowired
    private IBSToolProductService bsToolProductService;
    
    @Override
    public List<ToolCategory> queryToolCategoryAll() throws HsException {
    	try
		{
			return bsToolProductService.queryToolCategoryAll();
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryToolCategoryAll", "调用BS分页查询工具类别信息失败", ex);
			return null;
		}
    }

}
