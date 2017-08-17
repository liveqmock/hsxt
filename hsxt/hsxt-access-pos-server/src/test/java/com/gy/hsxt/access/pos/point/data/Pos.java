/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.access.pos.point.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/***************************************************************************
 * <PRE>
 *  Project Name    : point-server
 * 
 *  Package Name    : com.gy.point.tc
 * 
 *  File Name       : Pos.java
 * 
 *  Creation Date   : 2015-1-27
 * 
 *  Author          : huangfh
 * 
 *  Purpose         : TODO
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "pos")
public class Pos {

	@XmlElement(name = "posNo")
	private String posNo;
	@XmlElement(name = "batchNo")
	private String batchNo;
	@XmlElement(name = "posRunCode")
	private String posRunCode;
	
	public Pos(){}
	
	public Pos(String posNo,String batchNo,String posRunCode){
		this.posNo = posNo;
		this.batchNo = batchNo;
		this.posRunCode = posRunCode;
	}
	
	public String getPosNo() {
		return posNo;
	}
	public void setPosNo(String posNo) {
		this.posNo = posNo;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getPosRunCode() {
		return posRunCode;
	}
	public void setPosRunCode(String posRunCode) {
		this.posRunCode = posRunCode;
	}
	
	
}
