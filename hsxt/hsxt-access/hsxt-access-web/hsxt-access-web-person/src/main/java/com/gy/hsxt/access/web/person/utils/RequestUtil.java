/**
 * 
 */
package com.gy.hsxt.access.web.person.utils;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.common.utils.DoubleUtil;
import com.gy.hsxt.common.utils.NumbericUtils;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 请求工具类
 * Simple to Introduction
 * @category          Simple to Introduction
 * @projectName   hsxt-access-web-person
 * @package           com.gy.hsxt.access.web.person.utils.RequestUtil.java
 * @className       RequestUtil
 * @description      一句话描述该类的功能 
 * @author              LiZhi Peter
 * @createDate       2015-8-13 下午12:03:47  
 * @updateUser      LiZhi Peter
 * @updateDate      2015-8-13 下午12:03:47
 * @updateRemark 说明本次修改内容
 * @version              v0.0.1
 */
public class RequestUtil {
	private static final Logger logger = LoggerFactory.getLogger(RequestUtil.class);
	
	public static final String LAST_PAGE = "com.alibaba.lastPage";
    public static final String REDIRECT_HOME = "/";
    
    
    /**
     * 获取当前Request对象.
     * @return 当前Request对象或{@code null}.
     * @throws IllegalStateException 当前线程不是web请求抛出此异常.
     */
    public static HttpServletRequest currentRequest() throws IllegalStateException {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            throw new IllegalStateException("当前线程中不存在 Request 上下文");
        }
        return attrs.getRequest();
    }
    
   
    /**
     * 获取请求路径地址
     * @return 当前求求的Url对峙
     */
    public static String currentRequestUrl(){
    	String currentRequestUtl = null;
    	currentRequestUtl = currentRequest().getRequestURI();
    	return currentRequestUtl;
    }
    
   
    
    /**
     * 加密请求页面
     * @param request
     * @return
     */
    public static  String hashRequestPage(HttpServletRequest request) {
        String reqUri = request.getRequestURI();
        String query = request.getQueryString();
        
        if (query != null) {
            reqUri += "?" + query;
        }
        
        String targetPage = null;
        
        //执行加密方法
        targetPage = "";
//            base64.encodeAsString(reqUri.getBytes("UTF-8"));
        return targetPage;
    }
    
   

    /**
     * 解密请求的页面
     * @param targetPage
     * @return
     */
    public static String retrieve(String targetPage) {
    	//执行加密请求路径
        byte[] decode = null;
        //非空验证
        if(StringUtils.isBlank(targetPage))
        {
        	return null;
        }
        try 
        {
        	decode = targetPage.getBytes();
            String requestUri = new String(decode, "UTF-8");
            int i = requestUri.indexOf("/", 1);
            return requestUri.substring(i);
            
        } catch (UnsupportedEncodingException ex) {
            //this does not happen
            return null;
        }
    }
    
    /**
     * 请求的多个参数非空验证，错误后返回map界面接受对象
     * @param locale	请求的当前语言
     * @param strings	请求多个String[]={"请求参数","错误后国际化语言提示信息"}
     * @return
     */
    public static HttpRespEnvelope checkParamIsNotEmpty(Locale locale,Object[] ...strings ){
    	HttpRespEnvelope hre = null;
    	
    	//遍历所有的验证参数
    	for (Object[] string : strings) {  
    		
    		//参数非法
    		if(string == null || string.length<2)
    		{
//    			hre = new HttpRespEnvelope(RespCode.AS_OPT_FAILED.getCode(),RespCode.AS_OPT_FAILED.getCode()
//    					,ResourceUtils.getInstance().getMessage(locale,"common.parameterError"));
    			
				return hre;
    		}else{
    			//String 
    			if(string[0] instanceof String){
    				
    				//非空验证
    				if(StringUtils.isBlank(string[0]))
    				{
//    					hre = new HttpRespEnvelope(RespCode.AS_OPT_FAILED.getCode(),RespCode.AS_OPT_FAILED.getCode()
//    	       					,ResourceUtils.getInstance().getMessage(locale,string[1].toString()));
    					return hre;
    				}
    				
    			}else if(NumbericUtils.isPlus(String.valueOf(string[0]))){
    				if(DoubleUtil.parseDouble(string[0])== 0.0D){
//    					hre = new HttpRespEnvelope(RespCode.AS_OPT_FAILED.getCode(),RespCode.AS_OPT_FAILED.getCode()
//    	       					,ResourceUtils.getInstance().getMessage(locale,string[1].toString()));
    					return hre;
    				}
    			}else{
//    				hre = new HttpRespEnvelope(RespCode.AS_OPT_FAILED.getCode(),RespCode.AS_OPT_FAILED.getCode()
//        					,ResourceUtils.getInstance().getMessage(locale,"common.parameterError"));
    				return hre;
    			}
    		} 
    		
    		
		}
    	return hre;
    }
    
    
}
