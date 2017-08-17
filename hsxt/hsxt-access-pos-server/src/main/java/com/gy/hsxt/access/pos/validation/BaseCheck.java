/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.validation;

import java.util.Arrays;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.json.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosReqTypeEnum;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.constant.ReqMsg;
import com.gy.hsxt.access.pos.exception.PosException;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.service.UcApiService;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.access.pos.util.PosUtil;
import com.gy.hsxt.uc.as.bean.device.AsDevCheckParam;
import com.gy.hsxt.uc.as.bean.device.AsDevCheckResult;
import com.gy.hsxt.uc.as.bean.device.AsDevice.AsDeviceStatusEnum;
import com.gy.hsxt.uc.as.bean.device.AsPos;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;
import com.gy.hsxt.uc.as.bean.enumerate.AsDeviceTypeEumn;
		
/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.validation  
 * @ClassName: BaseCheck 
 * @Description: 基础信息检查
 *
 * @author: weiyq 
 * @date: 2015-12-29 下午2:45:22 
 * @version V1.0
 */
@Service("baseCheck")
public class BaseCheck implements Command {
	
	@Autowired
	@Qualifier("ucApiService")
	private UcApiService ucApiService;

	/**
	 * 目前仅适用部分高频率的交易 如：消费积分，消费积分冲正，消费积分撤单
	 */
	@Override
	public boolean execute(Context context) throws Exception {
		
		SystemLog.info("Pos","BaseCheck","检查基础信息");
		
		Cmd cmd = (Cmd)context.get("cmd");
		//MAC校验的数据
		PosReqParam reqParam = cmd.getPosReqParam();
        String entResNo = reqParam.getEntNo();
        String posNo = reqParam.getPosNo();
        byte[] macData = reqParam.getMacDat();
        if (macData == null) {
            throw new PosException(PosRespCode.MAC_CHECK, "MAC校验失败，MAC串为空");
            //return true;
        }
        
        byte[] typeId = cmd.getTypeId();
        // 含消息 含64域
        byte[] requestBody = cmd.getBody();
        
        //去掉64域
        byte[] newBody = new byte[requestBody.length - 8];
        System.arraycopy(requestBody, 0, newBody, 0, requestBody.length - 8);
        
        byte[] dataByte = new byte[typeId.length + newBody.length];
        
        int count = 0;
        System.arraycopy(typeId, 0, dataByte, count, typeId.length);
        
        count += typeId.length;
        System.arraycopy(newBody, 0, dataByte, count, newBody.length);
        //MAC校验的数据
        
		String reqTypeId = cmd.getReqId();
		PosReqTypeEnum reqType = PosReqTypeEnum.getTypeByTypeId(reqTypeId);
		
		String inputWay = reqParam.getInputWay();
		String isPin = reqParam.getIsPin();
		String resNo = "";
		String code = "";
		
		/**
		 * 是否校验交易密码
		 */
		boolean checkTradePwd = false;
		boolean checkLoginPwd = false;
		AsDevCheckParam param = new AsDevCheckParam();
		
		if(PosConstant.INPUT_WAY_STRIPE.equals(inputWay) && 
				reqType.getInputWay().contains(PosConstant.INPUT_WAY_STRIPE_CONTENT)){
		    //刷卡 卡号从磁道二（35域）获取
		    String cardNo = reqParam.getStripe2();
            CommonUtil.checkState(cardNo.length() != PosConstant.CARDNO_CIPHER_LENGTH, 
            		"8583报文异常, 刷卡长度不符合,cardNo：" + 
            		cardNo, PosRespCode.REQUEST_PACK_FORMAT);
            resNo = StringUtils.left(cardNo, PosConstant.CARDNO_LENGTH);
            code = StringUtils.right(cardNo, PosConstant.CIPHER_LENGTH);
            reqParam.setCardNo(resNo);
            
            //刷卡要求校验暗码
            param.setCheckCardCode(true);
            if(PosConstant.PIN_1.equals(isPin)){
                //有密码
                param.setPosPwd(reqParam.getPinDat());
                if(reqType.getPinWay().contains(PosConstant.LOGIN_PIN)){
                    //登录密码
                    checkLoginPwd = true;
                }else if(reqType.getPinWay().contains(PosConstant.TRADE_PIN)){
                    //交易密码
                    checkTradePwd = true;
                    
                }else{
                    throw new PosException(PosRespCode.REQUEST_PACK_FORMAT,"密码校验方式有误");
                }
            }else if(PosConstant.PIN_2.equals(isPin)&&reqType.getPinWay().contains(PosConstant.NO_PIN)){
                //没有密码
            }else{
                throw new PosException(PosRespCode.REQUEST_PACK_FORMAT,"密码校验方式有误");
            }
            
		}else if(PosConstant.INPUT_WAY_MANUAL.equals(inputWay) && reqType.getInputWay().contains(PosConstant.INPUT_WAY_MANUAL_CONTENT)){
		    //手输卡号
		    String cardNo = reqParam.getCardNo();
            CommonUtil.checkState(cardNo.length() != PosConstant.CARDNO_LENGTH,"8583报文异常, 手输卡长度不符合,cardNo：" + cardNo, PosRespCode.REQUEST_PACK_FORMAT);
            resNo = cardNo;
            //手输卡号不校验暗码
            param.setCheckCardCode(false);
            
            if(PosConstant.PIN_1.equals(isPin)){
                //有密码
                param.setPosPwd(reqParam.getPinDat());
                if(reqType.getPinWay().contains(PosConstant.LOGIN_PIN)){
                    //登录密码
                    checkLoginPwd = true;
                }else{
                    throw new PosException(PosRespCode.REQUEST_PACK_FORMAT,"密码校验方式有误");
                }
            }else if(PosConstant.PIN_2.equals(isPin)&&reqType.getPinWay().contains(PosConstant.NO_PIN)){
                //没有密码
            }else{
                throw new PosException(PosRespCode.REQUEST_PACK_FORMAT,"密码校验方式有误");
            }
		    
		}else if(PosConstant.INPUT_WAY_0.equals(inputWay) && reqType.getInputWay().contains(PosConstant.INPUT_WAY_0_CONTENT)){
		    //企业兑换互生币 卡号赋值为企业互生号？？？
		    resNo = entResNo;
		}else if(inputWay==null && reqType.getInputWay().contains(PosConstant.INPUT_WAY_0_CONTENT)){
		    resNo = reqParam.getCardNo();
		}else{
		    throw new PosException(PosRespCode.REQUEST_PACK_FORMAT,"密码校验方式有误");
		}
		String pwd = "";
		/*
		
		if(PosConstant.PIN_1.equals(isPin)){
            
            //每次签到生成的pik/mak不一样，生成的pin码和mac码会发生改变
            byte[] pinData = reqParam.getPinDat();
            
            CommonUtil.checkState(null == pinData || pinData.length != 8,"8583报文异常, 交易中包含PIN格式异常！pinDat: " + pinData, PosRespCode.PIN_FORMAT);
            
            // 调用 uc 解密 pin
//          byte[] arrGyPin = this.decrypt(entNo, posNo, operNo, pinData, cmd.getPartVersion());
            byte[] arrGyPin = ucApiService.getDecrypt(posNo, entResNo, pinData);
            
            CommonUtil.checkState(arrGyPin == null || arrGyPin.length != 8, "调用key server解密接口返回密钥格式异常！arrGyPin:" + arrGyPin, PosRespCode.POS_CENTER_FAIL);
            String servicePinLen = reqParam.getServicePinLen();//密码最大明文长度
            
            if (StringUtils.isEmpty(servicePinLen)) {
                SystemLog.warn("servicePinLen isEmpty..","","");
            }
            
            pwd = PosUtil.decryptWithANSIFormat(arrGyPin, resNo, servicePinLen);
            if (ReqMsg.HSBPAY.getReqId().equals(reqTypeId)
                    || ReqMsg.HSORDERHSBPAY.getReqId().equals(reqTypeId)
                    || ReqMsg.HSBBRECHARGE.getReqId().equals(reqTypeId)) {//交易密码放到action上，不能漏过
                CommonUtil.checkState(pwd == null || pwd.length() != 8, "密码长度有误", PosRespCode.CHECK_CARDORPASS_FAIL);
            } else {
                CommonUtil.checkState(pwd == null || pwd.length() != 6, "密码长度有误", PosRespCode.CHECK_CARDORPASS_FAIL);
            }
            
        } else if (PosConstant.PIN_2.equals(isPin)) {//交易中不包含PIN
        } else {
            throw new PosException(PosRespCode.REQUEST_PACK_FORMAT, "无效的其他服务点输入方式码异常！");
        }
		*/
		SystemLog.debug("Pos BaseCheck ","","加密报文数据============" + Hex.encodeHexString(dataByte));
		//组装报文
		param.setResNo(resNo);
		param.setCode(code);
		param.setEntResNo(entResNo);
		param.setDeviceNO(posNo);
		param.setDeviceType(AsDeviceTypeEumn.POS.getType());
		param.setOperator(reqParam.getOperNo());
		param.setData(dataByte);
		param.setMac(macData);
		param.setCheckMac(reqType.isMacCheck());
		param.setCheckCardPwd(checkLoginPwd);
		param.setGetPosInfo(true);
		
		AsDevCheckResult result = ucApiService.checkEntDevice(param);
		
		if(checkTradePwd || checkLoginPwd){
		    byte[] arrGyPin = result.getPwdDecrypt();
		    pwd = getPwd(reqParam, reqTypeId, resNo, arrGyPin);
		}
		
		String custId = result.getCustId();
		AsEntStatusInfo entInfo = result.getAsEntStatusInfo();
		
		CommonUtil.checkState(null == entInfo, "无此企业信息：" + entResNo, PosRespCode.NO_FOUND_ENT);
        CommonUtil.checkState(entInfo.getIsClosedEnt().equals(PosConstant.POS_TRUE), "企业已被冻结：" + 
        		entResNo, PosRespCode.CHECK_ACCOUNT_FREEZE);
        
        /**
         * @author XXX wucl create_date 2015-11-10 下午3:27:28
         * 
         * 基本状态   1：正常(NORMAL)  成员企业、托管企业用
                    2：预警(WARNING) 成员企业、托管企业用
                    3：休眠(DORMANT) 成员企业用
                    4：长眠（申请注销）成员企业
                    5：已注销 成员企业
                    6：申请停止积分活动中  托管企业用
                    7：停止积分活动    托管企业用
         */
        CommonUtil.checkState(entInfo.getBaseStatus() == 7, "企业基础状态异常：" + 
        		entResNo, PosRespCode.NO_ENT_CARDPOINT);
        CommonUtil.checkState(entInfo.getBaseStatus() == 5, "企业已被注销：" + 
        		entResNo, PosRespCode.NO_FOUND_ENT);
      //pos 设备校验
//        AsPos posInfo = ucApiService.findPosByDeviceNo(entResNo, posNo);
        AsPos posInfo = result.getAsPos();
        
        CommonUtil.checkState(null == posInfo, "没找到POS设备, 编号：" + entResNo + 
        		posNo, PosRespCode.NO_FOUND_DEVICE);
        SystemLog.debug("Pos BaseCheck ","pos信息" , JSON.json(entInfo));
        /**
         * @author wucl create_date 2015-11-10 下午3:56:45
         * 
         *    可用      ENABLED(1),
                            禁用       DISABLED(2),
                            返修       REPAIRED(3),
                            冻结          LOCKED(4);
         */
        CommonUtil.checkState(posInfo.getStatus() != AsDeviceStatusEnum.ENABLED.getValue(), 
        		"pos机状态异常, 编号：" + entResNo + posNo, PosRespCode.POS_STATUS);
        
        cmd.getTmpMap().put("entStatusInfo", entInfo);
        cmd.getTmpMap().put("posInfo", posInfo);
        //企业资源类型（申报资源类型） 1：首段资源  2：创业资源  3：全部资源 4：正常成员企业 5：免费成员企业
        //用以识别是否为免费成员企业 ，最小积分比例与非免费企业不同。
        CommonUtil.checkState(null == result.getStartResType(), "从uc获取企业资源类型为null, 企业互生号：" + 
        		entResNo, PosRespCode.POS_CENTER_FAIL);
        cmd.getTmpMap().put("startResType", result.getStartResType());
        
        if(checkLoginPwd){
        	SystemLog.debug("BaseCheck ","execute" , "消费者登录密码校验，resNo：" + resNo + "；pwd：" + pwd);
            // 校验登录密码
            //ucApiService.checkResNoAndLoginPwd(resNo, CommonUtil.string2MD5(pwd));
        	ucApiService.checkResNoAndLoginPwd(resNo, pwd);// 登录密码不要md5，交易密码仍要，但CommonUtil.string2MD5（）中算法要改
        }else if(checkTradePwd){
        	SystemLog.debug("BaseCheck ","execute" , "消费者支付密码校验，resNo：" + resNo + "；pwd：" + pwd);
            //校验 支付密码
            ucApiService.checkResNoAndTradePwd(resNo, CommonUtil.string2MD5(pwd));
        }
        
		return false;
	}
	
