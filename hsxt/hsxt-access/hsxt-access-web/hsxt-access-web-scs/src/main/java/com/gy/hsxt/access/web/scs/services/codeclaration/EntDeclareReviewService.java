package com.gy.hsxt.access.web.scs.services.codeclaration;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.bs.api.apply.IBSDeclareService;
import com.gy.hsxt.bs.bean.apply.DeclareAppInfo;
import com.gy.hsxt.bs.bean.apply.DeclareEntBaseInfo;
import com.gy.hsxt.bs.bean.apply.DeclareQueryParam;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.base.OptHisInfo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-scs
 * @package       : com.gy.hsxt.access.web.scs.services.codeclaration
 * @className     : EntDeclareReviewService.java
 * @description   : 企业申报复核接口实现
 * @author        : maocy
 * @createDate    : 2015-10-28
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public class EntDeclareReviewService extends BaseServiceImpl implements IEntDeclareReviewService {
	
    @Autowired
    private IBSDeclareService bsService;//企业申报服务类

    /**
     * 
     * 方法名称：企业申报复核查询
     * 方法描述：依据企业申请编号查询工商登记信息
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页条件
     * @return 
     * @throws HsException
     */
	@Override
	public PageData<DeclareEntBaseInfo> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
		DeclareQueryParam param = new DeclareQueryParam();
		param.setCustType(CommonUtils.toInteger(filterMap.get("entType")));//企业类型
		param.setDeclarerResNo((String) filterMap.get("pointNo"));//申报者互生号
		param.setOperatorCustId((String) filterMap.get("custId"));//操作员客户号
		param.setStartDate((String) filterMap.get("startDate"));//申报开始日期
		param.setEndDate((String) filterMap.get("endDate"));//申报结束日期
		param.setResNo((String) filterMap.get("entResNo"));//拟用互生号
		param.setEntName((String) filterMap.get("entName"));//企业名称
		param.setStatus(CommonUtils.toInteger(filterMap.get("status")));//状态
		return this.bsService.serviceQueryDeclareAppr(param, page);
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
        String applyId = (String) filterMap.get("applyId");
        return this.bsService.serviceQueryDeclareHis(applyId, page);
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
    
    /**
     * 
     * 方法名称：服务公司审批申报
     * 方法描述：服务公司审批申报
     * @param apprParam 审批内容
     * @throws HsException
     */
    @Override
    public void serviceApprDeclare(ApprParam apprParam) throws HsException{
       this.bsService.serviceApprDeclare(apprParam);
    }
	
}
