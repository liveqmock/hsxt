package com.gy.hsxt.uc.common.mapper;

import java.util.List;

import com.gy.hsxt.uc.common.bean.SysBoSetting;

public interface SysBoSettingMapper {
	/**
	 * 删除指定客户所有设置项
	 * 
	 * @param custId 客户号
	 * @return
	 */
	int deleteByCustId(String custId);

	/**
	 * 插入设置项
	 * 
	 * @param record 设置内容
	 * @return
	 */

	int insertSelective(SysBoSetting record);

	/**
	 * 查询指定客户所有设置项
	 * @param custId 客户号
	 * @return
	 */
	List<SysBoSetting> selectByCustId(String custId);

	/**
	 * 修改设置项
	 * @param record 设置内容
	 * @return
	 */
	int updateByPrimaryKeySelective(SysBoSetting record);

}