package com.gy.hsi.ds.login.dao;

import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.dao.BaseDao;
import com.gy.hsi.ds.login.beans.bo.User;

/**
 * @author liaoqiqi
 * @version 2013-11-28
 */
public interface IUserDao extends BaseDao<Long, User> {

    void executeSql(String sql);

    User getUserByName(String name);

}
