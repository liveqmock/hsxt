/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.toolorder.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.toolorder.EntInfoService;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;

@SuppressWarnings("rawtypes")
@Service
public class EntInfoServiceImpl extends BaseServiceImpl implements EntInfoService {

    @Autowired
    private IUCAsBankAcctInfoService iuCAsBankAcctInfoService;
    
    @Autowired
    private IUCAsEntService iuCAsEntService;
    
    @Override
    public AsEntMainInfo searchEntMainInfo(String entCustId) throws HsException {
    	try
		{
			return iuCAsEntService.searchEntMainInfo(entCustId);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "searchEntMainInfo", "根据客户号查询企业信息失败", ex);
			return null;
		}
    }

    @Override
    public AsEntMainInfo searchEntMainInfoByResNo(String resNo) throws HsException {
    	try
		{
			return iuCAsEntService.getMainInfoByResNo(resNo);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "searchEntMainInfoByResNo", "根据互生号查询企业信息失败", ex);
			return null;
		}
    }

    @Override
    public AsEntAllInfo searchEntAllInfo(String entCustId) throws HsException {
    	try
		{
			return iuCAsEntService.searchEntAllInfo(entCustId);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "searchEntAllInfo", "根据客户号查询企业信息失败", ex);
			return null;
		}
    }

    @Override
    public List<AsBankAcctInfo> findBanksByCustId(String custId, String userType) throws HsException {
        try
		{
        	List<AsBankAcctInfo> result = iuCAsBankAcctInfoService.listBanksByCustId(custId, userType);
        	return result;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findBanksByCustId", "查询企业信息失败", ex);
			return null;
		}
    }

    @Override
    public AsEntBaseInfo searchEntBaseInfo(String entCustId) throws HsException {
    	  try
  		{
          	return iuCAsEntService.searchEntBaseInfo(entCustId);
  		} catch (Exception ex)
  		{
  			SystemLog.error(this.getClass().getName(), "searchEntBaseInfo", "查询企业信息失败", ex);
  			return null;
  		}
    }

    @Override
    public AsEntAllInfo searchEntAllInfoByResNo(String entResNo) throws HsException {
        try
  		{
        	String entCustId = iuCAsEntService.findEntCustIdByEntResNo(entResNo);
        	return searchEntAllInfo(entCustId);
        } catch (Exception ex)
  		{
  			SystemLog.error(this.getClass().getName(), "searchEntAllInfoByResNo", "查询企业信息失败", ex);
  			return null;
  		}
    }

    /**  
     * @param entBaseInfo
     * @param operator
     * @throws HsException 
     * @see com.gy.hsxt.access.web.aps.services.toolorder.EntInfoService#updateEntBaseInfo(com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo, java.lang.String) 
     */
    @Override
    public void updateEntBaseInfo(AsEntBaseInfo entBaseInfo, String operator) throws HsException {
    	try
		{
    		iuCAsEntService.updateEntBaseInfo(entBaseInfo,operator);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "updateEntBaseInfo", "修改企业信息失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
    }

}
