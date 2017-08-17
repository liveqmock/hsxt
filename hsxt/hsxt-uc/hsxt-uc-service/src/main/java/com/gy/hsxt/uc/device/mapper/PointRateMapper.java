package com.gy.hsxt.uc.device.mapper;

import com.gy.hsxt.uc.device.bean.PointRate;

/**
 * 积分比例 Mapper对象
 * 
 * @Package: com.gy.hsxt.uc.device.mapper
 * @ClassName: PointRateMapper
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-10 下午5:44:48
 * @version V1.0
 */
public interface PointRateMapper {
	/**
	 * 删除积分比例 通过主键
	 * 
	 * @param custId
	 *            企业的积分比例 主键为 企业客户号 ，设备的积分比例主键是设备客户号
	 * @return
	 */
	int deleteByPrimaryKey(String custId);

	/**
	 * 插入积分比例（有值列插入）
	 * 
	 * @param record
	 *            积分比例对象
	 * @return
	 */
	int insertSelective(PointRate record);

	/**
	 * 查询积分比例
	 * 
	 * @param custId
	 *            企业的积分比例 主键为 企业客户号 ，设备的积分比例主键是设备客户号
	 * @return
	 */
	PointRate selectByPrimaryKey(String custId);

	/**
	 * 更新积分比例(有值属性更新)
	 * 
	 * @param record
	 *            积分比例对象
	 * @return
	 */
	int updateByPrimaryKeySelective(PointRate record);

	/**
	 * 更新积分比例 通过设备终端编号
	 * 
	 * @param record
	 *            积分比例对象
	 * @return
	 */
	int updateByDeviceNoSelective(PointRate record);
}