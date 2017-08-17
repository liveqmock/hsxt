package com.gy.hsxt.access.web.aps.services.infomanage;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.infomanage
 * @className     : IResAccountManageService.java
 * @description   : 账户资源管理
 * @author        : maocy
 * @createDate    : 2016-02-19
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public interface IResAccountManageService extends IBaseService{
	
    /**
     * 
     * 方法名称：账户资源管理-消费者资源
     * 方法描述：账户资源管理-消费者资源
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<?> findConsumerInfoList(Map filterMap, Map sortMap, Page page) throws HsException;

}
