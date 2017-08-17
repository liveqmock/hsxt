package com.gy.hsxt.uc.device.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.uc.device.bean.Device;

/**
 * 设备（pos、pad、cardreader） Mapper 类
 * 
 * @Package: com.gy.hsxt.uc.device.mapper
 * @ClassName: DeviceMapper
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-10 下午5:27:17
 * @version V1.0
 */
public interface DeviceMapper {

	/**
	 * 分页查询设备列表
	 * 
	 * @param entResNo
	 *            企业客户号（必填 ，不能为空）
	 * @param deviceType
	 *            设备类型 1：POS 2：刷卡器 3：平板
	 * @param deviceNo
	 *            设备终端编号 （平台定义、4位数字）
	 * @param start
	 *            查询开始位置
	 * @param end
	 *            查询结束位置
	 * @return
	 */
	List<Device> listDevice(@Param("entResNo") String entResNo,
			@Param("deviceType") Integer deviceType,
			@Param("deviceNo") String deviceNo, @Param("start") int start,
			@Param("end") int end);

	/**
	 * 统计设备总数
	 * 
	 * @param entResNo
	 *            企业客户号（必填 ，不能为空）
	 * @param deviceType
	 *            设备类型 1：POS 2：刷卡器 3：平板
	 * @param deviceNo
	 *            设备终端编号 （平台定义、4位数字）
	 * @return
	 */
	int countDevice(@Param("entResNo") String entResNo,
			@Param("deviceType") Integer deviceType,
			@Param("deviceNo") String deviceNo);

	/**
	 * 查询设备信息通过设备终端编号
	 * 
	 * @param entResNo
	 *            企业客户号（必填 ，不能为空）
	 * @param deviceType
	 *            设备类型 1：POS 2：刷卡器 3：平板
	 * @param deviceNo
	 *            设备终端编号 （平台定义、4位数字）
	 * @return
	 */
	Device findDeviceByDeviceNo(@Param("entResNo") String entResNo,
			@Param("deviceType") Integer deviceType,
			@Param("deviceNo") String deviceNo);

}