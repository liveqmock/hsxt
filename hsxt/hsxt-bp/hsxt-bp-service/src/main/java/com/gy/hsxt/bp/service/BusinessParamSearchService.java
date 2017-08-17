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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.ds.api.IDSBatchService;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.bp.api.IBusinessParamSearchService;
import com.gy.hsxt.bp.bean.BusinessAgreeFee;
import com.gy.hsxt.bp.bean.BusinessAgreeFeeRedis;
import com.gy.hsxt.bp.bean.BusinessCustParamItems;
import com.gy.hsxt.bp.bean.BusinessCustParamItemsRedis;
import com.gy.hsxt.bp.bean.BusinessSysBoSetting;
import com.gy.hsxt.bp.bean.BusinessSysParamItems;
import com.gy.hsxt.bp.bean.BusinessSysParamItemsRedis;
import com.gy.hsxt.bp.common.bean.BPConstants;
import com.gy.hsxt.bp.mapper.BusinessParamSearchMapper;
import com.gy.hsxt.bp.mapper.BusinessSysBoSettingMapper;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BeanCopierUtils;

/**
 * 参数获取服务业务实现类
 * 
 * @Package: com.gy.hsxt.bp.service
 * @ClassName: BusinessParamSearchService
 * @Description: TODO
 * 
 * @author: weixz
 * @date: 2015-11-25 上午10:38:14
 * @version V1.0
 */

@Transactional
@Service
public class BusinessParamSearchService implements IBusinessParamSearchService, IDSBatchService {

    // 参数获取服务业务Mapper
    @Autowired
    private BusinessParamSearchMapper businessParamSearchMapper;
    
    // 业务操作许可设置Mapper
    @Autowired
    private BusinessSysBoSettingMapper businessSysBoSettingMapper;

    @Resource(name="fixRedisUtil")
    RedisUtil<String> fixRedisUtil;

    public BusinessParamSearchService(RedisUtil<String> fixRedisUtil, BusinessParamSearchMapper businessParamSearchMapper) {
        this.fixRedisUtil = fixRedisUtil;
        this.businessParamSearchMapper = businessParamSearchMapper;
    }

    public BusinessParamSearchService() {
    }
    
    protected static final Logger LOG = LoggerFactory.getLogger(BusinessParamSearchService.class);
    /**
     * 回调监听类
     */
    @Autowired
    private IDSBatchServiceListener listener;
    
    @Override
    public boolean excuteBatch(String executeId, Map<String, String> args) 
    {
        boolean result=true;
        if(listener!=null){
            LOG.info("执行中!!!!");
            // 发送进度通知
            listener.reportStatus(executeId, 2, "执行中");
            
            try
            {
            	//同步业务参数系统数据到Redis服务器中
            	initDataToRedis();
                LOG.info("执行成功!!!!");
                // 发送进度通知
                listener.reportStatus(executeId, 0,"执行成功");
            }
            catch (Exception e)
            {
                LOG.info("异常，执行失败!!!!");
                // 发送进度通知
                listener.reportStatus(executeId, 1,"执行失败");
                result=false;
            }
        }
        return result;
    }

