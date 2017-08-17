/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.device.service;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.bp.api.IBusinessParamSearchService;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.api.device.IUCAsDeviceService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.bean.common.AsPosBaseInfo;
import com.gy.hsxt.uc.as.bean.common.AsPosEnt;
import com.gy.hsxt.uc.as.bean.consumer.AsHsCard;
import com.gy.hsxt.uc.as.bean.device.AsCardReader;
import com.gy.hsxt.uc.as.bean.device.AsDevice;
import com.gy.hsxt.uc.as.bean.device.AsPad;
import com.gy.hsxt.uc.as.bean.device.AsPos;
import com.gy.hsxt.uc.as.bean.device.AsResNoInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderAuthInfoService;
import com.gy.hsxt.uc.bs.api.device.IUCBsDeviceService;
import com.gy.hsxt.uc.bs.bean.consumer.BsRealNameAuth;
import com.gy.hsxt.uc.bs.bean.device.BsCardReader;
import com.gy.hsxt.uc.bs.bean.device.BsPad;
import com.gy.hsxt.uc.bs.bean.device.BsPos;
import com.gy.hsxt.uc.cache.CacheKeyGen;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.device.bean.Device;
import com.gy.hsxt.uc.device.mapper.DeviceMapper;
import com.gy.hsxt.uc.util.CustIdGenerator;

/**
 * 用户中心提供接入系统调用 设备相关服务接口
 * 
 * @Package: com.gy.hsxt.uc.device.service
 * @ClassName: AsDeviceService
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-10-29 下午2:27:23
 * @version V1.0
 */
@Service 
public class AsDeviceService implements IUCAsDeviceService {
	private static final String REGEX = "\\|";
	@Autowired
	private DeviceMapper deviceMapper;
	@Autowired
	private IUCBsDeviceService bsDeviceService;
	@Autowired
	private IUCAsCardHolderService cardHolderService;
	@Autowired
	private IUCBsCardHolderAuthInfoService bsCardHolderAuthInfoService;
	@Autowired
	IUCAsEntService entService;
	@Autowired
	RedisUtil<Object> fixRedisUtil;
	@Autowired
	public IBusinessParamSearchService businessParamSearch;
	@Autowired
	LcsClient lcsClient;
	@Autowired
	CommonCacheService commonCacheService;
	
	/**
	 * 查询pos机信息
	 * 
	 * @param entResNo
	 *            企业互生号 不能为空
	 * @param deviceNo
	 *            设备终端编号（平台自己定义 4位数字）
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.device.IUCAsDeviceService#findPosByDeviceNo(java.lang.String,
	 *      java.lang.String)
	 */
	public AsPos findPosByDeviceNo(String entResNo, String deviceNo) throws HsException {
		// 基础数据验证
		if (isBlank(entResNo) || isBlank(deviceNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "必传参数为空");
		}

		// 查询pos机
		BsPos bsPos = bsDeviceService.findPosByDeviceNo(entResNo, deviceNo);
		AsPos asPos = new AsPos();
		BeanUtils.copyProperties(bsPos, asPos);
		return asPos;
	}

	/**
	 * 查询pad平板信息
	 * 
	 * @param entResNo
	 *            企业互生号 不能为空
	 * @param deviceNo
	 *            设备终端编号（平台自己定义 4位数字）
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.device.IUCAsDeviceService#findPadByDeviceNo(java.lang.String,
	 *      java.lang.String)
	 */
	public AsPad findPadByDeviceNo(String entResNo, String deviceNo) throws HsException {
		// 基础数据验证
		if (isBlank(entResNo) || isBlank(deviceNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "必传参数为空");
		}

		// 查询平板信息
		BsPad bsPad = bsDeviceService.findPadByDeviceNo(entResNo, deviceNo);
		AsPad asPad = new AsPad();
		BeanUtils.copyProperties(bsPad, asPad);
		return asPad;
	}

