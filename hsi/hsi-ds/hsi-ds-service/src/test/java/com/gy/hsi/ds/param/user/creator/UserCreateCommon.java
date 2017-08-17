package com.gy.hsi.ds.param.user.creator;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.github.knightliao.apollo.utils.common.RandomUtil;
import com.gy.hsi.ds.common.constant.RoleEnum;
import com.gy.hsi.ds.common.thirds.ub.common.log.AopLogFactory;
import com.gy.hsi.ds.common.utils.SignUtils;
import com.gy.hsi.ds.login.beans.bo.User;
import com.gy.hsi.ds.login.dao.IUserDao;

/**
 * @author knightliao
 */
public class UserCreateCommon {

	protected final static Logger LOG = AopLogFactory
			.getLogger(UserCreateCommon.class);

	/**
	 * @param userName
	 * @param password
	 */
	public static void generateCreateSpecifyUserSQL(IUserDao userDao,
			String userName, String password, RoleEnum roleEnum,
			String ownAppIds) {

		User user = new User();

		user.setName(userName);

		user.setPassword(SignUtils.createPassword(password));
		// token
		user.setToken(SignUtils.createToken(userName));

		// set appids
		user.setOwnApps(ownAppIds);

		// role
		user.setRoleId(roleEnum.getValue());

		System.out.println("/* " + userName + "\t" + password + "*/");
		// userDao.create(user);

		List<User> userList = new ArrayList<User>();
		
		userList.add(user);

		printUserList(userList);
	}

	private static String getUserName(Long i) {
		return "testUser" + i;
	}

	/**
	 * 生成内测用户
	 * 
	 * @param userDao
	 */
	public static void generateCreateTestUserSQL(IUserDao userDao) {

		System.out.println("\n");

		int num = 5;

		List<User> userList = new ArrayList<User>();
		
		for (Long i = 1L; i < num + 1; ++i) {

			User user = new User();

			user.setId(i);

			user.setName(getUserName(i));

			user.setOwnApps("2");

			user.setRoleId(RoleEnum.NORMAL.getValue());

			int random = RandomUtil.random(0, 10000);
			
			String password = "MhxzKhl" + String.valueOf(random);
			user.setPassword(SignUtils.createPassword(password));
			
			// token
			user.setToken(SignUtils.createToken(user.getName()));

			System.out.println("/* userid" + user.getId() + "\t" + password
					+ "*/");
			
			// userDao.create(user);
			userList.add(user);
		}

		printUserList(userList);
	}

	/**
	 * 打印用户信息
	 * 
	 * @param userList
	 */
	private static void printUserList(List<User> userList) {
		for (User user : userList) {
			if (user.getId() != null) {
				System.out.format("DELETE FROM `user` where user_id=%d;\n",
						user.getId());
			}
			
			System.out
					.format("INSERT INTO `user` (`user_id`, `name`, `password`, `token`, `ownapps`,`role_id`) VALUES (%d,"
							+ " '%s', " + "'%s', '%s','%s', '%d');\n",
							user.getId(), user.getName(), user.getPassword(),
							user.getToken(), user.getOwnApps(),
							user.getRoleId());
		}
		
		System.out.println("\n");
	}
}
