package com.gy.hsxt.access.web.scs.services.codeclaration;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.base.OptInfo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-scs
 * @package       : com.gy.hsxt.access.web.scs.services.codeclaration
 * @className     : IEntDeclareService.java
 * @description   : 企业申报接口
 * @author        : maocy
 * @createDate    : 2015-10-30
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public interface IEntDeclareService extends IBaseService{
    
    /**
     * 
     * 方法名称：提交申报
     * 方法描述：企业申报-提交申报
     * @param applyId 申请编号
     * @param operator 操作员信息
     * @throws HsException
     */
    public void submitDeclare(String applyId, OptInfo operator) throws HsException;
    
    /**
     * 
     * 方法名称：查询服务公司可用互生号
     * 方法描述：企业申报-查询服务公司可用互生号
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<?> findSerResNos(Map filterMap, Map sortMap, Page page) throws HsException;
    
    /**
     * 
     * 方法名称：查询成员企业、托管企业可用互生号
     * 方法描述：企业申报-查询成员企业、托管企业可用互生号
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<?> findEntResNos(Map filterMap, Map sortMap, Page page) throws HsException;


    /**
     * 校验互生号是否可用
     * @param entResNo 企业资源号
     * @return
     * @throws HsException
     */
    Boolean checkValidEntResNo( String entResNo) throws HsException;
}
