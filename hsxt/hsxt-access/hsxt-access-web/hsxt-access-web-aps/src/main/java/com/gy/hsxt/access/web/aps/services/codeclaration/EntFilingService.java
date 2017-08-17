package com.gy.hsxt.access.web.aps.services.codeclaration;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.bs.bean.apply.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.bs.api.apply.IBSFilingService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.codeclaration
 * @className     : EntFilingService.java
 * @description   : 企业报备接口实现
 * @author        : maocy
 * @createDate    : 2015-12-15
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
	 * 方法名称：报备信息查询
	 * 方法描述：报备信息查询
	 * @param filterMap 查询参数
	 * @param sortMap 排序参数
	 * @param page 分页参数
	 * @return
	 * @throws HsException
	 */
    @Override
	public PageData<FilingAppInfo> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
		FilingQueryParam param = new FilingQueryParam();
		param.setProvinceNo((String) filterMap.get("provinceNo"));//所在地区
        param.setStartDate((String) filterMap.get("startDate"));//报备开始日期
        param.setEndDate((String) filterMap.get("endDate"));//报备结束日期
		param.setOptCustId((String) filterMap.get("custId"));//操作员客户号
		param.setStatus(CommonUtils.toInteger(filterMap.get("status")));//审核结果
		param.setEntName((String) filterMap.get("entName"));//企业名称
		param.setLinkman((String) filterMap.get("linkman"));//联系人
		return this.bsService.platQueryFiling(param, page);
	}
    
    /**
     * 
     * 方法名称：异议报备审核查询
     * 方法描述：异议报备审核查询
     * @param filterMap 查询参数
     * @param sortMap 排序参数
     * @param page 分页参数
     * @return
     * @throws HsException
     */
    @Override
    public PageData<FilingAppInfo> platQueryDisagreedFiling(Map filterMap, Map sortMap, Page page) throws HsException {
        FilingQueryParam param = new FilingQueryParam();
        param.setProvinceNo((String) filterMap.get("provinceNo"));//所在地区
        param.setOptCustId((String) filterMap.get("custId"));//操作员客户号
        param.setStartDate((String) filterMap.get("startDate"));//报备开始日期
        param.setEndDate((String) filterMap.get("endDate"));//报备结束日期
        param.setEntName((String) filterMap.get("entName"));//企业名称
        param.setLinkman((String) filterMap.get("linkman"));//联系人
        param.setStatus(CommonUtils.toInteger(filterMap.get("status")));//审核结果
        return this.bsService.platQueryDisagreedFiling(param, page);
    }
    
    /**
     * 
     * 方法名称：企业报备审核查询
     * 方法描述：企业报备审核查询
     * @param filterMap 查询参数
     * @param sortMap 排序参数
     * @param page 分页参数
     * @return
     * @throws HsException
     */
    @Override
    public PageData<FilingAppInfo> platQueryApprFiling(Map filterMap, Map sortMap, Page page) throws HsException {
        FilingQueryParam param = new FilingQueryParam();
        param.setProvinceNo((String) filterMap.get("provinceNo"));//所在地区
        param.setOptCustId((String) filterMap.get("custId"));//操作员客户号
        param.setStartDate((String) filterMap.get("startDate"));//报备开始日期
        param.setEndDate((String) filterMap.get("endDate"));//报备结束日期
        param.setEntName((String) filterMap.get("entName"));//企业名称
        param.setLinkman((String) filterMap.get("linkman"));//联系人
        param.setStatus(CommonUtils.toInteger(filterMap.get("status")));//审核结果
        return this.bsService.platQueryApprFiling(param, page);
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
	 * 方法名称：审批企业报备
	 * 方法描述：地区平台审批企业报备
	 * @param apprParam 审批信息
	 * @throws HsException
	 */
	public void apprCommFiling(FilingApprParam apprParam) throws HsException {
		this.bsService.apprFiling(apprParam);
	}
	
	/**
	 * 
	 * 方法名称：审批企业异议报备
	 * 方法描述：地区平台审批企业异议报备
	 * @param apprParam 审批信息
	 * @throws HsException
	 */
	public void apprDisaFiling(FilingApprParam apprParam) throws HsException {
		this.bsService.apprDisagreeFiling(apprParam);
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
		this.bsService.platModifyFiling(filing);
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
     * 方法名称：查询企业基本信息
     * 方法描述：根据申请编号查询企业基本信息
     * @param applyId 报备企业申请编号
     * @throws HsException
     */
    @Override
    public FilingApp queryFilingBaseInfoById(String applyId) throws HsException {
       return this.bsService.queryFilingBaseInfoById(applyId);
    }


    /**
     * 查询企业相同项
     *
     * @param applyId 报备编号
     * @return 相同项对象
     * @throws HsException
     */
    @Override
    public FilingSameItem findSameItem(String applyId) throws HsException {
        return this.bsService.querySameItem(applyId);
    }
}
