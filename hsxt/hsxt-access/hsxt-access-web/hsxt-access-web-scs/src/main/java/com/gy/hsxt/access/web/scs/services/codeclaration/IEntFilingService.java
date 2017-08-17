package com.gy.hsxt.access.web.scs.services.codeclaration;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.apply.FilingApp;
import com.gy.hsxt.bs.bean.apply.FilingAppInfo;
import com.gy.hsxt.bs.bean.apply.FilingAptitude;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-scs
 * @package       : com.gy.hsxt.access.web.scs.services.codeclaration
 * @className     : IEntFilingService.java
 * @description   : 企业报备接口
 * @author        : maocy
 * @createDate    : 2015-10-30
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public interface IEntFilingService extends IBaseService{
	
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
	public PageData<FilingAppInfo> serviceQueryDisagreedFiling(Map filterMap, Map sortMap, Page page) throws HsException;

	/**
	 * 
	 * 方法名称：查看异议报备详情
	 * 方法描述：依据申请编号查询异议报备详情
	 * @param applyId 申请编号
	 * @return
	 * @throws HsException
	 */
	public Map<String, Object> findFilingById(String applyId) throws HsException;
	
	/**
	 * 
	 * 方法名称：提异议
	 * 方法描述：提交异议
	 * @param applyId 申请编号
	 * @param operator 操作员客户号
	 * @param remark 异议说明
	 * @throws HsException
	 */
	public void raiseDissent(String applyId, String operator, String remark) throws HsException;
	
	/**
	 * 
	 * 方法名称：创建报备企业信息
	 * 方法描述：创建报备企业信息
	 * @param filing 报备企业信息
	 * @return 报备申请编号
	 * @throws HsException
	 */
	public String createEntFiling(FilingApp filing) throws HsException;
	
	/**
     * 
     * 方法名称：企业报备-删除企业报备
     * 方法描述：企业报备-删除企业报备
     * @param applyId 申请编号
     * @throws HsException
     */
    public void delEntFilingById(String applyId) throws HsException;
    
    /**
     * 
     * 方法名称：查询报备进行步骤
     * 方法描述：企业报备-查询报备进行步骤
     * @param applyId 申请编号
     * @throws HsException
     */
    public Integer queryFilingStep(String applyId) throws HsException;
	
	/**
	 * 
	 * 方法名称：修改报备企业信息
	 * 方法描述：修改报备企业信息
	 * @param filing 报备企业信息
	 * @return 报备申请编号 
	 * @throws HsException
	 */
	public void updateEntFiling(FilingApp filing) throws HsException;
	
	/**
     * 
     * 方法名称：保存附件信息
     * 方法描述：保存附件信息
     * @param filingAptitudes 附件信息列表
     * @throws HsException
     */
    public List<FilingAptitude> saveAptitude(List<FilingAptitude> filingAptitudes) throws HsException;
    
    /**
     * 
     * 方法名称：查询附件信息
     * 方法描述：根据申请编号查询附件信息
     * @param applyId 报备企业申请编号
     * @return filingAptitudes 附件信息列表
     * @throws HsException
     */
    public List<FilingAptitude> queryAptByApplyId(String applyId) throws HsException;
    
    /**
     * 
     * 方法名称：提交报备申请
     * 方法描述：提交报备申请
     * @param applyId 报备企业申请编号
     * @param operator 操作员
     * @throws HsException
     */
    public void submitFiling(String applyId, String operator) throws HsException;
    
    /**
     * 
     * 方法名称：查询企业基本信息
     * 方法描述：根据申请编号查询企业基本信息
     * @param applyId 报备企业申请编号
     * @throws HsException
     */
    public FilingApp queryFilingBaseInfoById(String applyId) throws HsException;
    
    public Integer isExistSameOrSimilar(String applyId) throws HsException;
    
}
