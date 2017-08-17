/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.device.service;

import static com.gy.hsxt.common.utils.StringUtils.getNowTimestamp;
import static com.gy.hsxt.common.utils.StringUtils.isBlank;
import static com.gy.hsxt.common.utils.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.HexBin;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.keyserver.util.KeyServerUtil;
import com.gy.hsxt.uc.as.api.common.IUCAsDeviceSignInService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.bean.common.AsPosPointRate;
import com.gy.hsxt.uc.bs.api.device.IUCBsDeviceService;
import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;
import com.gy.hsxt.uc.bs.bean.device.BsCardReader;
import com.gy.hsxt.uc.bs.bean.device.BsDevice;
import com.gy.hsxt.uc.bs.bean.device.BsDevice.BsDeviceStatusEnum;
import com.gy.hsxt.uc.bs.bean.device.BsPad;
import com.gy.hsxt.uc.bs.bean.device.BsPos;
import com.gy.hsxt.uc.bs.bean.device.SecretkeyResult;
import com.gy.hsxt.uc.bs.bean.ent.BsEntAllInfo;
import com.gy.hsxt.uc.bs.enumerate.BsDeviceTypeEumn;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.device.bean.CardReaderDevice;
import com.gy.hsxt.uc.device.bean.Device;
import com.gy.hsxt.uc.device.bean.PadDevice;
import com.gy.hsxt.uc.device.bean.PointRate;
import com.gy.hsxt.uc.device.bean.PosDevice;
import com.gy.hsxt.uc.device.mapper.CardReaderDeviceMapper;
import com.gy.hsxt.uc.device.mapper.DeviceMapper;
import com.gy.hsxt.uc.device.mapper.PadDeviceMapper;
import com.gy.hsxt.uc.device.mapper.PointRateMapper;
import com.gy.hsxt.uc.device.mapper.PosDeviceMapper;
import com.gy.hsxt.uc.util.CustIdGenerator;

/**
 * 用户中心提供给业务系统Bs 调用的 设备服务接口
 * 
 * @Package: com.gy.hsxt.uc.device.service
 * @ClassName: BsDeviceService
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-10-29 下午2:18:51
 * @version V1.0
 */
@Service
public class BsDeviceService implements IUCBsDeviceService {
	private final static String MOUDLENAME = "com.gy.hsxt.uc.device.service.BsDeviceService";
	private static final String ACTIVE = "Y";
	private static final String INACTIVE = "N";
	private static final String REGEX_SPLIT = "\\|";

	@Autowired
	private CardReaderDeviceMapper crMapper;
	@Autowired
	private PadDeviceMapper padMapper;
	@Autowired
	private PosDeviceMapper posMapper;
	@Autowired
	private PointRateMapper pointRateMapper;
	@Autowired
	private DeviceMapper deviceMapper;
	@Autowired
	private IUCAsEntService asEntService;
	@Autowired
	private IUCBsEntService bsEntService;
	@Autowired
	private CustIdGenerator generator;
	@Autowired
	private IUCAsDeviceSignInService deviceSignInService;
	@Autowired
	private CommonCacheService commonCacheService;

	/**
	 * 批量添加Pos机
	 * 
	 * @param list
	 *            pos机列表
	 * @param operator
	 *            操作者客户号
	 * 
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.device.IUCBsDeviceService#batchAddPos(java.util.List,
	 *      java.lang.String)
	 */
	@Transactional
	public List<BsPos> batchAddPos(List<BsPos> list, String operator) throws HsException {
		// 基础数据验证
		if (list == null || list.isEmpty() || isBlank(operator)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "必传参数为空");
		}

		// 批量添加pos机入库
		for (BsPos pos : list) {

			// 插入pos机
			String deviceCustId = generator.generatePosDeviceCustId();
			PosDevice posDevice = PosDevice.generatePosDevice(pos, deviceCustId, operator);
			posDevice.setPointRate(SysConfig.getDefaultPointRate());
			posMapper.insertSelective(posDevice);

			// 插入积分比例数据
			Device device = new Device();
			BeanUtils.copyProperties(posDevice, device);
			PointRate pointRate = PointRate.generatePointRate(device, operator);
			pointRateMapper.insertSelective(pointRate);

			pos = findPosByDeviceNo(pos.getEntResNo(), pos.getDeviceNO());
		}

