/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.toolorder;

import java.util.Map;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tool.ToolConfig;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 工具制作管理--工具订单列表
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.toolorder  
 * @ClassName: ToolOrderListService 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-11-17 下午6:53:07 
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
public interface ToolOrderListService extends IBaseService {

    public void toolOrderConfirmMark(String orderNo) throws HsException;
    
    public void closeToolOrder(String orderNo)throws HsException;

    public PageData<ToolConfig> getToolConfigPage(Map filterMap, Map sortMap, Page page);
}
