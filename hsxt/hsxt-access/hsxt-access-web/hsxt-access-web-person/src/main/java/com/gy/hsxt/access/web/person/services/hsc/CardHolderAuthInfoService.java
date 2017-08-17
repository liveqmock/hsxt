package com.gy.hsxt.access.web.person.services.hsc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.entstatus.IBSRealNameAuthService;
import com.gy.hsxt.bs.bean.entstatus.PerRealnameAuth;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderAuthInfoService;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameAuth;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameReg;

		

/***************************************************************************
 * <PRE>
 * Description 		:  查询实名注册信息服务类
 *
 * Project Name   	: hsxt-access-web-person
 *
 * Package Name     : com.gy.hsxt.access.web.person.hsc.service.impl
 *
 * File Name        : AccountServiceImpl
 * 
 * Author           : LiZhi Peter
 *
 * Create Date      : 2015-8-7 下午6:30:57
 *
 * Update User      : LiZhi Peter
 *
 * Update Date      : 2015-8-7 下午6:30:57
 *
 * UpdateRemark 	: 说明本次修改内容
 *
 * Version          : v0.0.1
 *
 * </PRE>
 ***************************************************************************/
@Service
public class CardHolderAuthInfoService extends BaseServiceImpl implements ICardHolderAuthInfoService {
    
    //用户中心-查询实名注册信息dubbo
    @Autowired private IUCAsCardHolderAuthInfoService ucCardHolderAuthInfoService;
    
   //用户中心-bs实名认证信息 dubbo
    @Autowired private IBSRealNameAuthService bsRealNameAuthService;
    
    /**
     * 查询实名注册信息
     * @param custId
     * @return
     * @throws HsException
     */
    @Override
    public AsRealNameReg searchRealNameRegInfo(String custId) throws HsException {
      return ucCardHolderAuthInfoService.searchRealNameRegInfo(custId);
    }

    /**
     * 实名注册
     * @param asRealNameReg 实名注册实体
     * @throws HsException
     */
    @Override
    public void saveRealNameReg(AsRealNameReg asRealNameReg) throws HsException {
        this.ucCardHolderAuthInfoService.regByRealName(asRealNameReg);
    }

    /**
     *  查询实名认证状态
     * @param custId 客户号
     * @return (1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证)
     * @throws HsException
     */
    @Override
    public String findAuthStatusByCustId(String custId) throws HsException {
        return this.ucCardHolderAuthInfoService.findAuthStatusByCustId(custId);
    }

    /**
     *  查询UC实名认证信息
     * @param custId 客户号
     * @return 实名认证信息
     * @throws HsException
     */
    @Override
    public AsRealNameAuth searchRealNameAuthInfo(String custId) throws HsException {
        return this.ucCardHolderAuthInfoService.searchRealNameAuthInfo(custId);
    }

    /**
     *  查询审批的实名认证信息
     * @param perCustId
     * @return
     */
    @Override
    public PerRealnameAuth queryPerRealnameAuthByCustId(String perCustId) throws HsException {
        return this.bsRealNameAuthService.queryPerRealnameAuthByCustId(perCustId);
    }

    /**
     *  实名认证-新增
     * @param  perRealnameAuth 实名认证实体
     * @return 
     * @throws HsException
     */
    @Override
    public void createPerRealnameAuth(PerRealnameAuth perRealnameAuth) throws HsException {
        this.bsRealNameAuthService.createPerRealnameAuth(perRealnameAuth);
    }
    
   
    /**
     *  实名认证-修改
     * @param  perRealnameAuth 实名认证实体
     * @return 
     * @throws HsException
     */
	public void modifyPerRealnameAuth(PerRealnameAuth perRealnameAuth) throws HsException{
		this.bsRealNameAuthService.modifyPerRealnameAuth(perRealnameAuth);
	}

}

	