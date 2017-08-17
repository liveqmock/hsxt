package com.gy.hsxt.access.web.mcs.services.codeclaration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.apply.IBSDeclareService;
import com.gy.hsxt.bs.bean.apply.DeclareLinkman;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-mcs
 * @package       : com.gy.hsxt.access.web.mcs.services.codeclaration
 * @className     : LinkService.java
 * @description   : 联系人接口
 * @author        : maocy
 * @createDate    : 2015-12-09
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public class LinkService extends BaseServiceImpl implements ILinkService {
    
    @Autowired
    private IBSDeclareService bsService;//企业申报服务类

    /**
     * 
     * 方法名称：查询联系人信息
     * 方法描述：查询联系人信息
     * @param applyId 申请编号
     * @return
     * @throws HsException
     */
    @Override
    public DeclareLinkman findLinkmanByApplyId(String applyId) throws HsException {
        return this.bsService.queryLinkmanByApplyId(applyId);
    }
    
    /**
     * 
     * 方法名称：修改联系人信息
     * 方法描述：修改联系人信息
     * @param linkMan 联系人对象
     * @throws HsException
     */
    @Override
    public void updateLinkInfo(DeclareLinkman linkMan) throws HsException {
        this.bsService.manageModifyLinkman(linkMan);
    }
	
}
