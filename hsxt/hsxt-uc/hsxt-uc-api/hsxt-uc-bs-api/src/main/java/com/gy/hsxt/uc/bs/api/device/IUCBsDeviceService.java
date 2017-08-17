/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.bs.api.device;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.bs.bean.device.BsCardReader;
import com.gy.hsxt.uc.bs.bean.device.BsDevice;
import com.gy.hsxt.uc.bs.bean.device.BsPad;
import com.gy.hsxt.uc.bs.bean.device.BsPos;
import com.gy.hsxt.uc.bs.bean.device.SecretkeyResult;

/**
 * 用户中心提供企业设备管理的接口，包括：平板、POS和刷卡器的批量添加、修改、删除、查询等功能
 * 
 * @Package: com.gy.hsxt.uc.bs.api.device
 * @ClassName: IUCBsDeviceService
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-11 下午3:48:49
 * @version V1.0
 */
public interface IUCBsDeviceService {
	/**
	 * 批量添加Pos机
	 * 
	 * @param list
	 *            pos机列表
	 * @param operator
	 *            操作者客户号
	 * @return
	 * @throws HsException
	 */
	public List<BsPos> batchAddPos(List<BsPos> list, String operator) throws HsException;

	/**
	 * 修改设备状态
	 * 
	 * @param entResNo
	 *            企业互生号
	 * @param deviceType
	 *            设备类型 1：POS 2：刷卡器 3：平板
	 * @param deviceNo
	 *            设备终端编号 （平台定义 4位数字）
	 * @param deviceStatus
	 *            设备状态0未启用、1 启用、2 停用、3 返修、4冻结
	 * @param operator
	 *            操作者客户号
	 * @throws HsException
	 */
	@Transactional
	public void updateDeviceStatus(String entResNo, Integer deviceType, String deviceNo,
			Integer deviceStatus, String operator) throws HsException;

	/**
	 * 批量添加刷卡器(新增)
	 * 
	 * @param list
	 *            刷卡器列表
	 * @param operator
	 *            操作者客户号
	 * @return
	 * @throws HsException
	 */
	public List<BsCardReader> batchAddCardReader(List<BsCardReader> list, String operator)
			throws HsException;

	/**
	 * 调换刷卡器
	 * 
	 * 除了设备终端编号沿用以前的要设备终端编号，其它一律使用新设备的信息
	 * 
	 * @param cardReader
	 *            需要调换刷卡器
	 * @param operator
	 *            操作者客户号
	 * @return
	 * @throws HsException
	 */
	public BsCardReader exchangeCardReader(BsCardReader cardReader, String operator)
			throws HsException;

	/**
	 * 批量添加平板
	 * 
	 * @param list
	 *            平板列表
	 * @param operator
	 *            操作者客户号
	 * @return
	 * @throws HsException
	 */
	public List<BsPad> batchAddPad(List<BsPad> list, String operator) throws HsException;

	/**
	 * 查询pos机信息
	 * 
	 * @param entResNo
	 *            企业互生号 不能为空
	 * @param deviceNo
	 *            设备终端编号（平台自己定义 4位数字）
	 * @return
	 * @throws HsException
	 */
	public BsPos findPosByDeviceNo(String entResNo, String deviceNo) throws HsException;

	/**
	 * 查询pad平板信息
	 * 
	 * @param entResNo
	 *            企业互生号 不能为空
	 * @param deviceNo
	 *            设备终端编号（平台自己定义 4位数字）
	 * @return
	 * @throws HsException
	 */
	public BsPad findPadByDeviceNo(String entResNo, String deviceNo) throws HsException;

	/**
	 * 查询刷卡器信息
	 * 
	 * @param entResNo
	 *            企业互生号 不能为空
	 * @param deviceNo
	 *            设备终端编号（平台自己定义 4位数字）
	 * @return
	 * @throws HsException
	 */
	public BsCardReader findCardReaderByDeviceNo(String entResNo, String deviceNo)
			throws HsException;

	/**
	 * 查询设备列表
	 * 
	 * @param entResNo
	 *            企业互生号 不能为空 必填
	 * @param deviceType
	 *            设备类型枚举 1：POS 2：刷卡器 3：平板
	 * @param deviceNo
	 *            设备终端编号（平台自己定义 4位数字）
	 * @param currentPage
	 *            当前页码
	 * @param pageCount
	 *            每页显示的数量
	 * @return
	 * @throws HsException
	 */
	public PageData<BsDevice> listBsDevice(String entResNo, Integer deviceType, String deviceNo,
			int currentPage, int pageCount) throws HsException;

