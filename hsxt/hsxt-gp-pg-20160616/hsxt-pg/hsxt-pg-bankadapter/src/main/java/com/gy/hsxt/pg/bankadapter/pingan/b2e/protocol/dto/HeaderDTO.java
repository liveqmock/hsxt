package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.gy.hsxt.pg.bankadapter.common.utils.ChineseCodeHelper;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.IStreamWritable;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.IsSignatured;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.MsgCharSet;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.MsgCmdNO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.NextPackageFlag;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.ProtocolType;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.ServType;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.SignatureAlgorithm;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.SignatureDataPacketType;

/**
 * @author jbli 此类用于封装包头信息
 */
public class HeaderDTO implements IStreamWritable {

	// 报文类别编号
	private String msgCmdNO;

	// 报文编码
	private String msgCharSet;

	// 通讯协议
	private String protocolType;

	// 企业银企直连标准代码 银行提供给企业的20位唯一的标识代码
	private String companyCode;

	// 接收报文长度 10个字节
	private String msgLength;

	// 交易码 6个字节
	private String tradeCode;

	// 操做员代码 5个字节
	private String operatorCode;

	// 服务类型
	private String servType;

	// 交易日期 yyyymmdd
	private String tradeDate;

	// 交易时间 hhmmss
	private String tradetime;

	// 请求方系统流水号 20个字节
	private String reqSequence;

	// 返回码 6个字节
	private String returndCode;

	// 返回描述 100个字节
	private String returndDesc;

	// 后续包标志
	private String nextPackageFlag;

	// 请求次数 3个字节
	private String requestTimes;

	// 签名标识
	private String isSignatured;

	// 签名数据包格式
	private String signatureDataPacketType;

	// 签名算法 12个字节
	private String signatureAlgorithm;

	// 签名数据长度 10个字节 0000000000 – 企业不需要签名
	private String signatureLength;

	// 附件数目 1个字节 0-没有；有的话填具体个数，最多9个
	private String attachmentCount;

	// 生成get方法
	public String getMsgCmdNO() {
		return msgCmdNO;
	}

	public String getMsgCharSet() {
		return msgCharSet;
	}

