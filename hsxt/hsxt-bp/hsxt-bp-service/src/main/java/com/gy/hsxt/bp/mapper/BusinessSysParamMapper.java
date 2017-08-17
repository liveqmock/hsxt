

package com.gy.hsxt.bp.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.bp.bean.BusinessSysParamGroup;
import com.gy.hsxt.bp.bean.BusinessSysParamItems;
import com.gy.hsxt.common.exception.HsException;


/**
 *  系统业务参数DAO映射类
 *  @Project Name    : hsxt-bp-service
 *  @Package Name    : com.gy.hsxt.bp.mapper
 *  @File Name       : SysParamMapper.java
 *  @Author          : weixz
 *  @Description     : TODO
 *  @Creation Date   : 2015-11-20
 *  @version V1.0
 * 
 */
public interface BusinessSysParamMapper {

    /**
     * 新增系统业务参数组
     * @param sysParamGroup 系统业务参数组对象
     * @throws SQLException
     */
    public void addSysParamGroup(BusinessSysParamGroup sysParamGroup) throws SQLException;

    /**
     * 更新系统业务参数组
     * @param sysParamGroup 系统业务参数组对象
     * @throws SQLException
     */
    public void updateSysParamGroup(BusinessSysParamGroup sysParamGroup) throws SQLException;

    /**
     * 删除系统业务参数组
     * @param sysGroupCode 系统参数组编号
     * @throws SQLException
     */
    public void deleteSysParamGroup(String sysGroupCode) throws SQLException;
    
    /**
     * 查询单个系统业务参数组
     * @param sysGroupCode 系统参数组编号
     * @throws SQLException
     */
    public BusinessSysParamGroup getSysParamGroupByCode(String sysGroupCode) throws SQLException;
    
    /**
     * 查询多个系统业务参数组
     * @param sysParamGroup 系统业务参数组对象
     * @return List<SysParamGroup> 封裝系统业务参数组对象集合
     * @throws SQLException
     */
    public List<BusinessSysParamGroup> searchSysParamGroupListPage(BusinessSysParamGroup sysParamGroup) throws SQLException;
    
    /**
     * 查询所有的系统参数组
     * @return List<BusinessSysPrramGroup> 系统参数组集合
     * @throws HsException
     */
    public List<BusinessSysParamGroup> searchSysParamGroupList() throws SQLException;
    
    /**
     * 新增系统业务参数项
     * @param BusinessSysParamItems 系统业务参数项对象
     * @throws SQLException
     */
    public void addSysParamItems(BusinessSysParamItems sysParamItems) throws SQLException;
    
    /**
     * 更新系统业务参数项
     * @param BusinessSysParamItems 系统业务参数项对象
     * @throws SQLException
     */
    public void updateSysParamItems(BusinessSysParamItems sysParamItems) throws SQLException;
    
    /**
     * 删除系统业务参数项
     * @param BusinessSysParamItems 系统参数项编号
     * @throws SQLException
     */
    public void deleteSysParamItems(String sysItemsCode) throws SQLException;
    
    /**
     * 查询单个系统业务参数项
     * @param sysItemsCode 系统参数项编号
     * @throws SQLException
     */
    public BusinessSysParamItems getSysParamItemsByCode(String sysItemsCode) throws SQLException;
    
    /**
     * 查询单个系统业务参数项
     * @param sysItemsKey 系统参数项键
     * @throws SQLException
     */
    public BusinessSysParamItems getSysParamItemsByKey(String sysItemsKey) throws SQLException;
    
    
    /**
     * 查询多个系统业务参数项
     * @param BusinessSysParamItems 系统业务参数项对象
     * @param page 分頁對象
     * @return List<SysParamItems> 封裝系统业务参数项对象集合
     * @throws SQLException
     */
    public List<BusinessSysParamItems> searchSysParamItemsListPage(BusinessSysParamItems sysParamItems) throws SQLException;
    
    /**
     * 通过系统参数组编码查询该编码对应下的所有参数项
     * @param sysGroupCode 系统参数组编码
     * @return List<BusinessSysParamItems> 系统业务参数项对象集合
     * @throws HsException
     */
    public List<BusinessSysParamItems> searchSysParamItemsList(String sysGroupCode) throws SQLException;
}
