/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.mcs.services.systemmanage;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.access.web.bean.MCSBase;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tm.bean.Group;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;

/**
 * 
 * 操作员管理服务
 * @Package: com.gy.hsxt.access.web.mcs.services.systemmanage  
 * @ClassName: OperatorService 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2016-1-9 下午12:12:30 
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
public interface OperatorService extends IBaseService{

    /**
     * 添加操作员
     * @param oper
     * @param adminCustId
     * @throws HsException
     */
    public String  addOper(AsOperator  oper, String adminCustId ,List<Long> groupIds) throws  HsException;
    
    /**
     * 查询操作员
     * @param operCustId
     * @return
     * @throws HsException
     */
    public  AsOperator  searchOperByCustId (String  operCustId) throws  HsException;
    
    /**
     * 查询企业操作员列表
     * @param entCustId
     * @return
     * @throws HsException
     */
    public List<AsOperator> listOperByEntCustId(String  entCustId) throws  HsException;
    
    /**
     * 修改企业操作员信息
     * @param oper
     * @param adminCustId
     * @throws HsException
     */
    public void updateOper (AsOperator  oper,  String  adminCustId,List<Long> groupIds) throws  HsException;

    /**
     * 删除操作员
     * @param operCustId
     * @param adminCustId
     * @throws HsException
     */
    public  void  deleteOper (String  operCustId, String  adminCustId) throws  HsException;
    
    /**
     * 获取操作员详情
     * @param mcsBase
     * @return
     */
    public Map<String, Object> getOperatorDetail(MCSBase mcsBase);
    
    /**
     * 查询所有工单分组及分组下的所有操作员
     * @param entCustId
     * @return
     */
    public List<Group> findTaskGroupInfo(String entCustId);
    
}
