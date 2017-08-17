/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

package com.gy.hsxt.bp.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.bp.bean.BusinessPosUpgrade;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * 
 * @Package: com.gy.hsxt.bp.mapper  
 * @ClassName: BusinessPosUpgradeMapper 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2016-4-9 上午11:29:30 
 * @version V1.0
 */
public interface BusinessPosUpgradeMapper {

    /**
     * 查询POS机升级信息
     * @param posDeviceType POS机型号
     * @return BusinessPosUpgrade POS机升级信息实体
     * @throws SQLException
     */
    public BusinessPosUpgrade searchPosUpgradeVerInfo(String posDeviceType) throws SQLException;
   
    /**
     * 查询POS机升级信息
     * @param posDeviceType POS机型号
     * @return BusinessPosUpgrade POS机升级信息实体
     * @throws SQLException
     */
    public List<BusinessPosUpgrade> searchPosUpgradeVerInfoList(String posDeviceType) throws SQLException;
   
    /**
     * 分页查询POS机升级信息
     * @param posDeviceType POS机型号
     * @return BusinessPosUpgrade POS机升级信息实体
     * @throws SQLException
     */
    public List<BusinessPosUpgrade> searchPosUpListPage(BusinessPosUpgrade businessPosUpgrade) throws SQLException;
   
    
    /**
     * 新增POS机升级信息
     * @param businessPosUpgrade POS机升级信息对象
     * @throws HsException
     */
    public void addPosUpgradeVerInfo(BusinessPosUpgrade businessPosUpgrade) throws HsException;
    
    /**
     * 更新POS机升级信息为不更新状态
     * @param posDeviceType POS机型号
     * @throws HsException
     */
    public void updatePosUpInfoForN(String posDeviceType) throws HsException;
    
    /**
     * 更新POS机升级信息为不更新状态
     * @param posId 
     * @throws HsException
     */
    public void updatePosUpForN(String posId) throws HsException;
    
    
    /**
     * 更新POS机升级信息为更新状态,强制更新状态
     * @param posDeviceType POS机型号
     * @throws HsException
     */
    public void updatePosUpForY(BusinessPosUpgrade businessPosUpgrade) throws HsException;
    
    /**
     * 更新多个POS机升级信息为不更新状态
     * @param lsit POS机型号集合
     * @throws HsException
     */
    public void updatePosUpList(List<BusinessPosUpgrade> list) throws HsException;
    
    
        
}
