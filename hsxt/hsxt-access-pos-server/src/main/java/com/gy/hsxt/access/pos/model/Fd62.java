/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.constant.ReqMsg;
import com.gy.hsxt.access.pos.exception.PosException;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.bp.bean.BusinessPosUpgrade;


/**
 * @Package: com.gy.hsxt.access.pos.model  
 * @ClassName: Fd62 
 * @Description: 62域 asc LLL ascii  请求:签到终端设备信息，同步参数
 *                  应答:同步参数，应答错误信息   新版本新增查询互生卡姓名
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:07:17 
 * @version V1.0
 */
public class Fd62 extends Afd {

	private final String FILLSPACE = StringUtils.SPACE;//与新国都约定部分参数补空格 
	
	@Override
	public Object doRequestProcess(String messageId, String data, byte[] pversion) throws PosException {
		SystemLog.debug("Fd62","doRequestProcess(s,s,b)","data：" + data);
		
		//同步参数
		if (ReqMsg.SYNCPARAM.getReqId().equals(messageId)) {
			
			SyncParamPosIn syncParam = new SyncParamPosIn();
			//基础信息版本号
			String baseInfoVersion = null;
			//积分信息版本号
			String pointInfoVersion = null;
			//货币信息版本号
			String currencyInfoVersion = null;
			//国家代码版本号
			String countryCodeVersion = null;
			//企业信息版本号
			String entInfoVersion = null;
			//start--added by liuzh on 2016-06-24
			//抵扣券版本号
			String deductionVoucherVersion = null;
			//end--added by liuzh on 2016-06-24
			
			//2.0版
			if(Arrays.equals(pversion,PosConstant.POSVERSION20)){
				if (data.length() == PosConstant.SYNC_NEW_VERSION_TOTLEN) {
					
					
//					baseInfoVersion = "0";
//				    pointInfoVersion = "0";
//					currencyInfoVersion = "0";
//					countryCodeVersion = "0";//kend test
					
					
					
				    baseInfoVersion = data.substring(0, 4);
				    pointInfoVersion = data.substring(4, 8);
					currencyInfoVersion = data.substring(8, 12);
					countryCodeVersion = data.substring(12, 16);
					
					
					
					
				}else
					CommonUtil.checkState(true, "参数同步到pos机2.0异常，62域请求参数长度非法，应为16。", 
							PosRespCode.REQUEST_PACK_FORMAT);
				
			//3.0版
			}else if(Arrays.equals(pversion,PosConstant.POSVERSION30)) {
				if (data.length() == PosConstant.SYNC_VERSION30_TOTLEN) {

//				    baseInfoVersion = "0";
//				    entInfoVersion = "0";
//				    pointInfoVersion = "0";
//					currencyInfoVersion = "0";
//					countryCodeVersion = "0";//kend test

				    baseInfoVersion = data.substring(0, 4);
				    entInfoVersion = data.substring(4, 8);
				    pointInfoVersion = data.substring(8, 12);
					currencyInfoVersion = data.substring(12, 16);
					countryCodeVersion = data.substring(16, 20);

				}else
					CommonUtil.checkState(true, "参数同步到pos机3.0异常，62域请求参数长度非法，应为20。", 
							PosRespCode.REQUEST_PACK_FORMAT);
			}
			//start--added by liuzh on 2016-06-24
			//3.01版本
			else if(Arrays.equals(pversion,PosConstant.POSVERSION301)) {
				if (data.length() == PosConstant.SYNC_VERSION301_TOTLEN) {

				    baseInfoVersion = data.substring(0, 4);
				    entInfoVersion = data.substring(4, 8);
				    pointInfoVersion = data.substring(8, 12);
					currencyInfoVersion = data.substring(12, 16);
					countryCodeVersion = data.substring(16, 20);
					deductionVoucherVersion = data.substring(20, 24);
					
				}else{
					CommonUtil.checkState(true, "参数同步到pos机3.01异常，62域请求参数长度非法，应为24。", 
							PosRespCode.REQUEST_PACK_FORMAT);
				}
			}
			//end--added by liuzh on 2016-06-24
			else {
				CommonUtil.checkState(true, "参数同步到pos机异常，协议版本号非法。ver：" + Hex.encodeHexString(pversion), 
						PosRespCode.REQUEST_PACK_FORMAT);
			}
			
			syncParam.setBaseInfoVersion(baseInfoVersion);
			syncParam.setPointInfoVersion(pointInfoVersion);
			syncParam.setCurrencyInfoVersion(currencyInfoVersion);
			syncParam.setCountryCodeVersion(countryCodeVersion);
			syncParam.setEntInfoVersion(entInfoVersion);//3.0中新增
			//start--added by liuzh 2016-06-24
			syncParam.setDeductionVoucherVersion(deductionVoucherVersion);//3.01中新增
			//end--added by liuzh 2016-06-24
			
			return syncParam;
		}
		//固件升级检测
		else if (ReqMsg.POSUPGRADECHECK.getReqId().equals(messageId)) {
			return getPosUpgradeCheckReq(data);
		}
		
		//非参数同步到pos机的其余业务，直接转发原始字符串。二维码串存放于此。
		return data;
	}

