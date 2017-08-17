package com.gy.hsi.ds.client.param.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.client.param.config.JedisConfig;
import com.gy.hsi.ds.param.HsPropertiesConfigurer;
/**
 * 演示分布式配置文件、分布式配置的更新Demo
 *
 * @author liaoqiqi
 * @version 2014-6-17
 */
@Service("disconfDemoTask")
public class DisconfDemoTask {
   
    @Autowired
    private JedisConfig jedisConfig;
    
    public void runInThread() {
    	new Thread() {
    		@Override
    		public void run() {
    			actionRun();
    		}
    	}.start();
    }
    
    private int actionRun() {

        try {
            while (true) {
                Thread.sleep(5000);
                
                System.out.println("redis.host: "+HsPropertiesConfigurer.getProperty("redis.host"));
                
                // System.out.println("jedisConfig2.getHost() = " +jedisConfig.getHost());
            }

        } catch (Exception e) {

            System.out.println(e.toString());
        }

        return 0;
    }
}
