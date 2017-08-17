package com.gy.hsi.nt.server.service.impl;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.gy.hsi.nt.api.beans.NoteBean;
import com.gy.hsi.nt.api.common.RespCode;
import com.gy.hsi.nt.server.service.INoteSendService;
import com.gy.hsi.nt.server.util.CacheUtil;
import com.gy.hsi.nt.server.util.HttpClientUtil;
import com.gy.hsi.nt.server.util.StringUtil;
import com.gy.hsi.nt.server.util.enumtype.ParamsKey;

/**
 * 
 * @className:NoteSendServiceImpl
 * @author:likui
 * @date:2015年7月28日
 * @desc:短信处理实现类
 * @company:gyist
 */
@Service("noteSendService")
public class NoteSendServiceImpl implements INoteSendService {

	private static final Logger logger = Logger.getLogger(NoteSendServiceImpl.class);

	private static final String SEND_OK = "100";
	private static final String RESULTCODE = "resultCode";

	/**
	 * 发送短信
	 * 
	 * @Description:
	 * @param note
	 * @return
	 */
	@Override
	public String ntSendNote(NoteBean note)
	{
		try
		{
			logger.info("调用短信网关发送短信");
			Map<String, String> param = CacheUtil.getConfigCache();
			if (null == param)
			{
				logger.info("缓存中短信参数没有配置，请先配置!");
				return RespCode.FAIL.name();
			}
			String result = getResultCode(
					HttpClientUtil.doGet(param.get(ParamsKey.SMS_ADDRESS.getKey()), createParam(note, param)));
			if (result.equals(SEND_OK))
			{
				logger.info("短信发送成功");
				return RespCode.SUCCESS.name();
			}
			return RespCode.FAIL.name();
		} catch (Exception e)
		{
			logger.error("短信发送异常", e);
			return RespCode.ERROR.name();
		}
	}

	/**
	 * 重发短信
	 * 
	 * @Description:
	 * @param note
	 * @return
	 */
	@Override
	public boolean resendNote(NoteBean note)
	{
		logger.info("重发调用短信网关发送短信");
		try
		{
			Map<String, String> param = CacheUtil.getConfigCache();
			if (null == param)
			{
				logger.info("缓存中短信参数没有配置，请先配置!");
				return false;
			}
			String result = getResultCode(
					HttpClientUtil.doGet(param.get(ParamsKey.SMS_ADDRESS.getKey()), createParam(note, param)));
			if (result.equals(SEND_OK))
			{
				logger.info("短信重发成功");
				return true;
			}
			return false;
		} catch (Exception ex)
		{
			logger.error("短信重发异常", ex);
			return false;
		}
	}

	/**
	 * 生成短信发送参数
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月28日 下午3:12:42
	 * @param note
	 * @param param
	 * @return
	 * @return : Map<String,String>
	 * @version V3.0.0
	 */
	private Map<String, String> createParam(NoteBean note, Map<String, String> param)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("customerId", param.get(ParamsKey.SMS_CUSTOMERID.getKey()));
		params.put("userId", param.get(ParamsKey.SMS_USERID.getKey()));
		params.put("password", param.get(ParamsKey.SMS_PASSWORD.getKey()));
		params.put("phones", StringUtil.arrayToString(note.getMsgReceiver(), ","));
		params.put("cont", note.getMsgContent() + param.get(ParamsKey.SMS_SIGN.getKey()));
		params.put("appId", param.get(ParamsKey.SMS_APPID.getKey()));
		return params;
	}

	/**
	 * 解析短信返回数据
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月28日 下午3:12:49
	 * @param xmlStr
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	private String getResultCode(String xmlStr)
	{
		logger.info("调用短信网关返回结果======>" + xmlStr);
		String returnStr = "";
		String xml = StringUtils.trimToEmpty(xmlStr);
		if (StringUtils.isNotBlank(xml))
		{
			try
			{
				Reader reader = new StringReader(xmlStr);
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder domBuilder = builderFactory.newDocumentBuilder();
				Document document = domBuilder.parse(new InputSource(reader));
				return document.getElementsByTagName(RESULTCODE).item(0).getTextContent();
			} catch (Exception e)
			{
				logger.error("解析返回的字符串异常:" + e.getMessage(), e);
			}
		}
		return returnStr;
	}
}
