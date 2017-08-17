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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.bp.api.IBusinessSysBoSettingService;
import com.gy.hsxt.bp.bean.BusinessCustParamItemsRedis;
import com.gy.hsxt.bp.bean.BusinessSysBoSetting;
import com.gy.hsxt.bp.common.bean.BPConstants;
import com.gy.hsxt.bp.common.bean.SetAndGetDataMethod;
import com.gy.hsxt.bp.mapper.BusinessSysBoSettingMapper;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;

/**
 * 业务操作许可设置
 * @Package: com.gy.hsxt.bp.service
 * @ClassName: BusinessSysBoSettingService
 * @Description: TODO
 * 
 * @author: maocan
 * @date: 2016-02-24 下午2:34:32
 * @version V1.0
 */

@Service
public class BusinessSysBoSettingService implements IBusinessSysBoSettingService {


    /**
     * 封装或者获取数据方法
     */
    @Autowired
    private SetAndGetDataMethod setAndGetDataMethod;
	
    @Resource(name="fixRedisUtil")
    RedisUtil<String> fixRedisUtil;

    // 业务操作许可设置Mapper
    @Autowired
    private BusinessSysBoSettingMapper businessSysBoSettingMapper;

    /**
     * 批量设置业务操作许可
     * @param custId 客户ID为空
     * @param operCustId 操作员ID为空
     * @param sysBoSettingList 业务操作许可List
     * @throws HsException 
     * @see com.gy.hsxt.bp.api.IBusinessSysBoSettingService#setSysBoSettingList(java.lang.String, java.lang.String, java.util.List)
     */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void setSysBoSettingList(String custId, String operCustId, List<BusinessSysBoSetting> sysBoSettingList)
			throws HsException {
		//校验客户ID是否为空
		if(custId == null || custId.length() == 0)
		{
			SystemLog.debug("HSXT_BP", "方法：setSysBoSettingList", RespCode.BP_PARAMETER_NULL.getCode() + "custId = "
                    + custId + ":客户ID为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "custId = " + custId + ":客户ID为空");
		}
		
		//校验操作员ID是否为空
		if(operCustId == null || operCustId.length() == 0)
		{
			SystemLog.debug("HSXT_BP", "方法：setSysBoSettingList", RespCode.BP_PARAMETER_NULL.getCode() + "operCustId = "
                    + operCustId + ":操作员ID为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "operCustId = " + operCustId + ":操作员ID为空");
		}
		
		BusinessSysBoSetting sysBoSetting = new BusinessSysBoSetting();
		//客户ID 
		sysBoSetting.setCustId(custId);
		
		//新增List
		List<BusinessSysBoSetting> addSysBoSettingList = new ArrayList<BusinessSysBoSetting>();
		//更新List
		List<BusinessSysBoSetting> updateSysBoSettingList = new ArrayList<BusinessSysBoSetting>();
		
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
            custParamItemsMap = new HashMap<String, BusinessCustParamItemsRedis>();
        }
		
		//查询多个业务操作许可
		Map<String, BusinessSysBoSetting> sysBoSettingMap = searchSysBoSettingList(sysBoSetting);
		for(int i=0; i<sysBoSettingList.size(); i++){
			BusinessSysBoSetting sysBoSettingL = sysBoSettingList.get(i);
			BusinessSysBoSetting sysBoSettingM = sysBoSettingMap.get(sysBoSettingL.getSettingName());
			if(sysBoSettingM != null && sysBoSettingL.getSettingName().equals(sysBoSettingM.getSettingName()))
			{
				sysBoSettingM.setIsActive("N");
				sysBoSettingM.setUpdatedby(operCustId);
				//更新对象添加
				updateSysBoSettingList.add(sysBoSettingM);
			}
			String preSysBoSettingNo = setAndGetDataMethod.getPreNo();//获取分录的流水号前缀
			String sysBoSettingNo = GuidAgent.getStringGuid(preSysBoSettingNo);
			sysBoSettingL.setSysBoSettingNo(sysBoSettingNo);
			sysBoSettingL.setIsActive("Y");
			sysBoSettingL.setCustId(custId);
			sysBoSettingL.setCreatedby(operCustId);
			//新增对象添加
			addSysBoSettingList.add(sysBoSettingL);
			
			//把禁止服务的添加到上传redis集合中
			if("1".equals(sysBoSettingL.getSettingValue()))
			{
				BusinessCustParamItemsRedis custParamItemsRedis = new BusinessCustParamItemsRedis();
				custParamItemsRedis.setCustId(sysBoSettingL.getCustId());
	        	custParamItemsRedis.setSysItemsKey(sysBoSettingL.getSettingName());
	        	custParamItemsRedis.setSysItemsValue(sysBoSettingL.getSettingValue());
	        	custParamItemsRedis.setSettingRemark(sysBoSettingL.getSettingRemark());
	        	custParamItemsMap.put(sysBoSettingL.getSettingName(), custParamItemsRedis);
			}
			else
			{
				custParamItemsMap.remove(sysBoSettingL.getSettingName());
			}
			
			/*else if(sysBoSettingM == null)
			{
				String preSysBoSettingNo = setAndGetDataMethod.getPreNo();//获取分录的流水号前缀
				String sysBoSettingNo = GuidAgent.getStringGuid(preSysBoSettingNo);
				sysBoSettingL.setSysBoSettingNo(sysBoSettingNo);
				sysBoSettingL.setIsActive("Y");
				sysBoSettingL.setCustId(custId);
				sysBoSettingL.setCreatedby(operCustId);
				//新增对象添加
				addSysBoSettingList.add(sysBoSettingL);
				
				//把禁止服务的添加到上传redis集合中
				BusinessCustParamItemsRedis custParamItemsRedis = new BusinessCustParamItemsRedis();
				custParamItemsRedis.setCustId(sysBoSettingL.getCustId());
	        	custParamItemsRedis.setSysItemsKey(sysBoSettingL.getSettingName());
	        	custParamItemsRedis.setSysItemsValue(sysBoSettingL.getSettingValue());
	        	custParamItemsRedis.setSettingRemark(sysBoSettingL.getSettingRemark());
	        	custParamItemsMap.put(sysBoSettingL.getSettingName(), custParamItemsRedis);
			}*/
		}
		
		try {
			
			//批量更新业务操作许可设置
			if(updateSysBoSettingList.size()>0)
			{
				businessSysBoSettingMapper.updateSysBoSettingList(updateSysBoSettingList);
			}
			
			//批量新增业务操作许可设置
			if(addSysBoSettingList.size()>0)
			{
				businessSysBoSettingMapper.addSysBoSettingList(addSysBoSettingList);
			}
		} catch (Exception e) {
			SystemLog.debug("HSXT_BP", "方法：searchSysBoSettingList", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
		}
		
		String objectJson = JSON.toJSONString(custParamItemsMap);
        fixRedisUtil.add(BPConstants.SYS_NAME, custId, objectJson);
		
	}

	/**
     * 查询多个业务操作许可
     * @param BusinessSysBoSetting 业务操作许可对象
     * @return List<BusinessSysBoSetting> 业务操作许可对象集合
     * @throws HsException
     */
	@Override
	public Map<String, BusinessSysBoSetting> searchSysBoSettingList(
			BusinessSysBoSetting sysBoSetting) throws HsException {
		
		String custId = sysBoSetting.getCustId();
		//校验客户ID是否为空
		if(custId == null || custId.length() == 0)
		{
			SystemLog.debug("HSXT_BP", "方法：setSysBoSettingList", RespCode.BP_PARAMETER_NULL.getCode() + "custId = "
                    + custId + ":客户全局编号为空");
            throw new HsException(RespCode.BP_PARAMETER_NULL.getCode(), "custId = " + custId + ":客户全局编号为空");
		}
		
		Map<String, BusinessSysBoSetting> sysBoSettingMap = new HashMap<String, BusinessSysBoSetting>();
		try {
			List<BusinessSysBoSetting> sysBoSettingList = businessSysBoSettingMapper.searchSysBoSettingList(sysBoSetting);
			for(int i=0; i<sysBoSettingList.size(); i++){
				BusinessSysBoSetting sysBoSettingl = sysBoSettingList.get(i);
				sysBoSettingMap.put(sysBoSettingl.getSettingName(), sysBoSettingl);
			}
		} catch (Exception e) {
			SystemLog.debug("HSXT_BP", "方法：searchSysBoSettingList", RespCode.BP_SQL_ERROR.getCode() + e.getMessage());
            throw new HsException(RespCode.BP_SQL_ERROR.getCode(), e.getMessage());
		}
		return sysBoSettingMap;
	}

    

}
