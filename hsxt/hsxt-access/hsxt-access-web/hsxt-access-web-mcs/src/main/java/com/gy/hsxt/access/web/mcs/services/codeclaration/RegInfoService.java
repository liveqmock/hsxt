package com.gy.hsxt.access.web.mcs.services.codeclaration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.apply.IBSDeclareService;
import com.gy.hsxt.bs.bean.apply.DeclareRegInfo;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-mcs
 * @package       : com.gy.hsxt.access.web.mcs.services.codeclaration
 * @className     : RegInfoService.java
 * @description   : 企业申报-系统注册信息
 * @author        : maocy
 * @createDate    : 2015-12-09
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public class RegInfoService extends BaseServiceImpl implements IRegInfoService {
    
    @Autowired
    private IBSDeclareService bsService;//企业申报服务类

    /**
     * 
     * 方法名称：查询系统注册信息
     * 方法描述：查询系统注册信息
     * @param applyId 申请编号
     * @return
     * @throws HsException
     */
    @Override
    public DeclareRegInfo findDeclareByApplyId(String applyId) throws HsException {
        return this.bsService.queryDeclareRegInfoByApplyId(applyId);
    }
    
    /**
     * 
     * 方法名称：修改系统注册信息
     * 方法描述：修改系统注册信息
     * @param regInfo 系统注册信息对象
     * @throws HsException
     */
    @Override
    public void updateDeclare(DeclareRegInfo regInfo) throws HsException {
        this.bsService.manageModifyDeclare(regInfo);
    }
	
}
