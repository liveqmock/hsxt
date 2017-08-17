/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.systemmanage;

import java.util.List;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.ent.AsEntGroup;

/**
 * 用户组管理服务
 * 
 * @Package: com.gy.hsxt.access.web.mcs.services.systemmanage  
 * @ClassName: EntGroupService 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2016-1-9 下午5:36:13 
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
public interface EntGroupService extends IBaseService {
    
    /**
     * 添加用户组
     * @param group
     * @param operator
     * @throws HsException
     */
    public  Long  addGroup(AsEntGroup  group,  String  operator) throws HsException;
    
    /**
     * 修改用户组
     * @param group
     * @param operator
     * @throws HsException
     */
    public  void  updateGroup(AsEntGroup  group,  String  operator)throws HsException;
    
    /**
     * 删除用户组
     * @param groupId
     * @param operator
     * @throws HsException
     */
    public  void  deleteGroup(Long groupId,  String operator) throws HsException;
    
    /**
     * 添加组员
     * @param operCustIds
     * @param groupId
     * @param operator
     * @throws HsException
     */
    public  void  addGroupUser(List<String> operCustIds,  Long groupId,  String operator) throws HsException;
    
    /**
     * 删除组员
     * @param operCustId
     * @param groupId
     * @param operator
     * @throws HsException
     */
    public  void  deleteGroupUser(String operCustId, Long groupId, String operator)
            throws HsException;
    
    /**
     * 查询用户组
     * @param entCustId
     * @param groupName
     * @return
     * @throws HsException
     */
    public AsEntGroup findGroup(String entCustId,String groupName) throws HsException;
    
    /**
     * 重置用户组组员
     * @param operCustIds  新组员id
     * @param groupId  用户组id
     * @param operator 操作人id
     * @throws HsException
     */
    public void resetGroupOperator(List<String> operCustIds,  Long groupId,  String operator) throws HsException;
    
    /**
     * 清空用户组组员
     * @param groupId  用户组id
     * @param operator 操作人
     * @throws HsException
     */
    public void cleanGroupOperator(Long groupId,String operator) throws HsException;
    
}