    /**
     * 获取同一组系统参数项接口
     * 
     * @param sysGroupCode
     *            系统参数组编号
     * @return Map<String,BusinessSysParamItems> 同一组系统参数项Map集合
     * @throws 
     */
    @Override
    public Map<String, BusinessSysParamItemsRedis> searchSysParamItemsByGroup(String sysGroupCode) throws HsException {
        // 返回的同一组系统参数项Map集合初始化
        Map<String, BusinessSysParamItemsRedis> sysParamItemsMap = null;
            try
            {
                // 根据系统参数组编号查询该组编号对应的全部系统参数项信息
                List<BusinessSysParamItems> businessSysParamItemsList = businessParamSearchMapper
                        .searchSysParamItemsByGroup(sysGroupCode);

                // 如果系统参数项集合不为空，遍历封装返回的Map集合（KEY：为 系统参数项键，VALUE：为系统参数项对象）
                if (!businessSysParamItemsList.isEmpty())
                {
                    sysParamItemsMap = new HashMap<String, BusinessSysParamItemsRedis>();
                    for (BusinessSysParamItems sysParamItems : businessSysParamItemsList)
                    {
                        BusinessSysParamItemsRedis sysParamItemsRedis = new BusinessSysParamItemsRedis();
                        BeanCopierUtils.copy(sysParamItems, sysParamItemsRedis);
                        sysParamItemsMap.put(sysParamItems.getSysItemsKey(), sysParamItemsRedis);
                    }
                    String objectJson = JSON.toJSONString(sysParamItemsMap);
                    fixRedisUtil.add(BPConstants.SYS_NAME, sysGroupCode, objectJson);
                }
            }
            catch (Exception e)
            {
                SystemLog.debug("HSXT_BP", "方法：searchSysParamItemsByGroup", RespCode.BP_SQL_ERROR.getCode()
                        + e.getMessage());
                throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
            }
        return sysParamItemsMap;
    }

    /**
     * 获取单个系统参数项接口
     * 
     * @param sysGroupCode
     *            系统参数组编号
     * @param sysItemsKey
     *            系统参数项键名
     * @return BusinessSysParamItems 系统参数项对象
     * @throws HsException
     */
    @Override
    public BusinessSysParamItemsRedis searchSysParamItemsByCodeKey(String sysGroupCode, String sysItemsKey)
            throws HsException {

        // 返回的系统参数项对象初始化
        BusinessSysParamItemsRedis sysParamItemsRedis = null;

        // 通过系统参数组编号去Redis服务器查找数据
        Map<String, BusinessSysParamItemsRedis> sysParamItemsMap = searchSysParamItemsByGroup(sysGroupCode);

        // 通过系统参数项键名匹配Redis服务的数据
        if (sysParamItemsMap != null)
        {
            sysParamItemsRedis = sysParamItemsMap.get(sysItemsKey);
        }

        // 如果Redis服务没有，则去数据库获取，并同时把该数据同步到Redis服务器上
//        if (sysParamItemsRedis == null)
//        {
//            throw new HsException(RespCode.BP_RESULT_EMPTY.getCode(), "sysItemsKey :" + sysItemsKey
//                        + ",Redis和数据库都不存在该数据");
//        }
        return sysParamItemsRedis;
    }

    /**
     * 获取同一协议的协议费用接口
     * 
     * @param agreeCode
     *            协议代码
     * @return Map<String,BusinessAgreeFee> 同一协议的协议费用Map集合
     * @throws HsException
     */
    @Override
    public Map<String, BusinessAgreeFeeRedis> searchBusinessAgreeFee(String agreeCode) throws HsException {

        // 返回的同一协议的协议费用Map集合初始化
        Map<String, BusinessAgreeFeeRedis> businessAgreeFeeMap = null;

            try
            {
                // 根据协议代码查询该代码对应的全部协议费用信息
                List<BusinessAgreeFee> businessAgreeFeeList = businessParamSearchMapper
                        .searchBusinessAgreeFee(agreeCode);

                // 如果协议费用集合不为空，遍历封装返回的Map集合（KEY：为 协议费用编号，VALUE：为协议费用对象）
                if (!businessAgreeFeeList.isEmpty())
                {
                    businessAgreeFeeMap = new HashMap<String, BusinessAgreeFeeRedis>();
                    for (BusinessAgreeFee businessAgreeFee : businessAgreeFeeList)
                    {
                        BusinessAgreeFeeRedis agreeFeeRedis = new BusinessAgreeFeeRedis();
                        BeanCopierUtils.copy(businessAgreeFee, agreeFeeRedis);
                        businessAgreeFeeMap.put(businessAgreeFee.getAgreeFeeCode(), agreeFeeRedis);
                    }
                    String objectJson = JSON.toJSONString(businessAgreeFeeMap);
                    fixRedisUtil.add(BPConstants.SYS_NAME, agreeCode, objectJson);
                }
            }
            catch (Exception e)
            {
                SystemLog.debug("HSXT_BP", "方法：searchBusinessAgreeFee", RespCode.BP_SQL_ERROR.getCode()
                        + e.getMessage());
                throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
            }
        return businessAgreeFeeMap;
    }

