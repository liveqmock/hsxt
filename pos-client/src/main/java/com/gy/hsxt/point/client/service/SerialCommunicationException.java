package com.gy.hsxt.point.client.service;

/**
 * Simple to Introduction
 * 
 * @category Simple to Introduction
 * @projectName WritePOS
 * @package com.gy.pos.SerialCommunicationException.java
 * @className SerialCommunicationException
 * @description 通讯异常
 * @author fandi
 * @createDate 2014-8-8 下午12:30:04
 * @updateUser fandi
 * @updateDate 2014-8-8 下午12:30:04
 * @updateRemark 说明本次修改内容
 * @version V3.0
 */
@SuppressWarnings("serial")
public class SerialCommunicationException extends Exception {
	public SerialCommunicationException(String str) {
		super(str);
	}

	/**
	 * Constructs a <code>SerialConnectionException</code> with no detail
	 * message.
	 */
	public SerialCommunicationException() {
		super();
	}

}
