package com.gy.hsxt.access.web.aps.services.toolmanage.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.toolmanage.IToolCategoryService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.tool.IBSToolProductService;
import com.gy.hsxt.bs.bean.tool.ToolCategory;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
@Service("toolService")
public class ToolCategoryService extends BaseServiceImpl<ToolCategoryService>
		implements IToolCategoryService {
	@Autowired
	private IBSToolProductService bsToolProductService;// 工具服务类

	public PageData<ToolCategory> findScrollResult(Map filterMap, Map sortMap,
			Page page) throws HsException {
		try
		{
			List<ToolCategory> toolCategorys = bsToolProductService
					.queryToolCategoryAll();
			return new PageData<ToolCategory>(toolCategorys.size(), toolCategorys);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findScrollResult", "调用BS分页查询工具类别失败", ex);
			return null;
		}
		
	}
}
