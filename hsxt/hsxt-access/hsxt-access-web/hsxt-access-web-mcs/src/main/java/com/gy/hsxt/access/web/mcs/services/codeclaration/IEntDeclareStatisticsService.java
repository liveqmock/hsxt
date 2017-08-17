package com.gy.hsxt.access.web.mcs.services.codeclaration;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.apply.DeclareAppInfo;
import com.gy.hsxt.bs.bean.base.OptHisInfo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-mcs
 * @package       : com.gy.hsxt.access.web.mcs.services.codeclaration
 * @className     : IEntDeclareStatisticsService.java
 * @description   : 审批统计查询接口
 * @author        : maocy
 * @createDate    : 2015-12-08
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public interface IEntDeclareStatisticsService extends IBaseService{
    
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
    public PageData<OptHisInfo> findOperationHisList(Map filterMap, Map sortMap, Page page) throws HsException;
    
    /**
     * 
     * 方法名称：查询申报信息
     * 方法描述：依据企业申请编号查询申报信息
     * @param applyId 申请编号
     * @return DeclareAppInfo 申报信息
     * @throws HsException
     */
    public DeclareAppInfo findDeclareAppInfoByApplyId(String applyId) throws HsException;
    
}
