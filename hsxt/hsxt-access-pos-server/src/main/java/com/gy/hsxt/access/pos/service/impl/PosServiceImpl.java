/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.EntTypeEnum;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.exception.PosException;
import com.gy.hsxt.access.pos.model.Country;
import com.gy.hsxt.access.pos.model.CountryCurrency;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.model.SyncParamPosIn;
import com.gy.hsxt.access.pos.model.SyncParamPosOut;
import com.gy.hsxt.access.pos.model.VersionAdapter;
import com.gy.hsxt.access.pos.service.BpApiService;
import com.gy.hsxt.access.pos.service.PosService;
import com.gy.hsxt.access.pos.service.UcApiService;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uc.as.api.util.CommonUtil;
import com.gy.hsxt.uc.as.bean.common.AsPosBaseInfo;
import com.gy.hsxt.uc.as.bean.common.AsPosEnt;
import com.gy.hsxt.uc.as.bean.common.AsPosInfo;
import com.gy.hsxt.uc.as.bean.common.AsPosPointRate;

@Service("posService")
public class PosServiceImpl implements PosService {

	@Autowired
	@Qualifier("ucApiService")
	private UcApiService ucApiService;
	
	//start--added by liuzh on 2016-06-24
	@Autowired
	@Qualifier("bpApiService")
	private BpApiService bpApiService;
	//end--added by liuzh on 2016-06-24
	
	@Autowired
	@Qualifier("lcsClient")
    public LcsClient lcsClient;
	
	@Autowired
    @Qualifier("commonUtil")
	public CommonUtil commonUtil;

