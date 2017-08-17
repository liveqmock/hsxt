package com.gy.hsxt.access.web.aps.services.codeclaration;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.codeclaration
 * @className     : IStaffQueryService.java
 * @description   : 开启系统业务接口
 * @author        : maocy
 * @createDate    : 2015-12-19
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public interface IStaffQueryService extends IBaseService{
	
    /**
     * 
     * 方法名称：开启系统
     * 方法描述：开启系统
     * @param param 开启信息
     * @throws HsException
     */
	public void openSystem(ApprParam param) throws HsException;

}
