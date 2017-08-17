package com.gy.hsi.ds.login.dao.impl;

import org.springframework.stereotype.Repository;

import com.gy.hsi.ds.common.constant.Columns;
import com.gy.hsi.ds.common.thirds.dsp.common.dao.AbstractDao;
import com.gy.hsi.ds.login.beans.bo.User;
import com.gy.hsi.ds.login.dao.IUserDao;

/**
 * @author liaoqiqi
 * @version 2013-11-28
 */
@Repository
public class UserDaoImpl extends AbstractDao<Long, User> implements IUserDao {

    /**
     * 执行SQL
     */
    public void executeSql(String sql) {

        executeSQL(sql, null);
    }

    /**
     */
    @Override
    public User getUserByName(String name) {

        return findOne(match(Columns.NAME, name));
    }

}