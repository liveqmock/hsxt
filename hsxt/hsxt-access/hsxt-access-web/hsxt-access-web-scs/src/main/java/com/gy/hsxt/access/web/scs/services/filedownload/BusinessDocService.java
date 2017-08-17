/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.services.filedownload;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.bs.api.bizfile.IBSBizFileService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-scs
 * @package       : com.gy.hsxt.access.web.scs.services.filedownload
 * @className     : BusinessDocService.java
 * @description   : 业务文件管理接口实现
 * @author        : maocy
 * @createDate    : 2016-03-08
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public class BusinessDocService extends BaseServiceImpl implements IBusinessDocService {
	
    @Autowired
    private IBSBizFileService bsService;//BS服务类

    /**
     * 方法名称：查询常用业务文档列表
     * 方法描述：查询常用业务文档列表
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
    public PageData<?> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
    	String docName = (String) filterMap.get("docName");//文档名称
    	Integer docStatus = CommonUtils.toInteger(filterMap.get("docStatus"));//文档状态（1：未发布 2：正常 3：停用）
		return this.bsService.getNormalDocList(docName, docStatus, page);
	}
    
}