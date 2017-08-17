package com.gy.hsxt.uc.as.api.device;

import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsPosBaseInfo;
import com.gy.hsxt.uc.as.bean.common.AsPosEnt;
import com.gy.hsxt.uc.as.bean.device.AsCardReader;
import com.gy.hsxt.uc.as.bean.device.AsDevice;
import com.gy.hsxt.uc.as.bean.device.AsPad;
import com.gy.hsxt.uc.as.bean.device.AsPos;
import com.gy.hsxt.uc.as.bean.device.AsResNoInfo;

/**
 * 用户中心提供个接入系统调用的设备服务接口
 * 
 * @Package: com.gy.hsxt.uc.as.api.device
 * @ClassName: IUCASDeviceService
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-10-29 下午2:40:13
 * @version V1.0
 */
public interface IUCAsDeviceService {

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
	public AsPos findPosByDeviceNo(String entResNo, String deviceNo) throws HsException;

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
	public AsPad findPadByDeviceNo(String entResNo, String deviceNo) throws HsException;

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
	public AsCardReader findCardReaderByDeviceNo(String entResNo, String deviceNo)
			throws HsException;

	/**
	 * 查询设备列表
	 * 
	 * @param entResNo
	 *            企业互生号 不能为空
	 * @param deviceType
	 *            设备类型 1：POS 2：刷卡器 3：平板
	 * @param deviceNo
	 *            设备终端编号（平台自己定义 4位数字）
	 * @param currentPage
	 *            当前页
	 * @param pageCount
	 *            每页显示数据数量
	 * @return
	 * @throws HsException
	 */
	public PageData<AsDevice> listAsDevice(String entResNo, Integer deviceType, String deviceNo,
			int currentPage, int pageCount) throws HsException;

	/**
	 * 根据终端编号查询设备客户号
	 * 
	 * @param entResNo
	 *            企业互生号 不能为空 企业互生号
	 * @param deviceType
	 *            设备类型 1：POS 2：刷卡器 3：平板
	 * 
	 * @param deviceNo
	 *            设备终端编号（平台自己定义 4位数字）
	 * 
	 * @return
	 * @throws HsException
	 */
	public String findDeviceCustId(String entResNo, Integer deviceType, String deviceNo)
			throws HsException;

	/**
	 * 查询积分比例
	 * 
	 * @param entResNo
	 *            企业互生号 不能为空 企业互生号
	 * @param deviceType
	 *            设备类型 1：POS 2：刷卡器 3：平板
	 * @param deviceNo
	 *            设备终端编号（平台自己定义 4位数字）
	 * @return 积分比例数组[1,3,5,7,9]
	 * @throws HsException
	 */
	public String[] findPointRate(String entResNo, Integer deviceType, String deviceNo)
			throws HsException;

	/**
	 * 校验互生卡密码是否正确、返回互生卡信息
	 * 
	 * @param perResNo
	 *            消费者互生号
	 * @param pwd
	 *            消费者密码
	 * @return
	 * @throws HsException
	 */
	public AsResNoInfo getResNoInfo(String perResNo, String pwd) throws HsException;

	/**
	 * 校验消费者互生卡号是否有效
	 * 
	 * @param perResNo
	 *            消费者互生号
	 * @param cryptogram
	 *            互生卡暗码
	 * @return
	 * @throws HsException
	 */
	public boolean validResNo(String perResNo, String cryptogram) throws HsException;

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
	 * 修改设备状态
	 * 
	 * @param entResNo
	 *            企业互生号 不能为空
	 * @param deviceNo
	 *            设备终端编号（平台自己定义 4位数字）
	 * @param deviceType
	 *            设备类型枚举 1：POS 2：刷卡器 3：平板
	 * @param deviceStatus
	 *            设备状态枚举 设备状态0未启用、1 启用、2 停用、3 返修、4冻结
	 * @param operator
	 *            操作者客户号
	 * @return
	 * @throws HsException
	 */
	public void updateDeviceStatus(String entResNo, Integer deviceType, String deviceNo,
			Integer deviceStatus, String operator) throws HsException;

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
	 * 缓存中没有数据时组装并获取POS的企业信息
	 * 
	 * @param entResNo
	 *            企业资源号
	 * @param deviceNo
	 *            设备号
	 * @return
	 * @throws HsException
	 */
	public AsPosEnt getPosEnt(String entResNo, String deviceNo) throws HsException;

	/**
	 * 获取POS公用信息
	 * 
	 * @return
	 * @throws HsException
	 */
	public AsPosBaseInfo getPosBaseInfo() throws HsException;
}
