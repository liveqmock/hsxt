/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.pingan.b2e.params;

import java.util.List;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.pingan.b2e.params
 * 
 *  File Name       : B2eQryBatchTransParam.java
 * 
 *  Creation Date   : 2014-11-6
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国平安银行B2E大批量转现
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class B2eQryBatchTransParam {

	// 批量转账凭证号, C(20) 必须 批量转账发起时上送的凭证号
	private String origThirdVoucher;
	// 查询类型, C(1) 非必须，默认为0, 0或者空：全部；1:成功, 2:失败, 3:处理中, 4:退票
	private String queryType;
	// 每页笔数, C(10) 非必须，默认单次返回上限500笔 每次查询返回的笔数，用于分页查询。建议为50，最大500,
	// 单批多次分页查询每页笔数必须保持一致。
	private int pageCts = 500;
	// 页码, C(10) 非必须，默认为1 当前查询的页码，用于分页查询。从1开始递增
	private int pageNo = 1;

	// 批量转账凭证号, C(20) 必须 批量转账发起时上送的凭证号
	private List<String> thirdVoucherList;

	public B2eQryBatchTransParam() {
	}
	
	public B2eQryBatchTransParam(String origThirdVoucher) {
		this.origThirdVoucher = origThirdVoucher;
	}

	public String getOrigThirdVoucher() {
		return origThirdVoucher;
	}

	public void setOrigThirdVoucher(String origThirdVoucher) {
		this.origThirdVoucher = origThirdVoucher;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public int getPageCts() {
		return pageCts;
	}

	public void setPageCts(int pageCts) {
		this.pageCts = pageCts;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public List<String> getThirdVoucherList() {
		return thirdVoucherList;
	}

	public void setThirdVoucherList(List<String> thirdVoucherList) {
		this.thirdVoucherList = thirdVoucherList;
	}
}
