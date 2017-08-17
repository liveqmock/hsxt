package com.gy.hsxt.access.web.person.services.hsc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.person.bean.BankInfo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;

/***************************************************************************
 * <PRE>
 * Description 		: 银行卡操作服务类
 * 
 * Project Name   	: hsxt-access-web-person
 * 
 * Package Name     : com.gy.hsxt.access.web.person.hsc.service.impl
 * 
 * File Name        : BankCardServiceImpl
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-8-28 下午6:56:27  
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-8-28 下午6:56:27
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v0.0.1
 * 
 * </PRE>
 ***************************************************************************/
@Service
public class BankCardService extends BaseServiceImpl implements IBankCardService {
    // 用户中心- 银行账户管理 dubbo
    @Resource private IUCAsBankAcctInfoService ucAsBankAcctInfoService;

    /**
     * 根据客户id获取银行列表
     * @param custId    客户号
     * @param userType  用户类 型
     * @return
     * @throws HsException
     */
    @Override
    public List<AsBankAcctInfo> findBankAccountList(String custId, String userType) throws HsException {
        return this.ucAsBankAcctInfoService.listBanksByCustId(custId, userType);
    }

    /**
     * 根据绑定银行id查询详细信息
     * 
     * @param bankAcctInfo  银行卡信息
     * @param userType      用户类型
     * @throws HsException
     */
    @Override
    public void addBankBind(AsBankAcctInfo bankAcctInfo, String userType) throws HsException{
        this.ucAsBankAcctInfoService.bindBank(bankAcctInfo, userType);
    }

    /**
     * 删除绑定的银行卡
     * 
     * @param bankId  绑定银行卡id
     * @param userType 用户类型 
     * @throws HsException
     */
    @Override
    public void delBankBind(Long bankId, String userType)throws HsException{
        this.ucAsBankAcctInfoService.unBindBank(bankId, userType);
    }
    
  

    

    

}
