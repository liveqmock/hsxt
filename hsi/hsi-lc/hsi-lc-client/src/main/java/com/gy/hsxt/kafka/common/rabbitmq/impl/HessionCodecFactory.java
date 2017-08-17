package com.gy.hsxt.kafka.common.rabbitmq.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.gy.hsxt.kafka.common.rabbitmq.CodecFactory;

/**
 * 
 * @ClassName: HessionCodecFactory 
 * @Description: Hession序列号工厂
 * @author Lee 
 * @date 2015-7-3 下午3:17:21
 */
public class HessionCodecFactory implements CodecFactory {

	private static final Logger log = LoggerFactory
			.getLogger(HessionCodecFactory.class);
	
	/**
	 * 序列化
	 */
	@Override
	public byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream baos = null;
		HessianOutput output = null;
		try {
			baos = new ByteArrayOutputStream(1024);
			output = new HessianOutput(baos);
			output.startCall();
			output.writeObject(obj);
			output.completeCall();
		} catch (final IOException ex) {
			throw ex;
		} finally {
			if (output != null) {
				try {
					baos.close();
				} catch (final IOException ex) {
					log.error("Failed to close stream.", ex);
				}
			}
		}
		return baos != null ? baos.toByteArray() : null;
	}

	/**
	 * 反序列化
	 */
	@Override
	public Object deSerialize(byte[] in) throws IOException {
		Object obj = null;
		ByteArrayInputStream bais = null;
		HessianInput input = null;
		try {
			bais = new ByteArrayInputStream(in);
			input = new HessianInput(bais);
			input.startReply();
			obj = input.readObject();
			input.completeReply();
		} catch (final IOException ex) {
			throw ex;
		} catch (final Throwable e) {
			log.error("Failed to decode object.", e);
		} finally {
			if (input != null) {
				try {
					bais.close();
				} catch (final IOException ex) {
					log.error("Failed to close stream.", ex);
                }
            }
        }
        return obj;
	}

}
