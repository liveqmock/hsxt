package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean;


/**
 * @author jbli
 * 
 */

public class QryBankNodeCodeResDetailDTO {
	
	// 网点名称 Char(30)
	private String nodeName;
	// 网点联行号  Char(14)
	private String nodeCode;
	
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeCode() {
		return nodeCode;
	}
	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

}
