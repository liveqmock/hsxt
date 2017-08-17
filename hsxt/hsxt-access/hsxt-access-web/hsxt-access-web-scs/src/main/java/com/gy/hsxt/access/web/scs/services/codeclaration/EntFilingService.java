package com.gy.hsxt.access.web.scs.services.codeclaration;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.bs.api.apply.IBSFilingService;
import com.gy.hsxt.bs.bean.apply.FilingApp;
import com.gy.hsxt.bs.bean.apply.FilingAppInfo;
import com.gy.hsxt.bs.bean.apply.FilingAptitude;
import com.gy.hsxt.bs.bean.apply.FilingQueryParam;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-scs
 * @package       : com.gy.hsxt.access.web.scs.services.codeclaration
 * @className     : EntFilingService.java
 * @description   : 企业报备接口实现
 * @author        : maocy
 * @createDate    : 2015-10-30
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
@SuppressWarnings("rawtypes")
public class EntFilingService extends BaseServiceImpl<Object> implements IEntFilingService {
	
 	@Autowired
    private IBSFilingService bsService;//企业报备服务类

    /**
	 * 
	 * 方法名称：企业报备查询
	 * 方法描述：企业报备查询
	 * @param filterMap 查询参数
	 * @param sortMap 排序参数
	 * @param page 分页参数
	 * @return
	 * @throws HsException
	 */
    @Override
	public PageData<FilingAppInfo> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
		FilingQueryParam param = new FilingQueryParam();
		param.setSerResNo((String) filterMap.get("pointNo"));//互生卡号
		param.setStartDate((String) filterMap.get("startDate"));//报备开始日期
		param.setEndDate((String) filterMap.get("endDate"));//报备结束日期
		param.setEntName((String) filterMap.get("entName"));//企业名称
		param.setLinkman((String) filterMap.get("linkman"));//联系人
		param.setStatus(CommonUtils.toInteger(filterMap.get("status")));//审核结果
		return this.bsService.serviceQueryFiling(param, page);
	}
	
	/**
	 * 
	 * 方法名称：查询异议报备
	 * 方法描述：查询异议报备
	 * @param filterMap 查询参数
	 * @param sortMap 排序参数
	 * @param page 分页参数
	 * @return
	 * @throws HsException
	 */
	@Override
	public PageData<FilingAppInfo> serviceQueryDisagreedFiling(Map filterMap, Map sortMap, Page page) throws HsException {
		FilingQueryParam param = new FilingQueryParam();
		param.setSerResNo((String) filterMap.get("pointNo"));//互生卡号
		param.setStartDate((String) filterMap.get("startDate"));//报备开始日期
		param.setEndDate((String) filterMap.get("endDate"));//报备结束日期
		param.setEntName((String) filterMap.get("entName"));//企业名称
		param.setLinkman((String) filterMap.get("linkman"));//联系人
		param.setStatus(CommonUtils.toInteger(filterMap.get("status")));//审核结果
		return this.bsService.serviceQueryDisagreedFiling(param, page);
	}
	
	/**
	 * 
	 * 方法名称：查看异议报备详情
	 * 方法描述：依据申请编号查询异议报备详情
	 * @param applyId 申请编号
	 * @return
	 * @throws HsException
	 */
	@Override
	public Map<String, Object> findFilingById(String applyId) throws HsException {
		return this.bsService.queryFilingById(applyId);
	}
	
	/**
	 * 
	 * 方法名称：提异议
	 * 方法描述：提交异议
	 * @param applyId 申请编号
	 * @param operator 操作员客户号
	 * @param remark 异议说明
	 * @throws HsException
	 */
	@Override
	public void raiseDissent(String applyId, String operator, String remark) throws HsException {
		this.bsService.disagreedReject(applyId, operator, remark);
	}

	/**
	 * 
	 * 方法名称：创建报备企业信息
	 * 方法描述：创建报备企业信息
	 * @param filing 报备企业信息
	 * @return 报备申请编号 
	 * @throws HsException
	 */
	@Override
	public String createEntFiling(FilingApp filing) throws HsException {
		return this.bsService.createFiling(filing);
	}
	
	 /**
     * 
     * 方法名称：查询报备进行步骤
     * 方法描述：企业报备-查询报备进行步骤
     * @param applyId 申请编号
     * @throws HsException
     */
    public Integer queryFilingStep(String applyId) throws HsException{
        return this.bsService.queryFilingStep(applyId);
    }
	
	/**
     * 
     * 方法名称：企业报备-删除企业报备
     * 方法描述：企业报备-删除企业报备
     * @param applyId 申请编号
     * @throws HsException
     */
    public void delEntFilingById(String applyId) throws HsException {
        this.bsService.deleteFilingById(applyId);
    }

	/**
	 * 
	 * 方法名称：修改报备企业信息
	 * 方法描述：修改报备企业信息
	 * @param filing 报备企业信息
	 * @return 报备申请编号 
	 * @throws HsException
	 */
	@Override
	public void updateEntFiling(FilingApp filing) throws HsException {
		this.bsService.serviceModifyFiling(filing);
	}

	/**
     * 
     * 方法名称：保存附件信息
     * 方法描述：保存附件信息
     * @param filingAptitudes 附件信息列表
     * @throws HsException
     */
    @Override
    public List<FilingAptitude> saveAptitude(List<FilingAptitude> filingAptitudes) throws HsException {
        return this.bsService.saveAptitude(filingAptitudes);
    }
    
    /**
     * 
     * 方法名称：查询附件信息
     * 方法描述：根据申请编号查询附件信息
     * @param applyId 报备企业申请编号
     * @return filingAptitudes 附件信息列表
     * @throws HsException
     */
    @Override
    public List<FilingAptitude> queryAptByApplyId(String applyId) throws HsException {
        return this.bsService.queryAptByApplyId(applyId);
    }

    /**
     * 
     * 方法名称：提交报备申请
     * 方法描述：提交报备申请
     * @param applyId 报备企业申请编号
     * @param operator 操作员
     * @throws HsException
     */
    @Override
    public void submitFiling(String applyId, String operator) throws HsException {
        this.bsService.submitFiling(applyId, operator);
    }

    /**
     * 
     * 方法名称：查询企业基本信息
     * 方法描述：根据申请编号查询企业基本信息
     * @param applyId 报备企业申请编号
     * @throws HsException
     */
    @Override
    public FilingApp queryFilingBaseInfoById(String applyId) throws HsException {
       return this.bsService.queryFilingBaseInfoById(applyId);
    }

	@Override
	public Integer isExistSameOrSimilar(String applyId) throws HsException {
		// TODO Auto-generated method stub
		return this.bsService.isExistSameOrSimilar(applyId);
	}
    
}
