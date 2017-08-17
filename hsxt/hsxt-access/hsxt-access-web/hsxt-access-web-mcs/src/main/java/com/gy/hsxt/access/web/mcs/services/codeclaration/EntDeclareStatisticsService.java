package com.gy.hsxt.access.web.mcs.services.codeclaration;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.bs.api.apply.IBSDeclareService;
import com.gy.hsxt.bs.bean.apply.DeclareAppInfo;
import com.gy.hsxt.bs.bean.apply.DeclareEntBaseInfo;
import com.gy.hsxt.bs.bean.apply.DeclareQueryParam;
import com.gy.hsxt.bs.bean.base.OptHisInfo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-mcs
 * @package       : com.gy.hsxt.access.web.mcs.services.codeclaration
 * @className     : EntDeclareStatisticsService.java
 * @description   : 审批统计查询接口实现
 * @author        : maocy
 * @createDate    : 2015-12-08
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public class EntDeclareStatisticsService extends BaseServiceImpl implements IEntDeclareStatisticsService {
	
    @Autowired
    private IBSDeclareService bsService;//企业申报服务类

    /**
     * 
     * 方法名称：查询申报信息
     * 方法描述：查询申报信息
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页条件
     * @return PageData 分页对象
     * @throws HsException
     */
	@Override
	public PageData<DeclareEntBaseInfo> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
	    DeclareQueryParam param = new DeclareQueryParam();
	    param.setCustType(CommonUtils.toInteger(filterMap.get("custType")));
	    param.setManageResNo((String) filterMap.get("pointNo"));
	    param.setOperatorCustId((String) filterMap.get("custId"));
	    param.setResNo((String) filterMap.get("resNo"));
	    param.setEntName((String) filterMap.get("entName"));
	    param.setStartDate((String) filterMap.get("startDate"));
	    param.setEndDate((String) filterMap.get("endDate"));
	    param.setStatus(CommonUtils.toInteger(filterMap.get("status")));
		return this.bsService.manageQueryDeclareList(param, page);
	}
	
	/**
     * 
     * 方法名称：查询办理状态信息
     * 方法描述：依据企业申请编号查询办理状态信息
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页条件
     * @return
     * @throws HsException
     */
    @Override
    public PageData<OptHisInfo> findOperationHisList(Map filterMap, Map sortMap, Page page) throws HsException {
        return this.bsService.queryDeclareHis((String) filterMap.get("applyId"), page);
    }
    
    /**
     * 
     * 方法名称：查询申报信息
     * 方法描述：依据企业申请编号查询申报信息
     * @param applyId 申请编号
     * @return DeclareAppInfo 申报信息
     * @throws HsException
     */
    @Override
    public DeclareAppInfo findDeclareAppInfoByApplyId(String applyId) throws HsException{
       return this.bsService.queryDeclareAppInfoByApplyId(applyId);
    }
	
}
