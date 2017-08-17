package com.gy.hsxt.access.web.company.services.systemBusiness;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;

/***************************************************************************
 * <PRE>
 * Description 		: 银行卡操作服务层
 * 
 * Project Name   	: hsxt-access-web-person
 * 
 * Package Name     : com.gy.hsxt.access.web.person.hsc.service
 * 
 * File Name        : IAccountService1
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-8-28 下午6:54:41  
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-8-28 下午6:54:41
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v0.0.1
 * 
 * </PRE>
 ***************************************************************************/
@Service
public interface IBankCardService extends IBaseService {
    /**
     * 根据客户id获取银行列表
     * 
     * @param custId
     *            客户号
     * @param userType
     *            用户类 型
     * @return
     * @throws HsException
     */
    public List<AsBankAcctInfo> findBankAccountList(String custId, String userType) throws HsException;

    /**
     * 根据绑定银行id查询详细信息
     * 
     * @param bankAcctInfo
     *            银行卡信息
     * @param userType
     *            用户类型
     * @throws HsException
     */
    public void addBankBind(AsBankAcctInfo bankAcctInfo, String userType) throws HsException;

    /**
     * 删除绑定的银行卡
     * 
     * @param bankId
     *            绑定银行卡id
     * @param userType
     *            用户类型
     * @throws HsException
     */
    public void delBankBind(Long bankId, String userType) throws HsException;

    /**
     * 根据绑定银行id查询详细信息
     * 
     * @param bankId
     *            银行id
     * @return
     */
    public String findBankBindById(String custId, String bankId);

    /**
     * 绑定用户银行卡操作
     * 
     * @param custId
     *            客户号
     * @param pointNo
     *            互生卡号
     * @param name
     *            真实姓名
     * @param currency
     *            结算币种
     * @param bank
     *            开户银行
     * @param isDefault
     *            默认银行账号
     * @param bankAddr
     *            开户地区
     * @param bankNo
     *            银行卡号
     * @param bankNoRe
     *            确认卡号
     * @return
     */
    public String bankBind(String custId, String pointNo, String name, String currency, String bank, String isDefault,
            String bankAddr, String bankNo, String bankNoRe);
}
