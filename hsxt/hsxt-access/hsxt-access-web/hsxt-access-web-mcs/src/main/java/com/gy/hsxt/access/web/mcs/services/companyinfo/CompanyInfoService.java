/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.mcs.services.companyinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.access.web.bean.UpdateEmailBean;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.Base64Utils;
import com.gy.hsxt.uc.as.api.common.IUCAsEmailService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;

/**
 * 
 * @projectName : hsxt-access-web-mcs
 * @package : com.gy.hsxt.access.web.mcs.services.companyinfo
 * @className : CompanyInfoService.java
 * @description : 企业系统信息接口实现
 * @author : maocy
 * @createDate : 2016-01-12
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public class CompanyInfoService extends BaseServiceImpl implements ICompanyInfoService {

	@Autowired
	private IUCAsEntService ucService;// UC服务类

	@Autowired
	private IUCAsEmailService ucEmailService;

	/**
	 * 
	 * 方法名称：查询企业信息 方法描述：查询企业信息
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @throws HsException
	 */
	public AsEntExtendInfo findEntAllInfo(String entCustId) throws HsException
	{
		return this.ucService.searchEntExtInfo(entCustId);
	}

	/**
	 * 
	 * 方法名称：更新企业信息 方法描述：更新企业信息
	 * 
	 * @param entExtInfo
	 *            企业信息
	 * @param operator
	 *            操作用户id
	 * @throws HsException
	 */
	public void updateEntExtInfo(AsEntExtendInfo entExtInfo, String operator) throws HsException
	{
		this.ucService.updateEntExtInfo(entExtInfo, operator);
	}

	/**
	 * 校验邮件
	 * 
	 * @param randomToken
	 * @param email
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.person.services.consumer.ICardholderService#checkEmailCode(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void checkEmailCode(String param) throws HsException
	{
			byte[] paramByte = Base64Utils.decode(param);
			if (paramByte == null)
			{
			throw new HsException(ASRespCode.AS_PARAM_INVALID);
			}
			String paramStr = new String(paramByte);
			UpdateEmailBean ueb = JSON.parseObject(paramStr, UpdateEmailBean.class);
			ucEmailService.bindEmail(ueb.getCustId(), ueb.getRandom(), ueb.getEmail(), ueb.getUserType());
	}

	/**
	 * 方法名称：发送验证邮件 方法描述： 发送验证邮件
	 * 
	 * @param email
	 * @param userName
	 * @param entResNo
	 * @param userType
	 */
	public void validEmail(String email, String userName, String entResNo, String userType) throws HsException
	{
		ucEmailService.sendMailForValidEmail(email, userName, entResNo, userType, CustType.MANAGE_CORP.getCode());
	}
}