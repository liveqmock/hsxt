package com.gy.hsxt.access.web.aps.services.codeclaration;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.codeclaration
 * @className     : IAuthCodeService.java
 * @description   : 授权码查询接口
 * @author        : maocy
 * @createDate    : 2015-12-15
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public interface IAuthCodeService extends IBaseService {
	
    /**
     * 
     * 方法名称：重发申报授权码
     * 方法描述：重发申报授权码
     * @param applyId 申请编号
     * @return
     * @throws HsException
     */
	public void  sendAuthCode(String applyId) throws HsException;

}
