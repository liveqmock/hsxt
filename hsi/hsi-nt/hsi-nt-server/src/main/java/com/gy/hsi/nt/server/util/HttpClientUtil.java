package com.gy.hsi.nt.server.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.gy.hsi.nt.api.exception.NoticeException;

/**
 * 
 * @className:HttpClientUtil
 * @author:likui
 * @date:2015年7月28日
 * @desc:发送httpclient的get、post封装
 * @company:gyist
 */
public class HttpClientUtil {
	private static final Logger logger = Logger.getLogger(HttpClientUtil.class);
	
	/**
	 * 编码
	 */
	private static final String CHARSET = "UTF-8";
	
	private static final CloseableHttpClient HTTP_CLIENT;
	
	private HttpClientUtil() {
		super();
	}
	
	static {
		//设置连接时的参数
        RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
        HTTP_CLIENT = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }
	
	/**
	 * 
	*@Description: doGet(处理get请求)
	*@return String   返回类型
	*@param url 请求地址
	*@param params 请求参数
	*@return
	 * @throws NoticeException 
	 */
	public static String doGet(String url, Map<String, String> params) throws NoticeException{
        return doGet(url, params,CHARSET);
    }
	
	/**
	 * 
	*@Description: doPost(处理post请求)
	*@return String   返回类型
	*@param url 请求地址
	*@param params 请求参数
	*@return
	 */
    public static String doPost(String url, Map<String, String> params){
        return doPost(url, params,CHARSET);
    }
	
    /**
     * 
    *@Description: doGet(处理get请求)
    *@return String   返回类型
    *@param url 请求地址
    *@param params 请求参数
    *@param charset 字符编码
    *@return
     * @throws NoticeException 
     */
    public static String doGet(String url, Map<String, String> params,String charset) throws NoticeException{
    	
    	if(StringUtils.isBlank(url)) {
    		throw new NoticeException("http url is null...");
    	}
    	CloseableHttpResponse response = null;
    	try {
    		StringBuilder requestUrl = new StringBuilder(url);
			if(null != params && !params.isEmpty()) {
				List <NameValuePair> nvps = new ArrayList <NameValuePair>();
				for(String key : params.keySet()){
			        String value = params.get(key);
			        if(null != value){
			        	nvps.add(new BasicNameValuePair(key,value));
			        }
			    }
				//拼接请求地址
				requestUrl.append("?").append(EntityUtils.toString(new UrlEncodedFormEntity(nvps, charset)));
			}
			//get请求
			HttpGet httpGet = new HttpGet(requestUrl.toString());
			response = HTTP_CLIENT.execute(httpGet);
			
			int statusCode = response.getStatusLine().getStatusCode();
			if(HttpStatus.SC_OK != statusCode) {
				httpGet.abort();
                throw new NoticeException("execute HttpClientUtil doGet method,error status code :" + statusCode);
			}
			
			String result = handlerEntity(response);
			response.close();
			
			return result;
		}  catch (Exception e) {
        	logger.error(e.getMessage(),e);
        	throw new NoticeException("execute HttpClientUtil doGet method, exception:"+e.getMessage());
        } finally{
			if(null != response) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error(e.getMessage(),e);
				}
			}
		}
    }
    
    /**
     * 
    *@Description: doPost(处理post请求)
    *@return String   返回类型
    *@param url  请求地址
    *@param params 请求参数
    *@param charset 字符编码
    *@return
     */
    public static String doPost(String url, Map<String, String> params,String charset){
    	if(StringUtils.isBlank(url)){
            return null;
        }
    	CloseableHttpResponse response = null;
        try {
            List<NameValuePair> nvps = null;
            if(params != null && !params.isEmpty()){
                nvps = new ArrayList<NameValuePair>(params.size());
                for(String key : params.keySet()){
                    String value = params.get(key);
                    if(null != value){
                        nvps.add(new BasicNameValuePair(key,value));
                    }
                }
            }
            //post请求
            HttpPost httpPost = new HttpPost(url);
            if(null != nvps && !nvps.isEmpty()){
                httpPost.setEntity(new UrlEncodedFormEntity(nvps,charset));
            }
            response = HTTP_CLIENT.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK != statusCode) {
                httpPost.abort();
                throw new NoticeException("execute HttpClientUtil doPost method,error status code :" + statusCode);
            }
            String result = handlerEntity(response);
            response.close();
            
            return result;
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
        } finally{
        	if(null != response) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error(e.getMessage(),e);
				}
			}
        }
        
        return null;
    }
    
    /**
     * 
    *@Description: doPost(post请求body)
    *@param url 请求地址
    *@param jsonParms json字符串
    *@return String   返回类型
     */
    public static String doPost(String url, String jsonParms){
    	 return doPost(url, jsonParms, CHARSET);
    }
    
    /**
     * 
    *@Description: doPost(post请求body)
    *@param url 请求地址
    *@param jsonParms json字符串
    *@param charset
    *@return String   返回类型
     */
    public static String doPost(String url, String jsonParms,String charset){
    	if(StringUtils.isBlank(url)){
            return null;
        }
    	CloseableHttpResponse response = null;
    	try {
    		HttpPost httpPost = new HttpPost(url);
    		httpPost.setEntity(new StringEntity(jsonParms, charset));
			response = HTTP_CLIENT.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK != statusCode) {
                httpPost.abort();
                throw new NoticeException("execute HttpClientUtil doPost method,error status code :" + statusCode);
            }
            String result = handlerEntity(response);
            response.close();
            
            return result;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
        	if(null != response) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error(e.getMessage(),e);
				}
			}
        }
    	return null;
    }

    /**
     * 
    *@Description: handlerEntity(处理response中的实体)
    *@return String   返回类型
    *@param response
    *@return
    *@throws IOException
     */
    private static String handlerEntity(CloseableHttpResponse response)
			throws IOException {
		String result = null;
		HttpEntity entity = response.getEntity();
		if(null != entity) {
			result = EntityUtils.toString(entity, CHARSET);
		}
		EntityUtils.consume(entity);
		return result;
	}
}
