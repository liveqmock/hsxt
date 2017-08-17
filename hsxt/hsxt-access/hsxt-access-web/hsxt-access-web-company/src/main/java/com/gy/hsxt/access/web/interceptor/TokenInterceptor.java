package com.gy.hsxt.access.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
		
/**   
 * 针对token信息的拦截器
 * @category          Simple to Introduction
 * @projectName   hsxt-access-web-person
 * @package           com.gy.hsxt.access.web.person.utils.CommonInterceptor.java
 * @className       CommonInterceptor
 * @description   	验证token信息是否合法，去除登录界面   
 * @author              LiZhi Peter
 * @createDate       2015-8-13 上午11:54:08  
 * @updateUser      LiZhi Peter
 * @updateDate      2015-8-13 上午11:54:08
 * @updateRemark 说明本次修改内容
 * @version              v0.0.1
 */
public class TokenInterceptor extends HandlerInterceptorAdapter{
	 /**  
     * 在业务处理器处理请求之前被调用  
     * 如果返回false  
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链 
     * 如果返回true  
     *    执行下一个拦截器,直到所有的拦截器都执行完毕  
     *    再执行被拦截的Controller  
     *    然后进入拦截器链,  
     *    从最后一个拦截器往回执行所有的postHandle()  
     *    接着再从最后一个拦截器往回执行所有的afterCompletion()  
	 * @throws Exception 
     */    
    @Override    
    public boolean preHandle(HttpServletRequest request,    
            HttpServletResponse response, Object handler) throws Exception  {    
    	//记录请求Url信息
//    	log.info(RequestUtil.currentRequestUrl());
    	
    	
    	//获取请求参数
        String requestUri = request.getRequestURI();  
        String contextPath = request.getContextPath();  
        String url = requestUri.substring(contextPath.length());
        String token = request.getParameter("token");
    	String callback = request.getParameter("callback"); 
//    	BizLog.biz("lz", "lz", "111", "222");
        System.out.println("requestUri:_________________________________________________:"+requestUri);
        System.out.println("contextPath:_________________________________________________:"+requestUri);
        System.out.println("url:_________________________________________________:"+requestUri);
        
        //验证token是否存在
        return true;
        
    }
}

	