	/**
	 * 解析请求:固件升级版本检测
	 * @param data
	 * @return
	 * @throws PosException
	 */
	public PosUpgradeCheckReq getPosUpgradeCheckReq(String data) throws PosException
	{
		//3个字节的长度值＋30个字节表示pos机设备型号+30个字节表示pos机机器号+20个字节表示当前固件版本号号
		//pos机设备型号
		String posDeviceType = null;
		//当前固件版本号
		String posCurrVerNo = null;
		//pos机机器号
		String posMachineNo = null;
		
		int dataLength = PosConstant.POS_DEVICE_TYPE_LEN + PosConstant.POS_MACHINE_NO_LEN + PosConstant.POS_UPGRADE_VER_NO_LENGTH;
		if (data.length() == dataLength) {
			int end = 0;
			posDeviceType = data.substring(end, end+=PosConstant.POS_DEVICE_TYPE_LEN);			
			posMachineNo = data.substring(end, end+=PosConstant.POS_MACHINE_NO_LEN);			
			posCurrVerNo = data.substring(end);

			posDeviceType = StringUtils.trim(posDeviceType);
			posMachineNo = StringUtils.trim(posMachineNo);
			posCurrVerNo = StringUtils.trim(posCurrVerNo);
			
		}else{
			CommonUtil.checkState(true, "pos固件升级版本检测异常，62域请求参数长度非法，应为" + dataLength, 
					PosRespCode.REQUEST_PACK_FORMAT);
		}
		
		PosUpgradeCheckReq posUpgradeCheckReq = new PosUpgradeCheckReq();
		posUpgradeCheckReq.setPosDeviceType(posDeviceType);
		posUpgradeCheckReq.setPosCurrVerNo(posCurrVerNo);
		posUpgradeCheckReq.setPosMachineNo(posMachineNo);
		
		return posUpgradeCheckReq;		
	}
	
