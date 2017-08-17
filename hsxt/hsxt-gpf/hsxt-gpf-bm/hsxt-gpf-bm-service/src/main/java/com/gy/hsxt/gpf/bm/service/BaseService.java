package com.gy.hsxt.gpf.bm.service;


import com.gy.hsxt.common.exception.HsException;

/**
 * 基础Service 接口
 * @category          基础Service 接口
 * @projectName   apply-incurement
 * @package           com.gy.apply.admin.increment.service.BaseService.java
 * @className       BaseService
 * @description     基础Service 接口
 * @author              zhucy
 * @createDate       2014-6-19 上午8:47:22  
 * @updateUser      zhucy
 * @updateDate      2014-6-19 上午8:47:22
 * @updateRemark    新增
 * @version              v0.0.1
 */
public interface BaseService<T> {
	/**
	 * 保存数据
	 * @param key 键
	 * @param value 值
	 */
	void save(String key, T value) throws HsException;
	
	/**
	 * 根据key 获取value
	 * @param key 键
	 * @return 值
	 */
	T getValueByKey(String key) throws HsException;

}

	