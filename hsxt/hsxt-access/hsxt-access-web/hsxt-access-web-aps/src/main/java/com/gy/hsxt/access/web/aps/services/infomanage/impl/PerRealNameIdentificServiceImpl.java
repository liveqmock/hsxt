/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.infomanage.impl;

import static com.gy.hsxt.common.utils.StringUtils.isNotBlank;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsxt.access.web.aps.services.infomanage.PerRealNameIdentificService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.entstatus.IBSRealNameAuthService;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.base.OptHisInfo;
import com.gy.hsxt.bs.bean.entstatus.CloseOpenEnt;
import com.gy.hsxt.bs.bean.entstatus.CloseOpenEntQueryParam;
import com.gy.hsxt.bs.bean.entstatus.EntRealnameBaseInfo;
import com.gy.hsxt.bs.bean.entstatus.PerRealnameAuth;
import com.gy.hsxt.bs.bean.entstatus.PerRealnameBaseInfo;
import com.gy.hsxt.bs.bean.entstatus.RealNameQueryParam;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;


@SuppressWarnings("rawtypes")
@Service
public class PerRealNameIdentificServiceImpl extends BaseServiceImpl implements PerRealNameIdentificService {

    @Autowired
    private IBSRealNameAuthService  ibSRealNameAuthService;
    @Override
    public PageData findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
        
        RealNameQueryParam  param = new RealNameQueryParam();
        //结束时间
        param.setEndDate(filterMap.get("endDate").toString());
        //消费者名称
        param.setName(filterMap.get("entName").toString());
        //消费者互生号
        param.setResNo(filterMap.get("entResNo").toString());
        //开始时间
        param.setStartDate(filterMap.get("startDate").toString());
        //状态
        Integer status = filterMap.get("status") == null || "".equals(filterMap.get("status").toString())?null:Integer.parseInt(filterMap.get("status").toString());
        param.setStatus(status);
        
        return ibSRealNameAuthService.queryPerRealnameAuth(param, page);
    }
    @Override
    public void apprPerRealnameAuth(ApprParam apprParam) throws HsException {
        
        ibSRealNameAuthService.apprPerRealnameAuth(apprParam);
    }
    @Override
    public void reviewApprPerRealnameAuth(ApprParam apprParam) throws HsException {
        
        ibSRealNameAuthService.reviewApprPerRealnameAuth(apprParam);
    }
    @Override
    public PerRealnameAuth queryPerRealnameAuthById(String applyId) throws HsException {
        
        return ibSRealNameAuthService.queryPerRealnameAuthById(applyId);
    }

    @Override
    public void modifyPerRealnameAuth(PerRealnameAuth perRealnameAuth) throws HsException {
        ibSRealNameAuthService.modifyPerRealnameAuth(perRealnameAuth);
    }
    @Override
    public PageData<OptHisInfo> queryPerRealnameAuthHis(String applyId, Page page) throws HsException {
        
        return ibSRealNameAuthService.queryPerRealnameAuthHis(applyId, page);
    }
    @Override
    public PageData queryApprResult(Map filterMap, Map sortMap, Page page) throws HsException {
        
        RealNameQueryParam  param = new RealNameQueryParam();
        //结束时间
        param.setEndDate(filterMap.get("endDate").toString());
        //消费者名称
        param.setName(filterMap.get("entName").toString());
        //消费者互生号
        param.setResNo(filterMap.get("entResNo").toString());
        //开始时间
        param.setStartDate(filterMap.get("startDate").toString());
        //状态
        Integer status = filterMap.get("status") == null || "".equals(filterMap.get("status").toString())?null:Integer.parseInt(filterMap.get("status").toString());
        param.setStatus(status);
        //操作员客户号
        param.setOptCustId(filterMap.get("custId").toString());
        
        return ibSRealNameAuthService.queryPerRealnameAuth4Appr(param, page);
    }
    
    

	/**
	 * 查询消费者实名认证审批记录
	 * 
	 * @param closeOpenEntParam
	 *            查询条件
	 * @param page
	 *            分页信息
	 * @return 关闭、开启系统申请列表
	 * @throws HsException
	 */
	@Override
	public PageData<PerRealnameBaseInfo> pageRealnameAuthRecord(Map filterMap, Map sortMap, Page page) throws HsException{
	    //查询条件类
		RealNameQueryParam  paramRealNameQueryParam = this.createCOEP(filterMap);
		//返回分页结果
		return ibSRealNameAuthService.queryPerRealnameAuth(paramRealNameQueryParam, page);
	}
	
	/**
	 * 创建查询实体类
	 * @param filterMap
	 * @return
	 */
	private RealNameQueryParam  createCOEP(Map filterMap){
		RealNameQueryParam  param = new RealNameQueryParam();
		 //结束时间
		if(isNotBlank(filterMap.get("endDate"))){
	        param.setEndDate(filterMap.get("endDate").toString());
		}
		//企业名称
		if(isNotBlank(filterMap.get("entName"))){
			param.setName(filterMap.get("entName").toString());
		}
		//企业互生号
		if(isNotBlank(filterMap.get("entResNo"))){
			param.setResNo(filterMap.get("entResNo").toString());
		}
	    //消费者名称
		if(isNotBlank(filterMap.get("pName"))){
			param.setName(filterMap.get("pName").toString());
		}
		//消费者互生号
		if(isNotBlank(filterMap.get("pResNo"))){
			param.setResNo(filterMap.get("pResNo").toString());
		}
		 //开始时间
		if(isNotBlank(filterMap.get("startDate"))){
			 param.setStartDate(filterMap.get("startDate").toString());
		}
		//设置企业类型
		if(isNotBlank(filterMap.get("entType"))){
			param.setEntType(Integer.parseInt(filterMap.get("entType").toString()));
		}
        //状态
        Integer status = filterMap.get("status") == null || "".equals(filterMap.get("status").toString())?null:Integer.parseInt(filterMap.get("status").toString());
        param.setStatus(status);
        return param;
	}
	
	/**
	 * 分页查询企业实名认证审批记录
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException 
	 * @see com.gy.hsxt.access.web.aps.services.infomanage.PerRealNameIdentificService#pageEntRealnameAuthRecord(java.util.Map, java.util.Map, com.gy.hsxt.common.bean.Page)
	 */
	public PageData<EntRealnameBaseInfo> pageEntRealnameAuthRecord(Map filterMap, Map sortMap, Page page) throws HsException{
		  //查询条件类
		  RealNameQueryParam  paramRealNameQueryParam = this.createCOEP(filterMap);
		  //返回分页结果
		  return ibSRealNameAuthService.queryEntRealnameAuth(paramRealNameQueryParam, page);
	}
    
    
}
