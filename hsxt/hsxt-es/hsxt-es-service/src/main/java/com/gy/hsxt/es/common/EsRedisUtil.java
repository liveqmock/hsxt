/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.es.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.bp.bean.BusinessSysParamItemsRedis;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;

/**
 * @Package :com.gy.hsxt.ps.common
 * @ClassName : EsRedisUtil
 * @Description : 查询缓存
 * @Author : Martin.Cubbon
 * @Date : 1/28 0028 10:20
 * @Version V3.0.0.0
 */
@Service
public class EsRedisUtil {

    // 企业税率查询KEY
    private static String TAXRATECACHEKEY = "UC.E_RES_TAXRATE";

    // 根据资源号查CUSTID的KEY
    private static String CUSTIDCACHEKEY = "UC.E_RES_CUS_";

    @Autowired
    private RedisUtil fixRedisUtil;

    @Autowired
    private LcsClient lcsClient;

    @Autowired
    private IUCAsEntService uCAsEntService;

    // 业务参数系统配置服务
    @Autowired
    private BusinessParamSearch businessParamSearch;

    private static EsRedisUtil esRedisUtil;

    @PostConstruct
    public void init() {
        if (esRedisUtil == null) {
            esRedisUtil = this;
        }
    }

    /**
     * 获取地区平台信息
     * @return LocalInfo
     */
    public static LocalInfo getLocalInfo() throws HsException {
        return esRedisUtil.lcsClient.getLocalInfo();
    }

    /**
     * 根据资源号批量查询客户ID
     *
     * @param entResNoList 资源号
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Map<String, String> getCustIdMap(List<String> entResNoList) throws HsException {

        List<Object> entResNoListNew = new ArrayList<>();
        entResNoListNew.addAll(entResNoList);
        //去除重复的数据
        removeDuplicate(entResNoListNew);
        // 记录查到的企业资源号
        List<Object> findResNo = new ArrayList<>();
        // 返回的map
        Map<String, String> map = new HashMap<>();
        // 查询缓存
        List<Object> entCustIdList = esRedisUtil.fixRedisUtil.redisTemplate.opsForHash()
                .multiGet(CUSTIDCACHEKEY, entResNoListNew);
        if (!CollectionUtils.isEmpty(entCustIdList)) {
            // 循环，把查出来的企业记录起来
            for (Object custId : entCustIdList) {
                if (!EsTools.isEmpty(custId)) {
                    String custIdString = custId.toString();
                    String entResNo = custIdString.substring(0, 11);
                    // 放到list记录下来
                    findResNo.add(entResNo);
                    // 查询结果放入map中
                    map.put(entResNo, custIdString);
                }
            }
            // 去除已查到的企业
            entResNoListNew.removeAll(findResNo);
            // 类型转换
            List<String> notFindResNo = (List) entResNoListNew;

            // 缓存中没有查到，dubbo接口查询
            // 判断是否有没有查到的企业
            if (!CollectionUtils.isEmpty(notFindResNo)) {
                // 调用DUBBO接口
                Map<String, String> mapDubbo = esRedisUtil.uCAsEntService
                        .findEntCustIdByEntResNo(notFindResNo);
                // 判断是否查询到，有值放到返回的map中
                if (!CollectionUtils.isEmpty(mapDubbo)) {
                    map.putAll(mapDubbo);
                }
            }
        }
        // 返回结果
        return map;
    }

    /**
     * 根据互生号批量查询客户ID
     *
     * @param entResNoList 互生号
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List<Object> getCustIdList(List<String> entResNoList) throws HsException {

        List<Object> entResNoListNew = new ArrayList<>();
        entResNoListNew.addAll(entResNoList);
        //去除重复的数据
        removeDuplicate(entResNoListNew);
        // 记录查到的企业资源号
        List<Object> findResNo = new ArrayList<>();
        // 记录查到的企业客户号
        List<Object> findCustId = new ArrayList<>();
        // 查询缓存
        List<Object> entCustIdList = esRedisUtil.fixRedisUtil.redisTemplate.opsForHash()
                .multiGet(CUSTIDCACHEKEY, entResNoListNew);
        if (!CollectionUtils.isEmpty(entCustIdList)) {
            // 循环，把查出来的企业记录起来
            for (Object custId : entCustIdList) {
                String custIdString = String.valueOf(custId);
                if ("".equals(custIdString) && "null".equals(custIdString)) {
                    String entResNo = custId.toString().substring(0, 11);
                    // 放到list记录下来
                    findResNo.add(entResNo);
                    // 放到list记录下来
                    findCustId.add(custIdString);
                }
            }
            // 去除已查到的企业
            entResNoListNew.removeAll(findResNo);
            // 类型转换
            List<String> notFindResNo = (List) entResNoListNew;
            // 缓存中没有查到，有dubbo接口查询
            // 判断是否有没有查到的企业
            if (!CollectionUtils.isEmpty(notFindResNo)) {
                try {
                    // 调用DUBBO接口
                    Map<String, String> mapDubbo = esRedisUtil.uCAsEntService
                            .findEntCustIdByEntResNo(notFindResNo);
                    // 判断是否查询到，有值放到返回的map中
                    if (!CollectionUtils.isEmpty(mapDubbo)) {
                        for (String md : mapDubbo.values()) {
                            findCustId.add(md);
                        }
                    }
                } catch (HsException e) {
                    // 否则抛出互生异常
                    new EsException(new Throwable().getStackTrace()[0],
                            RespCode.PS_UC_ERROR.getCode(), e.getMessage());
                }
            }
        }
        // 返回结果
        return findCustId;
    }

    /**
     * 根据资源号批量查询企业税率
     *
     * @param entResNoList 资源号
     */
    public static Map<String, String> getEntTaxMap(List<String> entResNoList) throws HsException {

        List<Object> entResNoListNew = new ArrayList<>();
        entResNoListNew.addAll(entResNoList);
        //去除重复的数据
        removeDuplicate(entResNoListNew);
        // 记录没有查到的录
        List<Object> notfind = new ArrayList<>();
        // 返回的map
        Map<String, String> map = new HashMap<>();

        // 查询缓存
        List entTaxList = esRedisUtil.fixRedisUtil.redisTemplate.opsForHash()
                .multiGet(TAXRATECACHEKEY, entResNoListNew);
        if (!CollectionUtils.isEmpty(entTaxList)) {
            // 循环，把查出来的企业记录起来
            for (int i = 0; i < entTaxList.size(); i++) {
                String tax = String.valueOf(entTaxList.get(i));
                if ("".equals(tax) && "null".equals(tax)) {
                    map.put(entResNoListNew.get(i).toString(), entTaxList.get(i).toString());
                } else {
                    notfind.add(entResNoListNew.get(i));
                }
            }
            // 类型转换
            List<String> notFindResNo = (List) notfind;
            // 缓存中没有查到，有dubbo接口查询
            // 判断是否有没有查到的企业
            if (!CollectionUtils.isEmpty(notFindResNo)) {
                try {
                    // 调用DUBBO接口
                    Map<String, String> mapDubbo = esRedisUtil.uCAsEntService
                            .listEntTaxRate(notFindResNo);
                    // 判断是否查询到，有值放到返回的map中
                    if (!CollectionUtils.isEmpty(mapDubbo)) {
                        map.putAll(mapDubbo);
                    }
                } catch (HsException e) {
                    // 否则抛出互生异常
                   EsException.esThrowException(new Throwable().getStackTrace()[0],
                            RespCode.PS_UC_ERROR.getCode(), e.getMessage(), e);
                }
            }
            // 没有查到税率的资源号，直接返回“0”
            /*
             * for (String resno:notFindResNo) { if (!map.containsKey(resno)) {
			 * map.put(resno,"0"); } }
			 */
        }
        // 返回结果
        return map;
    }


