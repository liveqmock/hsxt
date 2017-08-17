package com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.utils.connection;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

/**
 * 
 * 此代码有中国银联开发人员提供
 *
 */
public abstract class CPHttpConnection {
	private static final Logger logger = Logger.getLogger(CPHttpConnection.class);

	/**
	 * 信息编码
	 */
	private String msgEncoding;
	
	/**
	 * 接收响应的最大值
	 */
	protected static final int MAXLEN = 4096;

	/**
	 * 不限制数据流大小
	 */
	protected static final int NOLIMITLEN = 0;

	/**
	 * 限制数据流大小为4k
	 */
	protected static final int LIMITLEN = 1;

	/**
	 * 接收响应的长度设置类型<br>
	 * 不限制长度大小,根据http返回内容长度获得:0<br>
	 * 限制长度大小为MAXLEN:1<br>
	 */
	protected int lenType = LIMITLEN;

	/** 接收报文的地址 **/
	protected String URL;

	/**
	 * 连接和接收响应的超时时间
	 */
	protected String timeOut;

	/**
	 * 发送的报文内容
	 */
	String sendData;

	/**
	 * 接收的报文内容
	 */
	protected byte[] receiveData;

	/**
	 * 输入流
	 */
	protected BufferedInputStream iBufferedInputStream = null;

	/**
	 * 发送报文到接收地址，并且接收返回信息
	 * 
	 * @param msgStr 需要发送的数据
	 * @return
	 */
	public int sendMsg(String msgStr) {
		HttpURLConnection urlCon;
		OutputStream tOut = null;
		int result = CPHttpSendResultCode.PROCESS_DATA_FAILED;

		// 设置连接和超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "" + timeOut);
		System.setProperty("sun.net.client.defaultReadTimeout", "" + timeOut);

		// 建立连接，发送数据
		try {
			// 创建URL对象
			URL tUrl = new URL(URL);
			urlCon = (HttpURLConnection) tUrl.openConnection();
		} catch (MalformedURLException mue) {
			logger.error("connect failed! errCode=[-12];errMsg=[无法连接对方主机]");
			
			return CPHttpSendResultCode.CANNOT_CENNECT_TARGET_HOST;
		} catch (IOException ioe) {
			logger.error("connect failed! errCode=[-14];errMsg=[通讯建链失败]");
			
			return CPHttpSendResultCode.COMMUNICATION_LINK_FAILURE;
		}

		try {
			logger.debug("sendData=[" + msgStr + "]");

			urlCon.setRequestMethod("POST");
			urlCon.setDoOutput(true);
			urlCon.setDoInput(true);
			tOut = urlCon.getOutputStream();

			// 判断字符编码是否为空，不为空，则设置编码
			if (getMsgEncoding() == null || getMsgEncoding().equals("")) {
				tOut.write(msgStr.getBytes());
			} else {
				tOut.write(msgStr.getBytes(getMsgEncoding()));
			}
			
			tOut.flush();
		} catch (IOException ioe) {
			logger.error("Http connect failed! errCode=[-52];errMsg=[process data timeout]");
			
			return CPHttpSendResultCode.PROCESS_DATA_TIMEOUT;
		} catch (Exception e) {
			logger.error("process data failed!");
			
			return CPHttpSendResultCode.PROCESS_DATA_FAILED;
		} finally {
			try {
				if (tOut != null) {
					tOut.close();
				}
			} catch (IOException e) {
				logger.error("output stream failed!");
				
				return CPHttpSendResultCode.PROCESS_DATA_FAILED;
			}
		}

		// 以数据流的方式接收响应数据
		InputStream input = null;
		
		try {
			int respCode = urlCon.getResponseCode();
			logger.debug("http ResponseCode=[" + respCode + "]");
			
			// 目标服务器成功响应
			if (200 == respCode) {
				int contentLen = urlCon.getContentLength();
				input = urlCon.getInputStream();

				// 获取 ContentLength失败，则采用ByteArrayOutputStream获取流内容
				logger.debug("ContentLength=[" + contentLen + "]");
				
				if (contentLen == -1) {
					logger.debug("get ContentLength failed，by ByteArrayOutputStream to get data");
					receiveData = getBytes(input, lenType == NOLIMITLEN ? 0
							: MAXLEN);
				} else {
					// 获取 ContentLength 成功
					int len = lenType == NOLIMITLEN ? contentLen : Math.min(
							contentLen, MAXLEN);
					logger.debug("ReceiveData Length=[" + len + "]");
					
					receiveData = new byte[len];
					
					int ch;
					int i = 0;
					
					while ((ch = input.read()) != -1) {
						receiveData[i++] = (byte) ch;
						
						if (i == len) {
							break;
						}
					}
				}
				
				result = CPHttpSendResultCode.SUCCESS;
			} else {
				logger.debug(new StringBuilder("server return error code=[")
						.append(respCode).append("];respMsg=[")
						.append(urlCon.getResponseMessage()).append("]").toString());
			}
		} catch (IOException ioex) {
			logger.error("connect failed! errCode=[-54];errMsg=[receive data error]");
			
			return CPHttpSendResultCode.RECEIVE_DATA_ERROR;
		} catch (Exception ex) {
			logger.error("System error!");
			
			return CPHttpSendResultCode.PROCESS_DATA_FAILED;
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				logger.error("close input stream failed!");
				
				return CPHttpSendResultCode.PROCESS_DATA_FAILED;
			}
		}

		return result;
	}

	/**
	 * 获得对方返回的报文内容
	 * 
	 * @return
	 */
	public abstract byte[] getReceiveData();

	public String getMsgEncoding() {
		return msgEncoding;
	}

	public void setMsgEncoding(String msgEncoding) {
		this.msgEncoding = msgEncoding;
	}

	public int getLenType() {
		return lenType;
	}

	public void setLenType(int lenType) {
		this.lenType = lenType;
	}

	/**
	 * 获取流内容
	 * 
	 * @param is 输入流
	 * @param limitSize 获取流最大长度，不限制设置为0
	 * @return 流内容byte数组
	 * @throws IOException 处理失败抛出异常
	 */
	public byte[] getBytes(InputStream is, int limitSize)
			throws IOException {
		if (is == null) {
			throw new IOException("InputStream is Closed!");
		}
		
		ByteArrayOutputStream os = null;
		
		try {
			int ch;
			os = new ByteArrayOutputStream();
			int count = 0;
			
			while ((ch = is.read()) != -1) {
				if (limitSize != 0 && count >= limitSize) {
					break;
				}
					
				os.write(ch);
				count++;
			}
			
			os.flush();
			
			logger.debug("getBytes:Length=[" + count + "]");
			
			return os.toByteArray();
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}
	
	public static class CPHttpSendResultCode {
		/** 成功 **/
		public static final int SUCCESS = 1;
		
		/** 数据处理失败  **/
		public static final int PROCESS_DATA_FAILED = -1;
		
		/** 无法连接对方主机  **/
		public static final int CANNOT_CENNECT_TARGET_HOST = -12;
		
		/** 通讯建链失败  **/
		public static final int COMMUNICATION_LINK_FAILURE = -14;
		
		/** process data timeout  **/
		public static final int PROCESS_DATA_TIMEOUT = -52;

		/** receive data error  **/
		public static final int RECEIVE_DATA_ERROR = -54;
	}
}
