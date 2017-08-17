package com.gy.hsi.ds.login.dao.impl;

import org.springframework.stereotype.Repository;

import com.gy.hsi.ds.common.thirds.dsp.common.dao.AbstractDao;
import com.gy.hsi.ds.login.beans.bo.Role;
import com.gy.hsi.ds.login.dao.IRoleDao;

/**
 * @author liaoqiqi
 * @version 2014-1-14
 */
@Repository
public class RoleDaoImpl extends AbstractDao<Integer, Role> implements IRoleDao {

}
