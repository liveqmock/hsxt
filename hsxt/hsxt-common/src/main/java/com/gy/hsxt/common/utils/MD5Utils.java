/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具
 *
 * @Package com.gy.hsxt.common.utils
 * @ClassName : MD5Utils
 * @Description : MD5加密工具
 * @Author : chenm
 * @Date : 2015/10/21 15:38
 * @Version V3.0.0.0
 */
public abstract class MD5Utils {

    private static Logger logger = LoggerFactory.getLogger(MD5Utils.class);
    /**
     * 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合
     */
    private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static MessageDigest messagedigest = null;

    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            logger.error("============MD5 初始化失败==========", e);
        }
    }

    /**
     * 生成字符串的md5校验值
     *
     * @param s 参数
     * @return md5
     */
    public static String toMD5code(String s) {
        return toMD5code(s.getBytes());
    }

    /**
     * 生成文件的md5校验值
     *
     * @param file 文件
     * @return md5
     * @throws IOException
     */
    public static String toMD5code(File file) throws IOException {
        InputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int numRead = 0;
        while ((numRead = fis.read(buffer)) > 0) {
            messagedigest.update(buffer, 0, numRead);
        }
        fis.close();
        return bufferToHex(messagedigest.digest());
    }

    /**
     * 二进制 MD5 加密
     *
     * @param bytes 二进制
     * @return md5
     */
    public static String toMD5code(byte[] bytes) {
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }

    /**
     * 二进制 转 十六进制
     *
     * @param bytes 二进制
     * @return 十六进制
     */
    private static String bufferToHex(byte bytes[]) {
        StringBuilder buffer = new StringBuilder(2 * bytes.length);
        for (int l = 0; l < bytes.length; l++) {
            // 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
            char c0 = hexDigits[(bytes[l] & 0xf0) >> 4];
            // 取字节中低 4 位的数字转换
            char c1 = hexDigits[bytes[l] & 0xf];
            buffer.append(c0);
            buffer.append(c1);
        }
        return buffer.toString();
    }

}
