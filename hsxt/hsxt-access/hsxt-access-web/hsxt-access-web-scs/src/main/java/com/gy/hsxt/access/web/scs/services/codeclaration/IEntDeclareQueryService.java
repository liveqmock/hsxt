package com.gy.hsxt.access.web.scs.services.codeclaration;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-scs
 * @package       : com.gy.hsxt.access.web.scs.services.codeclaration
 * @className     : IEnterReportService.java
 * @description   : 企业申报查询接口
 * @author        : maocy
 * @createDate    : 2015-10-28
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public interface IEntDeclareQueryService extends IBaseService{
    
    /**
     * 
     * 方法名称：查询申报进行步骤
     * 方法描述：依据企业申请编号查询申报进行步骤
     * @param applyId 查询条件
     * @return 申报进行步骤
     * @throws HsException
     */
    public Integer findDeclareStep(String applyId) throws HsException;
    
    /**
     * 
     * 方法名称：删除申报信息
     * 方法描述：依据企业申请编号删除待提交状态的申报信息
     * @param applyId 申请编号
     * @throws HsException
     */
    public void delUnSubmitDeclare(String applyId) throws HsException;

}
