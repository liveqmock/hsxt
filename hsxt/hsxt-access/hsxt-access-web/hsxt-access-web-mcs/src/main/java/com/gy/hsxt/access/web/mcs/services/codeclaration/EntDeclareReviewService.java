package com.gy.hsxt.access.web.mcs.services.codeclaration;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.bs.api.apply.IBSDeclareService;
import com.gy.hsxt.bs.bean.apply.DeclareEntBaseInfo;
import com.gy.hsxt.bs.bean.apply.DeclareQueryParam;
import com.gy.hsxt.bs.bean.apply.DeclareRegInfo;
import com.gy.hsxt.bs.bean.apply.ResNo;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-mcs
 * @package       : com.gy.hsxt.access.web.mcs.services.codeclaration
 * @className     : EntDeclareReviewService.java
 * @description   : 复核业务接口实现
 * @author        : maocy
 * @createDate    : 2015-12-08
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
		return this.bsService.manageQueryDeclareReview(param, page);
	}
	
	/**
     * 
     * 方法名称：管理公司复核
     * 方法描述：管理公司复核
     * @param apprParam 审批内容
     * @throws HsException
     */
    @Override
    public void managerReviewDeclare (ApprParam apprParam) throws HsException{
       this.bsService.manageReviewDeclare(apprParam);
    }

    /**
     * 
     * 方法名称：查询服务公司可用互生号列表
     * 方法描述：查询服务公司可用互生号列表
     * @param countryNo 所属国家
     * @param provinceNo 所属省份
     * @param cityNo 所属城市
     * @throws HsException
     */
    @Override
    public List<String> findSerResNoList(String countryNo, String provinceNo, String cityNo) {
        return this.bsService.getSerResNoList(countryNo, provinceNo, cityNo);
    }
    
    /**
     * 
     * 方法名称：管理公司选号
     * 方法描述：管理公司选号
     * @param applyId 申请编号
     * @param entResNo 服务公司互生号
     * @param optCustId 操作员客户号
     * @param toSelectMode 资源号选择方式 0-顺序选择 1-人工选择
     * @throws HsException
     */
    public void managePickResNo(String applyId, String entResNo, String optCustId, Integer toSelectMode) throws HsException {
    	DeclareRegInfo info = new DeclareRegInfo();
    	info.setApplyId(applyId);
    	info.setToEntResNo(entResNo);
    	info.setOptCustId(optCustId);
    	info.setToSelectMode(toSelectMode);
    	this.bsService.managePickResNo(info);
    }
    
    /**
     * 
     * 方法名称：查询服务公司可用互生号
     * 方法描述：企业申报-查询服务公司可用互生号
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<ResNo> findSerResNos(Map filterMap, Map sortMap, Page page) throws HsException {
    	String countryNo = (String) filterMap.get("countryNo");//国家代码
    	String provinceNo = (String) filterMap.get("provinceNo");//省代码
    	String cityNo = (String) filterMap.get("cityNo");//城市代码
    	String resNo = (String) filterMap.get("resNo");//拟用互生号
    	return this.bsService.getSerResNos(countryNo, provinceNo, cityNo, resNo, page);
    }
    
    /**
     * 
     * 方法名称：查询服务公司可用互生号--顺序选号
     * 方法描述：企业申报-查询服务公司可用互生号
     * @param countryNo 国家代码
     * @param provinceNo 省份代码
     * @param cityNo 城市代码
     * @return
     */
    public PageData<ResNo> findSerResNos(String countryNo, String provinceNo, String cityNo) throws HsException {
    	return this.bsService.getSerResNos(countryNo, provinceNo, cityNo, null, new Page(1, 1));
    }
	
}