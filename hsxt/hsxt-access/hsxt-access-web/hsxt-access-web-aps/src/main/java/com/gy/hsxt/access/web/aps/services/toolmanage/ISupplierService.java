package com.gy.hsxt.access.web.aps.services.toolmanage;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tool.Supplier;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.toolmanage
 * @className     : ISupplierService.java
 * @description   : 供应商信息接口
 * @author        : maocy
 * @createDate    : 2016-02-25
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public interface ISupplierService extends IBaseService {
    
    /**
     * 
     * 方法名称：添加供应商
     * 方法描述：供应商信息-添加供应商
     * @param bean 供应商信息
     * @throws HsException
     */
	public void addSupplier(Supplier bean)throws HsException;
	
	/**
     * 
     * 方法名称：修改供应商
     * 方法描述：供应商信息-修改供应商
     * @param bean 供应商信息
     * @throws HsException
     */
	public void modifySupplier(Supplier bean)throws HsException;
	
	/**
     * 
     * 方法名称：删除供应商
     * 方法描述：供应商信息-删除供应商
     * @param supplierId 供应商标识
     * @throws HsException
     */
	public void removeSupplier(String supplierId)throws HsException;
	
	/**
     * 
     * 方法名称：查询供应商
     * 方法描述：供应商信息-依据供应商标识查询供应商
     * @param supplierId 供应商标识
     * @throws HsException
     */
	public Supplier querySupplierById(String supplierId);
	
}
