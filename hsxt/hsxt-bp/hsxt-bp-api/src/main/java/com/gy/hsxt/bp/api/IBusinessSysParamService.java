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

import com.gy.hsxt.bp.bean.BusinessSysParamGroup;
import com.gy.hsxt.bp.bean.BusinessSysParamItems;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;


/** 
 * 系统业务参数接口
 * @Package: com.gy.hsxt.bp.api  
 * @ClassName: ISysParamService 
 * @Description: TODO
 * @author: weixz 
 * @date: 2015-11-20 上午11:10:47 
 * @version V1.0 
 */
public interface IBusinessSysParamService{

    /**
     * 新增系统业务参数组
     * @param businessSysParamGroup 系统业务参数组对象
     * @throws HsException
     */
    public void addSysParamGroup(BusinessSysParamGroup businessSysParamGroup) throws HsException;
    
    /**
     * 更新系统业务参数组
     * @param businessSysParamGroup 系统业务参数组对象
     * @throws HsException
     */
    public void updateSysParamGroup(BusinessSysParamGroup businessSysParamGroup) throws HsException;
    
    /**
     * 删除系统业务参数组
     * @param sysGroupCode 系统参数组编号
     * @throws HsException
     */
    public void deleteSysParamGroup(String sysGroupCode) throws HsException;
    
    /**
     * 查询单个系统业务参数组
     * @param sysGroupCode 系统参数组编号
     * @throws HsException
     */
    public BusinessSysParamGroup getSysParamGroupByCode(String sysGroupCode) throws HsException;
    
    /**
     * 查询多个系统业务参数组
     * @param BusinessSysParamGroup 系统业务参数组对象
     * @param page 分頁對象
     * @return PageData<BusinessSysPrramGroup> 封裝系统业务参数组对象集合、當前條件的查詢總記錄數
     * @throws HsException
     */
    public PageData<BusinessSysParamGroup> searchSysParamGroupPage(BusinessSysParamGroup sysParamGroup, Page page) throws HsException;
    
    /**
     * 查询所有的系统参数组
     * @return List<BusinessSysPrramGroup> 系统参数组集合
     * @throws HsException
     */
    public List<BusinessSysParamGroup> searchSysParamGroupList() throws HsException;
    
    
    /**
     * 新增系统业务参数项
     * @param BusinessSysParamItems 系统业务参数项对象
     * @throws HsException
     */
    public void addSysParamItems(BusinessSysParamItems businessSysParamItems) throws HsException;
    
    /**
     * 更新系统业务参数项
     * @param BusinessSysParamItems 系统业务参数项对象
     * @throws HsException
     */
    public void updateSysParamItems(BusinessSysParamItems businessSysParamItems) throws HsException;
    
    /**
     * 删除系统业务参数项
     * @param sysItemsCode 系统参数项编号
     * @param sysGroupCode 系统参数组编号
     * @param sysItemsKey  系统参数项键
     * @throws HsException
     */
    public void deleteSysParamItems(String sysItemsCode,String sysGroupCode,String sysItemsKey) throws HsException;
    
    /**
     * 查询单个系统业务参数项
     * @param sysItemsCode 系统参数项编号
     * @throws HsException
     */
    public BusinessSysParamItems getSysParamItemsByCode(String sysItemsCode) throws HsException;
    
    /**
     * 查询多个系统业务参数项
     * @param BusinessSysParamItems 系统业务参数项对象
     * @param page 分頁對象
     * @return PageData<BusinessSysParamItems> 封裝系统业务参数项对象集合、當前條件的查詢總記錄數
     * @throws HsException
     */
    public PageData<BusinessSysParamItems> searchSysParamItemsPage(BusinessSysParamItems businessSysParamItems, Page page) throws HsException;
    
    /**
     * 通过系统参数组编码查询该编码对应下的所有参数项
     * @param sysGroupCode 系统参数组编码
     * @return List<BusinessSysParamItems> 系统业务参数项对象集合
     * @throws HsException
     */
    public List<BusinessSysParamItems> searchSysParamItemsList(String sysGroupCode) throws HsException;

}
