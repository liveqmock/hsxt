/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.api.common;

import java.util.Map;
import java.util.Set;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsSignInInfo;
import com.gy.hsxt.uc.as.bean.device.AsDevCheckParam;
import com.gy.hsxt.uc.as.bean.device.AsDevCheckResult;
import com.gy.hsxt.uc.as.bean.result.AsCardReaderSignInResult;
import com.gy.hsxt.uc.as.bean.result.AsPadSignInResult;
import com.gy.hsxt.uc.as.bean.result.AsPosSignInResult;

/**
 * 设备签入
 * 
 * @Package: com.gy.hsxt.uc.as.api.common
 * @ClassName: IUCAsDeviceSignInService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-10-19 下午12:22:23
 * @version V1.0
 */
public interface IUCAsDeviceSignInService {

	/**
	 * 检查企业信息版本号和POS积分版本号，如果不一致返回相应信息
	 * 
	 * @param signInInfo
	 * @return
	 * @throws HsException
	 */
	public AsPosSignInResult checkVersion(AsSignInInfo signInInfo)
			throws HsException;

	/**
	 * POS签入，签入失败返回异常，成功无返回
	 * 
	 * @param siginInfo
	 *            POS签入信息
	 * @return
	 * @throws HsException
	 */
	public byte[] posSignIn(AsSignInInfo signInInfo) throws HsException;

	/**
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
	 */
	public void deviceLogout(String entResNo, String deviceNo, String operator,
			int type) throws HsException;

	/**
	 * 刷卡器签入
	 * 
	 * @param signInfo
	 *            签入信息
	 * @return
	 * @throws HsException
	 */
	public AsCardReaderSignInResult cardReaderSignIn(AsSignInInfo signInInfo)
			throws HsException;

	/**
	 * 平板签入
	 * 
	 * @param siginInfo签入信息
	 * @return
	 * @throws HsException
	 */
	public AsPadSignInResult padSignIn(AsSignInInfo signInInfo)
			throws HsException;

	/**
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
	 */
	public byte[] getEncrypt(String posNo, String entResNo, byte[] cnt)
			throws HsException;

	/**
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
	 */
	public byte[] getDecrypt(String posNo, String entResNo, byte[] cnt)
			throws HsException;

	/**
	 * 验证PMK
	 * 
	 * @param posNo
	 *            设备编号
	 * @param entResNo
	 *            企业资源号
	 * @param operator
	 *            操作员名称
	 * @param pmk
	 *            设备加密后的PMK
	 * @return
	 * @throws HsException
	 */
	public boolean checkPmk(String posNo, String entResNo, String operator,
			byte[] pmk) throws HsException;

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
	 */
	public void checkAuth(String entResNo, String posNo, String operator,
			int type) throws HsException;

	/**
	 * 验证资源号和暗码，如果不匹配抛出异常
	 * 
	 * @param resNo
	 *            资源号
	 * @param code
	 *            暗码
	 */
	public void checkResNoAndCode(String resNo, String code) throws HsException;

	/**
	 * 验证资源号、登录密码和暗码是否匹配，如果不匹配抛出异常
	 * 
	 * @param resNo
	 *            资源号
	 * @param pwd
	 *            登录密码
	 * @param code
	 *            暗码
	 * @throws HsException
	 */
	public void checkResNoPwdAndCode(String resNo, String pwd, String code)
			throws HsException;

	/**
	 * 验证资源号和支付密码是否匹配
	 * 
	 * @param resNo
	 *            资源号
	 * @param chargePwd
	 *            支付密码，使用MD5加密
	 * @throws HsException
	 */
	public void checkResNoAndTradePwd(String resNo, String tradePwd)
			throws HsException;

	/**
	 * 验证资源号、登录密码和暗码是否匹配，如果不匹配抛出异常
	 * 
	 * @param resNo
	 * @param pwd
	 *            登录密码，使用MD5加密
	 * @throws HsException
	 */
	public void checkResNoAndLoginPwd(String resNo, String pwd)
			throws HsException;

	/**
	 * 检查刷卡器和互生卡是否可用
	 * @param entResNo 企业互生号
	 * @param readerNo 刷卡器编号
	 * @param resNo 持卡人的互生卡号
	 * @param code 持卡人互生卡号的暗码
	 */
	public void checkCardAndReader(String entResNo, String readerNo, String resNo, String code) throws HsException;
	
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
	public void checkMac(String resNo, String posNo, byte[] data, byte[] mac)
			throws HsException;

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
	 */
	public byte[] getEncryptCnt(String posNo, String entResNo, byte[] cnt)
			throws HsException;

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
	 */
	public byte[] getDecryptCnt(String posNo, String entResNo, byte[] cnt)
			throws HsException;

	/**
	 * 获取mac
	 * 
	 * @param resNo
	 *            企业资源号
	 * @param posNo
	 *            POS机编号
	 * @param data
	 *            加密内容
	 * @return
	 * @throws HsException
	 */
	public byte[] genMac(String resNo, String posNo, byte[] data)
			throws HsException;

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
	public byte[] getMac(String resNo, String posNo, byte[] data);

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
			Map<String, Object> params);
	
	/**
	 * 企业设备校验批处理 
	 * 互生卡暗码校验,设备登陆鉴权 ,mac校验 ,密码解密，返回企业信息，返回pos机信息
	 * @param vo
	 *            待校验参数
	 * @return 校验通过则返回校验结果（消费者客户号，企业状态信息,解密后密码，pos机信息）
	 */
	public AsDevCheckResult checkEntDevice(AsDevCheckParam vo) throws HsException;
	
	/**
	 * pos二维码解密校验
	 * @param key 解密秘钥
	 * @param QrStr 二维码内容
	 * @return 加密串解密后的明文串 
	 * &3位货币代码（49）&12位交易金额（4）&4位积分比例（48用法六）&12企业承兑积分额（48用法六）&12互生币金额（48用法六）&8位随机扰码（数字型字符串）&8位mac校验位
	 */
	public String parseQrTransBill(byte[] key, String QrStr);
}
