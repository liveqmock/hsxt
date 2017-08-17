package com.gy.hsxt.access.web.company.services.systemBusiness.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.company.services.systemBusiness.ICompanyInforService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;

/**
 * 
 * @projectName : hsxt-access-web-company
 * @package : com.gy.hsxt.access.web.company.services.systemBusiness.imp
 * @className : CompanyInforService.java
 * @description : 企业信息查询
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@Service("invoiceCompanyInforService")
public class CompanyInforService extends BaseServiceImpl<CompanyInforService>
		implements ICompanyInforService {
	@Autowired
	private IUCAsEntService iuCAsEntService;
	@Autowired
	private IUCAsBankAcctInfoService iuCAsBankInfoService;
	@Autowired
	private IUCAsCardHolderService iucAsCardHolderService;
	@Autowired
	private IUCAsPwdService iucasPwdService;

	/**
	 * 根据 resNo 查询企业的重要信息
	 * 
	 * @param resNo
	 *            :企业互生号
	 * @param request
	 * @return
	 */
	@Override
	public AsEntMainInfo findMainByResNo(String resNo) throws HsException {

		return iuCAsEntService.getMainInfoByResNo(resNo);
	}
	
	/**
	 * 根据 resNo 查询企业基础信息
	 * 
	 * @param entCustId
	 *            :企业互生号
	 * @param request
	 * @return
	 */
	@Override
	public AsEntBaseInfo findBaseInfoCustId(String entCustId) throws HsException {
		return iuCAsEntService.searchEntBaseInfo(entCustId);
	}

	/**
	 * 根据 custId 查询默认银行账户
	 * 
	 * @param custId
	 *            :企业客户ID
	 * @param request
	 * @return
	 */
	@Override
	public AsBankAcctInfo searchDefaultBankAcc(String custId)
			throws HsException {
		String userType = "4";
		AsBankAcctInfo bankAcctInfo = iuCAsBankInfoService
				.searchDefaultBankAcc(custId, userType);
//		if (bankAcctInfo == null) {
//			List<AsBankAcctInfo> listBank = iuCAsBankInfoService
//					.listBanksByCustId(custId, userType);
//			if(listBank!=null && listBank.size()>0){
//				bankAcctInfo=listBank.get(0);
//			}
//		}
		return bankAcctInfo;
	}

	/**
	 * 通过消费者互生号查询消费者客户号
	 * 
	 * @param perResNo
	 *            :消费者互生号
	 * @param request
	 * @return
	 */
	@Override
	public String findCustIdByResNo(String perResNo) throws HsException {
		return iucAsCardHolderService.findCustIdByResNo(perResNo);
	}

	/**
	 * 验证登陆密码是否正确
	 * @param custId
	 *            :客户号
	 * @param loginPwd
	 *            :登陆密码(AES加密后的密文)
	 * @param secretKey
	 *            :AES秘钥（随机报文校验token)
	 */
	public void checkLoginPwd(String custId, String loginPwd, String secretKey) throws HsException{
		String userType = "3";// 1：非持卡人 2：持卡人 3：操作员
		iucasPwdService.checkLoginPwd(custId, loginPwd, userType, secretKey);
	}
}
