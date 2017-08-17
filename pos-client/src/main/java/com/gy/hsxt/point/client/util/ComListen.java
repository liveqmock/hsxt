package com.gy.hsxt.point.client.util;

/**
 * Simple to Introduction
 * 
 * @category 与串口通讯的接口
 * @projectName WritePOS
 * @package com.gy.pos.ComListen.java
 * @className ComListen
 * @description 监听端口回调接口
 * @author fandi
 * @createDate 2014-8-13 上午9:15:09
 * @updateUser lyh
 * @updateDate 2014-8-13 上午9:15:09
 * @updateRemark 说明本次修改内容
 * @version V3.0
 */
public interface ComListen {
	void GetFromCom(byte[] Buffer, int length);
}
