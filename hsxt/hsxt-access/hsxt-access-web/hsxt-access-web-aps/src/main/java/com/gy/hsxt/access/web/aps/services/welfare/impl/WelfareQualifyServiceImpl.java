package com.gy.hsxt.access.web.aps.services.welfare.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.welfare.IWelfareQualifyService;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.api.IWsWelfareService;
import com.gy.hsxt.ws.bean.WelfareQualify;

/**
 * 
 * 积分福利资格查询服务实现类
 * @category      Simple to Introduction
 * @projectName   hsxt-access-web-aps
 * @package       com.gy.hsxt.access.web.aps.services.welfare.impl.WelfareQualifyServiceImpl.java
 * @className     WelfareQualifyServiceImpl
 * @description   积分福利资格查询服务实现类
 * @author        leiyt
 * @createDate    2015-12-31 下午5:02:28  
 * @updateUser    leiyt
 * @updateDate    2015-12-31 下午5:02:28
 * @updateRemark 	积分福利资格查询服务实现类
 * @version       v0.0.1
 */
@Service
public class WelfareQualifyServiceImpl implements IWelfareQualifyService {

	/** 积分福利资格查询 WS dubbo */
	@Autowired
	IWsWelfareService wsWelfareService;

	@Override
	public PageData<WelfareQualify> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
		String hsResNo = (String) filterMap.get("hsResNo");
		Integer welfareType = CommonUtils.toInteger(filterMap.get("welfareType"));
		try
		{
			return wsWelfareService.listWelfareQualify(hsResNo, welfareType, page);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findScrollResult", "积分福利资格查询失败", ex);
			return null;
		}
	}

	@Override
	public List<WelfareQualify> queryHisWelfareQualify(String resNo) throws HsException {
		try
		{
			return wsWelfareService.queryHisWelfareQualify(resNo);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryHisWelfareQualify", "积分福利历史记录查询失败", ex);
			return null;
		}
	}
	@Override
	public PageData<WelfareQualify> queryHisWelfareQualify(Map filterMap, Map sortMap, Page page)  throws HsException{
		String hsResNo = (String) filterMap.get("resNo");
		try
		{
			return wsWelfareService.queryHisWelfareQualify(hsResNo,page);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryHisWelfareQualify", "积分福利历史记录查询失败", ex);
			return null;
		}
	}

	@Override
	public WelfareQualify findWelfareQualify(String custId) throws HsException {
		try
		{
			return wsWelfareService.findWelfareQualify(custId);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findWelfareQualify", "积分福利记录查询失败", ex);
			return null;
		}
	}

	@Override
	public boolean isHaveWelfareQualify(String custId, Integer welfareType) throws HsException {
		try
		{
			return wsWelfareService.isHaveWelfareQualify(custId, welfareType);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "isHaveWelfareQualify", "积分福利记录查询失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}



	@Override
	public Object findById(Object id) throws HsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save(String entityJsonStr) throws HsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkVerifiedCode(String custId, String verifiedCode) throws HsException {
		// TODO Auto-generated method stub
		
	}

}
