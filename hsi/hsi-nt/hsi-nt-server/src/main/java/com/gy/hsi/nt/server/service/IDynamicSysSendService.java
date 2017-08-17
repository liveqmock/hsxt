package com.gy.hsi.nt.server.service;

import com.gy.hsi.nt.api.beans.DynamicSysMsgBean;

/**
 * 处理系统互动信息接口
 * 
 * @Package: com.gy.hsi.nt.server.service
 * @ClassName: IDynamicSendService
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月26日 下午12:28:38
 * @company: gyist
 * @version V3.0.0
 */
public interface IDynamicSysSendService {

	/**
	 * 发送系统互动消息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午4:06:16
	 * @param bean
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	public String ntSendDynamicSys(DynamicSysMsgBean bean);

	/**
	 * 失败后的重发
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午4:06:36
	 * @param bean
	 * @return
	 * @return : boolean
	 * @version V3.0.0
	 */
	public boolean resendDynamicSys(DynamicSysMsgBean bean);

}
