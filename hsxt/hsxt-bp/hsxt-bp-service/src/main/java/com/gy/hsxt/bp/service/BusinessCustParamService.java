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

package com.gy.hsxt.bp.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.bp.api.IBusinessCustParamService;
import com.gy.hsxt.bp.bean.BusinessCustParamItems;
import com.gy.hsxt.bp.bean.BusinessCustParamItemsRedis;
import com.gy.hsxt.bp.common.bean.BPConstants;
import com.gy.hsxt.bp.common.bean.PageContext;
import com.gy.hsxt.bp.mapper.BusinessCustParamMapper;
import com.gy.hsxt.bp.mapper.BusinessParamSearchMapper;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BeanCopierUtils;

/**
 * 
 * @Package: com.gy.hsxt.bp.service
 * @ClassName: businessCustParamMapper
 * @Description: TODO
 * 
 * @author: weixz
 * @date: 2015-11-24 下午2:34:32
 * @version V1.0
 */

@Service
public class BusinessCustParamService implements IBusinessCustParamService {

    // 客户业务参数Mapper
    @Autowired
    private BusinessCustParamMapper businessCustParamMapper;

    @Resource(name="fixRedisUtil")
    RedisUtil<String> fixRedisUtil;

    // 参数获取服务业务Mapper
    @Autowired
    private BusinessParamSearchMapper businessParamSearchMapper;

    /**
     * 新增客户业务参数
     * 
     * @param BusinessCustParamItems
     *            客户业务参数对象
     * @throws HsException
     */
    @Override
    public void addCustParamItems(BusinessCustParamItems businessCustParamItems) throws HsException {

        // 客户全局编号
        String custId = businessCustParamItems.getCustId();
        if (custId == null || "".equals(custId))
        {
            SystemLog.debug("HSXT_BP", "方法：addCustParamItems", RespCode.BP_PARAMETER_NULL.getCode() + "custId = "
                    + custId + ":客户全局编号为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "custId = " + custId + ":客户全局编号为空");
        }
        
        // 系统参数组编号
        String sysGroupCode = businessCustParamItems.getSysGroupCode();
        if (sysGroupCode == null || "".equals(sysGroupCode))
        {
            SystemLog.debug("HSXT_BP", "方法：addCustParamItems", RespCode.BP_PARAMETER_NULL.getCode() + "sysGroupCode = "
                    + sysGroupCode + ":系统参数组编号为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysGroupCode = " + sysGroupCode + ":系统参数组编号为空");
        }

        // 系统参数项键
        String sysItemsKey = businessCustParamItems.getSysItemsKey();
        if (sysItemsKey == null || "".equals(sysItemsKey))
        {
            SystemLog.debug("HSXT_BP", "方法：addCustParamItems", RespCode.BP_PARAMETER_NULL.getCode() + "sysItemsKey = "
                    + sysItemsKey + ":系统参数项键为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysItemsKey = " + sysItemsKey + ":系统参数项键为空");
        }

        // 系统参数项键名称
        String sysItemsName = businessCustParamItems.getSysItemsName();
        if (sysItemsName == null || "".equals(sysItemsName))
        {
            SystemLog.debug("HSXT_BP", "方法：addCustParamItems", RespCode.BP_PARAMETER_NULL.getCode() + "sysItemsName = "
                    + sysItemsName + ":系统参数项键名称为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysItemsName = " + sysItemsName
                    + ":系统参数项键名称为空");
        }

        // 系统参数项键值
//        String sysItemsValue = businessCustParamItems.getSysItemsValue();
//        if (sysItemsValue == null || "".equals(sysItemsValue))
//        {
//            SystemLog.debug("HSXT_BP", "方法：addCustParamItems", RespCode.BP_PARAMETER_NULL.getCode()
//                    + "sysItemsValue = " + sysItemsValue + ":系统参数项键值为空");
//            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysItemsValue = " + sysItemsValue
//                    + ":系统参数项键值为空");
//        }

        // 创建者
        String createdby = businessCustParamItems.getCreatedby();
        if (createdby == null || "".equals(createdby))
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamItems", RespCode.BP_PARAMETER_NULL.getCode() + "createdby = "
                    + createdby + ":创建者参数为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "createdby = " + createdby + ":创建者参数为空");
        }

        // 激活状态:Y：是；N：否
        String isActive = businessCustParamItems.getIsActive();
        if (isActive == null || "".equals(isActive))
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamItems", RespCode.BP_PARAMETER_NULL.getCode() + "isActive = "
                    + isActive + ":激活状态为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "isActive = " + isActive + ":激活状态为空");
        }
        else if (!BPConstants.IS_ACTIVE_YES.equals(isActive) && !BPConstants.IS_ACTIVE_NOT.endsWith(isActive))
        {
            SystemLog.debug("HSXT_BP", "方法：addSysParamItems", RespCode.BP_PARAMETER_FORMAT_ERROR.getCode()
                    + "isActive = " + isActive + ":激活状态只能用（Y或N）表示");
            throw new HsException(RespCode.BP_PARAMETER_FORMAT_ERROR.getCode(), "isActive = " + isActive
                    + ":激活状态只能用（Y或N）表示");
        }

