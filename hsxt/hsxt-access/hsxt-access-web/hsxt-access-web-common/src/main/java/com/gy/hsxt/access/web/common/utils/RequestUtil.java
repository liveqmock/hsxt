/**
 * 
 */
package com.gy.hsxt.access.web.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.common.constant.Channel;
import com.gy.hsxt.common.constant.IRespCode;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.Validator;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.DoubleUtil;
import com.gy.hsxt.common.utils.IdcardUtils;
import com.gy.hsxt.common.utils.NumbericUtils;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 请求工具类 Simple to Introduction
 * 
 * @category Simple to Introduction
 * @projectName hsxt-access-web-person
 * @package com.gy.hsxt.access.web.person.utils.RequestUtil.java
 * @className RequestUtil
 * @description 一句话描述该类的功能
 * @author LiZhi Peter
 * @createDate 2015-8-13 下午12:03:47
 * @updateUser LiZhi Peter
 * @updateDate 2015-8-13 下午12:03:47
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
public class RequestUtil {

    private static final Logger logger = LoggerFactory.getLogger(RequestUtil.class);

    public static final String LAST_PAGE = "com.alibaba.lastPage";

    public static final String REDIRECT_HOME = "/";

    /**
     * 获取当前Request对象.
     * 
     * @return 当前Request对象或{@code null}.
     * @throws IllegalStateException
     *             当前线程不是web请求抛出此异常.
     */
    public static HttpServletRequest currentRequest() throws IllegalStateException {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null)
        {
            throw new IllegalStateException("当前线程中不存在 Request 上下文");
        }
        return attrs.getRequest();
    }

    /**
     * 获取请求路径地址
     * 
     * @return 当前求求的Url对峙
     */
    public static String currentRequestUrl() {
        String currentRequestUtl = null;
        currentRequestUtl = currentRequest().getRequestURI();
        return currentRequestUtl;
    }

    /**
     * 加密请求页面
     * 
     * @param request
     * @return
     */
    public static String hashRequestPage(HttpServletRequest request) {
        String reqUri = request.getRequestURI();
        String query = request.getQueryString();

        if (query != null)
        {
            reqUri += "?" + query;
        }

        String targetPage = null;

        // 执行加密方法
        targetPage = "";
        // base64.encodeAsString(reqUri.getBytes("UTF-8"));
        return targetPage;
    }

    /**
     * 解密请求的页面
     * 
     * @param targetPage
     * @return
     */
    public static String retrieve(String targetPage) {
        // 执行加密请求路径
        byte[] decode = null;
        // 非空验证
        if (StringUtils.isBlank(targetPage))
        {
            return null;
        }
        try
        {
            decode = targetPage.getBytes();
            String requestUri = new String(decode, "UTF-8");
            int i = requestUri.indexOf("/", 1);
            return requestUri.substring(i);

        }
        catch (UnsupportedEncodingException ex)
        {
            // this does not happen
            return null;
        }
    }

    /**
     * 请求的多个参数非空验证，错误后返回map界面接受对象
     * 
     * @param locale
     *            请求的当前语言
     * @param strings
     *            请求多个String[]={"请求参数","错误后国际化语言提示信息"}
     * @return
     */
    public static HttpRespEnvelope checkParamIsNotEmpty(Object[]... strings) {
        HttpRespEnvelope hre = null;

        // 遍历所有的验证参数
        for (Object[] string : strings)
        {

            // 参数非法
            if (string == null || string.length < 2)
            {
                hre = new HttpRespEnvelope(RespCode.AS_PARAM_INVALID);

                return hre;
            }
            else
            {
                // String
                if (string[0] instanceof String)
                {

                    // 非空验证
                    if (StringUtils.isBlank(string[0]))
                    {
                        hre = new HttpRespEnvelope(RespCode.AS_PARAM_INVALID.getCode(), string[1].toString());
                        return hre;
                    }

                }
                else if (NumbericUtils.isPlus(String.valueOf(string[0])))
                {
                    if (DoubleUtil.parseDouble(string[0]) == 0.0D)
                    {
                        hre = new HttpRespEnvelope(RespCode.AS_PARAM_INVALID.getCode(), string[1].toString());
                        return hre;
                    }
                }
                else
                {
                    hre = new HttpRespEnvelope(RespCode.AS_PARAM_INVALID.getCode(), string[1].toString());
                    return hre;
                }
            }

        }
        return hre;
    }

    /**
     * 
     * 方法名称：多个参数非空验证 方法描述：请求多个Object[]={"请求参数", 错误代码, "错误描述"}或者Object[]={"请求参数",
     * 异常枚举}
     * 
     * @param strings
     */
    public static void verifyParamsIsNotEmpty(Object[]... strings) {
        // 遍历所有的验证参数
        for (Object[] array : strings)
        {
            // 参数非法
            if (array == null || (array.length != 2 && array.length != 3))
            {
                throw new HsException(RespCode.AS_PARAM_INVALID);
            }
            else
            {
                if (array[0] == null)
                {
                    throw getVerifyException(array);
                }
                else if (array[0] instanceof String)
                {// String
                    if (StringUtils.isBlank(array[0]))
                    {
                        throw getVerifyException(array);
                    }
                }
                else if (NumbericUtils.isPlus(String.valueOf(array[0])))
                {// Double
                    // if (DoubleUtil.parseDouble(array[0]) == 0.0D) {
                    // throw getVerifyException(array);
                    // }
                }
                else if (StringUtils.isBlank(array[0]))
                {
                    throw getVerifyException(array);
                }
            }
        }
    }

    /**
     * 方法名称：获取多个参数非空验证异常 方法描述：请求多个Object[]={"请求参数", 错误代码,
     * "错误描述"}或者Object[]={"请求参数", 异常枚举}
     * 
     * @param array
     * @return
     */
    public static HsException getVerifyException(Object[] array) {
        if (array.length == 2)
        {
            return new HsException((IRespCode) array[1]);
        }
        else
        {
            return new HsException((int) array[1], (String) array[2]);
        }
    }

    /**
     * 
     * 方法名称：多个参数长度验证 方法描述：请求多个Object[]={"请求参数", 最小长度，最大长度，错误代码,
     * "错误描述"}或者Object[]={"请求参数", 最小长度，最大长度，异常枚举}
     * 
     * @param strings
     */
    public static void verifyParamsLength(Object[]... strings) {
        // 遍历所有的验证参数
        for (Object[] array : strings)
        {
            // 参数非法
            if (array == null || (array.length != 4 && array.length != 5))
            {
                throw new HsException(RespCode.AS_PARAM_INVALID);
            }
            else
            {
                if (array.length == 4)
                {
                    String str = ((String) array[0] == null) ? "" : (String) array[0];// 请求参数
                    int minLength = (int) array[1];// 最小长度
                    int maxLength = (int) array[2];// 最大长度
                    if (str.length() < minLength || str.length() > maxLength)
                    {
                        throw new HsException((IRespCode) array[3]);
                    }
                }
                else
                {
                    String str = ((String) array[0] == null) ? "" : (String) array[0];// 请求参数
                    int minLength = (int) array[1];// 最小长度
                    int maxLength = (int) array[2];// 最大长度
                    if (str.length() < minLength || str.length() > maxLength)
                    {
                        throw new HsException((int) array[3], (String) array[4]);
                    }
                }
            }
        }
    }

    /**
     * 
     * 方法名称：日期校验 方法描述：检查开始日期、结束日期不为空而且结束时间不能早于开始时间，要求传入字符格式为yyyy-MM-dd
     * 
     * @param staTime
     *            查询开始时间
     * @param endTime
     *            查询结束时间
     */
    public static void verifyQueryDate(String staTime, String endTime) throws HsException {
        Date sta = DateUtil.StringToDate(staTime);
        Date end = DateUtil.StringToDate(endTime);
        if (sta == null)
        {
            throw new HsException(RespCode.AS_QUERY_STA_TIME_INVALID);
        }
        if (end == null)
        {
            throw new HsException(RespCode.AS_QUERY_END_TIME_INVALID);
        }
        if (sta.after(end))
        {
            throw new HsException(RespCode.AS_QUERY_STA_END_INVALID);
        }
    }

    /**
     * 
     * 方法名称：日期校验 方法描述：检查开始日期、结束日期不为空而且结束时间不能早于开始时间，自定义时间格式
     * 
     * @param staTime
     *            查询开始时间
     * @param endTime
     *            查询结束时间
     * @param pattern
     *            日期格式
     */
    public static void verifyQueryDate(String staTime, String endTime, String pattern) {
        Date sta = DateUtil.StringToDate(staTime, pattern);
        Date end = DateUtil.StringToDate(staTime, pattern);
        if (sta == null)
        {
            throw new HsException(RespCode.AS_QUERY_STA_TIME_INVALID);
        }
        if (end == null)
        {
            throw new HsException(RespCode.AS_QUERY_END_TIME_INVALID);
        }
        if (sta.after(end))
        {
            throw new HsException(RespCode.AS_QUERY_STA_END_INVALID);
        }
    }

    /**
     * 验证参数是否相等
     * 
     * @param str1
     * @param str2
     * @param respCode
     * @throws HsException
     */
    public static void verifyEquals(String str1, String str2, IRespCode respCode) throws HsException {
        // 参数非空验证
        if (StringUtils.isBlank(str1) || StringUtils.isBlank(str2) || respCode == null)
        {
            throw new HsException(RespCode.AS_PARAM_INVALID);
        }

        // 参数是否相等校验
        if (str1.equals(str2) == false)
        {
            throw new HsException(respCode.getCode(), respCode.getDesc());
        }

    }

    /**
     * 验证参数是否试着正整数
     * 
     * @param str1
     * @param respCode
     *            错误编码
     * @throws HsException
     */
    public static void verifyPositiveInteger(String str1, IRespCode respCode) throws HsException {
        // 参数非空验证
        if (StringUtils.isBlank(str1) || respCode == null)
        {
            throw new HsException(RespCode.AS_PARAM_INVALID);
        }

        // 参数是否是整数
        if (NumbericUtils.isNumber(str1) == false)
        {
            throw new HsException(respCode.getCode(), respCode.getDesc());
        }

    }

    /**
     * 验证邮箱格式
     * 
     * @param str1
     * @param respCode
     * @throws HsException
     */
    public static void verifyEamil(String str1, IRespCode respCode) throws HsException {
        // 参数非空验证
        if (StringUtils.isBlank(str1) || respCode == null)
        {
            throw new HsException(RespCode.AS_PARAM_INVALID.getCode(), RespCode.AS_PARAM_INVALID.getDesc());
        }

        // 参数是否相等校验
        if (Validator.isEmail(str1) == false)
        {
            throw new HsException(respCode.getCode(), respCode.getDesc());
        }

    }

    /**
     * 身份证规则验证
     * 
     * @param str1
     * @param respCode
     *            错误编码
     * @throws HsException
     */
    public static void verifyCard(String str1, IRespCode respCode) throws HsException {
        // 参数非空验证
        if (StringUtils.isBlank(str1) || respCode == null)
        {
            throw new HsException(RespCode.AS_PARAM_INVALID);
        }

        // 参数身份证号码规则是否正确
        if (IdcardUtils.validateCard(str1) == false)
        {
            throw new HsException(respCode.getCode(), respCode.getDesc());
        }

    }

    /**
     * 验证银行卡号是否和
     * 
     * @param bankAccNo
     *            银行卡号
     * @param respCode
     *            错误码对象
     * @throws HsException
     */
    public static void verifyBankNo(String bankAccNo, IRespCode respCode) throws HsException {
        // 参数非空验证
        if (StringUtils.isBlank(bankAccNo) || respCode == null)
        {
            throw new HsException(RespCode.AS_PARAM_INVALID);
        }

        // 验证银行卡号是否合法
        if (StringUtils.checkBankCard(bankAccNo) == false)
        {
            throw new HsException(respCode);
        }

    }

    /**
     * 获取用户ip方法
     * 
     * @param request
     * @return
     */
    public static  String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;

    }
    
    /**
     * 呼叫中心密码加密如果用base64加密后会吧+转译 
     * 只针对呼叫中心的方法
     * @param request
     * @param encrypted
     * @return
     */
    public static String encodeBase64String(HttpServletRequest request,String encrypted)
    {
        String ivrFlag = request.getParameter("ivrFlag");
        String returnValue = encrypted;
        
        //呼叫中心判断才会有值
        if(RequestUtil.isIVR(request))
        {
            if(StringUtils.isNotBlank(encrypted))
            {
                returnValue = Base64.encodeBase64String(hexStringToBytes(encrypted));//此处使用BAES64做转码功能，同时能起到2次加密的作用。
            }
        }
        return returnValue;
    }
    
    /**
     * 是否是呼叫中心处理方法
     * @param request
     * @param encrypted
     * @return
     */
    public static boolean isIVR(HttpServletRequest request){
    	 String ivrFlag = request.getParameter("ivrFlag");
    	//呼叫中心判断才会有值
         if(StringUtils.isNotBlank(ivrFlag) && ivrFlag.equals("true"))
         {
        	 return true;
         }else{
        	 return false;
         }
    }
    public static byte[] hexStringToBytes(String hexString) {   
        if (hexString == null || hexString.equals("")) {   
            return null;   
        }   
        hexString = hexString.toUpperCase();   
        int length = hexString.length() / 2;   
        char[] hexChars = hexString.toCharArray();   
        byte[] d = new byte[length];   
        for (int i = 0; i < length; i++) {   
            int pos = i * 2;   
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));   
        }   
        return d;   
    }  
    /**  
     * Convert char to byte  
     * @param c char  
     * @return byte  
     */  
     private static byte charToByte(char c) {   
        return (byte) "0123456789ABCDEF".indexOf(c);   
    }  

}
