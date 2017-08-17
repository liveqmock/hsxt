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
import com.gy.hsxt.access.web.aps.services.toolorder.ToolListPrint2Service;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.tool.IBSToolSendService;
import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolShippingPage;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

@SuppressWarnings("rawtypes")
@Service
public class ToolListPrint2ServiceImpl extends BaseServiceImpl implements ToolListPrint2Service {

    @Autowired
    private IBSToolSendService ibSToolSendService;
    
    @Override
    public PageData findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
        
        BaseParam param = new BaseParam();
        param.setOperNo(filterMap.get("operNo").toString());
        param.setStartDate(filterMap.get("startDate").toString());
        param.setEndDate(filterMap.get("endDate").toString());
        param.setHsResNo(filterMap.get("hsResNo").toString());
        param.setHsCustName(filterMap.get("hsCustName").toString());
        param.setType(filterMap.get("type").toString());
        
    	try
		{
			PageData<ToolShippingPage> result = ibSToolSendService.queryShippingPage(param, page);
			return result == null || result.getCount() == 0 || result.getResult() == null? null : result;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findScrollResult", "调用BS分页查询失败", ex);
			return null;
		}
        
    }
}
