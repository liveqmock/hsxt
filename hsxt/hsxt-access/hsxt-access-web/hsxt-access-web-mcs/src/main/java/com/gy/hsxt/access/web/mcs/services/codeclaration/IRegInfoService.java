package com.gy.hsxt.access.web.mcs.services.codeclaration;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.apply.DeclareRegInfo;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-mcs
 * @package       : com.gy.hsxt.access.web.mcs.services.codeclaration
 * @className     : IRegInfoService.java
 * @description   : 企业申报-系统注册信息接口
 * @author        : maocy
 * @createDate    : 2015-12-09
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public interface IRegInfoService extends IBaseService{
    
    /**
     * 
     * 方法名称：查询系统注册信息
     * 方法描述：查询系统注册信息
     * @param applyId 申请编号
     * @return
     * @throws HsException
     */
    public DeclareRegInfo findDeclareByApplyId(String applyId) throws HsException;
    
    /**
     * 
     * 方法名称：修改系统注册信息
     * 方法描述：修改系统注册信息
     * @param regInfo 系统注册信息对象
     * @throws HsException
     */
    public void updateDeclare(DeclareRegInfo regInfo) throws HsException;

}
