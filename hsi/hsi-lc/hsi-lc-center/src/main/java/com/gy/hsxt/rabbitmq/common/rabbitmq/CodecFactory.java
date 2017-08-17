package com.gy.hsxt.rabbitmq.common.rabbitmq;



import java.io.IOException;

/**
 * 
 * @ClassName: CodecFactory 
 * @Description: 编码和解码工厂 
 * @author Lee 
 * @date 2015-7-3 下午2:42:53
 */
public interface CodecFactory {

	byte[] serialize(Object obj) throws IOException;
	
	Object deSerialize(byte[] in) throws IOException;

}
