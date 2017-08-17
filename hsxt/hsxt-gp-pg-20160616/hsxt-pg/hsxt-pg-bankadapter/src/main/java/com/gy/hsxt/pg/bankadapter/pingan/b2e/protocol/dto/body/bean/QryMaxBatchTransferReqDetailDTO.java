package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean;


/**
 * @author jbli
 *
 */
public class QryMaxBatchTransferReqDetailDTO {

	//单笔转账凭证号 批次中单笔流水号；若上送此单笔凭证号，则只返回此笔交易结果。此域若上送则QueryType无效，且不分页。最多支持500个
	private String sThirdVoucher;

	public String getSThirdVoucher() {
		return sThirdVoucher;
	}

	private QryMaxBatchTransferReqDetailDTO(String sThirdVoucher){
		this.sThirdVoucher = sThirdVoucher;
	}

	public static QryMaxBatchTransferReqDetailDTO getInstance(String sThirdVoucher){
		return new QryMaxBatchTransferReqDetailDTO(sThirdVoucher);
	}

}
