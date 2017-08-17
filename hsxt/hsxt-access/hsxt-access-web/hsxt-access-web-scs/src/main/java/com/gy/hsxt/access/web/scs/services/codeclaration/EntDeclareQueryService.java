package com.gy.hsxt.access.web.scs.services.codeclaration;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.bs.api.apply.IBSDeclareService;
import com.gy.hsxt.bs.bean.apply.DeclareEntBaseInfo;
import com.gy.hsxt.bs.bean.apply.DeclareQueryParam;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-scs
 * @package       : com.gy.hsxt.access.web.scs.services.codeclaration
 * @className     : EnterReportService.java
 * @description   : 企业申报查询接口实现
 * @author        : maocy
 * @createDate    : 2015-10-28
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public class EntDeclareQueryService extends BaseServiceImpl implements IEntDeclareQueryService {
	
	@Autowired
    private IBSDeclareService bsService;//企业申报服务类

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
		return this.bsService.serviceQueryDeclare(param, page);
	}
	
    /**
     * 
     * 方法名称：查询申报进行步骤
     * 方法描述：依据企业申请编号查询申报进行步骤
     * @param applyId 申请编号
     * @return 返回 0:未找到该申报  1.企业系统注册信息填写完成,2.企业工商登记信息填写完成,3.企业联系信息填写完成,4.企业银行账户信息填写完成,5.企业上传资料填写完成
     * @throws HsException
     */
	@Override
    public Integer findDeclareStep(String applyId) throws HsException{
        return this.bsService.queryDeclareStep(applyId);
    };
    
    /**
     * 
     * 方法名称：删除申报信息
     * 方法描述：依据企业申请编号删除待提交状态的申报信息
     * @param applyId 申请编号
     * @throws HsException
     */
    @Override
    public void delUnSubmitDeclare(String applyId) throws HsException{
        this.bsService.delUnSubmitDeclare(applyId);
    };
	
}