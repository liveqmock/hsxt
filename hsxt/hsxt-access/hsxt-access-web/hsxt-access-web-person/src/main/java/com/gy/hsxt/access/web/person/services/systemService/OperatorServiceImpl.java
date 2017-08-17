/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.services.systemService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.person.bean.PersonBase;
import com.gy.hsxt.access.web.person.services.consumer.INoCardholderService;
import com.gy.hsxt.access.web.person.services.hsc.IBankCardService;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolderAllInfo;

@Service
public class OperatorServiceImpl extends BaseServiceImpl implements
		OperatorService {

	@Resource
	private IUCAsCardHolderService ucAsCardHolderService;

	@Resource
    private INoCardholderService noCardholderService;//非持卡人服务类
	
	@Resource
    private IBankCardService bankCardService; // 银行卡信息
	/**
	 * 获取操作员详情
	 * 
	 * @param scsBase
	 * @return
	 * @see com.gy.hsxt.access.web.scs.services.systemmanage.OperatorService#getOperatorDetail(com.gy.hsxt.access.web.PersonBase.SCSBase)
	 */
	@Override
	public Map<String, Object> getOperatorDetail(PersonBase personBase,String hs_isCard) {
		// 变量声明
		AsCardHolderAllInfo achai = null;
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		 //持卡人与非持卡人验证  非持卡人不能执行查询状态
		if(StringUtils.isEmpty(hs_isCard) == false && "undefined".equals(hs_isCard) == false&& "1".equals(hs_isCard) ==true)
        {
        	//查询非持卡人手机号码
			Map<String,String> map = noCardholderService.findMobileByCustId(personBase.getCustId());
			
			String mobile = map.get("mobile");
			
			
			// 获取邮箱信息
			map = this.noCardholderService.findEamilByCustId(personBase.getCustId());
			
			String email = map.get("email");
			 // 获取用户银行卡账户
            List bankList = this.bankCardService.findBankAccountList(personBase.getCustId(), UserTypeEnum.NO_CARDER.getType());
            
            if(bankList != null && bankList.size()>0){
            	retMap.put("isBindBank","1");                                // 是绑定银行卡
            }else{
            	retMap.put("isBindBank","0");                                // 否绑定银行卡
            }
            
			//邮件信息
			if(StringUtils.isNotBlank(email)){
				retMap.put("isAuthEmail",1);                 	// 邮箱
				retMap.put("email",email);                 		// 邮箱
			}else{
				retMap.put("isAuthEmail",0);                 	// 邮箱
			}
			
			retMap.put("mobile", mobile);					// 手机
			retMap.put("resNo", mobile);					// 非持卡人显示手机号
			retMap.put("isAuthMobile","1");                 // 手机
			
        }
        else
        {
        	// 获取操作员信息
    		achai = ucAsCardHolderService.searchAllInfo(personBase.getCustId());
    		
    		//非空验证
    		if (achai == null || achai.getBaseInfo() == null) {
    			return null;
    		}
    		
    		//初始化方法
    		retMap = new HashMap<String, Object>();
    		
    		//封装返回操作员信息
    		//retMap.put("lastLoginIp",StringUtils.trimToBlank(achai.getBaseInfo().getLastLoginIp())); 	 // 最后登录ip
    		retMap.put("entResNo",StringUtils.trimToBlank(achai.getBaseInfo().getEntResNo()));			 // 企业互生号
    		retMap.put("ensureInfo",StringUtils.trimToBlank(achai.getBaseInfo().getEnsureInfo()));		 // 预留信息
    		//证件类型为营业执照时取企业名称(entName) 否则取用户名称
    		if("3".equals(StringUtils.trimToBlank(achai.getAuthInfo().getCerType())))
    		{
    		    retMap.put("custName",StringUtils.trimToBlank(achai.getAuthInfo().getEntName()));           // 持卡人名称
    		}
    		else
    		{
    		    retMap.put("custName",StringUtils.trimToBlank(achai.getAuthInfo().getUserName()));           // 持卡人名称
    		}
    		retMap.put("isAuthEmail", achai.getBaseInfo().getIsAuthEmail());                             // 邮箱是否已校验
    		retMap.put("email",StringUtils.trimToBlank(achai.getBaseInfo().getEmail()));                 // 邮箱
    		retMap.put("isBindBank",achai.getBaseInfo().getIsBindBank());                                // 是否绑定银行卡
    		retMap.put("isRealnameAuth",achai.getBaseInfo().getIsRealnameAuth());                        // 实名状态
    		retMap.put("isAuthMobile",achai.getBaseInfo().getIsAuthMobile());                            // 是否绑定手机
    		retMap.put("nickname", achai.getNetworkInfo().getNickname());                                // 昵称
    		retMap.put("resNo", achai.getBaseInfo().getPerResNo());										 // 互生号
        }
		
		
		return retMap ;
	}

	

	public static void main(String[] args) {
	}
}
