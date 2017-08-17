/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.services.consumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.common.IUCAsMobileService;
import com.gy.hsxt.uc.as.api.common.IUCAsNetworkInfoService;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.common.IUCAsReceiveAddrInfoService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsNoCardHolderService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.bean.common.AsNetworkInfo;
import com.gy.hsxt.uc.as.bean.common.AsNoCarderMainInfo;
import com.gy.hsxt.uc.as.bean.common.AsReceiveAddr;
import com.gy.hsxt.uc.as.bean.consumer.AsNoCardHolder;

/***************************************************************************
 * <PRE>
 * Description 		: 非持卡用户的操作服务类
 * 
 * Project Name   	: hsxt-access-web-person 
 * 
 * Package Name     : com.gy.hsxt.access.web.person.services.hsc  
 * 
 * File Name        : CardholderService 
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-11-16 下午6:40:59
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-11-16 下午6:40:59
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v1.0
 * 
 * </PRE>
 ***************************************************************************/
@Service
public class NoCardholderService extends BaseServiceImpl implements INoCardholderService {

    
    /**
     * 用户中心-手机服务dubbo
     */
    @Autowired
    private IUCAsNoCardHolderService noCardHolderService;
    
    @Autowired
    private IUCAsMobileService  ucAsMobileService;
    
    /**
     * 网络信息
     */
    @Autowired
    private IUCAsNetworkInfoService ucCAsNetworkInfoService;
    
    /**
     * 交易密码
     */
    @Autowired
    private IUCAsPwdService ucAsPwdService;
    
    /**
     * 收货地址服务类
     */
    @Autowired
    private IUCAsReceiveAddrInfoService ucAsReceiveAddrInfoService;
    /**
     * @param custId
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.person.services.consumer.ICardholderService#findMobileByCustId(java.lang.String)
     */
    @Override
    public Map<String, String> findMobileByCustId(String custId) throws HsException {
    	AsNoCardHolder asNoCardHolder = null;
        Map<String, String> retMap = new HashMap<String, String>();
        String isBind = "1"; // 是否绑定手机号 1:绑定 0：未绑定
        String mobile = ""; // 手机号码
        try
        {
        	asNoCardHolder = noCardHolderService.searchNoCardHolderInfoByCustId(custId);
        	if(asNoCardHolder != null){
        		mobile = asNoCardHolder.getMobile();
        	}
        }
        catch (HsException e)
        {
            // 未绑定手机
            if (e.getErrorCode() == ErrorCodeEnum.MOBILE_NOT_CHECK.getValue())
            {
                isBind = "0";
            }
            else
            {
                throw e;
            }
        }
        retMap.put("isBind", isBind);
        retMap.put("mobile", mobile);
        return retMap;
    }
    /**
     * 获取持卡人邮箱绑定状态
     * @param custId
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.person.services.consumer.ICardholderService#findEamilByCustId(java.lang.String)
     */
    @Override
    public Map<String, String> findEamilByCustId(String custId) throws HsException {
    	Map<String,String> resultMap = new HashMap<>();
    	AsNoCardHolder noCarder = noCardHolderService.searchNoCardHolderInfoByCustId(custId);
    	resultMap.put("isAuthEmail", noCarder.getIsAuthEmail().toString());
		resultMap.put("email", StringUtils.nullToEmpty(noCarder.getEmail()));
    	return resultMap;
    	
    }

	@Override
	public String checkSmsCode(String mobile, String smsCode) throws HsException {
		return ucAsMobileService.checkSmsCode(mobile, smsCode);
	}
	
	/**
     * 根据客户号查询网络信息详情
     * @param custId 客户号
     * @return
     * @throws HsException
     */
	@Override
	public AsNetworkInfo findNetworkInfoByCustId(String custId) throws HsException {
		return this.ucCAsNetworkInfoService.searchByCustId(custId);
	}
	
