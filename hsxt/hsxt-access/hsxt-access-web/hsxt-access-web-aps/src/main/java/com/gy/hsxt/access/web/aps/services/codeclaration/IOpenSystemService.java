package com.gy.hsxt.access.web.aps.services.codeclaration;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.apply.DebtOpenSys;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.codeclaration
 * @className     : IOpenSystemService.java
 * @description   : 开系统欠费审核
 * @author        : maocy
 * @createDate    : 2015-12-30
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public interface IOpenSystemService extends IBaseService{
	
    /**
     * 
     * 方法名称：开系统欠费审核
     * 方法描述：开系统欠费审核
     * @param debtOpenSys 审核信息
     * @throws HsException
     */
	public void apprDebtOpenSys(DebtOpenSys debtOpenSys) throws HsException;

}