	private String getPwd(PosReqParam reqParam,String reqTypeId,String resNo, byte[] arrGyPin) throws PosException{
        CommonUtil.checkState(arrGyPin == null || arrGyPin.length != 8, "调用key server解密接口返回密钥格式异常！arrGyPin:" + 
        		Arrays.toString(arrGyPin), PosRespCode.POS_CENTER_FAIL);
        //密码最大明文长度
        String servicePinLen = reqParam.getServicePinLen();
        
        if (StringUtils.isEmpty(servicePinLen)) {
            SystemLog.warn("servicePinLen isEmpty..","servicePinLen isEmpty..","servicePinLen isEmpty..");
        }
        
        
        SystemLog.debug("BaseCheck", "decryptWithANSIFormat", "---------------arrGyPin:" + 
        		Arrays.toString(arrGyPin) + ";resNo:" + resNo + ";servicePinLen:"+ servicePinLen);//kend test
		
        
        String pwd = PosUtil.decryptWithANSIFormat(arrGyPin, resNo, servicePinLen);
        if (ReqMsg.HSBPAY.getReqId().equals(reqTypeId)
                || ReqMsg.HSORDERHSBPAY.getReqId().equals(reqTypeId)
                || ReqMsg.HSBENTRECHARGE.getReqId().equals(reqTypeId)) {
            //交易密码放到action上，不能漏过
            CommonUtil.checkState(pwd == null || pwd.length() != 8, "密码长度有误", PosRespCode.CHECK_CARDORPASS_FAIL);
        } else {
            CommonUtil.checkState(pwd == null || pwd.length() != 6, "密码长度有误", PosRespCode.CHECK_CARDORPASS_FAIL);
        }
        return pwd;
    }
}

	