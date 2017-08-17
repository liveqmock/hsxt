/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsSignInInfo;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolder;
import com.gy.hsxt.uc.as.bean.consumer.AsHsCard;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameReg;
import com.gy.hsxt.uc.as.bean.device.AsDevCheckParam;
import com.gy.hsxt.uc.as.bean.device.AsDevCheckResult;
import com.gy.hsxt.uc.as.bean.device.AsPos;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;
import com.gy.hsxt.uc.as.bean.enumerate.AsDeviceTypeEumn;
import com.gy.hsxt.uc.as.bean.result.AsPosSignInResult;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.service  
 * @ClassName: UcApiService 
 * @Description: 用户中心接口封装
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:14:35 
 * @version V1.0
 */
public interface UcApiService {


	/**
	 * 客户号查询
	 * 根据企业互生号查找企业客户号
	 * 
	 * @param entResNo	企业资源号
	 * @return
	 * @throws HsException
	 */
	public String findEntCustIdByEntResNo(String entResNo) throws HsException;

	/**
	 * 企业状态查询
	 * 
	 * @param custId	企业客户号
	 * @return
	 * @throws HsException
	 */
	public AsEntStatusInfo searchEntStatusInfo(String custId) throws HsException;

	/**
	 * 企业状态查询
	 * @param entResNo 企业资源号
	 * @return
	 * @throws HsException
	 */
	public AsEntStatusInfo searchEntStatusInfoByResNo(String entResNo) throws HsException;
	
	
	/**
	 * 查询企业一般信息
	 * @author   wucl	
	 * 2015-11-4 下午5:05:43
	 * @param entCustId
	 * @return
	 * @throws HsException
	 */
	public AsEntBaseInfo searchEntBaseInfo(String entCustId) throws HsException;

	/**
	 * 查询客户号
	 * 
	 * 根据互生号查找客户号
	 * 
	 * @param perResNo
	 * @return
	 * @throws HsException
	 */
	public String findCustIdByResNo(String perResNo) throws HsException;
	
	/**
     * 根据互生号查询互生卡相关的信息
     * 
     * @param resNo
     *            互生号
     * @return 
     */
    public AsHsCard searchHsCardInfoByResNo(String resNo)throws HsException;
    
    
    /**
     * 查询持卡人相关信息()
     * @param resNo
     * @return
     * @throws HsException
     */
    public AsCardHolder searchCardHolderInfoByResNo(String resNo) throws HsException;
    
    
    /**
     * 持卡人注册相关信息查询
     * @return
     * @throws HsException
     */
    public AsRealNameReg searchRealNameRegInfo(String custId)throws HsException;
    
    
    /**
     * 查询持卡人名称
     * @param custId
     * @return
     * @throws HsException
     */
    public String searchCardNameByCustId(String custId) throws HsException;
	
	
	/**
	 * 更新积分比例
	 * @param entResNo 
	 * @param deviceType   设备类型
	 * @param deviceNo 设备号
	 * @param pointRate    积分比例
	 * @param operator 操作员
	 * @throws HsException
	 */
    public void updatePointRate(String entResNo, AsDeviceTypeEumn deviceType, String deviceNo, String[] pointRate,
            String operator) throws HsException;
    
    
    /**
     * 校验企业8位交易密码
     * @param entCustId 企业客户号
     * @param tradePwd MD5处理后的密码
     * @throws HsException
     */
    public void checkEntTradePwd(String entCustId, String tradePwd) throws HsException;

	/**
	 * 校验挑一密码
	 * 
	 * @param custId
	 * @param tradePwd
	 * @param userType
	 * @throws HsException
	 */
	//public void checkTradePwd(String custId, String tradePwd, UserTypeEnum userType, String secretKey) throws HsException;