    /**去除重复和空
     */
    private static void removeDuplicate(List list) {
        List<Object> removeList=new ArrayList<>();
        Set set = new HashSet(list);
        list.clear();
        list.addAll(set);
        for (Object listOld:list) {
            String element =ObjectUtils.identityToString(listOld);
            if (StringUtils.isEmpty(element)) {
                removeList.add(listOld);
            }
        }
        list.removeAll(removeList);
    }


    /**
     * 商业服务费比例
     * @return 商业服务费比例
     */
    public static BigDecimal getBusinessServiceRate() throws HsException {
        BusinessSysParamItemsRedis businessSysParamItemsRedis = esRedisUtil.businessParamSearch.searchSysParamItemsByCodeKey(BusinessParam.CONFIG_PARA.getCode(), BusinessParam.HS_BUSINESS_SERVICE_RATE.getCode());
        EsAssert.notNull(businessSysParamItemsRedis, RespCode.PS_PARAM_ERROR, "商业服务费对象为空！");
        EsAssert.notNull(businessSysParamItemsRedis.getSysItemsValue(), RespCode.PS_PARAM_ERROR, "商业服务费比例为空！");
        //商业服务费比例
        BigDecimal hBusinessServiceRate = new BigDecimal(businessSysParamItemsRedis.getSysItemsValue());
        return  hBusinessServiceRate;
    }
    
    /**
     * @查询平台信息
     * @throws HsException
     */
    public static Pair<String,String> getPlatInfo() throws HsException {
        List<String> entResNoList=new ArrayList<>();

        String platNo=getLocalInfo().getPlatResNo();
        entResNoList.add(platNo);
        Map<String,String> map=getCustIdMap(entResNoList);
        EsAssert.collectionNotNull(map, RespCode.PS_PARAM_ERROR, "缓存中没有查到平台custId");

        String platCustId= Objects.toString(map.get(platNo),"");
        EsAssert.notNull(platCustId, RespCode.PS_PARAM_ERROR, "缓存中没有查到平台custId");
        return Pair.of(platCustId,platNo);
    }
}
