package com.gy.hsxt.uc.index.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gy.hsxt.uc.search.server.SolrServerContext;


/**
 * 搜索引擎索引工程的启动监听类，定义启动需加载的必要方法等
 * 
 * @category 搜索引擎索引工程的启动监听类，定义启动需加载的必要方法等
 * @projectName hsec-searchp-indexing
 * @package com.gy.hsec.searchp.indexing.web.listener.StartupListener.java
 * @className StartupListener
 * @description 搜索引擎索引工程的启动监听类，定义启动需加载的必要方法等
 * @author lixuan
 * @createDate 2015-1-17 下午1:17:13
 * @updateUser lixuan
 * @updateDate 2015-1-17 下午1:17:13
 * @updateRemark 新建
 * @version v0.0.1
 */
public class StartupListener implements ServletContextListener {
	private Log log = LogFactory.getLog(this.getClass());
	static ApplicationContext CTX = null; 
	public StartupListener() {
		
	}

	public void contextInitialized(ServletContextEvent event) {
		log.debug("Initializing context...");
		
		CTX = WebApplicationContextUtils
				.getRequiredWebApplicationContext(event.getServletContext());
			// 连接搜索引擎服务器
		SolrServerContext solrServerContext = (SolrServerContext) CTX
				.getBean("solrServerContext");
		
		// 初始化搜索服务器连接
		solrServerContext.init();
		log.debug("Context has been initialized successfully.");

	}
	
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
}
