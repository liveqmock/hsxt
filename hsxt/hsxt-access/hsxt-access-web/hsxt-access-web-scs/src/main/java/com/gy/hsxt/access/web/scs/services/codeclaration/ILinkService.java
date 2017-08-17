package com.gy.hsxt.access.web.scs.services.codeclaration;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.apply.DeclareLinkman;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-scs
 * @package       : com.gy.hsxt.access.web.scs.services.codeclaration
 * @className     : ILinkService.java
 * @description   : 企业申报-联系人接口
 * @author        : maocy
 * @createDate    : 2015-11-17
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public interface ILinkService extends IBaseService{
    
    /**
     * 
     * 方法名称：查询联系人信息
     * 方法描述：查询联系人信息
     * @param applyId 申请编号
     * @return
     * @throws HsException
     */
    public DeclareLinkman findLinkmanByApplyId(String applyId) throws HsException;
    
    /**
     * 
     * 方法名称：新增联系人信息
     * 方法描述：企业申报新增-新增联系人信息
     * @param linkMan 联系人对象
     * @throws HsException
     */
    public DeclareLinkman createLinkInfo(DeclareLinkman linkMan) throws HsException;
    
    /**
     * 
     * 方法名称：修改联系人信息
     * 方法描述：企业申报新增-保存联系人信息
     * @param linkMan 联系人对象
     * @throws HsException
     */
    public DeclareLinkman updateLinkInfo(DeclareLinkman linkMan) throws HsException;

}