	@Override
	public String doResponseProcess(String messageId, Object data, Object extend,byte[] pversion) throws PosException {
		SystemLog.debug("Fd62","doResponseProcess(s,o,o,b)","data：" + data);
		if (ReqMsg.SYNCPARAM.getReqId().equals(messageId)) {//同步参数
			
			//pos设备请求数据    版本状态
		    SyncParamPosIn syncParamIn = (SyncParamPosIn) extend;
		    //输出参数 已从后台获取
		    SyncParamPosOut syncParamOut = (SyncParamPosOut) data;
		    
		    //start--added by liuzh on 2016-05-06
			SystemLog.debug("Fd62","doResponseProcess(s,o,o,b)","syncParamIn:" + JSON.toJSONString(syncParamIn)
					+ ", syncParamOut:" + JSON.toJSONString(syncParamOut));
			//end--added by liuzh on 2016-05-06
			
		    //版本号必须有数据，如果无更新内容是请求版本号
            String baseInfoVersion = syncParamOut.getBaseInfoVersion();
            String pointInfoVersion = syncParamOut.getPointInfoVersion();
            String currencyInfoVersion = syncParamOut.getCurrencyInfoVersion();
            String countryCodeVersion = syncParamOut.getCountryCodeVersion();
            //3.0版新增
            String entInfoVersion = syncParamOut.getEntInfoVersion();
            //start--added by liuzh on 2016-06-24
            //3.01版新增
            String deductionVoucherInfoVersion = syncParamOut.getDeductionVoucherInfoVersion();
            //end--added by liuzh on 2016-06-24
            
            String entName = syncParamOut.getEntName();
            String entTypeName = syncParamOut.getEntTypeName();
            String servicePhone = syncParamOut.getServicePhone();
            String serviceWebSite = syncParamOut.getServiceWebSite();
            
            //3.0 之后没有商铺的参数，补空位
            String shopName = syncParamOut.getShopName();
            
            //充值限额参数
            BigDecimal creChargeMax = syncParamOut.getCreChargeMax();
            BigDecimal creChargeMin = syncParamOut.getCreChargeMin();
            BigDecimal reChargeMax = syncParamOut.getReChargeMax();
            BigDecimal reChargeMin = syncParamOut.getReChargeMin();
            //兑换率
            BigDecimal pointExchangeRate = syncParamOut.getPointExchangeRate();
            BigDecimal hsbExchangeRate = syncParamOut.getHsbExchangeRate();
            //积分比例
            BigDecimal[] pointRates = syncParamOut.getPointRates();
            
            //积分比例最值
            BigDecimal rateMaxVal = syncParamOut.getRateMaxVal();
            BigDecimal rateMinVal = syncParamOut.getRateMinVal();
            
            //“内容描述”部分 指明4部分（3.0为5部分） 0无更新 1有更新
            StringBuilder vBuilder = new StringBuilder("");
            StringBuilder baseBuilder = new StringBuilder("");
            StringBuilder pointBuilder = new StringBuilder("");
            StringBuilder currBuilder = new StringBuilder("");
            StringBuilder countryBuilder = new StringBuilder("");
            //3.0新增 企业信息
            StringBuilder entBuilder = new StringBuilder("");
            //start--added by liuzh on 2016-06-24
            //3.01新增抵扣券信息
            StringBuilder deductionVoucherBuilder = new StringBuilder("");
            //end--added by liuzh on 2016-06-24
            
            
            
            SystemLog.debug("Fd62","doResponseProcess(s,o,o,b)", "------------------------版本号比较信息：" + 
            		"\r\n BaseInfo in:" + syncParamIn.getBaseInfoVersion() + "; out:" + baseInfoVersion +
            		"\r\n CountryCode in:" + syncParamIn.getCountryCodeVersion() + "; out:" + countryCodeVersion + 
            		"\r\n CurrencyInfo in:" + syncParamIn.getCurrencyInfoVersion()  + "; out:" + currencyInfoVersion +
            		"\r\n EntInfo in:" + syncParamIn.getEntInfoVersion()  + "; out:" + entInfoVersion +
            		"\r\n PointInfo in:" + syncParamIn.getPointInfoVersion() + "; out:" + pointInfoVersion);//kend test
            
            
            
    		//start--added by liuzh on 2016-06-26
            SystemLog.debug("Fd62","doResponseProcess(s,o,o,b)", "------------------------版本号比较信息："
            			+ "\r\n DeductionVoucherInfo in:" + syncParamIn.getDeductionVoucherVersion() + "; out:" + deductionVoucherInfoVersion
            		);
    		//end--added by liuzh on 2016-06-26
    		
            //2.0版的参数拼装格式
            if(Arrays.equals(pversion,PosConstant.POSVERSION20)){
            	//拼接基础信息部分
                if (syncParamIn.getBaseInfoVersion().equals(baseInfoVersion)) {
                	vBuilder.append(PosConstant.ZERO_CHAR);
                    SystemLog.debug("Fd62","doResponseProcess(s,o,o,b)","参数同步到pos机2.0，组装应答报文--基础信息无更新");
                }else{
                	SystemLog.debug("Fd62","doResponseProcess(s,o,o,b)","参数同步到pos机2.0，组装应答报文--更新基础信息");
                    vBuilder.append(PosConstant.ONE_CHAR);
                    baseBuilder.append(baseInfoVersion);
                    
                    int cnCount = CommonUtil.getCnCount(null == entName? "":entName);
                    entName = StringUtils.leftPad(entName, 128 - cnCount,FILLSPACE);//补位（减中文个数）
                    
                    cnCount = CommonUtil.getCnCount(null == entTypeName? "":entTypeName);
                    entTypeName = StringUtils.leftPad(entTypeName, 16 - cnCount,FILLSPACE);     
                   
                    servicePhone = StringUtils.leftPad(servicePhone, 25, FILLSPACE);
                    serviceWebSite = StringUtils.leftPad(serviceWebSite, 30, FILLSPACE);//暂定网址不含中文
                    baseBuilder.append(entName)
                    			.append(entTypeName)
                    			.append(servicePhone)
                    			.append(serviceWebSite);
                    if(!StringUtils.isBlank(shopName)){//有可能没绑定商铺
                        cnCount = CommonUtil.getCnCount(null == shopName? "":shopName);
                        shopName = StringUtils.leftPad(shopName, 40 - cnCount,FILLSPACE);
                        baseBuilder.append(shopName);
                    }else{
                        baseBuilder.append(StringUtils.leftPad("", 40,FILLSPACE));
                    }
                    
                    baseBuilder.append(CommonUtil.fill0InMoney(creChargeMax))
                    		.append(CommonUtil.fill0InMoney(creChargeMin))
                    		.append(CommonUtil.fill0InMoney(reChargeMax))
                    		.append(CommonUtil.fill0InMoney(reChargeMin));
                }
                
                //拼接积分信息部分
                if(syncParamIn.getPointInfoVersion().equals(pointInfoVersion)){
                    vBuilder.append(PosConstant.ZERO_CHAR);
                    SystemLog.debug("Fd62","doResponseProcess(s,o,o,b)", "参数同步到pos机2.0，组装应答报文--积分信息无更新");
                }else{
                	//start--modified by liuzh on 2016-05-07   
                	//修改原因: 增加积分比例异常数据保护处理,避免积分比例异常数据下发到pos机,将导致pos机服务不可用.
                	/* 原代码逻辑 
                	SystemLog.debug("Fd62","doResponseProcess(s,o,o,b)", "参数同步到pos机2.0，组装应答报文--更新积分信息");
                    vBuilder.append(PosConstant.ONE_CHAR);
                    pointBuilder.append(pointInfoVersion);
                    int exchangeRateInt = pointExchangeRate.multiply(CommonUtil.rateDivisor).intValue();
                    int hsbExchangeRateInt = hsbExchangeRate.multiply(CommonUtil.rateDivisor).intValue();
                    
                    pointBuilder.append(StringUtils.leftPad(String.valueOf(exchangeRateInt), 
                    						PosConstant.AMOUNT_12_LENGTH, PosConstant.ZERO_CHAR))
                    			.append(StringUtils.leftPad(String.valueOf(hsbExchangeRateInt), 
                    						PosConstant.AMOUNT_12_LENGTH, PosConstant.ZERO_CHAR))
                    			.append(pointRates.length);
                    for(BigDecimal pr : pointRates){
                    	pointBuilder.append(CommonUtil.fill0InRate(pr));
                    }
                    */
                    int exchangeRateInt = pointExchangeRate.multiply(CommonUtil.rateDivisor).intValue();
                    int hsbExchangeRateInt = hsbExchangeRate.multiply(CommonUtil.rateDivisor).intValue();
                    
                	boolean isPointDataExcept = false;
                	StringBuilder pointDataBuilder = new StringBuilder("");
                	
                    for(BigDecimal pr : pointRates){
                    	//打印异常数据
                    	if(pr.compareTo(rateMaxVal)>0 || pr.compareTo(rateMinVal)<0) {
                        	SystemLog.info("Fd62","doResponseProcess", "积分比例数据异常:" + String.valueOf(pr)
                        			+ "|min:" + String.valueOf(rateMinVal) + ",max:" + String.valueOf(rateMaxVal)
                        			);
                        	isPointDataExcept = true;
                    	}
                    	pointDataBuilder.append(CommonUtil.fill0InRate(pr));
                    }
                    if(isPointDataExcept){
                       	//积分比例数据异常,不更新pos机积分比例数据
                    	SystemLog.debug("Fd62","doResponseProcess","参数同步到pos机2.0，组装应答报文--获取到的积分比例数据异常,不更新POS机积分信息");
                        vBuilder.append(PosConstant.ZERO_CHAR);
                    	pointDataBuilder = new StringBuilder("");
                    	pointBuilder = new StringBuilder("");
                    	
                    }else{
                    	SystemLog.debug("Fd62","doResponseProcess","参数同步到pos机2.0，组装应答报文--更新积分信息");
                        vBuilder.append(PosConstant.ONE_CHAR);        
                        pointBuilder.append(pointInfoVersion);
                        pointBuilder.append(StringUtils.leftPad(String.valueOf(exchangeRateInt), 
        						PosConstant.AMOUNT_12_LENGTH, PosConstant.ZERO_CHAR))
	        			.append(StringUtils.leftPad(String.valueOf(hsbExchangeRateInt), 
	        						PosConstant.AMOUNT_12_LENGTH, PosConstant.ZERO_CHAR))
	        			.append(pointRates.length);
                        
                        pointBuilder.append(pointDataBuilder);                                                
                    }
                    //end--modified by liuzh on 2016-05-07   
                    
                }
             //3.0版的参数拼装格式
            //start--modified by liuzh on 2016-06-24
            //}else if(Arrays.equals(pversion,PosConstant.POSVERSION30)){
            }else if(Arrays.equals(pversion,PosConstant.POSVERSION30) || Arrays.equals(pversion,PosConstant.POSVERSION301)){
            //end--modified by liuzh on 2016-06-24
            	
            	//拼接基础信息部分
                if (syncParamIn.getBaseInfoVersion().equals(baseInfoVersion)) {
                	vBuilder.append(PosConstant.ZERO_CHAR);
                    SystemLog.debug("Fd62","doResponseProcess(s,o,o,b)","参数同步到pos机3.0，组装应答报文--基础信息无更新");
                }else{
                	SystemLog.debug("Fd62","doResponseProcess(s,o,o,b)","参数同步到pos机3.0，组装应答报文--更新基础信息");
                    vBuilder.append(PosConstant.ONE_CHAR);
                    baseBuilder.append(baseInfoVersion);
                    
                    servicePhone = StringUtils.leftPad(servicePhone, 25, FILLSPACE);
                    serviceWebSite = StringUtils.leftPad(serviceWebSite, 30, FILLSPACE);//暂定网址不含中文
                    int exchangeRateInt = pointExchangeRate.multiply(CommonUtil.rateDivisor).intValue();
                    int hsbExchangeRateInt = hsbExchangeRate.multiply(CommonUtil.rateDivisor).intValue();
                    
                    baseBuilder.append(servicePhone)
        						.append(serviceWebSite)
        						.append(CommonUtil.fill0InMoney(creChargeMax))
        						.append(CommonUtil.fill0InMoney(creChargeMin))
        						.append(CommonUtil.fill0InMoney(reChargeMax))
        						.append(CommonUtil.fill0InMoney(reChargeMin))
                    			.append(StringUtils.leftPad(String.valueOf(exchangeRateInt), 
                    						PosConstant.AMOUNT_12_LENGTH, PosConstant.ZERO_CHAR))
                    			.append(StringUtils.leftPad(String.valueOf(hsbExchangeRateInt), 
                    						PosConstant.AMOUNT_12_LENGTH, PosConstant.ZERO_CHAR));
  
                }
                
                
                //拼接企业信息部分
                if (syncParamIn.getEntInfoVersion().equals(entInfoVersion)) {
                	vBuilder.append(PosConstant.ZERO_CHAR);
                    SystemLog.debug("Fd62","doResponseProcess(s,o,o,b)","参数同步到pos机3.0，组装应答报文--企业信息无更新");
                }else{
                	SystemLog.debug("Fd62","doResponseProcess(s,o,o,b)","参数同步到pos机3.0，组装应答报文--更新企业信息");
                	vBuilder.append(PosConstant.ONE_CHAR);
                	entBuilder.append(entInfoVersion);
                	
                    int cnCount = CommonUtil.getCnCount(null == entName? "":entName);
                    entName = StringUtils.leftPad(entName, 128 - cnCount,FILLSPACE);
                    cnCount = CommonUtil.getCnCount(null == entTypeName? "":entTypeName);
                    entTypeName = StringUtils.leftPad(entTypeName, 16 - cnCount,FILLSPACE);     

                    entBuilder.append(entName)
                    		  .append(entTypeName);
                    
                    if(!StringUtils.isBlank(shopName)){//有可能没绑定商铺
                        cnCount = CommonUtil.getCnCount(null == shopName? "":shopName);
                        shopName = StringUtils.leftPad(shopName, 40 - cnCount,FILLSPACE);
                        entBuilder.append(shopName);
                    }else{
                    	entBuilder.append(StringUtils.leftPad("", 40,FILLSPACE));
                    }
                    
                    int rateMaxValInt = null == rateMaxVal ? 0 : 
            			rateMaxVal.multiply(CommonUtil.rateDivisor).intValue();
                    int rateMinValInt = null == rateMaxVal ? 0 : 
            			rateMinVal.multiply(CommonUtil.rateDivisor).intValue();
                    
                    entBuilder.append(StringUtils.leftPad(String.valueOf(rateMaxValInt), 
    									PosConstant.RATE_LENGTH, PosConstant.ZERO_CHAR))
    						  .append(StringUtils.leftPad(String.valueOf(rateMinValInt), 
    									PosConstant.RATE_LENGTH, PosConstant.ZERO_CHAR));
                }
                
                //拼接积分信息部分
                if(syncParamIn.getPointInfoVersion().equals(pointInfoVersion)){
                    vBuilder.append(PosConstant.ZERO_CHAR);
                    SystemLog.debug("Fd62","doResponseProcess(s,o,o,b)","参数同步到pos机3.0，组装应答报文--积分信息无更新");
                }else{
                	//start--modified by liuzh on 2016-05-07   
                	//修改原因: 增加积分比例异常数据保护处理,避免积分比例异常数据下发到pos机,将导致pos机服务不可用.
                	/* 原代码逻辑 
                	SystemLog.debug("Fd62","doResponseProcess(s,o,o,b)","参数同步到pos机3.0，组装应答报文--更新积分信息");
                    vBuilder.append(PosConstant.ONE_CHAR);
                    pointBuilder.append(pointInfoVersion)
                    			.append(pointRates.length);
                    for(BigDecimal pr : pointRates){
                    	pointBuilder.append(CommonUtil.fill0InRate(pr));
                    }
                    */
                	
                	boolean isPointDataExcept = false;
                	StringBuilder pointDataBuilder = new StringBuilder("");
                    for(BigDecimal pr : pointRates){
                    	SystemLog.debug("Fd62","doResponseProcess", "积分比例:" + String.valueOf(pr));
                    	
                    	//打印异常数据
                    	if(pr.compareTo(rateMaxVal)>0 || pr.compareTo(rateMinVal)<0) {
                        	SystemLog.info("Fd62","doResponseProcess", "积分比例数据异常:" + String.valueOf(pr)
                        			+ "|min:" + String.valueOf(rateMinVal) + ",max:" + String.valueOf(rateMaxVal)
                        			);
                        	isPointDataExcept = true;
                    	}
                    	
                    	pointDataBuilder.append(CommonUtil.fill0InRate(pr));
                    }
                    if(isPointDataExcept) {
                    	//积分比例数据异常,不更新pos机积分比例数据
                    	SystemLog.debug("Fd62","doResponseProcess","参数同步到pos机3.0，组装应答报文--获取到的积分比例数据异常,不更新POS机积分信息");
                    	vBuilder.append(PosConstant.ZERO_CHAR);
                    	pointDataBuilder = new StringBuilder("");
                    	pointBuilder = new StringBuilder("");
                    }else{
                    	//积分比例数据正常,更新pos机积分比例数据
                    	SystemLog.debug("Fd62","doResponseProcess","参数同步到pos机3.0，组装应答报文--更新积分信息");
                    	vBuilder.append(PosConstant.ONE_CHAR);
                        pointBuilder.append(pointInfoVersion).append(pointRates.length);
                        pointBuilder.append(pointDataBuilder);                                            
                    }
                	//end--modified by liuzh on 2016-05-07
                }
 
            }else
            	CommonUtil.checkState(true, "参数同步到pos机异常，请求中的版本号pversion：" + 
            			Hex.encodeHexString(pversion) + "非法。", PosRespCode.REQUEST_PACK_FORMAT);
            
            //拼接货币信息部分 3.0无变化
            if(syncParamIn.getCurrencyInfoVersion().equals(currencyInfoVersion)){
                vBuilder.append(PosConstant.ZERO_CHAR);
                SystemLog.debug("Fd62","doResponseProcess(s,o,o,b)","参数同步到pos机，组装应答报文--币种信息无更新");
            }else{
            	SystemLog.debug("Fd62","doResponseProcess(s,o,o,b)","参数同步到pos机，组装应答报文--更新币种信息");
                vBuilder.append(PosConstant.ONE_CHAR);
                currBuilder.append(currencyInfoVersion);
                List<CountryCurrency> currencyList = syncParamOut.getCurrencyList();
                CountryCurrency countryCurrency = new CountryCurrency();
                //固定6条货币信息
                for (Iterator<CountryCurrency> iterator = currencyList.iterator(); iterator.hasNext();) {
                    countryCurrency = (CountryCurrency) iterator.next();
                    currBuilder.append(StringUtils.leftPad(countryCurrency.getCurrencyCodeIndex()+"", 
                    							2, PosConstant.ZERO_CHAR))
                    			.append(StringUtils.leftPad(countryCurrency.getCurrencyId(), 
                    							3, PosConstant.ZERO_CHAR))
                    			.append(StringUtils.leftPad(countryCurrency.getCurrencyCode(), 
                    							10, PosConstant.ZERO_CHAR));
                }
            }
            
            
            
            //拼接国家代码部分 3.0无变化
            if (syncParamIn.getCountryCodeVersion().equals(countryCodeVersion)) {
                vBuilder.append(PosConstant.ZERO_CHAR);
                SystemLog.debug("Fd62","doResponseProcess(s,o,o,b)","参数同步到pos机，组装应答报文--国家信息无更新");
            } else {
            	SystemLog.debug("Fd62","doResponseProcess(s,o,o,b)","参数同步到pos机，组装应答报文--更新国家信息");
                vBuilder.append(PosConstant.ONE_CHAR);
                countryBuilder.append(countryCodeVersion);
                List<Country> countryList = syncParamOut.getCountryList();
                String countryCountStr = String.valueOf(syncParamOut.getCountryCount());
                countryBuilder.append(StringUtils.leftPad(countryCountStr, 2, PosConstant.ZERO_CHAR));//更新个数
                Country country = new Country();int i=0;
                for (Iterator<Country> iterator = countryList.iterator(); iterator.hasNext();) {
                    country = (Country) iterator.next();
                    countryBuilder
                    	.append(StringUtils.leftPad(country.getCountryCodeIndex()+"", 3, PosConstant.ZERO_CHAR))
                    	.append(StringUtils.leftPad(country.getCountryCode(), 3, PosConstant.ZERO_CHAR))
                    	.append(country.getOperaCode());//0为删除 1为增加或修改 
                }

            }
            
            
            //start--added by liuzh on 2016-06-24
            //pos3.01版本新增,软件分版本号600301
            if(Arrays.equals(pversion,PosConstant.POSVERSION301)) {
            	
                SystemLog.debug("", "", "参数同步到pos，响应的参数串。 \r\n syncParamIn.getDeductionVoucherVersion()：" + syncParamIn.getDeductionVoucherVersion()
                		+ ",deductionVoucherInfoVersion:" + deductionVoucherInfoVersion);

                //commented by liuzh on 2016-06-24 为方便调试,改为每次更新抵扣券信息
	            //拼接抵扣券部分信息
                /*
	            if(syncParamIn.getDeductionVoucherVersion().equals(deductionVoucherInfoVersion)){
	                vBuilder.append(PosConstant.ZERO_CHAR);
	                SystemLog.debug("Fd62","doResponseProcess(s,o,o,b)","参数同步到pos机，组装应答报文--抵扣券信息无更新");
	            }else{
	            */	
	            	SystemLog.debug("Fd62","doResponseProcess(s,o,o,b)","参数同步到pos机，组装应答报文--更新抵扣券信息");
	                vBuilder.append(PosConstant.ONE_CHAR);
	                //以下拼接抵扣券信息             
	                //抵扣劵版本号
	                deductionVoucherBuilder.append(deductionVoucherInfoVersion);
	                //抵扣劵个数
	                deductionVoucherBuilder.append(syncParamOut.getDeductionVoucherCount());
	                //抵扣券张数列表
	                List<Integer> deductionVoucherCountList = syncParamOut.getDeductionVoucherCountList();   
	                for(Integer row:deductionVoucherCountList) {
	                	deductionVoucherBuilder.append(StringUtils.leftPad(String.valueOf(row.intValue()),
	                			PosConstant.DEDUCTION_VOUCHER_COUNT_LEN,PosConstant.ZERO_CHAR));
	                }
	                
	                //抵扣券面值
	                deductionVoucherBuilder.append(StringUtils.leftPad(syncParamOut.getDeductionVoucherParValue().toString(),
	            			PosConstant.DEDUCTION_VOUCHER_PAR_VALUE_LEN,PosConstant.ZERO_CHAR));
	                //抵扣劵金额占比                
	                deductionVoucherBuilder.append(CommonUtil.fill0InRate(syncParamOut.getDeductionVoucherRate()));
	            //}
            }
            SystemLog.debug("", "", "参数同步到pos，响应的参数串。 \r\n vBuilder：" + vBuilder);
            SystemLog.debug("", "", "参数同步到pos，响应的参数串。 \r\n deductionVoucherBuilder：" + deductionVoucherBuilder);
            //end--added by liuzh on 2016-06-24
                       
            
            if(Arrays.equals(pversion,PosConstant.POSVERSION20)){
            	vBuilder.append(baseBuilder).append(pointBuilder)
            			.append(currBuilder).append(countryBuilder);
            }else if(Arrays.equals(pversion,PosConstant.POSVERSION30)){
            	vBuilder.append(baseBuilder).append(entBuilder).append(pointBuilder)
    				.append(currBuilder).append(countryBuilder);
            }
            //start--added by liuzh on 2016-06-24
            else if(Arrays.equals(pversion,PosConstant.POSVERSION301)){
            	vBuilder.append(baseBuilder).append(entBuilder).append(pointBuilder)
    				.append(currBuilder).append(countryBuilder).append(deductionVoucherBuilder);
            }
            //end--added by liuzh on 2016-06-24
            
            SystemLog.debug("", "", "参数同步到pos，响应的参数串。 \r\n baseBuilder：" + baseBuilder +
            		" ; \r\n entBuilder:" + entBuilder +
            		" ; \r\n pointBuilder:" + pointBuilder +
            		" ; \r\n currBuilder:" + currBuilder + 
            		" ; \r\n countryBuilder:" + countryBuilder );//kend test
            
            return vBuilder.toString();
            
		}else if (ReqMsg.POSUPGRADECHECK.getReqId().equals(messageId)) {
			//固件升级检测
			BusinessPosUpgrade verInfo = (BusinessPosUpgrade)data;
			PosUpgradeCheckReq posUpgradeCheckReq = (PosUpgradeCheckReq)extend; 
			String resp = getPosUpgradeCheckResp(verInfo,posUpgradeCheckReq.getPosCurrVerNo());
			
			return resp;
			
		}else{
			CommonUtil.checkState(true, 
					"Fd62:doResponseProcess(s,o,o,b)，请求类型不是“参数同步 messageId = ”" + messageId, 
					PosRespCode.REQUEST_PACK_FORMAT);
		}
		return (String) data;
	}

