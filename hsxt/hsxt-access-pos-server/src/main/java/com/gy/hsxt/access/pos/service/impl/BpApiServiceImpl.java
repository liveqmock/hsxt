package com.gy.hsxt.access.pos.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.model.DeductionVoucherParam;
import com.gy.hsxt.access.pos.service.BpApiService;
import com.gy.hsxt.bp.api.IBusinessParamSearchService;
import com.gy.hsxt.bp.api.IBusinessPosUpgradeInfoService;
import com.gy.hsxt.bp.bean.BusinessPosUpgrade;
import com.gy.hsxt.bp.bean.BusinessSysParamItemsRedis;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.exception.HsException;

/**
 * bp业务参数模块接口实现
 * @author liuzh
 *
 */
@Service("bpApiService")
public class BpApiServiceImpl implements BpApiService {
	
	@Autowired
	@Qualifier("businessPosUpgradeInfoService")
	private IBusinessPosUpgradeInfoService businessPosUpgradeInfoService;
	
	@Autowired
	@Qualifier("businessParamSearchService")
	private IBusinessParamSearchService businessParamSearchService;	
	
	@Override
	public BusinessPosUpgrade getPosUpgradeVerInfo(String posDeviceType,String entResNo,String posMachineNo) throws HsException {
        SystemLog.debug("BpApiServiceImpl", "getPosUpgradeVerInfo()", "业务参数接口  请求参数:|" + posDeviceType + "|" + entResNo + "|" + posMachineNo);        
		BusinessPosUpgrade info = businessPosUpgradeInfoService.searchPosUpgradeVerInfo(posDeviceType,entResNo,posMachineNo);
        SystemLog.debug("BpApiServiceImpl", "getPosUpgradeVerInfo()", "业务参数接口  返回结果:"+JSON.toJSONString(info));
		
		return info;
	}
	
	/**
	 * added by liuzh on 2016-06-24
	 * 获取抵扣券参数信息
	 */
	@Override
	public DeductionVoucherParam getDeductionVoucherParamInfo() throws HsException {
		DeductionVoucherParam param = new DeductionVoucherParam();
		
		Map<String, BusinessSysParamItemsRedis> map = businessParamSearchService.searchSysParamItemsByGroup(BusinessParam.DEDUCTION_VOUCHER.getCode());
		
		//抵扣券最大张数
		BusinessSysParamItemsRedis deductionVoucherMaxNumRedis = map.get(BusinessParam.DEDUCTION_VOUCHER_MAX_NUM.getCode());
		//抵扣券每张面额
		BusinessSysParamItemsRedis deductionVoucherPerAmountRedis = map.get(BusinessParam.DEDUCTION_VOUCHER_PER_AMOUNT.getCode());
		//抵扣券金额占消费金额比率
		BusinessSysParamItemsRedis deductionVoucherRateRedis = map.get(BusinessParam.DEDUCTION_VOUCHER_RATE.getCode());

		String deductionVoucherCount = deductionVoucherMaxNumRedis.getSysItemsValue();
		String deductionVoucherParValue = deductionVoucherPerAmountRedis.getSysItemsValue();
		String deductionVoucherRate = deductionVoucherRateRedis.getSysItemsValue();

		param.setDeductionVoucherCount(Integer.parseInt(deductionVoucherCount));
		param.setDeductionVoucherParValue(new BigDecimal(deductionVoucherParValue));
		param.setDeductionVoucherRate(new BigDecimal(deductionVoucherRate));
		
		List<Integer> deductionVoucherCountList = new ArrayList<Integer>();
		for(int i=1;i<=param.getDeductionVoucherCount();i++) {
			deductionVoucherCountList.add(Integer.valueOf(i));
		}
		param.setDeductionVoucherCountList(deductionVoucherCountList);		

		return param;
	}
}
