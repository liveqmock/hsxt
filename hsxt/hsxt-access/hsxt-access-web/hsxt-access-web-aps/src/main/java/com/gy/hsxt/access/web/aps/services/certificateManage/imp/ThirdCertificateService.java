/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.certificateManage.imp;

import java.net.URLDecoder;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.certificateManage.IThirdCertificateService;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.api.apply.IBSCertificateService;
import com.gy.hsxt.bs.bean.apply.Certificate;
import com.gy.hsxt.bs.bean.apply.CertificateBaseInfo;
import com.gy.hsxt.bs.bean.apply.CertificateContent;
import com.gy.hsxt.bs.bean.apply.CertificateQueryParam;
import com.gy.hsxt.bs.bean.apply.CertificateSendHis;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;

import javax.annotation.Resource;

/**
 * 第三方证书Service实现类
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.contractManage.imp
 * @ClassName: ThirdCertificateService
 * @Description: TODO
 * @author: likui
 * @date: 2016年1月22日 上午11:09:12
 * @company: gyist
 * @version V3.0.0
 */
@Service
public class ThirdCertificateService extends BaseServiceImpl<ThirdCertificateService> implements
		IThirdCertificateService {

	/**
	 * BS证书Service
	 */
	@Resource
	private IBSCertificateService bSCertificateService;

	/**
	 * 分页查询第三方证书发放
	 * 
	 * @Description:
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 */
	@Override
	public PageData<CertificateBaseInfo> queryThirdCertificateGiveOutByPage(Map<String, Object> filterMap,
			Map<String, Object> sortMap, Page page)
	{

		CertificateQueryParam param = null;
		try
		{
			param = new CertificateQueryParam();
			param.setEntResNo((String) filterMap.get("entResNo"));
			param.setCertificateNo((String) filterMap.get("certificateNo"));
			String entCustName = (String) filterMap.get("entCustName");
			entCustName = StringUtils.isNotBlank(entCustName) ? URLDecoder.decode(entCustName, "UTF-8") : entCustName;
			param.setEntName(entCustName);
			Object printStatusObj = filterMap.get("printStatus");
			if( printStatusObj !=null ){
				param.setPrintStatus((Boolean.parseBoolean((String)printStatusObj)));
			}
			Object sendStatusObj = filterMap.get("sendStatus");
			if( sendStatusObj !=null ){
				param.setSendStatus((Boolean.parseBoolean((String)sendStatusObj)) );
			}
			PageData<CertificateBaseInfo> pd = bSCertificateService.queryBizCre4Send(param, page);
			return pd;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryThirdCertificateGiveOutByPage", "分页查询第三方证书发放失败", ex);
		}
		return null;
	}

	/**
	 * 打印第三方证书
	 * 
	 * @Description:
	 * @param certificateNo
	 * @param custId
	 * @return
	 * @throws HsException
	 */
	@Override
	public CertificateContent printThirdCertificate(String certificateNo, String custId)
	{
		try
		{
			return bSCertificateService.printCertificate(certificateNo, custId);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "printThirdCertificate", "打印第三方证书失败", ex);
		}
		return null;
	}

	/**
	 * 发放第三方证书
	 * 
	 * @Description:
	 * @param bean
	 * @throws HsException
	 */
	@Override
	public void giveOutThirdCertificate(CertificateSendHis bean) throws HsException
	{
		bSCertificateService.sendCertificate(bean);
	}

	/**
	 * 查询第三方证书发放历史
	 * 
	 * @Description:
	 * @param filterMap
	 * @return
	 */
	@Override
	public PageData<CertificateSendHis> queryThirdCertificateGiveOutRecode(Map<String, Object> filterMap,
			Map<String, Object> sortMap, Page page)throws HsException
	{
		try
		{
			String certificateNo = (String)filterMap.get("certificateNo");
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { certificateNo,
					ASRespCode.ASP_CONTRACTNO_INVALID.getCode(), ASRespCode.ASP_CONTRACTNO_INVALID.getDesc() });
			PageData<CertificateSendHis> pd = bSCertificateService.queryCreSendHis(certificateNo, page);
			return pd;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryThirdCertificateGiveOutRecode", "查询第三方证书发放历史失败", ex);
		}
		return null;
	}

	/**
	 * 分页查询第三方证书发放历史
	 * 
	 * @Description:
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 */
	@Override
	public PageData<CertificateBaseInfo> queryThirdCertificateRecodeByPage(Map<String, Object> filterMap,
			Map<String, Object> sortMap, Page page)
	{
		CertificateQueryParam param = null;
		try
		{
			param = new CertificateQueryParam();
			param.setEntResNo((String) filterMap.get("entResNo"));
			String entCustName = (String) filterMap.get("entCustName");
			entCustName = StringUtils.isNotBlank(entCustName) ? URLDecoder.decode(entCustName, "UTF-8") : entCustName;
			param.setEntName(entCustName);
			
			Object sealStatusObj = filterMap.get("sealStatus");
			if(sealStatusObj!=null){
				param.setSealStatus((Integer.parseInt((String)sealStatusObj)));
			}
			
			Object printStatusObj = filterMap.get("printStatus");
			if(printStatusObj!=null){
				param.setPrintStatus((Boolean.parseBoolean((String)printStatusObj)));
			}
			
			Object sendStatusObj = filterMap.get("sendStatus");
			if(sendStatusObj!=null){
				param.setSendStatus((Boolean.parseBoolean((String)sendStatusObj)));
			}
			param.setCertificateNo((String)filterMap.get("certificateNo"));
			return bSCertificateService.queryBizCre4His(param, page);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryThirdCertificateRecodeByPage", "分页查询第三方证书发放历史失败", ex);
		}
		return null;
	}
	
	/**
	 * 通过ID查询证书内容
	 * @param certificateNo
	 * @param realTime 是否实时
	 * @return
	 * @throws HsException
	 */
	public CertificateContent queryCreContentById(String certificateNo,boolean realTime)throws HsException
	{
		RequestUtil.verifyParamsIsNotEmpty(
				new Object[] { certificateNo, ASRespCode.ASP_CERTIFICATENO_INVALID.getCode(), ASRespCode.ASP_CERTIFICATENO_INVALID.getDesc()}
		);
		return bSCertificateService.queryCreContentById(certificateNo,realTime);
	}
}
