/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bp.api;

import com.gy.hsxt.bp.bean.BusinessPosUpgrade;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;


/** 
 * 
 * POS机升级信息查询接口
 * @Package: com.gy.hsxt.bp.api  
 * @ClassName: IBusinessPosUpgradeInfoService 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2016-4-9 上午11:11:52 
 * @version V1.0
 */
public interface IBusinessPosUpgradeInfoService{

    /**
     * 查询POS机升级信息
     * @param posDeviceType POS机型号
     * @param posDeviceType 互生号
     * @param posMachineNo  POS机器号
     * @throws HsException
     */
    public BusinessPosUpgrade searchPosUpgradeVerInfo(String posDeviceType,String hsResNo,String posMachineNo) throws HsException;
    
    /**
     * 新增POS机升级信息
     * @param businessPosUpgrade POS机升级信息对象
     * @throws HsException
     */
    public void addPosUpgradeVerInfo(BusinessPosUpgrade businessPosUpgrade) throws HsException;
    
    /**
     * 分页查询POS机升级信息列表
     * @param page 分页信息
     * @return POS机升级信息列表
     * @throws HsException
     */
    public PageData<BusinessPosUpgrade> searchPosUpgradeVerInfoPage(BusinessPosUpgrade businessPosUpgrade, Page page) throws HsException;
    
    /**
     * 修改POS机升级信息
     * @param posId POS机升级ID
     * @param posDeviceType 机型号
     * @throws HsException
     */
    public void updatePosUpInfo(BusinessPosUpgrade businessPosUpgrade) throws HsException;
}
