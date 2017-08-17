package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.attachment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.IStreamWritable;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.Decoder;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.GetFileMethod;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.IsSignatured;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.MsgCharSet;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.SignatureAlgorithm;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.SignatureDataPacketType;

/**
 * @author jbli
 * 
 */
public class AttachmentHeaderDTO implements IStreamWritable {

	// 文件名称 240个字节
	private String fileName;

	// 文件内容编码
	private String msgCharSet;

	// 获取文件方式
	private String getFileMethod;

	// 是否对文件签名
	private String isSignatured;

	// 签名数据包格式
	private String signatureDataPacketType;

	// 哈希签名算法
	private String signatureAlgorithm;

	// 签名数据长度 10个字节 如果不签名此值赋值：0000000000
	private String signatureLength;

	// 文件内容长度 指“附件报文体”的长度。若获取文件方式0:流，则必输。默认为0000000000
	private String fileLength;

	public String getFileName() {
		return fileName;
	}

	public String getMsgCharSet() {
		return msgCharSet;
	}

	public String getGetFileMethod() {
		return getFileMethod;
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

	public String getFileLength() {
		return fileLength;
	}

	// 外部只能通过调用静态工厂方法来构建实例
	private AttachmentHeaderDTO(Builder builder) {
		this.fileName = builder.fileName;
		this.msgCharSet = builder.msgCharSet;
		this.getFileMethod = builder.getFileMethod;
		this.isSignatured = builder.isSignatured;
		this.signatureDataPacketType = builder.signatureDataPacketType;
		this.signatureAlgorithm = builder.signatureAlgorithm;
		this.signatureLength = builder.signatureLength;
		this.fileLength = builder.fileLength;
	};

	public static class Builder {
		// 文件名称 240个字节
		private String fileName;

		// 文件内容编码
		private String msgCharSet = MsgCharSet.UTF_8.getValue();

		// 获取文件方式
		private String getFileMethod = GetFileMethod.DEFAULT_IS_STREAM.getValue();

		// 是否对文件签名
		private String isSignatured = IsSignatured.NO.getValue();

		// 签名数据包格式
		private String signatureDataPacketType = SignatureDataPacketType.NAKED.getValue();

		// 哈希签名算法
		private String signatureAlgorithm = SignatureAlgorithm.DEFAULT_BLANK.getValue();

		// 签名数据长度 10个字节 如果不签名此值赋值：0000000000
		private String signatureLength = "0000000000";

		// 文件内容长度 指“附件报文体”的长度。若获取文件方式0:流，则必输。默认为0000000000
		private String fileLength;

		public AttachmentHeaderDTO build() {
			return new AttachmentHeaderDTO(this);
		}

		/**
		 * 供编码使用
		 * @param fileName
		 */
		public Builder(String fileName) {
			this.fileName = fileName;
			this.fileLength = StringHelper.leftPad(
					String.valueOf(new File(fileName).length()), 10, '0');
		}
		
		/**
		 * 供解码使用
		 * @param fileName
		 * @param fileLength
		 */
		private Builder(String fileName,String fileLength) {
			this.fileName = fileName;
			this.fileLength =fileLength;
		}		

		public Builder setMsgCharSet(String msgCharSet) {
			this.msgCharSet = msgCharSet;
			return this;
		}

		public Builder setGetFileMethod(String getFileMethod) {
			this.getFileMethod = getFileMethod;
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
			this.signatureLength = signatureLength;
			return this;
		}

	}

	/**
	 * 返回一个带有初始化默认值的实例引用，用于编码时简化参数的设置 设置了附件包头默认的
	 * 文件内容编码、获取文件方式、是否对文件签名、签名数据包格式、哈希签名算法、签名数据长度
	 * 
	 * @return
	 */

	public void write(OutputStream out) throws IOException {

	}

	public static AttachmentHeaderDTO readFields(InputStream in) throws IOException {
		byte[] attachmentHeaderBytes = new byte[PackageConstants.LENGTH_OF_ATTACHMENTHEADER];
		in.read(attachmentHeaderBytes);
		Decoder decoder = new Decoder(attachmentHeaderBytes);
		
		String fileName = decoder.getString(240);
		String msgCharSet = decoder.getString(2);
		String getFileMethod = decoder.getString(1);
		String isSignatured = decoder.getString(1);
		String signatureDataPacketType = decoder.getString(1);
		String signatureAlgorithm = decoder.getString(12);
		String signatureLength = decoder.getString(10);
		String fileLength = decoder.getString(10);
		
		return new AttachmentHeaderDTO.Builder(fileName, fileLength)
				.setMsgCharSet(msgCharSet)
				.setGetFileMethod(getFileMethod)
				.setIsSignatured(isSignatured)
				.setSignatureDataPacketType(signatureDataPacketType)
				.setSignatureAlgorithm(signatureAlgorithm)
				.setSignatureLength(signatureLength)
				.build();
	}

}
