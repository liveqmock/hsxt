package com.gy.hsxt.uc.sysoper.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.uc.as.bean.sysoper.AsSysOper;
import com.gy.hsxt.uc.sysoper.bean.AsQuerySysCondition;
import com.gy.hsxt.uc.sysoper.bean.SysOperator;

public interface SysOperatorMapper {
	/**
	 *  插入系统用户信息（有值的插入）
	 * @param record	系统用户信息
	 * @return
	 */
    int insertSelective(SysOperator record);

    /**
     * 通过系统用户的客户号查询系统用户信息
     * @param operCustId	系统用户客户号
     * @return	系统用户信息
     */
    SysOperator selectByPrimaryKey(String operCustId);
    
    /**
     * 通过系统用户的账号查询系统用户的客户号
     * @param userName	系统用户的账号
     * @param isactive  标记此条记录的状态Y:可用 N:不可用
     * @return
     */
    String selectByUserName(@Param("userName")String userName,@Param("isactive")String isactive);
   
    /**
     * 通过系统用户2的账号查询系统用户的客户号
     * @param userName	系统用户2的账号
     * @param isactive  标记此条记录的状态Y:可用 N:不可用
     * @return
     */
    String selectOperCustIdSecondByUserName(@Param("userName")String userName,@Param("isactive")String isactive);
    
    /**
     * 更新系统用户信息（有值更新）
     * @param record	待更新的系统用户信息
     * @return
     */
    int updateByPrimaryKeySelective(SysOperator record);
    
    /**
     * 系统用户查询
     * @param record
     * @return 查询结果列表
     */
    List<SysOperator> selectOperList(SysOperator record);

    
    /**
     * 更新第2个密码
     * @param operator
     * @return
     */
    int updateSecondPwdByPrimaryKeySelective(SysOperator operator);
    
    /**
     * 根据客户号查找第2个密码
     * @param custId
     * @return
     */
    SysOperator selectOperatorSecondPwdByCustId(String custId);
    
    /**
     * 添加第2个密码
     * @param operator
     * @return
     */
    int insertSecondPwdSelective(SysOperator operator);
    
    
    List<AsSysOper> listSysOperAndChecker(@Param("condition") AsQuerySysCondition condition,
			@Param("page") Page page);
}