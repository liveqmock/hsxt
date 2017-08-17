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

package com.gy.hsxt.bp.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.bp.bean.BusinessCustParamItems;

/** 
 * 
 * @Package: com.gy.hsxt.bp.mapper  
 * @ClassName: CustParamItemsMapper 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2015-11-24 上午11:48:59 
 * @version V1.0 
 

 */
public interface BusinessCustParamMapper {

    /**
     * 新增客户业务参数
     * @param BusinessCustParamItems 客户业务参数对象
     * @throws SQLException
     */
    public void addCustParamItems(BusinessCustParamItems businessCustParamItems) throws SQLException;
    
    /**
     * 批量新增客户业务参数
     * @param List<BusinessCustParamItems> 客户业务参数List
     * @throws SQLException
     */
    public void addCustParamItemsList(List<BusinessCustParamItems> custParamItemsList) throws SQLException;
    
    /**
     * 更新客户业务参数
     * @param BusinessCustParamItems 客户业务参数对象
     * @throws SQLException
     */
    public void updateCustParamItems(BusinessCustParamItems businessCustParamItems) throws SQLException;
    
    /**
     * 批量更新客户业务参数
     * @param List<BusinessCustParamItems> 客户业务参数List
     * @throws SQLException
     */
    public void updateCustParamItemsList(List<BusinessCustParamItems> custParamItemsList) throws SQLException;
    
    /**
     * 删除客户业务参数
     * @param custItemsId 客户参数ID
     * @throws SQLException
     */
    public void deleteCustParamItems(String custItemsId) throws SQLException;
    
    /**
     * 查询单个客户业务参数
     * @param custItemsId 客户参数ID
     * @throws SQLException
     */
    public BusinessCustParamItems getCustParamItemsById(String custItemsId) throws SQLException;
    
    /**
     * 查询单个客户业务参数
     * @param custId 客户全局编号
     * @param sysItemsKey 系统参数项键名
     * @throws SQLException
     */
    public BusinessCustParamItems getCustParamItemsByIdAndKey(@Param("custId")String custId,@Param("sysItemsKey")String sysItemsKey) throws SQLException;
    
    
    /**
     * 查询多个客户业务参数
     * @param BusinessCustParamItems 客户业务参数对象
     * @param page 分頁對象
     * @return List<BusinessCustParamItems> 封裝客户业务参数对象集合、當前條件的查詢總記錄數
     * @throws SQLException
     */
    public List<BusinessCustParamItems> searchCustParamItemsListPage (BusinessCustParamItems businessCustParamItems) throws SQLException;
}
