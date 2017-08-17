/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.upacp;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.pg.bankadapter.chinapay.upacp.work.UpacpOrderStateWorker;
import com.gy.hsxt.pg.bankadapter.chinapay.upacp.work.UpacpPayWorker;
import com.gy.hsxt.pg.bankadapter.chinapay.upacp.work.UpacpRefundWorker;
import com.unionpay.acp.sdk.SDKConfig;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upacp
 * 
 *  File Name       : ChinapayUpacpFacade.java
 * 
 *  Creation Date   : 2014-11-21
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联UPACP全渠道手机支付门面类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class ChinapayUpacpFacade {
	protected final Logger logger = Logger.getLogger(this.getClass());

	// 商户号
	// (1) 测试：777290058110404
	// (2) 生产：暂时未知
	private String mechantNo;

	// 验证签名证书目录
	private String validateCertDir;

	// 签名证书路径/两码(证书密码和类型)
	private String signCertPath;
	private String signCertPwd;
	private String signCertType;

	// UPACP支付
	private UpacpPayWorker pay;

	// UPACP退款
	private UpacpRefundWorker refund;

	// UPACP订单状态查询
	private UpacpOrderStateWorker orderState;

	/**
	 * 构造函数
	 */
	public ChinapayUpacpFacade() {
	}

	public UpacpPayWorker getPayWorker() {
		return pay;
	}

	public UpacpRefundWorker getRefundWorker() {
		return refund;
	}

	public UpacpOrderStateWorker getOrderStateWorker() {
		return orderState;
	}

	public void setPay(UpacpPayWorker pay) {
		this.pay = pay;
		this.pay.initFacade(this);
	}

	public void setRefund(UpacpRefundWorker refund) {
		this.refund = refund;
		this.refund.initFacade(this);
	}

	public void setOrderState(UpacpOrderStateWorker orderState) {
		this.orderState = orderState;
		this.orderState.initFacade(this);
	}

	public String getMechantNo() {
		return mechantNo;
	}

	public void setMechantNo(String mechantNo) {
		this.mechantNo = mechantNo;
	}

	public String getValidateCertDir() {
		return validateCertDir;
	}

	public void setValidateCertDir(String validateCertDir) {
		this.validateCertDir = validateCertDir;
	}

	public String getSignCertPath() {
		return signCertPath;
	}

	public void setSignCertPath(String signCertPath) {
		this.signCertPath = signCertPath;
	}

	public String getSignCertPwd() {
		return signCertPwd;
	}

	public void setSignCertPwd(String signCertPwd) {
		this.signCertPwd = signCertPwd;
	}

	public String getSignCertType() {
		return signCertType;
	}

	public void setSignCertType(String signCertType) {
		this.signCertType = signCertType;
	}
	
	/**
	 * 由Spring实例化bean时调用
	 */
	public void init() {
		try {
			SDKConfig sdkConfig = SDKConfig.getConfig();

			sdkConfig.setSignCertPath(this.signCertPath);
			sdkConfig.setSignCertPwd(this.signCertPwd);
			sdkConfig.setSignCertType(this.signCertType);
			sdkConfig.setValidateCertDir(this.validateCertDir);
			
			sdkConfig.setAppRequestUrl(this.pay.getBankPayServAddress());
			sdkConfig.setBackRequestUrl(this.refund.getBankRefundAddress());
			sdkConfig.setSingleQueryUrl(this.orderState.getBankGetOrderAddress());

			logger.info("初始化UPACP属性值成功:" + JSON.toJSON(sdkConfig));
		} catch (Exception e) {
			logger.info("初始化UPACP属性值失败!!", e);
		}
	}

}
