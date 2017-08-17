package com.gy.hsxt.uc.device.mapper;

import com.gy.hsxt.uc.device.bean.PadDevice;

/**
 * 平板设备 Mapper
 * 
 * @Package: com.gy.hsxt.uc.device.mapper
 * @ClassName: PadDeviceMapper
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-10 下午5:37:11
 * @version V1.0
 */
public interface PadDeviceMapper {
	/**
	 * 删除平板设备
	 * 
	 * @param deviceCustId
	 *            设备客户号
	 * @return
	 */
	int deleteByPrimaryKey(String deviceCustId);

	/**
	 * 插入平板设备
	 * 
	 * @param record
	 *            平板设备对象
	 * @return
	 */
	int insert(PadDevice record);

	/**
	 * 插入平板设备（有值插入）
	 * 
	 * @param record
	 *            平板设备对象
	 * @return
	 */
	int insertSelective(PadDevice record);

	/**
	 * 查询平板设备 通过设备客户号（主键）
	 * 
	 * @param deviceCustId
	 *            设备客户号
	 * @return
	 */
	PadDevice selectByPrimaryKey(String deviceCustId);

	/**
	 * 更新平板设备 （只更新有的属性）
	 * 
	 * @param record
	 *            平板设备对象
	 * @return
	 */
	int updateByPrimaryKeySelective(PadDevice record);

	/**
	 * 更新平板设备
	 * 
	 * @param record
	 *            平板设备对象
	 * @return
	 */
	int updateByPrimaryKey(PadDevice record);

	/**
	 * 查询平板设备 通过设备终端编号
	 * 
	 * @param entResNo
	 *            企业互生号
	 * @param deviceNo
	 *            设备终端编号 (平台自己定义 4为数字)
	 * @return
	 */
	PadDevice findPadByDeviceNo(String entResNo, String deviceNo);

	/**
	 * 更新平板信息 通过设备终端编号
	 * 
	 * @param record
	 *            平板设备对象
	 * @return
	 */
	int updateByDeviceNoSelective(PadDevice record);

}