	/**
     * 修改网络信息
     * @param netWorkInfo
     * @throws HsException
     */
	@Override
	public void updateNetworkInfo(AsNetworkInfo netWorkInfo) throws HsException {
		this.ucCAsNetworkInfoService.updateNetworkInfo(netWorkInfo);
	}
	
	/**
     * 发送手机验证码(重置登录密码) 
     * @param custId 客户好
     * @param mobile 手机号
     * @throws HsException
     */
	public void sendSmsCodeForResetPwd(String custId, String mobile) throws HsException{
		this.ucAsMobileService.sendNoCarderSmscode(mobile);
	}
	
	/**
	 * 重置交易密码身份验证
	 * @param perCustId 客户号
	 * @param email 邮件信息
	 * @param loginPassword 登录密码
	 * @param secretKey	随机token
	 * @return
	 * @throws HsException
	 */
	@Override
	public void changeBindEmail(String perCustId, String email,String loginPassword, String secretKey)  throws  HsException {
		 this.noCardHolderService.changeBindEmail(perCustId, email, loginPassword, secretKey);
	}
	
	/**
	 * 重置交易密码身份验证
	 * @param mainInfo
	 * @return
	 * @throws HsException
	 */
	public  String  checkNoCarderMainInfo(AsNoCarderMainInfo mainInfo) throws  HsException{
		return this.ucAsPwdService.checkNoCarderMainInfo(mainInfo);
	}
	
	/**
	 * 6.1.17.10.重置非持卡人交易密码
	 * @param mobile 手机号
	 * @param random 随机数
	 * @param newTradePwd 新交易密码
	 * @param secretKey  AES秘钥
	 * @throws HsException
	 */
	public  void  resetNoCarderTradepwd(String  mobile,String  random,String  newTradePwd, String  secretKey )throws HsException{
		this.ucAsPwdService.resetNoCarderTradepwd(mobile, random, newTradePwd, secretKey);
	}
	
	/**
	 * 查询收货地址
	 * @param custId 客户号
	 * @return
	 * @throws HsException
	 */
	public  List<AsReceiveAddr> findReceiveAddrByCustId(String custId ) throws  HsException{
		return ucAsReceiveAddrInfoService.listReceiveAddrByCustId(custId);
	}
	
	/**
	 * 查询收货地址详情
	 * @param custId 客户号
	 * @param addrId 收获地址ID
	 * @return
	 * @throws HsException
	 */
	public  AsReceiveAddr findReceiveAddrInfo(String custId ,Long  addrId) throws  HsException{
		return ucAsReceiveAddrInfoService.searchReceiveAddrInfo(custId, addrId) ;
	}
	
	/**
	 * 设置默认收货地址
	 * @param custId 客户号
	 * @param addrId 收获地址ID
	 * @return
	 * @throws HsException
	 */
	public  void setDefaultAddr(String custId ,Long  addrId) throws  HsException{
		 ucAsReceiveAddrInfoService.setDefaultReceiveAddr(custId, addrId) ;
	}
	
	/**
	 *  删除收货地址信息
	 * @param custId 客户号
	 * @param addrId 收获地址ID
	 * @return
	 * @throws HsException
	 */
	public  void deleteReceiveAddr (String custId ,Long  addrId) throws  HsException{
		 ucAsReceiveAddrInfoService.deleteReceiveAddr (custId, addrId) ;
	}
	/**
	 * 修改收货地址
	 * @param custId 客户号
	 * @param addr 收货地址实体
	 * @return
	 * @throws HsException
	 */
	public void updateReceiveAddr(String custId ,AsReceiveAddr  addr) throws  HsException{
		if(addr != null && addr.getAddrId() != null){
			ucAsReceiveAddrInfoService.updateReceiveAddr(custId, addr);	
		}else{
			ucAsReceiveAddrInfoService.addReceiveAddr(custId, addr);
		}
		
	}
}
