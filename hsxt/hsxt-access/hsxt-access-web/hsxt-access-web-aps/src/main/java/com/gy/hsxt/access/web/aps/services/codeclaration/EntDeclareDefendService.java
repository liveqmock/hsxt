package com.gy.hsxt.access.web.aps.services.codeclaration;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.bs.api.apply.IBSDeclareService;
import com.gy.hsxt.bs.bean.apply.DeclareEntBaseInfo;
import com.gy.hsxt.bs.bean.apply.DeclareIncrement;
import com.gy.hsxt.bs.bean.apply.DeclareQueryParam;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.codeclaration
 * @className     : EntDeclareDefendService.java
 * @description   : 申报信息维护接口
 * @author        : maocy
 * @createDate    : 2015-12-25
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public class EntDeclareDefendService extends BaseServiceImpl implements IEntDeclareDefendService {
	
    @Autowired
    private IBSDeclareService bsService;//企业申报服务类

	@Override
	public PageData<DeclareEntBaseInfo> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
		DeclareQueryParam param = new DeclareQueryParam();
	    param.setResNo((String) filterMap.get("resNo"));
	    param.setEntName((String) filterMap.get("entName"));
	    param.setStartDate((String) filterMap.get("startDate"));
	    param.setEndDate((String) filterMap.get("endDate"));
	    param.setStatus(CommonUtils.toInteger(filterMap.get("status")));
		return this.bsService.queryIncrementList(param, page);
	}
	
	/**
     * 
     * 方法名称：保存申报增值点信息
     * 方法描述：保存申报增值点信息
     * @param declareIncrement 积分增值信息
     * @return
     * @throws HsException
     */
	public void saveIncrement(DeclareIncrement declareIncrement) throws HsException {
		this.bsService.saveIncrement(declareIncrement);
	}
	
}