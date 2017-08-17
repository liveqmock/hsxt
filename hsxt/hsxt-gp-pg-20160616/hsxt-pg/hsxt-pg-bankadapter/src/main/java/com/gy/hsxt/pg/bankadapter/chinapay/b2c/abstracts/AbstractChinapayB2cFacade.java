/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.b2c.abstracts;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.b2c.abstracts
 * 
 *  File Name       : AbstractChinapayB2cFacade.java
 * 
 *  Creation Date   : 2014-10-30
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 抽象类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public abstract class AbstractChinapayB2cFacade {
	// 商户号
	// (1) 测试号：808080081393253
	// (2) B2C生产：808080201303096
	// (3) 无卡支付生产: 808080201303097
	private String mechantNo;

	// 私钥文件路径
	// (1) 测试环境密钥 D:/bankkeyfile/chinapay/testb2c/MerPrK_808080081393253_20130813164627.key
	// (2) B2C生产  D:/bankkeyfile/chinapay/b2c/MerPrK_808080201303096_20131118150856.key
	// (3) 无卡生产 D:/bankkeyfile/chinapay/nc/MerPrK_808080201303097_20131118151110.key
	private String privateKeyFilePath;
	
	// 银联公钥证书文件
	// (1) 测试公钥路径： D:/bankkeyfile/chinapay/testb2c/PgPubk.key
	// (2) B2C生产公钥文件路径：  D:/bankkeyfile/chinapay/PgPubk.key
	// (3) 无卡支付生产: D:/bankkeyfile/chinapay/PgPubk.key
	private String bankPubKeyPath;
	
	public AbstractChinapayB2cFacade() {
	}

	public String getMechantNo() {
		return mechantNo;
	}

	public void setMechantNo(String mechantNo) {
		this.mechantNo = mechantNo;
	}

	public String getPrivateKeyFilePath() {
		return privateKeyFilePath;
	}

	public void setPrivateKeyFilePath(String privateKeyFilePath) {
		this.privateKeyFilePath = privateKeyFilePath;
	}

	public String getBankPubKeyPath() {
		return bankPubKeyPath;
	}

	public void setBankPubKeyPath(String bankPubKeyPath) {
		this.bankPubKeyPath = bankPubKeyPath;
	}
}
