package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gy.hsxt.pg.bankadapter.pingan.b2e.orm.BodyDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.orm.Iserializer;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.IStreamWritable;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.MsgCharSet;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.ServType;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.attachment.AttachmentDTO;

/**
 * @author jbli
 * 为确保线程安全性和JVM效率，将数据包设计为一个不可变类
 */
public class PackageDTO implements IStreamWritable {

    private static final Logger logger = Logger.getLogger(PackageDTO.class.getName());  
    
	private static final ApplicationContext CONTEXT = new ClassPathXmlApplicationContext(
			"classpath*:spring/spring-bankadapter-context.xml");
    
    private static final String RES_BODY_PREFIX = "bodyRes_";    
    private static final String REQ_BODY_PREFIX = "bodyReq_";
    
	// 包头 固定长度 222字节
	private HeaderDTO header;

	// 包体
	private Iserializer body;

	// 附件
	private List<AttachmentDTO> attachments;

	// get and set
	public HeaderDTO getHeader() {
		return header;
	}

	public Iserializer getBody() {
		return body;
	}

	public List<AttachmentDTO> getAttachments() {
		return attachments;
	}
	
	private PackageDTO(Builder builder) {
		this.header = builder.header;
		this.body = builder.body;
		this.attachments = builder.attachments;
	}

	public static class Builder {
		private HeaderDTO header;
		private Iserializer body;
		private List<AttachmentDTO> attachments ;

		/**
		 * 传入包头、包体、附件实例
		 * @param header
		 * @param body
		 * @param attachments
		 */
		public Builder(HeaderDTO header,Iserializer body,List<AttachmentDTO> attachments){
			this.header = header;
			this.body = body;			
			this.attachments = attachments;
		}
		
		//
		public Builder setHeader(HeaderDTO header) {
			this.header = header;
			return this;
		}

		//填充包体，并自动设置包长
		public Builder setBody(Iserializer body) {
			this.body = body;
			return this;
		}

		public Builder setAttachments(List<AttachmentDTO> attachments) {
			if (attachments != null
					&& attachments.size() > PackageConstants.MAX_ATTACHMENTS) {
				throw new IllegalArgumentException("attachments  size = "
						+ attachments.size()
						+ " It's exceed the maximum size! your");
			}
			this.attachments = attachments;
			return this;
		}

		public PackageDTO Build(){
			return new PackageDTO(this);
		}

	}

	public void write(OutputStream out) throws IOException {
		
		header.write(out);
		
		int bodyLength = Integer.parseInt(header.getMsgLength());
		byte[] bodyBytes = new byte[bodyLength];
		
		String charSetName = "GBK";
		if(MsgCharSet.UTF_8.getValue().equals(header.getMsgCharSet())){
			charSetName = "UTF-8";
		}else if(MsgCharSet.GBK.getValue().equals(header.getMsgCharSet())){
			charSetName = "GBK";
		}else if(MsgCharSet.ISO_8859_1.getValue().equals(header.getMsgCharSet())){
			charSetName = "ISO-8859-1";
		}else if(MsgCharSet.UNICODE.getValue().equals(header.getMsgCharSet())){
			charSetName = "UNICODE";
		}	
		
		bodyBytes = body.obj2xml().getBytes(charSetName);
		out.write(bodyBytes);
		
		if (attachments != null) {
			for (IStreamWritable attachment : attachments) {
				attachment.write(out);
			}
		}
	}

