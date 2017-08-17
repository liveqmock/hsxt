package com.gy.hsxt.access.web.mcs.services.codeclaration;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-mcs
 * @package       : com.gy.hsxt.access.web.mcs.services.codeclaration
 * @className     : IEntDeclareTrialService.java
 * @description   : 初审业务接口
 * @author        : maocy
 * @createDate    : 2015-12-08
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public interface IEntDeclareTrialService extends IBaseService{
    
    /**
     * 
     * 方法名称：管理公司初审
     * 方法描述：管理公司初审
     * @param apprParam 审批内容
     * @throws HsException
     */
    public void managerApprDeclare(ApprParam apprParam) throws HsException;
    
}