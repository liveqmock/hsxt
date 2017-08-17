package com.gy.hsi.ds.login.dao.impl;

import org.springframework.stereotype.Repository;

import com.gy.hsi.ds.common.thirds.dsp.common.dao.AbstractDao;
import com.gy.hsi.ds.login.beans.bo.User;
import com.gy.hsi.ds.login.dao.ISignDao;

/**
 * @author liaoqiqi
 * @version 2013-11-28
 */
@Repository
public class SignDaoImpl extends AbstractDao<Long, User> implements ISignDao {

}