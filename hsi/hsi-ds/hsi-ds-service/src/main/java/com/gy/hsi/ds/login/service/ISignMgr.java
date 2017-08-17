package com.gy.hsi.ds.login.service;

import com.gy.hsi.ds.login.beans.bo.User;

/**
 * @author liaoqiqi
 * @version 2014-2-6
 */
public interface ISignMgr {

    User getUserByName(String name);

    boolean validate(String userPassword, String passwordToBeValidate);

    User signin(String userName);

}
