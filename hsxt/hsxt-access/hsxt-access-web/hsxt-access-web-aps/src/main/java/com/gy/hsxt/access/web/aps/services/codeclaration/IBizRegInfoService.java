package com.gy.hsxt.access.web.aps.services.codeclaration;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.apply.DeclareBizRegInfo;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.codeclaration
 * @className     : IBizRegInfoService.java
 * @description   : 企业申报-工商登记信息接口
 * @author        : maocy
 * @createDate    : 2015-12-15
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public interface IBizRegInfoService extends IBaseService{
    
    /**
     * 
     * 方法名称：查询工商登记信息
     * 方法描述：依据企业申请编号查询工商登记信息
     * @param applyId 申请编号
     * @return
     * @throws HsException
     */
    public DeclareBizRegInfo queryDeclareEntByApplyId(String applyId) throws HsException;
    
    /**
     * 
     * 方法名称：新增工商登记信息
     * 方法描述：企业申报新增-新增工商登记信息
     * @param bizInfo 工商登记信息对象
     * @throws HsException
     */
    public DeclareBizRegInfo createDeclareEnt(DeclareBizRegInfo bizInfo) throws HsException;
    
    /**
     * 
     * 方法名称：修改工商登记信息
     * 方法描述：企业申报新增-修改工商登记信息
     * @param bizInfo 工商登记信息对象
     * @throws HsException
     */
    public DeclareBizRegInfo updateDeclareEnt(DeclareBizRegInfo bizInfo) throws HsException;
    
}