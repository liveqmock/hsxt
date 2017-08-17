/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.migrate;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 企业信息加载器
 *
 * @Package : com.gy.hsxt.gpf.bm.migrate
 * @ClassName : ComInfoLoader
 * @Description : 企业信息加载器
 * @Author : chenm
 * @Date : 2016/1/19 19:30
 * @Version V3.0.0.0
 */
@Service
public class ComInfoLoader implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(ComInfoLoader.class);

    /**
     * 企业信息
     */
    private Map<String, String> infoMap = new HashMap<>();

    /**
     * 系统文件夹参数名称
     */
    public static final String USER_DIR = "user.dir";

    /**
     * txt 文件后缀
     */
    public static final String TXT_EXTENSION = "txt";

    /**
     * 文件名称
     */
    public static final String INFO_TXT = "info.txt";

    /**
     * 读取文件
     */
    private void init() {
        try {
            //获取文件根路径
            File root = new File(System.getProperty(USER_DIR));
            //获取根路径下所有的properties 文件
            Collection<File> files = FileUtils.listFiles(root, new String[]{TXT_EXTENSION}, true);
            for (File f : files) {
                //筛选出dubbo.properties
                String path = StringUtils.lowerCase(f.getPath());
                if (path.endsWith(INFO_TXT)) {
                    //读出文件内容
                    List<String> lines = FileUtils.readLines(f, Charset.defaultCharset().name());
                    for (String line : lines) {
                        infoMap.put(StringUtils.left(line, 11), line);
                    }
                }
            }
        } catch (IOException e) {
            logger.error("==========加载企业客户号信息异常=============", e);
        }
    }

    /**
     * Invoked by a BeanFactory after it has set all bean properties supplied
     * (and satisfied BeanFactoryAware and ApplicationContextAware).
     * <p>This method allows the bean instance to perform initialization only
     * possible when all bean properties have been set and to throw an
     * exception in the event of misconfiguration.
     *
     * @throws Exception in the event of misconfiguration (such
     *                   as failure to set an essential property) or if initialization fails.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    /**
     * 获取互生号对应的客户号
     *
     * @param resNo 互生号
     * @return string
     */
    public String getCustIdByResNo(String resNo) {
        return infoMap.get(resNo);
    }

}
