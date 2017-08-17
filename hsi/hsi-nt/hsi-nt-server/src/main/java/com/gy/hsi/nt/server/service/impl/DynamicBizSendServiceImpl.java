package com.gy.hsi.nt.server.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.nt.api.beans.DynamicBizMsgBean;
import com.gy.hsi.nt.api.common.DateUtil;
import com.gy.hsi.nt.api.common.MsgType;
import com.gy.hsi.nt.api.common.RespCode;
import com.gy.hsi.nt.api.exception.NoticeException;
import com.gy.hsi.nt.server.entity.dynamic.BodyRequest;
import com.gy.hsi.nt.server.entity.dynamic.Content;
import com.gy.hsi.nt.server.entity.dynamic.DynamicMsgRequest;
import com.gy.hsi.nt.server.entity.dynamic.Header;
import com.gy.hsi.nt.server.service.IDynamicBizSendService;
import com.gy.hsi.nt.server.util.CacheUtil;
import com.gy.hsi.nt.server.util.HttpClientUtil;
import com.gy.hsi.nt.server.util.ResNoUtil;
import com.gy.hsi.nt.server.util.StringUtil;
import com.gy.hsi.nt.server.util.enumtype.ParamsKey;
import com.gy.hsi.nt.server.util.enumtype.PushType;
import com.gy.hsi.nt.server.util.enumtype.ResType;

/**
 * 业务互动消息处理实现类
 *
 * @version V3.0.0
 * @Package: com.gy.hsi.nt.server.service.impl
 * @ClassName: DynamicBizSendServiceImpl
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月26日 下午4:12:42
 * @company: gyist
 */
@Service("dynamicBizSendService")
public class DynamicBizSendServiceImpl implements IDynamicBizSendService {

	private static final Logger logger = Logger.getLogger(DynamicBizSendServiceImpl.class);

	/**
	 * 互动参数
	 */
	private static String dynamicUrl;
	private static String dynamicAccessId;
	private static String dynamicAccessPwd;
	private static String dynamicAppKey;
	private static String dynamicSign;

	private final static String SUCCESS = "200";

	/**
	 * 发送业务互生消息
	 *
	 * @param bean
	 * @return
	 * @Description:
	 */
	@Override
	public String ntSendDynamicBiz(DynamicBizMsgBean bean)
	{
		logger.info("业务互动消息发送:" + bean);
		try
		{
			initConfigParam();
			if (sendDynamicMsg(bean))
			{
				logger.info("业务互动消息发送成功");
				return RespCode.SUCCESS.name();
			}
			return RespCode.FAIL.name();
		} catch (NoticeException e)
		{
			logger.error("业务互动消息发送异常", e);
			return RespCode.ERROR.name();
		}
	}

	/**
	 * 重发业务互动消息
	 *
	 * @param bean
	 * @return : boolean
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午4:24:20
	 * @version V3.0.0
	 */
	@Override
	public boolean resendDynamicBiz(DynamicBizMsgBean bean)
	{
		logger.info("业务互动消息重发:" + bean);
		try
		{
			initConfigParam();
			if (sendDynamicMsg(bean))
			{
				logger.info("业务互动消息重发成功");
				return true;
			}
			return false;
		} catch (NoticeException ex)
		{
			logger.error("业务互动消息重发异常," + ex.getMessage(), ex);
			return false;
		}
	}

	/**
	 * 加载互动消息配置参数
	 *
	 * @return : void
	 * @throws NoticeException
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午3:45:36
	 * @version V3.0.0
	 */
	private void initConfigParam() throws NoticeException
	{
		Map<String, String> param = CacheUtil.getConfigCache();
		if (null == param)
		{
			throw new NoticeException("缓存中互动消息参数没有配置，请先配置!");
		}
		dynamicUrl = param.get(ParamsKey.DYNAMIC_URL.getKey());
		dynamicAccessId = param.get(ParamsKey.DYNAMIC_ACCESS_ID.getKey());
		dynamicAccessPwd = param.get(ParamsKey.DYNAMIC_ACCESS_PWD.getKey());
		dynamicAppKey = param.get(ParamsKey.DYNAMIC_APP_KEY.getKey());
		dynamicSign = param.get(ParamsKey.DYNAMIC_SIGN.getKey());
	}

	/**
	 * 发送业务互动消息
	 *
	 * @param bean
	 * @return : boolean
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午3:45:28
	 * @version V3.0.0
	 */
	private boolean sendDynamicMsg(DynamicBizMsgBean bean)
	{
		String sendParam = createDynamicSendParam(bean, getPrivateDesUids(bean.getMsgType(), bean.getMsgReceiver()));
		logger.info("调用互动推送业务消息参数:" + sendParam);
		String resp = HttpClientUtil.doPost(dynamicUrl, sendParam);
		return getResultCode(resp);
	}

