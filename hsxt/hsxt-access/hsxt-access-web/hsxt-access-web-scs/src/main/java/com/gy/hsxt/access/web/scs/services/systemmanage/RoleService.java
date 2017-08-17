/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.scs.services.systemmanage;

import java.util.List;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.auth.AsRole;

/**
 * 角色管理服务
 * 
 * @Package: com.gy.hsxt.access.web.mcs.services.systemmanage  
 * @ClassName: RoleService 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2016-1-11 下午2:32:56 
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
public interface RoleService extends IBaseService {

    /**
     * 添加角色
     * @param role  角色实体
     * @param oprator  操作者
     * @throws HsException
     */
    public String addRole(AsRole role,String oprator) throws HsException;
    
    /**
     * 删除角色
     * @param roleId  角色id
     * @param oprator  操作者
     * @throws HsException
     */
    public void deleteRole(String roleId ,String oprator) throws HsException;
    
    /**
     * 修改角色
     * @param role 角色实体
     * @param oprator  操作者
     * @throws HsException
     */
    public void updateRole(AsRole role,String oprator) throws HsException;
    
    /**
     * 批量用户角色授权，跳过已有角色，插入未有角色。
     * 
     * @param custId
     * @param list
     *            需授权角色id列表
     * @param operator
     * @throws HsException
     */
    public void grantRole(String custId, List<String> list, String operator) throws HsException;

    /**
     * 批量回收用户角色,删除用户授权记录
     * 
     * @param custId
     * @param list
     *            需删除角色id列表,null则删除全部
     * @param operator
     * @throws HsException
     */
    public void revokeRole(String custId, List<String> list, String operator) throws HsException;

    /**
     * 批量授权用户角色,删除已有记录，插入新记录
     * 
     * @param custId
     * @param list
     *            需授权角色id列表
     * @param operator
     * @throws HsException
     */
    public void resetRole(String custId, List<String> list, String operator) throws HsException;
    
    /**
     * 查询角色列表，可附加过滤条件：平台，子系统,角色类型，企业客户id ,企业类型
     * 
     * @param platformCode 平台
     *            null则忽略该条件
     * @param subSystemCode 子系统
     *            null则忽略该条件
     * @param roleType 角色类型
     *            null则忽略该条件
     * @param entCustId  企业客户id
     *            null则忽略该条件
     * @param custType 企业类型
     *            null则忽略该条件
     * @return
     * @throws HsException
     */
    public List<AsRole> listRole(String platformCode, String subSystemCode, Short roleType,
            String entCustId, String custType) throws HsException;
}
