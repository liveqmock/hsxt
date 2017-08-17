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

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.bp.bean.BusinessAgreeFee;
import com.gy.hsxt.bp.bean.BusinessCustParamItems;
import com.gy.hsxt.bp.bean.BusinessSysParamItems;

/** 
 * 参数获取服务Mapper
 * @Package: com.gy.hsxt.bp.mapper  
 * @ClassName: BusinessParamSearchMapper 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2015-11-25 上午10:39:37 
 * @version V1.0 
 

 */
public interface BusinessParamSearchMapper {

    /**
     * 获取同一组系统参数项接口
     * @param sysGroupCode 系统参数组编号
     * @return List<BusinessSysParamItems> 同一组系统参数项集合
     * @throws SQLException
     */
    public List<BusinessSysParamItems> searchSysParamItemsByGroup(String sysGroupCode) throws SQLException;

    /**
     * 获取单个系统参数项接口
     * @param sysGroupCode 系统参数组编号
     * @param sysItemsKey 系统参数项键名
     * @return BusinessSysParamItems 系统参数项对象
     * @throws SQLException
     */
    public BusinessSysParamItems searchSysParamItemsByCodeKey(@Param("sysGroupCode")String sysGroupCode, @Param("sysItemsKey")String sysItemsKey) throws SQLException;

    /**
     * 获取全部激活状态的系统参数项接口
     * @param isActive 激活状态
     * @return List<BusinessSysParamItems> 系统参数项对象集合
     * @throws SQLException
     */
    public List<BusinessSysParamItems> searchSysParamItemsList(String isActive) throws SQLException;


    /**
     * 获取同一协议的协议费用接口
     * @param agreeCode 协议代码
     * @return List<BusinessAgreeFee> 同一协议的协议费用集合
     * @throws SQLException
     */
    public List<BusinessAgreeFee> searchBusinessAgreeFee(String agreeCode) throws SQLException;


    /**
     * 获取单个协议费用接口
     * @param agreeCode 协议代码
     * @param agreeFeeCode 协议费用编号
     * @return BusinessAgreeFee 协议费用对象
     * @throws SQLException
     */
    public BusinessAgreeFee searchBusinessAgreeFeeByCode(@Param("agreeCode")String agreeCode, @Param("agreeFeeCode")String agreeFeeCode) throws SQLException;

    /**
     * 获取全部激活协议费用接口
     * @param isActive 激活状态
     * @return List<BusinessAgreeFee> 协议费用对象集合
     * @throws SQLException
     */
    public List<BusinessAgreeFee> searchBusinessAgreeFeeList(String isActive) throws SQLException;

    
    /**
     * 获取同一客户业务参数接口
     * @param custId 客户全局编号
     * @return List<BusinessCustParamItems> 同一客户业务参数集合
     * @throws SQLException
     */
    public List<BusinessCustParamItems> searchCustParamItemsByGroup(String custId) throws SQLException;


    /**
     * 获取单个的客户业务参数接口
     * @param custId  客户全局编号  
     * @param sysItemsKey 系统参数项键名
     * @return BusinessCustParamItems 客户业务参数对象
     * @throws SQLException
     */
    public BusinessCustParamItems searchCustParamItemsByIdKey(@Param("custId")String custId,@Param("sysItemsKey")String sysItemsKey) throws SQLException;

    /**
     * 获取全部的激活的客户业务参数接口
     * @param isActive  激活状态
     * @return List<BusinessCustParamItems> 客户业务参数对象集合
     * @throws SQLException
     */
    public List<BusinessCustParamItems> searchCustParamItemsList(String isActive) throws SQLException;


}