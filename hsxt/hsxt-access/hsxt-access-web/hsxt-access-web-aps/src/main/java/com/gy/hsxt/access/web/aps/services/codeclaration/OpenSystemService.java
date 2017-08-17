package com.gy.hsxt.access.web.aps.services.codeclaration;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.bs.api.apply.IBSDeclareService;
import com.gy.hsxt.bs.bean.apply.DebtOpenSys;
import com.gy.hsxt.bs.bean.apply.DeclareEntBaseInfo;
import com.gy.hsxt.bs.bean.apply.DeclareQueryParam;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.codeclaration
 * @className     : OpenSystemService.java
 * @description   : 开系统欠费审核
 * @author        : maocy
 * @createDate    : 2015-12-30
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public class OpenSystemService extends BaseServiceImpl implements IOpenSystemService {
	
    @Autowired
    private IBSDeclareService bsService;//企业申报服务类

    /**
     * 
     * 方法名称：查询开系统欠费审核信息
     * 方法描述：查询开系统欠费审核信息
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页条件
     * @return PageData 分页对象
     * @throws HsException
     */
	@Override
	public PageData<DeclareEntBaseInfo> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
		DeclareQueryParam param = new DeclareQueryParam();
		param.setCustType(CommonUtils.toInteger(filterMap.get("custType")));//企业类型
	    param.setResNo((String) filterMap.get("resNo"));//企业互生号
	    param.setOperatorCustId((String) filterMap.get("custId"));//客户ID
	    param.setEntName((String) filterMap.get("entName"));//企业名称
	    param.setStartDate((String) filterMap.get("startDate"));//申报开始日期
	    param.setEndDate((String) filterMap.get("endDate"));//申报结束日期
		return this.bsService.platQueryOpenSysAppr(param, page);
	}
	
	/**
     * 
     * 方法名称：开系统欠费审核
     * 方法描述：开系统欠费审核
     * @param debtOpenSys 审核信息
     * @throws HsException
     */
	public void apprDebtOpenSys(DebtOpenSys debtOpenSys) throws HsException {
		this.bsService.apprDebtOpenSys(debtOpenSys);
	}
	
}