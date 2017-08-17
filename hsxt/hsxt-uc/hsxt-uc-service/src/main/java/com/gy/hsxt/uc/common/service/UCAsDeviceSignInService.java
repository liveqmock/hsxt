/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.common.service;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.HexBin;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.keyserver.DeviceToken;
import com.gy.hsxt.keyserver.util.KeyServerUtil;
import com.gy.hsxt.uc.as.api.common.IUCAsDeviceSignInService;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.api.device.IUCAsDeviceService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.bean.common.AsSignInInfo;
import com.gy.hsxt.uc.as.bean.consumer.AsHsCard;
import com.gy.hsxt.uc.as.bean.device.AsCardReader;
import com.gy.hsxt.uc.as.bean.device.AsDevCheckParam;
import com.gy.hsxt.uc.as.bean.device.AsDevCheckResult;
import com.gy.hsxt.uc.as.bean.device.AsPos;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;
import com.gy.hsxt.uc.as.bean.enumerate.AsDeviceTypeEumn;
import com.gy.hsxt.uc.as.bean.result.AsCardReaderSignInResult;
import com.gy.hsxt.uc.as.bean.result.AsPadSignInResult;
import com.gy.hsxt.uc.as.bean.result.AsPosSignInResult;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.ent.bean.EntStatusInfo;
import com.gy.hsxt.uc.util.CustIdGenerator;
import com.gy.kms.keyserver.CoDecode;

/**
 * 设备签入接口，包括设备的签入、签出、加密密码等操作
 * 
 * @Package: com.gy.hsxt.uc.common.service
 * @ClassName: UCDeviceSignInService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-11-10 下午5:32:51
 * @version V1.0
 */
@Service
public class UCAsDeviceSignInService implements IUCAsDeviceSignInService {
	private final static String MOUDLENAME = "com.gy.hsxt.uc.common.service.UCAsDeviceSignInService";
	/**
	 * 企业管理接口
	 */
	@Autowired
	IUCAsEntService asEntService;

	@Autowired
	IUCAsDeviceService deviceService;

	@Autowired
	IUCAsCardHolderService cardHolderService;

	@Autowired
	IUCAsPwdService pwdService;

	@Autowired
	SysConfig config;

	@Autowired
	CommonCacheService commonCacheService;
	
	@Autowired
	CommonService commonService;
	/**
	 * 
	 * 设备签出
	 * 
	 * @param entResNo
	 *            企业资源号
	 * @param deviceNo
	 *            设备编号
	 * @param operator
	 *            操作员
	 * @param type
	 *            设备类型
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsDeviceSignInService#deviceLogout(java.lang.String,
	 *      java.lang.String, java.lang.String,
	 *      com.gy.hsxt.uc.as.bean.enumerate.AsDeviceTypeEumn)
	 */
	public void deviceLogout(String entResNo, String deviceNo, String operator,
			int type) throws HsException {
		if (StringUtils.isEmpty(entResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业资源号为空");
		}
		if (StringUtils.isEmpty(deviceNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"POS机编号为空");
		}
		try {
			commonCacheService.removePosTokenCache(entResNo, deviceNo);
			SystemLog.debug(MOUDLENAME, "deviceLogout", "设备登出成功. 企业资源号："
					+ entResNo + ", 设备号：" + deviceNo);
		} catch (Exception e) {
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(),
					ErrorCodeEnum.UNKNOW_ERROR.getDesc());
		}
	}

