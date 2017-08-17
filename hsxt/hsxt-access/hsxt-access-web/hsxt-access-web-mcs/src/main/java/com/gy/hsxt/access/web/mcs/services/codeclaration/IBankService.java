package com.gy.hsxt.access.web.mcs.services.codeclaration;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.apply.DeclareBank;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-mcs
 * @package       : com.gy.hsxt.access.web.mcs.services.codeclaration
 * @className     : IBankService.java
 * @description   : 企业申报-银行信息接口
 * @author        : maocy
 * @createDate    : 2015-12-09
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public interface IBankService extends IBaseService{
    
    /**
     * 
     * 方法名称：查询银行账户信息
     * 方法描述：查询银行账户信息
     * @param applyId 申请编号
     * @return
     * @throws HsException
     */
    public DeclareBank findBankByApplyId(String applyId) throws HsException;
    
    /**
     * 
     * 方法名称：修改银行账户信息
     * 方法描述：企业申报新增-保存银行账户信息
     * @param bank 银行账户对象
     * @return 银行账户ID
     * @throws HsException
     */
    public String manageSaveBank(DeclareBank bank) throws HsException;

}
