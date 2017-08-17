/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.service.impl.dsbatch;

import org.springframework.stereotype.Service;

import com.gy.hsxt.gp.common.constant.ConfigConst.TcConfigConsts;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.service.impl
 * 
 *  File Name       : GpAoPayAccountingDSBatchService.java
 * 
 *  Creation Date   : 2016年2月19日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : GP支付系统与AO对账文件生成调度接口(提供给DS系统调用), 因为字段跟BS完全一致,所以直接继承GpBs的类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service("gpAoPayAccountingDSBatchService")
public class GpAoPayAccountingDSBatchService extends
		GpBsPayAccountingDSBatchService {
	
	/**
	 * 获取子系统标识
	 * 
	 * @return
	 */
	protected String getSrcSubsysId() {
		return "AO";
	}

	/**
	 * 获取CHK文件配置的key
	 * 
	 * @return
	 */
	protected String getTcChkFileNameKey() {
		return TcConfigConsts.KEY_SYS_TC_CHK_GP_AO_PAY;
	}

	/**
	 * 获取DET文件配置的key
	 * 
	 * @return
	 */
	protected String getTcDetFileNameKey() {
		return TcConfigConsts.KEY_SYS_TC_DET_GP_AO_PAY;
	}

	@Override
	protected void createBalanceFile(String balanceDate) throws Exception {
		super.createBalanceFile(balanceDate);
	}
}
