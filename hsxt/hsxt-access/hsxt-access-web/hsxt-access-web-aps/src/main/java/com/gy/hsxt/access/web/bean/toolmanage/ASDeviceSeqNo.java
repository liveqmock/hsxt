package com.gy.hsxt.access.web.bean.toolmanage;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.bean.toolmanage
 * @className     : ASDeviceSeqNo.java
 * @description   : 机器码对象
 * @author        : maocy
 * @createDate    : 2016-02-24
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
public class ASDeviceSeqNo {
	
	/**批次号*/
	private String batchNo;
	
	/**序列号*/
	private String seqNo;

	public ASDeviceSeqNo() {
		super();
	}

	public ASDeviceSeqNo(String batchNo, String seqNo) {
		super();
		this.batchNo = batchNo;
		this.seqNo = seqNo;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	
}

