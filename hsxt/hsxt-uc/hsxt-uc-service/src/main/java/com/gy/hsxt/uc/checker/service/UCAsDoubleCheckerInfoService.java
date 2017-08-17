package com.gy.hsxt.uc.checker.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.checker.IUCAsDoubleCheckerInfoService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.checker.AsDoubleChecker;
import com.gy.hsxt.uc.as.bean.sysoper.AsSysOper;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.checker.bean.DoubleChecker;
import com.gy.hsxt.uc.checker.mapper.DoubleCheckerMapper;
import com.gy.hsxt.uc.common.service.UCAsPwdService;
@Service
public class UCAsDoubleCheckerInfoService implements IUCAsDoubleCheckerInfoService {
	private static final String MOUDLENAME = "com.gy.hsxt.uc.checker.service.UCAsDoubleCheckerInfoService";
	@Autowired
	DoubleCheckerMapper doubleCheckerMapper;
	@Autowired
	CommonCacheService commonCacheService;
	@Autowired
	UCAsPwdService pwdService;
	@Override
	public void regDoubleChecker(AsSysOper asSysOper)
			throws HsException {
		validRegParams(asSysOper);
		DoubleChecker checker = new DoubleChecker();
		BeanUtils.copyProperties(asSysOper, checker);
		try {
			doubleCheckerMapper.insertSelective(checker);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "regDoubleChecker", "注册双签员异常：custId["+asSysOper.getOperCustId()+"],\r\n", e);
		}
	}
	



	
	
	@Override
	public AsDoubleChecker searchDoubleCheckerByCustId(String checkCustId)
			throws HsException {
		if(StringUtils.isBlank(checkCustId)){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "客户号不能为空");
		}
		DoubleChecker checker = commonCacheService.searchDoubleCheckerInfo(checkCustId);
		AsDoubleChecker asDoubleChecker = new AsDoubleChecker();
		BeanUtils.copyProperties(checker, asDoubleChecker);
		return asDoubleChecker;
	}

	@Override
	public List<AsDoubleChecker> ListDoubleChecker(String platformCode,
			String subSystemCode,Page page) throws HsException {
		List<AsDoubleChecker> finalList = new ArrayList<>();
		List<DoubleChecker> list = new ArrayList<>();
		try {
			list = doubleCheckerMapper.ListDoubleCheckerInfo(platformCode, subSystemCode, page);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "ListDoubleChecker", "查询双签员列表异常：\r\n", e);
		}
		AsDoubleChecker asDoubleChecker = null;
		for(DoubleChecker checker : list){
			asDoubleChecker = new AsDoubleChecker();
			BeanUtils.copyProperties(checker, asDoubleChecker);
			finalList.add(asDoubleChecker);
		}
		return finalList;
	}

	@Override
	public void updateDoubleCheckerInfo(AsDoubleChecker doubleChecker)
			throws HsException {
		if(null == doubleChecker){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "doubleChecker is null");
		}
		if(StringUtils.isBlank(doubleChecker.getOperCustId())){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "客户号不能为空");
		}
		DoubleChecker checker = new DoubleChecker();
		BeanUtils.copyProperties(doubleChecker, checker);
		try {
			doubleCheckerMapper.updateByPrimaryKeySelective(checker);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "updateDoubleCheckerInfo", "更新双签员列表信息异常：custId["+doubleChecker.getOperCustId()+"]\r\n", e);
		}
	}

	@Override
	public void modifyAdminLogPwd(AsDoubleChecker doubleChecker)
			throws HsException {
		validPwdParams(doubleChecker);
		DoubleChecker checker = commonCacheService.searchDoubleCheckerInfo(doubleChecker.getOperCustId());
		if(1 != checker.getIsAdmin()){
			throw new HsException(ErrorCodeEnum.OPER_IS_NOT_ADMIN.getValue(), ErrorCodeEnum.OPER_IS_NOT_ADMIN.getDesc()+",custId["+doubleChecker.getOperCustId()+"]");
		}
		pwdService.updateLoginPwd(doubleChecker.getOperCustId(), UserTypeEnum.CHECKER.getType(), doubleChecker.getPwdLogin(), doubleChecker.getNewPwdLogin(), doubleChecker.getSecretKey());
	}

	@Override
	public void modifyDouboleCheckerLogPwd(AsDoubleChecker doubleChecker)
			throws HsException {
		validPwdParams(doubleChecker);
		DoubleChecker checker = commonCacheService.searchDoubleCheckerInfo(doubleChecker.getOperCustId());
		if(1 == checker.getIsAdmin()){
			throw new HsException(ErrorCodeEnum.ADMIN_NOI_ALLOWED_CHANGEPWD.getValue(), ErrorCodeEnum.ADMIN_NOI_ALLOWED_CHANGEPWD.getDesc()+",custId["+doubleChecker.getOperCustId()+"]");
		}
		pwdService.updateLoginPwd(doubleChecker.getOperCustId(), UserTypeEnum.CHECKER.getType(), doubleChecker.getPwdLogin(), doubleChecker.getNewPwdLogin(), doubleChecker.getSecretKey());
	}
	
	
	/**
	 * 检查入参
	 * @param doubleChecker
	 * @throws HsException
	 */
	private void validRegParams(AsSysOper asSysOper) throws HsException{
		if(null == asSysOper){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "doubleChecker is null");
		}
		if(StringUtils.isBlank(asSysOper.getOperCustId())){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "客户号不能为空");
		}
		if(StringUtils.isBlank(asSysOper.getRandomToken())){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "AES秘钥不能为空");
		}
		if(StringUtils.isBlank(asSysOper.getPwdLogin())){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "AES登录密码不能为空");
		}
		if(StringUtils.isBlank(asSysOper.getUserName())){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "用户名不能为空");
		}
	}

	/**
	 * 检查入参
	 * @param doubleChecker
	 * @throws HsException
	 */
	private void validPwdParams(AsDoubleChecker doubleChecker) throws HsException{
		if(null == doubleChecker){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "doubleChecker is null");
		}
		if(StringUtils.isBlank(doubleChecker.getOperCustId())){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "客户号不能为空");
		}
		if(StringUtils.isBlank(doubleChecker.getSecretKey())){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "AES秘钥不能为空");
		}
		if(StringUtils.isBlank(doubleChecker.getPwdLogin())){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "AES登录密码不能为空");
		}
		if(StringUtils.isBlank(doubleChecker.getNewPwdLogin())){
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "AES新登录密码不能为空");
		}
	}
}
