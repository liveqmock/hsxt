package com.gy.hsxt.access.web.person.utils;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
		
/***************************************************************************
 * <PRE>
 * Description 		: 国际化资源工具类
 *
 * Project Name   	: hsxt-access-web-person
 *
 * Package Name     : com.gy.hsxt.access.web.person.utils
 *
 * File Name        : ResourceUtils
 * 
 * Author           : LiZhi Peter
 *
 * Create Date      : 2015-8-26 下午3:50:09  
 *
 * Update User      : LiZhi Peter
 *
 * Update Date      : 2015-8-26 下午3:50:09
 *
 * UpdateRemark 	: 说明本次修改内容
 *
 * Version          : v0.0.1
 *
 * </PRE>
 ***************************************************************************/

public class ResourceUtils {
	//国际化属性文件定义
	private static ResourceUtils resourceUtil = null;
	private HashMap<String,ResourceBundle> resourceMap = null;
	private final static String SOURCE_FILE_NAME = "messages";
	
	//重载控制器
	private final static ResourceBundleControl ctl = new ResourceBundleControl();  
	
	/**
	 * 私有构造方法
	 */
	private ResourceUtils()    
    {    
       //分别将两个文件中文与英文国际化默认加载
		resourceMap = new HashMap<String,ResourceBundle>();
		resourceMap.put(Locale.CHINA.getLanguage(), ResourceBundle.getBundle(SOURCE_FILE_NAME, Locale.CHINA));
		resourceMap.put(Locale.ENGLISH.getLanguage(), ResourceBundle.getBundle(SOURCE_FILE_NAME, Locale.ENGLISH));
    }  
	
	/**
	 * 单例模式生成国际化工具类对象返回，
	 * 线程同步
	 * @return 国际化工具类对象
	 */
	public static ResourceUtils getInstance(){
		if(resourceUtil == null){
			synchronized (ResourceUtils.class)
			{
				resourceUtil  = new ResourceUtils();
			}
		}
		return resourceUtil ;
	}
	
	/**
	 * 
	 * @param key
	 * @param local
	 * @return
	 */
	private String _getMessage(String key, Locale local){
		//语言为空则默认获取Default
		if(local == null){
			local = Locale.getDefault(); 
		}
		
		String msg = null;
		
		//根据语言获取国际化信息
		if (resourceMap.containsKey(local.getLanguage()))    
        {    
            msg = resourceMap.get(local.getLanguage()).getString(key);    
        }    
        
		//如果找不到资源文件，返回key     
        if (null == msg || "".equals(msg))    
        {    
            return key;    
        }    
        
        return msg;    
	}
	
	
	/**
	 * 定时重加载属性文件读取
	 * 返回 {res}.properties 中 key 对应的值
	 * @param locale
	 * @param baseName
	 * @param key
	 * @return
	 */
	private  void _getStringForLocale(String fileName,Locale locale, String key) {
		
		ResourceBundle resourceBundle = null;
		
		resourceBundle = ResourceBundle.getBundle(fileName,ctl);    
		resourceMap.put(fileName, resourceBundle);

		
	}

	/**
	 * 返回 {res}.properties 中 key 对应的值，并对值进行参数格式化
	 * @param local 请求语言
	 * @param key
	 * @param args
	 * @return
	 */
	public  String  getMessage(Locale local ,String key, Object...args) {
		String text = this._getMessage(key, local);
		return (text!=null)?MessageFormat.format(text, args):null;
	}
	
	/**
	 * 返回 发布环境默认语言{res}.properties 中 key 对应的值
	 * @param local 请求语言
	 * @param key 格式化参数
	 * @return
	 */
	public  String  getMessage(Locale local ,String key) {
		String text = this._getMessage(key, local);
		return text;
	}
	/**
	 *返回 发布环境默认语言 返回 {res}.properties 中 key 对应的值，并对值进行参数格式化
	 * @param key
	 * @param args
	 * @return
	 */
	public  String getMessage(String key, Object...args) {
		String text = this._getMessage(key,null);
		return (text!=null)?MessageFormat.format(text, args):null;
	}
	
	/**
	 * 返回 {res}.properties 中 key 对应的值，并对值进行参数格式化
	 * @param key
	 * @param args
	 * @return
	 */
	public  String getMessage(String key) {
		String text = this._getMessage(key,null);
		return text;
	}
	
	
	
	/**
	 * 重载控制器，每1个小时重载一次
	 * @author Winter Lau
	 * @date 2010-5-12 下午11:20:02
	 */
	private static class ResourceBundleControl extends ResourceBundle.Control {

		/**
		 * 每10个小时重载一次
		 */
		@Override
		public long getTimeToLive(String baseName, Locale locale) {
			return 36000000;
		}

		@Override
		public boolean needsReload(String baseName, Locale locale,
				String format, ClassLoader loader,
				ResourceBundle bundle, long loadTime) {
			return true;
		}
	}
	
}	