    /**
     * 获取单个协议费用接口
     * 
     * @param agreeCode
     *            协议代码
     * @param agreeFeeCode
     *            协议费用编号
     * @return BusinessAgreeFee 协议费用对象
     * @throws HsException
     */
    @Override
    public BusinessAgreeFeeRedis searchBusinessAgreeFeeByCode(String agreeCode, String agreeFeeCode) throws HsException {

        // 返回的协议费用对象初始化
        BusinessAgreeFeeRedis agreeFeeRedis = null;
        Map<String, BusinessAgreeFeeRedis> businessAgreeFeeMap = searchBusinessAgreeFee(agreeCode);

        // 通过协议代码匹配Redis服务的数据
        if (businessAgreeFeeMap != null)
        {
            agreeFeeRedis = businessAgreeFeeMap.get(agreeFeeCode);
        }

//        if (agreeFeeRedis == null)
//        {
//            throw new HsException(RespCode.BP_RESULT_EMPTY.getCode(), "agreeFeeCode :" + agreeFeeCode
//                        + ",Redis和数据库都不存在该数据");
//        }

        return agreeFeeRedis;
    }

    /**
     * 获取同一客户业务参数接口
     * 
     * @param custId
     *            客户全局编号
     * @return Map<String,BusinessCustParamItems> 同一客户业务参数Map集合
     * @throws HsException
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, BusinessCustParamItemsRedis> searchCustParamItemsByGroup(String custId) throws HsException {

        // 返回的同一客户业务参数Map集合初始化
        Map<String, BusinessCustParamItemsRedis> custParamItemsMap = null;

            try
            {
                // 根据客户全局编号查询该编号对应的全部客户业务参数信息
                List<BusinessCustParamItems> businessCustParamItemsList = businessParamSearchMapper
                        .searchCustParamItemsByGroup(custId);
                // 如果客户业务参数集合不为空，遍历封装返回的Map集合（KEY：系统参数项键名，VALUE：为客户业务参数对象）
                if (!businessCustParamItemsList.isEmpty())
                {
                    
                    // 获取redis上的参数
                    String custParams = String.valueOf(fixRedisUtil.get(BPConstants.SYS_NAME, custId, String.class));
                    
                    // 判断是否有参数
                    if (!"null".equals(custParams))
                    {
                        custParamItemsMap = new HashMap<String, BusinessCustParamItemsRedis>();
                        JSONObject jsonObject = null;
                        jsonObject = JSONObject.parseObject(custParams);
                        Map<String, JSONObject> mapString = JSONObject.toJavaObject(jsonObject, Map.class);
                        for (String key : mapString.keySet())
                        {
                            JSONObject jsonMap = mapString.get(key);
                            custParamItemsMap.put(key, JSONObject.toJavaObject(jsonMap, BusinessCustParamItemsRedis.class));
                        }
                    }
                    else
                    {
                        custParamItemsMap = new HashMap<String, BusinessCustParamItemsRedis>();
                    }
                    for (BusinessCustParamItems businessCustParamItems : businessCustParamItemsList)
                    {
                        BusinessCustParamItemsRedis custParamItemsRedis = new BusinessCustParamItemsRedis();
                        BeanCopierUtils.copy(businessCustParamItems, custParamItemsRedis);
                        custParamItemsMap.put(businessCustParamItems.getSysItemsKey(), custParamItemsRedis);
                    }
                    String objectJson = JSON.toJSONString(custParamItemsMap);
                    fixRedisUtil.add(BPConstants.SYS_NAME, custId, objectJson);
                }
            }
            catch (Exception e)
            {
                SystemLog.debug("HSXT_BP", "方法：searchCustParamItemsByGroup", RespCode.BP_SQL_ERROR.getCode()
                        + e.getMessage());
                throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
            }
        return custParamItemsMap;
    }

    /**
     * 获取单个的客户业务参数接口
     * 
     * @param custId
     *            客户全局编号
     * @param sysItemsKey
     *            系统参数项键名
     * @return BusinessCustParamItems 客户业务参数对象
     * @throws HsException
     */
    @Override
    public BusinessCustParamItemsRedis searchCustParamItemsByIdKey(String custId, String sysItemsKey) throws HsException {

        // 返回的客户业务参数对象初始化
        BusinessCustParamItemsRedis custParamItemsRedis = null;
        Map<String, BusinessCustParamItemsRedis> custParamItemsMap = searchCustParamItemsByGroup(custId);

        // 通过系统参数项键名匹配Redis服务的数据
        if (custParamItemsMap != null)
        {
            custParamItemsRedis = custParamItemsMap.get(sysItemsKey);
        }

//        if (custParamItemsRedis == null)
//        {
//            throw new HsException(RespCode.BP_RESULT_EMPTY.getCode(), "sysItemsKey :" + sysItemsKey
//                        + ",Redis和数据库都不存在该数据");
//        }
        return custParamItemsRedis;
    }
    
    
    /**
     * 同步业务参数系统数据到Redis服务器中
     */
    @SuppressWarnings("unchecked")
	@PostConstruct
    @Override
    public void initDataToRedis() throws HsException {

        // 获取系统参数组编号对应的每一组系统参数项的Map
        Map<String, Map<String, BusinessSysParamItemsRedis>> sysParamItemsMap = getAllBusinessSysParamItemsMap();
        // 遍历集合，并把系统参数项数据同步到Redis中
        if (!sysParamItemsMap.isEmpty())
        {
            for (String key : sysParamItemsMap.keySet())
            {
                String objectJson = JSON.toJSONString(sysParamItemsMap.get(key));
                fixRedisUtil.add(BPConstants.SYS_NAME, key, objectJson);
            }
        }

        // 获取协议对应的每一组协议费用的Map
        Map<String, Map<String, BusinessAgreeFeeRedis>> agreeFeeMap = getAllBusinessAgreeFeeMap();
        // 遍历集合，并把协议费用数据同步到Redis中
        if (!agreeFeeMap.isEmpty())
        {
            for (String key : agreeFeeMap.keySet())
            {
                String objectJson = JSON.toJSONString(agreeFeeMap.get(key));
                fixRedisUtil.add(BPConstants.SYS_NAME, key, objectJson);
            }
        }
        
        // 获取所有业务操作许可的Map
        Map<String, Map<String, BusinessCustParamItemsRedis>> custParamItemsRedisMap = getAllSysBoSettingServiceMap();
        // 遍历集合，并把所有业务操作许可数据同步到Redis中
        if (!custParamItemsRedisMap.isEmpty())
        {
            for (String key : custParamItemsRedisMap.keySet())
            {
            	// 获取redis上的参数
                String custParams = String.valueOf(fixRedisUtil.get(BPConstants.SYS_NAME, key, String.class));
                JSONObject jsonObject = null;
                jsonObject = JSONObject.parseObject(custParams);
                Map<String, JSONObject> mapString = JSONObject.toJavaObject(jsonObject, Map.class);
                if(mapString == null)
                {
                    mapString = new HashMap<String, JSONObject>();
                }
                //获取相同custId的业务操作许可Map
                Map<String, BusinessCustParamItemsRedis> sysItemsMap = custParamItemsRedisMap.get(key);
                for(String key2 : sysItemsMap.keySet())
                {
                	//替换redis上key相同的对象
                	mapString.put(key2, JSONObject.parseObject(JSON.toJSONString(sysItemsMap.get(key2))));
                }
                //上传同步到Redis中
                fixRedisUtil.add(BPConstants.SYS_NAME, key, JSON.toJSONString(mapString));
            }
        }
    }

