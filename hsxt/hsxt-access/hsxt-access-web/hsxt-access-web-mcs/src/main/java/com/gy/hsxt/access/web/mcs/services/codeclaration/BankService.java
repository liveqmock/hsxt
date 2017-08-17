package com.gy.hsxt.access.web.mcs.services.codeclaration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.apply.IBSDeclareService;
import com.gy.hsxt.bs.bean.apply.DeclareBank;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-mcs
 * @package       : com.gy.hsxt.access.web.mcs.services.codeclaration
 * @className     : BankkService.java
 * @description   : 企业申报-银行信息接口
 * @author        : maocy
 * @createDate    : 2015-12-09
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public class BankService extends BaseServiceImpl implements IBankService {
    
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
    public DeclareBank findBankByApplyId(String applyId) throws HsException {
        return this.bsService.queryBankByApplyId(applyId);
    }
    
    /**
     * 
     * 方法名称：编辑银行账户信息
     * 方法描述：编辑银行账户信息
     * @param bank 银行账户对象
     * @return 银行账户ID
     * @throws HsException
     */
    @Override
    public String manageSaveBank(DeclareBank bank) throws HsException {
        return this.bsService.manageSaveBank(bank);
    }
	
}
