/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.scs.services.companyInfo.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.access.web.bean.UpdateEmailBean;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.scs.services.companyInfo.IEntMainInfoService;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.Base64Utils;
import com.gy.hsxt.uc.as.api.common.IUCAsEmailService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;

/**
 * 企业重要信息的操作
 * 
 * @Package: com.gy.hsxt.access.web.scs.services.companyInfo.imp
 * @ClassName: EntMainInfoServiceImpl
 * @Description: TODO
 *
 * @author: liuxy
 * @date: 2015-11-17 下午2:49:04
 * @version V1.0
 */
@Service
public class EntMainInfoServiceImpl implements IEntMainInfoService {
	@Autowired
	private IUCAsEntService ucService;
	@Autowired
	private IUCAsEmailService ucEmailService;

	@Override
	public AsEntMainInfo findEntMainInfo(String entCustId)
	{
		return ucService.searchEntMainInfo(entCustId);
	}

	@Override
	public void updateAuthProxyFile(String authProxyFile, String operator, String entCustId)
	{
		ucService.uploadAuthProxyFile(entCustId, authProxyFile, operator);

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
		ucEmailService.sendMailForValidEmail(email, userName, entResNo, userType, CustType.SERVICE_CORP.getCode());
	}
}
