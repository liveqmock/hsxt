package com.gy.hsxt.access.web.aps.services.codeclaration;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.apply.DeclareIncrement;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.codeclaration
 * @className     : IAuthCodeService.java
 * @description   : 申报信息维护接口实现
 * @author        : maocy
 * @createDate    : 2015-12-25
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public interface IEntDeclareDefendService extends IBaseService{
	
	/**
     * 
     * 方法名称：保存申报增值点信息
     * 方法描述：保存申报增值点信息
     * @param declareIncrement 积分增值信息
     * @return
     * @throws HsException
     */
	public void saveIncrement(DeclareIncrement declareIncrement) throws HsException;

}
