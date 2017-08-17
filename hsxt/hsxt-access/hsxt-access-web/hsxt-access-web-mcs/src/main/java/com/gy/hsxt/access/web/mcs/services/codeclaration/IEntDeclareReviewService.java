package com.gy.hsxt.access.web.mcs.services.codeclaration;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.apply.ResNo;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-mcs
 * @package       : com.gy.hsxt.access.web.mcs.services.codeclaration
 * @className     : IEntDeclareReviewService.java
 * @description   : 复核业务接口
 * @author        : maocy
 * @createDate    : 2015-12-08
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public interface IEntDeclareReviewService extends IBaseService{
    
    /**
     * 
     * 方法名称：管理公司复核
     * 方法描述：管理公司复核
     * @param apprParam 审批内容
     * @throws HsException
     */
    public void managerReviewDeclare(ApprParam apprParam) throws HsException;
    
    /**
     * 
     * 方法名称：查询服务公司可用互生号列表
     * 方法描述：查询服务公司可用互生号列表
     * @param countryNo 所属国家
     * @param provinceNo 所属省份
     * @param cityNo 所属城市
     * @throws HsException
     */
    public List<String> findSerResNoList(String countryNo, String provinceNo, String cityNo);
    
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
    public void managePickResNo(String applyId, String entResNo, String optCustId, Integer toSelectMode) throws HsException;
    
    /**
     * 
     * 方法名称：查询服务公司可用互生号
     * 方法描述：企业申报-查询服务公司可用互生号
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<ResNo> findSerResNos(Map filterMap, Map sortMap, Page page) throws HsException;
    
    /**
     * 
     * 方法名称：查询服务公司可用互生号--顺序选号
     * 方法描述：企业申报-查询服务公司可用互生号
     * @param countryNo 国家代码
     * @param provinceNo 省份代码
     * @param cityNo 城市代码
     * @return
     */
    public PageData<ResNo> findSerResNos(String countryNo, String provinceNo, String cityNo) throws HsException;
    
}