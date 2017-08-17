package com.gy.hsxt.access.web.aps.controllers.invoice.bean;

import com.gy.hsxt.bs.bean.tax.TaxrateChange;

public class TaxrateChangeCompanyInfor extends TaxrateChange{
		    
	public String getContactAddr() {
		return contactAddr;
	}

	public void setContactAddr(String contactAddr) {
		this.contactAddr = contactAddr;
	}

	public String getTaxNo() {
		return taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	public String getCreName() {
		return creName;
	}

	public void setCreName(String creName) {
		this.creName = creName;
	}

	private static final long serialVersionUID = 5124946841254217996L;

		/** 企业联系地址 */
		private String contactAddr;
		
		/** 纳税人识别号 */
		private String taxNo;
		
		/** 法人代表 */
		private String creName;

		
}
