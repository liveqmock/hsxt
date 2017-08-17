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
import com.gy.hsxt.access.web.aps.services.certificateManage.ISellCertificateServuce;
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
 * 销售资格证书Service实现类
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.certificateManage.imp
 * @ClassName: SellCertificateServuce
 * @Description: TODO
 * @author: likui
 * @date: 2016年1月22日 上午11:08:14
 * @company: gyist
 * @version V3.0.0
 */
@Service
public class SellCertificateServuce extends BaseServiceImpl<SellCertificateServuce> implements ISellCertificateServuce {

	/**
	 * BS证书Service
	 */
	@Resource
	private IBSCertificateService bSCertificateService;

	/**
	 * 分页查询消费资格证书盖章
	 * 
	 * @Description:
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 */
	@Override
	public PageData<CertificateBaseInfo> querySellCertificateBySeal(Map<String, Object> filterMap,
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
			if( sealStatusObj != null ){
				param.setSealStatus(Integer.parseInt((String)sealStatusObj));
			}
			return bSCertificateService.querySaleCre4Seal(param, page);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "querySellCertificateBySeal", "分页查询消费资格证书盖章失败", ex);
		}
		return null;
	}

	/**
	 * 销售资格证书盖章
	 * 
	 * @Description:
	 * @param certificateNo
	 * @param custId
	 * @throws HsException
	 */
	@Override
	public void sellCertificateSeal(String certificateNo, String custId) throws HsException
	{
		try
		{
			bSCertificateService.sealCertificate(certificateNo, custId);
		} catch (HsException ex)
		{
			// 12000 参数错误
			// 12689 证书盖章失败
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "sellCertificateSeal," + certificateNo + "," + custId,
					"销售资格证书盖章失败", ex);
			throw new HsException(RespCode.FAIL);
		}
	}

	/**
	 * 根据ID查询销售资格证书
	 * 
	 * @Description:
	 * @param certificateNo
	 * @return
	 */
	@Override
	public Certificate querySellCertificateById(String certificateNo)
	{
		try
		{
			return bSCertificateService.queryCreById(certificateNo);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "querySellCertificateById", "根据ID查询销售资格证书失败", ex);
		}
		return null;
	}

	/**
	 * 查询销售资格证书内容
	 * 
	 * @Description:
	 * @param certificateNo
	 * @return
	 */
	@Override
	public CertificateContent querySellCertificateContent(String certificateNo)
	{
		try
		{
			return bSCertificateService.queryCreContentById(certificateNo,false);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "querySellCertificateContent", "查询销售资格证书内容失败", ex);
		}
		return null;
	}

	/**
	 * 分页查询证书发放
	 * 
	 * @Description:
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 */
	@Override
	public PageData<CertificateBaseInfo> querySellCertificateGiveOutByPage(Map<String, Object> filterMap,
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
			if( printStatusObj != null ){
				param.setPrintStatus(Boolean.parseBoolean((String)printStatusObj));
			}
			
			Object sendStatusObj = filterMap.get("sendStatus");
			if( sendStatusObj != null ){
				param.setSendStatus(Boolean.parseBoolean((String)sendStatusObj));
			}
			
			return bSCertificateService.querySaleCre4Send(param, page);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "querySellCertificateGiveOutByPage", "分页查询证书发放失败", ex);
		}
		return null;
	}

	/**
	 * 打印销售资格证书
	 * 
	 * @Description:
	 * @param certificateNo
	 * @param custId
	 * @return
	 * @throws HsException
	 */
	@Override
	public CertificateContent printSellCertificate(String certificateNo, String custId) throws HsException
	{
		try
		{
			return bSCertificateService.printCertificate(certificateNo, custId);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "printSellCertificate", "打印销售资格证书失败", ex);
		}
		return null;
	}

	/**
	 * 销售资格证书发放
	 * 
	 * @Description:
	 * @param giveOut
	 * @throws HsException
	 */
	@Override
	public void giveOutSellCertificate(String giveOut) throws HsException
	{
		CertificateSendHis bean = null;
		try
		{
			bean = JSONObject.parseObject(URLDecoder.decode(giveOut, "UTF-8"), CertificateSendHis.class);
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { bean.getCertificateNo(),
					ASRespCode.ASP_DOC_UNIQUENO_INVALID.getCode(), ASRespCode.ASP_DOC_UNIQUENO_INVALID.getDesc() },
					new Object[] { bean.getSendOperator(), ASRespCode.ASP_DOC_OPTCUSTID_INVALID.getCode(),
							ASRespCode.ASP_DOC_OPTCUSTID_INVALID.getDesc() });
			bSCertificateService.sendCertificate(bean);
		} catch (HsException ex)
		{
			// 12000 参数错误
			// 12690 发放证书失败
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "giveOutSellCertificate," + giveOut, "销售资格证书发放失败", ex);
			throw new HsException(RespCode.FAIL);
		}
	}

	/**
	 * 查询销售资格证书发放历史
	 * 
	 * @Description:
	 * @param certificateNo
	 * @return
	 */
	@Override
	public PageData<CertificateSendHis> querySellCertificateGiveOutRecode(String certificateNo,Page page)throws HsException
	{
		try
		{
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { certificateNo,
					ASRespCode.ASP_CONTRACTNO_INVALID.getCode(), ASRespCode.ASP_CONTRACTNO_INVALID.getDesc() });
			return bSCertificateService.queryCreSendHis(certificateNo,page);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "querySellCertificateGiveOutRecode", "查询销售资格证书发放历史失败", ex);
		}
		return null;
	}

	/**
	 * 分页查询销售资格证书发放历史
	 * 
	 * @Description:
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 */
	@Override
	public PageData<CertificateBaseInfo> querySellCertificateRecodeByPage(Map<String, Object> filterMap,
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
			Object printStatusObj = filterMap.get("printStatus");
			if( printStatusObj !=null ){
				param.setPrintStatus((Boolean.parseBoolean((String)printStatusObj)));
			}
			Object sendStatusObj = filterMap.get("sendStatus");
			if( sendStatusObj !=null ){
				param.setSendStatus((Boolean.parseBoolean((String)sendStatusObj)) );
			}
			Object sealStatusObj = filterMap.get("sealStatus");
			if( sealStatusObj !=null ){
				param.setSealStatus((Integer.parseInt((String)sealStatusObj)) );
			}
			param.setCertificateNo((String)filterMap.get("certificateNo"));
			return bSCertificateService.querySaleCre4His(param, page);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "querySellCertificateRecodeByPage", "分页查询销售资格证书发放历史失败", ex);
		}
		return null;
	}
}