	public static PackageDTO getInstance(InputStream in,OutputStream out) throws Exception {
		//读包头
		HeaderDTO header = HeaderDTO.getInstance(in);
		
		if(logger.isDebugEnabled()){
			logger.debug("received header="+header);
		}
		
		if(header == null){
			return null;
		}
		
		//读包体
		int bodyLength = Integer.parseInt(header.getMsgLength());
		if(logger.isDebugEnabled()){
			logger.debug("received bodyLength="+bodyLength);
		}
		
		BodyDTO body = null;
		if (bodyLength>0){
			byte[] bodyBytes = new byte[bodyLength];
			
			int readedCounts =in.read(bodyBytes);
			if(readedCounts<0){
				return null;
			}
			while(readedCounts<bodyBytes.length){
				readedCounts=readedCounts+in.read(bodyBytes,readedCounts,bodyBytes.length-readedCounts);								
			}
			
			String charSetName = "GBK";
			if(MsgCharSet.UTF_8.getValue().equalsIgnoreCase(header.getMsgCharSet().trim())){
				charSetName = "UTF-8";
			}else if(MsgCharSet.GBK.getValue().equalsIgnoreCase(header.getMsgCharSet().trim())){
				charSetName = "GBK";
			}else if(MsgCharSet.ISO_8859_1.getValue().equalsIgnoreCase(header.getMsgCharSet().trim())){
				charSetName = "ISO-8859-1";
			}else if(MsgCharSet.UNICODE.getValue().equalsIgnoreCase(header.getMsgCharSet().trim())){
				charSetName = "UNICODE";
			}	
			String bodyXmlStr = new String(bodyBytes,charSetName);
			if(logger.isDebugEnabled()){
				logger.debug("received body="+bodyXmlStr);
			}		
			
			String beanName = null;
			String servType = header.getServType();
			if(ServType.REQUEST.getValue().equals(servType.trim())){
				beanName = REQ_BODY_PREFIX+header.getTradeCode().trim();
			}else{
				beanName = RES_BODY_PREFIX+header.getTradeCode().trim();
			}
			
			body = (BodyDTO)CONTEXT.getBean(beanName); 
			body = (BodyDTO) body.xml2obj(bodyXmlStr);
			
			body.setXStream();
		}
		
        //读附件
		List<AttachmentDTO> attachments = null;
		int attachmentCount = Integer.parseInt(header.getAttachmentCount());
		
		if (attachmentCount>0){
			attachments = new LinkedList<AttachmentDTO>();
			for (int i = 0; i <attachmentCount; i++) {
				AttachmentDTO attachment = AttachmentDTO.readFields(in,out);
				attachments.add(attachment);
			}			
		}
		
		return new PackageDTO.Builder(header, body, attachments).Build();		
	}
	
	public String toString(){
		StringBuilder returnValue = new StringBuilder().append(header);
		if(body!=null){
			returnValue.append(body.obj2xml());
		}
		if (attachments!=null){
			returnValue.append(attachments);
		}
		return returnValue.toString();
	}
	

//	public static void main(String[] agrs) {
//		
//		// 附件采用流的方式进行传输，也就是在发送前，先装载附件到buffer字节数组中，发送前，也要设置附件包头文件的长度
//		LinkedList<AttachmentDTO> attachments = new LinkedList<AttachmentDTO>();
//		// 附件实例
//		AttachmentHeaderDTO attachmentHeader1 = new AttachmentHeaderDTO.Builder("").build();
//		AttachmentDTO attachmentDTO1 = new AttachmentDTO.Builder(attachmentHeader1).build();
//		// 附件实例
//		AttachmentHeaderDTO attachmentHeader2 = new AttachmentHeaderDTO.Builder("").build();
//		AttachmentDTO attachmentDTO2 = new AttachmentDTO.Builder(attachmentHeader2).build();
//		attachments.add(attachmentDTO1);
//		attachments.add(attachmentDTO2);
//
//		// 设置包体
//		AccountBalanceReqDTO body = new AccountBalanceReqDTO.Builder("11011781161701").build();
//
//		// 设置报文头
//		HeaderDTO header = new HeaderDTO.Builder(TradeCode.AccountBalanceReq.getValue(),body.toString().length(),attachments.size())
//		    .setCompanyCode("guiyi-000001")
//			.setOperatorCode("sddfg")
//			.setReqSequence("sfsdfsdfsdfsdfsdfsdfsdf")
//			.build();
//
//		 //封装包
//		 PackageDTO packageDTO = new PackageDTO.Builder(header,body,attachments).Build();
//		 
//		 logger.debug(packageDTO);
//		 
//		 //解析包
//		 
//	}	
}