		return list;
	}

	/**
	 * 批量添加刷卡器
	 * 
	 * @param list
	 *            刷卡器设备列表
	 * @param operator
	 *            操作者客户号
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.device.IUCBsDeviceService#batchAddCardReader(java.util.List,
	 *      java.lang.String)
	 */
	@Transactional
	public List<BsCardReader> batchAddCardReader(List<BsCardReader> list, String operator)
			throws HsException {
		// 基础数据验证
		if (list == null || list.isEmpty() || isBlank(operator)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "必传参数为空");
		}

		// 批量添加刷卡器入库
		for (BsCardReader cr : list) {
			if (isBlank(cr) || isBlank(cr.getEntResNo()) || isBlank(cr.getDeviceNO())) {
				throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "必传参数为空");
			}

			// 验证添加的设备是否存在
			CardReaderDevice cardReaderDivece = crMapper.findCardReaderByDeviceNo(cr.getEntResNo(),
					cr.getDeviceNO());
			if (cardReaderDivece != null) {
				throw new HsException(ErrorCodeEnum.DEVICE_EXIST.getValue(),
						ErrorCodeEnum.DEVICE_EXIST.getDesc()+cr.getEntResNo()+ cr.getDeviceNO());
			}

			// 插入刷卡器
			String deviceCustId = generator.generateCardReaderDeviceCustId();
			CardReaderDevice carderReaderDevice = CardReaderDevice.generateCardReaderDevice(cr,
					deviceCustId, operator);
			//设置默认积分比例pointRate
			carderReaderDevice.setPointRate(SysConfig.getDefaultPointRate());
			crMapper.insertSelective(carderReaderDevice);

			// 插入积分比例
			Device device = new Device();
			BeanUtils.copyProperties(carderReaderDevice, device);
			PointRate pointRate = PointRate.generatePointRate(device, operator);
			pointRateMapper.insertSelective(pointRate);
			
			if(cr.getStatus() == null){
				cr.setStatus(1);
			}
			cr.setDeviceCustId(deviceCustId);
			
			//cr = findCardReaderByDeviceNo(cr.getEntResNo(), cr.getDeviceNO());
		}

		return list;
	}

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
	 * @see com.gy.hsxt.uc.bs.api.device.IUCBsDeviceService#exchangeCardReader(com.gy.hsxt.uc.bs.bean.device.BsCardReader,
	 *      java.lang.String)
	 */
	@Override
	public BsCardReader exchangeCardReader(BsCardReader cardReader, String operator)
			throws HsException {
		// 基础数据验证
		if (cardReader == null || isBlank(cardReader.getEntResNo())
				|| isBlank(cardReader.getDeviceNO())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "必传参数为空");
		}

		// 验证是刷卡器设备是否存在
		CardReaderDevice oldCardReaderDevice = crMapper.findCardReaderByDeviceNo(
				cardReader.getEntResNo(), cardReader.getDeviceNO());
		if (oldCardReaderDevice == null) {
			throw new HsException(ErrorCodeEnum.DEVICE_IS_NOT_FOUND.getValue(),
					ErrorCodeEnum.DEVICE_IS_NOT_FOUND.getDesc()+cardReader.getEntResNo()+ cardReader.getDeviceNO());
		}
		String deviceCustId=oldCardReaderDevice.getDeviceCustId();
		CardReaderDevice newCarderReaderDevice = CardReaderDevice.generateCardReaderDevice(cardReader,
						deviceCustId, operator);


