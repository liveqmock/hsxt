package com.gy.hsxt.uc.device.mapper;

import java.util.List;

import com.gy.hsxt.uc.device.bean.PosDevice;

/**
 * POS机设备 Mapper对象
 * 
 * @Package: com.gy.hsxt.uc.device.mapper
 * @ClassName: PosDeviceMapper
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-10 下午5:52:19
 * @version V1.0
 */
public interface PosDeviceMapper {

	/**
	 * 删除POS设备
	 * 
	 * @param deviceCustId
	 *            设备客户号
	 * @return
	 */
	int deleteByPrimaryKey(String deviceCustId);

	/**
	 * 插入POS设备
	 * 
	 * @param record
	 *            POS设备对象
	 * @return
	 */
	int insert(PosDevice record);

	/**
	 * 插入POS设备（有值插入）
	 * 
	 * @param record
	 *            POS设备对象
	 * @return
	 */
	int insertSelective(PosDevice record);

	/**
	 * 查询POS设备 通过设备客户号（主键）
	 * 
	 * @param deviceCustId
	 *            设备客户号
	 * @return
	 */
	PosDevice selectByPrimaryKey(String deviceCustId);

	/**
	 * 更新POS设备 （只更新有的属性）
	 * 
	 * @param record
	 *            POS设备对象
	 * @return
	 */
	int updateByPrimaryKeySelective(PosDevice record);

	/**
	 * 更新POS设备
	 * 
	 * @param record
	 *            POS设备对象
	 * @return
	 */
	int updateByPrimaryKey(PosDevice record);

	/**
	 * 查询POS设备 通过设备终端编号
	 * 
	 * @param entResNo
	 *            企业互生号
	 * @param deviceNo
	 *            设备终端编号 (平台自己定义 4为数字)
	 * @return
	 */
	PosDevice findPosByDeviceNo(String entResNo, String deviceNo);
	
	/**
	 * 查询POS设备 通过设备终端编号
	 * 
	 * @param mechineNo
	 *            机器号
	 * @return
	 */
	PosDevice findByMachineNo(String machineNo);
	
	/**
	 * 更新POS信息 通过设备终端编号
	 * 
	 * @param record
	 *            POS设备对象
	 * @return
	 */
	int updateByDeviceNoSelective(PosDevice record);
	
	/**
	 * 根据PMK修改POS状态
	 * @param pos
	 */
	void updateStatusByPmk(PosDevice pos);
	
	/**
	 * 根据企业客户号查询企业下的所有POS设备
	 * @param pos
	 */
	List<PosDevice> listAllPosDevice(String entCustId);

}