package com.gy.hsi.nt.server.mapper;

import java.util.List;
import java.util.Map;

import com.gy.hsi.nt.server.entity.result.AbandonMsg;
import com.gy.hsi.nt.server.entity.result.ResendMsg;
import com.gy.hsi.nt.server.entity.result.SendedMsg;

/**
 * 消息结果操作接口
 * 
 * @Package: com.gy.hsi.nt.server.mapper
 * @ClassName: MsgResultMapper
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月26日 下午5:29:03
 * @company: gyist
 * @version V3.0.0
 */
public interface MsgResultMapper {

	/**
	 * 增加成功消息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午5:28:57
	 * @param sended
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertSendedMsg(SendedMsg sended);

	/**
	 * 删除成功消息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午5:29:10
	 * @param date
	 * @return : void
	 * @version V3.0.0
	 */
	public void deleteSendedMsg(String date);

	/**
	 * 增加彻底失败的消息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午5:29:15
	 * @param abandon
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertAbandonMsg(AbandonMsg abandon);

	/**
	 * 删除彻底失败的消息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午5:29:20
	 * @param date
	 * @return : void
	 * @version V3.0.0
	 */
	public void deleteAbandonMsg(String date);

	/**
	 * 增加重发的消息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午5:29:31
	 * @param resend
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertResendMsg(ResendMsg resend);

	/**
	 * 删除重发消息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午5:29:36
	 * @param msgId
	 * @return : void
	 * @version V3.0.0
	 */
	public void deleteResendMsg(String msgId);

	/**
	 * 修改重发消息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午5:29:44
	 * @param resend
	 * @return : void
	 * @version V3.0.0
	 */
	public void updateResendMsg(ResendMsg resend);

	/**
	 * 查询可以重发的消息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午5:29:50
	 * @param param
	 * @return
	 * @return : List<ResendMsg>
	 * @version V3.0.0
	 */
	public List<ResendMsg> selectResendMsgList(Map<String, Object> param);

	/**
	 * 查询此优先级的待重发的数量
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午5:29:56
	 * @param priority
	 * @return
	 * @return : Integer
	 * @version V3.0.0
	 */
	public Integer selectResendCountByPriority(int priority);
}
