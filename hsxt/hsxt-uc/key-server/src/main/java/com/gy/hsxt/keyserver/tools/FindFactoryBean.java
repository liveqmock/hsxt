package com.gy.hsxt.keyserver.tools;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.gy.hsxt.keyserver.storebase.BaseDao;
import com.gy.hsxt.keyserver.storebase.BaseDaoImpl;
import com.gy.hsxt.keyserver.storebase.doubleStore;
import com.gy.hsxt.keyserver.storebase.hazelStore;
import com.gy.hsxt.keyserver.storebase.mapStore;
import com.gy.hsxt.keyserver.storebase.store;

/***************************************************************************
 * <PRE>
 *  Project Name    : KeyServer
 * 
 *  Package Name    : com.gy.kms.keyserver
 * 
 *  File Name       : FindFactoryBean.java
 * 
 *  Creation Date   : 2014-7-4
 * 
 *  Author          : fandi
 * 
 *  Purpose         : 获取对象，根据配置文件，设置数据源
 * 
 * </PRE>
 ***************************************************************************/
public class FindFactoryBean {

	public static final class InnerStaticClass {
		private static ApplicationContext applicationContext;

		public static ApplicationContext getApplicationContext() {
			return applicationContext;
		}

		public static void setApplicationContext(
				ApplicationContext applicationContext) {
			InnerStaticClass.applicationContext = applicationContext;
		}

	}

	/**
	 * 此方法可以把ApplicationContext对象inject到当前类中作为一个静态成员变量。
	 * 
	 * @param applicationContext
	 *            ApplicationContext 对象.
	 * @throws BeansException
	 */

	public void setApplicationContext(ApplicationContext applicationContext) {
		FindFactoryBean.InnerStaticClass
				.setApplicationContext(applicationContext);
		baseDao = new BaseDaoImpl();
		switch (Config.getSaveDataType()){
			case 1:
				baseDao.setSave(InnerStaticClass.getApplicationContext()
				.getBean("oracleStore",store.class));
				break;
			case 2:
				baseDao.setSave(new mapStore());
				break;
			case 3:
				baseDao.setSave(new doubleStore(new mapStore(),InnerStaticClass.getApplicationContext()
						.getBean("oracleStore",store.class)));
				break;
			case 4:
				baseDao.setSave(new hazelStore());				
				break;
			case 5:
				baseDao.setSave(new doubleStore(new hazelStore(),InnerStaticClass.getApplicationContext()
						.getBean("oracleStore",store.class)));
				break;
			default:
		}
	}
	static BaseDaoImpl baseDao;
	/**
	 * 这是一个便利的方法，帮助我们快速得到一个BEAN
	 * 
	 * @param beanName
	 *            bean的名字
	 * @return 返回一个bean对象
	 */
	public static Object getBean(String beanName) {
		return FindFactoryBean.InnerStaticClass.getApplicationContext()
				.getBean(beanName);
	}

	/**
	 * 这是一个便利的方法，帮助我们快速得到一个BEAN
	 * 
	 * @param beanName
	 *            bean的名字
	 * @return 返回一个bean对象
	 */
	public static <T>  T getBean(String beanName,Class<T> requiredType) {
		return FindFactoryBean.InnerStaticClass.getApplicationContext()
				.getBean(beanName,requiredType);
	}
	public static BaseDao getBaseDao() {
		return FindFactoryBean.baseDao;
	}
}