    /**
     * 获取系统参数组编号对应的每一组系统参数项的Map
     * 
     * @return Map<String,Map<String,BusinessSysParamItems>>
     *         系统参数组编号对应的每一组系统参数项的Map
     */
    public Map<String, Map<String, BusinessSysParamItemsRedis>> getAllBusinessSysParamItemsMap() throws HsException {

        // 系统参数组编号对应的每一组系统参数项的Map
        Map<String, Map<String, BusinessSysParamItemsRedis>> sysGroupMap = new HashMap<>();
        // 系统参数项的Map
        Map<String, BusinessSysParamItemsRedis> sysItemsMap = null;
        String isActive = "Y";
        try
        {
            // 获取全部激活的系统参数项
            List<BusinessSysParamItems> sysParamItemsList = businessParamSearchMapper.searchSysParamItemsList(isActive);

            if (sysParamItemsList != null && !sysParamItemsList.isEmpty())
            {
                for (BusinessSysParamItems businessSysParamItems : sysParamItemsList)
                {
                    String sysItemsKey = businessSysParamItems.getSysItemsKey();// 系统参数项键名
                    String sysGroupCode = businessSysParamItems.getSysGroupCode();// 系统参数组编号

                    // 遍历全部系统参数项，按系统参数组编号为KEY，系统参数项对象为VALUE
                    // 封装为一组Map,最后还是以系统参数组编号为KEY，Map为VALUE封装为新的Map
                    sysItemsMap = sysGroupMap.get(sysGroupCode);
                    // 判断Map里面是否有系统参数组编号对应的参数项Map,没有则New一个新的Map,有则往这个Map里面添加数据
                    if (sysItemsMap == null)
                    {
                        sysItemsMap = new HashMap<String, BusinessSysParamItemsRedis>();
                        BusinessSysParamItemsRedis sysParamItemsRedis = new BusinessSysParamItemsRedis();
                        BeanCopierUtils.copy(businessSysParamItems, sysParamItemsRedis);
                        // 封装系统参数项的Map的数据
                        sysItemsMap.put(sysItemsKey, sysParamItemsRedis);
                        // 封装系统参数组编号对应的每一组系统参数项的Map
                        sysGroupMap.put(sysGroupCode, sysItemsMap);
                    }
                    else
                    {
                        BusinessSysParamItemsRedis sysParamItemsRedis = new BusinessSysParamItemsRedis();
                        BeanCopierUtils.copy(businessSysParamItems, sysParamItemsRedis);
                        
                        // 封装系统参数项的Map的数据
                        sysItemsMap.put(sysItemsKey, sysParamItemsRedis);
                        // 封装系统参数组编号对应的每一组系统参数项的Map
                        sysGroupMap.put(sysGroupCode, sysItemsMap);
                    }
                }
            }
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_BP", "方法：getAllBusinessSysParamItemsMap", RespCode.BP_SQL_ERROR.getCode()
                    + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }
        return sysGroupMap;
    }

