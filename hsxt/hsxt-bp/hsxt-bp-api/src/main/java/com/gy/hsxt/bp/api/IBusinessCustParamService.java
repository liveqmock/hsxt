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

import java.util.List;

import com.gy.hsxt.bp.bean.BusinessCustParamItems;
import com.gy.hsxt.bp.bean.BusinessCustParamItemsRedis;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/** 
 * 客户业务参数接口
 * @Package: com.gy.hsxt.bp.api  
 * @ClassName: IBusinessCustParamService 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2015-11-24 上午11:50:29 
 * @version V1.0 
 

 */
public interface IBusinessCustParamService {
    /**
     * 新增客户业务参数
     * @param BusinessCustParamItems 客户业务参数对象
     * @throws HsException
     */
    public void addCustParamItems(BusinessCustParamItems businessCustParamItems) throws HsException;
    
    /**
     * 批量新增或更新同一客户业务参数
     * @param custId 客户ID
     * @param custParamItemsList 客户业务参数对象List
     * @throws HsException 
     * @see com.gy.hsxt.bp.api.IBusinessCustParamService#addOrUpdateCustParamItemsList(java.lang.String, java.util.List)
     */
    public void addOrUpdateCustParamItemsList(String custId, List<BusinessCustParamItemsRedis> custParamItemsList) throws HsException;
    
    /**
     * 更新客户业务参数
     * @param BusinessCustParamItems 客户业务参数对象
     * @throws HsException
     */
    public void updateCustParamItems(BusinessCustParamItems businessCustParamItems) throws HsException;
    
    /**
     * 删除客户业务参数
     * @param custItemsId 客户参数ID
     * @param custId 客户全局编码
     * @param sysItemsKey 系统参数项键名
     * @throws HsException
     */
    public void deleteCustParamItems(String custItemsId,String custId ,String sysItemsKey) throws HsException;
    
    /**
     * 查询单个客户业务参数
     * @param custItemsId 客户参数ID
     * @throws HsException
     */
    public BusinessCustParamItems getCustParamItemsById(String custItemsId) throws HsException;
    
    /**
     * 查询多个客户业务参数
     * @param BusinessCustParamItems 客户业务参数对象
     * @param page 分頁對象
     * @return PageData<BusinessCustParamItems> 封裝客户业务参数对象集合、當前條件的查詢總記錄數
     * @throws HsException
     */
    public PageData<BusinessCustParamItems> searchCustParamItemsPage (BusinessCustParamItems businessCustParamItems, Page page) throws HsException;
}
