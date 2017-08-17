package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.attachment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.gy.hsxt.pg.bankadapter.common.utils.IOHelper;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.IStreamWritable;

/**
 * @author jbli
 *
 */
public  class AttachmentDTO implements IStreamWritable{
	//附件包头 存储附件的原信息，附件包体的具体的内容，由配置的文件目录+文件名来读取，边读边发，或者边读编写入到目录。
	public AttachmentHeaderDTO attachmentHeader;
	
	public AttachmentHeaderDTO getAttachmentHeader() {
		return attachmentHeader;
	}
	/**
	 * 返回一个不带任何默认值的实例引用，用于解码
	 * @return
	 */
	private AttachmentDTO(Builder builder){
		this.attachmentHeader = builder.attachmentHeader;
	}	
	
	public static class Builder{
		
		public AttachmentHeaderDTO attachmentHeader;		
		
		public Builder(AttachmentHeaderDTO attachmentHeader){
			this.attachmentHeader = attachmentHeader;
		}
		
		public AttachmentDTO build(){
			return new AttachmentDTO(this);
		}
	}
	
	public void write(OutputStream out) throws IOException {
		//写附件包头
		attachmentHeader.write(out);
		//读取文件，向输出流中写入文件数据
		IOHelper.copyBytes(new FileInputStream(new File(attachmentHeader.getFileLength())), out, true, false);
	}

	public static AttachmentDTO readFields(InputStream in,OutputStream out) throws IOException {
		AttachmentHeaderDTO attachmentHeaderDTO = AttachmentHeaderDTO.readFields(in);
		int fileLength= Integer.parseInt(attachmentHeaderDTO.getFileLength());
		//将附件存储到接收目录下或者输出到响应流
		IOHelper.copyFixedBytes(in, out, fileLength, false, true);

		return new AttachmentDTO.Builder(attachmentHeaderDTO).build();
	}

	
	
	
}