	@Override
	public SyncParamPosOut doPosSyncParamWork(PosReqParam reqParam,
			byte[] pversion) throws PosException{
		SystemLog.info("PosServiceImpl", "doPosSyncParamWork()", "entering method.");
	    SyncParamPosOut syncParamPosOut = new SyncParamPosOut();
	    
	    SyncParamPosIn serviceIn = (SyncParamPosIn) reqParam.getCustom2();

		String baseInfoVersion = serviceIn.getBaseInfoVersion();
		String pointVersion = serviceIn.getPointInfoVersion();
		String currencyInfoVersion = serviceIn.getCurrencyInfoVersion();
		String countryCodeVersion = serviceIn.getCountryCodeVersion();
		//企业信息版本  3.0新增
		String entInfoVersion = null == serviceIn.getEntInfoVersion() ? 
							baseInfoVersion : serviceIn.getEntInfoVersion();
		
		try {
			//1 国家信息处理
			//根据版本号返回所有大于此版本号的记录,结果清单已根据每条记录的版本号从小到大排列。
			List<com.gy.hsxt.lcs.bean.Country> countryList = lcsClient
								.findCountryForDelta(Long.parseLong(countryCodeVersion));
	        List<Country> retCountryList = new ArrayList<Country>();
	        SystemLog.info("PosServiceImpl", "doPosSyncParamWork()", "获取增量国家版本信息：" + 
	        		JSON.toJSONString(countryList) + " \r\n； pos中版本号：" + countryCodeVersion);
            long countryCodeVersionLong = Long.parseLong(countryCodeVersion);
	        long maxVersion = countryCodeVersionLong;
	        com.gy.hsxt.lcs.bean.Country aCountry = new com.gy.hsxt.lcs.bean.Country();
	        //不能超过50条
	        for(int i = 0; i < countryList.size() && i < 50 ; i++){
	        	//获取的都是比pos中版本号大的且根据版本号从小到大排列好的清单
	        	aCountry = countryList.get(i);
	        	Country country = new Country();
	        	country.setCountryCodeIndex(Integer.parseInt(aCountry.getCountryNo()));
	        	country.setCountryCode(aCountry.getCountryNo());
	        	//lcs中 1：删除   ； pos中0为删除 1位增加或修改
	        	country.setOperaCode(aCountry.getDelFlag() == 1L ? "0" : "1");
	        	retCountryList.add(country);
	        }
	        
	        //检索到更新数据后，取最后一个的版本号即可
	        if(countryList.size() != 0) maxVersion = aCountry.getVersion();
	            
	        SystemLog.info("PosServiceImpl", "doPosSyncParamWork()", "获取国家版本信息----maxVersion:" + 
	        		maxVersion + " ; countryCodeVersionLong:" + countryCodeVersionLong);//kend test

			//判断国家信息版本是否一致
			if (maxVersion == countryCodeVersionLong) {
				syncParamPosOut.setCountryCodeVersion(countryCodeVersion);
				SystemLog.info("PosServiceImpl", "doPosSyncParamWork()", "国家版本信息无变化");
			} else {
				syncParamPosOut.setCountryCodeVersion(StringUtils.leftPad(maxVersion + "", 4, '0'));
				syncParamPosOut.setCountryList(retCountryList);
				syncParamPosOut.setCountryCount(retCountryList.size());
				SystemLog.info("PosServiceImpl", "doPosSyncParamWork()", "提取了新的国家版本信息。版本号将更新为：" +
									syncParamPosOut.getCountryCodeVersion());
			}
			
			
			//2 货币信息处理
			List<com.gy.hsxt.lcs.bean.CountryCurrency> currencyList = lcsClient.queryCountryCurrency();//返回值最多只有6个，无需限制
			SystemLog.info("PosServiceImpl", "doPosSyncParamWork()", "获取常用货币版本信息：" + 
																	JSON.toJSONString(currencyList));
			List<CountryCurrency> retCurrencyList = new LinkedList<CountryCurrency>();
			int currencyInfoVersionInt = Integer.parseInt(currencyInfoVersion);
            int maxCurrencyVersion = currencyInfoVersionInt;
            for(com.gy.hsxt.lcs.bean.CountryCurrency item : currencyList ){
            	CountryCurrency obj = new CountryCurrency();
                obj.setCurrencyCodeIndex(item.getCurrencySeqNo());
                obj.setCurrencyId(item.getCurrencyNo());
                obj.setCurrencyCode(item.getCurrencyCode());//CNY
                obj.setVersion(item.getVersion());
                maxCurrencyVersion = (int) (maxCurrencyVersion>item.getVersion()?
                					maxCurrencyVersion:item.getVersion());
                retCurrencyList.add(obj);
            }
            
            SystemLog.info("PosServiceImpl", "doPosSyncParamWork()", "获取货币版本信息----maxCurrencyVersion：" + 
            		maxCurrencyVersion + " ; currencyInfoVersionInt:" + currencyInfoVersionInt);//kend test
            
            //判断货币信息版本是否一致
			if (maxCurrencyVersion<=currencyInfoVersionInt) {
				syncParamPosOut.setCurrencyInfoVersion(currencyInfoVersion);
				SystemLog.info("PosServiceImpl", "doPosSyncParamWork()", "货币版本信息无变化");
			} else {
				syncParamPosOut.setCurrencyInfoVersion(StringUtils.leftPad(maxCurrencyVersion+"",4, '0'));
				syncParamPosOut.setCurrencyList(retCurrencyList);
				SystemLog.info("PosServiceImpl", "doPosSyncParamWork()", "提取了新的货币版本信息");
			}
			
			
			//3 基础信息处理
			String entResNo = reqParam.getEntNo();
	        String posId = reqParam.getPosNo();
	        AsPosInfo posInfo = commonUtil.getPosInfoForVersion(entResNo,posId);
	        SystemLog.info("PosServiceImpl", "doPosSyncParamWork()", "从用户中心获取版本信息：" + 
	        														JSON.toJSONString(posInfo));
	        AsPosEnt ent = posInfo.getPosEnt();
	        AsPosPointRate pointRate = posInfo.getPointRate();
	        AsPosBaseInfo baseInfo = posInfo.getBaseInfo();
	        
	        String servVersion = StringUtils.leftPad(ent.getVersion() + pointRate.getVersion(), 4, '0');
	        
	        //start--added by liuzh on 2016-06-29
	        SystemLog.debug("PosServiceImpl", "doPosSyncParamWork()", 
    		"---------------pos中 三项版本号： \r\n baseInfoVersion： " + baseInfoVersion +
    		" ;  \r\n pointVersion:" + pointVersion + 
    		" ;  \r\n entInfoVersion:" + entInfoVersion + 
    		" ;  \r\n ent.getVersion():" + ent.getVersion() +
    		" ;  \r\n pointRate.getVersion():" + pointRate.getVersion() +
    		" ;  \r\n servVersion：" + servVersion);
	        //end--added by liuzh on 2016-06-29
	        
//	        SystemLog.debug("PosServiceImpl", "doPosSyncParamWork()", 
//	        		"---------------pos中 三项版本号： \r\n baseInfoVersion： " + baseInfoVersion +
//	        		" ;  \r\n pointVersion:" + pointVersion + 
//	        		" ;  \r\n entInfoVersion:" + entInfoVersion + 
//	        		" ;  \r\n ent.getVersion():" + ent.getVersion() +
//	        		" ;  \r\n pointRate.getVersion():" + pointRate.getVersion() +
//	        		" ;  \r\n servVersion：" + servVersion);//kend test
	        
	        
			// 调用uc 判断基础信息版本是否一致
			if (baseInfoVersion.equalsIgnoreCase(servVersion) && pointVersion.equalsIgnoreCase(servVersion) &&
							entInfoVersion.equalsIgnoreCase(servVersion)) {
				
	        	syncParamPosOut.setBaseInfoVersion(servVersion);
				syncParamPosOut.setPointInfoVersion(servVersion);
				syncParamPosOut.setEntInfoVersion(servVersion);
				SystemLog.info("PosServiceImpl", "doPosSyncParamWork()", "基础信息、积分信息、企业信息无变化");

			} else {
				syncParamPosOut.setBaseInfoVersion(servVersion);
				int entCustType = Integer.parseInt(ent.getEntType());
				
				//企业名称不可为空
				com.gy.hsxt.access.pos.util.CommonUtil.checkState(null == ent.getEntName(), 
						"pos机签到，同步企业信息，企业名称为空，签到失败。", PosRespCode.POS_CENTER_FAIL);
				//企业名称
				syncParamPosOut.setEntName(ent.getEntName());
				//企业类型
				syncParamPosOut.setEntTypeName("互生" + EntTypeEnum.getNameByCustTypeId(entCustType));
				//商铺名称 没有这个业务了，8583协议需修改 当去掉实体店业务后，此处同步到终端设备就是一个空。
				syncParamPosOut.setShopName("");
                // 企业充值每笔上限
                BigDecimal creChargeSingleMax = new BigDecimal(0);
                BigDecimal creChargeSingleMin = new BigDecimal(0);
                switch (entCustType){
                case 2:
                    //成员企业单笔兑换互生币上下限
                    creChargeSingleMax = new BigDecimal(baseInfo.getEntBChargeMaxValue());
                    creChargeSingleMin = new BigDecimal(baseInfo.getEntBChargeMinValue());
                    break;
                case 3:
                    //托管企业单笔兑换互生币上下限
                    creChargeSingleMax = new BigDecimal(baseInfo.getEntTChargeMaxValue());
                    creChargeSingleMin = new BigDecimal(baseInfo.getEntTChargeMinValue());
                    break;
                default:
                    com.gy.hsxt.access.pos.util.CommonUtil.checkState(true, 
                    		"参数同步企业类型错误，期望值是2或者3,结果是:"+entCustType, PosRespCode.REQUEST_PACK_FORMAT);
                }
				
				// 企业代消费者兑换互生币实名单笔限额
				BigDecimal reChargeResSingleMax = new BigDecimal(baseInfo.getCarderChargeMaxValue());
				BigDecimal reChargeResSingleMin = new BigDecimal(baseInfo.getCarderChargeMinValue());

				//兑换限额
				syncParamPosOut.setCreChargeMax(creChargeSingleMax);
				syncParamPosOut.setCreChargeMin(creChargeSingleMin);
				//代兑限额
				syncParamPosOut.setReChargeMax(reChargeResSingleMax);
				syncParamPosOut.setReChargeMin(reChargeResSingleMin);
				
				SystemLog.info("PosServiceImpl", "doPosSyncParamWork()", "更新了基础信息");

				//4 积分信息处理
				String[] rateAry = pointRate.getRate();
				
				BigDecimal[] pointRates = new BigDecimal[rateAry.length];//来自pos机，由pos端保证最多5个
				for(int i=0;i<rateAry.length;i++){
				    pointRates[i]=new BigDecimal(rateAry[i]);
				}
                syncParamPosOut.setPointInfoVersion(servVersion);
                syncParamPosOut.setPointRates(pointRates);
                //积分比例个数
                syncParamPosOut.setRateCount(rateAry.length);

                //积分兑换本地货币比率
                syncParamPosOut.setPointExchangeRate(new BigDecimal(baseInfo.getPvExchangeCurrencyRate()));
                //互生币兑换本币比率
                syncParamPosOut.setHsbExchangeRate(new BigDecimal(baseInfo.getHsbExchangeCurrencyRate()));
                // 平台电话
                syncParamPosOut.setServicePhone(baseInfo.getTelephone());
                // 平台网址
                syncParamPosOut.setServiceWebSite(baseInfo.getNetwork());
                
                //积分比例极值 3.0新增
                syncParamPosOut.setRateMaxVal(new BigDecimal(null == baseInfo.getPointRateMaxValue() 
                				? "0.3" : baseInfo.getPointRateMaxValue()));
                syncParamPosOut.setRateMinVal(new BigDecimal(null == baseInfo.getPointRateMinValue()
                				? "0" : baseInfo.getPointRateMinValue()));
                
                syncParamPosOut.setEntInfoVersion(servVersion);
                
                SystemLog.info("PosServiceImpl", "doPosSyncParamWork()", "提取了新的基础信息、积分信息、企业信息");
                
			}

            //start--added by liuzh on 2016-06-24
			VersionAdapter versionAdapter = new VersionAdapter(pversion);
			versionAdapter.build(syncParamPosOut, bpApiService);
			/*
			if(Arrays.equals(pversion, PosConstant.POSVERSION301)) {
	            //5抵扣券
	            //从bp获取抵扣券参数
	            DeductionVoucherParam deductionVoucherParam = bpApiService.getDeductionVoucherParamInfo();
	            syncParamPosOut.setDeductionVoucherCount(deductionVoucherParam.getDeductionVoucherCount());
	            syncParamPosOut.setDeductionVoucherCountList(deductionVoucherParam.getDeductionVoucherCountList());
	            syncParamPosOut.setDeductionVoucherParValue(deductionVoucherParam.getDeductionVoucherParValue());
	            syncParamPosOut.setDeductionVoucherRate(deductionVoucherParam.getDeductionVoucherRate());    
	            
	            //此处暂时写死版本号
	            String deductionVoucherInfoVersion = StringUtils.leftPad("1",4, '0');
	            syncParamPosOut.setDeductionVoucherInfoVersion(deductionVoucherInfoVersion);
			}
			*/
            //end--added by liuzh on 2016-06-24
            
			syncParamPosOut.setRespCode(PosRespCode.SUCCESS);
		} catch (Exception e) {
		    com.gy.hsxt.access.pos.util.CommonUtil.checkState(true, 
            		"参数同步失败。", PosRespCode.POS_CENTER_FAIL, e);
		}	


		return syncParamPosOut;
	}
	

}
