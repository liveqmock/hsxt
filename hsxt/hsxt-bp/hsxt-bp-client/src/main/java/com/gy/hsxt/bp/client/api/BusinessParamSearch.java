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

package com.gy.hsxt.bp.client.api;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.bp.api.IBusinessParamSearchService;
import com.gy.hsxt.bp.bean.BusinessAgreeFeeRedis;
import com.gy.hsxt.bp.bean.BusinessCustParamItemsRedis;
import com.gy.hsxt.bp.bean.BusinessSysParamItemsRedis;
import com.gy.hsxt.bp.client.common.bean.BPConstants;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BeanCopierUtils;

/**
 * 参数获取服务业务实现类
 * 
 * @Package: com.gy.hsxt.bp.service
 * @ClassName: BusinessParamSearch
 * @Description: TODO
 * 
 * @author: weixz
 * @date: 2015-12-02 上午10:38:14
 * @version V1.0
 */

@Service
public class BusinessParamSearch {

	@Resource(name="fixRedisUtil")
    RedisUtil<String> fixRedisUtil;
	
	@Resource(name="changeRedisUtil")
	RedisUtil<String> changeRedisUtil;
    
    //业务参数获取服务
    @Autowired
    private IBusinessParamSearchService businessParamSearchService;
    
    public BusinessParamSearch() {

    }

