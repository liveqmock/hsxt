package com.gy.hsxt.access.web.company.services.systemBusiness;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
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
    
    @Override
    public PageData findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
        String returnString = "[{\"acctType\":null,\"bankAccount\":\"6000867090127856\",\"bankAreaNo\":\"440104\",\"defaultFlag\":\"N\",\"bankAcctId\":\"77150aec-ff60-4f72-bb74-788fcf75a8ec\",\"custResNo\":\"01003010728\",\"custResType\":\"M\",\"bankBranch\":null,\"usedFlag\":\"N\",\"custId\":\"0100301072820150519\",\"bankCode\":\"111\",\"provinceCode\":\"44\",\"cityName\":\"深圳\",\"bankAcctName\":\"张三\"},{\"acctType\":null,\"bankAccount\":\"1234567890123456\",\"bankAreaNo\":\"440104\",\"defaultFlag\":\"Y\",\"bankAcctId\":\"77150aec-ff60-4f72-bb74-788fcf75a8ec\",\"custResNo\":\"01003010728\",\"custResType\":\"M\",\"bankBranch\":null,\"usedFlag\":\"N\",\"custId\":\"0100301072820150519\",\"bankCode\":\"111\",\"provinceCode\":\"44\",\"cityName\":\"广州\",\"bankAcctName\":\"李四\"}]";
        return super.findScrollResult(filterMap, sortMap, page);
    }

    @Override
    public String bankBind(String custId, String pointNo, String name, String currency, String bank, String isDefault,
            String bankAddr, String bankNo, String bankNoRe) {
        String returnString = "{\"code\":0,\"data\":{\"resultMsg\":\"成功!\",\"resultCode\":0}}";
        return returnString;
    }

    @Override
    public String findBankBindById(String custId, String bankId) {
        String returnString = "{\"acctType\":null,\"bankAccount\":\"1234567890123456\",\"bankAreaNo\":\"440104\",\"defaultFlag\":\"N\",\"bankAcctId\":\"77150aec-ff60-4f72-bb74-788fcf75a8ec\",\"custResNo\":\"01003010728\",\"custResType\":\"M\",\"bankBranch\":null,\"usedFlag\":\"N\",\"custId\":\"0100301072820150519\",\"bankCode\":\"111\",\"provinceCode\":\"44\",\"cityName\":\"深圳\",\"bankAcctName\":\"张三\"}";
        return returnString;
    }

 

    

}
