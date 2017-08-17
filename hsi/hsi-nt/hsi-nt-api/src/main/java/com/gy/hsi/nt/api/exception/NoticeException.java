package com.gy.hsi.nt.api.exception;

/**
 * 
 * @className:NoticeException
 * @author:likui
 * @date:2015年7月28日
 * @desc:通知系统异常类
 * @company:gyist
 */
public final class NoticeException extends Exception {

	private static final long serialVersionUID = -190504743209029813L;

	/**
	 * 错误代码
	 */
	private String errorCode;

	public NoticeException()
	{
		super();
	}

	public NoticeException(String message)
	{
		super(message);
	}

	public NoticeException(String message, String errorCode)
	{
		super(message);
		this.errorCode = errorCode;
	}

	public NoticeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public String getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}
}
