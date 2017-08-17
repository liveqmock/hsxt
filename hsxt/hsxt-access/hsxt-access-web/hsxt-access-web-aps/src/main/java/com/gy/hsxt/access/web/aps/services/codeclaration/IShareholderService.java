package com.gy.hsxt.access.web.aps.services.codeclaration;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.apply.FilingShareHolder;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.codeclaration
 * @className     : IShareholderService.java
 * @description   : 股东信息接口
 * @author        : maocy
 * @createDate    : 2015-12-15
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public interface IShareholderService extends IBaseService{
	
	/**
	 * 
	 * 方法名称：创建股东信息
	 * 方法描述：创建股东信息
	 * @param shareHolder 股东信息
	 * @return
	 * @throws HsException
	 */
	public void createShareholder(FilingShareHolder shareHolder) throws HsException;
	
	/**
	 * 
	 * 方法名称：修改股东信息
	 * 方法描述：修改股东信息
	 * @param shareHolder 股东信息
	 * @throws HsException
	 */
	public void updateShareholder(FilingShareHolder shareHolder) throws HsException;
	
	/**
	 * 
	 * 方法名称：删除股东信息
	 * 方法描述：删除股东信息
	 * @param filingShId 股东编号
	 * @param operator 操作员客户
	 * @throws HsException
	 */
	public void deleteShareholder(String filingShId, String operator) throws HsException;
	
}
