package com.gy.hsxt.access.web.aps.services.invoice.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gy.hsxt.access.web.aps.services.invoice.ICompanyInforService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.services.invoice.imp
 * @className : CompanyInforService.java
 * @description : 企业信息查询，银行账号查询
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

	/**
	 * 根据 custId 查询企业的所有信息
	 * 
	 * @param custId
	 *            :企业客户ID
	 * @param request
	 * @return
	 */
	@Override
	public AsEntAllInfo findAllByCustId(String custId) throws HsException {

		return iuCAsEntService.searchEntAllInfo(custId);
	}

	/**
	 * 根据 custId 查询企业的重要信息
	 * 
	 * @param custId
	 *            :企业客户ID
	 * @param request
	 * @return
	 */
	@Override
	public AsEntMainInfo findMainByCustId(String custId) throws HsException {

		return iuCAsEntService.searchEntMainInfo(custId);
	}
	/**
	 * 根据 resNo 查询企业的重要信息
	 * 
	 * @param resNO
	 *            :企业resNo
	 * @param request
	 * @return
	 */
	@Override
	public AsEntMainInfo findMainByResNo(String resNo) throws HsException {

		return iuCAsEntService.getMainInfoByResNo(resNo);
	}

	/**
	 * 根据 custId 查询企业的基本信息
	 * 
	 * @param custId
	 *            :企业客户ID
	 * @param request
	 * @return
	 */
	@Override
	public AsEntBaseInfo findBaseByCustId(String custId) throws HsException {
		return iuCAsEntService.searchEntBaseInfo(custId);
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
	 * 通过企业互生号查询企业客户号
	 * 
	 * @param entResNo
	 *            :企业互生号
	 * @param request
	 * @return
	 */
	@Override
	public String findEntCustIdByEntResNo(String entResNo) throws HsException {
		return iuCAsEntService.findEntCustIdByEntResNo(entResNo);
	}

	/**
	 * 查询企业状态信息
	 * 
	 * @param custId
	 *            企业id
	 * @return
	 * @throws HsException
	 */
	@Override
	public AsEntStatusInfo searchEntStatusInfo(String custId)
			throws HsException {
		AsEntStatusInfo info = iuCAsEntService.searchEntStatusInfo(custId);
		return info;
	}
}
