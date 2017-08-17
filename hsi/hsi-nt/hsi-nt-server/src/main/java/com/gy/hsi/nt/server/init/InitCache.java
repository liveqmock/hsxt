package com.gy.hsi.nt.server.init;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.nt.server.entity.MsgTemp;
import com.gy.hsi.nt.server.util.CacheUtil;
import com.gy.hsi.nt.server.util.ExcelRead;
import com.gy.hsi.nt.server.util.PropertyConfigurer;
import com.gy.hsi.nt.server.util.enumtype.ParamsKey;
import com.gy.hsi.nt.server.util.enumtype.Usable;

/**
 * 初始化加载数据
 * 
 * @Package: com.gy.hsi.nt.server.init
 * @ClassName: InitCache
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月26日 下午2:19:55
 * @company: gyist
 * @version V3.0.0
 */
public class InitCache implements Serializable {

	private static final Logger logger = Logger.getLogger(InitCache.class);

	private static final long serialVersionUID = 6193311999721138236L;

	private String excelPath = "/config/NoteAndEmailTemp.xls";

	/**
	 * 加载配置参数
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午2:19:50
	 * @return : void
	 * @version V3.0.0
	 */
	public void init()
	{
		initConfigParam();
		logger.info("<<<<<<<<<<<<<<<<<加载系统配置参数成功>>>>>>>>>>>>>>>>>>>>>>>>");
		// initMsgTemp();
		// logger.info("<<<<<<<<<<<<<<<<加载短信和邮箱模板成功>>>>>>>>>>>>>>>>>>>>>>");
	}

	/**
	 * 重新加载参数
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午2:20:05
	 * @return : void
	 * @version V3.0.0
	 */
	public void resendInit()
	{
		logger.info("<<<<<<<<<<<<<<<<<重新加载系统配置参数>>>>>>>>>>>>>>>>>>>>>");
		CacheUtil.getCache().clear();
		init();
	}

	/**
	 * 加载信息模板(短信，邮件)
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午2:20:13
	 * @return : void
	 * @version V3.0.0
	 */
	@SuppressWarnings("unused")
	private void initMsgTemp()
	{
		logger.info("<<<<<<<<<<<<<<<<<加载短信和邮箱模板>>>>>>>>>>>>>>>>>>>>>>>>");
		List<Object> list = ExcelRead.readExcel(this.getClass().getResource(excelPath).getPath(), MsgTemp.class, 2);
		if (null != list && list.size() > 0)
		{
			String json = JSONObject.toJSONString(list);
			List<MsgTemp> tempList = JSONObject.parseArray(json, MsgTemp.class);
			for (MsgTemp temp : tempList)
			{
				if (temp.getIsUsable().equals(Usable.Y.name()))
				{
					CacheUtil.put(temp.getTempNo(), temp);
				}
			}
		}
	}

	/**
	 * 初始化系统配置参数到缓存
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月26日 下午2:20:21
	 * @return : void
	 * @version V3.0.0
	 */
	private void initConfigParam()
	{
		logger.info("<<<<<<<<<<<<<<<<<<加载系统配置参数>>>>>>>>>>>>>>>>>>>>>>>>");
		Map<String, String> param = PropertyConfigurer.getCtxpropertiesmap();
		if (null != param && param.size() > 0)
		{
			CacheUtil.put(ParamsKey.NT_PARAMS.getKey(), param);
			CacheUtil.put(ParamsKey.HIGH_RESEND_ORDER.getKey(), param.get(ParamsKey.HIGH_RESEND_ORDER.getKey()));
			CacheUtil.put(ParamsKey.MIDDLE_RESEND_ORDER.getKey(), param.get(ParamsKey.MIDDLE_RESEND_ORDER.getKey()));
			CacheUtil.put(ParamsKey.LOW_RESEND_ORDER.getKey(), param.get(ParamsKey.LOW_RESEND_ORDER.getKey()));
			CacheUtil.put(ParamsKey.PHONE_PATTERN.getKey(), param.get(ParamsKey.PHONE_PATTERN.getKey()));
			CacheUtil.put(ParamsKey.EMAIL_PATTERN.getKey(), param.get(ParamsKey.EMAIL_PATTERN.getKey()));
		}
	}
}