	public String getPosUpgradeCheckResp(BusinessPosUpgrade verInfo,String posCurrVerNo)
	{
        String posDeviceType = verInfo.getPosDeviceType();
        
		boolean isUpgrade = false;
		String isForceUpgrade = verInfo.getIsForceUpgrade();
		String fileCrcCode = verInfo.getFileCrcCode();		
		String posUpgradeVerNo = verInfo.getPosUpgradeVerNo();
		String fileHttpUrl = verInfo.getFileHttpUrl();
		int fileLength = verInfo.getFileBytes();
		
        SystemLog.debug("Fd62", "doResponseProcess |　getPosUpgradeCheckResp()", "verInfo:" + 
        		JSON.toJSONString(verInfo));
        
		if(!posCurrVerNo.equals(posUpgradeVerNo)) {
			isUpgrade = true;
		} else {
			isUpgrade = false;
		}
		//start--added by liuzh on 2016-05-03
		/* commented by liuzh on 2016-06-03 版本不一致才需要更新
		if(isForceUpgrade!=null && isForceUpgrade.equals("Y")) {
			isUpgrade = true;
		}
		*/
		//end--added by liuzh on 2016-05-03
		
		/** 
		 * 赋值 报文62域
		 * 数据格式: 是否更新标记|版本强制更新标记|pos机设备型号|需更新的固件版本号|文件CRC校验码|固件下载的http地址
		 */
		String fd62 = null;
		if(isUpgrade){
			fd62 = String.format("%s|%s|%s|%s|%s|%s|%s", 
					isUpgrade?"Y":"N",
					isForceUpgrade,
					posDeviceType,
					posUpgradeVerNo,
					fileCrcCode,
					fileHttpUrl,
					fileLength
					);
		}else{
			fd62 = "N|N&N|N&N|N&N|N&N|N&N|N&N";
		}	
		
        SystemLog.debug("Fd62", "doResponseProcess | getPosUpgradeCheckResp()", "fd62:" + fd62);
        
		return fd62;
	}
}