//		cardReaderDevice.setDeviceSeq(cardReader.getDeviceNO());
//		oldCardReaderDevice.setUpdateDate(getNowTimestamp());
//		oldCardReaderDevice.setUpdatedby(operator);
		newCarderReaderDevice.setCreatedby(null);
		crMapper.updateByPrimaryKeySelective(newCarderReaderDevice);
		
		// 返回设备
		//cardReader = findCardReaderByDeviceNo(cardReader.getEntResNo(), cardReader.getDeviceNO());
		cardReader.setDeviceCustId(deviceCustId);
		if(cardReader.getStatus() == null){
			cardReader.setStatus(1);
		}
		
		return cardReader;
	}

	/**
	 * 批量添加平板
	 * 
	 * @param list
	 *            平板设备列表
	 * @param operator
	 *            操作者客户号
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.device.IUCBsDeviceService#batchAddPad(java.util.List,
	 *      java.lang.String)
	 */
	@Transactional
	public List<BsPad> batchAddPad(List<BsPad> list, String operator) throws HsException {
		// 基础数据验证
		if (list == null || list.isEmpty() || isBlank(operator)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "必传参数为空");
		}

		// 批量添加平板入库
		for (BsPad pad : list) {
			// 平板信息入库
			String deviceCustId = generator.generatePadDeviceCustId();
			PadDevice padDevice = PadDevice.generatePadDevice(pad, deviceCustId, operator);
			padDevice.setPointRate(SysConfig.getDefaultPointRate());
			padMapper.insertSelective(padDevice);

			// 插入积分比例
			Device device = new Device();
			BeanUtils.copyProperties(padDevice, device);
			PointRate pointRate = PointRate.generatePointRate(device, operator);
			pointRateMapper.insertSelective(pointRate);

			pad = findPadByDeviceNo(pad.getEntResNo(), pad.getDeviceNO());
		}

		return list;
	}

	/**
	 * 查询pos机 信息
	 * 
	 * @param entResNo
	 *            企业互生号
	 * @param deviceNo
	 *            设备终端编号 （平台定义 4位数字）
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.device.IUCBsDeviceService#findPosByDeviceNo(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public BsPos findPosByDeviceNo(String entResNo, String deviceNo) throws HsException {
		// 基础数据验证
		if (isBlank(entResNo) || isBlank(deviceNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "必传参数为空");
		}

		// 查询pos信息
		PosDevice posDevice = posMapper.findPosByDeviceNo(entResNo, deviceNo);
		if (posDevice == null) {
			throw new HsException(ErrorCodeEnum.DEVICE_IS_NOT_FOUND.getValue(),
					"entResNo["+entResNo+"]deviceNo["+deviceNo+"]"+ErrorCodeEnum.DEVICE_IS_NOT_FOUND.getDesc());
		}

		return posDevice.generateBsPos();
	}

	/**
	 * 查询平板信息
	 * 
	 * @param entResNo
	 *            企业互生号
	 * @param deviceNo
	 *            设备终端编号 （平台定义 4位数字）
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.device.IUCBsDeviceService#findPadByDeviceNo(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public BsPad findPadByDeviceNo(String entResNo, String deviceNo) throws HsException {
		// 基础数据验证
		if (isBlank(entResNo) || isBlank(deviceNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "必传参数为空");
		}

		// 查询平板信息
		PadDevice padDevice = padMapper.findPadByDeviceNo(entResNo, deviceNo);
		if (padDevice == null) {
			throw new HsException(ErrorCodeEnum.DEVICE_IS_NOT_FOUND.getValue(),
					"entResNo["+entResNo+"]deviceNo["+deviceNo+"]"+ErrorCodeEnum.DEVICE_IS_NOT_FOUND.getDesc());
		}

		return padDevice.generateBsPad();
	}

	/**
	 * 查询刷卡器信息
	 * 
	 * @param entResNo
	 *            企业互生号
	 * @param deviceNo
	 *            设备终端编号 （平台定义 4位数字）
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.device.IUCBsDeviceService#findCardReaderByDeviceNo(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public BsCardReader findCardReaderByDeviceNo(String entResNo, String deviceNo)
			throws HsException {
		// 基础数据验证
		if (isBlank(entResNo) || isBlank(deviceNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "必传参数为空");
		}

		// 查询刷卡器信息
		CardReaderDevice cardReader = crMapper.findCardReaderByDeviceNo(entResNo, deviceNo);
		if (cardReader == null) {
			throw new HsException(ErrorCodeEnum.DEVICE_IS_NOT_FOUND.getValue(),
					"entResNo["+entResNo+"]deviceNo["+deviceNo+"]"+ErrorCodeEnum.DEVICE_IS_NOT_FOUND.getDesc());
		}

		return cardReader.generateBsCardReader();
	}

	/**
	 * 查询企业设备
	 * 
	 * @param entResNo
	 *            企业互生号
	 * @param deviceType
	 * @param deviceNo
	 *            设备终端编号 （平台定义 4位数字）
	 * @param currentPage
	 *            当前页码
	 * @param pageCount
	 *            每页显示的条数
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.device.IUCBsDeviceService#listBsDevice(java.lang.String,
	 *      com.gy.hsxt.uc.bs.enumerate.BsDeviceTypeEumn, java.lang.String, int,
	 *      int)
	 */
	@Override
	public PageData<BsDevice> listBsDevice(String entResNo, Integer deviceType, String deviceNo,
			int currentPage, int pageCount) throws HsException {
		// 基础数据验证
		if (isBlank(entResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "必传参数为空");
		}
		if (currentPage < 1 || pageCount < 1) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_ILLEGAL.getValue(),
					ErrorCodeEnum.PARAM_IS_ILLEGAL.getDesc());
		}

		// 分页查询设备信息
		PageData<BsDevice> page = new PageData<BsDevice>();

		int count = deviceMapper.countDevice(entResNo, deviceType, deviceNo);
		page.setCount(count);
		if (count < 1) {
			return page;
		}
		// 计算分页信息
		int start = currentPage * pageCount - pageCount;
		int end = currentPage * pageCount + 1;
		// 查询设备
		List<Device> list = deviceMapper.listDevice(entResNo, deviceType, deviceNo, start, end);

		List<BsDevice> result = new ArrayList<BsDevice>();
		for (Device device : list) {
			result.add(device.generateBsDevice());
		}

		page.setResult(result);
		return page;
	}

	/**
	 * 修改设备状态
	 * 
	 * @param entResNo
	 *            企业互生号
	 * @param deviceType
	 * @param deviceNo
	 *            设备终端编号 （平台定义 4位数字）
	 * @param deviceStatus
	 *            设备状态
	 * @param operator
	 *            操作者客户号
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.device.IUCBsDeviceService#updateDeviceStatus(java.lang.String,
	 *      com.gy.hsxt.uc.bs.enumerate.Integer, java.lang.String,
	 *      com.gy.hsxt.uc.bs.bean.device.BsDevice.BsDeviceStatusEnum,
	 *      java.lang.String)
	 */
	@Transactional
	public void updateDeviceStatus(String entResNo, Integer deviceType, String deviceNo,
			Integer deviceStatus, String operator) throws HsException {
		// 基础数据验证
		if (isBlank(entResNo) || isBlank(deviceNo) || deviceType == null) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "必传参数为空");
		}

		switch (deviceType) {
		case 1:
			PosDevice posDevice = posMapper.findPosByDeviceNo(entResNo, deviceNo);
			if (posDevice == null) {
				throw new HsException(ErrorCodeEnum.DEVICE_IS_NOT_FOUND.getValue(),
						ErrorCodeEnum.DEVICE_IS_NOT_FOUND.getDesc());
			}
			posDevice.setDeviceStatus(deviceStatus);
			posDevice.setUpdatedby(operator);
			posDevice.setUpdateDate(getNowTimestamp());
			if (deviceStatus == BsDeviceStatusEnum.ENABLED.getValue()) {
				posDevice.setIsactive(ACTIVE);
			}
			posMapper.updateByDeviceNoSelective(posDevice);
			break;
		case 2:
			CardReaderDevice crDevice = crMapper.findCardReaderByDeviceNo(entResNo, deviceNo);
			if (crDevice == null) {
				throw new HsException(ErrorCodeEnum.DEVICE_IS_NOT_FOUND.getValue(),
						ErrorCodeEnum.DEVICE_IS_NOT_FOUND.getDesc());
			}
			crDevice.setDeviceStatus(deviceStatus);
			crDevice.setUpdatedby(operator);
			crDevice.setUpdateDate(getNowTimestamp());
			if (deviceStatus == BsDeviceStatusEnum.ENABLED.getValue()) {
				crDevice.setIsactive(ACTIVE);
			}
			crMapper.updateByPrimaryKeySelective(crDevice);
			break;
		case 3:
			PadDevice padDevice = padMapper.findPadByDeviceNo(entResNo, deviceNo);
			if (padDevice == null) {
				throw new HsException(ErrorCodeEnum.DEVICE_IS_NOT_FOUND.getValue(),
						ErrorCodeEnum.DEVICE_IS_NOT_FOUND.getDesc());
			}
			padDevice.setDeviceStatus(deviceStatus);
			padDevice.setUpdatedby(operator);
			padDevice.setUpdateDate(getNowTimestamp());
			if (deviceStatus == BsDeviceStatusEnum.ENABLED.getValue()) {
				padDevice.setIsactive(ACTIVE);
			}
			padMapper.updateByPrimaryKeySelective(padDevice);
			break;
		default:
			throw new HsException(ErrorCodeEnum.PARAM_IS_ILLEGAL.getValue(), "设备类型不正确，设备类型："
					+ deviceType);
		}

	}

	/**
	 * 修改积分比例
	 * 
	 * @param entResNo
	 *            企业互生号
	 * @param deviceType
	 * @param deviceNo
	 *            设备终端编号 （平台定义 4位数字）
	 * @param pointRate
	 *            积分比例
	 * @param operator
	 *            操作者客户号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.device.IUCBsDeviceService#updatePointRate(java.lang.String,
	 *      com.gy.hsxt.uc.bs.enumerate.Integer, java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Transactional
	public void updatePointRate(String entResNo, Integer deviceType, String deviceNo,
			String[] pointRate, String operator) throws HsException {
		// 基础数据验证
		if (isBlank(entResNo) || isBlank(pointRate) || isBlank(deviceType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "必传参数为空");
		}
		SystemLog.info(MOUDLENAME, "updatePointRate", "修改积分比例：企业互生号：" + entResNo +", 机器编号：" + deviceNo + "修改积分比例：" + JSON.toJSONString(pointRate));
		// 获取设备终端编号 （平台定义 4位数字）
		String deviceCustId = null;

		if (isNotBlank(deviceNo)) {
			switch (deviceType) {
			case 1:
				deviceCustId = findPosByDeviceNo(entResNo, deviceNo).getDeviceCustId();
				break;
			case 2:
				deviceCustId = findCardReaderByDeviceNo(entResNo, deviceNo).getDeviceCustId();
				break;
			case 3:
				deviceCustId = findPadByDeviceNo(entResNo, deviceNo).getDeviceCustId();
				
				break;
			default:
				throw new HsException(ErrorCodeEnum.PARAM_IS_ILLEGAL.getValue(), "设备类型不正确，设备类型："
						+ deviceType);
			}
		} else {
			deviceCustId = commonCacheService.findEntCustIdByEntResNo(entResNo);
			if(StringUtils.isBlank(deviceCustId)){
				throw new HsException(ErrorCodeEnum.ENT_IS_NOT_FOUND.getValue(),
						ErrorCodeEnum.ENT_IS_NOT_FOUND.getDesc()+",entResNo["+entResNo+"]");
			}
		}

		// 更新积分比例
		PointRate rate = new PointRate();
		rate.setPointRate(StringUtils.join(pointRate, "|"));
		rate.setModifyAcount(-1);
		rate.setUpdatedby(operator);
		rate.setCustId(deviceCustId);
		rate.setUpdateDate(getNowTimestamp());
		pointRateMapper.updateByPrimaryKeySelective(rate);

		// 更新缓存
		if (deviceType == BsDeviceTypeEumn.POS.getType()) {
			updatePosPointRate(entResNo, deviceNo, pointRate);
		}
	}

	/**
	 * 更新POS积分比例缓存
	 * 
	 * @param entResNo
	 *            企业资源号
	 * @param posNo
	 *            POS编号
	 * @param rates
	 *            比例
	 * @param version
	 *            版本号
	 */
	public void updatePosPointRate(String entResNo, String posNo, String[] rates) {
		AsPosPointRate oldRate = commonCacheService.getPosPointRateCache(entResNo, posNo);
		if (oldRate == null) {
			oldRate = new AsPosPointRate();
		}
		AsPosPointRate rate = new AsPosPointRate();
		rate.setRate(rates);
		String version = genVersion(oldRate.getVersion());
		rate.setVersion(version);
		SystemLog.info(MOUDLENAME, "修改POS积分比例存入缓存:", entResNo +", 机器编号：" + posNo + "修改积分比例：" + JSON.toJSONString(rate));
		commonCacheService.addPosPointRateCache(entResNo, posNo, rate);
	}

	

	/**
	 * 生成基础信息版本号
	 * 
	 * @param vs
	 * @return
	 */
	public String genVersion(String vs) {
		int version = Integer.valueOf(vs);
		version = version >= 99 ? 1 : version + 1;

		return String.format("%02d", version);
	}

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
			throws HsException {
		PosDevice posDevice = new PosDevice();
		posDevice.setUpdatedby(operator);
		posDevice.setUpdateDate(getNowTimestamp());
		KeyServerUtil util = new KeyServerUtil();
		String newPmk = util.encodePmk(HexBin.decode(pmk));
		SystemLog.info(MOUDLENAME, "updatePosStatus", "--转换后的新PMK：" + newPmk);
		posDevice.setMainSecretKey(newPmk);
		posDevice.setDeviceStatus(deviceStatus);
		if (deviceStatus == BsDeviceStatusEnum.ENABLED.getValue()) {
			posDevice.setIsactive(ACTIVE);
		} else {
			posDevice.setIsactive(INACTIVE);
		}
		posMapper.updateStatusByPmk(posDevice);
	}

	
	
	/**
	 * 创建pos机秘钥
	 * 
	 * @param entResNo
	 *            企业互生号
	 * @param deviceNo
	 *            设备终端编号 （平台定义 4位数字）
	 * @param machineNo
	 *            设备机器码 （厂家定义）
	 * @param isNewVersion
	 *            是否新版本
	 * @param operCustId
	 *            操作者客户号
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.device.IUCBsDeviceService#createPosPMK(java.lang.String,
	 *      java.lang.String, java.lang.String, boolean, java.lang.String)
	 */
	@Transactional
	public SecretkeyResult createPosPMK(String entResNo, String deviceNo, String machineNo,
			boolean isNewVersion, String operCustId) throws HsException {
		// 基础数据验证
		if (isBlank(entResNo) || isBlank(deviceNo) || isBlank(machineNo) || isBlank(operCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "必传参数为空");
		}

		// 验证设备是否存在
		PosDevice posDevice = posMapper.findByMachineNo(machineNo);

		if (posDevice != null) {
			throw new HsException(ErrorCodeEnum.DEVICE_EXIST.getValue(),
					ErrorCodeEnum.DEVICE_EXIST.getDesc());
		}

		// 生成秘钥
		Pmk pmk = new Pmk();

		// pos机设备数据入库
		String deviceCustId = generator.generatePosDeviceCustId();
		String entCustId = commonCacheService.findEntCustIdByEntResNo(entResNo);
		if(StringUtils.isBlank(entCustId)){
			throw new HsException(ErrorCodeEnum.ENT_IS_NOT_FOUND.getValue(),
					ErrorCodeEnum.ENT_IS_NOT_FOUND.getDesc()+",entResNo["+entResNo+"]");
		}
		PosDevice pos = new PosDevice();
		pos.setCreatedby(operCustId);
		pos.setDeviceCustId(deviceCustId);
		pos.setDeviceSeq(deviceNo);
		pos.setDeviceStatus(BsDeviceStatusEnum.NOT_ENABLED.getValue());
		pos.setEntCustId(entCustId);
		pos.setEntResNo(entResNo);
		pos.setMachineNo(machineNo);
		pos.setMainSecretKey(pmk.getPmkEncoderStr());
		pos.setIsactive(INACTIVE);
		pos.setUpdatedby(operCustId);
		posMapper.insertSelective(pos);

		// 积分比例入库
		insertPointRate(entResNo, deviceNo, operCustId, deviceCustId, entCustId);

		// 创建返回对象
		String[] pointRate = SysConfig.getDefaultPointRate().split(REGEX_SPLIT);
		return bulidResultForCreatePMK(entCustId, pointRate, pmk.getPmkSrcStr());
	}

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
	 * @see com.gy.hsxt.uc.bs.api.device.IUCBsDeviceService#reCreatePosPMK(java.lang.String,
	 *      java.lang.String, boolean, java.lang.String)
	 */
	@Override
	public SecretkeyResult reCreatePosPMK(String entResNo, String deviceNo, boolean isNewVersion,
			String operCustId, String newDeviceSeqNo) throws HsException {
		// 基础数据验证
		if (isBlank(entResNo) || isBlank(deviceNo) || isBlank(operCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "必传参数为空");
		}
		PosDevice posDevice = posMapper.findPosByDeviceNo(entResNo, deviceNo);
		if (posDevice == null) {
			throw new HsException(ErrorCodeEnum.DEVICE_IS_NOT_FOUND.getValue(),
					ErrorCodeEnum.DEVICE_IS_NOT_FOUND.getDesc());
		}

		// 生成秘钥
		Pmk pmk = new Pmk();
		posDevice.setUpdateDate(getNowTimestamp());
		posDevice.setUpdatedby(operCustId);
		posDevice.setMainSecretKey(pmk.getPmkEncoderStr());
		posDevice.setMachineNo(newDeviceSeqNo);
		posDevice.setDeviceStatus(BsDeviceStatusEnum.REPAIRED.getValue());
		// 修改设备
		posMapper.updateByPrimaryKeySelective(posDevice);
		// String[] points =
		return bulidResultForCreatePMK(posDevice.getEntCustId(),
				posDevice.getPointRate().split(REGEX_SPLIT), pmk.getPmkSrcStr());

	}

	/**
	 * 拆分积分比例字符串成积分比例数组
	 * 
	 * @param pointRate
	 * @return
	 */
	private String[] splitPointRate(String pointRate) {
		String[] points = new String[1];
		if (!StringUtils.isBlank(pointRate)) {
			if (pointRate.contains("|")) {
				points = pointRate.split(REGEX_SPLIT);
			} else {
				points[0] = pointRate;
			}
		}
		return points;
	}

	/**
	 * 插入积分比例数据入库
	 * 
	 * @param entResNo
	 *            企业互生号
	 * @param deviceNo
	 *            设备终端编号 （平台定义 4位数字）
	 * @param operCustId
	 *            操作者客户号
	 * @param deviceCustId
	 *            设备客户号
	 * @param entCustId
	 *            企业客户号
	 * @return
	 */
	private void insertPointRate(String entResNo, String deviceNo, String operCustId,
			String deviceCustId, String entCustId) {
		PointRate pointRate = new PointRate();
		pointRate.setCreatedby(operCustId);
		pointRate.setDeviceSeq(deviceNo);
		pointRate.setEntResNo(entResNo);
		pointRate.setCustId(deviceCustId);
		pointRate.setPointRate(SysConfig.getDefaultPointRate());
		pointRate.setEntCustId(entCustId);
		pointRate.setUpdatedby(operCustId);
		pointRateMapper.insertSelective(pointRate);
	}

	/**
	 * 构建创建秘钥返回结果
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @param pointRate
	 *            积分比例
	 * @param pmk
	 *            秘钥 PMK
	 * @return
	 */
	private SecretkeyResult bulidResultForCreatePMK(String entCustId, String[] pointRate, String pmk) {
		BsEntAllInfo entAllInfo = bsEntService.searchEntAllInfo(entCustId);
		SecretkeyResult result = new SecretkeyResult();
		result.setEntCustId(entCustId);
		result.setEntName(entAllInfo.getMainInfo().getEntName());
		result.setEntType(entAllInfo.getMainInfo().getEntCustType());
		result.setEnyStatus(entAllInfo.getStatusInfo().getBaseStatus());
		result.setPmk(pmk);
		result.setPointRate(pointRate);
		result.setVersion(entAllInfo.getMainInfo().getEntVersion());
		return result;
	}

	/**
	 * 创建pos机秘钥
	 * 
	 * @param entResNo
	 *            企业互生号
	 * @param deviceNo
	 *            设备终端编号 （平台定义 4位数字）
	 * @param machineNo
	 *            设备机器码 （厂家定义）
	 * @param isNewVersion
	 *            是否新版本
	 * @param operCustId
	 *            操作者客户号
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.device.IUCBsDeviceService#createPosPMK(java.lang.String,
	 *      java.lang.String, java.lang.String, boolean, java.lang.String)
	 */
	@Transactional
	public SecretkeyResult createPadPMK(String entResNo, String deviceNo, String machineNo,
			boolean isNewVersion, String operCustId) throws HsException {
		// 基础数据验证
		if (isBlank(entResNo) || isBlank(deviceNo) || isBlank(machineNo) || isBlank(operCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "必传参数为空");
		}

		// 验证设备是否存在
		PadDevice padDevice = padMapper.findPadByDeviceNo(entResNo, deviceNo);
		if (padDevice != null) {
			throw new HsException(ErrorCodeEnum.DEVICE_EXIST.getValue(),
					ErrorCodeEnum.DEVICE_EXIST.getDesc());
		}

		// 生成秘钥
		Pmk pmk = new Pmk();

		// pad设备数据入库
		String deviceCustId = generator.generatePadDeviceCustId();
		String entCustId = commonCacheService.findEntCustIdByEntResNo(entResNo);
		if(StringUtils.isBlank(entCustId)){
			throw new HsException(ErrorCodeEnum.ENT_IS_NOT_FOUND.getValue(),
					ErrorCodeEnum.ENT_IS_NOT_FOUND.getDesc()+",entResNo["+entResNo+"]");
		}
		PadDevice pad = new PadDevice();
		pad.setCreatedby(operCustId);
		pad.setDeviceCustId(deviceCustId);
		pad.setDeviceSeq(deviceNo);
		pad.setDeviceStatus(BsDeviceStatusEnum.NOT_ENABLED.getValue());
		pad.setEntCustId(entCustId);
		pad.setEntResNo(entResNo);
		pad.setMachineNo(machineNo);
		pad.setMainSecretKey(pmk.getPmkEncoderStr());
		pad.setUpdatedby(operCustId);
		pad.setIsactive(INACTIVE);
		padMapper.insertSelective(pad);

		// 积分比例入库
		insertPointRate(entResNo, deviceNo, operCustId, deviceCustId, entCustId);

		// 创建返回对象
		String[] pointRate = SysConfig.getDefaultPointRate().split(REGEX_SPLIT);
		return bulidResultForCreatePMK(entCustId, pointRate, pmk.getPmkSrcStr());
	}

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
	 * @see com.gy.hsxt.uc.bs.api.device.IUCBsDeviceService#reCreatePadPMK(java.lang.String,
	 *      java.lang.String, boolean, java.lang.String)
	 */
	@Override
	public SecretkeyResult reCreatePadPMK(String entResNo, String deviceNo, boolean isNewVersion,
			String operCustId) throws HsException {
		// 基础数据验证
		if (isBlank(entResNo) || isBlank(deviceNo) || isBlank(operCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "必传参数为空");
		}

		// 验证设备是否存在
		PadDevice padDevice = padMapper.findPadByDeviceNo(entResNo, deviceNo);
		if (isBlank(padDevice)) {
			throw new HsException(ErrorCodeEnum.DEVICE_IS_NOT_FOUND.getValue(),
					ErrorCodeEnum.DEVICE_IS_NOT_FOUND.getDesc());
		}

		// 生成秘钥
		Pmk pmk = new Pmk();
		padDevice.setUpdateDate(getNowTimestamp());
		padDevice.setUpdatedby(operCustId);
		padDevice.setMainSecretKey(pmk.getPmkEncoderStr());

		// 修改设备
		padMapper.updateByPrimaryKeySelective(padDevice);

		return bulidResultForCreatePMK(padDevice.getEntCustId(),
				padDevice.getPointRate().split("\\|"), pmk.getPmkSrcStr());
	}

	/**
	 * 查询企业积分比例
	 * 
	 * @param entResNo
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.device.IUCBsDeviceService#findEntPointRate(java.lang.String)
	 */
	@Override
	public String[] findEntPointRate(String entResNo) throws HsException {
		// 基础数据验证
		if (isBlank(entResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "必传参数为空");
		}

		// 查询企业积分比例
		String entCustId = commonCacheService.findEntCustIdByEntResNo(entResNo);
		if(StringUtils.isBlank(entCustId)){
			throw new HsException(ErrorCodeEnum.ENT_IS_NOT_FOUND.getValue(),
					ErrorCodeEnum.ENT_IS_NOT_FOUND.getDesc()+",entResNo["+entResNo+"]");
		}
		PointRate pointRate = pointRateMapper.selectByPrimaryKey(entCustId);
		String[] points = new String[0];
		if (null != pointRate) {
			points = splitPointRate(pointRate.getPointRate());
		}
		return points;
	}

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
	 * @see com.gy.hsxt.uc.bs.api.device.IUCBsDeviceService#updateEntPointRate(java.lang.String,
	 *      java.lang.String[], java.lang.String)
	 */
	@Override
	public void updateEntPointRate(String entResNo, String[] pointRate, String operator)
			throws HsException {
		// 基础数据验证
		if (isBlank(entResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "必传参数为空");
		}

		// 查询企业积分比例
		String entCustId = commonCacheService.findEntCustIdByEntResNo(entResNo);
		if(StringUtils.isBlank(entCustId)){
			throw new HsException(ErrorCodeEnum.ENT_IS_NOT_FOUND.getValue(),
					ErrorCodeEnum.ENT_IS_NOT_FOUND.getDesc()+",entResNo["+entResNo+"]");
		}
		PointRate rate = pointRateMapper.selectByPrimaryKey(entCustId);
		if (rate == null) {
			throw new HsException(ErrorCodeEnum.ENT_POINT_RATE_NOT_SET.getValue(),
					ErrorCodeEnum.ENT_POINT_RATE_NOT_SET.getDesc());
		}

		// 更新积分比例
		rate.setPointRate(StringUtils.join(pointRate, "|"));
		rate.setModifyAcount(-1);
		rate.setUpdatedby(operator);
		rate.setUpdateDate(getNowTimestamp());
		pointRateMapper.updateByPrimaryKeySelective(rate);

	}

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
	 * @see com.gy.hsxt.uc.bs.api.device.IUCBsDeviceService#setEntPointRate(java.lang.String,
	 *      java.lang.String[], java.lang.String)
	 */
	@Override
	public void setEntPointRate(String entResNo, String[] pointRate, String operator)
			throws HsException {
		// 基础数据验证
		if (isBlank(entResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "必传参数为空");
		}

		// 查询企业积分比例
		String entCustId = commonCacheService.findEntCustIdByEntResNo(entResNo);
		if(StringUtils.isBlank(entCustId)){
			throw new HsException(ErrorCodeEnum.ENT_IS_NOT_FOUND.getValue(),
					ErrorCodeEnum.ENT_IS_NOT_FOUND.getDesc()+",entResNo["+entResNo+"]");
		}
		PointRate rate = pointRateMapper.selectByPrimaryKey(entCustId);
		if (rate != null) {
			updateEntPointRate(entResNo, pointRate, operator);
			return;
		}

		// 设置企业积分比例
		rate = new PointRate();
		rate.setPointRate(StringUtils.join(pointRate, "|"));
		rate.setModifyAcount(-1);
		rate.setCreatedby(operator);
		rate.setUpdatedby(operator);
		rate.setCustId(entCustId);
		rate.setEntResNo(entResNo);
		pointRateMapper.insertSelective(rate);
	}

	private class Pmk {
		private byte[] pmkByte;
		private String pmkSrcStr;
		private String pmkEncoderStr;

		public Pmk() {
			// 生成PMK
			KeyServerUtil util = new KeyServerUtil();
			pmkByte = util.genPMK();
			pmkSrcStr = HexBin.encode(pmkByte);
			pmkEncoderStr = util.encodePmk(pmkByte);
		}

		/**
		 * @return the pmkSrcStr
		 */
		public String getPmkSrcStr() {
			return pmkSrcStr;
		}

		/**
		 * @return the pmkEncoderStr
		 */
		public String getPmkEncoderStr() {
			return pmkEncoderStr;
		}
	}
}
