/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.mcs.services.messagecenter.imp;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsi.fs.client.FS;
import com.gy.hsi.fs.common.beans.SecurityToken;
import com.gy.hsi.fs.common.exception.FsException;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.FreemarkerUtils;
import com.gy.hsxt.access.web.mcs.services.messagecenter.IMessageCenterService;
import com.gy.hsxt.bs.api.message.IBSMsgSendService;
import com.gy.hsxt.bs.bean.message.Message;
import com.gy.hsxt.bs.bean.message.MessageQuery;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

@SuppressWarnings("rawtypes")
@Service
public class MessageCenterServiceImpl extends BaseServiceImpl implements IMessageCenterService {

    @Autowired
    private IBSMsgSendService bsMsgService;
    
    @Autowired
    private HsPropertiesConfigurer propertyConfigurer;
    
    @Override
    public PageData<Message> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
    	MessageQuery param = new MessageQuery();
    	String entCustId = (String)filterMap.get("entCustId");
    	param.setEntCustId(entCustId);
    	Integer status = Integer.parseInt(filterMap.get("status").toString());
    	param.setStatus(status);
    	try{
	    	String title = filterMap.get("msgTitle").toString();
	    	param.setTitle(title);
    	}catch(Exception e){
    	}
    	SystemLog.info("管理公司","分页查询","page="+JSON.toJSONString(page)+" param="+JSON.toJSONString(param));
    	return bsMsgService.queryMessageListPage(page, param);
    }
    
    @Override
    public void createMsgSend(Message msg,boolean isSend, String token) throws HsException {
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    	msg.setSendTime(df.format(new Date()));
    	try {
			String path = uploadFile(msg, isSend, token);
			msg.setFileId(path);
		} catch (FsException e) {
			throw new HsException(ASRespCode.AS_OPT_FAILED);
		}
    	SystemLog.info("管理公司", "发送消息", JSON.toJSONString(msg));
        bsMsgService.createAndSendMessage(msg, isSend);
    }
    @Override
    public void editMsg(Message msg,boolean isSend, String token) throws HsException {
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    	msg.setSendTime(df.format(new Date()));
    	try {
			String path = uploadFile(msg, isSend, token);
			msg.setFileId(path);
		} catch (FsException e) {
			throw new HsException(ASRespCode.AS_OPT_FAILED);
		}
    	SystemLog.info("管理公司", "修改消息", JSON.toJSONString(msg));
    	bsMsgService.modifyAndSendMessage(msg, isSend);
    }

    @Override
    public void deleteMsgSend(String msgId) throws HsException {
    	
    	bsMsgService.removeMessageById(msgId);
    	
    }

    @Override
    public Message query(String msgId) throws HsException {
    	Message msg =  bsMsgService.queryMessageById(msgId);
    	SystemLog.info("管理公司", "单笔查询", "msgId="+msgId+"  result ="+JSON.toJSONString(msg));
    	return msg;
    }

	@Override
	public void deleteMessages(List<String> msgIds) throws HsException {
		SystemLog.info("管理公司", "批量删除消息", JSON.toJSONString(msgIds));
		bsMsgService.batchRemoveMessages(msgIds);
	}
    
	/**
	 * 创建目录
	 * 
	 * @param path
	 */
	private void makeDirectoryIfNotExist(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	
	private String uploadFile(Message msg,boolean isSend,String token) throws FsException{
		if(isSend){
    		String htmlSuffix = ".html";
    		String templateName = "hsmsgcenter-template-m.ftl";
    		String hsimHtmlCacheLocalPath = "./msgcenter_cache/";
    		// 创建目录
    		makeDirectoryIfNotExist(hsimHtmlCacheLocalPath);
    		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
    		String htmlFileName = hsimHtmlCacheLocalPath + msg.getSender() + df.format(new Date())
    				+ htmlSuffix;
    		String hsimHtmlTemplatePath = null;
    		try {
    			hsimHtmlTemplatePath = new ClassPathResource(
    					"/hsmsgcenter-template-m.ftl").getFile().getParent();
    		} catch (IOException e) {
    			//logger.error("读取hsmsgcenter-template.ftl失败", e);
    			throw new HsException(ASRespCode.APS_MSG_TEMP_NOT_FOUND);
    		}
    		String hsimTfsServerUrl = this.revisePathSuffix(
					propertyConfigurer.getProperty("hsi.fs.download.server.url"),
					"/")+"fs/download/";
    		String images = msg.getImages();
    		if(images!=null){
    			msg.setImages(hsimTfsServerUrl+images);
    		}
    		// 根据模板生成html页面
    		boolean success = FreemarkerUtils.analysisTemplate(
    				hsimHtmlTemplatePath, templateName, htmlFileName,
    				msg);
    		msg.setImages(images);
    		// 文件生成成功, 将其上传到tfs
    		if (success) {
    			//logger.debug("根据模板生成html页面成功。");
    			String htmlPageUrl = "";
    			SecurityToken securityToken = new SecurityToken(msg.getSender(),token,"1");
    			String strUuids = FS.getClient().uploadPublicFile(htmlFileName, htmlSuffix, securityToken, true);
    			
    			cleanHtmlCache(htmlFileName);
    			if (!StringUtils.isEmpty(strUuids)) {
    				htmlPageUrl += hsimTfsServerUrl;
    				htmlPageUrl += strUuids;
    				return htmlPageUrl;
    			}
    			
    		}
    		throw new HsException();
    	}
		return "";
	}
	/**
	 * 清理缓存
	 * 
	 * @param filePath
	 */
	private void cleanHtmlCache(final String filePath) {
		
		File file = new File(filePath);
	
		try {
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
		}
		
	}
	
	/**
	 * 加工文件路径结尾
	 * 
	 * @param path
	 * @param suffixChar
	 * @return
	 */
	private String revisePathSuffix(String path, String suffixChar) {
		if (!path.endsWith(suffixChar)) {
			path += suffixChar;
		}
		return path;
	}
}
