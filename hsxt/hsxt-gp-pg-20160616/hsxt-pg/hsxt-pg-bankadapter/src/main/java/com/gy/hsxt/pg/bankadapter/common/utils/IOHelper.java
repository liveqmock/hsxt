package com.gy.hsxt.pg.bankadapter.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

/**
 * @author jbli
 *
 */
public class IOHelper {

	private static final Logger logger = Logger.getLogger(IOHelper.class);
	
	/**
	 * 从输入流中拷贝数据到输出流中，只读取定长的字节，默认包括对流的关闭。
	 * @param in
	 * @param out
	 */	
	public static void copyFixedBytes(InputStream in, OutputStream out, int fixedSize, boolean closeIn,boolean closeOut)
			throws IOException {
		int bufferSize = 0;
		if(fixedSize-1024<=0){
			bufferSize = fixedSize;
			byte buf[] = new byte[bufferSize];
			int bytesRead = in.read(buf);
			out.write(buf, 0, bytesRead);
		}else{
			bufferSize = 1024;
			byte buf[] = new byte[bufferSize];
			int bytesRead = 0;
			for(int i= 0; i< fixedSize/bufferSize; i++){
				bytesRead = bytesRead+in.read(buf);
				out.write(buf, 0, bytesRead);
			}
			int left = fixedSize%bufferSize;
			byte buf2[] = new byte[left];
			bytesRead = bytesRead+in.read(buf2);
			out.write(buf, 0, bytesRead);
		}
		try{
			if(closeIn){
				in.close();
				in = null;
			}
			
			if(closeOut){
				out.close();
				out = null;
			}
		} finally {
			if (closeIn) {
				closeStream(in);
			}
			if (closeOut) {
				closeStream(out);
			}			
		}
	}			
	
	/**
	 * 从输入流中拷贝数据到输出流中，默认使用4K的缓存区。
	 * @param in
	 * @param out
	 * @param closeIn
	 * @param closeOut
	 * @throws IOException
	 */
	public static void copyBytes(InputStream in, OutputStream out, boolean closeIn,boolean closeOut) throws IOException {
		try {
			copyBytes(in, out,4096);
			if(closeIn){
				in.close();
				in = null;
			}
			
			if(closeOut){
				out.close();
				out = null;
			}
		} finally {
			if (closeIn) {
				closeStream(in);
			}
			if (closeOut) {
				closeStream(out);
			}			
		}
	}
	
	public static void copyBytes(InputStream in, OutputStream out, int buffSize)
			throws IOException {
		byte buf[] = new byte[buffSize];
		int bytesRead = in.read(buf);
		while (bytesRead >= 0) {
			out.write(buf, 0, bytesRead);
			bytesRead = in.read(buf);
		}
	}

	public static void closeStream(java.io.Closeable stream) {
		cleanup(null, stream);
	}

	public static void cleanup( java.io.Closeable... closeables) {
		for (java.io.Closeable c : closeables) {
			if (c != null) {
				try {
					c.close();
				} catch (IOException e) {
					logger.error(" Exception in closing ",e);
				}
			}
		}
	}
}
