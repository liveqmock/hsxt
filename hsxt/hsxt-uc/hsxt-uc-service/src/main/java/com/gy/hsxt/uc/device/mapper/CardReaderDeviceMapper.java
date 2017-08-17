package com.gy.hsxt.uc.device.mapper;

import java.util.List;

import com.gy.hsxt.uc.device.bean.CardReaderDevice;
import com.gy.hsxt.uc.device.bean.PosDevice;

/**
 * 
 * 刷卡器设备 Mapper类
 * 
 * @Package: com.gy.hsxt.uc.device.mapper
 * @ClassName: CardReaderDeviceMapper
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-10 下午5:20:22
 * @version V1.0
 */
public interface CardReaderDeviceMapper {
	/**
	 * 删除刷卡器
	 * 
	 * @param deviceCustId
	 *            刷卡器设备客户号
	 * @return
	 */
	int deleteByPrimaryKey(String deviceCustId);

	/**
	 * 添加刷卡器设备
	 * 
	 * @param record
	 *            刷卡器对象
	 * @return
	 */
	int insert(CardReaderDevice record);

	/**
	 * 添加刷卡器设备（有值的插入）
	 * 
	 * @param record
	 *            刷卡器对象
	 * @return
	 */
	int insertSelective(CardReaderDevice record);

	/**
	 * 查询刷卡器设备
	 * 
	 * @param deviceCustId
	 *            刷卡器设备客户号
	 * @return
	 */
	CardReaderDevice selectByPrimaryKey(String deviceCustId);

	/**
	 * 更新刷卡器设备（更新有值的属性）
	 * 
	 * @param record
	 *            刷卡器对象
	 * @return
	 */
	int updateByPrimaryKeySelective(CardReaderDevice record);

	/**
	 * 更新刷卡器设备
	 * 
	 * @param record
	 *            刷卡器对象
	 * @return
	 */
	int updateByPrimaryKey(CardReaderDevice record);

	/**
	 * 查询刷卡器设备
	 * 
	 * @param entResNo
	 *            企业互生号
	 * @param deviceNo
	 *            设备终端编号（平台定义 4位数字编号）
	 * @return
	 */
	CardReaderDevice findCardReaderByDeviceNo(String entResNo, String deviceNo);

	/**
	 * 更新刷卡器设备 通过设备终端编号（更新有值的属性）
	 * 
	 * @param record
	 *            刷卡器对象
	 * @return
	 */
	int updateByDeviceNoSelective(CardReaderDevice record);
	
	
	/**
	 * 根据企业客户号查询企业下的所有刷卡器设备
	 * @param pos
	 */
	List<CardReaderDevice> listAllCardReaderDevice(String entCustId);
}