	/**
	 * 修改设备积分比例
	 * 
	 * @param entResNo
	 *            企业互生号 不能为空
	 * @param deviceType
	 *            设备类型枚举 1：POS 2：刷卡器 3：平板
	 * @param deviceNo
	 *            设备终端编号（平台自己定义 4位数字）
	 * @param pointRate
	 *            积分比例
	 * @param operator
	 *            操作者客户号
	 * @throws HsException
	 */
	public void updatePointRate(String entResNo, Integer deviceType, String deviceNo,
			String[] pointRate, String operator) throws HsException;

	/**
	 * 修改企业积分比例
	 * 
	 * @param entResNo
	 *            企业互生号 不能为空
	 * @param pointRate
	 *            积分比例
	 * @param operator
	 *            操作者客户号
	 * @throws HsException
	 */
	public void updateEntPointRate(String entResNo, String[] pointRate, String operator)
			throws HsException;

	/**
	 * 查询企业积分比例
	 * 
	 * @param entResNo
	 * @return
	 * @throws HsException
	 */
	public String[] findEntPointRate(String entResNo) throws HsException;

	/**
	 * 修改设备状态
	 * 
	 * @param pmk
	 *            pmk
	 * @param deviceStatus
	 *            设备状态枚举 设备状态0未启用、1 启用、2 停用、3 返修、4冻结
	 * @param operator
	 *            操作者客户号
	 * @return
	 * @throws HsException
	 */
	public void updatePosStatus(String pmk, Integer deviceStatus, String operator)
			throws HsException;

	/**
	 * 创建POS机设备秘钥
	 * 
	 * 创建pos机设备秘钥时需要插入pos机设备信息、积分比例信息入库
	 * 
	 * @param entResNo
	 *            企业互生号 不能为空 企业客户号
	 * @param deviceNo
	 *            设备终端编号（平台自己定义 4位数字） 终端编号
	 * @param machineNo
	 *            机器码（厂家自己定义）
	 * @param isNewVersion
	 *            是否新版本
	 * @param operCustId
	 *            操作者客户号
	 * @return
	 * @throws HsException
	 */
	public SecretkeyResult createPosPMK(String entResNo, String deviceNo, String machineNo,
			boolean isNewVersion, String operCustId) throws HsException;

	/**
	 * 重新创建POS机设备秘钥
	 * 
	 * @param entResNo
	 *            企业互生号 不能为空 企业客户号
	 * @param deviceNo
	 *            设备终端编号（平台自己定义 4位数字） 终端编号
	 * @param isNewVersion
	 *            是否新版本
	 * @param operCustId
	 *            操作者客户号
	 * @return
	 * @throws HsException
	 */
	public SecretkeyResult reCreatePosPMK(String entResNo, String deviceNo, boolean isNewVersion,
			String operCustId, String newDeviceSeqNo) throws HsException;

	/**
	 * 创建平板Pad设备秘钥
	 * 
	 * 创建平板Pad设备秘钥时需要插入平板Pad设备信息、积分比例信息入库
	 * 
	 * @param entResNo
	 *            企业互生号 不能为空 企业客户号
	 * @param deviceNo
	 *            设备终端编号（平台自己定义 4位数字） 终端编号
	 * @param machineNo
	 *            机器码（厂家自己定义）
	 * @param isNewVersion
	 *            是否新版本
	 * @param operCustId
	 *            操作者客户号
	 * @return
	 * @throws HsException
	 */
	public SecretkeyResult createPadPMK(String entResNo, String deviceNo, String machineNo,
			boolean isNewVersion, String operCustId) throws HsException;

	/**
	 * 重新创建平板pad设备秘钥
	 * 
	 * @param entResNo
	 *            企业互生号 不能为空 企业客户号
	 * @param deviceNo
	 *            设备终端编号（平台自己定义 4位数字） 终端编号
	 * @param isNewVersion
	 *            是否新版本
	 * @param operCustId
	 *            操作者客户号
	 * @return
	 * @throws HsException
	 */
	public SecretkeyResult reCreatePadPMK(String entResNo, String deviceNo, boolean isNewVersion,
			String operCustId) throws HsException;

	/**
	 * 设置企业积分比例
	 * 
	 * @param entResNo
	 *            企业互生号 不能为空
	 * @param pointRate
	 *            积分比例
	 * @param operator
	 *            操作者客户号
	 * @throws HsException
	 */
	public void setEntPointRate(String entResNo, String[] pointRate, String operator)
			throws HsException;

}
