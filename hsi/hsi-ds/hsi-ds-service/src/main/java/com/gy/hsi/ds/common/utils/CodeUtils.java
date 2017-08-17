package com.gy.hsi.ds.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by knightliao on 15/1/7.
 */
public class CodeUtils {

    protected static final Logger LOG = LoggerFactory.getLogger(CodeUtils.class);

    /**
     * utf-8 转换成 unicode
     *
     * @param inStr
     *
     * @return
     *
     * @author fanhui
     * 2007-3-15
     */
    public static String utf8ToUnicode(String inStr) {

        return CodeUtils2.unicodeToUtf8(inStr);

    }

    /**
     * unicode 转换成 utf-8
     *
     * @param theString
     *
     * @return
     *
     * @author fanhui
     * 2007-3-15
     */
    public static String unicodeToUtf8(String theString) {
        return CodeUtils2.unicodeToUtf8(theString);
    }
}
