package com.gy.hsxt.bs.service.testcase;

import java.io.FileNotFoundException;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

public class JUnit4CRunner extends SpringJUnit4ClassRunner {

    static
    {
        try
        {
            Log4jConfigurer.initLogging("classpath:log4j.properties");
        }
        catch (FileNotFoundException ex)
        {
            System.err.println("Cannot Initialize log4j");
        }
    }

    public JUnit4CRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

}
