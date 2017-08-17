package com.gy.hsi.ds.common.thirds.dsp.common.listener;

import java.util.Locale;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class StartupListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(StartupListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {

    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {

        logger.info("Load StartupListener start...");

        try {

            Locale.setDefault(Locale.SIMPLIFIED_CHINESE);

        } catch (Throwable t) {
            logger.error(t.getMessage(), t);

            System.exit(-1);
        }

        logger.info("Load StartupListener end...");
    }
}
