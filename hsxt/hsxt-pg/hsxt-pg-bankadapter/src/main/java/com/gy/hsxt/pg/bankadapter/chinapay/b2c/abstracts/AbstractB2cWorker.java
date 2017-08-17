/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.b2c.abstracts;

import org.apache.log4j.Logger;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.b2c.abstracts
 * 
 *  File Name       : AbstractB2cWorker.java
 * 
 *  Creation Date   : 2014-10-23
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 抽象工作者类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public abstract class AbstractB2cWorker {
	protected final Logger logger = Logger.getLogger(this.getClass());
	protected AbstractChinapayB2cFacade chinapayFacade;

	/** 商户号 **/
	protected String mechantNo;

	/** 私钥文件路径 **/
	protected String privateKeyFilePath;

	/** 银联公钥证书文件 **/
	protected String bankPubKeyPath;

	public AbstractB2cWorker() {
	}
	
	/**
	 * 初始化数据
	 * 
	 * @param chinapayFacade
	 */
	public void initFacade(AbstractChinapayB2cFacade chinapayFacade) {
		this.chinapayFacade = chinapayFacade;
		
		this.mechantNo = chinapayFacade.getMechantNo();
		this.privateKeyFilePath = chinapayFacade.getPrivateKeyFilePath();
		this.bankPubKeyPath = chinapayFacade.getBankPubKeyPath();
	}

}
