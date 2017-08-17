package com.gy.hsi.ds.param.user.creator;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gy.hsi.ds.common.constant.RoleEnum;
import com.gy.hsi.ds.login.dao.IUserDao;

/**
 * 生成测试用户SQL的工具（不会写进数据库里，只是单纯的生成SQL）
 *
 * @author liaoqiqi
 * @version 2014-2-8
 */
public class UserCreateTools {

	private static IUserDao userDao;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext();
		ctx.getEnvironment().setActiveProfiles("production");
		ctx.setConfigLocation("spring-global.xml");
		ctx.refresh();

		userDao = (IUserDao) ctx.getBean("userDaoImpl");

		/**
		 * 生成测试用户 SQL
		 */
		UserCreateCommon.generateCreateTestUserSQL(userDao);

		/**
		 * 生成指定用户 SQL
		 */
		UserCreateCommon.generateCreateSpecifyUserSQL(userDao, "prism",
				"prism_MhxzKhl", RoleEnum.NORMAL, "8");

		System.exit(1);
	}

}
