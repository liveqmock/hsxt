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

package com.gy.hsxt.bp.api;

import java.util.Map;

import com.gy.hsxt.bp.bean.BusinessAgreeFeeRedis;
import com.gy.hsxt.bp.bean.BusinessCustParamItemsRedis;
import com.gy.hsxt.bp.bean.BusinessSysParamItemsRedis;
import com.gy.hsxt.common.exception.HsException;

/** 
 * 参数获取服务接口
 * @Package: com.gy.hsxt.bp.api  
 * @ClassName: IBusinessParamSearchService 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2015-11-20 下午4:08:58 
 * @version V1.0 
 

 */
public interface IBusinessParamSearchService {

    /**
     * 获取同一组系统参数项接口
     * @param sysGroupCode 系统参数组编号
     * @return Map<String,BusinessSysParamItems> 同一组系统参数项Map集合
     * @throws HsException
     */
    public Map<String,BusinessSysParamItemsRedis> searchSysParamItemsByGroup(String sysGroupCode) throws HsException;

    /**
     * 获取单个系统参数项接口
     * @param sysGroupCode 系统参数组编号
     * @param sysItemsKey 系统参数项键名
     * @return BusinessSysParamItems 系统参数项对象
     * @throws HsException
     */
    public BusinessSysParamItemsRedis searchSysParamItemsByCodeKey(String sysGroupCode, String sysItemsKey) throws HsException;


    /**
     * 获取同一协议的协议费用接口
     * @param agreeCode 协议代码
     * @return Map<String,BusinessAgreeFee> 同一协议的协议费用Map集合
     * @throws HsException
     */
    public Map<String,BusinessAgreeFeeRedis> searchBusinessAgreeFee(String agreeCode) throws HsException;


    /**
     * 获取单个协议费用接口
     * @param agreeCode 协议代码
     * @param agreeFeeCode 协议费用编号
     * @return BusinessAgreeFee 协议费用对象
     * @throws HsException
     */
    public BusinessAgreeFeeRedis searchBusinessAgreeFeeByCode(String agreeCode, String agreeFeeCode) throws HsException;

    /**
     * 获取同一客户业务参数接口
     * @param custId 客户全局编号
     * @return Map<String,BusinessCustParamItems> 同一客户业务参数Map集合
     * @throws HsException
     */
    public Map<String,BusinessCustParamItemsRedis> searchCustParamItemsByGroup(String custId) throws HsException;


    /**
     * 获取单个的客户业务参数接口
     * @param custId  客户全局编号  
     * @param sysItemsKey 系统参数项键名
     * @return BusinessCustParamItems 客户业务参数对象
     * @throws HsException
     */
    public BusinessCustParamItemsRedis searchCustParamItemsByIdKey(String custId,String sysItemsKey) throws HsException;

    /**
     * 同步业务参数系统数据到Redis服务器中的接口
     * @throws HsException
     */
    public void initDataToRedis() throws HsException;

}
