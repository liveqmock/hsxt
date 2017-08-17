/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

package com.gy.hsxt.bp.mapper;

import java.sql.SQLException;
import java.util.List;

import com.gy.hsxt.bp.bean.BusinessAgree;
import com.gy.hsxt.bp.bean.BusinessAgreeFee;
import com.gy.hsxt.common.exception.HsException;

/** 
 * 协议参数管理Mapper
 * @Package: com.gy.hsxt.bp.mapper  
 * @ClassName: AgreeParamManagerMapper 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2015-11-24 下午3:30:27 
 * @version V1.0 
 

 */
public interface BusinessAgreeMapper {

    /**
     * 新增协议参数管理
     * @param BusinessAgree 协议参数管理对象
     * @throws SQLException
     */
    public void addAgree(BusinessAgree agreeParamManager) throws SQLException;
    
    /**
     * 更新协议参数管理
     * @param BusinessAgree 协议参数管理对象
     * @throws SQLException
     */
    public void updateAgree(BusinessAgree agreeParamManager) throws SQLException;
    
    /**
     * 删除协议参数管理
     * @param agreeId 协议ID
     * @throws SQLException
     */
    public void deleteAgree(String agreeId) throws SQLException;
    
    /**
     * 查询单个协议参数管理
     * @param agreeId 协议ID
     * @throws SQLException
     */
    public BusinessAgree getAgreeById(String agreeId) throws SQLException;
    
    /**
     * 查询单个协议参数管理
     * @param agreeCode 协议编码
     * @throws SQLException
     */
    public BusinessAgree getAgreeByCode(String agreeCode) throws SQLException;
    
    
    /**
     * 查询多个协议参数管理
     * @param BusinessAgree 协议参数管理对象
     * @return List<AgreeParamManager> 封裝协议参数管理对象集合
     * @throws SQLException
     */
    public List<BusinessAgree> searchAgreeListPage(BusinessAgree agreeParamManager) throws SQLException;
    
    /**
     * 查询所有协议参数管理
     * @return List<AgreeParamManager> 协议参数管理对象集合
     * @throws HsException
     */
    public List<BusinessAgree> searchAgreeList() throws SQLException;
    
    /**
     * 新增协议费用参数管理
     * @param BusinessAgreeFee 协议费用参数管理对象
     * @throws SQLException
     */
    public void addAgreeFee(BusinessAgreeFee businessAgreeFee) throws SQLException;
    
    /**
     * 更新协议费用参数管理
     * @param BusinessAgreeFee 协议费用参数管理对象
     * @throws SQLException
     */
    public void updateAgreeFee(BusinessAgreeFee businessAgreeFee) throws SQLException;
    
    /**
     * 删除协议费用参数管理
     * @param agreeFeeId 协议费用ID
     * @throws SQLException
     */
    public void deleteAgreeFee(String agreeFeeId) throws SQLException;
    
    /**
     * 查询单个协议费用参数管理
     * @param agreeFeeId 协议费用ID
     * @throws SQLException
     */
    public BusinessAgreeFee getAgreeFeeById(String agreeFeeId) throws SQLException;
    
    /**
     * 查询单个协议费用参数管理
     * @param agreeFeeCode 协议费用编码
     * @throws SQLException
     */
    public BusinessAgreeFee getAgreeFeeByCode(String agreeFeeCode) throws SQLException;
    
    
    /**
     * 查询多个协议费用参数管理
     * @param BusinessAgreeFee 协议费用参数管理对象
     * @return List<BusinessAgreeFee> 封裝协议费用参数管理对象集合
     * @throws SQLException
     */
    public List<BusinessAgreeFee> searchAgreeFeeListPage(BusinessAgreeFee businessAgreeFee) throws SQLException;
    
    /**
     * 通过协议编码查询协议费用参数管理
     * @param agreeCode 协议编码
     * @return List<BusinessAgreeFee> 协议费用参数管理对象集合
     * @throws HsException
     */
    public List<BusinessAgreeFee> searchAgreeFeeList(String agreeCode) throws SQLException;
}
