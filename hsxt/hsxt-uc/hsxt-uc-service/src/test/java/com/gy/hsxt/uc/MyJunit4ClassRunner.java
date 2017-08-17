/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc;

import java.io.FileNotFoundException;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

/**
 * junit初始化 log4j配置文件
 * @Package: com.gy.hsxt.uc
 * @ClassName: JUnit4ClassRunner
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2015-12-1 下午7:31:32
 * @version V1.0
 */
public class MyJunit4ClassRunner extends SpringJUnit4ClassRunner {
    static

    {
        // conf
        String path = "file:${user.dir}/conf/hsxt-uc/log4j.properties";
        try
        {
            System.out.println("Log4jConfigurer.initLogging(path) begin=" + path);
            Log4jConfigurer.initLogging(path);
            System.out.println("Log4jConfigurer.initLogging(path) end  =" + path);
        }
        catch (FileNotFoundException ex)
        {
            System.err.println("Cannot Initialize log4j path=" + path);
        }
    }

    public MyJunit4ClassRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }
}
