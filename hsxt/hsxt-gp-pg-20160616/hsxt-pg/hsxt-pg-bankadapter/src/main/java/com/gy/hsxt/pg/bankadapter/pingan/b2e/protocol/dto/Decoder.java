package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto;

import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * @author jbli
 * 用于解包
 */
public class Decoder {
	
    private static final Logger logger = Logger.getLogger(Decoder.class);  
    
	private int offset;
	private byte bytes[];
	
	public Decoder(byte [] abyte){
		if(abyte == null){
			return;
		}
		bytes = abyte;
		offset =0;
	}
	
	/**
	 * 返回指定长度的字符串
	 * @return
	 */
	public String getString(int strLength) throws IOException{
		if((strLength+offset) > bytes.length) {
			throw new IOException("the strLength is wrong! ");
		}
		byte[] tmpByte = new byte[strLength];
		System.arraycopy(bytes, offset, tmpByte, 0, strLength);
		offset = offset +strLength;
		return new String(tmpByte,"UTF-8");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String header =  "A0010102010010107990000999900000000001104001       0120100809171028    2010080981026055                                                                                                          00000                       0";
		byte[]  bytes =null;
		try {
		bytes = header.getBytes("UTF-8");
		Decoder decoder = new Decoder(bytes);

			logger.debug("1:"+decoder.getString(6));
			logger.debug("2:"+decoder.getString(2));
			logger.debug("3:"+decoder.getString(2));
			logger.debug("4:"+decoder.getString(20));
			logger.debug("5:"+decoder.getString(10));
			
			logger.debug("6:"+decoder.getString(6));
			logger.debug("7:"+decoder.getString(5));
			logger.debug("8:"+decoder.getString(2));
			logger.debug("9:"+decoder.getString(8));
			logger.debug("10:"+decoder.getString(6));
			
			logger.debug("11:"+decoder.getString(20));
			logger.debug("12:"+decoder.getString(6));
			logger.debug("13:"+decoder.getString(100));
			logger.debug("14:"+decoder.getString(1));
			logger.debug("15:"+decoder.getString(3));
			
			logger.debug("16:"+decoder.getString(1));
			logger.debug("17:"+decoder.getString(1));
			logger.debug("18:"+decoder.getString(12));
			logger.debug("19:"+decoder.getString(10));			
			logger.debug("20:"+decoder.getString(1));
			
		}  catch (Exception e) {
			logger.error("in test main method error:",e);
		}
	}

}