    /**
     * 获取同一组系统参数项接口
     * 
     * @param sysGroupCode
     *            系统参数组编号
     * @return Map<String,BusinessSysParamItems> 同一组系统参数项Map集合
     * @throws HsException
     */
    @SuppressWarnings("unchecked")
    public Map<String, BusinessSysParamItemsRedis> searchSysParamItemsByGroup(String sysGroupCode) throws HsException {
        
    	if(sysGroupCode == null || "".equals(sysGroupCode))
        {
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysGroupCode = " + sysGroupCode + ":系统参数组编号为空");
        }
    	
    	// 返回的同一组系统参数项Map集合初始化
        Map<String, BusinessSysParamItemsRedis> sysParamItemsMap = null;

        // 获取redis上的参数
        String sysParams = String.valueOf(fixRedisUtil.get(BPConstants.SYS_NAME, sysGroupCode, String.class));

        // 判断是否有参数
        if (!"null".equals(sysParams))
        {
            sysParamItemsMap = new HashMap<String, BusinessSysParamItemsRedis>();
            JSONObject jsonObject = null;
            jsonObject = JSONObject.parseObject(sysParams);
            Map<String, JSONObject> mapString = JSONObject.toJavaObject(jsonObject, Map.class);
            for (String key : mapString.keySet())
            {
                JSONObject jsonMap = mapString.get(key);
                sysParamItemsMap.put(key, JSONObject.toJavaObject(jsonMap, BusinessSysParamItemsRedis.class));
            }
        }
        else
        {
            sysParamItemsMap = businessParamSearchService.searchSysParamItemsByGroup(sysGroupCode);
            if(sysParamItemsMap==null)
            {
            	sysParamItemsMap = new HashMap<String, BusinessSysParamItemsRedis>();
            }
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
    public BusinessSysParamItemsRedis searchSysParamItemsByCodeKey(String sysGroupCode, String sysItemsKey)
            throws HsException {
    	
    	// 系统参数组编号
    	if (sysGroupCode == null || "".equals(sysGroupCode))
        {
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysGroupCode = " + sysGroupCode + ":系统参数组编号为空");
        }

        // 系统参数项键
        if (sysItemsKey == null || "".equals(sysItemsKey))
        {
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysItemsKey = " + sysItemsKey + ":系统参数项键为空");
        }

        // 返回的系统参数项对象初始化
        BusinessSysParamItemsRedis businessSysParamItems = null;

        // 通过系统参数组编号去Redis服务器查找数据
        Map<String, BusinessSysParamItemsRedis> sysParamItemsMap = searchSysParamItemsByGroup(sysGroupCode);

        // 通过系统参数项键名匹配Redis服务的数据
        if (sysParamItemsMap != null)
        {
            businessSysParamItems = sysParamItemsMap.get(sysItemsKey);
        }

        // 如果Redis服务没有，则去数据库获取，并同时把该数据同步到Redis服务器上
        if (businessSysParamItems == null)
        {
            businessSysParamItems = businessParamSearchService.searchSysParamItemsByCodeKey(sysGroupCode, sysItemsKey);
            
            if(businessSysParamItems==null)
            {
            	businessSysParamItems = new BusinessSysParamItemsRedis();
            	businessSysParamItems.setSysGroupCode(sysGroupCode);
            	businessSysParamItems.setSysItemsKey(sysItemsKey);
            }
        }

        return businessSysParamItems;
    }

    /**
     * 获取同一协议的协议费用接口
     * 
     * @param agreeCode
     *            协议代码
     * @return Map<String,BusinessAgreeFee> 同一协议的协议费用Map集合
     * @throws HsException
     */
    @SuppressWarnings("unchecked")
    public Map<String, BusinessAgreeFeeRedis> searchBusinessAgreeFee(String agreeCode) throws HsException {
    	
    	// 协议代码
        if (agreeCode == null || "".equals(agreeCode))
        {
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "agreeCode = " + agreeCode + ":协议代码为空");
        }
    	
        // 返回的同一协议的协议费用Map集合初始化
        Map<String, BusinessAgreeFeeRedis> businessAgreeFeeMap = null;

        // 获取redis上的参数
        String agreeParams = String.valueOf(fixRedisUtil.get(BPConstants.SYS_NAME, agreeCode, String.class));

        // 判断是否有参数
        if (!"null".equals(agreeParams))
        {
            businessAgreeFeeMap = new HashMap<String, BusinessAgreeFeeRedis>();
            JSONObject jsonObject = null;
            jsonObject = JSONObject.parseObject(agreeParams);
            Map<String, JSONObject> mapString = JSONObject.toJavaObject(jsonObject, Map.class);
            for (String key : mapString.keySet())
            {
                JSONObject jsonMap = mapString.get(key);
                businessAgreeFeeMap.put(key, JSONObject.toJavaObject(jsonMap, BusinessAgreeFeeRedis.class));
            }
        }
        else
        {
            businessAgreeFeeMap = businessParamSearchService.searchBusinessAgreeFee(agreeCode);
            if(businessAgreeFeeMap == null)
            {
            	businessAgreeFeeMap = new HashMap<String, BusinessAgreeFeeRedis>();
            }
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
    public BusinessAgreeFeeRedis searchBusinessAgreeFeeByCode(String agreeCode, String agreeFeeCode) throws HsException {
    	
    	// 协议代码
        if (agreeCode == null || "".equals(agreeCode))
        {
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "agreeCode = " + agreeCode + ":协议代码为空");
        }
        
        // 协议费用编号
        if (agreeFeeCode == null || "".equals(agreeFeeCode))
        {
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "agreeFeeCode = " + agreeFeeCode + ":协议费用编号为空");
        }
    	
        // 返回的协议费用对象初始化
        BusinessAgreeFeeRedis businessAgreeFee = null;
        Map<String, BusinessAgreeFeeRedis> businessAgreeFeeMap = searchBusinessAgreeFee(agreeCode);

        // 通过协议代码匹配Redis服务的数据
        if (businessAgreeFeeMap != null)
        {
            businessAgreeFee = businessAgreeFeeMap.get(agreeFeeCode);
        }

        if (businessAgreeFee == null)
        {
            businessAgreeFee = businessParamSearchService.searchBusinessAgreeFeeByCode(agreeCode, agreeFeeCode);
            if(businessAgreeFee == null)
            {
            	businessAgreeFee = new BusinessAgreeFeeRedis();
            	businessAgreeFee.setAgreeCode(agreeCode);
            	businessAgreeFee.setAgreeFeeCode(agreeFeeCode);
            }
        }

        return businessAgreeFee;
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
    public Map<String, BusinessCustParamItemsRedis> searchCustParamItemsByGroup(String custId) throws HsException {

    	// 客户全局编号
        if (custId == null || "".equals(custId))
        {
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "custId = " + custId + ":客户全局编号为空");
        }
    	
        // 返回的同一客户业务参数Map集合初始化
        Map<String, BusinessCustParamItemsRedis> custParamItemsMap = null;

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
            custParamItemsMap = businessParamSearchService.searchCustParamItemsByGroup(custId);
            if(custParamItemsMap == null)
            {
            	custParamItemsMap = new HashMap<String, BusinessCustParamItemsRedis>();
            }
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
    public BusinessCustParamItemsRedis searchCustParamItemsByIdKey(String custId, String sysItemsKey) throws HsException {

    	// 客户全局编号
        if (custId == null || "".equals(custId))
        {
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "custId = " + custId + ":客户全局编号为空");
        }
        
        // 系统参数项键
        if (sysItemsKey == null || "".equals(sysItemsKey))
        {
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysItemsKey = " + sysItemsKey + ":系统参数项键为空");
        }
    	
        // 返回的客户业务参数对象初始化
        BusinessCustParamItemsRedis businessCustParamItems = null;
        Map<String, BusinessCustParamItemsRedis> custParamItemsMap = searchCustParamItemsByGroup(custId);

        // 通过系统参数项键名匹配Redis服务的数据
        if (custParamItemsMap != null)
        {
            businessCustParamItems = custParamItemsMap.get(sysItemsKey);
        }
        // 未在Redis服务上获取到数据，从数据库获取
        if (businessCustParamItems == null)
        {
            businessCustParamItems = businessParamSearchService.searchCustParamItemsByIdKey(custId, sysItemsKey);
            if (businessCustParamItems == null)
            {
                businessCustParamItems = new BusinessCustParamItemsRedis();
                businessCustParamItems.setCustId(custId);
                businessCustParamItems.setSysItemsKey(sysItemsKey);
            }
        }
        return businessCustParamItems;
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
	public BusinessCustParamItemsRedis searchCustParamItemsByIdKey(String custId, String sysGroupCode, String sysItemsKey) throws HsException {
    	
    	// 客户全局编号
        if (custId == null || "".equals(custId))
        {
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "custId = " + custId + ":客户全局编号为空");
        }
        
        // 系统参数组编号
    	if (sysGroupCode == null || "".equals(sysGroupCode))
        {
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysGroupCode = " + sysGroupCode + ":系统参数组编号为空");
        }
        
        // 系统参数项键
        if (sysItemsKey == null || "".equals(sysItemsKey))
        {
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysItemsKey = " + sysItemsKey + ":系统参数项键为空");
        }
    	
        // 返回的客户业务参数对象初始化
        BusinessCustParamItemsRedis custParamItemsRedis = null;
        Map<String, BusinessCustParamItemsRedis> custParamItemsMap = searchCustParamItemsByGroup(custId);

        // 通过系统参数项键名匹配Redis服务的数据
        if (custParamItemsMap != null)
        {
        	custParamItemsRedis = custParamItemsMap.get(sysItemsKey);
        }

        // 未在Redis服务上获取到数据，从数据库获取
        if (custParamItemsRedis == null)
        {
        	custParamItemsRedis = businessParamSearchService.searchCustParamItemsByIdKey(custId, sysItemsKey);
        }
        
        // 客户未在数据库设置个人业务参数去取平台默认设置的参数
        if(custParamItemsRedis == null)
        {
        	BusinessSysParamItemsRedis sysParamItemsRedis = this.searchSysParamItemsByCodeKey(sysGroupCode, sysItemsKey);
        	
        	if(sysParamItemsRedis != null)
        	{
        		custParamItemsRedis = new BusinessCustParamItemsRedis();
        		BeanCopierUtils.copy(sysParamItemsRedis, custParamItemsRedis);
        		custParamItemsRedis.setCustId(custId);
            	custParamItemsRedis.setSysGroupCode(sysGroupCode);
            	custParamItemsRedis.setSysItemsKey(sysItemsKey);
        	}
        	else
            {
            	custParamItemsRedis = new BusinessCustParamItemsRedis();
            	custParamItemsRedis.setCustId(custId);
            	custParamItemsRedis.setSysGroupCode(sysGroupCode);
            	custParamItemsRedis.setSysItemsKey(sysItemsKey);
            }
        }
        return custParamItemsRedis;
    }

    /**
     * 客户限制参数累加
     * @param businessCustParamItems
     * @throws HsException
     */
    @SuppressWarnings("unchecked")
	public void setBusinessCustParamItemsRed(BusinessCustParamItemsRedis custParamItemsRedis) throws HsException {
        
        // 客户全局编号
    	String custId = custParamItemsRedis.getCustId();
        if (custId == null || "".equals(custId))
        {
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "custId = " + custId + ":客户全局编号为空");
        }

        // 系统参数项键
        String sysItemsKey = custParamItemsRedis.getSysItemsKey();
        if (sysItemsKey == null || "".equals(sysItemsKey))
        {
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysItemsKey = " + sysItemsKey + ":系统参数项键为空");
        }
        
        //参数项值
        String sysItemsValue = custParamItemsRedis.getSysItemsValue();
        if (sysItemsValue == null || "".equals(sysItemsValue))
        {
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "sysItemsValue = " + sysItemsKey + ":参数项值为空");
        }
        
