package com.gy.hsi.nt.server.service;

import com.gy.hsi.nt.api.beans.NoteBean;
/**
 * 
 * @className:INoteSendService
 * @author:likui
 * @date:2015年7月28日
 * @desc:短信处理接口
 * @company:gysit
 */
public interface INoteSendService {
	/**
	 * 
	* @author:likui
	* @created:2015年7月28日下午5:52:35
	* @desc:发送短信
	* @param:@param note
	* @param:@return
	* @param:String
	* @throws
	 */
	public String ntSendNote(NoteBean note); 
	
	/**
	 * 
	* @author:likui
	* @created:2015年7月31日下午12:01:30
	* @desc:失败后的重发
	* @param:@param note
	* @param:@return
	* @param:boolean
	* @throws
	 */
	public boolean resendNote(NoteBean note);
}
