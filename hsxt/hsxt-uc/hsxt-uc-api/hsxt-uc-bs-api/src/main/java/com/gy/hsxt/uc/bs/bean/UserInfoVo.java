/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.bs.bean;

import java.io.Serializable;

/**
 * Dubbo demo实体对象
 * 
 * @Package: com.gy.hsxt.dubbo.bean
 * @ClassName: UserInfo
 * @Description: TODO
 * 
 * @author: guiyi147
 * @date: 2015-9-18 下午8:22:10
 * @version V1.0
 */
public class UserInfoVo  implements Serializable{

    private static final long serialVersionUID = -3732657721457525680L;

    /** ID */
    private int userId;

    /** 姓名 */
    private String userName;

    /** 年龄 */
    private int age;

    public UserInfoVo() {

    }

    public UserInfoVo(int userId, String userName, int age) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserInfo [userId=" + userId + ", userName=" + userName + ", age=" + age + "]";
    }

}
