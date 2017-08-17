/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.bp.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bp.api.IBusinessPosUpgradeInfoService;
import com.gy.hsxt.bp.bean.BusinessPosUpgrade;
import com.gy.hsxt.bp.common.bean.BPConstants;
import com.gy.hsxt.bp.common.bean.PageContext;
import com.gy.hsxt.bp.mapper.BusinessPosUpgradeMapper;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 
 * POS机升级信息实现类
 * @Package: com.gy.hsxt.bp.service  
 * @ClassName: BusinessPosUpgradeService 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2016-4-9 上午11:24:48 
 * @version V1.0
 */

@Service
public class BusinessPosUpgradeService implements IBusinessPosUpgradeInfoService {

    // POS机升级信息Mapper
    @Autowired
    private BusinessPosUpgradeMapper businessPosUpgradeMapper;

    /**  
     * 查询POS机升级信息
     * @param posDeviceType POS机型号
     * @return BusinessPosUpgrade POS机升级信息
     * @throws HsException 
     * @see com.gy.hsxt.bp.api.IBusinessPosUpgradeInfoService#searchPosUpgradeInfo(com.gy.hsxt.bp.bean.BusinessPosUpgrade) 
     */
    @Override
    public BusinessPosUpgrade searchPosUpgradeVerInfo(String posDeviceType,String hsResNo,String posMachineNo) throws HsException {
        
        //判断POS机型号是否为空
        if(StringUtils.isBlank(posDeviceType)){
            SystemLog.debug("HSXT_BP", "方法：getPosUpgradeVerInfo()", RespCode.BP_PARAMETER_NULL.getCode() + "posDeviceType = "
                    + posDeviceType + ":POS机型号为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "posDeviceType = " + posDeviceType + ":POS机型号为空");
        }
        //判断POS机器号是否为空 
        /*if(StringUtils.isBlank(posMachineNo)){
            SystemLog.debug("HSXT_BP", "方法：getPosUpgradeVerInfo()", RespCode.BP_PARAMETER_NULL.getCode() + "posMachineNo = "
                    + posMachineNo + ":POS机器号为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "posMachineNo = " + posMachineNo + ":POS机器号为空");
        }*/
        //初始化POS机升级信息
        BusinessPosUpgrade bPos = null;
        try
        {
            //通过POS机型号查询数据库当前可升级的版本信息
            bPos = businessPosUpgradeMapper.searchPosUpgradeVerInfo(posDeviceType);
            if(bPos == null){
                throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "POS机型号:"+posDeviceType+"对应的升级文件不存在");
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：getPosUpgradeVerInfo()", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }
        // 返回当前可升级的POS机型号版本信息
        return bPos;
    }

    /**  
     * 新增POS机升级信息
     * @param businessPosUpgrade
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.bp.api.IBusinessPosUpgradeInfoService#addPosUpgradeVerInfo(com.gy.hsxt.bp.bean.BusinessPosUpgrade) 
     */
    @Override
    public void addPosUpgradeVerInfo(BusinessPosUpgrade businessPosUpgrade) throws HsException {
        
        // POS机型号
        String posDeviceType = businessPosUpgrade.getPosDeviceType();
        // 升级版本号
        String posUpVerNo = businessPosUpgrade.getPosUpgradeVerNo();
        
        try
        {
            //是否有相同POS机型号的POS机信息，如果有则把它们更新为不需要更新的状态
            List<BusinessPosUpgrade> list = businessPosUpgradeMapper.searchPosUpgradeVerInfoList(posDeviceType);
            if(!list.isEmpty()){
                for(BusinessPosUpgrade pos : list){
                    String verson = pos.getPosUpgradeVerNo();
                    if(posUpVerNo.equals(verson)){
                        throw new HsException(RespCode.BP_REPEATED_DATA.getCode(), "POS机升级版本号重复");
                    }
                }
                businessPosUpgradeMapper.updatePosUpList(list);
            }
            //新增POS机升级信息
            businessPosUpgradeMapper.addPosUpgradeVerInfo(businessPosUpgrade);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：addPosUpgradeVerInfo()", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**  
     * 修改POS机升级信息
     * @param posId POS机升级ID
     * @param posDeviceType 机型号
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.bp.api.IBusinessPosUpgradeInfoService#addPosUpgradeVerInfo(com.gy.hsxt.bp.bean.BusinessPosUpgrade) 
     */
    @Override
    public void updatePosUpInfo(BusinessPosUpgrade businessPosUpgrade) throws HsException {
        
        String posId = businessPosUpgrade.getPosId();
        if(StringUtils.isBlank(posId)){
            SystemLog.debug("HSXT_BP", "方法：updatePosUpInfo()", RespCode.BP_PARAMETER_NULL.getCode() + "posId = "
                    + posId + ":POS升级信息ID为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "posId = " + posId + ":POS升级信息ID为空");
        }
        String posDeviceType = businessPosUpgrade.getPosDeviceType();
        if(StringUtils.isBlank(posDeviceType)){
            SystemLog.debug("HSXT_BP", "方法：updatePosUpInfo()", RespCode.BP_PARAMETER_NULL.getCode() + "posDeviceType = "
                    + posDeviceType + ":POS机型号为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "posDeviceType = " + posDeviceType + ":POS机型号为空");
        }
        try
        {
            String isUpdate = businessPosUpgrade.getIsUpgrade();// 更新标识
            if(StringUtils.isNotBlank(isUpdate)&&BPConstants.IS_ACTIVE_YES.equals(isUpdate)){
                //查询当前机型号为更新状态的版本改为不更新状态版本
                businessPosUpgradeMapper.updatePosUpInfoForN(posDeviceType);
            }
            //根据POS升级ID，修改指定的版本为更新版本
            businessPosUpgradeMapper.updatePosUpForY(businessPosUpgrade);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：updatePosUpInfo()", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }
    }
    
    /**  
     * 分页查询POS机升级信息列表
     * @param page
     * @return POS机升级信息列表
     * @throws HsException 
     * @see com.gy.hsxt.bp.api.IBusinessPosUpgradeInfoService#searchPosUpgradeVerInfoPage(com.gy.hsxt.common.bean.Page) 
     */
    @Override
    public PageData<BusinessPosUpgrade> searchPosUpgradeVerInfoPage(BusinessPosUpgrade businessPosUpgrade,Page page) throws HsException {
        
        PageData<BusinessPosUpgrade> pd = null;// 返回的POS机升级信息列表
        try
        {
            PageContext.setPage(page);
            this.validateDateFormat(businessPosUpgrade);
            //分页查询POS机升级信息列表
            List<BusinessPosUpgrade> list = businessPosUpgradeMapper.searchPosUpListPage(businessPosUpgrade);
            if(!list.isEmpty()){
                pd = new PageData<BusinessPosUpgrade>(page.getCount(),list);
            }
        }
        catch (SQLException e)
        {
            SystemLog.debug("HSXT_BP", "方法：searchPosUpgradeVerInfoPage()", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }
        return pd;
    }

   /**
    * 校验时间
    * @param businessPosUpgrade
    * @throws HsException
    */
   public void validateDateFormat(BusinessPosUpgrade businessPosUpgrade) throws HsException{
       
       String beginDate = businessPosUpgrade.getBeginDate();
       // 格式化日期并转换日期格式String-->TimeStamp
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       // 开始时间
       if (StringUtils.isNotBlank(beginDate))
       {
           try
           {
               Date date = sdf.parse(beginDate);
               beginDate = sdf.format(date) + " 00:00:00";
               businessPosUpgrade.setBeginDate(beginDate);
           }
           catch (ParseException e)
           {
               SystemLog.debug("HSXT_AC", "方法：validateDateFormat", RespCode.BP_PARAMETER_FORMAT_ERROR.getCode() + ","
                       + beginDate + " = " + beginDate + " ,日期格式错误，正确格式 yyyy-MM-dd");
               throw new HsException(RespCode.BP_PARAMETER_FORMAT_ERROR.getCode(), beginDate + " = " + beginDate
                       + " ,日期格式错误，正确格式 yyyy-MM-dd");
           }
       }
       String endDate = businessPosUpgrade.getEndDate();
       // 结束时间
       if (StringUtils.isNotBlank(endDate))
       {
           try
           {
               Date date = sdf.parse(endDate);
               endDate = sdf.format(date) + " 23:59:59";
               businessPosUpgrade.setEndDate(endDate);
           }
           catch (ParseException e)
           {
               SystemLog.debug("HSXT_AC", "方法：validateDateFormat", RespCode.BP_PARAMETER_FORMAT_ERROR.getCode() + ","
                       + endDate + " = " + endDate + " ,日期格式错误，正确格式 yyyy-MM-dd");
               throw new HsException(RespCode.BP_PARAMETER_FORMAT_ERROR.getCode(), endDate + " = " + endDate
                       + " ,日期格式错误，正确格式 yyyy-MM-dd");
           }
       }
   }
    



}
