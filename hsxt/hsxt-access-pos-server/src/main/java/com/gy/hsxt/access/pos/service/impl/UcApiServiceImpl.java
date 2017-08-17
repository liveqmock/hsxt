/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.service.UcApiService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.common.IUCAsAuthService;
import com.gy.hsxt.uc.as.api.common.IUCAsDeviceSignInService;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderAuthInfoService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.api.device.IUCAsDeviceService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
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
 * @Package: com.gy.hsxt.access.pos.service.impl  
 * @ClassName: UcApiServiceImpl 
 * @Description: 
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:13:50 
 * @version V1.0
 */
@Service("ucApiService")
public class UcApiServiceImpl implements UcApiService {

	// 鉴权
	@Autowired
	@Qualifier("ucAsAuthService")
	private IUCAsAuthService ucAsAuthService;
	
	// 企业信息
	@Autowired
	@Qualifier("ucAsEntService")
	private IUCAsEntService ucAsEntService;
	
	
	@Autowired
	@Qualifier("ucAsCardHolderService")
	private IUCAsCardHolderService ucAsCardHolderService;
	
	//密码服务
	@Autowired
	@Qualifier("ucPwdService")
	private IUCAsPwdService ucPwdService;
	
	//签到服务
	@Autowired
	@Qualifier("ucAsDeviceSignInService")
	private IUCAsDeviceSignInService ucAsDeviceSignInService;
	
	//设备信息
	@Autowired
	@Qualifier("ucAsDeviceService")
	private IUCAsDeviceService ucAsDeviceService;
	
	@Autowired
    @Qualifier("ucAsCardHolderAuthInfoService")
	private IUCAsCardHolderAuthInfoService ucAsCardHolderAuthInfoService;
	

	@Override
	public String findEntCustIdByEntResNo(String entResNo) throws HsException{
		SystemLog.debug("UcApiServiceImpl", "findEntCustIdByEntResNo()", "用户中心，请求：entResNo=" + entResNo);
	    String entCustNo = ucAsEntService.findEntCustIdByEntResNo(entResNo);
	    SystemLog.debug("UcApiServiceImpl", "findEntCustIdByEntResNo()", "返回：entCustNo=" + entCustNo);
        return entCustNo;
	}

	
	@Override
	public AsEntStatusInfo searchEntStatusInfo(String custId)
			throws HsException {
        SystemLog.debug("UcApiServiceImpl", "searchEntStatusInfo()", "用户中心，请求：custId=" + custId);
        AsEntStatusInfo entInfo = ucAsEntService.searchEntStatusInfo(custId);
        SystemLog.debug("UcApiServiceImpl", "searchEntStatusInfo()", "返回：entInfo=" + JSON.toJSONString(entInfo));
		return entInfo;
	}
	
	@Override
	public AsEntStatusInfo searchEntStatusInfoByResNo(String entResNo)
			throws HsException {
	    SystemLog.debug("UcApiServiceImpl", "searchEntStatusInfoByResNo()", "用户中心，请求：entResNo=" + entResNo);
        AsEntStatusInfo entInfo = ucAsEntService.searchEntStatusInfo(findEntCustIdByEntResNo(entResNo));
        SystemLog.debug("UcApiServiceImpl", "searchEntStatusInfoByResNo()", "返回：entInfo=" + JSON.toJSONString(entInfo));
		return entInfo;
	}
	
	@Override
	public AsEntBaseInfo searchEntBaseInfo(String entCustId) throws HsException {
		SystemLog.debug("UcApiServiceImpl", "searchEntBaseInfo()", "用户中心，请求：entCustId=" + entCustId);
	    AsEntBaseInfo entInfo = ucAsEntService.searchEntBaseInfo(entCustId);
	    SystemLog.debug("UcApiServiceImpl", "searchEntBaseInfo()", "返回：entInfo=" + JSON.toJSONString(entInfo));
		return entInfo;
	}
	
	@Override
	public String findCustIdByResNo(String resNo){
		SystemLog.debug("UcApiServiceImpl", "findCustIdByResNo()", "用户中心，请求：resNo=" + resNo);
	    String custId = ucAsCardHolderService.findCustIdByResNo(resNo);
	    SystemLog.debug("UcApiServiceImpl", "findCustIdByResNo()", "返回：custId=" + custId);
		return custId;
	}
	
	@Override
	public AsHsCard searchHsCardInfoByResNo(String resNo)throws HsException{
		SystemLog.debug("UcApiServiceImpl", "searchHsCardInfoByResNo()", "用户中心，请求：resNo=" + resNo);
	    AsHsCard ahc = ucAsCardHolderService.searchHsCardInfoByResNo(resNo);
	    SystemLog.debug("UcApiServiceImpl", "searchHsCardInfoByResNo()", "返回：ahc=" + JSON.toJSONString(ahc));
	    return ahc;
	}
	
