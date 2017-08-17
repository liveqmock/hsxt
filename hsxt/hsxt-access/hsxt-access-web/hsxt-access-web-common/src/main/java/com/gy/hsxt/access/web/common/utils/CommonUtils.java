package com.gy.hsxt.access.web.common.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.gy.hsxt.common.utils.DoubleUtil;
import com.gy.hsxt.common.utils.NumbericUtils;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 
 * @projectName   : hsxt-access-web-common
 * @package       : com.gy.hsxt.access.web.common.utils
 * @className     : NumberUtils.java
 * @description   : 常用工具类
 * @author        : maocy
 * @createDate    : 2015-11-04
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
public class CommonUtils {
	
	/**
	 * 
	 * 方法名称：转Byte
	 * 方法描述：转Byte，如果参数为空则返回null
	 * @param obj 目标对象
	 * @return
	 */
	public static Byte toByte(Object obj){
		if(StringUtils.isBlank(obj)){
			return null;
		}
		try {
			return Byte.parseByte(obj.toString());
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	/**
	 * 
	 * 方法名称：转Short
	 * 方法描述：转Short，如果参数为空则返回null
	 * @param obj 目标对象
	 * @return
	 */
	public static Short toShort(Object obj){
		if(StringUtils.isBlank(obj)){
			return null;
		}
		try {
			return Short.parseShort(obj.toString());
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	/**
	 * 
	 * 方法名称：转Integer
	 * 方法描述：转Integer，如果参数为空则返回null
	 * @param obj 目标对象
	 * @return
	 */
	public static Integer toInteger(Object obj){
		if(StringUtils.isBlank(obj)){
			return null;
		}
		try {
			return Integer.parseInt(obj.toString());
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	/**
	 * 
	 * 方法名称：转Float
	 * 方法描述：转Float，如果参数为空则返回null
	 * @param obj 目标对象
	 * @return
	 */
	public static Float toFloat(Object obj){
		if(StringUtils.isBlank(obj)){
			return null;
		}
		try {
			return Float.parseFloat(obj.toString());
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	/**
	 * 
	 * 方法名称：转Double
	 * 方法描述：转Double，如果参数为空则返回null
	 * @param obj 目标对象
	 * @return
	 */
	public static Double toDouble(Object obj){
		if(StringUtils.isBlank(obj)){
			return null;
		}
		try {
			return Double.parseDouble(obj.toString());
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	/**
	 * 
	 * 方法名称：转Long
	 * 方法描述：转Long，如果参数为空则返回null
	 * @param obj 目标对象
	 * @return
	 */
	public static Long toLong(Object obj){
		if(StringUtils.isBlank(obj)){
			return null;
		}
		try {
			return Long.parseLong(obj.toString());
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	/**
	 * 
	 * 方法名称：转List
	 * 方法描述：转List
	 * @param str 目标对象
	 * @param regex 分割符
	 * @return
	 */
	public static List<String> toStringList(String str, String regex){
		if(StringUtils.isBlank(str) || StringUtils.isBlank(regex)){
			return new ArrayList<String>();
		}
		String[] array = str.split(regex);
		List<String> list = new ArrayList<>();
		for (String string : array) {
			list.add(string);
		}
		return list;
	}
	
	/**
	 * 字符串加掩码
	 * @param str 需要修改的字符串
	 * @param startRetain 开始保留位数
	 * @param lastRetain 后保留位数
	 * @param replaceStr 掩码字符串
	 * @return
	 */
	public static String strMask(String str,int startRetain, int lastRetain,String replaceStr){
        if (!StringUtils.isEmpty(str))
        {
            int strLen=str.length();
            if((startRetain+lastRetain)>=strLen){
            	return str; //字符串长度不够，不做掩码,为了适应，营业执照号码无长度限制
            }
            //截取字符串位数
            int interceptLen=strLen-lastRetain;
            
            //掩码个数
            StringBuffer replaceSB=new StringBuffer();
            for (int i = 0; i < interceptLen-startRetain; i++)
                replaceSB.append(replaceStr);
            
            //替换掩码
            StringBuffer sb = new StringBuffer(str);
            sb.delete(startRetain, interceptLen);
            sb.insert(startRetain, replaceSB);
            
            return sb.toString();
        }
        return str;
    }
	
	/**
	 * 证件号码掩码
	 * @param CerType 证件类型: 1身份证，2护照，3营业执照
	 * @param cerNO 证件号码
	 * @return 证件号码掩码
	 */
	public static String  getHiddenCerNo(String CerType,String cerNO){
		if(cerNO==null){
			return null;
		}
		if("1".equals(CerType)){	    
			
			int iLen=cerNO.length();
			if(iLen>=18){
				//大陆身份证证件号码的年月日用*替换
				cerNO = CommonUtils.strMask(cerNO, 6, 4, "*");
			}else{
				//港澳台：首位与最后2位之间的字符用*号替换
				cerNO = CommonUtils.strMask(cerNO, 1, 2, "*");
			}
		}else if("2".equals(CerType)){
		    //护照：432**22
			//前三位与最后2位之间用*号替换
			cerNO = CommonUtils.strMask(cerNO, 3, 2, "*");
		}else if("3".equals(CerType)){
		    //营业执照 : 123456******123
			//首位与最后2位之间的字符用*号替换
			cerNO = CommonUtils.strMask(cerNO, 1, 2, "*");
		}
		return cerNO;
	}
	
	/**
	 * 金额格式化
	 * @param amount
	 * @return
	 */
    public static String moneyFormat(Object amount) {
        //非空验证
        if(StringUtils.isBlank(amount)){
            return "0.00";
        }
        
        //格式化
        NumberFormat nf = new DecimalFormat("#,###.00");
        return nf.format(DoubleUtil.parseDouble(amount));
    }
}