	/**
	 * 解析结果
	 *
	 * @param resp
	 * @return : boolean
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午3:45:20
	 * @version V3.0.0
	 */
	private boolean getResultCode(String resp)
	{
		logger.info("调用互动发送业务消息返回结果======>" + resp);
		JSONObject json = JSONObject.parseObject(resp);
		if (null != json)
		{
			if (StringUtils.isNotBlank(json.getString("body")))
			{
				json = JSONObject.parseObject(json.getString("body"));
				if (json != null)
				{
					String retCode = json.getString("retCode");
					if (StringUtils.isNotBlank(retCode) && SUCCESS.equals(retCode))
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 创建互动参数
	 *
	 * @param bean
	 * @param desUids
	 * @return : String
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午3:44:38
	 * @version V3.0.0
	 */
	private String createDynamicSendParam(DynamicBizMsgBean bean, String desUids)
	{
		// 头部
		Header header = new Header();
		StringBuilder transactionID = new StringBuilder(DateUtil.dateTimeToStringNOSSS(new Date()));
		transactionID.append(String.valueOf(Math.round(Math.random() * (999 - 100) + 100)));
		header.setTransactionID(transactionID.toString());
		header.setAccessID(dynamicAccessId);
		header.setAccessPwd(dynamicAccessPwd);
		header.setAppKey(dynamicAppKey);
		header.setSign(dynamicSign);
		header.setTimeStamp(DateUtil.dateTimeToStringNO(new Date()));
		header.setExpires("100");
		header.setPushType(getPushType(StringUtil.arrayToString(bean.getMsgReceiver(), ",")));
		header.setSrcUid("e_" + bean.getSender());
		header.setDesUids(desUids);
		header.setDesGroupId(null);

		// 消息类
		String[] msgCode = null;
		if (StringUtils.isNotBlank(bean.getMsgCode()) && StringUtils.isNotBlank(bean.getSubMsgCode()))
		{
			msgCode = new String[] { bean.getMsgCode(), bean.getSubMsgCode() };
		} else
		{
			msgCode = getMessageCode(desUids);
		}
		// 请求实体
		BodyRequest body = new BodyRequest();
		Content content = new Content();
		content.setMsg_code(msgCode[0]);
		content.setSub_msg_code(msgCode[1]);
		content.setMsg_subject(bean.getMsgTitle());
		content.setMsg_content(bean.getMsgContent());
		content.setMsg_icon("");
		content.setMsg_id("");
		content.setRes_no("");
		content.setMsg_note("");

		// body.setContent(content);
		body.setStrContent(StringUtil.jsonToTranslation(JSONObject.toJSONString(content)));

		// 发送互动消息
		DynamicMsgRequest dmr = new DynamicMsgRequest();
		dmr.setHeader(header);
		dmr.setBody(body);
		return JSONObject.toJSONString(dmr).replace("strContent", "content");
	}

	/**
	 * 解析推送类型
	 *
	 * @param receivers
	 * @return : String
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午3:44:28
	 * @version V3.0.0
	 */
	private String getPushType(String receivers)
	{
		String receiver = receivers.trim().replaceAll("^,{1,}|,{1,}$", "");
		boolean isSingle = (0 > receiver.indexOf(","));
		return isSingle ? PushType.SINGLE.getPushType() : PushType.MULTIPLE.getPushType();
	}

	/**
	 * 获取消息大类
	 *
	 * @param desUids
	 * @return : String[]
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午3:44:21
	 * @version V3.0.0
	 */
	private String[] getMessageCode(String desUids)
	{
		/**
		 * 私信: 消费者
		 */
		if (StringUtils.isNotBlank(desUids) && desUids.trim().startsWith("c_"))
		{
			return new String[] { "101", "10101" };
		}
		return new String[] { "203", "20301" };
	}

	/**
	 * 获取私信用户群
	 *
	 * @param msgType
	 * @param receivers
	 * @return : String
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午3:44:12
	 * @version V3.0.0
	 */
	private String getPrivateDesUids(int msgType, String[] receivers)
	{
		if (MsgType.PRIVATE_LETTER.getMsgType() != msgType)
		{
			return "";
		}
		StringBuilder buffer = new StringBuilder();

		/*
		 * String commaRegex =
		 * "((((\\s*)|(\\t*)|(\\r*)|(\\n*))(\\,+)((\\s*)|(\\t*)|(\\r*)|(\\n*)))+)";
		 * String underlineRegex =
		 * "(\\_([^,])+\\,)|(\\_\\,)|(\\_([^,])+)|(\\_$)|(\\s*_)"; String
		 * prefixCommaRegex = new
		 * StringBuilder("(^").append(commaRegex).append("|\\s*)").append("|(")
		 * .append(commaRegex).append("|\\s*$)").toString(); String receiver =
		 * receivers.replaceAll(prefixCommaRegex, "").replaceAll(commaRegex,
		 * ","); String[] receiverArray = receiver.split(",");
		 */

		for (String receiver : receivers)
		{
			if (StringUtils.isBlank(receiver) || receiver.length() < 11)
			{
				continue;
			}
			String resNo = receiver.substring(0, 11);
			if (!resNo.matches("[0-9]{11}"))
			{
				continue;
			}
			// 业务消息发给管理员：e_企业互生号_员工登录用户名
			if (!ResNoUtil.type(resNo).equals(ResType.P.name()))
			{
				buffer.append("e_").append(receiver);
				// 如果没有指定, 则默认发给管理员0000
				if (resNo.equals(receiver) && receiver.indexOf("_") <= 0)
				{
					buffer.append("_0000");
				}
				buffer.append(",");
			}
			// 持卡人
			else
			{
				buffer.append("c_").append(receiver).append(",");
			}
		}
		return buffer.toString().replaceAll("^,{1,}|,{1,}$", "");
	}
}