	@Override
	public AsCardHolder searchCardHolderInfoByResNo(String resNo) throws HsException{
		SystemLog.debug("UcApiServiceImpl", "searchCardHolderInfoByResNo()", "用户中心，请求：resNo=" + resNo);
	    AsCardHolder ach = ucAsCardHolderService.searchCardHolderInfoByResNo(resNo);
	    SystemLog.debug("UcApiServiceImpl", "searchCardHolderInfoByResNo()", "返回：ach=" + JSON.toJSONString(ach));
	    return ach;
	}
	
	@Override
	public AsRealNameReg searchRealNameRegInfo(String custId)throws HsException{
		SystemLog.debug("UcApiServiceImpl", "searchCardHolderInfoByResNo()", "用户中心，请求：custId=" + custId);
        AsRealNameReg arnr = ucAsCardHolderAuthInfoService.searchRealNameRegInfo(custId);
        SystemLog.debug("UcApiServiceImpl", "searchCardHolderInfoByResNo()", "返回：arnr=" + JSON.toJSONString(arnr));
        return arnr;
	}
	
	@Override
	public String searchCardNameByCustId(String custId) throws HsException{
	    AsRealNameReg cardInfo = searchRealNameRegInfo(custId);
	    return null == cardInfo ? null : cardInfo.getRealName();
	}
	
	@Override
	public void updatePointRate(String entResNo, AsDeviceTypeEumn deviceType, String deviceNo, String[] pointRate,
            String operator) throws HsException{
	    SystemLog.debug("UcApiServiceImpl", "updatePointRate()", "用户中心，请求：entResNo="+entResNo+"  deviceType="+deviceType+" deviceNo="+deviceNo+" pointRate="+JSON.toJSONString(pointRate)+" operator="+operator);
	    ucAsDeviceService.updatePointRate(entResNo, deviceType.getType(), deviceNo, pointRate, operator);
	}
	
	@Override
	public void checkEntTradePwd(String entCustId, String tradePwd) throws HsException{
		SystemLog.debug("UcApiServiceImpl", "checkEntTradePwd()", "用户中心，请求：entCustId=" + entCustId + 
					"  tradePwd=" + tradePwd);
		ucPwdService.checkEntTradePwd(entCustId, tradePwd);
	}


	@Override
	public byte[] posSignIn(AsSignInInfo siginInfo) throws HsException {
	    SystemLog.debug("UcApiServiceImpl", "posSignIn()", "用户中心，请求：siginInfo="+ JSON.toJSONString(siginInfo));
		return ucAsDeviceSignInService.posSignIn(siginInfo);
	}
	

	@Override
	public void deviceLogout(String entNo, String posNo, String operNo,
			int type) throws HsException {
	    SystemLog.debug("UcApiServiceImpl", "deviceLogout()", "用户中心，请求：entNo="+entNo+"  posNo="+posNo+" operNo="+operNo+" type="+type);
	    ucAsDeviceSignInService.deviceLogout(entNo, posNo, operNo, type);
	}
	
	@Override
	public void checkAuth(String entResNo, String posNo, String operator,
			int type) throws HsException {
		SystemLog.debug("UcApiServiceImpl", "checkAuth()", "用户中心，请求：entResNo="+entResNo+"  posNo="+posNo+" operator="+operator+" type="+type);
		ucAsDeviceSignInService.checkAuth(entResNo, posNo, operator, type);
	}
	
	@Override
	public AsPos findPosByDeviceNo(String entResNo, String deviceNo) throws HsException {
		SystemLog.debug("UcApiServiceImpl", "findPosByDeviceNo()", "用户中心，请求：entResNo="+entResNo+"  deviceNo="+deviceNo);
	    AsPos ap = ucAsDeviceService.findPosByDeviceNo(entResNo, deviceNo);
	    SystemLog.debug("UcApiServiceImpl", "findPosByDeviceNo()", "返回：ap=" + JSON.toJSONString(ap));
	    return ap;
	}
	
	@Override
    public void checkResNoAndCode(String resNo, String code) throws HsException {
		SystemLog.debug("UcApiServiceImpl", "checkResNoAndCode()", "用户中心，请求：resNo="+resNo+"  code="+code);
	    ucAsDeviceSignInService.checkResNoAndCode(resNo, code);
    }
	
