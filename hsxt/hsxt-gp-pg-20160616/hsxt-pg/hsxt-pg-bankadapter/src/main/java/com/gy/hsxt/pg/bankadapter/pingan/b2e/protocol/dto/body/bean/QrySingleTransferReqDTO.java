package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.gy.hsxt.pg.bankadapter.pingan.b2e.orm.BodyDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants;

/**
 * @author huke.zhang
 * 
 * 单笔转账指令查询[4005]
 * 
 * <p>
 * <br> 查询4004发起的转账的交易结果状态。4015可以替代此接口
 * <br> 建议查询条件为OrigThirdVoucher，即4004的ThirdVoucher
 * </p>
 * <p>
 * <br> 报文头中的错误码和错误信息只表示本次查询交易的成功，不表示转账指令的处理结果，
 * <br> 需要在4005成功返回后解析Stt（20成功、30失败、其他的都为银行处理中）来确定， Yhcljg只是对状态的描述，不可以作为判断成功的依据；
 * <br> 只有在转账记录银行未收到的情况下，报文头报错：记录不存在。 
 * <br> 若4005返回：记录不存在，则表示发送给银行的记录未收到或者未收妥(需要延时在发起4005查询交易)。
 * <br> 重发转账指令一定要保持转账流水号不变，避免重复支付。
 * </p>
 */
@Component("bodyReq_4005")  
public class QrySingleTransferReqDTO   extends BodyDTO {
	
	public QrySingleTransferReqDTO(){};
	
	// 请求流水号, C(20) 非必输, 不建议使用此条件查询
	private String origThirdLogNo;
	// 转账凭证号, C(20) 非必输, 推荐使用；使用4004接口上送的ThirdVoucher或者4014上送的SThirdVoucher
	private String origThirdVoucher;
	// 银行流水号, C(14) 非必输, 推荐使用；银行返回的转账流水号
	private String origFrontLogNo;

	private QrySingleTransferReqDTO(Builder builder) {
		this.origThirdLogNo = builder.origThirdLogNo;
		this.origThirdVoucher = builder.origThirdVoucher;
		this.origFrontLogNo = builder.origFrontLogNo;
	}

	public String getOrigThirdLogNo() {
		return origThirdLogNo;
	}

	public String getOrigThirdVoucher() {
		return origThirdVoucher;
	}

	public String getOrigFrontLogNo() {
		return origFrontLogNo;
	}

	public static class Builder {
		public Builder() {
		}

		// 请求流水号, C(20) 非必输, 不建议使用此条件查询
		private String origThirdLogNo;
		// 转账凭证号, C(20) 非必输, 推荐使用；使用4004接口上送的ThirdVoucher或者4014上送的SThirdVoucher
		private String origThirdVoucher;
		// 银行流水号, C(14) 非必输, 推荐使用；银行返回的转账流水号
		private String origFrontLogNo;

		public Builder setOrigThirdLogNo(String origThirdLogNo) {
			this.origThirdLogNo = origThirdLogNo;
			return this;
		}

		public Builder setOrigThirdVoucher(String origThirdVoucher) {
			this.origThirdVoucher = origThirdVoucher;
			return this;
		}

		public Builder setOrigFrontLogNo(String origFrontLogNo) {
			this.origFrontLogNo = origFrontLogNo;
			return this;
		}

		public QrySingleTransferReqDTO build() {
			return new QrySingleTransferReqDTO(this);
		}
	}

	@Override
	public String obj2xml() {
		this.preHandleAliasFields();
		
		return PackageConstants.XML_SCHEMA_TITLE + xStream.toXML(this);
	}

	@Override
	public Object xml2obj(String strXml) {
		if (StringUtils.isEmpty(strXml)) {
			return null;
		}

		this.preHandleAliasFields();

		return xStream.fromXML(strXml);
	}
	
	/**
	 * 字段名称预处理
	 */
	private void preHandleAliasFields() {
		xStream.omitField(BodyDTO.class, "xStream");
		xStream.alias("Result", QrySingleTransferReqDTO.class);

		xStream.aliasField("OrigThirdLogNo", QrySingleTransferReqDTO.class, "origThirdLogNo");
		xStream.aliasField("OrigThirdVoucher", QrySingleTransferReqDTO.class, "origThirdVoucher");
		xStream.aliasField("OrigFrontLogNo", QrySingleTransferReqDTO.class, "origFrontLogNo");
	}
}