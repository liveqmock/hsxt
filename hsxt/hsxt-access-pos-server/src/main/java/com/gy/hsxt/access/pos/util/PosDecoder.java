/**
 * @filename 文件名：Decoder.java
 * @description 描    述：POS二维码加密解密
 * @author 作    者：luobin
 * @date 时    间：2016-4-13
 * @Copyright 版    权：归一科技公司源代码，版权归归一科技公司所有。
 */
//package com.gy.hsec.rescontitution.utils;
package com.gy.hsxt.access.pos.util;

import com.alibaba.druid.util.HexBin;

/**
 * @filename 文件名：Decoder.java
 * @description 描    述：POS二维码加密解密
 * @author 作    者：luobin
 * @date 时    间：2016-4-13
 * @Copyright 版    权：归一科技公司源代码，版权归归一科技公司所有。
 */
public class PosDecoder {

    // 转16进制
    public static String toHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    /**
     * 
     * 取字符长度
     * 
     * @param index
     * @param s
     * @return
     */
    public static String getStrBylimit(int index, String s) {

        return s.substring(0, index);

    }

    /**
     * 
     * 字符逆排序
     * 
     * @param s
     * @return
     */
    public static String reverse(String s) {
        char[] chars = s.toCharArray();
        int i;
        s = "";
        for (i = chars.length - 1; i >= 0; i--)
            s += chars[i];
        return s;
    }

    /**
     * 
     * 1.字符前三个字符重复5次末尾加第一个字符 2.整个字符进行转换16进制
     * 
     * @param s
     * @return
     */
    public static String hex_first3_dup_5_1(String s) {
        String str = "";
        str = s.substring(0, 3);
        String end_1 = s.substring(0, 1);
        String tmp_str = "";
        for (int i = 0; i < 5; i++) {
            tmp_str += str;
        }
        tmp_str = tmp_str + end_1;
        tmp_str = toHexString(tmp_str);
        return tmp_str;

    }

    /**
     * 
     * 字符串第三位开始到最后的字符进行16进制转换
     * 
     * @param s
     * @return
     */
    public static String hex_last3(String s) {
        String str = "";
        str = s.substring(3, s.length());
        str = toHexString(str);
        return str;

    }

    /**
     * 
     * 字符串异或结果
     * 
     * @param str1
     * @param str2
     * @return
     */

    private static String xor(String str1, String str2) {
        StringBuffer sb = new StringBuffer();
        int len1 = str1.length(), len2 = str2.length();
        int i = 0, index = 0;
        if (len2 > len1) {
            index = len2 - len1;
            while (i++ < len2 - len1) {
                sb.append(str2.charAt(i - 1));
                str1 = "0" + str1;
            }
        } else if (len1 > len2) {
            index = len1 - len2;
            while (i++ < len1 - len2) {
                sb.append(str1.charAt(i - 1));
                str2 = "0" + str2;
            }
        }
        int len = str1.length();
        while (index < len) {
            int j = (Integer.parseInt(str1.charAt(index) + "", 16) ^ Integer
                    .parseInt(str2.charAt(index) + "", 16)) & 0xf;
            sb.append(Integer.toHexString(j));
            index++;
        }
        return sb.toString();
    }
    
    /**
     * 
     * @method 方法名：getSecretKey
     * @features 功    能：获取秘钥
     * @params 参    数：@param str 扫码的字符串
     * @params 参    数：@return 秘钥
     * @return 返回值：String
     * @modify 修改者: luobin
     
     */
    public static String getSecretKey(String str){
        //字符串截取前19位
        String first_19 = getStrBylimit(19, str);
        //对截取的19位字符串逆序排列
        String revese_first_19 = reverse(first_19);
        
        //1.字符前三个字符重复5次末尾加第一个字符 
        //2.整个字符进行转换16进制
        String str_16 = hex_first3_dup_5_1(str);
        //第四个到最后组成16位长的新字符串 并转换16进制
        String str_last_3 = hex_last3(first_19);
        //两个字符串str_16, str_last_3按位做异或运算生成字符串1
        String str_xor = xor(str_16, str_last_3);
        
        //1.字符前三个字符重复5次末尾加第一个字符 
        //2.整个字符进行转换16进制
        String revese_str_16 = hex_first3_dup_5_1(revese_first_19);
        //第四个到最后组成16位长的新字符串 并转换16进制
        String revese_str_last_3 = hex_last3(revese_first_19);
        //两个字符串revese_str_16, revese_str_last_3按位做异或运算生成字符串2
        String revse_str_xor = xor(revese_str_16, revese_str_last_3);

        //字符串1 str_xor和字符串2 revse_str_xor转为字节数组后按位对应做异或运算
        String secrt = xor(str_xor, revse_str_xor);

        return secrt;
    }
    
    public static byte[] getHexSecretKey(String str) {
    	return HexBin.decode(str);    	
    }
    
    public static void main(String[] args) {
        String str = "abcdefghijklmnopqrs";
        System.out.println(PosDecoder.getSecretKey(str));
    }
}