	/**
	 * POS机签到
	 * 
	 * @author wucl 2015-11-3 下午5:29:23
	 * @param siginInfo
	 * @return
	 * @throws HsException
	 */
	public byte[] posSignIn(AsSignInInfo siginInfo) throws HsException;
	
	
	/**
	 * 
	 * @author   wucl	
	 * 2015-11-9 下午3:51:34
	 * @param entResNo
	 * @param deviceNo
	 * @param operator
	 * @param type
	 * @throws HsException
	 */
	public void deviceLogout(String entNo, String posNo, String operNo, int type) throws HsException;

	
	
	/**
	 * 设备鉴权
	 * @author   wucl	
	 * 2015-11-6 下午2:14:56
	 * @param entResNo
	 * @param posNo
	 * @param operator
	 * @param type
	 * @throws HsException
	 */
	public void checkAuth(String entResNo, String posNo, String operator, int type) throws HsException;

	/**
	 * 查询pos 机信息
	 * 
	 * @author wucl 2015-11-4 上午10:14:57
	 * @param entResNo
	 * @param deviceNo
	 * @return
	 * @throws HsException
	 */
	public AsPos findPosByDeviceNo(String entResNo, String deviceNo) throws HsException;
	
	/**
	 * 验证资源号和暗码，如果不匹配抛出异常
	 * @param resNo
	 * @param code
	 * @throws HsException
	 */
	public void checkResNoAndCode(String resNo, String code)throws HsException ;
	
	/**
	 * 验证资源号和登录密码，如果不匹配抛出异常
	 * @param resNo 资源号
	 * @param pwd  登录密码
	 * @throws HsException
	 */
	public void checkResNoAndLoginPwd(String resNo, String pwd) throws HsException ;
	
	/**
     * 验证资源号、登录密码和暗码是否匹配，如果不匹配抛出异常
     * @param resNo 资源号
     * @param pwd 登录密码
     * @param code 暗码
     * @throws HsException
     */
    public void checkResNoPwdAndCode(String resNo, String pwd, String code) throws HsException ;


    /**
     * 验证资源号和支付密码是否匹配
     * @param resNo 资源号
     * @param chargePwd 支付密码
     * @throws HsException
     */
    public void checkResNoAndTradePwd(String resNo, String tradePwd) throws HsException  ;

    
	/**
	 * 加密 pin
	 * 
	 * @author wucl 2015-11-4 上午9:36:19
	 * @param posNo
	 * @param entNo
	 * @param cnt
	 * @return
	 * @throws HsException
	 */
	public byte[] getEncrypt(String posNo, String entNo, byte[] cnt) throws HsException;
	
	/**
	 * 解密 pin
	 * @author   wucl	
	 * 2015-11-9 下午3:22:12
	 * @param posNo
	 * @param entResNo
	 * @param cnt
	 * @return
	 * @throws HsException
	 */
	public byte[] getDecrypt(String posNo, String entNo, byte[] cnt) throws HsException;
	
	
	/**
     * 校验MAC
     * @param resNo 资源号
     * @param posNo POS机编号
     * @param mac MAC
     * @param data 数据
     */
    public void checkMac(String resNo, String posNo, byte[] data, byte[] mac) throws HsException;
    
    /**
     * 
     * @param resNo
     * @param posNo
     * @param data
     * @return
     * @throws HsException
     */
    public byte[] genMac(String resNo, String posNo, byte[] data) throws HsException;
    
    /**
     * 校验版本号
     * @param info
     * @return
     * @throws HsException
     */
    public AsPosSignInResult checkVersion (AsSignInInfo info)throws HsException;
    
    /**
     * 设备接入交互参数校验
     * @param param
     * @return
     * @throws HsException
     */
    public AsDevCheckResult checkEntDevice(AsDevCheckParam param)throws HsException;
    
    /**
     * pos机生成的交易单据二维码解密及校验
     * @param key 16位长的byte数组作为密钥
     * @param qrStr 格式为:（2位字母数字）类型&11位企业互生号&4位pos终端编号&6位批次号&6位pos机凭证号&14位日期时间（YYYYMMDDhh24mmss）+密文
     * @return
     * @throws HsException
     */
    public String parseQrTransBill(byte[] key, String qrStr) throws HsException;

}