	/**
	 * 设备鉴权
	 * 
	 * @param entResNo
	 *            企业资源号
	 * @param posNo
	 *            设备编号
	 * @param operator
	 *            操作员名称
	 * @param type
	 *            设备类型
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsDeviceSignInService#checkAuth(java.lang.String,
	 *      java.lang.String, java.lang.String,
	 *      com.gy.hsxt.uc.as.bean.enumerate.AsDeviceTypeEumn)
	 */
	@Override
	public void checkAuth(String entResNo, String deviceNo, String operator,
			int type) throws HsException {
		if (StringUtils.isEmpty(entResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业资源号为空");
		}
		if (StringUtils.isEmpty(deviceNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"POS机编号为空");
		}

		if (!asEntService.isActived(entResNo)) {
			throw new HsException(ErrorCodeEnum.ENT_WAS_INACTIVITY.getValue(),
					ErrorCodeEnum.ENT_WAS_INACTIVITY.getDesc());
		}

		// 鉴权POS
		if (type == AsDeviceTypeEumn.POS.getType()) {
			DeviceToken token = commonCacheService.getPosTokenCache(entResNo,
					deviceNo);
			if (token == null) {
				SystemLog.info(MOUDLENAME, "checkAuth", "pos鉴权失败. 企业资源号："
						+ entResNo + ", 设备号：" + deviceNo);
				throw new HsException(ErrorCodeEnum.DEVICE_NOT_SIGN.getValue(),
						ErrorCodeEnum.DEVICE_NOT_SIGN.getDesc());
			}
			
		}

	}

	public byte[] posSignIn(String entResNo, String deviceNo) {
		if (!asEntService.isActived(entResNo)) {
			throw new HsException(ErrorCodeEnum.ENT_WAS_INACTIVITY.getValue(),
					ErrorCodeEnum.ENT_WAS_INACTIVITY.getDesc());
		}
		AsPos pos = deviceService.findPosByDeviceNo(entResNo, deviceNo);
		if (pos == null) {
			SystemLog.error(MOUDLENAME, "posSignIn", "POS不存在， 企业资源号："
					+ entResNo + ", 设备号：" + deviceNo, null);
			throw new HsException(ErrorCodeEnum.DEVICE_IS_NOT_FOUND.getValue(),
					ErrorCodeEnum.DEVICE_IS_NOT_FOUND.getDesc());
		}
		// 生成mak, pik信息
		KeyServerUtil keyUtil = new KeyServerUtil();
		byte[] pmk = HexBin.decode(pos.getPmk());

		DeviceToken token = keyUtil.genPikMak(pmk);
		if (token == null) {
			throw new HsException(
					ErrorCodeEnum.POS_PIK_MAC_GEN_FAILED.getValue(),
					ErrorCodeEnum.POS_PIK_MAC_GEN_FAILED.getDesc());
		}
		long second = commonService.computeUnlockSecond(SysConfig
				.getPosReSignInTime());
		//System.out.println("-----------------------------倒计时：" + second);
		// 保存至缓存
		commonCacheService.addPosTokenCache(entResNo, deviceNo, token,second);
		byte[] pikmak=token.getPikmak();
		
		SystemLog.debug(MOUDLENAME, "posSignIn", "POS签入成功. 企业资源号：" + entResNo
				+ ", 设备号：" + deviceNo+",pikpmk="+Arrays.toString(pikmak) + "，倒计时：" + second);
		// 清除比对mac失败结果次数
		commonCacheService.removePosMacMatchCache(entResNo, deviceNo);
//		SystemLog.debug(MOUDLENAME, "posSignIn",
//				"POS签入成功后删除mac比对失败结果成功. 企业资源号：" + entResNo + ", 设备号："
//						+ deviceNo);
		
		return pikmak;
	}

	/**
	 * POS签入，签入失败返回异常，成功无返回
	 * 
	 * @param siginInfo
	 *            POS签入信息
	 * @return Pikmak
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsDeviceSignInService#posSignIn(com.gy.hsxt.uc.as.bean.common.AsSignInInfo)
	 */
	public byte[] posSignIn(AsSignInInfo signInInfo) throws HsException {
		String entResNo = signInInfo.getEntResNO();
		String deviceNo = signInInfo.getDeviceNo();

		return this.posSignIn(entResNo, deviceNo);

	}

	/**
	 * POS机验证企业版本号和积分版本号
	 * 
	 * @param signInInfo
	 *            签入信息
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsDeviceSignInService#posSignIn(com.gy.hsxt.uc.as.bean.common.AsSignInInfo)
	 */
	@Override
	public AsPosSignInResult checkVersion(AsSignInInfo signInInfo)
			throws HsException {
		if (!asEntService.isActived(signInInfo.getEntResNO())) {
			throw new HsException(ErrorCodeEnum.ENT_WAS_INACTIVITY.getValue(),
					ErrorCodeEnum.ENT_WAS_INACTIVITY.getDesc());
		}
		if (StringUtils.isBlank(signInInfo.getDeviceNo())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"设备号为空");
		}
		if (StringUtils.isBlank(signInInfo.getEntVer())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业版本号为空");
		}
		if (StringUtils.isBlank(signInInfo.getPointVer())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"版本号为空");
		}

		AsPosSignInResult r = new AsPosSignInResult();
		// 获取企业信息
		AsEntMainInfo info = asEntService.getMainInfoByResNo(signInInfo
				.getEntResNO());
		// 比对版本号是否相同, 如果不同，将企业版本信息返回
		if (!info.getEntVersion().trim().equals(signInInfo.getEntVer().trim())) {
			SystemLog.debug(
					MOUDLENAME,
					"checkVersion",
					"POS比对版本号不一致.设备号：" + signInInfo.getDeviceNo() + "，原版本号："
							+ info.getEntVersion() + "， 传入版本号："
							+ signInInfo.getEntVer());
			r.setEntVer(info.getEntVersion());
			r.setEntMainInfo(info);
		} else {
			SystemLog.debug(MOUDLENAME, "checkVersion",
					signInInfo.getDeviceNo() + " 版本号比对一致");
		}
		// 比对积分号是否相同
		AsPos pos = deviceService.findPosByDeviceNo(signInInfo.getEntResNO(),
				signInInfo.getDeviceNo());
		// 如果不同则返回积分版本号和积分比例信息
		if (!signInInfo.getPointVer().equals(pos.getPwdVersion())) {
			SystemLog.debug(MOUDLENAME, "checkVersion", "POS比对积分版本号不一致.设备号："
					+ pos.getPwdVersion() + "，原版本号：" + info.getEntVersion()
					+ "， 传入版本号：" + signInInfo.getPointVer());
			r.setPointRate(pos.getPointRate());
			r.setPointVer(pos.getPointRateVer());
		} else {
			SystemLog.debug(MOUDLENAME, "checkVersion",
					signInInfo.getDeviceNo() + " 积分版本号比对一致");
		}