    /**
     * 获取协议对应的每一组协议费用的Map
     * 
     * @return Map<String,Map<String,BusinessAgreeFee>> 协议对应的每一组协议费用的Map
     */
    public Map<String, Map<String, BusinessAgreeFeeRedis>> getAllBusinessAgreeFeeMap() throws HsException {

        // 协议对应的每一组协议费用的Map
        Map<String, Map<String, BusinessAgreeFeeRedis>> agreeMap = new HashMap<>();
        // 协议费用的Map
        Map<String, BusinessAgreeFeeRedis> agreeFeeMap = null;
        String isActive = "Y";
        try
        {
            // 获取全部激活的协议费用
            List<BusinessAgreeFee> agreeFeeList = businessParamSearchMapper.searchBusinessAgreeFeeList(isActive);

            if (agreeFeeList != null && !agreeFeeList.isEmpty())
            {
                for (BusinessAgreeFee businessAgreeFee : agreeFeeList)
                {
                    String agreeFeeCode = businessAgreeFee.getAgreeFeeCode();// 协议费用编号
                    String agreeCode = businessAgreeFee.getAgreeCode();// 协议代码

                    // 遍历全部协议费用，按协议代码为KEY，协议费用对象为VALUE
                    // 封装为一组Map,最后还是以协议代码为KEY，Map为VALUE封装为新的Map
                    agreeFeeMap = agreeMap.get(agreeCode);
                    if (agreeFeeMap == null)
                    {
                        agreeFeeMap = new HashMap<String, BusinessAgreeFeeRedis>();
                        BusinessAgreeFeeRedis agreeFeeRedis = new BusinessAgreeFeeRedis();
                        BeanCopierUtils.copy(businessAgreeFee, agreeFeeRedis);
                        // 封装协议费用的Map的数据
                        agreeFeeMap.put(agreeFeeCode, agreeFeeRedis);
                        // 封装协议对应的每一组协议费用的Map
                        agreeMap.put(agreeCode, agreeFeeMap);
                    }
                    else
                    {
                        BusinessAgreeFeeRedis agreeFeeRedis = new BusinessAgreeFeeRedis();
                        BeanCopierUtils.copy(businessAgreeFee, agreeFeeRedis);
                        // 封装协议费用的Map的数据
                        agreeFeeMap.put(agreeFeeCode, agreeFeeRedis);
                        // 封装协议对应的每一组协议费用的Map
                        agreeMap.put(agreeCode, agreeFeeMap);
                    }
                }
            }
        }
        catch (Exception e)
        {
            SystemLog
                    .debug("HSXT_BP", "方法：getAllBusinessAgreeFeeMap", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
        }
        return agreeMap;
    }
    
    /**
     * 获取所有业务操作许可的Map
     * 
     * @return List<String,BusinessAgreeFee>> 所有业务操作许可的List
     */
    public Map<String, Map<String, BusinessCustParamItemsRedis>> getAllSysBoSettingServiceMap() throws HsException {
    	
    	Map<String, Map<String, BusinessCustParamItemsRedis>> custParamItemsRedisMap = new HashMap<String, Map<String, BusinessCustParamItemsRedis>>();
    	
    	Map<String, BusinessCustParamItemsRedis> custParamItemsMap = null;
    	
    	try {
    		BusinessSysBoSetting sysBoSetting = new BusinessSysBoSetting();
    		//查询所有业务操作许可
			List<BusinessSysBoSetting> sysBoSettingList = businessSysBoSettingMapper.searchSysBoSettingList(sysBoSetting);
			for(int i=0; i<sysBoSettingList.size(); i++){
				BusinessSysBoSetting sysBoSettingL = sysBoSettingList.get(i);
				BusinessCustParamItemsRedis custParamItemsRedis = new BusinessCustParamItemsRedis();
				//客户ID
				custParamItemsRedis.setCustId(sysBoSettingL.getCustId());
				//设置项
	        	custParamItemsRedis.setSysItemsKey(sysBoSettingL.getSettingName());
	        	//设置值
	        	custParamItemsRedis.setSysItemsValue(sysBoSettingL.getSettingValue());
	        	//设置原因
	        	custParamItemsRedis.setSettingRemark(sysBoSettingL.getSettingRemark());
	        	
	        	// 遍历所有业务操作许可，按客户ID为KEY，客户业务参数對象为VALUE
	        	custParamItemsMap = custParamItemsRedisMap.get(sysBoSettingL.getCustId());
	        	if(custParamItemsMap == null)
	        	{
	        		custParamItemsMap = new HashMap<String, BusinessCustParamItemsRedis>();
	        		custParamItemsMap.put(sysBoSettingL.getCustId(), custParamItemsRedis);
	        	}
	        	else
	        	{
	        		custParamItemsMap.put(sysBoSettingL.getCustId(), custParamItemsRedis);
	        	}
	        	custParamItemsRedisMap.put(sysBoSettingL.getCustId(), custParamItemsMap);
			}
		} catch (Exception e) {
			SystemLog.debug("HSXT_BP", "方法：searchSysBoSettingList", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
		}
    	
    	return custParamItemsRedisMap;
    }
}