        try
        {
            // 判断该客户ID对应的系统参数组编码和系统参数键是否已经存在数据库中
            BusinessCustParamItems buCustParamItems = businessCustParamMapper.getCustParamItemsByIdAndKey(custId,
                    sysItemsKey);
            if (buCustParamItems != null)
            {
                SystemLog.debug("HSXT_BP", "方法：addCustParamItems", RespCode.BP_PARAMETER_NULL.getCode()
                        + "sysItemsKey = " + sysItemsKey + ":该客户参数的系统常数项键已经存在数据库中");
                throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysItemsKey = " + sysItemsKey
                        + ":该客户参数的系统常数项键已经存在数据库中");
            }

            // 将该客户业务参数对象插入到数据中
            businessCustParamMapper.addCustParamItems(businessCustParamItems);
            // 将该客户业务参数对象添加到Redis服务器中
            BusinessParamSearchService businessParamSearchService = new BusinessParamSearchService(fixRedisUtil,
                    businessParamSearchMapper);
            businessParamSearchService.searchCustParamItemsByIdKey(custId, sysItemsKey);

        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：addCustBusinessParam", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }

    }

    /**
     * 批量新增或更新同一客户业务参数
     * @param custId 客户ID
     * @param custParamItemsList 客户业务参数对象List
     * @throws HsException 
     * @see com.gy.hsxt.bp.api.IBusinessCustParamService#addOrUpdateCustParamItemsList(java.lang.String, java.util.List)
     */
    public void addOrUpdateCustParamItemsList(String custId, List<BusinessCustParamItemsRedis> custParamItemsList) throws HsException {

        // 校验客户业务参数是否为空
        if (custParamItemsList == null || custParamItemsList.size() == 0)
        {
            SystemLog.debug("HSXT_BP", "方法：addCustParamItemsList", RespCode.BP_PARAMETER_NULL.getCode()
                    + "custParamItems = NULL:客户业务参数为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "custParamItems = NULL:客户业务参数为空");
        }
        
        // 客户全局编号
        if (custId == null || "".equals(custId))
        {
            SystemLog.debug("HSXT_BP", "方法：addOrUpdateCustParamItemsList", RespCode.BP_PARAMETER_NULL.getCode() + "custId = "
                    + custId + ":客户全局编号为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "custId = " + custId + ":客户全局编号为空");
        }
        
        
        //新增客户业务参数对象List
        List<BusinessCustParamItems> addCustParamItemsList = new ArrayList<BusinessCustParamItems>();
        
        //修改客户业务参数对象List
        List<BusinessCustParamItems> updateCustParamItemsList = new ArrayList<BusinessCustParamItems>();
        
        for (int i = 0; i < custParamItemsList.size(); i++)
        {
            
            BusinessCustParamItemsRedis custParamItemsRedis = custParamItemsList.get(i);
            BusinessCustParamItems custParamItems = new BusinessCustParamItems();
            
            // 互生号
            String hsResNo = custParamItemsRedis.getHsResNo();
            if (hsResNo == null || "".equals(hsResNo))
            {
                SystemLog.debug("HSXT_BP", "方法：addOrUpdateCustParamItemsList", RespCode.BP_PARAMETER_NULL.getCode()
                        + "hsResNo = " + hsResNo + ":互生号为空");
                throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "hsResNo = " + hsResNo
                        + ":互生号为空");
            }
            
            // 客户名称
            String custName = custParamItemsRedis.getCustName();
            /*if (custName == null || "".equals(custName))
            {
                SystemLog.debug("HSXT_BP", "方法：addOrUpdateCustParamItemsList", RespCode.BP_PARAMETER_NULL.getCode()
                        + "custName = " + custName + ":客户名称为空");
                throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "custName = " + custName
                        + ":客户名称为空");
            }*/
            
            // 系统参数组编号
            String sysGroupCode = custParamItemsRedis.getSysGroupCode();
            if (sysGroupCode == null || "".equals(sysGroupCode))
            {
                SystemLog.debug("HSXT_BP", "方法：addOrUpdateCustParamItemsList", RespCode.BP_PARAMETER_NULL.getCode()
                        + "sysGroupCode = " + sysGroupCode + ":系统参数组编号为空");
                throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysGroupCode = " + sysGroupCode
                        + ":系统参数组编号为空");
            }

            // 客户业务参数项键
            String sysItemsKey = custParamItemsRedis.getSysItemsKey();
            if (sysItemsKey == null || "".equals(sysItemsKey))
            {
                SystemLog.debug("HSXT_BP", "方法：addOrUpdateCustParamItemsList", RespCode.BP_PARAMETER_NULL.getCode()
                        + "sysItemsKey = " + sysItemsKey + ":系统参数项键为空");
                throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysItemsKey = " + sysItemsKey
                        + ":系统参数项键为空");
            }

            // 客户业务参数项键名称
            String sysItemsName = custParamItemsRedis.getSysItemsName();
            if (sysItemsName == null || "".equals(sysItemsName))
            {
                SystemLog.debug("HSXT_BP", "方法：addCustParamItems", RespCode.BP_PARAMETER_NULL.getCode()
                        + "sysItemsName = " + sysItemsName + ":系统参数项键名称为空");
                throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysItemsName = " + sysItemsName
                        + ":系统参数项键名称为空");
            }

            // 客户业务参数项键值
            String sysItemsValue = custParamItemsRedis.getSysItemsValue();
