package com.gy.hsxt.access.pos.model;

/***************************************************************************
 * <PRE>
 *  Project Name    : PosServer
 * 
 *  Package Name    : com.gy.point.model
 * 
 *  File Name       : BatchUpload.java
 * 
 *  Creation Date   : 2014-6-11
 * 
 *  Author          : huangfuhua
 * 
 *  Purpose         : 批上送
 *  输出pos server
 * 
 * </PRE>
 ***************************************************************************/
public class BatchUploadPosOut extends BaseOut {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3509816184279404609L;

	/**
	 * 本批上送总笔数
	 */
	private int batchUploadCount;

	/**
	 * 网络管理信息码 批上送使用201；对帐不平衡时，上送IC卡通知信息，使用206，上送磁条卡通知信息，使用202；对帐平衡时，批上送结束使用207
	 */
	private String netCode;

	public BatchUploadPosOut() {
	}

	public BatchUploadPosOut(int batchUploadCount, String netCode) {
		this.batchUploadCount = batchUploadCount;
		this.netCode = netCode;
	}

	public int getBatchUploadCount() {
		return batchUploadCount;
	}

	public void setBatchUploadCount(int batchUploadCount) {
		this.batchUploadCount = batchUploadCount;
	}

	public String getNetCode() {
		return netCode;
	}

	public void setNetCode(String netCode) {
		this.netCode = netCode;
	}

}
