package com.gy.hsi.nt.server.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.nt.api.beans.DynamicSysMsgBean;
import com.gy.hsi.nt.api.common.DateUtil;
import com.gy.hsi.nt.api.common.HsimMsgGroupCode;
import com.gy.hsi.nt.api.common.MsgStyle;
import com.gy.hsi.nt.api.common.MsgType;
import com.gy.hsi.nt.api.common.RespCode;
import com.gy.hsi.nt.api.exception.NoticeException;
import com.gy.hsi.nt.server.entity.dynamic.BodyRequest;
import com.gy.hsi.nt.server.entity.dynamic.Content;
import com.gy.hsi.nt.server.entity.dynamic.DynamicMsgRequest;
import com.gy.hsi.nt.server.entity.dynamic.Header;
import com.gy.hsi.nt.server.entity.dynamic.MsgContent;
import com.gy.hsi.nt.server.service.IDynamicSysSendService;
import com.gy.hsi.nt.server.util.CacheUtil;
import com.gy.hsi.nt.server.util.HttpClientUtil;
import com.gy.hsi.nt.server.util.ResNoUtil;
import com.gy.hsi.nt.server.util.StringUtil;
import com.gy.hsi.nt.server.util.enumtype.ParamsKey;
import com.gy.hsi.nt.server.util.enumtype.PushType;
import com.gy.hsi.nt.server.util.enumtype.ResType;

/**
 * 系统互动消息处理实现类
 *
 * @version V3.0.0
 * @Package: com.gy.hsi.nt.server.service.impl
 * @ClassName: DynamicSysSendServiceImpl
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月26日 下午4:05:07
 * @company: gyist
 */
@Service("dynamicSysSendService")
public class DynamicSysSendServiceImpl implements IDynamicSysSendService {

	private static final Logger logger = Logger.getLogger(DynamicSysSendServiceImpl.class);

	private static String dynamicUrl;
	private static String dynamicAccessId;
	private static String dynamicAccessPwd;
	private static String dynamicAppKey;
	private static String dynamicSign;

	private final static String SUCCESS = "200";

	/**
	 * 发送系统互生消息
	 *
	 * @param bean
	 * @return
	 * @Description:
	 */
	@Override
	public String ntSendDynamicSys(DynamicSysMsgBean bean)
	{
		logger.info("系统互动消息发送:" + JSONObject.toJSONString(bean));
		try
		{
			initConfigParam();
			if (sendDynamicMsg(bean))
			{
				logger.info("系统互动消息发送成功");
				return RespCode.SUCCESS.name();
			}
			return RespCode.FAIL.name();
		} catch (NoticeException e)
		{
			logger.error("系统互动消息发送异常", e);
			return RespCode.ERROR.name();
		}
	}