		return r;
	}

	/**
	 * 刷卡器签入
	 * 
	 * @param signInfo
	 *            签入信息
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsDeviceSignInService#cardReaderSignIn(com.gy.hsxt.uc.as.bean.common.AsSignInInfo)
	 */
	@Override
	public AsCardReaderSignInResult cardReaderSignIn(AsSignInInfo signInInfo)
			throws HsException {
		if (!asEntService.isActived(signInInfo.getEntResNO())) {
			throw new HsException(ErrorCodeEnum.ENT_WAS_INACTIVITY.getValue(),
					ErrorCodeEnum.ENT_WAS_INACTIVITY.getDesc());
		}
		AsCardReaderSignInResult r = new AsCardReaderSignInResult();
		// 获取企业信息
		AsEntMainInfo info = asEntService.getMainInfoByResNo(signInInfo
				.getEntResNO());
		// 比对版本号和积分号是否相同
		if (info.getEntVersion().trim().equals(signInInfo.getEntVer().trim())) {
			r.setEntVer(info.getEntVersion());
			r.setEntMainInfo(info);
		}
		// 比对积分号是否相同
		@SuppressWarnings("unused")
		AsEntStatusInfo status = asEntService.searchEntStatusInfo(info
				.getEntCustId());

		// 生成mak, pik信息
		KeyServerUtil keyUtil = new KeyServerUtil();
		byte[] pmk = keyUtil.genPMK();

		DeviceToken token = keyUtil.genPikMak(pmk);
		if (token == null) {
			throw new HsException();
		}
		commonCacheService.addCardReaderTokenCache(info.getEntResNo(),
				signInInfo.getDeviceNo(), token);// 保存至缓存
		// 返回签入信息
		r.setChannelType(signInInfo.getChannelType());
		r.setEntResNo(signInInfo.getEntResNO());
		r.setMak(token.getMak());
		r.setPik(token.getPik());
		r.setPikmak(token.getPikmak());
		return r;
	}

	/**
	 * 平板签入
	 * 
	 * @param siginInfo签入信息
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsDeviceSignInService#padSignIn(com.gy.hsxt.uc.as.bean.common.AsSignInInfo)
	 */
	@Override
	public AsPadSignInResult padSignIn(AsSignInInfo signInInfo)
			throws HsException {
		if (!asEntService.isActived(signInInfo.getEntResNO())) {
			throw new HsException(ErrorCodeEnum.ENT_WAS_INACTIVITY.getValue(),
					ErrorCodeEnum.ENT_WAS_INACTIVITY.getDesc());
		}
		AsPadSignInResult r = new AsPadSignInResult();
		// 获取企业信息
		AsEntMainInfo info = asEntService.getMainInfoByResNo(signInInfo
				.getEntResNO());
		// 比对版本号和积分号是否相同
		if (info.getEntVersion().trim().equals(signInInfo.getEntVer().trim())) {
			r.setEntVer(info.getEntVersion());
			r.setEntMainInfo(info);
		}
		// 比对积分号是否相同
		AsEntStatusInfo status = asEntService.searchEntStatusInfo(info
				.getEntCustId());

		// 生成mak, pik信息
		KeyServerUtil keyUtil = new KeyServerUtil();
		byte[] pmk = keyUtil.genPMK();

		DeviceToken token = keyUtil.genPikMak(pmk);
		if (token == null) {
			throw new HsException();
		}
		commonCacheService.addPosTokenCache(info.getEntResNo(),
				signInInfo.getDeviceNo(), token, null);// 保存至缓存
		// 返回签入信息
		r.setChannelType(signInInfo.getChannelType());
		r.setEntResNo(signInInfo.getEntResNO());
		r.setMak(token.getMak());
		r.setPik(token.getPik());
		r.setPikmak(token.getPikmak());
		return r;
	}

	/**
	 * 
	 * 加密密码
	 * 
	 * @param posNo
	 *            POS机编号
	 * @param entResNo
	 *            企业资源号
	 * @param cnt
	 *            待加密密码
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsDeviceSignInService#getEncrypt(java.lang.String,
	 *      java.lang.String, byte[])
	 */
	@Override
	public byte[] getEncrypt(String posNo, String entResNo, byte[] cnt)
			throws HsException {
		// 验证pik是否存在
		DeviceToken token = commonCacheService
				.getPosTokenCache(entResNo, posNo);
		if (token == null) {
			throw new HsException(ErrorCodeEnum.DEVICE_NOT_SIGN.getValue(),
					ErrorCodeEnum.DEVICE_NOT_SIGN.getDesc());
		}
		// 加密
		KeyServerUtil keyUtil = new KeyServerUtil();
		return keyUtil.encodeCnt(token.getPik(), cnt);
	}

	/**
	 * 
	 * 解密密码
	 * 
	 * @param posNo
	 *            POS机编号
	 * @param entResNo
	 *            企业资源号
	 * @param cnt
	 *            待解密密码
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsDeviceSignInService#getDecrypt(java.lang.String,
	 *      java.lang.String, byte[])
	 */
	@Override
	public byte[] getDecrypt(String posNo, String entResNo, byte[] cnt)
			throws HsException {
		// 验证pik是否存在
		DeviceToken token = commonCacheService
				.getPosTokenCache(entResNo, posNo);
		if (token == null) {
			throw new HsException(ErrorCodeEnum.DEVICE_NOT_SIGN.getValue(),
					ErrorCodeEnum.DEVICE_NOT_SIGN.getDesc());
		}
		// 解密
		KeyServerUtil keyUtil = new KeyServerUtil();
		return keyUtil.decodeCnt(token.getPik(), cnt);
	}

	/**
	 * 
	 * 加密内容
	 * 
	 * @param posNo
	 *            POS机编号
	 * @param entResNo
	 *            企业资源号
	 * @param cnt
	 *            待加密内容
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsDeviceSignInService#getEncrypt(java.lang.String,
	 *      java.lang.String, byte[])
	 */
	@Override
	public byte[] getEncryptCnt(String posNo, String entResNo, byte[] cnt)
			throws HsException {
		// 验证pik是否存在
		DeviceToken token = commonCacheService
				.getPosTokenCache(entResNo, posNo);
		if (token == null) {
			throw new HsException(ErrorCodeEnum.DEVICE_NOT_SIGN.getValue(),
					ErrorCodeEnum.DEVICE_NOT_SIGN.getDesc());
		}
		// 加密
		KeyServerUtil keyUtil = new KeyServerUtil();
		return keyUtil.encodeCnt(token.getMak(), cnt);
	}

	/**
	 * 
	 * 解密内容
	 * 
	 * @param posNo
	 *            POS机编号
	 * @param entResNo
	 *            企业资源号
	 * @param cnt
	 *            待解密内容
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsDeviceSignInService#getDecrypt(java.lang.String,
	 *      java.lang.String, byte[])
	 */
	@Override
	public byte[] getDecryptCnt(String posNo, String entResNo, byte[] cnt)
			throws HsException {
		// 验证pik是否存在
		DeviceToken token = commonCacheService
				.getPosTokenCache(entResNo, posNo);
		if (token == null) {
			throw new HsException(ErrorCodeEnum.DEVICE_NOT_SIGN.getValue(),
					ErrorCodeEnum.DEVICE_NOT_SIGN.getDesc());
		}
		// 解密
		KeyServerUtil keyUtil = new KeyServerUtil();
		return keyUtil.decodeCnt(token.getMak(), cnt);
	}

	/**
	 * 
	 * 验证PMK
	 * 
	 * @param posNo
	 *            设备编号
	 * @param entResNo
	 *            企业资源号
	 * @param operator
	 *            操作员
	 * @param pmk
	 *            设备加密后的PMK
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsDeviceSignInService#checkPmk(java.lang.String,
	 *      java.lang.String, java.lang.String, byte[])
	 */
	@Override
	public boolean checkPmk(String posNo, String entResNo, String operator,
			byte[] pmk) throws HsException {
		// 效验PMK
		DeviceToken token = commonCacheService
				.getPosTokenCache(entResNo, posNo);
		if (token == null) {
			throw new HsException(ErrorCodeEnum.DEVICE_NOT_SIGN.getValue(),
					ErrorCodeEnum.DEVICE_NOT_SIGN.getDesc());
		}

		KeyServerUtil keyUtil = new KeyServerUtil();
		SystemLog.debug(MOUDLENAME, "checkPmk", "开始校验PMK, posNo:" + posNo);
		return keyUtil.checkPMK(token.getPmk(), pmk, operator);
	}

	/**
	 * 验证资源号和暗码，如果不匹配抛出异常
	 * 
	 * @param resNo
	 *            资源号
	 * @param code
	 *            暗码
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsDeviceSignInService#checkResNoAndCode(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void checkResNoAndCode(String resNo, String code) throws HsException {
		checkCardCode(resNo, code);
	}

	/**
	 * 验证卡是否有效，如果验证失败抛出异常
	 * 
	 * @param resNo
	 * @return
	 */
	private AsHsCard validCard(String resNo) {
		// 获取卡信息，如果为空表示卡未激活或不可使用
		AsHsCard card = cardHolderService.searchHsCardInfoByResNo(resNo);
		if (card == null) {
			throw new HsException(ErrorCodeEnum.RES_NO_IS_NOT_FOUND.getValue(),
					resNo + ErrorCodeEnum.RES_NO_IS_NOT_FOUND.getDesc());
		}
		// 验证卡是否有效，如果验证失败抛出异常
		if (card.getIsactive().equals("N")) {
			throw new HsException(ErrorCodeEnum.HSCARD_NOT_FOUND.getValue(),
					ErrorCodeEnum.HSCARD_NOT_FOUND.getDesc());
		}
		 if (card.getCardStatus().intValue() == 3) {
			 throw new HsException(ErrorCodeEnum.HSCARD_IS_STOP.getValue(),
					 ErrorCodeEnum.HSCARD_IS_STOP.getDesc());
		 }

		return card;
	}

	/**
	 * 验证资源号、登录密码和暗码是否匹配，如果不匹配抛出异常
	 * 
	 * @param resNo
	 * @param pwd
	 *            登录密码，使用MD5加密
	 * @param code
	 *            卡暗码
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsDeviceSignInService#checkResNoPwdAndCode(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public void checkResNoPwdAndCode(String resNo, String pwd, String code)
			throws HsException {
		checkCardCode(resNo, code);
		// 判断登录密码是否正确
		pwdService.checkLoginPwdForPOS(resNo, pwd);
		SystemLog.debug(MOUDLENAME, "checkResNoPwdAndCode",
				"校验资源号、密码和暗码成功, 资源号:" + resNo);
	}

	/**
	 * 验证资源号、登录密码是否匹配，如果不匹配抛出异常
	 * 
	 * @param resNo
	 * @param pwd
	 *            登录密码，使用MD5加密
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsDeviceSignInService#checkResNoPwdAndCode(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public void checkResNoAndLoginPwd(String resNo, String pwd)
			throws HsException {
		checkCardLoginPwd(resNo, pwd);
	}

	@Override
	public void checkCardAndReader(String entResNo, String readerNo,
			String resNo, String code) throws HsException{
		if (entResNo != null && readerNo != null) {
			// 获取读卡器
			AsCardReader reader = deviceService.findCardReaderByDeviceNo(
					entResNo, readerNo);
			if (reader.getStatus().intValue() != 1) {
				throw new HsException(
						ErrorCodeEnum.DEVICE_IS_NOT_FOUND.getValue(),
						"设备状态不为使用状态，状态：" + reader.getStatus() + ", 设备编号："
								+ readerNo);
			}
		}
		if (resNo != null && code != null) {
			checkCardCode(resNo, code);
		}
	}

	/**
	 * 验证资源号和暗码，如果不匹配抛出异常
	 * 
	 * @param resNo
	 * @param code
	 * @return 返回消费者客户号
	 * @throws HsException
	 */
	public String checkCardCode(String resNo, String code) throws HsException {
		// 验证卡是否有效，如果验证失败抛出异常
		AsHsCard card = validCard(resNo);
		// 卡挂失只禁止刷卡行为，不影响正常业务操作
		if (card.getCardStatus().intValue() == AsHsCard.CARD_STATS_LOSS) {
			// 挂失中
			throw new HsException(ErrorCodeEnum.HSCARD_IS_LOSS.getValue(),
					resNo + ErrorCodeEnum.HSCARD_IS_LOSS.getDesc());
		}

		// 判断暗码是否一样
		if (!card.getCryptogram().equals(code.trim())) {
			throw new HsException(
					ErrorCodeEnum.HS_CARD_CODE_IS_WRONG.getValue(), resNo
							+ ErrorCodeEnum.HS_CARD_CODE_IS_WRONG.getDesc());
		}
		// SystemLog.debug(MOUDLENAME, "checkCardCode", "校验资源号和暗码成功, 资源号:" +
		// resNo);
		return card.getPerCustId();
	}

	/**
	 * 验证资源号、登录密码是否匹配，如果不匹配抛出异常
	 * 
	 * @param resNo
	 * @param pwd
	 * @return 返回消费者客户号
	 * @throws HsException
	 */
	public String checkCardLoginPwd(String resNo, String pwd)
			throws HsException {
		// 验证卡是否有效，如果验证失败抛出异常
		AsHsCard card = validCard(resNo);
		// 判断登录密码是否正确
		pwdService.checkLoginPwdForPOS(resNo, pwd);
		// SystemLog.debug(MOUDLENAME, "checkCardLoginPwd", "校验资源号和登录密码成功, 资源号:"
		// + resNo);
		return card.getPerCustId();
	}

	/**
	 * 验证资源号和支付密码是否匹配
	 * 
	 * @param resNo
	 *            资源号
	 * @param chargePwd
	 *            支付密码，使用MD5加密
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsDeviceSignInService#checkResNoAndTradePwd(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void checkResNoAndTradePwd(String resNo, String tradePwd)
			throws HsException {
		// 验证卡是否有效，如果验证失败抛出异常
		validCard(resNo);
		// 判断支付密码是否正确
		pwdService.checkTradePwdForPOS(resNo, tradePwd);
		SystemLog.debug(MOUDLENAME, "checkResNoAndTradePwd",
				"校验资源号和支付密码成功, 资源号:" + resNo);
	}

	/**
	 * 生成mac
	 * 
	 * @param data
	 *            数据
	 * @param mak
	 *            mak
	 * @return
	 */
	@Override
	public byte[] genMac(String resNo, String posNo, byte[] data)
			throws HsException {
		DeviceToken token = commonCacheService.getPosTokenCache(resNo, posNo);
		if (token == null) {
			throw new HsException(ErrorCodeEnum.DEVICE_NOT_SIGN.getValue(),
					ErrorCodeEnum.DEVICE_NOT_SIGN.getDesc());
		}

		KeyServerUtil keyUtil = new KeyServerUtil();
		// 使用mak 加密data
		return keyUtil.encodeCnt(token.getMak(), data);
	}

	/**
	 * 获取mac
	 * 
	 * @param resNo
	 *            资源号
	 * @param posNo
	 *            POS机编号
	 * @param data
	 *            数据
	 * @return
	 */
	@Override
	public byte[] getMac(String resNo, String posNo, byte[] data) {
		// 获取mak
		DeviceToken token = commonCacheService.getPosTokenCache(resNo, posNo);
		if (token == null) {
			throw new HsException(ErrorCodeEnum.DEVICE_NOT_SIGN.getValue(),
					ErrorCodeEnum.DEVICE_NOT_SIGN.getDesc());
		}
		// 加密
		KeyServerUtil keyUtil = new KeyServerUtil();
		// 使用mak 加密data
		byte[] dest = keyUtil.genMac(token.getMak(), data);
		return dest;
	}

	/**
	 * 校验MAC
	 * 
	 * @param resNo
	 *            资源号
	 * @param posNo
	 *            POS机编号
	 * @param mac
	 *            MAC
	 * @param data
	 *            数据
	 */
	@Override
	public void checkMac(String resNo, String posNo, byte[] data, byte[] mac)
			throws HsException {
		// 获取mak
		DeviceToken token = commonCacheService.getPosTokenCache(resNo, posNo);
		if (token == null) {
			throw new HsException(ErrorCodeEnum.DEVICE_NOT_SIGN.getValue(),
					ErrorCodeEnum.DEVICE_NOT_SIGN.getDesc());
		}
		// 加密
		KeyServerUtil keyUtil = new KeyServerUtil();
		// 使用mak 加密data
		byte[] dest = keyUtil.genMac(token.getMak(), data);

		// 比对

		boolean isRight = Arrays.equals(dest, mac);

		// 如果失败，失败次数加1，如果连续失败3次，需重新签入
		if (!isRight) {
			SystemLog.warn(MOUDLENAME, "checkMac", resNo+"校验mac失败,posNo="+posNo+",data.length="+data.length);
			SystemLog.warn(MOUDLENAME, "checkMac",
					resNo+"mak:" + HexBin.encode(token.getMak()));
			SystemLog.warn(MOUDLENAME, "checkMac",
					resNo+"dest:" + HexBin.encode(dest));
			SystemLog.warn(MOUDLENAME, "checkMac", resNo+"mac:" + HexBin.encode(mac));
			Integer failedTimes = commonCacheService.getPosMacMatchCache(resNo,
					posNo);
			if (failedTimes == null) {
				commonCacheService.addPosMacMatchCache(resNo, posNo,
						new Integer(1));
				SystemLog.warn(MOUDLENAME, "checkMac", "校验mac失败, 次数为1 ");
			} else if (failedTimes.intValue() + 1 <= SysConfig
					.getPosMatMatchFailTimes()) {
				commonCacheService.addPosMacMatchCache(resNo, posNo,
						failedTimes + 1);
				SystemLog.warn(MOUDLENAME, "checkMac", "校验mac失败, 次数为"
						+ (failedTimes + 1));
			} else {
				commonCacheService.removePosTokenCache(resNo, posNo);// 删除已签入缓存
				SystemLog.warn(MOUDLENAME, "checkMac",
						"校验mac失败次数超过限制，需重新签到.  次数为" + failedTimes + 1);
				throw new HsException(
						ErrorCodeEnum.POS_MATCH_MAC_FAILED_SIGNUP.getValue(),
						posNo
								+ ErrorCodeEnum.POS_MATCH_MAC_FAILED_SIGNUP
										.getDesc());
			}
			throw new HsException(
					ErrorCodeEnum.POS_MATCH_MAC_FAILED.getValue(), posNo
							+ ErrorCodeEnum.POS_MATCH_MAC_FAILED.getDesc());
		} else {
			commonCacheService.removePosMacMatchCache(resNo, posNo);// 比对成功清除失败次数
			// SystemLog.debug(MOUDLENAME, "checkMac", "校验mac成功，并删除缓存的校验失败次数 ");
		}

	}

	/**
	 * 批处理操作 ,一次批处理多个操作
	 * 
	 * @param execCommand
	 *            批处理命令
	 * @param params
	 *            批处理交互参数
	 * @return 批处理操作结果
	 */
	public Map<String, Object> batchExec(Set<String> execCommand,
			Map<String, Object> params) throws HsException {
		String resNo = (String) params.get("resNo");// 持卡人互生号
		if (StringUtils.isBlank(resNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数互生号为空");
		}
		if (execCommand.contains("checkResNoAndCode")) {// 检查互生卡号及卡暗码
			String code = (String) params.get("code");// 卡暗码
			checkResNoAndCode(resNo, code);
		}
		if (execCommand.contains("checkResNoAndLoginPwd")) {// 检查互生号及登陆密码
			String pwd = (String) params.get("pwd");// 登陆密码
			checkResNoAndLoginPwd(resNo, pwd);
		}
		if (execCommand.contains("checkResNoAndTradePwd")) {// 检查互生号及交易密码
			String tradePwd = (String) params.get("tradePwd");// 交易密码
			checkResNoAndTradePwd(resNo, tradePwd);
		}
		if (execCommand.contains("findCustIdByResNo")) {// 持卡人根据互生号查询客户号
			String custId = commonCacheService.findCustIdByResNo(resNo);
			CustIdGenerator.isCarderExist(custId, resNo);
			params.put("custId", custId);// 保存查询结果，以便返回
		}

		String entResNo = (String) params.get("entResNo");// 企业互生号
		if (execCommand.contains("checkAuth")) {// 设备鉴权
			String deviceNo = (String) params.get("deviceNo");// 设备编号
			String operator = (String) params.get("operator");// 操作员
			String type = params.get("type").toString();// 设备类型
			checkAuth(entResNo, deviceNo, operator, Integer.parseInt(type));
		}
		if (execCommand.contains("checkMac")) {// 消息安全检查
			String posNo = (String) params.get("posNo"); // pos机编号
			byte[] data = (byte[]) params.get("data"); // 校验数据
			byte[] mac = (byte[]) params.get("mac"); // mac数据
			checkMac(entResNo, posNo, data, mac);
		}
		if (execCommand.contains("findEntCustIdByEntResNo")) {// 企业互生号查企业客户号
			String entCustId = commonCacheService
					.findEntCustIdByEntResNo(entResNo);
			if (StringUtils.isBlank(entCustId)) {
				throw new HsException(
						ErrorCodeEnum.ENT_IS_NOT_FOUND.getValue(),
						ErrorCodeEnum.ENT_IS_NOT_FOUND.getDesc() + ",entResNo["
								+ entResNo + "]");
			}
			params.put("entCustId", entCustId);// 保存查询结果，以便返回或后续使用
		}
		if (execCommand.contains("searchEntStatusInfo")) {// 企业客户号查询企业状态信息
			String entCustId = (String) params.get("entCustId");
			AsEntStatusInfo asEntStatusInfo = asEntService
					.searchEntStatusInfo(entCustId);
			params.put("asEntStatusInfo", asEntStatusInfo);// 保存查询结果，以便返回
		}
		if (execCommand.contains("findPosByDeviceNo")) {// 查询pos机信息
			String deviceNo = (String) params.get("deviceNo");// 设备号
			AsPos asPos = deviceService.findPosByDeviceNo(entResNo, deviceNo);
			params.put("asPos", asPos); // 保存查询结果，以便返回
		}
		return params;
	}

	/**
	 * 消费者卡校验
	 * 
	 * @param resNo
	 *            互生号
	 * @param checkCode
	 *            校验码：卡暗码或者登陆密码
	 * @param isCheckPwd
	 *            是否使用登陆密码校验
	 * @return 校验通过返回客户号，否则抛异常
	 */
	public String checkCard(String resNo, String checkCode, boolean isCheckPwd) {
		if (isCheckPwd) {// 检查互生号及登陆密码
			return checkCardLoginPwd(resNo, checkCode);
		} else {// 检查互生号及卡暗码
			return checkCardCode(resNo, checkCode);
		}
	}

	/**
	 * 企业设备校验批处理 互生卡暗码校验,设备登陆鉴权 ,mac校验 ,密码解密，返回企业信息，返回pos机信息
	 * 
	 * @param vo
	 *            待校验参数
	 * @return 校验通过则返回校验结果（消费者客户号，企业状态信息,解密后密码，pos机信息）
	 */
	public AsDevCheckResult checkEntDevice(AsDevCheckParam vo)
			throws HsException {
		AsDevCheckResult ret = new AsDevCheckResult();// 待返回数据

		String entResNo = vo.getEntResNo();// 企业互生号
		String deviceNo = vo.getDeviceNO();
		if (vo.isPosSignIn()) {// pos签到
			ret.setPikmak(this.posSignIn(entResNo, deviceNo));
		} else {
			// 设备鉴权
			checkAuth(entResNo, deviceNo, vo.getOperator(), vo.getDeviceType());
			if (vo.isCheckMac()) {// 消息安全检查
				checkMac(entResNo, deviceNo, vo.getData(), vo.getMac());
			}
		}

		// 查询企业信息
		String entCustId = commonCacheService.findEntCustIdByEntResNo(entResNo);
		ret.setEntCustId(entCustId);
		if (StringUtils.isBlank(entCustId)) {
			throw new HsException(ErrorCodeEnum.ENT_IS_NOT_FOUND.getValue(),
					ErrorCodeEnum.ENT_IS_NOT_FOUND.getDesc() + ",entResNo["
							+ entResNo + "]");
		}
		AsEntStatusInfo asEntStatusInfo = asEntService
				.searchEntStatusInfo(entCustId);
		AsEntExtendInfo asEntExtendInfo = asEntService
				.searchEntExtInfo(entCustId);

		ret.setAsEntStatusInfo(asEntStatusInfo);
		ret.setStartResType(asEntExtendInfo.getStartResType());

		if (vo.isGetPosInfo() || vo.isPosSignIn()) {// 查询pos机信息
			AsPos asPos = deviceService.findPosByDeviceNo(entResNo, deviceNo);
			ret.setAsPos(asPos);
		}

		// 解密消费者输入密码
		if (vo.getPosPwd() != null) {
			byte[] pwdDecrypt = getDecrypt(deviceNo, entResNo, vo.getPosPwd()); // 密码解密
			ret.setPwdDecrypt(pwdDecrypt);
		}
		// 解密企业交易密码
		if (vo.getEntTradePwd() != null) {
			byte[] pwdDecrypt = getDecrypt(deviceNo, entResNo,
					vo.getEntTradePwd()); // 密码解密
			ret.setEntTradePwd(pwdDecrypt);
		}

		if (vo.isCheckCardCode()) {// 检查消费者互生卡暗码
			ret.setCustId(checkCardCode(vo.getResNo(), vo.getCode()));
		} else if (vo.getResNo() != null) {// 消费者卡校验及返回客户号
			AsHsCard card = validCard(vo.getResNo());
			ret.setCustId(card.getPerCustId());
		}

		return ret;
	}

	/**
	 * pos二维码解密校验
	 * @param key 解密秘钥
	 * @param qrStr 二维码内容
	 * @return 加密串解密后的明文串 
	 * &3位货币代码（49）&12位交易金额（4）&4位积分比例（48用法六）&12企业承兑积分额（48用法六）&12互生币金额（48用法六）&8位随机扰码（数字型字符串）&8位mac校验位&entId&entName
	 */
	public String parseQrTransBill(byte[] key, String qrStr) {

		
		byte[] decKey=key;//new byte[key.length];
//		for(int i=0;i<key.length;i++){
//			decKey[i]=key[i];
//		}
		String encData=qrStr.substring(48); //加密字符串
//		System.out.println("---------------decKey=["+Arrays.toString(decKey)+"]");
//		System.out.println("---------------encData=["+encData+"]");
		String decData=dec3Des(decKey,encData);//解密
//		System.out.println("---------------decData=["+decData+"]");
//		System.out.println("---------------QrStr="+qrStr);
		String[] params=qrStr.split("&");
//		System.out.println("---------------params.length="+params.length);
		String entResNo=params[1];
		String entCustId=commonCacheService.findEntCustIdByEntResNo(entResNo);
		String entName=null;
		EntStatusInfo ent= commonCacheService.searchEntStatusInfo(entCustId);
		if(ent!=null){
			entName=ent.getEntCustName();
		}
		decData+=("&"+entCustId);
		decData+=("&"+entName);

		return decData;
	}
	

	

	public static String dec3Des(byte[] ks,String data){
//		byte[] ks=key.getBytes();
		byte[] sourcebyte=null;
		try {
			byte[] out2=HexBin.decode(data);
			sourcebyte = new byte[out2.length];
			CoDecode.DES3decrypt(ks, out2, out2.length, sourcebyte);
			String dec1=new String(sourcebyte);
			return dec1;
		} catch (Exception e) {
			System.err.println(Arrays.toString(ks));
			System.err.println(data);
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args){
	
		
		byte[] ks2={112, 104, 22, 112, 121, 22, 112, 120, 23, 112, 120, 6, 112, 110, 110, 0};
		String data2="BH&06001020000&0002&000001&000019&20160330183911FA3418215EB776DBD8685CC45B7C6AF61DE5B4A8CF93E399C1818DFB528409BDE1141734B8C68F98BB9311F104E2833D06B4E4F9C09544B388119A6F19C059A4CAC1C108E60DC16493097258";
		String data=data2.substring(48);
		System.out.println(data);
		String s2=dec3Des(ks2,data);
		System.out.println(s2);
	
	}

}