        //参数到期日期
        String dueDate = custParamItemsRedis.getDueDate();
        if (dueDate == null || "".equals(dueDate))
        {
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "dueDate = " + sysItemsKey + ":参数到期日期为空");
        }
        
        
        // 返回的同一组系统参数项Map集合初始化
        Map<String, BusinessCustParamItemsRedis> businessCustParamItemsMap = null;
        
        // 获取redis上的参数
        String custParams = String.valueOf(fixRedisUtil.get(BPConstants.SYS_NAME, custId, String.class));

        // 判断是否有参数
        if (custParams != null && !"".equals(custParams) && !"null".equals(custParams))
        {
        	businessCustParamItemsMap = new HashMap<String, BusinessCustParamItemsRedis>();
            JSONObject jsonObject = null;
            jsonObject = JSONObject.parseObject(custParams);
            Map<String, JSONObject> mapString = JSONObject.toJavaObject(jsonObject, Map.class);
            for (String key : mapString.keySet())
            {
                JSONObject jsonMap = mapString.get(key);
                businessCustParamItemsMap.put(key, JSONObject.toJavaObject(jsonMap, BusinessCustParamItemsRedis.class));
            }
        }

        if (businessCustParamItemsMap == null)
        {
            businessCustParamItemsMap = new HashMap<String, BusinessCustParamItemsRedis>();
        }

        businessCustParamItemsMap.put(custParamItemsRedis.getSysItemsKey(), custParamItemsRedis);
        String objectJson = JSON.toJSONString(businessCustParamItemsMap);
        fixRedisUtil.add(BPConstants.SYS_NAME, custId, objectJson);
    }
    
}