	/**
	 * 查询刷卡器信息
	 * 
	 * @param entResNo
	 *            企业互生号 不能为空
	 * @param deviceNo
	 *            设备终端编号（平台自己定义 4位数字）
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.device.IUCAsDeviceService#findCardReaderByDeviceNo(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public AsCardReader findCardReaderByDeviceNo(String entResNo, String deviceNo)
			throws HsException {
		// 基础数据验证
		if (isBlank(entResNo) || isBlank(deviceNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "必传参数为空");
		}

		// 查询刷卡器信息
		BsCardReader bsCardReader = bsDeviceService.findCardReaderByDeviceNo(entResNo, deviceNo);
		AsCardReader asCardReader = new AsCardReader();
		BeanUtils.copyProperties(bsCardReader, asCardReader);
		return asCardReader;
	}

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
	 * @see com.gy.hsxt.uc.as.api.device.IUCAsDeviceService#listAsDevice(java.lang.String,
	 *      java.lang.Integer, java.lang.String, int, int)
	 */
	@Override
	public PageData<AsDevice> listAsDevice(String entResNo, Integer deviceType, String deviceNo,
			int currentPage, int pageCount) throws HsException {
		// 基础数据验证
		if (isBlank(entResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "必传参数为空");
		}
		if (currentPage < 1 || pageCount < 1) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_ILLEGAL.getValue(), "非法参数值");
		}
		if(StringUtils.isBlank(deviceType)){
			deviceType = null;
		}
		if(StringUtils.isBlank(deviceNo)){
			deviceNo = null;
		}
		// 分页查询设备数据
		PageData<AsDevice> page = new PageData<AsDevice>();

		int count = deviceMapper.countDevice(entResNo, deviceType, deviceNo);
		page.setCount(count);
		if (count < 1) {
			return page;
		}
		// 计算分页数据
		int start = currentPage * pageCount - pageCount;
		int end = currentPage * pageCount + 1;
		// 查询
		List<Device> list = deviceMapper.listDevice(entResNo, deviceType, deviceNo, start, end);

		List<AsDevice> result = new ArrayList<AsDevice>();
		for (Device device : list) {
			result.add(device.generateAsDevice());
		}
		page.setResult(result);
		return page;
	}

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
	 * @see com.gy.hsxt.uc.as.api.device.IUCAsDeviceService#findDeviceCustId(java.lang.String,
	 *      java.lang.Integer, java.lang.String)
	 */
	@Override
	public String findDeviceCustId(String entResNo, Integer deviceType, String deviceNo)
			throws HsException {
		// 基础数据验证
		if (isBlank(entResNo) || isBlank(deviceNo) || isBlank(deviceType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "必传参数为空");
		}

		// 查询设备客户号 比填不能为空
		Device device = deviceMapper.findDeviceByDeviceNo(entResNo, deviceType, deviceNo);

		if (device == null) {
			throw new HsException(ErrorCodeEnum.DEVICE_IS_NOT_FOUND.getValue(),
					ErrorCodeEnum.DEVICE_IS_NOT_FOUND.getDesc());
		}
		return device.getDeviceCustId();
	}

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
	 * @see com.gy.hsxt.uc.as.api.device.IUCAsDeviceService#findPointRate(java.lang.String,
	 *      java.lang.Integer, java.lang.String)
	 */
	@Override
	public String[] findPointRate(String entResNo, Integer deviceType, String deviceNo)
			throws HsException {
		// 基础数据验证
		if (isBlank(entResNo) || isBlank(deviceNo) || isBlank(deviceType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "必传参数为空");
		}

		// 查询设备
		Device device = deviceMapper.findDeviceByDeviceNo(entResNo, deviceType, deviceNo);
		String[] points = null;
		if (device != null) {
			points = splitPointRate(device.getPointRate());
		}
		// 获取设备积分比例
		return points;
	}

	/**
	 * 拆分积分比例字符串成积分比例数组
	 * @param pointRate
	 * @return
	 */
	private String[] splitPointRate(String pointRate){
		String[] points = new String[1];
		if(!StringUtils.isBlank(pointRate)){
			if(pointRate.contains("|")){
				points = pointRate.split(REGEX);
			}else{
				points[0] = pointRate;
			}
		}
		return points;
	}

	/**
	 * 校验互生卡密码是否正确、返回互生卡信息
	 * 
	 * @param perResNo
	 *            消费者互生号
	 * @param pwd
	 *            消费者密码
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.device.IUCAsDeviceService#getResNoInfo(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public AsResNoInfo getResNoInfo(String perResNo, String pwd) throws HsException {
		// 基础数据验证
		if (isBlank(perResNo) || isBlank(pwd)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "必传参数为空");
		}

		String perCustId = commonCacheService.findCustIdByResNo(perResNo);
		CustIdGenerator.isCarderExist(perCustId, perResNo);
		BsRealNameAuth authInfo = bsCardHolderAuthInfoService.searchRealNameAuthInfo(perCustId);

		AsResNoInfo resnoInfo = new AsResNoInfo();
		resnoInfo.setRealName(authInfo.getUserName());
		return resnoInfo;
	}

	/**
	 * 校验消费者互生卡号是否有效
	 * 
	 * @param perResNo
	 *            消费者互生号
	 * @param cryptogram
	 *            互生卡暗码
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.device.IUCAsDeviceService#validResNo(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public boolean validResNo(String perResNo, String cryptogram) throws HsException {
		// 基础数据验证
		if (isBlank(perResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "必传参数为空");
		}

		// 获取互生卡信息
		AsHsCard hsCardInfo = cardHolderService.searchHsCardInfoByResNo(perResNo);

		return hsCardInfo.getCardStatus() == 1;
	}

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
	 * @see com.gy.hsxt.uc.as.api.device.IUCAsDeviceService#updatePointRate(java.lang.String,
	 *      java.lang.Integer, java.lang.String, java.lang.String[],
	 *      java.lang.String)
	 */
	@Override
	public void updatePointRate(String entResNo, Integer deviceType, String deviceNo,
			String[] pointRate, String operator) throws HsException {

		bsDeviceService.updatePointRate(entResNo, deviceType, deviceNo, pointRate, operator);

	}

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
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.device.IUCAsDeviceService#updateDeviceStatus(java.lang.String,
	 *      java.lang.Integer, java.lang.String, java.lang.Integer,
	 *      java.lang.String)
	 */
	public void updateDeviceStatus(String entResNo, Integer deviceType, String deviceNo,
			Integer deviceStatus, String operator) throws HsException {

		bsDeviceService.updateDeviceStatus(entResNo, deviceType, deviceNo, deviceStatus, operator);
	}

	/**
	 * 查询企业积分比例
	 * 
	 * @param entResNo
	 *            企业资源号
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.device.IUCAsDeviceService#findEntPointRate(java.lang.String)
	 */
	@Override
	public String[] findEntPointRate(String entResNo) throws HsException {
		return bsDeviceService.findEntPointRate(entResNo);
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
	 * @see com.gy.hsxt.uc.as.api.device.IUCAsDeviceService#updateEntPointRate(java.lang.String,
	 *      java.lang.String[], java.lang.String)
	 */
	@Override
	public void updateEntPointRate(String entResNo, String[] pointRate, String operator)
			throws HsException {
		bsDeviceService.updateEntPointRate(entResNo, pointRate, operator);
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
	 * @see com.gy.hsxt.uc.as.api.device.IUCAsDeviceService#setEntPointRate(java.lang.String,
	 *      java.lang.String[], java.lang.String)
	 */
	@Override
	public void setEntPointRate(String entResNo, String[] pointRate, String operator)
			throws HsException {
		bsDeviceService.setEntPointRate(entResNo, pointRate, operator);
	}

	/**
	 * 获取POS基础信息
	 * 
	 * @return
	 * @throws HsException
	 */
	@Override
	public AsPosBaseInfo getPosBaseInfo() throws HsException {
		AsPosBaseInfo commonInfo = new AsPosBaseInfo();
		LocalInfo localInfo = lcsClient.getLocalInfo();
		if (localInfo == null) {
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(), "LCS的平台信息缓存中不存在");
		}
		commonInfo.setHsbExchangeCurrencyRate(localInfo.getExchangeRate());

		// 成员企业代兑
		String tmp = businessParamSearch.searchSysParamItemsByCodeKey(
				BusinessParam.EXCHANGE_HSB.getCode(), BusinessParam.B_SINGLE_BUY_HSB_MAX.getCode())
				.getSysItemsValue();
		commonInfo.setEntBChargeMaxValue(tmp);
		// 成员企业代兑下限
		tmp = businessParamSearch.searchSysParamItemsByCodeKey(
				BusinessParam.EXCHANGE_HSB.getCode(), BusinessParam.B_SINGLE_BUY_HSB_MIN.getCode())
				.getSysItemsValue();
		commonInfo.setEntBChargeMinValue(tmp);
		// 托管企业代兑最大值
		tmp = businessParamSearch.searchSysParamItemsByCodeKey(
				BusinessParam.EXCHANGE_HSB.getCode(), BusinessParam.T_SINGLE_BUY_HSB_MAX.getCode())
				.getSysItemsValue();
		commonInfo.setEntTChargeMaxValue(tmp);
		// 托管企业代兑最小值
		tmp = businessParamSearch.searchSysParamItemsByCodeKey(
				BusinessParam.EXCHANGE_HSB.getCode(), BusinessParam.T_SINGLE_BUY_HSB_MIN.getCode())
				.getSysItemsValue();
		commonInfo.setEntTChargeMinValue(tmp);
		// 个人充值上限
		tmp = businessParamSearch.searchSysParamItemsByCodeKey(
				BusinessParam.EXCHANGE_HSB.getCode(),
				BusinessParam.P_REGISTERED_SINGLE_BUY_HSB_MAX.getCode()).getSysItemsValue();
		commonInfo.setCarderChargeMaxValue(tmp);
		// 个人充值下限
		tmp = businessParamSearch.searchSysParamItemsByCodeKey(
				BusinessParam.EXCHANGE_HSB.getCode(),
				BusinessParam.P_REGISTERED_SINGLE_BUY_HSB_MIN.getCode()).getSysItemsValue();
		commonInfo.setCarderChargeMinValue(tmp);
		
		// 积分比例下限
		tmp = businessParamSearch.searchSysParamItemsByCodeKey(
				BusinessParam.CONFIG_PARA.getCode(),
				BusinessParam.HS_POIT_RATE_MIN.getCode()).getSysItemsValue();
		commonInfo.setPointRateMinValue(tmp);
		// 积分比例上限
		tmp = businessParamSearch.searchSysParamItemsByCodeKey(
				BusinessParam.CONFIG_PARA.getCode(),
				BusinessParam.HS_POIT_RATE_MAX.getCode()).getSysItemsValue();
		commonInfo.setPointRateMaxValue(tmp);
		commonInfo.setNetwork("www.hsxt.com");
		commonInfo.setTelephone(localInfo.getTechSupportPhone());
		commonInfo.setVersion(1);

		// 存入缓存
		fixRedisUtil.add(CacheKeyGen.genPosBaseInfoKey(), commonInfo);
		return commonInfo;
	}

	/**
	 * 缓存中没有数据时组装POS企业信息
	 * 
	 * @param entResNo
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.device.IUCAsDeviceService#getPosEnt(java.lang.String)
	 */
	@Override
	public AsPosEnt getPosEnt(String entResNo, String deviceNo) throws HsException {
		// 调用接口从数据库中获取企业信息
//		String custId = fixRedisUtil.getToString(CacheKeyGen.genEntCustId(entResNo));
		String custId = commonCacheService.findEntCustIdByEntResNo(entResNo);
		if(StringUtils.isBlank(custId)){
			throw new HsException(ErrorCodeEnum.ENT_IS_NOT_FOUND.getValue(),
					ErrorCodeEnum.ENT_IS_NOT_FOUND.getDesc()+",entResNo["+entResNo+"]");
		}
		AsEntBaseInfo entInfo = entService.searchEntBaseInfo(custId);
		if (entInfo == null) {
			throw new HsException(ErrorCodeEnum.ENT_IS_NOT_FOUND.getValue(),
					ErrorCodeEnum.ENT_IS_NOT_FOUND.getDesc());
		}
		AsPosEnt posEnt = new AsPosEnt();
		posEnt.setEntName(entInfo.getEntName());
		posEnt.setEntType(String.valueOf(entInfo.getEntCustType()));
		posEnt.setEntResNo(entResNo);
		// 设置积分比例
//		AsPos pos = findPosByDeviceNo(entResNo, deviceNo);
		// posEnt.setPointRate(pos.getPointRate());
		posEnt.setVersion("01");

		// 存入缓存
		fixRedisUtil.add(CacheKeyGen.genPosEntKey(entResNo), posEnt);
		return posEnt;
	}

}
