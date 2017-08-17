/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.mcs.controllers.resoucemanage;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.mcs.services.resoucemanage.IResouceQuotaService;

/**
 * 
 * @projectName   : hsxt-access-web-mcs
 * @package       : com.gy.hsxt.access.web.mcs.controllers.resoucemanage
 * @className     : ResQuotaQueryController.java
 * @description   : 资源配额查询
 * @author        : maocy
 * @createDate    : 2016-02-17
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Controller
@RequestMapping("resQuotaQueryController")
public class ResQuotaQueryController extends BaseController {
	
    /** 资源配额查询 */
    @Resource
    private IResouceQuotaService iResouceQuotaService;

    @Override
    protected IBaseService getEntityService() {
        return iResouceQuotaService;
    }

}