	/**
	 * 重发系统互动消息
	 *
	 * @param bean
	 * @return
	 * @Description:
	 */
	@Override
	public boolean resendDynamicSys(DynamicSysMsgBean bean)
	{
		logger.info("系统互动消息重发:" + bean);
		try
		{
			initConfigParam();
			if (sendDynamicMsg(bean))
			{
				logger.info("系统互动消息重发成功");
				return true;
			}
			return false;
		} catch (NoticeException ex)
		{
			logger.error("互动消息重发异常," + ex.getMessage(), ex);
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
	 * 发送系统互动消息
	 *
	 * @param bean
	 * @return : boolean
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午3:45:28
	 * @version V3.0.0
	 */
	private boolean sendDynamicMsg(DynamicSysMsgBean bean)
	{
		boolean falg = true;
		List<String> sendParam = createSendParam(bean);
		for (String param : sendParam)
		{
			logger.info("调用互动推送互动消息参数:" + param);
			String resp = HttpClientUtil.doPost(dynamicUrl, param);
			falg = getResultCode(resp);
		}
		return falg;
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
		logger.info("调用互动发送系统消息返回结果======>" + resp);
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
	 * 创建发送参数
	 *
	 * @param bean
	 * @return : List<String>
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午3:45:08
	 * @version V3.0.0
	 */
	private List<String> createSendParam(DynamicSysMsgBean bean)
	{
		List<String> param = new ArrayList<String>();
		List<String> desGroupList = getPublicMsgDesGroupId(bean);
		/**
		 * 公开信
		 */
		if (null != desGroupList && desGroupList.size() > 0)
		{
			for (String desGroup : desGroupList)
			{
				if (StringUtils.isNotBlank(desGroup))
				{
					param.add(createDynamicSendParam(bean, desGroup, null));
				}
			}
		} else
		{
			// String[] desUidsList = groupDesUidsByPrefix(
			// getPrivateDesUids(bean.getMsgType().intValue(),
			// bean.getMsgReceiver()),
			// new String[] { "e_", "c_" });
			// for (String desUid : desUidsList)
			// {
			// if (StringUtils.isNotBlank(desUid))
			// {
			//
			// param.add(createDynamicSendParam(bean, null, desUid));
			// }
			// }

			param.add(createDynamicSendParam(bean, null,
					getPrivateDesUids(bean.getMsgType().intValue(), bean.getMsgReceiver())));
		}
		return param;
	}

	/**
	 * 组装desGroupId数组[只针对公开信]
	 *
	 * @param dynamic
	 * @return : List<String>
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午3:44:48
	 * @version V3.0.0
	 */
	private List<String> getPublicMsgDesGroupId(DynamicSysMsgBean dynamic)
	{
		if (null == dynamic)
		{
			return null;
		}
		if (MsgType.PRIVATE_LETTER.getMsgType() != dynamic.getMsgType().intValue())
		{
			if (dynamic.getMsgReceiver() == null)
			{
				return null;
			}
			List<String> desGroupEnums = Arrays.asList(dynamic.getMsgReceiver());
			List<String> desGroupIdList = new ArrayList<String>();
			// String underlineRegex =
			// "(\\_([^,])+\\,)|(\\_\\,)|(\\_([^,])+)|(\\_$)|(\\s*_)";
			if (desGroupEnums.contains(HsimMsgGroupCode.ALL.name()))
			{
				desGroupEnums.clear();
				desGroupEnums.add(HsimMsgGroupCode.ALL.name());
			} else if (desGroupEnums.contains(HsimMsgGroupCode.S_ALL.name()))
			{
				desGroupEnums.remove(HsimMsgGroupCode.S_ALL_B.name());
				desGroupEnums.remove(HsimMsgGroupCode.S_ALL_T.name());
			}
			for (String desGroupEnum : desGroupEnums)
			{
				if (!desGroupIdList.contains(desGroupEnum))
				{
					desGroupIdList.add(desGroupEnum);
				}
			}
			return desGroupIdList;
		}
		return null;
	}

	/**
	 * 创建互动参数
	 *
	 * @param bean
	 * @param desGroupId
	 * @param desUids
	 * @return : String
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午3:44:38
	 * @version V3.0.0
	 */
	private String createDynamicSendParam(DynamicSysMsgBean bean, String desGroupId, String desUids)
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
		header.setPushType(
				getPushType(bean.getMsgType().intValue(), StringUtil.arrayToString(bean.getMsgReceiver(), ",")));
		header.setSrcUid("e_" + bean.getSender());
		header.setDesUids(StringUtil.navNull(desUids));
		if (StringUtils.isNotBlank(desGroupId))
		{
			header.setDesGroupId(HsimMsgGroupCode.getHsimCode(desGroupId) + bean.getSender().substring(0, 11));
		}

		String[] msgCode = getMessageCode(desGroupId, desUids, bean.getSender());
		// 请求实体
		BodyRequest body = new BodyRequest();
		Content content = new Content();
		content.setMsg_code(msgCode[0]);
		content.setSub_msg_code(msgCode[1]);
		content.setMsg_subject(bean.getMsgTitle());
		content.setMsg_icon("");
		content.setMsg_id("");
		content.setRes_no("");
		content.setMsg_note("");

		// 消息主体
		MsgContent msgContent = new MsgContent();
		msgContent.setMsgid(bean.getMsgId());// 消息ID
		msgContent.setMsgStyle(MsgStyle.TEXT.getStyle());// 消息样式
		msgContent.setSummary(bean.getMsgSummary());// 消息简述
		msgContent.setPageUrl(bean.getMsgContent());// 消息内容
		msgContent.setRealPicUrl("");// 图片url
		msgContent.setSmallPicUrl("");// 缩略图url
		content.setMsg_content(StringUtil.jsonToTranslation(JSONObject.toJSONString(msgContent)));

		body.setContent(content);

		// 发送互动消息
		DynamicMsgRequest dmr = new DynamicMsgRequest();
		dmr.setHeader(header);
		dmr.setBody(body);
		return JSONObject.toJSONString(dmr);
	}

	/**
	 * 解析推送类型
	 *
	 * @param msgType
	 * @param receivers
	 * @return : String
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午3:44:28
	 * @version V3.0.0
	 */
	private String getPushType(int msgType, String receivers)
	{
		if (MsgType.PRIVATE_LETTER.getMsgType() != msgType)
		{
			return PushType.GROUP.getPushType();
		}
		if (MsgType.PRIVATE_LETTER.getMsgType() == msgType)
		{
			String receiver = receivers.trim().replaceAll("^,{1,}|,{1,}$", "");
			boolean isSingle = (0 > receiver.indexOf(","));
			return isSingle ? PushType.SINGLE.getPushType() : PushType.MULTIPLE.getPushType();
		}
		return PushType.SINGLE.getPushType();
	}

	/**
	 * 获取消息大类
	 *
	 * @param desGroupId
	 * @param desUids
	 * @return : String[]
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午3:44:21
	 * @version V3.0.0
	 */
	private String[] getMessageCode(String desGroupId, String desUids, String sender)
	{
		/**
		 * 公开信：按群组发送
		 */
		if (StringUtils.isNotBlank(desGroupId))
		{
			String[] msgCode = null;
			switch (HsimMsgGroupCode.getHsimMsgGroupCode(desGroupId))
			{
			case ALL:
				msgCode = new String[] { "203", "20301" };
				break;
			case ALL_M:
				msgCode = new String[] { "203", "20301" };
				break;
			case ALL_S:
				msgCode = new String[] { "203", "20301" };
				break;
			case ALL_T:
				msgCode = new String[] { "203", "20301" };
				break;
			case ALL_B:
				msgCode = new String[] { "203", "20301" };
				break;
			case ALL_P:
				msgCode = new String[] { "203", "20301" };
				break;
			case M_ALL_S:
				msgCode = new String[] { "203", "20304" };
				break;
			case S_ALL_T:
				msgCode = new String[] { "203", "20303" };
				break;
			case S_ALL_B:
				msgCode = new String[] { "203", "20303" };
				break;
			case S_ALL:
				msgCode = new String[] { "203", "20303" };
				break;
			default:
				break;
			}
			return msgCode;
		}
		/**
		 * 私信: 消费者|企业
		 */
		if (StringUtils.isNotBlank(desUids))
		{
			return new String[] { "101", "10101" };
		}
		return null;
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
			// String resNo = value.replaceAll(underlineRegex, "").trim();
			if (StringUtils.isBlank(receiver) || receiver.length() < 11)
			{
				continue;
			}
			String resNo = receiver.substring(0, 11);
			if (!resNo.matches("[0-9]{11}"))
			{
				continue;
			}
			// 持卡消费者：c_积分卡号
			// 企业员工(未绑定积分卡)：e_企业互生号_员工登录用户名
			// 企业
			if (!ResNoUtil.type(resNo).equals(ResType.P.name()))
			{
				buffer.append("e_").append(receiver);
				// 如果没有指定, 则默认发给管理员0000
				if (resNo.equals(receiver) && receiver.indexOf("_") <= 0)
				{
					buffer.append("_0000");
				}
				buffer.append(",");
				// 持卡人
			} else
			{
				buffer.append("c_").append(receiver).append(",");
			}
		}
		return buffer.toString().replaceAll("^,{1,}|,{1,}$", "");
	}

	/**
	 * 分为企业和个人两个组
	 *
	 * @param desUids
	 * @param prefixes
	 * @return : String[]
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午3:44:02
	 * @version V3.0.0
	 */
	private String[] groupDesUidsByPrefix(String desUids, String[] prefixes)
	{
		List<String> list = new ArrayList<String>();
		for (String prefix : prefixes)
		{
			list.add(desUids.replaceAll(prefix + "[^\\,]+\\,{0,1}", "").replaceAll("^,{1,}|,{1,}$", ""));
		}
		return list.toArray(new String[] {});
	}
}
