package com.gy.hsxt.kafka.common.util;




import java.io.UnsupportedEncodingException;

/**
 * 
 * @ClassName: StringUtils 
 * @Description: 字符串工具类 
 * @author Lee 
 * @date 2015-7-3 下午2:39:51
 */
public class StringUtils {
	public final static String EMPTY = "";
    /**
     * 检查字符串是否为<code>null</code>或空字符串<code>""</code>。
     * 
     * <pre>
     * StringUtil.isEmpty(null)      = true
     * StringUtil.isEmpty("")        = true
     * StringUtil.isEmpty(" ")       = false
     * StringUtil.isEmpty("bob")     = false
     * StringUtil.isEmpty("  bob  ") = false
     * </pre>
     * 
     * @param str
     *            要检查的字符串
     * 
     * @return 如果为空, 则返回<code>true</code>
     */
    public static boolean isEmpty(final String str) {
        return str == null || str.length() == 0;
    }


    /**
     * 检查字符串是否是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。
     * 
     * <pre>
     * StringUtil.isBlank(null)      = true
     * StringUtil.isBlank("")        = true
     * StringUtil.isBlank(" ")       = true
     * StringUtil.isBlank("bob")     = false
     * StringUtil.isBlank("  bob  ") = false
     * </pre>
     * 
     * @param str
     *            要检查的字符串
     * 
     * @return 如果为空白, 则返回<code>true</code>
     */
    public static boolean isBlank(final String str) {
        int length;

        if (str == null || (length = str.length()) == 0) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }


	public static String replace(String inString, String oldPattern, String newPattern) {
		if (!hasLength(inString) || !hasLength(oldPattern) || newPattern == null) {
			return inString;
		}
		StringBuilder sb = new StringBuilder();
		int pos = 0; // our position in the old string
		int index = inString.indexOf(oldPattern);
		// the index of an occurrence we've found, or -1
		int patLen = oldPattern.length();
		while (index >= 0) {
			sb.append(inString.substring(pos, index));
			sb.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sb.append(inString.substring(pos));
		// remember to append any characters to the right of a match
		return sb.toString();
	}
	
	/**
	 * Check that the given CharSequence is neither <code>null</code> nor of length 0.
	 * Note: Will return <code>true</code> for a CharSequence that purely consists of whitespace.
	 * <p><pre>
	 * StringUtils.hasLength(null) = false
	 * StringUtils.hasLength("") = false
	 * StringUtils.hasLength(" ") = true
	 * StringUtils.hasLength("Hello") = true
	 * </pre>
	 * @param str the CharSequence to check (may be <code>null</code>)
	 * @return <code>true</code> if the CharSequence is not null and has length
	 */
	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}
	
	/**
	 * Check that the given String is neither <code>null</code> nor of length 0.
	 * Note: Will return <code>true</code> for a String that purely consists of whitespace.
	 * @param str the String to check (may be <code>null</code>)
	 * @return <code>true</code> if the String is not null and has length
	 */
	public static boolean hasLength(String str) {
		return hasLength((CharSequence) str);
	}
	
	public static byte[] getUtf8Bytes(String str){
		if(hasLength(str)){
			try {
				return str.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// ignore
				
			}
		}
		return null;
	}


	public static String getStringFromUtf8Bytes(byte[] tmpArray) {
		if(tmpArray != null && tmpArray.length > 0){
			try {
				return new String(tmpArray,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// ignore
				
			}
		}
		return null;
	}


	public static boolean equals(String str1, String str2) {
		if(str1==null && str2 == null){
			return true;
		}
		if(str1 == null || str2 == null){
			return false;
		}
		return str1.equals(str2);
	}
	
	/**
	 * 
	* @Title: isNumer 
	* @Description: 检查字符串是否是由数字组成，不包含小数点
	* @param @param s  要判定字符串
	* @param @return    是数值类型,或为空 false 不空并且不是数字组成
	* @return boolean    返回类型   
	* @throws
	 */
	public static boolean isNumer(String s) {
		if (isBlank(s)) {
			return false;
		}
		int i = s.trim().length();
		if (i == 0) {
			return false;
		}
		byte abyte0[] = new byte[i];
		abyte0 = s.getBytes();
		for (int j = 0; j < i; j++) {
			if ((abyte0[j] < 48 || abyte0[j] > 57)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	* @Title: nullToEmpty 
	* @Description: 将null值转为空值
	* @param @param inputValue
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String nullToEmpty(String inputValue) {

		if (inputValue == null) {
			return "";
		}
		return inputValue.trim();
	}
	
	
	/**
	 * 
	* @Title: nullToZero 
	* @Description: 将null或者""值转为字符0
	* @param @param inputValue
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String nullToZero(String inputValue) {

		if (inputValue == null || "".equals(inputValue)) {
			return "0";
		}
		return inputValue;
	}
	
	/**
	 * 
	* @Title: isWord 
	* @Description: 是否英文字母 
	* @param @param c
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	public static boolean isWord(char c){
		return  String.valueOf(c).matches("^[a-zA-Z]*");
	}
	
	public static void main(String[] args) {
		String s1 = "55e5f2a3e05a203fa1adfafb32c86e679e1ef6628d4fe38f16dd48c33db8c2f4";
		String s2 = "55e5f2a3e07a203fa1adfafb32c86e679e1ef6628d4fe38f16dd48c33db8c2f4";
	}
}