	public void checkResNoAndLoginPwd(String resNo, String pwd) throws HsException {
		SystemLog.debug("UcApiServiceImpl", "checkResNoAndLoginPwd()", "用户中心，请求：resNo="+resNo+"  pwd="+pwd);
	    ucAsDeviceSignInService.checkResNoAndLoginPwd(resNo, pwd);
	}


    @Override
    public void checkResNoPwdAndCode(String resNo, String pwd, String code) throws HsException {
    	SystemLog.debug("UcApiServiceImpl", "checkResNoPwdAndCode()", "用户中心，请求：resNo="+resNo+"  pwd="+pwd+" code="+code);
        ucAsDeviceSignInService.checkResNoPwdAndCode(resNo, pwd, code);
    }


    @Override
    public void checkResNoAndTradePwd(String resNo, String tradePwd) throws HsException {
        SystemLog.debug("UcApiServiceImpl", "checkResNoAndTradePwd()", "用户中心，请求：resNo="+resNo+"  tradePwd="+tradePwd);
        ucAsDeviceSignInService.checkResNoAndTradePwd(resNo, tradePwd);
    }	

	@Override
	public byte[] getEncrypt(String posNo, String entNo, byte[] cnt) throws HsException {
		SystemLog.debug("UcApiServiceImpl", "getEncrypt()", "用户中心，请求：posNo="+posNo+"  entNo="+entNo+" cnt="+JSON.toJSONString(cnt));
	    byte[] ary = ucAsDeviceSignInService.getEncrypt(posNo, entNo, cnt);
	    SystemLog.debug("UcApiServiceImpl", "getEncrypt()", "返回：ary:" + JSON.toJSONString(ary));
	    return ary;
	}


	@Override
	public byte[] getDecrypt(String posNo, String entNo, byte[] cnt)
			throws HsException {
	    SystemLog.debug("UcApiServiceImpl", "getDecrypt()", "用户中心，请求：posNo="+posNo+"  entNo="+entNo+" cnt="+JSON.toJSONString(cnt));
        byte[] ary = ucAsDeviceSignInService.getDecrypt(posNo, entNo, cnt);
        SystemLog.debug("UcApiServiceImpl", "getDecrypt()", "返回：ary:" + JSON.toJSONString(ary));
        return ary;
	}
	
	@Override
	public void checkMac(String resNo, String posNo, byte[] data, byte[] mac) throws HsException{
	    
		SystemLog.debug("UcApiServiceImpl", "checkMac()", "用户中心，请求：resNo="+resNo+"  posNo="+posNo+" data="+JSON.toJSONString(data)+" mac="+JSON.toJSONString(mac));
        ucAsDeviceSignInService.checkMac(resNo, posNo, data, mac);
	}

	@Override
	public byte[] genMac(String resNo, String posNo, byte[] data) throws HsException{
		SystemLog.debug("UcApiServiceImpl", "genMac()", "用户中心，请求：posNo="+posNo+"  resNo="+resNo+" data="+JSON.toJSONString(data));
        byte[] ary = ucAsDeviceSignInService.getMac(resNo, posNo, data);
        SystemLog.debug("UcApiServiceImpl", "genMac()", "返回：ary:" + JSON.toJSONString(ary));
        return ary;
	}
	
	@Override
	public AsPosSignInResult checkVersion (AsSignInInfo info)throws HsException{
		SystemLog.debug("UcApiServiceImpl", "checkVersion()", "用户中心，请求：info=" + JSON.toJSONString(info));
	    AsPosSignInResult result = ucAsDeviceSignInService.checkVersion(info);
	    SystemLog.debug("UcApiServiceImpl", "checkVersion()", "返回：result=" + JSON.toJSONString(result));
        return result;
	}
	
	@Override
	public AsDevCheckResult checkEntDevice(AsDevCheckParam param)throws HsException{
		SystemLog.debug("UcApiServiceImpl", "checkEntDevice()", "用户中心，请求：param=" + JSON.toJSONString(param));
        AsDevCheckResult ret = ucAsDeviceSignInService.checkEntDevice(param);
        SystemLog.debug("UcApiServiceImpl", "checkEntDevice()", "返回：ret=" + JSON.toJSONString(ret));
        return ret;
	}
	
	@Override
	public String parseQrTransBill(byte[] key, String qrStr) throws HsException{
		SystemLog.debug("UcApiServiceImpl", "parseQrTransBill()", "用户中心，请求：key="+ new String(key) + ",qrStr=" + qrStr);
        String ret = ucAsDeviceSignInService.parseQrTransBill(key, qrStr);
        SystemLog.debug("UcApiServiceImpl", "parseQrTransBill()", "返回：ret=" + JSON.toJSONString(ret));
        return ret;
	}
	
}

	