package com.gy.hsxt.access.pos.service;

import com.gy.hsxt.access.pos.model.DeductionVoucherParam;
import com.gy.hsxt.bp.bean.BusinessPosUpgrade;
import com.gy.hsxt.common.exception.HsException;

/**
 * bp业务参数模块接口封装
 * @author liuzh
 *
 */
public interface BpApiService {
	/**
	 * 获取POS终端固件升级版本信息
	 * @param posDeviceType POS机型号
	 * @param entResNo 企业互生号
	 * @param posMachineNo POS机器号
	 * @return
	 */
	public BusinessPosUpgrade getPosUpgradeVerInfo(String posDeviceType,String entResNo,String posMachineNo) throws HsException;
	
	/** added by liuzh on 2016-06-24
	 * 获取抵扣券信息
	 * @param arg0
	 * @return
	 * @throws HsException
	 */
	public DeductionVoucherParam getDeductionVoucherParamInfo() throws HsException;
}
