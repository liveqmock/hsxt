package com.gy.hsxt.access.pos.point.data;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * 专用于发送短byte[]给ServerSide，可以放在Spring里配置成单态的Bean。
 * 为了增强性能，这是修改过的。
 *
 * @author Huang, GengLian
 */

public class SocketClient {

	final String host;
	final int port;
	
	/**
	 * socket 暂定3秒超时
	 */
	public static final int SOCKET_TIMEOUT = 3000;
	

	public SocketClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	/**
	 * 发送给KeyServer，用这个api。
	 * GengLian modified at 2014/11/18.
	 * int dataLen = 4 + bodyLen; // 占位1byte+类型1byte+长度2byte+bodylen
	 * 读取的时候，会跳过头部的2bytes。
	 * 
	 */
	public byte[] sendToKeyServer(byte headTypeBs, byte[] bodyBs) {
		byte buf[] = null;
		
		final int bodyLen = bodyBs.length;
		
		final byte[] lenBs = { (byte) (bodyLen >> 8), (byte) (bodyLen & 0xff) };
		try (
				final Socket skt = new Socket(host, port);
				final OutputStream out = skt.getOutputStream();
				final InputStream in = skt.getInputStream()) {
			skt.setSoTimeout(SOCKET_TIMEOUT);
			final ByteBuffer bb = ByteBuffer.allocate(4 + bodyLen);
			bb.position(1);
			out.write(bb.put(headTypeBs).put(lenBs).put(bodyBs).array());
			out.flush();
			in.skip(2);
			in.read(lenBs);
			buf = new byte[(lenBs[0] << 8) + lenBs[1]]; // round brackets is mandatory.
			in.read(buf);
			if(buf.length > 0){
			}
		} catch (Exception e) {
		}
		return buf;
	}

	/**
	 * 发送普通socket，用这个api。
	 * 
	 * 原始的是用DataInputStream/DataOutputStream，这当然在code上简单些。
	 * 但此处，是性能很要紧之处，所以，搞移位更快。
	 */
	public byte[] send(byte[] bs) throws Exception {
		final int v = bs.length;
		byte buf[] = null;
		try (final Socket skt = new Socket(host, port);
				final OutputStream out = skt.getOutputStream();
				final InputStream in = skt.getInputStream()) {
			out.write((v >>> 8) & 0xFF); // it was copied from JDK.
			out.write(v & 0xFF);
			out.write(bs);
			out.flush();
			buf = new byte[(in.read() << 8) + in.read()]; // round brackets is mandatory.
			in.read(buf);
		} catch (Exception e) {
		}
		return buf;
	}
}