	public String getProtocolType() {
		return protocolType;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public String getMsgLength() {
		return msgLength;
	}

	public String getTradeCode() {
		return tradeCode;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public String getServType() {
		return servType;
	}

	public String getTradeDate() {
		return tradeDate;
	}

	public String getTradetime() {
		return tradetime;
	}

	public String getReqSequence() {
		return reqSequence;
	}

	public String getReturndCode() {
		return returndCode;
	}

	public String getReturndDesc() {
		String returndDesc = StringHelper.avoidNull(this.returndDesc);

		// 替换掉开头的冒号
		returndDesc = returndDesc.replaceFirst("^\\s*\\:", "");

		if (!ChineseCodeHelper.isNormalEncoding(returndDesc)) {
			returndDesc = "与银行通信故障,或银行内部处理异常!!";
		}

		return returndDesc;
	}

	public String getNextPackageFlag() {
		return nextPackageFlag;
	}

	public String getRequestTimes() {
		return requestTimes;
	}

	public String getIsSignatured() {
		return isSignatured;
	}

	public String getSignatureDataPacketType() {
		return signatureDataPacketType;
	}

	public String getSignatureAlgorithm() {
		return signatureAlgorithm;
	}

	public String getSignatureLength() {
		return signatureLength;
	}

	public String getAttachmentCount() {
		return attachmentCount;
	}

	// 私有的构造器
	private HeaderDTO(Builder builder) {
		this.msgCmdNO = builder.msgCmdNO;
		this.msgCharSet = builder.msgCharSet;
		this.protocolType = builder.protocolType;
		this.companyCode = builder.companyCode;
		this.msgLength = builder.msgLength;
		this.tradeCode = builder.tradeCode;
		this.operatorCode = builder.operatorCode;
		this.servType = builder.servType;
		this.tradeDate = builder.tradeDate;
		this.tradetime = builder.tradetime;
		this.reqSequence = builder.reqSequence;
		this.returndCode = builder.returndCode;
		this.returndDesc = builder.returndDesc;
		this.nextPackageFlag = builder.nextPackageFlag;
		this.requestTimes = builder.requestTimes;
		this.isSignatured = builder.isSignatured;
		this.signatureDataPacketType = builder.signatureDataPacketType;
		this.signatureAlgorithm = builder.signatureAlgorithm;
		this.signatureLength = builder.signatureLength;
		this.attachmentCount = builder.attachmentCount;
	}

	public static class Builder {
		// 报文类别编号
		private String msgCmdNO = MsgCmdNO.DEFAULT.getValue();

		// 报文编码
		private String msgCharSet = MsgCharSet.UTF_8.getValue();

		// 通讯协议
		private String protocolType = ProtocolType.TCPIP.getValue();

		// 企业银企直连标准代码 银行提供给企业的20位唯一的标识代码
		private String companyCode = "00203030000000037000";

		// 接收报文长度 10个字节
		private String msgLength = "";

		// 交易码 6个字节
		private String tradeCode = "";

		// 操做员代码 5个字节
		private String operatorCode = "     ";

		// 服务类型
		private String servType = ServType.REQUEST.getValue();

		// 交易日期 yyyymmdd
		private String tradeDate = StringHelper.getYYYYMMddDateFormat().format(System.currentTimeMillis());

		// 交易时间 hhmmss
		private String tradetime = StringHelper.getHHmmssDateFormat().format(System.currentTimeMillis());

		// 请求方系统流水号 20个字节
		private String reqSequence = "";

		// 返回码 6个字节
		private String returndCode = "      ";

		// 返回描述 100个字节
		private String returndDesc = "                                                                                                    ";

		// 后续包标志
		private String nextPackageFlag = NextPackageFlag.ISLAST.getValue();

		// 请求次数 3个字节
		private String requestTimes = "000";

		// 签名标识
		private String isSignatured = IsSignatured.NO.getValue();

		// 签名数据包格式
		private String signatureDataPacketType = SignatureDataPacketType.NAKED.getValue();

		// 签名算法 12个字节
		private String signatureAlgorithm = SignatureAlgorithm.DEFAULT_BLANK.getValue();

		// 签名数据长度 10个字节 0000000000 – 企业不需要签名
		private String signatureLength = "          ";

		// 附件数目 1个字节 0-没有；有的话填具体个数，最多9个
		private String attachmentCount = "0";

		/**
		 * 传入交易码、包长(包长只包括包体的长度，不包括包头和附件部分)、附件数
		 * @param companyCode
		 */
		public Builder(String tradeCode, int msgLength, int attachmentCount) {
			this.setTradeCode(tradeCode);
			this.setMsgLength(msgLength);
			this.setAttachmentCount(attachmentCount);
		}

		public HeaderDTO build() {
			return new HeaderDTO(this);
		}

		public Builder setCompanyCode(String companyCode) {
			this.companyCode = StringHelper.rightPad(companyCode, 20);
			return this;
		}

		private Builder setMsgLength(int msgLength) {
			this.msgLength = StringHelper.leftPad(String.valueOf(msgLength),10,'0');
			return this;
		}

		public Builder setMsgCmdNO(String msgCmdNO) {
			this.msgCmdNO = msgCmdNO;
			return this;
		}

		public Builder setMsgCharSet(String msgCharSet) {
			this.msgCharSet = msgCharSet;
			return this;
		}

		public Builder setProtocolType(String protocolType) {
			this.protocolType = protocolType;
			return this;
		}

		private Builder setTradeCode(String tradeCode) {
			this.tradeCode = StringHelper.rightPad(tradeCode,6);
			return this;
		}

		public Builder setOperatorCode(String operatorCode) {
			this.operatorCode = StringHelper.rightPad(operatorCode,5);
			return this;
		}

		public Builder setServType(String servType) {
			this.servType = servType;
			return this;
		}

		public Builder setTradeDate(String tradeDate) {
			this.tradeDate = tradeDate;
			return this;
		}

		public Builder setTradetime(String tradetime) {
			this.tradetime = tradetime;
			return this;
		}

		public Builder setReqSequence(String reqSequence) {
			this.reqSequence = StringHelper.rightPad(reqSequence,20);
			return this;
		}

		public Builder setReturndCode(String returndCode) {
			this.returndCode = StringHelper.rightPad(returndCode, 6);
			return this;
		}

		public Builder setReturndDesc(String returndDesc) {
			this.returndDesc = StringHelper.rightPad(returndDesc, 100);
			return this;
		}

		public Builder setNextPackageFlag(String nextPackageFlag) {
			this.nextPackageFlag = nextPackageFlag;
			return this;
		}

		public Builder setRequestTimes(int requestTimes) {
			this.requestTimes = StringHelper.leftPad(String.valueOf(requestTimes),3,'0');
			return this;
		}

		public Builder setIsSignatured(String isSignatured) {
			this.isSignatured = isSignatured;
			return this;
		}

		public Builder setSignatureDataPacketType(String signatureDataPacketType) {
			this.signatureDataPacketType = signatureDataPacketType;
			return this;
		}

		public Builder setSignatureAlgorithm(String signatureAlgorithm) {
			this.signatureAlgorithm = signatureAlgorithm;
			return this;
		}

		public Builder setSignatureLength(String signatureLength) {
			this.signatureLength = StringHelper.rightPad(signatureLength, 10);
			return this;
		}

		private Builder setAttachmentCount(int attachmentCount) {
			if (attachmentCount > 9 || attachmentCount < 0) {
				throw new IllegalArgumentException("attachmentCount is too big！");
			}
			this.attachmentCount = String.valueOf(attachmentCount);
			return this;
		}
	}

	public void write(OutputStream out) throws IOException {
		String charSetName = "GBK";
		if(MsgCharSet.UTF_8.getValue().equals(msgCharSet)){
			charSetName = "UTF-8";
		}else if(MsgCharSet.GBK.getValue().equals(msgCharSet)){
			charSetName = "GBK";
		}else if(MsgCharSet.ISO_8859_1.getValue().equals(msgCharSet)){
			charSetName = "ISO-8859-1";
		}else if(MsgCharSet.UNICODE.getValue().equals(msgCharSet)){
			charSetName = "UNICODE";
		}	
		out.write(this.toString().getBytes(charSetName));
	}
	
	public String toString() {
		StringBuilder  sb = new StringBuilder()
		.append(msgCmdNO)
		.append(msgCharSet)
		.append(protocolType)
		.append(companyCode)
		.append(msgLength)		
		
		.append(tradeCode)
		.append(operatorCode)
		.append(servType)
		.append(tradeDate)
		.append(tradetime)
		
		.append(StringHelper.leftPad(reqSequence,20))
		.append(returndCode)
		.append(returndDesc)
		.append(nextPackageFlag)
		.append(requestTimes)
		
		.append(isSignatured)
		.append(signatureDataPacketType)
		.append(signatureAlgorithm)
		.append(signatureLength)
		.append(attachmentCount);
	
		return sb.toString();
	}
	
	/**
	 * 静态工厂方法，由字节数组反序列化实例，需要先从流中读取定长的字节数组
	 * @param header
	 * @return
	 * @throws IOException
	 */
	public static HeaderDTO getInstance(InputStream in) throws Exception {
		byte[] headerBytes = new byte[PackageConstants.LENGTH_OF_HEADER];
		int readedCounts =in.read(headerBytes);
		if(readedCounts<0){
			return null;
		}
		while(readedCounts<headerBytes.length){
			readedCounts = readedCounts+in.read(headerBytes,readedCounts,headerBytes.length-readedCounts);
		}
		Decoder decoder = new Decoder(headerBytes);
		// 1、报文类别编号
		String msgCmdNO = decoder.getString(6);
		// 2、报文编码
		String msgCharSet = decoder.getString(2);
		// 3、通讯协议
		String protocolType = decoder.getString(2);
		// 4、企业银企直连标准代码 银行提供给企业的20位唯一的标识代码
		String companyCode = decoder.getString(20);
		// 5、接收报文长度 10个字节
		String msgLength = decoder.getString(10);
		// 6、交易码 6个字节
		String tradeCode = decoder.getString(6);
		// 7、操做员代码 5个字节
		String operatorCode = decoder.getString(5);
		// 8、服务类型
		String servType = decoder.getString(2);
		// 9、交易日期 yyyymmdd
		String tradeDate = decoder.getString(8);
		// 10、交易时间 hhmmss
		String tradetime = decoder.getString(6); 
		// 11、请求方系统流水号 20个字节
		String reqSequence = decoder.getString(20);
		// 12、返回码 6个字节
		String returndCode = decoder.getString(6);
		// 13、返回描述 100个字节
		String returndDesc = decoder.getString(100);
		// 14、后续包标志
		String nextPackageFlag = decoder.getString(1);
		// 15、请求次数 3个字节
		String requestTimes = decoder.getString(3);
		// 16、签名标识
		String isSignatured = decoder.getString(1);
		// 17、签名数据包格式
		String signatureDataPacketType = decoder.getString(1);
		// 18、签名算法 12个字节
		String signatureAlgorithm = decoder.getString(12);
		// 19、签名数据长度 10个字节 0000000000 – 企业不需要签名
		String signatureLength = decoder.getString(10);
		// 20、附件数目 1个字节 0-没有；有的话填具体个数，最多9个
		String attachmentCount = decoder.getString(1);

		return new HeaderDTO.Builder(tradeCode ,Integer.parseInt(msgLength), Integer.parseInt(attachmentCount))
					.setMsgCmdNO(msgCmdNO)
					.setMsgCharSet(msgCharSet)				
					.setProtocolType(protocolType)
					.setCompanyCode(companyCode)
					
					.setOperatorCode(operatorCode)
					.setServType(servType)
					.setTradeDate(tradeDate)
					.setTradetime(tradetime)	
					
					.setReqSequence(reqSequence)
					.setReturndCode(returndCode)
					.setReturndDesc(returndDesc)
					.setNextPackageFlag(nextPackageFlag)
					.setRequestTimes(Integer.parseInt(requestTimes))
					
					.setIsSignatured(isSignatured)
					.setSignatureDataPacketType(signatureDataPacketType)
					.setSignatureAlgorithm(signatureAlgorithm)
					.setSignatureLength(signatureLength)
					.build();
	}

}