//            if (sysItemsValue == null || "".equals(sysItemsValue))
//            {
//                SystemLog.debug("HSXT_BP", "方法：addCustParamItems", RespCode.BP_PARAMETER_NULL.getCode()
//                        + "sysItemsValue = " + sysItemsValue + ":系统参数项键值为空");
//                throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysItemsValue = " + sysItemsValue
//                        + ":系统参数项键值为空");
//            }
            custParamItems.setSysGroupCode(sysGroupCode);
            custParamItems.setSysItemsKey(sysItemsKey);
            custParamItems.setSysItemsName(sysItemsName);
            custParamItems.setSysItemsValue(sysItemsValue);
            custParamItems.setCustId(custId);
            custParamItems.setCreatedby(custId);
            custParamItems.setHsResNo(hsResNo);
            custParamItems.setCustName(custName);
            String isActive = custParamItemsRedis.getIsActive();
            if(StringUtils.isBlank(isActive)){
                isActive = BPConstants.IS_ACTIVE_YES;
            }
            custParamItems.setIsActive(isActive);
            
            // 客户参数ID
            String custItemsId = custParamItemsRedis.getCustItemsId();
            custParamItems.setCustItemsId(custItemsId);
            //判断客户参数ID是否有
            if (custItemsId == null || "".equals(custItemsId))
            {
                BusinessCustParamItems businessCustParamItems;
                try
                {
                    //无客户参数ID先根据客户ID与参数键名查询该客户的业务参数
                    businessCustParamItems = businessCustParamMapper.getCustParamItemsByIdAndKey(custId, sysItemsKey);
                    //判断是否查询到该客户的业务参数
                    if(businessCustParamItems!=null)
                    {
                        custParamItems.setCustItemsId(businessCustParamItems.getCustItemsId());
                        //已有该客户的业务参数就放到更新List里
                        updateCustParamItemsList.add(custParamItems);
                    }else{
                        //无该客户的业务参数就放到新增List里
                        addCustParamItemsList.add(custParamItems);
                    }
                }
                catch (Exception e)
                {
                    SystemLog.debug("HSXT_BP", "方法：addOrUpdateCustParamItemsList", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
                    throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
                }
            }else{
                //有客户参数ID直接放入更新List里
                updateCustParamItemsList.add(custParamItems);
            }
        }
        
        try
        {
            //批量新增客户业务参数
            if(addCustParamItemsList!=null&&addCustParamItemsList.size()>0)
            {
                businessCustParamMapper.addCustParamItemsList(addCustParamItemsList);
            }
            
            //批量更新客户业务参数
            if(updateCustParamItemsList!=null&&updateCustParamItemsList.size()>0)
            {
                businessCustParamMapper.updateCustParamItemsList(updateCustParamItemsList);
            }
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：addOrUpdateCustParamItemsList", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }
        
        // 将该客户业务参数对象添加到Redis服务器中
        BusinessParamSearchService businessParamSearchService = new BusinessParamSearchService(fixRedisUtil,
                businessParamSearchMapper);
        businessParamSearchService.searchCustParamItemsByGroup(custId);
        
    }

    /**
     * 更新客户业务参数
     * 
     * @param BusinessCustParamItems
     *            客户业务参数对象
     * @throws HsException
     */
    @Override
    public void updateCustParamItems(BusinessCustParamItems businessCustParamItems) throws HsException {

        // 客户参数ID
        String custItemsId = businessCustParamItems.getCustItemsId();
        if (custItemsId == null || "".equals(custItemsId))
        {
            this.addCustParamItems(businessCustParamItems);
            return;
        }

        // 客户全局编码
        String custId = businessCustParamItems.getCustId();
        if (custId == null || "".equals(custId))
        {
            SystemLog.debug("HSXT_BP", "方法：updateCustBusinessParam", RespCode.BP_PARAMETER_NULL.getCode() + "custId = "
                    + custId + ":客户全局编码为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "custId = " + custId + ":客户全局编码为空");
        }

        // 系统参数项键名
        String sysItemsKey = businessCustParamItems.getSysItemsKey();
        if (sysItemsKey == null || "".equals(sysItemsKey))
        {
            SystemLog.debug("HSXT_BP", "方法：updateCustBusinessParam", RespCode.BP_PARAMETER_NULL.getCode()
                    + "sysItemsKey = " + sysItemsKey + ":系统参数项键名为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysItemsKey = " + sysItemsKey + ":系统参数项键名为空");
        }

        // 更新者ID
        String updatedby = businessCustParamItems.getUpdatedby();
        if (updatedby == null || "".equals(updatedby))
        {
            SystemLog.debug("HSXT_BP", "方法：updateCustBusinessParam", RespCode.BP_PARAMETER_NULL.getCode()
                    + "updatedby = " + updatedby + ":更新者ID为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "updatedby = " + updatedby + ":更新者ID为空");
        }

        try
        {
            // 根据客户业务参数对象中的条件更新对应数据中的数据
            businessCustParamMapper.updateCustParamItems(businessCustParamItems);

            // 将该更新的客户业务参数对象更新到Redis服务器中
            BusinessParamSearchService businessParamSearchService = new BusinessParamSearchService(fixRedisUtil,
                    businessParamSearchMapper);
            Map<String, BusinessCustParamItemsRedis> businessCustParamItemsMap = businessParamSearchService
                    .searchCustParamItemsByGroup(custId);
            BusinessCustParamItemsRedis custParamItemsRedis = new BusinessCustParamItemsRedis();
            BeanCopierUtils.copy(businessCustParamItems, custParamItemsRedis);
            businessCustParamItemsMap.put(sysItemsKey, custParamItemsRedis);
            // 把Map装换为String
            String objectJson = JSON.toJSONString(businessCustParamItemsMap);
            fixRedisUtil.add(BPConstants.SYS_NAME, custId, objectJson);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：updateCustBusinessParam", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }

    }

    /**
     * 删除客户业务参数
     * 
     * @param custItemsId
     *            客户参数ID
     * @param custId
     *            客户全局编码
     * @param sysItemsKey
     *            系统参数项键名
     * @throws SQLException
     */
    @Override
    public void deleteCustParamItems(String custItemsId, String custId, String sysItemsKey) throws HsException {

        // 客户参数ID
        if (custItemsId == null || "".equals(custItemsId))
        {
            SystemLog.debug("HSXT_BP", "方法：deleteCustParamItems", RespCode.BP_PARAMETER_NULL.getCode()
                    + "custItemsId = " + custItemsId + ":客户参数ID为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "custItemsId = " + custItemsId + ":客户参数ID为空");
        }
        // 客户全局编码
        if (custId == null || "".equals(custId))
        {
            SystemLog.debug("HSXT_BP", "方法：updateCustBusinessParam", RespCode.BP_PARAMETER_NULL.getCode() + "custId = "
                    + custId + ":客户全局编码为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "custId = " + custId + ":客户全局编码为空");
        }

        // 系统参数项键名
        if (sysItemsKey == null || "".equals(sysItemsKey))
        {
            SystemLog.debug("HSXT_BP", "方法：updateCustBusinessParam", RespCode.BP_PARAMETER_NULL.getCode()
                    + "sysItemsKey = " + sysItemsKey + ":系统参数项键名为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysItemsKey = " + sysItemsKey + ":系统参数项键名为空");
        }
        try
        {
            // 根据该客户参数ID删除对应的数据库数据
            businessCustParamMapper.deleteCustParamItems(custItemsId);
            // 删除将该客户参数ID对应的的客户参数对象同时删除Redis服务器对应的对象
            BusinessParamSearchService businessParamSearchService = new BusinessParamSearchService(fixRedisUtil,
                    businessParamSearchMapper);
            Map<String, BusinessCustParamItemsRedis> businessCustParamItemsMap = businessParamSearchService
                    .searchCustParamItemsByGroup(custId);
            businessCustParamItemsMap.remove(sysItemsKey);
            // 把Map装换为String
            String objectJson = JSON.toJSONString(businessCustParamItemsMap);
            fixRedisUtil.add(BPConstants.SYS_NAME, custId, objectJson);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：deleteCustBusinessParam", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }

    }

    /**
     * 查询单个客户业务参数
     * 
     * @param custItemsId
     *            客户参数ID
     * @throws SQLException
     */
    @Override
    public BusinessCustParamItems getCustParamItemsById(String custItemsId) throws HsException {

        // 客户参数ID
        if (custItemsId == null || "".equals(custItemsId))
        {
            SystemLog.debug("HSXT_BP", "方法：getCustParamItemsById", RespCode.BP_PARAMETER_NULL.getCode()
                    + "custItemsId = " + custItemsId + ":客户参数ID为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "custItemsId = " + custItemsId + ":客户参数ID为空");
        }
        // 客户业务参数对象
        BusinessCustParamItems businessCustParamItems = null;
        try
        {
            // 根据客户参数ID查找对应数据库中的数据
            businessCustParamItems = (BusinessCustParamItems) businessCustParamMapper
                    .getCustParamItemsById(custItemsId);
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：getCustBusinessParamByCode", RespCode.BP_SQL_ERROR.getCode()
                    + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }
        return businessCustParamItems;
    }

    /**
     * 查询多个客户业务参数
     * 
     * @param BusinessCustParamItems
     *            客户业务参数对象
     * @param page
     *            分頁對象
     * @return PageData<BusinessCustParamItems> 封裝客户业务参数对象集合、當前條件的查詢總記錄數
     * @throws SQLException
     */
    @Override
    public PageData<BusinessCustParamItems> searchCustParamItemsPage(BusinessCustParamItems businessCustParamItems,
            Page page) throws HsException {

        // 分页信息设值
        PageContext.setPage(page);
        PageData<BusinessCustParamItems> pageDate = null;// 公用分页查询返回参数类

        try
        {
            // 根据客户业务参数对象中的条件查找对应的数据库信息数据
            List<BusinessCustParamItems> custBusinessParamList = businessCustParamMapper
                    .searchCustParamItemsListPage(businessCustParamItems);
            // 封裝客户业务参数对象集合、當前條件的查詢總記錄數
            pageDate = new PageData<BusinessCustParamItems>(page.getCount(), custBusinessParamList);
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：searchCustBusinessParamPage", RespCode.BP_SQL_ERROR.getCode()
                    + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }
        return pageDate;
    }

}
